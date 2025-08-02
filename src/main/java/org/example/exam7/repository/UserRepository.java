package org.example.exam7.repository;

import lombok.RequiredArgsConstructor;

import org.example.exam7.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbc;

    public void save(User user) {
        String sql = """
            INSERT INTO users (phone, username, password, enabled, role)
            VALUES (?, ?, ?, ?, ?)
        """;
        jdbc.update(sql,
                user.getPhone(),
                user.getUsername(),
                user.getPassword(),
                true,
                user.getRole());
    }

    public Optional<User> findByPhone(String phone) {
        String sql = "SELECT * FROM users WHERE phone = ?";
        List<User> users = jdbc.query(sql, new BeanPropertyRowMapper<>(User.class), phone);
        return users.stream().findFirst();
    }
}





