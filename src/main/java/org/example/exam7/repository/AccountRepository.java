package org.example.exam7.repository;

import org.example.exam7.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    private final JdbcTemplate jdbc;

    public AccountRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(Account account) {
        String sql = """
        INSERT INTO accounts (account_number, balance, user_id, currency)
        VALUES (?, ?, ?, ?)
    """;
        jdbc.update(sql,
                account.getAccountNumber(),
                account.getBalance(),
                account.getUserId(),
                account.getCurrency());
    }
    public int countByUserId(Long userId) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE user_id = ?";
        return jdbc.queryForObject(sql, Integer.class, userId);
    }

    public int countByUserIdAndCurrency(Long userId, String currency) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE user_id = ? AND currency = ?";
        return jdbc.queryForObject(sql, Integer.class, userId, currency);
    }


}
