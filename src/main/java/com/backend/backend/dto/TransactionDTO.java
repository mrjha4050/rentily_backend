package com.backend.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private String id;
    private String productId;
    private String sellerId;
    private String buyerId;
    private String type;
    private String status;
    private LocalDateTime timestamp;
}
