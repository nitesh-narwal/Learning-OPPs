package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * STEP 2: PRACTICAL APPLICATIONS OF WRAPPER CLASSES
 * 
 * Where are Wrapper Classes Used?
 * ================================
 * 1. Collections Framework (ArrayList, HashMap, etc.) - Only accepts Objects
 * 2. Method parameters that expect Objects
 * 3. Storing primitive values in data structures
 * 4. Converting between primitive types and Strings
 * 5. Method parameters in APIs and frameworks
 * 6. Null handling when no value is provided
 *
 * Why Collections Need Wrapper Classes:
 * ======================================
 * Java Collections use Generic types (type parameters) which only work with Objects.
 * They cannot directly store primitive types. That's where wrapper classes come in!
 */

public class Step2_PracticalApplications {

    public static void main(String[] args) {
        System.out.println("===== STEP 2: PRACTICAL APPLICATIONS OF WRAPPER CLASSES =====\n");

        // ============= APPLICATION 1: Using Wrapper Classes with Collections =============
        System.out.println("1. Wrapper Classes with Collections (ArrayList, HashMap):\n");

        // WITHOUT wrapper classes, this is IMPOSSIBLE:
        // ArrayList<int> numbers = new ArrayList<>();  // ERROR! primitives not allowed
        
        // WITH wrapper classes, this WORKS:
        List<Integer> numbersList = new ArrayList<>();
        numbersList.add(10);           // auto-boxing: 10 -> Integer(10)
        numbersList.add(20);
        numbersList.add(30);
        numbersList.add(null);         // Wrapper classes can store null!

        System.out.println("ArrayList<Integer> with auto-boxing:");
        System.out.println("  numbersList = " + numbersList);
        System.out.println("  numbersList.get(0) = " + numbersList.get(0) + " (auto-unboxed to int)");

        // HashMap requires Objects as keys and values
        Map<String, Double> salaryMap = new HashMap<>();
        salaryMap.put("Alice", 50000.0);    // auto-boxing: 50000.0 -> Double(50000.0)
        salaryMap.put("Bob", 60000.0);
        salaryMap.put("Charlie", null);     // Can store null value

        System.out.println("\nHashMap<String, Double> with auto-boxing:");
        System.out.println("  salaryMap = " + salaryMap);
        System.out.println("  salaryMap.get(\"Alice\") = " + salaryMap.get("Alice"));

        // List of Booleans
        List<Boolean> flags = new ArrayList<>();
        flags.add(true);                    // auto-boxing
        flags.add(false);
        flags.add(null);

        System.out.println("\nArrayList<Boolean> with auto-boxing:");
        System.out.println("  flags = " + flags);

        // ============= APPLICATION 2: Method Parameters Expecting Objects =============
        System.out.println("\n2. Methods Expecting Objects (Not Primitives):\n");

        Integer value1 = 50;
        Integer value2 = 100;

        // This method expects Integer objects, not primitive ints
        Integer maxValue = findMax(value1, value2);
        System.out.println("  findMax(50, 100) = " + maxValue);

        // This method expects wrapper objects in a List
        List<Integer> numbers = new ArrayList<>();
        numbers.add(15);
        numbers.add(25);
        numbers.add(35);
        numbers.add(5);

        Integer max = findMaxInList(numbers);
        System.out.println("  findMaxInList([15, 25, 35, 5]) = " + max);

        // ============= APPLICATION 3: Converting Strings to Numbers =============
        System.out.println("\n3. String to Number Conversion:\n");

        String userInput1 = "250";
        String userInput2 = "99.99";
        String userInput3 = "false";

        try {
            // Parse strings to numbers
            int parsedInt = Integer.parseInt(userInput1);
            double parsedDouble = Double.parseDouble(userInput2);
            boolean parsedBool = Boolean.parseBoolean(userInput3);

            System.out.println("  String \"250\" -> int: " + parsedInt);
            System.out.println("  String \"99.99\" -> double: " + parsedDouble);
            System.out.println("  String \"false\" -> boolean: " + parsedBool);

        } catch (NumberFormatException e) {
            System.out.println("  Error: Invalid number format!");
        }

        // ============= APPLICATION 4: Null Handling =============
        System.out.println("\n4. Null Handling (Primitives Cannot Be Null):\n");

        // Scenario: Database returns null for missing values
        Integer databaseValue = null;  // Wrapper can be null

        // Primitives cannot be null
        // int primitiveValue = null;  // ERROR! Compilation error

        System.out.println("  databaseValue = " + databaseValue);
        System.out.println("  (Primitives cannot be null, causing NullPointerException)");

        // Null-safe checking
        if (databaseValue != null) {
            System.out.println("  Value is: " + databaseValue);
        } else {
            System.out.println("  Value is null (database had no data)");
        }

        // ============= APPLICATION 5: Creating Objects with Default Values =============
        System.out.println("\n5. Default Values in Objects:\n");

        StudentGrade student1 = new StudentGrade("Alice", 85);
        StudentGrade student2 = new StudentGrade("Bob", null);  // null for absent student

        System.out.println("  Student 1: " + student1);
        System.out.println("  Student 2: " + student2);
        System.out.println("  Student 2 has no grade because grade is null");

        // ============= APPLICATION 6: Type Information =============
        System.out.println("\n6. Accessing Type Information:\n");

        Integer intObj = 100;
        Double doubleObj = 99.99;
        Boolean boolObj = true;

        System.out.println("  Integer object class: " + intObj.getClass());
        System.out.println("  Double object class: " + doubleObj.getClass());
        System.out.println("  Boolean object class: " + boolObj.getClass());

        // ============= APPLICATION 7: Comparison =============
        System.out.println("\n7. Comparing Wrapper Objects:\n");

        Integer num1 = 100;
        Integer num2 = 100;
        Integer num3 = 200;

        // Use equals() for content comparison (not ==)
        System.out.println("  num1.equals(num2) = " + num1.equals(num2) + " (content comparison)");
        System.out.println("  num1.equals(num3) = " + num1.equals(num3));

        // Use compareTo() for ordering
        System.out.println("  num1.compareTo(num3) = " + num1.compareTo(num3) + " (negative = smaller)");
        System.out.println("  num3.compareTo(num1) = " + num3.compareTo(num1) + " (positive = larger)");

        // ============= APPLICATION 8: Methods Returning Objects =============
        System.out.println("\n8. Methods Returning Wrapper Objects:\n");

        // Method returns Integer object, not primitive
        Integer result = calculateTotal(10, 20, 30);
        System.out.println("  calculateTotal(10, 20, 30) = " + result);

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ Collections require wrapper classes, not primitives");
        System.out.println("✓ Many API methods expect Objects as parameters");
        System.out.println("✓ Wrapper classes can store null values");
        System.out.println("✓ Useful for converting Strings to numbers");
        System.out.println("✓ Provides type information and comparison methods");
    }

    // ============= HELPER METHODS =============

    /**
     * Method that expects Integer objects (not primitives)
     */
    public static Integer findMax(Integer a, Integer b) {
        return (a != null && b != null) ? Math.max(a, b) : (a != null ? a : b);
    }

    /**
     * Method that works with a List of wrapper objects
     */
    public static Integer findMaxInList(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) return null;
        
        Integer max = numbers.get(0);
        for (Integer num : numbers) {
            if (num != null && num > max) {
                max = num;
            }
        }
        return max;
    }

    /**
     * Method returns wrapper object (Integer)
     */
    public static Integer calculateTotal(Integer a, Integer b, Integer c) {
        if (a == null) a = 0;
        if (b == null) b = 0;
        if (c == null) c = 0;
        return a + b + c;
    }

    /**
     * Simple class demonstrating wrapper class usage
     */
    static class StudentGrade {
        String name;
        Integer grade;  // Wrapper class - can be null for absent students

        StudentGrade(String name, Integer grade) {
            this.name = name;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return name + " -> Grade: " + (grade != null ? grade : "Absent");
        }
    }
}

