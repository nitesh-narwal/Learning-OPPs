package me.niteshh.OPPs.tutorial.generics.lecturePractice.boundedTypeParameters;

// INTERFACE: Defines the printing behavior
interface Printable {
    void print();
}

// CLASS: Custom number that is both a Number and Printable
class MyNumber extends Number implements Printable {
    
    private final int value;

    public MyNumber(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public void print() {
        System.out.println("MyNumber: " + value);
    }
}

// GENERIC CLASS: Box with multiple bounded type parameters
class Box<T extends Number & Printable> {
    
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void displayNumberInfo() {
        if (item != null) {
            System.out.println("--- As a Number ---");
            System.out.println("Int Value: " + item.intValue());
            System.out.println("Double Value: " + item.doubleValue());
            System.out.println("Long Value: " + item.longValue());
            System.out.println("Float Value: " + item.floatValue());
        }
    }

    public void displayPrintableInfo() {
        if (item != null) {
            System.out.println("--- As Printable ---");
            item.print();
        }
    }

    public void displayBothCapabilities() {
        if (item != null) {
            System.out.println("--- Using Both Number and Printable ---");
            System.out.println("- Double value is: " + item.doubleValue());
            System.out.print("- Printing value: ");
            item.print();
        }
    }
}

// MAIN TEST CLASS
public class Test {
    static void main(String[] args) {
        
        System.out.println("=== Multiple Bounded Type Parameters Example ===\n");
        
        // Create instance of MyNumber
        MyNumber number = new MyNumber(25);
        
        // Create Box with MyNumber as type parameter
        Box<MyNumber> box = new Box<>();
        
        // Store the number in the box
        box.setItem(number);
        System.out.println("Stored MyNumber(25) in Box\n");
        
        // Get and display information
        box.displayNumberInfo();
        System.out.println();
        
        box.displayPrintableInfo();
        System.out.println();
        
        box.displayBothCapabilities();
        System.out.println();
        
        // Also demonstrate with direct access
        MyNumber retrieved = box.getItem();
        System.out.println("--- Direct Access ---");
        System.out.println("Retrieved value as double: " + retrieved.doubleValue());
        System.out.print("Retrieved value via print: ");
        retrieved.print();
        
        System.out.println("\n=== Example Complete ===");
    }
}

/*
 * ════════════════════════════════════════════════════════════════════════════════
 * EXPLANATION OF MULTIPLE BOUNDED TYPE PARAMETERS
 * ════════════════════════════════════════════════════════════════════════════════
 * 
 * 1. WHAT IS A BOUNDED TYPE PARAMETER?
 * ───────────────────────────────────────
 *    A bounded type parameter restricts the types that can be used as type arguments.
 *    The constraint is specified using the 'extends' keyword.
 *    
 *    Syntax: <T extends UpperBound>
 *    Meaning: T can be UpperBound or any subtype of UpperBound.
 * 
 * 
 * 2. WHAT ARE MULTIPLE BOUNDS?
 * ─────────────────────────────
 *    Multiple bounds allow a type parameter to satisfy multiple constraints.
 *    You can have one class bound and multiple interface bounds.
 *    They are separated by the ampersand (&) operator.
 *    
 *    Syntax: <T extends Class & Interface1 & Interface2>
 *    
 *    Rules:
 *    • Maximum one class bound (must be first)
 *    • Unlimited interface bounds
 *    • Class always comes before interfaces
 * 
 * 
 * 3. HOW IT WORKS IN THIS EXAMPLE
 * ───────────────────────────────
 *    The Box class is declared as:
 *    
 *    class Box<T extends Number & Printable>
 *    
 *    This means:
 *    • T must be a type that extends the Number class
 *    • T must also implement the Printable interface
 *    • In other words, T must satisfy BOTH constraints
 * 
 * 
 * 4. VALID vs INVALID TYPE ARGUMENTS
 * ──────────────────────────────────
 *    VALID:
 *    • Box<MyNumber> ✓  (MyNumber extends Number AND implements Printable)
 *    
 *    INVALID:
 *    • Box<String> ✗    (String neither extends Number nor implements Printable)
 *    • Box<Integer> ✗   (Integer extends Number but doesn't implement Printable)
 *    • Box<Double> ✗    (Double extends Number but doesn't implement Printable)
 *    
 *    The compiler will show compile error if you try to use an invalid type.
 * 
 * 
 * 5. WHAT CAN YOU DO WITH MULTIPLE BOUNDS?
 * ────────────────────────────────────────
 *    Because T is guaranteed to be both Number and Printable, you can:
 *    
 *    • Call Number methods:
 *      - item.intValue()
 *      - item.doubleValue()
 *      - item.longValue()
 *      - item.floatValue()
 *    
 *    • Call Printable methods:
 *      - item.print()
 *    
 *    This dual capability is the main benefit of multiple bounds.
 * 
 * 
 * 6. WHY USE MULTIPLE BOUNDS?
 * ──────────────────────────
 *    Multiple bounds are useful when you need a type that:
 *    • Has properties from a parent class (Number in this case)
 *    • Implements specific behavior from interfaces (Printable in this case)
 *    • You want type safety - wrong types won't compile
 *    • You want to use all methods without casting
 * 
 * 
 * 7. REAL WORLD USE CASES
 * ──────────────────────
 *    • <T extends Comparable<T> & Serializable>
 *      A type that can be compared AND serialized
 *    
 *    • <T extends Cloneable & Serializable>
 *      A type that can be cloned AND serialized
 *    
 *    • <T extends Number & Comparable<T>>
 *      A type that is numeric AND comparable
 * 
 * 
 * 8. WHAT WAS WRONG IN ORIGINAL CODE?
 * ───────────────────────────────────
 *    • Box<number> - WRONG! 'number' is a variable, not a class type
 *      Should be: Box<MyNumber> - MyNumber is the class
 *    
 *    • intValue() returning 0 - WRONG! Should return the actual value
 *      This was not returning the stored value
 *    
 *    • Empty Box class - Had no methods to demonstrate the concept
 *      Now it has multiple methods showing Number and Printable capabilities
 *    
 *    • No functionality - The original code couldn't run/demonstrate anything
 *      Now it has complete working implementation
 * 
 * 
 * 9. KEY TAKEAWAYS
 * ────────────────
 *    • Type parameters are placeholders for types, not values
 *    • Multiple bounds use 'extends' for both classes and interfaces
 *    • Class bounds must come before interface bounds
 *    • Compiler enforces all constraints at compile time
 *    • You can use methods from all bounded types without casting
 *    • This provides type safety and code reusability
 * 
 * 
 * 10. OUTPUT EXPLANATION
 * ─────────────────────
 *     The program demonstrates:
 *     1. Creating a custom Number type (MyNumber)
 *     2. Creating a generic Box that accepts only types with both constraints
 *     3. Storing and retrieving the MyNumber in the Box
 *     4. Calling methods from both Number and Printable interfaces
 *     5. Showing that we have access to all methods without any casting
 * 
 */
