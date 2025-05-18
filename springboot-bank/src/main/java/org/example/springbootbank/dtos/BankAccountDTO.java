package org.example.springbootbank.dtos;

import lombok.Data;
import org.example.springbootbank.entities.AccountStatus;


import java.util.Date;

@Data
public class BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private String type;
} 