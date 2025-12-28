package com.example.demo_es_cqrs_axon.commands.dto;

public record DebitAccountRequestDTO(String accountId, double amount, String currency){

}
