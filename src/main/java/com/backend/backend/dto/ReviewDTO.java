package com.backend.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ReviewDTO {

    private String sellerId;
    private String productId;

    @Min(1)
    @Max(5)
    private int rating;

    private String comment;

}
