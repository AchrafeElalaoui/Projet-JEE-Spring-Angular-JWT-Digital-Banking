package org.example.springbootbank.entities;


import jakarta.persistence.*;

import java.util.List;
import lombok.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<BankAccount> bankAccounts;

}