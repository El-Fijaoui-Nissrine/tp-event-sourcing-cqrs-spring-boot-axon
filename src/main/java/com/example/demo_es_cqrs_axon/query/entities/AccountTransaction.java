package com.example.demo_es_cqrs_axon.query.entities;

import com.example.demo_es_cqrs_axon.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class AccountTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant instant;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private String currency;
    @ManyToOne
    @JsonIgnore
private  Account account;
}
