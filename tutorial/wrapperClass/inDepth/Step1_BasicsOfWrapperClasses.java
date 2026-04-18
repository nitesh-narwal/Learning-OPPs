package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

/**
 * STEP 1: BASICS OF WRAPPER CLASSES
 * 
 * What are Wrapper Classes?
 * ========================
 * Wrapper classes are classes that "wrap" primitive data types (int, double, boolean, etc.)
 * and convert them into objects. They are part of Java's java.lang package.
 *
 * Why do we need Wrapper Classes?
 * ===============================
 * 1. Java Collections (ArrayList, HashMap) only work with objects, not primitives
 * 2. Methods often expect Objects as parameters, not primitives
 * 3. Some methods return Objects that represent primitive values
 * 4. We need to perform operations on primitive values as objects
 *
 * All Primitive Types and Their Wrapper Classes:
 * ==============================================
 * Primitive       -> Wrapper Class     (Package: java.lang)
 * boolean         -> Boolean
 * byte            -> Byte
 * char            -> Character
 * short           -> Short
 * int             -> Integer
 * long            -> Long
 * float           -> Float
 * double          -> Double
 *
 * Note: All wrapper classes (except Character and Boolean) inherit from Number class.
 *       All wrapper classes inherit from Object class.
 */

public class Step1_BasicsOfWrapperClasses {

    public static void main(String[] args) {
        System.out.println("===== STEP 1: BASICS OF WRAPPER CLASSES =====\n");

        // ============= DEMONSTRATION 1: Creating Wrapper Objects =============
        System.out.println("1. Creating Wrapper Objects:\n");

        // Method 1: Using Constructor (Old Way - Deprecated in Java 9+)
        Integer integerObject1 = new Integer(25);      // Wrapping primitive int into Integer object
        Double doubleObject1 = new Double(3.14);       // Wrapping primitive double into Double object
        Boolean booleanObject1 = new Boolean(true);    // Wrapping primitive boolean into Boolean object
        
        System.out.println("Using Constructors (Deprecated in Java 9+):");
        System.out.println("  integerObject1 = " + integerObject1);
        System.out.println("  doubleObject1 = " + doubleObject1);
        System.out.println("  booleanObject1 = " + booleanObject1);

        // Method 2: Using valueOf() method (Recommended)
        Integer integerObject2 = Integer.valueOf(50);
        Double doubleObject2 = Double.valueOf(2.71);
        Boolean booleanObject2 = Boolean.valueOf(false);
        
        System.out.println("\nUsing valueOf() method (Recommended):");
        System.out.println("  integerObject2 = " + integerObject2);
        System.out.println("  doubleObject2 = " + doubleObject2);
        System.out.println("  booleanObject2 = " + booleanObject2);

        // ============= DEMONSTRATION 2: Auto-Boxing =============
        System.out.println("\n2. Auto-Boxing (Converting Primitive to Wrapper):\n");
        System.out.println("Auto-boxing automatically wraps primitive into wrapper object");
        System.out.println("This feature was introduced in Java 5+\n");

        // The compiler automatically converts int to Integer
        Integer autoBoxedInt = 100;              // Compiler converts: 100 -> Integer.valueOf(100)
        Double autoBoxedDouble = 99.99;          // Compiler converts: 99.99 -> Double.valueOf(99.99)
        Boolean autoBoxedBool = true;            // Compiler converts: true -> Boolean.valueOf(true)
        Long autoBoxedLong = 999999L;            // Compiler converts: 999999L -> Long.valueOf(999999L)

        System.out.println("  autoBoxedInt = " + autoBoxedInt + " (Type: " + autoBoxedInt.getClass().getSimpleName() + ")");
        System.out.println("  autoBoxedDouble = " + autoBoxedDouble + " (Type: " + autoBoxedDouble.getClass().getSimpleName() + ")");
        System.out.println("  autoBoxedBool = " + autoBoxedBool + " (Type: " + autoBoxedBool.getClass().getSimpleName() + ")");
        System.out.println("  autoBoxedLong = " + autoBoxedLong + " (Type: " + autoBoxedLong.getClass().getSimpleName() + ")");

        // ============= DEMONSTRATION 3: Un-Boxing =============
        System.out.println("\n3. Un-Boxing (Converting Wrapper to Primitive):\n");
        System.out.println("Un-boxing automatically converts wrapper object back to primitive\n");

        Integer wrappedValue = 42;
        Double wrappedDouble = 7.5;
        Boolean wrappedBoolean = true;

        // The compiler automatically extracts the primitive value
        int extractedInt = wrappedValue;        // Compiler calls: wrappedValue.intValue()
        double extractedDouble = wrappedDouble; // Compiler calls: wrappedDouble.doubleValue()
        boolean extractedBool = wrappedBoolean; // Compiler calls: wrappedBoolean.booleanValue()

        System.out.println("  extractedInt = " + extractedInt + " (Type: primitive int)");
        System.out.println("  extractedDouble = " + extractedDouble + " (Type: primitive double)");
        System.out.println("  extractedBool = " + extractedBool + " (Type: primitive boolean)");

        // ============= DEMONSTRATION 4: Wrapper vs Primitive Comparison =============
        System.out.println("\n4. Key Differences: Wrapper Classes vs Primitives:\n");

        // Primitives: stored in stack, faster, memory efficient
        int primitiveInt = 50;

        // Wrappers: stored in heap, slower, but objects with methods
        Integer wrapperInt = 50;

        System.out.println("Primitive int:");
        System.out.println("  - Stored in Stack");
        System.out.println("  - No methods available");
        System.out.println("  - Memory efficient");
        System.out.println("  - Faster execution");

        System.out.println("\nWrapper Integer:");
        System.out.println("  - Stored in Heap");
        System.out.println("  - Has utility methods (intValue(), toString(), etc.)");
        System.out.println("  - More memory overhead");
        System.out.println("  - Slightly slower");
        System.out.println("  - Can store null value (primitives cannot)");

        // ============= DEMONSTRATION 5: Null Values =============
        System.out.println("\n5. Wrapper Classes Can Store Null:\n");

        Integer nullableInt = null;
        System.out.println("  nullableInt = " + nullableInt);
        System.out.println("  (Primitives cannot store null value)");

        // ============= DEMONSTRATION 6: Common Methods =============
        System.out.println("\n6. Common Methods Available in Wrapper Classes:\n");

        Integer num = 123;
        
        // Method: intValue() - Extract primitive value
        int primitiveValue = num.intValue();
        System.out.println("  num.intValue() = " + primitiveValue + " (returns int)");

        // Method: toString() - Convert to String
        String stringValue = num.toString();
        System.out.println("  num.toString() = " + stringValue + " (returns String)");

        // Method: compareTo() - Compare two wrapper objects
        Integer num2 = 200;
        int comparison = num.compareTo(num2);
        System.out.println("  num.compareTo(num2) = " + comparison + " (negative = num is smaller)");

        // Method: equals() - Check equality
        Integer num3 = 123;
        boolean isEqual = num.equals(num3);
        System.out.println("  num.equals(num3) = " + isEqual);

        // ============= DEMONSTRATION 7: Type Conversion =============
        System.out.println("\n7. Converting Strings to Wrapper Objects:\n");

        String strNumber = "456";
        String strDouble = "7.89";
        String strBoolean = "true";

        // Parse String to Wrapper objects
        Integer parsedInt = Integer.parseInt(strNumber);      // Returns primitive, then auto-boxes
        Integer parsedInt2 = Integer.valueOf(strNumber);      // Returns Integer directly
        Double parsedDouble = Double.parseDouble(strDouble);
        Boolean parsedBool = Boolean.parseBoolean(strBoolean);

        System.out.println("  String \"456\" -> Integer: " + parsedInt);
        System.out.println("  String \"456\" -> Integer (valueOf): " + parsedInt2);
        System.out.println("  String \"7.89\" -> Double: " + parsedDouble);
        System.out.println("  String \"true\" -> Boolean: " + parsedBool);

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Wrapper classes convert primitives to objects");
        System.out.println("✓ Auto-boxing automatically wraps primitives");
        System.out.println("✓ Un-boxing automatically unwraps wrapper objects");
        System.out.println("✓ Wrapper classes have utility methods");
        System.out.println("✓ Wrapper classes can be null, primitives cannot");
        System.out.println("✓ Use for collections and when you need object behavior");
    }
}

