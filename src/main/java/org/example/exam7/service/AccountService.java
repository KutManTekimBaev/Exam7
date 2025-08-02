package org.example.exam7.service;

import org.example.exam7.dto.AccountRequestDTO;
import org.example.exam7.model.Account;
import org.example.exam7.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repo;
    private final JdbcTemplate jdbc;

    public AccountService(AccountRepository repo, JdbcTemplate jdbc) {
        this.repo = repo;
        this.jdbc = jdbc;
    }

    public void createAccount(AccountRequestDTO dto) {
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setUserId(dto.getUserId());
        account.setBalance(0.0);

        repo.save(account);
    }

    public void deposit(Long accountId, double amount) {
        String selectSql = "SELECT balance FROM accounts WHERE id = ?";
        Double currentBalance = jdbc.queryForObject(selectSql, Double.class, accountId);

        if (currentBalance == null) {
            throw new RuntimeException("Аккаунт не найден");
        }

        double newBalance = currentBalance + amount;

        String updateSql = "UPDATE accounts SET balance = ? WHERE id = ?";
        jdbc.update(updateSql, newBalance, accountId);
    }


}
