package com.retailer.services.repository;

import com.retailer.services.model.Customer;
import com.retailer.services.model.Transaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
public class RewardTransactionRepositoryTest {

    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    Long customerId;
    LocalDate startDate;
    LocalDate endDate;

    @BeforeEach
    void setUp() {
        // Create customer entity and save into repository
        Customer customer = new Customer(32456L, "Test 10 Praveen", "Tummala", LocalDate.of(1991, 1, 10));
        customer = customerRepository.save(customer); // Save the customer to get the generated ID

        customerId = customer.getCustomerId();
        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2023, 1, 31);

        // Create transaction entity and save into transaction repository
        Transaction transaction1 = new Transaction(null, customerId, 100.0, LocalDate.of(2023, 1, 10));
        Transaction transaction2 = new Transaction(null, customerId, 200.0, LocalDate.of(2023, 1, 20));
        Transaction transaction3 = new Transaction(null, customerId, 300.0, LocalDate.of(2023, 2, 10)); // out of range

        rewardTransactionRepository.save(transaction1);
        rewardTransactionRepository.save(transaction2);
        rewardTransactionRepository.save(transaction3);
    }

    @Test
    public void testFindByCustomerIdAndTransactionDateBetween() {
        // given
        // setup() method will get the inputs

        // when
        List<Transaction> transactions = rewardTransactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

        // then
        assertThat(transactions).hasSize(2);
        assertTrue(transactions.stream().anyMatch(t -> t.getAmount() == 100.0));
        assertTrue(transactions.stream().anyMatch(t -> t.getAmount() == 200.0));
    }

    @Test
    public void testFindByCustomerId() {
        // given
        // setup() method will get the inputs

        // when
        List<Transaction> transactions = rewardTransactionRepository.findByCustomerId(customerId);

        // then
        assertThat(transactions).hasSize(3);
        assertThat(transactions).extracting(Transaction::getAmount).containsExactlyInAnyOrder(100.0, 200.0, 300.0);
    }
}