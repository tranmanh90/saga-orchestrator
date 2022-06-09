package com.saga.uni.controller;

import com.saga.uni.entity.Room;
import com.saga.uni.model.ReservationCommand;
import com.saga.uni.model.ReservationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/booking")
public interface IBookingController {

    @GetMapping("/getRooms")
    ResponseEntity<List<Room>> getRooms();

    @PostMapping("/reserveRoom")
    ResponseEntity<ReservationResult> reserveRoom(@RequestBody ReservationCommand reservationCommand);

    @PostMapping("/reserveRoom/{roomNo}")
    ResponseEntity<ReservationResult> reserveRoom(@PathVariable String roomNo, @RequestBody ReservationCommand reservationCommand);
}
