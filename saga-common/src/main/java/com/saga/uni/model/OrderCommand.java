package com.saga.uni.model;

import com.saga.uni.vo.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderCommand {
    private UUID id;
    private OrderStatus status;
    private String cause;

    public OrderCommand(UUID id, OrderStatus status, String cause) {
        this.id = id;
        this.status = status;
        if (status == OrderStatus.CANCEL && cause == null) {
            throw new IllegalArgumentException("Cause must be informed for status = " + OrderStatus.CANCEL);
        }
        this.cause = cause;
    }
}