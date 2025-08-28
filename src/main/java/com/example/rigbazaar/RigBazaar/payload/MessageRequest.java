package com.example.rigbazaar.RigBazaar.payload;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private String content;
    private String contentType;
    @Nullable
    private MultipartFile file;
    private String sender;
    private String roomId;
    private String isRead;
}