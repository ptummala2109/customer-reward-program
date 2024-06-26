package com.retailer.services.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name = "customer_id", nullable = false)
    @JsonProperty("customer_id")
    private Long customerId;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "dob", nullable = false)
    @JsonProperty("dob")
    private LocalDate dob;
}
