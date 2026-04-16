package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * STEP 5: THROWS KEYWORD AND EXCEPTION PROPAGATION
 * 
 * THROWS Keyword:
 * - Used to declare exceptions that a method might throw
 * - Shifts responsibility to the caller
 * - Used with checked exceptions
 * - Syntax: returnType methodName(params) throws ExceptionType1, ExceptionType2 { }
 * 
 * Exception Propagation:
 * - Exception moves up the call stack
 * - If not caught at current level, goes to caller
 * - Continues until caught or program terminates
 * 
 * Call Stack Example:
 *    main() → method1() → method2() → method3()
 *    ↓ ↓ ↓ Exception propagates backward ↓ ↓ ↓
 * 
 * If method3 throws exception:
 * 1. method3 doesn't catch it
 * 2. method2 receives it (if no throws, must catch)
 * 3. method1 receives it (if no throws, must catch)
 * 4. main catches it or program crashes
 * 
 * throw vs throws:
 * - throw: Actually throws an exception instance
 * - throws: Declares that method might throw exception
 */

import java.io.*;

public class Step5_ThrowsKeyword {

    public static void main(String[] args) {
        System.out.println("=== THROWS KEYWORD AND EXCEPTION PROPAGATION ===\n");
        
        // Example 1: Simple throws declaration
        System.out.println("Example 1: Simple throws declaration");
        try {
            riskyOperation(); // Method throws IOException
        } catch (IOException e) {
            System.out.println("Caught in main: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 2: Exception propagation through call stack
        System.out.println("Example 2: Exception propagation through call stack");
        try {
            levelOne(); // Calls levelTwo which calls levelThree
        } catch (IOException e) {
            System.out.println("Caught in main from deep call stack");
            System.out.println("Exception originated from levelThree");
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 3: Multiple exceptions declared with throws
        System.out.println("Example 3: Multiple exceptions in throws clause");
        try {
            processData("data.txt"); // Can throw IOException or SQLException
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Caught SQLException: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 4: Catching and re-throwing exception
        System.out.println("Example 4: Catching and re-throwing exception");
        try {
            wrapAndRethrow(); // Method catches and re-throws
        } catch (IOException e) {
            System.out.println("Caught re-thrown exception: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 5: Partial handling (catch some, let others propagate)
        System.out.println("Example 5: Partial exception handling");
        try {
            partialHandling(); // Handles some exceptions, propagates others
        } catch (IOException e) {
            System.out.println("IOException propagated to main: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Other exception: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 6: Override with throws (related to inheritance)
        System.out.println("Example 6: Method override considerations");
        performOperation(); // Uses parent or child class method
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 7: throws with custom exceptions
        System.out.println("Example 7: throws with custom exceptions");
        try {
            validateAge(150); // Throws custom exception
        } catch (CustomValidationException e) {
            System.out.println("Caught custom exception: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 8: Exception propagation in real scenario
        System.out.println("Example 8: Real-world exception propagation");
        try {
            processUserRequest("user_123");
        } catch (IOException e) {
            System.out.println("User request failed due to I/O: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("User request failed due to DB: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        System.out.println("✓ All throws and propagation examples completed!");
    }
    
    // ============ EXAMPLE METHODS ============
    
    /**
     * Example 1: Simple method that throws IOException
     * Caller must handle this exception
     * throws IOException - checked exception
     */
    static void riskyOperation() throws IOException {
        System.out.println("Performing risky operation...");
        throw new IOException("Simulated I/O error");
    }
    
    /**
     * Example 2A: Level One - calls Level Two
     * Declares that it might throw IOException
     */
    static void levelOne() throws IOException {
        System.out.println("Level 1: Starting operation");
        levelTwo();
        System.out.println("Level 1: Operation completed");
    }
    
    /**
     * Example 2B: Level Two - calls Level Three
     * Declares exception it doesn't catch
     */
    static void levelTwo() throws IOException {
        System.out.println("  Level 2: Processing...");
        levelThree();
        System.out.println("  Level 2: Done");
    }
    
    /**
     * Example 2C: Level Three - actually throws exception
     * Exception propagates up through level 2 to level 1 to main
     */
    static void levelThree() throws IOException {
        System.out.println("    Level 3: Executing risky code");
        throw new IOException("Exception from Level 3");
    }
    
    /**
     * Example 3: Multiple exceptions in throws clause
     * throws IOException, SQLException
     */
    static void processData(String fileName) throws IOException, SQLException {
        System.out.println("Processing file: " + fileName);
        // Could throw IOException
        throw new IOException("File read error");
        // Could throw SQLException (not reached in this example)
    }
    
    /**
     * Example 4: Catching exception and re-throwing it
     * Allows for logging/processing while propagating exception
     */
    static void wrapAndRethrow() throws IOException {
        try {
            System.out.println("Attempting operation...");
            throw new IOException("Original error");
        } catch (IOException e) {
            System.out.println("Caught in wrapAndRethrow: " + e.getMessage());
            System.out.println("Re-throwing to caller...");
            throw e; // Re-throw same exception
        }
    }
    
    /**
     * Example 5: Partial exception handling
     * - Catches NumberFormatException
     * - Re-throws IOException (propagates to caller)
     */
    static void partialHandling() throws IOException {
        try {
            String value = "notANumber";
            int num = Integer.parseInt(value); // NumberFormatException
            throw new IOException("File error"); // Would be thrown if reached
        } catch (NumberFormatException e) {
            System.out.println("Handled NumberFormatException locally");
            System.out.println("But IOException will be propagated if thrown");
        }
    }
    
    /**
     * Example 6: Method in parent class with throws
     */
    static void performOperation() {
        try {
            Operations ops = new ChildOperations();
            ops.execute(); // Uses child's overridden method
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }
    
    /**
     * Example 7: Using throws with custom exceptions
     */
    static void validateAge(int age) throws CustomValidationException {
        if (age < 0 || age > 120) {
            throw new CustomValidationException("Age must be between 0 and 120", age);
        }
        System.out.println("Age is valid: " + age);
    }
    
    /**
     * Example 8: Real-world scenario
     * Method throws multiple exceptions
     */
    static void processUserRequest(String userId) throws IOException, DatabaseException {
        System.out.println("Processing request for user: " + userId);
        // Simulating database operation that might fail
        throw new DatabaseException("Database connection failed");
    }
}

// ============ HELPER CLASSES ============

/**
 * Parent class with throws declaration
 */
class Operations {
    public void execute() throws IOException {
        System.out.println("Executing operation in parent class");
    }
}

/**
 * Child class overriding parent method
 * Can only throw same or subtype exceptions
 * Cannot throw broader checked exceptions
 */
class ChildOperations extends Operations {
    @Override
    public void execute() throws IOException {
        System.out.println("Executing operation in child class");
        // Could throw IOException or any subtype
    }
}

/**
 * Custom exception for validation
 */
class CustomValidationException extends Exception {
    private int value;
    
    public CustomValidationException(String message, int value) {
        super(message);
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}

/**
 * Custom database exception
 */
class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}

/**
 * KEY CONCEPTS SUMMARY:
 * 
 * 1. THROWS Declaration:
 *    - Placed after method parameters
 *    - Before method body
 *    - Syntax: throws ExceptionType1, ExceptionType2, ...
 *    - Used with checked exceptions
 * 
 * 2. Exception Propagation:
 *    - Exception moves UP the call stack
 *    - Stops when caught or program terminates
 *    - Helps with error reporting
 *    - Allows handling at appropriate level
 * 
 * 3. THROW vs THROWS:
 *    - throw: Creates and throws exception instance
 *             throw new IOException("message");
 *    - throws: Declares potential exceptions
 *              public void method() throws IOException
 * 
 * 4. Call Stack Propagation:
 *    - Exception originates in deepest method
 *    - Propagates upward if not caught
 *    - Can be caught at any level
 * 
 * 5. Method Overriding Rules:
 *    - Child can throw same exceptions as parent
 *    - Child can throw subtypes of parent's exceptions
 *    - Child CANNOT throw broader exceptions
 * 
 * 6. Best Practices:
 *    ✓ Declare specific exceptions, not generic
 *    ✓ Don't declare Exception or Throwable unless necessary
 *    ✓ Catch exceptions at appropriate level
 *    ✓ Provide meaningful error messages
 *    ✓ Allow propagation for serious errors
 *    ✓ Use finally for cleanup regardless of exception
 * 
 * 7. When to Use throws:
 *    ✓ When method cannot recover from exception
 *    ✓ When caller is better positioned to handle
 *    ✓ For checked exceptions that must propagate
 *    ✓ To indicate that caller must be aware
 */

