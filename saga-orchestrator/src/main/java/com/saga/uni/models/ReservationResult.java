package com.saga.uni.models;

public class ReservationResult {
    private String order;
    private String room;
    private Double price;
    private RoomStatus reservationStatus;
    private String cause;
    
    public static enum RoomStatus{
        RESERVED,
        CONFIRMED,
        FREE
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public RoomStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(RoomStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "ReservationResult [exceptionCause=" + cause + ", order=" + order + ", price=" + price
                + ", reservationStatus=" + reservationStatus + ", room=" + room + "]";
    }
}