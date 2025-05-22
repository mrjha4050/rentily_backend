    package com.backend.backend.Service.ServiceImpl;

    import com.backend.backend.Service.ChatMessageService;
    import com.backend.backend.Service.ChatRoomService;
    import com.backend.backend.dao.ChatRoomRepository;
    import com.backend.backend.dao.MessageRepository;
    import com.backend.backend.dto.ChatMessageDTO;
    import com.backend.backend.models.ChatMessage;
    import com.backend.backend.models.ChatRoom;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class ChatServiceImpl implements ChatRoomService, ChatMessageService {

        @Autowired
        private ChatRoomRepository chatRoomRepository;

        @Autowired
        private MessageRepository messageRepository;

        @Override
        public ChatRoom createChatRoom(String productId, String buyerId, String sellerId) {
            return chatRoomRepository.findByProductIdAndBuyerId(productId, buyerId)
                    .orElseGet(() -> {
                        ChatRoom room = new ChatRoom();
                        room.setProductId(productId);
                        room.setBuyerId(buyerId);
                        room.setSellerId(sellerId);
                        return chatRoomRepository.save(room);
                    });
        }

        @Override
        public ChatRoom getChatRoom(String productId, String buyerId) {
            return chatRoomRepository.findByProductIdAndBuyerId(productId, buyerId)
                    .orElse(null);
        }

        @Override
        public void deleteChatRoom(String productId, String buyerId) {
            chatRoomRepository.findByProductIdAndBuyerId(productId, buyerId)
                    .ifPresent(chatRoomRepository::delete);
        }

        @Override
        public ChatMessage sendMessage(ChatMessageDTO chatMessageDTO) {
            ChatRoom chatRoom = chatRoomRepository.findById(chatMessageDTO.getChatRoomId())
                    .orElseThrow(() -> new RuntimeException("Chat room not found"));

            ChatMessage message = new ChatMessage();
            message.setChatRoomId(chatRoom.getId());
            message.setSenderId(chatMessageDTO.getSenderId());
            message.setReceiverId(chatMessageDTO.getReceiverId());
            message.setTimestamp(chatMessageDTO.getTimestamp());
            message.setContent(chatMessageDTO.getContent());

            return messageRepository.save(message);
        }

        @Override
        public List<ChatMessage> getMessages(String chatRoomId) {
            return messageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
        }

        public List<ChatRoom> getUserChatRooms(String userId) {
            return chatRoomRepository.findByBuyerIdOrSellerId(userId, userId);
        }
    }