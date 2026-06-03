package com.ekart.inventoryservice.kafka;


import com.ekart.common.exception.InsufficientStockException;
import com.ekart.inventoryservice.entity.Inventory;
import com.ekart.inventoryservice.repository.InventoryRepository;
import com.ekart.common.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

    private final InventoryRepository inventoryRepository;

    public InventoryConsumer(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @KafkaListener(
            topics = "order-topic",
            groupId = "inventory-group")
    public void consume(OrderEvent event) {

        System.out.println("Received Order Event: " + event);

        Inventory inventory =
                inventoryRepository.findByProductId(
                                event.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException("Inventory not found"));
        if (inventory.getQuantity() < event.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock for product " + event.getProductId());
        }

        inventory.setQuantity(inventory.getQuantity() - event.getQuantity());
        inventoryRepository.save(inventory);

        System.out.println("Inventory Updated Successfully");
    }

    @KafkaListener(
            topics = "order-cancel-topic",
            groupId = "inventory-group")
    public void handleOrderCancel(OrderEvent event) {

        Inventory inventory =
                inventoryRepository.findByProductId(event.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException("Inventory not found"));

        inventory.setQuantity(
                inventory.getQuantity() + event.getQuantity()
        );

        inventoryRepository.save(inventory);

        System.out.println("Inventory restored after order cancellation");
    }

    @KafkaListener(
            topics = "order-update-topic",
            groupId = "inventory-group")
    public void handleOrderUpdate(OrderEvent event) {

        Inventory inventory = inventoryRepository
                .findByProductId(event.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Inventory not found"));

        int difference =
                event.getNewQuantity() - event.getOldQuantity();

        if (difference > 0) {

            if (inventory.getQuantity() < difference) {
                throw new InsufficientStockException(
                        "Insufficient stock");
            }

            inventory.setQuantity(
                    inventory.getQuantity() - difference);
        }
        else if (difference < 0) {

            inventory.setQuantity(
                    inventory.getQuantity() + Math.abs(difference));
        }

        inventoryRepository.save(inventory);

        System.out.println("Inventory updated after order modification");
    }
}



