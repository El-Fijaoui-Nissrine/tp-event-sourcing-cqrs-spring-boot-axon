package com.example.demo_es_cqrs_axon.query.service;

import com.example.demo_es_cqrs_axon.enums.TransactionType;
import com.example.demo_es_cqrs_axon.events.AccountCreatedEvent;
import com.example.demo_es_cqrs_axon.events.AccountCreditedEvent;
import com.example.demo_es_cqrs_axon.events.AccountDebitedEvent;
import com.example.demo_es_cqrs_axon.query.entities.Account;
import com.example.demo_es_cqrs_axon.query.entities.AccountTransaction;
import com.example.demo_es_cqrs_axon.query.repo.AccountRepository;
import com.example.demo_es_cqrs_axon.query.repo.AccountTransactionRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;
    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventMessage){
        Account account=new Account();
        account.setId(event.getAccountId());
        account.setBalance(event.getInitialBalance());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        account.setInstant(eventMessage.getTimestamp());
accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage) {
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountTransaction accountOperation = AccountTransaction.builder()
                .amount(event.getAmount())
                .instant(eventMessage.getTimestamp())
                .type(TransactionType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        accountTransactionRepository.save(accountOperation);
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage) {
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountTransaction accountOperation = AccountTransaction.builder()
                .amount(event.getAmount())
                .instant(eventMessage.getTimestamp())
                .type(TransactionType.Debit)
                .currency(event.getCurrency())
                .account(account)
                .build();
        accountTransactionRepository.save(accountOperation);
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
    }
}
