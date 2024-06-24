package com.retailer.services.service;

import com.retailer.services.model.Transaction;
import com.retailer.services.repository.RewardTransactionRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardTransactionService {

    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;

    private static final Logger LOG = LoggerFactory.getLogger(RewardTransactionService.class);

    public Transaction saveTransaction(Transaction transaction) {
        return rewardTransactionRepository.save(transaction);
    }

    public List<Transaction>getTransactionsByCustomerId(Long customerId) {
        return rewardTransactionRepository.findByCustomerId(customerId);
    }

    /**
     * Description: Retrieves rewards for customer by id
     * Request: customerId
     * @return Map<String, Integer>
     */
    public Map<String, Integer> getRewardsByCustomer(Long customerId) {
        LOG.debug("Fetching rewards for customer with ID: {}", customerId);
        Map<String, Integer> rewards = new HashMap<>();
        YearMonth currentMonth = YearMonth.now();

        for (int i = 0; i < 3; i++) {
            YearMonth month = currentMonth.minusMonths(i);
            LocalDate startDate = month.atDay(1);
            LocalDate endDate = month.atEndOfMonth();
            List<Transaction> transactions = rewardTransactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);

            int points = transactions.stream().mapToInt(this::calculatePoints).sum();
            rewards.put(month.toString(), points);
        }
        LOG.debug("Fetched rewards for customer with ID: {}, rewards: {}", customerId, rewards);
        return rewards;
    }

    /**
     * Description: Retrieves reward points for a customer between transaction date
     * Request: customerId, startDate, endDate
     * @return Map<String, Integer>
     */
    public Map<String, Integer>calculateRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate) {
        LOG.debug("Fetching reward points for customer with ID: {}, start Date {}, end date {}", customerId, startDate, endDate);
        List<Transaction> transactions = rewardTransactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
        return transactions.stream().collect(Collectors.groupingBy(
                transaction ->transaction.getTransactionDate().getMonth().toString(),
                Collectors.summingInt(this::calculatePoints)
        ));
    }

    /**
     * Description: Calculate reward points for a customer
     * Request: transaction
     * @return int
     */
    private int calculatePoints(@NotNull Transaction transaction) {
        LOG.debug("Fetching reward points for customer with ID: {}", transaction.getCustomerId());
        double amount = transaction.getAmount();
        int points = 0;

        if (amount > 100) {
            points += (int) ((amount - 100) * 2);
            amount = 100;
        }
        if (amount > 50) {
            points += (int) (amount - 50);
        }
        LOG.debug("Fetched reward points for customer with ID: {}, and points: {}", transaction.getCustomerId(), points);
        return points;
    }

    /**
     * Description: Calculate reward points for a customer
     * Request: transaction
     * @return int
     */
    public int getPointsForTransaction(Transaction transaction) {
        return calculatePoints(transaction);
    }


}
