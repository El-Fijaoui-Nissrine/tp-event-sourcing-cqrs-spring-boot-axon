package com.example.demo_es_cqrs_axon.query.repo;

import com.example.demo_es_cqrs_axon.query.entities.Account;
import com.example.demo_es_cqrs_axon.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Long> {
    List<AccountTransaction> findByAccountId(String id);
}
