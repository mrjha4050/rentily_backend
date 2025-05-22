package com.backend.backend.Controller;

import com.backend.backend.Service.ProductService;
import com.backend.backend.Service.ServiceImpl.CloudinaryService;
import com.backend.backend.Service.ServiceImpl.ProductServiceImpl;
import com.backend.backend.dto.ProductDTO;
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

    private  final ProductServiceImpl productServiceImpl;
    private final CloudinaryService cloudinaryService;
    private final ProductService productService;

    @Autowired
    public ProductController(CloudinaryService cloudinaryService, ProductServiceImpl productServiceImpl, ProductService productService) {
        this.cloudinaryService = cloudinaryService;
        this.productServiceImpl = productServiceImpl;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProductWithoutImage(@RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productServiceImpl.createProduct(productDTO));
    }
//    public ResponseEntity<Product> createProduct (
//            @Valid @RequestPart("product") ProductDTO productDTO,
//            @RequestPart(value = "image", required = false) MultipartFile image)  throws IOException {
//        if (image != null) {
//            String imageUrl = cloudinaryService.uploadImage(image);
//            productDTO.setImageUrl(imageUrl);
//        }
//        return ResponseEntity.ok(productServiceImpl.createProduct(productDTO));
//    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @Valid @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "image", required = false) MultipartFile image ) throws IOException {

        if (image != null) {
            String imageUrl = cloudinaryService.uploadImage(image);
            productDTO.setImageUrl(imageUrl);
        }
        return ResponseEntity.ok(productServiceImpl.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productServiceImpl.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Product>> getProductsByUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productServiceImpl.getProductsByUser(userId, pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Product>> getProductByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(productServiceImpl.getProductsByCategory(category, pageable));
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<Page<Product>> getProductByType(
            @PathVariable String type,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(productServiceImpl.getProductsByType(type, pageable));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Product>> getProductsByCategoryAndType(
            @RequestParam String category,
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productServiceImpl.getProductsByCategoryAndType(category, type, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "1000000") double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> result = productService.searchProducts(keyword, category, type, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(result);
    }

}
