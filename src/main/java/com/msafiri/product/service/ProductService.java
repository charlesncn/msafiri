package com.msafiri.product.service;

import com.msafiri.product.dto.request.ProductRequest;
import com.msafiri.product.dto.response.ApiResponse;
import com.msafiri.product.exception.ProductNotFoundException;
import com.msafiri.product.model.Product;
import com.msafiri.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
        try {
            productRepository.save(product);
        } catch (Exception e) {
            log.error("Unable to create product {}\nReason: {}", productRequest.getName(), e.getMessage());
            return new ResponseEntity<>(ApiResponse.errorResponse("Unable to process this request, Retry"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Product created {} successfully", product.getProductId());
        return new ResponseEntity<>(ApiResponse.successResponse(null), HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse> getProduct(String i) throws ProductNotFoundException {
        if (i == null || i.isEmpty()) {
            List<Product> products = productRepository.findAll();
            HttpStatus httpStatus = products.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;

            return new ResponseEntity<>(ApiResponse.successResponse(products), httpStatus);
        }
        Product product = productRepository.findByProductId(Integer.parseInt(i));
        if (product == null) throw new ProductNotFoundException("Product not found");

        return new ResponseEntity<>(ApiResponse.successResponse(new Object()), HttpStatus.OK);
    }
}
