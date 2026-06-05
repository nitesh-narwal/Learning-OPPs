package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.indepth;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * ============================================================
 *      ADVANCED QUEUE PATTERNS & REAL-WORLD APPLICATIONS 🚀
 * ============================================================
 *
 * Bhai, ab tak tumne Queue ka ABC seekh liya.
 * Ab aati hai REAL industry patterns! 💼
 *
 * This file covers:
 * 1. Rate Limiting using Queues
 * 2. Circuit Breaker Pattern
 * 3. Event-Driven Architecture
 * 4. Batch Processing
 * 5. Load Balancing
 * 6. Message Deduplication
 * 7. Dead Letter Queue (DLQ)
 * 8. Request Coalescing
 * 9. Time-based Expiration
 * 10. Multi-level Priority Queue
 *
 * ============================================================
 *  REAL INDUSTRY SCENARIOS
 * ============================================================
 *
 * - Amazon SQS: Distributed message queue
 * - Kafka: Event streaming platform (queue-based)
 * - RabbitMQ: Message broker
 * - Redis: In-memory queue for caching
 * - Spring @Async with TaskExecutor
 * - Netty: Network framework (uses queues internally)
 * - Actor model: Akka, Erlang (mailbox = queue)
 *
 */
class AdvancedPatterns {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== ADVANCED QUEUE PATTERNS =====\n");

        // ============================================================
        // PATTERN 1: Rate Limiter (Sliding Window)
        // ============================================================

        System.out.println("===== PATTERN 1: Rate Limiter =====\n");

        RateLimiter rateLimiter = new RateLimiter(5, 1000); // 5 requests per second

        System.out.println("Simulating 10 API requests:");
        for (int i = 1; i <= 10; i++) {
            if (rateLimiter.allowRequest()) {
                System.out.println("  Request " + i + " - ✅ ALLOWED");
            } else {
                System.out.println("  Request " + i + " - ❌ RATE LIMITED");
            }
            Thread.sleep(150); // 150ms between requests
        }

        // ============================================================
        // PATTERN 2: Circuit Breaker with Queue
        // ============================================================

        System.out.println("\n===== PATTERN 2: Circuit Breaker =====\n");

        CircuitBreakerQueue circuitBreaker = new CircuitBreakerQueue(3, 5000);

        System.out.println("Simulating API calls with failures:");
        for (int i = 1; i <= 10; i++) {
            boolean success = (i != 2 && i != 3 && i != 4); // Simulate failures
            circuitBreaker.recordRequest(success);
            System.out.println("  Request " + i + " - " + 
                (success ? "✅ SUCCESS" : "❌ FAILED") + 
                " | Circuit: " + circuitBreaker.getState());
            Thread.sleep(100);
        }

        // ============================================================
        // PATTERN 3: Event-Driven System
        // ============================================================

        System.out.println("\n===== PATTERN 3: Event-Driven System =====\n");

        EventBus eventBus = new EventBus();

        // Register event handlers
        eventBus.subscribe("ORDER_CREATED", event -> 
            System.out.println("  📧 Sending email for: " + event));
        eventBus.subscribe("ORDER_CREATED", event -> 
            System.out.println("  💰 Processing payment for: " + event));
        eventBus.subscribe("PAYMENT_SUCCESS", event -> 
            System.out.println("  📦 Initiating shipment for: " + event));

        // Publish events
        eventBus.publish(new Event("ORDER_CREATED", "Order#123"));
        eventBus.publish(new Event("PAYMENT_SUCCESS", "Order#123"));
        
        Thread.sleep(500); // Let async handlers complete
        eventBus.shutdown();

        // ============================================================
        // PATTERN 4: Batch Processing
        // ============================================================

        System.out.println("\n===== PATTERN 4: Batch Processing =====\n");

        BatchProcessor batchProcessor = new BatchProcessor(5, 2000); // 5 items or 2 seconds
        batchProcessor.start();

        // Add items
        for (int i = 1; i <= 12; i++) {
            batchProcessor.addItem("Item-" + i);
            Thread.sleep(300);
        }

        Thread.sleep(3000); // Wait for final batch
        batchProcessor.stop();

        // ============================================================
        // PATTERN 5: Load Balancer (Round Robin)
        // ============================================================

        System.out.println("\n===== PATTERN 5: Load Balancer =====\n");

        LoadBalancer loadBalancer = new LoadBalancer(3);

        // Simulate 10 requests
        for (int i = 1; i <= 10; i++) {
            String server = loadBalancer.getNextServer();
            System.out.println("  Request " + i + " → " + server);
        }

        // ============================================================
        // PATTERN 6: Message Deduplication
        // ============================================================

        System.out.println("\n===== PATTERN 6: Message Deduplication =====\n");

        DeduplicationQueue<String> dedupQueue = new DeduplicationQueue<>();

        // Add messages (some duplicates)
        String[] messages = {"MSG1", "MSG2", "MSG1", "MSG3", "MSG2", "MSG4"};
        
        System.out.println("Adding messages (with duplicates):");
        for (String msg : messages) {
            boolean added = dedupQueue.offer(msg);
            System.out.println("  " + msg + " - " + (added ? "✅ ADDED" : "❌ DUPLICATE"));
        }

        System.out.println("\nProcessing unique messages:");
        while (!dedupQueue.isEmpty()) {
            System.out.println("  Processing: " + dedupQueue.poll());
        }

        // ============================================================
        // PATTERN 7: Dead Letter Queue (DLQ)
        // ============================================================

        System.out.println("\n===== PATTERN 7: Dead Letter Queue =====\n");

        QueueWithDLQ messageQueue = new QueueWithDLQ(3); // Max 3 retries

        // Process messages (some will fail)
        messageQueue.addMessage(new Message("MSG1", true));  // Will succeed
        messageQueue.addMessage(new Message("MSG2", false)); // Will fail and go to DLQ
        messageQueue.addMessage(new Message("MSG3", true));  // Will succeed

        messageQueue.processMessages();

        System.out.println("\nMessages in Dead Letter Queue:");
        messageQueue.showDLQ();

        // ============================================================
        // PATTERN 8: Request Coalescing
        // ============================================================

        System.out.println("\n===== PATTERN 8: Request Coalescing =====\n");

        RequestCoalescer coalescer = new RequestCoalescer(100); // 100ms window

        // Multiple requests for same data
        coalescer.request("USER:123", result -> 
            System.out.println("  Caller 1 got: " + result));
        coalescer.request("USER:123", result -> 
            System.out.println("  Caller 2 got: " + result));
        coalescer.request("USER:456", result -> 
            System.out.println("  Caller 3 got: " + result));
        coalescer.request("USER:123", result -> 
            System.out.println("  Caller 4 got: " + result));

        Thread.sleep(500);
        coalescer.shutdown();

        // ============================================================
        // PATTERN 9: Time-based Expiration
        // ============================================================

        System.out.println("\n===== PATTERN 9: Time-based Expiration =====\n");

        ExpiringQueue<String> expiringQueue = new ExpiringQueue<>(2000); // 2 second TTL

        expiringQueue.offer("Item1");
        expiringQueue.offer("Item2");
        System.out.println("Added 2 items");

        Thread.sleep(1000);
        System.out.println("After 1s, queue size: " + expiringQueue.size());

        Thread.sleep(1500);
        System.out.println("After 2.5s, queue size (should be empty): " + expiringQueue.size());

        // ============================================================
        // PATTERN 10: Multi-level Priority Queue
        // ============================================================

        System.out.println("\n===== PATTERN 10: Multi-level Priority Queue =====\n");

        MultiLevelPriorityQueue mlpq = new MultiLevelPriorityQueue();

        mlpq.addTask(new APPriorityTask("Urgent bug fix", 1));
        mlpq.addTask(new APPriorityTask("Feature request", 3));
        mlpq.addTask(new APPriorityTask("Critical security patch", 1));
        mlpq.addTask(new APPriorityTask("Code review", 2));
        mlpq.addTask(new APPriorityTask("Documentation", 3));

        System.out.println("Processing tasks by priority:");
        APPriorityTask task;
        int count = 1;
        while ((task = mlpq.getNextTask()) != null) {
            System.out.println("  " + count++ + ". " + task);
        }

        System.out.println("\n===== ALL ADVANCED PATTERNS COMPLETE =====");
    }
}

/**
 * PATTERN 1: Rate Limiter using Sliding Window
 * Industry use: API throttling, DDoS prevention
 */
class RateLimiter {
    private final int maxRequests;
    private final long windowMs;
    private final java.util.Deque<Long> timestamps = new LinkedList<>();

    public RateLimiter(int maxRequests, long windowMs) {
        this.maxRequests = maxRequests;
        this.windowMs = windowMs;
    }

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();
        
        // Remove expired timestamps
        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > windowMs) {
            timestamps.pollFirst();
        }

        // Check if under limit
        if (timestamps.size() < maxRequests) {
            timestamps.offerLast(now);
            return true;
        }

        return false;
    }
}

/**
 * PATTERN 2: Circuit Breaker Pattern
 * Industry use: Microservices resilience, prevent cascading failures
 */
class CircuitBreakerQueue {
    private enum State { CLOSED, OPEN, HALF_OPEN }
    
    private State state = State.CLOSED;
    private final int failureThreshold;
    private final long resetTimeoutMs;
    private final java.util.Deque<Boolean> recentRequests = new LinkedList<>();
    private long openedAt = 0;

    public CircuitBreakerQueue(int failureThreshold, long resetTimeoutMs) {
        this.failureThreshold = failureThreshold;
        this.resetTimeoutMs = resetTimeoutMs;
    }

    public synchronized void recordRequest(boolean success) {
        if (state == State.OPEN) {
            // Check if timeout expired
            if (System.currentTimeMillis() - openedAt > resetTimeoutMs) {
                state = State.HALF_OPEN;
                recentRequests.clear();
            } else {
                return; // Circuit still open
            }
        }

        recentRequests.offerLast(success);
        if (recentRequests.size() > 10) {
            recentRequests.pollFirst();
        }

        // Count recent failures
        long failures = recentRequests.stream().filter(s -> !s).count();

        if (failures >= failureThreshold) {
            state = State.OPEN;
            openedAt = System.currentTimeMillis();
        } else if (state == State.HALF_OPEN && success) {
            state = State.CLOSED;
        }
    }

    public State getState() {
        return state;
    }
}

/**
 * PATTERN 3: Event-Driven System
 * Industry use: Microservices, event sourcing, CQRS
 */
class EventBus {
    private final Map<String, List<EventHandler>> handlers = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void subscribe(String eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(handler);
    }

    public void publish(Event event) {
        List<EventHandler> eventHandlers = handlers.get(event.type);
        if (eventHandlers != null) {
            for (EventHandler handler : eventHandlers) {
                executor.submit(() -> handler.handle(event));
            }
        }
    }

    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}

class Event {
    String type;
    String data;

    public Event(String type, String data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return type + ": " + data;
    }
}

interface EventHandler {
    void handle(Event event);
}

/**
 * PATTERN 4: Batch Processor
 * Industry use: Database bulk inserts, log aggregation
 */
class BatchProcessor {
    private final int batchSize;
    private final long batchTimeoutMs;
    private final Queue<String> buffer = new LinkedList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private long lastProcessTime = System.currentTimeMillis();

    public BatchProcessor(int batchSize, long batchTimeoutMs) {
        this.batchSize = batchSize;
        this.batchTimeoutMs = batchTimeoutMs;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkAndProcess, 100, 100, TimeUnit.MILLISECONDS);
    }

    public synchronized void addItem(String item) {
        buffer.offer(item);
        if (buffer.size() >= batchSize) {
            processBatch();
        }
    }

    private synchronized void checkAndProcess() {
        if (!buffer.isEmpty() && 
            System.currentTimeMillis() - lastProcessTime > batchTimeoutMs) {
            processBatch();
        }
    }

    private void processBatch() {
        if (buffer.isEmpty()) return;

        List<String> batch = new ArrayList<>(buffer);
        buffer.clear();
        lastProcessTime = System.currentTimeMillis();

        System.out.println("  📦 Processing batch of " + batch.size() + " items: " + batch);
    }

    public void stop() {
        scheduler.shutdown();
        processBatch(); // Process remaining items
    }
}

/**
 * PATTERN 5: Load Balancer
 * Industry use: Distributing requests across servers
 */
class LoadBalancer {
    private final Queue<String> servers = new LinkedList<>();

    public LoadBalancer(int numServers) {
        for (int i = 1; i <= numServers; i++) {
            servers.offer("Server-" + i);
        }
    }

    public String getNextServer() {
        String server = servers.poll();
        servers.offer(server); // Round-robin: move to back
        return server;
    }
}

/**
 * PATTERN 6: Message Deduplication
 * Industry use: At-least-once delivery systems (Kafka, SQS)
 */
class DeduplicationQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final Set<T> seen = new HashSet<>();

    public synchronized boolean offer(T item) {
        if (seen.contains(item)) {
            return false; // Duplicate
        }
        seen.add(item);
        return queue.offer(item);
    }

    public synchronized T poll() {
        T item = queue.poll();
        if (item != null) {
            seen.remove(item); // Allow re-adding after processing
        }
        return item;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * PATTERN 7: Dead Letter Queue
 * Industry use: Failed message handling (AWS SQS DLQ, RabbitMQ)
 */
class QueueWithDLQ {
    private final Queue<Message> mainQueue = new LinkedList<>();
    private final Queue<Message> deadLetterQueue = new LinkedList<>();
    private final int maxRetries;

    public QueueWithDLQ(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void addMessage(Message msg) {
        mainQueue.offer(msg);
    }

    public void processMessages() {
        while (!mainQueue.isEmpty()) {
            Message msg = mainQueue.poll();
            
            if (msg.willSucceed) {
                System.out.println("  ✅ Processed: " + msg.id);
            } else {
                msg.retries++;
                if (msg.retries >= maxRetries) {
                    deadLetterQueue.offer(msg);
                    System.out.println("  ❌ Failed after " + maxRetries + " retries, moved to DLQ: " + msg.id);
                } else {
                    mainQueue.offer(msg); // Retry
                    System.out.println("  🔄 Retrying: " + msg.id + " (attempt " + msg.retries + ")");
                }
            }
        }
    }

    public void showDLQ() {
        deadLetterQueue.forEach(msg -> System.out.println("  - " + msg.id));
    }
}

class Message {
    String id;
    boolean willSucceed;
    int retries = 0;

    public Message(String id, boolean willSucceed) {
        this.id = id;
        this.willSucceed = willSucceed;
    }
}

/**
 * PATTERN 8: Request Coalescing
 * Industry use: GraphQL DataLoader, batch database queries
 */
class RequestCoalescer {
    private final Map<String, List<Consumer<String>>> pendingRequests = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public RequestCoalescer(long coalescingWindowMs) {
        scheduler.scheduleAtFixedRate(this::processBatch, coalescingWindowMs, 
            coalescingWindowMs, TimeUnit.MILLISECONDS);
    }

    public void request(String key, Consumer<String> callback) {
        pendingRequests.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).add(callback);
    }

    private void processBatch() {
        if (pendingRequests.isEmpty()) return;

        System.out.println("  🔄 Coalescing " + pendingRequests.size() + " unique requests");
        
        pendingRequests.forEach((key, callbacks) -> {
            // Simulate fetching data once for all requests
            String result = "Data for " + key;
            System.out.println("    Fetching: " + key + " (serves " + callbacks.size() + " callers)");
            callbacks.forEach(callback -> callback.accept(result));
        });

        pendingRequests.clear();
    }

    public void shutdown() {
        processBatch(); // Process remaining
        scheduler.shutdown();
    }
}

/**
 * PATTERN 9: Expiring Queue
 * Industry use: Session management, cache invalidation
 */
class ExpiringQueue<T> {
    private final Queue<ExpiringItem<T>> queue = new LinkedList<>();
    private final long ttlMs;

    public ExpiringQueue(long ttlMs) {
        this.ttlMs = ttlMs;
    }

    public synchronized void offer(T item) {
        queue.offer(new ExpiringItem<>(item, System.currentTimeMillis() + ttlMs));
    }

    public synchronized T poll() {
        removeExpired();
        ExpiringItem<T> item = queue.poll();
        return item != null ? item.value : null;
    }

    public synchronized int size() {
        removeExpired();
        return queue.size();
    }

    private void removeExpired() {
        long now = System.currentTimeMillis();
        while (!queue.isEmpty() && queue.peek().expiryTime < now) {
            queue.poll();
        }
    }

    private static class ExpiringItem<T> {
        T value;
        long expiryTime;

        ExpiringItem(T value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }
}

/**
 * PATTERN 10: Multi-level Priority Queue
 * Industry use: OS task scheduling, game AI
 */
class MultiLevelPriorityQueue {
    private final Map<Integer, Queue<APPriorityTask>> levels = new HashMap<>();

    public void addTask(APPriorityTask task) {
        levels.computeIfAbsent(task.priority, k -> new LinkedList<>()).offer(task);
    }

    public APPriorityTask getNextTask() {
        // Process highest priority first
        for (int priority = 1; priority <= 3; priority++) {
            Queue<APPriorityTask> queue = levels.get(priority);
            if (queue != null && !queue.isEmpty()) {
                return queue.poll();
            }
        }
        return null;
    }
}

/**
 * Task with priority for AdvancedPatterns
 */
class APPriorityTask {
    String description;
    int priority;

    public APPriorityTask(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[P" + priority + "] " + description;
    }
}

/*
 * ============================================================
 *  KEY TAKEAWAYS - ADVANCED PATTERNS
 * ============================================================
 *
 *  ✅ Rate Limiting: Sliding window with Deque
 *  ✅ Circuit Breaker: Prevent cascading failures
 *  ✅ Event-Driven: Decouple producers and consumers
 *  ✅ Batch Processing: Optimize bulk operations
 *  ✅ Load Balancing: Distribute work evenly
 *  ✅ Deduplication: Handle duplicate messages
 *  ✅ Dead Letter Queue: Handle permanent failures
 *  ✅ Request Coalescing: Reduce redundant operations
 *  ✅ Expiring Queue: Automatic TTL management
 *  ✅ Multi-level Priority: Complex scheduling needs
 *
 * ============================================================
 *  INDUSTRY APPLICATIONS
 * ============================================================
 *
 *  AWS SQS: Message queue with DLQ support
 *  Kafka: Event streaming (partitioned queues)
 *  RabbitMQ: Message broker (exchanges + queues)
 *  Redis: In-memory queue (lists, streams)
 *  Akka: Actor mailboxes (queues)
 *  Netty: Network events (channel pipeline = queue)
 *  Spring: @Async with TaskExecutor (queue-based)
 *  ThreadPoolExecutor: Work queue for threads
 *
 * ============================================================
 *  BEST PRACTICES SUMMARY
 * ============================================================
 *
 *  1. Choose right queue type for your use case
 *  2. Always handle InterruptedException properly
 *  3. Use bounded queues to prevent OOM
 *  4. Monitor queue depth (alerts when too full)
 *  5. Implement DLQ for failed messages
 *  6. Use timeouts for critical systems
 *  7. Consider backpressure mechanisms
 *  8. Test under load (stress testing)
 *  9. Log queue metrics (size, throughput, latency)
 *  10. Document expected behavior and failure modes
 *
 */
