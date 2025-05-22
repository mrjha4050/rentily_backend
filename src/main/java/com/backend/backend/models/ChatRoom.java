package com.backend.backend.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "chatrooms")
public class ChatRoom {
    private String id;
    private String productId;
    private String sellerId;
    private String buyerId;
}
