package com.msafiri.orderservice.service;

import com.msafiri.orderservice.dto.reesponse.ApiResponse;
import com.msafiri.orderservice.dto.request.OrderItemDto;
import com.msafiri.orderservice.dto.request.OrderRequest;
import com.msafiri.orderservice.entity.Order;
import com.msafiri.orderservice.entity.OrderItem;
import com.msafiri.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PlaceOrderService {
    private final OrderRepository orderRepository;

    public PlaceOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<?> createOrder(OrderRequest orderRequest) {
        try{
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setDescription("Glovo");
            List<OrderItem> orderItemList = orderRequest.getOrderItemList().stream().map(this::mapToEntity).toList();

            order.setOrderItems(orderItemList);

            orderRepository.save(order);
            return new ResponseEntity<>(ApiResponse.successResponse("Order created successfully"), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
