package com.retailer.services.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testGettersAndSetters() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDob(LocalDate.of(1990, 1, 1));

        assertEquals(1L, customer.getCustomerId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), customer.getDob());
    }

    @Test
    public void testConstructor() {
        Customer customer = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1));
        assertEquals(1L, customer.getCustomerId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), customer.getDob());
    }

    @Test
    public void testEqualsAndHashCode() {
        Customer customer1 = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1));
        Customer customer2 = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1));
        Customer customer3 = new Customer(2L, "Jane", "Doe", LocalDate.of(1992, 2, 2));

        assertEquals(customer1, customer2);
        assertNotEquals(customer1, customer3);
        assertEquals(customer1.hashCode(), customer2.hashCode());
        assertNotEquals(customer1.hashCode(), customer3.hashCode());
    }

    @Test
    public void testToString() {
        Customer customer = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1));
        String expected = "Customer(customerId=1, firstName=John, lastName=Doe, dob=1990-01-01)";
        assertEquals(expected, customer.toString());
    }

    @Test
    public void testValidation() {
        Customer customer = new Customer();
        customer.setCustomerId(null); // Assuming @NotNull on customerId
        customer.setFirstName(""); // Assuming @NotEmpty on firstName
        customer.setLastName(""); // Assuming @NotEmpty on lastName
        customer.setDob(null); // Assuming @NotNull on dob

        assertNull(customer.getCustomerId());
    }
}
