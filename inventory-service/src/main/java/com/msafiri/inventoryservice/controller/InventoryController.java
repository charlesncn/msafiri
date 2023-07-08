package com.msafiri.inventoryservice.controller;

import com.msafiri.inventoryservice.dto.request.NewProductRequest;
import com.msafiri.inventoryservice.service.InventoryService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<?> getInventoryStatus(@Param("id") String id) {
        return inventoryService.getInventoryStatus(Long.valueOf(id));
    }

//    save inventory
    @PostMapping
    public ResponseEntity<?> saveInventory(@RequestBody NewProductRequest inventory) {
        return inventoryService.saveInventory(inventory);
    }
}
