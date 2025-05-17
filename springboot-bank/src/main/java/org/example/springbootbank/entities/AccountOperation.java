package org.example.springbootbank.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private double amount;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne
    private BankAccount bankAccount;
    private String description;
}