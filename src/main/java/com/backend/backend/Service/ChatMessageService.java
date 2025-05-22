package com.backend.backend.Service;

import com.backend.backend.dto.ChatMessageDTO;
import com.backend.backend.models.ChatMessage;
import java.util.List;

public interface ChatMessageService {
    ChatMessage sendMessage(ChatMessageDTO chatMessageDTO);
    List<ChatMessage> getMessages(String chatRoomId);
}
