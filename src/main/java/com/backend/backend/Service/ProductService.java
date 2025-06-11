package com.backend.backend.Service;

import com.backend.backend.dto.ProductDTO;
import com.backend.backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO dto);
    Product updateProduct(String id , ProductDTO dto);
    void deleteProduct(String id);

    Page<Product> getProductsByUser(String userId, Pageable pageable);
    Page<Product> getProductsByCategory(String category, Pageable pageable);
    Page<Product> getProductsByType(String type, Pageable pageable);
    Page<Product> searchProducts(String keyword, String category, String type, double minPrice, double maxPrice, Pageable pageable);
    Page<Product> getProductsByCategoryAndType(String category, String type, Pageable pageable);
    List<Product> getAllByOrderByCreatedAtDesc(int limit);
}
