package com.ekart.inventoryservice.controller;

import com.ekart.inventoryservice.dto.InventoryRequestDTO;
import com.ekart.inventoryservice.dto.InventoryResponseDTO;
import com.ekart.inventoryservice.entity.Inventory;
import com.ekart.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }



    @PutMapping("/{id}")
    public Inventory updateInventory(
            @PathVariable Long id,
            @RequestBody Inventory inventory) {

        return inventoryService.updateInventory(id, inventory);
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id);
    }

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @DeleteMapping("/{id}")
    public String deleteInventory(@PathVariable Long id) {

        inventoryService.deleteInventory(id);

        return "Inventory deleted successfully";
    }

    @GetMapping("/product/{productId}")
    public Inventory getInventoryByProductId(
            @PathVariable Long productId) {

        return inventoryService.getInventoryByProductId(productId);
    }
}