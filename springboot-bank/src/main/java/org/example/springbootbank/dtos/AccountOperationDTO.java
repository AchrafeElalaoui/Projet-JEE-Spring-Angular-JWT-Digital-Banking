package org.example.springbootbank.dtos;

import lombok.Data;
import org.example.springbootbank.entities.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
} 