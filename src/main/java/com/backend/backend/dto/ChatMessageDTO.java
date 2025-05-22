package com.backend.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private String chatRoomId;
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime timestamp;
}
