package com.saga.uni.repository;

import com.saga.uni.entity.Room;
import com.saga.uni.vo.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    List<Room> findRoomByStatus(RoomStatus status);

    Optional<Room> findRoomByRoomNo(String roomNo);

    List<Room> findRoomByStatusAndOrder(RoomStatus status, String order);
}
