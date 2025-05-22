package com.backend.backend.dao;

import com.backend.backend.models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findByProductIdAndBuyerId(String productId, String buyerId);
    List<ChatRoom> findByBuyerIdOrSellerId(String userId, String userId2);
}
