package com.retailer.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "trans_id", nullable = false)
    @JsonProperty("trans_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long transId;

    @Column(name = "customer_id", nullable = false)
    @JsonProperty("customer_id")
    private Long customerId;

    @Column(name = "amount", nullable = false)
    @JsonProperty("amount")
    private double amount;

    @Column(name = "transaction_date", nullable = false)
    @JsonProperty("transaction_date")
    private LocalDate transactionDate;

}
