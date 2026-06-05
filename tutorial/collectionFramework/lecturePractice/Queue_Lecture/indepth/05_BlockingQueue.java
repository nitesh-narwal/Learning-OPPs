package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.concurrent.*;
import java.util.*;

/**
 * ============================================================
 *      BLOCKINGQUEUE - THE THREAD-SAFE QUEUE 🔒🧵
 * ============================================================
 *
 * Bhai, BlockingQueue is GAME CHANGER for multi-threaded applications!
 *
 * Normal Queue problem:
 * - Thread 1: queue.offer(item) } 
 * - Thread 2: queue.poll()      } → RACE CONDITION! 💥
 * - Need explicit synchronization (locks, volatile, atomic)
 *
 * BlockingQueue solution:
 * - Thread-safe by design (internal synchronization)
 * - BLOCKS when queue is full (put operation)
 * - BLOCKS when queue is empty (take operation)
 * - No race conditions, no deadlocks (if used correctly)
 *
 * Real-world analogy:
 * - Restaurant kitchen: Waiter gives order (producer), chef takes order (consumer)
 * - If too many orders: Waiter WAITS (blocking put)
 * - If no orders: Chef WAITS (blocking take)
 *
 * ============================================================
 *  PRODUCER-CONSUMER PATTERN
 * ============================================================
 *
 *  Without BlockingQueue:                With BlockingQueue:
 *  
 *  synchronized(lock) {                  queue.put(item)
 *    while (queue.isFull()) {            // Automatically blocks!
 *      lock.wait();                      // No manual synchronization
 *    }
 *    queue.add(item);
 *    lock.notifyAll();
 *  }
 *
 * ============================================================
 *  BLOCKINGQUEUE HIERARCHY
 * ============================================================
 *
 *                  BlockingQueue (interface)
 *                         |
 *       +-----------------+------------------+
 *       |                 |                  |
 *  ArrayBlockingQueue  LinkedBlockingQueue  PriorityBlockingQueue
 *  (bounded)           (optionally bounded) (unbounded, priority)
 *       |
 *  SynchronousQueue    DelayQueue
 *  (no capacity!)      (delayed elements)
 *
 * ============================================================
 *  BLOCKINGQUEUE METHODS (4 Types!)
 * ============================================================
 *
 * Type          | Throws Exception | Special Value | Blocks     | Times Out
 * --------------|------------------|---------------|------------|------------------
 * Insert        | add(e)           | offer(e)      | put(e)     | offer(e, time, unit)
 * Remove        | remove()         | poll()        | take()     | poll(time, unit)
 * Examine       | element()        | peek()        | -          | -
 *
 * Industry tip: Use put/take for producer-consumer pattern!
 *
 * ============================================================
 *  IMPLEMENTATION COMPARISON
 * ============================================================
 *
 * ArrayBlockingQueue:
 * ✅ Bounded capacity (prevents memory overflow)
 * ✅ Fair/unfair mode (FIFO ordering optional)
 * ✅ Backed by array (cache-friendly)
 * ❌ Fixed size (no resizing)
 * Use: Fixed-size buffer, rate limiting
 *
 * LinkedBlockingQueue:
 * ✅ Optionally bounded (can be unbounded)
 * ✅ Higher throughput (separate locks for put/take)
 * ✅ Dynamic size
 * ❌ More memory overhead (node objects)
 * Use: General-purpose producer-consumer (DEFAULT CHOICE)
 *
 * PriorityBlockingQueue:
 * ✅ Unbounded
 * ✅ Priority ordering (not FIFO)
 * ❌ Slower than others (heap operations)
 * Use: Task scheduling with priorities
 *
 * SynchronousQueue:
 * ✅ Zero capacity (direct handoff)
 * ✅ Each insert waits for remove
 * Use: Thread handoff, CachedThreadPool uses this
 *
 * DelayQueue:
 * ✅ Elements available only after delay
 * Use: Scheduled tasks, caching with TTL
 *
 * ============================================================
 *  INDUSTRY USE CASES
 * ============================================================
 *
 * 1. THREAD POOLS (Executor framework)
 *    → ThreadPoolExecutor uses BlockingQueue internally
 *    → Tasks submitted to queue, worker threads take from queue
 *
 * 2. PRODUCER-CONSUMER PATTERN
 *    → Multiple producers, multiple consumers
 *    → Decoupling production from consumption
 *
 * 3. RATE LIMITING
 *    → Bounded queue prevents overwhelming the system
 *
 * 4. MESSAGE QUEUES
 *    → Microservices communication
 *    → Order processing systems
 *
 * 5. EVENT-DRIVEN SYSTEMS
 *    → Event handlers consume events from queue
 *
 * 6. BATCH PROCESSING
 *    → Collect items in queue, process in batches
 *
 */
class BlockingQueueExamples {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== BLOCKINGQUEUE - COMPLETE GUIDE =====\n");

        // ============================================================
        // DEMO 1: ArrayBlockingQueue Basics
        // ============================================================

        System.out.println("===== DEMO 1: ArrayBlockingQueue Basics =====\n");

        // Bounded queue with capacity 3
        java.util.concurrent.BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        // Add elements
        queue.put("Item 1");
        queue.put("Item 2");
        queue.put("Item 3");
        System.out.println("Queue (full): " + queue);

        // Try adding when full (using offer - non-blocking)
        boolean added = queue.offer("Item 4");
        System.out.println("Offer Item 4 (queue full): " + added); // false

        // Try adding with timeout
        added = queue.offer("Item 4", 1, TimeUnit.SECONDS);
        System.out.println("Offer Item 4 (1s timeout): " + added); // false

        // Remove elements
        String item = queue.take(); // Blocking take
        System.out.println("\nTook: " + item);
        System.out.println("Queue now: " + queue);

        // ============================================================
        // DEMO 2: Simple Producer-Consumer
        // ============================================================

        System.out.println("\n===== DEMO 2: Simple Producer-Consumer =====\n");

        java.util.concurrent.BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>(5);

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    sharedQueue.put(i);
                    System.out.println("Produced: " + i + " | Queue size: " + sharedQueue.size());
                    Thread.sleep(100); // Simulate production time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    Integer value = sharedQueue.take();
                    System.out.println("  Consumed: " + value + " | Queue size: " + sharedQueue.size());
                    Thread.sleep(200); // Simulate consumption time (slower than producer)
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        // ============================================================
        // DEMO 3: Multiple Producers & Consumers
        // ============================================================

        System.out.println("\n===== DEMO 3: Multiple Producers & Consumers =====\n");

        java.util.concurrent.BlockingQueue<BQTask> taskQueue = new LinkedBlockingQueue<>(10);
        int numProducers = 2;
        int numConsumers = 3;

        // Create producers
        List<Thread> producers = new ArrayList<>();
        for (int i = 0; i < numProducers; i++) {
            int producerId = i + 1;
            Thread p = new Thread(() -> {
                try {
                    for (int j = 1; j <= 5; j++) {
                        BQTask task = new BQTask("P" + producerId + "-Task" + j);
                        taskQueue.put(task);
                        System.out.println("Producer-" + producerId + " produced: " + task);
                        Thread.sleep(150);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            producers.add(p);
            p.start();
        }

        // Create consumers
        List<Thread> consumers = new ArrayList<>();
        for (int i = 0; i < numConsumers; i++) {
            int consumerId = i + 1;
            Thread c = new Thread(() -> {
                try {
                    while (true) {
                        BQTask task = taskQueue.poll(2, TimeUnit.SECONDS);
                        if (task == null) break; // No more tasks
                        System.out.println("  Consumer-" + consumerId + " consumed: " + task);
                        Thread.sleep(300); // Simulate processing
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            consumers.add(c);
            c.start();
        }

        // Wait for all threads
        for (Thread p : producers) p.join();
        for (Thread c : consumers) c.join();

        // ============================================================
        // DEMO 4: PriorityBlockingQueue
        // ============================================================

        System.out.println("\n===== DEMO 4: PriorityBlockingQueue =====\n");

        java.util.concurrent.BlockingQueue<BQPriorityTask> priorityQueue = 
            new PriorityBlockingQueue<>();

        // Add tasks with different priorities
        priorityQueue.put(new BQPriorityTask("Low priority task", 3));
        priorityQueue.put(new BQPriorityTask("Critical bug fix", 1));
        priorityQueue.put(new BQPriorityTask("Medium priority", 2));
        priorityQueue.put(new BQPriorityTask("Another critical", 1));

        System.out.println("Tasks processed by priority:");
        while (!priorityQueue.isEmpty()) {
            BQPriorityTask task = priorityQueue.take();
            System.out.println("  " + task);
        }

        // ============================================================
        // DEMO 5: SynchronousQueue (Zero Capacity)
        // ============================================================

        System.out.println("\n===== DEMO 5: SynchronousQueue (Direct Handoff) =====\n");

        java.util.concurrent.BlockingQueue<String> syncQueue = new SynchronousQueue<>();

        Thread sender = new Thread(() -> {
            try {
                String[] messages = {"Hello", "World", "From", "SynchronousQueue"};
                for (String msg : messages) {
                    System.out.println("Sender trying to send: " + msg);
                    syncQueue.put(msg); // Blocks until someone receives!
                    System.out.println("Sender sent: " + msg);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread receiver = new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    Thread.sleep(500); // Delay to show blocking behavior
                    String msg = syncQueue.take();
                    System.out.println("  Receiver received: " + msg);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        sender.start();
        receiver.start();
        sender.join();
        receiver.join();

        // ============================================================
        // DEMO 6: DelayQueue (Scheduled Tasks)
        // ============================================================

        System.out.println("\n===== DEMO 6: DelayQueue (Time-based) =====\n");

        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        long now = System.currentTimeMillis();
        delayQueue.put(new DelayedTask("Task 1", now + 1000)); // 1 second delay
        delayQueue.put(new DelayedTask("Task 2", now + 500));  // 500ms delay
        delayQueue.put(new DelayedTask("Task 3", now + 1500)); // 1.5 second delay

        System.out.println("Taking tasks (will wait for their delay):");
        while (!delayQueue.isEmpty()) {
            DelayedTask task = delayQueue.take(); // Blocks until delay expires!
            System.out.println("  Took: " + task);
        }

        // ============================================================
        // DEMO 7: Real-World - Thread Pool Simulation
        // ============================================================

        System.out.println("\n===== DEMO 7: Simple Thread Pool Simulation =====\n");

        SimpleThreadPool threadPool = new SimpleThreadPool(3, 10);

        // Submit 15 tasks
        for (int i = 1; i <= 15; i++) {
            int taskId = i;
            threadPool.submit(() -> {
                System.out.println("Executing task " + taskId + 
                    " on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        threadPool.shutdown();
        System.out.println("Thread pool shut down.");

        // ============================================================
        // DEMO 8: Performance Comparison
        // ============================================================

        System.out.println("\n===== DEMO 8: Performance Comparison =====\n");

        int iterations = 100_000;

        // ArrayBlockingQueue
        long start = System.nanoTime();
        java.util.concurrent.BlockingQueue<Integer> abq = new ArrayBlockingQueue<>(iterations);
        for (int i = 0; i < iterations; i++) {
            abq.offer(i);
        }
        while (!abq.isEmpty()) {
            abq.poll();
        }
        long abqTime = System.nanoTime() - start;

        // LinkedBlockingQueue
        start = System.nanoTime();
        java.util.concurrent.BlockingQueue<Integer> lbq = new LinkedBlockingQueue<>();
        for (int i = 0; i < iterations; i++) {
            lbq.offer(i);
        }
        while (!lbq.isEmpty()) {
            lbq.poll();
        }
        long lbqTime = System.nanoTime() - start;

        System.out.println("Single-threaded performance (" + iterations + " ops):");
        System.out.println("  ArrayBlockingQueue:  " + abqTime / 1_000_000 + " ms");
        System.out.println("  LinkedBlockingQueue: " + lbqTime / 1_000_000 + " ms");

        System.out.println("\n===== ALL BLOCKINGQUEUE DEMOS COMPLETE =====");
    }
}

/**
 * Simple task class for BlockingQueue demo
 */
class BQTask {
    String name;

    public BQTask(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

/**
 * Task with priority for PriorityQueue
 */
class BQPriorityTask implements Comparable<BQPriorityTask> {
    String description;
    int priority; // Lower number = higher priority

    public BQPriorityTask(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    @Override
    public int compareTo(BQPriorityTask other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return "[Priority " + priority + "] " + description;
    }
}

/**
 * Delayed task for DelayQueue
 */
class DelayedTask implements Delayed {
    String name;
    long executeTime; // Milliseconds since epoch

    public DelayedTask(String name, long executeTime) {
        this.name = name;
        this.executeTime = executeTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = executeTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        return Long.compare(this.executeTime, ((DelayedTask) other).executeTime);
    }

    @Override
    public String toString() {
        return name;
    }
}

/**
 * Simple Thread Pool implementation using BlockingQueue
 * (Real production code should use ExecutorService)
 */
class SimpleThreadPool {
    private final java.util.concurrent.BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workers;
    private volatile boolean isShutdown = false;

    public SimpleThreadPool(int numThreads, int queueCapacity) {
        taskQueue = new LinkedBlockingQueue<>(queueCapacity);
        workers = new ArrayList<>();

        // Create worker threads
        for (int i = 0; i < numThreads; i++) {
            WorkerThread worker = new WorkerThread("Worker-" + (i + 1));
            workers.add(worker);
            worker.start();
        }
    }

    public void submit(Runnable task) throws InterruptedException {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shutdown");
        }
        taskQueue.put(task); // Blocks if queue is full
    }

    public void shutdown() throws InterruptedException {
        isShutdown = true;
        
        // Add poison pills to stop workers
        for (int i = 0; i < workers.size(); i++) {
            taskQueue.put(() -> {}); // Empty task signals shutdown
        }

        // Wait for all workers to finish
        for (WorkerThread worker : workers) {
            worker.join();
        }
    }

    /**
     * Worker thread that takes tasks from queue and executes them
     */
    private class WorkerThread extends Thread {
        public WorkerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!isShutdown) {
                try {
                    Runnable task = taskQueue.take(); // Blocks until task available
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}

/*
 * ============================================================
 *  KEY TAKEAWAYS
 * ============================================================
 *
 *  ✅ BlockingQueue = Thread-safe queue with blocking operations
 *  ✅ put() blocks when full, take() blocks when empty
 *  ✅ Perfect for producer-consumer pattern
 *  ✅ No manual synchronization needed!
 *  ✅ ArrayBlockingQueue: bounded, fair/unfair
 *  ✅ LinkedBlockingQueue: optionally bounded, higher throughput
 *  ✅ PriorityBlockingQueue: unbounded, priority ordering
 *  ✅ SynchronousQueue: zero capacity, direct handoff
 *  ✅ DelayQueue: elements available after delay
 *
 * ============================================================
 *  INDUSTRY BEST PRACTICES
 * ============================================================
 *
 *  1. DEFAULT CHOICE: LinkedBlockingQueue
 *     → Good throughput, flexible capacity
 *
 *  2. Use bounded queues to prevent memory exhaustion
 *     → new LinkedBlockingQueue<>(1000)
 *
 *  3. Handle InterruptedException properly
 *     → Thread.currentThread().interrupt() to restore flag
 *
 *  4. Use timeout versions for critical systems
 *     → offer(e, timeout, unit) instead of put(e)
 *     → poll(timeout, unit) instead of take()
 *
 *  5. Don't use BlockingQueue for single-threaded code
 *     → Use ArrayDeque instead (faster, no synchronization overhead)
 *
 *  6. ExecutorService internally uses BlockingQueue
 *     → ThreadPoolExecutor uses it for task queue
 *
 * ============================================================
 *  COMMON INTERVIEW QUESTIONS
 * ============================================================
 *
 *  Q: What is BlockingQueue?
 *  A: Thread-safe queue that blocks on put when full and take when empty
 *
 *  Q: Difference between put() and offer()?
 *  A: put() blocks until space available
 *     offer() returns false immediately if full
 *     offer(timeout) waits for specified time
 *
 *  Q: ArrayBlockingQueue vs LinkedBlockingQueue?
 *  A: Array: fixed size, single lock (lower throughput)
 *     Linked: dynamic size, separate locks for put/take (higher throughput)
 *
 *  Q: What is SynchronousQueue?
 *  A: Queue with zero capacity. Each insert must wait for remove.
 *     Used in CachedThreadPool for direct thread handoff.
 *
 *  Q: How does ThreadPoolExecutor use BlockingQueue?
 *  A: Tasks submitted to executor go to BlockingQueue.
 *     Worker threads take tasks from queue and execute them.
 *
 *  Q: What is fairness in ArrayBlockingQueue?
 *  A: new ArrayBlockingQueue<>(10, true) → FIFO order guaranteed
 *     false (default) → no ordering guarantee (better performance)
 *
 * ============================================================
 *  WHEN TO USE WHICH?
 * ============================================================
 *
 *  ArrayBlockingQueue:
 *  → Fixed-size buffer needed
 *  → Fairness guarantee required
 *  → Memory-constrained environments
 *
 *  LinkedBlockingQueue:
 *  → General producer-consumer (DEFAULT)
 *  → Need higher throughput
 *  → Dynamic sizing needed
 *
 *  PriorityBlockingQueue:
 *  → Task scheduling with priorities
 *  → Process high-priority items first
 *
 *  SynchronousQueue:
 *  → Direct handoff between threads
 *  → No buffering needed
 *  → CachedThreadPool pattern
 *
 *  DelayQueue:
 *  → Scheduled tasks
 *  → Caching with expiration
 *  → Rate limiting with time windows
 *
 */
