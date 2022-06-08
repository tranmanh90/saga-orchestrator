package com.saga.uni.kafka;

import com.saga.uni.exception.InsufficientBalanceException;
import com.saga.uni.service.ITransactionService;
import com.saga.uni.vo.Transaction;
import com.saga.uni.vo.TransactionResult;
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

    private final ITransactionService iTransactionService;
    private final PaymentKafkaProducer producer;

    @KafkaListener(topics = "payment_request")
    public void listen(ConsumerRecord<String, Transaction> cr) throws Exception {
        Transaction transaction = cr.value();
        logger.info("Received Reservation Command: " + transaction);
        processTransaction(transaction);
    }

    private void processTransaction(Transaction transaction) {
        TransactionResult transactionResult = null;
        try {
            String accountNo = transaction.getAccountNo();
            iTransactionService.processTransaction(accountNo, transaction);
            transactionResult = new TransactionResult(transaction.getAccountNo(), transaction.getTrxIdentifier(),
                    transaction.getValue(), TransactionResult.ResultType.APPROVED, null);
        } catch (NoResultException e) {
            transactionResult = new TransactionResult(transaction.getAccountNo(), transaction.getTrxIdentifier(),
                    transaction.getValue(), TransactionResult.ResultType.DENIED, "No Balance for account " + transaction.getAccountNo());
        } catch (InsufficientBalanceException e) {
            transactionResult = new TransactionResult(transaction.getAccountNo(), transaction.getTrxIdentifier(),
                    transaction.getValue(), TransactionResult.ResultType.DENIED, e.getMessage());
        } finally {
            if (transactionResult != null) {
                producer.produceMessage(transactionResult);
            }
        }
    }
}
