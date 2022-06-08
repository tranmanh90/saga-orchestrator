package com.saga.uni.entity;

import com.saga.uni.vo.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "room")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false,
            columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "room_no", length = 15)
    private String roomNo;

    @Column(name = "room_order", length = 50)
    private String order;

    @Column(name = "price", length = 30)
    private String price;

    @Column(name = "status", length = 20)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    public Room(String roomNo, String order, String price, RoomStatus status) {
        this.roomNo = roomNo;
        this.order = order;
        this.price = price;
        this.status = status;
    }
}
