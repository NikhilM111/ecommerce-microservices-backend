package com.model.inventory_model.business_logic;

import com.model.inventory_model.entities.Inventory;
import com.model.inventory_model.repository.InventoryRepository;
import com.model.inventory_model.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryBusinessLogic implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getAll() {
        List<Inventory> result = inventoryRepository.findAll();
        return result;
    }

    @Override
    public Optional<Inventory> getById(Long inventoryId) {
        Optional<Inventory> result = inventoryRepository.findById(inventoryId);
        return result;
    }

    @Override
    public Inventory patchRecord(Long inventoryId, Inventory payLoad) {

        Inventory existingRecord = getById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found with ID: " + inventoryId));

        if(existingRecord != null) {
            if(payLoad.getQuantity() > 0) {
               existingRecord.setQuantity(existingRecord.getQuantity() + payLoad.getQuantity());
            }
            if(payLoad.getSkuCode() != null) {
                existingRecord.setSkuCode(existingRecord.getSkuCode() + payLoad.getSkuCode());
            }

            inventoryRepository.save(existingRecord);
        } else {
            throw new RuntimeException("Inventory not found");
        }
        return existingRecord;
    }
}
