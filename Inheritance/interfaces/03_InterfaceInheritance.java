package me.niteshh.OPPs.Inheritance.interfaces;

/**
 * ============================================================================
 * STEP 3: INTERFACE INHERITANCE & ADVANCED CONCEPTS
 * ============================================================================
 * 
 * Did you know? Interfaces can also EXTEND other interfaces!
 * 
 * Syntax:
 * -------
 * public interface InterfaceA extends InterfaceB {
 *     // InterfaceA inherits all methods from InterfaceB
 *     // Plus can add new methods
 * }
 * 
 * Key Points:
 * -----------
 * 1. Interface can extend MULTIPLE interfaces (unlike class which extends one)
 *    Syntax: public interface A extends B, C, D { }
 * 
 * 2. When a class implements child interface, it must implement ALL methods
 *    from both child and parent interfaces
 * 
 * 3. Interface Variables:
 *    - All variables in interface are PUBLIC, STATIC, FINAL by default
 *    - Must be initialized when declared
 *    - Example: int MAX_SIZE = 100; // implicitly public static final
 * 
 * 4. Default Methods (Java 8+):
 *    - Interfaces can have methods with implementation
 *    - Syntax: default void methodName() { implementation }
 *    - Implementing class inherits this method (but can override)
 * 
 * 5. Static Methods (Java 8+):
 *    - Interfaces can have static methods
 *    - Syntax: static void methodName() { implementation }
 *    - Must be called using InterfaceName.methodName()
 *    - Cannot be overridden
 * 
 * 6. Private Methods (Java 9+):
 *    - Interfaces can have private methods for helper functions
 *    - Syntax: private void methodName() { implementation }
 * ============================================================================
 */

// PARENT INTERFACE: Basic shape properties
interface Shape {
    // Interface constant (implicitly public static final)
    double PI = 3.14159;
    
    // Abstract method
    void draw();
    
    // Default method - class inherits this if not overridden
    default void describe() {
        System.out.println("This is a shape");
    }
    
    // Static method - belongs to interface, not instance
    static void printShapeInfo() {
        System.out.println("Shape is a 2D figure\n");
    }
}

// INTERFACE: Extends Shape interface (Inheritance between interfaces)
interface Drawable extends Shape {
    /**
     * New abstract method only in Drawable
     * But implementing class must implement BOTH:
     * - draw() from Shape
     * - fill() from Drawable
     */
    void fill();
    
    // Default method with better implementation
    @Override
    default void describe() {
        System.out.println("This is a drawable shape");
    }
}

/**
 * CLASS: Circle
 * =============
 * Implements Drawable interface (which extends Shape)
 * Must implement: draw(), fill() (from Drawable)
 */
class Circle implements Drawable {
    
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public void draw() {
        System.out.println("○ Drawing a circle with radius: " + radius);
    }
    
    @Override
    public void fill() {
        System.out.println("✓ Filling circle with color");
    }
    
    // Can override default methods if needed
    @Override
    public void describe() {
        System.out.println("I am a circle with radius: " + radius);
    }
    
    // Calculate area using PI constant from Shape interface
    public double calculateArea() {
        return Shape.PI * radius * radius;
    }
}

/**
 * CLASS: Rectangle
 * ================
 * Also implements Drawable interface
 * Different implementation same interface
 */
class Rectangle implements Drawable {
    
    private double length;
    private double width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    public void draw() {
        System.out.println("▭ Drawing a rectangle: " + length + " x " + width);
    }
    
    @Override
    public void fill() {
        System.out.println("✓ Filling rectangle with pattern");
    }
    
    public double calculateArea() {
        return length * width;
    }
}

/**
 * INTERFACE: Resizable
 * ====================
 * Independent interface for resizable objects
 */
interface Resizable {
    void resize(double factor);
}

/**
 * CLASS: ResizableCircle
 * ======================
 * Implements MULTIPLE interfaces
 * Shows how a class can implement multiple unrelated interfaces
 */
class ResizableCircle implements Drawable, Resizable {
    
    private double radius;
    
    public ResizableCircle(double radius) {
        this.radius = radius;
    }
    
    // From Drawable
    @Override
    public void draw() {
        System.out.println("○ Drawing resizable circle: " + radius);
    }
    
    // From Drawable
    @Override
    public void fill() {
        System.out.println("✓ Filling resizable circle");
    }
    
    // From Resizable
    @Override
    public void resize(double factor) {
        radius = radius * factor;
        System.out.println("↺ Circle resized by factor: " + factor + ", new radius: " + radius);
    }
    
    // Calculate area using PI constant from Shape interface
    public double calculateArea() {
        return Shape.PI * radius * radius;
    }
}

/**
 * SUMMARY OF INTERFACE FEATURES:
 * 
 * 1. ABSTRACT METHODS (Java 1.0+)
 *    public void methodName();
 *    No implementation, must be overridden by implementing class
 * 
 * 2. CONSTANTS
 *    int MAX = 100;
 *    Implicitly public static final
 * 
 * 3. DEFAULT METHODS (Java 8+)
 *    default void methodName() { }
 *    Has implementation, can be inherited or overridden
 * 
 * 4. STATIC METHODS (Java 8+)
 *    static void methodName() { }
 *    Called as Interface.methodName(), cannot be overridden
 * 
 * 5. PRIVATE METHODS (Java 9+)
 *    private void methodName() { }
 *    Helper methods for default/static methods
 * 
 * 6. INTERFACE INHERITANCE
 *    interface Child extends Parent { }
 *    Can extend multiple interfaces
 * 
 * 7. MULTIPLE INTERFACE IMPLEMENTATION
 *    class MyClass implements I1, I2, I3 { }
 *    Java's way of multiple inheritance
 */

