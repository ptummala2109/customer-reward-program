package com.retailer.services.repository;


import com.retailer.services.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //No custom implementation required here. This is to save customer record.
}
