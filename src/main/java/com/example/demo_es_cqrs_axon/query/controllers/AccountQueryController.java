package com.example.demo_es_cqrs_axon.query.controllers;

import com.example.demo_es_cqrs_axon.query.dto.AccountStatementResponseDTO;
import com.example.demo_es_cqrs_axon.query.entities.Account;
import com.example.demo_es_cqrs_axon.query.queries.GetAccountStatementQuery;
import com.example.demo_es_cqrs_axon.query.queries.GetAllAccounts;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
public class AccountQueryController {
    private QueryGateway queryGateway;
@GetMapping("/list")
    public CompletableFuture<List<Account>>accountList(){
    CompletableFuture<List<Account>> result=queryGateway.query(
            new GetAllAccounts(),
            ResponseTypes.multipleInstancesOf(Account.class)
);
return result;}
    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId) {
        return queryGateway.query(
                new GetAccountStatementQuery(accountId),
                ResponseTypes.instanceOf(AccountStatementResponseDTO.class)
        );
    }

}
