package me.niteshh.OPPs.tutorial.generics.depth;

/*
 * ============================================================================
 * STEP 3: BOUNDED TYPE PARAMETERS - INTERMEDIATE LEVEL
 * ============================================================================
 * 
 * WHAT ARE BOUNDED TYPE PARAMETERS?
 * Sometimes aap chahte ho ki generic type kisi specific class ya interface
 * ko extend/implement karega. Iske liye use hote hain bounded type parameters.
 * 
 * SCENARIO:
 * Agar aapko ek generic method banana hai jo sirf Number types pe kam kare:
 * Integer, Double, Float, etc.
 * 
 * SYNTAX:
 * public <T extends Number> void someMethod(T param) { }
 * 
 * "extends" keyword use hota hai upper bound lagane ke liye
 * ============================================================================
 */

public class Step3_BoundedTypeParameters {
    
    // ========================================================================
    // EXAMPLE 1: UPPER BOUNDED TYPE PARAMETER
    // ========================================================================
    
    /*
     * CONCEPT:
     * <T extends Number> ka matlab T sirf Number ya uska subclass ho sakta hai.
     * 
     * Class hierarchy:
     * Object
     *   └-> Number (abstract)
     *       ├-> Integer
     *       ├-> Double
     *       ├-> Float
     *       ├-> Long
     *       └-> etc.
     * 
     * Agar <T extends Number> use karo to:
     * ✅ Integer    - allowed
     * ✅ Double     - allowed
     * ✅ Float      - allowed
     * ❌ String     - NOT allowed
     * ❌ Boolean    - NOT allowed
     */
    
    public class NumberContainer<T extends Number> {
        private T value;
        
        public NumberContainer(T value) {
            this.value = value;
        }
        
        public T getValue() {
            return value;
        }
        
        // Method jo numbers ke saath kuch kar sakte hain
        public double getDoubleValue() {
            // Number type ko double mein convert kar sakte hain
            return value.doubleValue();
        }
        
        public int getIntValue() {
            return value.intValue();
        }
        
        /*
         * USAGE:
         * 
         * NumberContainer<Integer> intContainer = 
         *     new NumberContainer<>(42);
         * 
         * NumberContainer<Double> doubleContainer = 
         *     new NumberContainer<>(3.14);
         * 
         * NumberContainer<String> stringContainer = // ❌ COMPILE ERROR!
         *     new NumberContainer<>("Hello");
         * 
         * Compiler automatically checks bounded type at compile time.
         */
    }
    
    // ========================================================================
    // EXAMPLE 2: BOUNDED GENERIC METHOD
    // ========================================================================
    
    /*
     * Generic method jo sirf Comparable types ke saath kaam kare.
     * 
     * Comparable = ek interface jo "comparison" ka behavior define karta hai.
     * Classes jo Comparable implement karti hain:
     * Integer, String, Double, LocalDate, etc.
     */
    
    public class Comparator<T extends Comparable<T>> {
        
        // Method to find maximum value between two elements
        public T findMax(T a, T b) {
            // Kyunki T extends Comparable<T> hai, toh compareTo() method
            // available hai
            return a.compareTo(b) > 0 ? a : b;
        }
        
        // Method to find minimum value
        public T findMin(T a, T b) {
            return a.compareTo(b) < 0 ? a : b;
        }
        
        /*
         * USAGE:
         * 
         * Comparator<Integer> intComparator = new Comparator<>();
         * Integer max = intComparator.findMax(10, 20);  // Returns 20
         * 
         * Comparator<String> strComparator = new Comparator<>();
         * String max = strComparator.findMax("apple", "zebra");  // "zebra"
         * 
         * Custom class bhi use kar sakte ho agar Comparable implement kare:
         * 
         * public class Person implements Comparable<Person> {
         *     private int age;
         *     
         *     public int compareTo(Person other) {
         *         return this.age - other.age;
         *     }
         * }
         * 
         * Comparator<Person> personComparator = new Comparator<>();
         * Person older = personComparator.findMax(person1, person2);
         */
    }
    
    // ========================================================================
    // EXAMPLE 3: MULTIPLE BOUNDED TYPE PARAMETERS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Ek type parameter ko multiple bounds de sakte ho!
     * 
     * Syntax: <T extends Type1 & Type2 & Type3>
     * 
     * RULES:
     * 1. Maximum ek class bound ho sakta hai (usually Object ya custom class)
     * 2. Unlimited interfaces bound ho sakte hain
     * 3. Class bound always pehle aता hai, phir interfaces
     * 
     * Example: <T extends Number & Comparable>
     * ^ Type Number class se inherit karega
     * ^ AND Comparable interface implement karega
     */
    
    public class MultiplelyBounded<T extends Number & Comparable<T>> {
        private T value;
        
        public MultiplelyBounded(T value) {
            this.value = value;
        }
        
        public T getValue() {
            return value;
        }
        
        // Ab Number ke methods aur Comparable ke methods dono available hain
        public double getAsDouble() {
            return value.doubleValue();  // From Number
        }
        
        public int compareWith(T other) {
            return value.compareTo(other);  // From Comparable
        }
        
        /*
         * USAGE:
         * 
         * MultiplelyBounded<Integer> container = 
         *     new MultiplelyBounded<>(42);
         * 
         * Kyunki Integer both Number aur Comparable implement karta hai:
         * - doubleValue() available hai (from Number)
         * - compareTo() available hai (from Comparable)
         * 
         * ab agar kuch aisa class use karo jo sirf Number extend kare
         * ya sirf Comparable implement kare (dono nahi):
         * 
         * class MyClass extends Number { }  // Comparable implement nahi kiya
         * MultiplelyBounded<MyClass> obj = new MultiplelyBounded<>(myObj);
         * ^ COMPILE ERROR! MyClass Comparable implement nahi karta
         */
    }
    
    // ========================================================================
    // EXAMPLE 4: BOUNDED METHOD WITH INHERITANCE
    // ========================================================================
    
    /*
     * CONCEPT:
     * Generic method mein bhi bounds use kar sakte ho.
     * Jab method specific type ke saath kaam karega, tab bounds use karo.
     */
    
    public class AnimalProcessor {
        
        // Sirf Animal ya uska subclass accept karega
        public <T extends Animal> void processAnimal(T animal) {
            // T Animal ya uska subclass hai
            animal.makeSound();  // Animal mein ye method hai
        }
        
        // Ek array of animals se process karne ke liye
        public <T extends Animal> void processAnimals(T[] animals) {
            for (T animal : animals) {
                animal.makeSound();
            }
        }
    }
    
    // Simple Animal class for demonstration
    public static class Animal {
        public void makeSound() {
            // Default implementation
        }
    }
    
    public static class Dog extends Animal {
        @Override
        public void makeSound() {
            // Woof woof
        }
    }
    
    public static class Cat extends Animal {
        @Override
        public void makeSound() {
            // Meow meow
        }
    }
    
    // ========================================================================
    // IMPORTANT CONCEPTS
    // ========================================================================
    
    /*
     * 1. UPPER BOUND (extends keyword):
     *    <T extends ParentClass>
     *    T can be ParentClass or any subclass
     *    
     * 2. LOWER BOUND (super keyword - learn in next step):
     *    <T super ChildClass>
     *    T can be ChildClass or any parent class
     *    
     * 3. UNBOUNDED TYPE PARAMETER:
     *    <T>
     *    T can be ANY type (Object ke children)
     *    
     * 4. MULTIPLE BOUNDS:
     *    <T extends Class & Interface1 & Interface2>
     *    Exactly one class, multiple interfaces
     *    Class must come first
     */
    
    // ========================================================================
    // REAL-WORLD USE CASE
    // ========================================================================
    
    /*
     * SCENARIO: Repository pattern (common in real apps)
     * 
     * Ek generic repository class jo sirf entities ke saath kaam kare
     * (entities = database tables ke representation)
     * 
     * public class Repository<T extends Entity> {
     *     public void save(T entity) {
     *         // Save to database
     *     }
     *     
     *     public T findById(Long id) {
     *         // Find from database
     *     }
     * }
     * 
     * public class Entity {
     *     // Base entity properties like id, createdAt, updatedAt
     * }
     * 
     * public class User extends Entity {
     *     private String name;
     *     private String email;
     * }
     * 
     * USAGE:
     * Repository<User> userRepo = new Repository<>();
     * userRepo.save(newUser);
     * User user = userRepo.findById(1L);
     */
}

/*
 * ============================================================================
 * SUMMARY OF STEP 3:
 * ============================================================================
 * 
 * KEY POINTS:
 * 1. Bounded type parameters control which types can be used
 * 2. "extends" keyword use karte hain upper bound ke liye
 * 3. Multiple bounds possible hain (1 class + N interfaces)
 * 4. Real-world: Repositories, Service classes, Utilities
 * 5. Type safety zyada better hota hai bounds ke saath
 * 
 * NEXT STEP: Learn about wildcards (?, extends, super)
 * ============================================================================
 */

