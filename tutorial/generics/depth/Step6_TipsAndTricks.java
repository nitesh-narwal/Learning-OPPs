package me.niteshh.OPPs.tutorial.generics.depth;

import java.util.*;

/*
 * ============================================================================
 * STEP 6: TIPS AND TRICKS FOR EFFECTIVE GENERICS - PRACTICAL WISDOM
 * ============================================================================
 * 
 * Ye file mein production-level code mein generics ko effectively use karne
 * ke liye practical tips aur tricks hain. Real-world scenarios mein ye
 * definitely help karega.
 * ============================================================================
 */

public class Step6_TipsAndTricks {
    
    // ========================================================================
    // TIP 1: USE GENERICS TO ELIMINATE CASTING AND SUPPRESS WARNINGS
    // ========================================================================
    
    /*
     * ❌ BAD PRACTICE (Casting needed):
     * 
     * public class OldWay {
     *     private Object value;
     *     
     *     public void setValue(Object value) {
     *         this.value = value;
     *     }
     *     
     *     public String getValue() {
     *         return (String) value;  // Casting required - risky!
     *     }
     * }
     * 
     * 
     * ✅ GOOD PRACTICE (No casting):
     * 
     * public class NewWay<T> {
     *     private T value;
     *     
     *     public void setValue(T value) {
     *         this.value = value;
     *     }
     *     
     *     public T getValue() {
     *         return value;  // No casting needed!
     *     }
     * }
     */
    
    // Practical example: Generic wrapper
    public static class SafeBox<T> {
        private T value;
        
        public void put(T value) {
            this.value = value;
        }
        
        public T get() {
            return value;  // Type-safe return
        }
        
        // No casting required anywhere!
    }
    
    // ========================================================================
    // TIP 2: LEVERAGE TYPE ERASURE CAREFULLY
    // ========================================================================
    
    /*
     * TYPE ERASURE = Runtime pe type information erase ho jaati hai
     * 
     * ❌ CANNOT DO (Type erasure ke wajah se):
     * 
     * if (obj instanceof Box<String>) { }     // Compile error!
     * new T[10]                              // Compile error!
     * throw new T("message")                 // Compile error!
     * 
     * 
     * ✅ CAN DO (Type erasure ke baavjood):
     * 
     * if (obj instanceof Box) { }             // Works - raw type check
     * new Object[10]                         // Works - create Object array
     * Generic<String> obj = new Generic<>(); // Works - type at compile time
     */
    
    // Workaround for type-specific operations
    public static class GenericArray<T> {
        private T[] array;
        
        // Constructor with Class parameter for type info
        public GenericArray(Class<T> type, int size) {
            // Use reflection to create typed array
            @SuppressWarnings("unchecked")
            T[] temp = (T[]) new Object[size];
            array = temp;
        }
        
        public T get(int index) {
            return array[index];
        }
        
        public void set(int index, T element) {
            array[index] = element;
        }
        
        /*
         * EXPLANATION:
         * Array<String> arr = new Array<>(String.class, 10);
         * Constructor mein String.class pass karte hain
         * Ye runtime pe type information provide karta hai
         * Reflection use karke typed array create kar sakte hain
         */
    }
    
    // ========================================================================
    // TIP 3: USE BOUNDED TYPE PARAMETERS FOR BETTER CONTROL
    // ========================================================================
    
    /*
     * Bounded types se zyada specific aur safe code likha ja sakta hai.
     * 
     * ❌ TOO GENERIC (Nahi kya karna hai):
     * 
     * public <T> void process(T item) {
     *     // T kuch bhi ho sakta hai - kya operations kar sakte ho?
     * }
     * 
     * 
     * ✅ BOUNDED (Clear intent):
     * 
     * public <T extends Number> double process(T item) {
     *     return item.doubleValue();  // Clear ki Number type hi chahiye
     * }
     */
    
    // Practical example: Entity repository
    public interface Entity {
        Long getId();
        void setId(Long id);
    }
    
    public static class BaseRepository<T extends Entity> {
        
        public void validate(T entity) {
            // T definitely Entity implement karta hai
            // getId(), setId() methods available hain
            if (entity.getId() == null) {
                entity.setId(System.currentTimeMillis());
            }
        }
        
        // Ab specific methods likha ja sakta hai confidently
    }
    
    // ========================================================================
    // TIP 4: PECS - PRODUCER EXTENDS, CONSUMER SUPER
    // ========================================================================
    
    /*
     * GOLDEN RULE:
     * 
     * READING DATA (Producer):
     * public void display(List<? extends Number> list) {
     *     for (Number n : list) {
     *         System.out.println(n);  // READING
     *     }
     * }
     * 
     * ADDING DATA (Consumer):
     * public void fill(List<? super Integer> list) {
     *     list.add(42);  // ADDING
     * }
     */
    
    public static class DataProcessor {
        
        // Read from list of numbers
        public double processNumbers(List<? extends Number> numbers) {
            double sum = 0;
            for (Number n : numbers) {
                sum += n.doubleValue();
            }
            return sum;
        }
        
        // Add integers to object list
        public void fillWithIntegers(List<? super Integer> list) {
            for (int i = 0; i < 5; i++) {
                list.add(i * 10);
            }
        }
    }
    
    // ========================================================================
    // TIP 5: AVOID RAW TYPES - ALWAYS USE TYPED GENERICS
    // ========================================================================
    
    /*
     * ❌ RAW TYPE (Warning - Type unsafe):
     * 
     * List list = new ArrayList();      // Raw type - warning!
     * Box box = new Box();              // Raw type - warning!
     * 
     * 
     * ✅ CORRECT:
     * 
     * List<String> list = new ArrayList<>();  // Typed
     * Box<String> box = new Box<>();          // Typed
     * 
     * 
     * RAW TYPES SE PROBLEMS:
     * 1. Type safety lost
     * 2. Casting required on retrieval
     * 3. Compiler warnings
     * 4. Runtime errors possible
     */
    
    // ========================================================================
    // TIP 6: USE DIAMOND OPERATOR (<>)
    // ========================================================================
    
    /*
     * JAVA 7+ mein diamond operator use kar sakte ho.
     * 
     * ❌ OLD WAY:
     * List<String> list = new ArrayList<String>();
     * 
     * ✅ NEW WAY:
     * List<String> list = new ArrayList<>();
     * 
     * Compiler automatically type infer karta hai right side se.
     * Cleaner aur shorter code!
     */
    
    public static class DiamondOperatorExample {
        
        public void demonstrate() {
            // Type explicitly left side mein, right side mein diamond
            Map<String, Integer> map = new HashMap<>();
            List<String> list = new ArrayList<>();
            Set<Double> set = new HashSet<>();
            
            // Cleaner aur readable!
        }
    }
    
    // ========================================================================
    // TIP 7: CREATE GENERIC UTILITY CLASSES FOR REUSABILITY
    // ========================================================================
    
    /*
     * Generic utility classes ek baar likha ja sakta hai
     * aur har jagah reuse kiya ja sakta hai.
     * 
     * Real-world examples:
     * Apache Commons, Google Guava, Spring Framework
     * Sab heavily generic utilities use karte hain.
     */
    
    public static class GenericPair<K, V> {
        private K key;
        private V value;
        
        public GenericPair(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() { return key; }
        public V getValue() { return value; }
        
        // Aur sab jagah use kar sakte ho jo pair store karna ho
    }
    
    public static class GenericCache<K, V> {
        private Map<K, V> cache = new HashMap<>();
        
        public void put(K key, V value) {
            cache.put(key, value);
        }
        
        public V get(K key) {
            return cache.get(key);
        }
        
        public void clear() {
            cache.clear();
        }
    }
    
    // ========================================================================
    // TIP 8: USE WILDCARDS FOR SIMPLE CASES, GENERICS FOR COMPLEX
    // ========================================================================
    
    /*
     * SIMPLE CASE - WILDCARD:
     * public void displayList(List<?> list) {
     *     // Simple - just display, no complex operations
     * }
     * 
     * 
     * COMPLEX CASE - GENERICS:
     * public <T> List<T> transformList(List<T> source, Function<T, T> transformer) {
     *     // Complex - need type info for operations
     * }
     */
    
    // ========================================================================
    // TIP 9: LEVERAGE TYPE INFERENCE
    // ========================================================================
    
    /*
     * Compiler automatically type parameter infer kar sakta hai.
     * 
     * Explicit specify karne ki zaroorat nahi:
     * 
     * // Compiler infers T as String
     * String result = processString("Hello");
     * 
     * // Compiler infers T as Integer
     * Integer result = processNumber(42);
     */
    
    public static <T> void printType(T value) {
        System.out.println("Type: " + value.getClass().getSimpleName());
    }
    
    // ========================================================================
    // TIP 10: DOCUMENT GENERICS CLEARLY
    // ========================================================================
    
    /*
     * Generic code likha tha toh clear documentation likho:
     * 
     * javadoc mein type parameter explain karo:
     * 
     * /**
     *  * Generic container for storing values
     *  * 
     *  * @param <T> The type of value to store
     *  * 
     * public class Container<T> { }
     */
    
    /**
     * Generic response wrapper for API responses
     * 
     * @param <T> The type of data in response
     * 
     * Type T can be String, Number, Custom Object, Collection, etc.
     */
    public static class ApiResponse<T> {
        private boolean success;
        private T data;
        private String message;
        
        public ApiResponse(boolean success, T data, String message) {
            this.success = success;
            this.data = data;
            this.message = message;
        }
        
        public boolean isSuccess() { return success; }
        public T getData() { return data; }
        public String getMessage() { return message; }
    }
    
    // ========================================================================
    // TIP 11: AVOID MIXING RAW AND TYPED IN SAME CLASS
    // ========================================================================
    
    /*
     * ❌ INCONSISTENT:
     * 
     * public class Inconsistent<T> {
     *     private List list;  // Raw type!
     *     
     *     public void add(T item) {
     *         list.add(item);  // Mixing raw and typed
     *     }
     * }
     * 
     * 
     * ✅ CONSISTENT:
     * 
     * public class Consistent<T> {
     *     private List<T> list;  // Typed
     *     
     *     public void add(T item) {
     *         list.add(item);  // Consistent
     *     }
     * }
     */
    
    // ========================================================================
    // TIP 12: USE GENERICS WITH STREAMS (JAVA 8+)
    // ========================================================================
    
    /*
     * Modern Java mein generic methods with streams:
     * 
     * public <T> List<T> filterList(List<T> list, Predicate<T> predicate) {
     *     return list.stream()
     *         .filter(predicate)
     *         .collect(Collectors.toList());
     * }
     * 
     * Very powerful aur expressive!
     */
    
    public static <T> List<T> filterList(List<T> list, java.util.function.Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
}

/*
 * ============================================================================
 * SUMMARY OF TIP 6:
 * ============================================================================
 * 
 * KEY TIPS:
 * 1. Eliminate casting with proper generics
 * 2. Understand type erasure limitations
 * 3. Use bounded types for better control
 * 4. Follow PECS principle religiously
 * 5. Avoid raw types always
 * 6. Use diamond operator for clean code
 * 7. Create reusable generic utilities
 * 8. Use wildcards for simple, generics for complex
 * 9. Leverage type inference
 * 10. Document generics clearly
 * 11. Keep code consistent (all typed or all raw)
 * 12. Combine generics with modern Java features
 * 
 * NEXT STEP: Common mistakes and how to avoid them
 * ============================================================================
 */

