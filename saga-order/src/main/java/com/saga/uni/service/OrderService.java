package com.saga.uni.service;

import com.saga.uni.dto.CreateOrderRequest;
import com.saga.uni.dto.CreateOrderResponse;
import com.saga.uni.entity.Order;
import com.saga.uni.kafka.OrderKafkaProducer;
import com.saga.uni.repository.IOrderRepository;
import com.saga.uni.util.JsonService;
import com.saga.uni.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository iOrderRepository;
    private final OrderKafkaProducer producer;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        Order order = iOrderRepository.save(Mapper.mapper(request, Order.class));
        String str = JsonService.toString(order);
        producer.produceMessage(str, "order_request");
        return Mapper.mapper(order, CreateOrderResponse.class);
    }
}
