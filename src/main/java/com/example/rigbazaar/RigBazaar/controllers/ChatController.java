package com.example.rigbazaar.RigBazaar.controllers;



import com.example.rigbazaar.RigBazaar.entities.LastMessageInfo;
import com.example.rigbazaar.RigBazaar.entities.Message;
import com.example.rigbazaar.RigBazaar.entities.Room;
import com.example.rigbazaar.RigBazaar.payload.MessageRequest;
import com.example.rigbazaar.RigBazaar.repositories.RoomRepository;
import com.example.rigbazaar.RigBazaar.services.MessageProducerService;
import com.example.rigbazaar.RigBazaar.services.MessageService;
import com.example.rigbazaar.RigBazaar.services.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@CrossOrigin(origins = "localhost:8080")
public class ChatController {

 private SimpMessagingTemplate messagingTemplate;

    @Autowired
    MessageProducerService messageProducerService;

    @Autowired
    RoomService roomService;

    @Autowired
    MessageService messageService;


    public ChatController(RoomRepository roomRepository, SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;

    }

    @MessageMapping("/sendMessage/{roomId}")
    public void sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request
    ) {
        log.info("Queueing message for room {}", roomId);
        ArrayList<String> errors = new ArrayList<String>();
        if (roomService.getRoomById(roomId) == null) {
            errors.add("Chat not found");
        }
        request.setRoomId(roomId);

        if(!Objects.equals(request.getContentType(), "text") && request.getFile() == null){
            errors.add("File is required for non-text messages");
        }
        if (request.getContent() == null && request.getContentType().equals("text")) {
            errors.add("Message content cannot be empty");
        }

        messageProducerService.sendMessage(request, roomId);
    }

    // Optional: Manually fetch last message if needed (used rarely now)
    @MessageMapping("/getLastMessage/{roomId}") // /app/getLastMessage/{roomId}
    @SendTo("/topic/lastMessage/{roomId}")
    public LastMessageInfo getLastMessageInfo(@DestinationVariable String roomId) {
        log.info("Getting last message info for room {}", roomId);
        Room room = roomService.getRoomById(roomId);
        if (room == null || room.getMessages().isEmpty()) {
            throw new RuntimeException("room not found or has no messages!");
        }

        Message last = room.getMessages().get(room.getMessages().size() - 1);
        log.info("Getting last message info for room {}", last.toString());
        LastMessageInfo info = new LastMessageInfo();
        info.setMessage(last.getContent());
        info.setTimestamp(last.getTimestamp());
        info.setSenderId(last.getSender());
        info.setUnread(last.isRead());
        return info;
    }


    //Mark as read (all msg when chat opens)
    @GetMapping("/markAsRead/{roomId}")
    public void markAsRead(@DestinationVariable String roomId) {
        log.info("Marking all messages as read for room {}", roomId);
        Room room = roomService.getRoomById(roomId);
        if (room == null) {
            throw new RuntimeException("Room not found");
        }

        List<Message> messages = room.getMessages();
        for (Message message : messages) {
            message.setRead(true);
            room.setMessages(messages);
            roomService.saveRoom(room);
        }

        // Optionally, you can send an update to the clients
        messagingTemplate.convertAndSend("/topic/room/" + roomId, "All messages marked as read");
    }
}
