package com.backend.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTO {
    private String id;
    private String title;
    private String description;
    private double price;
    private String category;
    private String type;
    private String status;
    private String userId;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
}
