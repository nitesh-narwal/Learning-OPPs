package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.threadpool;

import java.util.concurrent.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * ============================================================================
 * THREAD POOL - COMPREHENSIVE GUIDE
 * ============================================================================
 * 
 * WHAT IS A THREAD POOL?
 * ======================
 * A Thread Pool is a collection of PRE-CREATED, REUSABLE threads that
 * execute tasks submitted to a queue. Instead of creating a new thread
 * for each task, the pool reuses existing threads, which is much more
 * efficient.
 * 
 * Think of it like a restaurant:
 * - OLD WAY: Hire a new chef for each customer (create new thread per task)
 *   Problem: Hiring is expensive, takes time, chef size varies
 * 
 * - NEW WAY: Hire 5 permanent chefs at the start (thread pool)
 *   Benefit: Always ready, consistent quality, no hiring overhead
 *   Chefs take orders from queue as customers arrive
 * 
 * ============================================================================
 * WHY DO WE NEED THREAD POOLS?
 * =============================
 * 
 * PROBLEM WITH CREATING A NEW THREAD PER TASK:
 * =============================================
 * 
 * for (Task task : tasks) {
 *     new Thread(task).start();  // DON'T DO THIS!
 * }
 * 
 * Problems:
 * 1. Thread creation is EXPENSIVE (memory, CPU overhead)
 * 2. Creating 10,000 threads = disaster!
 * 3. Each thread takes ~1MB memory
 * 4. OS scheduler gets overwhelmed
 * 5. Context switching kills performance
 * 6. No control over number of concurrent threads
 * 
 * SOLUTION: Thread Pool
 * =====================
 * 
 * ExecutorService executor = Executors.newFixedThreadPool(10);
 * for (Task task : tasks) {
 *     executor.execute(task);  // Queue task to pool
 * }
 * executor.shutdown();
 * 
 * Benefits:
 * 1. Fixed number of threads (you control concurrency)
 * 2. Threads are reused (no creation overhead)
 * 3. Tasks wait in queue if all threads busy
 * 4. System stays stable under load
 * 5. Better performance overall
 * 
 * ============================================================================
 * KEY BENEFITS OF THREAD POOLS
 * =============================
 * 
 * 1. PERFORMANCE
 *    - Reuses threads (no creation overhead)
 *    - Faster task execution (thread already exists)
 *    - Better CPU utilization
 * 
 * 2. RESOURCE MANAGEMENT
 *    - Fixed number of threads (predictable memory)
 *    - No thread explosion
 *    - OS isn't overwhelmed
 * 
 * 3. CONCURRENCY CONTROL
 *    - You decide how many parallel tasks
 *    - Fixed pool size = predictable behavior
 *    - Prevents resource exhaustion
 * 
 * 4. EASIER MANAGEMENT
 *    - SingleThreadExecutor: Sequential execution
 *    - FixedThreadPool(n): Exactly n parallel tasks
 *    - CachedThreadPool: Auto-scaling threads
 *    - ScheduledExecutor: Delayed/periodic tasks
 * 
 * 5. SCALABILITY
 *    - Handle thousands of tasks with few threads
 *    - Queue buffers excess tasks
 *    - System remains responsive
 * 
 * 6. LIFECYCLE MANAGEMENT
 *    - shutdown(): Wait for tasks to complete
 *    - shutdownNow(): Force stop
 *    - awaitTermination(): Block until done
 * 
 * ============================================================================
 * KEY DRAWBACKS OF THREAD POOLS
 * ==============================
 * 
 * 1. COMPLEXITY
 *    - More setup than manual threading
 *    - Need to understand ExecutorService API
 *    - Lifecycle management needed
 * 
 * 2. TUNING
 *    - Wrong pool size = wasted resources
 *    - Too many threads: context switching overhead
 *    - Too few threads: some stay idle
 * 
 * 3. QUEUE BUILDUP
 *    - If tasks produced faster than executed
 *    - Queue grows enormously
 *    - Memory can be exhausted
 * 
 * 4. REJECTION HANDLING
 *    - When queue is full, what happens?
 *    - Default: RejectedExecutionException
 *    - Need explicit rejection policy
 * 
 * 5. DEBUGGING
 *    - Tasks run asynchronously
 *    - Stack traces less meaningful
 *    - Harder to trace execution flow
 * 
 * 6. NOT ALWAYS BETTER
 *    - For very few tasks: overhead outweighs benefits
 *    - For 1-2 tasks: just use regular threads
 * 
 * ============================================================================
 */

public class Threadpool {
    
    // Helper to print with timestamp
    private static void print(String msg) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
        System.out.println("[" + time + "] " + Thread.currentThread().getName() + " - " + msg);
    }
    
    
    /*
     * ========================================================================
     * SECTION 1: EXECUTOR FRAMEWORK BASICS (BEGINNER)
     * ========================================================================
     * 
     * WHAT IS ExecutorService?
     * =========================
     * The main interface for thread pool management in Java.
     * Provides methods to submit tasks and manage lifecycle.
     * 
     * KEY METHODS:
     * - execute(Runnable): Submit task, no return value
     * - submit(Runnable/Callable): Submit task, returns Future
     * - shutdown(): Stop accepting new tasks, finish existing
     * - shutdownNow(): Immediate stop, best effort cancel
     * - awaitTermination(): Block until all tasks done
     * 
     * COMMON IMPLEMENTATIONS:
     * - ThreadPoolExecutor: Most flexible
     * - ForkJoinPool: For divide-and-conquer (used by parallelStream)
     * - ScheduledThreadPoolExecutor: For scheduled tasks
     * 
     * ========================================================================
     */
    
    public static void basicThreadPool() {
        System.out.println("\n=== SECTION 1: Basic Thread Pool ===\n");
        
        // Create a thread pool with 3 worker threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit 5 tasks to the pool
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                print("Executing task " + taskId);
                try {
                    Thread.sleep(1000);  // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                print("Task " + taskId + " completed");
            });
        }
        
        // Shutdown the pool
        executor.shutdown();  // No more tasks accepted
        
        try {
            // Wait for all tasks to complete (max 5 seconds)
            if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                print("All tasks completed!");
            } else {
                print("Timeout waiting for tasks");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 2: TYPES OF THREAD POOLS (INTERMEDIATE)
     * ========================================================================
     * 
     * 1. FIXED THREAD POOL
     *    newFixedThreadPool(n)
     *    - Exactly n threads
     *    - Best for: Known workload, stable thread count needed
     *    - Queue: Unbounded (can grow infinitely!)
     *    - Example: Web server with fixed worker threads
     * 
     * 2. SINGLE THREAD EXECUTOR
     *    newSingleThreadExecutor()
     *    - Only 1 thread
     *    - Best for: Sequential processing, single-threaded safety
     *    - Queue: Unbounded
     *    - Example: Event processing, database background updates
     * 
     * 3. CACHED THREAD POOL
     *    newCachedThreadPool()
     *    - Threads created as needed
     *    - Threads reused if idle < 60 seconds
     *    - Best for: Varying workload, short-lived tasks
     *    - Queue: Bounded (SynchronousQueue)
     *    - Example: Connection handlers, bursty workload
     * 
     * 4. SCHEDULED THREAD POOL
     *    newScheduledThreadPool(n)
     *    - n threads for scheduled/delayed tasks
     *    - Best for: Periodic tasks, delayed execution
     *    - Queue: DelayQueue
     *    - Example: Cleanup tasks, server pings, monitoring
     * 
     * 5. WORK STEALING POOL
     *    newWorkStealingPool()
     *    - One queue per thread (no contention!)
     *    - Best for: Heavy parallel computation
     *    - Advanced feature: Threads steal work from others
     *    - Example: Large array processing, recursive tasks
     * 
     * ========================================================================
     */
    
    public static void differentPoolTypes() {
        System.out.println("\n\n=== SECTION 2: Different Pool Types ===\n");
        
        // 1. Fixed Thread Pool
        System.out.println("--- Fixed Thread Pool (3 threads) ---");
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 5; i++) {
            final int id = i;
            fixedPool.execute(() -> {
                print("Fixed pool task " + id);
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            });
        }
        fixedPool.shutdown();
        try { fixedPool.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) {}
        
        // 2. Single Thread Executor
        System.out.println("\n--- Single Thread Executor ---");
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            singlePool.execute(() -> print("Single task " + id));
        }
        singlePool.shutdown();
        try { singlePool.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) {}
        
        // 3. Cached Thread Pool
        System.out.println("\n--- Cached Thread Pool ---");
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            cachedPool.execute(() -> {
                print("Cached task " + id);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            });
        }
        cachedPool.shutdown();
        try { cachedPool.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) {}
        
        // 4. Scheduled Thread Pool
        System.out.println("\n--- Scheduled Thread Pool ---");
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
        
        // Schedule task to run after 1 second
        scheduledPool.schedule(() -> print("Delayed task executed"), 1, TimeUnit.SECONDS);
        
        // Schedule task to run repeatedly every 500ms
        ScheduledFuture<?> repeating = scheduledPool.scheduleAtFixedRate(
            () -> print("Repeating task"),
            0,
            500,
            TimeUnit.MILLISECONDS
        );
        
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        
        repeating.cancel(false);  // Stop repeating
        scheduledPool.shutdown();
        try { scheduledPool.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) {}
    }
    
    
    /*
     * ========================================================================
     * SECTION 3: HOW THREAD POOLS WORK INTERNALLY (INTERMEDIATE)
     * ========================================================================
     * 
     * INTERNAL ARCHITECTURE:
     * ======================
     * 
     * User submits task
     *   |
     *   v
     * [Task Queue] (bounded or unbounded)
     *   |
     *   +---> [Worker Thread 1] -> executes task -> wait for next
     *   |
     *   +---> [Worker Thread 2] -> executing task...
     *   |
     *   +---> [Worker Thread 3] -> idle, waiting
     *   
     * WORKFLOW:
     * 1. Task submitted via execute() or submit()
     * 2. If thread available: execute immediately
     * 3. If all busy: task queued
     * 4. When thread finishes: picks next task from queue
     * 5. On shutdown(): wait for all tasks to complete
     * 
     * THREAD STATES:
     * ==============
     * 
     * RUNNING: Actively executing task
     * WAITING: Blocked waiting for lock/IO
     * AVAILABLE: Idle, waiting for task from queue
     * TERMINATED: Thread has exited
     * 
     * KEY CONCEPT: THREAD REUSE
     * ==========================
     * 
     * Key benefit of pools:
     * 
     * Without Pool:
     * Task 1: Create thread -> Execute -> Destroy (expensive!)
     * Task 2: Create thread -> Execute -> Destroy (expensive!)
     * Task 3: Create thread -> Execute -> Destroy (expensive!)
     * 
     * With Pool:
     * Task 1: Thread 1 ready -> Execute -> Back to waiting (cheap!)
     * Task 2: Thread 2 ready -> Execute -> Back to waiting (cheap!)
     * Task 3: Thread 1 idle -> Execute -> Back to waiting (cheap!)
     * 
     * Same thread executes multiple tasks!
     * No creation/destruction overhead!
     * 
     * ========================================================================
     */
    
    public static void demonstrateThreadReuse() {
        System.out.println("\n\n=== SECTION 3: Thread Reuse in Pools ===\n");
        
        ExecutorService pool = Executors.newFixedThreadPool(2);
        
        // 4 tasks, but only 2 threads
        // Each thread will execute 2 tasks
        for (int i = 1; i <= 4; i++) {
            final int taskId = i;
            pool.execute(() -> {
                String threadName = Thread.currentThread().getName();
                print("Task " + taskId + " started on " + threadName);
                try { Thread.sleep(800); } catch (InterruptedException e) {}
                print("Task " + taskId + " ended on " + threadName);
            });
        }
        
        pool.shutdown();
        try { pool.awaitTermination(10, TimeUnit.SECONDS); } catch (InterruptedException e) {}
        
        // Notice: Only 2 threads execute all 4 tasks!
        // Each thread gets reused!
    }
    
    
    /*
     * ========================================================================
     * SECTION 4: SUBMIT vs EXECUTE (INTERMEDIATE)
     * ========================================================================
     * 
     * DIFFERENCE:
     * ===========
     * 
     * execute():
     * - Takes Runnable only
     * - Returns void
     * - Can't get result
     * - Can't catch exceptions easily
     * 
     * submit():
     * - Takes Runnable or Callable
     * - Returns Future<T>
     * - Can get result
     * - Can catch exceptions
     * 
     * WHEN TO USE execute():
     * - No return value needed
     * - Fire and forget
     * 
     * WHEN TO USE submit():
     * - Need to get result
     * - Want to track completion
     * - Want to cancel task
     * - Need exception handling
     * 
     * FUTURE INTERFACE:
     * =================
     * 
     * Future<Integer> future = executor.submit(() -> 42);
     * 
     * // Wait for result (blocks until done)
     * Integer result = future.get();
     * 
     * // Wait up to 2 seconds (throws TimeoutException)
     * Integer result = future.get(2, TimeUnit.SECONDS);
     * 
     * // Check if done (non-blocking)
     * if (future.isDone()) {}
     * 
     * // Cancel task (only if not yet started)
     * future.cancel(false);
     * 
     * ========================================================================
     */
    
    public static void submitVsExecute() {
        System.out.println("\n\n=== SECTION 4: Submit vs Execute ===\n");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // execute() - Fire and forget
        executor.execute(() -> print("Fire and forget task"));
        
        // submit() - Get result
        Future<Integer> future = executor.submit(() -> {
            print("Computing result...");
            Thread.sleep(500);
            return 42;
        });
        
        try {
            // Get result (blocks if not ready)
            Integer result = future.get();
            print("Got result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            print("Error getting result: " + e.getMessage());
        }
        
        executor.shutdown();
        try { executor.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) {}
    }
    
    
    /*
     * ========================================================================
     * SECTION 5: QUEUE AND REJECTION POLICIES (ADVANCED)
     * ========================================================================
     * 
     * TASK QUEUE:
     * ===========
     * When all threads busy, new tasks go into queue.
     * 
     * Queue Types:
     * - Unbounded (LinkedBlockingQueue): Never rejects, but can exhaust memory
     * - Bounded (ArrayBlockingQueue): Rejects if full
     * - SynchronousQueue: No storage, hand-off only
     * 
     * REJECTION POLICY - What happens when queue is full?
     * ===================================================
     * 
     * 1. ABORT_POLICY (default)
     *    Throws RejectedExecutionException
     *    Task is lost!
     * 
     * 2. CALLER_RUNS
     *    Caller thread executes task
     *    Slows down caller but doesn't lose task
     * 
     * 3. DISCARD
     *    Silently discards task
     *    Task lost without error
     * 
     * 4. DISCARD_OLDEST
     *    Removes oldest task from queue, adds new one
     *    Oldest task lost
     * 
     * WHICH TO USE?
     * =============
     * - ABORT: Fail fast, catch problem immediately
     * - CALLER_RUNS: Back pressure, no task loss
     * - DISCARD: Real-time systems where old data is stale
     * - DISCARD_OLDEST: Similar to DISCARD but keeps new data
     * 
     * ========================================================================
     */
    
    public static void queueAndRejectionPolicy() {
        System.out.println("\n\n=== SECTION 5: Queue and Rejection Policies ===\n");
        
        // Create custom thread pool with bounded queue
        int coreThreads = 2;
        int maxThreads = 3;
        int queueCapacity = 2;
        
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueCapacity);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            coreThreads,
            maxThreads,
            60,
            TimeUnit.SECONDS,
            queue,
            new ThreadPoolExecutor.CallerRunsPolicy()  // Rejection policy
        );
        
        print("Queue capacity: " + queueCapacity);
        print("Max threads: " + maxThreads);
        
        // Submit more tasks than queue can handle
        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            try {
                executor.execute(() -> {
                    print("Task " + taskId + " executing");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    print("Task " + taskId + " done");
                });
            } catch (RejectedExecutionException e) {
                print("Task " + taskId + " REJECTED!");
            }
        }
        
        executor.shutdown();
        try { executor.awaitTermination(10, TimeUnit.SECONDS); } catch (InterruptedException e) {}
    }
    
    
    /*
     * ========================================================================
     * SECTION 6: LIFECYCLE MANAGEMENT (INTERMEDIATE)
     * ========================================================================
     * 
     * EXECUTOR LIFECYCLE:
     * ===================
     * 
     * Running -> Shutdown -> Terminated
     * 
     * METHODS:
     * 
     * 1. shutdown()
     *    - No new tasks accepted
     *    - Existing tasks continue
     *    - Returns immediately
     *    - Use when done submitting tasks
     * 
     * 2. shutdownNow()
     *    - No new tasks accepted
     *    - Interrupts running tasks
     *    - Cancels queued tasks (returns them)
     *    - Returns immediately
     *    - Use when need immediate stop
     * 
     * 3. awaitTermination(timeout, unit)
     *    - Blocks current thread
     *    - Waits for all tasks to finish
     *    - Returns true if completed, false if timeout
     *    - Use to wait for completion before continuing
     * 
     * 4. isShutdown()
     *    - Returns true if shutdown() called
     *    - Tasks may still be running
     * 
     * 5. isTerminated()
     *    - Returns true when all tasks done
     *    - Only true after shutdown() and all tasks complete
     * 
     * CORRECT USAGE PATTERN:
     * ======================
     * 
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * 
     * // Submit tasks
     * for (Task t : tasks) {
     *     executor.execute(t);
     * }
     * 
     * // Signal no more tasks
     * executor.shutdown();
     * 
     * // Wait for completion (optional timeout for safety)
     * if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
     *     // Timeout occurred, force stop if needed
     *     executor.shutdownNow();
     * }
     * 
     * ========================================================================
     */
    
    public static void lifecycleManagement() {
        System.out.println("\n\n=== SECTION 6: Lifecycle Management ===\n");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // Submit tasks
        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            executor.execute(() -> {
                print("Task " + taskId + " starting");
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                print("Task " + taskId + " finished");
            });
        }
        
        // Shutdown: no more tasks accepted, but existing ones continue
        executor.shutdown();
        print("Executor shutdown called");
        
        try {
            // Wait up to 5 seconds for completion
            if (executor.awaitTermination(5, TimeUnit.SECONDS)) {
                print("All tasks completed!");
            } else {
                print("Timeout! Some tasks still running");
                executor.shutdownNow();  // Force stop
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 7: REAL-WORLD EXAMPLE - WEB REQUEST HANDLER (ADVANCED)
     * ========================================================================
     * 
     * SCENARIO:
     * Web server receives 100 requests per second.
     * Each request takes 2 seconds to process.
     * 
     * Option 1: Create new thread per request
     * - Memory: 100 req/sec * 2 sec = 200 threads
     * - Memory usage: 200 * 1MB = 200MB just for threads!
     * - Context switching overhead kills performance
     * 
     * Option 2: Use thread pool with 50 threads
     * - Memory: 50 * 1MB = 50MB
     * - All 100 requests handled by 50 threads
     * - Requests queue up, processed as threads free
     * - System stable and predictable
     * 
     * ========================================================================
     */
    
    public static class WebRequestHandler {
        private ExecutorService executor;
        private int requestCounter = 0;
        
        public WebRequestHandler(int threadPoolSize) {
            executor = Executors.newFixedThreadPool(threadPoolSize);
        }
        
        // Simulate incoming web request
        public void handleRequest(String clientIp) {
            final int requestId = ++requestCounter;
            
            executor.execute(() -> {
                print("Request " + requestId + " from " + clientIp + " started");
                
                try {
                    // Simulate processing
                    Thread.sleep(500);
                    
                    print("Request " + requestId + " processed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    print("Request " + requestId + " interrupted");
                }
            });
        }
        
        public void shutdown() {
            executor.shutdown();
            try {
                executor.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }
    
    public static void webServerExample() {
        System.out.println("\n\n=== SECTION 7: Real-World Web Server Example ===\n");
        
        WebRequestHandler server = new WebRequestHandler(3);
        
        // Simulate 5 incoming requests
        for (int i = 0; i < 5; i++) {
            server.handleRequest("192.168.1." + (100 + i));
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
        
        server.shutdown();
    }
    
    
    /*
     * ========================================================================
     * SECTION 8: THREAD POOL SIZING (CRITICAL!)
     * ========================================================================
     * 
     * HOW MANY THREADS TO USE?
     * =========================
     * 
     * This is THE most important decision!
     * Wrong size = terrible performance
     * 
     * FOR CPU-BOUND TASKS (computation):
     * ===================================
     * Number of threads = Number of CPU cores
     * 
     * Example: 8-core CPU = pool size 8
     * 
     * Why? 
     * - Each thread runs on separate core
     * - More threads = context switching overhead
     * 
     * Code:
     * int poolSize = Runtime.getRuntime().availableProcessors();
     * ExecutorService executor = Executors.newFixedThreadPool(poolSize);
     * 
     * 
     * FOR IO-BOUND TASKS (network, disk, DB):
     * =========================================
     * Number of threads = 2 * Number of CPU cores (or more!)
     * 
     * Example: 8-core CPU = pool size 16-32
     * 
     * Why?
     * - Many threads waiting for IO (not using CPU)
     * - While one thread waits, others can work
     * - Need more threads to keep CPU busy
     * 
     * Code:
     * int poolSize = Runtime.getRuntime().availableProcessors() * 2;
     * ExecutorService executor = Executors.newFixedThreadPool(poolSize);
     * 
     * 
     * GENERAL FORMULA:
     * ================
     * 
     * poolSize = (desiredConcurrency / (1 - blockingCoefficient)) + 1
     * 
     * blockingCoefficient = estimated fraction of time task is blocked
     * Example: If task is blocked 80% of time:
     * poolSize = (8 / (1 - 0.8)) + 1 = 8 / 0.2 + 1 = 41
     * 
     * 
     * PRACTICAL GUIDELINES:
     * =====================
     * 
     * | Workload Type | Pool Size                     |
     * |---------------|-------------------------------|
     * | CPU-bound     | core_count                    |
     * | Light IO      | core_count * 2                |
     * | Heavy IO      | core_count * 4-8              |
     * | Network calls | 100-1000 (depends on latency) |
     * | Database      | core_count * 2-4              |
     * 
     * 
     * HOW TO TUNE:
     * ============
     * 1. Start with estimate
     * 2. Load test with real workload
     * 3. Monitor CPU and thread utilization
     * 4. Adjust based on metrics
     * 5. Repeat until optimal
     * 
     * ========================================================================
     */
    
    public static void threadPoolSizing() {
        System.out.println("\n\n=== SECTION 8: Thread Pool Sizing ===\n");
        
        int cores = Runtime.getRuntime().availableProcessors();
        print("Available CPU cores: " + cores);
        
        int cpuBoundPoolSize = cores;
        int ioBoundPoolSize = cores * 2;
        int heavyIOPoolSize = cores * 4;
        
        print("CPU-bound pool size: " + cpuBoundPoolSize);
        print("IO-bound pool size: " + ioBoundPoolSize);
        print("Heavy IO pool size: " + heavyIOPoolSize);
        
        // Example: CPU-bound task (calculation)
        System.out.println("\n--- CPU-Bound Task ---");
        ExecutorService cpuPool = Executors.newFixedThreadPool(cpuBoundPoolSize);
        
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 16; i++) {
            final int taskId = i;
            cpuPool.execute(() -> {
                long sum = 0;
                for (long j = 0; j < 1_000_000_000; j++) {
                    sum += j;
                }
                print("CPU task " + taskId + " computed: " + sum);
            });
        }
        
        cpuPool.shutdown();
        try { cpuPool.awaitTermination(30, TimeUnit.SECONDS); } catch (InterruptedException e) {}
        long duration = System.currentTimeMillis() - startTime;
        print("CPU task completed in " + duration + "ms");
    }
    
    
    /*
     * ========================================================================
     * SECTION 9: INVOKEALL AND INVOKEANY (ADVANCED)
     * ========================================================================
     * 
     * INVOKEALL: Submit multiple tasks, wait for ALL to complete
     * ===========================================================
     * 
     * List<Callable<Integer>> tasks = Arrays.asList(
     *     () -> 1,
     *     () -> 2,
     *     () -> 3
     * );
     * 
     * List<Future<Integer>> results = executor.invokeAll(tasks);
     * 
     * for (Future<Integer> future : results) {
     *     System.out.println(future.get());
     * }
     * 
     * Returns in order!
     * 
     * 
     * INVOKEANY: Submit multiple tasks, return result of FIRST to complete
     * ========================================================================
     * 
     * Integer firstResult = executor.invokeAny(tasks);  // BLOCKS
     * 
     * Gets result of whichever completes first!
     * Other tasks are cancelled.
     * 
     * USE CASES:
     * - Query multiple databases, use first response
     * - Try multiple algorithms, use fastest
     * - Redundant calls, use first successful
     * 
     * ========================================================================
     */
    
    public static void invokeAllAndInvokeAny() throws Exception {
        System.out.println("\n\n=== SECTION 9: InvokeAll and InvokeAny ===\n");
        
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Create 3 callable tasks
        List<Callable<Integer>> tasks = Arrays.asList(
            () -> {
                Thread.sleep(100);
                return 1;
            },
            () -> {
                Thread.sleep(50);
                return 2;
            },
            () -> {
                Thread.sleep(150);
                return 3;
            }
        );
        
        // invokeAll: Wait for all
        System.out.println("--- InvokeAll: Wait for all ---");
        List<Future<Integer>> allResults = executor.invokeAll(tasks);
        for (int i = 0; i < allResults.size(); i++) {
            print("Result " + i + ": " + allResults.get(i).get());
        }
        
        // invokeAny: Get first result
        System.out.println("\n--- InvokeAny: First result ---");
        Integer firstResult = executor.invokeAny(tasks);
        print("First result: " + firstResult);
        
        executor.shutdown();
        try { executor.awaitTermination(5, TimeUnit.SECONDS); } catch (InterruptedException e) {}
    }
    
    
    /*
     * ========================================================================
     * SECTION 10: COMMON MISTAKES & CONFUSION
     * ========================================================================
     * 
     * MISTAKE 1: Setting pool size too small
     * =======================================
     * WRONG:
     * ExecutorService executor = Executors.newFixedThreadPool(1);
     * 
     * Then submitting 1000 tasks!
     * Result: Sequential execution, huge queue, poor performance
     * 
     * FIX: Match pool size to workload characteristics
     * 
     * 
     * MISTAKE 2: Forgetting to shutdown
     * ==================================
     * WRONG:
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * executor.execute(task);
     * // Forgot to shutdown!
     * 
     * Result: Threads keep running, application doesn't exit!
     * Threads are daemon? No! They're user threads!
     * 
     * RIGHT:
     * try {
     *     // use executor
     * } finally {
     *     executor.shutdown();
     * }
     * 
     * 
     * MISTAKE 3: Unbounded queue growth
     * ==================================
     * WRONG:
     * ExecutorService executor = Executors.newFixedThreadPool(10);
     * for (Task t : MILLIONS_OF_TASKS) {
     *     executor.execute(t);  // Queue grows to millions!
     * }
     * 
     * Result: Out of memory! Queue holds all tasks!
     * 
     * FIX: Use bounded queue with rejection policy
     * Or: Process tasks in batches
     * 
     * 
     * MISTAKE 4: Not handling exceptions in tasks
     * =============================================
     * WRONG:
     * executor.execute(() -> {
     *     risky_operation();  // Exception not caught!
     * });
     * 
     * Result: Exception silently lost! Task is stuck!
     * 
     * RIGHT:
     * executor.execute(() -> {
     *     try {
     *         risky_operation();
     *     } catch (Exception e) {
     *         log_error(e);  // Log it!
     *     }
     * });
     * 
     * 
     * MISTAKE 5: Sharing single executor for different task types
     * =============================================================
     * WRONG:
     * ExecutorService shared = Executors.newFixedThreadPool(10);
     * shared.execute(shortTask);
     * shared.execute(longTask);
     * shared.execute(cpuTask);
     * 
     * Problem: All compete for same resource
     * Fast tasks delayed by slow ones
     * Impossible to tune pool size
     * 
     * RIGHT: Create separate executors
     * ExecutorService shortTasks = newFixedThreadPool(5);
     * ExecutorService longTasks = newFixedThreadPool(2);
     * 
     * 
     * CONFUSION 1: "Do shutdown threads immediately die?"
     * ====================================================
     * NO! shutdown() just stops accepting new tasks.
     * Existing tasks continue running.
     * 
     * shutdownNow() interrupts threads.
     * But interruption doesn't kill threads!
     * Task must check isInterrupted() to actually stop.
     * 
     * 
     * CONFUSION 2: "Can I reuse executor after shutdown?"
     * ====================================================
     * NO! Once shutdown, can't use it.
     * Create a new one if needed.
     * 
     * 
     * CONFUSION 3: "What's the difference between pool size and queue?"
     * ==================================================================
     * Pool size = Maximum concurrent tasks
     * Queue = Buffer for pending tasks
     * 
     * Example: Pool size 10, queue 100
     * - 10 tasks running immediately
     * - 11th task waits in queue
     * - 101st task rejected (queue full)
     * 
     * ========================================================================
     */
    
    public static void commonMistakes() {
        System.out.println("\n\n=== SECTION 10: Common Mistakes & Solutions ===\n");
        
        // MISTAKE: No exception handling
        System.out.println("--- Exception Handling in Tasks ---");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.execute(() -> {
            try {
                print("Task starting");
                int result = 1 / 0;  // Will throw exception
            } catch (Exception e) {
                print("Caught exception: " + e.getMessage());
            }
        });
        
        executor.shutdown();
        try { executor.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) {}
    }
    
    
    /*
     * ========================================================================
     * SECTION 11: BEST PRACTICES CHECKLIST
     * ========================================================================
     * 
     * [✓] Always shutdown executor when done
     * [✓] Use try-finally or try-with-resources for cleanup
     * [✓] Size pool based on workload type (CPU vs IO)
     * [✓] Handle exceptions in task code
     * [✓] Use submit() if you need result, execute() if not
     * [✓] Use appropriate rejection policy
     * [✓] Monitor queue size (don't let it grow unbounded)
     * [✓] Use separate executors for different task types
     * [✓] Log slow/stuck tasks
     * [✓] Test under realistic load
     * [✓] Use CompletableFuture for complex async patterns
     * [✓] Don't create one-time use executors
     * [✓] Use awaitTermination() to wait for completion
     * [✓] Consider ForkJoinPool for divide-and-conquer
     * [✓] Use parallelStream() for simple parallel processing
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 12: WHEN TO USE THREAD POOL
     * ========================================================================
     * 
     * USE THREAD POOL WHEN:
     * =====================
     * - Many short tasks (> 10 tasks)
     * - Tasks take similar time
     * - Variable load (burst of requests)
     * - Need concurrency control
     * - Server application
     * - Batch processing
     * - Parallel computation
     * 
     * DON'T USE THREAD POOL WHEN:
     * ============================
     * - Only 1-2 tasks ever
     * - Need ultra-low latency (creation overhead acceptable)
     * - Task needs very high priority
     * - Real-time hard deadline required
     * - Simple sequential processing
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 13: ADVANCED - COMPLETABLEFUTURE (EXPERT)
     * ========================================================================
     * 
     * Modern approach to async operations.
     * More powerful than ExecutorService alone.
     * 
     * Example: Chain operations
     * 
     * CompletableFuture.supplyAsync(() -> getData(), executor)
     *     .thenApply(data -> processData(data))
     *     .thenAccept(result -> displayResult(result))
     *     .exceptionally(ex -> {
     *         log.error("Error", ex);
     *         return null;
     *     });
     * 
     * Benefits:
     * - Composable async operations
     * - Exception handling built-in
     * - Multiple operations in pipeline
     * - No callback hell
     * 
     * ========================================================================
     */
    
    public static void completableFutureExample() {
        System.out.println("\n\n=== SECTION 13: CompletableFuture Example ===\n");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        CompletableFuture.supplyAsync(() -> {
            print("Getting data...");
            return "Data";
        }, executor)
        .thenApply(data -> {
            print("Processing: " + data);
            return data.toUpperCase();
        })
        .thenAccept(result -> {
            print("Result: " + result);
        })
        .exceptionally(ex -> {
            print("Error: " + ex.getMessage());
            return null;
        });
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        executor.shutdown();
        try { executor.awaitTermination(3, TimeUnit.SECONDS); } catch (InterruptedException e) {}
    }
    
    
    /*
     * ========================================================================
     * SECTION 14: PERFORMANCE TIPS
     * ========================================================================
     * 
     * TIP 1: Monitor thread pool metrics
     * ==================================
     * ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;
     * 
     * pool.getActiveCount()    // Currently executing
     * pool.getPoolSize()       // Current threads
     * pool.getTaskCount()      // Total tasks submitted
     * pool.getCompletedTaskCount()  // Completed count
     * 
     * 
     * TIP 2: Use ForkJoinPool for recursive tasks
     * =============================================
     * RecursiveTask<Long> task = new RecursiveTask<Long>() {
     *     protected Long compute() {
     *         if (too_small) {
     *             return compute_directly();
     *         } else {
     *             split into subtasks;
     *             recursively compute;
     *             return combine results;
     *         }
     *     }
     * };
     * 
     * 
     * TIP 3: Use parallelStream() for simple cases
     * =============================================
     * No need to manually manage executor!
     * 
     * list.parallelStream()
     *     .filter(...)
     *     .map(...)
     *     .collect(Collectors.toList());
     * 
     * JVM handles threading automatically!
     * 
     * ========================================================================
     */
    
    
    public static void main(String[] args) throws Exception {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("THREAD POOL - COMPREHENSIVE GUIDE");
        System.out.println("=".repeat(70));
        
        basicThreadPool();
        differentPoolTypes();
        demonstrateThreadReuse();
        submitVsExecute();
        queueAndRejectionPolicy();
        lifecycleManagement();
        webServerExample();
        threadPoolSizing();
        invokeAllAndInvokeAny();
        commonMistakes();
        completableFutureExample();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Thread Pool Guide Completed!");
        System.out.println("=".repeat(70) + "\n");
    }
}
