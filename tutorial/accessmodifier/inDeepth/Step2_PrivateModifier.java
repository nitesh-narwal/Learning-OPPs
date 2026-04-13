package me.niteshh.OPPs.tutorial.accessmodifier.inDeepth;

/**
 * STEP 2: PRIVATE ACCESS MODIFIER - BEGINNER LEVEL
 * 
 * Private is the most restrictive access modifier.
 * 
 * Key Points:
 * - Can ONLY be accessed WITHIN the SAME CLASS
 * - Not accessible from subclasses or other classes in same package
 * - Even within same package, you cannot access private members
 * - Perfect for hiding internal implementation details (ENCAPSULATION)
 * 
 * When to use Private:
 * - Internal helper methods
 * - Internal state variables
 * - Implementation details that shouldn't be exposed
 * - Data that needs validation before access (use getters/setters)
 */

public class Step2_PrivateModifier {
    
    // PRIVATE VARIABLE - Can ONLY be accessed within this class
    // This is internal state, not meant for external modification
    private String password;
    
    // PRIVATE VARIABLE - Internal counter
    private int accessCount = 0;
    
    // PUBLIC CONSTANT - Can be accessed from anywhere
    public static final int MAX_ATTEMPTS = 5;
    
    /**
     * PUBLIC METHOD - External interface to get password securely
     * We don't expose the raw password, instead verify it
     */
    public boolean verifyPassword(String inputPassword) {
        accessCount++; // Use private variable internally
        if (accessCount > MAX_ATTEMPTS) {
            System.out.println("Max attempts exceeded!");
            return false;
        }
        return this.password.equals(inputPassword);
    }
    
    /**
     * PUBLIC METHOD - External interface to set password
     * We can add validation before setting the private variable
     */
    public boolean setPassword(String newPassword) {
        // VALIDATION - Only expose validated interface to outside world
        if (newPassword == null || newPassword.length() < 8) {
            System.out.println("Password must be at least 8 characters!");
            return false;
        }
        this.password = newPassword;
        System.out.println("Password set successfully!");
        return true;
    }
    
    /**
     * PRIVATE METHOD - Can ONLY be called from within this class
     * This is an internal helper method not meant for external use
     */
    private void resetAccessCount() {
        accessCount = 0;
        System.out.println("Access count reset to 0 (internal operation)");
    }
    
    /**
     * PUBLIC METHOD - Uses the private helper method internally
     */
    public void unlockAfterTimeout() {
        resetAccessCount(); // Can call private method within the same class
        System.out.println("User unlocked after timeout");
    }
    
    /**
     * PRIVATE METHOD - Internal validation logic
     * We can modify this without affecting external code
     */
    private boolean isPasswordStrong(String password) {
        return password.length() >= 8 && 
               password.matches(".*[0-9].*") && 
               password.matches(".*[a-z].*");
    }
}

/**
 * PRINCIPLE: ENCAPSULATION
 * 
 * Private members enforce encapsulation:
 * - Hide implementation details
 * - Provide controlled access through public methods
 * - Can change internal implementation without affecting external code
 * - Enforces data validation
 * 
 * Example Flow:
 * 1. User tries to access password directly → NOT ALLOWED (private)
 * 2. User calls setPassword() → Can validate input
 * 3. User calls verifyPassword() → Can check attempts
 * 4. resetAccessCount() called internally → Not exposed to user
 */

/**
 * EXAMPLE USAGE:
 * 
 * Step2_PrivateModifier account = new Step2_PrivateModifier();
 * 
 * // ✓ These work - using public methods
 * account.setPassword("SecurePass123");
 * account.verifyPassword("SecurePass123");
 * account.unlockAfterTimeout();
 * 
 * // ✗ These DO NOT work - private members cannot be accessed
 * // account.password = "HackedPass";           // COMPILATION ERROR
 * // account.resetAccessCount();                 // COMPILATION ERROR
 * // account.isPasswordStrong("test");           // COMPILATION ERROR
 */

