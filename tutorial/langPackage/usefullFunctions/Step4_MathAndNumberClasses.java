package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

/**
 * STEP 4: MATH CLASS AND NUMBER UTILITIES
 * 
 * Math class provides mathematical operations and constants.
 * Number class is the abstract parent of numeric wrapper classes.
 * 
 * Key Concepts:
 * - Math constants (PI, E)
 * - Mathematical operations (sin, cos, sqrt, pow)
 * - Rounding and comparison methods
 * - Random number generation
 */

public class Step4_MathAndNumberClasses {

    public static void main(String[] args) {
        System.out.println("===== STEP 4: MATH AND NUMBER CLASSES =====\n");

        // ============= 1. MATH CONSTANTS =============
        System.out.println("1️⃣  MATH CONSTANTS:\n");

        System.out.println("  Math.PI: " + Math.PI);
        System.out.println("  Purpose: Mathematical constant π (3.14159...)");

        System.out.println("\n  Math.E: " + Math.E);
        System.out.println("  Purpose: Mathematical constant e (2.71828...)");

        // ============= 2. BASIC ARITHMETIC =============
        System.out.println("\n2️⃣  BASIC ARITHMETIC OPERATIONS:\n");

        int a = 10;
        int b = -20;

        System.out.println("  a = " + a + ", b = " + b);

        // abs() - Absolute value
        System.out.println("\n  Math.abs(a) = " + Math.abs(a));
        System.out.println("  Math.abs(b) = " + Math.abs(b));
        System.out.println("  Purpose: Return absolute value (always positive)");

        // max() and min() - Maximum and minimum
        System.out.println("\n  Math.max(a, b) = " + Math.max(a, b));
        System.out.println("  Math.min(a, b) = " + Math.min(a, b));
        System.out.println("  Purpose: Find largest and smallest of two values");

        // ============= 3. POWER AND SQUARE ROOT =============
        System.out.println("\n3️⃣  POWER AND SQUARE ROOT:\n");

        double num = 9.0;

        // sqrt() - Square root
        System.out.println("  Math.sqrt(" + num + ") = " + Math.sqrt(num));
        System.out.println("  Purpose: Calculate square root");

        // pow() - Power
        System.out.println("\n  Math.pow(2, 8) = " + Math.pow(2, 8));
        System.out.println("  Purpose: Calculate power (2^8)");

        System.out.println("\n  Math.pow(10, 3) = " + Math.pow(10, 3));

        // ============= 4. ROUNDING AND COMPARISON =============
        System.out.println("\n4️⃣  ROUNDING METHODS:\n");

        double value = 3.7;

        // round() - Rounds to nearest integer
        System.out.println("  Value: " + value);
        System.out.println("  Math.round(" + value + ") = " + Math.round(value));
        System.out.println("  Purpose: Round to nearest integer");

        // floor() - Rounds down
        System.out.println("\n  Math.floor(" + value + ") = " + Math.floor(value));
        System.out.println("  Purpose: Round down (largest integer ≤ value)");

        // ceil() - Rounds up
        System.out.println("\n  Math.ceil(" + value + ") = " + Math.ceil(value));
        System.out.println("  Purpose: Round up (smallest integer ≥ value)");

        // ============= 5. TRIGONOMETRIC FUNCTIONS =============
        System.out.println("\n5️⃣  TRIGONOMETRIC FUNCTIONS:\n");

        double angle = Math.PI / 4;  // 45 degrees

        System.out.println("  Angle: π/4 radians (45 degrees)");
        System.out.println("  Math.sin(π/4) = " + Math.sin(angle));
        System.out.println("  Math.cos(π/4) = " + Math.cos(angle));
        System.out.println("  Math.tan(π/4) = " + Math.tan(angle));
        System.out.println("  Purpose: Calculate sine, cosine, tangent values");

        // ============= 6. LOGARITHMIC FUNCTIONS =============
        System.out.println("\n6️⃣  LOGARITHMIC FUNCTIONS:\n");

        double number = 1000.0;

        // log10() - Base 10 logarithm
        System.out.println("  Math.log10(" + number + ") = " + Math.log10(number));
        System.out.println("  Purpose: Calculate logarithm base 10");

        // log() - Natural logarithm (base e)
        System.out.println("\n  Math.log(" + number + ") = " + Math.log(number));
        System.out.println("  Purpose: Calculate natural logarithm (base e)");

        // ============= 7. RANDOM NUMBERS =============
        System.out.println("\n7️⃣  RANDOM NUMBER GENERATION:\n");

        // Math.random() - Returns double between 0.0 and 1.0
        System.out.println("  Math.random() examples:");
        for (int i = 0; i < 3; i++) {
            System.out.println("  Random: " + Math.random());
        }

        System.out.println("\n  ✓ Generate random integer between 1 and 100:");
        int randomInt = (int) (Math.random() * 100) + 1;
        System.out.println("  Random: " + randomInt);

        // ============= 8. SIGN FUNCTIONS =============
        System.out.println("\n8️⃣  SIGN DETECTION:\n");

        double positive = 5.0;
        double negative = -3.0;
        double zero = 0.0;

        // copySign() - Copies sign of one number to another
        System.out.println("  Math.copySign(10, -1) = " + Math.copySign(10, -1));
        System.out.println("  Purpose: Copy sign from second number to first");

        // signum() - Returns sign of number (-1, 0, 1)
        System.out.println("\n  Math.signum(" + positive + ") = " + Math.signum(positive));
        System.out.println("  Math.signum(" + negative + ") = " + Math.signum(negative));
        System.out.println("  Math.signum(" + zero + ") = " + Math.signum(zero));
        System.out.println("  Purpose: Determine sign of number");

        // ============= 9. NUMBER CLASS - CONVERTING STRINGS =============
        System.out.println("\n9️⃣  NUMBER CLASS - STRING CONVERSION:\n");

        String intString = "123";
        String doubleString = "45.67";
        String boolString = "true";

        // parseInt() - String to int
        int intValue = Integer.parseInt(intString);
        System.out.println("  Integer.parseInt(\"" + intString + "\") = " + intValue);

        // parseDouble() - String to double
        double doubleValue = Double.parseDouble(doubleString);
        System.out.println("  Double.parseDouble(\"" + doubleString + "\") = " + doubleValue);

        // parseBoolean() - String to boolean
        boolean boolValue = Boolean.parseBoolean(boolString);
        System.out.println("  Boolean.parseBoolean(\"" + boolString + "\") = " + boolValue);

        System.out.println("  Purpose: Convert strings to numeric types");

        // ============= 10. NUMBER CLASS - TYPE CONVERSION =============
        System.out.println("\n🔟  TYPE CONVERSION METHODS:\n");

        Integer intObj = 100;
        Double doubleObj = 45.67;

        // Converting to different types
        System.out.println("  intObj.doubleValue() = " + intObj.doubleValue());
        System.out.println("  intObj.longValue() = " + intObj.longValue());
        System.out.println("  doubleObj.intValue() = " + doubleObj.intValue());
        System.out.println("  doubleObj.floatValue() = " + doubleObj.floatValue());
        System.out.println("  Purpose: Convert between numeric types");

        // ============= 11. PRACTICAL EXAMPLE: GEOMETRY CALCULATIONS =============
        System.out.println("\n1️⃣1️⃣  PRACTICAL EXAMPLE: Circle Calculations:\n");

        double radius = 5.0;
        double circumference = 2 * Math.PI * radius;
        double area = Math.PI * radius * radius;

        System.out.println("  Circle with radius " + radius + ":");
        System.out.println("  Circumference: " + circumference);
        System.out.println("  Area: " + area);

        // ============= 12. PRACTICAL EXAMPLE: STATISTICS =============
        System.out.println("\n1️⃣2️⃣  PRACTICAL EXAMPLE: Statistical Calculations:\n");

        double[] scores = {85.5, 90.0, 78.5, 92.3, 88.0};
        calculateStatistics(scores);

        // ============= SUMMARY =====
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Math class provides mathematical operations");
        System.out.println("✓ Math.random() for random numbers");
        System.out.println("✓ Use parse methods to convert strings");
        System.out.println("✓ Numeric classes have conversion methods");
        System.out.println("✓ Math operations are static and available everywhere");
    }

    // ============= HELPER METHODS =============

    /**
     * Calculate and display statistics
     */
    static void calculateStatistics(double[] scores) {
        double sum = 0;
        double max = scores[0];
        double min = scores[0];

        for (double score : scores) {
            sum += score;
            max = Math.max(max, score);
            min = Math.min(min, score);
        }

        double average = sum / scores.length;
        double variance = calculateVariance(scores, average);
        double stdDev = Math.sqrt(variance);

        System.out.println("  Count: " + scores.length);
        System.out.println("  Sum: " + sum);
        System.out.println("  Average: " + average);
        System.out.println("  Maximum: " + max);
        System.out.println("  Minimum: " + min);
        System.out.println("  Standard Deviation: " + stdDev);
    }

    /**
     * Calculate variance
     */
    static double calculateVariance(double[] scores, double average) {
        double varianceSum = 0;
        for (double score : scores) {
            varianceSum += Math.pow(score - average, 2);
        }
        return varianceSum / scores.length;
    }
}

