package com.backend.backend.Service;

import com.backend.backend.dto.ProductDTO;
import com.backend.backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createProduct(ProductDTO dto);
    Product updateProduct(String id , ProductDTO dto);
    void deleteProduct(String id);

    Page<Product> getProductsByUser(String userId, Pageable pageable);
    Page<Product> getProductsByCategory(String category, Pageable pageable);
    Page<Product> getProductsByType(String type, Pageable pageable);
    Page<Product> getProductsByCategoryAndType(String category, String type, Pageable pageable);
}
