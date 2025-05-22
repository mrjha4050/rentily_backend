package com.backend.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "chatmessages")
public class ChatMessage {
    @Id
    private String id;
    private String chatRoomId;
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime timestamp;
}
