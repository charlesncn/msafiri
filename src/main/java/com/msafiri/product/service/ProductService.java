package com.msafiri.product.service;

import com.msafiri.product.dto.request.ProductRequest;
import com.msafiri.product.dto.response.ApiResponse;
import com.msafiri.product.model.Product;
import com.msafiri.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<ApiResponse> createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(BigDecimal.valueOf(productRequest.getPrice()))
                .category(productRequest.getCategory())
                .image(productRequest.getImage())
                .quantity(String.valueOf(productRequest.getQuantity()))
                .build();
        productRepository.save(product);
        log.info("Product created {} successfully", product.getProductId());
        return new ResponseEntity<>(ApiResponse.successResponse(HttpStatus.CREATED, "Product created successfully", null), HttpStatus.CREATED);
    }
}
