package com.backend.backend.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

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
}
