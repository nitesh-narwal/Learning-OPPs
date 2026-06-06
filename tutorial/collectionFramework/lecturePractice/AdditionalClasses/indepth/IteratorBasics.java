package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;

/**
 * ==========================================
 * ITERATOR BASICS - The Foundation
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Bhai, Iterator ek design pattern hai jo tumhe collection ke elements ko
 * ek-ek karke access karne deta hai WITHOUT exposing collection ki internal structure.
 * 
 * Simple shabdon mein: Collection ke andar ghoomne ka ek standard tarika!
 * 
 * WHY ITERATOR? (Kyu chahiye bhai?)
 * ================================
 * 1. ABSTRACTION: Tumhe pata nahi chahiye collection internally kaise kaam kar rahi hai
 * 2. UNIFORM ACCESS: ArrayList ho ya LinkedList, same way se iterate karo
 * 3. SAFE REMOVAL: Loop ke beech mein bhi elements remove kar sakte ho
 * 4. FAIL-FAST: Agar koi aur thread modify kare to turant pata chal jaye
 * 
 * REAL-WORLD ANALOGY:
 * ====================
 * Socho museum guide hai (Iterator) jo tumhe ek-ek painting dikhata hai.
 * Tumhe museum ka blueprint (internal structure) dekhne ki zaroorat nahi!
 * 
 * @author Nitesh Kumar
 * @level Beginner
 */
public class IteratorBasics {
    
    public static void main(String[] args) {
        System.out.println("=== ITERATOR BASICS ===\n");
        
        // Example 1: Basic Iterator Usage
        basicIteratorDemo();
        
        // Example 2: Why Iterator over Index-based Loop?
        whyIteratorOverIndex();
        
        // Example 3: Iterator with Different Collections
        iteratorWithDifferentCollections();
        
        // Example 4: Common Beginner Mistake
        commonBeginnerMistake();
    }
    
    /**
     * BASIC ITERATOR DEMO
     * ====================
     * Sabse pehle dekho ki Iterator kaise kaam karta hai - step by step!
     */
    private static void basicIteratorDemo() {
        System.out.println("1. BASIC ITERATOR USAGE:");
        System.out.println("-".repeat(50));
        
        // Step 1: Collection banao (ArrayList in this case)
        List<String> cricketers = new ArrayList<>();
        cricketers.add("Virat");
        cricketers.add("Rohit");
        cricketers.add("Dhoni");
        cricketers.add("Bumrah");
        
        System.out.println("Original List: " + cricketers);
        
        // Step 2: Iterator object prapt karo (get iterator)
        // Har collection class mein iterator() method hota hai
        Iterator<String> iterator = cricketers.iterator();
        
        // Step 3: Iterator use karke traverse karo
        System.out.println("\nIterating using Iterator:");
        
        // hasNext() - Check karta hai ki agle element hai ya nahi
        // next() - Agle element ko return karta hai aur cursor aage badhata hai
        while(iterator.hasNext()) {
            String cricketer = iterator.next();
            System.out.println("Current Player: " + cricketer);
        }
        
        /*
         * BEHIND THE SCENES (Kya ho raha hai internally?):
         * ================================================
         * 1. iterator() call hone par, cursor list ke START se PEHLE hota hai
         * 2. hasNext() cursor ke aage element check karta hai
         * 3. next() element return karke cursor ko aage move karta hai
         * 
         * Visualization:
         * [CURSOR] -> Virat -> Rohit -> Dhoni -> Bumrah
         * After first next(): Virat -> [CURSOR] -> Rohit -> Dhoni -> Bumrah
         */
        
        System.out.println("\n");
    }
    
    /**
     * WHY ITERATOR OVER INDEX-BASED LOOP?
     * =====================================
     * LinkedList mein index-based access O(n) hai, but Iterator O(1)!
     * Industry mein ye bohot important optimization hai!
     */
    private static void whyIteratorOverIndex() {
        System.out.println("2. ITERATOR vs INDEX-BASED LOOP:");
        System.out.println("-".repeat(50));
        
        // LinkedList banate hain - yahan farak dikhega!
        LinkedList<Integer> numbers = new LinkedList<>();
        for(int i = 1; i <= 5; i++) {
            numbers.add(i * 10);
        }
        
        // BAD APPROACH ❌: Index-based loop (Inefficient for LinkedList)
        System.out.println("BAD: Using index-based loop (O(n²) for LinkedList):");
        long startTime = System.nanoTime();
        for(int i = 0; i < numbers.size(); i++) {
            // Har get(i) call O(n) complexity ka hai LinkedList mein!
            // Because LinkedList ko start se traverse karke i-th element tak jaana padta hai
            System.out.print(numbers.get(i) + " ");
        }
        long endTime = System.nanoTime();
        System.out.println("\nTime taken: " + (endTime - startTime) + " ns");
        
        // GOOD APPROACH ✅: Iterator (Efficient - O(n))
        System.out.println("\nGOOD: Using Iterator (O(n) for all collections):");
        startTime = System.nanoTime();
        Iterator<Integer> it = numbers.iterator();
        while(it.hasNext()) {
            // next() internally already next node ka reference maintain karta hai
            // So O(1) access hai!
            System.out.print(it.next() + " ");
        }
        endTime = System.nanoTime();
        System.out.println("\nTime taken: " + (endTime - startTime) + " ns");
        
        /*
         * INDUSTRY INSIGHT:
         * =================
         * Large LinkedLists (lakhs of elements) mein ye difference
         * seconds ya minutes ka ho sakta hai!
         * 
         * Professional developers ALWAYS prefer Iterator for LinkedList traversal!
         */
        
        System.out.println("\n");
    }
    
    /**
     * ITERATOR WITH DIFFERENT COLLECTIONS
     * =====================================
     * Sabhi collections ke liye SAME syntax - ye hai Iterator ki power!
     */
    private static void iteratorWithDifferentCollections() {
        System.out.println("3. ITERATOR WITH DIFFERENT COLLECTIONS:");
        System.out.println("-".repeat(50));
        
        // ArrayList
        List<String> arrayList = new ArrayList<>(Arrays.asList("Java", "Python", "C++"));
        
        // LinkedList
        List<String> linkedList = new LinkedList<>(Arrays.asList("HTML", "CSS", "JS"));
        
        // HashSet (unordered)
        Set<String> hashSet = new HashSet<>(Arrays.asList("React", "Angular", "Vue"));
        
        // TreeSet (sorted)
        Set<String> treeSet = new TreeSet<>(Arrays.asList("Zebra", "Apple", "Mango"));
        
        System.out.println("ArrayList:");
        printUsingIterator(arrayList);
        
        System.out.println("\nLinkedList:");
        printUsingIterator(linkedList);
        
        System.out.println("\nHashSet (random order):");
        printUsingIterator(hashSet);
        
        System.out.println("\nTreeSet (sorted order):");
        printUsingIterator(treeSet);
        
        /*
         * PRO TIP:
         * ========
         * Iterator ka syntax SAME hai, but behavior collection pe depend karta hai:
         * - ArrayList/LinkedList: Insertion order maintain
         * - HashSet: Random order (no guarantee)
         * - TreeSet: Sorted order (natural/custom)
         * 
         * Ye polymorphism ka perfect example hai! 🎯
         */
        
        System.out.println("\n");
    }
    
    /**
     * Utility method: Generic way to print any collection using Iterator
     * Ye professional code mein reusable patterns ka example hai!
     */
    private static void printUsingIterator(Collection<?> collection) {
        Iterator<?> iterator = collection.iterator();
        while(iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }
    
    /**
     * COMMON BEGINNER MISTAKE
     * ========================
     * Ye mistake SABKO hoti hai initially! 😅
     */
    private static void commonBeginnerMistake() {
        System.out.println("4. COMMON BEGINNER MISTAKE:");
        System.out.println("-".repeat(50));
        
        List<String> fruits = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        Iterator<String> iterator = fruits.iterator();
        
        System.out.println("WRONG WAY ❌:");
        System.out.println("Calling next() without hasNext() check:");
        
        try {
            // Mistake: Directly next() call karna without checking hasNext()
            String fruit1 = iterator.next();
            System.out.println(fruit1);
            
            String fruit2 = iterator.next();
            System.out.println(fruit2);
            
            String fruit3 = iterator.next();
            System.out.println(fruit3);
            
            // BOOM! 💥 NoSuchElementException!
            String fruit4 = iterator.next(); // No more elements!
            System.out.println(fruit4);
            
        } catch(NoSuchElementException e) {
            System.out.println("ERROR: " + e.getClass().getSimpleName());
            System.out.println("Reason: next() called when no more elements!");
        }
        
        // RIGHT WAY ✅
        System.out.println("\nRIGHT WAY ✅:");
        System.out.println("Always check hasNext() before next():");
        
        iterator = fruits.iterator(); // Fresh iterator
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        // Safe! No exception! 🎉
        
        /*
         * MEMORY TIP (Yaad rakhne ka tarika):
         * ====================================
         * "hasNext() se pehle pucho, next() se pehle dekho!"
         * 
         * Jaise traffic light - green hai tab hi cross karo!
         * hasNext() = green light check karna
         * next() = road cross karna
         */
        
        System.out.println("\n");
    }
}

/*
 * ==========================================
 * KEY TAKEAWAYS (Yaad rakhne layak baatein)
 * ==========================================
 * 
 * 1. Iterator ek INTERFACE hai java.util package mein
 * 2. Sabhi collections (List, Set, Queue) ka apna Iterator implementation hai
 * 3. ALWAYS hasNext() before next() - Golden Rule! ✨
 * 4. LinkedList mein Iterator >>> Index-based loop
 * 5. Iterator collection-agnostic hai - same code, different collections
 * 
 * NEXT STEP:
 * ==========
 * Agli file mein dekhenge Iterator vs Traditional Loops ka detailed comparison!
 * File: 02_IteratorVsForLoop.java
 */
