package me.niteshh.OPPs.tutorial.accessmodifier.inDeepth;

/**
 * STEP 1: PUBLIC ACCESS MODIFIER - BEGINNER LEVEL
 * 
 * Public is the least restrictive access modifier.
 * 
 * Key Points:
 * - Can be accessed from ANYWHERE (same package, different package, subclasses)
 * - No restrictions on who can access the member
 * - Use sparingly - expose only what's necessary for external use
 * 
 * When to use Public:
 * - API methods that clients need to call
 * - Global utility methods
 * - Exception classes
 * - Constants (static final variables)
 */

public class Step1_PublicModifier {
    
    // PUBLIC VARIABLE - Can be accessed from anywhere
    public String name;
    
    // PUBLIC CONSTANT - Typically used for constants
    public static final double PI = 3.14159;
    
    /**
     * PUBLIC METHOD - Can be called from anywhere
     * This is your API to the outside world
     */
    public void greet() {
        System.out.println("Hello! I'm " + name);
    }
    
    /**
     * PUBLIC METHOD - Can be called from anywhere
     * This method represents the public contract of this class
     */
    public String getName() {
        return name;
    }
    
    /**
     * PUBLIC METHOD - Can be called from anywhere
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * PUBLIC CLASS - Can be accessed from anywhere
 * Note: Only one public class per file, and filename must match the public class name
 */
class PublicClassExample {
    public String message = "I can be accessed from anywhere";
    
    public void displayMessage() {
        System.out.println(message);
    }
}

/**
 * EXAMPLE USAGE:
 * 
 * Step1_PublicModifier obj = new Step1_PublicModifier();
 * obj.name = "Nitesh";  // Direct access - public variable
 * obj.greet();          // Call public method from anywhere
 * 
 * Even from different packages:
 * import me.niteshh.OPPs.tutorial.accessmodifier.inDeepth.Step1_PublicModifier;
 * 
 * Step1_PublicModifier obj = new Step1_PublicModifier();
 * obj.setName("John");  // Still accessible from different package
 */

