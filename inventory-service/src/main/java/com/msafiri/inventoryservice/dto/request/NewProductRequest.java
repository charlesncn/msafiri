package com.msafiri.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductRequest {
    private long productId;
    private int quantity;
}
