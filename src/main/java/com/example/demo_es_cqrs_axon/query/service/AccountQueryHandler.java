package com.example.demo_es_cqrs_axon.query.service;

import com.example.demo_es_cqrs_axon.query.dto.AccountStatementResponseDTO;
import com.example.demo_es_cqrs_axon.query.entities.Account;
import com.example.demo_es_cqrs_axon.query.entities.AccountTransaction;
import com.example.demo_es_cqrs_axon.query.queries.GetAccountStatementQuery;
import com.example.demo_es_cqrs_axon.query.queries.GetAllAccounts;
import com.example.demo_es_cqrs_axon.query.repo.AccountRepository;
import com.example.demo_es_cqrs_axon.query.repo.AccountTransactionRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;
    @QueryHandler
    public List<Account> on (GetAllAccounts query){
       return accountRepository.findAll();

    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query) {
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountTransaction> operations = accountTransactionRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account, operations);
    }
}
