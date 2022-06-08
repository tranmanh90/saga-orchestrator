package com.saga.uni.service;

import com.saga.uni.dto.CreateOrderRequest;
import com.saga.uni.dto.CreateOrderResponse;

public interface IOrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
}
