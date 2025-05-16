package com.backend.backend.dao;

import com.backend.backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product , String> {

    Page<Product> findByUserId(String userId, Pageable pageable);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByType(String type, Pageable pageable);
    Page<Product> findByCategoryAndType(String category, String type, Pageable pageable);

    @Query("{ '$and': [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +               // keyword
            "{ 'category': { $regex: ?1, $options: 'i' } }, " +           // category
            "{ 'type': { $regex: ?2, $options: 'i' } }, " +               // type
            "{ 'price': { $gte: ?3, $lte: ?4 } } " +                      // price range
            "] }")
    Page<Product> searchProducts(
            String keyword,
            String category,
            String type,
            double minPrice,
            double maxPrice,
            Pageable pageable
    );

}
