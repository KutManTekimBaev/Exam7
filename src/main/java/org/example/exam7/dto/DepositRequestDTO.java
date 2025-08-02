package org.example.exam7.dto;

import lombok.Data;

@Data
public class DepositRequestDTO {
    private Long accountId;
    private double amount;
}
