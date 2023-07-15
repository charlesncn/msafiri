package com.msafiri.orderservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    @NotEmpty(message = "Customer Id is required")
    private long customerId;
    @NotEmpty(message = "Product Id is required")
    private String productId;
    @NotEmpty(message = "Quantity is required")
    private int quantity;
    @NotEmpty(message = "Price is required")
    private BigDecimal price;
}
