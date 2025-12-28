package com.example.Analytics_Service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @Data @NoArgsConstructor @Builder
public class AccountAnalytics {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private double balance;
    private double totalDebit;
    private double totalCredit;
    private int totalNubeOfDebit;
    private int totalNubeOfCredit;

}
