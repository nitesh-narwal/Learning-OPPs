    package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ==========================================
 * FAIL-FAST vs FAIL-SAFE ITERATORS
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Bhai, ye sabse CONFUSING topic hai iterators mein! 😵
 * Kabhi kabhi loop ke beech mein ConcurrentModificationException aata hai -
 * iska reason samajhna bohot zaroori hai!
 * 
 * FAIL-FAST:
 * ==========
 * Agar koi collection ko modify kare (without iterator.remove()),
 * to IMMEDIATELY exception throw kar do! 💥
 * 
 * Examples: ArrayList, HashMap, HashSet, LinkedList
 * 
 * FAIL-SAFE:
 * ==========
 * Collection ki COPY pe kaam karo, to koi exception nahi!
 * Original collection modify ho bhi to chalega! 😎
 * 
 * Examples: CopyOnWriteArrayList, ConcurrentHashMap
 * 
 * REAL-WORLD ANALOGY:
 * ===================
 * Fail-Fast = Photo khinchte time koi hila to "BLUR! Dobara lo!"
 * Fail-Safe = Pehle photo khicho, fir original mein kuch bhi karo!
 * 
 * @author Nitesh Kumar
 * @level Intermediate-Advanced
 */
public class FailFast_FailSafe {
    
    public static void main(String[] args) {
        System.out.println("=== FAIL-FAST vs FAIL-SAFE ITERATORS ===\n");
        
        // Understanding Fail-Fast
        failFastDemo();
        
        // Why ConcurrentModificationException?
        whyConcurrentModificationException();
        
        // Fail-Safe demo
        failSafeDemo();
        
        // How to avoid CME?
        howToAvoidCME();
        
        // Multi-threading scenarios
        multiThreadingScenarios();
        
        // Comparison table
        comparisonTable();
    }
    
    /**
     * FAIL-FAST DEMO
     * ==============
     * Dekho kaise exception aata hai!
     */
    private static void failFastDemo() {
        System.out.println("1. FAIL-FAST ITERATOR DEMO:");
        System.out.println("-".repeat(60));
        
        // Scenario 1: Modifying during enhanced for loop ❌
        System.out.println("SCENARIO 1: Enhanced For Loop + collection.remove()");
        List<String> fruits = new ArrayList<>(
            Arrays.asList("Apple", "Banana", "Cherry", "Date")
        );
        
        System.out.println("Original: " + fruits);
        System.out.println("Trying to remove 'Banana' during iteration...");
        
        try {
            for(String fruit : fruits) {
                System.out.println("  Current: " + fruit);
                if(fruit.equals("Banana")) {
                    fruits.remove(fruit); // BOOM! 💥
                }
            }
        } catch(ConcurrentModificationException e) {
            System.out.println("  ❌ ConcurrentModificationException!");
            System.out.println("  Reason: Modified collection during enhanced for loop");
        }
        
        System.out.println();
        
        // Scenario 2: Manual iterator + collection.remove() ❌
        System.out.println("SCENARIO 2: Iterator + collection.remove()");
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        
        System.out.println("Original: " + numbers);
        
        try {
            Iterator<Integer> it = numbers.iterator();
            while(it.hasNext()) {
                Integer num = it.next();
                System.out.println("  Current: " + num);
                
                if(num == 3) {
                    numbers.remove(num); // Using collection.remove() - BAD!
                }
            }
        } catch(ConcurrentModificationException e) {
            System.out.println("  ❌ ConcurrentModificationException!");
            System.out.println("  Reason: Used collection.remove() instead of iterator.remove()");
        }
        
        System.out.println();
        
        // Scenario 3: Correct way - iterator.remove() ✅
        System.out.println("SCENARIO 3: Correct Way - iterator.remove()");
        List<String> colors = new ArrayList<>(
            Arrays.asList("Red", "Green", "Blue", "Yellow")
        );
        
        System.out.println("Original: " + colors);
        
        Iterator<String> colorIt = colors.iterator();
        while(colorIt.hasNext()) {
            String color = colorIt.next();
            if(color.equals("Green")) {
                colorIt.remove(); // Using iterator.remove() - GOOD! ✅
                System.out.println("  Removed: " + color);
            }
        }
        
        System.out.println("Final: " + colors);
        System.out.println("✅ No exception! Safe removal!");
        
        /*
         * FAIL-FAST MECHANISM:
         * ====================
         * 
         * Internally, collection ek "modCount" maintain karta hai:
         * - Jab bhi add/remove hota hai, modCount++
         * - Iterator apna "expectedModCount" store karta hai
         * - Har next() call pe check: modCount == expectedModCount?
         * - Agar different hai, to ConcurrentModificationException!
         * 
         * PSEUDOCODE:
         * -----------
         * class ArrayList {
         *     int modCount = 0;
         *     
         *     void add(E e) {
         *         // add logic
         *         modCount++;
         *     }
         * }
         * 
         * class IteratorImpl {
         *     int expectedModCount = list.modCount;
         *     
         *     E next() {
         *         if(list.modCount != expectedModCount) {
         *             throw new ConcurrentModificationException();
         *         }
         *         // return element
         *     }
         * }
         */
        
        System.out.println("\n");
    }
    
    /**
     * WHY CONCURRENTMODIFICATIONEXCEPTION?
     * ====================================
     * Design decision ki deep explanation
     */
    private static void whyConcurrentModificationException() {
        System.out.println("2. WHY ConcurrentModificationException EXISTS?");
        System.out.println("-".repeat(60));
        
        System.out.println("REASON 1: Data Consistency 🎯");
        System.out.println("  Problem: Agar iteration ke beech mein collection change ho,");
        System.out.println("  to unpredictable behavior ho sakta hai!");
        
        List<Integer> demo = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\n  Example: " + demo);
        System.out.println("  Iteration: 1, 2, remove(2), ??? (3 skip ho jayega!)");
        
        System.out.println();
        
        System.out.println("REASON 2: Multi-threading Safety 🔐");
        System.out.println("  Agar ek thread iterate kar rahi hai aur dusri modify kar rahi hai,");
        System.out.println("  to immediately pata chal jaye ki something is wrong!");
        
        System.out.println();
        
        System.out.println("REASON 3: Fail-Fast Philosophy 💡");
        System.out.println("  \"Silent failure se better hai loud crash!\"");
        System.out.println("  Bug turant pakad mein aa jata hai, production mein nahi!");
        
        System.out.println();
        
        System.out.println("DESIGN DECISION:");
        System.out.println("  Java designers ne socha: Better to throw exception early");
        System.out.println("  than to have subtle bugs that are hard to debug!");
        
        /*
         * INDUSTRY PERSPECTIVE:
         * =====================
         * 
         * Fail-Fast approach is actually GOOD for:
         * 1. Early bug detection during development
         * 2. Preventing data corruption
         * 3. Making threading issues visible
         * 
         * BUT developers often get confused initially!
         * Solution: Use iterator.remove() or fail-safe collections
         */
        
        System.out.println("\n");
    }
    
    /**
     * FAIL-SAFE DEMO
     * ==============
     * Collections that DON'T throw ConcurrentModificationException
     */
    private static void failSafeDemo() {
        System.out.println("3. FAIL-SAFE ITERATOR DEMO:");
        System.out.println("-".repeat(60));
        
        // Example 1: CopyOnWriteArrayList
        System.out.println("EXAMPLE 1: CopyOnWriteArrayList");
        List<String> safeList = new CopyOnWriteArrayList<>(
            Arrays.asList("Java", "Python", "C++", "JavaScript")
        );
        
        System.out.println("Original: " + safeList);
        System.out.println("Modifying during iteration...");
        
        try {
            for(String lang : safeList) {
                System.out.println("  Current: " + lang);
                
                if(lang.equals("Python")) {
                    safeList.remove(lang); // No exception! ✅
                    System.out.println("    → Removed: " + lang);
                }
                
                if(lang.equals("C++")) {
                    safeList.add("Go"); // Adding also works!
                    System.out.println("    → Added: Go");
                }
            }
            
            System.out.println("✅ No ConcurrentModificationException!");
            System.out.println("Final: " + safeList);
            
        } catch(ConcurrentModificationException e) {
            System.out.println("❌ Exception occurred (shouldn't happen!)");
        }
        
        System.out.println();
        
        // Example 2: ConcurrentHashMap
        System.out.println("EXAMPLE 2: ConcurrentHashMap");
        Map<String, Integer> safeMap = new ConcurrentHashMap<>();
        safeMap.put("Apple", 10);
        safeMap.put("Banana", 20);
        safeMap.put("Cherry", 30);
        
        System.out.println("Original: " + safeMap);
        System.out.println("Modifying during iteration...");
        
        try {
            Iterator<Map.Entry<String, Integer>> it = safeMap.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                System.out.println("  " + entry.getKey() + " = " + entry.getValue());
                
                if(entry.getKey().equals("Banana")) {
                    safeMap.put("Date", 40); // Adding during iteration!
                    System.out.println("    → Added: Date");
                }
            }
            
            System.out.println("✅ No exception!");
            System.out.println("Final: " + safeMap);
            
        } catch(ConcurrentModificationException e) {
            System.out.println("❌ Exception (shouldn't happen!)");
        }
        
        /*
         * HOW FAIL-SAFE WORKS?
         * =====================
         * 
         * CopyOnWriteArrayList:
         * - Iterator works on a SNAPSHOT (copy) of data
         * - Original list modify ho to iterator ko pata hi nahi chalta!
         * - Write operations costly hain (full array copy!)
         * 
         * ConcurrentHashMap:
         * - Internal structure allows concurrent reads/writes
         * - Segments/buckets separate locked hote hain
         * - Multiple threads safely kaam kar sakte hain
         * 
         * TRADE-OFF:
         * ==========
         * Fail-Safe = Safety ✅ but Performance cost ⚠️
         */
        
        System.out.println("\n");
    }
    
    /**
     * HOW TO AVOID CONCURRENTMODIFICATIONEXCEPTION?
     * ==============================================
     * Practical solutions!
     */
    private static void howToAvoidCME() {
        System.out.println("4. HOW TO AVOID ConcurrentModificationException?");
        System.out.println("-".repeat(60));
        
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        
        // Solution 1: Use iterator.remove() ✅
        System.out.println("SOLUTION 1: Use iterator.remove()");
        List<Integer> copy1 = new ArrayList<>(numbers);
        System.out.println("Before: " + copy1);
        
        Iterator<Integer> it1 = copy1.iterator();
        while(it1.hasNext()) {
            if(it1.next() % 2 == 0) {
                it1.remove(); // Safe removal
            }
        }
        System.out.println("After: " + copy1);
        System.out.println("✅ Best for removal operations");
        
        System.out.println();
        
        // Solution 2: Collect items to remove, then remove ✅
        System.out.println("SOLUTION 2: Two-pass approach (Collect + Remove)");
        List<Integer> copy2 = new ArrayList<>(numbers);
        List<Integer> toRemove = new ArrayList<>();
        
        System.out.println("Before: " + copy2);
        
        // Pass 1: Collect items to remove
        for(Integer num : copy2) {
            if(num % 2 == 0) {
                toRemove.add(num);
            }
        }
        
        // Pass 2: Remove them
        copy2.removeAll(toRemove);
        
        System.out.println("After: " + copy2);
        System.out.println("✅ Good for complex conditions");
        
        System.out.println();
        
        // Solution 3: Use removeIf() - Java 8+ ✅
        System.out.println("SOLUTION 3: Use removeIf() - Java 8+");
        List<Integer> copy3 = new ArrayList<>(numbers);
        System.out.println("Before: " + copy3);
        
        copy3.removeIf(num -> num % 2 == 0); // One-liner!
        
        System.out.println("After: " + copy3);
        System.out.println("✅ Most concise and modern way!");
        
        System.out.println();
        
        // Solution 4: Use Streams - Java 8+ ✅
        System.out.println("SOLUTION 4: Use Streams (Functional approach)");
        List<Integer> copy4 = new ArrayList<>(numbers);
        System.out.println("Before: " + copy4);
        
        List<Integer> filtered = copy4.stream()
                                      .filter(num -> num % 2 != 0)
                                      .toList(); // or .collect(Collectors.toList())
        
        System.out.println("After: " + filtered);
        System.out.println("✅ Creates new list, original unchanged");
        
        System.out.println();
        
        // Solution 5: Use fail-safe collections ✅
        System.out.println("SOLUTION 5: Use CopyOnWriteArrayList");
        List<Integer> copy5 = new CopyOnWriteArrayList<>(numbers);
        System.out.println("Before: " + copy5);
        
        for(Integer num : copy5) {
            if(num % 2 == 0) {
                copy5.remove(num); // No exception!
            }
        }
        
        System.out.println("After: " + copy5);
        System.out.println("✅ Best for multi-threaded scenarios");
        
        System.out.println();
        
        // Solution 6: Traditional for loop (backward) ✅
        System.out.println("SOLUTION 6: Traditional for loop (backward)");
        List<Integer> copy6 = new ArrayList<>(numbers);
        System.out.println("Before: " + copy6);
        
        for(int i = copy6.size() - 1; i >= 0; i--) {
            if(copy6.get(i) % 2 == 0) {
                copy6.remove(i);
            }
        }
        
        System.out.println("After: " + copy6);
        System.out.println("✅ Old-school but works!");
        
        /*
         * WHICH SOLUTION TO USE?
         * =======================
         * 
         * Single-threaded removal:
         *   1st choice: iterator.remove()
         *   2nd choice: removeIf() (Java 8+)
         * 
         * Complex filtering:
         *   1st choice: Streams
         *   2nd choice: Two-pass approach
         * 
         * Multi-threaded:
         *   1st choice: CopyOnWriteArrayList
         *   2nd choice: Explicit synchronization
         * 
         * Legacy code/No Java 8:
         *   1st choice: iterator.remove()
         *   2nd choice: Backward for loop
         */
        
        System.out.println("\n");
    }
    
    /**
     * MULTI-THREADING SCENARIOS
     * =========================
     * Concurrent access ka real example
     */
    private static void multiThreadingScenarios() {
        System.out.println("5. MULTI-THREADING SCENARIOS:");
        System.out.println("-".repeat(60));
        
        // Scenario 1: Fail-Fast with multiple threads ❌
        System.out.println("SCENARIO 1: ArrayList (Fail-Fast) + Multiple Threads");
        
        List<Integer> sharedList = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            sharedList.add(i);
        }
        
        // Thread 1: Iterating
        Thread reader = new Thread(() -> {
            try {
                for(Integer num : sharedList) {
                    Thread.sleep(1); // Simulate work
                    // Just reading
                }
            } catch(ConcurrentModificationException e) {
                System.out.println("  ❌ Reader thread: ConcurrentModificationException!");
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Thread 2: Modifying
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(10); // Let reader start
                sharedList.add(100); // Modify!
                System.out.println("  Writer thread: Added element");
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        reader.start();
        writer.start();
        
        try {
            reader.join();
            writer.join();
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("  Result: Race condition! Exception likely!");
        
        System.out.println();
        
        // Scenario 2: Fail-Safe with multiple threads ✅
        System.out.println("SCENARIO 2: CopyOnWriteArrayList (Fail-Safe) + Multiple Threads");
        
        List<Integer> safeList = new CopyOnWriteArrayList<>();
        for(int i = 0; i < 100; i++) {
            safeList.add(i);
        }
        
        // Thread 1: Iterating
        Thread safeReader = new Thread(() -> {
            try {
                int count = 0;
                for(Integer num : safeList) {
                    Thread.sleep(1);
                    count++;
                }
                System.out.println("  ✅ Reader thread: Read " + count + " elements (no crash!)");
            } catch(ConcurrentModificationException e) {
                System.out.println("  ❌ Exception (shouldn't happen)");
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Thread 2: Modifying
        Thread safeWriter = new Thread(() -> {
            try {
                Thread.sleep(10);
                safeList.add(100);
                System.out.println("  ✅ Writer thread: Added element (no crash!)");
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        safeReader.start();
        safeWriter.start();
        
        try {
            safeReader.join();
            safeWriter.join();
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("  Result: Both threads completed safely! ✨");
        
        /*
         * THREADING BEST PRACTICES:
         * ==========================
         * 
         * 1. Use concurrent collections:
         *    - CopyOnWriteArrayList (for lists)
         *    - ConcurrentHashMap (for maps)
         *    - ConcurrentLinkedQueue (for queues)
         * 
         * 2. Synchronize access if using regular collections:
         *    synchronized(list) {
         *        Iterator it = list.iterator();
         *        while(it.hasNext()) { ... }
         *    }
         * 
         * 3. Use Collections.synchronizedList() wrapper:
         *    List<String> syncList = Collections.synchronizedList(new ArrayList<>());
         *    (But still need to sync iteration!)
         * 
         * 4. Avoid sharing mutable collections across threads if possible
         */
        
        System.out.println("\n");
    }
    
    /**
     * COMPARISON TABLE
     * ================
     */
    private static void comparisonTable() {
        System.out.println("6. FAIL-FAST vs FAIL-SAFE - COMPARISON TABLE:");
        System.out.println("-".repeat(60));
        
        System.out.println("Aspect              | Fail-Fast          | Fail-Safe");
        System.out.println("--------------------|--------------------|-----------------");
        System.out.println("Exception on modify | YES ❌             | NO ✅");
        System.out.println("Works on            | Original           | Clone/Snapshot");
        System.out.println("Memory overhead     | Low ✅             | High ❌");
        System.out.println("Performance         | Fast ✅            | Slower ❌");
        System.out.println("Thread-safe         | NO ❌              | YES ✅");
        System.out.println("Sees updates        | Immediately        | After iteration");
        System.out.println("Use case            | Single-threaded    | Multi-threaded");
        
        System.out.println();
        
        System.out.println("FAIL-FAST COLLECTIONS:");
        System.out.println("  - ArrayList, LinkedList");
        System.out.println("  - HashMap, HashSet, TreeSet");
        System.out.println("  - Vector, Stack");
        
        System.out.println();
        
        System.out.println("FAIL-SAFE COLLECTIONS:");
        System.out.println("  - CopyOnWriteArrayList");
        System.out.println("  - CopyOnWriteArraySet");
        System.out.println("  - ConcurrentHashMap");
        System.out.println("  - ConcurrentLinkedQueue");
        
        System.out.println();
        
        System.out.println("DECISION GUIDE:");
        System.out.println("-".repeat(60));
        System.out.println("Use FAIL-FAST when:");
        System.out.println("  ✓ Single-threaded application");
        System.out.println("  ✓ Performance is critical");
        System.out.println("  ✓ Memory is limited");
        System.out.println("  ✓ You want to catch bugs early");
        
        System.out.println();
        
        System.out.println("Use FAIL-SAFE when:");
        System.out.println("  ✓ Multi-threaded application");
        System.out.println("  ✓ Concurrent reads/writes needed");
        System.out.println("  ✓ Stability > Performance");
        System.out.println("  ✓ Read-heavy workload");
        
        System.out.println("\n");
    }
}

/*
 * ==========================================
 * COMPLETE SUMMARY
 * ==========================================
 * 
 * FAIL-FAST:
 * ==========
 * ✅ Pros:
 *    - Fast performance
 *    - Low memory usage
 *    - Early bug detection
 * 
 * ❌ Cons:
 *    - Throws ConcurrentModificationException
 *    - Not thread-safe
 *    - Can't modify during iteration (without iterator.remove)
 * 
 * FAIL-SAFE:
 * ==========
 * ✅ Pros:
 *    - Thread-safe
 *    - No ConcurrentModificationException
 *    - Can modify during iteration
 * 
 * ❌ Cons:
 *    - Higher memory usage (creates copies)
 *    - Slower performance
 *    - Iterator may not see latest changes
 * 
 * 
 * KEY LEARNINGS:
 * ==============
 * 1. ConcurrentModificationException = Fail-Fast behavior
 * 2. Use iterator.remove() for safe removal in fail-fast
 * 3. Use CopyOnWriteArrayList for thread-safe scenarios
 * 4. modCount mechanism powers fail-fast detection
 * 5. Trade-off: Safety vs Performance
 * 
 * MODERN ALTERNATIVES (Java 8+):
 * ==============================
 * - removeIf() method
 * - Stream API with filter()
 * - forEach with lambda (but can't modify)
 * 
 * NEXT FILE: 06_CommonMistakes.java
 * (Sabse common galtiyan aur unka solution!)
 */
