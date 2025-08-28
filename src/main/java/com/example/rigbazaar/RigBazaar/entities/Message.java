package com.example.rigbazaar.RigBazaar.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message{
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
