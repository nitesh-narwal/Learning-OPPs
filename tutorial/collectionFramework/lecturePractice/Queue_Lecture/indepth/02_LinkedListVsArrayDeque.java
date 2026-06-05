package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;
import java.util.Deque;

/**
 * ============================================================
 *     LINKEDLIST vs ARRAYDEQUE - THE ULTIMATE SHOWDOWN 🥊
 * ============================================================
 *
 * Bhai, dono hi Queue implement karte hain, lekin andar se BILKUL alag hain!
 *
 * LINKEDLIST:
 * - Doubly linked list (har node ke do pointers: prev & next)
 * - Memory mein scattered (ek node yahan, ek node wahan)
 * - Har element ke saath extra memory (2 pointers + object header)
 * - Cache-unfriendly (CPU cache miss zyada hoti hai)
 * 
 * ARRAYDEQUE:
 * - Circular array (contiguous memory block)
 * - Memory efficient (no pointer overhead)
 * - Cache-friendly (CPU cache hit zyada hoti hai)
 * - Auto-resizing (jaise ArrayList)
 * 
 * Industry reality: 99% cases mein ArrayDeque FASTER hai! 🚀
 *
 * ============================================================
 *  PERFORMANCE COMPARISON
 * ============================================================
 *
 * Operation       | LinkedList | ArrayDeque | Winner
 * ----------------|------------|------------|--------
 * offerFirst()    | O(1)       | O(1)       | ArrayDeque (cache-friendly)
 * offerLast()     | O(1)       | O(1)       | ArrayDeque (cache-friendly)
 * pollFirst()     | O(1)       | O(1)       | ArrayDeque (cache-friendly)
 * pollLast()      | O(1)       | O(1)       | ArrayDeque (cache-friendly)
 * get(index)      | O(n)       | O(1)       | ArrayDeque
 * Memory          | High       | Low        | ArrayDeque
 * Thread-safe?    | No         | No         | Both NOT thread-safe
 *
 * ============================================================
 *  WHEN TO USE WHAT?
 * ============================================================
 *
 * USE ARRAYDEQUE when:
 * ✅ You need a general-purpose queue/deque (DEFAULT CHOICE!)
 * ✅ Performance matters (which is always!)
 * ✅ Memory efficiency matters
 * ✅ You're doing stack operations (push/pop)
 *
 * USE LINKEDLIST when:
 * ✅ You need frequent additions/removals in the MIDDLE (iterator.remove())
 * ✅ You're implementing the List interface (need get(index), add(index, element))
 * ✅ Legacy code already uses it (don't optimize prematurely)
 * ✅ You need predictable iterator performance during concurrent modifications
 *
 * Industry tip: Joshua Bloch (Java Collections author) recommends ArrayDeque!
 *
 * ============================================================
 *  INTERNAL WORKING
 * ============================================================
 *
 * LINKEDLIST:
 * 
 *   [Rahul] <--> [Priya] <--> [Amit] <--> [Sneha]
 *      ↑                                      ↑
 *    head                                   tail
 * 
 * Each node:
 *   class Node {
 *       E item;
 *       Node<E> next;   // 8 bytes reference
 *       Node<E> prev;   // 8 bytes reference
 *   }
 * Total overhead per element: ~32 bytes (object header + 2 pointers)
 *
 * ARRAYDEQUE (Circular Array):
 * 
 *   Array: [null, Priya, Amit, Sneha, null, null, Rahul, null]
 *                  ↑head                         ↑tail
 * 
 * - When tail reaches end, it wraps around to beginning (circular!)
 * - When array is full, it doubles in size (like ArrayList)
 * - Resizing cost: O(n), but amortized O(1)
 * - Memory overhead: minimal (just the array)
 *
 */
class LinkedListVsArrayDeque {

     static void main(String[] args) {

        System.out.println("===== LINKEDLIST vs ARRAYDEQUE - COMPREHENSIVE COMPARISON =====\n");

        // ============================================================
        // DEMO 1: Basic Operations Comparison
        // ============================================================

        System.out.println("===== DEMO 1: Basic Operations =====\n");

        // Both implement Queue and Deque interfaces
        Queue<String> linkedQueue = new LinkedList<>();
        Queue<String> arrayQueue = new ArrayDeque<>();

        // Adding elements
        linkedQueue.offer("First");
        linkedQueue.offer("Second");
        linkedQueue.offer("Third");

        arrayQueue.offer("First");
        arrayQueue.offer("Second");
        arrayQueue.offer("Third");

        System.out.println("LinkedList Queue: " + linkedQueue);
        System.out.println("ArrayDeque Queue: " + arrayQueue);

        // Both behave identically for queue operations
        System.out.println("\nPolling from LinkedList: " + linkedQueue.poll());
        System.out.println("Polling from ArrayDeque: " + arrayQueue.poll());

        // ============================================================
        // DEMO 2: Performance Benchmark (The Truth Revealed 🔍)
        // ============================================================

        System.out.println("\n===== DEMO 2: Performance Benchmark =====\n");

        int iterations = 1_000_000;

        // Benchmark 1: Adding elements
        long start = System.nanoTime();
        Queue<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < iterations; i++) {
            linkedList.offer(i);
        }
        long linkedListAddTime = System.nanoTime() - start;

        start = System.nanoTime();
        Queue<Integer> arrayDeque = new ArrayDeque<>();
        for (int i = 0; i < iterations; i++) {
            arrayDeque.offer(i);
        }
        long arrayDequeAddTime = System.nanoTime() - start;

        System.out.println("Adding " + iterations + " elements:");
        System.out.println("  LinkedList: " + linkedListAddTime / 1_000_000 + " ms");
        System.out.println("  ArrayDeque: " + arrayDequeAddTime / 1_000_000 + " ms");
        System.out.println("  Winner: " + (arrayDequeAddTime < linkedListAddTime ? "ArrayDeque 🏆" : "LinkedList 🏆"));

        // Benchmark 2: Removing elements
        start = System.nanoTime();
        while (!linkedList.isEmpty()) {
            linkedList.poll();
        }
        long linkedListRemoveTime = System.nanoTime() - start;

        start = System.nanoTime();
        while (!arrayDeque.isEmpty()) {
            arrayDeque.poll();
        }
        long arrayDequeRemoveTime = System.nanoTime() - start;

        System.out.println("\nRemoving " + iterations + " elements:");
        System.out.println("  LinkedList: " + linkedListRemoveTime / 1_000_000 + " ms");
        System.out.println("  ArrayDeque: " + arrayDequeRemoveTime / 1_000_000 + " ms");
        System.out.println("  Winner: " + (arrayDequeRemoveTime < linkedListRemoveTime ? "ArrayDeque 🏆" : "LinkedList 🏆"));

        // Benchmark 3: Mixed operations
        start = System.nanoTime();
        Queue<Integer> ll = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) {
            ll.offer(i);
            if (i % 3 == 0) ll.poll();
            if (i % 5 == 0) ll.peek();
        }
        long llMixedTime = System.nanoTime() - start;

        start = System.nanoTime();
        Queue<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 100_000; i++) {
            ad.offer(i);
            if (i % 3 == 0) ad.poll();
            if (i % 5 == 0) ad.peek();
        }
        long adMixedTime = System.nanoTime() - start;

        System.out.println("\nMixed operations (100k iterations):");
        System.out.println("  LinkedList: " + llMixedTime / 1_000_000 + " ms");
        System.out.println("  ArrayDeque: " + adMixedTime / 1_000_000 + " ms");
        System.out.println("  Winner: " + (adMixedTime < llMixedTime ? "ArrayDeque 🏆" : "LinkedList 🏆"));

        // ============================================================
        // DEMO 3: Memory Consumption Analysis
        // ============================================================

        System.out.println("\n===== DEMO 3: Memory Consumption =====\n");

        // Rough memory calculation for 1000 Integer objects
        int elements = 1000;
        
        // LinkedList: each node has 3 references (item, next, prev) + object header
        // Object header: 12-16 bytes
        // 3 references: 3 * 8 = 24 bytes (on 64-bit JVM)
        // Total per node: ~40 bytes
        int linkedListMemory = elements * 40 + 16; // 16 bytes for LinkedList object
        
        // ArrayDeque: just the array + object header
        // Array overhead: 12 bytes header + (8 bytes per reference * capacity)
        // Initial capacity for 1000 elements: ~1024 (next power of 2)
        int arrayDequeMemory = 12 + (1024 * 8) + 16; // 16 bytes for ArrayDeque object

        System.out.println("Memory for 1000 Integer elements (approximate):");
        System.out.println("  LinkedList: ~" + linkedListMemory / 1024 + " KB");
        System.out.println("  ArrayDeque: ~" + arrayDequeMemory / 1024 + " KB");
        System.out.println("  Memory Savings: ~" + (linkedListMemory - arrayDequeMemory) / 1024 + " KB with ArrayDeque");

        // ============================================================
        // DEMO 4: Deque Operations (Both Ends)
        // ============================================================

        System.out.println("\n===== DEMO 4: Deque Operations (Both Ends) =====\n");

        // Using as Deque (double-ended queue)
        java.util.Deque<String> linkedDeque = new LinkedList<>();
        java.util.Deque<String> arrayDequeAsDeque = new ArrayDeque<>();

        // Adding to both ends
        linkedDeque.offerFirst("Front-1");
        linkedDeque.offerLast("Back-1");
        linkedDeque.offerFirst("Front-2");
        linkedDeque.offerLast("Back-2");

        arrayDequeAsDeque.offerFirst("Front-1");
        arrayDequeAsDeque.offerLast("Back-1");
        arrayDequeAsDeque.offerFirst("Front-2");
        arrayDequeAsDeque.offerLast("Back-2");

        System.out.println("LinkedList as Deque: " + linkedDeque);
        System.out.println("ArrayDeque as Deque: " + arrayDequeAsDeque);

        // Both produce same output: [Front-2, Front-1, Back-1, Back-2]

        // Removing from both ends
        System.out.println("\nRemoving from first:");
        System.out.println("  LinkedList: " + linkedDeque.pollFirst());
        System.out.println("  ArrayDeque: " + arrayDequeAsDeque.pollFirst());

        System.out.println("\nRemoving from last:");
        System.out.println("  LinkedList: " + linkedDeque.pollLast());
        System.out.println("  ArrayDeque: " + arrayDequeAsDeque.pollLast());

        // ============================================================
        // DEMO 5: Use Case - When LinkedList is Better
        // ============================================================

        System.out.println("\n===== DEMO 5: When LinkedList WINS =====\n");

        // Scenario: Removing elements during iteration
        // This is where LinkedList shines!

        List<String> linkedListAsList = new LinkedList<>();
        List<String> arrayDequeAsList = new ArrayList<>(); // ArrayDeque doesn't implement List

        // Populate
        for (int i = 1; i <= 10; i++) {
            linkedListAsList.add("Item-" + i);
            arrayDequeAsList.add("Item-" + i);
        }

        // Remove all even-numbered items using iterator
        System.out.println("Removing even items using iterator:");

        // LinkedList: efficient removal during iteration
        long llIterStart = System.nanoTime();
        Iterator<String> llIter = linkedListAsList.iterator();
        while (llIter.hasNext()) {
            String item = llIter.next();
            if (item.endsWith("2") || item.endsWith("4") || 
                item.endsWith("6") || item.endsWith("8") || item.endsWith("0")) {
                llIter.remove(); // O(1) for LinkedList
            }
        }
        long llIterTime = System.nanoTime() - llIterStart;

        // ArrayList: slow removal during iteration
        long alIterStart = System.nanoTime();
        Iterator<String> alIter = arrayDequeAsList.iterator();
        while (alIter.hasNext()) {
            String item = alIter.next();
            if (item.endsWith("2") || item.endsWith("4") || 
                item.endsWith("6") || item.endsWith("8") || item.endsWith("0")) {
                alIter.remove(); // O(n) for ArrayList (shifts elements)
            }
        }
        long alIterTime = System.nanoTime() - alIterStart;

        System.out.println("  LinkedList: " + linkedListAsList);
        System.out.println("  ArrayList: " + arrayDequeAsList);
        System.out.println("  LinkedList removal time: " + llIterTime + " ns");
        System.out.println("  ArrayList removal time: " + alIterTime + " ns");
        System.out.println("  Winner: LinkedList (for frequent iterator.remove())");

        // ============================================================
        // DEMO 6: Real-World Example - Task Queue System
        // ============================================================

        System.out.println("\n===== DEMO 6: Real-World Task Queue =====\n");

        // Using ArrayDeque (industry standard for task queues)
        TaskQueue taskQueue = new TaskQueue();

        taskQueue.addTask(new Task("Send email to customer", "HIGH"));
        taskQueue.addTask(new Task("Generate monthly report", "MEDIUM"));
        taskQueue.addTask(new Task("Backup database", "LOW"));
        taskQueue.addTask(new Task("Process payment", "CRITICAL"));

        // Add urgent task to front (Deque advantage!)
        taskQueue.addUrgentTask(new Task("Security alert!", "CRITICAL"));

        System.out.println("Processing tasks in order:");
        taskQueue.processTasks();

        // ============================================================
        // DEMO 7: Stack Operations (ArrayDeque as Stack)
        // ============================================================

        System.out.println("\n===== DEMO 7: ArrayDeque as Stack (Better than Stack class!) =====\n");

        // Old way: Stack class (synchronized, slow, extends Vector)
        Stack<String> oldStack = new Stack<>();
        oldStack.push("A");
        oldStack.push("B");
        oldStack.push("C");
        System.out.println("Old Stack.pop(): " + oldStack.pop()); // C

        // Modern way: ArrayDeque (faster, not synchronized)
        java.util.Deque<String> modernStack = new ArrayDeque<>();
        modernStack.push("A");
        modernStack.push("B");
        modernStack.push("C");
        System.out.println("ArrayDeque.pop(): " + modernStack.pop()); // C

        System.out.println("\n💡 Industry Tip: NEVER use Stack class!");
        System.out.println("   Use ArrayDeque.push() and ArrayDeque.pop() instead.");

        // ============================================================
        // DEMO 8: Null Handling Difference
        // ============================================================

        System.out.println("\n===== DEMO 8: Null Handling =====\n");

        Queue<String> ll2 = new LinkedList<>();
        Queue<String> ad2 = new ArrayDeque<>();

        // LinkedList allows null
        ll2.offer(null);
        ll2.offer("NotNull");
        System.out.println("LinkedList with null: " + ll2); // [null, NotNull]

        // ArrayDeque does NOT allow null
        try {
            ad2.offer(null); // NullPointerException!
        } catch (NullPointerException e) {
            System.out.println("❌ ArrayDeque threw NullPointerException for null");
        }

        System.out.println("\n💡 ArrayDeque doesn't allow null elements!");
        System.out.println("   This is intentional - helps catch bugs early.");

        System.out.println("\n===== ALL DEMOS COMPLETE =====");
    }
}

/**
 * Task Queue implementation using ArrayDeque (industry standard)
 */
class TaskQueue {
    // ArrayDeque is the DEFAULT choice for queues in production!
    private Deque<Task> queue = new ArrayDeque<>();

    public void addTask(Task task) {
        queue.offerLast(task);
        System.out.println("📋 Task added: " + task);
    }

    // Deque allows adding to front (urgent tasks!)
    public void addUrgentTask(Task task) {
        queue.offerFirst(task);
        System.out.println("🚨 URGENT task added to front: " + task);
    }

    public void processTasks() {
        int count = 1;
        while (!queue.isEmpty()) {
            Task task = queue.pollFirst();
            System.out.println("  " + count++ + ". Processing: " + task);
        }
    }
}

class Task {
    String description;
    String priority;

    public Task(String description, String priority) {
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[" + priority + "] " + description;
    }
}

/*
 * ============================================================
 *  KEY TAKEAWAYS
 * ============================================================
 *
 *  ✅ ArrayDeque is FASTER than LinkedList in 99% of cases
 *  ✅ ArrayDeque is more MEMORY EFFICIENT (no pointer overhead)
 *  ✅ ArrayDeque is CACHE-FRIENDLY (contiguous memory)
 *  ✅ LinkedList is better for frequent iterator.remove() in middle
 *  ✅ ArrayDeque doesn't allow null elements (feature, not bug!)
 *  ✅ Use ArrayDeque as a Stack (NOT the Stack class)
 *  ✅ Joshua Bloch (Java Collections author) recommends ArrayDeque
 *
 * ============================================================
 *  INDUSTRY BEST PRACTICES
 * ============================================================
 *
 *  1. DEFAULT CHOICE: Use ArrayDeque for queues and stacks
 *     Queue<String> queue = new ArrayDeque<>();
 *     Deque<String> stack = new ArrayDeque<>();
 *
 *  2. Use LinkedList ONLY when:
 *     - You need List interface operations (get, add at index)
 *     - Frequent iterator.remove() in middle of list
 *     - You need to store null elements
 *
 *  3. NEVER use Stack class → use ArrayDeque instead
 *
 *  4. For thread-safe queues → use BlockingQueue implementations
 *     (ConcurrentLinkedQueue, LinkedBlockingQueue, ArrayBlockingQueue)
 *
 *  5. Initial capacity tuning for ArrayDeque:
 *     new ArrayDeque<>(1000) // if you know approximate size
 *     Avoids resizing overhead
 *
 * ============================================================
 *  COMMON INTERVIEW QUESTIONS
 * ============================================================
 *
 *  Q: Why is ArrayDeque faster than LinkedList?
 *  A: 1. Cache locality (contiguous memory)
 *     2. No pointer overhead (less memory allocations)
 *     3. Better CPU cache hit rate
 *
 *  Q: When would you choose LinkedList over ArrayDeque?
 *  A: 1. Need List interface (indexed access)
 *     2. Frequent removals during iteration
 *     3. Need to store null elements
 *
 *  Q: Why doesn't ArrayDeque allow null?
 *  A: poll() returns null when empty. If null elements allowed,
 *     you can't distinguish between "empty queue" and "null element".
 *     This design prevents bugs!
 *
 *  Q: Can I use ArrayDeque as a Stack?
 *  A: YES! It's RECOMMENDED over Stack class.
 *     push(e), pop(), peek() all work perfectly.
 *
 */
