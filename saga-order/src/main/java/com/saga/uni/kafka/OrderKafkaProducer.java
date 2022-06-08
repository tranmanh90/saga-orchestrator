package com.saga.uni.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceMessage(String data, String topic) {
        try {
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            log.warn("Publish message kafka to crm fail! Message " + data);
        }
    }
}
