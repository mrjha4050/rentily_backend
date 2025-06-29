package com.backend.backend.Service.ServiceImpl;

import com.backend.backend.Service.ProductService;
import com.backend.backend.dao.ProductRepository;
import com.backend.backend.dto.ProductDTO;
import com.backend.backend.models.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;

        public ProductServiceImpl(ProductRepository productRepository) {this.productRepository = productRepository;}

        public Product createProduct(ProductDTO productDTO) {
            Product product = new Product();
            product.setTitle(productDTO.getTitle());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setUserId(productDTO.getUserId());
            product.setImageUrls(productDTO.getImageUrls());
            product.setCategory(productDTO.getCategory());
            product.setType(productDTO.getType());
            product.setStatus("Available");
            product.setCreatedAt(productDTO.getCreatedAt());
            return productRepository.save(product);
        }

    public Product updateProduct(String id , @Valid ProductDTO product) {
            Product existing = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not found"));
            existing.setTitle(product.getTitle());
            existing.setCategory(product.getCategory());
            existing.setDescription(product.getDescription());
            existing.setPrice(product.getPrice());
            existing.setType(product.getType());
            existing.setStatus(product.getStatus());
            existing.setImageUrls(product.getImageUrls());
            existing.setCreatedAt(product.getCreatedAt());
            return productRepository.save(existing);
        }

        public void deleteProduct(String id) {
            productRepository.deleteById(id);
        }

        public Page<Product> getProductsByUser(String userId, Pageable pageable) {
            return productRepository.findByUserId(userId, pageable);
        }

        public Page<Product> getProductById(String id, Pageable pageable) {
            return productRepository.findById(id, pageable);
        }

        public Page<Product> getProductsByCategory(String category, Pageable pageable) {
            return productRepository.findByCategory(category, pageable);
        }

        public Page<Product> getProductsByType(String type, Pageable pageable) {
            return productRepository.findByType(type, pageable);
        }

        public Page<Product> getProductsByCategoryAndType(String category, String type, Pageable pageable) {
            return productRepository.findByCategoryAndType(category, type, pageable);
        }

        public Page<Product> searchProducts(String keyword, String category, String type, double minPrice, double maxPrice, Pageable pageable) {
            return productRepository.searchProducts(keyword, category, type, minPrice, maxPrice, pageable);
        }

        public List<Product> getAllByOrderByCreatedAtDesc(int limit) {
            Pageable pageable = PageRequest.of(0, limit, Sort.by("createdAt").descending());
            return productRepository.findAllByOrderByCreatedAtDesc(pageable).getContent();
        }

}
