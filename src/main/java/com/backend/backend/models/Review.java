package com.backend.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String reviewerId;
    private String sellerId;
    private String productId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}
