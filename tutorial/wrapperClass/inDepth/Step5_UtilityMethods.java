package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * STEP 5: UTILITY METHODS OF WRAPPER CLASSES
 * 
 * Every wrapper class provides useful static and instance methods:
 * 
 * STATIC METHODS (Class methods - called on class):
 * ================================================
 * - valueOf()          : Convert primitive or String to wrapper
 * - parseInt()         : Parse String to primitive
 * - parseDouble()      : Parse String to primitive double
 * - parseBoolean()     : Parse String to primitive boolean
 * - toBinaryString()   : Convert to binary representation
 * - toHexString()      : Convert to hexadecimal
 * - toOctalString()    : Convert to octal
 * - getInteger()       : Get value from System property
 * - getDeclaredField() : Get field information
 * - TYPE              : Holds the Class object of primitive type
 * - MAX_VALUE / MIN_VALUE : Maximum and minimum values
 *
 * INSTANCE METHODS (Object methods - called on instance):
 * =======================================================
 * - intValue()         : Extract as int
 * - doubleValue()      : Extract as double
 * - floatValue()       : Extract as float
 * - longValue()        : Extract as long
 * - shortValue()       : Extract as short
 * - byteValue()        : Extract as byte
 * - toString()         : Convert to String
 * - equals()           : Compare values
 * - hashCode()         : Get hash code
 * - compareTo()        : Compare two wrapper objects
 * - compare()          : Static method to compare values
 */

public class Step5_UtilityMethods {

    public static void main(String[] args) {
        System.out.println("===== STEP 5: UTILITY METHODS OF WRAPPER CLASSES =====\n");

        // ============= DEMONSTRATION 1: valueOf() Method =============
        System.out.println("1. valueOf() - Convert to Wrapper Object:\n");

        // Convert primitive to wrapper
        Integer wrapInt = Integer.valueOf(100);
        Double wrapDouble = Double.valueOf(3.14);
        Boolean wrapBool = Boolean.valueOf(true);

        System.out.println("  Integer.valueOf(100) = " + wrapInt + " (Type: Integer)");
        System.out.println("  Double.valueOf(3.14) = " + wrapDouble + " (Type: Double)");
        System.out.println("  Boolean.valueOf(true) = " + wrapBool + " (Type: Boolean)");

        // Convert String to wrapper
        Integer wrapIntStr = Integer.valueOf("500");
        Double wrapDoubleStr = Double.valueOf("99.99");
        Boolean wrapBoolStr = Boolean.valueOf("false");

        System.out.println("\n  Integer.valueOf(\"500\") = " + wrapIntStr);
        System.out.println("  Double.valueOf(\"99.99\") = " + wrapDoubleStr);
        System.out.println("  Boolean.valueOf(\"false\") = " + wrapBoolStr);

        // ============= DEMONSTRATION 2: Parsing Methods =============
        System.out.println("\n2. Parse Methods - String to Primitive (or Wrapper):\n");

        // parseInt() returns primitive int (then auto-boxes to Integer)
        Integer parsedInt = Integer.parseInt("250");
        System.out.println("  Integer.parseInt(\"250\") = " + parsedInt);

        // parseDouble() returns primitive double (then auto-boxes to Double)
        Double parsedDouble = Double.parseDouble("77.55");
        System.out.println("  Double.parseDouble(\"77.55\") = " + parsedDouble);

        // parseBoolean() returns primitive boolean (then auto-boxes to Boolean)
        Boolean parsedBool = Boolean.parseBoolean("true");
        System.out.println("  Boolean.parseBoolean(\"true\") = " + parsedBool);

        // parseLong() returns primitive long (then auto-boxes to Long)
        Long parsedLong = Long.parseLong("999999");
        System.out.println("  Long.parseLong(\"999999\") = " + parsedLong);

        // ============= DEMONSTRATION 3: Extracting Primitive Values =============
        System.out.println("\n3. Value Extraction Methods - Wrapper to Primitive:\n");

        Integer wrappedInt = 100;
        Double wrappedDouble = 25.5;
        Long wrappedLong = 123456789L;

        // Extract different primitive types
        int extractedInt = wrappedInt.intValue();
        double extractedDouble = wrappedInt.doubleValue();      // Converting int to double
        long extractedLong = wrappedInt.longValue();            // Converting int to long
        float extractedFloat = wrappedDouble.floatValue();      // Converting double to float

        System.out.println("  wrappedInt.intValue() = " + extractedInt);
        System.out.println("  wrappedInt.doubleValue() = " + extractedDouble + " (int converted to double)");
        System.out.println("  wrappedInt.longValue() = " + extractedLong + " (int converted to long)");
        System.out.println("  wrappedDouble.floatValue() = " + extractedFloat + " (double converted to float)");

        // ============= DEMONSTRATION 4: toString() Method =============
        System.out.println("\n4. toString() - Convert to String:\n");

        Integer intValue = 123;
        Double doubleValue = 45.67;
        Boolean boolValue = true;

        String intStr = intValue.toString();
        String doubleStr = doubleValue.toString();
        String boolStr = boolValue.toString();

        System.out.println("  Integer(123).toString() = \"" + intStr + "\"");
        System.out.println("  Double(45.67).toString() = \"" + doubleStr + "\"");
        System.out.println("  Boolean(true).toString() = \"" + boolStr + "\"");

        // Static method toString()
        String staticIntStr = Integer.toString(789);
        String staticDoubleStr = Double.toString(99.99);

        System.out.println("  Integer.toString(789) = \"" + staticIntStr + "\"");
        System.out.println("  Double.toString(99.99) = \"" + staticDoubleStr + "\"");

        // ============= DEMONSTRATION 5: MIN and MAX Constants =============
        System.out.println("\n5. MIN_VALUE and MAX_VALUE Constants:\n");

        System.out.println("  Integer.MIN_VALUE = " + Integer.MIN_VALUE);
        System.out.println("  Integer.MAX_VALUE = " + Integer.MAX_VALUE);

        System.out.println("\n  Long.MIN_VALUE = " + Long.MIN_VALUE);
        System.out.println("  Long.MAX_VALUE = " + Long.MAX_VALUE);

        System.out.println("\n  Double.MIN_VALUE = " + Double.MIN_VALUE);
        System.out.println("  Double.MAX_VALUE = " + Double.MAX_VALUE);

        System.out.println("\n  Float.MIN_VALUE = " + Float.MIN_VALUE);
        System.out.println("  Float.MAX_VALUE = " + Float.MAX_VALUE);

        System.out.println("\n  Byte.MIN_VALUE = " + Byte.MIN_VALUE);
        System.out.println("  Byte.MAX_VALUE = " + Byte.MAX_VALUE);

        // ============= DEMONSTRATION 6: Number Base Conversion =============
        System.out.println("\n6. Number Base Conversion (Binary, Hex, Octal):\n");

        int number = 255;

        String binaryStr = Integer.toBinaryString(number);
        String hexStr = Integer.toHexString(number);
        String octalStr = Integer.toOctalString(number);

        System.out.println("  Number: " + number);
        System.out.println("  toBinaryString(255) = \"" + binaryStr + "\" (binary)");
        System.out.println("  toHexString(255) = \"" + hexStr + "\" (hexadecimal)");
        System.out.println("  toOctalString(255) = \"" + octalStr + "\" (octal)");

        // Parsing from different bases
        int fromBinary = Integer.parseInt("11111111", 2);        // Base 2
        int fromHex = Integer.parseInt("FF", 16);                // Base 16
        int fromOctal = Integer.parseInt("377", 8);              // Base 8

        System.out.println("\n  Parsing from different bases:");
        System.out.println("  Integer.parseInt(\"11111111\", 2) = " + fromBinary + " (from binary)");
        System.out.println("  Integer.parseInt(\"FF\", 16) = " + fromHex + " (from hexadecimal)");
        System.out.println("  Integer.parseInt(\"377\", 8) = " + fromOctal + " (from octal)");

        // ============= DEMONSTRATION 7: compareTo() Method =============
        System.out.println("\n7. compareTo() - Compare Wrapper Objects:\n");

        Integer num1 = 100;
        Integer num2 = 200;
        Integer num3 = 100;

        int compResult1 = num1.compareTo(num2);  // Returns negative (num1 < num2)
        int compResult2 = num2.compareTo(num1);  // Returns positive (num2 > num1)
        int compResult3 = num1.compareTo(num3);  // Returns 0 (num1 == num3)

        System.out.println("  100.compareTo(200) = " + compResult1 + " (negative = smaller)");
        System.out.println("  200.compareTo(100) = " + compResult2 + " (positive = larger)");
        System.out.println("  100.compareTo(100) = " + compResult3 + " (zero = equal)");

        // ============= DEMONSTRATION 8: compare() Static Method =============
        System.out.println("\n8. Static compare() Method:\n");

        int comp1 = Integer.compare(50, 100);   // Returns negative (50 < 100)
        int comp2 = Integer.compare(100, 50);   // Returns positive (100 > 50)
        int comp3 = Integer.compare(100, 100);  // Returns 0 (equal)

        System.out.println("  Integer.compare(50, 100) = " + comp1);
        System.out.println("  Integer.compare(100, 50) = " + comp2);
        System.out.println("  Integer.compare(100, 100) = " + comp3);

        // ============= DEMONSTRATION 9: equals() and hashCode() =============
        System.out.println("\n9. equals() and hashCode() Methods:\n");

        Integer a = 100;
        Integer b = 100;
        Integer c = new Integer(100);

        System.out.println("  a.equals(b) = " + a.equals(b) + " (same value)");
        System.out.println("  a.equals(c) = " + a.equals(c) + " (same value, different object)");

        System.out.println("\n  Hash codes:");
        System.out.println("  a.hashCode() = " + a.hashCode());
        System.out.println("  b.hashCode() = " + b.hashCode());
        System.out.println("  c.hashCode() = " + c.hashCode());

        // ============= DEMONSTRATION 10: TYPE Constant =============
        System.out.println("\n10. TYPE Constant - Class Object of Primitive:\n");

        System.out.println("  Integer.TYPE = " + Integer.TYPE);
        System.out.println("  Integer.TYPE.getName() = " + Integer.TYPE.getName());

        System.out.println("\n  Double.TYPE = " + Double.TYPE);
        System.out.println("  Double.TYPE.getName() = " + Double.TYPE.getName());

        System.out.println("\n  Boolean.TYPE = " + Boolean.TYPE);
        System.out.println("  Boolean.TYPE.getName() = " + Boolean.TYPE.getName());

        // ============= DEMONSTRATION 11: Practical Example - Data Parsing =============
        System.out.println("\n11. Practical Example - Parsing and Storing Data:\n");

        // Simulating data from user input or API
        String[] userAges = {"25", "30", "invalid", "45"};
        List<Integer> validAges = new ArrayList<>();

        for (String ageStr : userAges) {
            try {
                Integer age = Integer.valueOf(ageStr);
                if (age >= 0 && age <= Integer.MAX_VALUE) {
                    validAges.add(age);
                }
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Invalid age: " + ageStr + " (skipped)");
            }
        }

        System.out.println("  Valid ages: " + validAges);

        // ============= DEMONSTRATION 12: Working with Collections =============
        System.out.println("\n12. Wrapper Methods in Collections:\n");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);

        // Using wrapper methods to manipulate collection
        for (String name : scores.keySet()) {
            Integer score = scores.get(name);
            String status = score >= 90 ? "Pass" : "Fail";
            System.out.println("  " + name + ": " + score + " -> " + status);
        }

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ valueOf() - Convert primitive or String to wrapper");
        System.out.println("✓ parseInt(), parseDouble(), etc - Parse String to primitive");
        System.out.println("✓ intValue(), doubleValue(), etc - Extract primitive value");
        System.out.println("✓ toString() - Convert wrapper to String");
        System.out.println("✓ MIN_VALUE, MAX_VALUE - Get range limits");
        System.out.println("✓ toBinaryString(), toHexString() - Convert to different bases");
        System.out.println("✓ compareTo(), compare() - Compare values");
        System.out.println("✓ equals(), hashCode() - Compare objects and get hash");
        System.out.println("✓ TYPE - Get Class object of primitive type");
    }
}

