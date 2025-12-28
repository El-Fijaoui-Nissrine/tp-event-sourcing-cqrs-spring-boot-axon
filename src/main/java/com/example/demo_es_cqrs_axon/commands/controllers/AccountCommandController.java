package com.example.demo_es_cqrs_axon.commands.controllers;

import com.example.demo_es_cqrs_axon.commands.commands.AddAccountCommand;
import com.example.demo_es_cqrs_axon.commands.commands.CreditAccountCommand;
import com.example.demo_es_cqrs_axon.commands.commands.DebitAccountCommand;
import com.example.demo_es_cqrs_axon.commands.dto.AddNewAccountRequestDTO;
import com.example.demo_es_cqrs_axon.commands.dto.CreditAccountRequestDTO;
import com.example.demo_es_cqrs_axon.commands.dto.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private EventStore eventStore;
    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> response = this.commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return response;
    }
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        return e.getMessage();
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> response = this.commandGateway.send(new CreditAccountCommand(
               request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }
    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> response = this.commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }
}
