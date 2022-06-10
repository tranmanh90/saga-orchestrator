package com.saga.uni.kafka;

import com.saga.uni.dto.UpdateOrderRequest;
import com.saga.uni.model.OrderCommand;
import com.saga.uni.model.OrderCreatedEvent;
import com.saga.uni.model.ReservationCommand;
import com.saga.uni.service.IOrderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OrderKafkaConsumer {

    private static final String ORDER_RESPONSE = "order_response";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final IOrderService iOrderService;

    public OrderKafkaConsumer(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @KafkaListener(topics = ORDER_RESPONSE)
    public void listen(ConsumerRecord<String, OrderCommand> cr) {
        OrderCommand orderCreatedEvent = cr.value();
        logger.info("Received Reservation Command: " + orderCreatedEvent);
        UpdateOrderRequest request =  UpdateOrderRequest.builder()
                .id(orderCreatedEvent.getId())
                .status(orderCreatedEvent.getStatus())
                .cause(orderCreatedEvent.getCause())
                .build();
        iOrderService.updateOrder(request);
    }
}