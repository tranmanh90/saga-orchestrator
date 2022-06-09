package com.saga.uni.kafka;

import com.saga.uni.exception.InsufficientBalanceException;
import com.saga.uni.model.PaymentRequest;
import com.saga.uni.model.PaymentResponse;
import com.saga.uni.service.ITransactionService;
import com.saga.uni.vo.ResultType;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class PaymentKafkaConsumer {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static final String PAYMENT_REQUEST = "payment_request";
    private static final String PAYMENT_RESPONSE = "payment_response";

    private final ITransactionService iTransactionService;
    private final PaymentKafkaProducer producer;

    @KafkaListener(topics = PAYMENT_REQUEST)
    public void listen(ConsumerRecord<String, PaymentRequest> cr) {
        PaymentRequest transaction = cr.value();
        logger.info("Received Reservation Command: " + transaction);
        processTransaction(transaction);
    }

    private void processTransaction(PaymentRequest transaction) {
        PaymentResponse transactionResult = null;
        try {
            String accountNo = transaction.getAccountNo();
            iTransactionService.processTransaction(accountNo, transaction);
            transactionResult = new PaymentResponse(
                    transaction.getAccountNo(),
                    transaction.getTrxIdentifier(),
                    transaction.getValue(),
                    ResultType.APPROVED,
                    null);
        } catch (NoResultException e) {
            transactionResult = new PaymentResponse(
                    transaction.getAccountNo(),
                    transaction.getTrxIdentifier(),
                    transaction.getValue(),
                    ResultType.DENIED,
                    "No Balance for account " + transaction.getAccountNo());
        } catch (InsufficientBalanceException e) {
            transactionResult = new PaymentResponse(
                    transaction.getAccountNo(),
                    transaction.getTrxIdentifier(),
                    transaction.getValue(),
                    ResultType.DENIED,
                    e.getMessage());
        } finally {
            if (transactionResult != null) {
                producer.produceMessage(PAYMENT_RESPONSE, transactionResult);
            }
        }
    }
}
