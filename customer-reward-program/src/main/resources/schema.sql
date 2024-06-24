//DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer (
    customer_id BIGINT NOT NULL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    dob DATE NOT NULL
);

//DROP TABLE IF EXISTS transactions;
CREATE TABLE IF NOT EXISTS transactions (
    trans_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    CONSTRAINT FK_PMT_TRANS_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

//DROP TABLE IF EXISTS customer_rewards;
CREATE TABLE IF NOT EXISTS customer_rewards (
    rewards_id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    customer_id BIGINT NOT NULL,
    reward_points BIGINT NOT NULL,
    last_updated_dt TIMESTAMP NOT NULL,
    CONSTRAINT FK_REWARDS_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);