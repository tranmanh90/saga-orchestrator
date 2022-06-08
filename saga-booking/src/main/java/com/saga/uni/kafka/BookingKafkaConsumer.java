package com.saga.uni.kafka;

import com.saga.uni.service.IReservationService;
import com.saga.uni.vo.ReservationCommand;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class BookingKafkaConsumer {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final IReservationService iReservationService;

    public BookingKafkaConsumer(IReservationService iReservationService) {
        this.iReservationService = iReservationService;
    }

    @KafkaListener(topics = "reservation_request")
    public void listen(ConsumerRecord<String, ReservationCommand> cr) throws Exception {
        ReservationCommand reservationCommand = cr.value();
        logger.info("Received Reservation Command: " + reservationCommand);
        iReservationService.processReservationCommand(reservationCommand);
    }
}