package com.example.demo_es_cqrs_axon.query.dto;

import com.example.demo_es_cqrs_axon.query.entities.Account;
import com.example.demo_es_cqrs_axon.query.entities.AccountTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatementResponseDTO {
    private Account account;
    private List<AccountTransaction> operations;
}