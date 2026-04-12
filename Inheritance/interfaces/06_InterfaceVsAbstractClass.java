package me.niteshh.OPPs.Inheritance.interfaces;

/**
 * ============================================================================
 * STEP 6: INTERFACE vs ABSTRACT CLASS - WHEN TO USE WHAT?
 * ============================================================================
 * 
 * This is a CRITICAL concept that confuses many developers
 * Both interfaces and abstract classes define contracts, but when to use?
 * 
 * Let's understand the differences in detail
 * ============================================================================
 */

/**
 * ============================================================================
 * ABSTRACT CLASS EXAMPLE
 * ============================================================================
 * 
 * Use Abstract Class when:
 * 1. Classes share a COMMON BASE (IS-A relationship)
 * 2. Need shared state (non-final fields)
 * 3. Need non-public members (protected, private)
 * 4. Need constructors
 * 5. Classes are closely related
 * 
 * Example: Vehicle is base for Car, Bike, Truck
 *          They ARE vehicles
 */
abstract class Vehicle {
    
    // DIFFERENCE 1: Can have non-final fields (state)
    protected String color;
    protected int year;
    
    // DIFFERENCE 2: Can have constructor
    public Vehicle(String color, int year) {
        this.color = color;
        this.year = year;
    }
    
    // DIFFERENCE 3: Can have concrete methods with implementation
    public void honk() {
        System.out.println("Beep! Beep!");
    }
    
    // DIFFERENCE 4: Can have non-public methods
    protected void maintenance() {
        System.out.println("Regular maintenance needed");
    }
    
    // Abstract method (no implementation)
    abstract void drive();
}

class Car extends Vehicle {
    
    private int doors;
    
    public Car(String color, int year, int doors) {
        super(color, year);
        this.doors = doors;
    }
    
    @Override
    void drive() {
        System.out.println("Driving car with " + doors + " doors");
    }
}

/**
 * ============================================================================
 * INTERFACE EXAMPLE
 * ============================================================================
 * 
 * Use Interface when:
 * 1. Classes have NO common base (CAN-DO relationship)
 * 2. Define behavior/capability, not identity
 * 3. Want multiple inheritance
 * 4. Classes are unrelated but share behavior
 * 
 * Example: Different animals CAN be trained
 *          Training is a capability, not identity
 */

// INTERFACE: Animals CAN be trained
interface Trainable {
    void train();
    void obey();
}

// INTERFACE: Animals CAN eat
interface Eatable {
    void eat();
    void drink();
}

// Completely different classes implementing same interface
class Dog implements Trainable, Eatable {
    @Override
    public void train() {
        System.out.println("Dog is being trained");
    }
    
    @Override
    public void obey() {
        System.out.println("Dog obeys the command");
    }
    
    @Override
    public void eat() {
        System.out.println("Dog eating meat");
    }
    
    @Override
    public void drink() {
        System.out.println("Dog drinking water");
    }
}

class Parrot implements Trainable, Eatable {
    @Override
    public void train() {
        System.out.println("Parrot is being trained to speak");
    }
    
    @Override
    public void obey() {
        System.out.println("Parrot repeats the command");
    }
    
    @Override
    public void eat() {
        System.out.println("Parrot eating seeds");
    }
    
    @Override
    public void drink() {
        System.out.println("Parrot drinking from cup");
    }
}

/**
 * ============================================================================
 * DETAILED COMPARISON TABLE
 * ============================================================================
 * 
 * FEATURE                  | ABSTRACT CLASS        | INTERFACE
 * ======================== | ==================== | ======================
 * Purpose                  | Define common base   | Define capability
 * Relationship             | IS-A                 | CAN-DO
 * Inheritance             | extends (1 only)     | implements (multiple)
 * Fields                  | Any type             | public static final only
 * Constructor             | YES                  | NO
 * Methods                 | concrete + abstract  | abstract + default + static
 * Access Modifiers        | protected, private   | public only
 * Use Case                | Related classes      | Unrelated classes
 * 
 * ============================================================================
 * REAL WORLD ANALOGY
 * ============================================================================
 * 
 * ABSTRACT CLASS = Family
 * - All members ARE part of family
 * - Share common DNA
 * - Share family resources
 * - Only one family
 * Example: All animals in Animal family
 * 
 * INTERFACE = Hobby/Skill
 * - You CAN play sports
 * - You CAN learn programming
 * - You CAN speak language
 * - Can have multiple hobbies
 * Example: Training (Trainable interface)
 * 
 * ============================================================================
 * DECISION TREE: ABSTRACT CLASS or INTERFACE?
 * ============================================================================
 * 
 * Question 1: Do classes share common base/IS-A relationship?
 *    YES → Use Abstract Class
 *    NO  → Go to Question 2
 * 
 * Question 2: Do you need to define behavior/capability only?
 *    YES → Use Interface
 *    NO  → Go to Question 3
 * 
 * Question 3: Do you need instance variables or constructors?
 *    YES → Use Abstract Class
 *    NO  → Use Interface
 * 
 * Question 4: Do classes need to inherit from multiple types?
 *    YES → Use Interface (can implement multiple)
 *    NO  → Can use either, prefer abstract class for related classes
 * 
 * ============================================================================
 * PRACTICAL EXAMPLES
 * ============================================================================
 * 
 * WHEN TO USE ABSTRACT CLASS:
 * ---------------------------
 * 1. Shape (Abstract) → Circle, Square, Triangle extend Shape
 *    - All ARE shapes
 *    - Share common properties like color, area calculation logic
 * 
 * 2. Animal (Abstract) → Dog, Cat, Bird extend Animal
 *    - All ARE animals
 *    - Share common behaviors like eating, sleeping
 * 
 * 3. Document (Abstract) → Report, Letter, Invoice extend Document
 *    - All ARE documents
 *    - Share common processing logic
 * 
 * WHEN TO USE INTERFACE:
 * ----------------------
 * 1. Comparable → Sort any objects by implementing compareTo()
 *    - Objects don't inherit from Comparable
 *    - Just define comparison behavior
 * 
 * 2. Cloneable → Any object can be cloned
 *    - Different classes can implement cloning
 *    - Not about IS-A, but CAN-DO
 * 
 * 3. Serializable → Make any object persistable
 *    - Any class can be serialized
 *    - Adding capability, not defining base class
 * 
 * 4. Drawable, Printable, Saveable → Multiple different objects
 *    - Different classes with different bases
 *    - But all can be drawn, printed, saved
 * 
 * ============================================================================
 * HYBRID APPROACH: ABSTRACT CLASS + INTERFACE
 * ============================================================================
 * 
 * Often you combine both for maximum flexibility:
 */

// Abstract base class for all shapes
abstract class ShapeExample {
    protected String color;
    
    abstract void draw();
}

// Interface for resizable objects (capability)
interface ResizableInterface {
    void resize(double factor);
}

// A class can extend abstract class AND implement interface
class ResizableCircleExample extends ShapeExample implements ResizableInterface {
    private double radius;
    
    public ResizableCircleExample(double radius, String color) {
        this.radius = radius;
        this.color = color;
    }
    
    @Override
    void draw() {
        System.out.println("Drawing " + color + " circle");
    }
    
    @Override
    public void resize(double factor) {
        radius = radius * factor;
        System.out.println("Circle resized by factor: " + factor);
    }
}

/**
 * This approach gives you:
 * 1. Common base (from abstract class)
 * 2. Additional capabilities (from interface)
 * 3. Maximum code reuse and flexibility
 */

/**
 * ============================================================================
 * JAVA 8+ CHANGES: BLURRED LINES
 * ============================================================================
 * 
 * Since Java 8, interfaces became more powerful:
 * 
 * Before Java 8:
 * - Interfaces: only abstract methods
 * - Abstract classes: concrete + abstract methods
 * 
 * After Java 8:
 * - Interfaces: abstract + default + static methods
 * 
 * This blurred the line, but the decision principle remains:
 * - Use interface for BEHAVIOR/CAPABILITY
 * - Use abstract class for COMMON BASE/STATE
 * 
 * ============================================================================
 */

