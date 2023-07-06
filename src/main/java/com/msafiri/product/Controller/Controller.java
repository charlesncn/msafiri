package com.msafiri.product.Controller;

import com.msafiri.product.dto.request.ProductRequest;
import com.msafiri.product.dto.response.ApiResponse;
import com.msafiri.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class Controller {
    private final ProductService productService;

    public Controller(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }
}
