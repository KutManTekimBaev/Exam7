package org.example.exam7.controllers;


import org.example.exam7.dto.AccountRequestDTO;
import org.example.exam7.dto.DepositRequestDTO;
import org.example.exam7.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountRequestDTO dto) {
        service.createAccount(dto);
        return ResponseEntity.ok("Счёт успешно создан!");
    }
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositRequestDTO dto) {
        try {
            service.deposit(dto.getAccountId(), dto.getAmount());
            return ResponseEntity.ok("Пополнение прошло успешно!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}

