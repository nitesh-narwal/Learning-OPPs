package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework.SimpleTopics;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class CountDownLatchTheory {
// ============================================================================
// COMPREHENSIVE COUNTDOWNLATCH GUIDE - BEGINNER TO ADVANCED
// ============================================================================
// Master CountDownLatch for multithreading coordination and synchronization
// Read sequentially from top to bottom for complete understanding
// ============================================================================

// ============================================================================
    // SECTION 1: WHAT IS COUNTDOWNLATCH? (CONCEPTUAL FOUNDATION)
    // ============================================================================
    /*
    SIMPLE DEFINITION:
    CountDownLatch is a synchronization primitive that allows multiple threads
    to wait for a set of operations to complete before proceeding.

    Think of it like a CLASSROOM SCENARIO:
    - Teacher: "Class, don't leave until all 30 students complete the exam"
    - As each student finishes, they leave (countdown)
    - When counter reaches 0 (all 30 done), teacher can proceed
    - Students waiting outside can enter after the countdown reaches 0

    KEY CHARACTERISTICS:
    └─ One-time use only! (Cannot be reset)
    └─ Threads WAIT at await() until counter becomes 0
    └─ Other threads DECREMENT the counter via countDown()
    └─ When counter reaches 0, all waiting threads are released

    REAL-WORLD ANALOGY (RACE START):
    - Marshal: "All 8 runners must be ready before race starts"
    - Each runner signals "Ready" (countdown by 1)
    - Marshal counts: 8, 7, 6, 5, 4, 3, 2, 1, 0
    - When 0, the race STARTS (all waiting runners released)

    CODE EXAMPLE:
    CountDownLatch latch = new CountDownLatch(3);  // Wait for 3 events
    
    // Thread 1 waits
    latch.await();  // BLOCKED until counter reaches 0
    
    // Other threads signal completion
    latch.countDown();  // counter: 3 → 2
    latch.countDown();  // counter: 2 → 1
    latch.countDown();  // counter: 1 → 0 (Thread 1 RELEASED!)
    */

    // ============================================================================
    // SECTION 2: KEY BENEFITS OF COUNTDOWNLATCH
    // ============================================================================
    /*
    BENEFIT 1: SIMPLE THREAD COORDINATION
    - Easy to understand: "wait for N events"
    - Cleaner than manual notification logic
    - Built-in synchronization, no data corruption

    BENEFIT 2: ONE-WAY LATCH (READ SAFETY)
    - After countdown to 0, thread-safe for reading
    - No need for locks once barrier passed
    - Perfect for initialization phases

    BENEFIT 3: MULTIPLE WAITERS
    - Many threads can call await() simultaneously
    - All released together (fairness)
    - No starvation issues

    BENEFIT 4: TIMEOUT SUPPORT
    - await(long timeout, TimeUnit unit) available
    - Can break free after specific time
    - Handles deadlock scenarios

    BENEFIT 5: CLEAN EXCEPTION PROPAGATION
    - InterruptedException if thread interrupted
    - Natural way to handle interrupts
    - Can gracefully shutdown

    BENEFIT 6: PARTITIONED BARRIER (Unlike CyclicBarrier)
    - Master waits for workers separately
    - NOT all-or-nothing like CyclicBarrier
    - More flexible coordination

    BENEFIT 7: LIGHTWEIGHT
    - Fast synchronization
    - No context switching overhead (compared to locks)
    - Optimal for coordination-only scenarios

    PERFORMANCE COMPARISON:
    Scenario                | CyclicBarrier | CountDownLatch | Winner
    =====================+=================+=================+========
    Wait for N events      | Possible       | YES             | CDL **
    Reusable barrier       | YES            | NO              | CB **
    Master-worker pattern  | NO             | YES             | CDL **
    Multiple waiters       | YES            | YES             | -
    Timeout support        | NO             | YES             | CDL **
    */

    // ============================================================================
    // SECTION 3: KEY DRAWBACKS OF COUNTDOWNLATCH
    // ============================================================================
    /*
    DRAWBACK 1: ONE-TIME USE ONLY
    - Cannot reset counter after reaching 0
    - For reusable barrier, use CyclicBarrier
    - Must create new latch for each round

    DRAWBACK 2: NO REUSABILITY
    - Common mistake: trying to reuse
    - Each new barrier needs new CountDownLatch
    - Can be memory wasteful in loops

    DRAWBACK 3: COUNTING ERRORS HARD TO DEBUG
    - If you countDown() more times than initialized: OK (counter stays at 0)
    - If you countDown() less: threads wait forever (DEADLOCK!)
    - Silent failures are dangerous

    DRAWBACK 4: LIMITED FLEXIBILITY
    - Only forward counting (cannot increment)
    - Cannot check current count safely
    - Limited to hierarchical barriers

    DRAWBACK 5: NO EXCEPTION PROPAGATION FROM WORKERS
    - If worker thread throws exception
    - Main thread waiting won't know
    - No automatic propagation

    DRAWBACK 6: ORDERING ISSUES
    - If await() called after all countDown(): immediate return
    - Timing-dependent behavior
    - Race conditions if not careful

    DRAWBACK 7: NOT INTERRUPTIBLE BY DEFAULT
    - await() can be interrupted (good)
    - But task already started (warning)
    - Need manual cancellation logic

    DRAWBACK 8: MEMORY OVERHEAD
    - Each latch takes memory (internal counter, waiters list)
    - Creating many latches = memory

    COMMON PITFALLS:
    ❌ Forgetting initialize with correct count
    ❌ Calling countDown() more than init count (OK, but dangerous)
    ❌ Throwing exception in task without decrementing (DEADLOCK!)
    ❌ Calling await() after all countDown() - races!
    ❌ Trying to reuse same latch (creates new tasks, don't work)
    */

    // ============================================================================
    // SECTION 4: HOW COUNTDOWNLATCH WORKS INTERNALLY
    // ============================================================================
    /*
    STEP-BY-STEP: What happens?

    Step 1: INITIALIZATION
    -------
    CountDownLatch latch = new CountDownLatch(3);
    Internal state:
    ├─ counter = 3
    └─ waiters = empty list

    Step 2: MULTIPLE THREADS CALL await()
    -------
    Thread A: latch.await()  // BLOCKED, added to waiters list
    Thread B: latch.await()  // BLOCKED, added to waiters list
    Thread C: latch.await()  // BLOCKED, added to waiters list

    Internal state:
    ├─ counter = 3
    └─ waiters = [A, B, C]

    Step 3: OTHER THREADS COUNT DOWN
    -------
    Thread X: latch.countDown()  // counter: 3 → 2, NO release
    Thread Y: latch.countDown()  // counter: 2 → 1, NO release
    Thread Z: latch.countDown()  // counter: 1 → 0, RELEASE ALL!

    After final countDown():
    ├─ counter = 0 (locked forever)
    └─ Threads A, B, C all released simultaneously

    Step 4: FUTURE AWAIT() CALLS
    -------
    Thread D: latch.await()  // counter = 0, returns IMMEDIATELY
    (No blocking, state is terminal)

    KEY INSIGHT: CountDownLatch is ONE-WAY!
    counter: 3 → 2 → 1 → 0 ← STUCK HERE FOREVER
    Once 0, it never changes back.

    INTERNAL SYNCHRONIZATION:
    - Uses AbstractQueuedSynchronizer (AQS) internally
    - Atomic counter operations
    - Fair lock policy (FIFO for threads)

    MEMORY LAYOUT:
    CountDownLatch instance:
    ├─ internal counter (atomic)
    ├─ condition queue (list of waiting threads)
    └─ lock object

    When counter reaches 0:
    └─ All threads in queue are signaled
    └─ They independently check counter and proceed
    └─ No more state changes after this
    */

    // ============================================================================
    // SECTION 5: BASIC COUNTDOWNLATCH OPERATIONS
    // ============================================================================

    public static void section5_BasicOperations() {
        System.out.println("\n=== SECTION 5: BASIC COUNTDOWNLATCH OPERATIONS ===");

        // CREATE a CountDownLatch for 3 events
        CountDownLatch latch = new CountDownLatch(3);
        System.out.println("Created CountDownLatch with count = 3");

        // Thread that waits
        Thread waiter = new Thread(() -> {
            try {
                System.out.println("  [Waiter] Starting to wait...");
                latch.await();  // BLOCKS here
                System.out.println("  [Waiter] Released! All 3 countdown events occurred!");
            } catch (InterruptedException e) {
                System.out.println("  [Waiter] Interrupted!");
                Thread.currentThread().interrupt();
            }
        });

        waiter.start();

        // Simulate 3 events (countdowns)
        try {
            Thread.sleep(100);  // Let waiter start first
            
            System.out.println("  [Event 1] Occurring...");
            latch.countDown();  // 3 → 2
            System.out.println("  [Event 1] Done! Count now: " + latch.getCount());
            
            Thread.sleep(100);
            System.out.println("  [Event 2] Occurring...");
            latch.countDown();  // 2 → 1
            System.out.println("  [Event 2] Done! Count now: " + latch.getCount());
            
            Thread.sleep(100);
            System.out.println("  [Event 3] Occurring...");
            latch.countDown();  // 1 → 0 (RELEASES WAITER!)
            System.out.println("  [Event 3] Done! Count now: " + latch.getCount());

            waiter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ============================================================================
    // SECTION 6: PRACTICAL EXAMPLE 1 - WAITING FOR ALL THREADS TO COMPLETE
    // ============================================================================
    /*
    REAL-WORLD SCENARIO: Web crawler
    - Main thread submits 5 download tasks
    - Each task downloads a page
    - Main thread waits for ALL downloads to complete before processing

    Traditional approach: Without CountDownLatch
    ❌ Manual synchronization
    ❌ Flag checking in loop
    ❌ Complex and error-prone

    With CountDownLatch:
    ✅ One line: latch.await()
    ✅ Clean and understandable
    ✅ Guaranteed synchronization
    */

    public static void section6_WaitForAllThreads() {
        System.out.println("\n=== SECTION 6: WAIT FOR ALL THREADS TO COMPLETE ===");

        int numTasks = 3;
        CountDownLatch latch = new CountDownLatch(numTasks);
        ExecutorService executor = Executors.newFixedThreadPool(numTasks);

        System.out.println("Submitting " + numTasks + " download tasks...");

        // Submit 3 download tasks
        for (int i = 1; i <= numTasks; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("  [Task " + taskId + "] Downloading...");
                try {
                    Thread.sleep(1000 + taskId * 500);  // Simulate work
                    System.out.println("  [Task " + taskId + "] Download complete!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();  // Signal completion
                }
            });
        }

        try {
            System.out.println("Main: Waiting for all downloads to complete...");
            long startTime = System.currentTimeMillis();
            latch.await();  // Wait for all tasks
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("Main: All downloads complete in " + duration + "ms!");
            System.out.println("Main: Processing downloaded data now...");
        } catch (InterruptedException e) {
            System.out.println("Main: Interrupted while waiting!");
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }

    // ============================================================================
    // SECTION 7: PRACTICAL EXAMPLE 2 - SYNCHRONIZED START (RACE START)
    // ============================================================================
    /*
    REAL-WORLD SCENARIO: Load testing
    - Start 5 concurrent load test threads at EXACT same time
    - Coordinator ensures all ready before starting
    - Prevents timing issues (some faster to prepare than others)

    Pattern:
    1. Create latch with count = 1 (just a gate)
    2. All worker threads wait at latch
    3. Main thread signals (countDown to 0)
    4. All workers start SIMULTANEOUSLY

    This is DIFFERENT from Section 6!
    Section 6: Wait for N tasks to FINISH
    Section 7: Synchronize N tasks to START at same time
    */

    public static void section7_SynchronizedStart() {
        System.out.println("\n=== SECTION 7: SYNCHRONIZED START (RACE START) ===");

        int numWorkers = 3;
        CountDownLatch startLatch = new CountDownLatch(1);  // Gate for start
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);

        System.out.println("Preparing " + numWorkers + " workers...");
        long masterStartTime = System.currentTimeMillis();

        // Create workers that wait at start gate
        for (int i = 1; i <= numWorkers; i++) {
            final int workerId = i;
            executor.submit(() -> {
                try {
                    System.out.println("  [Worker " + workerId + "] Ready, waiting for start signal...");
                    startLatch.await();  // WAIT AT GATE
                    long relativeTime = System.currentTimeMillis() - masterStartTime;
                    System.out.println("  [Worker " + workerId + "] STARTED at time " + relativeTime + "ms!");
                    Thread.sleep(500);  // Simulate work
                    System.out.println("  [Worker " + workerId + "] Done!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        try {
            Thread.sleep(200);  // Let all workers reach await()
            System.out.println("Coordinator: All workers ready! Starting race...");
            startLatch.countDown();  // Release all workers SIMULTANEOUSLY!
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }

    // ============================================================================
    // SECTION 8: PRACTICAL EXAMPLE 3 - TWO-PHASE BARRIER
    // ============================================================================
    /*
    REAL-WORLD SCENARIO: Database migration
    Phase 1: Prepare (all threads ready, wait for signal)
    Phase 2: Execute (all threads execute in parallel)

    Use TWO latches:
    1. readyLatch: All threads signal "I'm ready"
    2. startLatch: Main thread signals "START NOW"

    This is MORE POWERFUL than single latch!
    Two counters = fine-grained control
    */

    public static void section8_TwoPhaseBarrier() {
        System.out.println("\n=== SECTION 8: TWO-PHASE BARRIER ===");

        int numThreads = 3;
        CountDownLatch readyLatch = new CountDownLatch(numThreads);  // Phase 1
        CountDownLatch startLatch = new CountDownLatch(1);            // Phase 2
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        System.out.println("PHASE 1: Initialization phase");

        // Threads prepare in parallel
        for (int i = 1; i <= numThreads; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    System.out.println("  [Thread " + id + "] Initializing...");
                    Thread.sleep(100 + id * 50);  // Different prep times
                    System.out.println("  [Thread " + id + "] Ready!");
                    readyLatch.countDown();  // Signal I'm ready

                    // Wait for all to be ready, then wait for start signal
                    startLatch.await();
                    System.out.println("  [Thread " + id + "] Starting execution!");
                    Thread.sleep(200);
                    System.out.println("  [Thread " + id + "] Execution complete!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        try {
            System.out.println("Coordinator: Waiting for all threads ready...");
            readyLatch.await();  // Wait for all to be ready
            System.out.println("Coordinator: All ready! Current state verified.");

            Thread.sleep(100);
            System.out.println("\nPHASE 2: Execution phase");
            System.out.println("Coordinator: Signaling all threads to start...");
            startLatch.countDown();  // Release all threads

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }

    // ============================================================================
    // SECTION 9: PRACTICAL EXAMPLE 4 - WITH TIMEOUT
    // ============================================================================
    /*
    REAL-WORLD SCENARIO: API gateway timeout
    - Wait for max 5 seconds for all services to respond
    - If timeout: return error, don't wait forever
    - Prevents application hang

    Methods:
    ✅ await() - wait forever (blocking)
    ✅ await(timeout, unit) - wait with timeout
    └─ Returns: true if count reached 0, false if timeout
    */

    public static void section9_WithTimeout() {
        System.out.println("\n=== SECTION 9: COUNTDOWNLATCH WITH TIMEOUT ===");

        int numServices = 3;
        CountDownLatch latch = new CountDownLatch(numServices);
        ExecutorService executor = Executors.newFixedThreadPool(numServices);

        System.out.println("Starting 3 services with different completion times...\n");

        // Service 1: completes in 1 second
        executor.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Service 1: Ready!");
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Service 2: completes in 2 seconds
        executor.submit(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Service 2: Ready!");
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Service 3: SLOW - takes 10 seconds (will timeout)
        executor.submit(() -> {
            try {
                Thread.sleep(10000);
                System.out.println("Service 3: Ready!");
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        try {
            System.out.println("Gateway: Waiting for all services (timeout = 3 seconds)...");
            boolean allReady = latch.await(3, TimeUnit.SECONDS);

            if (allReady) {
                System.out.println("Gateway: All services ready!");
            } else {
                System.out.println("Gateway: TIMEOUT! Only " + (numServices - latch.getCount()) + " of " + numServices + " services ready.");
                System.out.println("Gateway: Returning DEGRADED response to client.");
            }
        } catch (InterruptedException e) {
            System.out.println("Gateway: Interrupted!");
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }

    // ============================================================================
    // SECTION 10: PRACTICAL EXAMPLE 5 - EXCEPTION HANDLING
    // ============================================================================
    /*
    CRITICAL ISSUE: What happens if a worker throws exception?
    - The latch.countDown() is never called
    - Waiting threads wait FOREVER
    - Classic deadlock!

    SOLUTION: Use try-finally
    try {
        // Actual work
    } finally {
        latch.countDown();  // ALWAYS count down
    }

    ALTERNATIVE: Use try-with-resources or CountdownLatch wrapper
    */

    public static void section10_ExceptionHandling() {
        System.out.println("\n=== SECTION 10: EXCEPTION HANDLING ===");

        int numTasks = 3;
        CountDownLatch latch = new CountDownLatch(numTasks);
        ExecutorService executor = Executors.newFixedThreadPool(numTasks);

        for (int i = 1; i <= numTasks; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    System.out.println("  [Task " + taskId + "] Working...");
                    if (taskId == 2) {
                        throw new SQLException("Database connection failed!");
                    }
                    Thread.sleep(500);
                    System.out.println("  [Task " + taskId + "] Success!");
                } catch (SQLException e) {
                    System.out.println("  [Task " + taskId + "] Error: " + e.getMessage());
                } catch (InterruptedException e) {
                    System.out.println("  [Task " + taskId + "] Interrupted!");
                    Thread.currentThread().interrupt();
                } finally {
                    // ALWAYS count down, even if exception!
                    latch.countDown();
                    System.out.println("  [Task " + taskId + "] Counted down!");
                }
            });
        }

        try {
            System.out.println("Main: Waiting for all tasks...");
            latch.await();
            System.out.println("Main: All tasks processed (some may have failed)!");
        } catch (InterruptedException e) {
            System.out.println("Main: Interrupted!");
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }

    // Custom exception for testing
    static class SQLException extends Exception {
        public SQLException(String msg) { super(msg); }
    }

    // ============================================================================
    // SECTION 11: COUNTDOWNLATCH VS OTHER SYNCHRONIZERS
    // ============================================================================
    /*
    WHEN TO USE WHAT?

    Synchronizer      | One-time | Reusable | Use Case
    ================= +=========+==========+==================================
    CountDownLatch    | YES      | NO       | Wait for N events to complete
    CyclicBarrier     | NO       | YES      | Reusable multi-round barrier
    Semaphore         | NO       | YES      | Limit concurrent access (N slots)
    Phaser            | NO       | YES      | Advanced multi-phase coordination
    CountDownLatch    | YES      | NO       | One-way synchronization
    ================= +=========+==========+==================================

    QUICK DECISION TREE:

    Need to wait for N events?
    ├─ YES
    │  ├─ Reuse same barrier multiple times?
    │  │  ├─ YES → CyclicBarrier
    │  │  └─ NO → CountDownLatch ✓
    │  └─ Limit concurrent threads?
    │     ├─ YES → Semaphore ✓
    │     └─ NO → CountDownLatch ✓
    └─ NO
       └─ Use other primitives (Lock, Monitor, etc.)

    SPECIFIC COMPARISONS:

    CountDownLatch vs CyclicBarrier:
    ┌──────────────────────┬────────────┬────────────┐
    │ Feature              │ CountDown  │ Cyclic     │
    │                      │ Latch      │ Barrier    │
    ├──────────────────────┼────────────┼────────────┤
    │ Reusable             │ NO         │ YES        │
    │ One-way              │ YES        │ NO         │
    │ Multiple waiters     │ YES        │ YES        │
    │ Master-worker        │ YES        │ NO         │
    │ All participate      │ NO         | YES        │
    │ Timeout              │ YES        │ YES        │
    │ Performance          │ Faster     │ Slower     │
    └──────────────────────┴────────────┴────────────┘

    SCENARIO-BASED CHOICE:

    Scenario: Download 5 files in parallel, process when all done
    └─ CountDownLatch (one-time barrier)

    Scenario: Multiple threads rendezvous at checkpoint
    └─ CyclicBarrier (all must reach point)

    Scenario: Limit 10 concurrent connections to database
    └─ Semaphore(10) (not a barrier)

    Scenario: Pipeline with multiple stages
    └─ Phaser (advanced)

    Scenario: Load testing with waves
    └─ CyclicBarrier (reusable for multiple waves)
    */

    // ============================================================================
    // SECTION 12: COMMON MISTAKES & HOW TO AVOID
    // ============================================================================
    /*
    MISTAKE 1: FORGETTING TRY-FINALLY AROUND countDown()
    -------
    ❌ WRONG:
    try {
        // Work
        doSomething();
        latch.countDown();  // If doSomething throws, this never runs!
    } catch (Exception e) {
        // Handle error
        // BUT latch still waiting! DEADLOCK!
    }

    ✅ CORRECT:
    try {
        doSomething();
    } finally {
        latch.countDown();  // Always runs!
    }

    MISTAKE 2: WRONG INITIAL COUNT
    -------
    ❌ WRONG:
    CountDownLatch latch = new CountDownLatch(5);
    executor.submit(task1);  // Supposed to have 5 tasks
    executor.submit(task2);
    executor.submit(task3);  // Only 3 submitted! Waiting forever!
    latch.await();  // DEADLOCK!

    ✅ CORRECT:
    CountDownLatch latch = new CountDownLatch(3);  // Match actual count
    executor.submit(task1);
    executor.submit(task2);
    executor.submit(task3);
    latch.await();

    MISTAKE 3: TRYING TO REUSE SAME LATCH
    -------
    ❌ WRONG:
    for (int round = 0; round < 10; round++) {
        latch.countDown();  // Already 0, doesn't work!
        latch.await();      // Returns immediately
    }

    ✅ CORRECT:
    for (int round = 0; round < 10; round++) {
        CountDownLatch latch = new CountDownLatch(3);  // NEW latch each round
        // Submit tasks
        latch.await();
    }

    MISTAKE 4: NOT CHECKING TIMEOUT RETURN VALUE
    -------
    ❌ WRONG:
    latch.await(5, TimeUnit.SECONDS);  // Ignored return value!
    // Assume all threads done, but they might not be!

    ✅ CORRECT:
    boolean success = latch.await(5, TimeUnit.SECONDS);
    if (!success) {
        System.out.println("TIMEOUT! Some threads still running!");
    }

    MISTAKE 5: CALLING await() AFTER ALL countDown()
    -------
    Problem: Race condition
    ├─ If await() called after countDown() reaches 0: OK (returns immediately)
    └─ If countDown() happens after await() called: OK (threads released)
    But timing-dependent!

    MISTAKE 6: CALLING countDown() MORE TIMES THAN INITIALIZED
    -------
    ❌ WRONG:
    CountDownLatch latch = new CountDownLatch(2);
    latch.countDown();  // 2 → 1
    latch.countDown();  // 1 → 0
    latch.countDown();  // 0 → 0 (no change!)  ← DANGER: unclear behavior
    latch.await();      // Returns immediately (OK)

    ISSUE: Code becomes hard to debug
    └─ Extra countDown() silently ignored
    └─ Suggests logical error in task

    MISTAKE 7: SHARED LATCHES ACROSS DIFFERENT PHASES
    -------
    ❌ RISKY:
    CountDownLatch latch = new CountDownLatch(10);
    // Use for first phase
    latch.await();
    // Reuse for second phase
    // ERROR! counter is 0 and cannot reset

    ✅ Separate latches for each phase
    */

    public static void section12_CommonMistakes() {
        System.out.println("\n=== SECTION 12: COMMON MISTAKES ===");

        // Mistake 1 demo: Importance of try-finally
        System.out.println("Mistake 1: NOT using try-finally for countDown()");
        CountDownLatch latch1 = new CountDownLatch(2);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            try {
                System.out.println("  Task 1 starting...");
                // Simulate work with potential exception
                int x = 10 / 1;  // OK
                System.out.println("  Task 1 done!");
            } catch (Exception e) {
                System.out.println("  Task 1 exception: " + e);
            } finally {
                System.out.println("  Task 1: Finally block - counting down!");
                latch1.countDown();
            }
        });

        executor.submit(() -> {
            try {
                System.out.println("  Task 2 starting...");
                Thread.sleep(200);
                System.out.println("  Task 2 done!");
            } catch (Exception e) {
                System.out.println("  Task 2 exception: " + e);
            } finally {
                System.out.println("  Task 2: Finally block - counting down!");
                latch1.countDown();
            }
        });

        try {
            System.out.println("Waiting for all tasks...");
            latch1.await();
            System.out.println("All tasks complete!\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();

        // Mistake 4 demo: Checking timeout return value
        System.out.println("Mistake 4: NOT checking timeout return value");
        CountDownLatch latch2 = new CountDownLatch(1);

        new Thread(() -> {
            try {
                Thread.sleep(5000);  // Takes 5 seconds
                latch2.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        try {
            System.out.println("Waiting with timeout of 2 seconds...");
            boolean finished = latch2.await(2, TimeUnit.SECONDS);
            if (finished) {
                System.out.println("SUCCESS: Task completed within timeout!");
            } else {
                System.out.println("TIMEOUT: Task not completed in 2 seconds!");
                System.out.println("(Task still running in background)");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ============================================================================
    // SECTION 13: ADVANCED PATTERNS
    // ============================================================================
    /*
    PATTERN 1: MASTER-WORKER WITH SHARED LATCH
    --------
    readyLatch (workers) → startLatch (master)
    
    All workers wait at startLatch until master signals start
    Good for: synchronized task execution

    PATTERN 2: MULTIPLE LATCHES FOR MULTIPLE PHASES
    --------
    phase1Latch → phase2Latch → phase3Latch
    
    Sequential barrier progression
    Good for: pipeline processing

    PATTERN 3: EXCEPTION COLLECTION
    --------
    Each task stores exception instead of throwing
    Main thread collects after latch.await()
    Good for: batch error reporting

    PATTERN 4: CONDITIONAL TASK SUBMISSION
    --------
    Submit tasks only if latch count > 0
    Prevents orphaned tasks
    Good for: defensive programming

    PATTERN 5: HIERARCHICAL COUNTDOWN
    --------
    Inner latch → Outer latch
    Nested barriers for hierarchical sync
    Good for: complex multi-level coordination
    */

    public static void section13_AdvancedPatterns() {
        System.out.println("\n=== SECTION 13: ADVANCED PATTERNS ===");

        // PATTERN 3: Exception Collection
        System.out.println("Pattern 3: Exception Collection Pattern");
        
        class Task implements Runnable {
            String name;
            Exception exception = null;

            Task(String name) { this.name = name; }

            @Override
            public void run() {
                try {
                    System.out.println("  Task " + name + " running...");
                    if (name.equals("T2")) {
                        throw new SQLException("Task " + name + " failed!");
                    }
                    Thread.sleep(300);
                    System.out.println("  Task " + name + " completed!");
                } catch (Exception e) {
                    this.exception = e;
                    System.out.println("  Task " + name + " caught exception: " + e.getMessage());
                }
            }
        }

        int numTasks = 3;
        CountDownLatch latch = new CountDownLatch(numTasks);
        ExecutorService executor = Executors.newFixedThreadPool(numTasks);
        Task[] tasks = new Task[numTasks];

        for (int i = 0; i < numTasks; i++) {
            tasks[i] = new Task("T" + (i + 1));
            final int idx = i;
            executor.submit(() -> {
                try {
                    tasks[idx].run();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
            System.out.println("All tasks completed. Checking for errors...");
            for (Task t : tasks) {
                if (t.exception != null) {
                    System.out.println("  ERROR in " + t.name + ": " + t.exception.getMessage());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();
    }

    // ============================================================================
    // SECTION 14: MULTITHREADING BENEFITS & REAL-WORLD APPLICATIONS
    // ============================================================================
    /*
    MULTITHREADING BENEFIT 1: PARALLEL INITIALIZATION
    --------
    Before: Sequential startup (service1, then service2, then service3)
    └─ Time: 3 + 4 + 2 = 9 seconds

    After: Parallel startup with CountDownLatch
    └─ Time: max(3, 4, 2) = 4 seconds (70% faster!)

    Code:
    CountDownLatch latch = new CountDownLatch(3);
    executor.submit(service1task); // Start
    executor.submit(service2task); // Start
    executor.submit(service3task); // Start
    latch.await();                 // Wait for all
    // Continue when all ready

    MULTITHREADING BENEFIT 2: STAGGERED LOAD
    --------
    Instead of: All clients hitting server at exact time (thundering herd)
    Benefits: CountDownLatch → stagger requests naturally

    MULTITHREADING BENEFIT 3: GRACEFUL DEGRADATION
    --------
    With timeout:
    await(timeout) → if TIMEOUT, return partial results
    Not crashing, just returning degraded response

    MULTITHREADING BENEFIT 4: CLEAN COORDINATION LOGIC
    --------
    Without CountDownLatch:
    ├─ Manual flag checking
    ├─ Volatile variables
    ├─ Complex synchronization
    └─ Race conditions everywhere

    With CountDownLatch:
    ├─ One line: latch.await()
    ├─ Clear intent
    ├─ No race conditions
    └─ Thread-safe by design

    REAL-WORLD APPLICATION 1: TEST FRAMEWORK
    --------
    Scenario: Unit test with 4 parallel subtests
    CountDownLatch latch = new CountDownLatch(4);
    // Submit 4 subtests
    latch.await();
    // Assert results
    
    Use: Apache JMeter, Load testing frameworks

    REAL-WORLD APPLICATION 2: MICROSERVICES STARTUP
    --------
    Scenario: Start 10 microservices in parallel
    CountDownLatch readyLatch = new CountDownLatch(10);
    // Each service calls readyLatch.countDown() when ready
    readyLatch.await();
    // All services ready, start accepting requests
    
    Use: Kubernetes, Docker, Spring Boot initialization

    REAL-WORLD APPLICATION 3: DATABASE BULK INSERT
    --------
    Scenario: Insert 1000 records (10 threads × 100 records each)
    CountDownLatch latch = new CountDownLatch(10);
    // Submit 10 batch insert tasks
    latch.await();
    // All inserts done, commit
    
    Use: ETL processes, data import

    REAL-WORLD APPLICATION 4: API AGGREGATION
    --------
    Scenario: Dashboard calling 8 APIs in parallel
    CountDownLatch latch = new CountDownLatch(8);
    // Call 8 APIs in parallel threads
    latch.await(5, TimeUnit.SECONDS);  // Wait max 5 seconds
    // Return dashboard with available data (partial OK)
    
    Use: Backend-for-Frontend (BFF), API gateways

    REAL-WORLD APPLICATION 5: DISTRIBUTED LOCKS
    --------
    Scenario: Coordinate 5 replicas to agree on state
    CountDownLatch latch = new CountDownLatch(5);
    // Each replica votes
    latch.await();
    // Consensus reached, apply state
    
    Use: Database replication, consensus algorithms

    REAL-WORLD APPLICATION 6: GAME MULTIPLAYER STARTUP
    --------
    Scenario: Wait for 4 players to connect before starting game
    CountDownLatch latch = new CountDownLatch(4);
    // Each player connection signals
    latch.await();
    // All players ready, start game
    
    Use: Online multiplayer games

    REAL-WORLD APPLICATION 7: COMPILE & BUILD SYSTEMS
    --------
    Scenario: Compile 5 modules in parallel
    CountDownLatch latch = new CountDownLatch(5);
    // Spawn 5 compiler threads
    latch.await();
    // All compiled, start linking
    
    Use: Maven, Gradle, Build systems

    PERFORMANCE GAIN: PARALLELISM
    --------
    Sequential: 1 + 2 + 3 + 4 + 5 = 15 units of work
    Parallel (5 threads): max(5) = 5 units (70% faster!)
    Synchronization overhead: CountDownLatch adds minimal overhead
    */

    public static void section14_RealWorldApplications() {
        System.out.println("\n=== SECTION 14: REAL-WORLD APPLICATIONS ===");

        // Application: Microservices initialization
        System.out.println("Application: Microservices Startup");
        
        int numServices = 4;
        CountDownLatch latch = new CountDownLatch(numServices);
        ExecutorService executor = Executors.newFixedThreadPool(numServices);

        String[] services = {"AuthService", "DatabaseService", "CacheService", "LoggingService"};
        
        for (String service : services) {
            executor.submit(() -> {
                try {
                    System.out.println(service + ": Starting initialization...");
                    int duration = 500 + (int)(Math.random() * 1500);
                    Thread.sleep(duration);
                    System.out.println(service + ": Ready! (took " + duration + "ms)");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            System.out.println("Server: Waiting for all services to be ready...");
            long startTime = System.currentTimeMillis();
            latch.await();
            long totalTime = System.currentTimeMillis() - startTime;
            System.out.println("Server: All services ready in " + totalTime + "ms!");
            System.out.println("Server: Application ready to accept requests!\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();
    }

    // ============================================================================
    // SECTION 15: CONFUSION POINTS CLARIFIED
    // ============================================================================
    /*
    CONFUSION 1: getCount() FOR DECISION MAKING
    --------
    ❌ WRONG:
    if (latch.getCount() == 0) {
        // Assume all threads done
    }
    // RACE CONDITION! Count might change before next line

    ✅ CORRECT:
    Use latch.await() instead
    // Guaranteed all threads done when this returns

    CONFUSION 2: CAN YOU RESET COUNTDOWNLATCH?
    --------
    ❌ WRONG ASSUMPTION: "I'll reset counter and reuse"
    ✅ REALITY: NO RESET METHOD!
    
    CountDownLatch is ONE-TIME USE!
    Once counter reaches 0, it stays there forever.

    For reusable: Use CyclicBarrier instead

    CONFUSION 3: WHAT IF SOMEONE CALLS countDown() WITHOUT await()?
    --------
    ✅ FINE!
    CountDownLatch doesn't require someone waiting.
    
    It's just a counter:
    count: 5 → 4 → 3 → 2 → 1 → 0
    
    Whether anyone is waiting is irrelevant.
    The counter just decrements.

    CONFUSION 4: DIFFERENCE BETWEEN countDown() AND countUp()
    --------
    ❌ WRONG: "I need to count UP"
    ✅ There's no countUp() method!
    
    CountDownLatch only counts DOWN.
    If you need to count UP, use a different primitive.

    CONFUSION 5: what IF YOU initialize WITH 0?
    --------
    CountDownLatch latch = new CountDownLatch(0);
    latch.await();  // Returns IMMEDIATELY! No waiting!
    
    Useful for: Optional delays, conditional synchronization
    But usually: bug (programmer forgot to set correct count)

    CONFUSION 6: multiple THREADS CALLING await() SIMULTANEOUSLY
    --------
    ✅ PERFECTLY FINE!
    
    All threads wait.
    When counter reaches 0, ALL are released simultaneously.
    No batching, no queuing.
    
    This is a FEATURE, not a bug!

    CONFUSION 7: IS COUNTDOWNLATCH A BARRIER?
    --------
    ❌ NOT EXACTLY!
    
    Barrier = Synchronization point (all must reach)
    CountDownLatch = Event counter (events must occur)
    
    Barriers are usually bidirectional.
    CountDownLatch is one-way (only down).
    
    For true barrier: Use CyclicBarrier

    CONFUSION 8: CALLING countDown() WHEN ALREADY 0
    --------
    CountDownLatch latch = new CountDownLatch(2);
    latch.countDown();  // 2 → 1
    latch.countDown();  // 1 → 0
    latch.countDown();  // 0 → 0 (no change)
    
    ✅ SAFE (no negative numbers)
    ⚠️ But indicates logic error!

    CONFUSION 9: DIFFERENCE FORM SEMAPHORE(1)
    --------
    Semaphore(1):
    ├─ acquire() - can be called multiple times
    ├─ release() - replenish permits
    └─ Reusable for multiple entries

    CountDownLatch:
    ├─ countDown() - exactly N times
    ├─ await() - one-time wait
    └─ One-time use

    USE Semaphore(1) for mutual exclusion.
    USE CountDownLatch for event waiting.
    */

    public static void section15_ConfusionPoints() {
        System.out.println("\n=== SECTION 15: CONFUSION POINTS CLARIFIED ===");

        // Confusion 3: countDown() without await()
        System.out.println("Confusion 3: countDown() without anyone waiting");
        CountDownLatch latch1 = new CountDownLatch(3);
        System.out.println("Initial count: " + latch1.getCount());
        
        latch1.countDown();
        System.out.println("After countDown #1: " + latch1.getCount());
        
        latch1.countDown();
        System.out.println("After countDown #2: " + latch1.getCount());
        
        latch1.countDown();
        System.out.println("After countDown #3: " + latch1.getCount());
        System.out.println("(No thread was waiting, but counter decreased anyway!)\n");

        // Confusion 5: Initialize with 0
        System.out.println("Confusion 5: Initialize with 0");
        CountDownLatch latch2 = new CountDownLatch(0);
        System.out.println("Created with count = 0");
        try {
            System.out.println("Calling await()...");
            latch2.await();
            System.out.println("Returned immediately! (no waiting)\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Confusion 6: Multiple threads waiting
        System.out.println("Confusion 6: Multiple threads waiting simultaneously");
        CountDownLatch latch3 = new CountDownLatch(1);
        AtomicInteger releasedCount = new AtomicInteger(0);

        for (int i = 0; i < 3; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("  Thread " + threadId + " waiting...");
                    latch3.await();
                    releasedCount.incrementAndGet();
                    System.out.println("  Thread " + threadId + " released!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        try {
            Thread.sleep(200);
            System.out.println("Main: Releasing all waiting threads!");
            latch3.countDown();
            Thread.sleep(200);
            System.out.println("Main: Total released: " + releasedCount.get() + " threads\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ============================================================================
    // SECTION 16: BEST PRACTICES CHECKLIST
    // ============================================================================
    /*
    INITIALIZATION:
    ✅ Match initial count to number of tasks
    ✅ Use meaningful counter values (not magic numbers)
    ✅ Document expected count in comments
    ✅ Use try-finally for countDown()

    USAGE PATTERNS:
    ✅ Always call countDown() in finally block
    ✅ Check timeout return value
    ✅ Handle InterruptedException properly
    ✅ Use separate latches for different phases

    PERFORMANCE:
    ✅ Pre-create and submit tasks before await()
    ✅ Use ExecutorService for thread management
    ✅ Consider timeout to prevent deadlocks
    ✅ Monitor for hanging threads

    DEBUGGING:
    ✅ Log countDown() calls
    ✅ Use thread dump tools (jstack, VisualVM)
    ✅ Check initial count matches task count
    ✅ Verify no extra countDown() calls

    MULTITHREADING:
    ✅ Use try-finally for exception safety
    ✅ Don't share latches across phases
    ✅ Use CyclicBarrier if reusability needed
    ✅ Collect exceptions from workers
    ✅ Don't rely on getCount() for decisions
    ✅ Handle thread interruption gracefully

    ADVANCED:
    ✅ Use CountDownLatch for master-worker pattern
    ✅ Combine multiple latches for multi-phase sync
    ✅ Consider Phaser for complex scenarios
    ✅ Document threading assumptions
    ✅ Test with thread contention

    ANTI-PATTERNS (AVOID):
    ❌ Trying to reuse same latch
    ❌ Not using try-finally
    ❌ Ignoring timeout return value
    ❌ Using getCount() for decisions
    ❌ Sharing latches across unrelated code
    ❌ Not handling InterruptedException
    ❌ Forgetting to start tasks before await()
    */

    // ============================================================================
    // MAIN: RUN ALL SECTIONS
    // ============================================================================
        public static void main(String[] args) {
            System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
            System.out.println("║     COMPREHENSIVE COUNTDOWNLATCH GUIDE - BEGINNER TO ADVANCED    ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════╝");

            section5_BasicOperations();
            section6_WaitForAllThreads();
            section7_SynchronizedStart();
            section8_TwoPhaseBarrier();
            section9_WithTimeout();
            section10_ExceptionHandling();
            section12_CommonMistakes();
            section13_AdvancedPatterns();
            section14_RealWorldApplications();
            section15_ConfusionPoints();

            System.out.println("╔════════════════════════════════════════════════════════════════════╗");
            System.out.println("║          All examples completed successfully!                     ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════╝");
            System.out.println("\nREADY FOR COUNTDOWNLATCH INTERVIEW QUESTIONS!");
            System.out.println("Review the theory sections above for complete understanding.\n");
        }
    }
