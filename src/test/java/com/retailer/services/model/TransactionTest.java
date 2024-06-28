package com.retailer.services.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testGettersAndSetters() {
        Transaction transaction = new Transaction();
        transaction.setTransId(1L);
        transaction.setCustomerId(101L);
        transaction.setAmount(100.0);
        transaction.setTransactionDate(LocalDate.of(2024, 6, 28));

        assertEquals(1L, transaction.getTransId());
        assertEquals(101L, transaction.getCustomerId());
        assertEquals(100.0, transaction.getAmount());
        assertEquals(LocalDate.of(2024, 6, 28), transaction.getTransactionDate());
    }

    @Test
    public void testConstructor() {
        Transaction transaction = new Transaction(1L, 101L, 100.0, LocalDate.of(2024, 6, 28));

        assertEquals(1L, transaction.getTransId());
        assertEquals(101L, transaction.getCustomerId());
        assertEquals(100.0, transaction.getAmount());
        assertEquals(LocalDate.of(2024, 6, 28), transaction.getTransactionDate());
    }


    @Test
    public void testEqualsAndHashCode() {
        Transaction transaction1 = new Transaction(1L, 101L, 100.0, LocalDate.of(2024, 6, 28));
        Transaction transaction2 = new Transaction(1L, 101L, 100.0, LocalDate.of(2024, 6, 28));
        Transaction transaction3 = new Transaction(2L, 102L, 200.0, LocalDate.of(2024, 6, 29));

        assertEquals(transaction1, transaction2);
        assertNotEquals(transaction1, transaction3);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
        assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

    @Test
    public void testToString() {
        Transaction transaction = new Transaction(1L, 101L, 100.0, LocalDate.of(2024, 6, 28));
        String expected = "Transaction(transId=1, customerId=101, amount=100.0, transactionDate=2024-06-28)";
        assertEquals(expected, transaction.toString());
    }

}
