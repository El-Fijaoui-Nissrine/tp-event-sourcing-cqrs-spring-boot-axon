package com.example.demo_es_cqrs_axon.query.dto;
import com.example.demo_es_cqrs_axon.query.entities.Account;
import com.example.demo_es_cqrs_axon.query.entities.AccountTransaction;

import java.util.List;

public record AccountStatementResponseDTO(Account account, List<AccountTransaction> operations) {
}
