package com.msafiri.product.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "productId",
        "name",
        "description",
        "price",
        "category",
        "image",
        "quantity",
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private double price;
    private String category;
    private String image;
    private int quantity;
}
