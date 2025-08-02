package org.example.exam7.service;

import lombok.RequiredArgsConstructor;

import org.example.exam7.dto.RegisterRequest;
import org.example.exam7.model.User;
import org.example.exam7.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void register(RegisterRequest request) {
        User user = User.builder()
                .phone(request.getPhone())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .enabled(true)
                .role("USER")
                .build();
        repository.save(user);
    }
}

