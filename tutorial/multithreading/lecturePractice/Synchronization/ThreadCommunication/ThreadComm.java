package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.ThreadCommunication;

import java.util.concurrent.*;
import java.util.Queue;
import java.util.LinkedList;

/*
 * ============================================================================
 * THREAD COMMUNICATION - COMPREHENSIVE GUIDE
 * ============================================================================
 * 
 * WHAT IS THREAD COMMUNICATION?
 * =============================
 * Thread Communication is the mechanism by which multiple threads can
 * coordinate and exchange information with each other to achieve a
 * common goal in a synchronized manner.
 * 
 * Think of it as threads sending messages to each other:
 * - Producer thread: "I've finished making something, come and get it!"
 * - Consumer thread: "I'm waiting for you to finish, let me know!"
 * 
 * WITHOUT thread communication:
 * - Threads work independently
 * - No coordination between threads
 * - Inefficient polling/busy waiting
 * - Wasted CPU cycles
 * 
 * WITH thread communication:
 * - Threads wait efficiently
 * - Threads notify each other when ready
 * - Smooth coordination
 * - Better performance
 * 
 * ============================================================================
 * REAL-WORLD ANALOGIES
 * ====================
 * 
 * 1. RESTAURANT ORDER SYSTEM
 *    - Customer places order (Producer sends message)
 *    - Chef waits for orders (Consumer waits for message)
 *    - Chef notifies when food is ready (Producer notifies)
 *    - Customer picks up order (Consumer receives message)
 * 
 * 2. ASSEMBLY LINE FACTORY
 *    - Worker A finishes part (Producer notifies)
 *    - Worker B was waiting (Consumer wakes up)
 *    - Worker B takes the part and processes it
 *    - Worker B notifies Worker C when done
 * 
 * 3. EMAIL NOTIFICATION SYSTEM
 *    - Email arrives (Producer sends event)
 *    - App was waiting for email (Consumer receives notification)
 *    - App wakes up and processes email
 * 
 * ============================================================================
 * KEY BENEFITS OF THREAD COMMUNICATION
 * =======================================
 * 
 * 1. EFFICIENCY
 *    - Threads don't waste CPU in busy-waiting loops
 *    - Threads sleep until they're needed
 *    - Better CPU utilization
 * 
 * 2. COORDINATION
 *    - Multiple threads work together smoothly
 *    - No race conditions
 *    - Predictable behavior
 * 
 * 3. RESPONSIVENESS
 *    - Threads wake up immediately when needed
 *    - No delays from polling
 *    - Real-time communication
 * 
 * 4. SIMPLICITY
 *    - Cleaner code than busy-waiting
 *    - Intent is obvious from code
 *    - Easier to maintain
 * 
 * ============================================================================
 * KEY DRAWBACKS OF THREAD COMMUNICATION
 * ========================================
 * 
 * 1. COMPLEXITY
 *    - More complicated than single-threaded code
 *    - Easy to make mistakes
 *    - Harder to debug
 * 
 * 2. DEADLOCK RISK
 *    - Two threads can deadlock if not careful
 *    - Waiting forever for notification that never comes
 * 
 * 3. SPURIOUS WAKEUPS
 *    - Thread might wake up without being notified
 *    - Must handle this with loops
 * 
 * 4. TIMING ISSUES
 *    - If notification happens before waiting, message is lost
 *    - Need careful ordering of operations
 * 
 * ============================================================================
 */

public class ThreadComm {
    
    /*
     * ========================================================================
     * SECTION 1: METHOD 1 - wait() and notify() (BEGINNER)
     * ========================================================================
     * 
     * WHAT IS wait()?
     * ===============
     * - Makes current thread STOP and GIVE UP the lock
     * - Thread goes into WAITING state
     * - CPU not used (efficient!)
     * - Other threads can now acquire the lock
     * - Thread waits for another thread to call notify()
     * 
     * IMPORTANT: wait() can ONLY be called from inside synchronized block!
     * 
     * WHAT IS notify()?
     * =================
     * - Wakes up ONE waiting thread
     * - That thread exits WAITING state and becomes RUNNABLE
     * - Doesn't happen immediately! Thread must acquire lock first
     * - IMPORTANT: notify() also must be called from synchronized block!
     * 
     * WHAT IS notifyAll()?
     * ====================
     * - Wakes up ALL waiting threads
     * - Each will compete to acquire the lock
     * - Better than notify() in most cases (prevents deadlock)
     * 
     * HOW IT WORKS (Timeline):
     * ========================
     * Time 0: Thread A acquires lock
     * Time 1: Thread A calls wait() -> gives up lock, goes to WAITING state
     * Time 2: Thread B acquires lock
     * Time 3: Thread B does work
     * Time 4: Thread B calls notify()
     * Time 5: Thread A wakes up (still WAITING for lock)
     * Time 6: Thread B releases lock
     * Time 7: Thread A acquires lock and continues
     * 
     * ========================================================================
     */
    
    // Example 1: Simple Producer-Consumer with wait/notify
    public static class ProducerConsumer_BasicExample {
        private Object lock = new Object();
        private Queue<String> buffer = new LinkedList<>();
        private int MAX_SIZE = 5;
        
        // PRODUCER: Adds items to buffer
        public void produce(String item) {
            synchronized (lock) {  // MUST be in synchronized block!
                // Wait if buffer is full
                while (buffer.size() >= MAX_SIZE) {
                    try {
                        System.out.println("Buffer full! Producer waiting...");
                        lock.wait();  // Give up lock and wait
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                
                // Add item
                buffer.add(item);
                System.out.println("Produced: " + item + " (Buffer: " + buffer.size() + ")");
                
                // Notify waiting consumer
                lock.notifyAll();  // Wake up ALL waiting threads
            }
        }
        
        // CONSUMER: Takes items from buffer
        public String consume() {
            synchronized (lock) {  // MUST be in synchronized block!
                // Wait if buffer is empty
                while (buffer.isEmpty()) {
                    try {
                        System.out.println("Buffer empty! Consumer waiting...");
                        lock.wait();  // Give up lock and wait
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }
                
                // Remove item
                String item = buffer.poll();
                System.out.println("Consumed: " + item + " (Buffer: " + buffer.size() + ")");
                
                // Notify waiting producer (buffer may have space now)
                lock.notifyAll();  // Wake up ALL waiting threads
                
                return item;
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 2: IMPORTANT DETAIL - WHY use notifyAll() over notify()?
     * ========================================================================
     * 
     * notify() vs notifyAll():
     * 
     * notify():
     * - Wakes up ONE random waiting thread
     * - Other threads stay waiting
     * - PROBLEM: What if wrong thread is woken up?
     * 
     * notifyAll():
     * - Wakes up ALL waiting threads
     * - All threads compete for lock
     * - All can check their condition
     * - SAFE: guarantees correct thread will continue
     * 
     * EXAMPLE OF PROBLEM WITH notify():
     * ==================================
     * Time 0: Producer1 and Producer2 both waiting
     * Time 1: notify() wakes up Producer1
     * Time 2: Consumer acquires lock
     * Time 3: Consumer releases notify()
     * PROBLEM: Consumer might have notified Producer1 but Producer2 is still waiting
     * and could've done the job!
     * 
     * RULE: Use notifyAll() unless you REALLY know what you're doing!
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 3: IMPORTANT DETAIL - SPURIOUS WAKEUPS
     * ========================================================================
     * 
     * WHAT IS A SPURIOUS WAKEUP?
     * ===========================
     * Thread wakes up WITHOUT being notified!
     * Rare in Java, but can happen on some systems.
     * 
     * WRONG WAY (buggy code):
     * =======================
     * synchronized (lock) {
     *     if (condition) {  // <-- Wrong! Single check
     *         lock.wait();
     *     }
     *     // Do work
     * }
     * 
     * Problem: If spurious wakeup happens, condition might not be true anymore!
     * 
     * RIGHT WAY (safe code):
     * ======================
     * synchronized (lock) {
     *     while (!condition) {  // <-- Correct! Loop checks condition
     *         lock.wait();
     *     }
     *     // Do work
     * }
     * 
     * This is the GOLDEN RULE for wait/notify:
     * Always use WHILE with condition check, NEVER use if!
     * 
     * Why? 
     * - Spurious wakeup
     * - Another thread might have consumed the resource
     * - Multiple threads waiting for same condition
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 4: METHOD 2 - BlockingQueue (RECOMMENDED FOR BEGINNERS)
     * ========================================================================
     * 
     * WHAT IS BlockingQueue?
     * =======================
     * - Thread-safe queue that handles wait/notify automatically
     * - Put: Adds item (waits if full)
     * - Take: Removes item (waits if empty)
     * - No manual synchronized blocks needed!
     * - Much cleaner than raw wait/notify
     * 
     * ADVANTAGES OVER wait/notify:
     * =============================
     * 1. SIMPLER: No boilerplate synchronized code
     * 2. SAFER: Can't make spurious wakeup mistakes
     * 3. CLEANER: Intent is clear (producer-consumer pattern)
     * 4. BUILT-IN: ArrayBlockingQueue, LinkedBlockingQueue already available
     * 
     * AVAILABLE IMPLEMENTATIONS:
     * ===========================
     * 1. ArrayBlockingQueue - Fixed size, backed by array
     * 2. LinkedBlockingQueue - Unlimited or limited size, backed by linked list
     * 3. PriorityBlockingQueue - Items are ordered by priority
     * 4. SynchronousQueue - Each put must wait for take (queue size = 0)
     * 5. DelayQueue - Items released only after delay expires
     * 
     * ========================================================================
     */
    
    // Example 2: Producer-Consumer with BlockingQueue (RECOMMENDED)
    public static class ProducerConsumer_BlockingQueueExample {
        private BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        
        // PRODUCER: Put items into queue
        // If queue is full, automatically waits
        public void produce(String item) {
            try {
                System.out.println("Producing: " + item);
                queue.put(item);  // Waits if full, no exception!
                System.out.println("Produced: " + item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // CONSUMER: Take items from queue
        // If queue is empty, automatically waits
        public String consume() {
            try {
                System.out.println("Waiting to consume...");
                String item = queue.take();  // Waits if empty, no exception!
                System.out.println("Consumed: " + item);
                return item;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        
        // SO MUCH CLEANER! No synchronized blocks, no spurious wakeups!
    }
    
    
    /*
     * ========================================================================
     * SECTION 5: COMPARISON OF THREAD COMMUNICATION METHODS
     * ========================================================================
     * 
     * +-----------------+----------+---------+-------------+------------+
     * | Method          | Simplicity| Safety | Flexibility | Performance|
     * +-----------------+----------+---------+-------------+------------+
     * | wait/notify     | Low      | Medium  | High        | Good       |
     * | BlockingQueue   | High     | High    | Medium      | Good       |
     * | Condition Var   | Medium   | High    | High        | Good       |
     * | Semaphore       | Medium   | High    | Medium      | Good       |
     * | CountDownLatch  | High     | High    | Low         | Good       |
     * | CyclicBarrier   | Medium   | High    | Low         | Good       |
     * +-----------------+----------+---------+-------------+------------+
     * 
     * RECOMMENDATION:
     * ===============
     * For most cases, use BlockingQueue (simplest + safest).
     * Only use wait/notify when you need more control.
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 6: METHOD 3 - CONDITIONS & LOCKS (ADVANCED)
     * ========================================================================
     * 
     * WHAT IS Condition?
     * ===================
     * - Part of java.util.concurrent.locks package
     * - Like wait/notify but with explicit locks (ReentrantLock)
     * - Multiple conditions per lock
     * - More control than synchronized wait/notify
     * 
     * ADVANTAGES OVER wait/notify:
     * =============================
     * 1. MULTIPLE CONDITIONS: Different conditions for different scenarios
     * 2. NAMED CONDITIONS: Code is more readable
     * 3. EXPLICIT LOCKS: Clearer what's being protected
     * 4. FAIRER: ReentrantLock can be fair = everyone gets a turn
     * 
     * DISADVANTAGE:
     * ==============
     * More verbose than BlockingQueue
     * More complex than synchronized wait/notify
     * 
     * ========================================================================
     */
    
    // Example 3: Using Condition (Advanced)
    public static class ProducerConsumer_ConditionExample {
        private final Object[] buffer = new Object[5];
        private int count = 0;
        private int in = 0;
        private int out = 0;
        
        private final java.util.concurrent.locks.Lock lock =
            new java.util.concurrent.locks.ReentrantLock();
        
        // Condition for when buffer is NOT full (producer can produce)
        private final java.util.concurrent.locks.Condition notFull =
            lock.newCondition();
        
        // Condition for when buffer is NOT empty (consumer can consume)
        private final java.util.concurrent.locks.Condition notEmpty =
            lock.newCondition();
        
        public void produce(Object item) {
            lock.lock();
            try {
                while (count == buffer.length) {
                    System.out.println("Buffer full! Producer waiting...");
                    notFull.await();  // Wait for space
                }
                
                buffer[in] = item;
                in = (in + 1) % buffer.length;
                count++;
                System.out.println("Produced: " + item);
                
                notEmpty.signalAll();  // Wake up consumers
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
        
        public Object consume() {
            lock.lock();
            try {
                while (count == 0) {
                    System.out.println("Buffer empty! Consumer waiting...");
                    notEmpty.await();  // Wait for items
                }
                
                Object item = buffer[out];
                out = (out + 1) % buffer.length;
                count--;
                System.out.println("Consumed: " + item);
                
                notFull.signalAll();  // Wake up producers
                return item;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } finally {
                lock.unlock();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 7: METHOD 4 - SEMAPHORE (ADVANCED)
     * ========================================================================
     * 
     * WHAT IS Semaphore?
     * ===================
     * - Maintains a counter of available permits
     * - acquire(): Decrements counter, waits if zero
     * - release(): Increments counter, wakes up waiting thread
     * - Used to limit access to resource (e.g., max 5 connections)
     * 
     * USE CASE:
     * =========
     * When you need to allow N threads to access resource, but not more.
     * 
     * Example: Database connection pool with max 10 connections
     * - Semaphore(10)
     * - Each thread acquires before connecting
     * - Only 10 threads can connect at a time
     * 
     * ========================================================================
     */
    
    // Example 4: Semaphore for limiting access
    public static class SemaphoreExample {
        private java.util.concurrent.Semaphore semaphore =
            new java.util.concurrent.Semaphore(3);  // Max 3 threads
        
        public void limitedAccess() {
            try {
                semaphore.acquire();  // Get permit
                System.out.println(Thread.currentThread().getName() + " acquired access");
                
                // Do work
                Thread.sleep(2000);
                
                System.out.println(Thread.currentThread().getName() + " releasing access");
                semaphore.release();  // Release permit, wake up waiting thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 8: METHOD 5 - COUNTDOWNLATCH (ADVANCED)
     * ========================================================================
     * 
     * WHAT IS CountDownLatch?
     * ========================
     * - Allows threads to wait for N events to happen
     * - countDown(): Decrements counter (one event happened)
     * - await(): Waits for counter to reach zero
     * - One-time use (can't be reset)
     * 
     * USE CASE:
     * =========
     * Example: Start a web server after all components are initialized
     * - Create CountDownLatch(3) for 3 components
     * - Each component calls countDown() when initialized
     * - Main thread calls await() to wait for all 3
     * - Once all initialized, start the server
     * 
     * ========================================================================
     */
    
    // Example 5: CountDownLatch for synchronization
    public static class CountDownLatchExample {
        public static void demonstrateCountDownLatch() throws InterruptedException {
            java.util.concurrent.CountDownLatch latch =
                new java.util.concurrent.CountDownLatch(3);
            
            // Component 1
            new Thread(() -> {
                System.out.println("Component 1 initializing...");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
                System.out.println("Component 1 initialized!");
                latch.countDown();  // Decrement counter
            }).start();
            
            // Component 2
            new Thread(() -> {
                System.out.println("Component 2 initializing...");
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                System.out.println("Component 2 initialized!");
                latch.countDown();  // Decrement counter
            }).start();
            
            // Component 3
            new Thread(() -> {
                System.out.println("Component 3 initializing...");
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
                System.out.println("Component 3 initialized!");
                latch.countDown();  // Decrement counter
            }).start();
            
            // Main thread waits for all components
            System.out.println("Waiting for all components to initialize...");
            latch.await();  // Wait until counter reaches 0
            System.out.println("All components initialized! Starting application...");
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 9: METHOD 6 - CYCLICBARRIER (ADVANCED)
     * ========================================================================
     * 
     * WHAT IS CyclicBarrier?
     * ======================
     * - Allows N threads to wait for each other
     * - await(): Thread waits until all N threads have called await()
     * - Can be reused multiple times (unlike CountDownLatch)
     * 
     * USE CASE:
     * =========
     * Synchronization point where all threads meet and proceed together.
     * 
     * Example: Multi-threaded simulation where all threads must complete
     * round 1 before moving to round 2.
     * 
     * ========================================================================
     */
    
    // Example 6: CyclicBarrier for synchronization
    public static class CyclicBarrierExample {
        public static void demonstrateCyclicBarrier() {
            java.util.concurrent.CyclicBarrier barrier =
                new java.util.concurrent.CyclicBarrier(3, () -> {
                    System.out.println("=== All threads reached barrier, proceeding ===");
                });
            
            for (int i = 1; i <= 3; i++) {
                final int threadId = i;
                new Thread(() -> {
                    try {
                        System.out.println("Thread " + threadId + " doing round 1 work...");
                        Thread.sleep(1000 * threadId);  // Different times
                        System.out.println("Thread " + threadId + " waiting at barrier");
                        barrier.await();  // Wait for all threads
                        System.out.println("Thread " + threadId + " proceeding to round 2");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 10: COMPLETE PRODUCER-CONSUMER REAL-WORLD EXAMPLE
     * ========================================================================
     */
    
    // Real-World Example: Data Processing Pipeline
    public static class DataProcessingPipeline {
        private BlockingQueue<String> inputQueue = new ArrayBlockingQueue<>(10);
        private BlockingQueue<String> processedQueue = new ArrayBlockingQueue<>(10);
        private boolean running = false;
        
        // Producer: Reads data from source
        public class Producer implements Runnable {
            @Override
            public void run() {
                try {
                    for (int i = 1; i <= 10; i++) {
                        String data = "Data-" + i;
                        inputQueue.put(data);  // Waits if full
                        System.out.println("[Producer] Produced: " + data);
                        Thread.sleep(500);  // Simulate data generation
                    }
                    inputQueue.put("STOP");  // Signal end
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        // Consumer: Processes data
        public class Processor implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        String data = inputQueue.take();  // Waits if empty
                        if ("STOP".equals(data)) {
                            processedQueue.put("STOP");
                            break;
                        }
                        
                        // Process data
                        String processed = data.toUpperCase();
                        processedQueue.put(processed);  // Waits if full
                        System.out.println("[Processor] Processed: " + processed);
                        Thread.sleep(1000);  // Simulate processing time
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        // Final Consumer: Outputs data
        public class OutputHandler implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        String data = processedQueue.take();  // Waits if empty
                        if ("STOP".equals(data)) break;
                        
                        System.out.println("[Output] Final result: " + data);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        public void start() throws InterruptedException {
            Thread producerThread = new Thread(new Producer());
            Thread processorThread = new Thread(new Processor());
            Thread outputThread = new Thread(new OutputHandler());
            
            producerThread.start();
            processorThread.start();
            outputThread.start();
            
            producerThread.join();
            processorThread.join();
            outputThread.join();
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 11: COMMON MISTAKES & CONFUSION
     * ========================================================================
     * 
     * MISTAKE 1: Calling wait() outside synchronized block
     * =====================================================
     * WRONG:
     *   lock.wait();  // IllegalMonitorStateException!
     * 
     * RIGHT:
     *   synchronized (lock) {
     *       lock.wait();
     *   }
     * 
     * 
     * MISTAKE 2: Using if instead of while with wait()
     * ==================================================
     * WRONG:
     *   synchronized (lock) {
     *       if (buffer.isEmpty()) {  // What if spurious wakeup?
     *           lock.wait();
     *       }
     *       consume();
     *   }
     * 
     * RIGHT:
     *   synchronized (lock) {
     *       while (buffer.isEmpty()) {  // Check again after waking up
     *           lock.wait();
     *       }
     *       consume();
     *   }
     * 
     * 
     * MISTAKE 3: Using notify() instead of notifyAll()
     * ==================================================
     * Problem: Wrong thread might wake up
     * Solution: Always use notifyAll() unless you KNOW what you're doing
     * 
     * 
     * MISTAKE 4: Holding lock for too long
     * ======================================
     * WRONG:
     *   synchronized (lock) {
     *       get data
     *       do expensive processing
     *       release lock
     *   }  // Other threads wait while you process!
     * 
     * RIGHT:
     *   synchronized (lock) {
     *       get data
     *   }  // Release lock early
     *   do expensive processing
     *   synchronized (lock) {
     *       put results
     *   }
     * 
     * 
     * MISTAKE 5: Forgetting InterruptedException handling
     * =====================================================
     * WRONG:
     *   try {
     *       lock.wait();
     *   } catch (InterruptedException e) {
     *       // Ignore!
     *   }  // Thread can be stuck forever!
     * 
     * RIGHT:
     *   try {
     *       lock.wait();
     *   } catch (InterruptedException e) {
     *       Thread.currentThread().interrupt();  // Restore status
     *       return;  // Exit gracefully
     *   }
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 12: CONFUSION POINTS CLARIFIED
     * ========================================================================
     * 
     * CONFUSION 1: "What's the difference between wait() and Thread.sleep()?"
     * =========================================================================
     * 
     * sleep():
     * - Pauses thread for specified time
     * - KEEPS the lock it owns
     * - Wakes up automatically after time expires
     * - Other threads CANNOT acquire the lock
     * 
     * wait():
     * - Pauses thread INDEFINITELY (or until notified)
     * - RELEASES the lock
     * - Needs explicit notify() to wake up
     * - Other threads CAN now acquire the lock
     * 
     * Example:
     * sleep() = Setting an alarm and sleeping (lock held)
     * wait()  = Going to sleep, friend wakes you up (lock released)
     * 
     * 
     * CONFUSION 2: "When does notify() wake up the thread?"
     * ======================================================
     * NOT immediately! Timeline:
     * 
     * Time 0: Thread A acquires lock, calls wait()
     * Time 1: Thread A releases lock and goes to WAITING state
     * Time 2: Thread B acquires lock
     * Time 3: Thread B calls notify()  <- Thread A is WOKEN but still WAITING for lock
     * Time 4: Thread B releases lock
     * Time 5: Thread A acquires lock and continues from wait()
     * 
     * Key: notify() just changes state from WAITING to RUNNABLE
     * Thread still needs to acquire lock before actually running!
     * 
     * 
     * CONFUSION 3: "Why use BlockingQueue if wait/notify exists?"
     * ============================================================
     * BlockingQueue is built on top of wait/notify but adds:
     * - Automatic thread-safety
     * - Can't make spurious wakeup mistakes
     * - Can't forget surrounding while loop
     * - Cleaner code
     * 
     * Think: BlockingQueue is wait/notify with guardrails!
     * 
     * 
     * CONFUSION 4: "What if notify() is called before wait()?"
     * ==========================================================
     * The notification is LOST!
     * 
     * WRONG ORDER:
     * Thread B: notify()      <- Called first
     * Thread A: wait()        <- Called after, too late!
     * Result: Thread A waits forever for notification that won't come!
     * 
     * SOLUTION: Always ensure proper ordering:
     * 1. Set a flag or condition
     * 2. Data is ready
     * 3. Then call notify()
     * 4. Thread A checks flag/condition before wait()
     * 
     * 
     * CONFUSION 5: "Is wait() a busy loop that wastes CPU?"
     * ======================================================
     * NO! wait() puts thread in WAITING state:
     * - Thread doesn't consume CPU cycles
     * - Completely blocked at OS level
     * - Wakes up immediately when notified
     * - Very efficient!
     * 
     * Busy loop (wastes CPU):
     * while (!condition) { }  // Spins forever, uses 100% CPU!
     * 
     * wait() (efficient):
     * while (!condition) {
     *     lock.wait();  // CPU not used, thread sleeps
     * }
     * 
     * 
     * CONFUSION 6: "Doesn't Condition.await() need synchronized block?"
     * ===================================================================
     * NO! Condition is used with explicit Lock, not synchronized:
     * 
     * WRONG:
     *   synchronized (nope) {
     *       condition.await();
     *   }
     * 
     * RIGHT:
     *   lock.lock();
     *   try {
     *       condition.await();
     *   } finally {
     *       lock.unlock();
     *   }
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 13: PERFORMANCE & WHEN TO USE WHAT
     * ========================================================================
     * 
     * USE wait/notify WHEN:
     * =====================
     * - Need fine-grained control
     * - Complex coordination patterns
     * - Multiple conditions on same lock
     * 
     * USE BlockingQueue WHEN:
     * =======================
     * - Producer-consumer pattern
     * - Want simplicity and safety
     * - Don't need complex coordination
     * - MOST COMMON CASE - use this by default!
     * 
     * USE Condition WHEN:
     * ===================
     * - Using explicit Lock (not synchronized)
     * - Need multiple conditions per lock
     * - Want fairer locking (fair=true)
     * 
     * USE Semaphore WHEN:
     * ===================
     * - Limiting access to N resources
     * - Connection pools
     * - Rate limiting
     * 
     * USE CountDownLatch WHEN:
     * ========================
     * - One-time initialization coordination
     * - All threads must wait for N events
     * - No need to reuse barrier
     * 
     * USE CyclicBarrier WHEN:
     * =======================
     * - Synchronization between rounds
     * - All threads must meet at checkpoint
     * - Barrier can be reused
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 14: REAL-WORLD APPLICATIONS
     * ========================================================================
     * 
     * 1. WEB SERVER REQUEST HANDLING
     *    - Request comes (producer)
     *    - Queued in BlockingQueue
     *    - Worker threads take requests (consumers)
     *    - Process and send response
     * 
     * 2. DATABASE CONNECTION POOLING
     *    - Max 10 connections (Semaphore(10))
     *    - Thread acquires permit before connecting
     *    - At most 10 connections at once
     *    - Release permit after use
     * 
     * 3. GAME SERVER - PLAYER MOVEMENTS
     *    - Multiple players send move events
     *    - Main game thread waits for events (BlockingQueue)
     *    - Processes all events each frame
     *    - Updates game state
     * 
     * 4. PARALLEL DATA PROCESSING
     *    - Multiple threads produce data
     *    - Multiple threads consume/process
     *    - Use BlockingQueue between stages
     *    - Pipeline pattern
     * 
     * 5. APPLICATION STARTUP
     *    - Multiple components need initialization
     *    - Use CountDownLatch to wait
     *    - Start app only when all ready
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 15: BEST PRACTICES CHECKLIST
     * ========================================================================
     * 
     * [✓] Use BlockingQueue by default for producer-consumer
     * [✓] Always use while loops with wait(), never if
     * [✓] Always use notifyAll() instead of notify()
     * [✓] Keep synchronized blocks small
     * [✓] Move heavy processing OUTSIDE synchronized blocks
     * [✓] Use try-finally for lock.unlock()
     * [✓] Handle InterruptedException properly (restore status)
     * [✓] Document your communication pattern clearly
     * [✓] Test with multiple threads (stress testing)
     * [✓] Use jstack to detect deadlocks
     * [✓] One-time use? -> CountDownLatch
     * [✓] Multiple rounds? -> CyclicBarrier
     * [✓] Resource limiting? -> Semaphore
     * [✓] Producer-consumer? -> BlockingQueue
     * [✓] Complex patterns? -> Condition variables
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 16: TESTING EXAMPLE - Producer-Consumer System
     * ========================================================================
     */
    
    public static class CompleteExample {
        public static void main(String[] args) throws InterruptedException {
            System.out.println("=== Thread Communication Examples ===\n");
            
            // Example 1: ProducerConsumer with BlockingQueue
            System.out.println("1. BlockingQueue Example:");
            ProducerConsumer_BlockingQueueExample example1 =
                new ProducerConsumer_BlockingQueueExample();
            
            Thread producer = new Thread(() -> {
                for (int i = 1; i <= 5; i++) {
                    example1.produce("Item-" + i);
                    try { Thread.sleep(200); } catch (InterruptedException e) {}
                }
            });
            
            Thread consumer = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    example1.consume();
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                }
            });
            
            producer.start();
            consumer.start();
            producer.join();
            consumer.join();
            
            System.out.println("\n2. CountDownLatch Example:");
            CountDownLatchExample.demonstrateCountDownLatch();
            
            System.out.println("\n3. Semaphore Example (max 2 threads access):");
            SemaphoreExample semaphoreExample = new SemaphoreExample();
            for (int i = 0; i < 5; i++) {
                Thread t = new Thread(() -> semaphoreExample.limitedAccess(), "Thread-" + i);
                t.start();
            }
        }
    }
}
