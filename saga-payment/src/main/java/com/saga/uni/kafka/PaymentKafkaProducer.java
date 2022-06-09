package com.saga.uni.kafka;

import com.saga.uni.vo.TransactionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentKafkaProducer {

    private final KafkaTemplate<String, TransactionResult> kafkaTemplate;

    public PaymentKafkaProducer(KafkaTemplate<String, TransactionResult> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceMessage(String topic, TransactionResult data) {
        try {
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            log.warn("Publish message kafka to crm fail! Message " + data);
        }
    }
}
