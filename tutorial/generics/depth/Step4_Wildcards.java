package me.niteshh.OPPs.tutorial.generics.depth;

import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * STEP 4: WILDCARDS IN GENERICS - INTERMEDIATE TO ADVANCED
 * ============================================================================
 * 
 * WHAT ARE WILDCARDS?
 * Wildcards use question mark (?) to represent an unknown type.
 * They're useful when aap exact type nahi specify karna chahte,
 * lekin kuch constraints hain.
 * 
 * THREE TYPES OF WILDCARDS:
 * 1. Unbounded Wildcard: <?>
 * 2. Upper Bounded Wildcard: <? extends Type>
 * 3. Lower Bounded Wildcard: <? super Type>
 * ============================================================================
 */

public class Step4_Wildcards {
    
    // ========================================================================
    // PART 1: UNBOUNDED WILDCARD (?)
    // ========================================================================
    
    /*
     * CONCEPT:
     * <?>  means "kisi bhi type ko accept karo"
     * 
     * Use case: Jab aapko list ka content matter nahi ho, sirf list ke
     * saath kuch generic operation karna ho.
     * 
     * Example use case: List ka size nikalna, type matter nahi karta
     */
    
    public class UnboundedWildcardExample {
        
        // Method jo kisi bhi type ka list accept kare
        public void printListSize(List<?> list) {
            // <?> = unknown type, lekin definitely ek list hai
            // Size print karna type-independent operation hai
            System.out.println("List size: " + list.size());
        }
        
        // Method jo kisi bhi generic object accept kare
        public void processGeneric(UnboundedWildcard<?> generic) {
            // <?> = kisi bhi type ka ho sakta hai
            // sirf object ko process karte hain, type specific nahi
        }
        
        /*
         * USAGE:
         * 
         * List<String> strings = new ArrayList<>();
         * List<Integer> integers = new ArrayList<>();
         * List<Double> doubles = new ArrayList<>();
         * 
         * Sab ko same method mein pass kar sakte ho:
         * printListSize(strings);    // works
         * printListSize(integers);   // works
         * printListSize(doubles);    // works
         */
    }
    
    public static class UnboundedWildcard<T> {
        private T value;
        public UnboundedWildcard(T value) { this.value = value; }
    }
    
    // ========================================================================
    // PART 2: UPPER BOUNDED WILDCARD (<? extends Type>)
    // ========================================================================
    
    /*
     * CONCEPT:
     * <? extends Type> = sirf Type ya uska subclass accept karo
     * UPPER BOUND lagana ka matlab maximum jo type ho sakti hai
     * 
     * Use case: Jab aapko kisi specific type ya uske child types ke saath
     * kaam karna ho.
     * 
     * Example: Sirf Number types (Integer, Double, Float, etc.)
     */
    
    public class UpperBoundedWildcardExample {
        
        // Method jo sirf Number types ke saath kaam kare
        public double sumNumbers(List<? extends Number> numberList) {
            // <? extends Number> = List of Number or any Number subclass
            // Integer, Double, Float, Long, etc. - sab allowed hain
            
            double sum = 0;
            for (Number num : numberList) {
                // Number ke methods use kar sakte hain
                sum += num.doubleValue();
            }
            return sum;
        }
        
        // Practical example: findMax in list of any number type
        public Number findMax(List<? extends Number> numbers) {
            if (numbers.isEmpty()) return null;
            
            // Comparison ke liye convert to double
            double max = Double.MIN_VALUE;
            Number maxNumber = null;
            
            for (Number num : numbers) {
                if (num.doubleValue() > max) {
                    max = num.doubleValue();
                    maxNumber = num;
                }
            }
            return maxNumber;
        }
        
        // Generic method with upper bounded wildcard
        public void processAnimals(List<? extends Animal> animals) {
            // <?extends Animal> = Animal or any Animal subclass
            for (Animal animal : animals) {
                animal.makeSound();
            }
        }
        
        /*
         * USAGE:
         * 
         * List<Integer> ints = new ArrayList<>();
         * ints.add(10);
         * ints.add(20);
         * double sum1 = sumNumbers(ints);  // works!
         * 
         * List<Double> doubles = new ArrayList<>();
         * doubles.add(3.14);
         * doubles.add(2.71);
         * double sum2 = sumNumbers(doubles);  // works!
         * 
         * List<String> strings = new ArrayList<>();  
         * sumNumbers(strings);  // ❌ COMPILE ERROR! String nahi Number extend karta
         */
    }
    
    // ========================================================================
    // PART 3: LOWER BOUNDED WILDCARD (<? super Type>)
    // ========================================================================
    
    /*
     * CONCEPT:
     * <? super Type> = sirf Type ya uska parent class accept karo
     * LOWER BOUND = minimum jo type ho sakti hai
     * 
     * Use case: Jab aapko data ADD karna ho generic collection mein
     * 
     * IMPORTANT: Upper bounded wildcard mein generally READ karte ho
     *            Lower bounded wildcard mein generally WRITE karte ho
     */
    
    public class LowerBoundedWildcardExample {
        
        // Method jo child type ko parent type ke list mein add kare
        public void addDog(List<? super Dog> animalList) {
            // <? super Dog> = Dog class ya Object class, ki saari parent classes
            // Dog ko add kar sakte hain
            Dog dog = new Dog();
            animalList.add(dog);
        }
        
        // Generic method for converting list
        public void addNumbers(List<? super Integer> numberList) {
            // <? super Integer> = Integer ya Object class
            // Integer ko add kar sakte hain
            numberList.add(new Integer(42));
            numberList.add(new Integer(100));
        }
        
        /*
         * USAGE:
         * 
         * List<Object> objects = new ArrayList<>();
         * addDog(objects);  // works! Object is parent of Dog
         * 
         * List<Animal> animals = new ArrayList<>();
         * addDog(animals);  // works! Animal is parent of Dog
         * 
         * List<Dog> dogs = new ArrayList<>();
         * addDog(dogs);  // works! Dog is Dog itself
         * 
         * List<String> strings = new ArrayList<>();
         * addDog(strings);  // ❌ COMPILE ERROR! String nahi parent of Dog
         */
    }
    
    // ========================================================================
    // PART 4: PECS PRINCIPLE (Producer-Extends, Consumer-Super)
    // ========================================================================
    
    /*
     * GOLDEN RULE FOR WILDCARD USAGE:
     * 
     * PRODUCER (Reading data):
     * Jab list se data NIKALNA (read) ho:
     * Use <? extends Type>
     * 
     * Example:
     * public void display(List<? extends Number> list) {
     *     for (Number n : list) {
     *         System.out.println(n);  // READ operation
     *     }
     * }
     * 
     * 
     * CONSUMER (Adding data):
     * Jab list mein data DALNA (write) ho:
     * Use <? super Type>
     * 
     * Example:
     * public void fill(List<? super Integer> list) {
     *     list.add(42);  // WRITE operation
     * }
     * 
     * 
     * PECS = Producer Extends, Consumer Super
     * Ye rule follow karo to generics sahi tarah se use ho jaayega.
     */
    
    // ========================================================================
    // PART 5: COMPARISON - GENERICS vs WILDCARDS
    // ========================================================================
    
    /*
     * GENERICS (Type Parameter):
     * public class Box<T> { }
     * 
     * PROS:
     * - Entire class/method k liye type consistent hota hai
     * - Type variable ko multiple operations k saath use kar sakte ho
     * - Better for complex scenarios
     * 
     * CONS:
     * - Specific type define karna padta hai jab class create karte ho
     * 
     * 
     * WILDCARDS:
     * public void method(List<?> list) { }
     * 
     * PROS:
     * - Flexible - kisi bhi type accept kar sakte ho
     * - Single parameter ke liye use kar sakte ho
     * - Simple scenarios ke liye better
     * 
     * CONS:
     * - Type information limited hoti hai
     * - Zyada restrictions hoti hain kya kar sakte ho
     */
    
    public static class Animal {
        public void makeSound() { }
    }
    
    public static class Dog extends Animal {
        @Override
        public void makeSound() { }
    }
    
    public static class Cat extends Animal {
        @Override
        public void makeSound() { }
    }
}

/*
 * ============================================================================
 * SUMMARY OF STEP 4:
 * ============================================================================
 * 
 * WILDCARDS:
 * 1. Unbounded <?> - kisi bhi type accept kare
 * 2. Upper <? extends Type> - Type ya subclass accept kare
 * 3. Lower <? super Type> - Type ya parent class accept kare
 * 
 * GOLDEN RULE (PECS):
 * - Producer Extends: Read data -> use extends
 * - Consumer Super: Write data -> use super
 * 
 * WHEN TO USE:
 * - Generics: Full control chahiye, consistent type de sakte ho
 * - Wildcards: Flexible chahiye, single parameter ke liye
 * 
 * NEXT STEP: Generic methods aur advanced patterns
 * ============================================================================
 */

