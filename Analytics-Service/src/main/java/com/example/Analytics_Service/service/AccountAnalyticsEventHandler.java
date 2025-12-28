package com.example.Analytics_Service.service;

import com.example.Analytics_Service.entities.AccountAnalytics;
import com.example.Analytics_Service.queries.GetAccountAnalyticsById;
import com.example.Analytics_Service.queries.GetAllAccountAnalytics;
import com.example.Analytics_Service.repo.AccountAnalyticsRepository;
import com.example.demo_es_cqrs_axon.events.AccountCreatedEvent;
import com.example.demo_es_cqrs_axon.events.AccountCreditedEvent;
import com.example.demo_es_cqrs_axon.events.AccountDebitedEvent;
import lombok.AllArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountAnalyticsEventHandler {
    private AccountAnalyticsRepository accountAnalyticsRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on (AccountCreatedEvent createdEvent){

        AccountAnalytics accountAnalytics= AccountAnalytics.builder()
                .accountId(createdEvent.getAccountId())
                .totalDebit(0)
                .totalCredit(0)
                .totalNubeOfCredit(0)
                .totalNubeOfDebit(0)
                .balance(createdEvent.getInitialBalance())
                .build();
    accountAnalyticsRepository.save(accountAnalytics);
    }


    @EventHandler
    public void on (AccountDebitedEvent event){
AccountAnalytics accountAnalytics=accountAnalyticsRepository.findByAccountId(event.getAccountId());
        accountAnalytics.setBalance(accountAnalytics.getBalance()-event.getAmount());
        accountAnalytics.setTotalDebit(accountAnalytics.getTotalDebit()+ event.getAmount());
        accountAnalytics.setTotalNubeOfDebit(accountAnalytics.getTotalNubeOfDebit()+1);
        accountAnalyticsRepository.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAccountAnalyticsById.class,(query)->query.getAccountId().equals(accountAnalytics.getAccountId()),accountAnalytics);
    }
    @EventHandler
    public void on (AccountCreditedEvent event){
        AccountAnalytics accountAnalytics=accountAnalyticsRepository.findByAccountId(event.getAccountId());
        accountAnalytics.setBalance(accountAnalytics.getBalance()+event.getAmount());
        accountAnalytics.setTotalCredit(accountAnalytics.getTotalCredit()+ event.getAmount());
        accountAnalytics.setTotalNubeOfCredit(accountAnalytics.getTotalNubeOfCredit()+1);
        accountAnalyticsRepository.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAccountAnalyticsById.class,(query)->query.getAccountId().equals(accountAnalytics.getAccountId()),accountAnalytics);

    }
@QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalytics query){
        return accountAnalyticsRepository.findAll();
}

    @QueryHandler
    public AccountAnalytics on(GetAccountAnalyticsById query){
        return accountAnalyticsRepository.findByAccountId(query.getAccountId());
    }


}
