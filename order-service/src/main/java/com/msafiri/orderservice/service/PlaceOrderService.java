package com.msafiri.orderservice.service;

import com.msafiri.orderservice.dto.reesponse.ApiResponse;
import com.msafiri.orderservice.dto.reesponse.InventoryResponse;
import com.msafiri.orderservice.dto.request.OrderItemDto;
import com.msafiri.orderservice.dto.request.OrderRequest;
import com.msafiri.orderservice.entity.Order;
import com.msafiri.orderservice.entity.OrderItem;
import com.msafiri.orderservice.exception.ItemNotFoundException;
import com.msafiri.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlaceOrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public PlaceOrderService(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public ResponseEntity<?> createOrder(OrderRequest orderRequest) throws ItemNotFoundException {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setDescription("Glovo");
            List<OrderItem> orderItemList = orderRequest.getOrderItemList().stream().map(this::mapToEntity).toList();

            order.setOrderItems(orderItemList);

            List<String> productIdList = order.getOrderItems().stream().map(OrderItem::getProductId).map(String::valueOf).toList();
            System.out.println(productIdList);

            InventoryResponse[] results = webClient.get()
                    .uri("http://localhost:8082/api/v1/inventory",
                            uriBuilder -> uriBuilder.queryParam("id", productIdList).build())
                    .retrieve().bodyToMono(InventoryResponse[].class)
                    .block();

            System.out.println("Results: " + Arrays.toString(results));

            if ((results != null ? results.length : 0) <= 0) throw new ItemNotFoundException("Product(s) not found");

            boolean allProductsAvailable = Arrays.stream(results).allMatch(InventoryResponse::isAvailable);

            List<InventoryResponse> unavailableProducts = Arrays.stream(results).filter(
                            response -> !response.isAvailable())
                    .toList();

            List<String> nonExistingProducts = productIdList.stream()
                    .filter(item -> Arrays.stream(results).noneMatch(rs -> Objects.equals(rs.getProductId(), item)))
                    .toList();

            System.out.println("Unavailable products: " + unavailableProducts);

            if (unavailableProducts.size() > 0 || !allProductsAvailable || nonExistingProducts.size() > 0){
                List<String> missingProducts = new ArrayList<>(unavailableProducts.stream().map(InventoryResponse::getProductId).toList());
                missingProducts.addAll(nonExistingProducts);

                throw new ItemNotFoundException("Product(s) :" +
                        missingProducts + " are not available");

            }
            else {
                orderRepository.save(order);
                return new ResponseEntity<>(ApiResponse.successResponse("Order created successfully"), HttpStatus.CREATED);
            }
    }


    private OrderItem mapToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setStatus("NEW");
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setCustomerId(orderItemDto.getCustomerId());
        orderItem.setProductId(orderItemDto.getProductId());
        return orderItem;
    }
}
