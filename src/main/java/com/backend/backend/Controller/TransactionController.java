package com.backend.backend.Controller;

import com.backend.backend.Service.ServiceImpl.TransactionServiceImpl;
import com.backend.backend.Service.TransactionService;
import com.backend.backend.dto.TransactionDTO;
import com.backend.backend.models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionServiceImpl transactionServiceImpl;

    public TransactionController(TransactionService transactionService, TransactionServiceImpl transactionServiceImpl) {
        this.transactionService = transactionService;
        this.transactionServiceImpl = transactionServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(
        @RequestBody TransactionDTO transactionDTO
    ) {
        return ResponseEntity.ok(transactionServiceImpl.createTransaction(transactionDTO));
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<Iterable<Transaction>> getTransactionsByBuyerId(@PathVariable String buyerId) {
        return ResponseEntity.ok(transactionServiceImpl.getTransactionsByBuyerId(buyerId));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Iterable<Transaction>> getTransactionsBySellerId(@PathVariable String sellerId) {
        return ResponseEntity.ok(transactionServiceImpl.getTransactionsBySellerId(sellerId));
    }

    @PatchMapping("/{transactionId}/status")
    public ResponseEntity<Transaction> updateStatus(
            @PathVariable String transactionId,
            @RequestParam String status // "COMPLETE" OR "CANCELLED"
    ) {
        return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId, status));
    }

}
