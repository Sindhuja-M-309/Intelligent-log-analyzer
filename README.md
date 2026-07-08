# High-Throughput E-Commerce Log Analyzer

A high-performance Java processing engine designed to parse, validate, and aggregate metrics from massive transactional datasets. The system ingests and processes log streams at scale, decoupling processing logic from persistence layers. It features a fault-tolerant design that dynamically persists traffic dashboards to a local file system ledger, with native support for optional relational database syncing via MySQL/JDBC.

## Runtime Architecture Overview

The system processes large operational datasets with low memory overhead by maintaining an entirely streaming architecture. Rather than loading massive execution buffers into heap memory, it applies sequential tokenization to data streams instantly.



[Java Runtime Engine]──(Streams Stream)   ──> [Memory-Efficient BufferedReader]

│

(String Tokenization)

│

▼

[MySQL Relational Storage] <──(JDBC Sink)─── [Metrics Registry] ───(File IO Sink)──>[analytics_report.txt]

---

- **Memory Efficiency:** Uses structured streaming abstractions (`BufferedReader`) to maintain a flat, predictable memory footprint regardless of file scale.
- **Decoupled System Design:** Separates structural entity parsing (`TransactionRecord`) from metric calculations and aggregation registries (`MetricsRegistry`) to support straightforward modifications.
- **Resilient Multi-Persistence Sinks:** Implements a dual-sink architecture. Aggregate summaries write immediately to local physical files (`FileWriter`), while secondary database transmission handles exceptions gracefully without causing application crashes.

---

## Technical Specifications & Environment

- **Core Engine:** Java (JDK 8 or higher)
- **Primary Persistence Sink:** Local File System (IO Text Append Logging)
- **Secondary Persistence Sink:** MySQL Engine 8.0+ / Relational Schema
- **Data Connectivity Layer:** JDBC (MySQL Connector/J)

---

## Database Configuration (Optional Syncing Mode)

If you intend to capture logs inside a relational engine alongside local text backups, prepare your schema instance using the following initialization structure:

```sql
CREATE DATABASE IF NOT EXISTS ecommerce_analytics;
USE ecommerce_analytics;

CREATE TABLE IF NOT EXISTS traffic_summary (
    id INT AUTO_INCREMENT PRIMARY KEY,
    run_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_parsed INT NOT NULL,
    chrome_users INT NOT NULL,
    firefox_users INT NOT NULL,
    credit_card_tx INT NOT NULL,
    cash_tx INT NOT NULL,
    execution_time_ms INT NOT NULL
);
```

---


## Execution Guide

### 1. Compile the Source Code
Compile the core controller using explicit UTF-8 encoding strings to protect character formatting across terminal interfaces:

```bash
javac -encoding utf-8 LogAnalyzer.java
```

### 2. Standalone Processing Mode (File System Only)
To execute the engine using zero-dependency, local file logging (fully isolated from database setups):

```Bash
java LogAnalyzer
```

### 3. Integrated Pipeline Mode (File + MySQL Syncing)
To link the engine to your working relational network, supply your project environment with the official MySQL driver bin assembly and execute using the application class path flag:

```Bash
java -cp ".;mysql-connector-j-8.x.x.jar" LogAnalyzer
```

Benchmarked Production Metrics
During validation benchmarks executing across full enterprise operational logs, the engine recorded the following output performance indicators:

```Plaintext
Initializing High-Throughput Log Processor Engine...
Streaming target files safely...

==================================================
       REAL-TIME TRAFFIC & TRANSACTION REPORT     
==================================================
 Total Transactions Parsed : 172839
 Time Taken Execution      : 248 ms
--------------------------------------------------
 🌐 BROWSER DISTRIBUTION:
   - Google Chrome Users   : 28254
   - Mozilla Firefox Users : 26592
--------------------------------------------------
 💳 PAYMENT METHOD DISTRIBUTION:
   - Credit Card Checkout  : 53031
   - Cash On Delivery      : 72670
==================================================

Writing metrics to local persistence ledger...
✅ Metrics successfully committed to history ledger: analytics_report.txt
Connecting to database pipeline...
✅ Metrics successfully committed to MySQL Database!
```

---


Save that file as **`README.md`** inside your `intelligent-log-analyzer` folder, commit it to GitHub alongside your `LogAnalyzer.java` code, and your project documentation is officially flawless!
