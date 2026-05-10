package me.niteshh.OPPs.tutorial.generics.depth;

/*
 * ============================================================================
 * STEP 2: GENERICS WITH CUSTOM CLASSES - BEGINNER TO INTERMEDIATE
 * ============================================================================
 * 
 * Yeh step mein hum dekhenge ki kaise apne custom classes mein generics
 * use kar sakte hain. Real-world applications mein ye bahut common hai.
 * 
 * SCENARIO: Ek generic container banana hai jo kisi bhi type ka data store kar sake
 * ============================================================================
 */

// EXAMPLE 1: Simple Generic Container
public class Step2_GenericContainer<T> {
    
    /*
     * EXPLANATION:
     * Yeh generic container class kisi bhi type T ka data store kar sakta hai.
     * T ek placeholder hai jo creation time pe actual type se replace hota hai.
     */
    
    private T data;
    private String description;
    
    // Constructor
    public Step2_GenericContainer(T data, String description) {
        this.data = data;
        this.description = description;
    }
    
    // Getter for data
    public T getData() {
        return data;
    }
    
    // Method that returns type T
    public T extractData() {
        return data;
    }
    
    // Method that accepts type T
    public void setData(T data) {
        this.data = data;
    }
    
    // Description getter
    public String getDescription() {
        return description;
    }
    
    // ============================================================================
    // MULTIPLE TYPE PARAMETERS
    // ============================================================================
    /*
     * CONCEPT:
     * Ek generic class mein multiple type parameters ho sakte hain.
     * Useful jab aapko different types ko together handle karna ho.
     * 
     * Syntax: public class ClassName<T, U, V> { }
     * 
     * Use cases:
     * 1. Pair<K, V> - Key-Value pairs
     * 2. Triple<A, B, C> - Teen values store karne ke liye
     * 3. Map<K, V> - Dictionary-like structure
     */
}

// EXAMPLE 2: Generic Pair Class (Multiple Type Parameters)
class Step2_GenericPair<T, U> {
    
    /*
     * EXPLANATION:
     * Yeh class do different types T aur U ko store kar sakta hai.
     * Jab object create karte ho: new Step2_GenericPair<String, Integer>("age", 25)
     * Tab T = String aur U = Integer
     */
    
    private T first;
    private U second;
    
    // Constructor
    public Step2_GenericPair(T first, U second) {
        this.first = first;
        this.second = second;
    }
    
    // Getters
    public T getFirst() {
        return first;
    }
    
    public U getSecond() {
        return second;
    }
    
    // Setters
    public void setFirst(T first) {
        this.first = first;
    }
    
    public void setSecond(U second) {
        this.second = second;
    }
    
    // Method na demonstrate kare ki dono types ko use kar sakte hain
    public void displayPair() {
        // first is T type, second is U type
        // Safely use kare sakte ho dono ko
    }
    
    /*
     * PRACTICAL USAGE:
     * 
     * Step2_GenericPair<String, Integer> pair1 
     *     = new Step2_GenericPair<>("Name", 25);
     * String name = pair1.getFirst();  // No casting needed!
     * Integer age = pair1.getSecond();
     * 
     * Step2_GenericPair<Double, Boolean> pair2 
     *     = new Step2_GenericPair<>(3.14, true);
     * Double pi = pair2.getFirst();
     * Boolean flag = pair2.getSecond();
     */
}

// EXAMPLE 3: Generic Response Wrapper (Common in real apps)
class Step2_GenericResponse<T> {
    
    /*
     * REAL-WORLD USAGE:
     * Most REST APIs use generic response wrappers like:
     * 
     * {
     *   "success": true,
     *   "data": { actual data },
     *   "message": "Operation successful"
     * }
     * 
     * Yeh class kisi bhi type ka data wrap kar sakta hai.
     */
    
    private boolean success;
    private T data;
    private String message;
    
    // Constructor
    public Step2_GenericResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
    
    // Getters
    public boolean isSuccess() {
        return success;
    }
    
    public T getData() {
        return data;
    }
    
    public String getMessage() {
        return message;
    }
    
    /*
     * USAGE EXAMPLES:
     * 
     * // String response
     * Step2_GenericResponse<String> strResponse = 
     *     new Step2_GenericResponse<>(true, "Data processed", "Success");
     * 
     * // Integer response
     * Step2_GenericResponse<Integer> intResponse = 
     *     new Step2_GenericResponse<>(true, 42, "Number fetched");
     * 
     * // Custom object response
     * Step2_GenericResponse<User> userResponse = 
     *     new Step2_GenericResponse<>(true, userObject, "User found");
     */
}

// ============================================================================
// EXAMPLE 4: How to NOT use Generics (Common Mistakes)
// ============================================================================

// ❌ MISTAKE: Raw type usage (BAD - generates warnings)
// public class BadExample {
//     private Step2_GenericContainer container;  // Missing <T>
//     // Compiler warning: Raw use of parameterized class
//     // Type safety lost!
// }

// ✅ CORRECT: Always specify type parameter
// public class GoodExample {
//     private Step2_GenericContainer<String> container;  // Proper usage
//     // Type safe!
// }

// ============================================================================
// EXAMPLE 5: Nested Generics (Intermediate)
// ============================================================================

/*
 * CONCEPT:
 * Generic types ko nest kar sakte ho (ek generic mein dusra generic).
 * 
 * Example:
 * List<String> = List of Strings
 * List<List<String>> = List of List of Strings (nested)
 * Step2_GenericPair<String, Step2_GenericContainer<Integer>>
 *     = Pair jisme String aur Integer container hai
 * 
 * Yeh powerful hai but complex bhi ho sakta hai.
 */

class Step2_GenericStorage<T> {
    
    /*
     * EXPLANATION:
     * Yeh class T type ke container ko store kar sakta hai
     * yaani ek generic object ko store kar rahe ho
     */
    
    private Step2_GenericContainer<T> container;
    
    // Constructor
    public Step2_GenericStorage(Step2_GenericContainer<T> container) {
        this.container = container;
    }
    
    // Get the data from container
    public T getStoredData() {
        return container.getData();
    }
    
    /*
     * USAGE:
     * Step2_GenericContainer<String> stringContainer = 
     *     new Step2_GenericContainer<>("Hello", "String data");
     * 
     * Step2_GenericStorage<String> storage = 
     *     new Step2_GenericStorage<>(stringContainer);
     * 
     * String data = storage.getStoredData();
     */
}

// ============================================================================
// UNDERSTANDING: Converting Non-Generic to Generic Class
// ============================================================================

/*
 * SCENARIO:
 * Pehle ek non-generic Box class thi:
 * 
 * public class Box {
 *     private Object item;
 *     
 *     public void setItem(Object item) {
 *         this.item = item;
 *     }
 *     
 *     public Object getItem() {
 *         return item;
 *     }
 * }
 * 
 * PROBLEMS:
 * 1. Type safety nahi hai - koi bhi type add kar sakte ho
 * 2. Casting required hai - Object se actual type mein convert karna pade
 * 3. Runtime errors ho sakte hain - agar galat type cast kare
 * 
 * CONVERSION TO GENERIC:
 * 
 * public class Box<T> {
 *     private T item;
 *     
 *     public void setItem(T item) {
 *         this.item = item;
 *     }
 *     
 *     public T getItem() {
 *         return item;
 *     }
 * }
 * 
 * ab:
 * Box<String> strBox = new Box<>();
 * strBox.setItem("Hello");
 * String content = strBox.getItem();  // No casting needed!
 * 
 * Box<Integer> intBox = new Box<>();
 * intBox.setItem(42);
 * Integer number = intBox.getItem();  // No casting needed!
 */

// ============================================================================
// KEY POINTS TO REMEMBER:
// ============================================================================

/*
 * 1. TYPE PARAMETER CONSTRAINTS:
 *    - Type parameter kisi bhi class type ho sakta hai
 *    - Primitive types nahi (int, double, boolean)
 *    - Primitive ke wrapper use karo (Integer, Double, Boolean)
 * 
 * 2. OBJECT CREATION:
 *    Generic<String> obj = new Generic<>();  // ✅ Correct
 *    Generic<int> obj = new Generic<>();     // ❌ Error! Use Integer
 * 
 * 3. TYPE ERASURE:
 *    Runtime pe T ko Object se replace kiya jaata hai
 *    Compile time pe type information available hota hai
 * 
 * 4. BACKWARD COMPATIBILITY:
 *    Raw type use kar sakte ho (backwards compatible):
 *    Generic obj = new Generic<>();  // Works but warning
 *    Generic<String> obj = new Generic<>();  // Best practice
 * 
 * 5. INHERITANCE WITH GENERICS:
 *    class Child<T> extends Parent<T> { }
 *    class Child<T> extends Parent<String> { }
 *    class Child extends Parent<String> { }
 */


