package com.msafiri.inventoryservice.service;

import com.msafiri.inventoryservice.dto.reponse.ApiResponse;
import com.msafiri.inventoryservice.dto.reponse.InventoryResponse;
import com.msafiri.inventoryservice.dto.request.NewProductRequest;
import com.msafiri.inventoryservice.entity.Inventory;
import com.msafiri.inventoryservice.exception.ProductNotFoundException;
import com.msafiri.inventoryservice.repository.InventoryRepository;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getInventoryStatus(List<String> id) {
        if (id.isEmpty()){
            List<Inventory> inventory = inventoryRepository.findAll();
            if (inventory.isEmpty())
                try {
                    throw new ProductNotFoundException("No items in inventory");
                } catch (ProductNotFoundException e) {
                    e.printStackTrace();
                }
            return new ResponseEntity<>(inventory.stream().map(inventoryRes ->
                    InventoryResponse.builder()
                            .productId(inventoryRes.getProductId())
                            .available(inventoryRes.getQuantity() > 0)
                            .quantity(inventoryRes.getQuantity())
                            .build()).toList(),
                    HttpStatus.OK);
        }

        List<Inventory> inventory = inventoryRepository.findByProductIdIn(id);
        System.out.println("Items in Inventory: " + inventory);

        return new ResponseEntity<>(inventory.stream().map(inventoryRes ->
                InventoryResponse.builder()
                        .productId(inventoryRes.getProductId())
                        .available(inventoryRes.getQuantity() > 0)
                        .quantity(inventoryRes.getQuantity())
                        .build()).toList(),
                HttpStatus.OK);
    }

    public ResponseEntity<?> saveInventory(NewProductRequest request) {
        Inventory inventory = new Inventory();
        inventory.setProductId(String.valueOf(request.getProductId()));
        inventory.setQuantity(request.getQuantity());
        try {
            inventoryRepository.save(inventory);
            return new ResponseEntity<>(ApiResponse.successResponse("saved successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unable to save, try again");
        }
    }
}
