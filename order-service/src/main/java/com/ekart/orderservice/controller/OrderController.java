package com.ekart.orderservice.controller;

import com.ekart.orderservice.dto.OrderRequestDTO;
import com.ekart.orderservice.dto.OrderResponseDTO;
import com.ekart.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderservice;

    public OrderController(OrderService orderservice) {
        this.orderservice = orderservice;
    }

    // GET Orders BY ID

    @GetMapping("/{id}")

    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderservice.getOrderById(id));
    }

    @PostMapping()

//Create an order

    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderservice.createOrder(request));
    }

    // GET ALL orders

    @GetMapping()
    public ResponseEntity<List<OrderResponseDTO>> getAllOrder() {
        return ResponseEntity.ok(orderservice.getAllorder());
    }

@PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(
        @Valid @PathVariable Long id,@RequestBody OrderRequestDTO request) {
    return ResponseEntity.ok(orderservice.updateOrder(id, request));
        }

    @DeleteMapping("/{id}")
public ResponseEntity<String> deleteOrder(@PathVariable Long id){
         boolean deleted = orderservice.deleteOrder(id);
        System.out.println("DELETE ID = " + id);
        if (deleted)
            return ResponseEntity.ok("Order deleted successfully");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order not found");
    }
    }

