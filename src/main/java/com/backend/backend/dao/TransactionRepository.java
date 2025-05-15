package com.backend.backend.dao;

import com.backend.backend.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByBuyerId(String buyerId);
    List<Transaction> findBySellerId(String sellerId);
}
