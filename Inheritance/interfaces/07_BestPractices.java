package me.niteshh.OPPs.Inheritance.interfaces;

import java.util.*;

/**
 * ============================================================================
 * STEP 7: INTERFACE BEST PRACTICES & DESIGN PATTERNS
 * ============================================================================
 * 
 * Following these practices will make you a better developer
 * These are industry standards used by top companies
 * ============================================================================
 */

/**
 * BEST PRACTICE 1: Keep Interfaces Focused
 * ==========================================
 * 
 * PRINCIPLE: Interface Segregation Principle (ISP)
 * 
 * Rule: Don't force classes to implement methods they don't need
 * 
 * WRONG: Big interface with too many unrelated methods
 */
interface BAD_WrongExample {
    // A class implementing this must have all methods
    // But what if class only needs some?
    void save();
    void delete();
    void send();
    void print();
    void email();
}

/**
 * RIGHT: Segregate into small, focused interfaces
 */
interface SaveableP {
    void save();
    void delete();
}

interface SendableP {
    void send();
}

interface PrintableP {
    void print();
}

interface EmailableP {
    void email();
}

/**
 * Now a class can implement only what it needs:
 */
class DocumentP implements SaveableP, PrintableP {
    // Only implements what's needed
    @Override
    public void save() {
        System.out.println("Document saved");
    }
    
    @Override
    public void delete() {
        System.out.println("Document deleted");
    }
    
    @Override
    public void print() {
        System.out.println("Document printed");
    }
}

/**
 * ============================================================================
 * BEST PRACTICE 2: Use Composition Over Inheritance
 * ==================================================
 * 
 * PRINCIPLE: Dependency Injection
 * 
 * Instead of implementing interface directly, use composition
 * This provides better flexibility and testing
 */

// Step 1: Define the interface
interface LoggerBP {
    void log(String message);
}

// Step 2: Create implementations
class ConsoleLoggerBP implements LoggerBP {
    @Override
    public void log(String message) {
        System.out.println("[CONSOLE] " + message);
    }
}

class FileLoggerBP implements LoggerBP {
    @Override
    public void log(String message) {
        System.out.println("[FILE] Writing to file: " + message);
    }
}

// WRONG: Tight coupling
class BAD_Service {
    private ConsoleLoggerBP logger = new ConsoleLoggerBP(); // Hard dependency
    
    public void doSomething() {
        logger.log("Doing something");
        // Can't use FileLogger without changing code
    }
}

// RIGHT: Composition + Dependency Injection
class GOOD_Service {
    private LoggerBP logger; // Depend on interface, not implementation
    
    // Inject dependency through constructor
    public GOOD_Service(LoggerBP logger) {
        this.logger = logger;
    }
    
    public void doSomething() {
        logger.log("Doing something");
        // Can use any Logger implementation
    }
}

/**
 * Usage:
 * GOOD_Service service1 = new GOOD_Service(new ConsoleLogger());
 * GOOD_Service service2 = new GOOD_Service(new FileLogger());
 * 
 * Both work without changing Service code!
 */

/**
 * ============================================================================
 * BEST PRACTICE 3: Design by Contract
 * ====================================
 * 
 * PRINCIPLE: Clear contract between interface and implementer
 * 
 * Include detailed documentation about:
 * 1. What the method does
 * 2. What parameters it expects
 * 3. What it returns
 * 4. What exceptions it throws
 * 5. Any side effects
 */

interface PaymentProcessor {
    
    /**
     * Process a payment transaction
     * 
     * @param amount - Payment amount in rupees (must be > 0)
     * @param transactionId - Unique transaction identifier (cannot be null or empty)
     * @return true if payment successful, false if failed
     * 
     * @throws IllegalArgumentException if amount <= 0 or transactionId is invalid
     * @throws PaymentException if payment fails (insufficient funds, network error)
     * 
     * Side effects:
     * - Deducts amount from account
     * - Creates audit log entry
     * - Sends confirmation email
     * 
     * Example:
     * PaymentProcessor processor = new CreditCardProcessor();
     * try {
     *     boolean success = processor.processPayment(1000, "TXN123");
     * } catch (PaymentException e) {
     *     System.out.println("Payment failed: " + e.getMessage());
     * }
     */
    boolean processPayment(double amount, String transactionId) throws PaymentException;
    
    /**
     * Refund a previous transaction
     * 
     * @param transactionId - ID of transaction to refund
     * @return true if refund successful
     * 
     * @throws IllegalArgumentException if transactionId not found
     * @throws PaymentException if refund fails
     */
    boolean refund(String transactionId) throws PaymentException;
}

// Custom exception
class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }
}

/**
 * ============================================================================
 * BEST PRACTICE 4: Use Functional Interfaces with Lambdas (Java 8+)
 * =================================================================
 * 
 * Functional interface = Interface with single abstract method
 * Can be implemented using lambda expressions
 * Makes code cleaner and more readable
 */

// Functional interface
@FunctionalInterface
interface Operation {
    int execute(int a, int b);
    
    // Can have default methods
    default void printResult(int result) {
        System.out.println("Result: " + result);
    }
}

/**
 * Usage without lambda (VERBOSE):
 * Operation addition = new Operation() {
 *     @Override
 *     public int execute(int a, int b) {
 *         return a + b;
 *     }
 * };
 * 
 * Usage with lambda (CLEAN):
 */
class Calculator {
    
    public void demonstrateLambda() {
        // Lambda syntax: (parameters) -> { implementation }
        Operation add = (a, b) -> a + b;
        Operation subtract = (a, b) -> a - b;
        Operation multiply = (a, b) -> a * b;
        Operation divide = (a, b) -> a / b;
        
        System.out.println(add.execute(10, 5));      // 15
        System.out.println(subtract.execute(10, 5)); // 5
        System.out.println(multiply.execute(10, 5)); // 50
        System.out.println(divide.execute(10, 5));   // 2
    }
}

/**
 * ============================================================================
 * BEST PRACTICE 5: Use Marker Interfaces Carefully
 * ================================================
 * 
 * Marker interfaces (no methods) are useful but can be overused
 */

// Good use: Clear intent that object is safe for concurrent access
interface ThreadSafe {
    // No methods needed, just marks intent
}

// Implementation
class ConcurrentCache implements ThreadSafe {
    // Implementation guarantees thread safety
}

// Bad use: Overuse of marker interfaces
// public interface Good {} // Meaningless, don't do this!

/**
 * ============================================================================
 * BEST PRACTICE 6: API Design with Interfaces
 * ====================================
 * 
 * Always program to interface, not implementation
 */

// BAD: Exposing concrete class
class BAD_DatabaseAPI {
    public java.util.ArrayList<User> getUsers() {
        return new java.util.ArrayList<>();
    }
}

// GOOD: Returning interface
interface Database {
    java.util.List<User> getUsers();
}

class User {
    private String name;
    private String email;
    
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

/**
 * This way:
 * 1. Clients don't depend on ArrayList implementation
 * 2. You can return different List implementations (LinkedList, etc.)
 * 3. Easier to change internal implementation later
 */

/**
 * ============================================================================
 * BEST PRACTICE 7: Named Interfaces Patterns
 * ===========================================
 * 
 * Use naming conventions to make intent clear
 */

// -able suffix: Shows capability
interface SerializableNamed {
}

interface ComparableNamed<T> {
    int compareTo(T other);
}

// -er suffix: Shows implementer is "doer" of action
interface LoggerNamed {
    void log(String message);
}

interface ProcessorNamed {
    void process(String data);
}

// Factory pattern with interface
interface DatabaseFactoryNamed {
    Database createDatabase();
}

/**
 * ============================================================================
 * COMMON PITFALLS TO AVOID
 * ========================
 * 
 * 1. Too Many Methods in Interface
 *    Problem: Classes forced to implement unneeded methods
 *    Solution: Follow Interface Segregation Principle
 * 
 * 2. Mutable Interface Fields
 *    All fields are final by default, good practice
 * 
 * 3. Mixing Concerns
 *    Problem: Interface mixing unrelated behaviors
 *    Solution: Create separate focused interfaces
 * 
 * 4. Over-Engineering
 *    Problem: Using interfaces where simple methods work
 *    Solution: Use interfaces only when needed for polymorphism
 * 
 * 5. Ignoring Documentation
 *    Problem: No javadoc on interface methods
 *    Solution: Always document contracts thoroughly
 * 
 * 6. Circular Dependencies
 *    Problem: Interface A extends B, B extends A
 *    Solution: Plan inheritance hierarchy carefully
 * 
 * ============================================================================
 */

