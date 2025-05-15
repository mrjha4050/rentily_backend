package com.backend.backend.Service;

import com.backend.backend.dto.TransactionDTO;
import com.backend.backend.models.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO transactionDTO);
    List<Transaction> getTransactionsByBuyerId(String buyerId);
    List<Transaction> getTransactionsBySellerId(String sellerId);
    Transaction updateTransactionStatus(String transactionId, String status);
}
