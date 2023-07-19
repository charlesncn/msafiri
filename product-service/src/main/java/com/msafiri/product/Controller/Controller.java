package com.msafiri.product.Controller;

import com.msafiri.product.dto.request.ProductRequest;
import com.msafiri.product.dto.response.ApiResponse;
import com.msafiri.product.exception.ProductNotFoundException;
import com.msafiri.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class Controller {
    private final ProductService productService;

    public Controller(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping()
    public ResponseEntity<?> getProduct(@RequestParam(value = "id") String id) throws ProductNotFoundException {
        return productService.getProduct(id);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteProduct(@RequestParam(value = "id") String id) throws ProductNotFoundException {
        if (id == null || id.isEmpty()) throw new ProductNotFoundException("Invalid product id");
        return productService.deleteProduct(id);
    }
}
