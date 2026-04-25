package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * CONFUSIONS AND COMMON MISTAKES IN MULTITHREADING
 * 
 * This file documents common mistakes developers make when working with threads
 * and explains why they're wrong. Learn from these to avoid them in your code.
 */

public class ConfusionsAndMistakes {
    
    /*
     * ============================================================================
     * MISTAKE 1: CALLING run() INSTEAD OF start()
     * ============================================================================
     * 
     * WRONG:
     * Thread thread = new Thread(() -> {
     *     System.out.println("Thread work");
     *     Thread.sleep(1000);
     * });
     * thread.run();  // WRONG! Executes in main thread, NOT multithreading!
     * 
     * WHY IT'S WRONG:
     * - run() is just a method call, executed by calling thread (main)
     * - Does NOT create a new thread
     * - Thread.sleep() blocks the main thread, not a separate thread
     * - Completely sequential, no parallelism
     * 
     * CORRECT:
     * thread.start();  // RIGHT! Creates new thread and calls run() in it
     * 
     * SYMPTOMS:
     * - Code runs sequentially despite using threads
     * - All work happens in main thread
     * - No performance improvement
     * - Thread.getName() shows "main" instead of thread name
     * 
     * TEST:
     * System.out.println(Thread.currentThread().getName());
     * // If prints "main", you're executing in main thread!
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 2: NOT HANDLING InterruptedException PROPERLY
     * ============================================================================
     * 
     * WRONG 1: Silently swallowing exception
     * try {
     *     Thread.sleep(1000);
     * } catch (InterruptedException e) {
     *     // Doing nothing!
     *     // Interrupt status is cleared but not re-raised
     * }
     * // Thread continues running even though it was interrupted!
     * 
     * WRONG 2: Catching and ignoring
     * try {
     *     queue.take();
     * } catch (InterruptedException e) {
     *     System.out.println("Interrupted");
     *     // Thread continues, parent doesn't know it was interrupted
     * }
     * 
     * WHY IT'S WRONG:
     * - Interrupt flag is cleared when exception is caught
     * - Parent thread expecting clean shutdown doesn't know
     * - Thread might be in middle of critical operation
     * - Can cause resource leaks or data corruption
     * 
     * CORRECT 1: Re-raise interrupt status
     * try {
     *     Thread.sleep(1000);
     * } catch (InterruptedException e) {
     *     Thread.currentThread().interrupt();  // Re-set interrupt flag
     *     return;  // Exit the method
     * }
     * 
     * CORRECT 2: Propagate up
     * try {
     *     queue.take();
     * } catch (InterruptedException e) {
     *     Thread.currentThread().interrupt();  // Set flag
     *     break;  // Exit loop/return from method
     * }
     * 
     * CORRECT 3: Check interrupt status in loop
     * while (!Thread.currentThread().isInterrupted()) {
     *     // Do work
     * }
     * 
     * BEST: Use try-catch-finally for cleanup
     * try {
     *     Thread.sleep(1000);
     * } catch (InterruptedException e) {
     *     // Cleanup code
     *     cleanup();
     *     Thread.currentThread().interrupt();
     *     return;
     * } finally {
     *     // More cleanup if needed
     * }
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 3: RACE CONDITION - NOT SYNCHRONIZING SHARED DATA
     * ============================================================================
     * 
     * WRONG: Multiple threads modifying without synchronization
     * class Counter {
     *     private int count = 0;  // Shared, no synchronization
     *     
     *     public void increment() {
     *         count++;  // RACE CONDITION! Read-modify-write is not atomic
     *     }
     * }
     * 
     * Expected with 5 threads, 1000 increments each:
     * count = 5000
     * 
     * Actual: Often 4000 or 3500 or some other number!
     * 
     * WHY:
     * count++ is actually 3 operations:
     * 1. Read count value (say 100)
     * 2. Add 1 (result = 101)
     * 3. Write back to count
     * 
     * If two threads do this simultaneously:
     * Thread A: read 100, add 1 = 101
     * Thread B: read 100, add 1 = 101
     * Thread A: write 101
     * Thread B: write 101
     * Result: 101 (not 102!)
     * 
     * CORRECT:
     * public synchronized void increment() {
     *     count++;
     * }
     * OR
     * private AtomicInteger count = new AtomicInteger(0);
     * public void increment() {
     *     count.incrementAndGet();
     * }
     * 
     * LESSON: Always protect shared mutable state!
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 4: USING wait() OUTSIDE synchronized BLOCK
     * ============================================================================
     * 
     * WRONG:
     * while (!dataAvailable) {
     *     wait();  // Compile error! (if uncommented)
     * }
     * 
     * WRONG: Not checking condition
     * if (dataAvailable) {
     *     synchronized(lock) {
     *         wait();  // What if data becomes unavailable between check and wait?
     *     }
     * }
     * 
     * WHY IT'S WRONG:
     * - wait() MUST be called from synchronized context
     * - Can only be called on lock object
     * - If condition changes between check and wait, you miss the notification
     * 
     * CORRECT:
     * synchronized(lock) {
     *     while (!dataAvailable) {  // while, not if!
     *         wait();
     *     }
     *     // Process data
     * }
     * 
     * WHY while and not if:
     * - After notification, condition might be false again
     * - Spurious wakeups can occur
     * - Multiple threads might be notified but resource limited
     * 
     * EXAMPLE:
     * // Bad: Multiple consumers but only one item
     * synchronized(lock) {
     *     if (!buffer.isEmpty()) {  // If, not while!
     *         item = buffer.poll();  // First consumer takes it
     *     }
     *     wait();  // Second consumer still waits (data is gone!)
     * }
     * 
     * // Good:
     * synchronized(lock) {
     *     while (buffer.isEmpty()) {  // While!
     *         wait();  // All consumers check after waking
     *     }
     *     item = buffer.poll();  // Safe now
     * }
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 5: CONFUSION: notify() vs notifyAll()
     * ============================================================================
     * 
     * SCENARIO: Producer-Consumer with multiple consumers
     * 
     * WRONG: Using notify()
     * class Buffer {
     *     private Queue<Item> items = new LinkedList<>();
     *     
     *     public void produce(Item item) {
     *         synchronized(this) {
     *             items.add(item);
     *             notify();  // Wakes ONE waiting thread
     *         }
     *     }
     *     
     *     public Item consume() {
     *         synchronized(this) {
     *             while (items.isEmpty()) {
     *                 wait();  // Waits for notification
     *             }
     *             return items.poll();
     *         }
     *     }
     * }
     * 
     * PROBLEM:
     * - Thread1 (producer) adds 1 item, calls notify()
     * - Thread2 (consumer) wakes up, takes item
     * - Thread3 (consumer) still waiting (notify() only wakes 1!)
     * - If producer never adds more, Thread3 waits forever
     * - Other waiting threads might be consumers OR producers
     * - Notification meant for consumer might wake producer
     * 
     * CORRECT: Using notifyAll()
     * public void produce(Item item) {
     *     synchronized(this) {
     *         items.add(item);
     *         notifyAll();  // Wakes ALL waiting threads
     *     }
     * }
     * 
     * WHEN TO USE:
     * - notify(): Only if 100% sure all waiting threads can proceed
     * - notifyAll(): When in doubt (usually safer)
     * 
     * PERFORMANCE:
     * - notifyAll() wakes all threads
     * - Threads compete for lock
     * - Some threads will wait again
     * - Slight performance cost, but correctness > performance
     * 
     * RULE: Use notifyAll() unless you have specific reason for notify()
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 6: DEADLOCK - HOLDING LOCK WHILE WAITING FOR ANOTHER
     * ============================================================================
     * 
     * DEADLOCK SCENARIO:
     * Imagine:
     * Account A (initially $100)
     * Account B (initially $50)
     * Thread 1: Transfer $10 from A to B
     * Thread 2: Transfer $5 from B to A
     * 
     * WRONG: Lock in inconsistent order
     * class Account {
     *     private int balance;
     *     
     *     public void transfer(Account toAccount, int amount) {
     *         synchronized(this) {
     *             synchronized(toAccount) {  // Nested lock!
     *                 this.balance -= amount;
     *                 toAccount.balance += amount;
     *             }
     *         }
     *     }
     * }
     * 
     * DEADLOCK:
     * Thread 1: Locks accountA, tries to lock accountB (waits)
     * Thread 2: Locks accountB, tries to lock accountA (waits)
     * Both waiting forever! DEADLOCK!
     * 
     * CORRECT: Always lock in consistent order
     * public void transfer(Account toAccount, int amount) {
     *     Account first, second;
     *     
     *     // Always lock accounts in consistent order (by ID)
     *     if (this.id < toAccount.id) {
     *         first = this;
     *         second = toAccount;
     *     } else {
     *         first = toAccount;
     *         second = this;
     *     }
     *     
     *     synchronized(first) {
     *         synchronized(second) {
     *             this.balance -= amount;
     *             toAccount.balance += amount;
     *         }
     *     }
     * }
     * 
     * Thread 1: Locks A (id=1), then B (id=2) - proceeds
     * Thread 2: Locks A (id=1) (waits for release), then B - proceeds after 1 finishes
     * No deadlock!
     * 
     * DETECTION:
     * - Program seems hung, no output, no exception
     * - All threads seem to be waiting
     * - CPU usage very low (threads not running)
     * - Use jstack command to see thread dump
     * 
     * PREVENTION:
     * 1. Always acquire locks in same order
     * 2. Minimize critical sections
     * 3. Don't hold lock while acquiring another
     * 4. Use timeout: lock.tryLock(timeout)
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 7: CONFUSION: volatile vs synchronized vs AtomicInteger
     * ============================================================================
     * 
     * WRONG: Using volatile for counter
     * class Counter {
     *     private volatile int count = 0;
     *     
     *     public void increment() {
     *         count++;  // NOT atomic! Still a race condition!
     *     }
     * }
     * 
     * WHAT VOLATILE DOES:
     * - Ensures visibility (writes seen by other threads)
     * - Does NOT make operations atomic
     * 
     * WHAT count++ NEEDS:
     * - Atomicity (read-modify-write as single operation)
     * - Visibility (others see the change)
     * 
     * count++ OPERATIONS:
     * 1. Read count
     * 2. Increment
     * 3. Write count
     * 
     * Even with volatile:
     * Thread A: read 100
     * Thread B: read 100
     * Thread A: increment to 101, write back
     * Thread B: increment to 101, write back
     * Result: 101 (one increment lost!)
     * 
     * CORRECT SOLUTIONS:
     * 
     * Option 1: synchronized (simple, but slower)
     * public synchronized void increment() {
     *     count++;
     * }
     * 
     * Option 2: AtomicInteger (best for counters)
     * private AtomicInteger count = new AtomicInteger(0);
     * public void increment() {
     *     count.incrementAndGet();
     * }
     * 
     * Option 3: volatile (only for visibility, not atomicity)
     * private volatile int flag = 0;
     * // Set by one thread, read by others (no compound operations)
     * 
     * WHEN TO USE:
     * volatile      : Simple flags, one writer, multiple readers
     * synchronized  : Simple operations, low contention
     * AtomicInteger : Counters, high contention
     * Lock          : Complex operations, high contention, need timeout
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 8: NOT SHUTTING DOWN EXECUTOR / THREADS NOT TERMINATING
     * ============================================================================
     * 
     * WRONG: Creating executor but not shutting down
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * for (Task task : tasks) {
     *     executor.execute(task);
     * }
     * // Program never exits!
     * // Executor threads keep running, JVM doesn't terminate
     * 
     * SYMPTOMS:
     * - Program seems to finish but doesn't exit
     * - Process still running (check with ps command)
     * - No error message
     * - Waiting for threads to stop
     * 
     * CORRECT: Shutdown executor
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * try {
     *     for (Task task : tasks) {
     *         executor.execute(task);
     *     }
     * } finally {
     *     executor.shutdown();  // Stop accepting new tasks
     *     if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
     *         executor.shutdownNow();  // Force shutdown
     *     }
     * }
     * System.out.println("Program completed and exiting");
     * 
     * DAEMON THREADS: Automagical solution but different semantics
     * Thread t = new Thread(runnable);
     * t.setDaemon(true);  // Won't prevent JVM exit
     * t.start();
     * // JVM can exit even if daemon thread running
     * 
     * BUT: Daemon threads are interrupted abruptly, might lose data
     * Use for background tasks, not critical work
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 9: THREAD UNSAFE ITERATORS ON CONCURRENT COLLECTIONS
     * ============================================================================
     * 
     * WRONG: Iterating without synchronization (with regular List)
     * ArrayList<String> list = new ArrayList<>();
     * new Thread(() -> {
     *     while (true) {
     *         for (String item : list) {  // Thread 1 iterating
     *             System.out.println(item);
     *         }
     *     }
     * }).start();
     * 
     * new Thread(() -> {
     *     for (int i = 0; i < 100; i++) {
     *         list.add("Item-" + i);  // Thread 2 modifying
     *     }
     * }).start();
     * // Result: ConcurrentModificationException!
     * 
     * WHY: ArrayList iterator maintains internal state
     * When list is modified, iterator state becomes invalid
     * 
     * CORRECT: Use concurrent collections
     * CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
     * // Or ConcurrentHashMap, ConcurrentLinkedQueue, etc.
     * 
     * NEW RULE: When using Collections.synchronizedList():
     * List<String> syncList = Collections.synchronizedList(new ArrayList<>());
     * synchronized(syncList) {
     *     for (String item : syncList) {
     *         System.out.println(item);
     *     }
     * }
     * // Must synchronize iteration explicitly!
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 10: SPURIOUS WAKEUPS (wait() waking without notification)
     * ============================================================================
     * 
     * WRONG: Checking condition with if
     * synchronized(lock) {
     *     if (!dataReady) {
     *         wait();
     *     }
     *     processData();  // Data might not actually be ready!
     * }
     * 
     * WHY: Spurious Wakeups
     * - Thread can wake up without being notified
     * - Condition might no longer be true
     * - Multiple threads woken but only one resource
     * 
     * SCENARIO:
     * Thread 1: wait() because buffer empty
     * Producer: adds 1 item, notify()
     * Thread 2: also wakes up (spurious wakeup)
     * Thread 1: takes item and returns
     * Thread 2: wakes up, no item (but tries to take) = error!
     * 
     * CORRECT: Check condition in while loop
     * synchronized(lock) {
     *     while (!dataReady) {  // while, not if!
     *         wait();
     *     }
     *     processData();  // Now guaranteed dataReady is true
     * }
     * 
     * If Thread 2 wakes spuriously, condition is rechecked
     * Buffer empty, so wait() again
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 11: CREATING TOO MANY THREADS
     * ============================================================================
     * 
     * WRONG: Creating new thread for every task
     * for (int i = 0; i < 10000; i++) {
     *     new Thread(new Task(i)).start();  // 10,000 threads!
     * }
     * 
     * PROBLEMS:
     * - Each thread needs ~1MB stack: 10GB memory!
     * - JVM crashes or hangs with OutOfMemoryError
     * - Thousands of threads = extreme context switching
     * - Performance actually degrades
     * - Threads compete for CPU, thrashing occurs
     * 
     * Typical systems:
     * - Laptop: 100-500 threads max
     * - Server: 1000-5000 threads max
     * - Beyond that: severe degradation
     * 
     * CORRECT: Use thread pool
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * for (int i = 0; i < 10000; i++) {
     *     executor.execute(new Task(i));  // 10 threads handle 10,000 tasks
     * }
     * // Tasks queued and executed as threads become available
     * 
     * FORMULA: 
     * Pool size = (# CPU cores) * (1 + wait_time/compute_time)
     * 
     * Example:
     * - 4 cores
     * - Tasks: 50% computing, 50% waiting (1:1 ratio)
     * - Pool size = 4 * (1 + 1) = 8 threads
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 12: THREAD VISIBILITY - CHANGES NOT SEEN BY OTHER THREADS
     * ============================================================================
     * 
     * WRONG: Assuming changes are immediately visible
     * class Flag {
     *     private boolean running = true;  // No synchronization
     *     
     *     public void stop() {
     *         running = false;
     *     }
     *     
     *     public void work() {
     *         while (running) {  // Might see cached value!
     *             doWork();
     *         }
     *     }
     * }
     * 
     * Thread 1: running = true, cached in CPU register
     * Thread 2: calls stop(), sets running = false (main memory)
     * Thread 1: still sees true (reads from register), never stops!
     * 
     * This is due to CPU/JVM optimizations and memory caching
     * 
     * CORRECT: Use volatile or synchronization
     * private volatile boolean running = true;
     * // volatile: Tells JVM to always read/write from main memory
     * 
     * OR
     * private boolean running = true;
     * public synchronized void stop() { running = false; }
     * public synchronized void work() { while(running) {...} }
     * 
     * LESSON: Don't assume memory visibility in multithreaded code!
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 13: COMMON EXCEPTION MISUNDERSTANDINGS
     * ============================================================================
     * 
     * WRONG: Not understanding ConcurrentModificationException
     * CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
     * list.add("A");
     * list.add("B");
     * 
     * for (String s : list) {
     *     if (s.equals("A")) {
     *         list.remove(s);  // Looks safe with CopyOnWriteArrayList
     *     }
     * }
     * // Actually OK with CopyOnWriteArrayList (weak consistency)
     * // But problematic with ArrayList (would throw exception)
     * 
     * CONFUSION: When exception is thrown
     * ArrayList<String> list = new ArrayList<>();
     * list.add("A");
     * 
     * Thread 1: for (String s : list)  // Iterating
     * Thread 2: list.remove("A")      // Modifying
     * Result: ConcurrentModificationException
     * 
     * Reason: ArrayList's iterator checks if list was modified
     * If modification count changes, exception thrown
     * 
     * NOT ABOUT: Concurrent modification by multiple threads
     * It's about STRUCTURAL modification (add/remove)
     * 
     * WHY CopyOnWriteArrayList doesn't throw:
     * - Iterator gets snapshot of data at iteration start
     * - Modifications create new copy
     * - Iterator still sees original copy
     * - Never detects modification
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 14: MEMORY LEAKS WITH THREADLOCAL
     * ============================================================================
     * 
     * WRONG: Not cleaning up ThreadLocal
     * class Database {
     *     private static ThreadLocal<Connection> connHolder = 
     *         ThreadLocal.withInitial(() -> new Connection());
     * }
     * 
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * for (int i = 0; i < 1000000; i++) {
     *     executor.execute(() -> {
     *         Connection conn = Database.connHolder.get();
     *         // Use connection
     *     });
     * }
     * 
     * MEMORY LEAK:
     * - Thread pool reuses threads
     * - ThreadLocal data persists between tasks
     * - 1 million tasks run, but only 10 threads
     * - Each thread accumulates ThreadLocal data
     * - Memory fills up with unused Connection objects
     * 
     * CORRECT: Always clean up
     * executor.execute(() -> {
     *     try {
     *         Connection conn = Database.connHolder.get();
     *         // Use connection
     *     } finally {
     *         Database.connHolder.remove();  // IMPORTANT!
     *     }
     * });
     * 
     * OR use try-with-resources if possible
     * try (Connection conn = Database.connHolder.get()) {
     *     // Use connection
     * } finally {
     *     Database.connHolder.remove();
     * }
     * 
     * LESSON: ThreadLocal in thread pools requires explicit cleanup
     */
    
    
    /*
     * ============================================================================
     * MISTAKE 15: THINKING THREAD.SLEEP() GUARANTEES WAKE TIME
     * ============================================================================
     * 
     * WRONG: Expecting exact timing
     * Thread.sleep(1000);  // Sleep for 1 second exactly?
     * // NO! Guaranteed MINIMUM 1 second, might be much longer
     * 
     * REALITY:
     * Thread.sleep(1000);  // Might wake after 1, 2, 5, 10 seconds!
     * // Depends on:
     * // - System load
     * // - JVM garbage collection
     * // - Kernel scheduling
     * // - Other processes on system
     * 
     * NOT FOR: Precise timing
     * NOT FOR: Real-time applications
     * 
     * GOOD FOR: Approximate delays, testing
     * Thread.sleep(100);  // 100ms+ delay (testing)
     * 
     * PRECISE TIMING:
     * long startTime = System.nanoTime();
     * while (System.nanoTime() - startTime < 1_000_000_000) {
     *     // Busy wait (consumes CPU though!)
     * }
     * 
     * OR: Use ScheduledExecutorService for periodic tasks
     * ScheduledExecutorService scheduler = 
     *     Executors.newScheduledThreadPool(1);
     * scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
     */
    
    
    public static void main(String[] args) {
        System.out.println("COMMON MULTITHREADING MISTAKES REFERENCE GUIDE");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("Review the comments in this file to understand:");
        System.out.println();
        System.out.println("1.  Calling run() instead of start()");
        System.out.println("2.  Not handling InterruptedException");
        System.out.println("3.  Race conditions (missing synchronization)");
        System.out.println("4.  Using wait() outside synchronized block");
        System.out.println("5.  notify() vs notifyAll() confusion");
        System.out.println("6.  Deadlocks (inconsistent lock ordering)");
        System.out.println("7.  volatile vs synchronized vs AtomicInteger");
        System.out.println("8.  Not shutting down threads/executors");
        System.out.println("9.  Thread-unsafe iteration");
        System.out.println("10. Spurious wakeups (using if instead of while)");
        System.out.println("11. Creating too many threads");
        System.out.println("12. Memory visibility issues");
        System.out.println("13. ConcurrentModificationException confusion");
        System.out.println("14. ThreadLocal memory leaks");
        System.out.println("15. Thread.sleep() timing expectations");
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("Each mistake includes:");
        System.out.println("- Wrong code example");
        System.out.println("- Why it's wrong");
        System.out.println("- Symptoms you'll see");
        System.out.println("- Correct solution");
        System.out.println("- Best practices to follow");
    }
}

