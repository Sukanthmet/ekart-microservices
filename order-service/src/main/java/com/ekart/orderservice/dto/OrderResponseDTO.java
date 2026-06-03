package com.ekart.orderservice.dto;

import java.math.BigDecimal;

public class OrderResponseDTO {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal totalAmount;
    private String status;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Long orderId, Long productId, Integer quantity, BigDecimal totalAmount, String status) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
