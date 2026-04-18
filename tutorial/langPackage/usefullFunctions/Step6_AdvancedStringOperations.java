package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

/**
 * STEP 6: ADVANCED STRING OPERATIONS
 * 
 * More complex and powerful String operations for real-world scenarios.
 * StringBuilder, String formatting, and regex patterns.
 * 
 * Key Concepts:
 * - StringBuilder for efficient string building
 * - String formatting and interpolation
 * - Regular expressions for pattern matching
 * - Text processing techniques
 */

public class Step6_AdvancedStringOperations {

    public static void main(String[] args) {
        System.out.println("===== STEP 6: ADVANCED STRING OPERATIONS =====\n");

        // ============= 1. STRING IMMUTABILITY PROBLEM =============
        System.out.println("1️⃣  STRING IMMUTABILITY - THE PROBLEM:\n");

        // Inefficient: Creates multiple String objects
        String inefficient = "";
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 1000; i++) {
            inefficient += i;  // Creates new String object each time!
        }
        
        long inefficientTime = System.nanoTime() - startTime;

        System.out.println("  String concatenation in loop: " + (inefficientTime / 1_000_000) + "ms");
        System.out.println("  Problem: Each += creates new String, old one becomes garbage");

        // ============= 2. STRINGBUILDER - THE SOLUTION =============
        System.out.println("\n2️⃣  STRINGBUILDER - EFFICIENT BUILDING:\n");

        StringBuilder efficient = new StringBuilder();
        startTime = System.nanoTime();
        
        for (int i = 0; i < 1000; i++) {
            efficient.append(i);  // Just appends, no new objects
        }
        
        long efficientTime = System.nanoTime() - startTime;

        System.out.println("  StringBuilder in loop: " + (efficientTime / 1_000_000) + "ms");
        System.out.println("  Improvement: " + (inefficientTime / efficientTime) + "x faster");

        System.out.println("\n  ✓ Key insight: StringBuilder is MUCH faster for building strings");

        // ============= 3. STRINGBUILDER METHODS =============
        System.out.println("\n3️⃣  STRINGBUILDER METHODS:\n");

        StringBuilder sb = new StringBuilder("Hello");
        System.out.println("  Initial: \"" + sb + "\"");

        // append() - Add text to end
        sb.append(" World");
        System.out.println("  After append(\" World\"): \"" + sb + "\"");

        // insert() - Insert text at position
        sb.insert(5, " Beautiful");
        System.out.println("  After insert(5, \" Beautiful\"): \"" + sb + "\"");

        // delete() - Delete range
        sb.delete(5, 15);
        System.out.println("  After delete(5, 15): \"" + sb + "\"");

        // replace() - Replace range
        sb.replace(6, 11, "Java");
        System.out.println("  After replace(6, 11, \"Java\"): \"" + sb + "\"");

        // reverse() - Reverse string
        StringBuilder rev = new StringBuilder("Hello");
        rev.reverse();
        System.out.println("  \"Hello\".reverse(): \"" + rev + "\"");

        // ============= 4. STRINGBUILDER VS STRINGBUFFER =============
        System.out.println("\n4️⃣  STRINGBUILDER VS STRINGBUFFER:\n");

        System.out.println("  StringBuilder:");
        System.out.println("  - Not synchronized (faster, single-threaded)");
        System.out.println("  - Use for most applications");

        System.out.println("\n  StringBuffer:");
        System.out.println("  - Synchronized (thread-safe)");
        System.out.println("  - Use in multi-threaded environments");

        // ============= 5. STRING FORMATTING =============
        System.out.println("\n5️⃣  STRING FORMATTING:\n");

        String name = "Alice";
        int age = 25;
        double salary = 75000.50;

        // Using String.format()
        String formatted = String.format("Name: %s, Age: %d, Salary: $%.2f", name, age, salary);
        System.out.println("  String.format(): " + formatted);

        // Using printf()
        System.out.println("\n  System.out.printf():");
        System.out.printf("  Name: %-10s Age: %3d Salary: $%8.2f%n", name, age, salary);

        // Format specifiers
        System.out.println("\n  Common format specifiers:");
        System.out.println("  %s - String");
        System.out.println("  %d - Integer");
        System.out.println("  %f - Float/Double");
        System.out.println("  %b - Boolean");
        System.out.println("  %x - Hexadecimal");

        // ============= 6. REGULAR EXPRESSIONS =============
        System.out.println("\n6️⃣  REGULAR EXPRESSIONS:\n");

        String text = "My email is john@example.com and bob@test.org";

        // matches() - Check if entire string matches pattern
        String email = "user@example.com";
        boolean isEmail = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        System.out.println("  Is \"" + email + "\" an email? " + isEmail);

        // replaceAll() - Replace all matches
        String withoutNumbers = text.replaceAll("\\d", "X");
        System.out.println("  Replace digits: " + withoutNumbers);

        // split() with regex
        String csv = "apple,banana,cherry";
        String[] fruits = csv.split(",");
        System.out.println("  Split \"" + csv + "\":");
        for (String fruit : fruits) {
            System.out.println("    - " + fruit);
        }

        // ============= 7. PRACTICAL REGEX PATTERNS =============
        System.out.println("\n7️⃣  PRACTICAL REGEX PATTERNS:\n");

        System.out.println("  Email pattern: ^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        System.out.println("  Phone (US): ^\\d{3}-\\d{3}-\\d{4}$");
        System.out.println("  URL: ^https?://.*");
        System.out.println("  Digits only: ^\\d+$");
        System.out.println("  No spaces: ^\\S+$");

        // ============= 8. STRING COMPARISON - DEEP DIVE =============
        System.out.println("\n8️⃣  ADVANCED STRING COMPARISON:\n");

        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        System.out.println("  str1 = \"Hello\" (literal)");
        System.out.println("  str2 = \"Hello\" (literal)");
        System.out.println("  str3 = new String(\"Hello\")");

        System.out.println("\n  str1 == str2: " + (str1 == str2) + " (same reference in pool)");
        System.out.println("  str1 == str3: " + (str1 == str3) + " (different references)");
        System.out.println("  str1.equals(str3): " + str1.equals(str3) + " (same content)");

        System.out.println("\n  ✓ ALWAYS use equals(), never use == for String comparison");

        // ============= 9. STRING METHODS - COMPLETE REFERENCE =============
        System.out.println("\n9️⃣  STRING METHODS REFERENCE:\n");

        String reference = "Java Programming";

        // Case methods
        System.out.println("  Basics:");
        System.out.println("    length(): " + reference.length());
        System.out.println("    charAt(0): " + reference.charAt(0));
        System.out.println("    indexOf('r'): " + reference.indexOf('r'));
        System.out.println("    substring(0, 4): " + reference.substring(0, 4));

        // Utility methods
        System.out.println("\n  Utilities:");
        System.out.println("    isEmpty(): " + reference.isEmpty());
        System.out.println("    contains(\"Program\"): " + reference.contains("Program"));
        System.out.println("    startsWith(\"Java\"): " + reference.startsWith("Java"));
        System.out.println("    endsWith(\"ing\"): " + reference.endsWith("ing"));

        // ============= 10. PRACTICAL EXAMPLE: TEXT PROCESSING =============
        System.out.println("\n🔟  PRACTICAL EXAMPLE: CSV Processing:\n");

        processCsvData();

        // ============= 11. PRACTICAL EXAMPLE: LOG MESSAGE BUILDER =============
        System.out.println("\n1️⃣1️⃣  PRACTICAL EXAMPLE: Log Message Builder:\n");

        buildLogMessages();

        // ============= SUMMARY =====
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Use StringBuilder for efficient string building");
        System.out.println("✓ String.format() for formatted strings");
        System.out.println("✓ Regex for pattern matching and validation");
        System.out.println("✓ Always use equals() for String comparison");
        System.out.println("✓ Regular expressions are powerful but can be slow");
    }

    // ============= HELPER METHODS =============

    /**
     * Process CSV data
     */
    static void processCsvData() {
        String csvLine = "John,Doe,john@example.com,25";
        String[] parts = csvLine.split(",");

        System.out.println("  CSV: " + csvLine);
        System.out.println("  Parsed:");
        System.out.println("    First Name: " + parts[0]);
        System.out.println("    Last Name: " + parts[1]);
        System.out.println("    Email: " + parts[2]);
        System.out.println("    Age: " + parts[3]);
    }

    /**
     * Build log messages efficiently
     */
    static void buildLogMessages() {
        StringBuilder logBuilder = new StringBuilder();

        // Build complex log message
        String timestamp = System.currentTimeMillis() + "";
        String level = "INFO";
        String component = "UserService";
        String message = "User login successful";

        logBuilder.append("[").append(timestamp).append("] ");
        logBuilder.append("[").append(level).append("] ");
        logBuilder.append("[").append(component).append("] ");
        logBuilder.append(message);

        System.out.println("  Log Message: " + logBuilder.toString());
        System.out.println("  Length: " + logBuilder.length() + " characters");
    }
}

