package com.saga.uni.service;

import com.saga.uni.entity.Room;
import com.saga.uni.repository.RoomRepository;
import com.saga.uni.vo.ReservationCommand;
import com.saga.uni.vo.ReservationResult;
import com.saga.uni.vo.RoomStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ReservationService implements IReservationService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final RoomRepository roomRepository;
    private final KafkaTemplate<String, ReservationResult> kafkaTemplate;

    public ReservationService(RoomRepository roomRepository, KafkaTemplate<String, ReservationResult> kafkaTemplate) {
        this.roomRepository = roomRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ReservationResult processReservationCommand(ReservationCommand reservationCommand) {
        ReservationResult result = null;
        switch (reservationCommand.getReservationRequest()) {
            case RESERVE:
                result = reserveRoom(reservationCommand);
                break;
            case CANCEL:
                result = cancelRoom(reservationCommand);
                break;

            case CONFIRM:
                result = confirmRoom(reservationCommand);
                break;
        }
        logger.info("Producing Reservation Response: " + result);
        kafkaTemplate.send("reservation_response", result);
        return result;
    }

    @Override
    public ReservationResult confirmRoom(ReservationCommand reservationCommand) {
        List<Room> rooms = roomRepository.findRoomByStatusAndOrder(RoomStatus.RESERVED, reservationCommand.getOrder());
        if (rooms.size() > 0) {
            Room room = rooms.get(0);
            room.setOrder(reservationCommand.getOrder());
            room.setStatus(RoomStatus.CONFIRMED);
            roomRepository.save(room);
            return new ReservationResult(room);
        } else {
            return new ReservationResult(reservationCommand.getOrder(), null, null, null,
                    "There are no rooms reserved for the Order " + reservationCommand.getOrder());
        }
    }

    @Override
    public ReservationResult cancelRoom(ReservationCommand reservationCommand) {
        List<Room> rooms = roomRepository.findRoomByStatusAndOrder(RoomStatus.RESERVED, reservationCommand.getOrder());
        if (rooms.size() > 0) {
            Room room = rooms.get(0);
            room.setOrder(null);
            room.setStatus(RoomStatus.FREE);
            roomRepository.save(room);
            return new ReservationResult(room);
        } else {
            return new ReservationResult(reservationCommand.getOrder(), null, null, null,
                    "There's no reserved room for the order " + reservationCommand.getOrder());
        }
    }

    @Override
    public ReservationResult reserveRoom(ReservationCommand reservationCommand) {
        List<Room> rooms = roomRepository.findRoomByStatus(RoomStatus.FREE);
        if (rooms.size() > 0) {
            Room room = rooms.get(0);
            room.setOrder(reservationCommand.getOrder());
            room.setStatus(RoomStatus.RESERVED);
            roomRepository.save(room);
            return new ReservationResult(room);
        } else {
            return new ReservationResult(reservationCommand.getOrder(), null, null, null,
                    "There are no rooms available");
        }
    }

    @Override
    public ReservationResult cancelRoom(String roomNo, ReservationCommand reservationCommand) {
        Optional<Room> roomResult = roomRepository.findRoomByRoomNo(roomNo);
        if (roomResult.isPresent() && !roomResult.get().getStatus().equals(RoomStatus.FREE)) {
            Room room = roomResult.get();
            room.setOrder(null);
            room.setStatus(RoomStatus.FREE);
            roomRepository.save(room);
            return new ReservationResult(room);
        } else {
            return new ReservationResult(null, roomNo, null, null, "There's no such room to be canceled");
        }
    }
}
