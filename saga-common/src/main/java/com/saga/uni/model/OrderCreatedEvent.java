package com.saga.uni.model;

import com.saga.uni.vo.OrderStatus;
import lombok.*;

import java.util.UUID;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {
    private UUID id;
    private OrderStatus status;
    private String cause;
}