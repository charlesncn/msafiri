package com.msafiri.inventoryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msafiri.inventoryservice.dto.request.NewProductRequest;
import com.msafiri.inventoryservice.entity.Inventory;
import com.msafiri.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class InventoryServiceApplicationTests {


    @Container
    private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.23")
            .withDatabaseName("order-service")
            .withPassword("toor")
            .withUsername("root");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InventoryRepository inventoryRepository;

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQL8Dialect");
    }

    @Test
    void shouldAddInventory() throws Exception {
        NewProductRequest inventoryRequest = getInventoryRequest();
        String request = objectMapper.writeValueAsString(inventoryRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
        Assertions.assertTrue(inventoryRepository.findAll().size() > 0);
    }

    @Test
    void shouldGetInventoryById() throws Exception {
        NewProductRequest inventoryRequest = getInventoryRequest();
        inventoryRepository.save(getInventory());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory")
                        .param("id", String.valueOf(inventoryRequest.getProductId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private NewProductRequest getInventoryRequest() {
        return new NewProductRequest("35474", 3400);
    }

    private Inventory getInventory() {
        NewProductRequest request = getInventoryRequest();
        Inventory inventory = new Inventory();
        inventory.setProductId(request.getProductId());
        inventory.setQuantity(request.getQuantity());
        return inventory;
    }
}
