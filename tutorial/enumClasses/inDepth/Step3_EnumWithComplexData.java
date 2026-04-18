package me.niteshh.OPPs.tutorial.enumClasses.inDepth;

/**
 * STEP 3: ENUM WITH COMPLEX DATA AND OPERATIONS
 * 
 * This step shows advanced patterns:
 * - Enums with collections
 * - Enums with multiple constructors behavior
 * - Enums with business logic
 * - Strategy pattern using enums
 */

public class Step3_EnumWithComplexData {

    /**
     * HTTP Status codes enum
     * Shows how to encapsulate multiple data in enum
     */
    enum HttpStatus {
        // Constants with code, message, and description
        OK(200, "OK", "Request succeeded"),
        CREATED(201, "Created", "Resource created successfully"),
        BAD_REQUEST(400, "Bad Request", "Invalid request format"),
        UNAUTHORIZED(401, "Unauthorized", "Authentication required"),
        FORBIDDEN(403, "Forbidden", "Access denied"),
        NOT_FOUND(404, "Not Found", "Resource not found"),
        INTERNAL_ERROR(500, "Internal Server Error", "Server error occurred"),
        SERVICE_UNAVAILABLE(503, "Service Unavailable", "Service temporarily unavailable");

        // ============= FIELDS =============
        private int code;              // HTTP status code
        private String message;        // Status message
        private String description;    // Detailed description

        // ============= CONSTRUCTOR =============
        /**
         * Constructor receives all enum data
         */
        HttpStatus(int code, String message, String description) {
            this.code = code;
            this.message = message;
            this.description = description;
        }

        // ============= GETTER METHODS =============
        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getDescription() {
            return description;
        }

        // ============= BUSINESS LOGIC METHODS =============
        /**
         * Check if status indicates success (2xx)
         */
        public boolean isSuccess() {
            return code >= 200 && code < 300;
        }

        /**
         * Check if status indicates client error (4xx)
         */
        public boolean isClientError() {
            return code >= 400 && code < 500;
        }

        /**
         * Check if status indicates server error (5xx)
         */
        public boolean isServerError() {
            return code >= 500 && code < 600;
        }

        /**
         * Get full status description
         */
        public String getFullStatus() {
            return code + " " + message + ": " + description;
        }
    }

    /**
     * Database operation types with their SQL counterparts
     */
    enum DatabaseOperation {
        // Each operation has a name and corresponding SQL keyword
        CREATE("INSERT", "CREATE - Insert new record"),
        READ("SELECT", "READ - Retrieve record"),
        UPDATE("UPDATE", "UPDATE - Modify existing record"),
        DELETE("DELETE", "DELETE - Remove record");

        private String sqlKeyword;
        private String operationName;

        DatabaseOperation(String sqlKeyword, String operationName) {
            this.sqlKeyword = sqlKeyword;
            this.operationName = operationName;
        }

        public String getSqlKeyword() {
            return sqlKeyword;
        }

        public String getOperationName() {
            return operationName;
        }

        // ============= STRATEGY PATTERN =============
        /**
         * Execute operation based on type
         * Shows strategy pattern implementation
         */
        public void executeOperation(String target) {
            switch (this) {
                case CREATE:
                    // Simulate INSERT operation
                    System.out.println("    Executing: " + sqlKeyword + " INTO " + target);
                    break;
                case READ:
                    // Simulate SELECT operation
                    System.out.println("    Executing: " + sqlKeyword + " FROM " + target);
                    break;
                case UPDATE:
                    // Simulate UPDATE operation
                    System.out.println("    Executing: " + sqlKeyword + " " + target);
                    break;
                case DELETE:
                    // Simulate DELETE operation
                    System.out.println("    Executing: " + sqlKeyword + " FROM " + target);
                    break;
            }
        }
    }

    /**
     * File type enum with extensions and descriptions
     */
    enum FileType {
        // Different file types with extensions
        JAVA(".java", "text/plain", "Java Source Code"),
        CLASS(".class", "application/octet-stream", "Java Compiled Class"),
        DOCUMENT(".pdf", "application/pdf", "PDF Document"),
        IMAGE(".png", "image/png", "PNG Image"),
        VIDEO(".mp4", "video/mp4", "MP4 Video");

        private String extension;
        private String mimeType;
        private String description;

        FileType(String extension, String mimeType, String description) {
            this.extension = extension;
            this.mimeType = mimeType;
            this.description = description;
        }

        public String getExtension() {
            return extension;
        }

        public String getMimeType() {
            return mimeType;
        }

        public String getDescription() {
            return description;
        }

        /**
         * Get file information formatted
         */
        public String getFileInfo() {
            return description + " (" + extension + ") - MIME: " + mimeType;
        }

        /**
         * Check if file is a document type
         */
        public boolean isDocument() {
            return this == DOCUMENT || this == JAVA;
        }

        /**
         * Check if file is a media type
         */
        public boolean isMedia() {
            return this == IMAGE || this == VIDEO;
        }
    }

    // ============= MAIN METHOD =============

    public static void main(String[] args) {
        System.out.println("===== STEP 3: ENUM WITH COMPLEX DATA =====\n");

        // ============= 1. HTTP STATUS ENUM DEMONSTRATION =============
        System.out.println("1️⃣  HTTP STATUS CODES:\n");

        // Access enum with multiple fields
        HttpStatus status = HttpStatus.OK;
        System.out.println("  Status: " + status.getFullStatus());

        status = HttpStatus.NOT_FOUND;
        System.out.println("  Status: " + status.getFullStatus());

        // ============= 2. BUSINESS LOGIC WITH ENUMS =============
        System.out.println("\n2️⃣  BUSINESS LOGIC - CATEGORIZING STATUS:\n");

        HttpStatus[] statuses = {
            HttpStatus.OK,
            HttpStatus.CREATED,
            HttpStatus.BAD_REQUEST,
            HttpStatus.INTERNAL_ERROR
        };

        System.out.println("  Success responses (2xx):");
        for (HttpStatus s : statuses) {
            if (s.isSuccess()) {
                System.out.println("    - " + s.getCode() + " " + s.getMessage());
            }
        }

        System.out.println("\n  Error responses:");
        for (HttpStatus s : statuses) {
            if (s.isClientError() || s.isServerError()) {
                System.out.println("    - " + s.getCode() + " " + s.getMessage());
            }
        }

        // ============= 3. DATABASE OPERATIONS =============
        System.out.println("\n3️⃣  DATABASE OPERATIONS:\n");

        DatabaseOperation createOp = DatabaseOperation.CREATE;
        System.out.println("  Operation: " + createOp.getOperationName());
        System.out.println("  SQL Keyword: " + createOp.getSqlKeyword());
        createOp.executeOperation("users");

        // ============= 4. ALL DATABASE OPERATIONS =============
        System.out.println("\n4️⃣  ALL CRUD OPERATIONS:\n");

        for (DatabaseOperation op : DatabaseOperation.values()) {
            System.out.println("  " + op + ":");
            op.executeOperation("employee");
        }

        // ============= 5. FILE TYPE CLASSIFICATION =============
        System.out.println("\n5️⃣  FILE TYPE ENUM:\n");

        FileType fileType = FileType.JAVA;
        System.out.println("  File: " + fileType);
        System.out.println("  Info: " + fileType.getFileInfo());
        System.out.println("  Is Document? " + fileType.isDocument());

        // ============= 6. FILE TYPE FILTERING =============
        System.out.println("\n6️⃣  FILTERING FILES BY TYPE:\n");

        System.out.println("  Document files:");
        for (FileType ft : FileType.values()) {
            if (ft.isDocument()) {
                System.out.println("    - " + ft + ": " + ft.getExtension());
            }
        }

        System.out.println("\n  Media files:");
        for (FileType ft : FileType.values()) {
            if (ft.isMedia()) {
                System.out.println("    - " + ft + ": " + ft.getExtension());
            }
        }

        // ============= 7. HTTP STATUS CATEGORY LOOKUP =============
        System.out.println("\n7️⃣  FINDING SPECIFIC STATUS:\n");

        int statusCode = 404;
        HttpStatus found = findStatusByCode(statusCode);
        if (found != null) {
            System.out.println("  Status code " + statusCode + ": " + found);
            System.out.println("  Full: " + found.getFullStatus());
        }

        // ============= 8. STATUS STATISTICS =============
        System.out.println("\n8️⃣  STATUS STATISTICS:\n");

        int successCount = 0;
        int errorCount = 0;

        for (HttpStatus s : HttpStatus.values()) {
            if (s.isSuccess()) {
                successCount++;
            } else if (s.isClientError() || s.isServerError()) {
                errorCount++;
            }
        }

        System.out.println("  Total HTTP statuses: " + HttpStatus.values().length);
        System.out.println("  Success statuses: " + successCount);
        System.out.println("  Error statuses: " + errorCount);

        // ============= 9. MIME TYPE LOOKUP =============
        System.out.println("\n9️⃣  MIME TYPE LOOKUP:\n");

        String fileName = "document.pdf";
        FileType result = findFileTypeByExtension("." + getExtension(fileName));
        if (result != null) {
            System.out.println("  File: " + fileName);
            System.out.println("  MIME Type: " + result.getMimeType());
            System.out.println("  Description: " + result.getDescription());
        }

        // ============= 10. ENUM WITH COMPLEX DATA SUMMARY =============
        System.out.println("\n🔟  KEY BENEFITS:\n");

        /*
         * Benefits of enum with complex data:
         * 
         * 1. Type Safety: Can't accidentally use wrong status code
         * 2. Encapsulation: Related data stays together
         * 3. Business Logic: Methods implement behavior per type
         * 4. Strategy Pattern: Different behavior per enum value
         * 5. Maintainability: Changes in one place affect all usage
         * 6. Performance: Singleton per enum constant, thread-safe
         */

        System.out.println("  ✓ Encapsulates related data together");
        System.out.println("  ✓ Business logic methods per type");
        System.out.println("  ✓ Type-safe filtering and lookup");
        System.out.println("  ✓ Easy to extend with new types");
        System.out.println("  ✓ Thread-safe singleton instances");
    }

    // ============= HELPER METHODS =============

    /**
     * Find HTTP status by code
     */
    static HttpStatus findStatusByCode(int code) {
        for (HttpStatus status : HttpStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;  // Not found
    }

    /**
     * Find file type by extension
     */
    static FileType findFileTypeByExtension(String extension) {
        for (FileType ft : FileType.values()) {
            if (ft.getExtension().equalsIgnoreCase(extension)) {
                return ft;
            }
        }
        return null;  // Not found
    }

    /**
     * Extract file extension from filename
     */
    static String getExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
            return filename.substring(lastDot);
        }
        return "";
    }
}

