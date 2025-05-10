package com.backend.backend.models;


import jakarta.validation.MessageInterpolator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {
    @Id private String id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price required")
    @PositiveOrZero(message = "price must be positive")
    private double price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Type is required")
    private String type; // e.g., "SELL", "RENT", "BUY"

    @NotBlank(message = "User ID is required")
    private String userId; // Reference to the user who posted it

    private String status; // e.g., "AVAILABLE", "SOLD", "RENTED"

    private String imageUrl;

}
