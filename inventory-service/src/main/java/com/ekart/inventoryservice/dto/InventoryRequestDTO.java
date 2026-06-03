package com.ekart.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {

    @NotNull
    private Long productId;

    @NotNull
    @Min(0)
    private Integer quantity;
}