package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * STEP 2: TRY-CATCH-FINALLY MECHANISM
 * 
 * The complete exception handling structure:
 * 
 * try {
 *     // Code that might throw an exception
 * } catch (SpecificException e) {
 *     // Handle specific exception
 * } catch (AnotherException e) {
 *     // Handle another exception
 * } finally {
 *     // Code that ALWAYS executes (cleanup code)
 * }
 * 
 * TRY Block:
 * - Contains risky code that might throw exceptions
 * - If exception occurs, control jumps to catch block
 * - If no exception, catch blocks are skipped
 * 
 * CATCH Block:
 * - Handles specific exception types
 * - Multiple catch blocks allowed for different exception types
 * - Order matters: specific exceptions before general ones
 * - Only ONE catch block executes for a single exception
 * 
 * FINALLY Block:
 * - Executes regardless of whether exception occurs or not
 * - Used for cleanup (closing files, database connections, etc.)
 * - Optional but highly recommended
 * - Executes even if catch block has return statement
 */

import java.io.*;

public class Step2_TryCatchFinally {

    public static void main(String[] args) {
        System.out.println("=== TRY-CATCH-FINALLY MECHANISM ===\n");
        
        // Example 1: Basic try-catch-finally
        System.out.println("Example 1: Basic try-catch-finally structure");
        try {
            System.out.println("Inside try block - executing risky code");
            int result = 20 / 2; // No exception, normal execution
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Inside catch block - exception caught");
        } finally {
            System.out.println("Inside finally block - ALWAYS EXECUTES");
            System.out.println("Perfect place for cleanup operations\n");
        }
        
        System.out.println("=".repeat(60) + "\n");
        
        // Example 2: try-catch with exception occurrence
        System.out.println("Example 2: Exception occurs and is caught");
        try {
            System.out.println("Attempting risky operation...");
            int result = 50 / 0; // This WILL throw ArithmeticException
            System.out.println("This line won't execute"); // Skipped
        } catch (ArithmeticException e) {
            System.out.println("Exception caught! Message: " + e.getMessage());
            System.out.println("Handled gracefully, program continues...\n");
        } finally {
            System.out.println("Finally block always executes - cleanup done\n");
        }
        
        System.out.println("=".repeat(60) + "\n");
        
        // Example 3: Multiple catch blocks (specific to general order)
        System.out.println("Example 3: Multiple catch blocks");
        try {
            String[] names = {"Alice", "Bob"};
            System.out.println("Accessing index 0: " + names[0]); // OK
            int number = Integer.parseInt(names[0]); // NumberFormatException
            System.out.println("Number: " + number);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught: ArrayIndexOutOfBoundsException - " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Caught: NumberFormatException - Value 'Alice' cannot be converted to int");
        } catch (Exception e) {
            // General exception - catches any other exception
            System.out.println("Caught: Generic Exception - " + e.getMessage());
        } finally {
            System.out.println("Finally: Cleanup resources\n");
        }
        
        System.out.println("=".repeat(60) + "\n");
        
        // Example 4: Finally block executes even with return statement
        System.out.println("Example 4: Finally with return statement");
        int value = methodWithReturnStatement();
        System.out.println("Returned value: " + value + "\n");
        
        System.out.println("=".repeat(60) + "\n");
        
        // Example 5: Realistic file handling scenario
        System.out.println("Example 5: Realistic file handling (simulated)");
        fileHandlingSimulation();
        
        System.out.println("=".repeat(60) + "\n");
        
        // Example 6: Nested try-catch-finally
        System.out.println("Example 6: Nested try-catch-finally");
        try {
            System.out.println("Outer try block");
            try {
                System.out.println("Inner try block");
                int result = 100 / 1; // No exception
                System.out.println("Inner calculation: " + result);
            } catch (ArithmeticException e) {
                System.out.println("Inner catch: " + e.getMessage());
            } finally {
                System.out.println("Inner finally");
            }
        } catch (Exception e) {
            System.out.println("Outer catch: " + e.getMessage());
        } finally {
            System.out.println("Outer finally\n");
        }
        
        System.out.println("=".repeat(60) + "\n");
        
        // Example 7: Multi-catch block (Java 7+) - catching multiple exceptions in one block
        System.out.println("Example 7: Multi-catch block (Java 7+)");
        try {
            String[] data = {"10", "20", "30"};
            int number = Integer.parseInt(data[1]); // Valid
            int result = number / 1; // No exception
            System.out.println("Result: " + result);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // This single catch handles both exception types
            System.out.println("Caught either NumberFormat or ArrayIndex exception");
        } finally {
            System.out.println("Multi-catch finally block\n");
        }
        
        System.out.println("=".repeat(60) + "\n");
        System.out.println("✓ All examples completed successfully!");
    }
    
    // Helper method to demonstrate finally with return
    public static int methodWithReturnStatement() {
        try {
            System.out.println("Inside try block");
            return 42; // Returns value but finally still executes
        } catch (Exception e) {
            System.out.println("Inside catch block");
            return 0;
        } finally {
            System.out.println("Finally block executes BEFORE return (important!)");
        }
    }
    
    // Helper method for file handling simulation
    public static void fileHandlingSimulation() {
        // Simulating file operations without actual I/O
        FileResource resource = null;
        try {
            resource = new FileResource("example.txt");
            System.out.println("File resource opened: " + resource.getName());
            resource.process();
            System.out.println("File processing successful");
        } catch (IOException e) {
            System.out.println("IOException caught: " + e.getMessage());
        } finally {
            if (resource != null) {
                resource.close();
                System.out.println("Resource closed in finally block");
            }
        }
    }
    
    // Inner helper class for file simulation
    static class FileResource {
        private String name;
        private boolean isOpen = true;
        
        public FileResource(String name) throws IOException {
            if (name == null || name.isEmpty()) {
                throw new IOException("Invalid file name");
            }
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public void process() {
            // Simulate file processing
        }
        
        public void close() {
            isOpen = false;
        }
    }
}

/**
 * KEY CONCEPTS SUMMARY:
 * 
 * 1. TRY Block:
 *    - Contains code that might throw exceptions
 *    - Mandatory when using catch or finally
 *    - Execution stops immediately when exception occurs
 * 
 * 2. CATCH Block:
 *    - Optional (can have 0 or multiple)
 *    - Must come after try block
 *    - Parameter specifies exception type to handle
 *    - Order matters: specific exceptions before general ones
 * 
 * 3. FINALLY Block:
 *    - Optional (0 or 1)
 *    - Always executes (almost always - system crash exceptions)
 *    - Executes even if try/catch has return, break, continue
 *    - Best place for resource cleanup
 * 
 * 4. Execution Flow:
 *    Normal: try → finally → continue
 *    With exception: try → (exception) → catch → finally → continue
 *    With return: try/catch → finally → return
 * 
 * 5. Multi-catch (Java 7+):
 *    - Syntax: catch (Exception1 | Exception2 e)
 *    - Handles multiple exception types in one block
 *    - Improves code readability and reduces duplication
 * 
 * 6. Best Practices:
 *    ✓ Always use finally for resource cleanup
 *    ✓ Catch specific exceptions before general ones
 *    ✓ Don't ignore exceptions (always handle appropriately)
 *    ✓ Don't catch Exception/Throwable unless necessary
 *    ✓ Keep try blocks small and focused
 */

