package com.backend.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private String userId;
    private List<ProductDTO> products;

    public CartDTO() {}

    public CartDTO(String userId, List<ProductDTO> products) {
        this.userId = userId;
        this.products = products;
    }

}