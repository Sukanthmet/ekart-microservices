package com.ekart.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderRequestDTO {
    @NotNull(message = "productId should not be null")
    @Positive(message = "productId should be positive")
    private Long productId;

    @NotNull(message = "quantity should not be null")
    @Positive(message = "quantity should be greater than 0")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderRequestDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public OrderRequestDTO() {
    }
}

