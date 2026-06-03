package com.ekart.orderservice.service;

import com.ekart.orderservice.dto.OrderRequestDTO;
import com.ekart.orderservice.dto.OrderResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface OrderService  {

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO createOrder(@Valid OrderRequestDTO request);

    List<OrderResponseDTO> getAllorder();

    OrderResponseDTO updateOrder(@Valid Long id, OrderRequestDTO request);

    boolean deleteOrder(Long id);
}

