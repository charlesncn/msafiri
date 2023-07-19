package com.msafiri.inventoryservice.controller;

import com.msafiri.inventoryservice.dto.request.NewProductRequest;
import com.msafiri.inventoryservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<?> getInventoryStatus(@RequestParam("id") List<String> id) {
        return inventoryService.getInventoryStatus(id);
    }

    //    save inventory
    @PostMapping
    public ResponseEntity<?> saveInventory(@RequestBody NewProductRequest inventory) {
        return inventoryService.saveInventory(inventory);
    }
}
