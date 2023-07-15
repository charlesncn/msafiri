package com.msafiri.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msafiri.orderservice.dto.request.OrderItemDto;
import com.msafiri.orderservice.dto.request.OrderRequest;
import com.msafiri.orderservice.repository.OrderRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

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
    private OrderRepository orderRepository;

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQL8Dialect");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.show_sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.use_sql_comments", () -> "true");
        registry.add("spring.datasource.initialization-mode", () -> "always");
        registry.add("spring.datasource.sql.init.schema-locations", () -> "classpath:init-script.sql");
    }

    @Test
    void shouldPlaceOrder() throws Exception {
        OrderRequest productRequest = getOrderRequest();
        String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
        Assertions.assertTrue(orderRepository.findAll().size() > 0);
    }

    private OrderRequest getOrderRequest() {
        OrderItemDto orderItemDto = new OrderItemDto(
                1,
                "12",
                3,
                BigDecimal.valueOf(200.00));
        List<OrderItemDto> orderRequestList = new ArrayList<>();
        orderRequestList.add(orderItemDto);
        return new OrderRequest(orderRequestList);
    }


}
