package com.example.rigbazaar.RigBazaar.services;


import com.example.rigbazaar.RigBazaar.entities.Chats.LastMessageInfo;
import com.example.rigbazaar.RigBazaar.entities.Chats.Message;
import com.example.rigbazaar.RigBazaar.entities.Chats.Room;
import com.example.rigbazaar.RigBazaar.payload.MessageRequest;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@CacheConfig(cacheNames = "messages")
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;

    public MessageService(SimpMessagingTemplate messagingTemplate,
                          RoomService roomService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
    }

    public void processMessage(MessageRequest request, String roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found for message processing");
        }

        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());
        message.setRead(false);

        // Save message to DB
        room.getMessages().add(message);
        roomService.saveRoom(room);

        // Send real-time updates
        sendRealTimeUpdates(roomId, message);
    }

    private void sendRealTimeUpdates(String roomId, Message message) {
        // Send full message to chat room subscribers
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);

        // Send last message info to home screen subscribers
        LastMessageInfo lastMessage = new LastMessageInfo();
        lastMessage.setMessage(message.getContent());
        lastMessage.setSenderId(message.getSender());
        lastMessage.setTimestamp(message.getTimestamp());
        lastMessage.setUnread(true);

        messagingTemplate.convertAndSend("/topic/lastMessage/" + roomId, lastMessage);
    }
}