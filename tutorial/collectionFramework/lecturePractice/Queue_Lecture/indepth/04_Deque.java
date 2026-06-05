package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;

/**
 * ============================================================
 *          DEQUE - THE DOUBLE-ENDED QUEUE 🔄
 * ============================================================
 *
 * Bhai, Deque (pronounced "deck") matlab "Double Ended Queue"
 * 
 * Normal Queue: Ek taraf se insert, doosri taraf se remove (ticket counter)
 * Deque: DONO taraf se insert aur remove kar sakte ho! (VIP line 😎)
 *
 * Real-life analogy:
 * - Undo/Redo functionality (add/remove from both ends)
 * - Browser history (back/forward buttons)
 * - Deck of playing cards (add/remove from top or bottom)
 * - Train bogies (attach/detach from front or rear)
 *
 * ============================================================
 *  DEQUE = QUEUE + STACK COMBINED!
 * ============================================================
 *
 *  As QUEUE (FIFO):              As STACK (LIFO):
 *    offerLast()  pollFirst()      push()  pop()
 *    ────────────────────>         ───────────────>
 *    [1] [2] [3] [4] [5]          [5] [4] [3] [2] [1]
 *    <────────────────────         <───────────────
 *    offerFirst()  pollLast()       push()  pop()
 *
 * Pro tip: Deque is a MORE POWERFUL interface than Queue!
 *
 * ============================================================
 *  DEQUE HIERARCHY
 * ============================================================
 *
 *             Queue (interface)
 *                  |
 *             Deque (interface)
 *                  |
 *         +--------+--------+
 *         |                 |
 *   ArrayDeque (class)  LinkedList (class)
 *
 * Industry standard: Use ArrayDeque!
 *
 * ============================================================
 *  DEQUE METHODS (The Complete Set)
 * ============================================================
 *
 * THROWS EXCEPTION        | SPECIAL VALUE      | EQUIVALENT
 * ------------------------|--------------------|-----------
 * addFirst(e)             | offerFirst(e)      | push(e)
 * addLast(e)              | offerLast(e)       | offer(e)
 * removeFirst()           | pollFirst()        | poll(), pop()
 * removeLast()            | pollLast()         | -
 * getFirst()              | peekFirst()        | peek()
 * getLast()               | peekLast()         | -
 *
 * Stack methods (from Deque):
 * - push(e)    = addFirst(e)
 * - pop()      = removeFirst()
 * - peek()     = peekFirst()
 *
 * ============================================================
 *  WHEN TO USE DEQUE?
 * ============================================================
 *
 * 1. IMPLEMENT STACK
 *    → Deque<Integer> stack = new ArrayDeque<>();
 *    → Better than legacy Stack class (not synchronized, faster)
 *
 * 2. IMPLEMENT QUEUE
 *    → Deque<String> queue = new ArrayDeque<>();
 *    → Better than LinkedList for queue operations
 *
 * 3. SLIDING WINDOW PROBLEMS
 *    → Maximum in sliding window of size K
 *    → Remove elements from both ends efficiently
 *
 * 4. PALINDROME CHECKING
 *    → Compare elements from both ends
 *
 * 5. UNDO/REDO FUNCTIONALITY
 *    → Text editors, graphics software
 *
 * 6. WORK STEALING ALGORITHMS
 *    → Thread pools steal tasks from both ends
 *
 * 7. BROWSER HISTORY
 *    → Navigate back/forward
 *
 */
class DequeExamples {

    public static void main(String[] args) {

        System.out.println("===== DEQUE - COMPLETE GUIDE =====\n");

        // ============================================================
        // DEMO 1: Basic Deque Operations
        // ============================================================

        System.out.println("===== DEMO 1: Basic Deque Operations =====\n");

        java.util.Deque<String> deque = new ArrayDeque<>();

        // Adding to both ends
        deque.offerFirst("B");
        System.out.println("After offerFirst(B): " + deque); // [B]

        deque.offerLast("C");
        System.out.println("After offerLast(C):  " + deque); // [B, C]

        deque.offerFirst("A");
        System.out.println("After offerFirst(A): " + deque); // [A, B, C]

        deque.offerLast("D");
        System.out.println("After offerLast(D):  " + deque); // [A, B, C, D]

        // Peeking both ends (without removing)
        System.out.println("\nFirst element (peek): " + deque.peekFirst()); // A
        System.out.println("Last element (peek):  " + deque.peekLast());   // D

        // Removing from both ends
        System.out.println("\nRemoving from first: " + deque.pollFirst()); // A
        System.out.println("Deque now: " + deque); // [B, C, D]

        System.out.println("\nRemoving from last: " + deque.pollLast()); // D
        System.out.println("Deque now: " + deque); // [B, C]

        // ============================================================
        // DEMO 2: Deque as Stack (Modern Java Way)
        // ============================================================

        System.out.println("\n===== DEMO 2: Deque as Stack =====\n");

        // DON'T use legacy Stack class! Use Deque instead.
        java.util.Deque<String> stack = new ArrayDeque<>();

        // Push elements (adds to front)
        stack.push("Java");
        stack.push("Python");
        stack.push("JavaScript");
        System.out.println("Stack after pushes: " + stack);
        // Output: [JavaScript, Python, Java]
        // Top ──────^

        // Peek top (without removing)
        System.out.println("Top element: " + stack.peek()); // JavaScript

        // Pop elements (removes from front) - LIFO
        System.out.println("\nPopping elements (LIFO):");
        while (!stack.isEmpty()) {
            System.out.println("  Popped: " + stack.pop());
        }
        // Output: JavaScript, Python, Java

        // ============================================================
        // DEMO 3: Deque as Queue
        // ============================================================

        System.out.println("\n===== DEMO 3: Deque as Queue =====\n");

        java.util.Deque<String> queue = new ArrayDeque<>();

        // Enqueue (add to rear)
        queue.offerLast("First");
        queue.offerLast("Second");
        queue.offerLast("Third");
        System.out.println("Queue: " + queue);
        // Output: [First, Second, Third]
        // Front ^              ^ Rear

        // Dequeue (remove from front) - FIFO
        System.out.println("\nDequeuing elements (FIFO):");
        while (!queue.isEmpty()) {
            System.out.println("  Dequeued: " + queue.pollFirst());
        }
        // Output: First, Second, Third

        // ============================================================
        // DEMO 4: Real-World - Undo/Redo System
        // ============================================================

        System.out.println("\n===== DEMO 4: Text Editor Undo/Redo =====\n");

        TextEditor editor = new TextEditor();

        editor.type("Hello");
        editor.type(" World");
        editor.type("!");
        System.out.println("Current text: " + editor.getText());

        editor.undo();
        System.out.println("After undo: " + editor.getText());

        editor.undo();
        System.out.println("After undo: " + editor.getText());

        editor.redo();
        System.out.println("After redo: " + editor.getText());

        editor.type(" Java");
        System.out.println("After typing: " + editor.getText());

        // ============================================================
        // DEMO 5: Real-World - Browser History
        // ============================================================

        System.out.println("\n===== DEMO 5: Browser History Navigation =====\n");

        BrowserHistory browser = new BrowserHistory();

        browser.visit("google.com");
        browser.visit("youtube.com");
        browser.visit("stackoverflow.com");
        browser.visit("github.com");

        System.out.println("Current page: " + browser.getCurrentPage());

        browser.back();
        System.out.println("After back: " + browser.getCurrentPage());

        browser.back();
        System.out.println("After back: " + browser.getCurrentPage());

        browser.forward();
        System.out.println("After forward: " + browser.getCurrentPage());

        browser.visit("leetcode.com");
        System.out.println("After visiting new page: " + browser.getCurrentPage());

        // ============================================================
        // DEMO 6: Sliding Window Maximum (LeetCode Hard Problem)
        // ============================================================

        System.out.println("\n===== DEMO 6: Sliding Window Maximum =====\n");

        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int windowSize = 3;

        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Window size: " + windowSize);

        int[] maxInWindows = slidingWindowMaximum(nums, windowSize);
        System.out.println("Maximum in each window: " + Arrays.toString(maxInWindows));

        // ============================================================
        // DEMO 7: Palindrome Checker using Deque
        // ============================================================

        System.out.println("\n===== DEMO 7: Palindrome Checker =====\n");

        String[] testStrings = {"racecar", "hello", "madam", "java", "noon"};

        for (String str : testStrings) {
            boolean isPalindrome = isPalindrome(str);
            System.out.println(str + " → " + (isPalindrome ? "Palindrome ✅" : "Not Palindrome ❌"));
        }

        // ============================================================
        // DEMO 8: Performance - ArrayDeque vs LinkedList as Deque
        // ============================================================

        System.out.println("\n===== DEMO 8: Performance Comparison =====\n");

        int iterations = 1_000_000;

        // ArrayDeque benchmark
        java.util.Deque<Integer> arrayDeque = new ArrayDeque<>();
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            arrayDeque.offerFirst(i);
            arrayDeque.offerLast(i);
            if (i % 3 == 0) arrayDeque.pollFirst();
            if (i % 5 == 0) arrayDeque.pollLast();
        }
        long arrayDequeTime = System.nanoTime() - start;

        // LinkedList benchmark
        java.util.Deque<Integer> linkedList = new LinkedList<>();
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            linkedList.offerFirst(i);
            linkedList.offerLast(i);
            if (i % 3 == 0) linkedList.pollFirst();
            if (i % 5 == 0) linkedList.pollLast();
        }
        long linkedListTime = System.nanoTime() - start;

        System.out.println("Deque operations (" + iterations + " iterations):");
        System.out.println("  ArrayDeque: " + arrayDequeTime / 1_000_000 + " ms");
        System.out.println("  LinkedList: " + linkedListTime / 1_000_000 + " ms");
        System.out.println("  Winner: " + (arrayDequeTime < linkedListTime ? "ArrayDeque 🏆" : "LinkedList 🏆"));

        // ============================================================
        // DEMO 9: Exception vs Special Value Methods
        // ============================================================

        System.out.println("\n===== DEMO 9: Exception Handling =====\n");

        java.util.Deque<String> emptyDeque = new ArrayDeque<>();

        // Special value methods (return null) - PREFERRED
        System.out.println("pollFirst() on empty: " + emptyDeque.pollFirst()); // null
        System.out.println("peekLast() on empty: " + emptyDeque.peekLast());   // null

        // Exception methods (throw exception)
        try {
            emptyDeque.removeFirst();
        } catch (NoSuchElementException e) {
            System.out.println("❌ removeFirst() threw: " + e.getClass().getSimpleName());
        }

        try {
            emptyDeque.getLast();
        } catch (NoSuchElementException e) {
            System.out.println("❌ getLast() threw: " + e.getClass().getSimpleName());
        }

        System.out.println("\n💡 Always prefer poll/peek over remove/get methods!");

        System.out.println("\n===== ALL DEQUE DEMOS COMPLETE =====");
    }

    /**
     * Sliding Window Maximum problem
     * Find maximum in each window of size k
     * 
     * Time: O(n), Space: O(k)
     * Industry use: Stock price analysis, network traffic monitoring
     */
    private static int[] slidingWindowMaximum(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];
        int resultIndex = 0;

        // Deque stores INDICES, not values
        // Maintains decreasing order of values
        java.util.Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Remove indices outside current window
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Remove smaller elements from rear (they'll never be max)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Add to result once window is full
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    /**
     * Check if string is palindrome using Deque
     * Time: O(n), Space: O(n)
     */
    private static boolean isPalindrome(String str) {
        java.util.Deque<Character> deque = new ArrayDeque<>();
        
        // Add all characters to deque
        for (char c : str.toLowerCase().toCharArray()) {
            deque.offerLast(c);
        }

        // Compare from both ends
        while (deque.size() > 1) {
            if (!deque.pollFirst().equals(deque.pollLast())) {
                return false;
            }
        }

        return true;
    }
}

/**
 * Text Editor with Undo/Redo functionality using Deque
 */
class TextEditor {
    private StringBuilder text = new StringBuilder();
    private java.util.Deque<String> undoStack = new ArrayDeque<>();
    private java.util.Deque<String> redoStack = new ArrayDeque<>();

    public void type(String newText) {
        undoStack.push(text.toString()); // Save current state
        text.append(newText);
        redoStack.clear(); // Clear redo stack on new action
        System.out.println("Typed: " + newText);
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }
        redoStack.push(text.toString()); // Save current state to redo
        text = new StringBuilder(undoStack.pop()); // Restore previous state
        System.out.println("Undo performed");
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo");
            return;
        }
        undoStack.push(text.toString()); // Save current state to undo
        text = new StringBuilder(redoStack.pop()); // Restore next state
        System.out.println("Redo performed");
    }

    public String getText() {
        return text.toString();
    }
}

/**
 * Browser History with Back/Forward navigation using Deque
 */
class BrowserHistory {
    private java.util.Deque<String> backStack = new ArrayDeque<>();
    private java.util.Deque<String> forwardStack = new ArrayDeque<>();
    private String currentPage = null;

    public void visit(String url) {
        if (currentPage != null) {
            backStack.push(currentPage);
        }
        currentPage = url;
        forwardStack.clear(); // Clear forward history on new visit
        System.out.println("Visited: " + url);
    }

    public void back() {
        if (backStack.isEmpty()) {
            System.out.println("No previous page");
            return;
        }
        forwardStack.push(currentPage);
        currentPage = backStack.pop();
        System.out.println("Going back...");
    }

    public void forward() {
        if (forwardStack.isEmpty()) {
            System.out.println("No forward page");
            return;
        }
        backStack.push(currentPage);
        currentPage = forwardStack.pop();
        System.out.println("Going forward...");
    }

    public String getCurrentPage() {
        return currentPage;
    }
}

/*
 * ============================================================
 *  KEY TAKEAWAYS
 * ============================================================
 *
 *  ✅ Deque = Double-ended queue (add/remove from both ends)
 *  ✅ Can be used as Stack OR Queue (more flexible!)
 *  ✅ ArrayDeque is FASTER than LinkedList for deque operations
 *  ✅ Modern Java: Use Deque instead of Stack class
 *  ✅ Prefer offer/poll/peek over add/remove/get (avoid exceptions)
 *  ✅ Does NOT allow null elements (ArrayDeque)
 *  ✅ NOT thread-safe (use ConcurrentLinkedDeque for threads)
 *
 * ============================================================
 *  INDUSTRY USE CASES
 * ============================================================
 *
 *  1. Stack implementation (better than Stack class)
 *  2. Queue implementation (better than LinkedList)
 *  3. Undo/Redo functionality (text editors, graphics)
 *  4. Browser history navigation
 *  5. Sliding window problems (algorithm)
 *  6. Work-stealing thread pools
 *  7. Palindrome checking
 *  8. Expression evaluation (infix to postfix)
 *  9. LRU cache implementation
 *  10. Parenthesis matching
 *
 * ============================================================
 *  COMMON INTERVIEW QUESTIONS
 * ============================================================
 *
 *  Q: What is the difference between Queue and Deque?
 *  A: Queue: insert at rear, remove from front (one direction)
 *     Deque: insert/remove from BOTH ends (bidirectional)
 *
 *  Q: Why use Deque instead of Stack class?
 *  A: 1. Stack is legacy (synchronized, slow)
 *     2. Deque is faster (ArrayDeque is cache-friendly)
 *     3. Deque is more flexible (can be queue or stack)
 *
 *  Q: ArrayDeque vs LinkedList as Deque?
 *  A: ArrayDeque is faster in 99% cases (better cache locality)
 *
 *  Q: How to implement LRU cache using Deque?
 *  A: Use LinkedHashMap or Deque + HashMap combination
 *
 *  Q: Time complexity of Deque operations?
 *  A: All operations (add/remove/peek from both ends) are O(1)
 *
 * ============================================================
 *  METHOD CHEAT SHEET
 * ============================================================
 *
 *  As STACK:              As QUEUE:
 *  push(e)               offerLast(e)  // enqueue
 *  pop()                 pollFirst()   // dequeue
 *  peek()                peekFirst()
 *
 *  From BOTH ends:
 *  offerFirst(e)  offerLast(e)
 *  pollFirst()    pollLast()
 *  peekFirst()    peekLast()
 *
 */
