import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogAnalyzer {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\SINDHUJA M\\OneDrive\\Documents\\intelligent-log-analyzer\\E-commerce Website Logs.csv"; 
        
        System.out.println("Starting Intelligent E-Commerce Traffic Analyzer...");
        System.out.println("Processing streams... please wait.\n");
        
        long startTime = System.currentTimeMillis();
        int lineCount = 0;
        
        // Analytical Counters
        int chromeCount = 0;
        int firefoxCount = 0;
        int creditCardCount = 0;
        int cashCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            
            while ((currentLine = br.readLine()) != null) {
                lineCount++;
                
                // we should skip the header row if the file contains column names
                if (lineCount == 1 && (currentLine.contains("Date") || currentLine.contains("Time"))) {
                    continue;
                }

                String[] tokens = currentLine.split(",");
                
                //to ensure the line has all 15 columns to prevent index errors
                if (tokens.length >= 15) {
                    
                    // 1. Analyze Web Browser (Column index 5)
                    String browser = tokens[5].trim();
                    if (browser.equalsIgnoreCase("Chrome")) {
                        chromeCount++;
                    } else if (browser.equalsIgnoreCase("Mozilla Firefox")) {
                        firefoxCount++;
                    }
                    
                    // 2. Analyze Payment Method (Column index 14)
                    String paymentMethod = tokens[14].trim();
                    if (paymentMethod.equalsIgnoreCase("Credit Card")) {
                        creditCardCount++;
                    } else if (paymentMethod.equalsIgnoreCase("Cash")) {
                        cashCount++;
                    }
                }
            }
            
        } catch (IOException e) {
            System.out.println("Critical Error reading the log engine: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        
        //Metrics Summary
        System.out.println("==================================================");
        System.out.println("       REAL-TIME TRAFFIC & TRANSACTION REPORT     ");
        System.out.println("==================================================");
        System.out.println(" Total Transactions Parsed : " + lineCount);
        System.out.println(" Time Taken Execution      : " + (endTime - startTime) + " ms");
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
