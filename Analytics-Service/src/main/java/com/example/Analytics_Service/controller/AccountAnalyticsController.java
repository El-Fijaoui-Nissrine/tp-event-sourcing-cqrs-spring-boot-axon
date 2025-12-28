package com.example.Analytics_Service.controller;

import com.example.Analytics_Service.entities.AccountAnalytics;
import com.example.Analytics_Service.queries.GetAccountAnalyticsById;
import com.example.Analytics_Service.queries.GetAllAccountAnalytics;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@RestController
@AllArgsConstructor

public class AccountAnalyticsController {
    private QueryGateway queryGateway;
    @GetMapping("/query/accountAnalytics")
    public CompletableFuture<List<AccountAnalytics>> accountAnalytics(){
        return queryGateway.query(new GetAllAccountAnalytics(), ResponseTypes.multipleInstancesOf(AccountAnalytics.class));
    }
    @GetMapping("/query/accountAnalytics/{accountId}")
    public CompletableFuture<AccountAnalytics> accountAnalyticsById(@PathVariable String accountId){
        return queryGateway.query(new GetAccountAnalyticsById(accountId), ResponseTypes.instanceOf(AccountAnalytics.class));
    }
    @GetMapping(value= "/query/accountAnalytics/{accountId}/watch",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountAnalytics> watch(@PathVariable String accountId){
        SubscriptionQueryResult<AccountAnalytics,AccountAnalytics> subscriptionQueryResult=queryGateway.subscriptionQuery(new GetAccountAnalyticsById(accountId),
                AccountAnalytics.class,AccountAnalytics.class);

        return subscriptionQueryResult.initialResult()
                .concatWith(subscriptionQueryResult.updates())
                .doFinally(signal -> subscriptionQueryResult.close());
    }

}
