package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class Step1_LinkedListBasics {
    
    static void main(String[] args) {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║         STEP 1: LINKEDLIST BASICS - THE FOUNDATION           ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * In this step, we'll learn:
         * 1. What is LinkedList?
         * 2. How to create LinkedList
         * 3. Basic operations (add, remove, get)
         * 4. Why LinkedList is different from ArrayList
         * 5. When to choose LinkedList
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 1: CREATING A LINKEDLIST
         * ═════════════════════════════════════════════════════════════════
         */
        
        // Create an empty LinkedList of Strings
        LinkedList<String> cities = new LinkedList<>();
        
        /*
         * What happens when we create LinkedList<String>?
         * • A LinkedList object is created
         * • Initially, it's empty (no nodes)
         * • Head pointer is null (no first node)
         * • Tail pointer is null (no last node)
         * • Size is 0
         * 
         * Memory diagram:
         * LinkedList citiesobject
         *   head: null
         *   tail: null
         *   size: 0
         */


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 2: ADDING ELEMENTS (CREATE NODES)
         * ═════════════════════════════════════════════════════════════════
         */
        
        cities.add("New York");     // O(1) - adds to the end
        cities.add("London");       // O(1) - adds to the end
        cities.add("Tokyo");        // O(1) - adds to the end
        cities.add("Paris");        // O(1) - adds to the end
        
        /*
         * After adding 4 cities, the LinkedList looks like:
         * 
         * head ──→ [New York] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ null
         *
         * Each [   ] is a NODE containing:
         * • Data (the city name)
         * • Reference to next node
         * 
         * LinkedList internal state after add("New York"), add("London"), etc:
         * size = 4
         * head = Node("New York")
         * tail = Node("Paris")
         */

        // Add at the beginning using addFirst()
        cities.addFirst("Barcelona");
        
        /*
         * Now the list looks like:
         * head ──→ [Barcelona] ──→ [New York] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ null
         *
         * addFirst() is O(1) because we just:
         * • Create new node with "Barcelona"
         * • New node points to old head (New York)
         * • Update head to point to new node
         * • Done!
         */

        // Add at the end using addLast()
        cities.addLast("Berlin");
        
        /*
         * Now the list looks like:
         * head ──→ [Barcelona] ──→ [New York] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ [Berlin] ──→ null
         *                                                                                     ↑
         *                                                                                   tail
         */

        // Add at specific index
        cities.add(2, "Singapore");  // O(n) - need to traverse to find position
        
        /*
         * Now the list looks like:
         * [Barcelona] ──→ [New York] ──→ [Singapore] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ [Berlin] ──→ null
         *
         * add(2, "Singapore") takes O(n) time because:
         * • We need to traverse from head to index 2
         * • Find the node at index 1 (New York)
         * • Create new node (Singapore)
         * • Update pointers:
         *   - New node points to the old node at index 2 (London)
         *   - Node at index 1 (New York) points to new node
         */

        print("Cities after adding elements:", cities);


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 3: ACCESSING ELEMENTS (TRAVERSAL)
         * ═════════════════════════════════════════════════════════════════
         */
        
        // Method 1: Using get(index) - NOT RECOMMENDED for LinkedList!
        String firstCity = cities.get(0);        // O(n) - must traverse from head
        
        /*
         * Why is get(0) O(n) even though we want the first element?
         * • To get index 0, LinkedList must traverse from head
         * • For first element, we traverse 0 steps (lucky!)
         * • For middle element, we traverse ~n/2 steps (unlucky!)
         * • For last element, we traverse n-1 steps (very unlucky!)
         * 
         * This is VERY DIFFERENT from ArrayList where get() is always O(1)!
         */

        // Method 2: Using getFirst() - BETTER!
        String firstCityOptimal = cities.getFirst();  // O(1) - direct access to head
        
        /*
         * getFirst() is O(1) because:
         * • LinkedList maintains a direct reference to head
         * • No traversal needed
         * • Just return the data from head node
         * 
         * Similarly, getLast() is O(1) because:
         * • LinkedList maintains a direct reference to tail
         * • No traversal needed
         * • Just return the data from tail node
         */

        // Method 3: Using iteration - MOST EFFICIENT for reading all elements
        // (We'll see this in detail in next steps)


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 4: REMOVING ELEMENTS
         * ═════════════════════════════════════════════════════════════════
         */
        
        cities.remove(2);  // Remove by index - O(n)
        /*
         * remove(2) operation:
         * • Traverse to index 2 (Singapore)
         * • Find reference to it
         * • Find previous node (New York)
         * • Update New York's next to point to London (skip Singapore)
         * • Remove Singapore node
         * • Return removed element
         * 
         * Timeline:
         * Before: [Barcelona] ──→ [New York] ──→ [Singapore] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ [Berlin]
         * After:  [Barcelona] ──→ [New York] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ [Berlin]
         */

        cities.removeFirst();  // Remove from beginning - O(1)
        /*
         * removeFirst() operation:
         * • Update head to point to head.next
         * • That's it! One step, O(1)
         * 
         * After removeFirst():
         * Before: [Barcelona] ──→ [New York] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ [Berlin]
         *         ↑ (head points here)
         * 
         * After:  [New York] ──→ [London] ──→ [Tokyo] ──→ [Paris] ──→ [Berlin]
         *         ↑ (head points here now)
         */

        cities.removeLast();  // Remove from end - O(1)
        /*
         * removeLast() operation:
         * • This should be O(1) because we have tail pointer
         * • Update tail to point to previous node
         * 
         * BUT WAIT! Finding the previous node is O(n) if LinkedList is singly-linked!
         * 
         * Java's LinkedList is DOUBLY-LINKED:
         * Each node has TWO pointers:
         * • next → points to next node
         * • previous → points to previous node
         * 
         * This makes removeLast() O(1) instead of O(n)!
         */

        print("Cities after removing elements:", cities);


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 5: CHECKING IF ELEMENT EXISTS
         * ═════════════════════════════════════════════════════════════════
         */
        
        boolean hasLondon = cities.contains("London");    // O(n)
        int indexOfTokyo = cities.indexOf("Tokyo");       // O(n)
        
        /*
         * contains() and indexOf() both require traversing the list O(n):
         * • Start from head
         * • Compare each node's data with search value
         * • If found, return result
         * • If reach end without finding, return false/not found
         * 
         * This is different from ArrayList where we can use binary search
         * if the list is sorted (but LinkedList doesn't support fast random access)
         */


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 6: BASIC PROPERTIES
         * ═════════════════════════════════════════════════════════════════
         */
        
        int size = cities.size();                         // O(1)
        boolean isEmpty = cities.isEmpty();               // O(1)
        
        /*
         * size() and isEmpty() are O(1) because:
         * • LinkedList stores size as instance variable
         * • Updated during each add/remove operation
         * • Just return the stored value
         */


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 7: ITERATION - THE RIGHT WAY
         * ═════════════════════════════════════════════════════════════════
         */
        
        // ❌ WRONG WAY - Using for loop with get(i)
        /*
        for (int i = 0; i < cities.size(); i++) {
            String city = cities.get(i);  // O(n) for each iteration
        }
        // Total complexity: O(n^2) - very inefficient!
        */

        // ✓ RIGHT WAY - Using iterator
        /*
        Iterator<String> iterator = cities.iterator();
        while (iterator.hasNext()) {
            String city = iterator.next();  // O(1) per iteration
        }
        // Total complexity: O(n) - efficient!
        */

        // ✓ ALSO RIGHT WAY - Using enhanced for loop
        /*
        for (String city : cities) {
            // Uses iterator internally
            // O(n) total
        }
        */


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 8: LINKEDLIST SPECIAL OPERATIONS
         * ═════════════════════════════════════════════════════════════════
         */
        
        // LinkedList has Queue operations (FIFO - First In First Out)
        cities.offer("Mumbai");      // Add to end, returns boolean
        cities.poll();               // Remove from beginning, returns element
        
        /*
         * offer() is like add() but for Queue interface
         * poll() is like removeFirst()
         * peek() is like getFirst()
         * 
         * These make LinkedList useful for implementing Queue!
         */

        // LinkedList has Stack operations (LIFO - Last In First Out)
        cities.push("Amsterdam");    // Add to beginning
        cities.pop();                // Remove from beginning
        
        /*
         * push() is like addFirst()
         * pop() is like removeFirst()
         * peek() is like getFirst()
         * 
         * These make LinkedList useful for implementing Stack!
         */


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 9: PERFORMANCE SUMMARY (BEGINNER LEVEL)
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * LINKEDLIST TIME COMPLEXITY:
         * 
         * Operation           | Time    | Why?
         * ─────────────────────────────────────────────────────────────────
         * add(E)              | O(1)    | Add to end, have tail pointer
         * add(0, E)           | O(1)    | Add to beginning, have head pointer
         * add(i, E)           | O(n)    | Must traverse to index i
         * get(0)              | O(1)*   | Direct access to head
         * get(n)              | O(n)    | Must traverse entire list
         * remove(0)           | O(1)    | Direct removal from head
         * remove(n-1)         | O(1)**  | Direct removal from tail (doubly-linked)
         * contains(E)         | O(n)    | Must traverse entire list
         * iterator.next()     | O(1)    | Move to next node directly
         * 
         * * index 0 is at head, so O(1)
         * ** last element is at tail, so O(1) with doubly-linked
         */


        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 10: WHEN TO USE LINKEDLIST
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * ✓ USE LinkedList WHEN:
         * 
         * 1. Frequent insertions/deletions at beginning or end
         *    Example: Implementing Queue, Stack
         *    
         * 2. Frequent insertions/deletions in middle (less common)
         *    Example: Custom data structures
         *    NOTE: Still need to traverse, so not ideal
         *    
         * 3. You don't need random access by index
         *    Example: Processing elements sequentially
         *    
         * 4. You're implementing LRU Cache, music playlists, etc.
         *    Example: Each song is a node, can quickly remove/add songs
         *
         * 5. You want FIFO (Queue) or LIFO (Stack) behavior
         *    Example: Browser back button, task queue
         *
         *
         * ✗ DO NOT USE LinkedList WHEN:
         * 
         * 1. You frequently need random access by index
         *    Example: scores[5], matrix[i][j]
         *    → Use ArrayList instead (O(1) vs O(n))
         *    
         * 2. You only need to iterate through elements
         *    Example: Adding them up, finding max
         *    → ArrayList is better (better cache locality)
         *    
         * 3. Memory is limited
         *    Example: Embedded systems, mobile apps
         *    → LinkedList uses more memory (extra pointers per node)
         *    
         * 4. You need to sort frequently
         *    Example: Sorting scores every frame
         *    → ArrayList is better
         *    
         * 5. Simple use case with no special needs
         *    Example: Just storing and iterating
         *    → ArrayList is simpler and faster
         */

        print("Final cities list:", cities);
    }

    // Helper method to print LinkedList nicely
    private static void print(String label, LinkedList<String> list) {
        System.out.println("\n" + label);
        System.out.println("Size: " + list.size());
        System.out.print("Elements: ");
        for (String element : list) {
            System.out.print(element + " → ");
        }
        System.out.println("null");
    }
}

