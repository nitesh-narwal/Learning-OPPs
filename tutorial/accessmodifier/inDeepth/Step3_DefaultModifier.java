package me.niteshh.OPPs.tutorial.accessmodifier.inDeepth;

/**
 * STEP 3: DEFAULT ACCESS MODIFIER (PACKAGE-PRIVATE) - INTERMEDIATE LEVEL
 * 
 * When you don't specify any access modifier, it defaults to "package-private"
 * 
 * Key Points:
 * - Can be accessed from SAME PACKAGE ONLY
 * - Not accessible from different packages
 * - Not accessible from subclasses in different packages
 * - Sits between private and protected in terms of visibility
 * - Used when you want to share within a package but hide from outside
 * 
 * When to use Default:
 * - Internal utility classes within a package
 * - Helper classes used only within the same package
 * - Classes that support other classes in the same package
 * - Implementation details shared within a package
 */

// NO MODIFIER = DEFAULT (PACKAGE-PRIVATE)
// This class can only be accessed from classes in the same package
class PackagePrivateClass {
    
    // DEFAULT VARIABLE - Accessible only within this package
    String packageData = "Only visible in same package";
    
    /**
     * DEFAULT METHOD - Only accessible within this package
     */
    void packageMethod() {
        System.out.println("This is a package-private method");
    }
}

/**
 * Another PUBLIC class in the same package
 * PUBLIC means it can be accessed from anywhere
 * But it can use DEFAULT members from the same package
 */
public class Step3_DefaultModifier {
    
    // DEFAULT VARIABLE - Accessible only within this package
    String internalData = "Shared within package";
    
    // PRIVATE VARIABLE - Only in this class
    private String secretData = "Only in this class";
    
    /**
     * PUBLIC METHOD - Can be called from anywhere
     */
    public void publicMethod() {
        System.out.println("Public method - accessible everywhere");
    }
    
    /**
     * DEFAULT METHOD - Only accessible within this package
     * Notice: NO public, private, or protected keyword
     */
    void packageMethod() {
        System.out.println("Package method - only in same package");
    }
    
    /**
     * PRIVATE METHOD - Only within this class
     */
    private void privateMethod() {
        System.out.println("Private method - only in this class");
    }
    
    /**
     * PUBLIC METHOD - Demonstrates scope usage
     */
    public void demonstrateScopes() {
        // ✓ Can call private method (within same class)
        privateMethod();
        
        // ✓ Can access private variable (within same class)
        String secret = secretData;
        
        // ✓ Can access default variable (within same class)
        String internal = internalData;
        
        // ✓ Can call default method (within same class)
        packageMethod();
    }
}

/**
 * This class is also in the same package
 * It can access default members of Step3_DefaultModifier
 */
class PackagePartner {
    
    public static void demonstrateAccess() {
        Step3_DefaultModifier obj = new Step3_DefaultModifier();
        
        // ✓ Can access public method
        obj.publicMethod();
        
        // ✓ Can access default method (same package)
        obj.packageMethod();
        
        // ✓ Can access default variable (same package)
        String data = obj.internalData;
        System.out.println(data);
        
        // ✗ Cannot access private method
        // obj.privateMethod();  // COMPILATION ERROR
        
        // ✗ Cannot access private variable
        // String secret = obj.secretData;  // COMPILATION ERROR
    }
}

/**
 * VISIBILITY CHART FOR DEFAULT ACCESS:
 * 
 * Location                          | Can Access Default Members?
 * ================================  | ===========================
 * Same Class                        | ✓ YES
 * Same Package                      | ✓ YES
 * Same Package (Different Class)    | ✓ YES
 * Different Package (Subclass)      | ✗ NO
 * Different Package (Unrelated)     | ✗ NO
 * 
 * EXAMPLE SCENARIO:
 * 
 * Package: me.niteshh.OPPs.tutorial.accessmodifier.inDeepth
 * ├── Step3_DefaultModifier (public class with default methods)
 * ├── PackagePartner (class with default access)
 * └── [Can access each other's default members]
 * 
 * Package: com.example.other
 * └── UnrelatedClass
 *     └── [Cannot access any default members]
 */

/**
 * KEY DIFFERENCE: DEFAULT vs PROTECTED
 * 
 * DEFAULT (no keyword):
 * - Same package: ✓ YES
 * - Different package subclass: ✗ NO
 * - Usage: Internal package utilities
 * 
 * PROTECTED (we'll see next):
 * - Same package: ✓ YES
 * - Different package subclass: ✓ YES
 * - Usage: tutorial relationships
 */

