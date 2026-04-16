package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * STEP 6: ADVANCED EXCEPTION HANDLING
 * 
 * Advanced Topics:
 * 1. Try-with-resources (Java 7+): Automatic resource cleanup
 * 2. Exception suppression: When multiple exceptions occur
 * 3. Stack trace analysis: Understanding exception origin
 * 4. Exception handling strategies: Best practices
 * 5. Finally vs try-with-resources: When to use each
 * 6. Multi-level exception handling: Complex scenarios
 */

import java.io.*;
import java.util.*;

public class Step6_AdvancedExceptionHandling {

    public static void main(String[] args) {
        System.out.println("=== ADVANCED EXCEPTION HANDLING ===\n");
        
        // Example 1: Try-with-resources (Java 7+) - Auto cleanup
        System.out.println("Example 1: Try-with-resources - Automatic resource cleanup");
        demonstrateTryWithResources();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 2: Multiple resources in try-with-resources
        System.out.println("Example 2: Multiple resources in try-with-resources");
        demonstrateMultipleResources();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 3: Exception suppression
        System.out.println("Example 3: Exception suppression and getSuppressed()");
        demonstrateExceptionSuppression();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 4: Stack trace analysis
        System.out.println("Example 4: Understanding stack traces");
        demonstrateStackTraceAnalysis();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 5: Exception wrapping and cause chain
        System.out.println("Example 5: Exception cause chain");
        demonstrateExceptionCauseChain();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 6: Handling exceptions from multiple sources
        System.out.println("Example 6: Multiple exception sources");
        demonstrateMultipleExceptionSources();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 7: Strategy - Fail fast vs Fail safe
        System.out.println("Example 7: Exception handling strategies");
        demonstrateHandlingStrategies();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 8: Resource cleanup guarantees
        System.out.println("Example 8: Resource cleanup comparison");
        demonstrateResourceCleanupGuarantee();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        System.out.println("✓ All advanced exception handling examples completed!");
    }
    
    /**
     * Example 1: Try-with-resources
     * Automatically closes resources implementing AutoCloseable
     * Available since Java 7
     * 
     * Syntax:
     * try (ResourceType resource = new ResourceType()) {
     *     // Use resource
     * } catch (Exception e) {
     *     // Handle exception
     * }
     * // Resource automatically closed here
     */
    static void demonstrateTryWithResources() {
        System.out.println("Demonstrating try-with-resources:");
        
        // Traditional way (old - before Java 7)
        System.out.println("\nOLD Way (manual resource management):");
        /*
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader("data"));
            // Use reader
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close(); // Must manually close
                } catch (IOException e) {
                    System.out.println("Error closing: " + e.getMessage());
                }
            }
        }
        */
        
        // Modern way (Java 7+)
        System.out.println("NEW Way (try-with-resources):");
        try (BufferedReader reader = new BufferedReader(new StringReader("Hello, World!"))) {
            System.out.println("Resource automatically managed: " + reader.readLine());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Resource automatically closed here without explicit close() call
        System.out.println("Resource automatically closed!");
    }
    
    /**
     * Example 2: Multiple resources in try-with-resources
     * Java 7+ allows multiple resources separated by semicolon
     * Closed in reverse order of declaration
     */
    static void demonstrateMultipleResources() {
        System.out.println("Multiple resources in try-with-resources:");
        
        try (
            StringReader input = new StringReader("source data");
            StringWriter output = new StringWriter();
            BufferedReader reader = new BufferedReader(input)
        ) {
            String line = reader.readLine();
            output.write("Processed: " + line);
            System.out.println("Output: " + output.toString());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // All three resources closed automatically in reverse order
        System.out.println("All resources closed automatically!");
    }
    
    /**
     * Example 3: Exception suppression
     * When multiple exceptions occur, some are suppressed
     * Access with getSuppressed() method
     */
    static void demonstrateExceptionSuppression() {
        System.out.println("Exception suppression demonstration:");
        
        try (ProblematicResource resource = new ProblematicResource()) {
            // This throws exception
            throw new IOException("Primary exception in try block");
        } catch (IOException e) {
            System.out.println("Primary exception caught: " + e.getMessage());
            
            // Check if there are suppressed exceptions
            Throwable[] suppressed = e.getSuppressed();
            if (suppressed.length > 0) {
                System.out.println("\nSuppressed exceptions (" + suppressed.length + "):");
                for (Throwable t : suppressed) {
                    System.out.println("  - " + t.getClass().getSimpleName() + ": " + t.getMessage());
                }
            }
        }
    }
    
    /**
     * Example 4: Stack trace analysis
     * Stack trace shows call sequence leading to exception
     */
    static void demonstrateStackTraceAnalysis() {
        System.out.println("Stack trace analysis:");
        
        try {
            methodA();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("\nStack Trace (calling sequence):");
            
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (int i = 0; i < stackTrace.length; i++) {
                StackTraceElement element = stackTrace[i];
                System.out.println(
                    (i + 1) + ". " +
                    element.getClassName() + "." +
                    element.getMethodName() + "() " +
                    "line " + element.getLineNumber()
                );
            }
        }
    }
    
    /**
     * Helper methods for stack trace
     */
    static void methodA() throws Exception {
        methodB();
    }
    
    static void methodB() throws Exception {
        methodC();
    }
    
    static void methodC() throws Exception {
        throw new Exception("Error in methodC");
    }
    
    /**
     * Example 5: Exception cause chain
     * Wrapping exceptions preserves original cause
     */
    static void demonstrateExceptionCauseChain() {
        System.out.println("Exception cause chain:");
        
        try {
            try {
                // Original exception
                throw new SQLException("Database connection failed");
            } catch (SQLException e) {
                // Wrap in higher-level exception
                throw new DataAccessException("Cannot access data", e);
            }
        } catch (DataAccessException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            
            System.out.println("\nCause chain:");
            Throwable cause = e.getCause();
            int level = 1;
            while (cause != null) {
                System.out.println(
                    level + ". Root cause: " +
                    cause.getClass().getSimpleName() +
                    " - " + cause.getMessage()
                );
                cause = cause.getCause();
                level++;
            }
        }
    }
    
    /**
     * Example 6: Handling exceptions from multiple sources
     */
    static void demonstrateMultipleExceptionSources() {
        System.out.println("Handling multiple exception sources:");
        
        List<String> sources = Arrays.asList("source1", "source2", "source3");
        List<String> results = new ArrayList<>();
        
        for (String source : sources) {
            try {
                String result = processSource(source);
                results.add(result);
                System.out.println("✓ Processed: " + source);
            } catch (SourceException e) {
                System.out.println("✗ Failed to process " + source + ": " + e.getMessage());
                // Continue processing other sources
            }
        }
        
        System.out.println("Successfully processed: " + results.size() + " sources");
    }
    
    static String processSource(String source) throws SourceException {
        if (source.equals("source2")) {
            throw new SourceException("Source2 has invalid data");
        }
        return "Processed: " + source;
    }
    
    /**
     * Example 7: Exception handling strategies
     */
    static void demonstrateHandlingStrategies() {
        System.out.println("Strategy A: Fail Fast (stop on first error)");
        try {
            for (int i = 1; i <= 5; i++) {
                if (i == 3) {
                    throw new IllegalArgumentException("Error at iteration " + i);
                }
                System.out.println("Processing item " + i);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Stopped: " + e.getMessage());
        }
        
        System.out.println("\nStrategy B: Fail Safe (continue on error)");
        List<String> items = Arrays.asList("item1", "item2", "invalid", "item4", "item5");
        int successCount = 0;
        int failureCount = 0;
        
        for (String item : items) {
            try {
                processItem(item);
                successCount++;
            } catch (Exception e) {
                failureCount++;
                System.out.println("Skipped: " + item);
            }
        }
        System.out.println("Success: " + successCount + ", Failures: " + failureCount);
    }
    
    static void processItem(String item) throws IllegalArgumentException {
        if (item.equals("invalid")) {
            throw new IllegalArgumentException("Invalid item");
        }
        System.out.println("Processing: " + item);
    }
    
    /**
     * Example 8: Resource cleanup guarantees
     */
    static void demonstrateResourceCleanupGuarantee() {
        System.out.println("Try-with-resources vs Traditional try-finally:\n");
        
        System.out.println("With try-with-resources:");
        try (SimpleResource res = new SimpleResource("Resource1")) {
            System.out.println("Using " + res.getName());
            // Exception could occur here
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        // Resource definitely closed
        
        System.out.println("\nWith traditional try-finally:");
        SimpleResource res = null;
        try {
            res = new SimpleResource("Resource2");
            System.out.println("Using " + res.getName());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            if (res != null) {
                try {
                    res.close();
                } catch (Exception e) {
                    System.out.println("Error closing: " + e.getMessage());
                }
            }
        }
    }
}

// ============ HELPER CLASSES ============

/**
 * Resource that implements AutoCloseable
 * Used with try-with-resources
 */
class SimpleResource implements AutoCloseable {
    private String name;
    private boolean isClosed = false;
    
    public SimpleResource(String name) throws Exception {
        this.name = name;
        System.out.println("  Resource opened: " + name);
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void close() throws Exception {
        isClosed = true;
        System.out.println("  Resource closed: " + name);
    }
    
    public boolean isClosed() {
        return isClosed;
    }
}

/**
 * Problematic resource that throws exception during close
 */
class ProblematicResource implements AutoCloseable {
    @Override
    public void close() throws IOException {
        // Throws exception when closed
        throw new IOException("Error closing resource");
    }
}

/**
 * Custom exception for data access
 */
class DataAccessException extends Exception {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * SQL Exception simulation
 */
class SQLException extends Exception {
    public SQLException(String message) {
        super(message);
    }
}

/**
 * Custom source exception
 */
class SourceException extends Exception {
    public SourceException(String message) {
        super(message);
    }
}

/**
 * KEY CONCEPTS FOR ADVANCED EXCEPTION HANDLING:
 * 
 * 1. Try-with-resources (Java 7+):
 *    ✓ Automatically closes resources implementing AutoCloseable
 *    ✓ Simplifies resource management
 *    ✓ Prevents resource leaks
 *    ✓ Closed in reverse order of declaration
 * 
 * 2. Exception Suppression:
 *    - Exceptions from close() are suppressed
 *    - Access via getSuppressed() method
 *    - Preserves primary exception
 * 
 * 3. Stack Trace:
 *    ✓ Shows method call sequence
 *    ✓ Helps identify error origin
 *    ✓ Use getStackTrace() to access programmatically
 *    ✓ Use printStackTrace() for debugging
 * 
 * 4. Exception Cause Chain:
 *    ✓ Wrapping preserves original exception
 *    ✓ Use getCause() to access root cause
 *    ✓ Helps with error diagnosis
 * 
 * 5. Handling Strategies:
 *    - Fail Fast: Stop on first error (good for critical operations)
 *    - Fail Safe: Continue despite errors (good for batch operations)
 * 
 * 6. Resource Management:
 *    ✓ Try-with-resources is preferred (Java 7+)
 *    ✓ Traditional try-finally for pre-7 Java
 *    ✓ Always ensure cleanup in finally block
 * 
 * 7. Best Practices:
 *    ✓ Use try-with-resources for automatic cleanup
 *    ✓ Catch specific exceptions
 *    ✓ Preserve cause chain for debugging
 *    ✓ Choose appropriate handling strategy
 *    ✓ Log enough information for debugging
 */

