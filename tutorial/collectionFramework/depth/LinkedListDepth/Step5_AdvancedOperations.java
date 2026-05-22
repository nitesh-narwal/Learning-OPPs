package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.*;

public class Step5_AdvancedOperations {
    
    static void main(String[] args) {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║          STEP 5: ADVANCED OPERATIONS WITH LINKEDLIST         ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * In this step, we learn:
         * 1. Queue operations (FIFO)
         * 2. Stack operations (LIFO)
         * 3. Deque (Double-ended queue)
         * 4. ListIterator - backward iteration
         * 5. Performance patterns
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 1: LINKEDLIST AS QUEUE (FIFO)
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     LINKEDLIST AS QUEUE - FIFO (First In First Out)            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        LinkedList<String> queue = new LinkedList<>();
        
        // Queue operations
        queue.add("Task1");      // Add to end (enqueue)
        queue.add("Task2");
        queue.add("Task3");
        queue.add("Task4");
        
        /*
         * Queue visualization (FIFO):
         *   Front/Head ──→ [Task1] ──→ [Task2] ──→ [Task3] ──→ [Task4] ←── Back/Tail
         *   
         *   Task1 was added first, will be removed first
         *   Task4 was added last, will be removed last
         */

        print("Queue after adding tasks:", queue);

        // Better Queue methods (more explicit)
        queue.offer("Task5");        // O(1) - add to end
        String nextTask = queue.poll();  // O(1) - remove from beginning
        
        /*
         * offer() vs add():
         * • offer(): Queue method, returns boolean (for bounded queues)
         * • add(): Collection method, throws exception if fails
         * • Both are O(1) for LinkedList
         * 
         * poll() vs remove():
         * • poll(): Queue method, returns null if empty
         * • remove(): throws exception if empty
         * • Both are O(1) for LinkedList
         * 
         * FIFO Behavior:
         *   add to end ──→ [1] [2] [3] [4] ← remove from front
         */

        print("Queue after offer() and poll():", queue);

        // Peek at front without removing
        String peeked = queue.peek();     // O(1) - peek from front
        String peekedLast = queue.peekLast();  // O(1) - peek from end
        
        print("Queue after peeking:", queue);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 2: LINKEDLIST AS STACK (LIFO)
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     LINKEDLIST AS STACK - LIFO (Last In First Out)             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        LinkedList<String> stack = new LinkedList<>();
        
        // Stack operations
        stack.push("Page1");      // Add to front
        stack.push("Page2");
        stack.push("Page3");
        stack.push("Page4");
        
        /*
         * Stack visualization (LIFO):
         *   Top/Front ──→ [Page4] ──→ [Page3] ──→ [Page2] ──→ [Page1] ←── Bottom/Back
         *   
         *   Page4 was added last, will be removed first (go back button)
         *   Page1 was added first, will be removed last
         */

        print("Stack after pushing pages:", stack);

        // Pop from stack (remove from front)
        String previousPage = stack.pop();  // O(1) - remove from front
        
        /*
         * push() vs add():
         * • push(): Stack method, adds to FRONT (head)
         * • add(): adds to BACK (tail)
         * • Both are O(1) for LinkedList
         * 
         * pop() vs remove():
         * • pop(): Stack method, removes from FRONT, throws exception if empty
         * • remove(): also removes from FRONT, throws exception if empty
         * • Both are O(1) for LinkedList
         * 
         * LIFO Behavior (Like browser back button):
         *   push to front → [4] [3] [2] [1] ← pop from front
         *   Last In = First Out
         */

        print("Stack after pop():", stack);

        // Peek at top without removing
        String topPage = stack.peek();  // O(1) - peek front
        
        print("Stack after peeking:", stack);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 3: DEQUE - DOUBLE-ENDED QUEUE
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     DEQUE - OPERATIONS AT BOTH ENDS                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        Deque<Integer> deque = new LinkedList<>();
        
        // Add to both ends - All O(1)
        deque.addFirst(10);      // Add to front
        deque.addLast(20);       // Add to back
        deque.addFirst(5);
        deque.addLast(25);
        
        /*
         * Deque visualization:
         *   Front ──→ [5] ──→ [10] ──→ [20] ──→ [25] ←── Back
         *   
         * Deque allows:
         * • Add at both ends: O(1)
         * • Remove from both ends: O(1)
         * • Peek at both ends: O(1)
         * • Get from both ends: O(1)
         */

        print("Deque after adding to both ends:", deque);

        // Remove from both ends - All O(1)
        Integer removedFromFront = deque.removeFirst();  // O(1)
        Integer removedFromBack = deque.removeLast();    // O(1)
        
        print("Deque after removing from both ends:", deque);

        // Peek at both ends - All O(1)
        Integer front = deque.peekFirst();   // O(1)
        Integer back = deque.peekLast();     // O(1)
        
        print("Deque after peeking:", deque);

        /*
         * DEQUE is perfect for:
         * • LRU Cache (add to back when accessed, remove from front)
         * • Sliding window problems
         * • Task scheduling with priority
         * • Palindrome checking
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 4: LISTITERATOR - BIDIRECTIONAL ITERATION
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     LISTITERATOR - ITERATE FORWARD AND BACKWARD                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        LinkedList<String> colors = new LinkedList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Yellow");
        colors.add("Purple");
        
        System.out.println("Original list: " + colors);
        System.out.println();

        // FORWARD ITERATION using ListIterator
        System.out.println("Forward iteration:");
        ListIterator<String> iterator = colors.listIterator();
        while (iterator.hasNext()) {
            String color = iterator.next();  // O(1) - move to next
            System.out.println("  → " + color);
        }
        System.out.println();
        
        /*
         * ListIterator maintains position in list:
         * Option: next(), previous(), hasNext(), hasPrevious()
         * Performance: O(1) per iteration step
         */

        // BACKWARD ITERATION using ListIterator
        System.out.println("Backward iteration (reverse order):");
        ListIterator<String> reverseIterator = colors.listIterator(colors.size());
        while (reverseIterator.hasPrevious()) {
            String color = reverseIterator.previous();  // O(1) - move to previous
            System.out.println("  ← " + color);
        }
        System.out.println();
        
        /*
         * Why ListIterator is powerful:
         * • Can iterate in both directions
         * • Can add/remove while iterating
         * • Maintains current position
         * • O(1) per step in both directions
         * 
         * This is why LinkedList is good for:
         * • Checking palindromes
         * • Processing data bidirectionally
         */

        // MODIFICATION during iteration
        System.out.println("Modifying while iterating:");
        ListIterator<String> modifyIterator = colors.listIterator();
        while (modifyIterator.hasNext()) {
            String color = modifyIterator.next();
            if (color.equals("Green")) {
                modifyIterator.remove();  // Remove "Green"
                modifyIterator.add("Lime");  // Add "Lime"
            }
        }
        print("After modification:", colors);

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 5: LINKEDLIST FOR SPECIFIC ALGORITHMS
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     LINKEDLIST FOR COMMON ALGORITHMS/PATTERNS                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // PATTERN 1: Simple Queue
        System.out.println("Pattern 1: Simple Queue");
        System.out.println("─────────────────────────");
        LinkedList<String> taskQueue = new LinkedList<>();
        taskQueue.add("Print");
        taskQueue.add("Save");
        taskQueue.add("Send");
        System.out.println("Tasks: " + taskQueue);
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.poll();
            System.out.println("Processing: " + task + " (O(1) removal from front)");
        }
        System.out.println();

        // PATTERN 2: Browser History (Stack)
        System.out.println("Pattern 2: Browser History");
        System.out.println("──────────────────────────");
        LinkedList<String> history = new LinkedList<>();
        history.push("google.com");
        history.push("github.com");
        history.push("stackoverflow.com");
        System.out.println("Visit order: " + history);
        System.out.println("Back button:");
        while (!history.isEmpty()) {
            String page = history.pop();
            System.out.println("  Back to: " + page + " (O(1) removal from front)");
        }
        System.out.println();

        // PATTERN 3: Sliding Window
        System.out.println("Pattern 3: Sliding Window (store last 3 elements)");
        System.out.println("──────────────────────────────────────────────");
        Deque<Integer> window = new LinkedList<>();
        int[] numbers = {1, 2, 3, 4, 5, 6, 7};
        int windowSize = 3;
        
        for (int num : numbers) {
            window.addLast(num);  // O(1) add to end
            if (window.size() > windowSize) {
                window.removeFirst();  // O(1) remove from front
            }
            System.out.println("Window: " + window);
        }
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 6: PERFORMANCE PATTERNS FOR LINKEDLIST
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     PERFORMANCE PATTERNS - WHAT TO DO AND AVOID                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("✓ DO THIS (Efficient O(n)):");
        System.out.println("──────────────────────────");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(\"A\");");
        System.out.println("list.add(\"B\");");
        System.out.println("for (String s : list) {  // Iterator internally");
        System.out.println("    // Process s");
        System.out.println("}");
        System.out.println("// Time: O(n), Uses iterator, each step O(1)");
        System.out.println();

        System.out.println("✗ DON'T DO THIS (Inefficient O(n²)):");
        System.out.println("──────────────────────────────────────");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(\"A\");");
        System.out.println("list.add(\"B\");");
        System.out.println("for (int i = 0; i < list.size(); i++) {");
        System.out.println("    String s = list.get(i);  // O(n) each time!");
        System.out.println("}");
        System.out.println("// Time: O(n²), get(i) is O(n)!");
        System.out.println();

        System.out.println("✓ EFFICIENT OPERATIONS:");
        System.out.println("──────────────────────");
        System.out.println("• addFirst(E) / addLast(E) → O(1)");
        System.out.println("• removeFirst() / removeLast() → O(1)");
        System.out.println("• getFirst() / getLast() → O(1)");
        System.out.println("• iterator.next() / previous() → O(1)");
        System.out.println("• Use as Queue or Stack → O(1) operations");
        System.out.println();

        System.out.println("✗ INEFFICIENT OPERATIONS:");
        System.out.println("────────────────────────");
        System.out.println("• get(i) in loop → O(n²) total");
        System.out.println("• add(i, E) in middle → O(n)");
        System.out.println("• remove(i) in middle → O(n)");
        System.out.println("• contains(E) / indexOf(E) → O(n)");
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * SECTION 7: SUMMARY OF ADVANCED PATTERNS
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     QUICK REFERENCE - ADVANCED OPERATIONS                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("QUEUE OPERATIONS (FIFO - First In First Out):");
        System.out.println("  add(E) / offer(E)  → Add to end (O(1))");
        System.out.println("  remove() / poll()  → Remove from front (O(1))");
        System.out.println("  element() / peek() → Look at front (O(1))");
        System.out.println();

        System.out.println("STACK OPERATIONS (LIFO - Last In First Out):");
        System.out.println("  push(E)  → Add to front (O(1))");
        System.out.println("  pop()    → Remove from front (O(1))");
        System.out.println("  peek()   → Look at front (O(1))");
        System.out.println();

        System.out.println("DEQUE OPERATIONS (Both ends):");
        System.out.println("  addFirst(E) / addLast(E)   → Add (O(1))");
        System.out.println("  removeFirst() / removeLast() → Remove (O(1))");
        System.out.println("  getFirst() / getLast()     → Access (O(1))");
        System.out.println();

        System.out.println("BIDIRECTIONAL ITERATION:");
        System.out.println("  ListIterator.next()     → Move forward (O(1))");
        System.out.println("  ListIterator.previous() → Move backward (O(1))");
        System.out.println("  Total for all elements  → O(n)");
    }

    private static void print(String label, Collection<?> list) {
        System.out.println(label);
        System.out.println("  " + list);
    }
}

