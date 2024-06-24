package com.retailer.services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.services.model.Transaction;
import com.retailer.services.repository.RewardTransactionRepository;
import com.retailer.services.service.RewardTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardTransactionController.class)
public class RewardTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardTransactionRepository rewardTransactionRepository;

    @MockBean
    private RewardTransactionService rewardTransactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTransaction() throws Exception {
        // Create a sample transaction
        Transaction transaction = new Transaction(null, 1L, 120.0, LocalDate.now());

        // Create a sample saved transaction (assuming the ID is generated upon save)
        Transaction savedTransaction = new Transaction(1L, 1L, 120.0, LocalDate.now());

        // Mock the service method
        when(rewardTransactionService.saveTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        // Perform POST request to /api/transactions
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andDo(print())  // Print the response for debugging
                .andExpect(status().isCreated())  // Verify the response status is 201 CREATED
                .andExpect(jsonPath("$.trans_id").value(1))  // Verify the transaction ID in the response
                .andExpect(jsonPath("$.customer_id").value(1))  // Verify the customer ID in the response
                .andExpect(jsonPath("$.amount").value(120.0))  // Verify the amount in the response
                .andExpect(jsonPath("$.transaction_date").value(LocalDate.now().toString()));  // Verify the transaction date in the response
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        // Create a sample transaction
        Transaction transaction = new Transaction(null, 1L, 120.0, LocalDate.now());

        // Mock the service method
        when(rewardTransactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));

        // Perform GET request to /api/transactions
        mockMvc.perform(get("/api/transactions"))
                .andDo(print()) // Print the response for debugging
                .andExpect(status().isOk()) // Verify the response status is 200 OK
                .andExpect(jsonPath("$[0].customer_id").value(1)) // Verify the customer ID in the response
                .andExpect(jsonPath("$[0].amount").value(120.0)); // Verify the amount in the response
    }

    @Test
    public void testGetTransactionsByCustomerId() throws Exception {
        // Create a sample transaction
        Transaction transaction = new Transaction(null, 1L, 120.0, LocalDate.now());

        // Mock the service method
        when(rewardTransactionService.getTransactionsByCustomerId(anyLong())).thenReturn(Collections.singletonList(transaction));

        // Perform GET request to /api/transactions/customer/{customerId}
        mockMvc.perform(get("/api/transactions/customer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Print the response for debugging
                .andExpect(status().isOk()) // Verify the response status is 200 OK
                .andExpect(jsonPath("$[0].customer_id").value(1)) // Verify the customer ID in the response
                .andExpect(jsonPath("$[0].amount").value(120.0)); // Verify the amount in the response
    }


    @Test
    public void testGetRewardsByCustomerId() throws Exception {
        // Create a sample rewards map
        Map<String, Integer> rewards = new HashMap<>();
        rewards.put("January", 100);
        rewards.put("February", 150);

        // Mock the service method
        when(rewardTransactionService.getRewardsByCustomer(anyLong())).thenReturn(rewards);

        // Perform GET request to /api/transactions/rewards/{customerId}
        mockMvc.perform(get("/api/transactions/rewards/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // Print the response for debugging
                .andExpect(status().isOk()) // Verify the response status is 200 OK
                .andExpect(jsonPath("$.January").value(100)) // Verify the January reward points in the response
                .andExpect(jsonPath("$.February").value(150)); // Verify the February reward points in the response
    }

    @Test
    public void testGetRewardPointsByDate() throws Exception {
        // Create a sample rewards map
        Map<String, Integer> rewards = new HashMap<>();
        rewards.put("January", 100);
        rewards.put("February", 150);

        // Mock the service method
        when(rewardTransactionService.calculateRewardPoints(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(rewards);

        // Define the start and end dates
        String startDate = "2023-01-01";
        String endDate = "2023-02-28";

        // Perform GET request to /api/transactions/rewards/date/1 with request parameters
        mockMvc.perform(get("/api/transactions/rewards/date/1")
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())  // Print the response for debugging
                .andExpect(status().isOk())  // Verify the response status is 200 OK
                .andExpect(jsonPath("$.January").value(100))  // Verify the January reward points in the response
                .andExpect(jsonPath("$.February").value(150));  // Verify the February reward points in the response
    }

}
