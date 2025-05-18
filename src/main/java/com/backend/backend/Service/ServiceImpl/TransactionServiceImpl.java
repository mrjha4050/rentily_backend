package com.backend.backend.Service.ServiceImpl;

import com.backend.backend.Service.TransactionService;
import com.backend.backend.dao.TransactionRepository;
import com.backend.backend.dto.TransactionDTO;
import com.backend.backend.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setBuyerId(transactionDTO.getBuyerId());
        transaction.setSellerId(transactionDTO.getSellerId());
        transaction.setType(transactionDTO.getType());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setProductId(transactionDTO.getProductId());
        transaction.setTimestamp(transactionDTO.getTimestamp());
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByBuyerId(String buyerId) {
        return transactionRepository.findByBuyerId(buyerId);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(String sellerId) {
        return transactionRepository.findBySellerId(sellerId);
    }

    @Override
    public Transaction updateTransactionStatus(String transactionId, String status) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

}
