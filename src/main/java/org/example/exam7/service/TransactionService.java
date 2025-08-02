package org.example.exam7.service;

import org.example.exam7.model.Transaction;
import org.example.exam7.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getHistory(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getPendingApproval() {
        return transactionRepository.findPendingApproval();
    }

    public boolean approve(Long id) {
        return transactionRepository.approveTransaction(id) > 0;
    }

    public boolean rollback(Long id) {
        Optional<Transaction> opt = transactionRepository.findById(id);
        if (opt.isEmpty()) return false;

        Transaction tx = opt.get();

        return transactionRepository.rollbackTransaction(id) > 0;
    }

    public boolean deleteAfterRollback(Long id) {
        return transactionRepository.markAsDeleted(id) > 0;
    }

    public void create(Transaction transaction) {
        transaction.setStatus("PENDING");
        transaction.setCreateDate(LocalDateTime.now());
        transactionRepository.createTransaction(transaction);
    }
}

