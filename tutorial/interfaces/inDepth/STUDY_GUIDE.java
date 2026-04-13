package me.niteshh.OPPs.tutorial.interfaces.inDepth;

/**
 * ============================================================================
 * COMPLETE INTERFACES STUDY GUIDE & REVISION NOTES
 * ============================================================================
 * 
 * This file serves as your complete reference for understanding Interfaces
 * in Java. Use this to revise any concept quickly.
 * 
 * Author: Nitesh Kumar
 * Date: 2024
 * ============================================================================
 */

/**
 * ============================================================================
 * TABLE OF CONTENTS
 * ============================================================================
 * 
 * 1. Quick Definition
 * 2. Why Interfaces Matter
 * 3. Interface Syntax & Rules
 * 4. Implementation Syntax
 * 5. Types of Interfaces
 * 6. Interface vs Abstract Class vs Concrete Class
 * 7. Real-world Use Cases
 * 8. Common Mistakes & How to Avoid
 * 9. Quick Decision Tree
 * 10. Code Snippets for Quick Reference
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 1. QUICK DEFINITION
 * ============================================================================
 * 
 * INTERFACE = A CONTRACT that defines what a class should do,
 *             but NOT how to do it.
 * 
 * Simple Analogy:
 * Menu (Interface) → Shows what dishes available
 * Restaurant (Class) → Actually makes the dishes
 * 
 * Key Point: Menu doesn't make food, but specifies what should be made!
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 2. WHY INTERFACES MATTER
 * ============================================================================
 * 
 * Problem They Solve:
 * ------------------
 * 1. TIGHT COUPLING
 *    - Without interface: Code depends on specific implementation
 *    - With interface: Code depends on contract, not implementation
 * 
 * 2. MULTIPLE INHERITANCE
 *    - Java class can extend only 1 class (limitation)
 *    - Java class can implement multiple interfaces (no limit)
 * 
 * 3. CODE FLEXIBILITY
 *    - Without interface: Change one implementation = change all code
 *    - With interface: Add new implementation = no code changes
 * 
 * 4. TESTING
 *    - Without interface: Hard to create mock objects for testing
 *    - With interface: Easy to create test implementations
 * 
 * SOLID Principles They Enable:
 * -----
 * S - Single Responsibility: Each interface has one purpose
 * O - Open/Closed: Open for extension (new implementations), closed for modification
 * L - Liskov Substitution: Any implementation can replace interface reference
 * I - Interface Segregation: Many small interfaces > few large ones
 * D - Dependency Inversion: Depend on interfaces, not implementations
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 3. INTERFACE SYNTAX & RULES
 * ============================================================================
 * 
 * Syntax:
 * -------
 * public interface InterfaceName {
 *     // Abstract methods (no body)
 *     returnType methodName(parameters);
 *     
 *     // Constants (implicitly public static final)
 *     int MAX_SIZE = 100;
 *     
 *     // Default methods (Java 8+)
 *     default void method() { }
 *     
 *     // Static methods (Java 8+)
 *     static void staticMethod() { }
 *     
 *     // Private methods (Java 9+)
 *     private void helperMethod() { }
 * }
 * 
 * Important Rules:
 * ----------------
 * 1. All methods are public by default (can't be private)
 * 2. All methods are abstract by default (can have default/static)
 * 3. All variables are public static final (must be constants)
 * 4. Cannot instantiate interface (new Interface() is WRONG)
 * 5. An interface cannot extend a class
 * 6. Interface can extend other interfaces (multiple inheritance allowed)
 * 
 * Access Levels in Interface:
 * --
 * public interface     ✓ (exposed to all)
 * private interface    ✗ (not allowed)
 * protected interface  ✗ (not allowed)
 * (default) interface  ✓ (package-private)
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 4. IMPLEMENTATION SYNTAX
 * ============================================================================
 * 
 * To implement interface:
 * -----------------------
 * public class ClassName implements InterfaceName {
 *     @Override  // Recommended but optional
 *     public returnType methodName(parameters) {
 *         // Must implement all abstract methods
 *     }
 * }
 * 
 * Multiple Implementation:
 * -----------------------
 * public class ClassName implements Interface1, Interface2, Interface3 {
 *     // Must implement all methods from all interfaces
 * }
 * 
 * Rules for Implementing:
 * ----------------------
 * 1. MUST implement ALL abstract methods
 * 2. Can inherit default methods (don't need to override)
 * 3. Must use "public" modifier for all implemented methods
 * 4. Cannot reduce visibility (can't make public method private)
 * 5. Can add additional methods beyond interface contract
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 5. TYPES OF INTERFACES
 * ============================================================================
 * 
 * Type 1: REGULAR INTERFACE
 * -------------------------
 * Has abstract methods that implementing class must define
 * Example: Drawable, Saveable, DataStorage
 * 
 * public interface Drawable {
 *     void draw();
 *     void erase();
 * }
 * 
 * Type 2: MARKER INTERFACE
 * -----------------------
 * Has NO methods, just marks/tags a class
 * Signals intent to JVM
 * Example: Serializable, Cloneable, Runnable
 * 
 * public interface Serializable {
 *     // No methods, just a marker
 * }
 * 
 * Type 3: FUNCTIONAL INTERFACE
 * ---------------------------
 * Exactly ONE abstract method
 * Can be implemented using lambda expressions (Java 8+)
 * Example: Runnable, Callable, Comparable
 * 
 * @FunctionalInterface
 * public interface Operation {
 *     int calculate(int a, int b);  // Only one method
 *     
 *     default void display(int result) { }  // OK, default method
 * }
 * 
 * // Can use lambda:
 * Operation add = (a, b) -> a + b;
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 6. INTERFACE vs ABSTRACT CLASS vs CONCRETE CLASS
 * ============================================================================
 * 
 * ╔════════════════════╦══════════════╦═══════════════╦════════════════╗
 * ║ FEATURE            ║ INTERFACE    ║ ABSTRACT      ║ CONCRETE       ║
 * ║                    ║              ║ CLASS         ║ CLASS          ║
 * ╠════════════════════╬══════════════╬═══════════════╬════════════════╣
 * ║ Can instantiate?   ║ NO           ║ NO            ║ YES            ║
 * ║ Has constructor?   ║ NO           ║ YES           ║ YES            ║
 * ║ Has variables?     ║ Constants    ║ Any type      ║ Any type       ║
 * ║ Has methods?       ║ Abstract+    ║ Both          ║ Concrete only  ║
 * ║ Access modifiers?  ║ Public only  ║ All types     ║ All types      ║
 * ║ Extend one class?  ║ N/A          ║ YES           ║ YES            ║
 * ║ Implement multiple?║ YES          ║ NO            ║ NO             ║
 * ║ Relationship       ║ CAN-DO       ║ IS-A (maybe)  ║ Complete impl  ║
 * ║ Use case           ║ Capability   ║ Common base   ║ Ready to use   ║
 * ╚════════════════════╩══════════════╩═══════════════╩════════════════╝
 * 
 * + = plus default and static methods (Java 8+)
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 7. REAL-WORLD USE CASES
 * ============================================================================
 * 
 * Use Case 1: DATABASE ABSTRACTION
 * --------------------------------
 * Interface defines database operations
 * Different implementations for MySQL, PostgreSQL, MongoDB
 * Allows switching databases without changing business code
 * 
 * Use Case 2: PAYMENT PROCESSING
 * ------------------------------
 * Interface defines payment interface
 * Different implementations for Credit Card, PayPal, UPI, etc.
 * Easy to add new payment methods
 * 
 * Use Case 3: NOTIFICATION SYSTEM
 * --------------------------------
 * Interface defines notification sending
 * Different implementations for Email, SMS, Push, etc.
 * Send notification same way, different backends
 * 
 * Use Case 4: LOGGING FRAMEWORK
 * ------------------------------
 * Interface defines logging contract
 * Different implementations for Console, File, Database, etc.
 * Switch logging without changing application code
 * 
 * Use Case 5: PLUGIN ARCHITECTURE
 * --------------------------------
 * Interface defines plugin contract
 * Different plugins implement interface
 * Application loads and uses plugins dynamically
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 8. COMMON MISTAKES & HOW TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Forgetting to implement all methods
 * -----------------------------------------------
 * WRONG:
 * class MyClass implements Drawable {
 *     @Override
 *     public void draw() { }
 *     // FORGOT to implement erase()!
 * }
 * ERROR: "MyClass is not abstract and does not override abstract method"
 * 
 * RIGHT:
 * class MyClass implements Drawable {
 *     @Override
 *     public void draw() { }
 *     @Override
 *     public void erase() { }  // All methods implemented
 * }
 * 
 * MISTAKE 2: Making implemented method non-public
 * ------------------------------------------------
 * WRONG:
 * class MyClass implements Runnable {
 *     private void run() { }  // Private is wrong!
 * }
 * ERROR: "Cannot reduce visibility of inherited method"
 * 
 * RIGHT:
 * class MyClass implements Runnable {
 *     @Override
 *     public void run() { }  // Must be public
 * }
 * 
 * MISTAKE 3: Trying to instantiate interface
 * ------------------------------------------
 * WRONG:
 * Drawable d = new Drawable();  // Can't do this!
 * ERROR: "Drawable is abstract; cannot be instantiated"
 * 
 * RIGHT:
 * Drawable d = new Circle();  // Circle implements Drawable
 * 
 * MISTAKE 4: Interface with too many methods
 * ------------------------------------------
 * WRONG:
 * public interface Document {
 *     void save(); void delete(); void send(); void email();
 *     void print(); void scan(); void share(); void upload();
 * }
 * Problem: Class must implement all, even if it only needs some
 * 
 * RIGHT:
 * public interface Saveable { void save(); void delete(); }
 * public interface Sendable { void send(); void email(); }
 * public interface Printable { void print(); }
 * // Now use only what's needed
 * 
 * MISTAKE 5: Confusing interface with abstract class
 * -------------------------------------------------
 * WRONG THINKING:
 * "Both can have abstract methods, so they're the same"
 * 
 * RIGHT THINKING:
 * - Abstract class: Shared implementation and identity (IS-A)
 * - Interface: Define contract only (CAN-DO)
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 9. QUICK DECISION TREE
 * ============================================================================
 * 
 * Question 1: Is this defining something a class CAN DO?
 *    YES → Use INTERFACE (Drawable, Serializable, Comparable)
 *    NO  → Go to Question 2
 * 
 * Question 2: Is this a COMMON BASE for related classes?
 *    YES → Use ABSTRACT CLASS (Animal, Shape, Vehicle)
 *    NO  → Go to Question 3
 * 
 * Question 3: Is this COMPLETE with no missing implementations?
 *    YES → Use CONCRETE CLASS (String, Integer, ArrayList)
 *    NO  → Go to Question 4
 * 
 * Question 4: Need multiple inheritance?
 *    YES → Use MULTIPLE INTERFACES (class A implements I1, I2, I3)
 *    NO  → Use SINGLE ABSTRACT CLASS or INTERFACE
 * 
 * ============================================================================
 */

/**
 * ============================================================================
 * 10. CODE SNIPPETS FOR QUICK REFERENCE
 * ============================================================================
 */

// SNIPPET 1: Basic Interface Definition
interface Speaker {
    void speak(String words);
}

// SNIPPET 2: Basic Implementation
class PersonSG implements Speaker {
    @Override
    public void speak(String words) {
        System.out.println("Person says: " + words);
    }
}

// SNIPPET 3: Multiple Interface Implementation
interface Walker {
    void walk();
}

class Human implements Speaker, Walker {
    @Override
    public void speak(String words) {
        System.out.println(words);
    }
    
    @Override
    public void walk() {
        System.out.println("Walking...");
    }
}

// SNIPPET 4: Interface with Constants
interface Constants {
    int MAX_ATTEMPTS = 3;
    double PI = 3.14159;
    String APP_NAME = "MyApp";
}

// SNIPPET 5: Interface with Default Method
interface VehicleSG {
    void start();
    
    default void stop() {
        System.out.println("Vehicle stopped");
    }
}

// SNIPPET 6: Using Interface Reference
class InterfaceReferenceExample {
    public void demo() {
        Speaker speaker = new PersonSG();  // Interface reference to implementation
        speaker.speak("Hello");          // Polymorphism in action
    }
}

// SNIPPET 7: Functional Interface with Lambda
@FunctionalInterface
interface Adder {
    int add(int a, int b);
}

class LambdaExample {
    public void demo() {
        Adder adder = (a, b) -> a + b;
        System.out.println(adder.add(5, 3));  // 8
    }
}

// SNIPPET 8: Dependency Injection Pattern
interface LoggerSG {
    void log(String msg);
}

class Service {
    private LoggerSG logger;
    
    public Service(LoggerSG logger) {  // Dependency injection
        this.logger = logger;
    }
    
    public void doWork() {
        logger.log("Doing work");
    }
}

/**
 * ============================================================================
 * REVISION CHECKLIST
 * ============================================================================
 * 
 * Before you finish learning, ensure you can answer:
 * 
 * [ ] What is an interface and why do we use them?
 * [ ] What are the differences between interface and abstract class?
 * [ ] How do you implement an interface?
 * [ ] Can a class implement multiple interfaces?
 * [ ] What is a functional interface?
 * [ ] What are default methods and static methods in interfaces?
 * [ ] How does polymorphism work with interfaces?
 * [ ] What is dependency injection and how interfaces enable it?
 * [ ] What are common interfaces like Comparable, Iterable, Runnable?
 * [ ] How to use lambda expressions with functional interfaces?
 * [ ] What does @FunctionalInterface annotation do?
 * [ ] When should you use interface vs abstract class?
 * [ ] How to design good interfaces (Interface Segregation Principle)?
 * [ ] What are real-world examples of interface usage?
 * [ ] How to avoid common interface mistakes?
 * 
 * ============================================================================
 * PRACTICE EXERCISES
 * ============================================================================
 * 
 * Exercise 1: Create Animal interface with methods eat() and sleep()
 *            Implement with Dog, Cat, Bird classes
 * 
 * Exercise 2: Create Shape interface and implement Circle, Square, Triangle
 *            with methods: area(), perimeter(), draw()
 * 
 * Exercise 3: Create PaymentProcessor interface for different payment methods
 *            Implement CreditCardProcessor, UPIProcessor, WalletProcessor
 * 
 * Exercise 4: Implement Comparable to sort Employee by salary
 * 
 * Exercise 5: Create functional interface and use lambda expressions
 * 
 * Exercise 6: Implement Iterable to make custom collection support for-each
 * 
 * Exercise 7: Create plugin system using interfaces
 * 
 * Exercise 8: Use dependency injection with interfaces for logging system
 * 
 * ============================================================================
 */

/**
 * FINAL TIPS FOR MASTERING INTERFACES
 * ====================================
 * 
 * 1. THINK IN TERMS OF BEHAVIOR, NOT IMPLEMENTATION
 *    Ask: "What does this object DO?" not "What IS this object?"
 * 
 * 2. USE INTERFACES TO DEFINE CONTRACTS
 *    Interfaces are like agreements between provider and consumer
 * 
 * 3. PROGRAM TO INTERFACE, NOT IMPLEMENTATION
 *    This is the golden rule of good design
 * 
 * 4. START SIMPLE, THEN EVOLVE
 *    Begin with basic interfaces, add complexity as needed
 * 
 * 5. USE DEPENDENCY INJECTION
 *    Pass dependencies as parameters using interface types
 * 
 * 6. FOLLOW SOLID PRINCIPLES
 *    Especially Interface Segregation and Dependency Inversion
 * 
 * 7. USE FUNCTIONAL INTERFACES FOR SIMPLE CALLBACKS
 *    Lambda expressions make code cleaner
 * 
 * 8. DOCUMENT YOUR INTERFACES WELL
 *    Future you will thank current you for clear documentation
 * 
 * 9. USE REAL-WORLD EXAMPLES
 *    Practice with scenarios you encounter daily
 * 
 * 10. REFACTOR WHEN YOU SEE TIGHT COUPLING
 *     If you see "new ClassName()" in service class, use interface!
 * 
 * ============================================================================
 */

