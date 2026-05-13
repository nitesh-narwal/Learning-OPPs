package me.niteshh.OPPs.tutorial.generics.lecturePractice;

/**
 * ADVANCED PATTERNS: EXCEPTIONS WITH GENERICS IN PRODUCTION CODE
 * ===============================================================
 * 
 * Real-world patterns and use cases for handling exceptions
 * when working with generic types in production systems.
 */

public class AdvancedExceptionPatterns {

    // ======================================================================
    // PATTERN 1: RESULT WRAPPER (Popular in modern Java)
    // ======================================================================
    
    /**
     * Instead of throwing exceptions, return a Result object.
     * This allows you to preserve generic type information.
     * Similar to Java's Optional, but more flexible.
     */
    
    static class Result<T> {
        private final T value;
        private final Exception exception;
        private final boolean success;

        private Result(T value, Exception exception, boolean success) {
            this.value = value;
            this.exception = exception;
            this.success = success;
        }

        public static <T> Result<T> success(T value) {
            return new Result<>(value, null, true);
        }

        public static <T> Result<T> failure(Exception e) {
            return new Result<>(null, e, false);
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

        public void ifSuccess(java.util.function.Consumer<T> action) {
            if (success) {
                action.accept(value);
            }
        }

        public void ifFailure(java.util.function.Consumer<Exception> action) {
            if (!success) {
                action.accept(exception);
            }
        }

        public <U> Result<U> map(java.util.function.Function<T, U> mapper) {
            if (success) {
                try {
                    return Result.success(mapper.apply(value));
                } catch (Exception e) {
                    return Result.failure(e);
                }
            }
            return Result.failure(exception);
        }

        @Override
        public String toString() {
            return success ? 
                    "Result[success: " + value + "]" : 
                    "Result[failure: " + exception.getMessage() + "]";
        }
    }

    // ======================================================================
    // PATTERN 2: TYPED EXCEPTION HANDLER
    // ======================================================================
    
    /**
     * Create handlers that can process different exception types
     * while preserving generic type information.
     */
    
    interface ExceptionHandler<T> {
        void handle(T data, Exception e);
    }

    static class ProcessingService<T> {
        private ExceptionHandler<T> exceptionHandler;

        public ProcessingService(ExceptionHandler<T> handler) {
            this.exceptionHandler = handler;
        }

        public Result<T> processData(T data) {
            try {
                if (data == null) {
                    throw new IllegalArgumentException("Data cannot be null");
                }

                if (data instanceof String) {
                    String str = (String) data;
                    if (str.isEmpty()) {
                        throw new IllegalArgumentException("Empty string not allowed");
                    }
                }

                System.out.println("Successfully processed: " + data);
                return Result.success(data);

            } catch (Exception e) {
                if (exceptionHandler != null) {
                    exceptionHandler.handle(data, e);
                }
                return Result.failure(e);
            }
        }
    }

    // ======================================================================
    // PATTERN 3: WRAPPER EXCEPTION WITH GENERIC DATA
    // ======================================================================
    
    /**
     * Store generic data without making the exception itself generic.
     * Use inheritance and type-specific subclasses instead.
     */
    
    static class ApplicationException extends Exception {
        protected Object contextData;
        protected String contextType;
        protected long timestamp;

        public ApplicationException(String message, Object contextData) {
            super(message);
            this.contextData = contextData;
            this.contextType = contextData != null ? 
                    contextData.getClass().getName() : "unknown";
            this.timestamp = System.currentTimeMillis();
        }

        public Object getContextData() {
            return contextData;
        }

        public String getContextType() {
            return contextType;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    // Type-specific subclasses (type-safe at compile time)
    static class StringProcessingException extends ApplicationException {
        public StringProcessingException(String message, String data) {
            super(message, data);
        }

        public String getStringData() {
            return (String) contextData;
        }
    }

    static class NumberProcessingException extends ApplicationException {
        public NumberProcessingException(String message, Number data) {
            super(message, data);
        }

        public Number getNumberData() {
            return (Number) contextData;
        }
    }

    // ======================================================================
    // PATTERN 4: GENERIC VALIDATOR WITH TYPED EXCEPTIONS
    // ======================================================================
    
    static interface Validator<T> {
        void validate(T value) throws ApplicationException;
    }

    static class StringValidator implements Validator<String> {
        private int minLength;
        private int maxLength;

        public StringValidator(int minLength, int maxLength) {
            this.minLength = minLength;
            this.maxLength = maxLength;
        }

        @Override
        public void validate(String value) throws StringProcessingException {
            if (value == null) {
                throw new StringProcessingException("String cannot be null", null);
            }
            if (value.length() < minLength || value.length() > maxLength) {
                throw new StringProcessingException(
                        "String length must be between " + minLength + " and " + maxLength,
                        value
                );
            }
        }
    }

    static class NumberValidator implements Validator<Integer> {
        private int min;
        private int max;

        public NumberValidator(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public void validate(Integer value) throws NumberProcessingException {
            if (value == null) {
                throw new NumberProcessingException("Number cannot be null", 0);
            }
            if (value < min || value > max) {
                throw new NumberProcessingException(
                        "Number must be between " + min + " and " + max,
                        value
                );
            }
        }
    }

    // ======================================================================
    // PATTERN 5: GENERIC CONTAINER WITH ERROR TRACKING
    // ======================================================================
    
    static class ProcessingContainer<T> {
        private T data;
        private Exception error;
        private int attemptCount;
        private long startTime;
        private long endTime;

        public ProcessingContainer(T data) {
            this.data = data;
            this.attemptCount = 0;
            this.startTime = System.currentTimeMillis();
        }

        public T getData() {
            return data;
        }

        public boolean hasError() {
            return error != null;
        }

        public Exception getError() {
            return error;
        }

        public void recordError(Exception e) {
            this.error = e;
            this.endTime = System.currentTimeMillis();
        }

        public void incrementAttempts() {
            this.attemptCount++;
        }

        public int getAttemptCount() {
            return attemptCount;
        }

        public long getExecutionTime() {
            return (endTime > 0 ? endTime : System.currentTimeMillis()) - startTime;
        }

        public String getDataType() {
            return data != null ? data.getClass().getSimpleName() : "unknown";
        }

        @Override
        public String toString() {
            return "ProcessingContainer{" +
                    "dataType='" + getDataType() + '\'' +
                    ", hasError=" + hasError() +
                    ", attemptCount=" + attemptCount +
                    ", executionTime=" + getExecutionTime() + "ms" +
                    (hasError() ? ", error=" + error.getMessage() : "") +
                    '}';
        }
    }

    // ======================================================================
    // PRACTICAL DEMONSTRATIONS
    // ======================================================================
    
    static void main(String[] args) {
        System.out.println("=== ADVANCED EXCEPTION PATTERNS ===\n");

        // ===== PATTERN 1: Result Wrapper =====
        System.out.println("PATTERN 1: Result Wrapper");
        Result<String> result1 = Result.success("Hello World");
        System.out.println(result1);

        Result<String> result2 = Result.failure(new Exception("Processing failed"));
        System.out.println(result2);

        result1.ifSuccess(val -> System.out.println("Got: " + val));
        result2.ifFailure(err -> System.out.println("Error: " + err.getMessage()));

        System.out.println("\n" + "=".repeat(50) + "\n");

        // ===== PATTERN 2: Typed Exception Handler =====
        System.out.println("PATTERN 2: Typed Exception Handler");
        ExceptionHandler<String> stringHandler = (data, e) -> 
            System.out.println("String handler caught: " + e.getMessage());

        ProcessingService<String> service = new ProcessingService<>(stringHandler);
        Result<String> result3 = service.processData("ValidData");
        System.out.println(result3);

        Result<String> result4 = service.processData("");
        System.out.println(result4);

        System.out.println("\n" + "=".repeat(50) + "\n");

        // ===== PATTERN 3: Type-specific Exceptions =====
        System.out.println("PATTERN 3: Type-specific Exceptions");
        try {
            throw new StringProcessingException("Invalid input", "tooshort");
        } catch (StringProcessingException e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("String data: " + e.getStringData());
            System.out.println("Type: " + e.getContextType());
        }

        System.out.println("\n" + "=".repeat(50) + "\n");

        // ===== PATTERN 4: Validators =====
        System.out.println("PATTERN 4: Generic Validators");
        StringValidator stringValidator = new StringValidator(3, 20);
        
        try {
            stringValidator.validate("ValidName");
            System.out.println("String validation passed");
        } catch (ApplicationException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }

        NumberValidator numberValidator = new NumberValidator(1, 100);
        
        try {
            numberValidator.validate(150);
        } catch (ApplicationException e) {
            System.out.println("Number validation failed: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(50) + "\n");

        // ===== PATTERN 5: Container with Error Tracking =====
        System.out.println("PATTERN 5: Processing Container with Error Tracking");
        ProcessingContainer<String> container1 = new ProcessingContainer<>("TestData");
        container1.incrementAttempts();
        System.out.println("Success: " + container1);

        ProcessingContainer<Integer> container2 = new ProcessingContainer<>(42);
        container2.incrementAttempts();
        container2.incrementAttempts();
        container2.recordError(new Exception("Processing failed"));
        System.out.println("Failed: " + container2);

        System.out.println("\n=== KEY TAKEAWAYS ===");
        System.out.println("1. Use Result wrapper to preserve generic types");
        System.out.println("2. Create type-specific exception subclasses");
        System.out.println("3. Use Validators interface for type-safe validation");
        System.out.println("4. Store context data without making exceptions generic");
        System.out.println("5. Track errors and metadata in containers");
        System.out.println("6. Let the type system help you catch errors compile-time");
    }
}

