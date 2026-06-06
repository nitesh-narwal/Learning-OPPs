package  me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;

/**
 * ==========================================
 * ITERATOR vs FOR LOOPS - The Ultimate Comparison
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Bhai, sabse confusing question: "Iterator use karein ya for loop?"
 * Iss file mein hum SAARE loops ko compare karenge aur samjhenge
 * ki KAHAN KYA USE KARNA HAI!
 * 
 * LOOPS WE'LL COMPARE:
 * ====================
 * 1. Traditional for loop (index-based)
 * 2. Enhanced for loop (for-each)
 * 3. Iterator
 * 4. forEach with Lambda (Java 8+)
 * 
 * DECISION TREE (Kab kya use karein?):
 * ====================================
 * - Element remove karna hai? → Iterator
 * - Index chahiye? → Traditional for loop
 * - Sirf read-only access? → Enhanced for / forEach
 * - Bidirectional traversal? → ListIterator
 * - Performance critical + LinkedList? → Iterator
 * 
 * @author Nitesh Kumar
 * @level Beginner-Intermediate
 */
public class IteratorVsForLoop {
    
    public static void main(String[] args) {
        System.out.println("=== ITERATOR vs FOR LOOPS ===\n");
        
        // Comparison 1: Basic Traversal
        basicTraversalComparison();
        
        // Comparison 2: Element Removal
        elementRemovalComparison();
        
        // Comparison 3: Performance Benchmark
        performanceComparison();
        
        // Comparison 4: When to Use What?
        whenToUseWhat();
        
        // Comparison 5: Modern Java Approaches
        modernJavaApproaches();
    }
    
    /**
     * BASIC TRAVERSAL COMPARISON
     * ==========================
     * Sabhi loops ka basic usage ek saath dekhte hain
     */
    private static void basicTraversalComparison() {
        System.out.println("1. BASIC TRAVERSAL COMPARISON:");
        System.out.println("-".repeat(60));
        
        List<String> languages = new ArrayList<>(
            Arrays.asList("Java", "Python", "JavaScript", "C++", "Go")
        );
        
        // Method 1: Traditional For Loop (Index-based)
        System.out.println("METHOD 1: Traditional For Loop (Index-based)");
        System.out.println("Pros: Index access, forward/backward, modify elements");
        System.out.println("Cons: Verbose, ArrayIndexOutOfBounds risk");
        for(int i = 0; i < languages.size(); i++) {
            System.out.println("  [" + i + "] " + languages.get(i));
        }
        
        System.out.println();
        
        // Method 2: Enhanced For Loop (For-Each)
        System.out.println("METHOD 2: Enhanced For Loop (For-Each) - Java 5+");
        System.out.println("Pros: Clean syntax, safe, readable");
        System.out.println("Cons: No index, can't remove, only forward");
        for(String lang : languages) {
            System.out.println("  Language: " + lang);
        }
        
        System.out.println();
        
        // Method 3: Iterator
        System.out.println("METHOD 3: Iterator");
        System.out.println("Pros: Safe removal, works with all collections");
        System.out.println("Cons: More verbose than for-each");
        Iterator<String> iterator = languages.iterator();
        while(iterator.hasNext()) {
            String lang = iterator.next();
            System.out.println("  Language: " + lang);
        }
        
        System.out.println();
        
        // Method 4: forEach with Lambda (Java 8+)
        System.out.println("METHOD 4: forEach with Lambda - Java 8+");
        System.out.println("Pros: Most concise, functional style");
        System.out.println("Cons: No break/continue, can't remove");
        languages.forEach(lang -> System.out.println("  Language: " + lang));
        
        System.out.println("\n");
    }
    
    /**
     * ELEMENT REMOVAL COMPARISON
     * ===========================
     * Ye sabse important comparison hai!
     * Loop ke beech mein element remove karna is TRICKY! 😱
     */
    private static void elementRemovalComparison() {
        System.out.println("2. ELEMENT REMOVAL - The Game Changer:");
        System.out.println("-".repeat(60));
        
        // Scenario: Remove all even numbers from list
        
        // WRONG WAY 1 ❌: Traditional for loop (forward)
        System.out.println("WRONG ❌: Traditional For Loop (Forward)");
        List<Integer> numbers1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        System.out.println("Before: " + numbers1);
        
        try {
            for(int i = 0; i < numbers1.size(); i++) {
                if(numbers1.get(i) % 2 == 0) {
                    numbers1.remove(i); // BUG! Elements shift left!
                    // After removing index 1 (2), element at index 2 becomes index 1
                    // So we skip checking the next element!
                }
            }
            System.out.println("After: " + numbers1);
            System.out.println("BUG: Number 4 didn't get removed! 🐛");
        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        
        System.out.println();
        
        // WRONG WAY 2 ❌: Enhanced for loop
        System.out.println("WRONG ❌: Enhanced For Loop");
        List<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        System.out.println("Before: " + numbers2);
        
        try {
            for(Integer num : numbers2) {
                if(num % 2 == 0) {
                    numbers2.remove(num); // ConcurrentModificationException!
                }
            }
        } catch(ConcurrentModificationException e) {
            System.out.println("ERROR: " + e.getClass().getSimpleName());
            System.out.println("Reason: Can't modify collection while iterating with for-each!");
        }
        
        System.out.println();
        
        // PARTIALLY CORRECT ⚠️: Traditional for loop (backward)
        System.out.println("PARTIALLY CORRECT ⚠️: Traditional For Loop (Backward)");
        List<Integer> numbers3 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        System.out.println("Before: " + numbers3);
        
        // Backward loop se element shift ka problem solve ho jata hai
        for(int i = numbers3.size() - 1; i >= 0; i--) {
            if(numbers3.get(i) % 2 == 0) {
                numbers3.remove(i);
            }
        }
        System.out.println("After: " + numbers3);
        System.out.println("Works! But not the BEST way...");
        
        System.out.println();
        
        // RIGHT WAY ✅: Iterator
        System.out.println("RIGHT WAY ✅: Iterator.remove()");
        List<Integer> numbers4 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        System.out.println("Before: " + numbers4);
        
        Iterator<Integer> it = numbers4.iterator();
        while(it.hasNext()) {
            Integer num = it.next();
            if(num % 2 == 0) {
                it.remove(); // Safe removal! No exception! 🎉
            }
        }
        System.out.println("After: " + numbers4);
        System.out.println("Perfect! This is the industry standard way! ✨");
        
        /*
         * WHY ITERATOR.REMOVE() IS SAFE?
         * ===============================
         * Iterator internally collection ki state ko track karta hai.
         * Jab tum it.remove() call karte ho, to ye internally:
         * 1. Element ko safely remove karta hai
         * 2. Internal state update karta hai
         * 3. Expected modification count adjust karta hai
         * 
         * Isliye ConcurrentModificationException nahi aata!
         */
        
        System.out.println("\n");
    }
    
    /**
     * PERFORMANCE COMPARISON
     * ======================
     * ArrayList vs LinkedList mein performance ka farak!
     */
    private static void performanceComparison() {
        System.out.println("3. PERFORMANCE BENCHMARK:");
        System.out.println("-".repeat(60));
        
        int SIZE = 10000;
        
        // ArrayList Performance
        System.out.println("ARRAYLIST (Size: " + SIZE + "):");
        List<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i < SIZE; i++) {
            arrayList.add(i);
        }
        
        // Test 1: For loop
        long start = System.nanoTime();
        int sum1 = 0;
        for(int i = 0; i < arrayList.size(); i++) {
            sum1 += arrayList.get(i);
        }
        long end = System.nanoTime();
        System.out.println("  For Loop: " + (end - start) / 1000 + " µs");
        
        // Test 2: Iterator
        start = System.nanoTime();
        int sum2 = 0;
        Iterator<Integer> it1 = arrayList.iterator();
        while(it1.hasNext()) {
            sum2 += it1.next();
        }
        end = System.nanoTime();
        System.out.println("  Iterator: " + (end - start) / 1000 + " µs");
        
        // Test 3: Enhanced for
        start = System.nanoTime();
        int sum3 = 0;
        for(Integer num : arrayList) {
            sum3 += num;
        }
        end = System.nanoTime();
        System.out.println("  Enhanced For: " + (end - start) / 1000 + " µs");
        
        System.out.println("  Result: For ArrayList, all are similar! ✅");
        
        System.out.println();
        
        // LinkedList Performance
        System.out.println("LINKEDLIST (Size: " + SIZE + "):");
        List<Integer> linkedList = new LinkedList<>();
        for(int i = 0; i < SIZE; i++) {
            linkedList.add(i);
        }
        
        // Test 1: For loop (SLOW!)
        start = System.nanoTime();
        sum1 = 0;
        for(int i = 0; i < linkedList.size(); i++) {
            sum1 += linkedList.get(i); // O(n) for each get()!
        }
        end = System.nanoTime();
        System.out.println("  For Loop: " + (end - start) / 1000 + " µs ❌ SLOW!");
        
        // Test 2: Iterator (FAST!)
        start = System.nanoTime();
        sum2 = 0;
        Iterator<Integer> it2 = linkedList.iterator();
        while(it2.hasNext()) {
            sum2 += it2.next(); // O(1) for each next()!
        }
        end = System.nanoTime();
        System.out.println("  Iterator: " + (end - start) / 1000 + " µs ✅ FAST!");
        
        // Test 3: Enhanced for (FAST!)
        start = System.nanoTime();
        sum3 = 0;
        for(Integer num : linkedList) {
            sum3 += num; // Uses Iterator internally!
        }
        end = System.nanoTime();
        System.out.println("  Enhanced For: " + (end - start) / 1000 + " µs ✅ FAST!");
        
        System.out.println("  Result: For LinkedList, Iterator/Enhanced-For >>> For Loop!");
        
        /*
         * INDUSTRY INSIGHT:
         * =================
         * Agar tumhe collection type nahi pata (polymorphism use kar rahe ho),
         * to ALWAYS prefer Iterator ya Enhanced For Loop!
         * 
         * Example: Method signature List<T> le raha hai - could be ArrayList or LinkedList!
         * 
         * public void process(List<String> items) {
         *     // Don't use: for(int i=0; i<items.size(); i++)
         *     // Use: for(String item : items) or Iterator
         * }
         */
        
        System.out.println("\n");
    }
    
    /**
     * WHEN TO USE WHAT?
     * ==================
     * Decision tree with real-world scenarios
     */
    private static void whenToUseWhat() {
        System.out.println("4. WHEN TO USE WHAT? (Decision Guide):");
        System.out.println("-".repeat(60));
        
        System.out.println("Scenario 1: Simple read-only traversal");
        System.out.println("  → Use: Enhanced For Loop (cleanest syntax)");
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        for(String name : names) {
            System.out.println("    Hello, " + name);
        }
        
        System.out.println();
        
        System.out.println("Scenario 2: Need index during iteration");
        System.out.println("  → Use: Traditional For Loop");
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
        for(int i = 0; i < fruits.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + fruits.get(i));
        }
        
        System.out.println();
        
        System.out.println("Scenario 3: Conditional element removal");
        System.out.println("  → Use: Iterator.remove()");
        List<Integer> scores = new ArrayList<>(Arrays.asList(45, 67, 89, 32, 91));
        Iterator<Integer> scoreIt = scores.iterator();
        while(scoreIt.hasNext()) {
            if(scoreIt.next() < 50) {
                scoreIt.remove(); // Remove failing scores
            }
        }
        System.out.println("    Passing scores: " + scores);
        
        System.out.println();
        
        System.out.println("Scenario 4: Functional-style operations");
        System.out.println("  → Use: forEach with Lambda");
        List<String> cities = Arrays.asList("Mumbai", "Delhi", "Bangalore");
        cities.forEach(city -> System.out.println("    City: " + city));
        
        System.out.println();
        
        System.out.println("Scenario 5: Unknown collection type (polymorphism)");
        System.out.println("  → Use: Iterator or Enhanced For");
        processCollection(new ArrayList<>(Arrays.asList(1, 2, 3)));
        processCollection(new LinkedList<>(Arrays.asList(4, 5, 6)));
        
        System.out.println("\n");
    }
    
    // Example of polymorphic method - doesn't know if ArrayList or LinkedList
    private static void processCollection(Collection<Integer> items) {
        System.out.print("    Processing: ");
        // Safe for both ArrayList and LinkedList!
        for(Integer item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
    
    /**
     * MODERN JAVA APPROACHES (Java 8+)
     * =================================
     * Streams aur functional programming ka preview
     */
    private static void modernJavaApproaches() {
        System.out.println("5. MODERN JAVA APPROACHES (Java 8+):");
        System.out.println("-".repeat(60));
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        System.out.println("Task: Filter even numbers and print");
        System.out.println();
        
        // Old Way: Iterator
        System.out.println("OLD WAY (Iterator):");
        Iterator<Integer> it = numbers.iterator();
        while(it.hasNext()) {
            Integer num = it.next();
            if(num % 2 == 0) {
                System.out.print("  " + num);
            }
        }
        System.out.println();
        
        System.out.println();
        
        // New Way: Streams (Java 8+)
        System.out.println("NEW WAY (Streams - Java 8+):");
        System.out.print("  ");
        numbers.stream()
               .filter(num -> num % 2 == 0)
               .forEach(num -> System.out.print(num + " "));
        System.out.println();
        
        /*
         * WHICH IS BETTER?
         * ================
         * - Streams: More declarative, easier to read, chainable operations
         * - Iterator: More control, can break early, traditional approach
         * 
         * INDUSTRY TIP:
         * =============
         * Modern codebases use Streams for most operations.
         * But Iterator is still important for:
         * 1. Element removal during iteration
         * 2. Early termination with complex conditions
         * 3. Performance-critical loops (Streams have overhead)
         * 4. Working with legacy code
         */
        
        System.out.println("\n");
    }
}

/*
 * ==========================================
 * DECISION FLOWCHART (Quick Reference)
 * ==========================================
 * 
 * START
 *   |
 *   ├─ Need to remove elements? ──→ YES ──→ Use Iterator.remove()
 *   |                             ↓
 *   |                            NO
 *   |                             |
 *   ├─ Need index? ───────────→ YES ──→ Use Traditional For Loop
 *   |                             ↓
 *   |                            NO
 *   |                             |
 *   ├─ LinkedList + Large data? → YES ──→ Use Iterator/Enhanced For
 *   |                             ↓
 *   |                            NO
 *   |                             |
 *   ├─ Functional operations? ──→ YES ──→ Use Streams (Java 8+)
 *   |                             ↓
 *   |                            NO
 *   |                             |
 *   └─ Simple traversal ─────────────→ Use Enhanced For Loop
 * 
 * 
 * KEY TAKEAWAYS:
 * ==============
 * 1. Enhanced For Loop: Best for simple read-only traversal
 * 2. Iterator: Best for safe element removal
 * 3. Traditional For Loop: Best when index is needed
 * 4. forEach Lambda: Best for functional-style code
 * 5. NEVER use traditional for loop with LinkedList (performance killer!)
 * 
 * NEXT FILE: 03_IteratorMethods.java
 * (Deep dive into hasNext(), next(), remove(), forEachRemaining())
 */
