package com.backend.backend.models;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "products")
public class Product {
    @Id private String id;
    private String title;
    private String description;

    @PositiveOrZero(message = "price should be positive ")
    private double price;

    private String category;
    private String type; // e.g., "SELL", "RENT", "BUY"
    private String userId; // Reference to the user who posted it
    private String status; // e.g., "AVAILABLE", "SOLD", "RENTED"
    private List<String> imageUrls;
    private LocalDateTime timestamp;

}
