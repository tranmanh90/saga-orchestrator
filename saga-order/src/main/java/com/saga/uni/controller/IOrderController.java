package com.saga.uni.controller;

import com.saga.uni.dto.CreateOrderRequest;
import com.saga.uni.dto.CreateOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/order")
public interface IOrderController {

    @PostMapping("/createOrder")
    ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request);
}
