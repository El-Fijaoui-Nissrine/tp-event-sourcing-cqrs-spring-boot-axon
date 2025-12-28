package com.example.Analytics_Service.repo;

import com.example.Analytics_Service.entities.AccountAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAnalyticsRepository extends JpaRepository<AccountAnalytics,Long> {
    AccountAnalytics findByAccountId(String accountId);
}
