package com.msafiri.inventoryservice.service;

import com.msafiri.inventoryservice.dto.reponse.ApiResponse;
import com.msafiri.inventoryservice.dto.request.NewProductRequest;
import com.msafiri.inventoryservice.entity.Inventory;
import com.msafiri.inventoryservice.repository.InventoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getInventoryStatus(Long id) {
        Optional<?> inventory = inventoryRepository.findByProductId(id);
        return inventory.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    public ResponseEntity<?> saveInventory(NewProductRequest request) {
        Inventory inventory = new Inventory();
        inventory.setProductId(request.getProductId());
        inventory.setQuantity(request.getQuantity());
        try {
            inventoryRepository.save(inventory);
            return new ResponseEntity<>(ApiResponse.successResponse("saved successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unable to save, try again");
        }
    }
}
