package me.niteshh.OPPs.tutorial.generics.depth;

import java.util.*;

/*
 * ============================================================================
 * STEP 7: COMMON MISTAKES AND HOW TO AVOID THEM - CRITICAL KNOWLEDGE
 * ============================================================================
 * 
 * Ye file mein typical mistakes explain kiye gaye hain jo developers
 * generics use karte waqt commit karte hain. Production code mein bugs
 * frequently in misunderstandings se aate hain.
 * 
 * IMPORTANT: Ye mistakes seriously lelo! Ye career mein frequently
 * encounter hote hain aur ye waste karti hain valuable debugging time.
 * ============================================================================
 */

public class Step7_CommonMistakes {
    
    // ========================================================================
    // MISTAKE 1: USING RAW TYPES (Type Unsafe)
    // ========================================================================
    
    /*
     * SCENARIO:
     * Junior developer ko generic class ka syntax nahi pata aur raw type use kar deta hai.
     * 
     * ❌ WRONG:
     * 
     * List list = new ArrayList();
     * list.add("Hello");
     * list.add(123);
     * 
     * for (Object obj : list) {
     *     String str = (String) obj;  // ❌ ClassCastException risk!
     *     System.out.println(str);
     * }
     * 
     * PROBLEMS:
     * 1. Type safety lost - compiler warning milega
     * 2. Casting required - runtime error ho sakta hai
     * 3. Hard to maintain - unclear code
     * 4. Future changes mein bugs aasakte hain
     * 
     * 
     * ✅ CORRECT:
     * 
     * List<String> list = new ArrayList<>();
     * list.add("Hello");
     * list.add(123);  // ❌ COMPILE ERROR - type safe!
     * 
     * for (String str : list) {
     *     System.out.println(str);  // No casting needed!
     * }
     */
    
    // ========================================================================
    // MISTAKE 2: MIXING RAW AND TYPED IN SAME CODE
    // ========================================================================
    
    /*
     * SCENARIO:
     * Kuch jagah generics use kiye, kuch jagah raw types.
     * 
     * ❌ WRONG (Inconsistent):
     * 
     * public class BadExample<T> {
     *     private List list;  // Raw type!
     *     
     *     public void add(T item) {
     *         list.add(item);  // Type mismatch warning
     *     }
     *     
     *     public T get() {
     *         return (T) list.get(0);  // Casting required!
     *     }
     * }
     * 
     * CONSEQUENCES:
     * 1. Compiler warnings everywhere
     * 2. Type safety compromised
     * 3. Code confusing aur hard to maintain
     * 4. Bugs easy to introduce
     * 
     * 
     * ✅ CORRECT (Consistent):
     * 
     * public class GoodExample<T> {
     *     private List<T> list = new ArrayList<>();  // Typed!
     *     
     *     public void add(T item) {
     *         list.add(item);  // Clean operation
     *     }
     *     
     *     public T get() {
     *         return list.get(0);  // No casting!
     *     }
     * }
     */
    
    // ========================================================================
    // MISTAKE 3: INCORRECT BOUNDED TYPE PARAMETERS
    // ========================================================================
    
    /*
     * SCENARIO:
     * Bounded type incorrectly specify karte ho, compile error aata hai.
     * 
     * ❌ WRONG:
     * 
     * // Aap chahte ho sirf Number types, lekin String use karte ho
     * public <T extends Number> void process(T value) {
     *     String str = "Number: " + value;
     * }
     * 
     * process("Hello");  // ❌ COMPILE ERROR! String nahi Number extend karta
     * 
     * // Ya galat order mein multiple bounds
     * public <T extends Comparable & Number> void test(T value) {
     *     // ❌ WRONG! Interface pehle nahi ata, class pehle aता है
     * }
     * 
     * 
     * ✅ CORRECT:
     * 
     * public <T extends Number> void process(T value) {
     *     double num = value.doubleValue();  // Safe - T ek Number hai
     * }
     * 
     * process(42);  // ✅ Works! Integer extends Number
     * process(3.14);  // ✅ Works! Double extends Number
     * 
     * // Correct order - class first, then interfaces
     * public <T extends Number & Comparable<T>> void test(T value) {
     *     // ✅ Correct!
     * }
     */
    
    // ========================================================================
    // MISTAKE 4: TYPE ERASURE MISUNDERSTANDING
    // ========================================================================
    
    /*
     * SCENARIO:
     * Developer sochta hai runtime pe type info available hai, lekin nahi hota.
     * 
     * ❌ WRONG (Type Erasure ke wajah se error):
     * 
     * public class Container<T> {
     *     public void printType() {
     *         System.out.println(T);  // ❌ COMPILE ERROR! T syntax nahi hai
     *     }
     *     
     *     public T[] createArray(int size) {
     *         return new T[size];  // ❌ COMPILE ERROR! Cannot instantiate T
     *     }
     *     
     *     public void checkType(Object obj) {
     *         if (obj instanceof Container<String>) {  // ❌ COMPILE ERROR!
     *             // Cannot use type parameter in instanceof
     *         }
     *     }
     * }
     * 
     * 
     * ✅ CORRECT (Type info kaise preserve karo):
     * 
     * public class Container<T> {
     *     private Class<T> type;
     *     
     *     public Container(Class<T> type) {
     *         this.type = type;  // Type info pass karo explicitly
     *     }
     *     
     *     public void printType() {
     *         System.out.println(type.getName());  // ✅ Works!
     *     }
     *     
     *     public T[] createArray(int size) {
     *         @SuppressWarnings("unchecked")
     *         T[] array = (T[]) java.lang.reflect.Array.newInstance(type, size);
     *         return array;  // ✅ Works with reflection!
     *     }
     *     
     *     public void checkType(Object obj) {
     *         if (obj.getClass() == type) {  // ✅ Works!
     *             // Type check
     *         }
     *     }
     * }
     */
    
    // ========================================================================
    // MISTAKE 5: WILDCARD MISUSE - UPPER vs LOWER BOUND
    // ========================================================================
    
    /*
     * SCENARIO:
     * Wildcard ka galat usage se type error aata hai runtime/compile time pe.
     * 
     * ❌ WRONG (Upper bound use karte hue adding):
     * 
     * List<? extends Number> numbers = new ArrayList<Integer>();
     * numbers.add(42);  // ❌ COMPILE ERROR!
     * // Kyu? <? extends Number> means read-only from Number's perspective
     * // Add sirf Number ya uska parent (Object) ke paas ho sakta hai
     * // Lekin compiler nahi janta ki exact type kya hai
     * 
     * ❌ WRONG (Lower bound use karte hue reading):
     * 
     * List<? super Integer> list = new ArrayList<Number>();
     * Integer num = list.get(0);  // ❌ Type mismatch!
     * // Kyu? List mein Number ya Object ho sakta hai
     * // Integer nahi guaranteed hai
     * 
     * 
     * ✅ CORRECT (PECS - Producer Extends, Consumer Super):
     * 
     * // READING from list: Use extends
     * List<? extends Number> numbers = new ArrayList<Double>();
     * for (Number n : numbers) {
     *     double value = n.doubleValue();  // ✅ Safe read
     * }
     * 
     * // ADDING to list: Use super
     * List<? super Integer> list = new ArrayList<Number>();
     * list.add(42);  // ✅ Safe add
     */
    
    // ========================================================================
    // MISTAKE 6: GENERIC TYPE PARAMETER CONSTRAINTS IGNORED
    // ========================================================================
    
    /*
     * SCENARIO:
     * Generic class likh diya but type parameter ke constraints nahi socha.
     * 
     * ❌ WRONG (No constraints):
     * 
     * public class DataStore<T> {
     *     private Map<String, T> store = new HashMap<>();
     *     
     *     public void persist(String key, T value) {
     *         // Lekin T kisi bhi type ka ho sakta hai - serializable nahi
     *         // Database mein save karte waqt error aasakte hain
     *     }
     * }
     * 
     * DataStore<Thread> store = new DataStore<>();  // ❌ Thread serializable nahi!
     * store.persist("key", new Thread());  // Runtime error!
     * 
     * 
     * ✅ CORRECT (Constraints define):
     * 
     * public interface Persistable {
     *     String serialize();
     *     T deserialize(String data);
     * }
     * 
     * public class DataStore<T extends Persistable> {
     *     private Map<String, T> store = new HashMap<>();
     *     
     *     public void persist(String key, T value) {
     *         // Ab T guaranteed serializable hai
     *         String serialized = value.serialize();  // ✅ Safe!
     *     }
     * }
     * 
     * DataStore<User> store = new DataStore<>();  // ✅ User is Persistable
     */
    
    // ========================================================================
    // MISTAKE 7: GENERIC INHERITANCE CONFUSION
    // ========================================================================
    
    /*
     * SCENARIO:
     * Generic class ko extend karte waqt type parameter sahi se pass nahi karते.
     * 
     * ❌ WRONG:
     * 
     * public class Parent<T> {
     *     private T value;
     * }
     * 
     * // Child class ko type parameter pass nahi kiya
     * public class Child extends Parent {  // ❌ Raw type!
     *     // T undefined hoga
     * }
     * 
     * // Ya galat type pass kiya
     * public class StringChild extends Parent<Integer> {  // ❌ Confusing!
     *     // Parent expects Integer but IntStream String return karega?
     * }
     * 
     * 
     * ✅ CORRECT:
     * 
     * // Option 1: Type parameter inherit karo
     * public class Child<T> extends Parent<T> {
     *     // Ab Child bhi generic hai
     * }
     * 
     * // Option 2: Specific type define karo
     * public class StringChild extends Parent<String> {
     *     private String value;  // Clear ki String type hai
     * }
     * 
     * // Option 3: Multi-level generics
     * public class Container<T> extends Parent<T> {
     *     // Flexibility maintain
     * }
     */
    
    // ========================================================================
    // MISTAKE 8: GENERIC ARRAYS (Most Common Confusion)
    // ========================================================================
    
    /*
     * SCENARIO:
     * Generic array create karne ka try karte ho. Type erasure ke wajah se
     * ye complicated hota hai.
     * 
     * ❌ WRONG (Direct array creation):
     * 
     * public class GenericArray<T> {
     *     private T[] array;
     *     
     *     public GenericArray(int size) {
     *         this.array = new T[size];  // ❌ COMPILE ERROR!
     *         // Runtime pe T known nahi hota
     *     }
     * }
     * 
     * ❌ WRONG (Unsafe casting):
     * 
     * T[] array = (T[]) new Object[size];  // ❌ Warning! Type unsafe!
     * 
     * 
     * ✅ CORRECT (Using Class<?> for type info):
     * 
     * public class GenericArray<T> {
     *     private T[] array;
     *     
     *     public GenericArray(Class<T> type, int size) {
     *         // Reflection use karte hain type info get karne ke liye
     *         @SuppressWarnings("unchecked")
     *         T[] temp = (T[]) java.lang.reflect.Array
     *             .newInstance(type, size);
     *         this.array = temp;
     *     }
     * }
     * 
     * // Usage:
     * GenericArray<String> strArr = new GenericArray<>(String.class, 10);
     * 
     * ✅ BETTER ALTERNATIVE (List use karo):
     * 
     * public class GenericList<T> {
     *     private List<T> list = new ArrayList<>();  // No array issues!
     *     
     *     public void add(T element) {
     *         list.add(element);
     *     }
     * }
     * 
     * Ye zyada safe aur simple hai!
     */
    
    // ========================================================================
    // MISTAKE 9: GENERIC METHOD TYPE PARAMETER SHADOWS CLASS TYPE PARAMETER
    // ========================================================================
    
    /*
     * SCENARIO:
     * Generic method aur generic class dono mein same name type parameter,
     * confusion create ho sakta hai.
     * 
     * ❌ CONFUSING:
     * 
     * public class Box<T> {
     *     private T item;
     *     
     *     // Method ka apna <T> hai, class ka <T> nahi
     *     public <T> void confusing(T parameter) {
     *         // Is T method ke scope mein hai, class ke T nahi
     *         // Confusing! Alag-alag T ho rahe hain
     *     }
     * }
     * 
     * 
     * ✅ CLEAR:
     * 
     * public class Box<T> {
     *     private T item;
     *     
     *     // Method ka alag name, class ka alag
     *     public <U> void process(U parameter) {
     *         // Clear hai ki U method-specific hai
     *         // T class-level hai, U method-level hai
     *     }
     * }
     */
    
    // ========================================================================
    // MISTAKE 10: NO TYPE CHECKING IN COLLECTIONS
    // ========================================================================
    
    /*
     * SCENARIO:
     * Raw collections use karte hain, phir type casting errors.
     * 
     * ❌ LEGACY CODE (Pre-generics):
     * 
     * public class UserManager {
     *     private List users = new ArrayList();  // Raw type!
     *     
     *     public void addUser(User user) {
     *         users.add(user);  // Type mismatch warning
     *     }
     *     
     *     public User getUser(int index) {
     *         return (User) users.get(index);  // ❌ ClassCastException risk!
     *     }
     * }
     * 
     * 
     * ✅ MODERN CODE (With generics):
     * 
     * public class UserManager {
     *     private List<User> users = new ArrayList<>();  // Typed!
     *     
     *     public void addUser(User user) {
     *         users.add(user);  // Type safe
     *     }
     *     
     *     public User getUser(int index) {
     *         return users.get(index);  // No casting! Type safe!
     *     }
     * }
     */
    
    // ========================================================================
    // MISTAKE 11: NOT USING @SuppressWarnings PROPERLY
    // ========================================================================
    
    /*
     * SCENARIO:
     * Type erasure ke wajah se kuch legitimate warnings hote hain.
     * Inhe properly suppress karna chahiye.
     * 
     * ❌ WRONG (No suppression):
     * 
     * public class GenericArray<T> {
     *     public T[] createArray(int size) {
     *         T[] array = (T[]) new Object[size];  // ⚠️ Warning ignored!
     *         return array;
     *     }
     * }
     * 
     * ❌ WRONG (Suppress all):
     * 
     * @SuppressWarnings("all")  // ❌ Too broad!
     * public class GenericArray<T> { }
     * 
     * 
     * ✅ CORRECT (Specific suppress):
     * 
     * public class GenericArray<T> {
     *     @SuppressWarnings("unchecked")  // ✅ Specific warning
     *     public T[] createArray(int size) {
     *         T[] array = (T[]) new Object[size];
     *         return array;
     *     }
     * }
     */
    
    // ========================================================================
    // MISTAKE 12: OVERLOADING GENERIC METHODS
    // ========================================================================
    
    /*
     * SCENARIO:
     * Generic methods ko overload karte waqt type erasure confusion paida hota hai.
     * 
     * ❌ WRONG (Type erasure ke wajah se duplicate):
     * 
     * public class MyClass {
     *     public <T> void print(T value) {
     *         System.out.println("Generic: " + value);
     *     }
     *     
     *     public <T> void print(List<T> list) {  // ❌ Compile error!
     *         // Type erasure ke baad dono method signature same ho jaate hain
     *         // (List becomes List, Object becomes Object)
     *         System.out.println("List: " + list);
     *     }
     * }
     * 
     * 
     * ✅ CORRECT (Different method names ya different parameters):
     * 
     * public class MyClass {
     *     public <T> void print(T value) {
     *         System.out.println("Generic: " + value);
     *     }
     *     
     *     public <T> void printList(List<T> list) {  // ✅ Different name!
     *         System.out.println("List: " + list);
     *     }
     * }
     */
}

/*
 * ============================================================================
 * SUMMARY OF STEP 7 - CRITICAL MISTAKES:
 * ============================================================================
 * 
 * TOP 12 MISTAKES:
 * 1. Using raw types (Type unsafe)
 * 2. Mixing raw and typed code (Inconsistent)
 * 3. Incorrect bounded type parameters (Compile errors)
 * 4. Type erasure misunderstanding (Runtime issues)
 * 5. Wildcard misuse (Logic errors)
 * 6. No constraints on type parameters (Type safety)
 * 7. Generic inheritance confusion (Undefined behavior)
 * 8. Generic arrays (Most common problem)
 * 9. Shadowing type parameters (Confusing code)
 * 10. No type checking in collections (ClassCastException)
 * 11. Wrong @SuppressWarnings usage (Bad practice)
 * 12. Overloading generic methods (Type erasure issues)
 * 
 * PREVENTION TIPS:
 * - Always use typed generics, never raw types
 * - Understand type erasure deeply
 * - Follow PECS principle strictly
 * - Use bounded types for constraints
 * - Keep code consistent (all typed or all raw)
 * - Prefer List over arrays in generics
 * - Document generic constraints clearly
 * - Use IDE's warnings as guide
 * 
 * NEXT STEP: MainDemo with practical examples
 * ============================================================================
 */

