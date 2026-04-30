package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.DeadLocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadLocking {
    
    /*
     * ============================================================================
     * DEADLOCK - COMPREHENSIVE GUIDE
     * ============================================================================
     * 
     * WHAT IS DEADLOCK?
     * =================
     * DeadLock is a situation in multithreading where 2 or more threads are
     * blocked forever, waiting for each other to release a resource.
     * It typically occurs when 2 or more threads have circular dependencies
     * on a set of locks.
     *
     * Real-world analogy:
     * - Two cars face each other on a narrow road
     * - Car A waits for Car B to back up
     * - Car B waits for Car A to back up
     * - Neither can move forward or backward
     * - Both cars are stuck forever!
     * 
     * In multithreading:
     * - Thread A holds Lock1 and waits for Lock2
     * - Thread B holds Lock2 and waits for Lock1
     * - Both threads are stuck forever!
     * 
     * ============================================================================
     * THE 4 NECESAR CONDITIONS FOR DEADLOCK (ALL 4 MUST BE TRUE!)
     * ============================================================================
     * 
     * For deadlock to occur, ALL FOUR of these conditions must be SIMULTANEOUSLY TRUE:
     * 
     * 1. MUTUAL EXCLUSION (M)
     *    - Only ONE thread can hold a lock at a time
     *    - Other threads MUST wait
     *    - It's the nature of locks themselves
     *    - Example: synchronized keyword, ReentrantLock
     * 
     * 2. HOLD AND WAIT (H&W)
     *    - A thread holds at least ONE resource AND
     *    - WHILE holding it, waits for ANOTHER resource
     *    - The thread doesn't release the first resource while waiting
     *    - This is the most dangerous condition!
     * 
     * 3. NO PREEMPTION (NP)
     *    - Resources CANNOT be forcibly taken away
     *    - Only the thread holding it can release it
     *    - The OS won't say "Hey lock, I'm taking you away!"
     *    - This is standard behavior in Java
     * 
     * 4. CIRCULAR WAIT (CW)
     *    - Threads wait in a CIRCULAR chain
     *    - T1 waits for T2, T2 waits for T3, T3 waits for T1
     *    - Must form a complete cycle/loop
     * 
     * KEY INSIGHT:
     * If you eliminate ANY ONE of these 4 conditions, deadlock is IMPOSSIBLE!
     * 
     * ============================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 1: BASIC DEADLOCK EXAMPLE (BEGINNER)
     * ========================================================================
     * 
     * THE CLASSIC DEADLOCK SCENARIO: Two threads, Two locks
     * 
     * Thread A:
     *   1. Acquires Lock1
     *   2. Does some work
     *   3. Tries to acquire Lock2 (BLOCKED! Thread B has it)
     *   4. WAITS forever...
     * 
     * Thread B:
     *   1. Acquires Lock2
     *   2. Does some work
     *   3. Tries to acquire Lock1 (BLOCKED! Thread A has it)
     *   4. WAITS forever...
     * 
     * Result: Both threads are STUCK, waiting for each other!
     * 
     * ========================================================================
     */
    
    // Example 1: Classic Deadlock - DON'T DO THIS!
    public static class DeadlockExample {
        // Two resources (locks)
        private Object lock1 = new Object();
        private Object lock2 = new Object();
        
        // Thread A: Acquire lock1, then try to acquire lock2
        public void methodA() {
            synchronized (lock1) {
                System.out.println("Thread A acquired Lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {} // simulate work
                
                // This will DEADLOCK!
                synchronized (lock2) {
                    System.out.println("Thread A acquired Lock2");
                }
            }
        }
        
        // Thread B: Acquire lock2, then try to acquire lock1
        public void methodB() {
            synchronized (lock2) {
                System.out.println("Thread B acquired Lock2");
                try { Thread.sleep(100); } catch (InterruptedException e) {} // simulate work
                
                // This will DEADLOCK!
                synchronized (lock1) {
                    System.out.println("Thread B acquired Lock1");
                }
            }
        }
        
        // Demonstrating the deadlock
        public static void demonstrateDeadlock() {
            DeadlockExample example = new DeadlockExample();
            
            Thread threadA = new Thread(() -> example.methodA(), "ThreadA");
            Thread threadB = new Thread(() -> example.methodB(), "ThreadB");
            
            threadA.start();
            threadB.start();
            
            // This program will HANG here forever!
            // Because both threads are deadlocked
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 2: WHY IS DEADLOCK A PROBLEM?
     * ========================================================================
     * 
     * 1. APPLICATION HANGS
     *    - No exceptions thrown
     *    - Program appears frozen
     *    - Users don't know what's wrong
     *    - Hard to debug!
     * 
     * 2. RESOURCE WASTE
     *    - Threads are stuck but still consuming memory
     *    - Other threads waiting for these threads waste CPU
     * 
     * 3. UNPREDICTABLE
     *    - May happen only under specific load conditions
     *    - May work fine in testing but fail in production
     *    - Difficult to reproduce
     * 
     * 4. NO AUTOMATIC RECOVERY
     *    - Java runtime doesn't detect or break deadlock automatically
     *    - You must manually kill the process
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 3: DETECTING DEADLOCK
     * ========================================================================
     * 
     * RUNTIME DETECTION:
     * 
     * 1. Check if threads are BLOCKED
     *    Thread.enumerate() - List all threads
     *    Thread.getState() - Check if BLOCKED or WAITING
     * 
     * 2. JVM Tools:
     *    - jstack: Print thread dump
     *    - jconsole: Visual thread monitor
     *    - VisualVM: Advanced monitoring
     * 
     * 3. Watch for patterns:
     *    - All worker threads showing "Object.wait()"
     *    - Multiple threads waiting on the same locks
     *    - Thread dump shows "waiting to lock"
     * 
     * HOW TO USE JSTACK (Command Line):
     *   1. Find Java process PID: jps
     *   2. Dump threads: jstack <PID>
     *   3. Look for "waiting to lock"
     * 
     * ========================================================================
     */
    
    // Example 2: Detecting deadlock with ThreadMXBean
    public static class DeadlockDetector {
        public static void checkForDeadlock() {
            java.lang.management.ThreadMXBean bean =
                java.lang.management.ManagementFactory.getThreadMXBean();
            
            long[] ids = bean.findDeadlockedThreads();
            
            if (ids != null && ids.length > 0) {
                System.out.println("DEADLOCK DETECTED!");
                System.out.println("Deadlocked threads: " + java.util.Arrays.toString(ids));
                
                java.lang.management.ThreadInfo[] infos = bean.getThreadInfo(ids);
                for (java.lang.management.ThreadInfo info : infos) {
                    System.out.println("Thread: " + info.getThreadName());
                    System.out.println("State: " + info.getThreadState());
                }
            } else {
                System.out.println("No deadlock detected");
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 4: HOW TO PREVENT DEADLOCK - STRATEGY 1: LOCK ORDERING
     * ========================================================================
     * 
     * SOLUTION: Acquire locks in a CONSISTENT ORDER always!
     * 
     * PRINCIPLE:
     * If every thread acquires locks in the SAME order, circular wait
     * cannot occur!
     * 
     * Example:
     * Always acquire Lock1 BEFORE Lock2 (never the other way around)
     * 
     * Thread A: Lock1 -> Lock2 ✓ (correct order)
     * Thread B: Lock1 -> Lock2 ✓ (same order)
     * 
     * Result: Lock2 will wait for Lock1, but never the other way!
     * No circular dependency = No deadlock!
     * 
     * ANALOGY:
     * Imagine both cars must turn LEFT first before going RIGHT.
     * If one car goes LEFT-RIGHT and other goes RIGHT-LEFT,
     * they might block each other. But if both follow LEFT-RIGHT,
     * one car goes first (acquiring LEFT), then the other gets a turn.
     * 
     * ========================================================================
     */
    
    // Example 3: Lock Ordering - The RIGHT Way
    public static class LockOrderingPrevention {
        private Object lock1 = new Object();
        private Object lock2 = new Object();
        
        // IMPORTANT: Assume lock1 < lock2 (always acquire in this order)
        
        // RIGHT: Thread A acquires locks in order lock1 -> lock2
        public void methodA() {
            synchronized (lock1) {  // FIRST
                System.out.println("Thread A acquired Lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                
                synchronized (lock2) {  // SECOND (consistent order)
                    System.out.println("Thread A acquired Lock2");
                }
            }
        }
        
        // RIGHT: Thread B also acquires locks in order lock1 -> lock2
        public void methodB() {
            synchronized (lock1) {  // FIRST (same order as methodA!)
                System.out.println("Thread B acquired Lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                
                synchronized (lock2) {  // SECOND (consistent order)
                    System.out.println("Thread B acquired Lock2");
                }
            }
        }
        
        // Now NO DEADLOCK occurs! One thread gets both locks first!
    }
    
    
    /*
     * ========================================================================
     * SECTION 5: HOW TO PREVENT DEADLOCK - STRATEGY 2: TIMEOUTS
     * ========================================================================
     * 
     * SOLUTION: Use tryLock() with timeout instead of blocking forever!
     * 
     * PRINCIPLE:
     * If a thread can't acquire all locks within a timeout,
     * it releases the locks it already has and retries.
     * This breaks the "hold and wait" condition!
     * 
     * ADVANTAGES:
     * - Thread won't wait forever
     * - Can detect and handle timeout
     * - Application remains responsive
     * 
     * DISADVANTAGES:
     * - Thread spins in retry loop (wastes CPU)
     * - Retry logic needed
     * - Livelock possible (all threads keep retrying)
     * 
     * ========================================================================
     */
    
    // Example 4: Timeout Prevention - Lock.tryLock()
    public static class TimeoutPrevention {
        private Lock lock1 = new ReentrantLock();
        private Lock lock2 = new ReentrantLock();
        
        // RIGHT: Try to acquire locks with timeout
        public void methodWithTimeout() {
            boolean lock1Acquired = false;
            boolean lock2Acquired = false;
            
            try {
                // Try to acquire lock1 within 1 second
                lock1Acquired = lock1.tryLock(1, TimeUnit.SECONDS);
                if (!lock1Acquired) {
                    System.out.println("Failed to acquire lock1 within timeout!");
                    return;
                }
                System.out.println("Acquired lock1");
                
                // Try to acquire lock2 within 1 second
                lock2Acquired = lock2.tryLock(1, TimeUnit.SECONDS);
                if (!lock2Acquired) {
                    System.out.println("Failed to acquire lock2 within timeout!");
                    return;  // Release lock1 and try again
                }
                System.out.println("Acquired lock2");
                
                // Do critical work here
                
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
                Thread.currentThread().interrupt();
            } finally {
                if (lock2Acquired) lock2.unlock();
                if (lock1Acquired) lock1.unlock();
            }
        }
        
        // Thread that retries if it fails
        public void methodWithRetry() {
            boolean success = false;
            int retries = 0;
            int maxRetries = 5;
            
            while (!success && retries < maxRetries) {
                try {
                    methodWithTimeout();
                    success = true;
                } catch (Exception e) {
                    retries++;
                    System.out.println("Retry " + retries);
                    try {
                        Thread.sleep(100);  // Back off before retry
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            
            if (!success) {
                System.out.println("Failed after " + maxRetries + " retries!");
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 6: HOW TO PREVENT DEADLOCK - STRATEGY 3: LOCK-FREE DESIGN
     * ========================================================================
     * 
     * SOLUTION: Avoid locks altogether using atomic operations or
     *           concurrent data structures!
     * 
     * PRINCIPLE:
     * No locks = No deadlock possible!
     * 
     * OPTIONS:
     * 1. Atomic variables: AtomicInteger, AtomicBoolean, AtomicReference
     * 2. Concurrent collections: ConcurrentHashMap, CopyOnWriteArrayList
     * 3. Compare-and-swap (CAS): java.util.concurrent.atomic
     * 4. Message passing: Use queues instead of shared state
     * 
     * ADVANTAGES:
     * - No deadlock possible (no mutex locks used)
     * - Better performance in most cases
     * - Simpler to reason about
     * 
     * DISADVANTAGES:
     * - Limited to simple data structures
     * - Can't protect complex operations
     * - May not work for all scenarios
     * 
     * ========================================================================
     */
    
    // Example 5: Lock-Free Design using Atomics
    public static class LockFreeDesign {
        private java.util.concurrent.atomic.AtomicInteger counter =
            new java.util.concurrent.atomic.AtomicInteger(0);
        
        private java.util.concurrent.ConcurrentHashMap<String, String> cache =
            new java.util.concurrent.ConcurrentHashMap<>();
        
        // NO LOCKS! Atomic operation
        public void incrementCounter() {
            counter.incrementAndGet();  // Thread-safe without locks!
        }
        
        public int getCounter() {
            return counter.get();  // No locks needed!
        }
        
        // NO LOCKS! Concurrent collection
        public void putInCache(String key, String value) {
            cache.put(key, value);  // Thread-safe without locks!
        }
        
        public String getFromCache(String key) {
            return cache.get(key);  // No locks needed!
        }
        
        // This design has ZERO deadlock risk!
    }
    
    
    /*
     * ========================================================================
     * SECTION 7: HOW TO PREVENT DEADLOCK - STRATEGY 4: THREAD INTERRUPTION
     * ========================================================================
     * 
     * SOLUTION: Allow threads to be interrupted while waiting for locks!
     * 
     * PRINCIPLE:
     * Instead of blocking forever, a thread can be interrupted and
     * given a chance to release resources and try again.
     * 
     * USE: Lock.lockInterruptibly() instead of lock()
     * 
     * ADVANTAGES:
     * - Thread can be canceled or interrupted
     * - Can break out of deadlock situation
     * - Graceful shutdown possible
     * 
     * DISADVANTAGES:
     * - Need to handle InterruptedException
     * - Complex error handling
     * - Recovery logic needed
     * 
     * ========================================================================
     */
    
    // Example 6: Interruptible Locks
    public static class InterruptibleLockPrevention {
        private Lock lock1 = new ReentrantLock();
        private Lock lock2 = new ReentrantLock();
        
        public void methodWithInterruptible() {
            boolean lock1Acquired = false;
            boolean lock2Acquired = false;
            
            try {
                // Can be interrupted while waiting
                lock1.lockInterruptibly();
                lock1Acquired = true;
                System.out.println("Acquired lock1 (interruptibly)");
                
                // Can be interrupted while waiting
                lock2.lockInterruptibly();
                lock2Acquired = true;
                System.out.println("Acquired lock2 (interruptibly)");
                
                // Critical section
                
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted! Releasing locks...");
                Thread.currentThread().interrupt();  // Restore interrupt status
            } finally {
                if (lock2Acquired) lock2.unlock();
                if (lock1Acquired) lock1.unlock();
            }
        }
        
        // Other thread can interrupt this operation
        public static void demonstrateInterruption() {
            InterruptibleLockPrevention example = new InterruptibleLockPrevention();
            
            Thread worker = new Thread(() -> {
                while (true) {
                    try {
                        example.methodWithInterruptible();
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.out.println("Worker interrupted, exiting gracefully");
                        break;
                    }
                }
            });
            
            worker.start();
            
            try {
                Thread.sleep(500);
                worker.interrupt();  // Interrupt the worker
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 8: HOW TO PREVENT DEADLOCK - STRATEGY 5: SINGLE LOCK
     * ========================================================================
     * 
     * SOLUTION: Instead of protecting multiple resources with multiple locks,
     *           use a SINGLE lock for all resources!
     * 
     * PRINCIPLE:
     * If there's only ONE lock, threads can't have a circular dependency!
     * (No circular wait possible with a single lock)
     * 
     * TRADE-OFF:
     * - Less concurrency (only one thread can execute)
     * - Simple and deadlock-free
     * - Good for protect critical sections that access multiple resources
     * 
     * ========================================================================
     */
    
    // Example 7: Single Lock Strategy
    public static class SingleLockStrategy {
        private Object masterLock = new Object();
        private int balance = 1000;
        private String accountStatus = "ACTIVE";
        
        // GOOD: Single lock protects multiple resources
        public void transfer(int amount) {
            synchronized (masterLock) {  // ONE lock for everything
                // Both balance and accountStatus are protected
                if (accountStatus.equals("ACTIVE") && balance >= amount) {
                    balance -= amount;
                    System.out.println("Transfer successful");
                }
            }
        }
        
        // GOOD: No deadlock possible with single lock
        public void updateStatus(String newStatus) {
            synchronized (masterLock) {  // Same lock
                accountStatus = newStatus;
            }
        }
        
        // GOOD: Both operations use same lock, so no circular dependency
        public void complexOperation() {
            synchronized (masterLock) {
                // Can safely access both balance and status here
                transfer(100);
                updateStatus("UPDATED");
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 9: COMPARING PREVENTION STRATEGIES
     * ========================================================================
     * 
     * Which strategy should you use?
     * 
     * +------------------+----------+----------+----------+----------+
     * | Strategy         | Easy     | Fast     | Flexible | Low Risk |
     * +------------------+----------+----------+----------+----------+
     * | Lock Ordering    | Medium   | Good     | Limited  | High     |
     * | Timeouts         | Hard     | Medium   | Good     | Medium   |
     * | Lock-Free        | Hard     | Excellent| Limited  | High     |
     * | Interruptible    | Hard     | Medium   | Good     | Medium   |
     * | Single Lock      | Easy     | Medium   | Limited  | High     |
     * +------------------+----------+----------+----------+----------+
     * 
     * RECOMMENDATION FOR DIFFERENT SCENARIOS:
     * 
     * 1. SIMPLE CASES (Few locks, clear order)
     *    USE: Lock Ordering
     *    WHY: Simple, effective, no overhead
     * 
     * 2. PERFORMANCE CRITICAL
     *    USE: Lock-Free Design with Atomics
     *    WHY: Best performance, zero deadlock risk
     * 
     * 3. COMPLEX SCENARIOS
     *    USE: Timeouts or Interruptible Locks
     *    WHY: More flexible, can handle edge cases
     * 
     * 4. SIMPLE SYSTEMS
     *    USE: Single Lock
     *    WHY: Dead simple, zero deadlock, okay performance
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 10: REAL-WORLD DEADLOCK EXAMPLES
     * ========================================================================
     */
    
    // Real-world Example 1: Bank Transfer (Classic problem)
    public static class BankTransfer {
        public static class Account {
            private long id;
            private double balance;
            private Lock lock = new ReentrantLock();
            
            public Account(long id, double balance) {
                this.id = id;
                this.balance = balance;
            }
            
            // WRONG: Can cause deadlock
            // If Thread1 transfers A->B while Thread2 transfers B->A
            public void transfer_WRONG(Account target, double amount) {
                this.lock.lock();  // Lock source
                try {
                    target.lock.lock();  // Try to lock destination
                    try {
                        this.balance -= amount;
                        target.balance += amount;
                    } finally {
                        target.lock.unlock();
                    }
                } finally {
                    this.lock.unlock();
                }
            }
            
            // RIGHT: Use lock ordering to prevent deadlock
            // Always acquire lock with SMALLER id first
            public void transfer_RIGHT(Account target, double amount) {
                // Determine order based on account ID
                Account first = this.id < target.id ? this : target;
                Account second = this.id < target.id ? target : this;
                
                first.lock.lock();
                try {
                    second.lock.lock();
                    try {
                        if (this == first) {
                            this.balance -= amount;
                            target.balance += amount;
                        } else {
                            this.balance -= amount;
                            target.balance += amount;
                        }
                    } finally {
                        second.lock.unlock();
                    }
                } finally {
                    first.lock.unlock();
                }
            }
        }
    }
    
    
    // Real-world Example 2: Database Connection Management
    public static class DatabaseConnectionPool {
        private java.util.concurrent.BlockingQueue<Connection> availableConnections;
        private Object connectionLock = new Object();
        
        public static class Connection {
            private String id;
            private boolean inUse;
            
            public Connection(String id) {
                this.id = id;
                this.inUse = false;
            }
        }
        
        // SIMPLE APPROACH: Avoid deadlock by limiting what operations hold locks
        public Connection getConnection(long timeout, TimeUnit unit)
            throws InterruptedException {
            // Don't hold lock while doing I/O or other operations
            return availableConnections.poll(timeout, unit);
        }
        
        public void returnConnection(Connection conn) {
            try {
                availableConnections.put(conn);  // Simple non-blocking operation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    
    // Real-world Example 3: Servlet Handling Requests
    public static class ServletDeadlockExample {
        private java.util.concurrent.ConcurrentHashMap<String, String> userCache = 
            new java.util.concurrent.ConcurrentHashMap<>();
        private java.util.concurrent.ConcurrentHashMap<String, String> sessionCache =
            new java.util.concurrent.ConcurrentHashMap<>();
        
        // GOOD: Using concurrent collections prevents deadlock
        public void handleRequest(String userId, String requestData) {
            // No locks needed! Concurrent collections handle threading
            String userData = userCache.get(userId);
            String sessionData = sessionCache.get(userId);
            
            // Update in any order, no deadlock possible
            userCache.put(userId, requestData);
            sessionCache.put(userId, requestData);
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 11: COMMON DEADLOCK MISTAKES
     * ========================================================================
     * 
     * MISTAKE 1: Nested synchronization without lock ordering
     * =========================================================
     * synchronized (lock1) {
     *     synchronized (lock2) {  // If other thread does lock2->lock1, DEADLOCK!
     *     }
     * }
     * 
     * FIX: Always acquire in same order
     * 
     * 
     * MISTAKE 2: Holding lock while doing I/O or network operations
     * ================================================================
     * synchronized (lock) {
     *     Thread.sleep(5000);  // BAD! Lock held for 5 seconds!
     *     networkCall();       // BAD! Lock held while waiting for network!
     * }
     * 
     * FIX: Do I/O operations OUTSIDE the lock
     * 
     * 
     * MISTAKE 3: Calling other synchronized methods while holding lock
     * ==================================================================
     * public synchronized void methodA() {
     *     methodB();  // If methodB waits for a lock A holds, DEADLOCK!
     * }
     * 
     * public synchronized void methodB() {
     *     // ...
     * }
     * 
     * FIX: Use explicit locks with clear lock ordering
     * 
     * 
     * MISTAKE 4: Multiple threads acquiring locks in different orders
     * ===================================================================
     * Thread A: lock1.lock(); then lock2.lock();
     * Thread B: lock2.lock(); then lock1.lock();  // WRONG ORDER!
     * 
     * FIX: Document and enforce lock ordering across entire codebase
     * 
     * 
     * MISTAKE 5: Assuming nested locks won't happen
     * ===============================================
     * Just because your code looks simple doesn't mean locks won't nest.
     * If you call other methods, check if they acquire locks!
     * 
     * FIX: Trace all lock acquisitions in call stack
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 12: CONFUSING CONCEPTS CLARIFIED
     * ========================================================================
     * 
     * CONFUSION 1: "Is synchronized the same as locks?"
     * ==================================================
     * Similar but NOT the same:
     * - synchronized: Java keyword, implicit lock, reentrant
     * - Lock: java.util.concurrent interface, explicit, more flexible
     * - Both can cause deadlock the same way
     * 
     * 
     * CONFUSION 2: "Can't Java prevent deadlock automatically?"
     * ============================================================
     * NO! Java runtime does NOT detect or prevent deadlock.
     * - JVM only runs code you write
     * - It doesn't know which order you "meant" to acquire locks
     * - It's YOUR responsibility to prevent deadlock!
     * 
     * 
     * CONFUSION 3: "If threads are deadlocked, will exceptions be thrown?"
     * =====================================================================
     * NO! No exception at all. Program just hangs silently.
     * - Thread state becomes "BLOCKED" or "WAITING"
     * - CPU usage might go to zero (blocked, not spinning)
     * - Much harder to debug than exceptions
     * 
     * 
     * CONFUSION 4: "Doesn't lock timeout always solve deadlock?"
     * ============================================================
     * Not completely:
     * - Timeout can DETECT potential deadlock
     * - But doesn't PREVENT it
     * - When timeout happens, threads still need recovery logic
     * - Can cause livelock if all threads keep timing out and retrying
     * 
     * 
     * CONFUSION 5: "Why not just use lock-free everything?"
     * =========================================================
     * Lock-free has limitations:
     * - Only works for simple operations (atomic operations)
     * - Can't protect complex multi-step transactions
     * - More difficult to implement correctly
     * - Not available for all data types
     * - Example: You can't atomically check balance AND transfer in one operation
     * 
     * 
     * CONFUSION 6: "Is reentrant lock different from lock ordering?"
     * ==================================================================
     * YES! Different concepts:
     * - Reentrancy: SAME thread can lock again (won't deadlock itself)
     * - Lock ordering: DIFFERENT threads must lock in same order
     * - Reentrancy: Thread A can lock1.lock() then lock1.lock() again (OK!)
     * - Lock ordering: Thread A lock1 then lock2, Thread B MUST also lock1 then lock2
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 13: MONITORING AND DEBUGGING DEADLOCK
     * ========================================================================
     * 
     * HOW TO KNOW IF YOUR APP HAS A DEADLOCK:
     * 
     * 1. Application hangs or becomes unresponsive
     * 2. Certain operations always timeout
     * 3. Logs show requests stuck at same place
     * 4. CPU usage drops to zero (threads blocked, not spinning)
     * 5. Thread count keeps increasing (new threads created, old ones blocked)
     * 
     * 
     * HOW TO DEBUG:
     * 
     * 1. USE JSTACK:
     *    jps                         # Find Java process ID
     *    jstack <PID>                # Print thread dump
     *    Look for "waiting to lock"
     * 
     * 2. ANALYZE THREAD DUMP:
     *    Thread-1: waiting to lock Lock1 (held by Thread-2)
     *    Thread-2: waiting to lock Lock2 (held by Thread-3)
     *    Thread-3: waiting to lock Lock1 (held by Thread-1)
     *    ^ This is a deadlock cycle!
     * 
     * 3. USE VISUALVM:
     *    Run VisualVM GUI tool
     *    Monitor thread states
     *    See blocked threads visually
     * 
     * 4. UNIT TESTS:
     *    Create stress tests with multiple threads
     *    Run millions of operations
     *    Try to trigger deadlock in controlled environment
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 14: WHEN DO YOU REALLY NEED TO WORRY?
     * ========================================================================
     * 
     * WORRY WHEN:
     * 
     * 1. MULTIPLE LOCKS: Your code uses 2 or more locks
     * 2. NESTING: Locks are acquired in nested blocks
     * 3. DIFFERENT ORDERS: Different methods acquire locks differently
     * 4. CONCURRENCY: High thread count and concurrent access
     * 5. COMPLEX LOGIC: Hard to trace all lock acquisition paths
     * 
     * 
     * DON'T WORRY WHEN:
     * 
     * 1. SINGLE LOCK: Using only one lock (no circular wait possible)
     * 2. LOCK-FREE: Using atomic/concurrent collections
     * 3. NO NESTING: Never acquire multiple locks from same lock
     * 4. SIMPLE CODE: Few threads, simple logic
     * 5. TIMEOUTS: All lock operations have timeouts
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 15: BEST PRACTICES CHECKLIST
     * ========================================================================
     * 
     * [✓] Always acquire locks in the SAME order across codebase
     * [✓] Keep lock scope SMALL - don't hold locks during I/O
     * [✓] Use tryLock() with timeouts for complex scenarios
     * [✓] Prefer concurrent collections over synchronized + manual locks
     * [✓] Document lock ordering (write it down!)
     * [✓] Avoid calling other synchronized methods while holding lock
     * [✓] Use lock-free design when possible
     * [✓] Test with high concurrency and stress testing
     * [✓] Use jstack to check for deadlocks in production
     * [✓] Consider fairness if writer starvation is observed
     * [✓] Always unlock in finally block
     * [✓] Use single lock if you don't have concurrent requirements
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 16: REAL-WORLD DECISION TREE
     * ========================================================================
     * 
     * Do you need multiple locks?
     * |
     * +--NO--> Use single lock or lock-free structures
     *          No deadlock possible!
     * |
     * +--YES--> Can you use concurrent collections?
     *           |
     *           +--YES--> Use ConcurrentHashMap, CopyOnWriteArrayList, etc.
     *                     No deadlock possible!
     *           |
     *           +--NO---> Do you need timeouts anyway?
     *                     |
     *                     +--YES--> Use tryLock() with timeout
     *                     |
     *                     +--NO---> Can you order locks?
     *                               |
     *                               +--YES--> Document and enforce lock ordering
     *                               |
     *                               +--NO---> Use interruptible locks
     * 
     * ========================================================================
     */
    
    // Example: Comprehensive demonstration of deadlock prevention
    public static class ComprehensiveExample {
        private Lock accountLock = new ReentrantLock();
        private Lock transactionLock = new ReentrantLock();
        
        // GOOD: Lock ordering - always account first, then transaction
        public void processTransaction(double amount) {
            accountLock.lock();
            try {
                transactionLock.lock();
                try {
                    // Do work
                    System.out.println("Processing transaction: " + amount);
                } finally {
                    transactionLock.unlock();
                }
            } finally {
                accountLock.unlock();
            }
        }
        
        // GOOD: Same order maintained
        public void cancelTransaction() {
            accountLock.lock();
            try {
                transactionLock.lock();
                try {
                    // Do work
                    System.out.println("Canceling transaction");
                } finally {
                    transactionLock.unlock();
                }
            } finally {
                accountLock.unlock();
            }
        }
        
        // NO DEADLOCK! Both methods use locks in same order
    }
}
