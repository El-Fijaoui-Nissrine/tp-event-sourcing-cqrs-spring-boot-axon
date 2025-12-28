package com.example.demo_es_cqrs_axon.query.entities;

import com.example.demo_es_cqrs_axon.enums.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Account {
    @Id
    private String id;
    private double balance;
    private Instant instant;
    private AccountStatus status;
    private String currency ;
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> accountTransactions;
}
