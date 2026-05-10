package me.niteshh.OPPs.tutorial.generics.depth;

/*
 * ============================================================================
 * STEP 1: BASICS OF GENERICS - BEGINNER LEVEL
 * ============================================================================
 * 
 * WHAT ARE GENERICS?
 * Generics allow you to write classes, interfaces, and methods that can work
 * with any type while maintaining type safety. They enable you to specify
 * the type of objects when you define a class or method.
 * 
 * KEY BENEFITS:
 * 1. Type Safety - Compile-time checking instead of runtime casting
 * 2. Code Reusability - One generic class works for multiple types
 * 3. Elimination of Casting - No need to cast retrieved objects
 * 4. Enables Stronger Algorithms - Better compile-time error detection
 * 
 * SYNTAX FOR GENERIC CLASS:
 * public class ClassName<T> { }
 * 
 * WHERE 'T' IS A TYPE PARAMETER - yeh ek placeholder hai jo actual type ko
 * represent karega jab object create karte hain.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.List;

// BEGINNER EXAMPLE 1: Simple Generic Class
public class Step1_BasicsOfGenerics<T> {
    
    /*
     * EXPLANATION: 
     * <T> means yeh class kisi bhi type ko accept kar sakta hai.
     * T = Type Parameter (ek variable ki tarah jo type ko represent karega)
     * 
     * Jab class create karte ho:
     * Step1_BasicsOfGenerics<String> obj = new Step1_BasicsOfGenerics<>();
     * Tab T ki jagah String aata hai sare methods mein
     */
    
    private T value;
    
    // CONSTRUCTOR - value ko store karne ke liye
    public Step1_BasicsOfGenerics(T value) {
        this.value = value;
    }
    
    // GETTER METHOD
    public T getValue() {
        return value;
    }
    
    // SETTER METHOD
    public void setValue(T value) {
        this.value = value;
    }
    
    // ============================================================================
    // GENERIC METHOD EXAMPLE
    // ============================================================================
    /*
     * EXPLANATION:
     * Generic method ka apna type parameter hota hai jo class ke type parameter
     * se independent hota hai.
     * 
     * Syntax: <ReturnType> returnType methodName(ReturnType parameter)
     * Example below mein: <U> U printAndReturn(U element)
     * Yeh U kisi bhi type ka ho sakta hai, T se alag.
     */
    
    public <U> U printAndReturn(U element) {
        // U kisi bhi type ka ho sakta hai - String, Integer, Custom Class, etc.
        return element;
    }
    
    // PRACTICAL EXAMPLE: Generic method that works with any type
    public <E> void displayElement(E element) {
        // Yeh method kisi bhi type ke element ko display karega
    }
    
    // ============================================================================
    // BEGINNER MISTAKE #1: Type Erasure
    // ============================================================================
    /*
     * IMPORTANT CONCEPT:
     * Java mein generics ka information COMPILE TIME pe hota hai.
     * RUNTIME pe T just Object ban jaata hai (Type Erasure).
     * 
     * Example:
     * Step1_BasicsOfGenerics<String> strObj = new Step1_BasicsOfGenerics<>("Hello");
     * Step1_BasicsOfGenerics<Integer> intObj = new Step1_BasicsOfGenerics<>(42);
     * 
     * RUNTIME pe:
     * strObj.getValue() -> Object return karega (then automatically String se cast)
     * intObj.getValue() -> Object return karega (then automatically Integer se cast)
     * 
     * This is why you cannot do:
     * if (obj instanceof Step1_BasicsOfGenerics<String>)  // COMPILE ERROR!
     * 
     * You can only do:
     * if (obj instanceof Step1_BasicsOfGenerics)  // WITHOUT TYPE PARAMETER
     */
    
    // ============================================================================
    // COMPARISON: WITHOUT GENERICS vs WITH GENERICS
    // ============================================================================
    
    /*
     * WITHOUT GENERICS (OLD WAY - NOT RECOMMENDED):
     * 
     *   public class Box {
     *       private Object item;  // Object ko store karte hain
     *       
     *       public void put(Object item) {
     *           this.item = item;
     *       }
     *       
     *       public Object get() {
     *           return item;  // Object return hota hai
     *       }
     *   }
     * 
     *   Box box = new Box();
     *   box.put("Hello");
     *   String result = (String) box.get();  // CASTING REQUIRED! 🔴 Type Unsafe
     *   
     * PROBLEMS:
     * 1. Runtime casting required - agar galat type cast kare to runtime error
     * 2. Type safety nahi hai - compiler ko pata nahi ki String aayega
     * 3. Code readable nahi hai
     * 
     * 
     * WITH GENERICS (NEW WAY - RECOMMENDED):
     * 
     *   public class Box<T> {
     *       private T item;
     *       
     *       public void put(T item) {
     *           this.item = item;
     *       }
     *       
     *       public T get() {
     *           return item;
     *       }
     *   }
     * 
     *   Box<String> box = new Box<>();
     *   box.put("Hello");
     *   String result = box.get();  // NO CASTING NEEDED! ✅ Type Safe
     *   
     * BENEFITS:
     * 1. Compile-time type checking - compiler error ayega agar galat type doge
     * 2. Type safe - kya type aayega ye pata hai advance mein
     * 3. Code readable - saaf pata chalta hai ki String ko hi use kar rahe ho
     * 4. No casting - automatically type match ho jaata hai
     */
    
    // ============================================================================
    // PRACTICAL EXAMPLE: Generic Collection
    // ============================================================================
    
    // Without Generics (OLD WAY):
    public static void demonstrateWithoutGenerics() {
        List list = new ArrayList();  // Type unsafe
        list.add("String");
        list.add(123);
        list.add(45.67);
        
        // Compiler warning: Raw use of parameterized class 'List'
        // Runtime mein kabhi bhi ClassCastException aasakte hain
        for (Object obj : list) {
            // Object hi milta hai, type pata nahi
        }
    }
    
    // With Generics (NEW WAY):
    public static void demonstrateWithGenerics() {
        List<String> stringList = new ArrayList<>();  // Type safe
        stringList.add("Hello");
        // stringList.add(123);  // COMPILE ERROR! ✅ Type checking at compile time
        
        for (String str : stringList) {
            // String directly milta hai, casting nahi chahiye
            int length = str.length();
        }
    }
    
    // ============================================================================
    // NAMING CONVENTIONS FOR TYPE PARAMETERS
    // ============================================================================
    /*
     * Standard naming conventions (follow these):
     * T = Type (most common, any general type)
     * E = Element (used in collections)
     * K = Key (used in maps)
     * V = Value (used in maps)
     * N = Number
     * R = Result
     * U, V, W, X, Y, Z = Multiple type parameters
     * 
     * Examples:
     * public class List<E> { }           // E = Element
     * public class Map<K, V> { }         // K = Key, V = Value
     * public class Pair<T, U> { }        // T and U = Generic types
     * public class Number<N extends Number> { }  // N = bounded to Number
     */
    
} // End of class

/*
 * ============================================================================
 * SUMMARY OF STEP 1:
 * ============================================================================
 * 1. Generics allow type-safe reusable code
 * 2. Type parameter <T> placeholder hota hai actual type ke liye
 * 3. Compile-time mein type check hota hai, safe hai code
 * 4. Casting required nahi hai generics se
 * 5. Type erasure ke wajah se runtime pe T = Object
 * 6. Naming conventions follow karo (T, E, K, V, etc.)
 */

