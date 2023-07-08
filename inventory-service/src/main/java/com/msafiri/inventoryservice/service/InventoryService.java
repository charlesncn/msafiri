package com.msafiri.inventoryservice.service;

import com.msafiri.inventoryservice.repository.InventoryRepository;
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
        return inventory.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
