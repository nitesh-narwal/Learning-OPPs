package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * STEP 7: EXCEPTION HANDLING TIPS AND TRICKS
 * 
 * This file contains practical tips, common pitfalls, and best practices
 * for exception handling in Java. Learn from real-world scenarios and
 * improve your error handling skills.
 * 
 * Topics Covered:
 * 1. Common pitfalls and how to avoid them
 * 2. Exception handling best practices
 * 3. Performance considerations
 * 4. Logging strategies
 * 5. Testing exception scenarios
 * 6. Clean exception messages
 * 7. Exception handling patterns
 * 8. When NOT to use exceptions
 */

import java.io.*;

public class Step7_TipsAndTricks {

    public static void main(String[] args) {
        System.out.println("=== EXCEPTION HANDLING TIPS AND TRICKS ===\n");
        
        // Tip 1: Don't catch generic Exception
        System.out.println("TIP 1: Catch specific exceptions, not generic ones");
        tip1_CatchSpecificExceptions();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 2: Never silently ignore exceptions
        System.out.println("TIP 2: Always handle exceptions - never use empty catch blocks");
        tip2_NeverIgnoreExceptions();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 3: Provide meaningful error messages
        System.out.println("TIP 3: Provide context in error messages");
        tip3_MeaningfulErrorMessages();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 4: Use exception chaining
        System.out.println("TIP 4: Chain exceptions to preserve root cause");
        tip4_ExceptionChaining();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 5: Clean up resources properly
        System.out.println("TIP 5: Always use try-with-resources or finally");
        tip5_ResourceCleanup();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 6: Order catch blocks correctly
        System.out.println("TIP 6: Order catch blocks from specific to general");
        tip6_CatchBlockOrder();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 7: Don't use exceptions for normal flow control
        System.out.println("TIP 7: Don't use exceptions for normal flow control");
        tip7_FlowControlVsExceptions();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 8: Add context to exceptions
        System.out.println("TIP 8: Include relevant context in exceptions");
        tip8_ContextualInformation();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 9: Performance considerations
        System.out.println("TIP 9: Performance impact of exception handling");
        tip9_PerformanceConsiderations();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Tip 10: Testing exception scenarios
        System.out.println("TIP 10: How to test exception handling");
        tip10_TestingExceptions();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        System.out.println("✓ All tips and tricks demonstrated!");
    }
    
    /**
     * TIP 1: Catch Specific Exceptions
     * 
     * ✗ BAD: Catching generic Exception
     * try {
     *     // code
     * } catch (Exception e) {
     *     // Catches EVERYTHING - bad practice
     * }
     * 
     * ✓ GOOD: Catching specific exception types
     * try {
     *     // code
     * } catch (IOException e) {
     *     // Handle specific exception
     * } catch (NumberFormatException e) {
     *     // Handle another specific exception
     * }
     */
    static void tip1_CatchSpecificExceptions() {
        System.out.println("BAD Approach (catches everything):");
        try {
            riskyOperation1();
        } catch (Exception e) { // Too generic!
            System.out.println("  ✗ Caught: " + e.getClass().getSimpleName());
            System.out.println("  - Problem: Can't distinguish between error types");
            System.out.println("  - Might accidentally catch and hide programming errors");
        }
        
        System.out.println("\nGOOD Approach (specific exceptions):");
        try {
            riskyOperation1();
        } catch (IOException e) {
            System.out.println("  ✓ Handled IOException specifically");
        } catch (NumberFormatException e) {
            System.out.println("  ✓ Handled NumberFormatException specifically");
        }
        // Exceptions like NullPointerException won't be caught -> reveals bugs
    }
    
    static void riskyOperation1() throws IOException {
        throw new IOException("File not found");
    }
    
    /**
     * TIP 2: Never Use Empty Catch Blocks
     * 
     * ✗ BAD: Empty catch blocks hide errors
     * try {
     *     // code
     * } catch (Exception e) {
     *     // Empty - silently ignores error!
     * }
     * 
     * ✓ GOOD: Always handle, log, or re-throw
     * try {
     *     // code
     * } catch (IOException e) {
     *     logger.error("Error reading file", e);
     *     // Handle appropriately
     * }
     */
    static void tip2_NeverIgnoreExceptions() {
        System.out.println("BAD Approach (ignoring exceptions):");
        try {
            String value = "not_a_number";
            int number = Integer.parseInt(value);
            System.out.println("Number: " + number);
        } catch (NumberFormatException e) {
            // ✗ BAD: Ignoring the error
            System.out.println("  ✗ Empty catch - error is hidden!");
        }
        System.out.println("  Program continues as if nothing happened\n");
        
        System.out.println("GOOD Approach (proper handling):");
        try {
            String value = "not_a_number";
            int number = Integer.parseInt(value);
            System.out.println("Number: " + number);
        } catch (NumberFormatException e) {
            // ✓ GOOD: Handle appropriately
            System.out.println("  ✓ Error logged: Invalid number format - '" + e.getMessage() + "'");
            System.out.println("  ✓ User notified or default value used");
            System.out.println("  ✓ Program behavior is predictable");
        }
    }
    
    /**
     * TIP 3: Provide Meaningful Error Messages
     * 
     * ✗ BAD: Vague error messages
     * throw new Exception("Error");
     * 
     * ✓ GOOD: Include context and details
     * throw new IOException("Failed to read config file: " + filePath +
     *                       " (file size: " + fileSize + " bytes)");
     */
    static void tip3_MeaningfulErrorMessages() {
        System.out.println("BAD: Vague error message");
        try {
            throw new Exception("Error"); // ✗ Not helpful
        } catch (Exception e) {
            System.out.println("  ✗ Message: '" + e.getMessage() + "'");
            System.out.println("  - What went wrong? No idea!");
        }
        
        System.out.println("\nGOOD: Descriptive error message");
        try {
            String filePath = "/home/user/config.properties";
            long fileSize = 0;
            throw new IOException(
                "Failed to read configuration file: " + filePath +
                " (expected size > 0, actual: " + fileSize + " bytes)"
            );
        } catch (IOException e) {
            System.out.println("  ✓ Message: '" + e.getMessage() + "'");
            System.out.println("  - Clear what happened and why");
        }
    }
    
    /**
     * TIP 4: Use Exception Chaining
     * 
     * ✗ BAD: Lost root cause
     * try {
     *     // Throws IOException
     * } catch (IOException e) {
     *     throw new RuntimeException("Error occurred");
     *     // Original exception is lost!
     * }
     * 
     * ✓ GOOD: Preserve cause chain
     * catch (IOException e) {
     *     throw new RuntimeException("Error occurred", e);
     *     // Original exception preserved as cause
     * }
     */
    static void tip4_ExceptionChaining() {
        System.out.println("BAD: Lost root cause");
        try {
            try {
                throw new IOException("Original: Database connection failed");
            } catch (IOException e) {
                // ✗ BAD: Throwing new exception without cause
                throw new RuntimeException("Operation failed");
            }
        } catch (RuntimeException e) {
            System.out.println("  ✗ Exception: " + e.getMessage());
            System.out.println("  - Root cause is LOST: " + e.getCause());
            System.out.println("  - Hard to debug\n");
        }
        
        System.out.println("GOOD: Preserved cause chain");
        try {
            try {
                throw new IOException("Original: Database connection failed");
            } catch (IOException e) {
                // ✓ GOOD: Include cause
                throw new RuntimeException("Operation failed", e);
            }
        } catch (RuntimeException e) {
            System.out.println("  ✓ Exception: " + e.getMessage());
            System.out.println("  ✓ Root cause preserved: " + e.getCause());
            System.out.println("  ✓ Complete error chain for debugging");
        }
    }
    
    /**
     * TIP 5: Resource Cleanup
     * 
     * ✗ BAD: Resource not closed on exception
     * BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
     * reader.close(); // Never reached if exception occurs above
     * 
     * ✓ GOOD: Use try-finally or try-with-resources
     * try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
     *     // Resource automatically closed
     * }
     */
    static void tip5_ResourceCleanup() {
        System.out.println("✓ Using try-with-resources (RECOMMENDED):");
        try (StringReader reader = new StringReader("data")) {
            System.out.println("  ✓ Resource opened and will be auto-closed");
            System.out.println("  ✓ Closed even if exception occurs");
            System.out.println("  ✓ Simplifies code");
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
        System.out.println("  ✓ Resource automatically closed\n");
        
        System.out.println("✓ Using try-finally (for legacy code):");
        StringReader reader = null;
        try {
            reader = new StringReader("data");
            System.out.println("  ✓ Resource opened");
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("  ✓ Resource closed in finally block");
                } catch (Exception e) {
                    System.out.println("  Error closing: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * TIP 6: Order Catch Blocks Correctly
     * 
     * ✗ BAD: General exception before specific
     * try {
     *     // code
     * } catch (Exception e) { // Too general - catches everything
     *     System.out.println("Generic");
     * } catch (NumberFormatException e) { // NEVER reached - UNREACHABLE CODE!
     *     System.out.println("Number format error");
     * }
     * Note: This causes a compile error - showing why order matters!
     * 
     * ✓ GOOD: Specific exceptions before general
     * try {
     *     // code
     * } catch (NumberFormatException e) { // Specific first
     *     System.out.println("Number format error");
     * } catch (Exception e) { // General last
     *     System.out.println("Other error");
     * }
     */
    static void tip6_CatchBlockOrder() {
        System.out.println("BAD: General exception first (would be unreachable catch)");
        try {
            Integer.parseInt("abc");
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException is more specific than Exception
            System.out.println("  ✗ Caught in specific handler first would be better");
        } catch (Exception e) {
            // ✓ Generic handler last
            System.out.println("  ✗ Caught in generic handler: " + e.getClass().getSimpleName());
        }
        
        System.out.println("\nGOOD: Specific exceptions first");
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            // ✓ Specific handler executes
            System.out.println("  ✓ Caught in specific handler: " + e.getClass().getSimpleName());
        } catch (IllegalArgumentException e) {
            // ✓ More specific exception next
            System.out.println("  ✓ Other specific handler as fallback");
        } catch (Exception e) {
            // ✓ Generic handler last
            System.out.println("  ✓ Generic handler as final fallback");
        }
    }
    
    /**
     * TIP 7: Don't Use Exceptions for Flow Control
     * 
     * ✗ BAD: Using exceptions for normal flow
     * try {
     *     int i = 0;
     *     while (true) {
     *         int value = array[i++]; // Uses exception to end loop
     *     }
     * } catch (ArrayIndexOutOfBoundsException e) {
     *     // Loop ended
     * }
     * 
     * ✓ GOOD: Use normal control flow
     * for (int value : array) {
     *     // Process value
     * }
     */
    static void tip7_FlowControlVsExceptions() {
        int[] numbers = {1, 2, 3, 4, 5};
        
        System.out.println("BAD: Using exception for flow control (slow and unclear)");
        long startTime = System.nanoTime();
        try {
            int i = 0;
            while (true) {
                // ✗ This approach is slow - creating exception objects
                int value = numbers[i++];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  ✗ Exception used to exit loop (bad practice)");
        }
        long badTime = System.nanoTime() - startTime;
        System.out.println("  Time: " + badTime + " ns\n");
        
        System.out.println("GOOD: Using normal control flow (fast and clear)");
        startTime = System.nanoTime();
        for (int value : numbers) {
            // ✓ Clear intent, good performance
        }
        long goodTime = System.nanoTime() - startTime;
        System.out.println("  ✓ Clear and efficient");
        System.out.println("  Time: " + goodTime + " ns (much faster!)");
        System.out.println("  Performance difference: " + (badTime / goodTime) + "x slower");
    }
    
    /**
     * TIP 8: Add Context to Exceptions
     * 
     * ✗ BAD: Exception with no context
     * throw new RuntimeException(e);
     * 
     * ✓ GOOD: Include relevant information
     * throw new RuntimeException("Failed to process user: " + userId +
     *                             " with request: " + requestData, e);
     */
    static void tip8_ContextualInformation() {
        System.out.println("BAD: Exception without context");
        try {
            int userId = 123;
            throw new RuntimeException("Invalid data"); // ✗ Where? What data?
        } catch (RuntimeException e) {
            System.out.println("  ✗ Message: '" + e.getMessage() + "'");
            System.out.println("  - Missing context about which user or data");
        }
        
        System.out.println("\nGOOD: Exception with context");
        try {
            int userId = 123;
            String userData = "{name: 'John', age: 30}";
            throw new RuntimeException(
                "Failed to process user ID: " + userId + 
                " with data: " + userData
            );
        } catch (RuntimeException e) {
            System.out.println("  ✓ Message: '" + e.getMessage() + "'");
            System.out.println("  ✓ Clear context about the error");
        }
    }
    
    /**
     * TIP 9: Performance Considerations
     * 
     * - Creating exceptions is expensive (stack trace creation)
     * - Use exceptions for exceptional conditions, not regular flow
     * - Catch and re-throw only when necessary
     * - Consider using Optional or other patterns for flow control
     */
    static void tip9_PerformanceConsiderations() {
        System.out.println("Performance Impact of Exception Creation:");
        
        // Creating many exceptions is expensive
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            Exception e = new Exception("Test exception");
        }
        long exceptionTime = System.nanoTime() - startTime;
        System.out.println("  Creating 10000 exceptions: " + exceptionTime + " ns");
        
        // Creating objects is faster
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            String s = "Test string";
        }
        long stringTime = System.nanoTime() - startTime;
        System.out.println("  Creating 10000 strings: " + stringTime + " ns");
        
        System.out.println("  ✓ Exceptions are ~" + (exceptionTime / stringTime) + "x slower");
        System.out.println("  ✓ Use exceptions for exceptional conditions only");
        System.out.println("  ✓ Not for normal flow control");
    }
    
    /**
     * TIP 10: Testing Exception Scenarios
     */
    static void tip10_TestingExceptions() {
        System.out.println("Testing Exception Handling:\n");
        
        System.out.println("Scenario 1: Testing exception is thrown");
        try {
            validateInput(-5);
            System.out.println("  ✗ Test FAILED - exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Test PASSED - exception thrown as expected");
            System.out.println("  ✓ Message: " + e.getMessage());
        }
        
        System.out.println("\nScenario 2: Testing normal execution");
        try {
            validateInput(10);
            System.out.println("  ✓ Test PASSED - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ Test FAILED - unexpected exception");
        }
        
        System.out.println("\nScenario 3: Testing exception message");
        try {
            validateInput(-5);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("positive")) {
                System.out.println("  ✓ Test PASSED - message contains expected text");
            } else {
                System.out.println("  ✗ Test FAILED - message incorrect");
            }
        }
    }
    
    static void validateInput(int value) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("Value must be positive");
        }
    }
}

/**
 * EXCEPTION HANDLING BEST PRACTICES SUMMARY:
 * 
 * ✓ DO:
 *   1. Catch specific exceptions, not generic Exception
 *   2. Always handle exceptions - never use empty catch blocks
 *   3. Provide meaningful, descriptive error messages
 *   4. Use exception chaining to preserve root cause
 *   5. Clean up resources with try-finally or try-with-resources
 *   6. Order catch blocks from specific to general
 *   7. Include context information in exceptions
 *   8. Log exceptions for debugging
 *   9. Test exception scenarios
 *   10. Use Optional or other patterns instead of exceptions for flow control
 * 
 * ✗ DON'T:
 *   1. Don't catch Exception or Throwable unless necessary
 *   2. Don't silently ignore exceptions
 *   3. Don't provide vague error messages
 *   4. Don't lose exception cause information
 *   5. Don't forget to close resources
 *   6. Don't put general exceptions before specific ones
 *   7. Don't use exceptions for normal flow control
 *   8. Don't create custom exceptions without good reason
 *   9. Don't catch multiple unrelated exceptions in same block
 *   10. Don't throw generic RuntimeException without context
 * 
 * EXCEPTION HANDLING PATTERNS:
 * 
 * Pattern 1: Fail Fast
 *   - Throw exception immediately when error detected
 *   - Used for critical operations
 *   - Example: Validation in constructor
 * 
 * Pattern 2: Fail Safe
 *   - Continue despite errors
 *   - Log and skip problematic item
 *   - Used for batch processing
 * 
 * Pattern 3: Graceful Degradation
 *   - Provide fallback or default behavior
 *   - Partially complete operation
 *   - Example: Cache fallback when DB unavailable
 * 
 * Pattern 4: Exception Translation
 *   - Convert checked to unchecked if appropriate
 *   - Wrap with domain-specific exception
 *   - Improves abstraction
 */

