package me.niteshh.OPPs.tutorial.generics.lecturePractice;

/**
 * QUICK REFERENCE GUIDE: GENERICS AND EXCEPTIONS
 * ===============================================
 * 
 * A comprehensive quick reference for developers working with
 * generics and exceptions. Use this for quick lookups!
 */

public class QuickReferenceGuide {

    /**
     * SECTION 1: WHAT YOU NEED TO KNOW (IN 30 SECONDS)
     * =================================================
     * 
     * ❌ JAVA DOES NOT SUPPORT GENERIC EXCEPTIONS
     * 
     * You CANNOT write:
     *     public class MyException<T> extends Exception { }
     * 
     * Why? Type erasure makes it impossible to safely catch at runtime.
     * 
     * ✓ SOLUTION: Use generic METHODS instead
     *     public <T> void handleData(T data) throws Exception { }
     * 
     * ✓ SOLUTION: Store type info as String
     *     public MyException(Object data) {
     *         super("Type: " + data.getClass().getSimpleName());
     *     }
     */

    /**
     * SECTION 2: COMMON PATTERNS QUICK REFERENCE
     * ==========================================
     */

    // Pattern A: Generic Constructor
    public static class PatternA_GenericConstructor extends Exception {
        public <T> PatternA_GenericConstructor(T value) {
            super("Error with " + value.getClass().getSimpleName() + ": " + value);
        }
    }

    // Pattern B: Type-Specific Subclass
    public static class PatternB_TypeSpecific extends Exception {
        public PatternB_TypeSpecific(String s) {
        }
    }

    public static class PatternB_StringException extends PatternB_TypeSpecific {
        public PatternB_StringException(String message, String data) {
            super(message + " [String: " + data + "]");
        }
    }

    // Pattern C: Result Wrapper
    public static class PatternC_Result<T> {
        private final T value;
        private final Exception exception;
        private final boolean success;

        private PatternC_Result(T value, Exception exception, boolean success) {
            this.value = value;
            this.exception = exception;
            this.success = success;
        }

        public static <T> PatternC_Result<T> success(T value) {
            return new PatternC_Result<>(value, null, true);
        }

        public static <T> PatternC_Result<T> failure(Exception e) {
            return new PatternC_Result<>(null, e, false);
        }

        public boolean isSuccess() {
            return success;
        }

        public T getValue() {
            return value;
        }

        public Exception getException() {
            return exception;
        }
    }

    /**
     * SECTION 3: DO'S AND DON'TS CHECKLIST
     * ====================================
     * 
     * DO's:
     * ✓ Use generic METHODS in exception classes
     * ✓ Extract type info at compile time
     * ✓ Store type information as String in message
     * ✓ Create type-specific exception subclasses
     * ✓ Check types with instanceof
     * ✓ Use Result wrappers to preserve type info
     * ✓ Keep exceptions simple and focused
     * ✓ Document what data the exception contains
     * 
     * DON'Ts:
     * ❌ Make exception class itself generic
     * ❌ Try to catch with type parameters
     * ❌ Assume type info is available at runtime
     * ❌ Ignore null checks in generic methods
     * ❌ Catch generic exceptions too broadly
     * ❌ Mix checked and unchecked arbitrarily
     * ❌ Use raw exception types in modern code
     * ❌ Store only data without type metadata
     */

    /**
     * SECTION 4: COMPILATION ERRORS AND WHAT THEY MEAN
     * =================================================
     */

    // Error 1: Generic Exception Class
    /*
    // ❌ This won't compile:
    // public class MyException<T> extends Exception { }
    // 
    // ERROR: Generic class may not extend java.lang.Throwable
    // SOLUTION: Remove <T>, use generic constructor instead
    // ✓ public class MyException extends Exception {
    // ✓     public <T> MyException(T data) { ... }
    // ✓ }
    */

    // Error 2: Catching with Type Parameter
    /*
    // ❌ This won't compile:
    // try { ... } catch (MyException<String> e) { }
    // 
    // ERROR: Unexpected type parameter (if exception was generic)
    // SOLUTION: Catch the exception without type parameter
    // ✓ try { ... } catch (MyException e) { }
    */

    // Error 3: Type Parameter at Runtime
    /*
    // ❌ This won't compile:
    // if (T == String) { }
    // 
    // ERROR: cannot find symbol: T
    // SOLUTION: Use instanceof or pass Class parameter
    // ✓ if (data instanceof String) { }
    // ✓ if (typeClass == String.class) { }
    */

    /**
     * SECTION 5: WHEN TO USE EACH PATTERN
     * ===================================
     * 
     * Use Pattern A (Generic Constructor) when:
     * - You want a simple exception that captures type info
     * - You don't need to handle different types differently
     * - The type info is for debugging/logging
     * 
     * Use Pattern B (Type-Specific Subclass) when:
     * - Different data types need different handling
     * - You want compile-time type safety
     * - You need type-specific methods on the exception
     * 
     * Use Pattern C (Result Wrapper) when:
     * - You want to preserve generic types through the call stack
     * - You want composable error handling
     * - You need both success and failure information
     * - Modern functional programming style is preferred
     */

    /**
     * SECTION 6: REAL-WORLD EXAMPLES
     * =============================
     */

    // Example 1: Simple generic constructor
    static class DataException extends Exception {
        public <T> DataException(T failedData) {
            super("Failed to process: " + 
                    (failedData != null ? failedData.getClass().getSimpleName() : "null") +
                    " = " + failedData);
        }
    }

    // Example 2: Type-specific with validation
    static class InputValidationException extends Exception {
        private final String field;
        private final String expectedType;
        private final Object actualValue;

        public InputValidationException(String field, String expectedType, Object actualValue) {
            super("Validation failed for field: " + field);
            this.field = field;
            this.expectedType = expectedType;
            this.actualValue = actualValue;
        }

        public String getField() {
            return field;
        }

        public String getExpectedType() {
            return expectedType;
        }

        public Object getActualValue() {
            return actualValue;
        }
    }

    // Example 3: Result wrapper for modern code
    static <T> PatternC_Result<T> processData(T data) {
        try {
            if (data == null) {
                return PatternC_Result.failure(new Exception("Data is null"));
            }
            // Process data
            return PatternC_Result.success(data);
        } catch (Exception e) {
            return PatternC_Result.failure(e);
        }
    }

    /**
     * SECTION 7: MIGRATION GUIDE (From Old to Modern)
     * ===============================================
     * 
     * OLD (Not type-safe):
     * try {
     *     Object result = process(data);
     * } catch (Exception e) {
     *     Object failedData = e.getMessage();  // Can't get original type
     * }
     * 
     * MODERN (Type-safe with generics):
     * Result<String> result = process(data);
     * if (result.isSuccess()) {
     *     String value = result.getValue();  // Type-safe!
     * } else {
     *     Exception error = result.getException();
     * }
     */

    /**
     * SECTION 8: KEY INTERVIEWS QUESTIONS & ANSWERS
     * ==============================================
     */

    /*
    Q1: Why can't Java exceptions be generic?
    A: Type erasure removes generic type information at runtime, but exception
       catching happens at runtime. This would make catching ambiguous.
    
    Q2: How do I work with generics and exceptions?
    A: Use generic METHODS (not classes), and extract/store type info as metadata.
    
    Q3: Should I use Result wrapper or exceptions?
    A: Both can work. Exceptions for exceptional cases, Result for expected failures.
    
    Q4: How do I preserve type information?
    A: Store the type name as String using data.getClass().getSimpleName()
    
    Q5: What's the best practice for generic data processing?
    A: Create type-specific exception subclasses or use Result wrappers.
    */

    /**
     * SECTION 9: DEBUGGING TIPS
     * =========================
     * 
     * 1. Type erasure confusion?
       - Remember: Type info only exists at COMPILE TIME for generics
       - Exception handling happens at RUNTIME
       - They can never meet unless you explicitly preserve info
    
     * 2. Type mismatch in exception?
       - Don't assume runtime type matches compile-time type
       - Always check with instanceof before casting
    
     * 3. Lost information?
       - Store type name: data.getClass().getSimpleName()
       - Store value: data.toString()
       - Store context: what were you doing when this failed?
    
     * 4. Ambiguous catching?
       - Catch specific exception types, not generic ones
       - Use instanceof or method checks for data type
    */

    /**
     * SECTION 10: QUICK TEMPLATES TO COPY-PASTE
     * ==========================================
     */

    // Template 1: Simple exception with generic constructor
    /*
    static class QuickException extends Exception {
        public <T> QuickException(String message, T data) {
            super(message + " [Type=" + data.getClass().getSimpleName() + 
                  ", Value=" + data + "]");
        }
    }
    */

    // Template 2: Type-specific exception
    /*
    static class SpecificException<T> {  // ❌ Can't extend Exception, so use wrapper
        private Exception exception;
        private T data;
        
        public SpecificException(T data, String message) {
            this.exception = new Exception(message);
            this.data = data;
        }
    }
    */

    // Template 3: Simple result wrapper
    /*
    static class SimpleResult<T> {
        private T value;
        private Exception error;
        
        public boolean success() { return error == null; }
        public T get() { return value; }
        public Exception error() { return error; }
    }
    */

    // Example usage
    static void demonstratePatterns() {
        System.out.println("=== QUICK REFERENCE EXAMPLES ===\n");

        // Example 1
        try {
            throw new PatternA_GenericConstructor(42);
        } catch (Exception e) {
            System.out.println("Pattern A: " + e.getMessage());
        }

        // Example 2
        try {
            throw new PatternB_StringException("Invalid input", "empty");
        } catch (PatternB_TypeSpecific e) {
            System.out.println("Pattern B: " + e.getMessage());
        }

        // Example 3
        PatternC_Result<String> result = processData("test");
        if (result.isSuccess()) {
            System.out.println("Pattern C: " + result.getValue());
        }

        System.out.println("\n=== END OF QUICK REFERENCE ===");
    }

    public static void main(String[] args) {
        demonstratePatterns();
    }
}

