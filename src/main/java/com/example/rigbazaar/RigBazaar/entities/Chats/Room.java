package com.example.rigbazaar.RigBazaar.entities.Chats;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // PK in Postgres

    @Column(name = "room_id", nullable = false, unique = true)
    private String roomId;

    @Column(name = "name", nullable = false)
    private String name;

    // One Room → Many Messages
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    // helper method to add message
    public void addMessage(Message message) {
        messages.add(message);
        message.setRoom(this);
    }

    // helper method to remove message
    public void removeMessage(Message message) {
        messages.remove(message);
        message.setRoom(null);
    }

    public Map<String, Object> toMap() {
        return Map.of("roomId", roomId, "messages", messages);
    }
}
