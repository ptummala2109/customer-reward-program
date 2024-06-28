# Customer Rewards Program Application

## Description
A retailer exposes an API that provides the following functionalities:

1. Retrieve Customer Records: Allows retrieval of customer records.

2.  Calculate Reward Points: Calculates reward points for customers.

Initialization Process

When the application starts, it loads some sample records for testing purposes.

## How to Run
To run the application, execute the following steps:

1. Ensure Gradle is Pre-Installed
   - Gradle needs to be pre-installed on your machine.
2. Clone the repository (https://github.com/ptummala2109/customer-reward-program.git)
3. Navigate to the project directory
4. Build the project: `./gradlew clean build`
5. Run the application:  `./gradlew bootRun`

**Note:** Gradle should be able to auto-detect the Spring Boot application file ServiceImplApplication and start it on port 8080.

**Alternative: Using IntelliJ IDEA**
- If not using Gradle, open the application in IntelliJ IDEA.
- Navigate to the RewardTransactionApplication file under the com.retailer.services project.
- Right-click on RewardTransactionApplication and select Run.

## API Documentation

### Create a Transaction
- **URL:** `/api/transactions`
- **Method:** `POST`
- **Request Body:**
```json
{
    "trans_id": 1,
    "customer_id": 99999,
    "amount": 120.0,
    "transaction_date": "2024-06-22"
}
```

### Get All Transactions
- **URL:** `/api/transactions`
- **Method:** GET

### Get Transactions by CustomerId
- **URL:** `/api/transactions/customer/{customerId}`
- **Method:** GET
- **Response Body:** 
```json lines
[
    {
        "trans_id": 1,
        "customer_id": 99999,
        "amount": 120.0,
        "transaction_date": "2024-06-22"
    }
]
```

### Get Reward points for a Customer
- **URL:** `/api/transactions/rewards/{customerId}`
- **Method:** GET
- **Response Body:**
```json lines
{
    "2024-04": 0,
    "2024-06": 90,
    "2024-05": 0
}
```

### Get Reward points for a Customer between Transaction Date
- **URL:** `/api/transactions/rewards/date/{customerId}`
- **Request Parameters:** `startDate<LocalDate>` and `endDate<LocalDate>`
- **Method:** GET
- **Response Body:**
```json lines
{
    "JUNE": 0,
    "MAY": 52,
    "APRIL": 50
}
```

### Error Handling
Returns appropriate HTTP status codes (200, 400, 422, 500) with human-readable messages.

