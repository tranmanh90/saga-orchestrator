package com.saga.uni.vo;

import com.saga.uni.entity.Room;
import lombok.Data;

@Data
public class ReservationResult {
    private String order;
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

    public ReservationResult(String order, String room, Double price, RoomStatus reservationStatus, String cause) {
        this.order = order;
        this.room = room;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.cause = cause;
    }
}