package me.niteshh.OPPs.Inheritance.innerClasses;

/**
 * STEP 1: MEMBER INNER CLASS
 * 
 * A member inner class is a class defined inside another class.
 * It is also called a "nested non-static class".
 * 
 * KEY POINTS:
 * ✓ It is associated with an instance of the outer class
 * ✓ Has implicit access to all members of the outer class (including private)
 * ✓ To create an instance, you MUST have an outer class instance
 * ✓ It cannot have static members (except static final constants)
 * 
 * REAL-WORLD ANALOGY:
 * Think of a Car (outer class) with an Engine (member inner class).
 * The Engine belongs to a specific Car instance.
 * You cannot have an Engine without a Car.
 */

public class Step1_MemberInnerClass {

    // ==================== OUTER CLASS MEMBERS ====================
    
    // Private variable in outer class
    private String carBrand = "BMW";
    private int carPrice = 50_00_000;
    
    // Protected variable
    protected String carColor = "Black";
    
    // Public method in outer class
    public void startCar() {
        System.out.println(carBrand + " is starting...");
    }
    
    // ==================== MEMBER INNER CLASS ====================
    
    /**
     * STEP 1.1: Basic Member Inner Class
     * This inner class represents the Engine of the Car.
     */
    public class Engine {
        
        // Inner class can have instance variables
        private String engineType = "V8 Turbo";
        private int horsepower = 500;
        
        // Inner class can have instance methods
        public void startEngine() {
            // KEY POINT: Inner class can access PRIVATE members of outer class
            System.out.println("═══════════════════════════════════");
            System.out.println("Engine Details:");
            System.out.println("Car Brand: " + carBrand);      // ✓ Accessing private outer class member
            System.out.println("Car Price: " + carPrice);      // ✓ Accessing private outer class member
            System.out.println("Car Color: " + carColor);      // ✓ Accessing protected outer class member
            System.out.println("Engine Type: " + engineType);
            System.out.println("Horsepower: " + horsepower);
            System.out.println("═══════════════════════════════════");
        }
        
        public String getEngineInfo() {
            return engineType + " with " + horsepower + " HP";
        }
    }
    
    // ==================== ANOTHER MEMBER INNER CLASS ====================
    
    /**
     * STEP 1.2: Another Member Inner Class
     * This inner class represents the Radio system in the Car.
     */
    public class RadioSystem {
        
        private boolean isOn = false;
        private int volume = 50;
        
        public void turnOn() {
            isOn = true;
            System.out.println("🔊 Radio turned ON (Volume: " + volume + ")");
        }
        
        public void turnOff() {
            isOn = false;
            System.out.println("🔇 Radio turned OFF");
        }
        
        public void setVolume(int newVolume) {
            if (newVolume >= 0 && newVolume <= 100) {
                this.volume = newVolume;
                System.out.println("Volume set to: " + newVolume);
            }
        }
        
        public void showStatus() {
            System.out.println("Radio Status - ON: " + isOn + ", Volume: " + volume);
        }
    }
    
    // ==================== METHOD IN OUTER CLASS ====================
    
    public void displayCarInfo() {
        System.out.println("\nCar Information:");
        System.out.println("Brand: " + carBrand);
        System.out.println("Price: ₹" + carPrice);
        System.out.println("Color: " + carColor);
    }
}

// ==================== SUMMARY OF MEMBER INNER CLASS ====================
/*
 * CHARACTERISTICS:
 * 1. Requires an outer class instance to be created
 * 2. Has implicit 'this' reference to outer class (use: OuterClass.this)
 * 3. Can access all members of outer class (public, private, protected, default)
 * 4. Cannot be static (but can have static final variables)
 * 5. Each instance of inner class is tied to one instance of outer class
 * 
 * MEMORY IMPACT:
 * - Every inner class instance holds a reference to its outer class instance
 * - This can cause memory leaks if not managed properly
 * - Always null the references when done
 * 
 * DECLARATION:
 * OuterClass outerObj = new OuterClass();
 * OuterClass.InnerClass innerObj = outerObj.new InnerClass();
 * 
 * USE CASES:
 * ✓ Logical grouping of classes that are only used by outer class
 * ✓ Encapsulation - inner class can access private members
 * ✓ Event handling in GUI applications
 * ✓ Implementing interfaces with multiple implementations
 */

