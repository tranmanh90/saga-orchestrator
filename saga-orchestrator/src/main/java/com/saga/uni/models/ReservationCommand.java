package com.saga.uni.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ReservationCommand {
    private UUID order;
    private ReservationRequest reservationRequest;

    public ReservationCommand(UUID order, ReservationRequest reservationRequest) {
        this.order = order;
        this.reservationRequest = reservationRequest;
        switch (reservationRequest) {
            case CANCEL:
            case CONFIRM:
                if (order == null) {
                    throw new IllegalArgumentException(reservationRequest + " requires an order number");
                }
                break;
            default:
                break;
        }
    }

    public enum ReservationRequest {
        RESERVE, CONFIRM, CANCEL
    }


}