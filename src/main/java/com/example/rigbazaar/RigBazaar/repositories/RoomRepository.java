package com.example.rigbazaar.RigBazaar.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rigbazaar.RigBazaar.entities.Chats.Room;

public interface RoomRepository extends JpaRepository<Room, String> {
    Room findByRoomId(String roomId);
}
