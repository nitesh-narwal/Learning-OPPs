package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

import java.util.*;
import java.util.regex.*;

/**
 * STEP 8: CONFUSIONS AND COMMON MISTAKES
 * 
 * This file documents confusing aspects and common mistakes developers make
 * when using java.lang package functions.
 * Ranges from small mistakes to critical production bugs.
 * 
 * Topics:
 * - String comparison confusion
 * - Autoboxing surprises
 * - Null handling issues
 * - Performance pitfalls
 * - Type casting problems
 */

public class Step8_ConfusionsAndMistakes {

    public static void main(String[] args) {
        System.out.println("===== CONFUSIONS AND COMMON MISTAKES =====\n");

        // ============= MISTAKE 1: Using == for String Comparison (SMALL) =============
        System.out.println("❌ MISTAKE 1: Using == for String Comparison\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        System.out.println("  str1 = \"Hello\";");
        System.out.println("  str2 = \"Hello\";");
        System.out.println("  str3 = new String(\"Hello\");");

        System.out.println("\n  str1 == str2: " + (str1 == str2) + " (works by luck - from pool)");
        System.out.println("  str1 == str3: " + (str1 == str3) + " (fails - different objects)");

        System.out.println("\n  ✓ CORRECT APPROACH:");
        System.out.println("  str1.equals(str2): " + str1.equals(str2) + " (always works)");
        System.out.println("  str1.equals(str3): " + str1.equals(str3) + " (always works)");

        System.out.println("\n📌 KEY LESSON: == compares references, equals() compares content!");

        // ============= MISTAKE 2: Integer Caching Confusion (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 2: Integer Caching Inconsistency\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        Integer a = 100;
        Integer b = 100;
        Integer c = 128;
        Integer d = 128;

        System.out.println("  Integer a = 100, b = 100;");
        System.out.println("  a == b: " + (a == b) + " (works - cached!)");

        System.out.println("\n  Integer c = 128, d = 128;");
        System.out.println("  c == d: " + (c == d) + " (fails - not cached!)");

        System.out.println("\n  Integer range -128 to 127 are cached by JVM");
        System.out.println("  Outside this range, == is unreliable!");

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  Always use equals(): a.equals(b) and c.equals(d)");

        System.out.println("\n📌 KEY LESSON: Don't rely on == for Integer comparison!");

        // ============= MISTAKE 3: Unboxing Null (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 3: Unboxing Null Values (CRITICAL)\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        Integer nullValue = null;
        System.out.println("  Integer nullValue = null;");

        try {
            int primitiveValue = nullValue;  // Auto-unboxing
            System.out.println("  int primitiveValue = nullValue;");
        } catch (NullPointerException e) {
            System.out.println("  ❌ NullPointerException thrown!");
            System.out.println("  Cannot unbox null to primitive type");
        }

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  if (nullValue != null) {");
        System.out.println("      int primitiveValue = nullValue;");
        System.out.println("  }");

        System.out.println("\n📌 KEY LESSON: Always null-check before unboxing!");

        // ============= MISTAKE 4: String Concatenation in Loops (PERFORMANCE) =============
        System.out.println("\n\n❌ MISTAKE 4: String Concatenation in Loops (PERFORMANCE)\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        long startTime = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < 10000; i++) {
            result += i;  // Creates new String each iteration!
        }
        long slowTime = System.currentTimeMillis() - startTime;

        System.out.println("  Using += in loop (10,000 iterations): " + slowTime + "ms");

        startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append(i);  // Efficient
        }
        long fastTime = System.currentTimeMillis() - startTime;

        System.out.println("  Using StringBuilder: " + fastTime + "ms");
        System.out.println("  StringBuilder is " + (slowTime / Math.max(fastTime, 1)) + "x faster!");

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  Use StringBuilder for building strings in loops");

        System.out.println("\n📌 KEY LESSON: += in loops = performance disaster!");

        // ============= MISTAKE 5: Comparing Wrapper Values Without Null Check (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 5: Comparing Without Null Check\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        Integer value1 = 50;
        Integer value2 = null;

        try {
            if (value1 > value2) {  // Auto-unboxing null!
                System.out.println("  value1 is greater");
            }
        } catch (NullPointerException e) {
            System.out.println("  ❌ NullPointerException: Cannot unbox null");
            System.out.println("  Trying: value1 (50) > value2 (null)");
        }

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  if (value1 != null && value2 != null && value1 > value2) {");
        System.out.println("      System.out.println(\"value1 is greater\");");
        System.out.println("  }");

        System.out.println("\n📌 KEY LESSON: Always check for null before comparison!");

        // ============= MISTAKE 6: Regex Compilation in Loops (PERFORMANCE) =============
        System.out.println("\n\n❌ MISTAKE 6: Compiling Regex Every Time (PERFORMANCE)\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        String[] emails = {"test@example.com", "invalid-email", "user@domain.org"};

        startTime = System.currentTimeMillis();
        for (String email : emails) {
            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                // Pattern compiled every iteration!
            }
        }
        long regexTime = System.currentTimeMillis() - startTime;

        System.out.println("  Compiling regex each time: " + regexTime + "ms");

        startTime = System.currentTimeMillis();
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        for (String email : emails) {
            if (pattern.matcher(email).matches()) {
                // Pattern compiled once, reused!
            }
        }
        long patternTime = System.currentTimeMillis() - startTime;

        System.out.println("  Using pre-compiled pattern: " + patternTime + "ms");

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  Pattern p = Pattern.compile(regex);");
        System.out.println("  for (String text : texts) {");
        System.out.println("      if (p.matcher(text).matches()) { }");
        System.out.println("  }");

        System.out.println("\n📌 KEY LESSON: Compile regex patterns once, reuse them!");

        // ============= MISTAKE 7: Forgetting Immutability (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 7: Thinking Strings Are Mutable\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        String original = "Hello";
        String modified = original.toUpperCase();

        System.out.println("  original = \"" + original + "\"");
        System.out.println("  modified = original.toUpperCase();");
        System.out.println("  modified = \"" + modified + "\"");

        System.out.println("\n  ❌ WRONG expectation:");
        System.out.println("  original would also change to \"HELLO\"");

        System.out.println("\n  ✓ CORRECT:");
        System.out.println("  original is still \"" + original + "\" (immutable)");
        System.out.println("  modified is \"" + modified + "\" (new object)");

        System.out.println("\n📌 KEY LESSON: Strings are immutable - methods return new objects!");

        // ============= MISTAKE 8: Modifying Collection While Iterating (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 8: Modifying Collection During Iteration\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        System.out.println("  Original list: " + names);

        try {
            for (String name : names) {
                if (name.equals("Bob")) {
                    names.remove(name);  // ❌ ConcurrentModificationException!
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("  ❌ ConcurrentModificationException!");
        }

        System.out.println("\n✓ CORRECT APPROACH:");
        List<String> names2 = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        Iterator<String> iterator = names2.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name.equals("Bob")) {
                iterator.remove();  // Use iterator's remove method
            }
        }
        System.out.println("  After removing Bob: " + names2);

        System.out.println("\n📌 KEY LESSON: Use Iterator.remove() or collect changes separately!");

        // ============= MISTAKE 9: Database NULL Not Handled (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 9: Not Handling Database NULL Values (CRITICAL)\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        // Simulate database query result
        Integer ageFromDatabase = null;  // Database returned NULL
        System.out.println("  Integer ageFromDatabase = null;  // From DB");

        try {
            int age = ageFromDatabase;  // Auto-unboxing
            int nextYear = age + 1;
            System.out.println("  Next year age: " + nextYear);
        } catch (NullPointerException e) {
            System.out.println("  ❌ PRODUCTION BUG: NullPointerException!");
            System.out.println("  Record exists but age field is NULL in database");
        }

        System.out.println("\n✓ CORRECT APPROACH:");
        Integer ageFromDb = null;
        int age = (ageFromDb != null) ? ageFromDb : 0;
        int nextYear = age + 1;
        System.out.println("  Handled safely - next year age: " + nextYear);

        System.out.println("\n📌 KEY LESSON: Database nulls are common - always handle them!");

        // ============= MISTAKE 10: parseInt() Without Error Handling (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 10: parseInt Without Exception Handling (CRITICAL)\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        String userInput = "abc123";  // User entered invalid number
        System.out.println("  User entered: \"" + userInput + "\"");

        try {
            int number = Integer.parseInt(userInput);
            System.out.println("  Parsed as: " + number);
        } catch (NumberFormatException e) {
            System.out.println("  ❌ NumberFormatException!");
            System.out.println("  \"" + userInput + "\" is not a valid integer");
        }

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  try {");
        System.out.println("      int number = Integer.parseInt(userInput);");
        System.out.println("  } catch (NumberFormatException e) {");
        System.out.println("      // Handle invalid input gracefully");
        System.out.println("  }");

        System.out.println("\n📌 KEY LESSON: Always wrap parsing in try-catch!");

        // ============= MISTAKE 11: Type Casting Confusion (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 11: Unsafe Type Casting\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        Object obj = "Hello";
        System.out.println("  Object obj = \"Hello\";");

        try {
            Integer number = (Integer) obj;  // ClassCastException!
            System.out.println("  Casted to Integer: " + number);
        } catch (ClassCastException e) {
            System.out.println("  ❌ ClassCastException!");
            System.out.println("  Cannot cast String to Integer");
        }

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  if (obj instanceof String) {");
        System.out.println("      String str = (String) obj;");
        System.out.println("  }");

        System.out.println("\n📌 KEY LESSON: Use instanceof before casting!");

        // ============= MISTAKE 12: Memory Waste with Wrappers (PERFORMANCE) =============
        System.out.println("\n\n❌ MISTAKE 12: Using Wrappers for Large Data (PERFORMANCE)\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        System.out.println("  Memory usage comparison (1 million numbers):");
        System.out.println("  - Primitive int[]: ~4 MB");
        System.out.println("  - Integer wrapper: ~16+ MB (4x more!)");

        System.out.println("\n  int[] primitives = new int[1000000];");
        System.out.println("  Integer[] wrappers = new Integer[1000000];");
        System.out.println("  wrappers uses 4x more memory and is slower!");

        System.out.println("\n✓ RECOMMENDATION:");
        System.out.println("  Use primitives for large data");
        System.out.println("  Use wrappers only when necessary");

        System.out.println("\n📌 KEY LESSON: Wrappers have significant memory overhead!");

        // ============= MISTAKE 13: Not Overriding hashCode() (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 13: Overriding equals() Without hashCode()\n");

        System.out.println("The Problem:");
        System.out.println("-----------");

        BadPerson person1 = new BadPerson("Alice", 25);
        BadPerson person2 = new BadPerson("Alice", 25);

        Set<BadPerson> set = new HashSet<>();
        set.add(person1);
        set.add(person2);

        System.out.println("  Added two equal persons to HashSet");
        System.out.println("  person1.equals(person2): " + person1.equals(person2));
        System.out.println("  Set size: " + set.size() + " (should be 1, but is 2!)");

        System.out.println("\n✓ CORRECT APPROACH:");
        System.out.println("  Override both equals() and hashCode()");

        System.out.println("\n📌 KEY LESSON: equals() and hashCode() must be overridden together!");

        // ============= SUMMARY OF MISTAKES =============
        System.out.println("\n\n===== SUMMARY OF COMMON MISTAKES =====");
        System.out.println("1. ❌ Using == for String comparison");
        System.out.println("2. ❌ Relying on Integer caching");
        System.out.println("3. ❌ Unboxing null values (CRITICAL)");
        System.out.println("4. ❌ String concatenation in loops (CRITICAL)");
        System.out.println("5. ❌ Comparing without null checks");
        System.out.println("6. ❌ Compiling regex patterns repeatedly");
        System.out.println("7. ❌ Assuming strings are mutable");
        System.out.println("8. ❌ Modifying collection during iteration");
        System.out.println("9. ❌ Not handling database NULLs (CRITICAL)");
        System.out.println("10. ❌ parseInt without exception handling (CRITICAL)");
        System.out.println("11. ❌ Unsafe type casting");
        System.out.println("12. ❌ Using wrappers for large datasets");
        System.out.println("13. ❌ Overriding equals() without hashCode()");

        System.out.println("\n===== BEST PRACTICES =====");
        System.out.println("✓ Always use equals() for String comparison");
        System.out.println("✓ Always null-check before unboxing");
        System.out.println("✓ Use StringBuilder for string building in loops");
        System.out.println("✓ Pre-compile regex patterns");
        System.out.println("✓ Use try-catch for parsing operations");
        System.out.println("✓ Use instanceof before type casting");
        System.out.println("✓ Use primitives for large datasets");
        System.out.println("✓ Override equals() and hashCode() together");
    }

    // ============= HELPER CLASS =============

    /**
     * Class that overrides equals() but NOT hashCode() - BAD PRACTICE!
     */
    static class BadPerson {
        String name;
        int age;

        BadPerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            BadPerson person = (BadPerson) obj;
            return age == person.age && name.equals(person.name);
        }

        // ❌ Missing hashCode() override!
        // This breaks HashMap and HashSet contract
    }
}

