package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;

/**
 * ============================================================
 *        PRIORITYQUEUE - THE VIP LINE DATA STRUCTURE 👑
 * ============================================================
 *
 * Bhai, normal queue mein sab barabar hote hain - FIFO.
 * Lekin PriorityQueue mein VIP system hai! 😎
 *
 * Real-life analogy:
 * - Hospital emergency room: Critical patients treated first (not FIFO!)
 * - Airport boarding: Business class pehle, then economy
 * - Task scheduling: High-priority tasks first
 * - Customer support: Premium customers ka call pehle uthta hai
 *
 * Key difference from normal Queue:
 * - Normal Queue: First Come First Serve (railway counter)
 * - PriorityQueue: Highest Priority First (hospital ER)
 *
 * ============================================================
 *  INTERNAL WORKING - MIN HEAP
 * ============================================================
 *
 * PriorityQueue internally uses a BINARY MIN HEAP (by default)
 *
 * What is a Heap?
 * - Complete binary tree
 * - Parent is always smaller/larger than children
 * - Min Heap: Parent < Children (default in Java)
 * - Max Heap: Parent > Children (use custom Comparator)
 *
 * Array representation of heap:
 *           1                    Array: [1, 4, 2, 8, 5, 7, 3]
 *         /   \                  Index:  0  1  2  3  4  5  6
 *        4     2
 *       / \   / \                For element at index i:
 *      8   5 7   3               - Left child:  2*i + 1
 *                                - Right child: 2*i + 2
 *                                - Parent:      (i-1)/2
 *
 * Operations:
 * - offer(e): Add element and bubble up → O(log n)
 * - poll():   Remove root and bubble down → O(log n)
 * - peek():   Return root → O(1)
 * - remove(e): Find and remove → O(n) (needs linear search + reheapify)
 *
 * ============================================================
 *  TIME COMPLEXITY
 * ============================================================
 *
 * Operation     | Time Complexity
 * --------------|----------------
 * offer(e)      | O(log n)  - add and bubble up
 * poll()        | O(log n)  - remove root and reheapify
 * peek()        | O(1)      - just return root
 * remove(e)     | O(n)      - linear search + O(log n) reheapify
 * contains(e)   | O(n)      - linear search
 * size()        | O(1)
 *
 * ============================================================
 *  WHEN TO USE PRIORITYQUEUE?
 * ============================================================
 *
 * 1. TASK SCHEDULING
 *    → OS task scheduler (high-priority processes first)
 *    → Thread pool executors with priority
 *
 * 2. DIJKSTRA'S ALGORITHM
 *    → Shortest path in graphs
 *    → Process nodes with smallest distance first
 *
 * 3. HUFFMAN CODING
 *    → Data compression algorithm
 *    → Build tree using min heap
 *
 * 4. LOAD BALANCING
 *    → Assign tasks to least loaded server
 *
 * 5. EVENT SIMULATION
 *    → Process events in time order
 *
 * 6. TOP K ELEMENTS
 *    → Find K largest/smallest elements efficiently
 *
 * 7. MERGE K SORTED ARRAYS
 *    → Efficiently merge using min heap
 *
 * ============================================================
 *  IMPORTANT NOTES
 * ============================================================
 *
 * ⚠️  NOT thread-safe! (Use PriorityBlockingQueue for thread-safety)
 * ⚠️  Does NOT allow null elements (throws NullPointerException)
 * ⚠️  Iterator does NOT guarantee sorted order!
 * ⚠️  Elements must be Comparable OR provide Comparator
 * ⚠️  Iteration order is NOT sorted (heap order, not sorted order)
 *
 */
class PriorityQueue {

    public static void main(String[] args) {

        System.out.println("===== PRIORITYQUEUE - COMPLETE GUIDE =====\n");

        // ============================================================
        // DEMO 1: Basic PriorityQueue - Min Heap (Default)
        // ============================================================

        System.out.println("===== DEMO 1: Basic PriorityQueue (Min Heap) =====\n");

        // Default: Min Heap (smallest element has highest priority)
        java.util.PriorityQueue<Integer> minHeap = new java.util.PriorityQueue<>();

        // Add elements in random order
        minHeap.offer(5);
        minHeap.offer(1);
        minHeap.offer(9);
        minHeap.offer(3);
        minHeap.offer(7);

        System.out.println("Added: 5, 1, 9, 3, 7");
        System.out.println("Internal heap array: " + minHeap);
        System.out.println("⚠️  Note: This is HEAP order, not sorted order!");

        // Polling gives elements in sorted order (ascending)
        System.out.println("\nPolling elements (ascending order):");
        while (!minHeap.isEmpty()) {
            System.out.println("  Polled: " + minHeap.poll());
        }

        // ============================================================
        // DEMO 2: Max Heap using Comparator
        // ============================================================

        System.out.println("\n===== DEMO 2: Max Heap (Reverse Order) =====\n");

        // Max Heap: Largest element has highest priority
        java.util.PriorityQueue<Integer> maxHeap = new java.util.PriorityQueue<>(Collections.reverseOrder());

        maxHeap.offer(5);
        maxHeap.offer(1);
        maxHeap.offer(9);
        maxHeap.offer(3);
        maxHeap.offer(7);

        System.out.println("Added: 5, 1, 9, 3, 7");
        System.out.println("Polling elements (descending order):");
        while (!maxHeap.isEmpty()) {
            System.out.println("  Polled: " + maxHeap.poll());
        }

        // ============================================================
        // DEMO 3: Real-World Example - Hospital Emergency Room
        // ============================================================

        System.out.println("\n===== DEMO 3: Hospital Emergency Room =====\n");

        java.util.PriorityQueue<Patient> emergencyRoom = new java.util.PriorityQueue<>();

        // Patients arrive
        emergencyRoom.offer(new Patient("Rahul", "Fever", 3));
        emergencyRoom.offer(new Patient("Priya", "Heart Attack", 1)); // Critical!
        emergencyRoom.offer(new Patient("Amit", "Broken Arm", 2));
        emergencyRoom.offer(new Patient("Sneha", "Minor Cut", 4));
        emergencyRoom.offer(new Patient("Rohan", "Severe Bleeding", 1)); // Critical!

        System.out.println("Patients treated in order of severity:");
        int order = 1;
        while (!emergencyRoom.isEmpty()) {
            Patient p = emergencyRoom.poll();
            System.out.println("  " + order++ + ". " + p);
        }

        // ============================================================
        // DEMO 4: Custom Comparator - Task Scheduling
        // ============================================================

        System.out.println("\n===== DEMO 4: Task Scheduling System =====\n");

        // Custom comparator: Higher priority value = higher priority
        // If same priority, earlier deadline first
        java.util.PriorityQueue<ScheduledTask> taskQueue = new java.util.PriorityQueue<>(
            (t1, t2) -> {
                if (t1.priority != t2.priority) {
                    return t2.priority - t1.priority; // Higher priority first
                }
                return t1.deadline.compareTo(t2.deadline); // Earlier deadline first
            }
        );

        taskQueue.offer(new ScheduledTask("Send email", 2, "2024-06-10"));
        taskQueue.offer(new ScheduledTask("Deploy to prod", 5, "2024-06-08")); // High priority!
        taskQueue.offer(new ScheduledTask("Write docs", 1, "2024-06-15"));
        taskQueue.offer(new ScheduledTask("Fix critical bug", 5, "2024-06-07")); // High priority, earlier deadline
        taskQueue.offer(new ScheduledTask("Code review", 3, "2024-06-09"));

        System.out.println("Tasks executed in priority order:");
        int taskNum = 1;
        while (!taskQueue.isEmpty()) {
            ScheduledTask task = taskQueue.poll();
            System.out.println("  " + taskNum++ + ". " + task);
        }

        // ============================================================
        // DEMO 5: Top K Elements Problem
        // ============================================================

        System.out.println("\n===== DEMO 5: Find Top K Largest Elements =====\n");

        int[] numbers = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        int k = 3;

        System.out.println("Array: " + Arrays.toString(numbers));
        System.out.println("Finding top " + k + " largest elements...");

        List<Integer> topK = findTopKLargest(numbers, k);
        System.out.println("Top " + k + " largest: " + topK);

        // ============================================================
        // DEMO 6: Merge K Sorted Arrays
        // ============================================================

        System.out.println("\n===== DEMO 6: Merge K Sorted Arrays =====\n");

        int[][] sortedArrays = {
            {1, 4, 7},
            {2, 5, 8},
            {3, 6, 9}
        };

        System.out.println("Input arrays:");
        for (int i = 0; i < sortedArrays.length; i++) {
            System.out.println("  Array " + (i+1) + ": " + Arrays.toString(sortedArrays[i]));
        }

        List<Integer> merged = mergeKSortedArrays(sortedArrays);
        System.out.println("Merged sorted array: " + merged);

        // ============================================================
        // DEMO 7: Iterator Warning - Not Sorted!
        // ============================================================

        System.out.println("\n===== DEMO 7: Iterator Warning =====\n");

        java.util.PriorityQueue<Integer> pq = new java.util.PriorityQueue<>();
        pq.offer(5);
        pq.offer(1);
        pq.offer(9);
        pq.offer(3);
        pq.offer(7);

        System.out.println("Using for-each loop (NOT sorted):");
        for (Integer num : pq) {
            System.out.print(num + " "); // NOT in sorted order!
        }
        
        System.out.println("\n\nUsing poll() in loop (sorted):");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " "); // Sorted order!
        }
        System.out.println();

        // ============================================================
        // DEMO 8: Performance Comparison
        // ============================================================

        System.out.println("\n===== DEMO 8: Performance Analysis =====\n");

        int size = 100_000;

        // Benchmark 1: PriorityQueue offer
        java.util.PriorityQueue<Integer> pqBench = new java.util.PriorityQueue<>();
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            pqBench.offer(i);
        }
        long pqOfferTime = System.nanoTime() - start;

        // Benchmark 2: TreeSet add (for comparison)
        TreeSet<Integer> treeSet = new TreeSet<>();
        start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            treeSet.add(i);
        }
        long treeSetAddTime = System.nanoTime() - start;

        System.out.println("Adding " + size + " elements:");
        System.out.println("  PriorityQueue.offer(): " + pqOfferTime / 1_000_000 + " ms");
        System.out.println("  TreeSet.add():         " + treeSetAddTime / 1_000_000 + " ms");
        System.out.println("  Winner: " + (pqOfferTime < treeSetAddTime ? "PriorityQueue 🏆" : "TreeSet 🏆"));

        // Benchmark 3: Polling
        start = System.nanoTime();
        while (!pqBench.isEmpty()) {
            pqBench.poll();
        }
        long pqPollTime = System.nanoTime() - start;

        start = System.nanoTime();
        while (!treeSet.isEmpty()) {
            treeSet.pollFirst();
        }
        long treeSetPollTime = System.nanoTime() - start;

        System.out.println("\nRemoving " + size + " elements:");
        System.out.println("  PriorityQueue.poll():  " + pqPollTime / 1_000_000 + " ms");
        System.out.println("  TreeSet.pollFirst():   " + treeSetPollTime / 1_000_000 + " ms");

        // ============================================================
        // DEMO 9: Common Pitfalls
        // ============================================================

        System.out.println("\n===== DEMO 9: Common Pitfalls =====\n");

        // Pitfall 1: Null elements
        java.util.PriorityQueue<String> pq1 = new java.util.PriorityQueue<>();
        try {
            pq1.offer(null);
        } catch (NullPointerException e) {
            System.out.println("❌ Pitfall 1: Cannot add null - " + e.getClass().getSimpleName());
        }

        // Pitfall 2: Non-comparable objects without Comparator
        try {
            java.util.PriorityQueue<NonComparableClass> pq2 = new java.util.PriorityQueue<>();
            pq2.offer(new NonComparableClass());
            pq2.offer(new NonComparableClass());
            pq2.poll(); // This will throw ClassCastException
        } catch (ClassCastException e) {
            System.out.println("❌ Pitfall 2: Objects must be Comparable or provide Comparator");
        }

        // Correct way: Provide Comparator
        java.util.PriorityQueue<NonComparableClass> pq3 = new java.util.PriorityQueue<>(
            (o1, o2) -> Integer.compare(o1.value, o2.value)
        );
        pq3.offer(new NonComparableClass());
        pq3.offer(new NonComparableClass());
        System.out.println("✅ Fixed: Provided custom Comparator");

        System.out.println("\n===== ALL PRIORITYQUEUE DEMOS COMPLETE =====");
    }

    /**
     * Find top K largest elements using min heap
     * Time: O(n log k), Space: O(k)
     * 
     * Industry use: Finding top K most frequent items, top K scorers, etc.
     */
    private static List<Integer> findTopKLargest(int[] nums, int k) {
        // Use min heap of size k
        // If element > heap top, remove top and add element
        java.util.PriorityQueue<Integer> minHeap = new java.util.PriorityQueue<>(k);

        for (int num : nums) {
            if (minHeap.size() < k) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }

        return new ArrayList<>(minHeap);
    }

    /**
     * Merge K sorted arrays using min heap
     * Time: O(N log k) where N = total elements, k = number of arrays
     * 
     * Industry use: External sorting, merging log files from multiple servers
     */
    private static List<Integer> mergeKSortedArrays(int[][] arrays) {
        List<Integer> result = new ArrayList<>();
        
        // Min heap: stores (value, arrayIndex, elementIndex)
        java.util.PriorityQueue<ArrayElement> minHeap = new java.util.PriorityQueue<>(
            (a, b) -> Integer.compare(a.value, b.value)
        );

        // Add first element from each array
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                minHeap.offer(new ArrayElement(arrays[i][0], i, 0));
            }
        }

        // Extract min and add next element from same array
        while (!minHeap.isEmpty()) {
            ArrayElement elem = minHeap.poll();
            result.add(elem.value);

            // Add next element from the same array
            int nextIndex = elem.elementIndex + 1;
            if (nextIndex < arrays[elem.arrayIndex].length) {
                minHeap.offer(new ArrayElement(
                    arrays[elem.arrayIndex][nextIndex],
                    elem.arrayIndex,
                    nextIndex
                ));
            }
        }

        return result;
    }
}

/**
 * Patient class for hospital emergency room example
 */
class Patient implements Comparable<Patient> {
    String name;
    String condition;
    int severity; // 1 = critical, 5 = minor

    public Patient(String name, String condition, int severity) {
        this.name = name;
        this.condition = condition;
        this.severity = severity;
    }

    @Override
    public int compareTo(Patient other) {
        // Lower severity number = higher priority
        return Integer.compare(this.severity, other.severity);
    }

    @Override
    public String toString() {
        return name + " [" + condition + ", Severity: " + severity + "]";
    }
}

/**
 * Task class for scheduling example
 */
class ScheduledTask {
    String description;
    int priority; // Higher number = higher priority
    String deadline;

    public ScheduledTask(String description, int priority, String deadline) {
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[Priority: " + priority + ", Deadline: " + deadline + "] " + description;
    }
}

/**
 * Helper class for merging K sorted arrays
 */
class ArrayElement {
    int value;
    int arrayIndex;
    int elementIndex;

    public ArrayElement(int value, int arrayIndex, int elementIndex) {
        this.value = value;
        this.arrayIndex = arrayIndex;
        this.elementIndex = elementIndex;
    }
}

/**
 * Example of non-comparable class
 */
class NonComparableClass {
    int value = (int) (Math.random() * 100);
}

/*
 * ============================================================
 *  KEY TAKEAWAYS
 * ============================================================
 *
 *  ✅ PriorityQueue = Min Heap by default (smallest element first)
 *  ✅ offer() = O(log n), poll() = O(log n), peek() = O(1)
 *  ✅ Use Collections.reverseOrder() for Max Heap
 *  ✅ Elements must be Comparable OR provide Comparator
 *  ✅ Does NOT allow null elements
 *  ✅ Iterator does NOT guarantee sorted order!
 *  ✅ NOT thread-safe (use PriorityBlockingQueue for threads)
 *
 * ============================================================
 *  INDUSTRY USE CASES
 * ============================================================
 *
 *  1. Task scheduling (OS, thread pools)
 *  2. Dijkstra's shortest path algorithm
 *  3. Huffman coding (data compression)
 *  4. Load balancing (assign to least loaded server)
 *  5. Event-driven simulation
 *  6. Top K elements problems
 *  7. Merge K sorted lists/arrays
 *  8. Median of stream (using 2 heaps)
 *  9. A* pathfinding algorithm
 *  10. CPU scheduling algorithms
 *
 * ============================================================
 *  COMMON INTERVIEW QUESTIONS
 * ============================================================
 *
 *  Q: What data structure does PriorityQueue use?
 *  A: Binary min heap (array-based complete binary tree)
 *
 *  Q: Time complexity of offer() and poll()?
 *  A: Both O(log n) - need to maintain heap property
 *
 *  Q: How to create a max heap?
 *  A: new PriorityQueue<>(Collections.reverseOrder())
 *
 *  Q: Why doesn't iterator return sorted order?
 *  A: Iterator traverses internal array, which is in heap order (not sorted).
 *     Only poll() guarantees sorted order.
 *
 *  Q: How to find K largest elements?
 *  A: Use min heap of size K. Keep only K largest at any time.
 *
 */
