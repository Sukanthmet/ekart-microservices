package com.ekart.orderservice.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long productId;
    private Integer quantity;
}