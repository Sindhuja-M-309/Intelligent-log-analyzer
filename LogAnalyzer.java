import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// ==========================================
// 1. DATA ENCAPSULATION LAYER
// ==========================================
class TransactionRecord {
    private final String browser;
    private final String paymentMethod;

    public TransactionRecord(String browser, String paymentMethod) {
        this.browser = browser != null ? browser.trim() : "";
        this.paymentMethod = paymentMethod != null ? paymentMethod.trim() : "";
    }

    public String getBrowser() { return this.browser; }
    public String getPaymentMethod() { return this.paymentMethod; }
}

// ==========================================
// 2. ANALYTICS & STATE MANAGEMENT LAYER (With Database Integration)
// ==========================================
class MetricsRegistry {
    private int totalLogs = 0;
    private int chromeCount = 0;
    private int firefoxCount = 0;
    private int creditCardCount = 0;
    private int cashCount = 0;

    // Database Configuration - CHANGE PASSWORD TO MATCH YOUR LOCAL MYSQL SETTINGS
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce_analytics";
    private static final String DB_USER = "root";
    private static final String DB_PASS = ""; 

    public void registerTransaction(TransactionRecord record) {
        totalLogs++;

        if (record.getBrowser().equalsIgnoreCase("Chrome")) {
            chromeCount++;
        } else if (record.getBrowser().equalsIgnoreCase("Mozilla Firefox")) {
            firefoxCount++;
        }

        if (record.getPaymentMethod().equalsIgnoreCase("Credit Card")) {
            creditCardCount++;
        } else if (record.getPaymentMethod().equalsIgnoreCase("Cash")) {
            cashCount++;
        }
    }

    // New Method to push data down to your MySQL Server
    public void saveToDatabase(long executionTime) {
        String insertSQL = "INSERT INTO traffic_summary (total_parsed, chrome_users, firefox_users, credit_card_tx, cash_tx, execution_time_ms) VALUES (?, ?, ?, ?, ?, ?)";

        System.out.println("\nConnecting to database pipeline...");
        
        // Establishing connection using try-with-resources for defensive cleanup
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            // Binding values safely to prevent SQL Injection vulnerabilities
            pstmt.setInt(1, totalLogs);
            pstmt.setInt(2, chromeCount);
            pstmt.setInt(3, firefoxCount);
            pstmt.setInt(4, creditCardCount);
            pstmt.setInt(5, cashCount);
            pstmt.setLong(6, executionTime);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Metrics successfully committed to MySQL Database!");
            }

        } catch (SQLException e) {
            System.out.println("❌ Database connectivity error occurred: " + e.getMessage());
        }
    }

    public void compileReport(long executionTime) {
        System.out.println("==================================================");
        System.out.println("       REAL-TIME TRAFFIC & TRANSACTION REPORT     ");
        System.out.println("==================================================");
        System.out.println(" Total Transactions Parsed : " + totalLogs);
        System.out.println(" Time Taken Execution      : " + executionTime + " ms");
        System.out.println("--------------------------------------------------");
        System.out.println(" 🌐 BROWSER DISTRIBUTION:");
        System.out.println("   - Google Chrome Users   : " + chromeCount);
        System.out.println("   - Mozilla Firefox Users : " + firefoxCount);
        System.out.println("--------------------------------------------------");
        System.out.println(" 💳 PAYMENT METHOD DISTRIBUTION:");
        System.out.println("   - Credit Card Checkout  : " + creditCardCount);
        System.out.println("   - Cash On Delivery      : " + cashCount);
        System.out.println("==================================================");
    }
}

// ==========================================
// 3. MAIN RUNTIME CONTROLLER
// ==========================================
public class LogAnalyzer {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\SINDHUJA M\\OneDrive\\Documents\\intelligent-log-analyzer\\E-commerce Website Logs.csv"; 
        
        System.out.println("Initializing Object-Oriented Log Processor Engine...");
        System.out.println("Streaming target files safely...\n");
        
        long startTime = System.currentTimeMillis();
        MetricsRegistry metrics = new MetricsRegistry();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            int currentLineNumber = 0;
            
            while ((currentLine = br.readLine()) != null) {
                currentLineNumber++;
                
                if (currentLineNumber == 1 && (currentLine.contains("Date") || currentLine.contains("Time"))) {
                    continue;
                }

                String[] tokens = currentLine.split(",");
                
                if (tokens.length >= 15) {
                    TransactionRecord record = new TransactionRecord(tokens[5], tokens[14]);
                    metrics.registerTransaction(record);
                }
            }
            
        } catch (IOException e) {
            System.out.println("Critical Error in streaming engine pipeline: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        // Print to console screen
        metrics.compileReport(timeTaken);
        
        // Persist records into storage
        metrics.saveToDatabase(timeTaken);
    }
}
