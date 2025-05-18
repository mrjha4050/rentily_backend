package com.backend.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {
    private String title;
    private String description;
    private double price;
    private String category;
    private String type;
    private String status;
    private String userId;
    private String imageUrl;
    private LocalDateTime timestamp;

}
