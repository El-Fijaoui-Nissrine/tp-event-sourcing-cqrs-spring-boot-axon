package com.example.demo_es_cqrs_axon.query.queries;

import lombok.*;

@AllArgsConstructor
@Getter
public class GetAccountStatementQuery {
    private String accountId;
}
