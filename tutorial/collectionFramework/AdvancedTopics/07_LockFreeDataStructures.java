package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.concurrent.atomic.*;
import java.util.*;

/**
 * ==========================================
 * LOCK-FREE DATA STRUCTURES - Non-Blocking Algorithms
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Lock-free = Bina lock ke thread-safe code! 🚀
 * CAS (Compare-And-Swap) operations use karte hain
 * 
 * WHY LOCK-FREE?
 * - No deadlocks possible
 * - Better performance under high contention
 * - Wait-free progress guarantee
 * - Used in high-frequency trading, databases
 * 
 * ATOMIC VARIABLES:
 * - AtomicInteger, AtomicLong
 * - AtomicReference
 * - AtomicBoolean
 * - LongAdder, DoubleAdder (Java 8+)
 * 
 * @author Nitesh Kumar
 * @level Expert
 */
class LockFreeDataStructures {
    
    public static void main(String[] args) throws InterruptedException {
        demonstrateAtomicVariables();
        demonstrateLockFreeStack();
        demonstrateLockFreeQueue();
        demonstrateLongAdder();
        demonstrateStampedLock();
    }
    
    /**
     * ATOMIC VARIABLES - Building Blocks
     * ===================================
     */
    static void demonstrateAtomicVariables() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        
        // Multiple threads incrementing
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.incrementAndGet(); // Atomic!
                }
            });
            threads[i].start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
        
        assert counter.get() == 10000;
        
        // CAS operation - foundation of lock-free
        AtomicReference<String> ref = new AtomicReference<>("old");
        boolean updated = ref.compareAndSet("old", "new");
        assert updated && ref.get().equals("new");
    }
    
    /**
     * LOCK-FREE STACK
     * ===============
     * 
     * Treiber Stack algorithm
     * Uses CAS for thread-safe operations
     */
    static class LockFreeStack<T> {
        private static class Node<T> {
            final T value;
            Node<T> next;
            
            Node(T value) {
                this.value = value;
            }
        }
        
        private final AtomicReference<Node<T>> head = new AtomicReference<>();
        
        public void push(T value) {
            Node<T> newHead = new Node<>(value);
            Node<T> oldHead;
            
            do {
                oldHead = head.get();
                newHead.next = oldHead;
            } while (!head.compareAndSet(oldHead, newHead));
            // Retry if another thread modified head
        }
        
        public T pop() {
            Node<T> oldHead;
            Node<T> newHead;
            
            do {
                oldHead = head.get();
                if (oldHead == null) return null;
                newHead = oldHead.next;
            } while (!head.compareAndSet(oldHead, newHead));
            
            return oldHead.value;
        }
        
        public boolean isEmpty() {
            return head.get() == null;
        }
    }
    
    static void demonstrateLockFreeStack() throws InterruptedException {
        LockFreeStack<Integer> stack = new LockFreeStack<>();
        
        // Multiple threads pushing
        Thread[] pushers = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int id = i;
            pushers[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    stack.push(id * 100 + j);
                }
            });
            pushers[i].start();
        }
        
        for (Thread t : pushers) {
            t.join();
        }
        
        // Multiple threads popping
        AtomicInteger popCount = new AtomicInteger(0);
        Thread[] poppers = new Thread[5];
        for (int i = 0; i < 5; i++) {
            poppers[i] = new Thread(() -> {
                while (true) {
                    Integer value = stack.pop();
                    if (value == null) break;
                    popCount.incrementAndGet();
                }
            });
            poppers[i].start();
        }
        
        for (Thread t : poppers) {
            t.join();
        }
        
        assert popCount.get() == 500;
    }
    
    /**
     * LOCK-FREE QUEUE
     * ===============
     * 
     * Michael-Scott queue algorithm
     * More complex than stack due to two pointers
     */
    static class LockFreeQueue<T> {
        private static class Node<T> {
            final T value;
            final AtomicReference<Node<T>> next = new AtomicReference<>();
            
            Node(T value) {
                this.value = value;
            }
        }
        
        private final AtomicReference<Node<T>> head;
        private final AtomicReference<Node<T>> tail;
        
        public LockFreeQueue() {
            Node<T> dummy = new Node<>(null);
            head = new AtomicReference<>(dummy);
            tail = new AtomicReference<>(dummy);
        }
        
        public void enqueue(T value) {
            Node<T> newNode = new Node<>(value);
            
            while (true) {
                Node<T> curTail = tail.get();
                Node<T> tailNext = curTail.next.get();
                
                if (curTail == tail.get()) {
                    if (tailNext == null) {
                        if (curTail.next.compareAndSet(null, newNode)) {
                            tail.compareAndSet(curTail, newNode);
                            return;
                        }
                    } else {
                        tail.compareAndSet(curTail, tailNext);
                    }
                }
            }
        }
        
        public T dequeue() {
            while (true) {
                Node<T> curHead = head.get();
                Node<T> curTail = tail.get();
                Node<T> headNext = curHead.next.get();
                
                if (curHead == head.get()) {
                    if (curHead == curTail) {
                        if (headNext == null) return null;
                        tail.compareAndSet(curTail, headNext);
                    } else {
                        T value = headNext.value;
                        if (head.compareAndSet(curHead, headNext)) {
                            return value;
                        }
                    }
                }
            }
        }
    }
    
    static void demonstrateLockFreeQueue() throws InterruptedException {
        LockFreeQueue<String> queue = new LockFreeQueue<>();
        
        // Producer threads
        Thread[] producers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int id = i;
            producers[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    queue.enqueue("Task" + id + "_" + j);
                }
            });
            producers[i].start();
        }
        
        // Consumer threads
        AtomicInteger consumed = new AtomicInteger(0);
        Thread[] consumers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            consumers[i] = new Thread(() -> {
                while (consumed.get() < 300) {
                    String task = queue.dequeue();
                    if (task != null) {
                        consumed.incrementAndGet();
                    }
                }
            });
            consumers[i].start();
        }
        
        for (Thread t : producers) {
            t.join();
        }
        for (Thread t : consumers) {
            t.join();
        }
        
        assert consumed.get() == 300;
    }
    
    /**
     * LONGADDER - High-Performance Counter
     * =====================================
     * 
     * Better than AtomicLong under high contention
     * Uses striping technique
     */
    static void demonstrateLongAdder() throws InterruptedException {
        LongAdder adder = new LongAdder();
        
        long start = System.nanoTime();
        
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    adder.increment();
                }
            });
            threads[i].start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
        
        long time = System.nanoTime() - start;
        
        assert adder.sum() == 200000;
    }
    
    /**
     * STAMPEDLOCK - Optimistic Locking
     * =================================
     * 
     * Java 8+ feature
     * Optimistic read doesn't block writers!
     */
    static void demonstrateStampedLock() {
        java.util.concurrent.locks.StampedLock lock = 
            new java.util.concurrent.locks.StampedLock();
        
        class Point {
            private double x, y;
            
            void move(double deltaX, double deltaY) {
                long stamp = lock.writeLock();
                try {
                    x += deltaX;
                    y += deltaY;
                } finally {
                    lock.unlockWrite(stamp);
                }
            }
            
            double distanceFromOrigin() {
                long stamp = lock.tryOptimisticRead();
                double currentX = x, currentY = y;
                
                if (!lock.validate(stamp)) {
                    stamp = lock.readLock();
                    try {
                        currentX = x;
                        currentY = y;
                    } finally {
                        lock.unlockRead(stamp);
                    }
                }
                
                return Math.sqrt(currentX * currentX + currentY * currentY);
            }
        }
        
        Point point = new Point();
        point.move(3, 4);
        double distance = point.distanceFromOrigin();
        assert distance == 5.0;
    }
}

/*
 * ==========================================
 * LOCK-FREE ALGORITHMS - Key Concepts
 * ==========================================
 * 
 * 1. CAS (Compare-And-Swap):
 *    - Atomic hardware instruction
 *    - compareAndSet(expected, new)
 *    - Foundation of lock-free programming
 * 
 * 2. ABA Problem:
 *    - A → B → A looks unchanged but isn't
 *    - Solution: Use AtomicStampedReference
 * 
 * 3. Memory Ordering:
 *    - Volatile ensures visibility
 *    - Happens-before relationship
 *    - Important for correctness
 * 
 * WHEN TO USE LOCK-FREE:
 * ======================
 * ✅ High contention scenarios
 * ✅ Real-time systems
 * ✅ Low-latency requirements
 * ✅ Simple data structures
 * 
 * WHEN NOT TO USE:
 * ================
 * ❌ Complex operations
 * ❌ Multiple steps need atomicity
 * ❌ Team lacks expertise
 * ❌ Locks work fine (premature optimization)
 * 
 * NEXT: 08_LazyLoading.java
 */
