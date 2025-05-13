package com.backend.backend.Service.ServiceImpl;

import com.backend.backend.dao.ProductRepository;
import com.backend.backend.models.Product;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService (
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public String uploadImage(MultipartFile file) throws IOException{
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw  new IOException("failed to upload image to cloudinary: " + e.getMessage(),e);
        }
    }

    @Service
    public static class ProductService {
        @Autowired
        private ProductRepository productRepository;

        public Product createProduct(Product product) {
            product.setStatus("Available");
            return productRepository.save(product);
        }

        public Product updateProduct(String id , Product product) {
            Product existing = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not found"));
            existing.setTitle(product.getTitle());
            existing.setCategory(product.getCategory());
            existing.setDescription(product.getDescription());
            existing.setPrice(product.getPrice());
            existing.setType(product.getType());
            existing.setStatus(product.getStatus());
            existing.setImageUrl(product.getImageUrl());
            return productRepository.save(existing);
        }

        public void deleteProduct(String id) {
            productRepository.deleteById(id);
        }

        public Page<Product> getProductsByUser(String userId, Pageable pageable) {
            return productRepository.findByUserId(userId, pageable);
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

    }
}
