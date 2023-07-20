package com.msafiri.orderservice.controller;

import com.msafiri.orderservice.dto.reesponse.ApiResponse;
import com.msafiri.orderservice.dto.request.OrderRequest;
import com.msafiri.orderservice.exception.ItemNotFoundException;
import com.msafiri.orderservice.service.PlaceOrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final PlaceOrderService orderService;

    public OrderController(PlaceOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @CircuitBreaker(name = "inventory" , fallbackMethod = "placeOrderFallback")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<Object> placeOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return orderService.createOrder(orderRequest);
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
                return CompletableFuture
                        .completedFuture(ResponseEntity
                                .status(INTERNAL_SERVER_ERROR)
                                .body(ApiResponse.errorResponse("Something went wrong, please try again later")));
            }
        });
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CompletableFuture<ResponseEntity<?>> placeOrderFallback(OrderRequest orderRequest, RuntimeException ex) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ApiResponse.errorResponse("Something went wrong, please try again later")));
    }

//    get all orders || get order by id || get order by customer id || get order by product id


//    update order
//    delete order

}
