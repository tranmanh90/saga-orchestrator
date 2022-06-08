package com.saga.uni.models;

import java.util.UUID;

public class OrderCommand {
    private UUID id;
    private OrderRequest status;
    private String cause;

    public OrderCommand() {

    }

    public OrderCommand(UUID id, OrderRequest status, String cause) {
        this.id = id;
        this.status = status;
        if (status == OrderRequest.CANCEL && cause == null) {
            throw new IllegalArgumentException("Cause must be informed for status = " + OrderRequest.CANCEL);
        }
        this.cause = cause;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderRequest getStatus() {
        return status;
    }

    public void setStatus(OrderRequest status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public enum OrderRequest {
        CONFIRM, CANCEL
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", status='" + getStatus() + "'" +
                ", cause='" + getCause() + "'" +
                "}";
    }

}