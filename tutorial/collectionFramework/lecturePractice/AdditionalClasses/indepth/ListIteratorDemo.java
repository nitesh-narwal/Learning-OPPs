package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;

/**
 * ==========================================
 * LISTITERATOR - Bidirectional Power! 🚀
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * ListIterator, Iterator ka UPGRADED version hai! Ye sirf List interface
 * ke saath kaam karta hai (ArrayList, LinkedList, etc.)
 * 
 * ITERATOR vs LISTITERATOR:
 * ==========================
 * Iterator:
 * - Sirf FORWARD direction mein traverse
 * - Read aur Delete kar sakte ho
 * - All collections ke saath kaam karta hai
 * 
 * ListIterator:
 * - FORWARD aur BACKWARD dono directions! 🔄
 * - Read, Delete, UPDATE, aur ADD kar sakte ho!
 * - SIRF Lists ke saath kaam karta hai
 * 
 * REAL-WORLD ANALOGY:
 * ===================
 * Iterator = One-way street (ek taraf hi ja sakte ho)
 * ListIterator = Two-way street (dono taraf ja sakte ho!)
 * 
 * @author Nitesh Kumar
 * @level Intermediate
 */
public class ListIteratorDemo {
    
    public static void main(String[] args) {
        System.out.println("=== LISTITERATOR - BIDIRECTIONAL ITERATION ===\n");
        
        // Feature 1: Bidirectional traversal
        bidirectionalTraversal();
        
        // Feature 2: Additional methods
        additionalMethods();
        
        // Feature 3: Modification capabilities
        modificationCapabilities();
        
        // Feature 4: Index tracking
        indexTracking();
        
        // Real-world use cases
        realWorldUseCases();
        
        // Iterator vs ListIterator comparison
        iteratorVsListIterator();
    }
    
    /**
     * BIDIRECTIONAL TRAVERSAL
     * =======================
     * Aage bhi jao, peeche bhi aao! 🔄
     */
    private static void bidirectionalTraversal() {
        System.out.println("1. BIDIRECTIONAL TRAVERSAL:");
        System.out.println("-".repeat(60));
        
        List<String> months = new ArrayList<>(
            Arrays.asList("Jan", "Feb", "Mar", "Apr", "May")
        );
        
        System.out.println("List: " + months);
        
        // Get ListIterator
        java.util.ListIterator<String> listIterator = months.listIterator();
        
        // Forward traversal
        System.out.println("\n📍 FORWARD TRAVERSAL (hasNext + next):");
        while(listIterator.hasNext()) {
            System.out.println("  → " + listIterator.next());
        }
        // Now cursor is at END
        
        // Backward traversal
        System.out.println("\n📍 BACKWARD TRAVERSAL (hasPrevious + previous):");
        while(listIterator.hasPrevious()) {
            System.out.println("  ← " + listIterator.previous());
        }
        // Now cursor is back at START
        
        /*
         * CURSOR POSITION VISUALIZATION:
         * ===============================
         * 
         * Initial:        [CURSOR] Jan Feb Mar Apr May
         * After next():   Jan [CURSOR] Feb Mar Apr May
         * After 5 next(): Jan Feb Mar Apr May [CURSOR]
         * 
         * Now going backward:
         * After previous(): Jan Feb Mar Apr [CURSOR] May
         * After 5 previous(): [CURSOR] Jan Feb Mar Apr May
         * 
         * IMPORTANT:
         * - next() returns element AFTER cursor
         * - previous() returns element BEFORE cursor
         */
        
        System.out.println("\n📍 MIXED TRAVERSAL (Forward + Backward):");
        listIterator = months.listIterator(); // Reset
        
        System.out.println("Forward 2 steps:");
        System.out.println("  1. " + listIterator.next());
        System.out.println("  2. " + listIterator.next());
        
        System.out.println("Backward 1 step:");
        System.out.println("  ← " + listIterator.previous());
        
        System.out.println("Forward 3 steps:");
        System.out.println("  3. " + listIterator.next());
        System.out.println("  4. " + listIterator.next());
        System.out.println("  5. " + listIterator.next());
        
        /*
         * INDUSTRY TIP:
         * =============
         * Bidirectional iteration useful hai when:
         * 1. Undo/Redo functionality implement kar rahe ho
         * 2. Palindrome checking (aage se bhi, peeche se bhi same?)
         * 3. Comparison algorithms (two pointers approach)
         * 4. Navigation history (back/forward buttons)
         */
        
        System.out.println("\n");
    }
    
    /**
     * ADDITIONAL METHODS
     * ==================
     * ListIterator ke exclusive methods!
     */
    private static void additionalMethods() {
        System.out.println("2. LISTITERATOR EXCLUSIVE METHODS:");
        System.out.println("-".repeat(60));
        
        List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
        System.out.println("Original List: " + numbers);
        
        java.util.ListIterator<Integer> lit = numbers.listIterator();
        
        // hasPrevious() - Check if previous element exists
        System.out.println("\n📍 hasPrevious() - Check backward availability:");
        System.out.println("At start, hasPrevious(): " + lit.hasPrevious());
        
        lit.next(); // Move forward
        System.out.println("After one next(), hasPrevious(): " + lit.hasPrevious());
        
        // previous() - Get previous element
        System.out.println("\n📍 previous() - Get previous element:");
        int prev = lit.previous();
        System.out.println("previous() returned: " + prev);
        
        // nextIndex() and previousIndex()
        System.out.println("\n📍 nextIndex() and previousIndex():");
        lit = numbers.listIterator(); // Reset to start
        
        System.out.println("Position | nextIndex | previousIndex | Element");
        System.out.println("-".repeat(55));
        
        while(lit.hasNext()) {
            int nextIdx = lit.nextIndex();
            int prevIdx = lit.previousIndex();
            String element = lit.next().toString();
            
            System.out.printf("Current  | %-9d | %-13d | %s\n", 
                            nextIdx, prevIdx, element);
        }
        
        /*
         * INDEX METHODS EXPLANATION:
         * ==========================
         * 
         * nextIndex():
         * - Returns index of element that next() will return
         * - At END: returns list.size()
         * 
         * previousIndex():
         * - Returns index of element that previous() will return
         * - At START: returns -1
         * 
         * Example: [10, 20, 30]
         *          [C]              nextIndex()=0, previousIndex()=-1
         *          10 [C]           nextIndex()=1, previousIndex()=0
         *          10 20 [C]        nextIndex()=2, previousIndex()=1
         *          10 20 30 [C]     nextIndex()=3, previousIndex()=2
         */
        
        System.out.println("\n");
    }
    
    /**
     * MODIFICATION CAPABILITIES
     * =========================
     * ListIterator mein 3 modification operations hain:
     * 1. remove() - Delete
     * 2. set() - Update
     * 3. add() - Insert
     */
    private static void modificationCapabilities() {
        System.out.println("3. MODIFICATION CAPABILITIES:");
        System.out.println("-".repeat(60));
        
        // Operation 1: remove() - Same as Iterator
        System.out.println("📍 OPERATION 1: remove()");
        List<String> colors = new ArrayList<>(
            Arrays.asList("Red", "Green", "Blue", "Yellow")
        );
        System.out.println("Original: " + colors);
        
        java.util.ListIterator<String> colorIt = colors.listIterator();
        while(colorIt.hasNext()) {
            String color = colorIt.next();
            if(color.equals("Green")) {
                colorIt.remove();
                System.out.println("Removed: " + color);
            }
        }
        System.out.println("After remove: " + colors);
        
        System.out.println();
        
        // Operation 2: set() - UPDATE (Unique to ListIterator!)
        System.out.println("📍 OPERATION 2: set() - UPDATE ✨");
        List<Integer> prices = new ArrayList<>(Arrays.asList(100, 200, 300, 400));
        System.out.println("Original prices: " + prices);
        
        java.util.ListIterator<Integer> priceIt = prices.listIterator();
        System.out.println("Applying 10% discount:");
        
        while(priceIt.hasNext()) {
            Integer price = priceIt.next();
            int discounted = (int)(price * 0.9);
            priceIt.set(discounted); // Update current element!
            System.out.println("  " + price + " → " + discounted);
        }
        
        System.out.println("Updated prices: " + prices);
        
        System.out.println();
        
        // Operation 3: add() - INSERT (Unique to ListIterator!)
        System.out.println("📍 OPERATION 3: add() - INSERT ✨");
        List<String> days = new ArrayList<>(Arrays.asList("Mon", "Wed", "Fri"));
        System.out.println("Original: " + days);
        
        java.util.ListIterator<String> dayIt = days.listIterator();
        
        // Insert "Tue" after "Mon"
        dayIt.next(); // Read "Mon"
        dayIt.add("Tue"); // Insert after "Mon"
        System.out.println("Added 'Tue' after 'Mon': " + days);
        
        // Insert "Thu" after "Wed"
        dayIt.next(); // Read "Wed"
        dayIt.add("Thu"); // Insert after "Wed"
        System.out.println("Added 'Thu' after 'Wed': " + days);
        
        System.out.println("Final: " + days);
        
        /*
         * MODIFICATION METHODS - THE RULES:
         * ==================================
         * 
         * remove():
         * - Must call next() or previous() first
         * - Removes last returned element
         * - Can't call twice without next()/previous()
         * 
         * set(E e):
         * - Must call next() or previous() first
         * - Replaces last returned element
         * - Can call multiple times for same element
         * 
         * add(E e):
         * - Inserts BEFORE the element next() would return
         * - Can call anytime (doesn't need next()/previous())
         * - After add(), cursor moves AFTER new element
         * 
         * MEMORY TRICK:
         * =============
         * remove() = "Jo dekha usko hata do"
         * set() = "Jo dekha usko badal do"
         * add() = "Cursor ke aage naya daal do"
         */
        
        System.out.println("\n");
    }
    
    /**
     * INDEX TRACKING
     * ==============
     * ListIterator ke saath index bhi track kar sakte ho!
     */
    private static void indexTracking() {
        System.out.println("4. INDEX TRACKING:");
        System.out.println("-".repeat(60));
        
        List<String> students = new ArrayList<>(
            Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve")
        );
        
        System.out.println("Students: " + students);
        System.out.println();
        
        // Starting from specific index
        System.out.println("📍 Starting from index 2:");
        java.util.ListIterator<String> lit = students.listIterator(2);
        
        System.out.println("Current position: index " + lit.nextIndex());
        System.out.println("Next element: " + lit.next());
        System.out.println("Previous element: " + lit.previous());
        
        System.out.println();
        
        // Finding element by index
        System.out.println("📍 Finding 'Charlie' and modifying:");
        lit = students.listIterator();
        
        while(lit.hasNext()) {
            int currentIndex = lit.nextIndex();
            String student = lit.next();
            
            if(student.equals("Charlie")) {
                System.out.println("Found 'Charlie' at index " + currentIndex);
                lit.set("Charles"); // Update name
                System.out.println("Updated to 'Charles'");
                break;
            }
        }
        
        System.out.println("Updated list: " + students);
        
        /*
         * STARTING FROM SPECIFIC INDEX:
         * ==============================
         * 
         * list.listIterator()      → Starts at index 0 (beginning)
         * list.listIterator(0)     → Same as above
         * list.listIterator(n)     → Starts at index n
         * list.listIterator(size)  → Starts at end
         * 
         * Use case: Resume processing from specific position
         * Example: "Process orders from index 100 onwards"
         */
        
        System.out.println("\n");
    }
    
    /**
     * REAL-WORLD USE CASES
     * ====================
     * Industry mein ListIterator kaise use hota hai
     */
    private static void realWorldUseCases() {
        System.out.println("5. REAL-WORLD USE CASES:");
        System.out.println("-".repeat(60));
        
        // Use Case 1: Text Editor (Undo/Redo)
        System.out.println("USE CASE 1: Text Editor - Undo/Redo 📝");
        
        class TextEditor {
            private List<String> history = new ArrayList<>();
            private java.util.ListIterator<String> cursor;
            
            TextEditor() {
                history.add(""); // Initial empty state
                cursor = history.listIterator(history.size());
            }
            
            void type(String text) {
                // Remove any forward history
                while(cursor.hasNext()) {
                    cursor.next();
                    cursor.remove();
                }
                
                cursor.add(text);
                System.out.println("  Typed: " + text);
            }
            
            String undo() {
                if(cursor.hasPrevious()) {
                    cursor.previous();
                    if(cursor.hasPrevious()) {
                        return cursor.previous();
                    }
                }
                return "";
            }
            
            String redo() {
                if(cursor.hasNext()) {
                    cursor.next();
                    if(cursor.hasNext()) {
                        return cursor.next();
                    }
                }
                return history.get(history.size() - 1);
            }
        }
        
        TextEditor editor = new TextEditor();
        editor.type("Hello");
        editor.type("Hello World");
        editor.type("Hello World!");
        
        System.out.println("  Undo: " + editor.undo());
        System.out.println("  Undo: " + editor.undo());
        System.out.println("  Redo: " + editor.redo());
        
        System.out.println();
        
        // Use Case 2: Playlist Navigation
        System.out.println("USE CASE 2: Music Playlist - Next/Previous 🎵");
        
        List<String> playlist = new ArrayList<>(
            Arrays.asList("Song1.mp3", "Song2.mp3", "Song3.mp3", "Song4.mp3")
        );
        
        java.util.ListIterator<String> player = playlist.listIterator();
        
        System.out.println("  Playing: " + player.next());
        System.out.println("  Next: " + player.next());
        System.out.println("  Previous: " + player.previous());
        System.out.println("  Previous: " + player.previous());
        System.out.println("  Next: " + player.next());
        
        System.out.println();
        
        // Use Case 3: In-place transformation
        System.out.println("USE CASE 3: Data Transformation 🔄");
        
        List<String> emails = new ArrayList<>(
            Arrays.asList("USER1@GMAIL.COM", "USER2@YAHOO.COM", "USER3@OUTLOOK.COM")
        );
        
        System.out.println("  Before: " + emails);
        
        java.util.ListIterator<String> emailIt = emails.listIterator();
        while(emailIt.hasNext()) {
            String email = emailIt.next();
            emailIt.set(email.toLowerCase()); // Normalize to lowercase
        }
        
        System.out.println("  After: " + emails);
        
        /*
         * MORE REAL-WORLD EXAMPLES:
         * ==========================
         * 
         * 1. Browser History (Back/Forward buttons)
         * 2. Game State Management (Undo moves in chess)
         * 3. Database Transaction Logs (Rollback/Rollforward)
         * 4. Version Control Systems (Navigate commits)
         * 5. Image Gallery (Previous/Next photo)
         * 6. Form Wizard (Previous/Next step)
         */
        
        System.out.println("\n");
    }
    
    /**
     * ITERATOR vs LISTITERATOR - COMPLETE COMPARISON
     * ===============================================
     */
    private static void iteratorVsListIterator() {
        System.out.println("6. ITERATOR vs LISTITERATOR - COMPARISON TABLE:");
        System.out.println("-".repeat(60));
        
        System.out.println("Feature              | Iterator | ListIterator");
        System.out.println("---------------------|----------|-------------");
        System.out.println("Forward traversal    |    ✅    |     ✅");
        System.out.println("Backward traversal   |    ❌    |     ✅");
        System.out.println("Read elements        |    ✅    |     ✅");
        System.out.println("Remove elements      |    ✅    |     ✅");
        System.out.println("Modify elements      |    ❌    |     ✅ (set)");
        System.out.println("Add elements         |    ❌    |     ✅ (add)");
        System.out.println("Index tracking       |    ❌    |     ✅");
        System.out.println("Works with Set       |    ✅    |     ❌");
        System.out.println("Works with List      |    ✅    |     ✅");
        System.out.println("Start from index     |    ❌    |     ✅");
        
        System.out.println();
        
        System.out.println("METHODS COMPARISON:");
        System.out.println("-".repeat(60));
        
        System.out.println("Iterator methods:");
        System.out.println("  - hasNext()");
        System.out.println("  - next()");
        System.out.println("  - remove()");
        System.out.println("  - forEachRemaining()");
        
        System.out.println("\nListIterator methods (ALL Iterator methods + ):");
        System.out.println("  - hasPrevious() ✨");
        System.out.println("  - previous() ✨");
        System.out.println("  - nextIndex() ✨");
        System.out.println("  - previousIndex() ✨");
        System.out.println("  - set(E e) ✨");
        System.out.println("  - add(E e) ✨");
        
        System.out.println();
        
        System.out.println("WHEN TO USE WHAT?");
        System.out.println("-".repeat(60));
        System.out.println("Use Iterator when:");
        System.out.println("  ✓ Working with any Collection (Set, List, Queue)");
        System.out.println("  ✓ Only need forward traversal");
        System.out.println("  ✓ Simple read/delete operations");
        
        System.out.println("\nUse ListIterator when:");
        System.out.println("  ✓ Working specifically with Lists");
        System.out.println("  ✓ Need bidirectional traversal");
        System.out.println("  ✓ Need to modify/add elements while iterating");
        System.out.println("  ✓ Need index information");
        System.out.println("  ✓ Implementing undo/redo, navigation features");
        
        System.out.println("\n");
    }
}

/*
 * ==========================================
 * KEY TAKEAWAYS
 * ==========================================
 * 
 * 1. ListIterator = Iterator ka SUPERSET (more powerful!)
 * 2. ONLY works with Lists (ArrayList, LinkedList, etc.)
 * 3. Bidirectional traversal: hasNext/next + hasPrevious/previous
 * 4. Three modification ops: remove(), set(), add()
 * 5. Index tracking: nextIndex(), previousIndex()
 * 6. Can start from specific index: list.listIterator(index)
 * 
 * CURSOR BEHAVIOR:
 * ================
 * - Always positioned BETWEEN elements
 * - next() returns element AFTER cursor
 * - previous() returns element BEFORE cursor
 * - add() inserts BEFORE cursor position
 * 
 * PRODUCTION TIPS:
 * ================
 * 1. Use for undo/redo functionality
 * 2. Perfect for bidirectional navigation
 * 3. In-place list modifications without creating new list
 * 4. Browser/playlist navigation features
 * 
 * NEXT FILE: 05_FailFast_FailSafe.java
 * (ConcurrentModificationException explained!)
 */
