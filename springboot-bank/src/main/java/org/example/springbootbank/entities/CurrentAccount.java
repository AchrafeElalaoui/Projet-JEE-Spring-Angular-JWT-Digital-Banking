package org.example.springbootbank.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("CA")
@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentAccount extends BankAccount {
    private double overDraft;
}
