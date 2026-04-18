package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

/**
 * STEP 1: STRING CLASS - FOUNDATION
 * 
 * String is one of the most used classes in java.lang package.
 * This step covers fundamental String operations that every developer needs to know.
 * 
 * Key Concepts:
 * - String is immutable (cannot be changed after creation)
 * - String methods return new String objects
 * - String pool optimization in Java
 */

public class Step1_StringClass_Basics {

    public static void main(String[] args) {
        System.out.println("===== STEP 1: STRING CLASS - FOUNDATION =====\n");

        // ============= 1. STRING CREATION =============
        System.out.println("1️⃣  STRING CREATION:\n");

        // Method 1: String literal (uses String pool)
        String str1 = "Hello Java";
        System.out.println("  String literal: " + str1);

        // Method 2: Using new keyword (creates new object in heap)
        String str2 = new String("Hello Java");
        System.out.println("  Using new: " + str2);

        // Method 3: Converting from char array
        char[] chars = {'J', 'a', 'v', 'a'};
        String str3 = new String(chars);
        System.out.println("  From char array: " + str3);

        // ============= 2. BASIC STRING METHODS =============
        System.out.println("\n2️⃣  BASIC STRING METHODS:\n");

        String text = "Welcome to Java Programming";

        // length() - Returns the length of the string
        System.out.println("  text = \"" + text + "\"");
        System.out.println("  text.length() = " + text.length());
        System.out.println("  Purpose: Get the number of characters");

        // charAt(index) - Returns character at specific index
        System.out.println("\n  text.charAt(0) = '" + text.charAt(0) + "'");
        System.out.println("  text.charAt(11) = '" + text.charAt(11) + "'");
        System.out.println("  Purpose: Access individual character");

        // substring(beginIndex) - Returns substring from beginIndex to end
        System.out.println("\n  text.substring(11) = \"" + text.substring(11) + "\"");
        System.out.println("  Purpose: Extract part of string from index to end");

        // substring(beginIndex, endIndex) - Returns substring between indices
        System.out.println("\n  text.substring(11, 15) = \"" + text.substring(11, 15) + "\"");
        System.out.println("  Purpose: Extract substring between two indices (endIndex exclusive)");

        // ============= 3. CASE CONVERSION =============
        System.out.println("\n3️⃣  CASE CONVERSION:\n");

        String mixedCase = "Java Programming";

        // toUpperCase() - Converts to uppercase
        System.out.println("  Original: \"" + mixedCase + "\"");
        System.out.println("  toUpperCase(): \"" + mixedCase.toUpperCase() + "\"");
        System.out.println("  Purpose: Convert all characters to uppercase");

        // toLowerCase() - Converts to lowercase
        System.out.println("\n  Original: \"" + mixedCase + "\"");
        System.out.println("  toLowerCase(): \"" + mixedCase.toLowerCase() + "\"");
        System.out.println("  Purpose: Convert all characters to lowercase");

        // ============= 4. SEARCHING AND FINDING =============
        System.out.println("\n4️⃣  SEARCHING AND FINDING:\n");

        String searchText = "Hello World Hello Java";

        // indexOf(String) - Finds first occurrence index
        System.out.println("  text = \"" + searchText + "\"");
        System.out.println("  indexOf(\"Hello\") = " + searchText.indexOf("Hello"));
        System.out.println("  Purpose: Find first occurrence of substring");

        // indexOf(String, fromIndex) - Finds first occurrence from specific position
        System.out.println("\n  indexOf(\"Hello\", 6) = " + searchText.indexOf("Hello", 6));
        System.out.println("  Purpose: Find first occurrence starting from position 6");

        // lastIndexOf(String) - Finds last occurrence
        System.out.println("\n  lastIndexOf(\"Hello\") = " + searchText.lastIndexOf("Hello"));
        System.out.println("  Purpose: Find last occurrence of substring");

        // contains(String) - Checks if contains substring
        System.out.println("\n  contains(\"World\") = " + searchText.contains("World"));
        System.out.println("  contains(\"Python\") = " + searchText.contains("Python"));
        System.out.println("  Purpose: Check if string contains a substring");

        // ============= 5. COMPARISON METHODS =============
        System.out.println("\n5️⃣  COMPARISON METHODS:\n");

        String str_a = "Java";
        String str_b = "Java";
        String str_c = "JAVA";

        // equals() - Case-sensitive comparison
        System.out.println("  str_a = \"" + str_a + "\"");
        System.out.println("  str_b = \"" + str_b + "\"");
        System.out.println("  str_a.equals(str_b) = " + str_a.equals(str_b));
        System.out.println("  Purpose: Compare strings for exact equality");

        // equalsIgnoreCase() - Case-insensitive comparison
        System.out.println("\n  str_a = \"" + str_a + "\"");
        System.out.println("  str_c = \"" + str_c + "\"");
        System.out.println("  str_a.equalsIgnoreCase(str_c) = " + str_a.equalsIgnoreCase(str_c));
        System.out.println("  Purpose: Compare strings ignoring case");

        // compareTo() - Lexicographic comparison
        System.out.println("\n  str_a.compareTo(str_b) = " + str_a.compareTo(str_b) + " (equal)");
        System.out.println("  str_a.compareTo(\"Zebra\") = " + str_a.compareTo("Zebra") + " (negative = smaller)");
        System.out.println("  str_a.compareTo(\"Apple\") = " + str_a.compareTo("Apple") + " (positive = larger)");
        System.out.println("  Purpose: Compare strings lexicographically");

        // ============= 6. WHITESPACE OPERATIONS =============
        System.out.println("\n6️⃣  WHITESPACE OPERATIONS:\n");

        String paddedText = "  Java  ";

        // trim() - Removes leading and trailing whitespace
        System.out.println("  Original: \"" + paddedText + "\"");
        System.out.println("  trim(): \"" + paddedText.trim() + "\"");
        System.out.println("  Purpose: Remove leading and trailing spaces");

        // strip() - Modern version of trim() (Java 11+)
        System.out.println("\n  strip(): \"" + paddedText.strip() + "\"");
        System.out.println("  Purpose: Remove all types of whitespace (more powerful than trim)");

        // ============= 7. REPLACEMENT OPERATIONS =============
        System.out.println("\n7️⃣  REPLACEMENT OPERATIONS:\n");

        String original = "Hello World Hello";

        // replace(oldChar, newChar) - Replace all occurrences
        System.out.println("  Original: \"" + original + "\"");
        System.out.println("  replace('o', '0'): \"" + original.replace('o', '0') + "\"");
        System.out.println("  Purpose: Replace all occurrences of character");

        // replace(oldString, newString) - Replace all occurrences of substring
        System.out.println("\n  Original: \"" + original + "\"");
        System.out.println("  replace(\"Hello\", \"Hi\"): \"" + original.replace("Hello", "Hi") + "\"");
        System.out.println("  Purpose: Replace all occurrences of substring");

        // replaceFirst(regex, replacement) - Replace first match
        System.out.println("\n  Original: \"" + original + "\"");
        System.out.println("  replaceFirst(\"Hello\", \"Hi\"): \"" + original.replaceFirst("Hello", "Hi") + "\"");
        System.out.println("  Purpose: Replace only first occurrence");

        // ============= 8. SPLITTING AND JOINING =============
        System.out.println("\n8️⃣  SPLITTING AND JOINING:\n");

        String csv = "Java,Python,JavaScript,C++";

        // split(delimiter) - Split string into array
        System.out.println("  Original: \"" + csv + "\"");
        String[] languages = csv.split(",");
        System.out.print("  split(\",\"): ");
        for (String lang : languages) {
            System.out.print("\"" + lang + "\" ");
        }
        System.out.println("\n  Purpose: Split string into array based on delimiter");

        // ============= 9. CHECKING PROPERTIES =============
        System.out.println("\n9️⃣  CHECKING PROPERTIES:\n");

        String empty = "";
        String blank = "   ";
        String normal = "Java";

        // isEmpty() - Checks if length is 0
        System.out.println("  empty.isEmpty() = " + empty.isEmpty());
        System.out.println("  normal.isEmpty() = " + normal.isEmpty());
        System.out.println("  Purpose: Check if string has zero length");

        // isBlank() - Checks if only whitespace (Java 11+)
        System.out.println("\n  blank.isBlank() = " + blank.isBlank());
        System.out.println("  normal.isBlank() = " + normal.isBlank());
        System.out.println("  Purpose: Check if string is empty or contains only whitespace");

        // startsWith(prefix) - Checks if starts with substring
        System.out.println("\n  normal.startsWith(\"Jav\") = " + normal.startsWith("Jav"));
        System.out.println("  Purpose: Check if string starts with prefix");

        // endsWith(suffix) - Checks if ends with substring
        System.out.println("\n  normal.endsWith(\"va\") = " + normal.endsWith("va"));
        System.out.println("  Purpose: Check if string ends with suffix");

        // ============= 10. CASE STUDY: PRACTICAL EXAMPLE =============
        System.out.println("\n🔟  PRACTICAL EXAMPLE:\n");

        String email = "  USER@GMAIL.COM  ";

        // Normalize email input
        String normalizedEmail = email.trim().toLowerCase();
        System.out.println("  Original email: \"" + email + "\"");
        System.out.println("  Normalized: \"" + normalizedEmail + "\"");

        // Extract domain
        int atIndex = normalizedEmail.indexOf("@");
        String domain = normalizedEmail.substring(atIndex + 1);
        System.out.println("  Domain: " + domain);

        // Validate
        boolean isValid = normalizedEmail.contains("@") && normalizedEmail.contains(".");
        System.out.println("  Is valid: " + isValid);

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ String is immutable - methods return new String objects");
        System.out.println("✓ Use equals() or equalsIgnoreCase() for comparison");
        System.out.println("✓ substring() is commonly used for extracting parts");
        System.out.println("✓ split() is useful for parsing delimited data");
        System.out.println("✓ String operations are foundation for any Java application");
    }
}

