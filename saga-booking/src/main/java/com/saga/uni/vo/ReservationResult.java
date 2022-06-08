package com.saga.uni.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.saga.uni.entity.Room;

@JsonInclude(Include.NON_NULL)
public class ReservationResult {

    private String order;
    private String room;
    private String price;
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

    public ReservationResult(String order, String room, String price, RoomStatus reservationStatus, String cause) {
        this.order = order;
        this.room = room;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.cause = cause;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public RoomStatus getReservationStatus() {
        return this.reservationStatus;
    }

    public void setReservationStatus(RoomStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getCause() {
        return this.cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "{" + " order='" + getOrder() + "'" + ", room='" + getRoom() + "'" + ", price='" + getPrice() + "'"
                + ", reservationStatus='" + getReservationStatus() + "'" + ", exceptionCause='" + getCause()
                + "'" + "}";
    }

}