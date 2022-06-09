package com.saga.uni.service;

import com.saga.uni.model.ReservationCommand;
import com.saga.uni.model.ReservationResult;

public interface IReservationService {
    ReservationResult processReservationCommand(ReservationCommand reservationCommand);

    ReservationResult confirmRoom(ReservationCommand reservationCommand);

    ReservationResult cancelRoom(ReservationCommand reservationCommand);

    ReservationResult reserveRoom(ReservationCommand reservationCommand);

    ReservationResult cancelRoom(String roomNo, ReservationCommand reservationCommand);
}
