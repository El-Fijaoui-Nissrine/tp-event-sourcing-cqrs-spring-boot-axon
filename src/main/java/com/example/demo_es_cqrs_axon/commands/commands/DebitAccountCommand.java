package com.example.demo_es_cqrs_axon.commands.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class DebitAccountCommand {
    @TargetAggregateIdentifier
    private String accountId;
    private double amount;
    private String currency;
}
