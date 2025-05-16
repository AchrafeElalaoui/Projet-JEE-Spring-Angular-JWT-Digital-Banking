package org.example.springbootbank.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Data @AllArgsConstructor @NoArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private Date createdAt;
    private double balance;
    private String currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;
}
