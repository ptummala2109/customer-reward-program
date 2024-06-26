package com.retailer.services;

import com.retailer.services.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class RewardTransactionApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateTransactionAndSave() {
        String baseUrl = "http://localhost:" + port + "/retailer/api/transactions/v1";
        Transaction transaction = new Transaction(null, 33333L, 120.0, LocalDate.now());

        ResponseEntity<Transaction> response = restTemplate.postForEntity (baseUrl, transaction, Transaction.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(120.0, response.getBody().getAmount());
    }

    @Test
    public void testRetrieveAllTransactions() {
        String baseUrl = "http://localhost:" + port + "/retailer/api/transactions/v1";

        ResponseEntity<Transaction[]> getAllResponse = restTemplate.getForEntity(baseUrl, Transaction[].class);
        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
        assertNotNull(getAllResponse.getBody());
        assertEquals(25,getAllResponse.getBody().length);
    }

    @Test
    public void testGetTransactionsByCustomerId() {
        long customerId = 99999L;
        String baseUrl = "http://localhost:" + port + "/retailer/api/transactions/v1/customer/"+customerId;

        ResponseEntity<Transaction[]> getTransactionResponse = restTemplate.getForEntity(baseUrl, Transaction[].class);
        assertEquals(HttpStatus.OK, getTransactionResponse.getStatusCode());
        assertNotNull(getTransactionResponse.getBody());
        assertEquals(1,getTransactionResponse.getBody().length);
    }

    @Test
    public void testGetTransactionsByCustomerIdNotExist() {
        long customerId = 999L;
        String baseUrl = "http://localhost:" + port + "/retailer/api/transactions/v1/customer/"+customerId;

        ResponseEntity<Transaction[]> getTransactionResponse = restTemplate.getForEntity(baseUrl, Transaction[].class);
        assertEquals(HttpStatus.NOT_FOUND, getTransactionResponse.getStatusCode());
        assertNull(getTransactionResponse.getBody());
    }

    @Test
    public void testGetRewardsByCustomerId() {
        long customerId = 99999L;
        String baseUrl = "http://localhost:" + port + "/retailer/api/transactions/v1/rewards/"+customerId;

        ResponseEntity<Map> getRewardsResponse = restTemplate.getForEntity(baseUrl, Map.class);
        assertEquals(HttpStatus.OK, getRewardsResponse.getStatusCode());
        assertNotNull(getRewardsResponse.getBody());
        assertEquals(3,getRewardsResponse.getBody().size());
        assertEquals(90, getRewardsResponse.getBody().get("2024-06"));
    }

    @Test
    public void testGetRewardsByCustomerIdNotExist() {
        long customerId = 999L;
        String baseUrl = "http://localhost:" + port + "/retailer/api/transactions/v1/rewards/"+customerId;

        ResponseEntity<Map> getRewardsResponse = restTemplate.getForEntity(baseUrl, Map.class);
        assertEquals(HttpStatus.OK, getRewardsResponse.getStatusCode());
        assertNotNull(getRewardsResponse.getBody());
        assertEquals(3,getRewardsResponse.getBody().size());
        assertEquals(0, getRewardsResponse.getBody().get("2024-06"));
    }

    @Test
    public void testGetRewardsByCustomerIdAndTransactionDate() {
        long customerId = 12345L;
        LocalDate startDate = LocalDate.of(2024, 4, 22);
        LocalDate endDate = LocalDate.of(2024, 6, 22);
        String basePath = "/retailer/api/transactions/v1/rewards/date/";

        Map<String, LocalDate> queryParams = new HashMap<>();
        queryParams.put("startDate", startDate);
        queryParams.put("endDate", endDate);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path(basePath + customerId);

        queryParams.forEach(builder::queryParam);

        String baseUrl = builder.toUriString();

        ResponseEntity<Map> getRewardsResponse = restTemplate.getForEntity(baseUrl, Map.class);
        assertEquals(HttpStatus.OK, getRewardsResponse.getStatusCode());
        assertNotNull(getRewardsResponse.getBody());
        assertEquals(2,getRewardsResponse.getBody().size());
        assertEquals(52,getRewardsResponse.getBody().get("MAY"));
    }

    @Test
    public void testGetRewardsByCustomerIdNotExistAndTransactionDate() {
        long customerId = 123L;
        LocalDate startDate = LocalDate.of(2024, 4, 22);
        LocalDate endDate = LocalDate.of(2024, 6, 22);
        String basePath = "/retailer/api/transactions/v1/rewards/date/";

        Map<String, LocalDate> queryParams = new HashMap<>();
        queryParams.put("startDate", startDate);
        queryParams.put("endDate", endDate);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path(basePath + customerId);

        queryParams.forEach(builder::queryParam);

        String baseUrl = builder.toUriString();

        ResponseEntity<Map> getRewardsResponse = restTemplate.getForEntity(baseUrl, Map.class);
        assertEquals(HttpStatus.NOT_FOUND, getRewardsResponse.getStatusCode());
        assertNull(getRewardsResponse.getBody());
    }


}


