package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * STEP 6: CONCURRENT COLLECTIONS & THREAD-SAFE DATA STRUCTURES
 * 
 * PROBLEM: Using standard collections (ArrayList, HashMap) in multithreaded
 * environments causes ConcurrentModificationException and data corruption.
 * 
 * SOLUTION: java.util.concurrent package provides thread-safe collections:
 * - ConcurrentHashMap
 * - CopyOnWriteArrayList
 * - ConcurrentLinkedQueue
 * - BlockingQueue (and variants)
 * - ConcurrentSkipListSet
 * 
 * These are optimized for concurrent access without full synchronization.
 */

import java.util.*;
import java.util.concurrent.*;

// ==================== UNSAFE: Regular HashMap in multi-threaded context ====================
class UnsafeMapDemo {
    private Map<String, Integer> unsafeMap = new HashMap<>();
    
    public void increment(String key) {
        // PROBLEM: This is not atomic!
        // 1. Read value
        // 2. Increment
        // 3. Write back
        // Race condition: Multiple threads can increment without proper synchronization
        Integer count = unsafeMap.getOrDefault(key, 0);
        unsafeMap.put(key, count + 1);
    }
    
    public Map<String, Integer> getMap() {
        return unsafeMap;
    }
}

// ==================== SAFE: ConcurrentHashMap ====================
/**
 * ConcurrentHashMap:
 * - Uses segment-based locking (or bucket locking in Java 8+)
 * - Multiple threads can access different segments simultaneously
 * - Better performance than Collections.synchronizedMap()
 * - Iterator is weakly consistent (doesn't throw ConcurrentModificationException)
 */
class SafeMapDemo {
    private Map<String, Integer> safeMap = new ConcurrentHashMap<>();
    
    public void increment(String key) {
        // ConcurrentHashMap provides atomic operations
        safeMap.put(key, safeMap.getOrDefault(key, 0) + 1);
    }
    
    // Even better: Use putIfAbsent and compute operations
    public void incrementAtomic(String key) {
        safeMap.putIfAbsent(key, 0);
        safeMap.computeIfPresent(key, (k, v) -> v + 1);
    }
    
    public Map<String, Integer> getMap() {
        return safeMap;
    }
}

// ==================== COPYONWRITEARRAYLIST ====================
/**
 * CopyOnWriteArrayList:
 * - Thread-safe version of ArrayList
 * - Write operations create a new copy (hence "CopyOnWrite")
 * - Read operations don't lock (very fast)
 * - Good when reads >> writes
 * - BAD for heavy write scenarios (copies are expensive)
 * 
 * Use case: Event listeners, configurations read frequently but modified rarely
 */
class ListenerManager {
    private List<String> listeners = new CopyOnWriteArrayList<>();
    
    public void addListener(String listener) {
        listeners.add(listener);
    }
    
    public void removeListener(String listener) {
        listeners.remove(listener);
    }
    
    public void notifyAllListeners(String event) {
        // Safe to iterate even if modifications happen concurrently
        for (String listener : listeners) {
            System.out.println("Notifying: " + listener + " about " + event);
        }
    }
}

// ==================== BLOCKINGQUEUE ====================
/**
 * BlockingQueue:
 * - Thread-safe queue with blocking operations
 * - put() blocks if queue is full (bounded queue)
 * - take() blocks if queue is empty
 * - Perfect for producer-consumer pattern
 * - Multiple implementations: ArrayBlockingQueue, LinkedBlockingQueue, etc.
 */
class BlockingQueueDemo {
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
    
    public void produce(String item) throws InterruptedException {
        System.out.println("[Producer] Adding: " + item);
        queue.put(item);  // Blocks if queue is full
        System.out.println("[Producer] Added. Queue size: " + queue.size());
    }
    
    public String consume() throws InterruptedException {
        System.out.println("[Consumer] Waiting for item...");
        String item = queue.take();  // Blocks if queue is empty
        System.out.println("[Consumer] Got: " + item + ". Queue size: " + queue.size());
        return item;
    }
}

// ==================== CONCURRENT COUNTER WITH ATOMICINTEGER ====================
/**
 * AtomicInteger, AtomicLong, AtomicReference:
 * - Atomic operations without locks
 * - Uses Compare-And-Swap (CAS) algorithm
 * - Better performance than synchronized in high-contention scenarios
 */
class AtomicCounterDemo {
    private java.util.concurrent.atomic.AtomicInteger counter = new java.util.concurrent.atomic.AtomicInteger(0);
    
    public void increment() {
        counter.incrementAndGet();  // Atomic operation
    }
    
    public void decrement() {
        counter.decrementAndGet();  // Atomic operation
    }
    
    public int getCount() {
        return counter.get();
    }
    
    // Add and get atomically
    public int addAndGet(int delta) {
        return counter.addAndGet(delta);
    }
}

// ==================== THREADS FOR DEMO ====================
class MapWriterThread implements Runnable {
    private Map<String, Integer> map;
    private String key;
    private int iterations;
    
    public MapWriterThread(Map<String, Integer> map, String key, int iterations) {
        this.map = map;
        this.key = key;
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            Integer count = map.getOrDefault(key, 0);
            map.put(key, count + 1);
        }
    }
}

class BlockingQueueProducer implements Runnable {
    private BlockingQueue<Integer> queue;
    private int itemCount;
    
    public BlockingQueueProducer(BlockingQueue<Integer> queue, int count) {
        this.queue = queue;
        this.itemCount = count;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= itemCount; i++) {
                System.out.println("[BQ-Producer] Producing: " + i);
                queue.put(i);
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class BlockingQueueConsumer implements Runnable {
    private BlockingQueue<Integer> queue;
    private int itemCount;
    
    public BlockingQueueConsumer(BlockingQueue<Integer> queue, int count) {
        this.queue = queue;
        this.itemCount = count;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemCount; i++) {
                Integer item = queue.take();
                System.out.println("[BQ-Consumer] Consumed: " + item);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Step6_ConcurrentCollections {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n========== CONCURRENT COLLECTIONS TUTORIAL ==========\n");
        
        // ========== 1. CONCURRENT HASHMAP VS REGULAR HASHMAP ==========
        System.out.println("--- 1. CONCURRENT HASHMAP ---");
        
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        Thread[] mapThreads = new Thread[5];
        
        System.out.println("Adding 1000 items from 5 threads using ConcurrentHashMap");
        for (int i = 0; i < 5; i++) {
            mapThreads[i] = new Thread(new MapWriterThread(concurrentMap, "counter", 1000));
            mapThreads[i].start();
        }
        
        for (Thread t : mapThreads) {
            t.join();
        }
        
        System.out.println("Final count: " + concurrentMap.get("counter"));
        System.out.println("Expected: " + (5 * 1000) + " (May not be exact due to race condition in getOrDefault)");
        
        System.out.println();
        
        // ========== 2. COPYONWRITEARRAYLIST ==========
        System.out.println("--- 2. COPYONWRITEARRAYLIST ---");
        
        ListenerManager manager = new ListenerManager();
        manager.addListener("Listener-1");
        manager.addListener("Listener-2");
        manager.addListener("Listener-3");
        
        Thread notifierThread = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                manager.notifyAllListeners("Event-" + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        Thread adderThread = new Thread(() -> {
            try {
                Thread.sleep(300);
                manager.addListener("Listener-4");
                Thread.sleep(300);
                manager.removeListener("Listener-1");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        notifierThread.start();
        adderThread.start();
        
        notifierThread.join();
        adderThread.join();
        
        System.out.println();
        
        // ========== 3. BLOCKINGQUEUE ==========
        System.out.println("--- 3. BLOCKINGQUEUE (Simple Example) ---");
        
        BlockingQueueDemo bqDemo = new BlockingQueueDemo();
        
        new Thread(() -> {
            try {
                bqDemo.produce("Item-1");
                bqDemo.produce("Item-2");
                bqDemo.produce("Item-3");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        Thread.sleep(1000);
        
        new Thread(() -> {
            try {
                bqDemo.consume();
                bqDemo.consume();
                bqDemo.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        
        Thread.sleep(2000);
        System.out.println();
        
        // ========== 4. BLOCKING QUEUE PRODUCER-CONSUMER ==========
        System.out.println("--- 4. BLOCKINGQUEUE (Producer-Consumer Pattern) ---");
        
        BlockingQueue<Integer> pqQueue = new LinkedBlockingQueue<>(3);
        
        Thread producer = new Thread(new BlockingQueueProducer(pqQueue, 5));
        Thread consumer = new Thread(new BlockingQueueConsumer(pqQueue, 5));
        
        long startTime = System.currentTimeMillis();
        producer.start();
        consumer.start();
        
        producer.join();
        consumer.join();
        long endTime = System.currentTimeMillis();
        
        System.out.println("Producer-Consumer completed in " + (endTime - startTime) + "ms");
        System.out.println();
        
        // ========== 5. ATOMICINTEGER ==========
        System.out.println("--- 5. ATOMICINTEGER (Lock-free counter) ---");
        
        AtomicCounterDemo atomicCounter = new AtomicCounterDemo();
        Thread[] atomicThreads = new Thread[5];
        
        System.out.println("Incrementing AtomicInteger 1000 times from 5 threads");
        for (int i = 0; i < 5; i++) {
            atomicThreads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicCounter.increment();
                }
            });
            atomicThreads[i].start();
        }
        
        for (Thread t : atomicThreads) {
            t.join();
        }
        
        System.out.println("Final count: " + atomicCounter.getCount());
        System.out.println("Expected: " + (5 * 1000) + " (Guaranteed to be correct!)");
        System.out.println();
        
        // ========== 6. COMPARISON ==========
        System.out.println("--- CONCURRENT COLLECTIONS COMPARISON ---");
        System.out.println("HashMap              : NOT thread-safe, avoid in multithreaded code");
        System.out.println("ConcurrentHashMap    : Thread-safe, best for balanced read/write");
        System.out.println("CopyOnWriteArrayList : Thread-safe, best when reads >> writes");
        System.out.println("BlockingQueue        : Thread-safe, blocking operations, producer-consumer");
        System.out.println("AtomicInteger/Long   : Lock-free, best for simple counters");
        System.out.println("Collections.synchronizedList() : Locks entire collection, slower");
    }
}

