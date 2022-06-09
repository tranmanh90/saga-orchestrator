package com.saga.uni.entity;

import com.saga.uni.vo.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false,
            columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "cancel_cause", length = 100)
    private String cause;

    @Column(name = "order_status", length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
