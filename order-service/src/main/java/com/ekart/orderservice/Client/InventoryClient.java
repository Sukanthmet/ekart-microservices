package com.ekart.orderservice.Client;

import com.ekart.orderservice.dto.InventoryResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate;

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InventoryResponseDTO getInventoryByProductId(Long productId) {
        return restTemplate.getForObject(
                "http://localhost:8083/inventory/product/" + productId,
                InventoryResponseDTO.class
        );

    }
}