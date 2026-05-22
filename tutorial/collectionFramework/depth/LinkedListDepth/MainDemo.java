 package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.*;

public class MainDemo {
    
    static void main(String[] args) throws InterruptedException {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║          MAINDEMO: COMPREHENSIVE LINKEDLIST EXAMPLES          ║
         * ║              Tying Everything Together with Practice          ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * This demo shows all LinkedList concepts in action
         * with real-world scenarios combined
         */

        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              LINKEDLIST COMPREHENSIVE DEMO                     ║");
        System.out.println("║                 All Concepts Combined                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 1: BASIC OPERATIONS
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("DEMO 1: BASIC LINKEDLIST OPERATIONS");
        System.out.println("───────────────────────────────────\n");
        
        LinkedList<Integer> numbers = new LinkedList<>();
        
        // Add elements
        numbers.add(10);      // add() - O(1)
        numbers.add(20);
        numbers.add(30);
        System.out.println("After adding [10, 20, 30]: " + numbers);
        
        // Add at ends
        numbers.addFirst(5);  // addFirst() - O(1)
        numbers.addLast(40);  // addLast() - O(1)
        System.out.println("After addFirst(5) and addLast(40): " + numbers);
        
        // Access
        System.out.println("getFirst(): " + numbers.getFirst());
        System.out.println("getLast(): " + numbers.getLast());
        System.out.println("get(2): " + numbers.get(2) + " (O(n) warning: traversal needed)");
        
        // Remove
        numbers.removeFirst();
        System.out.println("After removeFirst(): " + numbers);
        
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 2: PERFORMANCE COMPARISON (TIMING)
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("DEMO 2: PERFORMANCE - ARRAYLIST vs LINKEDLIST");
        System.out.println("────────────────────────────────────────────────\n");
        
        final int SIZE = 100000;
        
        // ArrayList Forward Iteration
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) arrayList.add(i);
        
        long start = System.currentTimeMillis();
        int sumAL = 0;
        for (Integer n : arrayList) {  // Iterator
            sumAL += n;
        }
        long alTime = System.currentTimeMillis() - start;
        
        // LinkedList Forward Iteration
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < SIZE; i++) linkedList.add(i);
        
        start = System.currentTimeMillis();
        int sumLL = 0;
        for (Integer n : linkedList) {  // Iterator
            sumLL += n;
        }
        long llTime = System.currentTimeMillis() - start;
        
        System.out.println("Forward iteration (" + SIZE + " elements):");
        System.out.println("  ArrayList: " + alTime + "ms");
        System.out.println("  LinkedList: " + llTime + "ms");
        System.out.println("  Winner: ArrayList (better cache locality)");
        System.out.println();
        
        // Remove from beginning (important!)
        arrayList.clear();
        linkedList.clear();
        for (int i = 0; i < 10000; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        start = System.currentTimeMillis();
        while (!arrayList.isEmpty()) arrayList.remove(0);  // O(n) each
        long alRemoveTime = System.currentTimeMillis() - start;
        
        start = System.currentTimeMillis();
        while (!linkedList.isEmpty()) linkedList.removeFirst();  // O(1)
        long llRemoveTime = System.currentTimeMillis() - start;
        
        System.out.println("Remove from beginning (10,000 elements):");
        System.out.println("  ArrayList: " + alRemoveTime + "ms");
        System.out.println("  LinkedList: " + llRemoveTime + "ms");
        System.out.println("  Winner: LinkedList (removeFirst is O(1))");
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 3: QUEUE PATTERN - TASK PROCESSOR
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("DEMO 3: QUEUE PATTERN - FIFO TASK PROCESSOR");
        System.out.println("──────────────────────────────────────────────\n");
        
        Queue<String> taskQueue = new LinkedList<>();
        
        // Enqueue tasks
        taskQueue.offer("Download file");      // add() at end - O(1)
        taskQueue.offer("Resize image");
        taskQueue.offer("Send email");
        taskQueue.offer("Compress data");
        
        System.out.println("Tasks in queue: " + taskQueue);
        System.out.println();
        
        // Process tasks
        System.out.println("Processing tasks (FIFO):");
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.poll();   // remove() from front - O(1)
            System.out.println("  ⚙️ Processing: " + task);
        }
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 4: STACK PATTERN - BROWSER HISTORY
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("DEMO 4: STACK PATTERN - BROWSER HISTORY");
        System.out.println("───────────────────────────────────────────\n");
        
        Deque<String> history = new LinkedList<>();
        
        // Visit pages
        System.out.println("Visiting pages:");
        String[] pages = {"google.com", "github.com", "stackoverflow.com", "Medium.com"};
        for (String page : pages) {
            history.push(page);  // LIFO - add to front
            System.out.println("  Visited: " + page);
        }
        System.out.println();
        System.out.println("Current history (most recent first): " + history);
        System.out.println();
        
        // Go back
        System.out.println("Going back (clicking back button):");
        while (!history.isEmpty()) {
            String page = history.pop();  // LIFO - remove from front
            System.out.println("  ← Back to: " + page);
        }
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 5: DEQUE PATTERN - SLIDING WINDOW
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("DEMO 5: DEQUE PATTERN - SLIDING WINDOW (Max of 3)");
        System.out.println("──────────────────────────────────────────────────\n");
        
        Deque<Integer> window = new LinkedList<>();
        int[] data = {10, 5, 20, 15, 8, 25, 12};
        int windowSize = 3;
        
        System.out.println("Finding running max (window size = 3):");
        for (int num : data) {
            window.addLast(num);
            if (window.size() > windowSize) {
                window.removeFirst();  // O(1) from both ends!
            }
            System.out.println("  Window: " + window + " → Max: " + window.stream().max(Integer::compareTo).orElse(0));
        }
        System.out.println();

        /*
         * DEMO 6: PROPER ITERATION METHODS - AVOIDING O(n) get(i) IN LOOPS
         */
        
        System.out.println("DEMO 6: ITERATION METHODS - RIGHT AND WRONG WAYS");
        System.out.println("───────────────────────────────────────────────────\n");
        
        LinkedList<String> colors = new LinkedList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        
        System.out.println("List: " + colors);
        System.out.println();
        
        // Forward iteration - BEST WAY
        System.out.println("✓ Forward iteration (enhanced for):");
        for (String color : colors) {
            System.out.println("  " + color);
        }
        System.out.println();
        
        // Backward iteration - efficient
        System.out.println("✓ Backward iteration (ListIterator):");
        ListIterator<String> iter = colors.listIterator(colors.size());
        while (iter.hasPrevious()) {
            System.out.println("  " + iter.previous());
        }
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 7: SAFE OPERATIONS
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("DEMO 7: SAFE OPERATIONS - EMPTY CHECKS");
        System.out.println("──────────────────────────────────────────\n");
        
        Queue<String> emptyQueue = new LinkedList<>();
        
        System.out.println("Testing empty queue:");
        
        // Safe way - using poll()
        String item = emptyQueue.poll();  // Returns null, no exception
        System.out.println("  poll() on empty: " + item + " (safe, returns null)");
        
        // Better way - checking first
        if (!emptyQueue.isEmpty()) {
            String item2 = emptyQueue.remove();  // Would throw exception
        }
        System.out.println("  isEmpty() check prevents errors");
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 8: REALISTIC SCENARIO - MESSAGE BROKER
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("DEMO 8: REALISTIC - MESSAGE BROKER");
        System.out.println("──────────────────────────────────────\n");
        
        class Message {
            String id;
            String content;
            long timestamp;
            
            Message(String id, String content) {
                this.id = id;
                this.content = content;
                this.timestamp = System.currentTimeMillis();
            }
            
            @Override
            public String toString() {
                return "[" + id + "] " + content;
            }
        }
        
        // Message broker using Queue
        Queue<Message> messageBroker = new LinkedList<>();
        
        // Producer
        System.out.println("Producer: Adding messages");
        messageBroker.offer(new Message("M1", "User logged in"));
        messageBroker.offer(new Message("M2", "Payment received"));
        messageBroker.offer(new Message("M3", "Email sent"));
        System.out.println("Messages in broker: " + messageBroker.size());
        System.out.println();
        
        // Consumer
        System.out.println("Consumer: Processing messages (FIFO)");
        int processed = 0;
        while (!messageBroker.isEmpty()) {
            Message msg = messageBroker.poll();  // O(1)
            processed++;
            System.out.println("  Processing: " + msg);
        }
        System.out.println("Total processed: " + processed);
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * DEMO 9: REVISION CHECKLIST
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║             LINKEDLIST REVISION CHECKLIST                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("✓ YOU SHOULD KNOW:");
        System.out.println("  □ LinkedList is doubly-linked, not singly-linked");
        System.out.println("  □ addFirst/addLast: O(1)");
        System.out.println("  □ get(i): O(n) - don't use in loops!");
        System.out.println("  □ remove(i): O(n)");
        System.out.println("  □ removeFirst/removeLast: O(1)");
        System.out.println("  □ Iterator is O(1) per step, total O(n)");
        System.out.println("  □ LinkedList uses 3x more memory than ArrayList");
        System.out.println("  □ ArrayList is default choice ~99% of time");
        System.out.println("  □ LinkedList excels at Queue/Stack/Deque patterns");
        System.out.println("  □ Use Deque interface, not LinkedList directly");
        System.out.println();

        System.out.println("✓ YOU SHOULD AVOID:");
        System.out.println("  □ Looping with get(i) on LinkedList");
        System.out.println("  □ Using LinkedList for random access");
        System.out.println("  □ Modifying while iterating without iterator.remove()");
        System.out.println("  □ Forgetting to check isEmpty() on Queue/Stack");
        System.out.println("  □ Using LinkedList for small collections");
        System.out.println("  □ Building LRU Cache manually (use LinkedHashMap)");
        System.out.println();

        System.out.println("✓ INTERVIEW QUESTIONS YOU CAN ANSWER:");
        System.out.println("  □ How many pointers per node in Java's LinkedList? (2 - prev & next)");
        System.out.println("  □ Why is remove(0) O(n) in ArrayList but O(1) in LinkedList?");
        System.out.println("  □ When would you use LinkedList over ArrayList?");
        System.out.println("  □ How would you implement LRU Cache? (LinkedHashMap!)");
        System.out.println("  □ Why is iterating LinkedList O(n) and not O(∞)? (Iterator!)");
        System.out.println("  □ What's the internal structure of Java's LinkedList?");
        System.out.println();

        System.out.println("═══════════════════════════════════════════════════════════════════");
        System.out.println("                        LEARNING COMPLETE! 🎉");
        System.out.println("═══════════════════════════════════════════════════════════════════");
        System.out.println();
        System.out.println("You now understand:");
        System.out.println("  ✓ LinkedList fundamentals and internal structure");
        System.out.println("  ✓ Time complexity of all operations");
        System.out.println("  ✓ How LinkedList compares to ArrayList");
        System.out.println("  ✓ Real-world usage patterns (Queue, Stack, Deque)");
        System.out.println("  ✓ Professional best practices and common mistakes");
        System.out.println("  ✓ When and how to use LinkedList effectively");
        System.out.println();
        System.out.println("Next Steps:");
        System.out.println("  1. Review files in this order: README → Step1 → ... → Step8");
        System.out.println("  2. Practice writing code with LinkedList");
        System.out.println("  3. Solve LeetCode problems involving LinkedList");
        System.out.println("  4. Understand real collections (ArrayDeque, PriorityQueue)");
        System.out.println("  5. Practice in interviews!");
        System.out.println();
    }
}

