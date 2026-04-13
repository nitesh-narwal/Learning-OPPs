package me.niteshh.OPPs.tutorial.accessmodifier.inDeepth;

/**
 * ╔═══════════════════════════════════════════════════════════════════════╗
 * ║                    QUICK REFERENCE GUIDE                             ║
 * ║              Access Modifiers at a Glance for Java                   ║
 * ╚═══════════════════════════════════════════════════════════════════════╝
 * 
 * This file serves as a quick lookup reference for access modifiers.
 * Print this or bookmark it for quick reference!
 */

/**
 * ═══════════════════════════════════════════════════════════════════════
 * 1. PUBLIC - BROADEST ACCESS
 * ═══════════════════════════════════════════════════════════════════════
 * 
 * SYNTAX:
 * -------
 * public class MyClass { }
 * public String myMethod() { }
 * public int myVariable;
 * public static final double PI = 3.14159;
 * 
 * ACCESSIBLE FROM:
 * ----------------
 * ✓ Same class
 * ✓ Same package
 * ✓ Different package (subclass)
 * ✓ Different package (unrelated)
 * 
 * USE WHEN:
 * ---------
 * - This is part of the public API
 * - External code needs to call it
 * - Global constants
 * - Utility methods
 * 
 * EXAMPLE:
 * --------
 * public class UserService {
 *     public User getUserById(long id) {  // Anyone can call
 *         return database.findById(id);
 *     }
 * }
 * 
 * RISK: Widely accessible, hard to change later
 */

/**
 * ═══════════════════════════════════════════════════════════════════════
 * 2. PRIVATE - MOST RESTRICTIVE
 * ═══════════════════════════════════════════════════════════════════════
 * 
 * SYNTAX:
 * -------
 * private String myString;
 * private void myMethod() { }
 * private int calculateValue() { }
 * 
 * ACCESSIBLE FROM:
 * ----------------
 * ✓ Same class only
 * ✗ Same package
 * ✗ Different package (subclass)
 * ✗ Different package (unrelated)
 * 
 * USE WHEN:
 * ---------
 * - Internal implementation detail
 * - Helper/utility methods
 * - Data that needs validation
 * - Internal state that shouldn't be exposed
 * 
 * EXAMPLE:
 * --------
 * public class BankAccount {
 *     private double balance;              // Hidden
 *     private String pin;                  // Protected
 *     
 *     public double getBalance() {         // Public getter
 *         return balance;
 *     }
 *     
 *     private boolean validatePin(String p) {  // Private helper
 *         return p.equals(pin);
 *     }
 * }
 * 
 * BENEFIT: Maximum encapsulation, can change implementation freely
 */

/**
 * ═══════════════════════════════════════════════════════════════════════
 * 3. DEFAULT (NO KEYWORD) - PACKAGE-PRIVATE
 * ═══════════════════════════════════════════════════════════════════════
 * 
 * SYNTAX:
 * -------
 * class MyClass { }              // No public/private/protected
 * String myMethod() { }          // No modifier = default
 * int myVariable;                // No modifier = default
 * 
 * ACCESSIBLE FROM:
 * ----------------
 * ✓ Same class
 * ✓ Same package
 * ✗ Different package (subclass)
 * ✗ Different package (unrelated)
 * 
 * USE WHEN:
 * ---------
 * - Package-internal utilities
 * - Helper classes within package
 * - Classes meant only for same package
 * - Default choice when unsure
 * 
 * EXAMPLE:
 * --------
 * // All in package: com.myapp.service
 * 
 * public class UserService {     // Public - available outside package
 *     void internalHelper() { }   // Default - only in this package
 * }
 * 
 * class UserValidator {          // Default class - same package only
 *     String validate(User u) { }
 * }
 * 
 * BENEFIT: Hides internals from outside packages
 */

/**
 * ═══════════════════════════════════════════════════════════════════════
 * 4. PROTECTED - FOR INHERITANCE
 * ═══════════════════════════════════════════════════════════════════════
 * 
 * SYNTAX:
 * -------
 * protected void myMethod() { }
 * protected String myVariable;
 * protected abstract void mustOverride();
 * 
 * ACCESSIBLE FROM:
 * ----------------
 * ✓ Same class
 * ✓ Same package
 * ✓ Different package (subclass ONLY)
 * ✗ Different package (unrelated)
 * 
 * USE WHEN:
 * ---------
 * - Method is meant to be overridden by subclass
 * - Subclass needs to access this member
 * - Part of inheritance contract
 * - Template method pattern
 * 
 * EXAMPLE:
 * --------
 * public abstract class PaymentProcessor {
 *     protected abstract void processPayment(double amount);
 *     
 *     protected void logTransaction(String detail) {
 *         // Subclasses can override this
 *     }
 *     
 *     public final void execute(double amount) {
 *         logTransaction("Starting payment: " + amount);
 *         processPayment(amount);
 *     }
 * }
 * 
 * public class CreditCardProcessor extends PaymentProcessor {
 *     @Override
 *     protected void processPayment(double amount) {
 *         // Override what parent allows
 *     }
 * }
 * 
 * BENEFIT: tutorial-friendly, can be overridden in subclasses
 */

/**
 * ═══════════════════════════════════════════════════════════════════════
 * COMPARISON MATRIX - CHOOSE THE RIGHT ONE
 * ═══════════════════════════════════════════════════════════════════════
 * 
 *                     PUBLIC  PROTECTED  DEFAULT  PRIVATE
 *                     ------  ---------  -------  -------
 * Same Class            ✓        ✓         ✓        ✓
 * Same Package          ✓        ✓         ✓        ✗
 * Diff Pkg (Subclass)   ✓        ✓         ✗        ✗
 * Diff Pkg (Unrelated)  ✓        ✗         ✗        ✗
 * 
 * Accessibility Level:  100%     75%       50%      0%
 * Risk Factor:          High     Medium    Low      None
 * Change Difficulty:    Hard     Medium    Easy     Easy
 * Use Frequency:        Common   Moderate  Common   Most
 */

/**
 * ═══════════════════════════════════════════════════════════════════════
 * DECISION TREE - WHICH MODIFIER TO USE?
 * ═══════════════════════════════════════════════════════════════════════
 * 
 * START: Do I need to expose this to outside?
 *   │
 *   ├─ YES → Is it the main API?
 *   │         ├─ YES → PUBLIC ✓
 *   │         └─ NO → Can it be used differently later?
 *   │               ├─ YES → PUBLIC (but document carefully)
 *   │               └─ NO → Reconsider making it public
 *   │
 *   └─ NO → Is this for inheritance/subclasses?
 *           ├─ YES → PROTECTED ✓
 *           └─ NO → Is this shared within package?
 *                  ├─ YES → DEFAULT ✓
 *                  └─ NO → PRIVATE ✓ (most likely!)
 */

import me.niteshh.OPPs.tutorial.interfaces.inDepth.User;

import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════════
 * COMMON PATTERNS - COPY & USE
 * ═══════════════════════════════════════════════════════════════════════
 */

// PATTERN 1: Encapsulation with Validation
class EncapsulationPattern {
    private int age;  // PRIVATE state
    
    // PUBLIC interface with validation
    public boolean setAge(int newAge) {
        if (newAge > 0 && newAge < 150) {
            age = newAge;
            return true;
        }
        return false;
    }
    
    public int getAge() {
        return age;
    }
}

// PATTERN 2: Immutability
class ImmutablePattern {
    private final String email;     // PRIVATE + FINAL
    private final long userId;      // PRIVATE + FINAL
    
    public ImmutablePattern(String email, long userId) {
        this.email = email;
        this.userId = userId;
    }
    
    // Only getters, NO setters
    public String getEmail() { return email; }
    public long getUserId() { return userId; }
}

// PATTERN 3: tutorial Hook
abstract class InheritanceHookPattern {
    private String data;  // PRIVATE - can't be messed with
    
    // PROTECTED hook for subclasses
    protected abstract void processData();
    
    // PUBLIC template method
    public final void execute() {
        loadData();
        processData();  // Calls overridden version
    }
    
    private void loadData() { /* Load data */ }
}

// PATTERN 4: Builder Pattern
class BuilderPattern {
    private String name;    // PRIVATE
    private String email;   // PRIVATE
    private int age;        // PRIVATE
    
    public BuilderPattern setName(String n) {
        this.name = n;
        return this;  // Fluent API
    }
    
    public BuilderPattern setEmail(String e) {
        this.email = e;
        return this;
    }
    
    public BuilderPattern setAge(int a) {
        this.age = a;
        return this;
    }
    
    // Validation before building
    public BuilderPattern build() {
        validateEmail();
        validateAge();
        return this;
    }
    
    private void validateEmail() {
        if (!email.contains("@")) throw new IllegalArgumentException();
    }
    
    private void validateAge() {
        if (age < 0 || age > 150) throw new IllegalArgumentException();
    }
}

// PATTERN 5: Constants
class ConstantsPattern {
    // PUBLIC constants - everyone can use
    public static final double PI = 3.14159;
    public static final int MAX_USERS = 1000;
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/db";
    
    // PRIVATE internal constant
    private static final String ENCRYPTION_KEY = "secret-key";
}

/**
 * ═══════════════════════════════════════════════════════════════════════
 * DON'T DO THIS - ANTI-PATTERNS
 * ═══════════════════════════════════════════════════════════════════════
 */

// ✗ ANTI-PATTERN 1: Public mutable state
class BadEncapsulation {
    public String password;  // ✗ NO! Anyone can change it
    public List<User> users; // ✗ NO! Anyone can modify
}

// ✗ ANTI-PATTERN 2: Protected when private is better
abstract class BadInheritance {
    protected String secretKey;  // ✗ Subclasses can see it
    // Better: private String secretKey;
}

// ✗ ANTI-PATTERN 3: Changing public method signature
class BadAPI_v1 {
    public void process(String data) { }
}

// Later, someone changed:
class BadAPI_v2 {
    public void process(String data, int timeout) { }  // ✗ BREAKS clients!
}

// ✗ ANTI-PATTERN 4: Returning mutable references
class BadReferences {
    private List<User> users;
    
    public List<User> getUsers() {
        return users;  // ✗ Caller can modify!
    }
    
    // Better:
    public List<User> getUsersSafe() {
        return new ArrayList<>(users);  // ✓ Return copy
    }
}

/**
 * ═══════════════════════════════════════════════════════════════════════
 * PROFESSIONAL GUIDELINES
 * ═══════════════════════════════════════════════════════════════════════
 * 
 * 1. DEFAULT TO PRIVATE
 *    Start with private, expand if needed
 * 
 * 2. MINIMIZE PUBLIC SURFACE
 *    Every public method is a contract
 * 
 * 3. USE GETTERS/SETTERS
 *    Adds flexibility for future changes
 * 
 * 4. DOCUMENT YOUR PUBLIC API
 *    Use JavaDoc for all public methods
 * 
 * 5. PROTECT YOUR DATA
 *    Validate in setters, not in callers
 * 
 * 6. USE FINAL FOR IMMUTABILITY
 *    private final fields cannot be changed
 * 
 * 7. RETURN COPIES NOT REFERENCES
 *    Prevent external modification
 * 
 * 8. PROTECT AGAINST INHERITANCE ABUSE
 *    Use final methods to lock behavior
 */

/**
 * ═══════════════════════════════════════════════════════════════════════
 * INTERVIEW QUESTIONS YOU MIGHT GET
 * ═══════════════════════════════════════════════════════════════════════
 * 
 * Q1: Explain the 4 access modifiers
 * A: PUBLIC (everywhere), PROTECTED (inheritance), DEFAULT (package),
 *    PRIVATE (class only). Start with PRIVATE, expand if needed.
 * 
 * Q2: What's the difference between PROTECTED and DEFAULT?
 * A: PROTECTED allows subclasses in different packages.
 *    DEFAULT only allows same package access.
 * 
 * Q3: Why use PRIVATE instead of PUBLIC?
 * A: Encapsulation - hide implementation, add validation, change freely
 *    without breaking external code.
 * 
 * Q4: Can I make a top-level class PRIVATE?
 * A: No. Top-level classes can be PUBLIC or DEFAULT only.
 *    Only inner classes can be PRIVATE.
 * 
 * Q5: How does access modifier work with inheritance?
 * A: PROTECTED members are accessible in subclasses.
 *    PRIVATE members are NOT accessible in subclasses.
 *    Use PROTECTED for extension points.
 */

public class QUICK_REFERENCE {
    // This class is just for documentation
    // Review the comments above for quick lookup!
}

