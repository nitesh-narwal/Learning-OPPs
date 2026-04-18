package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

/**
 * STEP 3: INNER WORKINGS - BOXING & UNBOXING DEEP DIVE
 * 
 * What is Boxing?
 * ===============
 * Boxing = Converting a primitive value to a wrapper object
 * Example: int 25 -> Integer object containing 25
 *
 * What is Unboxing?
 * =================
 * Unboxing = Extracting primitive value from a wrapper object
 * Example: Integer object containing 25 -> int 25
 *
 * Auto-Boxing (Since Java 5):
 * ===========================
 * Compiler automatically converts primitive to wrapper object
 * NO NEED for explicit wrapping anymore
 * Integer num = 50;  // Compiler converts: 50 -> Integer.valueOf(50)
 *
 * Auto-Unboxing (Since Java 5):
 * =============================
 * Compiler automatically extracts primitive from wrapper object
 * NO NEED for explicit unwrapping anymore
 * int value = num;   // Compiler converts: num.intValue()
 *
 * Performance Impact:
 * ===================
 * Boxing/Unboxing has performance overhead (memory and CPU)
 * Avoid in tight loops or performance-critical code
 */

public class Step3_BoxingAndUnboxing {

    public static void main(String[] args) {
        System.out.println("===== STEP 3: BOXING & UNBOXING DEEP DIVE =====\n");

        // ============= DEMONSTRATION 1: Manual Boxing =============
        System.out.println("1. Manual Boxing (Before Java 5 - Old Way):\n");

        // OLD WAY: Manually wrapping primitives (Deprecated)
        int primitiveInt = 100;
        Integer boxedInt1 = new Integer(primitiveInt);  // Explicit boxing
        System.out.println("  int 100 -> new Integer(100) = " + boxedInt1);

        double primitiveDouble = 25.5;
        Double boxedDouble1 = new Double(primitiveDouble);
        System.out.println("  double 25.5 -> new Double(25.5) = " + boxedDouble1);

        boolean primitiveBoolean = true;
        Boolean boxedBoolean1 = new Boolean(primitiveBoolean);
        System.out.println("  boolean true -> new Boolean(true) = " + boxedBoolean1);

        // ============= DEMONSTRATION 2: Boxing using valueOf() =============
        System.out.println("\n2. Boxing using valueOf() (Modern Way):\n");

        // MODERN WAY: Using static valueOf() method (Recommended)
        Integer boxedInt2 = Integer.valueOf(100);
        System.out.println("  Integer.valueOf(100) = " + boxedInt2);

        Double boxedDouble2 = Double.valueOf(25.5);
        System.out.println("  Double.valueOf(25.5) = " + boxedDouble2);

        Boolean boxedBoolean2 = Boolean.valueOf(true);
        System.out.println("  Boolean.valueOf(true) = " + boxedBoolean2);

        Long boxedLong = Long.valueOf(999999L);
        System.out.println("  Long.valueOf(999999L) = " + boxedLong);

        // ============= DEMONSTRATION 3: Auto-Boxing (Compiler Magic) =============
        System.out.println("\n3. Auto-Boxing (Compiler Automatically Wraps Primitives):\n");

        // Java 5+ automatically does boxing for us!
        Integer autoBox1 = 50;              // Compiler: 50 -> Integer.valueOf(50)
        Double autoBox2 = 3.14;             // Compiler: 3.14 -> Double.valueOf(3.14)
        Boolean autoBox3 = false;           // Compiler: false -> Boolean.valueOf(false)
        Long autoBox4 = 123456789L;         // Compiler: 123456789L -> Long.valueOf(123456789L)
        Float autoBox5 = 1.5f;              // Compiler: 1.5f -> Float.valueOf(1.5f)

        System.out.println("  int 50 (auto-boxed) = " + autoBox1 + " (class: " + autoBox1.getClass().getSimpleName() + ")");
        System.out.println("  double 3.14 (auto-boxed) = " + autoBox2 + " (class: " + autoBox2.getClass().getSimpleName() + ")");
        System.out.println("  boolean false (auto-boxed) = " + autoBox3 + " (class: " + autoBox3.getClass().getSimpleName() + ")");
        System.out.println("  long 123456789L (auto-boxed) = " + autoBox4 + " (class: " + autoBox4.getClass().getSimpleName() + ")");
        System.out.println("  float 1.5f (auto-boxed) = " + autoBox5 + " (class: " + autoBox5.getClass().getSimpleName() + ")");

        // ============= DEMONSTRATION 4: Manual Unboxing =============
        System.out.println("\n4. Manual Unboxing (Before Java 5 - Old Way):\n");

        Integer wrappedValue = 100;
        Double wrappedDouble = 25.5;
        Boolean wrappedBoolean = true;

        // OLD WAY: Manually extracting primitive values (Deprecated)
        int unwrappedInt = wrappedValue.intValue();              // Extract int
        double unwrappedDouble = wrappedDouble.doubleValue();    // Extract double
        boolean unwrappedBool = wrappedBoolean.booleanValue();   // Extract boolean

        System.out.println("  Integer.intValue() = " + unwrappedInt + " (type: primitive int)");
        System.out.println("  Double.doubleValue() = " + unwrappedDouble + " (type: primitive double)");
        System.out.println("  Boolean.booleanValue() = " + unwrappedBool + " (type: primitive boolean)");

        // ============= DEMONSTRATION 5: Auto-Unboxing (Compiler Magic) =============
        System.out.println("\n5. Auto-Unboxing (Compiler Automatically Unwraps):\n");

        Integer wrappedInt = 200;
        Double wrappedDbl = 99.99;
        Boolean wrappedBool = false;

        // Java 5+ automatically does unboxing for us!
        int autoUnwrap1 = wrappedInt;       // Compiler: wrappedInt.intValue()
        double autoUnwrap2 = wrappedDbl;    // Compiler: wrappedDbl.doubleValue()
        boolean autoUnwrap3 = wrappedBool;  // Compiler: wrappedBool.booleanValue()

        System.out.println("  Integer -> int (auto-unboxed) = " + autoUnwrap1);
        System.out.println("  Double -> double (auto-unboxed) = " + autoUnwrap2);
        System.out.println("  Boolean -> boolean (auto-unboxed) = " + autoUnwrap3);

        // ============= DEMONSTRATION 6: Boxing in Operations =============
        System.out.println("\n6. Auto-Boxing/Unboxing in Arithmetic Operations:\n");

        Integer num1 = 25;
        Integer num2 = 15;

        // Auto-unboxing in arithmetic
        int result = num1 + num2;  // Compiler: num1.intValue() + num2.intValue()
        System.out.println("  Integer(25) + Integer(15) = " + result);

        // Mixing primitives and wrapper objects
        int primitive = 10;
        Integer wrapped = 20;
        int sum = primitive + wrapped;  // Auto-unboxing: wrapped.intValue()
        System.out.println("  int 10 + Integer(20) = " + sum);

        // ============= DEMONSTRATION 7: Boxing in Comparisons =============
        System.out.println("\n7. Auto-Unboxing in Comparisons:\n");

        Integer wrappedNum1 = 50;
        Integer wrappedNum2 = 100;
        int primitiveNum = 50;

        // Auto-unboxing in comparison
        if (wrappedNum1 == primitiveNum) {  // Compiler: wrappedNum1.intValue() == primitiveNum
            System.out.println("  Integer(50) == int 50 = true (auto-unboxing)");
        }

        if (wrappedNum1 < wrappedNum2) {    // Compiler: wrappedNum1.intValue() < wrappedNum2.intValue()
            System.out.println("  Integer(50) < Integer(100) = true (auto-unboxing)");
        }

        // ============= DEMONSTRATION 8: Null Handling During Unboxing =============
        System.out.println("\n8. Null Handling in Unboxing (NullPointerException):\n");

        Integer nullValue = null;

        System.out.println("  nullValue = " + nullValue);

        try {
            // This will throw NullPointerException!
            int primitiveFromNull = nullValue;  // Compiler: nullValue.intValue() but nullValue is null!
            System.out.println("  Primitive value: " + primitiveFromNull);
        } catch (NullPointerException e) {
            System.out.println("  ⚠️  ERROR: NullPointerException when unboxing null!");
            System.out.println("     Wrapper objects can be null, but primitives cannot!");
        }

        // Safe null handling
        Integer safeValue = null;
        int defaultValue = 0;
        int safeResult = (safeValue != null) ? safeValue : defaultValue;  // Null-safe
        System.out.println("  Safe unboxing with null check: " + safeResult);

        // ============= DEMONSTRATION 9: Performance Consideration =============
        System.out.println("\n9. Performance Impact of Boxing/Unboxing:\n");

        System.out.println("  Why boxed values are slower:");
        System.out.println("    1. Primitives: stored in Stack (fast)");
        System.out.println("    2. Objects: stored in Heap (slower memory access)");
        System.out.println("    3. Boxing: creates new object (memory allocation)");
        System.out.println("    4. Unboxing: method call overhead");

        // Example: Performance impact in loops
        long startTime, endTime;

        // Primitive loop (faster)
        startTime = System.nanoTime();
        long primitiveSum = 0;
        for (int i = 0; i < 100000; i++) {
            primitiveSum += i;
        }
        endTime = System.nanoTime();
        long primitiveDuration = endTime - startTime;

        // Wrapper loop (slower)
        startTime = System.nanoTime();
        Long wrappedSum = 0L;
        for (int i = 0; i < 100000; i++) {
            wrappedSum += i;  // Auto-boxing/unboxing overhead
        }
        endTime = System.nanoTime();
        long wrappedDuration = endTime - startTime;

        System.out.println("\n  Performance Test (100000 iterations):");
        System.out.println("    Primitive loop: " + primitiveDuration + " ns");
        System.out.println("    Wrapper loop:   " + wrappedDuration + " ns");
        System.out.println("    Wrapper is " + (wrappedDuration / (double)primitiveDuration) + "x slower");

        // ============= DEMONSTRATION 10: Boxing/Unboxing in Method Calls =============
        System.out.println("\n10. Boxing/Unboxing in Method Calls:\n");

        int primitiveValue = 42;
        printObject(primitiveValue);        // Auto-boxing: 42 -> Integer(42)

        Integer wrapperValue = 100;
        printPrimitive(wrapperValue);       // Auto-unboxing: Integer(100) -> 100

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Boxing = Converting primitive to wrapper object");
        System.out.println("✓ Unboxing = Extracting primitive from wrapper object");
        System.out.println("✓ Auto-boxing/unboxing (Java 5+) makes code cleaner");
        System.out.println("✓ Compiler automatically handles boxing/unboxing");
        System.out.println("✓ Null values in wrapper objects cause NullPointerException when unboxed");
        System.out.println("✓ Use null checks before unboxing wrapper objects");
        System.out.println("✓ Avoid boxing/unboxing in performance-critical code");
    }

    // ============= HELPER METHODS =============

    /**
     * Method expects Object (not primitive)
     * Auto-boxing converts primitive to wrapper
     */
    public static void printObject(Integer value) {
        System.out.println("  printObject received: " + value + " (type: " + value.getClass().getSimpleName() + ")");
    }

    /**
     * Method expects primitive int
     * Auto-unboxing converts wrapper to primitive
     */
    public static void printPrimitive(int value) {
        System.out.println("  printPrimitive received: " + value + " (type: primitive int)");
    }
}

