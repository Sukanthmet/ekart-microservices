package com.ekart.inventoryservice.service;

import com.ekart.inventoryservice.entity.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory createInventory(Inventory inventory);

    Inventory updateInventory(Long id, Inventory inventory);

    Inventory getInventoryById(Long id);

    List<Inventory> getAllInventory();

    void deleteInventory(Long id);

    Inventory getInventoryByProductId(Long productId);
}