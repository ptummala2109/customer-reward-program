package com.retailer.services.controller;

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
@RequestMapping("/api/transactions")
public class RewardTransactionController {

    @Autowired
    private RewardTransactionService rewardTransactionService;

    @Autowired
    private RewardTransactionRepository rewardTransactionRepository;

    @PostMapping
    //POST method to create a new transaction and save into DB
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = rewardTransactionService.saveTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping()
    //GET method to retrieve all transactions from DB
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = rewardTransactionRepository.findAll();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    //GET method to retrieve all transactions for a particular customer
    public ResponseEntity<List<Transaction>> getTransactionsByCustomerId(@PathVariable Long customerId) {
        List<Transaction> transactions = rewardTransactionService.getTransactionsByCustomerId(customerId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/rewards/{customerId}")
    //GET method to retrieve all reward points for a particular customer
    public ResponseEntity<Map<String, Integer>> getRewardPoints(@PathVariable Long customerId) {
        Map<String, Integer> rewards = rewardTransactionService.getRewardsByCustomer(customerId);
        return ResponseEntity.ok(rewards);
    }

    @GetMapping("/rewards/date/{customerId}")
    //GET method to retrieve all reward points for a particular customer between transaction dates
    public ResponseEntity<Map<String, Integer>> getRewardPoints(@PathVariable Long customerId,
                                                                @RequestParam String startDate,
                                                                @RequestParam String endDate) {
        Map<String, Integer> rewards = rewardTransactionService.calculateRewardPoints(customerId, LocalDate.parse(startDate), LocalDate.parse(endDate));
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }


    @ExceptionHandler()
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

