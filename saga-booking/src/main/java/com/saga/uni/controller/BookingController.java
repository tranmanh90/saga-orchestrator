package com.saga.uni.controller;

import com.saga.uni.entity.Room;
import com.saga.uni.repository.RoomRepository;
import com.saga.uni.service.IReservationService;
import com.saga.uni.vo.ReservationCommand;
import com.saga.uni.vo.ReservationResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.saga.uni.vo.ReservationCommand.ReservationRequest.CANCEL;
import static com.saga.uni.vo.ReservationCommand.ReservationRequest.RESERVE;

@RestController
@AllArgsConstructor
public class BookingController implements IBookingController {

    private final RoomRepository roomRepository;
    private final IReservationService iReservationService;

    @Override
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @Override
    public ResponseEntity<ReservationResult> reserveRoom(ReservationCommand reservationCommand) {
        if (!reservationCommand.getReservationRequest().equals(RESERVE)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "This URL can only accept reservationReques=" + RESERVE);
        }
        ReservationResult reservationResult = iReservationService.processReservationCommand(reservationCommand);
        if (reservationResult.getReservationStatus() == null) {
            return new ResponseEntity<>(reservationResult, HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(reservationResult, HttpStatus.ACCEPTED);
        }
    }

    @Override
    public ResponseEntity<ReservationResult> reserveRoom(String roomNo, ReservationCommand reservationCommand) {
        if (reservationCommand.getReservationRequest().equals(RESERVE)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "This URL can NOT accept reservationReques=" + ReservationCommand.ReservationRequest.RESERVE);
        }
        ReservationResult reservationResult = null;
        if (reservationCommand.getReservationRequest().equals(CANCEL)) {
            reservationResult = iReservationService.cancelRoom(roomNo, reservationCommand);
        }
        if (reservationResult.getReservationStatus() == null) {
            return new ResponseEntity<>(reservationResult, HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(reservationResult, HttpStatus.ACCEPTED);
        }
    }
}
