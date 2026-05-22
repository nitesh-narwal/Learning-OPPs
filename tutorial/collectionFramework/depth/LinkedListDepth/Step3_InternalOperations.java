package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;

public class Step3_InternalOperations {
    
    static void main(String[] args) {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║     STEP 3: INTERNAL OPERATIONS AND COMPLEXITY ANALYSIS      ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * In this step, we learn:
         * 1. How each operation works internally
         * 2. Time complexity of each operation
         * 3. Why some operations are fast and others slow
         * 4. Best and worst case scenarios
         * 5. How to choose operations wisely
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 1: ADD OPERATIONS - DETAILED ANALYSIS
         * ═════════════════════════════════════════════════════════════════
         */
        
        LinkedList<String> fruits = new LinkedList<>();
        
        // OPERATION 1: add(E) - Add to end
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        
        /*
         * COMPLEXITY: O(1) - CONSTANT TIME
         * 
         * WHY O(1)?
         * • LinkedList has direct reference to TAIL node
         * • To add at end:
         *   1. Create new node
         *   2. Update old tail's next pointer (O(1))
         *   3. Update new tail's prev pointer (O(1))
         *   4. Update LinkedList's tail reference (O(1))
         *   5. Increment size (O(1))
         * • Total: 5 constant operations = O(1)
         * 
         * STEP BY STEP:
         * Before: head ──→ [Apple] ←→ [Banana] ←→ [Cherry] ←→ [Date] ←→ tail
         * 
         * add("Date"):
         * Step 1: newNode = Node("Date")
         * Step 2: oldTail("Date").next = newNode
         * Step 3: newNode.prev = oldTail("Date")
         * Step 4: tail = newNode
         * 
         * After:  head ──→ [Apple] ←→ [Banana] ←→ [Cherry] ←→ [Date] ←→ newNode ←→ tail
         */

        print("After add(E) at end:", fruits);

        // OPERATION 2: addFirst(E) - Add to beginning
        fruits.addFirst("Avocado");
        
        /*
         * COMPLEXITY: O(1) - CONSTANT TIME
         * 
         * WHY O(1)?
         * • LinkedList has direct reference to HEAD node
         * • To add at beginning:
         *   1. Create new node
         *   2. Update old head's prev pointer (O(1))
         *   3. Update new node's next pointer (O(1))
         *   4. Update LinkedList's head reference (O(1))
         *   5. Increment size (O(1))
         * • Total: 5 constant operations = O(1)
         * 
         * STEP BY STEP:
         * Before: head ──→ [Apple] ←→ ... ←→ [Date] ←→ tail
         * 
         * addFirst("Avocado"):
         * Step 1: newNode = Node("Avocado")
         * Step 2: newNode.next = head("Apple")
         * Step 3: head("Apple").prev = newNode
         * Step 4: head = newNode
         * 
         * After:  head ──→ [Avocado] ←→ [Apple] ←→ ... ←→ [Date] ←→ tail
         */

        print("After addFirst(E):", fruits);

        // OPERATION 3: add(int index, E) - Add at specific index
        fruits.add(2, "Blueberry");  // Add at index 2
        
        /*
         * COMPLEXITY: O(n) - LINEAR TIME
         * 
         * WHY O(n)?
         * • To add at index 2, we must:
         *   1. Traverse to index 2:
         *      - Check if index < size/2
         *      - If yes: traverse from head (forward)
         *      - If no: traverse from tail (backward)
         *   2. Once at index 2, insert (O(1))
         * 
         * TRAVERSAL:
         * • In worst case, we traverse ~n/2 elements
         * • Each traversal step is O(1)
         * • Total traversal: O(n/2) = O(n)
         * • Insertion: O(1)
         * • Overall: O(n)
         * 
         * OPTIMIZATION IN ACTION:
         * • index = 2, size = 5
         * • index < size/2? → 2 < 2.5? → YES
         * • Traverse forward from head: Apple → Banana → Blueberry location
         * • Only 2 steps instead of 3!
         * 
         * STEP BY STEP:
         * Before: head ──→ [Avocado] ←→ [Apple] ←→ [Banana] ←→ [Cherry] ←→ [Date] ←→ tail
         *         index:  0           1            2            3            4
         * 
         * add(2, "Blueberry"):
         * Step 1: Find node at index 1 (Banana)
         *   - Start: current = head (Avocado), count = 0
         *   - Move: current = Avocado.next (Apple), count = 1
         *   - Move: current = Apple.next (Banana), count = 2
         *   - Target reached
         * 
         * Step 2: Create newNode("Blueberry")
         * 
         * Step 3: Insert:
         *   - newNode.prev = prev (Apple)
         *   - newNode.next = current (Cherry)
         *   - Apple.next = newNode
         *   - Cherry.prev = newNode
         * 
         * After:  head ──→ [Avocado] ←→ [Apple] ←→ [Blueberry] ←→ [Banana] ←→ [Cherry] ←→ [Date] ←→ tail
         *         index:  0           1           2               3            4            5
         */

        print("After add(2, E):", fruits);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 2: GET OPERATIONS - DETAILED ANALYSIS
         * ═════════════════════════════════════════════════════════════════
         */
        
        // OPERATION 4: get(int index) - Access element at index
        String fruitAt2 = fruits.get(2);  // Get element at index 2
        
        /*
         * COMPLEXITY: O(n) - LINEAR TIME (WORST CASE)
         * 
         * WHY O(n)?
         * • To get element at index 2:
         *   1. If index < size/2: traverse from head
         *   2. If index >= size/2: traverse from tail
         *   3. Stop at the target index
         * 
         * PERFORMANCE:
         * • Best case: O(1) - accessing head or tail
         * • Average case: O(n/4) - typically closer to ends
         * • Worst case: O(n) - accessing middle with large list
         * 
         * EXAMPLE WITH SIZE = 10:
         * • get(0): 0 steps → O(1)
         * • get(9): 0 steps from tail → O(1)
         * • get(3): min(3, 10-3) = min(3, 7) = 3 steps → O(1)
         * • get(5): min(5, 10-5) = min(5, 5) = 5 steps → O(n)
         * 
         * This optimization shows LinkedList traverses from the NEARER END:
         *   if (index < size >> 1)  // index < size/2?
         *       traverse from head
         *   else
         *       traverse from tail
         */

        // OPERATION 5: getFirst() / getLast() - Get from ends
        String first = fruits.getFirst();   // O(1) - direct access to head
        String last = fruits.getLast();     // O(1) - direct access to tail
        
        /*
         * COMPLEXITY: O(1) - CONSTANT TIME
         * 
         * WHY O(1)?
         * • getFirst(): return head.data → O(1)
         * • getLast(): return tail.data → O(1)
         * • No traversal needed!
         */

        // OPERATION 6: peek() / peekFirst() / peekLast() - Peek without removing
        String peekFirst = fruits.peek();     // O(1)
        String peekLast = fruits.peekLast();  // O(1)
        
        /*
         * COMPLEXITY: O(1) - CONSTANT TIME
         * 
         * Difference from get():
         * • peek(): safe access (returns null if empty)
         * • get(): throws exception if empty
         * • Both are O(1) when accessing ends
         * • get(index): O(n) for arbitrary index
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 3: REMOVE OPERATIONS - DETAILED ANALYSIS
         * ═════════════════════════════════════════════════════════════════
         */
        
        // OPERATION 7: remove(int index) - Remove at specific index
        Object removedAt2 = fruits.remove(2);  // Remove element at index 2
        
        /*
         * COMPLEXITY: O(n) - LINEAR TIME
         * 
         * WHY O(n)?
         * • Similar to get(index), must traverse to find position
         * • Uses same optimization: traverse from nearer end
         * • Once found, removal is O(1)
         * • Overall: O(n) dominated by traversal
         * 
         * STEP BY STEP:
         * Before: [Avocado] ←→ [Apple] ←→ [Blueberry] ←→ [Banana] ←→ [Cherry] ←→ [Date]
         *         index: 0      1            2               3            4            5
         * 
         * remove(2):
         * Step 1: Find node at index 2 (Blueberry)
         * Step 2: Get prev and next:
         *   - prev = node at index 1 (Apple)
         *   - next = node at index 3 (Banana)
         * Step 3: Skip the node:
         *   - Apple.next = Banana
         *   - Banana.prev = Apple
         * Step 4: Return the data ("Blueberry")
         * 
         * After:  [Avocado] ←→ [Apple] ←→ [Banana] ←→ [Cherry] ←→ [Date]
         *         index: 0      1           2            3            4
         */

        print("After remove(2):", fruits);

        // OPERATION 8: removeFirst() / removeLast() - Remove from ends
        fruits.removeFirst();  // O(1)
        fruits.removeLast();   // O(1)
        
        /*
         * COMPLEXITY: O(1) - CONSTANT TIME
         * 
         * WHY O(1)?
         * removeFirst():
         * • Update head = head.next
         * • Update head.prev = null
         * • Decrement size
         * • All O(1)
         * 
         * removeLast():
         * • Update tail = tail.prev (because doubly-linked!)
         * • Update tail.next = null
         * • Decrement size
         * • All O(1)
         * 
         * KEY INSIGHT: If LinkedList were singly-linked, removeLast() would be O(n)!
         * The doubly-linked structure makes it O(1).
         */

        print("After removeFirst() and removeLast():", fruits);

        // OPERATION 9: poll() / pollFirst() / pollLast()
        String polled = fruits.poll();      // O(1) - safe removeFirst
        
        /*
         * COMPLEXITY: O(1) - CONSTANT TIME
         * 
         * Difference from remove():
         * • poll(): returns null if empty, doesn't throw exception
         * • remove(): throws exception if empty
         * • Both use same underlying mechanics
         */

        // OPERATION 10: remove(Object o) - Remove first occurrence
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Apple");
        fruits.remove("Apple");  // Remove first "Apple"
        
        /*
         * COMPLEXITY: O(n) - LINEAR TIME
         * 
         * WHY O(n)?
         * • Must traverse entire list to find "Apple"
         * • In worst case, traverse entire n elements
         * • Once found, removal is O(1)
         * • Overall: O(n)
         * 
         * STEP BY STEP:
         * Before: [Avocado] ←→ [Banana] ←→ [Apple] ←→ [Banana] ←→ [Apple]
         * 
         * remove("Apple"):
         * Step 1: Traverse from head comparing each element
         * Step 2: Find first match: Apple at index 2
         * Step 3: Remove it (update pointers, O(1))
         * Step 4: Return true (element was removed)
         * 
         * After:  [Avocado] ←→ [Banana] ←→ [Banana] ←→ [Apple]
         */

        print("After remove('Apple'):", fruits);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 4: SEARCH OPERATIONS - DETAILED ANALYSIS
         * ═════════════════════════════════════════════════════════════════
         */
        
        // OPERATION 11: contains(Object o) - Check if element exists
        boolean hasApple = fruits.contains("Apple");  // O(n)
        
        /*
         * COMPLEXITY: O(n) - LINEAR TIME
         * 
         * WHY O(n)?
         * • Must traverse entire list until found or reach end
         * • No shortcuts or optimizations possible
         * • Linear search through all elements
         * 
         * ALGORITHM:
         * for (Node node = head; node != null; node = node.next) {
         *     if (node.data.equals("Apple")) {
         *         return true;
         *     }
         * }
         * return false;
         */

        // OPERATION 12: indexOf(Object o) / lastIndexOf(Object o)
        int indexOfBanana = fruits.indexOf("Banana");      // O(n)
        int lastIndexOfBanana = fruits.lastIndexOf("Banana"); // O(n)
        
        /*
         * COMPLEXITY: O(n) - LINEAR TIME
         * 
         * indexOf():
         * • Traverse from head until found
         * • Return index of first match
         * • If not found, return -1
         * 
         * lastIndexOf():
         * • Traverse from tail until found
         * • Return index of last match
         * • Still O(n) but searches backward
         * 
         * ALGORITHM (indexOf):
         * int index = 0;
         * for (Node node = head; node != null; node = node.next) {
         *     if (node.data.equals("Banana")) {
         *         return index;
         *     }
         *     index++;
         * }
         * return -1;
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 5: ITERATION - MOST EFFICIENT WAY TO READ
         * ═════════════════════════════════════════════════════════════════
         */
        
        // OPERATION 13: Iteration using enhanced for loop
        /*
        for (String fruit : fruits) {
            // Total complexity: O(n)
            // Each next() call is O(1)
        }
        */
        
        // OPERATION 14: Iteration using Iterator
        /*
        Iterator<String> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            String fruit = iterator.next();  // O(1) per call
            // Total complexity: O(n)
        }
        */
        
        // OPERATION 15: Iteration using ListIterator (can go backward)
        /*
        ListIterator<String> listIter = fruits.listIterator(fruits.size());
        while (listIter.hasPrevious()) {
            String fruit = listIter.previous();  // O(1) per call
            // Total complexity: O(n)
        }
        */
        
        /*
         * COMPLEXITY: O(1) per element, O(n) total traversal
         * 
         * WHY THIS IS BEST WAY TO READ:
         * • Iterator maintains internal pointer
         * • next() just moves pointer and returns data → O(1)
         * • No repeated traversal like get(i) in loop would do
         * 
         * ❌ WRONG: Using loop with get(i)
         * for (int i = 0; i < fruits.size(); i++) {
         *     String fruit = fruits.get(i);  // O(n) each time!
         * }
         * Total: O(n^2) - TERRIBLE!
         * 
         * ✓ RIGHT: Using iterator
         * for (String fruit : fruits) {
         *     // Uses iterator internally
         * }
         * Total: O(n) - GOOD!
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 6: OTHER OPERATIONS
         * ═════════════════════════════════════════════════════════════════
         */
        
        int size = fruits.size();           // O(1)
        boolean empty = fruits.isEmpty();   // O(1)
        fruits.clear();                     // O(n) - must release all nodes
        
        /*
         * size(): O(1)
         * • LinkedList stores size as instance variable
         * • Just return the stored value
         * 
         * isEmpty(): O(1)
         * • Check if size == 0 or head == null
         * 
         * clear(): O(n)
         * • Must traverse and null out all nodes
         * • Helps garbage collector
         * • But: return statement is O(1) in modern Java
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 7: COMPLETE COMPLEXITY CHEAT SHEET
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * LINKEDLIST TIME COMPLEXITY:
         * 
         * ┌──────────────────────┬──────────┬──────────────────────────┐
         * │ Operation            │Complexity│ Notes                    │
         * ├──────────────────────┼──────────┼──────────────────────────┤
         * │ add(E)               │ O(1)     │ Add to end (tail)        │
         * │ addFirst(E)          │ O(1)     │ Add to beginning (head)  │
         * │ addLast(E)           │ O(1)     │ Same as add()            │
         * │ add(i, E)            │ O(n)     │ Must traverse to index i │
         * │                      │          │ Uses optimization: near  │
         * │                      │          │ end is faster            │
         * │ get(i)               │ O(n)     │ Must traverse to index i │
         * │                      │          │ Optimization: near end   │
         * │ getFirst()           │ O(1)     │ Direct access to head    │
         * │ getLast()            │ O(1)     │ Direct access to tail    │
         * │ peek()               │ O(1)     │ Same as getFirst()       │
         * │ peekLast()           │ O(1)     │ Same as getLast()        │
         * │ remove(i)            │ O(n)     │ Must traverse to index i │
         * │                      │          │ Then remove (O(1))       │
         * │ removeFirst()        │ O(1)     │ Update head pointer      │
         * │ removeLast()         │ O(1)     │ Update tail pointer      │
         * │ poll()               │ O(1)     │ Same as removeFirst()    │
         * │ pollLast()           │ O(1)     │ Same as removeLast()     │
         * │ remove(E)            │ O(n)     │ Must find element        │
         * │ contains(E)          │ O(n)     │ Must search entire list  │
         * │ indexOf(E)           │ O(n)     │ Must search entire list  │
         * │ lastIndexOf(E)       │ O(n)     │ Must search entire list  │
         * │ size()               │ O(1)     │ Stored in variable       │
         * │ isEmpty()            │ O(1)     │ Check size == 0          │
         * │ iterator.next()      │ O(1)     │ Move pointer + return    │
         * │ clear()              │ O(n)     │ Must null all references │
         * └──────────────────────┴──────────┴──────────────────────────┘
         */
    }

    private static void print(String label, LinkedList<String> list) {
        System.out.println("\n" + label);
        System.out.println("Size: " + list.size());
        System.out.print("Elements: ");
        if (list.isEmpty()) {
            System.out.println("[empty]");
        } else {
            for (String element : list) {
                System.out.print(element + " → ");
            }
            System.out.println("null");
        }
    }
}

