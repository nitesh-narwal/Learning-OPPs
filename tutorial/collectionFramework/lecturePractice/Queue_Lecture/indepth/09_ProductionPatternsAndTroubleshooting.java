package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * ============================================================
 *   PRODUCTION PATTERNS & TROUBLESHOOTING 🚨🔧
 * ============================================================
 *
 * Bhai, ab tak theory seekh liya.
 * Ab real production problems aur unke solutions dekhte hain! 💼
 *
 * This is the MOST IMPORTANT file for industry experience!
 *
 * Topics:
 * 1. Poison Pill pattern (graceful shutdown)
 * 2. Retry with Exponential Backoff
 * 3. Idempotent Processing
 * 4. Exactly-Once Semantics
 * 5. Queue Overflow handling
 * 6. Consumer scaling strategies
 * 7. Message ordering guarantees
 * 8. Duplicate detection
 * 9. Health checks
 * 10. Common production bugs
 *
 * ============================================================
 *  REAL PRODUCTION SCENARIOS
 * ============================================================
 *
 * Scenario 1: "Queue bharta ja raha hai, memory full ho rahi hai"
 * → Consumers slow hain, producers fast hain
 * → Solution: Add consumers, optimize processing, back pressure
 *
 * Scenario 2: "Messages duplicate process ho rahe hain"
 * → At-least-once delivery, no idempotency
 * → Solution: Idempotent processing, deduplication
 *
 * Scenario 3: "Shutdown ke time messages lost ho rahe hain"
 * → No graceful shutdown
 * → Solution: Poison pill pattern, drain queue
 *
 * Scenario 4: "Ek message fail hone par sab processing ruk jati hai"
 * → No error handling
 * → Solution: Dead letter queue, retry logic
 *
 * Scenario 5: "Order of messages matter karta hai, lekin out of order aa rahe hain"
 * → Multiple consumers, race conditions
 * → Solution: Partitioning, single consumer per partition
 *
 * ============================================================
 *  PRODUCTION QUEUE ANTI-PATTERNS (AVOID!)
 * ============================================================
 *
 * ❌ Unbounded queues in production (OOM risk)
 * ❌ No monitoring/alerts
 * ❌ No retry logic for transient failures
 * ❌ No dead letter queue for permanent failures
 * ❌ No graceful shutdown (lose in-flight messages)
 * ❌ No idempotency (duplicate processing)
 * ❌ Blocking operations in event loops
 * ❌ Single point of failure (one consumer thread)
 * ❌ No back pressure mechanism
 * ❌ Calling size() in hot path
 *
 */
class ProductionPatternsAndTroubleshooting {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== PRODUCTION PATTERNS & TROUBLESHOOTING =====\n");

        // ============================================================
        // PATTERN 1: Poison Pill (Graceful Shutdown)
        // ============================================================

        System.out.println("===== PATTERN 1: Poison Pill Pattern =====\n");

        poisonPillDemo();

        // ============================================================
        // PATTERN 2: Retry with Exponential Backoff
        // ============================================================

        System.out.println("\n===== PATTERN 2: Retry with Exponential Backoff =====\n");

        retryBackoffDemo();

        // ============================================================
        // PATTERN 3: Idempotent Processing
        // ============================================================

        System.out.println("\n===== PATTERN 3: Idempotent Processing =====\n");

        idempotentProcessingDemo();

        // ============================================================
        // PATTERN 4: Exactly-Once Semantics
        // ============================================================

        System.out.println("\n===== PATTERN 4: Exactly-Once Semantics =====\n");

        exactlyOnceDemo();

        // ============================================================
        // PATTERN 5: Queue Overflow Handling
        // ============================================================

        System.out.println("\n===== PATTERN 5: Queue Overflow Handling =====\n");

        overflowHandlingDemo();

        // ============================================================
        // PATTERN 6: Auto-Scaling Consumers
        // ============================================================

        System.out.println("\n===== PATTERN 6: Auto-Scaling Consumers =====\n");

        autoScalingDemo();

        // ============================================================
        // PATTERN 7: Ordered Processing with Partitioning
        // ============================================================

        System.out.println("\n===== PATTERN 7: Ordered Processing =====\n");

        orderedProcessingDemo();

        // ============================================================
        // TROUBLESHOOTING: Common Issues
        // ============================================================

        System.out.println("\n===== TROUBLESHOOTING COMMON ISSUES =====\n");

        troubleshootingGuide();

        System.out.println("\n===== ALL PRODUCTION PATTERNS COMPLETE =====");
    }

    /**
     * PATTERN 1: Poison Pill for graceful shutdown
     */
    private static void poisonPillDemo() throws InterruptedException {
        java.util.concurrent.BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        String POISON_PILL = "SHUTDOWN";

        // Consumer
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String msg = queue.take();
                    if (POISON_PILL.equals(msg)) {
                        System.out.println("  🛑 Poison pill received, shutting down gracefully");
                        break;
                    }
                    System.out.println("  Processing: " + msg);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();

        // Producer
        queue.put("Message 1");
        queue.put("Message 2");
        queue.put("Message 3");
        Thread.sleep(500);
        
        // Graceful shutdown
        System.out.println("Sending shutdown signal...");
        queue.put(POISON_PILL);

        consumer.join();
        System.out.println("✅ Consumer shut down gracefully, no messages lost!");
    }

    /**
     * PATTERN 2: Retry with exponential backoff
     */
    private static void retryBackoffDemo() {
        RetryQueue retryQueue = new RetryQueue();

        // Simulate failing message (fails 2 times, succeeds on 3rd)
        AtomicInteger attempts = new AtomicInteger(0);
        
        boolean success = retryQueue.processWithRetry(() -> {
            int attempt = attempts.incrementAndGet();
            System.out.println("  Attempt " + attempt);
            if (attempt < 3) {
                throw new RuntimeException("Transient failure");
            }
            return true;
        });

        System.out.println("Final result: " + (success ? "✅ SUCCESS" : "❌ FAILED"));
    }

    /**
     * PATTERN 3: Idempotent processing
     */
    private static void idempotentProcessingDemo() {
        IdempotentProcessor processor = new IdempotentProcessor();

        // Process same message multiple times (simulating duplicates)
        processor.process(new IdempotentMessage("MSG-1", "Create Order"));
        processor.process(new IdempotentMessage("MSG-1", "Create Order")); // Duplicate!
        processor.process(new IdempotentMessage("MSG-2", "Update Inventory"));
        processor.process(new IdempotentMessage("MSG-1", "Create Order")); // Another duplicate!

        System.out.println("\nTotal processed (should be 2, not 4): " + processor.getProcessedCount());
    }

    /**
     * PATTERN 4: Exactly-once semantics
     */
    private static void exactlyOnceDemo() {
        ExactlyOnceProcessor processor = new ExactlyOnceProcessor();

        // Simulate message processing with commits
        processor.processMessage(new TransactionalMessage("TXN-1", "Transfer $100"));
        processor.processMessage(new TransactionalMessage("TXN-1", "Transfer $100")); // Duplicate
        processor.processMessage(new TransactionalMessage("TXN-2", "Transfer $50"));

        System.out.println("Transactions processed: " + processor.getCommittedCount());
    }

    /**
     * PATTERN 5: Queue overflow handling
     */
    private static void overflowHandlingDemo() throws InterruptedException {
        OverflowQueue<String> queue = new OverflowQueue<>(5);

        System.out.println("Adding items to bounded queue:");
        for (int i = 1; i <= 10; i++) {
            queue.offer("Item-" + i);
        }

        System.out.println("\nOverflow queue size: " + queue.getOverflowSize());
        System.out.println("Main queue size: " + queue.getMainSize());
    }

    /**
     * PATTERN 6: Auto-scaling consumers
     */
    private static void autoScalingDemo() throws InterruptedException {
        AutoScalingConsumerPool pool = new AutoScalingConsumerPool(1, 5);
        pool.start();

        // Add many tasks
        for (int i = 1; i <= 20; i++) {
            pool.submitTask("Task-" + i);
            Thread.sleep(50);
        }

        Thread.sleep(3000);
        pool.shutdown();
    }

    /**
     * PATTERN 7: Ordered processing with partitioning
     */
    private static void orderedProcessingDemo() throws InterruptedException {
        PartitionedQueue partitionedQueue = new PartitionedQueue(3);

        // Messages with keys (same key = same partition = order preserved)
        partitionedQueue.send("user:123", "Login");
        partitionedQueue.send("user:456", "View Profile");
        partitionedQueue.send("user:123", "Update Settings"); // Same user, will be ordered
        partitionedQueue.send("user:456", "Logout");
        partitionedQueue.send("user:123", "Logout"); // Same user, will be ordered

        Thread.sleep(2000);
        partitionedQueue.shutdown();
    }

    /**
     * Troubleshooting guide
     */
    private static void troubleshootingGuide() {
        System.out.println("🔍 COMMON PRODUCTION ISSUES & SOLUTIONS:\n");

        System.out.println("1. MEMORY LEAK (Queue growing infinitely)");
        System.out.println("   Symptoms: Heap usage increasing, OOM errors");
        System.out.println("   Cause: Producers faster than consumers");
        System.out.println("   Fix: Use bounded queue, add monitoring, scale consumers\n");

        System.out.println("2. MESSAGE LOSS (Messages disappearing)");
        System.out.println("   Symptoms: Some messages never processed");
        System.out.println("   Cause: No persistence, crash before processing");
        System.out.println("   Fix: Durable queue, transaction logs, acknowledge after processing\n");

        System.out.println("3. DUPLICATE PROCESSING");
        System.out.println("   Symptoms: Same message processed multiple times");
        System.out.println("   Cause: At-least-once delivery, retries");
        System.out.println("   Fix: Idempotent operations, deduplication cache\n");

        System.out.println("4. HIGH LATENCY");
        System.out.println("   Symptoms: Messages waiting long in queue");
        System.out.println("   Cause: Slow consumers, large queue");
        System.out.println("   Fix: Optimize processing, add consumers, reduce queue size\n");

        System.out.println("5. DEADLOCK");
        System.out.println("   Symptoms: System hung, no progress");
        System.out.println("   Cause: Circular dependencies, blocking calls");
        System.out.println("   Fix: Use timeouts, avoid nested blocking, thread dumps\n");

        System.out.println("6. OUT-OF-ORDER PROCESSING");
        System.out.println("   Symptoms: Messages processed in wrong order");
        System.out.println("   Cause: Multiple consumers, no ordering guarantee");
        System.out.println("   Fix: Single consumer, partitioning, sequence numbers\n");
    }
}

/**
 * Retry queue with exponential backoff
 */
class RetryQueue {
    private static final int MAX_RETRIES = 5;
    private static final long INITIAL_BACKOFF_MS = 100;

    public boolean processWithRetry(Callable<Boolean> task) {
        int attempt = 0;
        long backoff = INITIAL_BACKOFF_MS;

        while (attempt < MAX_RETRIES) {
            try {
                return task.call();
            } catch (Exception e) {
                attempt++;
                System.out.println("  ❌ Failed (attempt " + attempt + "): " + e.getMessage());
                
                if (attempt < MAX_RETRIES) {
                    System.out.println("  ⏳ Retrying in " + backoff + "ms...");
                    try {
                        Thread.sleep(backoff);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                    backoff *= 2; // Exponential backoff
                }
            }
        }
        return false;
    }
}

/**
 * Idempotent message processor
 */
class IdempotentProcessor {
    private final Set<String> processedIds = ConcurrentHashMap.newKeySet();
    private final AtomicInteger processedCount = new AtomicInteger(0);

    public void process(IdempotentMessage message) {
        if (processedIds.add(message.id)) {
            // First time seeing this message
            System.out.println("  ✅ Processing: " + message);
            processedCount.incrementAndGet();
        } else {
            // Duplicate detected
            System.out.println("  ⚠️  Duplicate detected, skipping: " + message.id);
        }
    }

    public int getProcessedCount() {
        return processedCount.get();
    }
}

class IdempotentMessage {
    String id;
    String payload;

    public IdempotentMessage(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return id + ": " + payload;
    }
}

/**
 * Exactly-once processor using transaction log
 */
class ExactlyOnceProcessor {
    private final Set<String> committedTransactions = ConcurrentHashMap.newKeySet();
    private final AtomicInteger committedCount = new AtomicInteger(0);

    public void processMessage(TransactionalMessage message) {
        // Check if already committed
        if (committedTransactions.contains(message.txnId)) {
            System.out.println("  ⚠️  Transaction already committed: " + message.txnId);
            return;
        }

        try {
            // Process message
            System.out.println("  📝 Processing: " + message);
            
            // Commit transaction (atomically)
            if (committedTransactions.add(message.txnId)) {
                committedCount.incrementAndGet();
                System.out.println("  ✅ Committed: " + message.txnId);
            }
        } catch (Exception e) {
            System.out.println("  ❌ Rollback: " + message.txnId);
        }
    }

    public int getCommittedCount() {
        return committedCount.get();
    }
}

class TransactionalMessage {
    String txnId;
    String operation;

    public TransactionalMessage(String txnId, String operation) {
        this.txnId = txnId;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return txnId + ": " + operation;
    }
}

/**
 * Queue with overflow handling
 */
class OverflowQueue<T> {
    private final java.util.concurrent.BlockingQueue<T> mainQueue;
    private final Queue<T> overflowQueue = new ConcurrentLinkedQueue<>();

    public OverflowQueue(int capacity) {
        this.mainQueue = new ArrayBlockingQueue<>(capacity);
    }

    public void offer(T item) {
        if (!mainQueue.offer(item)) {
            overflowQueue.offer(item);
            System.out.println("  ⚠️  Main queue full, item moved to overflow: " + item);
        } else {
            System.out.println("  ✅ Added to main queue: " + item);
        }
    }

    public int getMainSize() {
        return mainQueue.size();
    }

    public int getOverflowSize() {
        return overflowQueue.size();
    }
}

/**
 * Auto-scaling consumer pool
 */
class AutoScalingConsumerPool {
    private final java.util.concurrent.BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();
    private final List<Thread> consumers = new ArrayList<>();
    private final int minConsumers;
    private final int maxConsumers;
    private volatile boolean running = true;

    public AutoScalingConsumerPool(int minConsumers, int maxConsumers) {
        this.minConsumers = minConsumers;
        this.maxConsumers = maxConsumers;
    }

    public void start() {
        // Start with minimum consumers
        for (int i = 0; i < minConsumers; i++) {
            addConsumer();
        }

        // Monitor thread
        new Thread(() -> {
            while (running) {
                int queueSize = taskQueue.size();
                int activeConsumers = consumers.size();

                // Scale up if queue is growing
                if (queueSize > 10 && activeConsumers < maxConsumers) {
                    addConsumer();
                    System.out.println("  📈 Scaled UP to " + consumers.size() + " consumers");
                }

                // Scale down if queue is empty (keep minimum)
                if (queueSize == 0 && activeConsumers > minConsumers) {
                    System.out.println("  📉 Could scale down (queue empty)");
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "ScalingMonitor").start();
    }

    private void addConsumer() {
        Thread consumer = new Thread(() -> {
            while (running) {
                try {
                    String task = taskQueue.poll(1, TimeUnit.SECONDS);
                    if (task != null) {
                        System.out.println("    [" + Thread.currentThread().getName() + "] Processing: " + task);
                        Thread.sleep(200); // Simulate work
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Consumer-" + consumers.size());

        consumers.add(consumer);
        consumer.start();
    }

    public void submitTask(String task) {
        taskQueue.offer(task);
    }

    public void shutdown() throws InterruptedException {
        running = false;
        for (Thread consumer : consumers) {
            consumer.join();
        }
    }
}

/**
 * Partitioned queue for ordered processing
 */
class PartitionedQueue {
    private final List<java.util.concurrent.BlockingQueue<OrderedMessage>> partitions;
    private final List<Thread> consumers;

    public PartitionedQueue(int numPartitions) {
        partitions = new ArrayList<>();
        consumers = new ArrayList<>();

        for (int i = 0; i < numPartitions; i++) {
            java.util.concurrent.BlockingQueue<OrderedMessage> partition = new LinkedBlockingQueue<>();
            partitions.add(partition);

            // One consumer per partition (ensures ordering)
            int partitionId = i;
            Thread consumer = new Thread(() -> {
                try {
                    while (true) {
                        OrderedMessage msg = partition.poll(1, TimeUnit.SECONDS);
                        if (msg == null) continue;
                        if (msg.payload.equals("SHUTDOWN")) break;
                        
                        System.out.println("  [Partition-" + partitionId + "] " + 
                            msg.key + " → " + msg.payload);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "PartitionConsumer-" + i);

            consumer.start();
            consumers.add(consumer);
        }
    }

    public void send(String key, String payload) {
        int partition = Math.abs(key.hashCode()) % partitions.size();
        partitions.get(partition).offer(new OrderedMessage(key, payload));
    }

    public void shutdown() throws InterruptedException {
        for (java.util.concurrent.BlockingQueue<OrderedMessage> partition : partitions) {
            partition.offer(new OrderedMessage("", "SHUTDOWN"));
        }
        for (Thread consumer : consumers) {
            consumer.join();
        }
    }

    private static class OrderedMessage {
        String key;
        String payload;

        OrderedMessage(String key, String payload) {
            this.key = key;
            this.payload = payload;
        }
    }
}

/*
 * ============================================================
 *  PRODUCTION CHECKLIST
 * ============================================================
 *
 *  □ Bounded queues used (prevent OOM)
 *  □ Monitoring & alerts configured
 *  □ Graceful shutdown implemented (poison pill)
 *  □ Retry logic with exponential backoff
 *  □ Dead letter queue for permanent failures
 *  □ Idempotent processing for duplicates
 *  □ Back pressure mechanism
 *  □ Health checks for queue depth
 *  □ Load testing performed
 *  □ Circuit breaker for cascading failures
 *  □ Logging & tracing enabled
 *  □ Documentation updated
 *
 * ============================================================
 *  INTERVIEW PREPARATION
 * ============================================================
 *
 *  Q: How do you ensure messages aren't lost during shutdown?
 *  A: Use poison pill pattern, drain queue before shutdown
 *
 *  Q: How do you handle duplicate messages?
 *  A: Idempotent operations + deduplication cache
 *
 *  Q: How do you guarantee message ordering?
 *  A: Single consumer OR partitioning by key
 *
 *  Q: How do you prevent queue from growing infinitely?
 *  A: Bounded queue + back pressure + monitoring
 *
 *  Q: How do you handle transient failures?
 *  A: Retry with exponential backoff, max retry limit
 *
 *  Q: How do you achieve exactly-once semantics?
 *  A: Transaction log + idempotency + atomic commits
 *
 */
