package com.model.inventory_model.repository;

import com.model.inventory_model.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    public Optional<Inventory> findBySkuCode(String skuCode);
}
