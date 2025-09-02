package com.example.rigbazaar.RigBazaar.entities.Chats;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private String sender;
    private String content;
    private String contentType;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Message(String sender, String content,String contentType) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    public void setTimeStamp(LocalDateTime now) {
        this.timestamp = now;
    }
}
