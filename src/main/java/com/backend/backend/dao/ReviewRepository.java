package com.backend.backend.dao;

import com.backend.backend.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findBysellerId(String sellerId);
    List<Review> findByProductId(String productId);
}
