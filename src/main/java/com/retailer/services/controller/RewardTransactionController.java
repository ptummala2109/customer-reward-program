package com.retailer.services.controller;

import com.retailer.services.exception.InvalidRequestException;
import com.retailer.services.exception.ResourceNotFoundException;
import com.retailer.services.model.Transaction;
import com.retailer.services.repository.RewardTransactionRepository;
import com.retailer.services.service.RewardTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions/v1")
public class RewardTransactionController {

    @Autowired
    private RewardTransactionService rewardTransactionService;

    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;

    /**
     * Description: POST method to create a new transaction and save into DB
     * Request: transaction
     * @return Transaction
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction savedTransaction = rewardTransactionService.saveTransaction(transaction);
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (InvalidRequestException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Description: GET method to retrieve all transactions from DB
     * @return List<Transaction>
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = rewardTransactionRepository.findAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Description: GET method to retrieve all transactions for a particular customer
     * Request: customerId
     * @return List<Transaction>
     */
    @GetMapping(value = "/customer/{customerId}", produces = "application/json")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable Long customerId) {
        try {
            List<Transaction> transactions = rewardTransactionService.getTransactionsByCustomerId(customerId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Description: GET method to retrieve all reward points for a particular customer
     * Request: customerId
     * @return Map<String, Integer>
     */
    @GetMapping(value = "/rewards/{customerId}",  produces = "application/json")
    public ResponseEntity<Map<String, Integer>> getRewardPoints(@PathVariable Long customerId) {
        try {
            Map<String, Integer> rewards = rewardTransactionService.getRewardsByCustomerId(customerId);
            return ResponseEntity.ok(rewards);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Description: GET method to retrieve all reward points for a particular customer between transaction dates
     * Request: customerId, startDate, endDate
     * @return Map<String, Integer>
     */
    @GetMapping(value = "/rewards/date/{customerId}", produces = "application/json")
    public ResponseEntity<Map<String, Integer>> getRewardPoints(@PathVariable Long customerId,
                                                                @RequestParam String startDate,
                                                                @RequestParam String endDate) {
        try {
            Map<String, Integer> rewards = rewardTransactionService.calculateRewardPoints(customerId, LocalDate.parse(startDate), LocalDate.parse(endDate));
            return new ResponseEntity<>(rewards, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}