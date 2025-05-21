package com.backend.backend.Service.ServiceImpl;

import com.backend.backend.Service.ReviewService;
import com.backend.backend.dao.ReviewRepository;
import com.backend.backend.dto.ReviewDTO;
import com.backend.backend.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review submitReview(String reviewerId, ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewerId(reviewerId);
        review.setSellerId(reviewDTO.getSellerId());
        review.setProductId(reviewDTO.getProductId());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsBySellerId(String sellerId) {
        return reviewRepository.findBysellerId(sellerId);
    }

    @Override
    public double getAverageRating(String sellerId) {
        List<Review> reviews = reviewRepository.findBysellerId(sellerId);
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

}
