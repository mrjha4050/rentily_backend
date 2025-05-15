package com.backend.backend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<String> productId;

    private Cart() { }

    public Cart(String userId, List<String> productId) {
        this.userId = userId;
        this.productId = productId;
    }

}
