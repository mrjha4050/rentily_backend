package com.backend.backend.Service.ServiceImpl;


import com.backend.backend.Service.CartService;
import com.backend.backend.dao.CartRepository;
import com.backend.backend.dao.ProductRepository;
import com.backend.backend.dto.CartDTO;
import com.backend.backend.dto.ProductDTO;
import com.backend.backend.models.Cart;
import com.backend.backend.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(userId, new ArrayList<>()));
        cart.getProductId().add(productId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId).orElse(new Cart(userId, new ArrayList<>()));
    }

    @Override
    public Cart removeFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getProductId().remove(productId);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getProductId().clear();
        cartRepository.save(cart);
    }

    private CartDTO mapToDTO(Cart cart) {
        List<Product> products = productRepository.findAllById(cart.getProductId());

        List<ProductDTO> productDto = products.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setTitle(product.getTitle());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setCategory(product.getCategory());
            dto.setType(product.getType());
            dto.setImageUrl(product.getImageUrl());
            dto.setUserId(product.getUserId());
            dto.setStatus(product.getStatus());
            return dto;
        }).collect(Collectors.toList());
        return new CartDTO(cart.getUserId(),productDto);
    }

}
