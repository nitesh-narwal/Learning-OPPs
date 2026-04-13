package me.niteshh.OPPs.tutorial.accessmodifier.inDeepth;

/**
 * STEP 4: PROTECTED ACCESS MODIFIER - INTERMEDIATE LEVEL
 * 
 * Protected is designed for inheritance scenarios.
 * 
 * Key Points:
 * - Can be accessed within SAME PACKAGE
 * - Can be accessed from SUBCLASSES (even in different packages)
 * - Cannot be accessed from unrelated classes in different packages
 * - Perfect for methods/variables that need to be overridden or extended
 * 
 * When to use Protected:
 * - Methods meant to be overridden by subclasses
 * - Variables that subclasses need to access
 * - Template methods in abstract classes
 * - Internal APIs for inheritance hierarchies
 */

// BASE CLASS with protected members
public class Step4_ProtectedModifier {
    
    // PROTECTED VARIABLE - Accessible in subclasses and same package
    protected String baseData = "Available to subclasses";
    
    // PRIVATE VARIABLE - Only in this class
    private String secretData = "Only in this class";
    
    /**
     * PROTECTED METHOD - Can be overridden in subclasses
     * This is part of the inheritance contract
     */
    protected void baseMethod() {
        System.out.println("Base method - can be overridden");
    }
    
    /**
     * PROTECTED METHOD - Template method pattern
     * Calls hook methods that subclasses can override
     */
    protected void processData() {
        System.out.println("Processing data...");
        performValidation(); // Call hook method
        performTransformation(); // Call hook method
        System.out.println("Processing complete!");
    }
    
    /**
     * PROTECTED HOOK METHOD - Subclasses override this
     */
    protected void performValidation() {
        System.out.println("Default validation");
    }
    
    /**
     * PROTECTED HOOK METHOD - Subclasses override this
     */
    protected void performTransformation() {
        System.out.println("Default transformation");
    }
    
    /**
     * PUBLIC METHOD - Everyone can call
     */
    public void publicMethod() {
        System.out.println("Public method");
    }
    
    /**
     * PRIVATE METHOD - Only in this class
     */
    private void privateMethod() {
        System.out.println("Private method");
    }
}

/**
 * SUBCLASS in the SAME PACKAGE
 * Can access protected members of parent class
 */
class SamePackageSubclass extends Step4_ProtectedModifier {
    
    public void demonstrateAccess() {
        // ✓ Can access protected variable
        String data = baseData;
        
        // ✓ Can call protected method
        baseMethod();
        
        // ✓ Can call public method
        publicMethod();
        
        // ✗ Cannot access private method
        // privateMethod();  // COMPILATION ERROR
    }
    
    @Override
    protected void baseMethod() {
        System.out.println("Overridden in same package subclass");
    }
    
    @Override
    protected void performValidation() {
        System.out.println("Custom validation in same package");
    }
}

/**
 * UNRELATED CLASS in the SAME PACKAGE
 * Can still access protected members (because it's in same package)
 */
class SamePackageUnrelated {
    
    public static void demonstrateAccess() {
        Step4_ProtectedModifier obj = new Step4_ProtectedModifier();
        
        // ✓ Can access protected variable (same package)
        String data = obj.baseData;
        
        // ✓ Can call protected method (same package)
        obj.baseMethod();
        
        // ✓ Can call public method
        obj.publicMethod();
        
        // ✗ Cannot access private
        // obj.privateMethod();  // COMPILATION ERROR
    }
}

/**
 * IMPORTANT: If this was in a DIFFERENT PACKAGE:
 * 
 * package com.example.other;
 * 
 * public class DifferentPackageSubclass extends Step4_ProtectedModifier {
 *     
 *     public void demonstrateAccess() {
 *         // ✓ Can access protected variable (is subclass)
 *         String data = baseData;
 *         
 *         // ✓ Can call protected method (is subclass)
 *         baseMethod();
 *         
 *         // ✓ Can call public method
 *         publicMethod();
 *     }
 * }
 * 
 * public class DifferentPackageUnrelated {
 *     
 *     public static void demonstrateAccess() {
 *         Step4_ProtectedModifier obj = new Step4_ProtectedModifier();
 *         
 *         // ✓ Can call public method only
 *         obj.publicMethod();
 *         
 *         // ✗ Cannot access protected (not subclass)
 *         // obj.baseMethod();      // COMPILATION ERROR
 *         // obj.baseData = "";     // COMPILATION ERROR
 *     }
 * }
 */

/**
 * VISIBILITY CHART FOR PROTECTED:
 * 
 * Location                          | Can Access Protected Members?
 * ================================  | ============================
 * Same Class                        | ✓ YES
 * Same Package (Subclass)           | ✓ YES
 * Same Package (Unrelated)          | ✓ YES
 * Different Package (Subclass)      | ✓ YES
 * Different Package (Unrelated)     | ✗ NO
 * 
 * The KEY DIFFERENCE from default:
 * Different Package (Subclass)      | ✓ YES (PROTECTED allows this)
 * Different Package (Subclass)      | ✗ NO  (DEFAULT doesn't allow this)
 */

/**
 * REAL-WORLD EXAMPLE:
 * 
 * public abstract class DataProcessor {
 *     protected List<Data> data; // For subclasses to use
 *     
 *     protected abstract void process();  // Subclasses override this
 *     
 *     public final void execute() {      // Final, cannot override
 *         loadData();
 *         process();                     // Calls overridden method
 *         saveResults();
 *     }
 * }
 * 
 * public class CSVProcessor extends DataProcessor {
 *     @Override
 *     protected void process() {        // Override protected method
 *         // Use protected data variable
 *         for (Data d : data) {
 *             // Process CSV data
 *         }
 *     }
 * }
 */

