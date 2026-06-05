package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ============================================================
 *    CONCURRENT QUEUES - LOCK-FREE & HIGH-PERFORMANCE 🔥
 * ============================================================
 *
 * Bhai, BlockingQueue toh seekh liya, lekin wo BLOCKS karta hai.
 * Production mein sometimes blocking = BAD (latency badh jata hai)
 *
 * Solution: LOCK-FREE CONCURRENT QUEUES! 🚀
 *
 * Key difference:
 * - BlockingQueue: Uses locks (synchronized, ReentrantLock)
 * - ConcurrentLinkedQueue: Lock-free (CAS - Compare And Swap)
 * - Result: Better throughput, lower latency, no thread blocking
 *
 * ============================================================
 *  CONCURRENTLINKEDQUEUE - THE WORKHORSE
 * ============================================================
 *
 * Internal: Michael-Scott non-blocking queue algorithm
 * - Uses AtomicReference for lock-free operations
 * - CAS (Compare-And-Swap) operations
 * - Unbounded (no capacity limit)
 * - No blocking (offer/poll never wait)
 *
 * When to use:
 * ✅ High-throughput scenarios (millions of ops/sec)
 * ✅ Low-latency requirements (microsecond level)
 * ✅ Multiple producers, multiple consumers
 * ✅ Non-blocking behavior needed
 * ✅ Message passing between threads
 *
 * When NOT to use:
 * ❌ Need blocking behavior (use BlockingQueue)
 * ❌ Need bounded capacity (use ArrayBlockingQueue)
 * ❌ Need priority ordering (use PriorityBlockingQueue)
 *
 * ============================================================
 *  TRANSFERQUEUE - ADVANCED PATTERN
 * ============================================================
 *
 * LinkedTransferQueue = ConcurrentLinkedQueue + extra features
 *
 * Special methods:
 * - transfer(E e): Hand off element directly to waiting consumer
 * - tryTransfer(E e): Try immediate handoff, return false if no consumer
 * - tryTransfer(E e, timeout): Try handoff with timeout
 *
 * Use case: Synchronous message passing (producer waits for consumer)
 *
 * Real example: Request-Response pattern in microservices
 *
 * ============================================================
 *  PERFORMANCE CHARACTERISTICS
 * ============================================================
 *
 * Queue Type               | Throughput | Latency | Blocking | Bounded
 * -------------------------|------------|---------|----------|--------
 * ConcurrentLinkedQueue    | Highest    | Lowest  | No       | No
 * LinkedBlockingQueue      | High       | Medium  | Yes      | Optional
 * ArrayBlockingQueue       | Medium     | Medium  | Yes      | Yes
 * SynchronousQueue         | Low        | High    | Yes      | No
 * LinkedTransferQueue      | High       | Low     | Optional | No
 *
 * ============================================================
 *  INDUSTRY USE CASES
 * ============================================================
 *
 * 1. NETTY (Network Framework)
 *    → Event loop uses ConcurrentLinkedQueue for tasks
 *    → Millions of I/O events per second
 *
 * 2. DISRUPTOR (LMAX Exchange)
 *    → Custom lock-free queue (ring buffer)
 *    → Processes 6 million orders/second
 *
 * 3. AKKA (Actor Framework)
 *    → Actor mailboxes use concurrent queues
 *    → Message passing between actors
 *
 * 4. KAFKA (Message Broker)
 *    → Internal queues for batching
 *    → High-throughput message delivery
 *
 * 5. REACTIVE STREAMS
 *    → Backpressure management
 *    → Non-blocking data flow
 *
 */
class ConcurrentQueues {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== CONCURRENT QUEUES - ADVANCED GUIDE =====\n");

        // ============================================================
        // DEMO 1: ConcurrentLinkedQueue Basics
        // ============================================================

        System.out.println("===== DEMO 1: ConcurrentLinkedQueue Basics =====\n");

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        // Non-blocking operations
        queue.offer("Item 1");
        queue.offer("Item 2");
        queue.offer("Item 3");

        System.out.println("Queue: " + queue);
        System.out.println("Size: " + queue.size()); // Note: size() is O(n)!

        // Poll is non-blocking
        System.out.println("Polled: " + queue.poll());
        System.out.println("Polled: " + queue.poll());
        System.out.println("Queue now: " + queue);

        // ============================================================
        // DEMO 2: Multi-threaded Performance Test
        // ============================================================

        System.out.println("\n===== DEMO 2: Multi-threaded Performance =====\n");

        performanceTest();

        // ============================================================
        // DEMO 3: Real-World - Event Loop (like Netty)
        // ============================================================

        System.out.println("\n===== DEMO 3: Event Loop Pattern =====\n");

        EventLoop eventLoop = new EventLoop();
        eventLoop.start();

        // Submit tasks from multiple threads
        Thread[] submitters = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int threadId = i + 1;
            submitters[i] = new Thread(() -> {
                for (int j = 1; j <= 3; j++) {
                    final int taskNum = j;
                    eventLoop.submit(() -> {
                        System.out.println("  Executing task from Thread-" + threadId + 
                            ", Task-" + taskNum + " on " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }
            });
            submitters[i].start();
        }

        for (Thread t : submitters) t.join();
        Thread.sleep(2000); // Let tasks complete
        eventLoop.shutdown();

        // ============================================================
        // DEMO 4: LinkedTransferQueue - Direct Handoff
        // ============================================================

        System.out.println("\n===== DEMO 4: LinkedTransferQueue (Direct Handoff) =====\n");

        LinkedTransferQueue<String> transferQueue = new LinkedTransferQueue<>();

        // Consumer waiting for messages
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Consumer waiting for messages...");
                for (int i = 0; i < 3; i++) {
                    String msg = transferQueue.take();
                    System.out.println("  Consumer received: " + msg);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();

        Thread.sleep(200); // Ensure consumer is waiting

        // Producer using transfer (waits for consumer)
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    String msg = "Message-" + i;
                    System.out.println("Producer transferring: " + msg);
                    transferQueue.transfer(msg); // Blocks until consumer takes it!
                    System.out.println("  Transfer complete for: " + msg);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();

        producer.join();
        consumer.join();

        // ============================================================
        // DEMO 5: tryTransfer - Non-blocking Handoff
        // ============================================================

        System.out.println("\n===== DEMO 5: tryTransfer (Non-blocking) =====\n");

        LinkedTransferQueue<String> queue2 = new LinkedTransferQueue<>();

        // Try transfer without waiting consumer
        boolean transferred = queue2.tryTransfer("Immediate");
        System.out.println("Transfer without consumer: " + transferred); // false

        // Add to queue normally
        queue2.offer("Queued item");
        System.out.println("Queue size: " + queue2.size());

        // ============================================================
        // DEMO 6: Work Stealing Pattern
        // ============================================================

        System.out.println("\n===== DEMO 6: Work Stealing Pattern =====\n");

        WorkStealingExecutor executor = new WorkStealingExecutor(3);

        // Submit many tasks
        for (int i = 1; i <= 15; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("  Task " + taskId + " on " + 
                    Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(4000);
        executor.shutdown();

        // ============================================================
        // DEMO 7: Batch Processing with ConcurrentLinkedQueue
        // ============================================================

        System.out.println("\n===== DEMO 7: Batch Processing =====\n");

        BatchCollector collector = new BatchCollector(5, 1000);
        collector.start();

        // Add items rapidly
        for (int i = 1; i <= 13; i++) {
            collector.add("Item-" + i);
            Thread.sleep(100);
        }

        Thread.sleep(2000);
        collector.stop();

        // ============================================================
        // DEMO 8: Memory Consistency & Visibility
        // ============================================================

        System.out.println("\n===== DEMO 8: Memory Consistency =====\n");

        ConcurrentLinkedQueue<Integer> sharedQueue = new ConcurrentLinkedQueue<>();

        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                sharedQueue.offer(i);
                System.out.println("Written: " + i);
            }
        });

        Thread reader = new Thread(() -> {
            try {
                Thread.sleep(100); // Let writer add some items
                Integer value;
                while ((value = sharedQueue.poll()) != null) {
                    System.out.println("  Read: " + value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();

        System.out.println("\n💡 Note: All values visible due to happens-before guarantee!");

        System.out.println("\n===== ALL CONCURRENT QUEUE DEMOS COMPLETE =====");
    }

    /**
     * Performance comparison: ConcurrentLinkedQueue vs LinkedBlockingQueue
     */
    private static void performanceTest() throws InterruptedException {
        int numThreads = 4;
        int opsPerThread = 100_000;

        // Test 1: ConcurrentLinkedQueue
        ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>();
        long start = System.nanoTime();
        
        Thread[] producersClq = new Thread[numThreads];
        Thread[] consumersClq = new Thread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            producersClq[i] = new Thread(() -> {
                for (int j = 0; j < opsPerThread; j++) {
                    clq.offer(j);
                }
            });
            consumersClq[i] = new Thread(() -> {
                for (int j = 0; j < opsPerThread; j++) {
                    clq.poll();
                }
            });
        }

        for (Thread t : producersClq) t.start();
        for (Thread t : consumersClq) t.start();
        for (Thread t : producersClq) t.join();
        for (Thread t : consumersClq) t.join();

        long clqTime = System.nanoTime() - start;

        // Test 2: LinkedBlockingQueue
        LinkedBlockingQueue<Integer> lbq = new LinkedBlockingQueue<>();
        start = System.nanoTime();
        
        Thread[] producersLbq = new Thread[numThreads];
        Thread[] consumersLbq = new Thread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            producersLbq[i] = new Thread(() -> {
                for (int j = 0; j < opsPerThread; j++) {
                    lbq.offer(j);
                }
            });
            consumersLbq[i] = new Thread(() -> {
                for (int j = 0; j < opsPerThread; j++) {
                    lbq.poll();
                }
            });
        }

        for (Thread t : producersLbq) t.start();
        for (Thread t : consumersLbq) t.start();
        for (Thread t : producersLbq) t.join();
        for (Thread t : consumersLbq) t.join();

        long lbqTime = System.nanoTime() - start;

        System.out.println("Performance test (" + (numThreads * opsPerThread * 2) + " operations):");
        System.out.println("  ConcurrentLinkedQueue: " + clqTime / 1_000_000 + " ms");
        System.out.println("  LinkedBlockingQueue:   " + lbqTime / 1_000_000 + " ms");
        System.out.println("  Winner: " + (clqTime < lbqTime ? 
            "ConcurrentLinkedQueue 🏆 (" + ((lbqTime - clqTime) * 100 / lbqTime) + "% faster)" :
            "LinkedBlockingQueue 🏆"));
    }
}

/**
 * Event Loop pattern (like Netty, Node.js)
 * Single thread processes tasks from non-blocking queue
 */
class EventLoop {
    private final ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();
    private final Thread eventLoopThread;
    private volatile boolean running = true;

    public EventLoop() {
        eventLoopThread = new Thread(() -> {
            System.out.println("Event loop started on " + Thread.currentThread().getName());
            while (running || !taskQueue.isEmpty()) {
                Runnable task = taskQueue.poll();
                if (task != null) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        System.err.println("Task failed: " + e.getMessage());
                    }
                } else {
                    // Yield CPU if no tasks (or use LockSupport.parkNanos)
                    Thread.yield();
                }
            }
            System.out.println("Event loop stopped");
        }, "EventLoopThread");
    }

    public void start() {
        eventLoopThread.start();
    }

    public void submit(Runnable task) {
        taskQueue.offer(task);
    }

    public void shutdown() throws InterruptedException {
        running = false;
        eventLoopThread.join();
    }
}

/**
 * Work Stealing pattern
 * Each worker has own queue, can steal from others when idle
 */
class WorkStealingExecutor {
    private final WorkerThread[] workers;
    private final AtomicInteger nextWorker = new AtomicInteger(0);

    public WorkStealingExecutor(int numWorkers) {
        workers = new WorkerThread[numWorkers];
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new WorkerThread("Worker-" + (i + 1), workers);
            workers[i].start();
        }
    }

    public void submit(Runnable task) {
        // Round-robin task distribution
        int workerIndex = nextWorker.getAndIncrement() % workers.length;
        workers[workerIndex].addTask(task);
    }

    public void shutdown() throws InterruptedException {
        for (WorkerThread worker : workers) {
            worker.shutdown();
        }
        for (WorkerThread worker : workers) {
            worker.join();
        }
    }

    private static class WorkerThread extends Thread {
        private final ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        private final WorkerThread[] allWorkers;
        private volatile boolean running = true;

        public WorkerThread(String name, WorkerThread[] allWorkers) {
            super(name);
            this.allWorkers = allWorkers;
        }

        public void addTask(Runnable task) {
            queue.offer(task);
        }

        public void shutdown() {
            running = false;
        }

        @Override
        public void run() {
            while (running || !queue.isEmpty()) {
                Runnable task = queue.poll();
                
                // Try to steal from others if own queue is empty
                if (task == null && running) {
                    task = stealTask();
                }

                if (task != null) {
                    try {
                        task.run();
                    } catch (Exception e) {
                        System.err.println("Task failed: " + e.getMessage());
                    }
                } else {
                    Thread.yield();
                }
            }
        }

        private Runnable stealTask() {
            for (WorkerThread other : allWorkers) {
                if (other != this) {
                    Runnable stolen = other.queue.poll();
                    if (stolen != null) {
                        return stolen;
                    }
                }
            }
            return null;
        }
    }
}

/**
 * Batch Collector using ConcurrentLinkedQueue
 * Collects items and processes in batches
 */
class BatchCollector {
    private final ConcurrentLinkedQueue<String> buffer = new ConcurrentLinkedQueue<>();
    private final int batchSize;
    private final long batchTimeoutMs;
    private final Thread processor;
    private volatile boolean running = true;

    public BatchCollector(int batchSize, long batchTimeoutMs) {
        this.batchSize = batchSize;
        this.batchTimeoutMs = batchTimeoutMs;
        this.processor = new Thread(this::process, "BatchProcessor");
    }

    public void start() {
        processor.start();
    }

    public void add(String item) {
        buffer.offer(item);
    }

    public void stop() throws InterruptedException {
        running = false;
        processor.join();
    }

    private void process() {
        long lastBatchTime = System.currentTimeMillis();
        List<String> batch = new ArrayList<>();

        while (running || !buffer.isEmpty()) {
            String item = buffer.poll();
            
            if (item != null) {
                batch.add(item);
            }

            // Process batch if size reached or timeout
            boolean sizeReached = batch.size() >= batchSize;
            boolean timeoutReached = System.currentTimeMillis() - lastBatchTime > batchTimeoutMs;

            if (!batch.isEmpty() && (sizeReached || timeoutReached)) {
                System.out.println("  📦 Processing batch of " + batch.size() + ": " + batch);
                batch.clear();
                lastBatchTime = System.currentTimeMillis();
            }

            if (item == null && running) {
                try {
                    Thread.sleep(10); // Small sleep to avoid busy-waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        // Process remaining
        if (!batch.isEmpty()) {
            System.out.println("  📦 Processing final batch of " + batch.size() + ": " + batch);
        }
    }
}

/*
 * ============================================================
 *  KEY TAKEAWAYS
 * ============================================================
 *
 *  ✅ ConcurrentLinkedQueue = Lock-free, highest throughput
 *  ✅ Uses CAS (Compare-And-Swap) for atomic operations
 *  ✅ No blocking, always non-blocking operations
 *  ✅ Unbounded capacity (can cause OOM if producers faster than consumers)
 *  ✅ LinkedTransferQueue = ConcurrentLinkedQueue + direct handoff
 *  ✅ size() is O(n) - avoid calling frequently!
 *  ✅ Perfect for event loops, work stealing, high-throughput scenarios
 *
 * ============================================================
 *  PRODUCTION BEST PRACTICES
 * ============================================================
 *
 *  1. Monitor queue depth (unbounded can cause memory issues)
 *  2. Don't call size() in hot paths (it's O(n)!)
 *  3. Use for high-throughput, low-latency scenarios
 *  4. Consider bounded alternatives if producers > consumers
 *  5. Use LinkedTransferQueue for request-response patterns
 *  6. Batch processing reduces overhead
 *  7. Event loop pattern for single-threaded processing
 *  8. Work stealing for load balancing
 *
 * ============================================================
 *  WHEN TO USE WHAT?
 * ============================================================
 *
 *  ConcurrentLinkedQueue:
 *  → Highest throughput needed
 *  → Non-blocking behavior required
 *  → Multiple producers/consumers
 *  → Unbounded is acceptable
 *
 *  LinkedBlockingQueue:
 *  → Need blocking behavior
 *  → Need bounded capacity
 *  → Simpler producer-consumer
 *
 *  LinkedTransferQueue:
 *  → Direct handoff needed
 *  → Request-response pattern
 *  → Hybrid blocking/non-blocking
 *
 */
