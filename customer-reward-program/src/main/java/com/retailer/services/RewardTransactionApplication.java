package com.retailer.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.retailer.services")
public class RewardTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardTransactionApplication.class, args);
	}

}
