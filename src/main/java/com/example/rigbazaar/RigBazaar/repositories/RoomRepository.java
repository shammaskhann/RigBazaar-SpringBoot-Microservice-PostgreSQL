package com.example.rigbazaar.RigBazaar.repositories;


import com.example.rigbazaar.RigBazaar.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {
    Room findByRoomId(String roomId);
}
