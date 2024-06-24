package com.retailer.services.repository;

import com.retailer.services.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RewardTransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Description: Retrieves list of transactions for customer between transaction date
     * Request: customerId, startDate, endDate
     * @return List<Transaction>
     */
    List<Transaction> findByCustomerIdAndTransactionDateBetween(Long customerId, LocalDate startDate, LocalDate endDate);

    /**
     * Description: Retrieves list of transactions by a customer
     * Request: customerId
     * @return List<Transaction>
     */
    List<Transaction> findByCustomerId(Long customerId);
}

