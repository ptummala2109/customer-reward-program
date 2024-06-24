package com.retailer.services.repository;

import com.retailer.services.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RewardTransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerIdAndTransactionDateBetween(Long customerId, LocalDate startDate, LocalDate endDate);

    List<Transaction> findByCustomerId(Long customerId);
}

