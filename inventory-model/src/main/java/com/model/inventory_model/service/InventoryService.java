package com.model.inventory_model.service;

import com.model.inventory_model.entities.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    public List<Inventory> getAll();

    Optional<Inventory> getById(Long inventoryId);

    Inventory patchRecord(Long inventoryId, Inventory payLoad);
}
