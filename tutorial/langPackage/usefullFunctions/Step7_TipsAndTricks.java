package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

/**
 * TIPS AND TRICKS - BEST PRACTICES
 * 
 * This file contains practical tips, tricks, and best practices
 * for using java.lang package functions efficiently and effectively.
 * 
 * Topics:
 * - Performance optimization tips
 * - Common gotchas and solutions
 * - Best practices for production code
 * - Memory and speed considerations
 */

public class Step7_TipsAndTricks {

    public static void main(String[] args) {
        System.out.println("===== TIPS AND TRICKS - BEST PRACTICES =====\n");

        // ============= TIP 1: STRING POOL OPTIMIZATION =============
        System.out.println("💡 TIP 1: String Pool Optimization:\n");

        String str1 = "Hello";  // Goes to String pool
        String str2 = "Hello";  // Returns reference from pool
        String str3 = new String("Hello");  // Creates new object, not in pool

        System.out.println("  str1 == str2: " + (str1 == str2) + " ✓ GOOD (from pool)");
        System.out.println("  str1 == str3: " + (str1 == str3) + " ✗ BAD (not from pool)");

        System.out.println("\n  ✓ BEST PRACTICE:");
        System.out.println("  - Use string literals for constants");
        System.out.println("  - Avoid new String() constructor");
        System.out.println("  - Let compiler optimize string creation");

        // ============= TIP 2: STRINGBUILDER CONCATENATION =============
        System.out.println("\n💡 TIP 2: Loop String Building:\n");

        System.out.println("  ✗ WRONG:");
        System.out.println("  for (int i = 0; i < 1000; i++) {");
        System.out.println("      result += i;  // Creates 1000 new objects!");
        System.out.println("  }");

        System.out.println("\n  ✓ CORRECT:");
        System.out.println("  StringBuilder sb = new StringBuilder();");
        System.out.println("  for (int i = 0; i < 1000; i++) {");
        System.out.println("      sb.append(i);  // Efficient");
        System.out.println("  }");

        System.out.println("\n  Performance Impact: 10-100x faster with StringBuilder");

        // ============= TIP 3: NULL CHECKING =============
        System.out.println("\n💡 TIP 3: Safe Null Checking:\n");

        String nullable = null;

        System.out.println("  ✗ WRONG:");
        System.out.println("  if (nullable == null) { }");
        System.out.println("  This is okay, but nullable.equals(value) would crash");

        System.out.println("\n  ✓ CORRECT:");
        System.out.println("  if (nullable != null && nullable.equals(value)) { }");
        System.out.println("  Short-circuit evaluation prevents NPE");

        System.out.println("\n  ✓ MODERN (Java 11+):");
        System.out.println("  if (nullable != null && !nullable.isBlank()) { }");

        // ============= TIP 4: USE EQUALS() NOT == =============
        System.out.println("\n💡 TIP 4: Comparison Best Practices:\n");

        Integer num1 = 100;
        Integer num2 = 100;
        Integer num3 = 128;
        Integer num4 = 128;

        System.out.println("  WRONG: num1 == num2 → " + (num1 == num2) + " (unreliable!)");
        System.out.println("  WRONG: num3 == num4 → " + (num3 == num4) + " (unreliable!)");

        System.out.println("\n  CORRECT: num1.equals(num2) → " + num1.equals(num2));
        System.out.println("  CORRECT: num3.equals(num4) → " + num3.equals(num4));

        System.out.println("\n  ✓ RULE: Always use equals() for object comparison");

        // ============= TIP 5: PERFORMANCE - MATH OPERATIONS =============
        System.out.println("\n💡 TIP 5: Math Operation Performance:\n");

        System.out.println("  Trigonometric functions (sin, cos, tan) are SLOW");
        System.out.println("  Use lookup tables if called repeatedly:");

        // Pre-computed sine values
        double[] sinTable = new double[360];
        for (int i = 0; i < 360; i++) {
            sinTable[i] = Math.sin(Math.toRadians(i));
        }

        System.out.println("  Pre-computed sin(45°): " + sinTable[45]);
        System.out.println("  This is faster than recalculating: Math.sin(Math.toRadians(45))");

        // ============= TIP 6: OBJECT EQUALITY AND HASHCODE =============
        System.out.println("\n💡 TIP 6: Equals and HashCode Contract:\n");

        System.out.println("  ✓ RULE: If you override equals(), override hashCode() too!");
        System.out.println("  Why? Objects used in HashMap/HashSet rely on both");

        System.out.println("\n  Contract:");
        System.out.println("  - If a.equals(b), then a.hashCode() == b.hashCode()");
        System.out.println("  - If a.hashCode() == b.hashCode(), a and b MAY be equal");

        // ============= TIP 7: IMMUTABLE STRINGS =============
        System.out.println("\n💡 TIP 7: String Immutability Benefits:\n");

        System.out.println("  ✓ Thread-safe by default (no synchronization needed)");
        System.out.println("  ✓ Can be used as HashMap/HashSet keys safely");
        System.out.println("  ✓ Can be cached and reused");
        System.out.println("  ✓ Predictable behavior in multi-threaded code");

        System.out.println("\n  ✗ Downside:");
        System.out.println("  ✗ String concatenation creates new objects");

        // ============= TIP 8: AUTOBOXING PITFALLS =============
        System.out.println("\n💡 TIP 8: Autoboxing and Unboxing:\n");

        System.out.println("  Autoboxing happens automatically:");
        Integer boxed = 10;  // int → Integer
        System.out.println("  Integer boxed = 10;  // Auto-boxes to Integer(10)");

        System.out.println("\n  Unboxing happens automatically:");
        int unboxed = boxed;  // Integer → int
        System.out.println("  int unboxed = boxed;  // Auto-unboxes to 10");

        System.out.println("\n  ⚠️  GOTCHA: Unboxing null throws NullPointerException!");
        System.out.println("  Integer nullValue = null;");
        System.out.println("  int value = nullValue;  // ❌ NullPointerException!");

        // ============= TIP 9: REGEX PERFORMANCE =============
        System.out.println("\n💡 TIP 9: Regex Performance:\n");

        System.out.println("  ✗ SLOW: Compiling regex every time");
        System.out.println("  for (String text : 1000Strings) {");
        System.out.println("      if (text.matches(\"^[0-9]+$\")) { }  // Compiles every time!");
        System.out.println("  }");

        System.out.println("\n  ✓ FAST: Compile pattern once");
        System.out.println("  java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(\"^[0-9]+$\");");
        System.out.println("  for (String text : 1000Strings) {");
        System.out.println("      if (pattern.matcher(text).matches()) { }");
        System.out.println("  }");

        // ============= TIP 10: MEMORY EFFICIENCY =============
        System.out.println("\n💡 TIP 10: Memory Efficiency Tips:\n");

        System.out.println("  1. Primitive types use less memory than wrapper classes");
        System.out.println("     int: 4 bytes vs Integer: ~16 bytes (4x more!)");

        System.out.println("\n  2. Use primitive arrays for large datasets");
        System.out.println("     int[] is much more memory-efficient than Integer[]");

        System.out.println("\n  3. Be careful with String concatenation");
        System.out.println("     Each + creates a new String object");

        System.out.println("\n  4. StringBuilder is lighter than String for building");

        // ============= TIP 11: SYSTEM RESOURCES =============
        System.out.println("\n💡 TIP 11: System Resource Management:\n");

        System.out.println("  ✗ WRONG: Calling System.gc() frequently");
        System.out.println("  System.gc();  // DON'T do this!");

        System.out.println("\n  ✓ RIGHT: Let JVM manage garbage collection");
        System.out.println("  Trust JVM's garbage collector");

        System.out.println("\n  ✓ BEST: Monitor and profile if needed");
        System.out.println("  Use profiling tools to find memory issues");

        // ============= TIP 12: CLASS REFLECTION =============
        System.out.println("\n💡 TIP 12: Reflection Performance:\n");

        System.out.println("  Reflection is POWERFUL but SLOW");
        System.out.println("  Use cache for frequently accessed methods:");

        System.out.println("\n  ✗ WRONG:");
        System.out.println("  for (Object obj : largeCollection) {");
        System.out.println("      Method m = obj.getClass().getMethod(...);");
        System.out.println("  }  // Looks up method 1000x!");

        System.out.println("\n  ✓ CORRECT:");
        System.out.println("  Method m = SomeClass.class.getMethod(...);");
        System.out.println("  for (Object obj : largeCollection) {");
        System.out.println("      m.invoke(obj);  // Reuse cached method");
        System.out.println("  }");

        // ============= TIP 13: CODE QUALITY =============
        System.out.println("\n💡 TIP 13: Code Quality Best Practices:\n");

        System.out.println("  ✓ Use Objects.requireNonNull() for null validation");
        System.out.println("  ✓ Use Objects.equals() for safe comparison");
        System.out.println("  ✓ Use try-with-resources for resource management");
        System.out.println("  ✓ Prefer primitives over wrappers when possible");
        System.out.println("  ✓ Use StringBuilder for string manipulation in loops");

        // ============= QUICK REFERENCE TABLE =============
        System.out.println("\n🔍 QUICK REFERENCE TABLE:\n");

        System.out.println("  ┌──────────────┬─────────────────────────────────┐");
        System.out.println("  │ Scenario     │ Best Practice                   │");
        System.out.println("  ├──────────────┼─────────────────────────────────┤");
        System.out.println("  │ String comp. │ Use equals(), not ==            │");
        System.out.println("  │ String build │ Use StringBuilder in loops      │");
        System.out.println("  │ Null check   │ Check before unboxing           │");
        System.out.println("  │ Performance  │ Cache compiled patterns         │");
        System.out.println("  │ Memory       │ Use primitives for large data   │");
        System.out.println("  │ Reflection   │ Cache Method objects           │");
        System.out.println("  │ Comparison   │ Use compareTo() for ordering    │");
        System.out.println("  │ GC           │ Let JVM manage, don't call gc() │");
        System.out.println("  └──────────────┴─────────────────────────────────┘");

        // ============= SUMMARY =====
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ String literals are optimized via String pool");
        System.out.println("✓ StringBuilder is essential for string building in loops");
        System.out.println("✓ Always null-check before unboxing wrapper types");
        System.out.println("✓ Use equals() for comparisons, never ==");
        System.out.println("✓ Cache compiled regex patterns for reuse");
        System.out.println("✓ Prefer primitives over wrappers for memory efficiency");
        System.out.println("✓ Override both equals() and hashCode() together");
        System.out.println("✓ Reflection is powerful but slower - use with caution");
    }
}

