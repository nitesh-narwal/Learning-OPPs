package me.niteshh.OPPs.tutorial.generics.depth;

import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * STEP 5: GENERIC METHODS AND ADVANCED PATTERNS - ADVANCED LEVEL
 * ============================================================================
 * 
 * GENERIC METHODS:
 * Methods jo apne type parameter rakh sakte hain independently.
 * Class ke type parameter se alag hote hain.
 * 
 * SYNTAX:
 * public <T> returnType methodName(T param) { }
 * public <T, U> returnType methodName(T param1, U param2) { }
 * 
 * BENEFITS:
 * 1. Method-level type safety
 * 2. Reusable across different classes
 * 3. Flexible argument types
 * ============================================================================
 */

public class Step5_GenericMethods {
    
    // ========================================================================
    // PART 1: BASIC GENERIC METHODS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Generic method apne type parameters define karta hai.
     * Ye type parameters sirf us method ke scope mein valid hote hain.
     * 
     * Jab method call karte ho, compiler automatically type infer karta hai.
     * Explicit type specify kar sakte ho bhi agar chahiye.
     */
    
    // Simple generic method that prints any element
    public static <T> void printElement(T element) {
        // T kisi bhi type ka ho sakta hai
        // Ye method T type ke element ko print karega
        System.out.println("Element: " + element);
    }
    
    // Method jo array ka first element return kare
    public static <T> T getFirstElement(T[] array) {
        if (array.length == 0) {
            return null;
        }
        return array[0];
    }
    
    // Method jo two elements ko swap kare
    public static <T> void swapElements(T[] array, int i, int j) {
        if (i < array.length && j < array.length && i >= 0 && j >= 0) {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
    
    // ========================================================================
    // PART 2: BOUNDED GENERIC METHODS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Generic method mein bhi bounds use kar sakte ho.
     * Jab specific type ke saath kaam karna ho tab bounds use karo.
     * 
     * Example: <T extends Number>  - sirf Number types
     *          <T extends Comparable>  - sirf Comparable types
     */
    
    // Generic method jo sirf Number types ke saath kaam kare
    public static <T extends Number> double doubleValue(T number) {
        // T extend karta hai Number ko, toh doubleValue() method available hai
        return number.doubleValue();
    }
    
    // Method jo comparable elements ko compare kare
    public static <T extends Comparable<T>> T findMax(T a, T b, T c) {
        T max = a;
        if (b.compareTo(max) > 0) {
            max = b;
        }
        if (c.compareTo(max) > 0) {
            max = c;
        }
        return max;
    }
    
    // Method jo list of numbers ka sum return kare
    public static <T extends Number> double sumList(List<T> numbers) {
        double sum = 0;
        for (T num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }
    
    // ========================================================================
    // PART 3: MULTIPLE TYPE PARAMETERS IN METHODS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Ek method mein multiple type parameters ho sakte hain.
     * Har type parameter ke liye bounds la sakte ho.
     * 
     * Syntax: <T, U, V> methodName(T t, U u, V v)
     */
    
    // Method jo two different types ko handle kare
    public static <T, U> void displayPair(T first, U second) {
        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }
    
    // Generic method jo type conversion kare
    public static <S, T> T convert(S source, Class<T> targetClass) {
        // Source se target class mein conversion
        // Real implementation mein actual conversion logic hota hai
        return null;
    }
    
    // Method with multiple bounded type parameters
    public static <T extends Number & Comparable<T>> T getMaxNumber(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }
    
    // ========================================================================
    // PART 4: GENERIC METHODS WITH WILDCARDS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Generic methods mein wildcards bhi use kar sakte ho.
     * Collections ke saath kaam karte waqt ye bahut useful hota hai.
     */
    
    // Method jo kisi bhi type ke list ko copy kare
    public static <T> void copyList(List<T> source, List<? super T> destination) {
        // Source from list (read operation) -> use normal T
        // Destination to list (write operation) -> use <? super T>
        for (T item : source) {
            destination.add(item);
        }
    }
    
    // Method jo list ke elements ko print kare (any type ke)
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }
    
    // Method jo list of numbers ko sum kare (any Number subclass)
    public static double sumNumberList(List<? extends Number> numbers) {
        double sum = 0;
        for (Number n : numbers) {
            sum += n.doubleValue();
        }
        return sum;
    }
    
    // ========================================================================
    // PART 5: RECURSIVE TYPE BOUNDS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Ek advanced pattern jaha type parameter apne aap ko reference karta hai.
     * Example: <T extends Comparable<T>>
     * 
     * Ye recursive type bound kehlaata hai.
     * SELF-REFERENTIAL GENERICS.
     * 
     * Use case: Jab methods chain karni ho ya fluent API banana ho.
     */
    
    // Class demonstrating recursive type bounds
    public static class Node<T extends Comparable<T>> {
        private T data;
        private Node<T> next;
        
        public Node(T data) {
            this.data = data;
        }
        
        // Generic method jo maximum element find kare
        public T getMax(Node<T> other) {
            return this.data.compareTo(other.data) > 0 ? this.data : other.data;
        }
    }
    
    // ========================================================================
    // PART 6: VARARGS WITH GENERICS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Variable number of arguments (varargs) ko generics ke saath use kar sakte ho.
     * Syntax: public <T> methodName(T... elements)
     * 
     * WARNING: Type erasure ke wajah se generic varargs mein complications ho sakte hain.
     * Compiler warning deta hai generally.
     */
    
    // Method jo variable arguments accept kare aur list return kare
    public static <T> List<T> arrayToList(T... elements) {
        List<T> list = new ArrayList<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }
    
    // Method jo varargs ko print kare
    public static <T> void printAll(T... elements) {
        for (T element : elements) {
            System.out.println(element);
        }
    }
    
    // ========================================================================
    // PART 7: TYPE INFERENCE IN GENERIC METHODS
    // ========================================================================
    
    /*
     * CONCEPT:
     * Compiler automatically type parameter ko infer karta hai based on arguments.
     * 
     * Example:
     * printElement("Hello");  // T inferred as String
     * printElement(42);       // T inferred as Integer
     * printElement(3.14);     // T inferred as Double
     * 
     * Agar type infer nahi kar paya, explicitly specify kar sakte ho:
     * Step5_GenericMethods.<String>printElement("Hello");
     */
    
    // Method jo comparison kare (type inference demonstrate)
    public static <T extends Comparable<T>> boolean isGreater(T first, T second) {
        return first.compareTo(second) > 0;
    }
    
    // ========================================================================
    // PART 8: PRACTICAL EXAMPLE - GENERIC UTILITY CLASS
    // ========================================================================
    
    public static class CollectionUtils {
        
        // Find element in list
        public static <T> boolean contains(List<T> list, T element) {
            return list.contains(element);
        }
        
        // Count occurrences of element
        public static <T> int countOccurrences(List<T> list, T element) {
            int count = 0;
            for (T item : list) {
                if (item.equals(element)) {
                    count++;
                }
            }
            return count;
        }
        
        // Find index of element
        public static <T> int indexOf(List<T> list, T element) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(element)) {
                    return i;
                }
            }
            return -1;
        }
        
        // Reverse list
        public static <T> void reverseList(List<T> list) {
            int n = list.size();
            for (int i = 0; i < n / 2; i++) {
                T temp = list.get(i);
                list.set(i, list.get(n - 1 - i));
                list.set(n - 1 - i, temp);
            }
        }
    }
    
    // ========================================================================
    // PART 9: TYPE VARIABLE VS WILDCARD (When to use what?)
    // ========================================================================
    
    /*
     * TYPE VARIABLE (<T>):
     * public static <T> void method(List<T> list) { }
     * 
     * PROS:
     * - Type variable ko use kar sakte ho multiple places pe
     * - More control aur flexibility
     * - Type relationship establish kar sakte ho
     * 
     * CONS:
     * - Type explicitly declare karna padta hai
     * - Complex scenarios ho sakte hain
     * 
     * 
     * WILDCARD (<?>, <? extends>, <? super>):
     * public static void method(List<?> list) { }
     * 
     * PROS:
     * - Simple aur readable
     * - Specific scenarios ke liye better
     * - Type safety
     * 
     * CONS:
     * - Limited flexibility
     * - Type relationship express nahi kar sakte
     * 
     * 
     * RULE OF THUMB:
     * - Agar type ko method ke multiple places pe use karna ho -> TYPE VARIABLE
     * - Agar sirf input accept karna ho -> WILDCARD
     */
}

/*
 * ============================================================================
 * SUMMARY OF STEP 5:
 * ============================================================================
 * 
 * GENERIC METHODS:
 * 1. <T> returnType method(T param) - basic syntax
 * 2. <T extends Bound> - bounded type parameters
 * 3. <T, U, V> - multiple type parameters
 * 4. Type inference - compiler automatically infers type
 * 5. Recursive bounds - <T extends Comparable<T>>
 * 6. Varargs - <T> method(T... elements)
 * 
 * REAL-WORLD USE:
 * - Utility classes aur helper methods
 * - Collections operations
 * - Data conversion methods
 * - API design
 * 
 * NEXT STEP: Tips, tricks, and common mistakes
 * ============================================================================
 */

