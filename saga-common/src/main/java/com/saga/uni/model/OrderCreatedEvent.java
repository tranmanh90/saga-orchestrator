package com.saga.uni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private UUID id;
    private OrderStatus status;
    private String cause;

    public enum OrderStatus {
        PENDING, APPROVED, DENIED
    }

}