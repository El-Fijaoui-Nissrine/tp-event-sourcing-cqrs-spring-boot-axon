package com.example.demo_es_cqrs_axon.query.repo;

import com.example.demo_es_cqrs_axon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
