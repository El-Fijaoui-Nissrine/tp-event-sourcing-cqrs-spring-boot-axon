package com.example.demo_es_cqrs_axon.commands.aggregates;

import com.example.demo_es_cqrs_axon.commands.commands.AddAccountCommand;
import com.example.demo_es_cqrs_axon.commands.commands.CreditAccountCommand;
import com.example.demo_es_cqrs_axon.commands.commands.DebitAccountCommand;
import com.example.demo_es_cqrs_axon.enums.AccountStatus;
import com.example.demo_es_cqrs_axon.events.AccountCreatedEvent;
import com.example.demo_es_cqrs_axon.events.AccountCreditedEvent;
import com.example.demo_es_cqrs_axon.events.AccountDebitedEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
@Getter @Setter
@Slf4j

public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;
    private String currency ;
    public AccountAggregate(){}
@CommandHandler
    public AccountAggregate(AddAccountCommand command){
    log.info("CreateAccount Command Received");
if (command.getInitialBalance()<0) throw  new IllegalArgumentException("balance must be positive");
    AggregateLifecycle.apply(new AccountCreatedEvent(
            command.getId(),
            command.getInitialBalance(),
            AccountStatus.CREATED,
            command.getCurrency()));

    }

@EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId=event.getAccountId();
    this.balance=event.getInitialBalance();
    this.status=event.getStatus();
this.currency=event.getCurrency();

}
@CommandHandler
public void handle(CreditAccountCommand command){
    log.info("CreditAccount Command Received");
    if(command.getAmount()<0) throw new RuntimeException("amount most be positive");
   AggregateLifecycle.apply(new AccountCreditedEvent(
           command.getAccountId(),
           command.getAmount(),
           command.getCurrency()
   ));
}
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){

        this.balance=this.balance+event.getAmount();


    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        log.info("debittAccount Command Received");
        if(command.getAmount()>this.balance) throw new RuntimeException("balance not sufficient Exception");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getAccountId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){

        this.balance=this.balance-event.getAmount();


    }


}
