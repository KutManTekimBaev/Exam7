package org.example.exam7.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String phone;
    private String username;
    private String password;
}
