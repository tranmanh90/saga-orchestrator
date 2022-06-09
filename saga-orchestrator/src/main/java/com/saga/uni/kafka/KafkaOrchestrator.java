package com.saga.uni.kafka;

import com.saga.uni.model.*;
import com.saga.uni.serdes.SerdesFactory;
import com.saga.uni.vo.ReservationRequest;
import com.saga.uni.vo.RoomStatus;
import com.saga.uni.vo.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.logging.Logger;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KafkaOrchestrator {

    private static final String PAYMENT_REQUEST = "payment_request";
    private static final String PAYMENT_RESPONSE = "payment_response";
    private static final String ORDER_REQUEST = "order_request";
    private static final String ORDER_RESPONSE = "order_response";
    private static final String RESERVATION_REQUEST = "reservation_request";
    private static final String RESERVATION_RESPONSE = "reservation_response";

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Bean
    Topology orchestratorTopology(StreamsBuilder builder) {
        /** Get Order and request reservation **/
        KStream<String, OrderCreatedEvent> orders = getPendingOrders(builder);
        requestRoomReservation(orders);

        /** Get room reservation and send payment or cancel request **/
        KStream<String, ReservationResult>[] reservationResults = getRoomReservationResponse(builder);
        sendPaymentRequestForApprovedReservations(reservationResults[0]);
        cancelOrderForDeniedReservations(reservationResults[1]);

        /** Get Payment response and send to Order **/
        KStream<String, PaymentResponse> paymentResponseStream = getPaymentResponseStream(builder);
        sendOrderResponse(paymentResponseStream);
        sendBookingCommand(paymentResponseStream);
        return builder.build();
    }

    private void cancelOrderForDeniedReservations(KStream<String, ReservationResult> reservationKStream) {
        KStream<String, OrderCommand> orderResponseStream = reservationKStream.mapValues((reservation) -> {
            return new OrderCommand(reservation.getOrder(), OrderCommand.OrderRequest.CANCEL, reservation.getCause());
        });
        orderResponseStream.foreach((key, value) -> logger.info("Sending Order response: " + value));
        orderResponseStream.to(ORDER_RESPONSE, Produced.with(Serdes.String(), SerdesFactory.getSerde(OrderCommand.class)));
    }

    private KStream<String, ReservationResult>[] getRoomReservationResponse(StreamsBuilder builder) {
        KStream<String, ReservationResult> results[] = builder.stream(RESERVATION_RESPONSE,
                Consumed.with(Serdes.String(), SerdesFactory.getSerde(ReservationResult.class)))
                .branch(
                        (key, value) -> RoomStatus.RESERVED.equals(value.getReservationStatus()),
                        (key, value) -> (value.getCause() != null)
                );
        results[0].foreach((key, value) -> logger.info("Processing Reservation response: " + value));
        results[1].foreach((key, value) -> logger.info("Processing Reservation exception: " + value));
        return results;
    }

    private void requestRoomReservation(KStream<String, OrderCreatedEvent> orderCreatedStream) {
        KStream<String, ReservationCommand> reservationRequestStream = orderCreatedStream.mapValues((value) -> {
            log.warn(String.valueOf(value));
            return new ReservationCommand(value.getId(), ReservationRequest.RESERVE);
        });
        reservationRequestStream.foreach((key, value) -> logger.info("Requesting a Room " + value));
        reservationRequestStream.to(RESERVATION_REQUEST,
                Produced.with(Serdes.String(), SerdesFactory.getSerde(ReservationCommand.class)));
    }

    private void sendBookingCommand(KStream<String, PaymentResponse> paymentResponseStream) {
        KStream<String, ReservationCommand> orderResponseStream = paymentResponseStream.mapValues((paymentReponse) -> {
            ReservationRequest reservationRequest = null;
            switch (paymentReponse.getResultType()) {
                case APPROVED:
                    reservationRequest = ReservationRequest.CONFIRM;
                    break;
                case DENIED:
                    reservationRequest = ReservationRequest.CANCEL;
                    break;
            }
            return new ReservationCommand(paymentReponse.getTrxIdentifier(), reservationRequest);
        });
        orderResponseStream.foreach((key, value) -> logger.info("Sending Reservation command: " + value));
        orderResponseStream.to(RESERVATION_REQUEST, Produced.with(Serdes.String(), SerdesFactory.getSerde(ReservationCommand.class)));
    }


    private void sendOrderResponse(KStream<String, PaymentResponse> paymentResponseStream) {
        KStream<String, OrderCommand> orderResponseStream = paymentResponseStream.mapValues((paymentResponse) -> {
            OrderCommand.OrderRequest orderRequest = null;
            String cause = paymentResponse.getCause();
            switch (paymentResponse.getResultType()) {
                case APPROVED:
                    orderRequest = OrderCommand.OrderRequest.CONFIRM;
                    break;
                case DENIED:
                    orderRequest = OrderCommand.OrderRequest.CANCEL;
                    break;
            }
            return new OrderCommand(paymentResponse.getTrxIdentifier(), orderRequest, cause);
        });
        orderResponseStream.foreach((key, value) -> logger.info("Sending Order response: " + value));
        orderResponseStream.to(ORDER_RESPONSE, Produced.with(Serdes.String(), SerdesFactory.getSerde(OrderCommand.class)));
    }

    private KStream<String, PaymentResponse> getPaymentResponseStream(StreamsBuilder builder) {
        KStream<String, PaymentResponse> paymentOutputStream = builder.stream(PAYMENT_RESPONSE, Consumed.with(Serdes.String(), SerdesFactory.getSerde(PaymentResponse.class)))
                .filter((key, value) -> value != null);
        paymentOutputStream.foreach((key, value) -> logger.info("Reading Payment result: " + value));
        return paymentOutputStream;
    }

    private KStream<String, PaymentRequest> sendPaymentRequestForApprovedReservations(KStream<String, ReservationResult> reservationResult) {
        KStream<String, PaymentRequest> paymentStream = reservationResult.mapValues((reservation) -> {
            return new PaymentRequest("8668111", reservation.getOrder(), TransactionType.WITHDRAW, reservation.getPrice());
        });
        paymentStream.foreach((key, value) -> logger.info("Sending Payment request: " + value));
        paymentStream.to(PAYMENT_REQUEST, Produced.with(Serdes.String(), SerdesFactory.getSerde(PaymentRequest.class)));
        return paymentStream;
    }

    private KStream<String, OrderCreatedEvent> getPendingOrders(StreamsBuilder builder) {
        KStream<String, OrderCreatedEvent> orderInputStream = builder.stream(ORDER_REQUEST, Consumed.with(Serdes.String(), SerdesFactory.getSerde(OrderCreatedEvent.class)));
        orderInputStream.filter((key, value) -> value != null)
                .filter((key, value) -> value.getStatus() == OrderCreatedEvent.OrderStatus.PENDING)
                .foreach((key, value) -> logger.info("Reading PENDING Order: " + value));
        return orderInputStream;
    }
}