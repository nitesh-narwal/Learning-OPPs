package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

/**
 * STEP 6: COMMON PITFALLS & HOW TO AVOID THEM
 * 
 * PITFALL 1: Using == to Compare Wrapper Objects
 * ===============================================
 * Problem: == compares references, not values
 * Due to caching, results are unpredictable
 * 
 * Pitfall: Integer a = 100; Integer b = 100;
 *          if (a == b) // TRUE (cached)
 *
 *          Integer c = 200; Integer d = 200;
 *          if (c == d) // FALSE (not cached)
 *
 * Solution: Always use .equals() method
 *
 * PITFALL 2: NullPointerException from Unboxing Null
 * ===================================================
 * Problem: Unboxing a null wrapper throws NullPointerException
 * 
 * Pitfall: Integer value = null;
 *          int primitive = value;  // NullPointerException!
 *
 * Solution: Check for null before unboxing
 *
 * PITFALL 3: Performance Issues in Loops
 * =======================================
 * Problem: Boxing/Unboxing in tight loops is slow
 * Creates many temporary objects
 * Garbage collection overhead
 *
 * Pitfall: Long sum = 0L;
 *          for (int i = 0; i < 1000000; i++) {
 *              sum += i;  // Auto-boxing/unboxing each iteration
 *          }
 *
 * Solution: Use primitive types in loops
 *
 * PITFALL 4: Assuming Constructor and valueOf() are Identical
 * ===========================================================
 * Problem: new Integer(100) creates new object (outside cache)
 *          Integer.valueOf(100) uses cache
 *
 * PITFALL 5: Float/Double Precision Issues
 * ==========================================
 * Problem: Float and Double have precision limitations
 * 0.1 + 0.2 != 0.3 (due to binary representation)
 */

public class Step6_CommonPitfalls {

    public static void main(String[] args) {
        System.out.println("===== STEP 6: COMMON PITFALLS & HOW TO AVOID THEM =====\n");

        // ============= PITFALL 1: Using == to Compare Wrapper Objects =============
        System.out.println("1. PITFALL: Using == Instead of equals()\n");

        System.out.println("  Problem: == compares object references, not values");
        System.out.println("  Due to caching, results are unpredictable\n");

        // WRONG WAY - Using ==
        Integer num1 = 100;
        Integer num2 = 100;
        Integer num3 = 200;
        Integer num4 = 200;

        System.out.println("  ❌ WRONG WAY (using ==):");
        System.out.println("    Integer num1 = 100;");
        System.out.println("    Integer num2 = 100;");
        System.out.println("    num1 == num2: " + (num1 == num2) + " (cached - true)");

        System.out.println("\n    Integer num3 = 200;");
        System.out.println("    Integer num4 = 200;");
        System.out.println("    num3 == num4: " + (num3 == num4) + " (not cached - false)");
        System.out.println("    ⚠️  Unpredictable behavior!");

        // RIGHT WAY - Using equals()
        System.out.println("\n  ✓ RIGHT WAY (using equals()):");
        System.out.println("    num1.equals(num2): " + num1.equals(num2) + " (compares values)");
        System.out.println("    num3.equals(num4): " + num3.equals(num4) + " (compares values)");
        System.out.println("    Consistent and predictable!");

        // ============= PITFALL 2: NullPointerException from Unboxing =============
        System.out.println("\n2. PITFALL: NullPointerException When Unboxing Null\n");

        System.out.println("  Problem: Unboxing null wrapper throws NullPointerException\n");

        Integer nullableValue = null;
        System.out.println("  Integer nullableValue = null;");

        System.out.println("\n  ❌ WRONG WAY (direct unboxing):");
        try {
            int primitive = nullableValue;  // Compiler calls: nullableValue.intValue()
            System.out.println("    int primitive = nullableValue;");
        } catch (NullPointerException e) {
            System.out.println("    Exception: " + e.getClass().getSimpleName());
            System.out.println("    Message: Cannot unbox null to primitive");
        }

        System.out.println("\n  ✓ RIGHT WAY 1 (null check):");
        if (nullableValue != null) {
            int primitive = nullableValue;
            System.out.println("    if (nullableValue != null) int value = nullableValue;");
        } else {
            System.out.println("    if (nullableValue != null) // false");
            System.out.println("    nullableValue is null, skipping unboxing");
        }

        System.out.println("\n  ✓ RIGHT WAY 2 (provide default value):");
        int defaultValue = 0;
        int safeValue = (nullableValue != null) ? nullableValue : defaultValue;
        System.out.println("    int safeValue = (nullableValue != null) ? nullableValue : 0;");
        System.out.println("    safeValue = " + safeValue);

        // ============= PITFALL 3: Performance Issues in Loops =============
        System.out.println("\n3. PITFALL: Boxing/Unboxing Performance in Loops\n");

        System.out.println("  Problem: Boxing/Unboxing in loops creates many objects");
        System.out.println("  Causes garbage collection overhead and slowness\n");

        // WRONG WAY - Using wrapper in loop
        System.out.println("  ❌ WRONG WAY (wrapper in loop):");
        long startTime = System.nanoTime();
        Long wrappedSum = 0L;
        for (int i = 0; i < 1000000; i++) {
            wrappedSum += i;  // Auto-boxing and unboxing each iteration!
        }
        long wrappedDuration = System.nanoTime() - startTime;
        System.out.println("    Long wrappedSum = 0L;");
        System.out.println("    for (int i = 0; i < 1000000; i++) {");
        System.out.println("        wrappedSum += i;  // Boxing/Unboxing overhead");
        System.out.println("    }");
        System.out.println("    Time: " + wrappedDuration + " ns");

        // RIGHT WAY - Using primitive in loop
        System.out.println("\n  ✓ RIGHT WAY (primitive in loop):");
        startTime = System.nanoTime();
        long primitiveSum = 0L;
        for (int i = 0; i < 1000000; i++) {
            primitiveSum += i;  // No boxing/unboxing overhead
        }
        long primitiveDuration = System.nanoTime() - startTime;
        System.out.println("    long primitiveSum = 0L;");
        System.out.println("    for (int i = 0; i < 1000000; i++) {");
        System.out.println("        primitiveSum += i;  // No overhead");
        System.out.println("    }");
        System.out.println("    Time: " + primitiveDuration + " ns");
        System.out.println("    Primitive is " + String.format("%.2f", (double) wrappedDuration / primitiveDuration) + "x faster!");

        // ============= PITFALL 4: Constructor vs valueOf() =============
        System.out.println("\n4. PITFALL: Assuming new Constructor and valueOf() Are Same\n");

        System.out.println("  Problem: new Integer() creates new object every time");
        System.out.println("  valueOf() uses cache for -128 to 127\n");

        // Using new Constructor
        Integer constructorObj1 = new Integer(100);
        Integer constructorObj2 = new Integer(100);

        System.out.println("  ❌ POTENTIAL ISSUE (new Constructor):");
        System.out.println("    new Integer(100) == new Integer(100): " + (constructorObj1 == constructorObj2));
        System.out.println("    Always creates new objects (no caching)");

        // Using valueOf()
        Integer valueOfObj1 = Integer.valueOf(100);
        Integer valueOfObj2 = Integer.valueOf(100);

        System.out.println("\n  ✓ BETTER (valueOf()):");
        System.out.println("    Integer.valueOf(100) == Integer.valueOf(100): " + (valueOfObj1 == valueOfObj2));
        System.out.println("    Uses cache for better performance");

        // Outside cache
        Integer constructorLarge1 = new Integer(500);
        Integer constructorLarge2 = new Integer(500);
        Integer valueOfLarge1 = Integer.valueOf(500);
        Integer valueOfLarge2 = Integer.valueOf(500);

        System.out.println("\n  For values outside cache (-128 to 127):");
        System.out.println("    Both create new objects (no difference)");
        System.out.println("    new Integer(500) == new Integer(500): " + (constructorLarge1 == constructorLarge2));
        System.out.println("    Integer.valueOf(500) == Integer.valueOf(500): " + (valueOfLarge1 == valueOfLarge2));

        // ============= PITFALL 5: Float/Double Precision Issues =============
        System.out.println("\n5. PITFALL: Float/Double Precision Issues\n");

        System.out.println("  Problem: Floating point numbers have precision limitations");
        System.out.println("  Binary representation of decimals can be approximate\n");

        // Classic precision issue
        double result = 0.1 + 0.2;
        System.out.println("  ❌ WRONG: 0.1 + 0.2 == 0.3");
        System.out.println("    Actual result: " + result);
        System.out.println("    Expected: 0.3");
        System.out.println("    Not equal due to binary representation precision!");

        // Right way - use epsilon for comparison
        System.out.println("\n  ✓ RIGHT WAY 1 (use epsilon for comparison):");
        double epsilon = 1e-9;
        boolean isEqual = Math.abs(result - 0.3) < epsilon;
        System.out.println("    epsilon = 1e-9");
        System.out.println("    Math.abs(result - 0.3) < epsilon: " + isEqual);

        // Right way - use BigDecimal for money calculations
        System.out.println("\n  ✓ RIGHT WAY 2 (use BigDecimal for money):");
        System.out.println("    import java.math.BigDecimal;");
        System.out.println("    BigDecimal amount1 = new BigDecimal(\"0.1\");");
        System.out.println("    BigDecimal amount2 = new BigDecimal(\"0.2\");");
        System.out.println("    BigDecimal sum = amount1.add(amount2);");
        System.out.println("    (Exact representation, no precision loss)");

        // ============= PITFALL 6: Not Handling NumberFormatException =============
        System.out.println("\n6. PITFALL: Not Handling NumberFormatException\n");

        System.out.println("  Problem: Parsing invalid strings causes exception\n");

        String invalidNumber = "abc123";
        System.out.println("  String invalidNumber = \"abc123\";");

        System.out.println("\n  ❌ WRONG WAY (no error handling):");
        try {
            // int num = Integer.parseInt(invalidNumber);  // Will throw exception
            System.out.println("    int num = Integer.parseInt(invalidNumber);");
            System.out.println("    // Program crashes with NumberFormatException");
        } catch (NumberFormatException e) {
            System.out.println("    Exception caught!");
        }

        System.out.println("\n  ✓ RIGHT WAY (with try-catch):");
        try {
            int num = Integer.parseInt(invalidNumber);
            System.out.println("    Parsed successfully: " + num);
        } catch (NumberFormatException e) {
            System.out.println("    Caught: " + e.getClass().getSimpleName());
            System.out.println("    Invalid number format: " + invalidNumber);
        }

        // ============= PITFALL 7: Uninitialized Wrapper Objects in Arrays =============
        System.out.println("\n7. PITFALL: Uninitialized Wrapper Objects Default to null\n");

        Integer[] numbers = new Integer[5];  // All initialized to null
        System.out.println("  Integer[] numbers = new Integer[5];");
        System.out.println("  Default values: " + java.util.Arrays.toString(numbers));

        System.out.println("\n  ❌ WRONG: Assuming default value is 0");
        try {
            if (numbers[0] < 10) {  // Will throw NullPointerException
                System.out.println("    This check will work...");
            }
        } catch (NullPointerException e) {
            System.out.println("    Exception: " + e.getClass().getSimpleName());
            System.out.println("    numbers[0] is null, not 0!");
        }

        System.out.println("\n  ✓ RIGHT WAY: Check for null first");
        if (numbers[0] != null && numbers[0] < 10) {
            System.out.println("    Safe check passed");
        } else {
            System.out.println("    numbers[0] is null or >= 10");
        }

        // ============= PITFALL 8: Infinite Loops with Wrapper Comparisons =============
        System.out.println("\n8. PITFALL: Dangerous Loop Conditions\n");

        System.out.println("  Problem: Null values can cause unexpected loop behavior\n");

        Integer counter = null;
        System.out.println("  Integer counter = null;");

        System.out.println("\n  ❌ WRONG: Loop with null checker");
        System.out.println("    if (counter != null && counter < 5) // Doesn't execute");
        System.out.println("    But what if you forget the null check?");

        Integer safeCounter = 0;
        System.out.println("\n  ✓ RIGHT WAY: Use primitive or initialize wrapper");
        System.out.println("    Integer safeCounter = 0;");
        System.out.println("    if (safeCounter != null && safeCounter < 5) // Works!");

        // ============= SUMMARY =====
        System.out.println("\n===== SUMMARY OF PITFALLS TO AVOID =====");
        System.out.println("❌ DON'T use == for wrapper object comparison");
        System.out.println("✓ DO use .equals() for wrapper objects");
        System.out.println();
        System.out.println("❌ DON'T unbox null values without checking");
        System.out.println("✓ DO check for null before unboxing");
        System.out.println();
        System.out.println("❌ DON'T use wrapper classes in tight performance loops");
        System.out.println("✓ DO use primitive types for performance-critical code");
        System.out.println();
        System.out.println("❌ DON'T assume new Constructor and valueOf() are same");
        System.out.println("✓ DO use valueOf() for caching benefit");
        System.out.println();
        System.out.println("❌ DON'T use == for Float/Double comparison");
        System.out.println("✓ DO use epsilon-based comparison or BigDecimal");
        System.out.println();
        System.out.println("❌ DON'T ignore NumberFormatException");
        System.out.println("✓ DO wrap parsing in try-catch blocks");
        System.out.println();
        System.out.println("❌ DON'T forget that wrapper arrays default to null");
        System.out.println("✓ DO check for null before using wrapper objects");
    }
}

