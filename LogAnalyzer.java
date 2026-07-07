import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogAnalyzer {
    public static void main(String[] args) {
       // change file path accordingly
        String filePath = "C:\\Users\\SINDHUJA M\\OneDrive\\Documents\\intelligent-log-analyzer\\E-commerce Website Logs.csv"; 
        
        System.out.println("Starting Log Analyzer Engine...");
        
        long startTime = System.currentTimeMillis();
        int lineCount = 0;

        // Using try-with-resources to automatically close the file when done
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            
            // Read the file line by line until the end
            while ((currentLine = br.readLine()) != null) {
                lineCount++;
                
                // For today, let's just print the first 10 lines so your console doesn't explode
                if (lineCount <= 10) {
                    System.out.println("Line " + lineCount + ": " + currentLine);
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error reading the log file: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\n--- Scan Complete ---");
        System.out.println("Total lines processed: " + lineCount);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}
