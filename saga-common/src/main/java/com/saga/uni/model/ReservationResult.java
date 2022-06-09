package com.saga.uni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResult {
    private UUID order;
    private String room;
    private Double price;
    private RoomStatus reservationStatus;
    private String cause;

    public enum RoomStatus {
        RESERVED,
        CONFIRMED,
        FREE
    }
}