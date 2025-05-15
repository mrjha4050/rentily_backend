package com.backend.backend.Service;


import com.backend.backend.models.Cart;

public interface CartService {
    Cart addToCart(String userId, String productId);
    Cart removeFromCart(String userId, String productId);
    Cart getCart(String userId);
    void clearCart(String userId);
}
