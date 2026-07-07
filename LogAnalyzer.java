import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// ==========================================
// 1. DATA ENCAPSULATION LAYER (OOPs Concept)
// ==========================================
class TransactionRecord {
    private final String browser;
    private final String paymentMethod;

    // Constructor to initialize our immutable object data
    public TransactionRecord(String browser, String paymentMethod) {
        this.browser = browser != null ? browser.trim() : "";
        this.paymentMethod = paymentMethod != null ? paymentMethod.trim() : "";
    }

    // Getters to safely access our private variables
    public String getBrowser() { return this.browser; }
    public String getPaymentMethod() { return this.paymentMethod; }
}

// ==========================================
// 2. ANALYTICS & STATE MANAGEMENT LAYER
// ==========================================
class MetricsRegistry {
    private int totalLogs = 0;
    private int chromeCount = 0;
    private int firefoxCount = 0;
    private int creditCardCount = 0;
    private int cashCount = 0;

    // Method to ingest an object and dynamically update state logic
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

    // Prints a structured dashboard report out to the console
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
        // Change this back to your true working dataset path!
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
                    // Instantiating our structural layout object block
                    TransactionRecord record = new TransactionRecord(tokens[5], tokens[14]);
                    
                    // Sending our formatted tracking bundle to the calculations register
                    metrics.registerTransaction(record);
                }
            }
            
        } catch (IOException e) {
            System.out.println("Critical Error in streaming engine pipeline: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        metrics.compileReport(endTime - startTime);
    }
}
