package com.backend.backend.Controller;

import com.backend.backend.Service.ServiceImpl.ChatServiceImpl;
import com.backend.backend.dto.ChatMessageDTO;
import com.backend.backend.models.ChatMessage;
import com.backend.backend.models.ChatRoom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatServiceImpl chatServiceImpl;
    public ChatController( ChatServiceImpl chatServiceImpl) {
        this.chatServiceImpl = chatServiceImpl;
    }

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createOrGetChatRoom(
            @RequestParam String productId,
            @RequestParam String buyerId,
            @RequestParam String sellerId) {
        return ResponseEntity.ok(chatServiceImpl.createChatRoom(productId, buyerId, sellerId));
    }

    @GetMapping("/room")
    public ResponseEntity<ChatRoom> getChatRoom(
            @RequestParam String productId,
            @RequestParam String buyerId) {
        return ResponseEntity.ok(chatServiceImpl.getChatRoom(productId, buyerId));
    }

    @DeleteMapping("/room")
    public ResponseEntity<Void> deleteChatRoom(
            @RequestParam String productId,
            @RequestParam String buyerId) {
        chatServiceImpl.deleteChatRoom(productId, buyerId);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get all chat rooms for a user
    @GetMapping("/rooms/{userId}")
    public ResponseEntity<List<ChatRoom>> getUserChatRooms(@PathVariable String userId) {
        return ResponseEntity.ok(chatServiceImpl.getUserChatRooms(userId));
    }

    // ✅ Send a message
    @PostMapping("/message")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageDTO messageDTO) {
        return ResponseEntity.ok(chatServiceImpl.sendMessage(messageDTO));
    }

    // ✅ Get all messages in a chat room
    @GetMapping("/messages/{chatRoomId}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable String chatRoomId) {
        return ResponseEntity.ok(chatServiceImpl.getMessages(chatRoomId));
    }
}