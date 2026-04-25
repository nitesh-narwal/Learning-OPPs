package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * TIPS AND TRICKS FOR MULTITHREADING
 * 
 * This file contains best practices, performance tips, and expert advice
 * for writing efficient and safe multithreaded code.
 */

public class TipsAndTricks {
    
    /*
     * ============================================================================
     * TIP 1: PREFER IMMUTABLE OBJECTS IN MULTITHREADED ENVIRONMENT
     * ============================================================================
     * 
     * PROBLEM: Mutable shared state requires synchronization
     * 
     * Example (BAD - Mutable):
     * class Person {
     *     private String name;           // Can be changed by any thread
     *     private int age;               // Must synchronize access
     * }
     * 
     * Example (GOOD - Immutable):
     * class Person {
     *     private final String name;     // Cannot be changed
     *     private final int age;         // Cannot be changed
     *     
     *     public Person(String name, int age) {
     *         this.name = name;
     *         this.age = age;
     *     }
     * }
     * 
     * BENEFITS:
     * - No synchronization needed
     * - Thread-safe by design
     * - Easier to reason about code
     * - Better cache locality
     * 
     * TIP: Mark fields as "final" when possible
     */
    
    
    /*
     * ============================================================================
     * TIP 2: USE THREAD POOLS INSTEAD OF CREATING NEW THREADS
     * ============================================================================
     * 
     * BAD: Creating new thread for each task
     * for (int i = 0; i < 1000; i++) {
     *     new Thread(new Task()).start();  // 1000 threads! Too expensive
     * }
     * 
     * GOOD: Using thread pool
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * for (int i = 0; i < 1000; i++) {
     *     executor.execute(new Task());   // 10 threads handle 1000 tasks
     * }
     * 
     * BENEFITS:
     * - Reduced memory usage (reuse threads)
     * - Better CPU utilization
     * - Automatic task queuing
     * - Easy shutdown mechanism
     * - Can scale easily
     * 
     * THREAD CREATION OVERHEAD:
     * - Stack allocation (~512KB to 1MB per thread)
     * - Context switching overhead
     * - Scheduler overhead
     * 
     * Use ThreadPoolExecutor with custom settings for fine control:
     * new ThreadPoolExecutor(
     *     corePoolSize,      // Threads always running
     *     maxPoolSize,       // Maximum threads
     *     keepAliveTime,     // How long to keep idle threads
     *     timeUnit,
     *     workQueue,         // Task queue
     *     threadFactory,     // Custom thread creation
     *     rejectionHandler   // What to do if queue is full
     * )
     */
    
    
    /*
     * ============================================================================
     * TIP 3: MINIMIZE CRITICAL SECTIONS (synchronized blocks)
     * ============================================================================
     * 
     * BAD: Large synchronized block
     * synchronized(lock) {
     *     // 100 lines of code here!
     *     // Most of it doesn't need synchronization
     *     loadDataFromFile();       // Slow I/O
     *     int result = calculate(); // CPU-intensive
     *     updateDatabase();         // Another slow I/O
     * }
     * 
     * GOOD: Only synchronize necessary parts
     * Data data = loadDataFromFile();     // No lock needed
     * int result = calculate(data);       // No lock needed
     * 
     * synchronized(lock) {
     *     // Only this part needs synchronization
     *     updateSharedState(result);
     * }
     * 
     * Benefits:
     * - Other threads can proceed while lock is not held
     * - Reduced lock contention
     * - Better overall throughput
     * - Less chance of deadlock
     * 
     * RULE: Acquire lock LATE, Release lock EARLY
     */
    
    
    /*
     * ============================================================================
     * TIP 4: AVOID NESTED LOCKS (DEADLOCK PREVENTION)
     * ============================================================================
     * 
     * DEADLOCK SCENARIO:
     * Thread 1: Acquires lock A, wants lock B
     * Thread 2: Acquires lock B, wants lock A
     * Result: Both threads wait forever!
     * 
     * BAD: Nested locks without ordering
     * Object lockA = new Object();
     * Object lockB = new Object();
     * 
     * Thread 1:                      Thread 2:
     * synchronized(lockA) {          synchronized(lockB) {
     *     synchronized(lockB) {           synchronized(lockA) {
     *         // ...                          // ...
     *     }                              }
     * }                              }
     * 
     * GOOD: Always acquire locks in same order
     * synchronized(lockA) {          synchronized(lockA) {
     *     synchronized(lockB) {           synchronized(lockB) {
     *         // ...                          // ...
     *     }                              }
     * }
     * 
     * EVEN BETTER: Use timeout to detect deadlock
     * Lock lockA = new ReentrantLock();
     * if (lockA.tryLock(5, TimeUnit.SECONDS)) {
     *     try {
     *         // Do work
     *     } finally {
     *         lockA.unlock();
     *     }
     * } else {
     *     // Timeout - might be deadlock
     * }
     * 
     * PREVENTION:
     * 1. Always acquire locks in the same order
     * 2. Never hold lock while acquiring another
     * 3. Use timeouts with tryLock()
     * 4. Avoid nested synchronization
     */
    
    
    /*
     * ============================================================================
     * TIP 5: UNDERSTAND HAPPENS-BEFORE RELATIONSHIP
     * ============================================================================
     * 
     * Java Memory Model guarantees visibility through:
     * 1. synchronized blocks/methods
     * 2. volatile variables
     * 3. Thread.start() and Thread.join()
     * 4. AtomicX operations
     * 
     * IMPORTANT: Without these, writes by one thread may not be visible to others!
     * 
     * BAD: Data visibility issue
     * class Data {
     *     private int value = 0;      // Regular field
     *     
     *     public void set(int val) {
     *         value = val;            // Thread 1 writes
     *     }
     *     
     *     public int get() {
     *         return value;           // Thread 2 reads (might see old value!)
     *     }
     * }
     * 
     * GOOD: Use volatile for visibility
     * class Data {
     *     private volatile int value = 0;  // Volatile ensures visibility
     *     
     *     public void set(int val) {
     *         value = val;
     *     }
     *     
     *     public int get() {
     *         return value;
     *     }
     * }
     * 
     * volatile: Tells JVM to always read/write from main memory, not cache
     */
    
    
    /*
     * ============================================================================
     * TIP 6: USE CONCURRENT COLLECTIONS INSTEAD OF SYNCHRONIZED WRAPPERS
     * ============================================================================
     * 
     * BAD: Collections.synchronizedList/Map (locks entire collection)
     * List<String> list = Collections.synchronizedList(new ArrayList<>());
     * for (String item : list) {
     *     // Problem: Entire list is locked during iteration
     *     // Slow performance
     * }
     * 
     * GOOD: ConcurrentHashMap/CopyOnWriteArrayList (fine-grained locking)
     * ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
     * map.forEach((key, value) -> {
     *     // Only affected bucket is locked
     *     // Better performance
     * });
     * 
     * COMPARISON:
     * Collections.synchronizedMap()  : Full lock on entire map
     * ConcurrentHashMap              : Lock per segment/bucket
     * ConcurrentHashMap is 10x+ faster in high-contention scenarios
     */
    
    
    /*
     * ============================================================================
     * TIP 7: INTERRUPT THREADS GRACEFULLY, DON'T USE STOP()
     * ============================================================================
     * 
     * BAD: Never use Thread.stop() (deprecated)
     * thread.stop();  // WRONG! Can corrupt data
     * 
     * GOOD: Use interrupt flag
     * class InterruptibleTask implements Runnable {
     *     @Override
     *     public void run() {
     *         while (!Thread.currentThread().isInterrupted()) {
     *             doWork();
     *         }
     *     }
     * }
     * 
     * Thread thread = new Thread(new InterruptibleTask());
     * thread.start();
     * // Later...
     * thread.interrupt();  // Gracefully stop thread
     * thread.join();       // Wait for completion
     * 
     * Why Thread.stop() is dangerous:
     * - Leaves object in inconsistent state
     * - Locks held by thread are released abruptly
     * - Can corrupt data structures
     * - No cleanup code runs
     */
    
    
    /*
     * ============================================================================
     * TIP 8: ALWAYS CALL join() OR shutdown() ON THREADS/EXECUTORS
     * ============================================================================
     * 
     * BAD: Forgetting to wait for threads
     * Thread thread = new Thread(new LongTask());
     * thread.start();
     * // Program might exit before thread completes!
     * 
     * GOOD: Always wait for completion
     * Thread thread = new Thread(new LongTask());
     * thread.start();
     * thread.join();  // Wait until thread finishes
     * System.out.println("Thread completed");
     * 
     * BAD: Not shutting down executor
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * executor.execute(task);
     * // Program doesn't exit because executor still running!
     * 
     * GOOD: Shutdown executor
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * executor.execute(task);
     * executor.shutdown();
     * executor.awaitTermination(60, TimeUnit.SECONDS);
     */
    
    
    /*
     * ============================================================================
     * TIP 9: THREAD NAMING FOR DEBUGGING
     * ============================================================================
     * 
     * Named threads are easier to debug
     * 
     * GOOD: Give meaningful names
     * Thread worker = new Thread(new Task());
     * worker.setName("DatabaseWorker-1");
     * worker.start();
     * 
     * In logs:
     * [DatabaseWorker-1] Task started
     * [DatabaseWorker-1] Processing user 123
     * [DatabaseWorker-1] Task completed
     * 
     * ThreadFactory for automatic naming:
     * ThreadFactory factory = new ThreadFactory() {
     *     private int count = 0;
     *     @Override
     *     public Thread newThread(Runnable r) {
     *         Thread t = new Thread(r);
     *         t.setName("Worker-" + (++count));
     *         return t;
     *     }
     * };
     * 
     * ExecutorService executor = Executors.newFixedThreadPool(5, factory);
     */
    
    
    /*
     * ============================================================================
     * TIP 10: WATCH OUT FOR FALSE SHARING (CPU CACHE LINES)
     * ============================================================================
     * 
     * PROBLEM: Multiple threads access variables in same CPU cache line
     * Each core invalidates entire cache line when modifying
     * Result: Extreme contention even with fine-grained locking
     * 
     * Modern CPUs use 64-byte cache lines
     * 
     * BAD: Variables too close (same cache line)
     * class Counter {
     *     long counter1 = 0;      // Cache line 1
     *     long counter2 = 0;      // Same cache line! False sharing!
     * }
     * 
     * Thread 1 modifies counter1, invalidates entire line
     * Thread 2 needs counter2, must reload line from memory
     * Result: Extreme slowdown!
     * 
     * GOOD: Pad variables to separate cache lines (if needed)
     * class Counter {
     *     long counter1 = 0;
     *     long pad1, pad2, pad3, pad4, pad5, pad6, pad7;  // 56 bytes padding
     *     long counter2 = 0;      // Different cache line
     * }
     * 
     * This is an advanced optimization, but important to know
     */
    
    
    /*
     * ============================================================================
     * TIP 11: USE THREAD-LOCAL STORAGE CAREFULLY
     * ============================================================================
     * 
     * ThreadLocal: Each thread gets its own copy of variable
     * 
     * Good Use: Connection pools, user context
     * class Database {
     *     private static ThreadLocal<Connection> connectionHolder = 
     *         ThreadLocal.withInitial(() -> createConnection());
     *     
     *     public static Connection getConnection() {
     *         return connectionHolder.get();
     *     }
     * }
     * 
     * WARNING: Must cleanup in thread pools!
     * try {
     *     // Use thread-local
     * } finally {
     *     threadLocal.remove();  // IMPORTANT! Prevent memory leak
     * }
     * 
     * Memory Leak Risk:
     * - Thread pools reuse threads
     * - ThreadLocal data persists between tasks
     * - Can accumulate memory over time
     */
    
    
    /*
     * ============================================================================
     * TIP 12: PERFORMANCE: synchronized vs ReentrantLock vs Volatile
     * ============================================================================
     * 
     * synchronized method/block:
     * - Simple and easy to understand
     * - Can't check if locked
     * - No timeout support
     * - Performance: Good for low contention, Java 6+ (biased locking)
     * 
     * ReentrantLock:
     * - More flexible (tryLock, timeout)
     * - Requires explicit unlock in finally
     * - Performance: Better for high contention
     * 
     * volatile:
     * - No locking overhead
     * - Only guarantees visibility, not atomicity
     * - Performance: Best for simple flags
     * 
     * AtomicInteger/Long:
     * - Lock-free using CAS (Compare-And-Swap)
     * - Good for simple counters
     * - Performance: Best for high contention counters
     * 
     * CHOOSE BASED ON:
     * - Low contention: synchronized (simpler)
     * - High contention: ReentrantLock or Atomic
     * - Simple flags: volatile
     * - Need timeout: ReentrantLock
     */
    
    
    /*
     * ============================================================================
     * TIP 13: UNDERSTANDING VOLATILE IS NOT ENOUGH FOR COMPOUND OPERATIONS
     * ============================================================================
     * 
     * BAD: volatile + compound operation (NOT atomic)
     * class Counter {
     *     private volatile int count = 0;
     *     
     *     public void increment() {
     *         count++;  // NOT atomic! Three operations: read, add, write
     *     }
     * }
     * 
     * Between read and write, another thread might modify count
     * Multiple threads can increment without some increments being counted
     * 
     * GOOD: Use AtomicInteger
     * class Counter {
     *     private AtomicInteger count = new AtomicInteger(0);
     *     
     *     public void increment() {
     *         count.incrementAndGet();  // Atomic operation
     *     }
     * }
     * 
     * OR use synchronized
     * class Counter {
     *     private volatile int count = 0;
     *     
     *     public synchronized void increment() {
     *         count++;
     *     }
     * }
     * 
     * RULE: volatile ensures visibility, not atomicity
     * For compound operations, use AtomicX or synchronized
     */
    
    
    /*
     * ============================================================================
     * TIP 14: TESTING MULTITHREADED CODE
     * ============================================================================
     * 
     * Multithreaded bugs are hard to reproduce!
     * 
     * TECHNIQUES:
     * 1. Stress Testing: Run thousands of iterations
     *    for (int i = 0; i < 10000; i++) {
     *        runConcurrentTest();
     *    }
     * 
     * 2. Use CountdownLatch to synchronize test start
     *    CountdownLatch latch = new CountdownLatch(threadCount);
     *    // All threads start roughly at same time
     * 
     * 3. Add small delays to increase timing variation
     *    Thread.sleep(random.nextInt(10));
     * 
     * 4. Use ThreadPoolExecutor with many threads
     *    newFixedThreadPool(100);  // More threads = more contention
     * 
     * 5. Run on multiple CPU architectures
     *    Bugs might appear on 1-core but not 4-core
     * 
     * 6. Use tools like ThreadSanitizer, FindBugs for static analysis
     */
    
    
    /*
     * ============================================================================
     * TIP 15: COMMON MULTITHREADING PATTERNS
     * ============================================================================
     * 
     * PATTERN 1: Producer-Consumer
     * Use: BlockingQueue (handles wait/notify for you)
     * 
     * PATTERN 2: Worker Pool
     * Use: ExecutorService with fixed thread pool
     * 
     * PATTERN 3: Barrier Synchronization
     * Use: CyclicBarrier or CountDownLatch
     * 
     * PATTERN 4: Resource Pool (limited connections)
     * Use: Semaphore
     * 
     * PATTERN 5: Read-Heavy Cache
     * Use: ReadWriteLock or CopyOnWriteArrayList
     * 
     * PATTERN 6: Reactive/Event-Driven
     * Use: Thread pool + callback handlers
     * 
     * PATTERN 7: Pipeline
     * Use: Multiple ExecutorServices chained together
     * 
     * PATTERN 8: Background Task
     * Use: ScheduledExecutorService.scheduleAtFixedRate()
     */
    
    
    /*
     * ============================================================================
     * SUMMARY: BEST PRACTICES CHECKLIST
     * ============================================================================
     * 
     * ✓ Prefer immutable objects
     * ✓ Use thread pools instead of creating threads
     * ✓ Minimize critical sections
     * ✓ Acquire locks in consistent order (avoid deadlock)
     * ✓ Understand happens-before relationships
     * ✓ Use concurrent collections (not synchronized wrappers)
     * ✓ Use interrupt for graceful shutdown
     * ✓ Always join/shutdown and wait for completion
     * ✓ Name threads for debugging
     * ✓ Watch out for false sharing (advanced)
     * ✓ Clean up ThreadLocal (memory leak risk)
     * ✓ Choose right synchronization mechanism
     * ✓ volatile only for visibility, not atomicity
     * ✓ Stress test multithreaded code
     * ✓ Use established patterns
     */
    
    
    public static void main(String[] args) {
        System.out.println("This file contains tips and tricks for multithreading.");
        System.out.println("Read through the comments to learn best practices and advanced techniques.");
        System.out.println();
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("1. Synchronization is necessary but expensive");
        System.out.println("2. Minimize lock contention and critical sections");
        System.out.println("3. Use thread pools for scalability");
        System.out.println("4. Prevent deadlocks through consistent lock ordering");
        System.out.println("5. Understand memory visibility guarantees");
        System.out.println("6. Use concurrent collections for better performance");
        System.out.println("7. Test multithreaded code thoroughly");
    }
}

