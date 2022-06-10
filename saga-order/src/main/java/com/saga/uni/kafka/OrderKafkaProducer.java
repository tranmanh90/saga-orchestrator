package com.saga.uni.kafka;

import com.saga.uni.model.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderKafkaProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderKafkaProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceMessage(String topic, OrderCreatedEvent data) {
        try {
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            log.warn("Publish message kafka to crm fail! Message " + data);
        }
    }
}
