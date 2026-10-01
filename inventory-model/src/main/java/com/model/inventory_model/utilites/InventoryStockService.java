package com.model.inventory_model.utilites;

import com.model.inventory_model.entities.Inventory;
import com.model.inventory_model.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryStockService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, int requiredQuantity) {
        return inventoryRepository.findBySkuCode(skuCode)
                .map(x -> x.getQuantity() >= requiredQuantity)
                .orElse(false);
    }

    public Inventory addStock(String skuCode, int quantity, Long userId) {
        Optional<Inventory> item = inventoryRepository.findBySkuCode(skuCode);

        if(item.isPresent()) {
            Inventory inventory = item.get();

            inventory.setQuantity(inventory.getQuantity() + quantity);
            return inventoryRepository.save(inventory);
        } else {
            Inventory newItem = new Inventory();
            newItem.setSkuCode(skuCode);
            newItem.setQuantity(quantity);
            newItem.setCreatedBy(userId);
            newItem.setCreatedDate(LocalDateTime.now());

            return inventoryRepository.save(newItem);
        }
    }
}
