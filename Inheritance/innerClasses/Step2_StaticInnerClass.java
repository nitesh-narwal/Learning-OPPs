package me.niteshh.OPPs.Inheritance.innerClasses;

/**
 * STEP 2: STATIC INNER CLASS
 * 
 * A static inner class is a class defined inside another class with static keyword.
 * It is also called a "nested static class".
 * 
 * KEY DIFFERENCES FROM MEMBER INNER CLASS:
 * ✓ Does NOT require an outer class instance to be created
 * ✓ NO implicit access to outer class instance members (only static members)
 * ✓ Can have both static and instance members
 * ✓ Works like a regular class, but logically grouped with outer class
 * ✓ Can be instantiated without outer class instance
 * 
 * REAL-WORLD ANALOGY:
 * Think of a Car Company (outer class) with a Blueprint (static inner class).
 * The Blueprint doesn't belong to a specific Car; it's shared by all Cars.
 * You don't need a Car to access the Blueprint.
 */

public class Step2_StaticInnerClass {

    // ==================== OUTER CLASS MEMBERS ====================
    
    // Static variable - shared by all instances
    public static int totalCarsSold = 150;
    
    // Instance variable - specific to each instance
    private String companySeminar = "Auto Expo 2026";
    
    // Static method
    public static void showCompanyStats() {
        System.out.println("Total Cars Sold: " + totalCarsSold);
    }
    
    // ==================== STATIC INNER CLASS ====================
    
    /**
     * STEP 2.1: Basic Static Inner Class
     * This represents a Car Blueprint/Specification.
     * It doesn't need a Car Company instance to exist.
     */
    public static class CarSpecification {
        
        // Static inner class CAN have static members
        public static String manufacturingStandard = "ISO 26262";
        
        // Instance variables
        private String model;
        private String fuelType;
        private int engineCapacity;
        
        // Constructor
        public CarSpecification(String model, String fuelType, int capacity) {
            this.model = model;
            this.fuelType = fuelType;
            this.engineCapacity = capacity;
        }
        
        // Static method in static inner class
        public static void displayManufacturingStandard() {
            System.out.println("Manufacturing Standard: " + manufacturingStandard);
        }
        
        // Instance method
        public void displaySpecification() {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("Car Specification:");
            System.out.println("Model: " + model);
            System.out.println("Fuel Type: " + fuelType);
            System.out.println("Engine Capacity: " + engineCapacity + " cc");
            
            // KEY POINT: Cannot access instance members of outer class
            // System.out.println(companySeminar);  // ❌ ERROR: Cannot access instance variable
            
            // But CAN access static members of outer class
            System.out.println("Total Cars Sold: " + totalCarsSold);  // ✓ Can access static variable
            System.out.println("=".repeat(40));
        }
        
        @Override
        public String toString() {
            return model + " - " + fuelType + " (" + engineCapacity + "cc)";
        }
    }
    
    // ==================== ANOTHER STATIC INNER CLASS ====================
    
    /**
     * STEP 2.2: Another Static Inner Class
     * Represents engine specifications that are standard across all cars.
     */
    public static class EngineSpecification {
        
        private String engineName;
        private int maxRPM;
        private double torque;  // in Nm (Newton-meters)
        
        public EngineSpecification(String engineName, int maxRPM, double torque) {
            this.engineName = engineName;
            this.maxRPM = maxRPM;
            this.torque = torque;
        }
        
        public void displayEngineSpec() {
            System.out.println("\nEngine Specifications:");
            System.out.println("Engine Name: " + engineName);
            System.out.println("Max RPM: " + maxRPM);
            System.out.println("Torque: " + torque + " Nm");
        }
        
        public int getMaxRPM() {
            return maxRPM;
        }
    }
}

// ==================== SUMMARY OF STATIC INNER CLASS ====================
/*
 * CHARACTERISTICS:
 * 1. Does NOT require an outer class instance
 * 2. NO implicit 'this' reference to outer class
 * 3. Can ONLY access static members of outer class
 * 4. Can have static members (both variables and methods)
 * 5. Behaves like a regular nested class
 * 
 * INSTANTIATION:
 * OuterClass.StaticInnerClass obj = new OuterClass.StaticInnerClass();
 * NO NEED for outer class instance
 * 
 * MEMORY IMPACT:
 * - No reference overhead like member inner classes
 * - More memory efficient
 * - Safe from memory leaks due to implicit references
 * 
 * KEY DIFFERENCE FROM MEMBER INNER CLASS:
 * 
 * Member Inner Class:
 *   Car car = new Car();
 *   Car.Engine engine = car.new Engine();
 *   ↳ Requires outer class instance
 * 
 * Static Inner Class:
 *   Car.Blueprint blueprint = new Car.Blueprint();
 *   ↳ Does NOT require outer class instance
 * 
 * USE CASES:
 * ✓ Utility classes related to outer class
 * ✓ Data holder classes (DTOs, POJOs)
 * ✓ Builder pattern implementation
 * ✓ Configuration/Specification classes
 * ✓ When you want logical grouping without instance dependency
 * ✓ Factory classes
 * 
 * BEST PRACTICE:
 * Prefer static inner classes over member inner classes
 * when you don't need access to outer class instance members.
 */

