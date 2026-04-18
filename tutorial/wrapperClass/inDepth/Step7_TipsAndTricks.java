package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * STEP 7: TIPS & TRICKS FOR WORKING WITH WRAPPER CLASSES
 * 
 * Collection of best practices, optimization techniques, and clever tricks
 * for working effectively with wrapper classes in real-world scenarios
 */

public class Step7_TipsAndTricks {

    public static void main(String[] args) {
        System.out.println("===== STEP 7: TIPS & TRICKS FOR WRAPPER CLASSES =====\n");

        // ============= TIP 1: Using Optional Instead of Null =============
        System.out.println("1. TIP: Use Optional Instead of Null Wrapper Objects\n");

        System.out.println("  Problem: Null wrapper objects can cause NullPointerException");
        System.out.println("  Solution: Use java.util.Optional (Java 8+)\n");

        // OLD WAY: Null wrapper objects
        Integer nullValue = null;
        System.out.println("  ❌ OLD WAY (null wrapper):");
        System.out.println("    Integer nullValue = null;");
        if (nullValue != null) {
            System.out.println("    Value: " + nullValue);
        } else {
            System.out.println("    Value is null");
        }

        // NEW WAY: Using Optional
        Optional<Integer> optionalValue = Optional.of(50);
        System.out.println("\n  ✓ NEW WAY (Optional):");
        System.out.println("    Optional<Integer> optionalValue = Optional.of(50);");
        optionalValue.ifPresent(val -> System.out.println("    Value: " + val));

        // Empty Optional
        Optional<Integer> emptyOptional = Optional.empty();
        System.out.println("    Optional<Integer> emptyOptional = Optional.empty();");
        emptyOptional.ifPresentOrElse(
            val -> System.out.println("    Value: " + val),
            () -> System.out.println("    No value present")
        );

        // ============= TIP 2: String Conversion Tricks =============
        System.out.println("\n2. TIP: String Conversion Techniques\n");

        // Multiple ways to convert wrapper to String
        Integer value = 100;
        System.out.println("  Converting Integer to String:");
        System.out.println("    value.toString()        = " + value.toString());
        System.out.println("    String.valueOf(value)   = " + String.valueOf(value));
        System.out.println("    \"\" + value             = " + ("" + value));
        System.out.println("    Integer.toString(value) = " + Integer.toString(value));

        // String to wrapper
        String strValue = "250";
        System.out.println("\n  Converting String to Integer:");
        System.out.println("    Integer.valueOf(\"250\")    = " + Integer.valueOf(strValue));
        System.out.println("    Integer.parseInt(\"250\")   = " + Integer.parseInt(strValue));

        // Safe parsing with default value
        System.out.println("\n  Safe parsing with default value:");
        String invalidStr = "abc";
        Integer parsed = parseIntSafe(invalidStr, 0);
        System.out.println("    parseIntSafe(\"abc\", 0) = " + parsed);

        // ============= TIP 3: Caching Trick for Performance =============
        System.out.println("\n3. TIP: Leverage Caching for Performance\n");

        System.out.println("  Problem: Creating wrapper objects has overhead");
        System.out.println("  Solution: Use valueOf() to leverage cache for -128 to 127\n");

        // Inefficient: Always creates new objects
        System.out.println("  ❌ Less Efficient:");
        long startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            Integer obj = new Integer(100);  // Always creates new object
        }
        long duration1 = System.nanoTime() - startTime;
        System.out.println("    new Integer(100) x 10000: " + duration1 + " ns");

        // Efficient: Uses cached object
        System.out.println("\n  ✓ More Efficient:");
        startTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            Integer obj = Integer.valueOf(100);  // Reuses cached object
        }
        long duration2 = System.nanoTime() - startTime;
        System.out.println("    Integer.valueOf(100) x 10000: " + duration2 + " ns");
        System.out.println("    Speed improvement: " + String.format("%.2f", (double) duration1 / duration2) + "x");

        // ============= TIP 4: Comparing Multiple Conditions =============
        System.out.println("\n4. TIP: Efficient Comparison of Multiple Wrapper Values\n");

        Integer age = 25;
        Integer minAge = 18;
        Integer maxAge = 65;

        System.out.println("  Checking if age is within range:");
        System.out.println("    Age: " + age);
        System.out.println("    Min: " + minAge + ", Max: " + maxAge);

        // Using compareTo()
        boolean isValid = age.compareTo(minAge) >= 0 && age.compareTo(maxAge) <= 0;
        System.out.println("    age.compareTo(minAge) >= 0: " + (age.compareTo(minAge) >= 0));
        System.out.println("    age.compareTo(maxAge) <= 0: " + (age.compareTo(maxAge) <= 0));
        System.out.println("    Result: " + isValid);

        // ============= TIP 5: Working with Collections =============
        System.out.println("\n5. TIP: Efficient Collection Operations\n");

        List<Integer> numbers = new ArrayList<>();
        numbers.add(50);
        numbers.add(100);
        numbers.add(75);
        numbers.add(null);
        numbers.add(200);

        System.out.println("  Original list: " + numbers);

        // Remove null values safely
        List<Integer> cleanedList = new ArrayList<>(numbers);
        cleanedList.removeIf(num -> num == null);
        System.out.println("  After removing nulls: " + cleanedList);

        // Find sum using streams
        int sum = cleanedList.stream().mapToInt(Integer::intValue).sum();
        System.out.println("  Sum of values: " + sum);

        // Find average
        double average = cleanedList.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
        System.out.println("  Average: " + average);

        // ============= TIP 6: Null-Safe Operations =============
        System.out.println("\n6. TIP: Null-Safe Operations\n");

        Integer value1 = null;
        Integer value2 = 50;

        System.out.println("  value1 = null, value2 = 50");

        // Safe addition
        int result = nullSafeAdd(value1, value2);
        System.out.println("  nullSafeAdd(null, 50) = " + result);

        // Safe multiplication
        int multiResult = nullSafeMultiply(value1, value2);
        System.out.println("  nullSafeMultiply(null, 50) = " + multiResult);

        // ============= TIP 7: Sorting Wrapper Objects =============
        System.out.println("\n7. TIP: Sorting Wrapper Collections\n");

        List<Integer> unsortedList = new ArrayList<>();
        unsortedList.add(50);
        unsortedList.add(10);
        unsortedList.add(100);
        unsortedList.add(25);

        System.out.println("  Original: " + unsortedList);
        unsortedList.sort(Integer::compare);
        System.out.println("  Sorted ascending: " + unsortedList);

        unsortedList.sort((a, b) -> b.compareTo(a));
        System.out.println("  Sorted descending: " + unsortedList);

        // ============= TIP 8: Type Checking and Casting =============
        System.out.println("\n8. TIP: Safe Type Checking\n");

        Object[] objects = {100, "hello", 3.14, true, null};
        System.out.println("  Filtering integers from mixed object array:");

        for (Object obj : objects) {
            if (obj instanceof Integer) {
                Integer intValue = (Integer) obj;
                System.out.println("    Found Integer: " + intValue);
            }
        }

        // ============= TIP 9: Using Ternary Operator for Default Values =============
        System.out.println("\n9. TIP: Ternary Operator for Default Values\n");

        Integer maybeNull = null;
        int defaultValue = 0;

        System.out.println("  maybeNull = " + maybeNull);
        System.out.println("  Ternary: " + (maybeNull != null ? maybeNull : defaultValue));

        // Even better: Use Objects.requireNonNullElse (Java 9+)
        // int value = Objects.requireNonNullElse(maybeNull, defaultValue);

        // ============= TIP 10: Wrapper Class Conversion Chain =============
        System.out.println("\n10. TIP: Converting Between Wrapper Types\n");

        Integer intValue = 100;
        System.out.println("  Starting with Integer: " + intValue);

        // Convert to various types
        Long longValue = intValue.longValue();
        System.out.println("  -> Long: " + longValue);

        Double doubleValue = longValue.doubleValue();
        System.out.println("  -> Double: " + doubleValue);

        String stringValue = doubleValue.toString();
        System.out.println("  -> String: " + stringValue);

        Integer backToInt = Integer.parseInt(stringValue.split("\\.")[0]);
        System.out.println("  -> Integer (back): " + backToInt);

        // ============= TIP 11: Wrapper Class Validation Patterns =============
        System.out.println("\n11. TIP: Common Validation Patterns\n");

        Integer age1 = 25;
        Integer age2 = null;
        Integer age3 = -5;

        System.out.println("  Validating ages:");
        System.out.println("    age1 (25): " + isValidAge(age1));
        System.out.println("    age2 (null): " + isValidAge(age2));
        System.out.println("    age3 (-5): " + isValidAge(age3));

        // ============= TIP 12: Using Wrapper Classes with Maps =============
        System.out.println("\n12. TIP: Efficient Map Operations with Wrapper Objects\n");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", null);  // Some students haven't taken test yet

        System.out.println("  Student scores map: " + scores);

        // Get with default
        int aliceScore = scores.getOrDefault("Alice", 0);
        System.out.println("  getOrDefault(\"Alice\", 0) = " + aliceScore);

        int charlieScore = scores.getOrDefault("Charlie", 0);
        System.out.println("  getOrDefault(\"Charlie\", 0) = " + charlieScore);

        // Compute if absent
        scores.putIfAbsent("Charlie", 88);
        System.out.println("  After putIfAbsent(\"Charlie\", 88): " + scores);

        // ============= TIP 13: Memory Efficiency =============
        System.out.println("\n13. TIP: Memory Considerations\n");

        System.out.println("  Memory sizes (approximate):");
        System.out.println("    Primitive int: 4 bytes");
        System.out.println("    Integer object: 16 bytes (on 64-bit JVM)");
        System.out.println("    Wrapper overhead: ~75% more memory");

        System.out.println("\n  When to use wrapper classes:");
        System.out.println("    ✓ Collections and APIs requiring objects");
        System.out.println("    ✓ When you need null values");
        System.out.println("    ✓ When you need utility methods");

        System.out.println("\n  When to use primitives:");
        System.out.println("    ✓ Performance-critical code");
        System.out.println("    ✓ Memory-constrained environments");
        System.out.println("    ✓ Simple calculations and loops");

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY OF TIPS & TRICKS =====");
        System.out.println("✓ Use Optional for null-safety instead of null wrappers");
        System.out.println("✓ Use valueOf() for -128 to 127 range (leverages cache)");
        System.out.println("✓ Always use equals() for comparing wrapper values");
        System.out.println("✓ Check for null before unboxing to primitive");
        System.out.println("✓ Use primitives in performance-critical loops");
        System.out.println("✓ Use wrapper classes with collections");
        System.out.println("✓ Handle NumberFormatException when parsing strings");
        System.out.println("✓ Use ternary operator for providing default values");
        System.out.println("✓ Use streams for filtering and processing wrapper collections");
        System.out.println("✓ Be aware of memory overhead of wrapper objects");
    }

    // ============= HELPER METHODS =============

    /**
     * Safe integer parsing with default value
     */
    public static Integer parseIntSafe(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Null-safe addition
     */
    public static int nullSafeAdd(Integer a, Integer b) {
        int valA = (a != null) ? a : 0;
        int valB = (b != null) ? b : 0;
        return valA + valB;
    }

    /**
     * Null-safe multiplication
     */
    public static int nullSafeMultiply(Integer a, Integer b) {
        if (a == null || b == null) {
            return 0;
        }
        return a * b;
    }

    /**
     * Validate age
     */
    public static boolean isValidAge(Integer age) {
        return age != null && age >= 0 && age <= 150;
    }
}

