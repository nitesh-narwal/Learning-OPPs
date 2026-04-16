package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * STEP 3: CHECKED vs UNCHECKED EXCEPTIONS
 * 
 * CHECKED EXCEPTIONS:
 * - Compiler forces you to handle them (compile-time checking)
 * - Must be caught using try-catch OR declared in method signature using throws
 * - Examples: IOException, SQLException, FileNotFoundException, ClassNotFoundException
 * - Used for recoverable conditions
 * - Checked at compile time
 * 
 * UNCHECKED EXCEPTIONS (Runtime Exceptions):
 * - Compiler doesn't force you to handle them
 * - Optional to catch or declare
 * - Extend RuntimeException
 * - Examples: NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException
 * - Usually caused by programming errors
 * - Checked at runtime
 * 
 * Hierarchy:
 *            Throwable
 *           /        \
 *        Error      Exception
 *                    |
 *         RuntimeException (Unchecked)
 *         
 * Other Exceptions from Exception (Checked)
 */

import java.io.*;

public class Step3_CheckedVsUnchecked {

    public static void main(String[] args) {
        System.out.println("=== CHECKED vs UNCHECKED EXCEPTIONS ===\n");
        
        // Example 1: Unchecked Exception (ArithmeticException)
        System.out.println("Example 1: Unchecked Exception - ArithmeticException");
        System.out.println("No try-catch needed, but good practice to use it");
        try {
            int result = 100 / 0; // Unchecked - compiler doesn't force handling
        } catch (ArithmeticException e) {
            System.out.println("Caught unchecked exception: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 2: Unchecked Exception (NullPointerException)
        System.out.println("Example 2: Unchecked Exception - NullPointerException");
        try {
            String text = null;
            int length = text.length(); // Unchecked - won't compile-time error
        } catch (NullPointerException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 3: Checked Exception - MUST handle or declare
        System.out.println("Example 3: Checked Exception - FileNotFoundException");
        System.out.println("Compiler FORCES you to handle or declare this exception");
        demonstrateCheckedExceptionHandling();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 4: Checked Exception with throws declaration
        System.out.println("Example 4: Using throws keyword to declare checked exception");
        try {
            demonstrateThrowsDeclaration();
        } catch (IOException e) {
            System.out.println("Caught in main: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 5: Common Unchecked Exceptions
        System.out.println("Example 5: Common Unchecked Exceptions");
        demonstrateCommonUncheckedExceptions();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 6: Common Checked Exceptions
        System.out.println("Example 6: Common Checked Exceptions (must be handled)");
        demonstrateCommonCheckedExceptions();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 7: Chaining checked exceptions
        System.out.println("Example 7: Chaining exceptions");
        try {
            methodThatThrowsCheckedException();
        } catch (IOException e) {
            System.out.println("Caught chained exception: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        System.out.println("✓ All exception type examples completed!");
    }
    
    /**
     * Method demonstrating checked exception handling with try-catch
     * Checked exceptions MUST be handled or declared
     */
    static void demonstrateCheckedExceptionHandling() {
        try {
            // This is a checked exception - must be handled
            FileReader reader = new FileReader("nonexistent.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Caught checked exception (FileNotFoundException)");
            System.out.println("Exception: " + e.getClass().getSimpleName());
            System.out.println("Gracefully handled the missing file");
        }
    }
    
    /**
     * Method demonstrating checked exception with throws declaration
     * Caller must handle this checked exception
     * Throws: IOException - checked exception
     */
    static void demonstrateThrowsDeclaration() throws IOException {
        // Using throws keyword - passes responsibility to caller
        // Caller (main method) catches this IOException
        throw new IOException("Simulated file read error");
    }
    
    /**
     * Method demonstrating common unchecked exceptions
     */
    static void demonstrateCommonUncheckedExceptions() {
        System.out.println("A) NullPointerException:");
        try {
            String str = null;
            str.toUpperCase(); // Unchecked exception
        } catch (NullPointerException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nB) ArrayIndexOutOfBoundsException:");
        try {
            int[] arr = {1, 2, 3};
            int val = arr[10]; // Unchecked exception
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nC) ClassCastException:");
        try {
            Object obj = "String";
            Integer num = (Integer) obj; // Unchecked exception
        } catch (ClassCastException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nD) NumberFormatException:");
        try {
            Integer.parseInt("abc123"); // Unchecked exception
        } catch (NumberFormatException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nE) IllegalArgumentException:");
        try {
            if (Thread.currentThread().getPriority() < 1) {
                throw new IllegalArgumentException("Priority must be >= 1");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
    }
    
    /**
     * Method demonstrating common checked exceptions
     */
    static void demonstrateCommonCheckedExceptions() {
        System.out.println("A) FileNotFoundException (Checked):");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("missing.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nB) IOException (Checked):");
        try {
            InputStream input = new ByteArrayInputStream("data".getBytes());
            input.close();
            input.read(); // Already closed
        } catch (IOException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nC) InterruptedException (Checked):");
        try {
            // Simulating interrupt
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Thread was interrupted");
            }
            System.out.println("   Thread is not interrupted");
        } catch (InterruptedException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nD) ClassNotFoundException (Checked):");
        try {
            Class.forName("com.nonexistent.MyClass");
        } catch (ClassNotFoundException e) {
            System.out.println("   Caught: " + e.getClass().getSimpleName());
        }
    }
    
    /**
     * Method that throws checked exception
     * Must be handled by caller
     */
    static void methodThatThrowsCheckedException() throws IOException {
        throw new IOException("This is a checked exception");
    }
}

/**
 * COMPARISON TABLE:
 * 
 * ┌─────────────────────┬────────────────────┬──────────────────────┐
 * │ Aspect              │ Checked            │ Unchecked            │
 * ├─────────────────────┼────────────────────┼──────────────────────┤
 * │ Extends             │ Exception          │ RuntimeException     │
 * │ Compile-time check  │ YES (forced)       │ NO (optional)        │
 * │ Must handle/declare │ YES                │ NO                   │
 * │ Examples            │ IOException,       │ NullPointer,         │
 * │                     │ SQLException       │ ArrayIndex,          │
 * │                     │ FileNotFound       │ ClassCast,           │
 * │ Use case            │ Recoverable        │ Programming errors   │
 * │ Thrown by           │ API methods        │ JVM, logic errors    │
 * └─────────────────────┴────────────────────┴──────────────────────┘
 * 
 * KEY DIFFERENCES:
 * 
 * 1. Checked Exceptions:
 *    ✓ Compiler checks if you handle them
 *    ✓ Must use try-catch or throws keyword
 *    ✓ For external factors (file not found, network down)
 *    ✓ Caller knows to expect and handle them
 * 
 * 2. Unchecked Exceptions:
 *    ✓ Compiler doesn't force handling
 *    ✓ Indicating programming errors
 *    ✓ Shouldn't happen in normal execution
 *    ✓ Better to prevent than catch
 * 
 * BEST PRACTICES:
 * 
 * ✓ Handle checked exceptions - they're expected
 * ✓ Prevent unchecked exceptions through validation
 * ✓ Use throws for propagating checked exceptions up the call stack
 * ✓ Catch specific exceptions, not generic Exception/Throwable
 * ✓ When catching checked exceptions, handle them meaningfully
 * ✓ For unchecked, fix the root cause (null check, bounds check, etc.)
 */

