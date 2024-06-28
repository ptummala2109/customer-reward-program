package com.retailer.services.repository;

import com.retailer.services.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSaveCustomer() {
        // Create a new customer object
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDob(LocalDate.of(1990, 1, 1));

        // Save the customer using the repository
        Customer savedCustomer = customerRepository.save(customer);

        // Verify that the customer is saved correctly
        assertNotNull(savedCustomer.getCustomerId());
        assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customer.getLastName(), savedCustomer.getLastName());
        assertEquals(customer.getDob(), savedCustomer.getDob());
    }

    @Test
    public void testFindCustomerById() {
        // Create a new customer object and save it
        Customer customer = new Customer(1L, "John", "Doe", LocalDate.of(1990, 1, 1));
        customerRepository.save(customer);

        // Retrieve the customer from the repository by ID
        Optional<Customer> foundCustomer = customerRepository.findById(1L);

        // Verify that the customer is found and attributes match
        assertTrue(foundCustomer.isPresent());
        assertEquals(customer.getCustomerId(), foundCustomer.get().getCustomerId());
        assertEquals(customer.getFirstName(), foundCustomer.get().getFirstName());
        assertEquals(customer.getLastName(), foundCustomer.get().getLastName());
        assertEquals(customer.getDob(), foundCustomer.get().getDob());
    }

}
