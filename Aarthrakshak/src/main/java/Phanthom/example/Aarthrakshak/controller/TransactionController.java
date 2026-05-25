package Phanthom.example.Aarthrakshak.controller;

import Phanthom.example.Aarthrakshak.model.Transaction;
import Phanthom.example.Aarthrakshak.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // POST /api/transaction/add
    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(
            @RequestBody Map<String, Object> body) {

        Long userId = Long.valueOf(body.get("userId").toString());
        String merchant = body.get("merchant").toString();
        Double amount = Double.valueOf(body.get("amount").toString());
        String category = body.get("category").toString();
        String ip = body.get("ipAddress").toString();
        Boolean isNewDevice = Boolean.valueOf(body.get("isNewDevice").toString());

        Transaction txn = transactionService.addTransaction(
                userId, merchant, amount, category, ip, isNewDevice);

        return ResponseEntity.ok(txn);
    }

    // GET /api/transaction/feed/{userId}
    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<Transaction>> getFeed(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
                transactionService.getUserTransactions(userId)

        );
    }
}