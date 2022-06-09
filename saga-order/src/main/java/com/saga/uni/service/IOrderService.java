package com.saga.uni.service;

import com.saga.uni.dto.CreateOrderRequest;
import com.saga.uni.dto.CreateOrderResponse;
import com.saga.uni.dto.UpdateOrderRequest;
import com.saga.uni.dto.UpdateOrderResponse;

public interface IOrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);

    UpdateOrderResponse updateOrder(UpdateOrderRequest request);
}
