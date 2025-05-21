package com.backend.backend.Service;

import com.backend.backend.dto.ReviewDTO;
import com.backend.backend.models.Review;

import java.util.List;

public interface ReviewService {
    Review submitReview(String reviewerId, ReviewDTO reviewDTO);
    List<Review> getReviewsBySellerId(String sellerId);
    double getAverageRating(String sellerId);
}
