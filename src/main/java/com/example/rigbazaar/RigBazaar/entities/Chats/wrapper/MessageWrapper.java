package com.example.rigbazaar.RigBazaar.entities.Chats.wrapper;

import com.example.rigbazaar.RigBazaar.payload.MessageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageWrapper implements Serializable {
    private MessageRequest messageRequest;
    private String roomId;
}