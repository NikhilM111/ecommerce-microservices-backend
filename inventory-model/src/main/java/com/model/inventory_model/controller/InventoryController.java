package com.model.inventory_model.controller;

import com.model.inventory_model.entities.Inventory;
import com.model.inventory_model.service.InventoryService;
import com.model.inventory_model.utilites.InventoryStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryStockService inventoryStockService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAll() {
        List<Inventory> result = inventoryService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("{inventoryId}")
    public ResponseEntity<Inventory> getById(@PathVariable("inventoryId") Long inventoryId) {
        Optional<Inventory> result = inventoryService.getById(inventoryId);
        return ResponseEntity.ok().body(result.get());
    }

    @PostMapping
    public ResponseEntity<Inventory> create(@RequestBody Inventory payLoad) {
        Inventory result = inventoryStockService.addStock(payLoad.getSkuCode(), payLoad.getQuantity(), payLoad.getCreatedBy());
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("{inventoryId}")
    public ResponseEntity<Inventory> update(@PathVariable("inventoryId") Long inventoryId, @RequestBody Inventory payLoad) {
        Inventory result = inventoryService.patchRecord(inventoryId, payLoad);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("in-stock")
    public ResponseEntity<Boolean> isInStock(@RequestParam("skuCode") String skuCode,
                                             @RequestParam("quantity") int requiredQuantity) {
        boolean result = inventoryStockService.isInStock(skuCode, requiredQuantity);
        return ResponseEntity.ok().body(result);
    }
}
