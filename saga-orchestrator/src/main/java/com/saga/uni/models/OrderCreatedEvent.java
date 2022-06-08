package com.saga.uni.models;

import java.util.UUID;

public class OrderCreatedEvent {
    private UUID id;
    private OrderStatus status;

    public OrderCreatedEvent() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" + " order='" + getId() + "'" + ", status='" + getStatus() + "'" + "}";
    }

    public static enum OrderStatus {
        PENDING, APPROVED, DENIED
    }

}