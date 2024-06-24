package com.retailer.services.service;

import com.retailer.services.model.Transaction;
import com.retailer.services.repository.RewardTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class RewardTransactionServiceTest {

    @Mock
    private RewardTransactionRepository rewardTransactionRepository;

    @InjectMocks
    private RewardTransactionService rewardTransactionService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(rewardTransactionRepository);
    }

    @Test
    public void testSaveTransaction() {
        // Create a sample transaction
        Transaction transaction = new Transaction(6L, 12345L, 100.0, LocalDate.of(2024, 6, 22));

        Mockito.when(rewardTransactionRepository.save(transaction)).thenReturn(transaction);

        Transaction savedTransaction = rewardTransactionService.saveTransaction(transaction);

        assertNotNull(savedTransaction);
        assertEquals(transaction.getTransId(), savedTransaction.getTransId());
        assertEquals(transaction.getAmount(), savedTransaction.getAmount());
        assertEquals(transaction, savedTransaction);
    }

   @Test
    public void testGetTransactionsByCustomerId() {
        Long customerId = 12345L;

        List<Transaction> mockTransactions = Arrays.asList(
            new Transaction(5L, customerId, 100.0, LocalDate.of(2023, 1, 10)),
            new Transaction(6L, customerId, 200.0, LocalDate.of(2023, 1, 20)),
            new Transaction(7L, customerId, 300.0, LocalDate.of(2023, 2, 10))
        );

        Mockito.when(rewardTransactionRepository.findByCustomerId(customerId)).thenReturn(mockTransactions);

        List<Transaction> transactions = rewardTransactionService.getTransactionsByCustomerId(customerId);

        assertNotNull(transactions);
        assertThat(transactions).hasSize(3);
        assertTrue(transactions.stream().anyMatch(t -> t.getAmount() == 100.0));
        assertTrue(transactions.stream().anyMatch(t -> t.getAmount() == 200.0));
        assertThat(transactions).extracting(Transaction::getAmount).containsExactlyInAnyOrder(100.0, 200.0, 300.0);
    }

   @Test
    public void testGetRewardsByCustomer() {
        Long customerId = 54321L;

        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(10L, customerId, 100.0, LocalDate.of(2023, 1, 10)),
                new Transaction(11L, customerId, 200.0, LocalDate.of(2023, 2, 20)),
                new Transaction(12L, customerId, 300.0, LocalDate.of(2023, 3, 10))
        );

        Mockito.when(rewardTransactionRepository.findByCustomerIdAndTransactionDateBetween(
                        Mockito.eq(customerId), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(mockTransactions);

        Map<String, Integer> rewards = rewardTransactionService.getRewardsByCustomer(customerId);

        assertNotNull(rewards);
        assertEquals(3, rewards.size());
        assertTrue(rewards.values().stream().mapToInt(Integer::intValue).sum() > 0);
    }

    @Test
    public void testCalculateRewardPoints() {
        Long customerId = 34521L;
        LocalDate startDate = LocalDate.now().minusMonths(3);
        LocalDate endDate = LocalDate.now();

        List<Transaction> mockTransactions = Arrays.asList(
                new Transaction(14L, customerId, 100.0, LocalDate.of(2023, 1, 10)),
                new Transaction(15L, customerId, 200.0, LocalDate.of(2023, 1, 20)),
                new Transaction(16L, customerId, 300.0, LocalDate.of(2023, 2, 10))
        );

        Mockito.when(rewardTransactionRepository.findByCustomerIdAndTransactionDateBetween(
                        Mockito.eq(customerId), Mockito.eq(startDate), Mockito.eq(endDate)))
                .thenReturn(mockTransactions);

        Map<String, Integer> rewards = rewardTransactionService.calculateRewardPoints(customerId, startDate, endDate);

        assertNotNull(rewards);
        assertEquals(2, rewards.size());
        assertTrue(rewards.values().stream().mapToInt(Integer::intValue).sum() > 0);
        assertEquals(750, rewards.values().stream().mapToInt(Integer::intValue).sum());
    }

    @Test
    public void testGetPointsForTransaction() {
        // Create a sample transaction
        Transaction transaction = new Transaction(4L, 44444L, 120.0, LocalDate.now());

        int points = rewardTransactionService.getPointsForTransaction(transaction);

        assertTrue(points >= 0); // Ensure points calculation logic is correct
        assertEquals(90, points);
    }

    @Test
    public void testGetPointsForTransactionBelow100() {
        // Create a sample transaction
        Transaction transaction = new Transaction(4L, 44444L, 60.0, LocalDate.now());

        int points = rewardTransactionService.getPointsForTransaction(transaction);

        // Ensure points calculation logic is correct
        assertTrue(points >= 0);
        assertEquals(10, points);
    }

    @Test
    public void testGetPointsForTransactionBelow50() {
        // Create a sample transaction
        Transaction transaction = new Transaction(4L, 44444L, 40.0, LocalDate.now());

        int points = rewardTransactionService.getPointsForTransaction(transaction);

        // Ensure points calculation logic is correct
        assertTrue(points >= 0);
        assertEquals(0, points);
    }



}