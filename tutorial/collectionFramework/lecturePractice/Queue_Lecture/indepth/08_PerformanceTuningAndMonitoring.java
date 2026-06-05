package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * ============================================================
 *   QUEUE PERFORMANCE TUNING & MONITORING 📊🔧
 * ============================================================
 *
 * Bhai, Queue banana toh seekh liya,
 * Lekin production mein performance kaise monitor karein?
 * Kaise optimize karein? Kaise problems detect karein?
 *
 * This file teaches you REAL production skills! 💼
 *
 * Topics covered:
 * 1. Queue Metrics & Monitoring
 * 2. Performance Tuning Techniques
 * 3. Memory Optimization
 * 4. Throughput vs Latency tradeoffs
 * 5. Back Pressure handling
 * 6. Queue sizing strategies
 * 7. Common performance problems
 * 8. Production troubleshooting
 *
 * ============================================================
 *  CRITICAL METRICS TO MONITOR
 * ============================================================
 *
 * 1. QUEUE DEPTH (size)
 *    → Current number of items
 *    → Alert if > 80% capacity (bounded queues)
 *    → Indicates backlog/bottleneck
 *
 * 2. ENQUEUE RATE (offers/second)
 *    → How fast items are added
 *    → Compare with dequeue rate
 *
 * 3. DEQUEUE RATE (polls/second)
 *    → How fast items are processed
 *    → Should match or exceed enqueue rate
 *
 * 4. WAIT TIME (latency)
 *    → Time item spends in queue
 *    → p50, p95, p99 percentiles
 *
 * 5. REJECTION RATE
 *    → Failed offers (bounded queues)
 *    → Indicates capacity issues
 *
 * 6. PROCESSING TIME
 *    → Time to process each item
 *    → Identifies slow consumers
 *
 * 7. MEMORY USAGE
 *    → Heap used by queue
 *    → Critical for unbounded queues
 *
 * ============================================================
 *  PERFORMANCE TUNING CHECKLIST
 * ============================================================
 *
 * ✅ Choose right queue type for your use case
 * ✅ Size queues appropriately (not too small, not too large)
 * ✅ Use bounded queues to prevent OOM
 * ✅ Batch operations when possible
 * ✅ Avoid calling size() in hot paths
 * ✅ Use appropriate thread pool sizes
 * ✅ Monitor and alert on metrics
 * ✅ Load test before production
 * ✅ Profile under realistic load
 * ✅ Plan for peak traffic (2-3x normal)
 *
 * ============================================================
 *  QUEUE SIZING FORMULA
 * ============================================================
 *
 * Optimal Queue Size = Throughput × Latency × Safety Factor
 *
 * Example:
 * - Throughput: 1000 requests/sec
 * - Latency: 100ms average processing time
 * - Safety Factor: 2x (for spikes)
 * - Queue Size = 1000 × 0.1 × 2 = 200
 *
 * Too small: Rejections, lost data
 * Too large: Memory waste, high latency
 *
 */
class PerformanceTuningAndMonitoring {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== QUEUE PERFORMANCE TUNING & MONITORING =====\n");

        // ============================================================
        // DEMO 1: Queue Metrics Collector
        // ============================================================

        System.out.println("===== DEMO 1: Queue Metrics Monitoring =====\n");

        MonitoredQueue<String> monitoredQueue = new MonitoredQueue<>(new LinkedBlockingQueue<>(100));

        // Simulate producers
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 50; i++) {
                    monitoredQueue.offer("Item-" + i);
                    Thread.sleep(50); // 20 items/sec
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Simulate slow consumer
        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(500); // Start late
                for (int i = 1; i <= 50; i++) {
                    monitoredQueue.poll();
                    Thread.sleep(100); // 10 items/sec (slower!)
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        // Print metrics periodically
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            monitoredQueue.printMetrics();
        }

        producer.join();
        consumer.join();

        System.out.println("\nFinal metrics:");
        monitoredQueue.printDetailedMetrics();

        // ============================================================
        // DEMO 2: Throughput vs Latency Comparison
        // ============================================================

        System.out.println("\n===== DEMO 2: Throughput vs Latency =====\n");

        throughputLatencyComparison();

        // ============================================================
        // DEMO 3: Back Pressure Handling
        // ============================================================

        System.out.println("\n===== DEMO 3: Back Pressure Handling =====\n");

        BackPressureQueue<Integer> bpQueue = new BackPressureQueue<>(10, 100);

        // Fast producer
        Thread fastProducer = new Thread(() -> {
            for (int i = 1; i <= 50; i++) {
                boolean added = bpQueue.offer(i);
                if (!added) {
                    System.out.println("  ⚠️  Back pressure applied, producer slowing down...");
                    try {
                        Thread.sleep(100); // Slow down
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    bpQueue.offer(i); // Retry
                }
            }
        });

        // Slow consumer
        Thread slowConsumer = new Thread(() -> {
            try {
                Thread.sleep(200); // Start late
                while (true) {
                    Integer item = bpQueue.poll();
                    if (item == null) {
                        if (!fastProducer.isAlive()) break;
                        Thread.sleep(10);
                        continue;
                    }
                    System.out.println("    Consumed: " + item);
                    Thread.sleep(50); // Slow processing
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        fastProducer.start();
        slowConsumer.start();
        fastProducer.join();
        slowConsumer.join();

        // ============================================================
        // DEMO 4: Batch Processing Optimization
        // ============================================================

        System.out.println("\n===== DEMO 4: Batch Processing Optimization =====\n");

        batchProcessingComparison();

        // ============================================================
        // DEMO 5: Memory Usage Tracking
        // ============================================================

        System.out.println("\n===== DEMO 5: Memory Usage Tracking =====\n");

        memoryUsageDemo();

        // ============================================================
        // DEMO 6: Queue Sizing Simulator
        // ============================================================

        System.out.println("\n===== DEMO 6: Queue Sizing Simulator =====\n");

        queueSizingSimulator();

        // ============================================================
        // DEMO 7: Hot Spot Detection
        // ============================================================

        System.out.println("\n===== DEMO 7: Performance Hot Spot Detection =====\n");

        hotSpotDetection();

        System.out.println("\n===== ALL PERFORMANCE TUNING DEMOS COMPLETE =====");
    }

    /**
     * Compare throughput vs latency for different queue types
     */
    private static void throughputLatencyComparison() throws InterruptedException {
        int numOperations = 100_000;

        // Test ArrayBlockingQueue
        java.util.concurrent.BlockingQueue<Integer> abq = new ArrayBlockingQueue<>(10000);
        long[] abqStats = measurePerformance(abq, numOperations);

        // Test LinkedBlockingQueue
        java.util.concurrent.BlockingQueue<Integer> lbq = new LinkedBlockingQueue<>();
        long[] lbqStats = measurePerformance(lbq, numOperations);

        // Test ConcurrentLinkedQueue (as Queue)
        Queue<Integer> clq = new ConcurrentLinkedQueue<>();
        long[] clqStats = measureNonBlockingPerformance(clq, numOperations);

        System.out.println("Performance comparison (" + numOperations + " ops):");
        System.out.println("  ArrayBlockingQueue:    Throughput=" + abqStats[0] + " ops/ms, Latency=" + abqStats[1] + " ns/op");
        System.out.println("  LinkedBlockingQueue:   Throughput=" + lbqStats[0] + " ops/ms, Latency=" + lbqStats[1] + " ns/op");
        System.out.println("  ConcurrentLinkedQueue: Throughput=" + clqStats[0] + " ops/ms, Latency=" + clqStats[1] + " ns/op");
    }

    private static long[] measurePerformance(java.util.concurrent.BlockingQueue<Integer> queue, int ops) throws InterruptedException {
        long start = System.nanoTime();
        
        Thread producer = new Thread(() -> {
            for (int i = 0; i < ops; i++) {
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < ops; i++) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        long duration = System.nanoTime() - start;
        long throughput = ops / (duration / 1_000_000); // ops per ms
        long latency = duration / ops; // ns per op

        return new long[]{throughput, latency};
    }

    private static long[] measureNonBlockingPerformance(Queue<Integer> queue, int ops) throws InterruptedException {
        long start = System.nanoTime();
        
        Thread producer = new Thread(() -> {
            for (int i = 0; i < ops; i++) {
                queue.offer(i);
            }
        });

        Thread consumer = new Thread(() -> {
            int consumed = 0;
            while (consumed < ops) {
                if (queue.poll() != null) {
                    consumed++;
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        long duration = System.nanoTime() - start;
        long throughput = ops / (duration / 1_000_000);
        long latency = duration / ops;

        return new long[]{throughput, latency};
    }

    /**
     * Compare single-item vs batch processing
     */
    private static void batchProcessingComparison() throws InterruptedException {
        int totalItems = 10000;

        // Single-item processing
        Queue<Integer> queue1 = new LinkedBlockingQueue<>();
        long start = System.nanoTime();
        for (int i = 0; i < totalItems; i++) {
            queue1.offer(i);
            queue1.poll();
        }
        long singleTime = System.nanoTime() - start;

        // Batch processing (batch size = 100)
        Queue<Integer> queue2 = new LinkedBlockingQueue<>();
        start = System.nanoTime();
        List<Integer> batch = new ArrayList<>(100);
        for (int i = 0; i < totalItems; i++) {
            queue2.offer(i);
            batch.add(queue2.poll());
            if (batch.size() >= 100) {
                // Process batch
                batch.clear();
            }
        }
        long batchTime = System.nanoTime() - start;

        System.out.println("Processing " + totalItems + " items:");
        System.out.println("  Single-item: " + singleTime / 1_000_000 + " ms");
        System.out.println("  Batch (100): " + batchTime / 1_000_000 + " ms");
        System.out.println("  Improvement: " + ((singleTime - batchTime) * 100 / singleTime) + "% faster");
    }

    /**
     * Demonstrate memory usage of different queue types
     */
    private static void memoryUsageDemo() {
        Runtime runtime = Runtime.getRuntime();
        int numElements = 100_000;

        // LinkedList (high overhead)
        runtime.gc();
        long before1 = runtime.totalMemory() - runtime.freeMemory();
        Queue<Integer> ll = new LinkedList<>();
        for (int i = 0; i < numElements; i++) ll.offer(i);
        long after1 = runtime.totalMemory() - runtime.freeMemory();
        long llMemory = (after1 - before1) / 1024;

        // ArrayDeque (low overhead)
        runtime.gc();
        long before2 = runtime.totalMemory() - runtime.freeMemory();
        Queue<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < numElements; i++) ad.offer(i);
        long after2 = runtime.totalMemory() - runtime.freeMemory();
        long adMemory = (after2 - before2) / 1024;

        System.out.println("Memory usage for " + numElements + " Integer objects:");
        System.out.println("  LinkedList:  ~" + llMemory + " KB");
        System.out.println("  ArrayDeque:  ~" + adMemory + " KB");
        System.out.println("  Savings:     ~" + (llMemory - adMemory) + " KB (" + 
            ((llMemory - adMemory) * 100 / llMemory) + "%)");
    }

    /**
     * Simulate different queue sizes to find optimal
     */
    private static void queueSizingSimulator() throws InterruptedException {
        int[] queueSizes = {10, 50, 100, 500, 1000};
        int throughput = 100; // items/sec
        int processingTimeMs = 20; // ms per item

        System.out.println("Queue sizing simulation (throughput=" + throughput + " items/sec):");
        
        for (int size : queueSizes) {
            int rejected = simulateQueue(size, throughput, processingTimeMs);
            System.out.println("  Size " + size + ": " + rejected + " rejections");
        }
    }

    private static int simulateQueue(int capacity, int rate, int processingMs) throws InterruptedException {
        java.util.concurrent.BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(capacity);
        AtomicInteger rejections = new AtomicInteger(0);
        int duration = 2000; // 2 seconds

        Thread producer = new Thread(() -> {
            long end = System.currentTimeMillis() + duration;
            int item = 0;
            while (System.currentTimeMillis() < end) {
                if (!queue.offer(item++)) {
                    rejections.incrementAndGet();
                }
                try {
                    Thread.sleep(1000 / rate); // Produce at specified rate
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (producer.isAlive() || !queue.isEmpty()) {
                try {
                    Integer item = queue.poll(10, TimeUnit.MILLISECONDS);
                    if (item != null) {
                        Thread.sleep(processingMs);
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        return rejections.get();
    }

    /**
     * Detect performance hot spots
     */
    private static void hotSpotDetection() {
        System.out.println("Common performance hot spots:");
        System.out.println("  1. Calling size() frequently → O(n) in some queues");
        System.out.println("  2. Synchronized blocks → Lock contention");
        System.out.println("  3. Unbounded queues → OOM risk");
        System.out.println("  4. Single-threaded consumers → Bottleneck");
        System.out.println("  5. Large object serialization → CPU/memory spike");
    }
}

/**
 * Monitored Queue wrapper with metrics
 */
class MonitoredQueue<T> {
    private final java.util.concurrent.BlockingQueue<T> queue;
    private final AtomicLong enqueueCount = new AtomicLong(0);
    private final AtomicLong dequeueCount = new AtomicLong(0);
    private final AtomicLong rejectionCount = new AtomicLong(0);
    private final List<Long> waitTimes = new CopyOnWriteArrayList<>();
    private final Map<T, Long> enqueueTimes = new ConcurrentHashMap<>();
    private final long startTime = System.currentTimeMillis();

    public MonitoredQueue(java.util.concurrent.BlockingQueue<T> queue) {
        this.queue = queue;
    }

    public boolean offer(T item) {
        boolean added = queue.offer(item);
        if (added) {
            enqueueCount.incrementAndGet();
            enqueueTimes.put(item, System.currentTimeMillis());
        } else {
            rejectionCount.incrementAndGet();
        }
        return added;
    }

    public T poll() {
        T item = queue.poll();
        if (item != null) {
            dequeueCount.incrementAndGet();
            Long enqueueTime = enqueueTimes.remove(item);
            if (enqueueTime != null) {
                long waitTime = System.currentTimeMillis() - enqueueTime;
                waitTimes.add(waitTime);
            }
        }
        return item;
    }

    public void printMetrics() {
        long enqueued = enqueueCount.get();
        long dequeued = dequeueCount.get();
        long rejected = rejectionCount.get();
        int depth = queue.size();
        
        System.out.println("📊 Metrics: Depth=" + depth + 
            " | Enqueued=" + enqueued + 
            " | Dequeued=" + dequeued + 
            " | Rejected=" + rejected);
    }

    public void printDetailedMetrics() {
        long totalTimeMs = System.currentTimeMillis() - startTime;
        long enqueued = enqueueCount.get();
        long dequeued = dequeueCount.get();
        
        double enqueueRate = (enqueued * 1000.0) / totalTimeMs;
        double dequeueRate = (dequeued * 1000.0) / totalTimeMs;
        
        long avgWait = waitTimes.stream().mapToLong(Long::longValue).sum() / Math.max(1, waitTimes.size());
        
        System.out.println("📈 Detailed Metrics:");
        System.out.println("  Total Runtime: " + totalTimeMs + " ms");
        System.out.println("  Enqueue Rate: " + String.format("%.2f", enqueueRate) + " items/sec");
        System.out.println("  Dequeue Rate: " + String.format("%.2f", dequeueRate) + " items/sec");
        System.out.println("  Avg Wait Time: " + avgWait + " ms");
        System.out.println("  Queue Depth: " + queue.size());
        System.out.println("  Rejections: " + rejectionCount.get());
    }
}

/**
 * Queue with back pressure support
 */
class BackPressureQueue<T> {
    private final java.util.concurrent.BlockingQueue<T> queue;
    private final int backPressureThreshold;

    public BackPressureQueue(int capacity, int backPressureThreshold) {
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.backPressureThreshold = backPressureThreshold;
    }

    public boolean offer(T item) {
        // Apply back pressure if queue is filling up
        if (queue.size() * 100 / ((ArrayBlockingQueue<T>)queue).remainingCapacity() > backPressureThreshold) {
            return false; // Signal producer to slow down
        }
        return queue.offer(item);
    }

    public T poll() {
        return queue.poll();
    }
}

/*
 * ============================================================
 *  KEY TAKEAWAYS - PERFORMANCE & MONITORING
 * ============================================================
 *
 *  ✅ Always monitor queue depth, rates, and latency
 *  ✅ Set up alerts for queue depth > 80% capacity
 *  ✅ Size queues based on throughput × latency × safety factor
 *  ✅ Use bounded queues to prevent OOM
 *  ✅ Batch processing improves throughput significantly
 *  ✅ Back pressure prevents cascading failures
 *  ✅ Profile before optimizing (measure first!)
 *  ✅ ArrayDeque uses less memory than LinkedList
 *  ✅ ConcurrentLinkedQueue has best throughput
 *
 * ============================================================
 *  PRODUCTION MONITORING CHECKLIST
 * ============================================================
 *
 *  □ Queue depth metric exported
 *  □ Enqueue/dequeue rate tracked
 *  □ Rejection rate monitored
 *  □ p50/p95/p99 latency measured
 *  □ Alerts configured (depth > 80%, rejections > 0)
 *  □ Memory usage dashboards
 *  □ Thread pool utilization tracked
 *  □ Dead letter queue for failures
 *  □ Circuit breaker for cascading failures
 *  □ Load testing performed
 *
 * ============================================================
 *  TROUBLESHOOTING GUIDE
 * ============================================================
 *
 *  Problem: Queue keeps growing
 *  → Consumers too slow or too few
 *  → Solution: Add more consumers, optimize processing
 *
 *  Problem: High rejection rate
 *  → Queue too small or producers too fast
 *  → Solution: Increase capacity, add back pressure
 *
 *  Problem: High memory usage
 *  → Unbounded queue with backlog
 *  → Solution: Use bounded queue, add monitoring
 *
 *  Problem: High latency
 *  → Queue too large, items waiting too long
 *  → Solution: Reduce capacity, add more consumers
 *
 *  Problem: Thread starvation
 *  → BlockingQueue blocking all threads
 *  → Solution: Use non-blocking queue or separate thread pools
 *
 */
