package com.ekart.orderservice.service;

import com.ekart.common.event.OrderEvent;
import com.ekart.orderservice.Client.InventoryClient;
import com.ekart.orderservice.Client.ProductClient;
import com.ekart.orderservice.dto.InventoryResponseDTO;
import com.ekart.orderservice.dto.ProductResponseDTO;
import com.ekart.orderservice.dto.OrderRequestDTO;
import com.ekart.orderservice.dto.OrderResponseDTO;
import com.ekart.orderservice.entity.Order;
import com.ekart.orderservice.kafka.OrderProducer;
import com.ekart.orderservice.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final InventoryClient inventoryClient;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;

    public OrderServiceImpl(OrderRepo orderRepo,
                            InventoryClient inventoryClient,
                            ProductClient productClient,
                            OrderProducer orderProducer) {
        this.orderRepo = orderRepo;
        this.inventoryClient = inventoryClient;
        this.productClient = productClient;
        this.orderProducer = orderProducer;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        // 🔥 1. CHECK INVENTORY (Stock validation)
        InventoryResponseDTO inventory =
                inventoryClient.getInventoryByProductId(request.getProductId());

        if (inventory == null) {
            throw new RuntimeException("Inventory not found for product");
        }

        if (inventory.getQuantity() < request.getQuantity()) {
            throw new RuntimeException(
                    "Insufficient stock. Available: " + inventory.getQuantity()
            );
        }

        // 🔥 2. GET PRODUCT (Price from Product Service)
        ProductResponseDTO product =
                productClient.getProductById(request.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // 🔥 3. CALCULATE TOTAL AMOUNT
        BigDecimal totalAmount = product.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        // 🔥 4. CREATE ORDER
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
       // order.setId(request.getId());
        order.setStatus("CREATED");
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepo.save(order);

        // 🔥 5. SEND KAFKA EVENT (after success)
        OrderEvent event = new OrderEvent();
        event.setOrderId(savedOrder.getId());
        event.setProductId(savedOrder.getProductId());
        event.setQuantity(savedOrder.getQuantity());
        event.setStatus("CREATED");

        orderProducer.sendOrderEvent(event);

        return mapToResponseDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllorder() {
        return orderRepo.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO request) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        InventoryResponseDTO inventory =
                inventoryClient.getInventoryByProductId(request.getProductId());

        int oldQuantity = order.getQuantity();
        int newQuantity = request.getQuantity();

        int additionalRequired = newQuantity - oldQuantity;

        if (additionalRequired > 0 &&
                inventory.getQuantity() < additionalRequired) {

            throw new RuntimeException(
                    "Insufficient stock for update");
        }

        ProductResponseDTO product =
                productClient.getProductById(request.getProductId());

        order.setProductId(request.getProductId());
        order.setQuantity(newQuantity);
        order.setStatus("UPDATED");

        BigDecimal totalAmount = product.getPrice()
                .multiply(BigDecimal.valueOf(newQuantity));

        order.setTotalAmount(totalAmount);

        Order updated = orderRepo.save(order);

        // Send update event
        OrderEvent event = new OrderEvent();
        event.setOrderId(updated.getId());
        event.setProductId(updated.getProductId());
        event.setOldQuantity(oldQuantity);
        event.setNewQuantity(newQuantity);

        orderProducer.sendOrderUpdateEvent(event);

        return mapToResponseDTO(updated);
    }


    @Override
    public boolean deleteOrder(Long id) {

        Order order = orderRepo.findById(id).orElse(null);

        if (order == null) {
            return false;
        }

        // 🔥 SEND CANCEL EVENT (Inventory will restore stock)
        OrderEvent event = new OrderEvent();
        event.setOrderId(order.getId());
        event.setProductId(order.getProductId());
        event.setQuantity(order.getQuantity());
        event.setStatus("CANCELLED");

        orderProducer.sendOrderCancelEvent(event);

        orderRepo.delete(order);
        return true;
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToResponseDTO(order);
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setProductId(order.getProductId());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        return dto;
    }
}