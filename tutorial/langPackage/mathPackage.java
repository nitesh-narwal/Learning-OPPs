package me.niteshh.OPPs.tutorial.langPackage;

public class mathPackage {
    static void main() {
        int a = 10;
        int b = 20;

        // Using Math class to perform basic operations
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        double sqrtA = Math.sqrt(a);
        double powAB = Math.pow(a, b);

        System.out.println("Max: " + max); // Max: 20
        System.out.println("Min: " + min); // Min: 10
        System.out.println("Square Root of a: " + sqrtA); // Square Root of a: 3.1622776601683795
        System.out.println("a raised to the power of b: " + powAB); // a raised to the power of b: 1.0000000000000002E10

        double random = Math.random();
        System.out.println("Random number: " + random);

        int i = Math.abs(-5);
        System.out.println("Absolute value of -5: " + i); // Absolute value of -5: 5

        long round = Math.round(1.5);
        System.out.println("Rounding 1.5: " + round); // Rounding 1.5: 2

        double floor = Math.floor(1.5);
        System.out.println("Flooring 1.5: " + floor); // Flooring 1.5: 1.0

        double ceil = Math.ceil(1.5);
        System.out.println("Ceiling 1.5: " + ceil); // Ceiling 1.5: 2.0

        double v = Math.nextAfter(1.5, 2.5);
        System.out.println("Next after 1.5: " + v); // Next after 1.5: 3.5


    }
}
