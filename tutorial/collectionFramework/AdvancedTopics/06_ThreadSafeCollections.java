package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/**
 * ==========================================
 * THREAD-SAFE COLLECTIONS - Concurrent Programming
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Multi-threading mein regular collections use karna = DISASTER! 💥
 * ConcurrentModificationException, data corruption, race conditions!
 * 
 * Thread-safe collections are MANDATORY for production apps
 * 
 * REAL SCENARIOS:
 * - Web servers handling multiple requests
 * - Chat applications (multiple users)
 * - Stock trading systems
 * - Real-time analytics
 * 
 * WHY THREAD-SAFE COLLECTIONS?
 * - No manual synchronization needed
 * - Better performance than Collections.synchronizedX()
 * - Lock-free algorithms (some)
 * - Industry standard for concurrent apps
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class ThreadSafeCollections {
    
    public static void main(String[] args) throws InterruptedException {
        demonstrateProblemWithRegularCollections();
        demonstrateConcurrentHashMap();
        demonstrateCopyOnWriteArrayList();
        demonstrateBlockingQueue();
        demonstrateConcurrentSkipListMap();
        demonstrateCustomThreadSafeCollection();
    }
    
    /**
     * THE PROBLEM - Why we need thread-safe collections
     * ==================================================
     * 
     * Regular collections fail with multiple threads!
     */
    static void demonstrateProblemWithRegularCollections() throws InterruptedException {
        // PROBLEM: Regular HashMap + multiple threads = CRASH
        Map<String, Integer> unsafeMap = new HashMap<>();
        
        // 10 threads trying to add data simultaneously
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    try {
                        unsafeMap.put("key" + threadId + "_" + j, j);
                        // This can throw ConcurrentModificationException
                        // Or worse - silent data corruption!
                    } catch (Exception e) {
                        // ConcurrentModificationException caught
                    }
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        // Expected: 1000 entries (10 threads * 100 each)
        // Actual: Less than 1000 due to race conditions!
        // Data might be corrupted or lost
    }
    
    /**
     * CONCURRENTHASHMAP - The Production Standard
     * ============================================
     * 
     * FEATURES:
     * - Thread-safe without locking entire map
     * - Segment-based locking (high concurrency)
     * - null keys/values NOT allowed
     * - Atomic operations (putIfAbsent, compute, etc.)
     * 
     * WHEN TO USE:
     * - Shared cache across threads
     * - Session management
     * - Configuration storage
     * - High-read, moderate-write scenarios
     * 
     * PERFORMANCE:
     * - Reads: No locking (super fast!)
     * - Writes: Fine-grained locks
     * - Much faster than Collections.synchronizedMap()
     */
    static void demonstrateConcurrentHashMap() throws InterruptedException {
        ConcurrentHashMap<String, Integer> safeMap = new ConcurrentHashMap<>();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        // 10 threads adding data concurrently
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    safeMap.put("key" + threadId + "_" + j, j);
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        // Perfect! All 1000 entries are there
        assert safeMap.size() == 1000;
        
        // ATOMIC OPERATIONS - Game changers!
        
        // 1. putIfAbsent - Add only if key doesn't exist
        Integer previous = safeMap.putIfAbsent("counter", 0);
        assert previous == null; // First time, no previous value
        
        previous = safeMap.putIfAbsent("counter", 100);
        assert previous == 0; // Key exists, returns existing value
        
        // 2. compute - Atomic update
        safeMap.compute("counter", (key, oldValue) -> {
            return oldValue == null ? 1 : oldValue + 1;
        });
        assert safeMap.get("counter") == 1;
        
        // 3. computeIfAbsent - Lazy initialization pattern
        safeMap.computeIfAbsent("lazy", k -> {
            // Expensive computation happens only once
            return expensiveComputation();
        });
        
        // 4. merge - Combine values atomically
        safeMap.merge("score", 10, Integer::sum); // Add 10
        safeMap.merge("score", 20, Integer::sum); // Add 20 more
        assert safeMap.get("score") == 30;
    }
    
    static int expensiveComputation() {
        // Simulate expensive operation
        return 42;
    }
    
    /**
     * COPYONWRITEARRAYLIST - For Read-Heavy Scenarios
     * ================================================
     * 
     * CONCEPT:
     * Every write creates a new copy of array
     * Reads happen on old array (no locking!)
     * 
     * PROS:
     * - Blazing fast reads (no synchronization)
     * - Iterator never throws ConcurrentModificationException
     * - Thread-safe without explicit locking
     * 
     * CONS:
     * - Writes are expensive (full array copy!)
     * - Uses more memory
     * - Stale reads possible (but usually acceptable)
     * 
     * WHEN TO USE:
     * - Event listeners (add rarely, iterate often)
     * - Observer pattern subscribers
     * - Configuration lists
     * - Read-to-write ratio > 10:1
     * 
     * WHEN NOT TO USE:
     * - Frequent writes (use ConcurrentHashMap instead)
     * - Large lists (memory overhead)
     * - Need strong consistency
     */
    static void demonstrateCopyOnWriteArrayList() throws InterruptedException {
        CopyOnWriteArrayList<String> safeList = new CopyOnWriteArrayList<>();
        
        // Add initial data
        safeList.add("Item1");
        safeList.add("Item2");
        safeList.add("Item3");
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        // Reader threads - iterate continuously
        for (int i = 0; i < 3; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    // Safe iteration - no ConcurrentModificationException!
                    for (String item : safeList) {
                        // Process item
                    }
                }
            });
        }
        
        // Writer threads - modify occasionally
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    safeList.add("NewItem" + threadId + "_" + j);
                    try {
                        Thread.sleep(10); // Simulate infrequent writes
                    } catch (InterruptedException e) {}
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        // All items safely added
        assert safeList.size() == 23; // 3 initial + 20 added
    }
    
    /**
     * BLOCKINGQUEUE - Producer-Consumer Pattern
     * ==========================================
     * 
     * TYPES:
     * - ArrayBlockingQueue: Bounded, FIFO
     * - LinkedBlockingQueue: Optionally bounded
     * - PriorityBlockingQueue: Priority-ordered
     * - DelayQueue: Delayed elements
     * - SynchronousQueue: No storage, direct handoff
     * 
     * BLOCKING OPERATIONS:
     * - put(): Blocks if queue full
     * - take(): Blocks if queue empty
     * - offer(timeout): Tries with timeout
     * - poll(timeout): Tries to retrieve with timeout
     * 
     * PERFECT FOR:
     * - Task queues (thread pools use this!)
     * - Message queues
     * - Work distribution
     * - Rate limiting
     */
    static void demonstrateBlockingQueue() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        
        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    queue.put("Task" + i); // Blocks if queue full
                    Thread.sleep(50); // Simulate task generation
                }
            } catch (InterruptedException e) {}
        });
        
        // Consumer threads
        Thread consumer1 = new Thread(() -> {
            try {
                while (true) {
                    String task = queue.take(); // Blocks if queue empty
                    // Process task
                    if (task.equals("Task19")) break; // Stop condition
                }
            } catch (InterruptedException e) {}
        });
        
        Thread consumer2 = new Thread(() -> {
            try {
                while (true) {
                    String task = queue.poll(100, TimeUnit.MILLISECONDS);
                    if (task == null) break; // Timeout, exit
                    // Process task
                }
            } catch (InterruptedException e) {}
        });
        
        producer.start();
        consumer1.start();
        consumer2.start();
        
        producer.join();
        consumer1.join();
        consumer2.join();
        
        // Queue should be empty after all processing
        assert queue.isEmpty();
    }
    
    /**
     * CONCURRENTSKIPLISTMAP - Sorted + Concurrent
     * ============================================
     * 
     * FEATURES:
     * - Sorted order (like TreeMap)
     * - Thread-safe (like ConcurrentHashMap)
     * - Lock-free algorithm (skip list)
     * - NavigableMap operations
     * 
     * COMPLEXITY:
     * - get/put/remove: O(log n) average
     * - Faster than synchronized TreeMap
     * 
     * WHEN TO USE:
     * - Need sorted keys + concurrency
     * - Range queries (subMap, headMap, tailMap)
     * - Time-series data
     * - Leaderboards
     */
    static void demonstrateConcurrentSkipListMap() throws InterruptedException {
        ConcurrentSkipListMap<Integer, String> leaderboard = new ConcurrentSkipListMap<>();
        
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        // Multiple players updating scores
        for (int i = 0; i < 5; i++) {
            final int playerId = i;
            executor.submit(() -> {
                for (int j = 0; j < 20; j++) {
                    int score = ThreadLocalRandom.current().nextInt(1000);
                    leaderboard.put(score, "Player" + playerId);
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        // Get top 10 scores (automatically sorted!)
        Map<Integer, String> top10 = leaderboard.descendingMap()
            .entrySet()
            .stream()
            .limit(10)
            .collect(LinkedHashMap::new,
                    (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                    Map::putAll);
        
        // Range query: scores between 500-800
        Map<Integer, String> midRange = leaderboard.subMap(500, true, 800, true);
    }
    
    /**
     * CUSTOM THREAD-SAFE COLLECTION
     * ==============================
     * 
     * When standard collections don't fit your needs
     * 
     * TECHNIQUES:
     * 1. Synchronized blocks (coarse-grained locking)
     * 2. ReentrantReadWriteLock (fine-grained)
     * 3. Atomic variables
     * 4. Lock-free algorithms (advanced!)
     */
    static class ThreadSafeCounter {
        private final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();
        
        public void increment(String key) {
            counters.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();
        }
        
        public int get(String key) {
            AtomicInteger counter = counters.get(key);
            return counter == null ? 0 : counter.get();
        }
        
        public Map<String, Integer> snapshot() {
            Map<String, Integer> snap = new HashMap<>();
            counters.forEach((key, counter) -> snap.put(key, counter.get()));
            return snap;
        }
    }
    
    /**
     * Using ReadWriteLock for read-heavy scenarios
     */
    static class ThreadSafeCache<K, V> {
        private final Map<K, V> cache = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public V get(K key) {
            lock.readLock().lock();
            try {
                return cache.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }
        
        public void put(K key, V value) {
            lock.writeLock().lock();
            try {
                cache.put(key, value);
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        public void clear() {
            lock.writeLock().lock();
            try {
                cache.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    static void demonstrateCustomThreadSafeCollection() throws InterruptedException {
        ThreadSafeCounter counter = new ThreadSafeCounter();
        
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        // 10 threads incrementing counters
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    counter.increment("page_views");
                    counter.increment("api_calls");
                }
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        // Perfect count: 10 threads * 100 = 1000
        assert counter.get("page_views") == 1000;
        assert counter.get("api_calls") == 1000;
    }
}

/*
 * ==========================================
 * THREAD-SAFE COLLECTIONS COMPARISON
 * ==========================================
 * 
 * Collection              | Read      | Write     | Use Case
 * ------------------------|-----------|-----------|------------------
 * ConcurrentHashMap       | O(1) fast | O(1) good | General purpose
 * CopyOnWriteArrayList    | O(1) fast | O(n) slow | Read-heavy
 * CopyOnWriteArraySet     | O(n) slow | O(n) slow | Small sets
 * ConcurrentSkipListMap   | O(log n)  | O(log n)  | Sorted + concurrent
 * BlockingQueue           | O(1)      | O(1)      | Producer-consumer
 * ConcurrentLinkedQueue   | O(1)      | O(1)      | Non-blocking queue
 * 
 * 
 * CHOOSING THE RIGHT ONE:
 * =======================
 * 
 * Map needed?
 *   ├─ Need sorting? → ConcurrentSkipListMap
 *   └─ No sorting? → ConcurrentHashMap
 * 
 * List needed?
 *   ├─ Read >> Write? → CopyOnWriteArrayList
 *   ├─ FIFO queue? → ConcurrentLinkedQueue
 *   └─ Blocking queue? → BlockingQueue variants
 * 
 * Set needed?
 *   └─ Small size? → CopyOnWriteArraySet
 *       (Use ConcurrentHashMap.newKeySet() for large sets)
 * 
 * 
 * COMMON MISTAKES:
 * ================
 * ❌ Using Collections.synchronizedX() (slower!)
 * ❌ Mixing synchronized blocks with concurrent collections
 * ❌ Assuming iterator is atomic (use snapshot!)
 * ❌ Forgetting null checks (ConcurrentHashMap doesn't allow null)
 * ❌ Using CopyOnWrite for write-heavy workload
 * 
 * BEST PRACTICES:
 * ===============
 * ✅ Prefer ConcurrentHashMap over synchronized HashMap
 * ✅ Use atomic operations (putIfAbsent, compute, etc.)
 * ✅ Consider read/write ratio when choosing collection
 * ✅ Test under real concurrent load
 * ✅ Monitor for contention and deadlocks
 * ✅ Use try-finally for manual locking
 * 
 * PERFORMANCE TIPS:
 * =================
 * 1. Reduce lock contention:
 *    - Use finer-grained locks
 *    - Minimize critical section size
 *    - Consider lock-free algorithms
 * 
 * 2. Batch operations:
 *    - Group multiple operations
 *    - Use bulk methods (putAll, etc.)
 * 
 * 3. Avoid premature synchronization:
 *    - Profile first
 *    - Synchronize only what's needed
 * 
 * INTERVIEW QUESTIONS:
 * ====================
 * Q: ConcurrentHashMap vs Collections.synchronizedMap()?
 * A: CHM has better concurrency (segment locking vs full lock)
 * 
 * Q: When to use CopyOnWriteArrayList?
 * A: When reads >> writes (like event listeners)
 * 
 * Q: How does ConcurrentHashMap achieve thread-safety?
 * A: Segment-based locking + CAS operations
 * 
 * Q: Can ConcurrentHashMap have null keys/values?
 * A: No! Will throw NullPointerException
 * 
 * NEXT: 07_LockFreeDataStructures.java
 * (Non-blocking algorithms and atomic operations)
 */
