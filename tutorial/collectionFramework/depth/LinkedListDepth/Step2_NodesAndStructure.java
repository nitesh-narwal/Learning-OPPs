package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.LinkedList;

public class Step2_NodesAndStructure {
    
    static void main(String[] args) {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║    STEP 2: NODES AND INTERNAL STRUCTURE OF LINKEDLIST        ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * In this step, we understand:
         * 1. What is a Node?
         * 2. Singly-linked vs Doubly-linked lists
         * 3. How Java's LinkedList is structured internally
         * 4. Memory layout of LinkedList
         * 5. Visual representation of operations
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 1: WHAT IS A NODE?
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * A NODE is the basic building block of a LinkedList.
         * 
         * In a SINGLY-LINKED list, each node has:
         * ┌────────────────────────┐
         * │        NODE            │
         * ├────────────────────────┤
         * │ data: "Hello"          │  → Actual value stored
         * │ next: ◻────────────────┼──→ Reference to next node
         * └────────────────────────┘
         *
         * Java pseudo-code of a node:
         * 
         *     private class Node {
         *         E data;
         *         Node next;
         *         
         *         Node(E data) {
         *             this.data = data;
         *             this.next = null;  // Initially points to nothing
         *         }
         *     }
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 2: SINGLY-LINKED LIST STRUCTURE
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * A SINGLY-LINKED LIST has nodes connected in ONE direction:
         * 
         * head ──→ [A] ──→ [B] ──→ [C] ──→ [D] ──→ null
         *
         * Each arrow (──→) represents a "next" pointer.
         * 
         * Properties:
         * • Can only traverse in one direction (forward)
         * • To go backward, you must traverse from head
         * • Removing last element requires finding second-last (O(n))
         * • Memory: One pointer per node (one "next" reference)
         * 
         * Problems:
         * • Backward traversal is expensive
         * • Removing from end is expensive
         * • No bidirectional iteration
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 3: DOUBLY-LINKED LIST STRUCTURE (Java's LinkedList!)
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * A DOUBLY-LINKED LIST has nodes connected in BOTH directions:
         * 
         *  null ←──[A]──→ [B] ←──→ [C] ←──→ [D] ←─→ null
         *   ↑  head→         tail←  ↑
         *
         * Better visualization:
         * 
         * null ◻────  head ──→ [A] ──→ [B] ──→ [C] ──→ [D] ──→ ◻
         * ◻ ◻ ◻ ◻ ◻ ◻ ◻     ◻    ◻    ◻    ◻    ◻      ◻ ◻ ◻
         *   ←────────────┘ ← ┘  ← ┘  ← ┘  ← ┘        tail ←───
         *
         * Each node has TWO pointers:
         * ┌───────────────────┐
         * │     NODE          │
         * ├───────────────────┤
         * │ prev ◻ ◻ ◻ ◻ ◻   │ ← Reference to PREVIOUS node
         * │ data: "Hello"     │ ← Actual value
         * │ next ◻ ◻ ◻ ◻ ◻   │ ← Reference to NEXT node
         * └───────────────────┘
         *
         * Java pseudo-code:
         * 
         *     private class Node {
         *         Node prev;      // Points to previous node
         *         E data;         // Actual value
         *         Node next;      // Points to next node
         *         
         *         Node(E data) {
         *             this.prev = null;
         *             this.data = data;
         *             this.next = null;
         *         }
         *     }
         *
         * Advantages:
         * • Can traverse in BOTH directions (forward and backward)
         * • Removing from end is O(1) (because we have tail)
         * • Backward traversal is efficient O(n)
         * • Support for ListIterator which can go backward
         *
         * Disadvantage:
         * • Uses more memory (two pointers per node instead of one)
         * • Each insert/delete needs to maintain two pointers
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 4: JAVA'S LINKEDLIST STRUCTURE
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * Java's LinkedList is a DOUBLY-LINKED, CIRCULAR list:
         * 
         * LinkedList object maintains:
         * • head: First node in the list
         * • tail: Last node in the list  
         * • size: Number of elements
         *
         * Memory diagram for LinkedList<String> list = new LinkedList<>();
         * after add("A"), add("B"), add("C"):
         *
         * LinkedList object:
         * ┌──────────────────────┐
         * │ head: Node('A')   ──┐│
         * │ tail: Node('C')   ──┐│
         * │ size: 3             ││
         * └──────────────────────┘
         *         │              │
         *         └──────┬───────┘
         *                ↓
         *    null ◻──[A]──┐
         *                 ↓
         *    ┌──[B]──┐
         *    │       ↓
         *    └──[C]──┐
         *           ↓
         *          null
         *
         * Where each node looks like:
         * [A] = Node { prev: null, data: "A", next: Node("B") }
         * [B] = Node { prev: Node("A"), data: "B", next: Node("C") }
         * [C] = Node { prev: Node("B"), data: "C", next: null }
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 5: EXPLORING LINKEDLIST STRUCTURE (HANDS ON)
         * ═════════════════════════════════════════════════════════════════
         */
        
        LinkedList<Integer> numbers = new LinkedList<>();
        
        numbers.add(10);    // Create Node with data=10, prev=null, next=null
        numbers.add(20);    // Create Node with data=20, prev=Node(10), next=null
        numbers.add(30);    // Create Node with data=30, prev=Node(20), next=null
        
        /*
         * After these operations, the LinkedList looks like:
         * 
         * head ──→ [10] ←── [20] ←── [30] ←── tail
         *         ↓               ↓              ↓
         *         │ next          │ next         │ next
         *         ↓               ↓              ↓
         *        Node(20)      Node(30)        null
         * 
         * Detailed view:
         * 
         * Node 0 (head):
         *   prev = null
         *   data = 10
         *   next = → points to Node 1
         * 
         * Node 1:
         *   prev = ← points to Node 0
         *   data = 20
         *   next = → points to Node 2
         * 
         * Node 2 (tail):
         *   prev = ← points to Node 1
         *   data = 30
         *   next = null
         */

        print("Numbers list:", numbers);
        /*
         * Output will be:
         * Size: 3
         * Elements: 10 → 20 → 30 → null
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 6: HOW ADD() WORKS INTERNALLY
         * ═════════════════════════════════════════════════════════════════
         */
        
        numbers.add(40);
        
        /*
         * When we call numbers.add(40), here's what happens:
         * 
         * STEP 1: Create new node
         *   newNode = Node { prev: null, data: 40, next: null }
         *
         * STEP 2: Update current tail's next pointer
         *   oldTail(30).next = newNode
         *
         * STEP 3: Update newNode's prev pointer
         *   newNode.prev = oldTail(30)
         *
         * STEP 4: Update tail reference
         *   tail = newNode
         *
         * STEP 5: Increment size
         *   size = 4
         * 
         * Timeline:
         * Before: [10] ←→ [20] ←→ [30] ←→ null
         *                          (tail)
         *
         * After:  [10] ←→ [20] ←→ [30] ←→ [40] ←→ null
         *                               (old tail) (new tail)
         * 
         * This process is O(1) because:
         * • We have direct access to tail
         * • No traversal needed
         * • Just update pointers
         */

        print("After add(40):", numbers);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 7: HOW ADD(INDEX, VALUE) WORKS INTERNALLY
         * ═════════════════════════════════════════════════════════════════
         */
        
        numbers.add(2, 25);  // Add 25 at index 2 (between 20 and 30)
        
        /*
         * When we call numbers.add(2, 25):
         * 
         * STEP 1: Traverse to find node at index 2
         *   current = head
         *   count = 0
         *   Loop: count++ until count == index
         *   After loop: current = Node(30)
         *
         * STEP 2: Get the previous node
         *   prev = current.prev  // This is Node(20)
         *   (Using doubly-linked list, we can get prev in O(1))
         *
         * STEP 3: Create new node
         *   newNode = Node { prev: prev, data: 25, next: current }
         *
         * STEP 4: Update all pointers
         *   prev.next = newNode        // Node(20) points to newNode
         *   current.prev = newNode     // Node(30) points back to newNode
         *
         * STEP 5: Increment size
         *   size = 5
         *
         * Timeline:
         * Before: [10] ←→ [20] ←→ [30] ←→ [40] ←→ null
         *                   ↓              ↑
         *                   └──────────────┘
         *                    we insert here
         *
         * After:  [10] ←→ [20] ←→ [25] ←→ [30] ←→ [40] ←→ null
         *                          (newly
         *                            added)
         * 
         * This process is O(n) because:
         * • We need to traverse to index 2
         * • Traversal takes O(index) time
         * • For i in the middle, it's O(n/2) which is O(n)
         * • Actual insertion is O(1), but finding position is O(n)
         *
         * Optimization: LinkedList traverses from nearest end!
         * • If index < size/2: traverse from head (forward)
         * • If index >= size/2: traverse from tail (backward)
         * • This cuts worst case in half!
         */

        print("After add(2, 25):", numbers);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 8: HOW REMOVE WORKS INTERNALLY
         * ═════════════════════════════════════════════════════════════════
         */
        
        numbers.remove(2);  // Remove element at index 2 (which is 25)
        
        /*
         * When we call numbers.remove(2):
         * 
         * STEP 1: Traverse to find node at index 2
         *   current = head
         *   count = 0
         *   Loop: count++ until count == index
         *   After loop: current = Node(25)
         *
         * STEP 2: Get previous and next nodes
         *   prev = current.prev    // Node(20)
         *   next = current.next    // Node(30)
         *
         * STEP 3: Update pointers (skip the current node)
         *   prev.next = next       // Node(20) points to Node(30)
         *   next.prev = prev       // Node(30) points back to Node(20)
         *
         * STEP 4: Decrement size
         *   size = 4
         *
         * STEP 5: Return the removed data
         *   return current.data
         *
         * Timeline:
         * Before: [10] ←→ [20] ←→ [25] ←→ [30] ←→ [40]
         *                          ↑
         *                    we remove this
         *
         * After:  [10] ←→ [20] ←→ [30] ←→ [40]
         *
         * This process is O(n) because:
         * • We need to traverse to index 2
         * • Finding position costs O(index)
         * • Actual removal is O(1)
         * 
         * But removing from ends is different:
         * • removeFirst(): O(1) - just update head
         * • removeLast(): O(1) - just update tail (because doubly-linked)
         */

        print("After remove(2):", numbers);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 9: MEMORY OVERHEAD OF LINKEDLIST
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * LinkedList uses MORE memory than ArrayList:
         * 
         * For each element in LinkedList, we have:
         * • The actual data
         * • prev pointer (8 bytes on 64-bit JVM)
         * • next pointer (8 bytes on 64-bit JVM)
         * + Object overhead
         * 
         * Example: LinkedList<Integer> vs ArrayList<Integer> of size 1000
         *
         * ArrayList memory:
         * • One array of 1000 elements: 1000 * (size of Integer wrapper)
         * • Reasonable memory usage
         *
         * LinkedList memory:
         * • 1000 Node objects
         * • Each Node has: prev(8) + Integer(16) + next(8) + overhead(16) = ~48 bytes
         * • Total: 1000 * 48 = ~48 KB just for structure!
         * 
         * Approximate comparison:
         * ArrayList<Integer>(1000): ~4 KB
         * LinkedList<Integer>(1000): ~50 KB
         * 
         * LinkedList uses ~12x more memory!
         * 
         * This is why LinkedList should only be used when you really need it!
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 10: BIDIRECTIONAL TRAVERSAL (DOUBLY-LINKED ADVANTAGE)
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * Because LinkedList is doubly-linked, we can traverse both ways:
         * 
         * Forward traversal:
         *   Iterator<Integer> iter = numbers.iterator();
         *   while (iter.hasNext()) {
         *       Integer num = iter.next();  // Move forward
         *   }
         * 
         * Backward traversal:
         *   ListIterator<Integer> iter = numbers.listIterator(numbers.size());
         *   while (iter.hasPrevious()) {
         *       Integer num = iter.previous();  // Move backward
         *   }
         * 
         * Backward traversal is efficient (O(n)) because:
         * • Each node has a prev pointer
         * • Moving backward is O(1) per step
         * • Total traversal is O(n)
         * 
         * If LinkedList were singly-linked, backward traversal would be O(n^2)!
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 11: VISUAL SUMMARY OF NODE STRUCTURE
         * ═════════════════════════════════════════════════════════════════
         */
        
        /*
         * SINGLY-LINKED NODE:
         * ┌─────────────┐
         * │ data: "Hi"  │
         * │ next: ◻─────┼───→ (next node or null)
         * └─────────────┘
         * Memory: data + 1 pointer
         *
         * DOUBLY-LINKED NODE (Java's LinkedList):
         * ┌─────────────┐
         * │ prev: ◻─────┼───→ (previous node or null)
         * │ data: "Hi"  │
         * │ next: ◻─────┼───→ (next node or null)
         * └─────────────┘
         * Memory: data + 2 pointers
         *
         * Advantage: Can traverse both ways
         * Disadvantage: More memory
         */

        print("Final numbers list:", numbers);
    }

    private static void print(String label, LinkedList<Integer> list) {
        System.out.println("\n" + label);
        System.out.println("Size: " + list.size());
        System.out.print("Elements: ");
        for (Integer element : list) {
            System.out.print(element + " → ");
        }
        System.out.println("null");
    }
}

