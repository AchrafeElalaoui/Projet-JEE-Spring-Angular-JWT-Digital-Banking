package org.example.springbootbank.dtos;

import lombok.Data;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private double interestRate;
} 