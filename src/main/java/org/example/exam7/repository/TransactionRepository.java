package org.example.exam7.repository;

import org.example.exam7.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Transaction> findByAccountId(String accountId) {
        String sql = "SELECT * FROM transactions WHERE from_account = ? OR to_account = ?";
        return jdbcTemplate.query(sql, new Object[]{accountId, accountId}, new BeanPropertyRowMapper<>(Transaction.class));
    }

    public List<Transaction> findAll() {
        return jdbcTemplate.query("SELECT * FROM transactions", new BeanPropertyRowMapper<>(Transaction.class));
    }

    public List<Transaction> findPendingApproval() {
        return jdbcTemplate.query("SELECT * FROM transactions WHERE status = 'PENDING'", new BeanPropertyRowMapper<>(Transaction.class));
    }

    public int approveTransaction(Long id) {
        return jdbcTemplate.update("UPDATE transactions SET status = 'APPROVED' WHERE id = ?", id);
    }

    public int rollbackTransaction(Long id) {
        // ты можешь реализовать логику с проверками баланса и вставкой в журнал
        return jdbcTemplate.update("UPDATE transactions SET status = 'ROLLED_BACK' WHERE id = ?", id);
    }

    public int markAsDeleted(Long id) {
        return jdbcTemplate.update("UPDATE transactions SET status = 'DELETED' WHERE id = ?", id);
    }

    public Optional<Transaction> findById(Long id) {
        try {
            String sql = "SELECT * FROM transactions WHERE id = ?";
            Transaction transaction = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Transaction.class));
            return Optional.ofNullable(transaction);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void createTransaction(Transaction transaction) {
        jdbcTemplate.update("""
            INSERT INTO transactions (from_account, to_account, amount, currency, status, create_date)
            VALUES (?, ?, ?, ?, ?, ?)
            """,
                transaction.getFromAccount(),
                transaction.getToAccount(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus(),
                transaction.getCreateDate()
        );
    }
}
