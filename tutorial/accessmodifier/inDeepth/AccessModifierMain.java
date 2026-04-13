package me.niteshh.OPPs.tutorial.accessmodifier.inDeepth;

/**
 * COMPREHENSIVE MAIN CLASS - TESTING AND DEMONSTRATING ALL ACCESS MODIFIERS
 * 
 * This class demonstrates:
 * 1. Public modifier usage
 * 2. Private modifier with encapsulation
 * 3. Default (package-private) modifier
 * 4. Protected modifier with inheritance
 * 5. Real-world bank system example
 * 
 * Run this to understand all concepts!
 */

public class AccessModifierMain {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║   JAVA ACCESS MODIFIERS: COMPREHENSIVE DEMONSTRATION           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        demonstratePublicModifier();
        demonstratePrivateModifier();
        demonstrateDefaultModifier();
        demonstrateProtectedModifier();
        demonstrateRealWorldExample();
    }
    
    /**
     * DEMONSTRATION 1: PUBLIC MODIFIER
     */
    private static void demonstratePublicModifier() {
        System.out.println("\n┌─ STEP 1: PUBLIC MODIFIER ────────────────────────────────────┐");
        System.out.println("│ Can access from ANYWHERE                                       │");
        System.out.println("└────────────────────────────────────────────────────────────────┘\n");
        
        Step1_PublicModifier publicObj = new Step1_PublicModifier();
        
        // ✓ Accessing public variable
        publicObj.name = "Nitesh Kumar";
        System.out.println("✓ Set name via public variable: " + publicObj.name);
        
        // ✓ Accessing public method
        publicObj.greet();
        System.out.println("✓ Called public method greet()");
        
        // ✓ Accessing public getter
        String name = publicObj.getName();
        System.out.println("✓ Called public getter: " + name);
        
        // ✓ Accessing public constant
        System.out.println("✓ Accessed public constant PI: " + Step1_PublicModifier.PI);
        
        System.out.println("\n[PUBLIC] - Broadest accessibility, use for API ✓\n");
    }
    
    /**
     * DEMONSTRATION 2: PRIVATE MODIFIER WITH ENCAPSULATION
     */
    private static void demonstratePrivateModifier() {
        System.out.println("┌─ STEP 2: PRIVATE MODIFIER ────────────────────────────────────┐");
        System.out.println("│ Can ONLY access within the SAME CLASS                         │");
        System.out.println("└───────────────────────────────────────────────────────────────┘\n");
        
        Step2_PrivateModifier account = new Step2_PrivateModifier();
        
        // ✓ Using public interface to access private data
        System.out.println("✓ Setting password through public method...");
        account.setPassword("SecurePass123");
        
        // ✓ Verifying password (uses private password variable internally)
        System.out.println("✓ Verifying password through public method...");
        boolean isValid = account.verifyPassword("SecurePass123");
        System.out.println("  Password valid: " + isValid);
        
        // ✓ Calling public method that uses private helper
        System.out.println("✓ Calling unlock (uses private resetAccessCount internally)...");
        account.unlockAfterTimeout();
        
        // ✗ THESE WOULD NOT WORK (commented to avoid compilation errors):
        // account.password = "Hacked";                // COMPILATION ERROR
        // account.resetAccessCount();                 // COMPILATION ERROR
        // boolean strong = account.isPasswordStrong("test");  // COMPILATION ERROR
        
        System.out.println("\n[PRIVATE] - Strictest, for internal implementation ✓\n");
    }
    
    /**
     * DEMONSTRATION 3: DEFAULT (PACKAGE-PRIVATE) MODIFIER
     */
    private static void demonstrateDefaultModifier() {
        System.out.println("┌─ STEP 3: DEFAULT (PACKAGE-PRIVATE) MODIFIER ──────────────────┐");
        System.out.println("│ Can access from SAME PACKAGE only                             │");
        System.out.println("└───────────────────────────────────────────────────────────────┘\n");
        
        Step3_DefaultModifier obj = new Step3_DefaultModifier();
        
        // ✓ Since we're in the same package, we can access default members
        System.out.println("✓ Calling public method...");
        obj.publicMethod();
        
        // ✓ Access default method (works because same package)
        System.out.println("✓ Calling default/package-private method...");
        obj.packageMethod();
        
        // ✓ Access default variable
        String data = obj.internalData;
        System.out.println("✓ Accessed default variable: " + data);
        
        // ✓ Demonstrates scope with multiple methods
        System.out.println("✓ Calling method that demonstrates internal scopes...");
        obj.demonstrateScopes();
        
        // Default partner class (also in same package)
        System.out.println("✓ Using package partner class to access default members...");
        PackagePartner.demonstrateAccess();
        
        System.out.println("\n[DEFAULT] - Package scope, for internal package utilities ✓\n");
    }
    
    /**
     * DEMONSTRATION 4: PROTECTED MODIFIER WITH INHERITANCE
     */
    private static void demonstrateProtectedModifier() {
        System.out.println("┌─ STEP 4: PROTECTED MODIFIER ──────────────────────────────────┐");
        System.out.println("│ For inheritance: subclasses + same package                    │");
        System.out.println("└───────────────────────────────────────────────────────────────┘\n");
        
        Step4_ProtectedModifier baseObj = new Step4_ProtectedModifier();
        
        // ✓ Public method accessible everywhere
        System.out.println("✓ Calling public method...");
        baseObj.publicMethod();
        
        // ✓ Protected method accessible in same package
        System.out.println("✓ Calling protected method from same package...");
        baseObj.baseMethod();
        
        // ✓ Protected variable accessible
        String data = baseObj.baseData;
        System.out.println("✓ Accessed protected variable: " + data);
        
        // ✓ Using subclass that overrides protected method
        System.out.println("✓ Creating subclass and overriding protected method...");
        SamePackageSubclass subclass = new SamePackageSubclass();
        subclass.demonstrateAccess();
        
        // ✓ Template method using protected hooks
        System.out.println("✓ Calling template method with protected hooks...");
        baseObj.processData();
        
        // ✓ Unrelated class in same package accessing protected
        System.out.println("✓ Unrelated class accessing protected members...");
        SamePackageUnrelated.demonstrateAccess();
        
        System.out.println("\n[PROTECTED] - For inheritance and extension ✓\n");
    }
    
    /**
     * DEMONSTRATION 5: REAL-WORLD BANK SYSTEM
     */
    private static void demonstrateRealWorldExample() {
        System.out.println("┌─ STEP 5: REAL-WORLD EXAMPLE - BANK SYSTEM ──────────────────┐");
        System.out.println("│ Combining all access modifiers in a production scenario     │");
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");
        
        // Create savings account
        System.out.println("╓─ Creating Savings Account ─────────────────────────────────╖");
        SavingsAccount savingsAccount = new SavingsAccount(1001L, "Nitesh Kumar", 5000);
        savingsAccount.displayAccountInfo();
        
        // Public operations
        System.out.println("\n╓─ Performing Transactions ──────────────────────────────────╖");
        System.out.println("\n✓ Deposit operation:");
        savingsAccount.deposit(1000);
        
        System.out.println("\n✓ Withdraw operation:");
        savingsAccount.withdraw(500);
        
        System.out.println("\n✓ Apply interest (uses protected abstract method):");
        savingsAccount.applyInterest();
        
        System.out.println("\n✓ Final balance: " + savingsAccount.getBalance());
        
        // Create checking account
        System.out.println("\n\n╓─ Creating Checking Account ────────────────────────────────╖");
        CheckingAccount checkingAccount = new CheckingAccount(2001L, "John Doe", 3000);
        checkingAccount.displayAccountInfo();
        
        // Additional feature
        System.out.println("\n✓ Write check operation:");
        checkingAccount.writeCheck("CH001", 500);
        System.out.println("✓ Total checks written: " + checkingAccount.getTotalChecksWritten());
        
        // Validate using package-private validator
        System.out.println("\n\n╓─ Validation (using package-private utility) ───────────────╖");
        System.out.println("✓ Valid account holder: " + AccountValidator.validateAccountHolder("Nitesh"));
        System.out.println("✓ Valid account number: " + AccountValidator.validateAccountNumber(1001L));
        System.out.println("✓ Valid transaction: " + AccountValidator.isValidTransaction(5000));
        
        // Error scenario
        System.out.println("\n✓ Invalid transaction (amount too high):");
        System.out.println("  Valid: " + AccountValidator.isValidTransaction(2000000));
        
        System.out.println("\n[REAL-WORLD] - All modifiers working together! ✓\n");
    }
}

/**
 * ═══════════════════════════════════════════════════════════════════
 * SUMMARY OF ALL ACCESS MODIFIERS
 * ═══════════════════════════════════════════════════════════════════
 * 
 * 1. PUBLIC (Step1_PublicModifier)
 *    - Accessible from anywhere
 *    - Use for external APIs
 *    - Examples: getters, setters, main API methods
 * 
 * 2. PRIVATE (Step2_PrivateModifier)
 *    - Only in the same class
 *    - Use for internal implementation
 *    - Enables encapsulation
 *    - Examples: validation, helper methods, internal state
 * 
 * 3. DEFAULT/PACKAGE-PRIVATE (Step3_DefaultModifier)
 *    - Same package only
 *    - Use for package-internal utilities
 *    - Not mentioned explicitly in code
 *    - Examples: utility classes, internal helpers
 * 
 * 4. PROTECTED (Step4_ProtectedModifier)
 *    - Same package + subclasses
 *    - Use for inheritance hierarchies
 *    - Designed for extension
 *    - Examples: abstract methods, template methods
 * 
 * 5. REAL-WORLD (Step5_AdvancedRealWorld)
 *    - Bank system combining all modifiers
 *    - Production-like scenario
 *    - Shows best practices
 * 
 * ═══════════════════════════════════════════════════════════════════
 * KEY PRINCIPLE: Expose minimum, hide maximum!
 * ═══════════════════════════════════════════════════════════════════
 */

