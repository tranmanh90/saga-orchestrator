package com.saga.uni.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderCommand {
    private UUID id;
    private OrderRequest status;
    private String cause;

    public OrderCommand(UUID id, OrderRequest status, String cause) {
        this.id = id;
        this.status = status;
        if (status == OrderRequest.CANCEL && cause == null) {
            throw new IllegalArgumentException("Cause must be informed for status = " + OrderRequest.CANCEL);
        }
        this.cause = cause;
    }

    public enum OrderRequest {
        CONFIRM, CANCEL
    }
}