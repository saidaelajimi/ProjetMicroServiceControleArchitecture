package org.example.transactionservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.model.Transaction;
import org.example.transactionservice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfert")
    public ResponseEntity<Transaction> effectuerTransfert(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(
                transactionService.effectuerTransfert(transaction),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/compte/{numeroCompte}")
    public ResponseEntity<List<Transaction>> getTransactionsByCompte(
            @PathVariable String numeroCompte) {
        return ResponseEntity.ok(
                transactionService.getTransactionsByCompte(numeroCompte)
        );
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
}