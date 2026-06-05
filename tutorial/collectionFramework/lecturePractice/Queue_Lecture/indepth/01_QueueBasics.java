package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;

/**
 * ============================================================
 *           QUEUE - THE LINE LAGAO DATA STRUCTURE 🚶‍♂️🚶‍♀️🚶
 * ============================================================
 *
 * Bhai, Queue samjhna bahut simple hai.
 * Imagine karo: Railway station ka ticket counter.
 * 
 * - Pehle aaya, pehle serve hua (FIFO - First In First Out)
 * - Line ke PEECHE se log join karte hain (enqueue/offer)
 * - Line ke AAGE se log nikalne (dequeue/poll)
 * - Beech mein ghusna = not allowed! (unless you're a VIP 😄)
 *
 * Real-life examples:
 * - Railway ticket counter ki line
 * - Printer queue (files print hoti hain order mein)
 * - Customer support calls (first come, first served)
 * - WhatsApp message delivery queue
 * - Restaurant waiting list
 *
 * ============================================================
 *  QUEUE HIERARCHY IN JAVA
 * ============================================================
 *
 *                    Collection
 *                        |
 *                      Queue (interface)
 *                        |
 *        +---------------+---------------+
 *        |               |               |
 *    Deque (I)      BlockingQueue (I)  PriorityQueue (class)
 *        |               |
 *  ArrayDeque(C)   LinkedBlockingQueue(C)
 *  LinkedList(C)   PriorityBlockingQueue(C)
 *                  ArrayBlockingQueue(C)
 *                  SynchronousQueue(C)
 *                  DelayQueue(C)
 *
 * (I) = Interface, (C) = Concrete Class
 *
 * ============================================================
 *  CORE QUEUE METHODS (The Confusing Pairs 😅)
 * ============================================================
 *
 * Queue has TWO sets of methods for same operations:
 *
 * THROWS EXCEPTION         | RETURNS SPECIAL VALUE
 * -------------------------|------------------------
 * add(e)    - insert       | offer(e)  - insert, returns true/false
 * remove()  - remove head  | poll()    - remove head, returns null if empty
 * element() - examine head | peek()    - examine head, returns null if empty
 *
 * Industry tip: Prefer offer(), poll(), peek() over add(), remove(), element()
 * Why? Because exceptions are expensive! Null checking is faster.
 *
 * ============================================================
 *  WHY QUEUE? REAL INDUSTRY USE CASES
 * ============================================================
 *
 * 1. MESSAGE QUEUES (RabbitMQ, Kafka, SQS)
 *    → Microservices communicate asynchronously
 *    → Order service sends order to queue
 *    → Payment service picks from queue and processes
 *
 * 2. TASK SCHEDULING
 *    → Background job processing (email sending, report generation)
 *    → Thread pools use queues internally
 *
 * 3. BREADTH-FIRST SEARCH (BFS)
 *    → Graph traversal algorithm
 *    → Level-order tree traversal
 *
 * 4. RATE LIMITING / THROTTLING
 *    → API rate limiting (allow 100 requests per minute)
 *    → Sliding window queues
 *
 * 5. PRINT SPOOLING
 *    → Multiple print jobs waiting
 *
 * 6. CACHE IMPLEMENTATION
 *    → LRU Cache uses queue-like structure
 *
 */


class QueueBasics {

    public static void main(String[] args) {

        System.out.println("===== QUEUE BASICS - THE COMPLETE BEGINNER'S GUIDE =====\n");

        // ============================================================
        // DEMO 1: Basic Queue Operations using LinkedList
        // ============================================================

        System.out.println("===== DEMO 1: Basic Queue Operations =====\n");

        // LinkedList implements Queue interface
        // Why LinkedList? Because adding/removing from ends is O(1)
        Queue<String> ticketQueue = new LinkedList<>();

        // Check if queue is empty
        System.out.println("Is queue empty? " + ticketQueue.isEmpty()); // true

        // ===== ADDING ELEMENTS (ENQUEUE) =====

        // Method 1: add() - throws IllegalStateException if no space (bounded queues)
        ticketQueue.add("Rahul");
        ticketQueue.add("Priya");
        ticketQueue.add("Amit");

        System.out.println("After adding 3 people: " + ticketQueue);
        // Output: [Rahul, Priya, Amit] — Rahul is at front (head)

        // Method 2: offer() - returns true/false (PREFERRED in industry)
        boolean added = ticketQueue.offer("Sneha");
        System.out.println("Sneha added? " + added); // true
        System.out.println("Queue now: " + ticketQueue);

        // ===== EXAMINING HEAD (WITHOUT REMOVING) =====

        // Method 1: element() - throws NoSuchElementException if empty
        String front1 = ticketQueue.element();
        System.out.println("\nWho's at front (element)? " + front1); // Rahul

        // Method 2: peek() - returns null if empty (PREFERRED in industry)
        String front2 = ticketQueue.peek();
        System.out.println("Who's at front (peek)? " + front2); // Rahul
        System.out.println("Queue still intact: " + ticketQueue);

        // ===== REMOVING ELEMENTS (DEQUEUE) =====

        // Method 1: remove() - throws NoSuchElementException if empty
        String served1 = ticketQueue.remove();
        System.out.println("\nServed ticket to: " + served1); // Rahul
        System.out.println("Queue after remove: " + ticketQueue);

        // Method 2: poll() - returns null if empty (PREFERRED in industry)
        String served2 = ticketQueue.poll();
        System.out.println("Served ticket to: " + served2); // Priya
        System.out.println("Queue after poll: " + ticketQueue);

        // ===== CHECKING SIZE =====
        System.out.println("\nQueue size: " + ticketQueue.size()); // 2

        // ============================================================
        // DEMO 2: Why offer/poll/peek are BETTER than add/remove/element
        // ============================================================

        System.out.println("\n===== DEMO 2: Exception Handling =====\n");

        Queue<Integer> numbers = new LinkedList<>();

        // Scenario 1: Trying to get from empty queue
        try {
            int value = numbers.element(); // throws exception!
            System.out.println("Got value: " + value);
        } catch (NoSuchElementException e) {
            System.out.println("❌ element() threw exception: " + e.getClass().getSimpleName());
        }

        // Better approach: use peek()
        Integer safePeek = numbers.peek();
        if (safePeek == null) {
            System.out.println("✅ peek() safely returned null - queue is empty");
        }

        // Scenario 2: Removing from empty queue
        try {
            int value = numbers.remove(); // throws exception!
        } catch (NoSuchElementException e) {
            System.out.println("❌ remove() threw exception: " + e.getClass().getSimpleName());
        }

        // Better approach: use poll()
        Integer safePoll = numbers.poll();
        if (safePoll == null) {
            System.out.println("✅ poll() safely returned null - queue is empty");
        }

        // ============================================================
        // DEMO 3: Real-World Example - Customer Support System
        // ============================================================

        System.out.println("\n===== DEMO 3: Customer Support Call Center =====\n");

        CustomerSupportQueue supportQueue = new CustomerSupportQueue();

        // Customers calling in
        supportQueue.addCall(new Call("Cust001", "Login issue", "High"));
        supportQueue.addCall(new Call("Cust002", "Payment failed", "Critical"));
        supportQueue.addCall(new Call("Cust003", "Feature request", "Low"));
        supportQueue.addCall(new Call("Cust004", "Account locked", "High"));

        System.out.println("Total calls waiting: " + supportQueue.getWaitingCallsCount());

        // Support agents answering calls (FIFO order)
        System.out.println("\nAgent 1 answering calls:");
        supportQueue.answerNextCall("Agent_Ramesh");
        
        System.out.println("\nAgent 2 answering calls:");
        supportQueue.answerNextCall("Agent_Sneha");

        System.out.println("\nCalls still waiting: " + supportQueue.getWaitingCallsCount());

        // Check next call without answering
        supportQueue.viewNextCall();

        // ============================================================
        // DEMO 4: Queue Iteration (IMPORTANT!)
        // ============================================================

        System.out.println("\n===== DEMO 4: Iterating a Queue =====\n");

        Queue<String> tasks = new LinkedList<>();
        tasks.offer("Task 1: Send email");
        tasks.offer("Task 2: Generate report");
        tasks.offer("Task 3: Backup database");

        // Method 1: Using enhanced for loop (doesn't remove elements)
        System.out.println("Viewing all tasks (non-destructive):");
        for (String task : tasks) {
            System.out.println("  - " + task);
        }
        System.out.println("Queue still has " + tasks.size() + " tasks");

        // Method 2: Using iterator (can remove during iteration)
        System.out.println("\nRemoving tasks containing 'report':");
        Iterator<String> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            String task = iterator.next();
            if (task.contains("report")) {
                iterator.remove(); // Safe removal during iteration
                System.out.println("  Removed: " + task);
            }
        }
        System.out.println("Queue now: " + tasks);

        // Method 3: Processing all elements (destructive)
        System.out.println("\nProcessing and removing all tasks:");
        while (!tasks.isEmpty()) {
            String task = tasks.poll();
            System.out.println("  Executing: " + task);
        }
        System.out.println("Queue is now empty: " + tasks.isEmpty());

        // ============================================================
        // DEMO 5: Queue vs List - When to use what?
        // ============================================================

        System.out.println("\n===== DEMO 5: Queue vs List =====\n");

        // ArrayList: Random access is O(1), but add/remove from front is O(n)
        List<String> arrayList = new ArrayList<>();
        arrayList.add("First");
        arrayList.add("Second");
        arrayList.add("Third");
        
        // Removing from front in ArrayList = expensive!
        // All elements shift left → O(n)
        arrayList.remove(0); // Slow!
        System.out.println("ArrayList after removing first: " + arrayList);

        // Queue (LinkedList): Add/remove from ends is O(1)
        Queue<String> queue = new LinkedList<>();
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");
        
        // Removing from front in Queue = fast!
        queue.poll(); // O(1) - no shifting needed
        System.out.println("Queue after removing first: " + queue);

        System.out.println("\n🎯 Use Queue when: You only need front/back operations (FIFO)");
        System.out.println("🎯 Use List when: You need random access or frequent middle insertions");

        System.out.println("\n===== ALL QUEUE BASICS DEMOS COMPLETE =====");
    }
}

/**
 * Real-world example: Customer Support Call Center Queue
 */
class CustomerSupportQueue {
    private Queue<Call> callQueue = new LinkedList<>();

    /**
     * Add a new call to the queue (customer calls in)
     */
    public void addCall(Call call) {
        callQueue.offer(call);
        System.out.println("📞 New call added: " + call);
    }

    /**
     * Agent answers the next call (removes from queue)
     */
    public void answerNextCall(String agentName) {
        Call call = callQueue.poll();
        if (call != null) {
            System.out.println("✅ " + agentName + " answering: " + call);
        } else {
            System.out.println("📭 No calls waiting for " + agentName);
        }
    }

    /**
     * View next call without answering (peek)
     */
    public void viewNextCall() {
        Call call = callQueue.peek();
        if (call != null) {
            System.out.println("👀 Next call in queue: " + call);
        } else {
            System.out.println("📭 No calls waiting");
        }
    }

    /**
     * Get number of calls waiting
     */
    public int getWaitingCallsCount() {
        return callQueue.size();
    }
}

/**
 * Call entity representing a customer support call
 */
class Call {
    String customerId;
    String issue;
    String priority;

    public Call(String customerId, String issue, String priority) {
        this.customerId = customerId;
        this.issue = issue;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[" + customerId + " | " + issue + " | Priority: " + priority + "]";
    }
}

/*
 * ============================================================
 *  KEY TAKEAWAYS (BEGINNER LEVEL)
 * ============================================================
 *
 *  ✅ Queue = FIFO (First In First Out) - jaise railway counter ki line
 *  ✅ Two sets of methods: Exception-throwing vs Special-value-returning
 *  ✅ ALWAYS prefer: offer(), poll(), peek() over add(), remove(), element()
 *  ✅ LinkedList is most common Queue implementation for basic use
 *  ✅ Use Queue when you need FIFO ordering
 *  ✅ Use List when you need random access or index-based operations
 *
 * ============================================================
 *  COMMON MISTAKES (AVOID THESE!)
 * ============================================================
 *
 *  ❌ Using add()/remove() instead of offer()/poll() - exceptions are costly
 *  ❌ Using ArrayList as a queue - remove(0) is O(n), very slow!
 *  ❌ Modifying queue while iterating without using iterator.remove()
 *  ❌ Assuming Queue allows middle insertions - it doesn't! (use Deque for that)
 *  ❌ Not checking isEmpty() before poll/peek
 *
 * ============================================================
 *  NEXT STEPS
 * ============================================================
 *
 *  After mastering this file, move to:
 *  → 02_LinkedListVsArrayDeque.java - Performance deep dive
 *  → 03_PriorityQueue.java - When order matters differently
 *  → 04_Deque.java - Double-ended queues (stack + queue combined)
 *  → 05_BlockingQueue.java - Thread-safe queues for producers/consumers
 *
 */
