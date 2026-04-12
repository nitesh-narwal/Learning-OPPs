package me.niteshh.OPPs.Inheritance.interfaces;

/**
 * ============================================================================
 * STEP 1: BASIC INTERFACE CONCEPT
 * ============================================================================
 * 
 * What is an Interface?
 * ---------------------
 * An Interface is a BLUEPRINT for creating classes, just like a contract.
 * It defines WHAT methods a class should have, but NOT HOW to implement them.
 * 
 * Key Characteristics:
 * 1. Contains only abstract methods (no body) - except static and default methods
 * 2. Cannot be instantiated (cannot create object directly)
 * 3. Used to achieve 100% abstraction
 * 4. All methods are public by default
 * 5. All variables are public, static, and final by default
 * 
 * Why Use Interfaces?
 * -------------------
 * 1. Loose Coupling - Reduces dependency between classes
 * 2. Standardization - Ensures all implementing classes follow same contract
 * 3. Multiple Inheritance - Java doesn't support multiple inheritance for classes,
 *    but a class can implement multiple interfaces
 * 4. Polymorphism - Different classes implementing same interface can be used
 *    interchangeably
 * 5. Code Reusability - Define common behaviors that different classes can use
 * 
 * Difference between Class and Interface:
 * -----------------------------------------------
 * CLASS:
 * - Can have concrete methods (with implementation)
 * - Can have constructor
 * - Can have state (non-final variables)
 * - A class can extend only one class
 * - Used for IS-A relationships
 * 
 * INTERFACE:
 * - Can have abstract methods (no implementation)
 * - No constructor
 * - Only constants (final static variables)
 * - A class can implement multiple interfaces
 * - Used for CAN-DO relationships
 * ============================================================================
 */

// Example of a simple interface
interface SimpleInterface {
    
    /**
     * Abstract Method:
     * - No body (no implementation)
     * - Each class that implements this interface MUST provide implementation
     */
    void greet();
    
    /**
     * Another abstract method
     * - The + sign in front means it's public (default in interface)
     */
    int add(int a, int b);
}

/**
 * REAL WORLD ANALOGY:
 * 
 * Think of an Interface as a RESTAURANT MENU:
 * - Menu lists all dishes (like interface lists methods)
 * - Menu doesn't make the food (interface doesn't implement methods)
 * - Different restaurants can make same dishes differently (different classes
 *   implementing same interface can have different implementations)
 * - Every restaurant must serve the dishes listed on menu (every class must
 *   implement all methods of interface)
 * 
 * Another analogy: CHARGING PORT
 * - iPhone, Android, USB-C all have different internals
 * - But they implement same charging interface
 * - You can charge any device if it implements the charging interface
 * - This is what interface does in programming!
 */

