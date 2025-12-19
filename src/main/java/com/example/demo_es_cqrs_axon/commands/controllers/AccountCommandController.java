package com.example.demo_es_cqrs_axon.commands.controllers;

import com.example.demo_es_cqrs_axon.commands.commands.AddAccountCommand;
import com.example.demo_es_cqrs_axon.commands.dto.AddNewAccountRequestDTO;
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
    public CompletableFuture<String> AddNewAccount(@RequestBody AddNewAccountRequestDTO request){
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
}
