package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * STEP 3: CONFUSIONS AND COMMON MISTAKES WITH WRAPPER CLASSES
 * 
 * This file documents the most common mistakes developers make with wrapper classes.
 * From small issues to critical production bugs.
 * 
 * ⚠️  Understanding these mistakes will save you from frustrating debugging sessions!
 */

public class Step3_ConfusionsAndMistakes {

    public static void main(String[] args) {
        System.out.println("===== STEP 3: CONFUSIONS AND COMMON MISTAKES =====\n");

        // ============= MISTAKE 1: Comparing Wrapper Objects with == (SMALL) =============
        System.out.println("❌ MISTAKE 1: Using == for Wrapper Object Comparison\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        Integer num1 = 100;
        Integer num2 = 100;
        Integer num3 = new Integer(100);
        
        // WRONG: Using == (compares memory references)
        System.out.println("  num1 == num2: " + (num1 == num2) + " (true due to caching [-128 to 127])");
        System.out.println("  num1 == num3: " + (num1 == num3) + " (false! Different objects)");
        
        System.out.println("\n  Expected both to be true, but second is false!");
        System.out.println("  This is because num3 = new Integer(100) creates a new object");
        
        // RIGHT: Using equals() (compares content)
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  num1.equals(num2): " + num1.equals(num2) + " (true - content comparison)");
        System.out.println("  num1.equals(num3): " + num1.equals(num3) + " (true - content comparison)");
        
        System.out.println("\n📌 KEY LESSON: Always use .equals() for wrapper objects, not ==");
        System.out.println("   == compares reference (memory address), not value!");

        // ============= MISTAKE 2: Integer Caching Confusion (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 2: Integer Caching Behavior Inconsistency\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        Integer a = 127;   // Within cache range [-128 to 127]
        Integer b = 127;
        Integer c = 128;   // Outside cache range
        Integer d = 128;
        
        // Due to caching mechanism:
        System.out.println("  a = 127, b = 127");
        System.out.println("  a == b: " + (a == b) + " (true - both use cached object!)");
        
        System.out.println("\n  c = 128, d = 128");
        System.out.println("  c == d: " + (c == d) + " (false - different objects, outside cache!)");
        
        System.out.println("\n📌 KEY LESSON: Integer values from -128 to 127 are cached");
        System.out.println("   Outside this range, == comparison is unpredictable!");

        // ============= MISTAKE 3: Auto-unboxing NullPointerException (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 3: NullPointerException from Auto-unboxing (CRITICAL)\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        Integer nullValue = null;
        System.out.println("  Integer nullValue = null;");
        
        try {
            // This will throw NullPointerException!
            int primitiveValue = nullValue;  // Auto-unboxing null to primitive
            System.out.println("  int primitiveValue = nullValue;");
        } catch (NullPointerException e) {
            System.out.println("  ❌ NullPointerException: Cannot unbox null to int!");
            System.out.println("  Stack Trace: " + e);
        }
        
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  if (nullValue != null) {");
        System.out.println("      int primitiveValue = nullValue;  // Safe now");
        System.out.println("  }");
        
        System.out.println("\n📌 KEY LESSON: Always null-check wrapper objects before unboxing!");

        // ============= MISTAKE 4: Boxing/Unboxing in Loops (PERFORMANCE) =============
        System.out.println("\n\n❌ MISTAKE 4: Excessive Boxing/Unboxing in Loops (PERFORMANCE)\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        long startTime = System.nanoTime();
        List<Integer> boxedNumbers = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            boxedNumbers.add(i);  // Boxing happens here! (int -> Integer)
        }
        long boxingTime = System.nanoTime() - startTime;
        
        System.out.println("  Time for 1,000,000 boxing operations: " + (boxingTime / 1_000_000) + "ms");
        
        System.out.println("\n✓ BETTER APPROACH:");
        System.out.println("  Use primitive collections like IntStream or arrays");
        System.out.println("  Or cache boxing operations outside loops");
        
        System.out.println("\n📌 KEY LESSON: Box/unbox in loops? That's expensive!");

        // ============= MISTAKE 5: Comparing Wrapper Values Directly (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 5: Direct Comparison Without Null Check\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        Integer value1 = 50;
        Integer value2 = null;
        
        // This can cause NullPointerException
        try {
            boolean isGreater = value1 > value2;  // Auto-unboxing null!
        } catch (NullPointerException e) {
            System.out.println("  ❌ NullPointerException: value2 is null!");
            System.out.println("  Comparing: value1 (50) > value2 (null) throws exception");
        }
        
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  if (value1 != null && value2 != null) {");
        System.out.println("      boolean isGreater = value1 > value2;");
        System.out.println("  }");
        
        System.out.println("\n📌 KEY LESSON: Always null-check before comparison operations!");

        // ============= MISTAKE 6: Modifying Wrapper Values in Collections (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 6: Wrapper Objects Are Immutable\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        
        System.out.println("  Initial list: " + numbers);
        
        // Wrapper objects are IMMUTABLE!
        Integer firstNumber = numbers.get(0);
        // firstNumber++; results in new object, doesn't modify in list
        firstNumber = firstNumber + 1;  // Creates NEW Integer, original unchanged
        
        System.out.println("  After firstNumber++: " + numbers);
        System.out.println("  ❌ List is unchanged! firstNumber now = " + firstNumber);
        
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  numbers.set(0, numbers.get(0) + 1);  // Replace with new value");
        
        numbers.set(0, numbers.get(0) + 1);
        System.out.println("  After using set(): " + numbers);
        
        System.out.println("\n📌 KEY LESSON: Wrapper classes are immutable!");

        // ============= MISTAKE 7: Type Mismatch in Collections (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 7: Type Mismatch in Generic Collections\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        
        // ClassCastException risk if we're not careful
        System.out.println("  scores.put(\"Alice\", 95);");
        System.out.println("  Map contains: " + scores);
        
        // Getting value with wrong expectation
        Object value = scores.get("Alice");
        System.out.println("  Retrieved value: " + value + " (type: " + value.getClass().getSimpleName() + ")");
        
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  Integer score = scores.get(\"Alice\");  // Type-safe");
        System.out.println("  or");
        System.out.println("  int score = scores.get(\"Alice\");  // Auto-unboxing");
        
        System.out.println("\n📌 KEY LESSON: Use proper generic types to avoid class cast exceptions!");

        // ============= MISTAKE 8: Database null Handling (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 8: Database NULL Not Handled Properly (CRITICAL)\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        // Simulate database retrieval
        Integer salaryFromDatabase = null;  // Database returned null
        System.out.println("  Integer salaryFromDatabase = null;  // From DB");
        
        try {
            // This crashes in production!
            int salary = salaryFromDatabase;  // Auto-unboxing null
            int finalSalary = salary + 5000;
            System.out.println("  Final salary: " + finalSalary);
        } catch (NullPointerException e) {
            System.out.println("  ❌ PRODUCTION BUG: NullPointerException!");
            System.out.println("  Employee has no salary record in database");
        }
        
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  Integer salary = salaryFromDatabase != null ? salaryFromDatabase : 0;");
        System.out.println("  or");
        System.out.println("  int salary = (salaryFromDatabase != null) ? salaryFromDatabase : 0;");
        
        Integer safeSalary = (salaryFromDatabase != null) ? salaryFromDatabase : 0;
        System.out.println("  Safe value: " + safeSalary);
        
        System.out.println("\n📌 KEY LESSON: Database values are often null - always handle them!");

        // ============= MISTAKE 9: String to Wrapper Conversion Errors (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 9: NumberFormatException in Conversion (CRITICAL)\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        String userInput = "abc123";  // User entered invalid number
        System.out.println("  String userInput = \"" + userInput + "\";");
        
        try {
            Integer number = Integer.parseInt(userInput);
            System.out.println("  Parsed number: " + number);
        } catch (NumberFormatException e) {
            System.out.println("  ❌ NumberFormatException: Cannot parse \"" + userInput + "\" as Integer");
            System.out.println("  This happens when user input is invalid!");
        }
        
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  try {");
        System.out.println("      Integer number = Integer.parseInt(userInput);");
        System.out.println("  } catch (NumberFormatException e) {");
        System.out.println("      // Handle invalid input");
        System.out.println("  }");
        
        System.out.println("\n📌 KEY LESSON: Always wrap parsing in try-catch!");

        // ============= MISTAKE 10: Object Equality vs Reference (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 10: Mixing equals() and hashCode() Issues\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        Integer x = new Integer(100);
        Integer y = new Integer(100);
        
        System.out.println("  x = new Integer(100);");
        System.out.println("  y = new Integer(100);");
        System.out.println("  x.equals(y): " + x.equals(y) + " (true - same value)");
        System.out.println("  x == y: " + (x == y) + " (false - different objects)");
        
        // In HashSet/HashMap, this matters
        List<Integer> list = new ArrayList<>();
        list.add(x);
        list.add(y);
        list.add(new Integer(100));
        
        System.out.println("\n  Added 3 objects with value 100 to list");
        System.out.println("  List size: " + list.size() + " (3 objects, same value)");
        
        System.out.println("\n📌 KEY LESSON: equals() checks content, == checks reference!");

        // ============= MISTAKE 11: Auto-boxing in Array Initialization (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 11: Confusion with Array Types\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        // Primitive array
        int[] primitiveArray = {1, 2, 3};
        System.out.println("  int[] primitiveArray = {1, 2, 3};");
        System.out.println("  Size: " + primitiveArray.length);
        
        // Wrapper array
        Integer[] wrapperArray = {1, 2, 3};
        System.out.println("\n  Integer[] wrapperArray = {1, 2, 3};");
        System.out.println("  Size: " + wrapperArray.length);
        
        System.out.println("\n  Both look similar but are DIFFERENT:");
        System.out.println("  - primitiveArray: stores actual values");
        System.out.println("  - wrapperArray: stores object references");
        
        System.out.println("\n📌 KEY LESSON: Choose between primitive and wrapper arrays carefully!");

        // ============= MISTAKE 12: Memory Overhead (PERFORMANCE) =============
        System.out.println("\n\n❌ MISTAKE 12: Memory Waste with Wrapper Classes\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        System.out.println("  Primitive int: 4 bytes");
        System.out.println("  Integer object: ~16 bytes (4 bytes value + overhead)");
        
        System.out.println("\n  1 million primitives: ~4 MB");
        System.out.println("  1 million Integers: ~16+ MB (4x memory!)");
        
        System.out.println("\n✓ RECOMMENDATION:");
        System.out.println("  Use primitives for large data sets");
        System.out.println("  Use wrappers only when necessary (collections, null values)");
        
        System.out.println("\n📌 KEY LESSON: Wrapper classes use 4x more memory!");

        // ============= MISTAKE 13: Deprecated Constructors (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 13: Using Deprecated Constructors\n");
        
        System.out.println("The Problem:");
        System.out.println("-----------");
        
        System.out.println("  Integer num1 = new Integer(100);  // ❌ Deprecated!");
        System.out.println("  Integer num2 = Integer.valueOf(100);  // ✓ Correct");
        System.out.println("  Integer num3 = 100;  // ✓ Auto-boxing (preferred)");
        
        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  - Use valueOf() factory method");
        System.out.println("  - Or rely on auto-boxing (100 -> Integer.valueOf(100))");
        
        System.out.println("\n📌 KEY LESSON: new Integer() is deprecated since Java 9!");

        // ============= SUMMARY OF MISTAKES =============
        System.out.println("\n\n===== SUMMARY OF COMMON MISTAKES =====");
        System.out.println("1. ❌ Using == instead of equals() for comparison");
        System.out.println("2. ❌ Forgetting about Integer caching [-128 to 127]");
        System.out.println("3. ❌ Auto-unboxing null values (CRITICAL - NullPointerException)");
        System.out.println("4. ❌ Boxing/unboxing in performance-critical loops");
        System.out.println("5. ❌ Comparing wrapped values without null check");
        System.out.println("6. ❌ Trying to modify immutable wrapper objects");
        System.out.println("7. ❌ Type mismatch in generic collections");
        System.out.println("8. ❌ Not handling database NULL values (CRITICAL)");
        System.out.println("9. ❌ Not catching NumberFormatException (CRITICAL)");
        System.out.println("10. ❌ Mixing equals() and reference equality");
        System.out.println("11. ❌ Confusion between primitive and wrapper arrays");
        System.out.println("12. ❌ Using wrappers for large datasets (memory waste)");
        System.out.println("13. ❌ Using deprecated new Integer() constructor");
        
        System.out.println("\n===== BEST PRACTICES =====");
        System.out.println("✓ Always use equals() for wrapper objects");
        System.out.println("✓ Always null-check before unboxing");
        System.out.println("✓ Use valueOf() or auto-boxing, not new");
        System.out.println("✓ Wrap parsing operations in try-catch");
        System.out.println("✓ Use primitives for performance-critical code");
        System.out.println("✓ Use wrappers for collections and null handling");
    }
}

