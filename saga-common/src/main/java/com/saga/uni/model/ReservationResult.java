package com.saga.uni.model;

import com.saga.uni.vo.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResult {
    private UUID order;
    private String room;
    private Double price;
    private RoomStatus reservationStatus;
    private String cause;
}