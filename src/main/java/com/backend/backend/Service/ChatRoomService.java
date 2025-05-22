package com.backend.backend.Service;

import com.backend.backend.models.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    ChatRoom createChatRoom(String productId, String buyerId, String sellerId);
    ChatRoom getChatRoom(String productId, String buyerId);
    void deleteChatRoom(String productId, String buyerId);
    List<ChatRoom> getUserChatRooms(String userId);
}
