package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ==========================================
 * PRODUCER-CONSUMER PATTERN
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Producer = Data create karta hai
 * Consumer = Data process karta hai
 * Queue = Dono ke beech buffer
 * 
 * REAL ANALOGY:
 * Restaurant kitchen:
 * Chef (Producer) → Orders on counter (Queue) → Waiter (Consumer)
 * 
 * INDUSTRY USAGE:
 * - Message queues (RabbitMQ, Kafka)
 * - Task processing (Celery, Sidekiq)
 * - Log aggregation (ELK stack)
 * - Video processing (YouTube uploads)
 * - Email sending (async queues)
 * 
 * WHY THIS PATTERN?
 * - Decouple producers from consumers
 * - Handle burst traffic (queue absorbs spikes)
 * - Process async (faster response times)
 * - Scale independently (more workers)
 * - Retry failed tasks
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
 class ProducerConsumer {
    
    public static void main(String[] args) throws Exception {
        demonstrateBasicPattern();
        demonstrateBlockingQueue();
        demonstratePriorityQueue();
        demonstrateWorkStealing();
        demonstrateRealWorldExample();
    }
    
    /**
     * BASIC PRODUCER-CONSUMER
     * =======================
     * Classic implementation with wait/notify
     * 
     * NOTE: This is educational - production code uses BlockingQueue!
     */
    static class SharedBuffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;
        
        SharedBuffer(int capacity) {
            this.capacity = capacity;
        }
        
        public synchronized void produce(int item) throws InterruptedException {
            // Wait if queue is full
            while (queue.size() == capacity) {
                wait(); // Release lock and wait
            }
            
            queue.add(item);
            notifyAll(); // Wake up consumers
        }
        
        public synchronized int consume() throws InterruptedException {
            // Wait if queue is empty
            while (queue.isEmpty()) {
                wait(); // Release lock and wait
            }
            
            int item = queue.poll();
            notifyAll(); // Wake up producers
            return item;
        }
    }
    
    static void demonstrateBasicPattern() throws Exception {
        SharedBuffer buffer = new SharedBuffer(5);
        
        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.produce(i);
                    Thread.sleep(100); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    int item = buffer.consume();
                    Thread.sleep(150); // Simulate processing
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
    
    /**
     * BLOCKING QUEUE - PRODUCTION READY
     * ==================================
     * 
     * Java's BlockingQueue handles all synchronization!
     * Much simpler and safer than manual wait/notify
     * 
     * TYPES:
     * - ArrayBlockingQueue: Bounded, array-based
     * - LinkedBlockingQueue: Optionally bounded, linked-list
     * - PriorityBlockingQueue: Priority-based ordering
     * - SynchronousQueue: No capacity, direct handoff
     * - DelayQueue: Elements available after delay
     */
    static class Task {
        final int id;
        final String data;
        
        Task(int id, String data) {
            this.id = id;
            this.data = data;
        }
        
        @Override
        public String toString() {
            return String.format("Task-%d(%s)", id, data);
        }
    }
    
    static class TaskProducer implements Runnable {
        private final BlockingQueue<Task> queue;
        private final int taskCount;
        
        TaskProducer(BlockingQueue<Task> queue, int taskCount) {
            this.queue = queue;
            this.taskCount = taskCount;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 1; i <= taskCount; i++) {
                    Task task = new Task(i, "Data-" + i);
                    queue.put(task); // Blocks if queue is full
                    Thread.sleep(50); // Simulate creation time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class TaskConsumer implements Runnable {
        private final BlockingQueue<Task> queue;
        private final int consumerId;
        private final AtomicInteger processedCount;
        
        TaskConsumer(BlockingQueue<Task> queue, int consumerId, AtomicInteger counter) {
            this.queue = queue;
            this.consumerId = consumerId;
            this.processedCount = counter;
        }
        
        @Override
        public void run() {
            try {
                while (true) {
                    Task task = queue.poll(1, TimeUnit.SECONDS); // Wait max 1 sec
                    if (task == null) break; // Timeout, assume done
                    
                    // Process task
                    Thread.sleep(100); // Simulate processing
                    processedCount.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static void demonstrateBlockingQueue() throws Exception {
        // Bounded queue - max 10 tasks
        BlockingQueue<Task> queue = new ArrayBlockingQueue<>(10);
        AtomicInteger processed = new AtomicInteger(0);
        
        int TASK_COUNT = 20;
        int CONSUMER_COUNT = 3;
        
        // Start producer
        Thread producer = new Thread(new TaskProducer(queue, TASK_COUNT));
        producer.start();
        
        // Start multiple consumers (load balancing!)
        List<Thread> consumers = new ArrayList<>();
        for (int i = 1; i <= CONSUMER_COUNT; i++) {
            Thread consumer = new Thread(new TaskConsumer(queue, i, processed));
            consumers.add(consumer);
            consumer.start();
        }
        
        // Wait for completion
        producer.join();
        for (Thread consumer : consumers) {
            consumer.join();
        }
        
        assert processed.get() == TASK_COUNT;
    }
    
    /**
     * PRIORITY QUEUE - TASK PRIORITIZATION
     * =====================================
     * 
     * Process high-priority tasks first!
     * Like hospital ER - critical patients first
     */
    static class PriorityTask implements Comparable<PriorityTask> {
        enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
        
        final String name;
        final Priority priority;
        final long timestamp;
        
        PriorityTask(String name, Priority priority) {
            this.name = name;
            this.priority = priority;
            this.timestamp = System.nanoTime();
        }
        
        @Override
        public int compareTo(PriorityTask other) {
            // Higher priority first
            int priorityCompare = other.priority.compareTo(this.priority);
            if (priorityCompare != 0) return priorityCompare;
            
            // Same priority? FIFO (earlier timestamp first)
            return Long.compare(this.timestamp, other.timestamp);
        }
        
        @Override
        public String toString() {
            return String.format("%s[%s]", name, priority);
        }
    }
    
    static void demonstratePriorityQueue() throws Exception {
        // Priority-based processing
        BlockingQueue<PriorityTask> queue = new PriorityBlockingQueue<>();
        
        // Add tasks in random order
        queue.put(new PriorityTask("Task1", PriorityTask.Priority.LOW));
        queue.put(new PriorityTask("Task2", PriorityTask.Priority.CRITICAL));
        queue.put(new PriorityTask("Task3", PriorityTask.Priority.MEDIUM));
        queue.put(new PriorityTask("Task4", PriorityTask.Priority.HIGH));
        
        // Consumer processes by priority
        List<PriorityTask> processed = new ArrayList<>();
        while (!queue.isEmpty()) {
            processed.add(queue.take());
        }
        
        // Verify order: CRITICAL > HIGH > MEDIUM > LOW
        assert processed.get(0).priority == PriorityTask.Priority.CRITICAL;
        assert processed.get(1).priority == PriorityTask.Priority.HIGH;
        assert processed.get(2).priority == PriorityTask.Priority.MEDIUM;
        assert processed.get(3).priority == PriorityTask.Priority.LOW;
    }
    
    /**
     * WORK STEALING PATTERN
     * ======================
     * 
     * Each consumer has own queue
     * Idle consumers "steal" work from busy ones
     * 
     * Used by: Java ForkJoinPool, Go goroutines
     */
    static class WorkStealingPool {
        private final List<Deque<Task>> workerQueues;
        private final List<Thread> workers;
        private final AtomicInteger nextWorker = new AtomicInteger(0);
        
        WorkStealingPool(int workerCount) {
            workerQueues = new ArrayList<>();
            workers = new ArrayList<>();
            
            for (int i = 0; i < workerCount; i++) {
                Deque<Task> queue = new LinkedBlockingDeque<>();
                workerQueues.add(queue);
                
                Thread worker = new Thread(new Worker(i, queue, workerQueues));
                workers.add(worker);
                worker.start();
            }
        }
        
        public void submit(Task task) {
            // Round-robin assignment
            int workerIndex = nextWorker.getAndIncrement() % workerQueues.size();
            workerQueues.get(workerIndex).offer(task);
        }
        
        public void shutdown() throws InterruptedException {
            for (Thread worker : workers) {
                worker.interrupt();
            }
            for (Thread worker : workers) {
                worker.join();
            }
        }
        
        static class Worker implements Runnable {
            private final int id;
            private final Deque<Task> myQueue;
            private final List<Deque<Task>> allQueues;
            
            Worker(int id, Deque<Task> myQueue, List<Deque<Task>> allQueues) {
                this.id = id;
                this.myQueue = myQueue;
                this.allQueues = allQueues;
            }
            
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    Task task = myQueue.poll(); // Try own queue first
                    
                    if (task == null) {
                        // Own queue empty? Steal from others!
                        task = stealWork();
                    }
                    
                    if (task != null) {
                        try {
                            Thread.sleep(50); // Process task
                        } catch (InterruptedException e) {
                            break;
                        }
                    } else {
                        // No work anywhere, wait a bit
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
            
            private Task stealWork() {
                // Try stealing from each worker (except self)
                for (int i = 0; i < allQueues.size(); i++) {
                    if (i == id) continue;
                    
                    Deque<Task> otherQueue = allQueues.get(i);
                    Task stolen = otherQueue.pollLast(); // Steal from back
                    if (stolen != null) {
                        return stolen;
                    }
                }
                return null;
            }
        }
    }
    
    static void demonstrateWorkStealing() throws Exception {
        WorkStealingPool pool = new WorkStealingPool(3);
        
        // Submit 20 tasks
        for (int i = 1; i <= 20; i++) {
            pool.submit(new Task(i, "Data-" + i));
        }
        
        // Let workers process
        Thread.sleep(2000);
        pool.shutdown();
    }
    
    /**
     * REAL-WORLD EXAMPLE: EMAIL SENDING SERVICE
     * ==========================================
     * 
     * Scenario: E-commerce sends thousands of emails
     * - Order confirmations
     * - Shipping notifications
     * - Marketing emails
     * 
     * Requirements:
     * - Don't block web requests
     * - Retry failures
     * - Prioritize transactional emails
     */
    static class EmailMessage {
        enum Type { TRANSACTIONAL, MARKETING }
        
        final String to;
        final String subject;
        final String body;
        final Type type;
        final int retryCount;
        
        EmailMessage(String to, String subject, String body, Type type) {
            this(to, subject, body, type, 0);
        }
        
        EmailMessage(String to, String subject, String body, Type type, int retryCount) {
            this.to = to;
            this.subject = subject;
            this.body = body;
            this.type = type;
            this.retryCount = retryCount;
        }
        
        EmailMessage retry() {
            return new EmailMessage(to, subject, body, type, retryCount + 1);
        }
    }
    
    static class EmailService {
        private final BlockingQueue<EmailMessage> emailQueue;
        private final BlockingQueue<EmailMessage> retryQueue;
        private final ExecutorService workers;
        private final int MAX_RETRIES = 3;
        
        EmailService(int workerCount) {
            // Separate queues for priority handling
            emailQueue = new PriorityBlockingQueue<>(100, 
                (e1, e2) -> e1.type.compareTo(e2.type)); // Transactional first
            
            retryQueue = new LinkedBlockingQueue<>();
            workers = Executors.newFixedThreadPool(workerCount);
            
            // Start workers
            for (int i = 0; i < workerCount; i++) {
                workers.submit(new EmailWorker());
            }
        }
        
        public void sendAsync(EmailMessage email) {
            emailQueue.offer(email);
        }
        
        private boolean sendEmail(EmailMessage email) {
            // Simulate sending (80% success rate)
            return Math.random() > 0.2;
        }
        
        class EmailWorker implements Runnable {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // Check retry queue first
                        EmailMessage email = retryQueue.poll(10, TimeUnit.MILLISECONDS);
                        if (email == null) {
                            // No retries, process new emails
                            email = emailQueue.poll(100, TimeUnit.MILLISECONDS);
                        }
                        
                        if (email != null) {
                            boolean success = sendEmail(email);
                            
                            if (!success && email.retryCount < MAX_RETRIES) {
                                // Failed, retry later
                                retryQueue.offer(email.retry());
                            }
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }
        
        public void shutdown() {
            workers.shutdownNow();
        }
    }
    
    static void demonstrateRealWorldExample() throws Exception {
        EmailService emailService = new EmailService(3);
        
        // Simulate email load
        emailService.sendAsync(new EmailMessage(
            "user@example.com", 
            "Order Confirmation", 
            "Your order #123", 
            EmailMessage.Type.TRANSACTIONAL
        ));
        
        emailService.sendAsync(new EmailMessage(
            "user@example.com", 
            "Sale Alert", 
            "50% off today!", 
            EmailMessage.Type.MARKETING
        ));
        
        // Let workers process
        Thread.sleep(1000);
        emailService.shutdown();
    }
}

/*
 * ==========================================
 * QUEUE SELECTION GUIDE
 * ==========================================
 * 
 * ArrayBlockingQueue:
 * + Fixed capacity, predictable memory
 * + Better performance for bounded queues
 * - Capacity must be known upfront
 * Use: Fixed-size thread pools
 * 
 * LinkedBlockingQueue:
 * + Optionally bounded (can be infinite)
 * + Good for variable load
 * - Slightly slower than array-based
 * Use: General-purpose producer-consumer
 * 
 * PriorityBlockingQueue:
 * + Process by priority, not insertion order
 * + Unbounded (grows as needed)
 * - No FIFO guarantee within priority
 * Use: Task prioritization, job scheduling
 * 
 * SynchronousQueue:
 * + Zero capacity - direct handoff
 * + Good for work handoff patterns
 * - Producer blocks until consumer ready
 * Use: Cached thread pools, work transfer
 * 
 * DelayQueue:
 * + Elements available after delay
 * + Good for scheduling
 * - Requires Delayed interface
 * Use: Scheduled tasks, rate limiting
 * 
 * PRODUCTION TIPS:
 * ================
 * 1. BOUNDED QUEUES:
 *    Always set capacity! Prevents OOM
 *    queue = new ArrayBlockingQueue<>(1000);
 * 
 * 2. BACKPRESSURE:
 *    What if queue fills up?
 *    - Block producer (put())
 *    - Reject with policy (offer() + handle false)
 *    - Drop oldest (custom logic)
 * 
 * 3. POISON PILL:
 *    Signal consumers to stop gracefully
 *    queue.put(POISON_PILL);
 * 
 * 4. MONITORING:
 *    Track queue size, processing rate
 *    Alert if queue keeps growing
 * 
 * 5. ERROR HANDLING:
 *    Failed tasks → retry queue
 *    Max retries → dead letter queue
 * 
 * COMMON MISTAKES:
 * ================
 * ❌ Unbounded queues (memory leak risk)
 * ❌ Not handling InterruptedException properly
 * ❌ Blocking forever (use timeout: poll(timeout))
 * ❌ No retry mechanism for failures
 * ❌ Not monitoring queue metrics
 * 
 * NEXT: 06_ThreadSafeCollections.java
 * (ConcurrentHashMap, CopyOnWriteArrayList, etc.)
 */
