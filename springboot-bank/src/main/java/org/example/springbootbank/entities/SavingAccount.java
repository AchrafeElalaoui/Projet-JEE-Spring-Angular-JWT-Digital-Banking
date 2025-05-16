package org.example.springbootbank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("SA")
@Data @AllArgsConstructor @NoArgsConstructor
public class SavingAccount extends BankAccount {
    private double interestRate;
}
