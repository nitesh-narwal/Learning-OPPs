package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.alarm;

import java.util.concurrent.*;
import java.util.*;

public class semaphore {

    /**
     * ================================================================================
     * SECTION 1: WHAT IS A SEMAPHORE? - BEGINNER LEVEL
     * ================================================================================
     * 
     * DEFINITION: A Semaphore is a counter-based synchronization primitive that controls
     * access to a shared resource by multiple threads.
     * 
     * REAL-WORLD ANALOGY: Think of a parking lot with 5 spaces:
     * - When a car arrives, it checks: Are there empty spaces? (permit > 0)
     * - If YES: Car parks (acquire permit), counter decreases to 4
     * - If NO: Car waits in queue until someone leaves
     * - When car leaves, counter increases, waiting cars can park
     * 
     * HOW IT WORKS:
     * - Semaphore(5) = Can allow up to 5 threads to access resource simultaneously
     * - acquire() = Thread tries to get a permit (decrements counter)
     * - release() = Thread gives back permit (increments counter)
     * - If permits = 0, acquire() BLOCKS thread until permit available
     * 
     * KEY DIFFERENCE from other synchronization:
     * - synchronized block = Only 1 thread (binary lock, like locked door)
     * - Semaphore = N threads allowed (like parking with multiple spaces)
     * - ReentrantLock = 1 thread with reentrance capability
     * 
     * TYPES OF SEMAPHORES:
     * 1. Counting Semaphore: Can have any non-negative count (0, 1, 2, 3, ...)
     *    Example: Semaphore(5) allows 5 threads to access resource
     * 
     * 2. Binary Semaphore: Only 0 or 1 (similar to lock, but NOT reentrant)
     *    Example: Semaphore(1) is like a binary lock (but don't use for mutual exclusion!)
     *    WHY NOT USE FOR MUTUAL EXCLUSION? Because it's not reentrant!
     */

    // ================================================================================
    // SECTION 2: KEY BENEFITS OF SEMAPHORE - WHY USE IT?
    // ================================================================================
    
    /**
     * BENEFIT 1: RESOURCE POOLING
     * - Control how many threads access resource simultaneously
     * - Perfect for limiting connections to database (max 10 simultaneous)
     * - Perfect for thread pools (only N worker threads process tasks)
     * Example: Database pool with max 5 connections
     *   new Semaphore(5) means: Max 5 threads query database simultaneously
     * 
     * BENEFIT 2: PREVENTING RESOURCE EXHAUSTION
     * - Without semaphore: 1000 requests = 1000 threads trying to access resource
     * - With Semaphore(100): Only 100 at a time, others wait in queue (orderly!)
     * - Prevents OutOfMemoryError from thread explosion
     * 
     * BENEFIT 3: FAIRNESS
     * - Can create fair semaphore: new Semaphore(N, true)
     * - Ensures threads acquire permits in FIFO order (first come, first served)
     * - Prevents starvation (some threads never getting a permit)
     * 
     * BENEFIT 4: CONTROLLING CONCURRENCY RATE
     * - Example: Rate limiting API calls (only 100 requests per second)
     * - Initialize: new Semaphore(100) and release 100 permits every second
     * 
     * BENEFIT 5: SIMPLICITY
     * - Simpler than hand-coding synchronized blocks
     * - Code intent is clear: "Allow max 5 threads"
     * - Easier to change limit (just change constructor parameter)
     * 
     * BENEFIT 6: WORKING WITH MULTIPLE RESOURCES
     * - Can use multiple semaphores for different resources
     * - Thread might acquire from Semaphore1 (DB) + Semaphore2 (Cache)
     * - Each independently controlled
     */

    // ================================================================================
    // SECTION 3: KEY DRAWBACKS - WHEN SEMAPHORE HURTS
    // ================================================================================
    
    /**
     * DRAWBACK 1: NO REENTRANCY
     * - If thread calls acquire() twice without release():
     *   acquire() #1 = OK, semaphore = 4
     *   acquire() #2 = BLOCKS FOREVER! Thread waiting for itself!
     * - This is DEADLOCK with yourself!
     * - Solution: Use methods instead (call acquire() only once at top)
     * 
     * DRAWBACK 2: NOT FOR MUTUAL EXCLUSION
     * - Example of BAD usage:
     *   Semaphore(1) s = new Semaphore(1);
     *   Thread1: s.acquire(); x++; s.release();
     *   Thread2: s.acquire(); x++; s.release();
     * - Problem: Thread1 acquire() -> Thread2 release() (wrong thread released!)
     * - Solution: Use ReentrantLock or synchronized for mutual exclusion
     * 
     * DRAWBACK 3: THREAD STARVATION (if not fair)
     * - Problem: Semaphore(5) but some threads never get permit
     * - Why? Aggressive threads keep grabbing all 5 permits
     * - Solution: Use fair mode -> new Semaphore(5, true)
     * - Cost: Fair mode is slightly slower
     * 
     * DRAWBACK 4: NO WAY TO CHECK CURRENT PERMITS
     * - You CANNOT do: "If semaphore has permits, continue"
     * - Why? Race condition! Between check + acquire, permits changed!
     * - There is availablePermits() but shouldn't rely on it
     * 
     * DRAWBACK 5: COMPLEXITY OF EXCEPTION HANDLING
     * - If acquire() succeeds but exception occurs, MUST release in finally!
     * - Example:
     *   sem.acquire();
     *   try {
     *       // work
     *   } finally {
     *       sem.release(); // MUST do this!
     *   }
     * - Forgetting this = permit lost forever = slow starvation
     * 
     * DRAWBACK 6: PERFORMANCE OVERHEAD
     * - More overhead than simple synchronized block
     * - Each acquire/release involves counter manipulation
     * - Should only use when really need the functionality
     */

    // ================================================================================
    // SECTION 4: MULTITHREADING ASPECTS - HOW SEMAPHORE HELPS THREADS
    // ================================================================================
    
    /**
     * MULTITHREADING BENEFIT 1: CONTROLLED PARALLELISM
     * - Without semaphore: System vs Application control
     *   Each request creates thread (OS decides parallelism, chaos!)
     * - With semaphore: Only N threads work concurrently
     *   Application decides exact parallelism level (controlled!)
     * 
     * MULTITHREADING BENEFIT 2: PREVENTING THREAD EXPLOSION
     * - 10,000 requests without semaphore:
     *   10,000 threads created → memory = 10GB (1MB per thread)
     *   Context switching hell, CPU thrashing
     * - 10,000 requests with Semaphore(100):
     *   Only 100 threads ever created, others queue up
     *   Memory = 100MB, stable CPU usage
     * - MASSIVE EFFICIENCY GAIN!
     * 
     * MULTITHREADING BENEFIT 3: FAIRNESS TO THREADS
     * - Fair Semaphore = Threads acquire permits in order (no starvation)
     * - Thread1 asks at 0.1s, Thread2 asks at 0.2s → Thread1 goes first
     * - Without fairness: Thread2 might be aggressive and always win
     * 
     * MULTITHREADING BENEFIT 4: WORKING WITH THREAD POOLS
     * - ThreadPool size = 50 threads
     * - Want only 5 threads accessing shared database?
     * - Use Semaphore(5) inside thread pool tasks!
     * 
     * MULTITHREADING BENEFIT 5: PREVENTING DOGPILING
     * - Dogpile: 1000 threads all waiting for 1 resource, all rush when available
     * - Semaphore naturally prevents this (only N threads rush at once)
     * 
     * MULTITHREADING BENEFIT 6: COORDINATION BETWEEN THREADS
     * - Can use semaphore for sequencing tasks
     * - Thread1 must complete, then Thread2 runs (Semaphore(0) pattern)
     */

    // ================================================================================
    // SECTION 5: BASIC IMPLEMENTATION - LEARN BY EXAMPLE
    // ================================================================================
    
    /**
     * SIMPLEST EXAMPLE: Database Connection Pool
     * 
     * Scenario: Web server gets 1000 requests, but database can handle max 10 connections
     * 
     * WITHOUT Semaphore:
     *   for (int i = 0; i < 1000; i++) {
     *       new Thread(() -> {
     *           // 1000 threads ALL try to connect to database simultaneously!
     *           // Database crashes! Can't handle 1000 connections!
     *       }).start();
     *   }
     * 
     * WITH Semaphore(10):
     *   Semaphore sem = new Semaphore(10); // Max 10 concurrent connections
     *   for (int i = 0; i < 1000; i++) {
     *       new Thread(() -> {
     *           sem.acquire();    // Wait until connection slot available
     *           try {
     *               // Only 10 threads here at a time (others waiting)
     *               database.query();
     *           } finally {
     *               sem.release(); // Free the slot for next thread
     *           }
     *       }).start();
     *   }
     * 
     * RESULT: Orderly, safe, no crashes!
     */

    // ================================================================================
    // SECTION 6: DETAILED SEMAPHORE MECHANICS
    // ================================================================================
    
    /**
     * HOW SEMAPHORE COUNTER WORKS (INTERNAL STATE):
     * 
     * Initial: Semaphore(3) → internal counter = 3
     * 
     * Timeline of events:
     * T1: Thread_A.acquire() → counter = 2 (A enters)
     * T2: Thread_B.acquire() → counter = 1 (B enters)
     * T3: Thread_C.acquire() → counter = 0 (C enters)
     * T4: Thread_D.acquire() → BLOCKED! counter = 0 (D waits in queue)
     * T5: Thread_E.acquire() → BLOCKED! counter = 0 (E waits in queue)
     * T6: Thread_A.release() → counter = 1 (A exits, D wakes up!)
     * T7: Thread_D.acquire()  → counter = 0 (D enters, E still waiting)
     * T8: Thread_B.release() → counter = 1 (B exits, E wakes up!)
     * T9: Thread_E.acquire() → counter = 0 (E enters)
     * 
     * KEY INSIGHT: Counter represents available "permits" at that moment!
     * Counter = 0 means: No resources available, threads must wait
     * 
     * CRITICAL: acquire() and release() are ATOMIC
     * - Means: No race condition between counter check and decrement
     * - No thread can sneak in between (guaranteed by Java/OS internals)
     * 
     * FAIRNESS vs UNFAIRNESS:
     * Unfair Semaphore:
     *   Counter = 1, 5 threads waiting in queue
     *   When released: New thread might grab it instead of waiting thread!
     *   Result: Starvation for some threads
     * 
     * Fair Semaphore(N, true):
     *   Counter = 1, 5 threads waiting in queue
     *   When released: First thread in queue gets it (FIFO order)
     *   Result: No starvation, guaranteed fairness
     */

    // ================================================================================
    // SECTION 7: COMMON CONFUSION POINTS - CLARIFIED!
    // ================================================================================
    
    /**
     * CONFUSION 1: "Why not just use synchronized?"
     * synchronized = 1 one thread at a time (binary lock, like bathroom with 1 stall)
     * Semaphore(5) = 5 threads at a time (like bathroom with 5 stalls)
     * 
     * Use synchronized when: Only ONE thread should access resource
     * Use Semaphore(N) when: UP TO N threads should access resource
     * 
     * Example difference:
     * Cache with 1 writer, multiple readers:
     *   - Don't use Semaphore (wrong tool)
     *   - Use ReentrantReadWriteLock (right tool)
     * 
     * Database pool with max 10 connections:
     *   - Can't use synchronized (would allow 1 only)
     *   - MUST use Semaphore(10) (allows 10)
     * 
     * ---
     * 
     * CONFUSION 2: "Does acquire() block or throw exception if no permits?"
     * Answer: BLOCKS (puts thread to sleep, not exception)
     * 
     * Try-catch won't help:
     *   try {
     *       sem.acquire();  // Thread sleeps here, NO exception thrown
     *   } catch (Exception e) { // This won't trigger
     *       e.printStackTrace();
     *   }
     * 
     * Thread will wait forever if no permits available!
     * Unless interrupted:
     *   sem.acquireUninterruptibly(); // Ignores interruption
     *   sem.acquire();                // CAN be interrupted
     *   sem.tryAcquire(1, TimeUnit.SECONDS); // Times out after 1 sec
     * 
     * ---
     * 
     * CONFUSION 3: "What if thread calls acquire() twice without release()?"
     * Answer: DEADLOCK with yourself!
     * 
     * Example:
     *   Semaphore sem = new Semaphore(1);
     *   
     *   void method1() {
     *       sem.acquire();
     *       method2();      // This calls acquire again!
     *       sem.release();
     *   }
     *   
     *   void method2() {
     *       sem.acquire(); // BLOCKS! Thread waiting for itself!
     *       // Never reaches here
     *       sem.release();
     *   }
     * 
     * WHY? Counter = 1
     * method1 calls acquire() → counter = 0
     * method2 calls acquire() → counter = 0, so BLOCK FOREVER!
     * 
     * Solution: Use method extraction (don't nest semaphore calls)
     * 
     * ---
     * 
     * CONFUSION 4: "Why is binary Semaphore(1) NOT a lock?"
     * Answer: No reentrancy + Wrong thread can release!
     * 
     * With Lock:
     *   Thread_A acquires lock → only Thread_A can release it
     * 
     * With Semaphore(1):
     *   Thread_A acquires sem → ANY thread can release it!
     *   Thread_B.release() wakes up Thread_C (wrong!)
     * 
     * Example problem:
     *   Semaphore(1) sem = new Semaphore(1);
     *   shared_var = 0;
     *   
     *   Thread1 {
     *       sem.acquire();
     *       shared_var++;  // Critical section
     *       // ERROR: Interleaved code here
     *   }
     *   
     *   Thread2 {
     *       // ERROR: Can call release() even though never acquired!
     *       sem.release();  // Violates mutual exclusion!
     *   }
     * 
     * Solution: Use ReentrantLock for mutual exclusion (thread-specific)
     * Use Semaphore for resource limiting (no enforcement of thread ownership)
     * 
     * ---
     * 
     * CONFUSION 5: "What is fair vs unfair? Which should I use?"
     * 
     * Unfair (default): new Semaphore(5)
     * - Fast (lower overhead)
     * - Some threads might starve (never get permit)
     * - Acceptable when: Fairness not critical, pure throughput matters
     * 
     * Fair: new Semaphore(5, true)
     * - Slower (higher overhead)
     * - FIFO order: First thread waiting gets permit (no starvation)
     * - Acceptable when: All threads must eventually progress
     * 
     * Example starvation with unfair:
     *   Semaphore(1) unfair = new Semaphore(1);
     *   
     *   Thread1: Always calls acquire/release very fast
     *   Thread2: Sleeps for 100ms between calls
     *   
     *   Result: Thread1 keeps grabbing all the time, Thread2 starves!
     *   (Thread2 might wait 10 seconds even though resource is "available")
     * 
     * ---
     * 
     * CONFUSION 6: "Can I check permits before calling acquire()?"
     * 
     * Code like this is WRONG:
     *   if (sem.availablePermits() > 0) {
     *       sem.acquire();  // Race condition! Between check + acquire,
     *   }                   // another thread might acquire the permit!
     * 
     * Correct approach: Just call acquire() and let it handle logic:
     *   sem.acquire();  // Blocks if needed, atomically safe
     * 
     * If you must avoid blocking:
     *   if (sem.tryAcquire()) {
     *       // Got permit without blocking
     *   } else {
     *       // No permit available, and didn't block
     *   }
     */

    // ================================================================================
    // SECTION 8: WHEN TO USE SEMAPHORE? DECISION TREE
    // ================================================================================
    
    /**
     * DECISION TREE:
     * 
     * Question 1: Multiple threads need to access ONE resource?
     *   ├─ YES
     *   │  └─ Question 2: Can ONLY 1 thread access at a time?
     *   │      ├─ YES → Use synchronized or ReentrantLock (not Semaphore!)
     *   │      │ (Example: Protecting counter variable)
     *   │      └─ NO → Question 3: Up to N threads can access?
     *   │          └─ YES → USE SEMAPHORE! (This is semaphore's job!)
     *   │          (Example: Database pool with max 10 connections)
     *   └─ NO → Question 2: Threads need to coordinate/wait for event?
     *       ├─ YES (Wait for one event) → Use CountDownLatch
     *       ├─ YES (Wait at synchronization point) → Use CyclicBarrier
     *       └─ YES (Limited access to resource pool) → Use Semaphore!
     * 
     * QUICK REFERENCE TABLE:
     * 
     * Need                           | Tool            | Why
     * ================================|=================|==========================
     * Only 1 thread at a time        | synchronized    | Built-in, simple, reentrant
     * Max N threads at a time        | Semaphore(N)    | Perfect for resource pools
     * Multiple readers, 1 writer     | RWLock          | Read-write optimization
     * Wait for N completions         | CountDownLatch  | One-time event waiting
     * Sync point for N threads       | CyclicBarrier   | Reusable synchronization
     * Task coordination              | BlockingQueue   | Thread-safe queuing
     * Thread-safe operations         | AtomicInteger   | Non-blocking alternative
     * 
     * WHEN SEMAPHORE IS PERFECT:
     * ✓ Database connection pool
     * ✓ Thread pool with limited workers
     * ✓ Rate limiting (max requests/sec)
     * ✓ Resource licensing (max N concurrent uses)
     * ✓ Controlling access to buffering system
     * 
     * WHEN SEMAPHORE IS WRONG:
     * ✗ Mutual exclusion (protecting data, single writer)
     * ✗ One-time event (use CountDownLatch instead)
     * ✗ Synchronization barrier (use CyclicBarrier instead)
     * ✗ Producer-consumer (use BlockingQueue instead)
     */

    // ================================================================================
    // SECTION 9: REAL-WORLD EXAMPLES - DATABASE CONNECTION POOL
    // ================================================================================

    // Example 9.1: Simple Database Connection Pool
    static class DatabaseConnectionPool {
        private List<String> availableConnections = new ArrayList<>();
        private Semaphore connectionLimiter;
        
        public DatabaseConnectionPool(int maxConnections) {
            connectionLimiter = new Semaphore(maxConnections);
            for (int i = 0; i < maxConnections; i++) {
                availableConnections.add("Connection-" + i);
            }
        }
        
        /**
         * Get a database connection
         * - If connections available: Return immediately
         * - If all busy: WAIT (block thread) until one becomes available
         * 
         * This prevents overwhelming the database with 1000 connection attempts
         * when it can only handle 10!
         */
        public String getConnection() throws InterruptedException {
            connectionLimiter.acquire(); // Wait if needed
            synchronized (availableConnections) {
                return availableConnections.remove(0);
            }
        }
        
        /**
         * Return a connection back to pool
         * - Wakes up 1 waiting thread to use this connection
         */
        public void releaseConnection(String connection) {
            synchronized (availableConnections) {
                availableConnections.add(connection);
            }
            connectionLimiter.release(); // Wake up 1 waiting thread
        }
    }

    // Example 9.2: Real Usage
    static void demonstrateConnectionPool() throws InterruptedException {
        DatabaseConnectionPool pool = new DatabaseConnectionPool(3);
        
        System.out.println("\n=== CONNECTION POOL EXAMPLE ===");
        System.out.println("Max connections: 3");
        System.out.println("Spawning 10 threads needing connections...\n");
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        for (int i = 1; i <= 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    String conn = pool.getConnection();
                    System.out.println("Thread-" + threadId + " GOT connection: " + conn);
                    Thread.sleep(1000); // Use connection
                    pool.releaseConnection(conn);
                    System.out.println("Thread-" + threadId + " RELEASED connection: " + conn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("All threads completed!\n");
    }

    // ================================================================================
    // SECTION 10: REAL-WORLD EXAMPLES - RATE LIMITING
    // ================================================================================

    /**
     * RATE LIMITING: Allow max 100 API calls per second
     * 
     * Without Semaphore: All 1000 requests hit API simultaneously
     * With Semaphore: Only 100 requests processed per second
     */
    static class APIRateLimiter {
        private Semaphore rateLimiter;
        
        public APIRateLimiter(int maxRequestsPerSecond) {
            rateLimiter = new Semaphore(maxRequestsPerSecond);
            
            // Refill permits every second
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        // Release all outstanding permits (allows up to maxRequestsPerSecond)
                        rateLimiter.drainPermits(); // Remove old permits
                        rateLimiter.release(maxRequestsPerSecond); // Add new batch
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).setDaemon(true);
        }
        
        public boolean allowRequest() throws InterruptedException {
            return rateLimiter.tryAcquire(1, TimeUnit.MILLISECONDS);
        }
    }

    static void demonstrateRateLimiting() throws InterruptedException {
        System.out.println("\n=== RATE LIMITING EXAMPLE ===");
        System.out.println("Max 5 requests per second");
        System.out.println("Spawning 20 threads with requests...\n");
        
        APIRateLimiter limiter = new APIRateLimiter(5);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 1; i <= 20; i++) {
            final int requestId = i;
            executor.submit(() -> {
                try {
                    if (limiter.allowRequest()) {
                        long elapsed = System.currentTimeMillis() - startTime;
                        System.out.println("[" + elapsed + "ms] Request-" + requestId + " ALLOWED");
                    } else {
                        System.out.println("Request-" + requestId + " RATE LIMITED");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    // ================================================================================
    // SECTION 11: REAL-WORLD EXAMPLES - THREAD POOL TASK LIMITER
    // ================================================================================

    /**
     * Use case: Have 100 worker threads but want to limit concurrent access to shared resource
     * Example: 100 web workers but only 10 can access database simultaneously
     */
    static class WorkerTask implements Runnable {
        private static Semaphore databaseAccess = new Semaphore(3); // Max 3 concurrent DB accesses
        private int taskId;
        
        public WorkerTask(int taskId) {
            this.taskId = taskId;
        }
        
        @Override
        public void run() {
            System.out.println("Task-" + taskId + " started (waiting for DB access)");
            
            try {
                databaseAccess.acquire(); // Wait for available slot
                System.out.println("Task-" + taskId + " GOT database access!");
                Thread.sleep(500); // Simulate DB query
                System.out.println("Task-" + taskId + " completed database query");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                databaseAccess.release(); // Release for next task
            }
        }
    }

    static void demonstrateWorkerPool() throws InterruptedException {
        System.out.println("\n=== WORKER POOL WITH SEMAPHORE ===");
        System.out.println("10 worker threads, max 3 concurrent DB accesses\n");
        
        ExecutorService workerPool = Executors.newFixedThreadPool(10);
        
        for (int i = 1; i <= 15; i++) {
            workerPool.submit(new WorkerTask(i));
        }
        
        workerPool.shutdown();
        workerPool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("All tasks completed!\n");
    }

    // ================================================================================
    // SECTION 12: ADVANCED PATTERN - BINARY SEMAPHORE FOR SIGNALING
    // ================================================================================

    /**
     * PATTERN: Use Semaphore(0) to signal "event completed"
     * 
     * Thread1 waits: sem.acquire()      // Blocks (counter = 0)
     * Thread2 signals: sem.release()    // Wakes up Thread1 (counter = 1)
     * 
     * This is NOT mutual exclusion (wrong tool for that)
     * This IS event signaling (correct tool)
     * 
     * Better approach: Use CountDownLatch (more semantic)
     * But Semaphore can work for simple one-time events
     */
    static void demonstrateSignaling() throws InterruptedException {
        System.out.println("\n=== EVENT SIGNALING WITH SEMAPHORE ===\n");
        
        Semaphore eventSignal = new Semaphore(0); // Start with 0 (no permits = blocked)
        
        // Thread 1: Waits for signal from Thread2
        Thread waiter = new Thread(() -> {
            try {
                System.out.println("[Waiter] Waiting for signal...");
                eventSignal.acquire();
                System.out.println("[Waiter] Got signal! Continuing...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // Thread 2: Signals after delay
        Thread signaler = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("[Signaler] Sending signal!");
                eventSignal.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        waiter.start();
        signaler.start();
        waiter.join();
        signaler.join();
    }

    // ================================================================================
    // SECTION 13: TRICKS AND TIPS
    // ================================================================================

    /**
     * TRICK 1: FAIR SEMAPHORE for preventing starvation
     * new Semaphore(5, true) vs new Semaphore(5)
     * 
     * When threads are aggressive (keep calling acquire immediately after release),
     * unfair semaphore might always give to same thread
     * 
     * Fair semaphore ensures all threads eventually get turn
     * Trade-off: ~5-10% performance overhead, but fairness guaranteed
     * 
     * Use fair when:
     *   - All threads are equal priority (fairness important)
     *   - Concerned about starvation
     *   - Long-running operations (performance overhead < 1%)
     * 
     * Use unfair when:
     *   - Throughput is critical (every % of performance matters)
     *   - Fairness not required
     *   - Quick operations (overhead matters more)
     * 
     * ---
     * 
     * TRICK 2: TRY-ACQUIRE for non-blocking check
     * 
     * Normal acquire():
     *   sem.acquire(); // BLOCKS if no permits
     * 
     * Try-acquire (don't block):
     *   if (sem.tryAcquire()) {
     *       // Got permit, do work
     *       sem.release();
     *   } else {
     *       // No permit available immediately
     *       // Don't block, do alternative work
     *   }
     * 
     * With timeout:
     *   if (sem.tryAcquire(1, TimeUnit.SECONDS)) {
     *       // Got permit within 1 second
     *   } else {
     *       // Timeout after 1 second
     *       // Give up or handle error
     *   }
     * 
     * Use tryAcquire when:
     *   - You have fallback plan if no permits
     *   - Don't want to block indefinitely
     *   - Want timeout behavior
     * 
     * ---
     * 
     * TRICK 3: RELEASING MULTIPLE PERMITS AT ONCE
     * 
     * Instead of:
     *   for (int i = 0; i < 5; i++) {
     *       sem.release();
     *   }
     * 
     * Do:
     *   sem.release(5); // More efficient, atomic!
     * 
     * Use when refilling permits in batch
     * 
     * ---
     * 
     * TRICK 4: DRAINING PERMITS (remove all)
     * 
     * drainPermits() returns count and sets counter to 0
     * Useful for rate limiter pattern:
     *   int oldPermits = sem.drainPermits();
     *   sem.release(100); // Refill with new batch
     * 
     * ---
     * 
     * TRICK 5: ALWAYS USE TRY-FINALLY
     * 
     * sem.acquire();
     * try {
     *     // Do work
     * } finally {
     *     sem.release(); // MUST release even if exception!
     * }
     * 
     * If you forget: Permit lost forever, thread count decreases
     * Eventually: No threads can acquire (slow starvation)
     * 
     * ---
     * 
     * TRICK 6: MULTIPLE SEMAPHORES FOR MULTIPLE RESOURCES
     * 
     * Example: Thread needs both database AND cache access
     * 
     *   Semaphore dbSem = new Semaphore(10);   // Max 10 DB connections
     *   Semaphore cacheSem = new Semaphore(5); // Max 5 cache accesses
     *   
     *   try {
     *       dbSem.acquire();
     *       cacheSem.acquire();
     *       // Access both resources
     *   } finally {
     *       cacheSem.release();
     *       dbSem.release();
     *   }
     * 
     * Order matters! Always acquire/release in same order to prevent deadlock
     * 
     * ---
     * 
     * TRICK 7: AVAILABLE PERMITS FOR MONITORING
     * 
     * int available = sem.availablePermits();
     * 
     * Don't use to decide whether to acquire (race condition!)
     * DO use to monitor/log for diagnostics:
     *   if (sem.availablePermits() < 2) {
     *       logger.warn("Semaphore running low: " + sem.availablePermits());
     *   }
     */

    // ================================================================================
    // SECTION 14: COMMON MISTAKES - AVOID THESE!
    // ================================================================================

    /**
     * MISTAKE 1: USING SEMAPHORE(1) FOR MUTUAL EXCLUSION
     * 
     * WRONG:
     *   static Semaphore sem = new Semaphore(1);
     *   static int counter = 0;
     *   
     *   void increment() {
     *       sem.acquire();
     *       counter++;  // PROBLEM: Wrong thread can call release!
     *       sem.release();
     *   }
     * 
     * ISSUE: Thread_B can call release() without acquiring!
     * RESULT: Mutual exclusion BROKEN!
     * 
     * CORRECT:
     *   static ReentrantLock lock = new ReentrantLock();
     *   
     *   void increment() {
     *       lock.lock();
     *       try {
     *           counter++;  // Guaranteed locked by this thread!
     *       } finally {
     *           lock.unlock();
     *       }
     *   }
     * 
     * ---
     * 
     * MISTAKE 2: NESTED ACQUIRE CALLS (SELF-DEADLOCK)
     * 
     * WRONG:
     *   static Semaphore sem = new Semaphore(1);
     *   
     *   void method1() throws InterruptedException {
     *       sem.acquire();
     *       try {
     *           method2();  // Calls acquire again!
     *       } finally {
     *           sem.release();
     *       }
     *   }
     *   
     *   void method2() throws InterruptedException {
     *       sem.acquire(); // DEADLOCK! Waiting for itself!
     *       try {
     *           // Never reaches here
     *       } finally {
     *           sem.release();
     *       }
     *   }
     * 
     * RESULT: Thread A acquires (counter=0), then tries to acquire again (BLOCKS)
     * SOLUTION: Extract method2, call it WITHOUT acquiring separately:
     *   void method1() throws InterruptedException {
     *       sem.acquire();
     *       try {
     *           method2_internals();  // Don't call method2 directly
     *       } finally {
     *           sem.release();
     *       }
     *   }
     * 
     * ---
     * 
     * MISTAKE 3: FORGETTING RELEASE IN EXCEPTION
     * 
     * WRONG:
     *   sem.acquire();
     *   int result = riskyOperation(); // Might throw exception!
     *   sem.release();                 // If exception, never reached!
     * 
     * CORRECT:
     *   sem.acquire();
     *   try {
     *       int result = riskyOperation();
     *   } finally {
     *       sem.release(); // ALWAYS called
     *   }
     * 
     * IMPACT: Each missed release = 1 permit lost = eventual starvation!
     * 
     * ---
     * 
     * MISTAKE 4: IGNORING FAIRNESS WHEN NEEDED
     * 
     * WRONG (if fairness needed):
     *   Semaphore sem = new Semaphore(5); // Unfair!
     * 
     * If test with many threads, some might starve forever
     * 
     * CORRECT:
     *   Semaphore sem = new Semaphore(5, true); // Fair!
     * 
     * Trade-off: Slightly slower but guaranteed fairness
     * 
     * ---
     * 
     * MISTAKE 5: USING AVAILABLE PERMITS FOR DECISION MAKING
     * 
     * WRONG:
     *   if (sem.availablePermits() > 0) {
     *       sem.acquire(); // Race condition! Another thread might grab it!
     *   }
     * 
     * CORRECT:
     *   if (sem.tryAcquire()) {
     *       // Atomically checks + acquires!
     *   }
     * 
     * Or just:
     *   sem.acquire(); // Handles blocking atomically
     */

    // ================================================================================
    // SECTION 15: BEST PRACTICES CHECKLIST
    // ================================================================================

    /**
     * CHECKLIST: Before using Semaphore, verify:
     * 
     * [ ] I need to LIMIT access to resource (not protect it from modification)
     * [ ] Multiple threads (up to N) can access simultaneously
     * [ ] I'm using try-finally for release
     * [ ] I understand fair vs unfair trade-offs
     * [ ] I won't call acquire() multiple times without release()
     * [ ] Database/connection pooling is main use case
     * [ ] I'm not using it for mutual exclusion
     * [ ] I've considered CountDownLatch/CyclicBarrier alternatives
     * [ ] Performance impact acceptable (slight overhead)
     * [ ] I have fallback for unavailable permits (timeout/retry)
     * 
     * IF ANY IS FALSE: Reconsider if Semaphore is right tool!
     */

    // ================================================================================
    // SECTION 16: COMPARISON WITH OTHER TOOLS
    // ================================================================================

    /**
     * Semaphore vs synchronized:
     *   synchronized = Only 1 thread (harsh)
     *   Semaphore(N) = Up to N threads (flexible)
     *   USE: Semaphore when N > 1 needed
     * 
     * Semaphore vs ReentrantLock:
     *   ReentrantLock = 1 thread with reentrancy (mutual exclusion)
     *   Semaphore = N threads for resource pooling
     *   USE: Semaphore for pools, ReentrantLock for mutual exclusion
     * 
     * Semaphore vs Thread Pool:
     *   Thread pool = Manages threads
     *   Semaphore = Limits resource access
     *   USE TOGETHER: Pool processes tasks, semaphore limits resource access
     * 
     * Semaphore vs CountDownLatch:
     *   CountDownLatch = Wait N completions (one-time)
     *   Semaphore = Limit resource access (repeated)
     *   USE: CountDownLatch for event signaling, Semaphore for pooling
     * 
     * Semaphore vs CyclicBarrier:
     *   CyclicBarrier = Sync point for N threads (reusable)
     *   Semaphore = Limit concurrent threads (no sync point)
     *   USE: Barrier for coordination, Semaphore for throttling
     */

    // ================================================================================
    // SECTION 17: COMPLETE WORKING EXAMPLE
    // ================================================================================

    public static void main(String[] args) throws InterruptedException {
        System.out.println("================================================================================");
        System.out.println("SEMAPHORE: COMPREHENSIVE GUIDE");
        System.out.println("================================================================================");
        
        // Run all examples
        demonstrateConnectionPool();
        demonstrateRateLimiting();
        demonstrateWorkerPool();
        demonstrateSignaling();
        
        System.out.println("\n================================================================================");
        System.out.println("CONCLUSION:");
        System.out.println("================================================================================");
        System.out.println("Semaphore is powerful for limiting resource access!");
        System.out.println("Key points:");
        System.out.println("1. Use for resource pooling (database, connections, threads)");
        System.out.println("2. ALWAYS use try-finally for release");
        System.out.println("3. Consider fair mode if starvation is concern");
        System.out.println("4. Don't use for mutual exclusion (wrong tool)");
        System.out.println("5. Perfect for rate limiting, connection pooling, worker limits");
        System.out.println("================================================================================\n");
    }
}
