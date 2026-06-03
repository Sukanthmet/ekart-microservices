package com.ekart.common.event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private String status;
    private Integer oldQuantity;
    private Integer newQuantity;
}
