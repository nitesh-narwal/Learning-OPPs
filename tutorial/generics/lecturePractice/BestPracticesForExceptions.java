package me.niteshh.OPPs.tutorial.generics.lecturePractice;

/**
 * BEST PRACTICES FOR WORKING WITH EXCEPTIONS AND GENERICS
 * ========================================================
 * 
 * This file contains production-ready patterns and practices
 * for handling exceptions when working with generic types.
 */

public class BestPracticesForExceptions {

    // ======================================================================
    // PRACTICE 1: CREATE TYPED EXCEPTION WRAPPERS (Not generic exceptions)
    // ======================================================================
    
    /**
     * BEST: Instead of making exception generic, create specific exception classes
     * for different scenarios. This is more type-safe at runtime.
     */
    
    static class DataProcessingException extends Exception {
        private String processedData;
        private Class<?> dataType;
        private long timestamp;

        public DataProcessingException(String message, Object data) {
            super(message);
            this.processedData = data != null ? data.toString() : "null";
            this.dataType = data != null ? data.getClass() : null;
            this.timestamp = System.currentTimeMillis();
        }

        public String getProcessedData() {
            return processedData;
        }

        public Class<?> getDataType() {
            return dataType;
        }

        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return "DataProcessingException{" +
                    "message='" + getMessage() + '\'' +
                    ", processedData='" + processedData + '\'' +
                    ", dataType=" + (dataType != null ? dataType.getSimpleName() : "unknown") +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }

    // ======================================================================
    // PRACTICE 2: USE GENERIC METHODS FOR TYPE-AWARE PROCESSING
    // ======================================================================
    
    /**
     * This method is generic, but it doesn't throw generic exceptions.
     * Instead, it creates exceptions with type information extracted
     * and stored as Strings/metadata.
     */
    
    static class GenericDataProcessor {
        public <T> void processData(T data) throws DataProcessingException {
            if (data == null) {
                throw new DataProcessingException("Null data received of unknown type", null);
            }
            
            // Type information is extracted at compile time
            // and stored in the exception message
            String typeInfo = data.getClass().getSimpleName();
            
            if (data instanceof String && ((String) data).isEmpty()) {
                throw new DataProcessingException("Empty string provided", data);
            }
            
            if (data instanceof Number) {
                Number num = (Number) data;
                if (num.doubleValue() < 0) {
                    throw new DataProcessingException(
                            "Negative number not allowed: " + num, 
                            data
                    );
                }
            }
            
            System.out.println("Successfully processed: " + data + " of type: " + typeInfo);
        }
    }

    // ======================================================================
    // PRACTICE 3: CUSTOM EXCEPTION WITH METADATA
    // ======================================================================
    
    /**
     * A more sophisticated exception that stores metadata about what failed
     * This approach preserves all information without relying on runtime generics
     */
    
    static class ValidationException extends Exception {
        private final Object failedObject;
        private final String fieldName;
        private final String expectedType;
        private final String actualValue;

        public ValidationException(String message, 
                                   Object failedObject, 
                                   String fieldName,
                                   String expectedType,
                                   Object actualValue) {
            super(message);
            this.failedObject = failedObject;
            this.fieldName = fieldName;
            this.expectedType = expectedType;
            this.actualValue = actualValue != null ? actualValue.toString() : "null";
        }

        public Object getFailedObject() {
            return failedObject;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getExpectedType() {
            return expectedType;
        }

        public String getActualValue() {
            return actualValue;
        }

        @Override
        public String toString() {
            return "ValidationException{" +
                    "message='" + getMessage() + '\'' +
                    ", fieldName='" + fieldName + '\'' +
                    ", expectedType='" + expectedType + '\'' +
                    ", actualValue='" + actualValue + '\'' +
                    ", failedObjectType=" + 
                    (failedObject != null ? failedObject.getClass().getSimpleName() : "unknown") +
                    '}';
        }
    }

    // ======================================================================
    // PRACTICE 4: GENERIC VALIDATION METHOD THAT USES EXCEPTION METADATA
    // ======================================================================
    
    static class Validator {
        public <T> void validateNotNull(T value, String fieldName, String expectedType) 
                throws ValidationException {
            if (value == null) {
                throw new ValidationException(
                        "Field '" + fieldName + "' cannot be null. Expected: " + expectedType,
                        null,
                        fieldName,
                        expectedType,
                        null
                );
            }
        }

        public <T extends Comparable<T>> void validateRange(T value, T min, T max, 
                                                              String fieldName)
                throws ValidationException {
            if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
                throw new ValidationException(
                        "Field '" + fieldName + "' out of range: " + 
                        min + " to " + max,
                        value,
                        fieldName,
                        "Range: " + min + " - " + max,
                        value
                );
            }
        }
    }

    // ======================================================================
    // PRACTICE 5: USE CASES AND DEMONSTRATIONS
    // ======================================================================
    
    static void main(String[] args) {
        System.out.println("=== BEST PRACTICE DEMONSTRATIONS ===\n");

        // Demo 1: Using typed exception wrapper
        System.out.println("DEMO 1: Typed Exception Wrapper");
        GenericDataProcessor processor = new GenericDataProcessor();
        
        try {
            processor.processData(42);
            processor.processData("Hello");
            processor.processData(-5);
        } catch (DataProcessingException e) {
            System.out.println("Caught: " + e);
            System.out.println("  Data Type: " + 
                    (e.getDataType() != null ? e.getDataType().getSimpleName() : "unknown"));
            System.out.println("  Processed Data: " + e.getProcessedData());
        }

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Demo 2: Using validation exception
        System.out.println("DEMO 2: Validation Exception with Metadata");
        Validator validator = new Validator();

        try {
            validator.validateNotNull("ValidString", "username", "String");
            validator.validateRange(50, 0, 100, "age");
            validator.validateRange(150, 0, 100, "age");
        } catch (ValidationException e) {
            System.out.println("Caught: " + e);
            System.out.println("  Field: " + e.getFieldName());
            System.out.println("  Expected: " + e.getExpectedType());
            System.out.println("  Actual: " + e.getActualValue());
        }

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Demo 3: Generic method handling
        System.out.println("DEMO 3: Generic Method Exception Handling");
        
        try {
            String nullValue = null;
            processor.processData(nullValue);
        } catch (DataProcessingException e) {
            System.out.println("Caught null data: " + e.getProcessedData());
        }

        System.out.println("\n=== KEY TAKEAWAYS ===");
        System.out.println("1. Don't make exception classes generic");
        System.out.println("2. Extract type info at compile time");
        System.out.println("3. Store metadata in non-generic fields");
        System.out.println("4. Use generic METHODS instead of generic exceptions");
        System.out.println("5. Create specific exception types for different scenarios");
    }
}

