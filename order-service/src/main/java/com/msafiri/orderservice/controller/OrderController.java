package com.msafiri.orderservice.controller;

import com.msafiri.orderservice.dto.request.OrderRequest;
import com.msafiri.orderservice.exception.ItemNotFoundException;
import com.msafiri.orderservice.service.PlaceOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final PlaceOrderService orderService;

    public OrderController(PlaceOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody @Valid OrderRequest orderRequest) throws ItemNotFoundException {
        return orderService.createOrder(orderRequest);
    }

//    get all orders || get order by id || get order by customer id || get order by product id


//    update order
//    delete order

}
