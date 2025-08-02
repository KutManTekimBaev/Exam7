package org.example.exam7.controllers;

import org.example.exam7.model.Transaction;
import org.example.exam7.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions/{accountId}/history")
    public List<Transaction> getHistory(@PathVariable String accountId) {
        return transactionService.getHistory(accountId);
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
        transactionService.create(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body("Транзакция создана и ожидает одобрения.");
    }

    @GetMapping("/admin/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAll();
    }

    @GetMapping("/admin/transactions/approval")
    public List<Transaction> getPendingTransactions() {
        return transactionService.getPendingApproval();
    }

    @PostMapping("/admin/transactions/approval")
    public ResponseEntity<String> approve(@RequestBody Map<String, Long> body) {
        Long id = body.get("id");
        boolean result = transactionService.approve(id);
        return result ? ResponseEntity.ok("Транзакция одобрена.") : ResponseEntity.badRequest().body("Не удалось одобрить.");
    }

    @PostMapping("/admin/transactions/rollback")
    public ResponseEntity<String> rollback(@RequestBody Map<String, Long> body) {
        Long id = body.get("id");
        boolean result = transactionService.rollback(id);
        return result ? ResponseEntity.ok("Откат успешен.") : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Недостаточно средств для отката.");
    }

    @DeleteMapping("/admin/transactions/{id}")
    public ResponseEntity<String> deleteAfterRollback(@PathVariable Long id) {
        boolean result = transactionService.deleteAfterRollback(id);
        return result ? ResponseEntity.ok("Статус изменён на DELETED.") : ResponseEntity.badRequest().body("Ошибка удаления.");
    }
}
