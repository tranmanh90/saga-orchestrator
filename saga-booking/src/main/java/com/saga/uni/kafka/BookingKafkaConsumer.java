package com.saga.uni.kafka;

import com.saga.uni.model.ReservationCommand;
import com.saga.uni.service.IReservationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class BookingKafkaConsumer {

    private static final String RESERVATION_REQUEST = "reservation_request";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final IReservationService iReservationService;

    public BookingKafkaConsumer(IReservationService iReservationService) {
        this.iReservationService = iReservationService;
    }

    @KafkaListener(topics = RESERVATION_REQUEST)
    public void listen(ConsumerRecord<String, ReservationCommand> cr) {
        ReservationCommand reservationCommand = cr.value();
        logger.info("Received Reservation Command: " + reservationCommand);
        iReservationService.processReservationCommand(reservationCommand);
    }
}