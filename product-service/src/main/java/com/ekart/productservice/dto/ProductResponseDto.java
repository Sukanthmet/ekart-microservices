package com.ekart.productservice.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductResponseDto {

    private Long id;
    private String productName;
    private BigDecimal price;
    private Integer availableQuantity;

    public ProductResponseDto() {
    }

    public ProductResponseDto(Long id, String productName, BigDecimal price, Integer availableQuantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }
}