package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * STEP 5: THREAD POOLS & EXECUTOR FRAMEWORK
 * 
 * PROBLEM: Creating new threads for every task is expensive:
 * - Thread creation consumes memory and CPU
 * - Need to manage thread lifecycle
 * - Too many threads cause performance degradation
 * 
 * SOLUTION: Thread Pools
 * - Reuse existing threads
 * - Queue pending tasks
 * - Automatic thread management
 * - Better resource utilization
 * 
 * The Executor Framework handles all of this!
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// ==================== SIMPLE TASK ====================
class SimpleTask implements Runnable {
    private String taskName;
    private int duration;
    
    public SimpleTask(String name, int duration) {
        this.taskName = name;
        this.duration = duration;
    }
    
    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] " + taskName + " started");
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[" + Thread.currentThread().getName() + "] " + taskName + " completed");
    }
}

// ==================== TASK WITH RETURN VALUE ====================
class CalculationTask implements Callable<Integer> {
    private int taskId;
    private int num1, num2;
    
    public CalculationTask(int id, int n1, int n2) {
        this.taskId = id;
        this.num1 = n1;
        this.num2 = n2;
    }
    
    @Override
    public Integer call() throws Exception {
        System.out.println("[" + Thread.currentThread().getName() + "] Task-" + taskId + " calculating " + num1 + " + " + num2);
        Thread.sleep(1000);  // Simulate work
        int result = num1 + num2;
        System.out.println("[" + Thread.currentThread().getName() + "] Task-" + taskId + " result = " + result);
        return result;
    }
}

// ==================== LONG RUNNING TASK ====================
class LongRunningTask implements Runnable {
    private String taskName;
    private AtomicInteger counter;
    
    public LongRunningTask(String name, AtomicInteger counter) {
        this.taskName = name;
        this.counter = counter;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println(taskName + " was interrupted!");
                break;
            }
            System.out.println(taskName + " - Iteration: " + (i + 1));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(taskName + " interrupted during sleep");
                Thread.currentThread().interrupt();
                break;
            }
        }
        counter.incrementAndGet();
    }
}

public class Step5_ThreadPoolsAndExecutor {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("\n========== THREAD POOLS & EXECUTOR FRAMEWORK TUTORIAL ==========\n");
        
        // ========== 1. FIXED THREAD POOL ==========
        System.out.println("--- 1. FIXED THREAD POOL (Fixed number of threads) ---");
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);  // Pool with 3 threads
        
        System.out.println("Submitting 8 tasks to pool with 3 threads...");
        for (int i = 1; i <= 8; i++) {
            fixedPool.execute(new SimpleTask("Task-" + i, 1000));
        }
        
        // Shutdown: No new tasks accepted, wait for running tasks to complete
        fixedPool.shutdown();
        
        // Wait for completion
        if (!fixedPool.awaitTermination(15, TimeUnit.SECONDS)) {
            System.out.println("Pool didn't terminate in time, forcing shutdown");
            fixedPool.shutdownNow();
        }
        
        System.out.println("Fixed pool completed\n");
        
        // ========== 2. CACHED THREAD POOL ==========
        System.out.println("--- 2. CACHED THREAD POOL (Creates threads as needed, reuses idle) ---");
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        
        System.out.println("Submitting 5 short tasks to cached pool...");
        for (int i = 1; i <= 5; i++) {
            cachedPool.execute(new SimpleTask("CachedTask-" + i, 500));
        }
        
        cachedPool.shutdown();
        if (!cachedPool.awaitTermination(10, TimeUnit.SECONDS)) {
            cachedPool.shutdownNow();
        }
        
        System.out.println("Cached pool completed\n");
        
        // ========== 3. SINGLE THREAD EXECUTOR ==========
        System.out.println("--- 3. SINGLE THREAD EXECUTOR (Single thread, sequential execution) ---");
        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
        
        System.out.println("Submitting 3 tasks to single thread executor...");
        for (int i = 1; i <= 3; i++) {
            singleExecutor.execute(new SimpleTask("SingleTask-" + i, 800));
        }
        
        singleExecutor.shutdown();
        if (!singleExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
            singleExecutor.shutdownNow();
        }
        
        System.out.println("Single executor completed\n");
        
        // ========== 4. SCHEDULED THREAD POOL ==========
        System.out.println("--- 4. SCHEDULED THREAD POOL (Execute tasks with delay/periodically) ---");
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
        
        // Schedule task to run after 2 seconds delay
        scheduledPool.schedule(
            new SimpleTask("DelayedTask", 100),
            2,
            TimeUnit.SECONDS
        );
        
        // Schedule task to run periodically (initial delay 1s, repeat every 1s)
        ScheduledFuture<?> periodicTask = scheduledPool.scheduleAtFixedRate(
            () -> System.out.println("[" + Thread.currentThread().getName() + "] Periodic task executed"),
            1,
            1,
            TimeUnit.SECONDS
        );
        
        Thread.sleep(5000);  // Let it run 5 times
        periodicTask.cancel(false);  // Stop periodic execution
        
        scheduledPool.shutdown();
        if (!scheduledPool.awaitTermination(5, TimeUnit.SECONDS)) {
            scheduledPool.shutdownNow();
        }
        
        System.out.println("Scheduled pool completed\n");
        
        // ========== 5. CALLABLE & FUTURE (Get return values from tasks) ==========
        System.out.println("--- 5. CALLABLE & FUTURE (Tasks that return results) ---");
        ExecutorService callablePool = Executors.newFixedThreadPool(2);
        
        System.out.println("Submitting 3 calculation tasks...");
        Future<Integer> future1 = callablePool.submit(new CalculationTask(1, 10, 20));
        Future<Integer> future2 = callablePool.submit(new CalculationTask(2, 30, 40));
        Future<Integer> future3 = callablePool.submit(new CalculationTask(3, 50, 60));
        
        int sum = 0;
        try {
            // Get results (blocks if not ready)
            int result1 = future1.get();  // Waits if needed
            int result2 = future2.get();
            int result3 = future3.get();
            sum = result1 + result2 + result3;
            
            System.out.println("All results: " + result1 + ", " + result2 + ", " + result3);
            System.out.println("Sum of all results: " + sum);
        } catch (ExecutionException e) {
            System.out.println("Task threw exception: " + e.getCause());
        }
        
        callablePool.shutdown();
        if (!callablePool.awaitTermination(10, TimeUnit.SECONDS)) {
            callablePool.shutdownNow();
        }
        
        System.out.println();
        
        // ========== 6. INVOKEALL (Submit multiple tasks, wait for all) ==========
        System.out.println("--- 6. INVOKEALL (Submit all tasks, get all results) ---");
        ExecutorService invokePool = Executors.newFixedThreadPool(2);
        
        System.out.println("Submitting 3 calculation tasks with invokeAll...");
        java.util.List<Callable<Integer>> tasks = new java.util.ArrayList<>();
        tasks.add(new CalculationTask(10, 5, 15));
        tasks.add(new CalculationTask(11, 25, 35));
        tasks.add(new CalculationTask(12, 45, 55));
        
        java.util.List<Future<Integer>> results = invokePool.invokeAll(tasks);
        
        System.out.println("Got " + results.size() + " results:");
        for (Future<Integer> future : results) {
            System.out.println("Result: " + future.get());
        }
        
        invokePool.shutdown();
        if (!invokePool.awaitTermination(10, TimeUnit.SECONDS)) {
            invokePool.shutdownNow();
        }
        
        System.out.println();
        
        // ========== 7. SHUTDOWN VS SHUTDOWNNOW ==========
        System.out.println("--- 7. SHUTDOWN STRATEGIES ---");
        ExecutorService strategyPool = Executors.newFixedThreadPool(2);
        
        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 1; i <= 3; i++) {
            strategyPool.execute(new LongRunningTask("Task-" + i, counter));
        }
        
        System.out.println("Calling shutdown()...");
        strategyPool.shutdown();  // No new tasks, wait for running tasks
        
        if (!strategyPool.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("Calling shutdownNow()...");
            java.util.List<Runnable> pending = strategyPool.shutdownNow();  // Interrupt running tasks
            System.out.println("Pending tasks: " + pending.size());
        }
        
        System.out.println("Completed tasks: " + counter.get());
        
        System.out.println();
        System.out.println("--- SUMMARY ---");
        System.out.println("newFixedThreadPool()      : Fixed number of threads, good for known workload");
        System.out.println("newCachedThreadPool()     : Unbounded threads, good for many short tasks");
        System.out.println("newSingleThreadExecutor() : Single thread, ensures sequential execution");
        System.out.println("newScheduledThreadPool()  : Scheduled/periodic task execution");
        System.out.println("Callable                  : Tasks that return values");
        System.out.println("Future                    : Represents result of async computation");
        System.out.println("shutdown()                : Graceful shutdown, wait for tasks");
        System.out.println("shutdownNow()             : Immediate shutdown, interrupt tasks");
    }
}

