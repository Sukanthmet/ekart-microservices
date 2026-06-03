package com.ekart.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data

public class ProductRequestDto {

    @NotBlank
    private String productName;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer availableQuantity;

    public ProductRequestDto(String productName, BigDecimal price, Integer availableQuantity) {
        this.productName = productName;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public String getName() {
        return productName;
    }

}
