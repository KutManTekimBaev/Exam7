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


}
