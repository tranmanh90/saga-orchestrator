package com.saga.uni.vo;

import com.saga.uni.entity.Room;
import lombok.Data;

import java.util.UUID;

@Data
public class ReservationResult {
    private UUID order;
    private String room;
    private Double price;
    private RoomStatus reservationStatus;
    private String cause;

    public ReservationResult() {
    }

    public ReservationResult(Room room) {
        this.order = room.getOrder();
        this.room = room.getRoomNo();
        this.price = room.getPrice();
        this.reservationStatus = room.getStatus();
    }

    public ReservationResult(UUID order, String room, Double price, RoomStatus reservationStatus, String cause) {
        this.order = order;
        this.room = room;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.cause = cause;
    }
}