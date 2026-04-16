package me.niteshh.OPPs.tutorial.exceptionHandling.lecturePractice;

import java.io.*;

/**
 * THROW, THROWS, AND FINALLY - Complete Guide
 * 
 * This file explains:
 * 1. THROW - Forcefully throwing an exception
 * 2. THROWS - Declaring that method might throw exception
 * 3. FINALLY - Code that always executes (for cleanup)
 * 4. TRY-WITH-RESOURCES - Automatic resource cleanup (Java 7+)
 */

public class Throw_Throws {

    // ========== ORIGINAL CODE - KEPT SAFE ==========
    
    /** When we want to forcefully throw an exception then we use this throw keyword
     *  And also remember that we use "throws" in the method signature of caller  */

    static void main() {  // this main() method is not been in use
        try {
            throw new IllegalArgumentException("This is a forced exception");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught an exception: " + e.getMessage());
        }

        System.out.println(divide(10, 0)); // This will return -1 due to exception handling
    }

    public static int divide(int a, int b){
        try{
            int result = a/b;
            return result;
        } catch (Exception e) {
            return -1;
        }finally{
            System.out.println("Finally block executed in divide method");
        }
    }

    // ========== DETAILED FINALLY EXAMPLES ==========

    /**
     * FINALLY BLOCK - What is it and why use it?
     * 
     * What is FINALLY?
     * - A block that ALWAYS executes after try-catch
     * - Runs whether exception happens or not
     * - Runs even if catch block has return statement
     * - Used for cleanup (close files, release resources)
     * 
     * Why use FINALLY?
     * - Guarantee that cleanup code runs
     * - Prevent resource leaks (file handles, connections)
     * - Ensure database connections are closed
     * - Make sure files are closed properly
     * - Reset state if something goes wrong
     * 
     * When to use FINALLY?
     * - When you open files or streams
     * - When you connect to database
     * - When you acquire locks or resources
     * - When you need guaranteed cleanup
     */

    // Example 1: Basic Finally - Simple Understanding
    static void example1_BasicFinally() {
        System.out.println("\n--- Example 1: Basic Finally ---");

        try {
            System.out.println("Try block: Opening resource");
            System.out.println("Try block: Using resource");
            System.out.println("Try block: Data processed");
            
        } catch (Exception e) {
            System.out.println("Catch block: Exception occurred");
            
        } finally {
            // This ALWAYS runs
            System.out.println("Finally block: Closing resource");
            System.out.println("Finally block: Cleanup done");
        }

        System.out.println("Program continues...\n");
    }

    // Example 2: Finally with Exception
    static void example2_FinallyWithException() {
        System.out.println("--- Example 2: Finally with Exception ---");

        try {
            System.out.println("Try block: Starting operation");
            
            // This throws exception
            int result = 10 / 0;
            System.out.println("This line never executes (after exception)");
            
        } catch (ArithmeticException e) {
            System.out.println("Catch block: Exception caught - " + e.getMessage());
            
        } finally {
            // Even though exception occurred, this still runs
            System.out.println("Finally block: Still executes even with exception!");
            System.out.println("Finally block: Resources cleaned up");
        }

        System.out.println("Program continues...\n");
    }

    // Example 3: Finally with Return Statement
    static void example3_FinallyWithReturn() {
        System.out.println("--- Example 3: Finally with Return Statement ---");

        int result = methodWithReturnAndFinally();
        System.out.println("Method returned: " + result);
        System.out.println();
    }

    static int methodWithReturnAndFinally() {
        try {
            System.out.println("Try block: Calculating value");
            int value = 42;
            System.out.println("Try block: Value = " + value);
            
            // Even though we return here, finally STILL runs first
            return value;
            
        } catch (Exception e) {
            System.out.println("Catch block: " + e.getMessage());
            return -1;
            
        } finally {
            // IMPORTANT: This runs BEFORE the return statement
            System.out.println("Finally block: Executes BEFORE return");
            System.out.println("Finally block: Cleanup code runs first");
        }
    }

    // Example 4: Finally for Resource Cleanup (File Handling)
    static void example4_FinallyForFileCleanup() {
        System.out.println("--- Example 4: Finally for File Cleanup ---");

        // OLD WAY: Manual cleanup in finally
        StringReader reader = null;

        try {
            System.out.println("Try: Opening file...");
            reader = new StringReader("File content here");
            System.out.println("Try: Reading file...");
            System.out.println("Try: Data = " + reader.toString());

        } catch (Exception e) {
            System.out.println("Catch: Error reading file - " + e.getMessage());

        } finally {
            // This ensures file is always closed
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("Finally: File closed safely");
                } catch (Exception e) {
                    System.out.println("Finally: Error closing file - " + e.getMessage());
                }
            }
        }

        System.out.println();
    }

    // Example 5: Try-With-Resources (NEW WAY - Java 7+)
    static void example5_TryWithResources() {
        System.out.println("--- Example 5: Try-With-Resources (Java 7+) ---");

        // NEW WAY: Automatic cleanup (BETTER!)
        try (StringReader reader = new StringReader("File content here")) {
            System.out.println("Try-with-resources: File opened automatically");
            System.out.println("Try-with-resources: Reading data...");
            System.out.println("Try-with-resources: Data = " + reader.toString());
            
        } catch (Exception e) {
            System.out.println("Catch: Error - " + e.getMessage());
        }
        
        // File automatically closed here - no finally needed!
        System.out.println("Try-with-resources: File closed automatically");
        System.out.println();
    }

    // Example 6: Database Connection Simulation
    static void example6_DatabaseConnectionCleanup() {
        System.out.println("--- Example 6: Database Connection Cleanup ---");

        DatabaseConnection conn = null;

        try {
            System.out.println("Try: Opening database connection...");
            conn = new DatabaseConnection();
            conn.open();

            System.out.println("Try: Executing query...");
            conn.executeQuery("SELECT * FROM users");

            System.out.println("Try: Query successful");

        } catch (Exception e) {
            System.out.println("Catch: Database error - " + e.getMessage());

        } finally {
            // CRITICAL: Always close database connection
            if (conn != null) {
                System.out.println("Finally: Closing database connection...");
                conn.close();
                System.out.println("Finally: Connection closed safely");
            }
        }

        System.out.println();
    }

    // Example 7: Nested Try-Finally
    static void example7_NestedTryFinally() {
        System.out.println("--- Example 7: Nested Try-Finally ---");

        try {
            System.out.println("Outer try: Starting");

            try {
                System.out.println("  Inner try: Processing");
                System.out.println("  Inner try: Done");

            } catch (Exception e) {
                System.out.println("  Inner catch: " + e.getMessage());

            } finally {
                System.out.println("  Inner finally: Inner cleanup");
            }

            System.out.println("Outer try: Continuing");

        } catch (Exception e) {
            System.out.println("Outer catch: " + e.getMessage());

        } finally {
            System.out.println("Outer finally: Outer cleanup");
        }

        System.out.println();
    }

    // Example 8: Multiple Resources with Try-With-Resources
    static void example8_MultipleResources() {
        System.out.println("--- Example 8: Multiple Resources ---");

        // Multiple resources: both closed automatically
        try (
            StringReader reader = new StringReader("Input data");
            StringWriter writer = new StringWriter()
        ) {
            System.out.println("Opening input reader...");
            System.out.println("Opening output writer...");

            String data = reader.toString();
            writer.write("Processed: " + data);

            System.out.println("Both resources working together");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Both resources automatically closed");
        System.out.println();
    }

    // Example 9: Finally in Loop (when you need cleanup multiple times)
    static void example9_FinallyInLoop() {
        System.out.println("--- Example 9: Finally in Loop ---");

        for (int i = 1; i <= 3; i++) {
            try {
                System.out.println("Iteration " + i + ": Processing item " + i);

                if (i == 2) {
                    throw new Exception("Error on item 2");
                }

                System.out.println("Iteration " + i + ": Item processed");

            } catch (Exception e) {
                System.out.println("Iteration " + i + ": Caught - " + e.getMessage());

            } finally {
                System.out.println("Iteration " + i + ": Cleanup done");
            }
        }

        System.out.println();
    }

    // ========== TIPS AND BEST PRACTICES ==========

    /**
     * TIPS FOR USING FINALLY EFFECTIVELY:
     * 
     * TIP 1: Always close resources in finally
     *        finally { if (resource != null) resource.close(); }
     * 
     * TIP 2: Use try-with-resources instead (Java 7+)
     *        try (Resource r = new Resource()) { ... }
     *        Cleaner and automatic closing
     * 
     * TIP 3: Keep try block small
     *        Only risky code in try, setup outside
     *        This makes finally cleanup simpler
     * 
     * TIP 4: Handle close exceptions properly
     *        finally { try { resource.close(); } catch {} }
     *        Don't let close() exception hide original error
     * 
     * TIP 5: Finally always runs (except system exit)
     *        Even if catch has return statement
     *        Even if exception in try block
     *        This makes it perfect for cleanup
     * 
     * TIP 6: Don't use finally for normal code logic
     *        It's for cleanup, not for business logic
     *        Don't throw exceptions from finally
     * 
     * TIP 7: Multiple resources? Use try-with-resources
     *        try (R1 r1 = new R1(); R2 r2 = new R2()) { ... }
     *        Closed in reverse order automatically
     * 
     * TIP 8: Check before closing
     *        if (resource != null) resource.close()
     *        Resource might not be initialized if exception in try
     */

    // Demo method showing all tips
    static void demonstrateAllTips() {
        System.out.println("--- Demonstrating All Tips ---\n");

        // TIP 1,2,7: Use try-with-resources (best practice)
        System.out.println("TIP 1-2-7: Using try-with-resources");
        try (StringReader reader = new StringReader("data")) {
            System.out.println("  Reading: " + reader.toString());
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
        System.out.println("  Automatically closed!\n");

        // TIP 3: Keep try block small
        System.out.println("TIP 3: Keep try block small");
        String data = null; // Setup outside try
        try {
            data = "processed data"; // Only risky code
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        } finally {
            System.out.println("  Cleanup: " + data + "\n");
        }

        // TIP 4-5-8: Handle exceptions properly
        System.out.println("TIP 4-5-8: Proper exception handling");
        StringReader reader2 = null;
        try {
            reader2 = new StringReader("test");
            System.out.println("  Data: " + reader2.toString());
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        } finally {
            // TIP 8: Check before closing
            if (reader2 != null) {
                try {
                    // TIP 4: Handle close exception properly
                    reader2.close();
                    System.out.println("  Closed safely\n");
                } catch (Exception e) {
                    System.out.println("  Error closing: " + e.getMessage() + "\n");
                }
            }
        }

        // TIP 6: Don't use finally for business logic
        System.out.println("TIP 6: Finally is for cleanup only");
        try {
            System.out.println("  Processing business logic");
        } finally {
            System.out.println("  Cleanup (not business logic)\n");
        }
    }

    // ========== REAL-WORLD SCENARIOS ==========

    // Scenario 1: Reading configuration file
    static String readConfigFile(String filename) {
        BufferedReader reader = null;
        try {
            System.out.println("Opening config file: " + filename);
            reader = new BufferedReader(new StringReader("config=value"));
            return reader.toString();

        } catch (Exception e) {
            System.out.println("Error reading config: " + e.getMessage());
            return "default_config";

        } finally {
            // Always close the file
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }

    // Scenario 2: Database transaction
    static void databaseTransaction() {
        DatabaseConnection conn = null;
        try {
            System.out.println("Starting transaction...");
            conn = new DatabaseConnection();
            conn.open();
            conn.executeQuery("INSERT INTO table VALUES (...)");
            conn.commit();
            System.out.println("Transaction committed");

        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
            if (conn != null) {
                conn.rollback(); // Undo changes
            }

        } finally {
            // Always close connection
            if (conn != null) {
                conn.close();
            }
        }
    }

    // ========== HELPER CLASSES ==========

    // Simple database connection simulator
    static class DatabaseConnection {
        private boolean isOpen = false;

        void open() {
            this.isOpen = true;
            System.out.println("  [DB] Connection opened");
        }

        void close() {
            this.isOpen = false;
            System.out.println("  [DB] Connection closed");
        }

        void executeQuery(String query) throws Exception {
            if (!isOpen) throw new Exception("Connection not open");
            System.out.println("  [DB] Executing: " + query);
        }

        void commit() {
            System.out.println("  [DB] Changes committed");
        }

        void rollback() {
            System.out.println("  [DB] Changes rolled back");
        }
    }

    // ========== MAIN TO RUN EXAMPLES ==========

    public static void main(String[] args) {
        System.out.println("===== FINALLY AND TRY-WITH-RESOURCES EXAMPLES =====\n");

        example1_BasicFinally();
        example2_FinallyWithException();
        example3_FinallyWithReturn();
        example4_FinallyForFileCleanup();
        example5_TryWithResources();
        example6_DatabaseConnectionCleanup();
        example7_NestedTryFinally();
        example8_MultipleResources();
        example9_FinallyInLoop();

        System.out.println("===== TIPS AND BEST PRACTICES =====\n");
        demonstrateAllTips();

        System.out.println("===== REAL-WORLD SCENARIOS =====\n");
        System.out.println("Scenario 1: Reading config file");
        String config = readConfigFile("app.properties");
        System.out.println("Config: " + config + "\n");

        System.out.println("Scenario 2: Database transaction");
        databaseTransaction();
    }
}
