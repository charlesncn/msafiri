package com.msafiri.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msafiri.product.dto.request.ProductRequest;
import com.msafiri.product.model.Product;
import com.msafiri.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Testcontainers
@AutoConfigureMockMvc
class ProductApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.3");
//

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ProductRepository productRepository;

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.data.mongodb.socketKeepAlive", () -> true);


    }


    @Test
    void shouldSaveProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String request = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());


        Assertions.assertTrue(productRepository.findAll().size() > 0);
    }

    @Test
    void shouldGetProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(productRepository.findAll().size() > 0 ? status().isOk() : status().isNoContent());
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Test Product")
                .description("Test Product Description")
                .price(100.00)
                .category("Test Category")
                .image("Test Image")
                .quantity(10)
                .build();
    }

    @Test
    void shouldDeleteProduct() {
        ProductRequest productRequest = getProductRequest();
        productRepository.save(Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(BigDecimal.valueOf(productRequest.getPrice()))
                .category(productRequest.getCategory())
                .image(productRequest.getImage())
                .quantity(String.valueOf(productRequest.getQuantity()))
                .build());

        Optional<Product> productOptional = productRepository.findAll().stream().findFirst();
        productOptional.ifPresent(product -> {
            try {
                System.out.printf("Deleting product with id %s%n", product.getProductId());
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/product")
                                .param("id", product.getProductId())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

}
