CREATE DATABASE ecommerce_analytics;

USE ecommerce_analytics;

CREATE TABLE traffic_summary (
    id INT AUTO_INCREMENT PRIMARY KEY,
    run_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_parsed INT,
    chrome_users INT,
    firefox_users INT,
    credit_card_tx INT,
    cash_tx INT,
    execution_time_ms INT
);


