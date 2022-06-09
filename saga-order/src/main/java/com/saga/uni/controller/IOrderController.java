package com.saga.uni.controller;

import com.saga.uni.dto.CreateOrderRequest;
import com.saga.uni.dto.CreateOrderResponse;
import com.saga.uni.dto.UpdateOrderRequest;
import com.saga.uni.dto.UpdateOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/order")
public interface IOrderController {

    @PostMapping("/createOrder")
    ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request);

    @PutMapping("/updateOrder")
    ResponseEntity<UpdateOrderResponse> updateOrder(@RequestBody UpdateOrderRequest request);
}
