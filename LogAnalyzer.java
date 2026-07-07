import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogAnalyzer {
    public static void main(String[] args) {
        // Path to your dataset file
        String filePath = "C:\\Users\\SINDHUJA M\\OneDrive\\Documents\\intelligent-log-analyzer\\E-commerce Website Logs.csv"; 
        
        System.out.println("Starting Log Analyzer Engine...\n");
        
        long startTime = System.currentTimeMillis();
        int lineCount = 0;
        int errorCount = 0;
        int infoCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            
            // Read the file line by line
            while ((currentLine = br.readLine()) != null) {
                lineCount++;
                
                // 🔍 DIAGNOSTIC BLOCK: Force print raw lines 2, 3, and 4 to inspect the structure
                if (lineCount >= 2 && lineCount <= 4) {
                    System.out.println("--- DIAGNOSTIC DATA FOR LINE " + lineCount + " ---");
                    System.out.println("RAW TEXT: ->" + currentLine + "<-");
                    
                    String[] debugTokens = currentLine.split(",");
                    System.out.println("Splitting by COMMA gives: " + debugTokens.length + " pieces.");
                    
                    for (int i = 0; i < debugTokens.length; i++) {
                        System.out.println("  Piece [" + i + "]: " + debugTokens[i]);
                    }
                    System.out.println("----------------------------------------\n");
                }

                // Analytical processing logic
                String[] tokens = currentLine.split(",");
                if (tokens.length >= 2) {
                    String logLevel = tokens[1].trim(); 
                    
                    if (logLevel.equalsIgnoreCase("ERROR")) {
                        errorCount++;
                    } else if (logLevel.equalsIgnoreCase("INFO")) {
                        infoCount++;
                    }
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error reading the log file: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        
        // Final analytics summary report
        System.out.println("--- Real-Time Analytics Report ---");
        System.out.println("Total Logs Processed: " + lineCount);
        System.out.println("Total Errors Found: " + errorCount);
        System.out.println("Total Info Logs Found: " + infoCount);
        System.out.println("\n--- Scan Complete ---");
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}
