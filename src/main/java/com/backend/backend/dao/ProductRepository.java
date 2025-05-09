package com.backend.backend.dao;

import com.backend.backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product , String> {
    Page<Product> findByUserId(String userId, Pageable pageable);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByType(String type, Pageable pageable);
    Page<Product> findByCategoryAndType(String category, String type, Pageable pageable);
}
