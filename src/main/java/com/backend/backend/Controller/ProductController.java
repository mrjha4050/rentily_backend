package com.backend.backend.Controller;


import com.backend.backend.Service.CloudinaryService;
import com.backend.backend.Service.ProductService;
import com.backend.backend.models.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    private final CloudinaryService cloudinaryService;

    @Autowired
    public ProductController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct (
            @Valid @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile image)  throws IOException {
        if (image != null) {
            String imageUrl = cloudinaryService.uploadImage(image);
            product.setImageUrl(imageUrl);
        }
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @Valid @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile image ) throws IOException {

        if (image != null) {
            String imageUrl = cloudinaryService.uploadImage(image);
            product.setImageUrl(imageUrl);
        }
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Product>> getProductsByUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductsByUser(userId, pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> getProductByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(productService.getProductsByCategory(category, pageable));
    }


    @GetMapping("/type/{type}")
    public ResponseEntity<Page<Product>> getProductByType(
            @PathVariable String type,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(productService.getProductsByType(type, pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Product>> getProductsByCategoryAndType(
            @RequestParam String category,
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductsByCategoryAndType(category, type, pageable));
    }
}
