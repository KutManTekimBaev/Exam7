package org.example.exam7.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String phone;
    private String username;
    private String password;
    private Boolean enabled;
    private String role;
}

