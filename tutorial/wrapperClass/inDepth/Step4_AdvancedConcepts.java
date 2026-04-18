package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

/**
 * STEP 4: ADVANCED CONCEPTS & INTEGER CACHE
 * 
 * Integer Caching:
 * ================
 * Java caches Integer objects from -128 to 127 for performance
 * The same cached object is reused when you create integers in this range
 * This is an OPTIMIZATION, not something to rely on
 *
 * Why?
 * ====
 * These values are frequently used
 * Caching reduces memory usage and improves performance
 * Similar caching exists for: Boolean, Byte, Short, Long, Double, Float
 *
 * Important: Using == to compare wrapper objects is DANGEROUS
 * Always use equals() instead!
 *
 * Integer Cache Range:
 * ====================
 * Default range: -128 to 127
 * Can be increased with JVM flag: -XX:AutoBoxCacheMax=1000
 *
 * How Caching Works:
 * ==================
 * Integer a = 100;              // Gets cached object
 * Integer b = 100;              // Gets SAME cached object
 * a == b returns true (same reference)
 *
 * Integer c = 200;              // Creates NEW object (outside cache)
 * Integer d = 200;              // Creates ANOTHER NEW object (outside cache)
 * c == d returns false (different references)
 */

public class Step4_AdvancedConcepts {

    public static void main(String[] args) {
        System.out.println("===== STEP 4: ADVANCED CONCEPTS & INTEGER CACHE =====\n");

        // ============= DEMONSTRATION 1: Integer Cache Behavior =============
        System.out.println("1. Integer Cache Behavior (-128 to 127):\n");

        // Values within cache range (-128 to 127) use same object
        Integer cachedInt1 = Integer.valueOf(100);
        Integer cachedInt2 = Integer.valueOf(100);

        System.out.println("  Creating two Integer(100) objects:");
        System.out.println("    cachedInt1 = " + cachedInt1);
        System.out.println("    cachedInt2 = " + cachedInt2);
        System.out.println("    cachedInt1 == cachedInt2: " + (cachedInt1 == cachedInt2) + " (same cached object)");
        System.out.println("    cachedInt1.equals(cachedInt2): " + cachedInt1.equals(cachedInt2));

        // ============= DEMONSTRATION 2: Cache Boundary =============
        System.out.println("\n2. Cache Boundary at -128 and 127:\n");

        // Inside cache: -128
        Integer minCache1 = Integer.valueOf(-128);
        Integer minCache2 = Integer.valueOf(-128);
        System.out.println("  Integer(-128) - Inside cache range:");
        System.out.println("    minCache1 == minCache2: " + (minCache1 == minCache2) + " (same cached object)");

        // Inside cache: 127
        Integer maxCache1 = Integer.valueOf(127);
        Integer maxCache2 = Integer.valueOf(127);
        System.out.println("  Integer(127) - Inside cache range:");
        System.out.println("    maxCache1 == maxCache2: " + (maxCache1 == maxCache2) + " (same cached object)");

        // Outside cache: -129 (below range)
        Integer belowCache1 = Integer.valueOf(-129);
        Integer belowCache2 = Integer.valueOf(-129);
        System.out.println("  Integer(-129) - Below cache range:");
        System.out.println("    belowCache1 == belowCache2: " + (belowCache1 == belowCache2) + " (different objects)");

        // Outside cache: 128 (above range)
        Integer aboveCache1 = Integer.valueOf(128);
        Integer aboveCache2 = Integer.valueOf(128);
        System.out.println("  Integer(128) - Above cache range:");
        System.out.println("    aboveCache1 == aboveCache2: " + (aboveCache1 == aboveCache2) + " (different objects)");

        // ============= DEMONSTRATION 3: Why == is Dangerous =============
        System.out.println("\n3. Why == is Dangerous for Comparing Wrapper Objects:\n");

        Integer a = 100;   // Within cache
        Integer b = 100;   // Within cache
        Integer c = 200;   // Outside cache
        Integer d = 200;   // Outside cache

        System.out.println("  Unpredictable Behavior with ==:");
        System.out.println("    Integer a = 100;");
        System.out.println("    Integer b = 100;");
        System.out.println("    a == b: " + (a == b) + " (true because of cache)");

        System.out.println("\n    Integer c = 200;");
        System.out.println("    Integer d = 200;");
        System.out.println("    c == d: " + (c == d) + " (false because outside cache)");

        System.out.println("\n  ⚠️  CONCLUSION: Results depend on caching, not values!");
        System.out.println("     Using == gives unpredictable results!");

        System.out.println("\n  Always Use .equals() for Comparison:");
        System.out.println("    a.equals(b): " + a.equals(b) + " (correct - compares values)");
        System.out.println("    c.equals(d): " + c.equals(d) + " (correct - compares values)");

        // ============= DEMONSTRATION 4: Auto-Boxing vs valueOf() =============
        System.out.println("\n4. Auto-Boxing Uses valueOf() Internally:\n");

        Integer autoBoxed = 100;           // Compiler calls: Integer.valueOf(100)
        Integer explicitValueOf = Integer.valueOf(100);

        System.out.println("  Integer autoBoxed = 100;           // Auto-boxing");
        System.out.println("  Integer explicitValueOf = Integer.valueOf(100);");
        System.out.println("  autoBoxed == explicitValueOf: " + (autoBoxed == explicitValueOf) + " (both use valueOf)");

        // ============= DEMONSTRATION 5: Different Cache Ranges =============
        System.out.println("\n5. Different Wrapper Classes Have Different Caches:\n");

        // Integer: -128 to 127
        Integer intA = Integer.valueOf(100);
        Integer intB = Integer.valueOf(100);
        System.out.println("  Integer Cache: -128 to 127");
        System.out.println("    Integer(100) == Integer(100): " + (intA == intB));

        // Boolean: Only true and false (always same objects)
        Boolean boolA = Boolean.valueOf(true);
        Boolean boolB = Boolean.valueOf(true);
        System.out.println("  Boolean Cache: Only true and false");
        System.out.println("    Boolean.TRUE == Boolean.TRUE: " + (boolA == boolB) + " (always true)");

        // Byte: -128 to 127 (full byte range)
        Byte byteA = Byte.valueOf((byte) 100);
        Byte byteB = Byte.valueOf((byte) 100);
        System.out.println("  Byte Cache: -128 to 127");
        System.out.println("    Byte(100) == Byte(100): " + (byteA == byteB));

        // Long: -128 to 127
        Long longA = Long.valueOf(100L);
        Long longB = Long.valueOf(100L);
        System.out.println("  Long Cache: -128 to 127");
        System.out.println("    Long(100) == Long(100): " + (longA == longB));

        // Double: NO CACHE (always new objects)
        Double doubleA = Double.valueOf(100.0);
        Double doubleB = Double.valueOf(100.0);
        System.out.println("  Double Cache: NO CACHE");
        System.out.println("    Double(100.0) == Double(100.0): " + (doubleA == doubleB) + " (different objects)");
        System.out.println("    Double(100.0).equals(Double(100.0)): " + doubleA.equals(doubleB) + " (same value)");

        // Float: NO CACHE (always new objects)
        Float floatA = Float.valueOf(100.0f);
        Float floatB = Float.valueOf(100.0f);
        System.out.println("  Float Cache: NO CACHE");
        System.out.println("    Float(100.0) == Float(100.0): " + (floatA == floatB) + " (different objects)");
        System.out.println("    Float(100.0).equals(Float(100.0)): " + floatA.equals(floatB) + " (same value)");

        // ============= DEMONSTRATION 6: valueOf() vs new Constructor =============
        System.out.println("\n6. valueOf() vs new Constructor:\n");

        Integer newObj1 = new Integer(100);
        Integer newObj2 = new Integer(100);
        Integer valueOfObj1 = Integer.valueOf(100);
        Integer valueOfObj2 = Integer.valueOf(100);

        System.out.println("  Using new Constructor (always creates new object):");
        System.out.println("    new Integer(100) == new Integer(100): " + (newObj1 == newObj2) + " (different objects)");

        System.out.println("  Using valueOf() (uses cache if available):");
        System.out.println("    Integer.valueOf(100) == Integer.valueOf(100): " + (valueOfObj1 == valueOfObj2) + " (same cached object)");

        System.out.println("  new Integer(100) == Integer.valueOf(100): " + (newObj1 == valueOfObj1) + " (different objects)");

        // ============= DEMONSTRATION 7: Practical Impact =============
        System.out.println("\n7. Practical Impact in Real Code:\n");

        System.out.println("  Example: Processing data with Integer caching");
        
        Integer processedValue = 50;
        Integer cachedValue = Integer.valueOf(50);

        if (processedValue == cachedValue) {
            System.out.println("    processedValue == cachedValue: true (by accident!)");
        }

        // Now try with value outside cache
        Integer processedValueLarge = 500;
        Integer cachedValueLarge = Integer.valueOf(500);

        if (processedValueLarge == cachedValueLarge) {
            System.out.println("    processedValueLarge == cachedValueLarge: true");
        } else {
            System.out.println("    processedValueLarge == cachedValueLarge: false (outside cache)");
        }

        System.out.println("    This can lead to BUGS that are hard to find!");

        // ============= DEMONSTRATION 8: Correct Way =============
        System.out.println("\n8. The Correct Way to Compare Wrapper Objects:\n");

        Integer val1 = 100;
        Integer val2 = 100;
        Integer val3 = new Integer(100);

        System.out.println("  Always use equals() for value comparison:");
        System.out.println("    val1.equals(val2): " + val1.equals(val2) + " ✓");
        System.out.println("    val1.equals(val3): " + val1.equals(val3) + " ✓");

        System.out.println("\n  Use == only to check if it's the SAME object (rare):");
        System.out.println("    val1 == val2: " + (val1 == val2) + " (same cached object)");
        System.out.println("    val1 == val3: " + (val1 == val3) + " (different objects)");

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Integer caches -128 to 127 for performance");
        System.out.println("✓ Boolean has special cache (true, false)");
        System.out.println("✓ Float and Double have NO cache");
        System.out.println("✓ NEVER use == to compare wrapper objects");
        System.out.println("✓ ALWAYS use .equals() for value comparison");
        System.out.println("✓ valueOf() uses cache, new Constructor doesn't");
        System.out.println("✓ Caching behavior can lead to subtle bugs");
        System.out.println("✓ Caching is internal implementation detail");
    }
}

