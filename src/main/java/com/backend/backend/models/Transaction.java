package com.backend.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private String productId;
    private String sellerId;
    private String buyerId;
    private String type; // e.g., "SELL", "RENT", "BUY"
    private LocalDateTime timestamp;
    private String status; // e.g., "PENDING", "COMPLETED", "CANCELLED"
}
