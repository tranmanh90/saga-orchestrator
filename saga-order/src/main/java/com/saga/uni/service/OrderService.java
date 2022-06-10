package com.saga.uni.service;

import com.saga.uni.dto.CreateOrderRequest;
import com.saga.uni.dto.CreateOrderResponse;
import com.saga.uni.dto.UpdateOrderRequest;
import com.saga.uni.dto.UpdateOrderResponse;
import com.saga.uni.entity.Order;
import com.saga.uni.kafka.OrderKafkaProducer;
import com.saga.uni.model.OrderCreatedEvent;
import com.saga.uni.repository.IOrderRepository;
import com.saga.uni.util.JsonService;
import com.saga.uni.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private static final String ORDER_REQUEST = "order_request";

    private final IOrderRepository iOrderRepository;
    private final OrderKafkaProducer producer;

    /**
     * Create order
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        /** Save order to database **/
        Order order = iOrderRepository.save(Mapper.mapper(request, Order.class));

        /** Save to outbox database **/
        // todo

        /** Send order request to orchestrator **/
        OrderCreatedEvent createdEvent = OrderCreatedEvent.builder()
                .id(order.getId())
                .status(order.getStatus())
                .cause(order.getCause())
                .build();
        producer.produceMessage(ORDER_REQUEST, createdEvent);
        return Mapper.mapper(order, CreateOrderResponse.class);
    }

    /**
     * Update order
     *
     * @param request
     * @return
     */
    @Override
    public UpdateOrderResponse updateOrder(UpdateOrderRequest request) {
        /** Save order to database **/
        Order order = iOrderRepository.save(Mapper.mapper(request, Order.class));

        /** Save to outbox database **/
        // todo

        return Mapper.mapper(order, UpdateOrderResponse.class);
    }
}
