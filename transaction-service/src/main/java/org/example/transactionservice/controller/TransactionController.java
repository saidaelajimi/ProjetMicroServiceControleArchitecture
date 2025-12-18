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

public class TransactionController {

    private final TransactionService transactionService;

    /**
     * POST /api/transactions/transfert
     * Effectue un transfert entre deux comptes
     */
    @PostMapping("/transfert")
    public ResponseEntity<Transaction> effectuerTransfert(@RequestBody Transaction transaction) {
        Transaction result = transactionService.effectuerTransfert(transaction);

        HttpStatus status = result.getStatut() == Transaction.StatutTransaction.SUCCESS
                ? HttpStatus.CREATED
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(result, status);
    }

    /**
     * GET /api/transactions/compte/{numeroCompte}
     * Récupère toutes les transactions d'un compte
     */
    @GetMapping("/compte/{numeroCompte}")
    public ResponseEntity<List<Transaction>> getTransactionsByCompte(
            @PathVariable String numeroCompte) {
        return ResponseEntity.ok(
                transactionService.getTransactionsByCompte(numeroCompte)
        );
    }

    /**
     * GET /api/transactions
     * Récupère toutes les transactions
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    /**
     * GET /api/transactions/{id}
     * Récupère une transaction par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    /**
     * POST /api/transactions/{id}/annuler
     * Annule une transaction
     */
    @PostMapping("/{id}/annuler")
    public ResponseEntity<Transaction> annulerTransaction(@PathVariable Long id) {
        try {
            Transaction result = transactionService.annulerTransaction(id);
            return ResponseEntity.ok(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
