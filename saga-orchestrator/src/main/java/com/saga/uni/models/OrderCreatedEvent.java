package com.saga.uni.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private UUID id;
    private OrderStatus status;

    public enum OrderStatus {
        PENDING, APPROVED, DENIED
    }

}