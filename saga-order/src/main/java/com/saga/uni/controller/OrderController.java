package com.saga.uni.controller;

import com.saga.uni.dto.CreateOrderRequest;
import com.saga.uni.dto.CreateOrderResponse;
import com.saga.uni.service.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderController implements IOrderController {

    private final IOrderService iOrderService;

    @Override
    public ResponseEntity<CreateOrderResponse> createOrder(CreateOrderRequest request) {
        return ResponseEntity.ok(iOrderService.createOrder(request));
    }
}
