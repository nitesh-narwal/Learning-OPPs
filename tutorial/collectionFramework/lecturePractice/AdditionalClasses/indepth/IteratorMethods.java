package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;

/**
 * ==========================================
 * ITERATOR METHODS - Deep Dive
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Iterator interface mein sirf 4 methods hain, but unki understanding
 * bohot zaroori hai! Iss file mein hum DETAILED mein samjhenge:
 * 
 * 1. boolean hasNext()     - Agle element hai ya nahi?
 * 2. E next()              - Agle element ko return karo
 * 3. void remove()         - Current element ko delete karo
 * 4. void forEachRemaining() - Bache hue elements pe action karo (Java 8+)
 * 
 * IMPORTANT NOTES:
 * ================
 * - remove() is OPTIONAL - kuch implementations mein nahi hota!
 * - next() ke bina remove() call karna = IllegalStateException
 * - forEachRemaining() ek hi baar use kar sakte ho
 * 
 * @author Nitesh Kumar
 * @level Intermediate
 */
public class IteratorMethods {
    
    public static void main(String[] args) {
        System.out.println("=== ITERATOR METHODS - DEEP DIVE ===\n");
        
        // Method 1: hasNext() - The Guard
        hasNextInDepth();
        
        // Method 2: next() - The Retriever
        nextInDepth();
        
        // Method 3: remove() - The Eliminator
        removeInDepth();
        
        // Method 4: forEachRemaining() - The Bulk Processor
        forEachRemainingInDepth();
        
        // Real-world scenarios
        realWorldScenarios();
    }
    
    /**
     * hasNext() METHOD - THE GUARD
     * =============================
     * Ye method check karta hai ki iterator ke paas aur elements hain ya nahi.
     * Think of it as a "traffic light" 🚦
     */
    private static void hasNextInDepth() {
        System.out.println("1. hasNext() - THE GUARD METHOD:");
        System.out.println("-".repeat(60));
        
        List<String> books = new ArrayList<>(Arrays.asList("Java", "Python", "C++"));
        Iterator<String> iterator = books.iterator();
        
        System.out.println("Collection: " + books);
        System.out.println("\nStep-by-step hasNext() demonstration:");
        
        int step = 1;
        while(true) {
            boolean hasNext = iterator.hasNext();
            System.out.println("Step " + step + ": hasNext() = " + hasNext);
            
            if(!hasNext) {
                System.out.println("  └─ No more elements! Loop terminates.");
                break;
            }
            
            String book = iterator.next();
            System.out.println("  ├─ next() returned: '" + book + "'");
            System.out.println("  └─ Cursor moved forward");
            step++;
        }
        
        System.out.println();
        
        // Edge case: hasNext() on empty collection
        System.out.println("EDGE CASE: Empty collection");
        List<String> emptyList = new ArrayList<>();
        Iterator<String> emptyIterator = emptyList.iterator();
        
        System.out.println("hasNext() on empty iterator: " + emptyIterator.hasNext());
        System.out.println("Result: false (safe to check!)");
        
        /*
         * IMPORTANT NOTES ABOUT hasNext():
         * ==================================
         * 1. hasNext() ko multiple baar call kar sakte ho - no side effects!
         * 2. hasNext() state change nahi karta, sirf check karta hai
         * 3. Thread-safe nahi hai (without synchronization)
         * 4. O(1) complexity - instant check!
         * 
         * MEMORY TIP:
         * ===========
         * hasNext() = "Agla chapter hai book mein?"
         * next() = "Wo chapter padho!"
         */
        
        System.out.println("\n");
    }
    
    /**
     * next() METHOD - THE RETRIEVER
     * ==============================
     * Element return karta hai AUR cursor aage badhata hai - dono ek saath!
     */
    private static void nextInDepth() {
        System.out.println("2. next() - THE RETRIEVER METHOD:");
        System.out.println("-".repeat(60));
        
        List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30));
        Iterator<Integer> iterator = numbers.iterator();
        
        System.out.println("Collection: " + numbers);
        System.out.println("\nCalling next() repeatedly:");
        
        // First call
        System.out.println("\n1st next() call:");
        Integer first = iterator.next();
        System.out.println("  Returned: " + first);
        System.out.println("  Cursor: now points after " + first);
        
        // Second call
        System.out.println("\n2nd next() call:");
        Integer second = iterator.next();
        System.out.println("  Returned: " + second);
        System.out.println("  Cursor: now points after " + second);
        
        // Third call
        System.out.println("\n3rd next() call:");
        Integer third = iterator.next();
        System.out.println("  Returned: " + third);
        System.out.println("  Cursor: now at END");
        
        // Fourth call - BOOM! 💥
        System.out.println("\n4th next() call (no more elements):");
        try {
            Integer fourth = iterator.next(); // NoSuchElementException!
            System.out.println("  Returned: " + fourth);
        } catch(NoSuchElementException e) {
            System.out.println("  ❌ NoSuchElementException thrown!");
            System.out.println("  Reason: No more elements to return");
        }
        
        System.out.println();
        
        // Visualization of cursor movement
        System.out.println("CURSOR MOVEMENT VISUALIZATION:");
        System.out.println("-".repeat(60));
        System.out.println("Initial:    [CURSOR] → 10 → 20 → 30");
        System.out.println("After 1st:  10 → [CURSOR] → 20 → 30");
        System.out.println("After 2nd:  10 → 20 → [CURSOR] → 30");
        System.out.println("After 3rd:  10 → 20 → 30 → [CURSOR] (END)");
        System.out.println("4th call:   💥 NoSuchElementException!");
        
        /*
         * NEXT() METHOD SECRETS:
         * =======================
         * 1. next() TWO operations karta hai:
         *    a) Current element return karna
         *    b) Cursor aage move karna
         * 
         * 2. Pehle element ke pehle bhi cursor hota hai!
         *    Initial position: BEFORE first element
         * 
         * 3. next() ke baad immediately remove() call kar sakte ho
         *    (Last returned element delete hoga)
         * 
         * 4. Exception handling zaroori hai production code mein!
         */
        
        System.out.println("\n");
    }
    
    /**
     * remove() METHOD - THE ELIMINATOR
     * =================================
     * Last returned element ko safely delete karta hai!
     * Ye method sabse tricky hai! 🎯
     */
    private static void removeInDepth() {
        System.out.println("3. remove() - THE ELIMINATOR METHOD:");
        System.out.println("-".repeat(60));
        
        // Scenario 1: Correct Usage ✅
        System.out.println("SCENARIO 1: Correct Usage ✅");
        List<String> fruits = new ArrayList<>(
            Arrays.asList("Apple", "Banana", "Cherry", "Date", "Elderberry")
        );
        
        System.out.println("Original: " + fruits);
        System.out.println("Task: Remove fruits starting with 'B' or 'D'");
        
        Iterator<String> iterator = fruits.iterator();
        while(iterator.hasNext()) {
            String fruit = iterator.next();
            if(fruit.startsWith("B") || fruit.startsWith("D")) {
                System.out.println("  Removing: " + fruit);
                iterator.remove(); // Safe removal!
            }
        }
        
        System.out.println("After removal: " + fruits);
        
        System.out.println();
        
        // Scenario 2: ERROR - remove() without next() ❌
        System.out.println("SCENARIO 2: ERROR - remove() without next() ❌");
        List<String> colors = new ArrayList<>(Arrays.asList("Red", "Green", "Blue"));
        Iterator<String> colorIt = colors.iterator();
        
        try {
            // Trying to remove without calling next()
            colorIt.remove(); // IllegalStateException!
        } catch(IllegalStateException e) {
            System.out.println("  ❌ IllegalStateException!");
            System.out.println("  Reason: Must call next() before remove()");
        }
        
        System.out.println();
        
        // Scenario 3: ERROR - Double remove() ❌
        System.out.println("SCENARIO 3: ERROR - Double remove() on same element ❌");
        List<Integer> nums = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Iterator<Integer> numIt = nums.iterator();
        
        try {
            numIt.next(); // Read first element
            numIt.remove(); // Remove it - OK!
            System.out.println("  First remove(): Success");
            
            numIt.remove(); // Try to remove again - ERROR!
            
        } catch(IllegalStateException e) {
            System.out.println("  ❌ IllegalStateException on second remove()!");
            System.out.println("  Reason: Can't remove twice without calling next()");
        }
        
        System.out.println();
        
        // Scenario 4: UnsupportedOperationException
        System.out.println("SCENARIO 4: Immutable collections don't support remove() ❌");
        List<String> immutableList = Arrays.asList("Java", "Python", "C++");
        Iterator<String> immutableIt = immutableList.iterator();
        
        try {
            immutableIt.next();
            immutableIt.remove(); // Not supported!
        } catch(UnsupportedOperationException e) {
            System.out.println("  ❌ UnsupportedOperationException!");
            System.out.println("  Reason: Arrays.asList() returns immutable list");
            System.out.println("  Tip: Use new ArrayList<>(Arrays.asList(...)) instead");
        }
        
        /*
         * REMOVE() METHOD - THE COMPLETE RULES:
         * ======================================
         * 
         * RULE 1: next() ke baad hi remove() call karo
         *         next() → remove() ✅
         *         remove() → remove() ❌
         * 
         * RULE 2: Ek next() ke liye ek hi remove() allowed
         *         next() → remove() → remove() ❌
         *         next() → remove() → next() → remove() ✅
         * 
         * RULE 3: Kuch collections remove() support nahi karte
         *         Check karne ka tarika:
         *         try-catch(UnsupportedOperationException)
         * 
         * RULE 4: remove() underlying collection ko modify karta hai
         *         Iterator ke through removal safe hai!
         * 
         * WHY ITERATOR.REMOVE() IS BETTER THAN COLLECTION.REMOVE()?
         * ==========================================================
         * 
         * BAD:  for(String s : list) { list.remove(s); }
         *       → ConcurrentModificationException 💥
         * 
         * GOOD: Iterator it = list.iterator();
         *       while(it.hasNext()) { 
         *           it.next();
         *           it.remove(); 
         *       }
         *       → Works perfectly! ✅
         * 
         * Industry mein ye pattern bohot common hai!
         */
        
        System.out.println("\n");
    }
    
    /**
     * forEachRemaining() METHOD - THE BULK PROCESSOR
     * ===============================================
     * Java 8 mein add hua - bache hue elements pe action karo!
     */
    private static void forEachRemainingInDepth() {
        System.out.println("4. forEachRemaining() - THE BULK PROCESSOR (Java 8+):");
        System.out.println("-".repeat(60));
        
        // Basic usage
        System.out.println("BASIC USAGE:");
        List<String> cities = new ArrayList<>(
            Arrays.asList("Mumbai", "Delhi", "Bangalore", "Chennai", "Kolkata")
        );
        
        Iterator<String> iterator = cities.iterator();
        
        // Process first two manually
        System.out.println("Manual processing:");
        System.out.println("  1. " + iterator.next());
        System.out.println("  2. " + iterator.next());
        
        // Process remaining with forEachRemaining
        System.out.println("\nProcessing remaining with forEachRemaining():");
        iterator.forEachRemaining(city -> System.out.println("  → " + city));
        
        System.out.println();
        
        // Practical example: Logging
        System.out.println("PRACTICAL EXAMPLE: Conditional logging");
        List<Integer> scores = new ArrayList<>(
            Arrays.asList(45, 67, 89, 92, 34, 78, 95, 88)
        );
        
        Iterator<Integer> scoreIt = scores.iterator();
        
        // Find first excellent score (>90)
        while(scoreIt.hasNext()) {
            int score = scoreIt.next();
            if(score > 90) {
                System.out.println("First excellent score found: " + score);
                break; // Stop searching
            }
        }
        
        // Log all remaining scores
        System.out.println("Remaining scores to review:");
        scoreIt.forEachRemaining(score -> 
            System.out.println("  Score: " + score + 
                             (score >= 75 ? " ✅ Good" : " ⚠️ Needs improvement"))
        );
        
        /*
         * forEachRemaining() CHARACTERISTICS:
         * ====================================
         * 
         * 1. Lambda/Method reference use kar sakte ho
         * 2. Bache hue SAARE elements process karega
         * 3. Ek baar call karne ke baad iterator exhausted ho jata hai
         * 4. Internal implementation optimized hai (better than manual loop)
         * 
         * WHEN TO USE?
         * ============
         * - Partial processing ke baad remaining elements process karna ho
         * - Functional programming style preferred ho
         * - Code concise banana ho
         * 
         * EQUIVALENT CODE:
         * ================
         * iterator.forEachRemaining(x -> process(x));
         * 
         * // is same as:
         * while(iterator.hasNext()) {
         *     process(iterator.next());
         * }
         */
        
        System.out.println("\n");
    }
    
    /**
     * REAL-WORLD SCENARIOS
     * ====================
     * Production code mein iterator methods kaise use hote hain
     */
    private static void realWorldScenarios() {
        System.out.println("5. REAL-WORLD SCENARIOS:");
        System.out.println("-".repeat(60));
        
        // Scenario 1: Data cleaning
        System.out.println("SCENARIO 1: Data Cleaning (Remove null/invalid entries)");
        List<String> userData = new ArrayList<>(
            Arrays.asList("Alice", null, "Bob", "", "Charlie", null, "Dave")
        );
        
        System.out.println("Before cleaning: " + userData);
        
        Iterator<String> cleanIt = userData.iterator();
        while(cleanIt.hasNext()) {
            String user = cleanIt.next();
            if(user == null || user.trim().isEmpty()) {
                cleanIt.remove(); // Remove invalid entries
            }
        }
        
        System.out.println("After cleaning: " + userData);
        
        System.out.println();
        
        // Scenario 2: Batch processing with early exit
        System.out.println("SCENARIO 2: Batch Processing (Process until error)");
        List<String> tasks = new ArrayList<>(
            Arrays.asList("Task1", "Task2", "CRITICAL_ERROR", "Task4", "Task5")
        );
        
        Iterator<String> taskIt = tasks.iterator();
        int processed = 0;
        
        while(taskIt.hasNext()) {
            String task = taskIt.next();
            
            if(task.contains("ERROR")) {
                System.out.println("  ❌ Critical error encountered: " + task);
                System.out.println("  Stopping batch processing...");
                break;
            }
            
            System.out.println("  ✅ Processed: " + task);
            processed++;
            taskIt.remove(); // Remove completed task
        }
        
        System.out.println("Processed " + processed + " tasks");
        System.out.println("Remaining tasks: " + tasks);
        
        System.out.println();
        
        // Scenario 3: Filtering with complex logic
        System.out.println("SCENARIO 3: E-commerce Order Filtering");
        
        class Order {
            String id;
            double amount;
            boolean isPaid;
            
            Order(String id, double amount, boolean isPaid) {
                this.id = id;
                this.amount = amount;
                this.isPaid = isPaid;
            }
            
            @Override
            public String toString() {
                return id + "($" + amount + ", " + (isPaid ? "Paid" : "Unpaid") + ")";
            }
        }
        
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("ORD001", 50.0, true));
        orders.add(new Order("ORD002", 150.0, false));
        orders.add(new Order("ORD003", 30.0, false));
        orders.add(new Order("ORD004", 200.0, true));
        
        System.out.println("All orders: ");
        orders.forEach(o -> System.out.println("  " + o));
        
        // Remove unpaid orders below $100
        System.out.println("\nRemoving unpaid orders below $100...");
        Iterator<Order> orderIt = orders.iterator();
        while(orderIt.hasNext()) {
            Order order = orderIt.next();
            if(!order.isPaid && order.amount < 100) {
                System.out.println("  Removing: " + order);
                orderIt.remove();
            }
        }
        
        System.out.println("\nRemaining orders:");
        orders.forEach(o -> System.out.println("  " + o));
        
        /*
         * INDUSTRY BEST PRACTICES:
         * =========================
         * 
         * 1. ALWAYS use hasNext() before next()
         *    → Prevents NoSuchElementException
         * 
         * 2. Use iterator.remove() instead of collection.remove() during iteration
         *    → Prevents ConcurrentModificationException
         * 
         * 3. Handle exceptions gracefully in production code
         *    → IllegalStateException, UnsupportedOperationException
         * 
         * 4. Prefer enhanced for-loop if no removal needed
         *    → Cleaner and more readable
         * 
         * 5. Use forEachRemaining() for partial processing scenarios
         *    → More functional and concise
         * 
         * 6. Document iterator usage in complex logic
         *    → Helps team understand why iterator was chosen
         */
        
        System.out.println("\n");
    }
}

/*
 * ==========================================
 * COMPLETE METHOD SUMMARY
 * ==========================================
 * 
 * Method               | Returns  | Purpose                      | Can throw
 * ---------------------|----------|------------------------------|------------------
 * hasNext()            | boolean  | Check if more elements exist | No exceptions
 * next()               | E        | Return next element          | NoSuchElementException
 * remove()             | void     | Remove last returned element | IllegalStateException
 *                      |          |                              | UnsupportedOperationException
 * forEachRemaining()   | void     | Process remaining elements   | -
 * 
 * 
 * CALLING SEQUENCE RULES:
 * =======================
 * 
 * ✅ VALID:
 * hasNext() → next() → remove()
 * hasNext() → next() → next() → remove()
 * next() → remove() → hasNext() → next() → remove()
 * next() → forEachRemaining()
 * 
 * ❌ INVALID:
 * next() → remove() → remove()  (Double remove without next)
 * remove() → next()  (Remove before next)
 * forEachRemaining() → next()  (Iterator exhausted)
 * 
 * 
 * NEXT FILE: 04_ListIterator.java
 * (Bidirectional iteration aur extra methods!)
 */
