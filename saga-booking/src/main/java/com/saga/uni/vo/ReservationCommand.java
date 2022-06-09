package com.saga.uni.vo;

import lombok.Data;

import java.util.UUID;

@Data
public class ReservationCommand {
    private UUID order;
    private ReservationRequest reservationRequest;

    public ReservationCommand() {
    }

    public ReservationCommand(UUID order, Integer room, ReservationRequest reservationRequest) {
        this.order = order;
        this.reservationRequest = reservationRequest;
        switch (reservationRequest) {
            case CANCEL:
            case CONFIRM:
                if (room == null || order == null) {
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