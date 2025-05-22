package com.backend.backend.Controller;

import com.backend.backend.Service.ReviewService;
import com.backend.backend.dto.ReviewDTO;
import com.backend.backend.models.Review;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{reviewerId}")
    public ResponseEntity<Review> submitReview(@PathVariable String reviewerId,
                                               @Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.submitReview(reviewerId, reviewDTO));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Review>> getReviewsBySellerId(@PathVariable String sellerId) {
        return ResponseEntity.ok(reviewService.getReviewsBySellerId(sellerId));
    }

    @GetMapping("/average/{sellerId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable String sellerId) {
        return ResponseEntity.ok(reviewService.getAverageRating(sellerId));
    }

}
