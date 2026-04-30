package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.Locking.Explicit;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

/**
╔════════════════════════════════════════════════════════════════════════════════╗
║          COMPREHENSIVE GUIDE TO LOCK INTERRUPTIBLY                             ║
╠════════════════════════════════════════════════════════════════════════════════╣
║                                                                                ║
║ 1. WHAT IS lockInterruptibly()?                                                ║
║    ├─ Method that acquires lock unless current thread gets interrupted         ║
║    ├─ Throws InterruptedException if thread is interrupted while waiting       ║
║    ├─ Allows threads to respond to cancellation requests gracefully            ║
║    └─ More responsive than lock() for cancellable operations                   ║
║                                                                                ║
║ 2. KEY DIFFERENCES FROM lock():                                                ║
║    ┌─────────────────────┬──────────────────┬──────────────────────┐           ║
║    │ Method              │ lock()           │ lockInterruptibly()  │           ║
║    ├─────────────────────┼──────────────────┼──────────────────────┤           ║
║    │ Interruptible?      │ NO               │ YES                  │           ║
║    │ Throws Exception?   │ NO               │ YES (checked)        │           ║
║    │ Can be cancelled?   │ NO               │ YES                  │           ║
║    │ Responsive?         │ Low              │ High                 │           ║
║    └─────────────────────┴──────────────────┴──────────────────────┘           ║
║                                                                                ║
║ 3. USE CASES:                                                                  ║
║    ├─ Cancellable long-running operations                                      ║
║    ├─ Server threads that need graceful shutdown                               ║
║    ├─ GUI applications responding to user cancellation                         ║
║    ├─ Worker threads in thread pools                                           ║
║    ├─ Any operation where "stop" signal should work immediately                ║
║    └─ Preventing thread starvation/hanging                                     ║
║                                                                                ║
║ 4. BENEFITS:                                                                   ║
║    ├─ Responsive application design (no hanging threads)                       ║
║    ├─ Graceful shutdown capability                                             ║
║    ├─ Resource cleanup on interruption                                         ║
║    ├─ Better thread control in thread pools                                    ║
║    └─ Prevents threads from getting stuck indefinitely                         ║
║                                                                                ║
║ 5. IMPORTANT RULES:                                                            ║
║    ├─ Always handle InterruptedException properly                              ║
║    ├─ Always maintain lock acquisition counter (like lock())                   ║
║    ├─ If interrupted, call unlock() same number of times                       ║
║    ├─ Consider whether to restore interrupt status                             ║
║    └─ Don't ignore InterruptedException casually                               ║
║                                                                                ║
╚════════════════════════════════════════════════════════════════════════════════╝
*/

public class LockIntruptably {

    /**
    ═══════════════════════════════════════════════════════════════════════════
    BEGINNER LEVEL: Basic lockInterruptibly() Usage
    ═══════════════════════════════════════════════════════════════════════════
    
    Pattern:
    1. Try to acquire lock using lockInterruptibly()
    2. If lock acquired, enter try block
    3. Perform critical section work
    4. In finally, unlock only if lock was acquired
    5. Catch InterruptedException if thread is interrupted
    */
    
    private static class BasicInterruptibleExample {
        private final Lock lock = new ReentrantLock();
        private int counter = 0;
        
        public void incrementWithInterrupt() {
            System.out.println("Thread: " + Thread.currentThread().getName() + 
                             " attempting to acquire lock...");
            
            try {
                // Interruptible lock acquisition
                lock.lockInterruptibly();
                
                try {
                    System.out.println("Thread: " + Thread.currentThread().getName() + 
                                     " acquired lock!");
                    counter++;
                    System.out.println("Counter incremented to: " + counter);
                    
                    // Simulate some work
                    Thread.sleep(2000);
                    
                } finally {
                    // Release lock only if acquired
                    lock.unlock();
                    System.out.println("Thread: " + Thread.currentThread().getName() + 
                                     " released lock!");
                }
                
            } catch (InterruptedException e) {
                // Thread was interrupted while waiting for lock
                System.out.println("Thread: " + Thread.currentThread().getName() + 
                                 " was INTERRUPTED while waiting for lock!");
                
                // Restore interrupt status
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
    ═══════════════════════════════════════════════════════════════════════════
    INTERMEDIATE LEVEL: Using tryLock(timeout) vs lockInterruptibly()
    ═══════════════════════════════════════════════════════════════════════════
    
    When to use what?
    - lockInterruptibly(): When thread interruption is natural way to cancel
    - tryLock(timeout): When you want to give up after specific time
    */
    
    private static class ComparisonExample {
        private final Lock lock = new ReentrantLock();
        
        // Interruptible approach - cancels when interrupted
        public void interruptibleApproach() {
            System.out.println("\n[INTERRUPTIBLE APPROACH]");
            try {
                System.out.println("Waiting for lock (interruptibly)...");
                lock.lockInterruptibly();
                
                try {
                    System.out.println("Lock acquired! Doing work...");
                    Thread.sleep(1000);
                } finally {
                    lock.unlock();
                }
                
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED! Stopping immediately.");
                Thread.currentThread().interrupt();
            }
        }
        
        // Try-lock with timeout approach - gives up after timeout
        public void tryLockApproach() throws InterruptedException {
            System.out.println("\n[TRY-LOCK WITH TIMEOUT APPROACH]");
            
            // Try to get lock within 2 seconds
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                try {
                    System.out.println("Lock acquired! Doing work...");
                    Thread.sleep(1000);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Could not acquire lock within timeout!");
            }
        }
    }

    /**
    ═══════════════════════════════════════════════════════════════════════════
    INTERMEDIATE LEVEL: Cancellable Task Pattern
    ═══════════════════════════════════════════════════════════════════════════
    
    Demonstrates proper handling of InterruptedException
    in a real-world cancellable task
    */
    
    private static class CancellableTask implements Runnable {
        private final Lock lock = new ReentrantLock();
        private final String taskName;
        
        public CancellableTask(String taskName) {
            this.taskName = taskName;
        }
        
        @Override
        public void run() {
            System.out.println("[" + taskName + "] Starting...");
            
            try {
                // Try to acquire lock interruptibly
                System.out.println("[" + taskName + "] Waiting for lock...");
                lock.lockInterruptibly();
                
                try {
                    System.out.println("[" + taskName + "] Lock acquired, doing work...");
                    
                    // Simulate long-running operation
                    for (int i = 0; i < 5; i++) {
                        // Check if interrupted after each iteration
                        if (Thread.currentThread().isInterrupted()) {
                            System.out.println("[" + taskName + "] Detected interruption, cleaning up...");
                            throw new InterruptedException("Cancelled during execution");
                        }
                        
                        System.out.println("[" + taskName + "] Working... step " + (i + 1) + "/5");
                        Thread.sleep(500);
                    }
                    
                    System.out.println("[" + taskName + "] Work completed successfully!");
                    
                } finally {
                    lock.unlock();
                    System.out.println("[" + taskName + "] Lock released!");
                }
                
            } catch (InterruptedException e) {
                System.out.println("[" + taskName + "] INTERRUPTED! Performing cleanup...");
                
                // IMPORTANT: Restore interrupt status or re-throw exception
                Thread.currentThread().interrupt();
                
                // Perform any necessary cleanup
                System.out.println("[" + taskName + "] Cleanup completed!");
            }
        }
    }

    /**
    ═══════════════════════════════════════════════════════════════════════════
    INTERMEDIATE LEVEL: Reentrant Pattern with lockInterruptibly()
    ═══════════════════════════════════════════════════════════════════════════
    
    Shows how reentrancy works with lockInterruptibly()
    Same thread can acquire same lock multiple times
    */
    
    private static class ReentrantInterruptibleExample {
        private final Lock lock = new ReentrantLock();
        
        public void outerMethodInterruptible() throws InterruptedException {
            System.out.println("\n[REENTRANT WITH INTERRUPTIBLE]");
            lock.lockInterruptibly();
            try {
                System.out.println("1st lock acquired by: " + Thread.currentThread().getName());
                innerMethodInterruptible();
            } finally {
                lock.unlock();
            }
        }
        
        private void innerMethodInterruptible() throws InterruptedException {
            // Same thread can acquire lock again
            lock.lockInterruptibly();
            try {
                System.out.println("2nd lock acquired by: " + Thread.currentThread().getName());
                System.out.println("Both locks held by same thread - no deadlock!");
            } finally {
                lock.unlock();
            }
        }
    }

    /**
    ═══════════════════════════════════════════════════════════════════════════
    ADVANCED LEVEL: Producer-Consumer with Interruption
    ═══════════════════════════════════════════════════════════════════════════
    
    Real-world example: Shared buffer with proper interrupt handling
    Producers and consumers can be canceled gracefully
    */
    
    private static class SharedBuffer {
        private final List<Integer> buffer = new ArrayList<>();
        private final int capacity;
        private final Lock lock = new ReentrantLock();
        
        public SharedBuffer(int capacity) {
            this.capacity = capacity;
        }
        
        public void produce(int value) throws InterruptedException {
            lock.lockInterruptibly();
            try {
                while (buffer.size() >= capacity) {
                    System.out.println("[PRODUCER] Buffer full, waiting...");
                    Thread.sleep(100);
                }
                
                buffer.add(value);
                System.out.println("[PRODUCER] Produced: " + value + ", Buffer size: " + buffer.size());
                
            } finally {
                lock.unlock();
            }
        }
        
        public int consume() throws InterruptedException {
            lock.lockInterruptibly();
            try {
                while (buffer.isEmpty()) {
                    System.out.println("[CONSUMER] Buffer empty, waiting...");
                    Thread.sleep(100);
                }
                
                int value = buffer.remove(0);
                System.out.println("[CONSUMER] Consumed: " + value + ", Buffer size: " + buffer.size());
                return value;
                
            } finally {
                lock.unlock();
            }
        }
    }

    /**
    ═══════════════════════════════════════════════════════════════════════════
    ADVANCED LEVEL: Thread Pool Task with Proper Shutdown
    ═══════════════════════════════════════════════════════════════════════════
    
    Demonstrates proper thread pool design with interruptible locks
    */
    
    private static class ThreadPoolTask implements Runnable {
        private final Lock sharedLock;
        private final String taskId;
        private final String resource;
        
        public ThreadPoolTask(Lock sharedLock, String taskId, String resource) {
            this.sharedLock = sharedLock;
            this.taskId = taskId;
            this.resource = resource;
        }
        
        @Override
        public void run() {
            try {
                System.out.println("[TASK-" + taskId + "] Requesting " + resource + "...");
                
                // Use interruptibly for proper shutdown support
                sharedLock.lockInterruptibly();
                
                try {
                    System.out.println("[TASK-" + taskId + "] Acquired " + resource);
                    
                    // Simulate work
                    Thread.sleep(1000);
                    
                    System.out.println("[TASK-" + taskId + "] Work completed");
                    
                } finally {
                    sharedLock.unlock();
                    System.out.println("[TASK-" + taskId + "] Released " + resource);
                }
                
            } catch (InterruptedException e) {
                System.out.println("[TASK-" + taskId + "] Interrupted during execution");
                
                // Signal thread pool to stop this thread
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
    ═══════════════════════════════════════════════════════════════════════════
    ADVANCED LEVEL: Deadlock Prevention with Timeout + Interruption
    ═══════════════════════════════════════════════════════════════════════════
    
    Combines tryLock with timeout and interruption handling
    */
    
    private static class DeadlockPreventionExample {
        private final Lock lock1 = new ReentrantLock();
        private final Lock lock2 = new ReentrantLock();
        private int sharedResource1 = 0;
        private int sharedResource2 = 0;
        
        public void transferBetweenResources() throws InterruptedException {
            // Try to acquire both locks in safe order
            lock1.lockInterruptibly();
            System.out.println("Lock1 acquired");
            
            try {
                // Try to get second lock with timeout to prevent deadlock
                if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Lock2 acquired");
                        sharedResource1++;
                        sharedResource2++;
                        System.out.println("Transfer completed");
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    System.out.println("Could not acquire lock2, rolling back...");
                }
                
            } finally {
                lock1.unlock();
            }
        }
    }

    /**
    ═══════════════════════════════════════════════════════════════════════════
    EFFICIENT SEQUENTIAL OPERATIONS: Best Practices Summary
    ═══════════════════════════════════════════════════════════════════════════
    
    BEST PRACTICES for using lockInterruptibly():
    
    1. ALWAYS wrap in try-catch for InterruptedException
       - Don't ignore or swallow the exception
       - Restore interrupt status or propagate exception
    
    2. MINIMAL CRITICAL SECTION
       - Keep lock-holding time short
       - Do heavy work outside critical section
    
    3. PROPER RESOURCE CLEANUP
       - Use finally block to ensure unlock()
       - Clean up resources even if interrupted
    
    4. INTERRUPT STATUS HANDLING
       - Option A: Thread.currentThread().interrupt() - mark for later
       - Option B: throw new InterruptedException() - propagate
       - Option C: Handle and continue
    
    5. REENTRANCY AWARENESS
       - Same thread can lock multiple times
       - Each lock increments counter
       - Each unlock decrements counter
    
    6. COMBINE WITH OTHER TECHNIQUES
       - Use tryLock(timeout) for deadlock prevention
       - Use Condition for signaling
       - Monitor thread interruption status
    
    7. AVOID COMMON MISTAKES
       - DON'T ignore InterruptedException silently
       - DON'T forget to unlock in finally
       - DON'T block for long time while holding lock
       - DON'T use lockInterruptibly if you can't handle interruption
    */

    /**
    ═══════════════════════════════════════════════════════════════════════════
    DEMONSTRATION: Complete Real-World Example
    ═══════════════════════════════════════════════════════════════════════════
    */

    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   LOCK INTERRUPTIBLY COMPREHENSIVE GUIDE           ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        // ─────────────────────────────────────────────────────────────────
        // 1. BASIC INTERRUPTIBLE EXAMPLE
        // ─────────────────────────────────────────────────────────────────
        System.out.println("1. BASIC INTERRUPTIBLE LOCK USAGE:");
        System.out.println("─".repeat(50));
        BasicInterruptibleExample basic = new BasicInterruptibleExample();
        
        Thread basicThread = new Thread(() -> {
            try {
                basic.incrementWithInterrupt();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }, "BasicThread");
        
        basicThread.start();
        basicThread.join();

        // ─────────────────────────────────────────────────────────────────
        // 2. COMPARISON: LOCKINTERRUPTIBLY vs TRYLOCK
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n2. COMPARISON OF APPROACHES:");
        System.out.println("─".repeat(50));
        ComparisonExample comparison = new ComparisonExample();
        
        try {
            comparison.interruptibleApproach();
            comparison.tryLockApproach();
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }

        // ─────────────────────────────────────────────────────────────────
        // 3. REENTRANT WITH INTERRUPTIBLE
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n3. REENTRANCY WITH INTERRUPTIBLE:");
        System.out.println("─".repeat(50));
        ReentrantInterruptibleExample reentrant = new ReentrantInterruptibleExample();
        try {
            reentrant.outerMethodInterruptible();
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }

        // ─────────────────────────────────────────────────────────────────
        // 4. CANCELLABLE TASK EXAMPLE
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n4. CANCELLABLE TASK WITH INTERRUPT:");
        System.out.println("─".repeat(50));
        
        Thread task1 = new Thread(new CancellableTask("Task-1"));
        task1.start();
        
        // Let it run for 2 seconds then interrupt
        Thread.sleep(2000);
        System.out.println("[MAIN] Interrupting Task-1...");
        task1.interrupt();
        
        try {
            task1.join(3000);
        } catch (InterruptedException e) {
            System.out.println("Main interrupted");
        }

        // ─────────────────────────────────────────────────────────────────
        // 5. PRODUCER-CONSUMER WITH INTERRUPTIBLE
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n5. PRODUCER-CONSUMER PATTERN:");
        System.out.println("─".repeat(50));
        
        SharedBuffer buffer = new SharedBuffer(3);
        
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i * 10);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                System.out.println("[PRODUCER] Interrupted!");
                Thread.currentThread().interrupt();
            }
        }, "Producer");
        
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    buffer.consume();
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) {
                System.out.println("[CONSUMER] Interrupted!");
                Thread.currentThread().interrupt();
            }
        }, "Consumer");
        
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        // ─────────────────────────────────────────────────────────────────
        // 6. THREAD POOL SIMULATION
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n6. THREAD POOL WITH PROPER SHUTDOWN:");
        System.out.println("─".repeat(50));
        
        Lock poolLock = new ReentrantLock();
        
        Thread poolTask1 = new Thread(new ThreadPoolTask(poolLock, "1", "Database"));
        Thread poolTask2 = new Thread(new ThreadPoolTask(poolLock, "2", "Database"));
        Thread poolTask3 = new Thread(new ThreadPoolTask(poolLock, "3", "Database"));
        
        poolTask1.start();
        poolTask2.start();
        
        Thread.sleep(500);
        System.out.println("[MAIN] Shutting down pool - interrupting tasks...");
        
        poolTask1.interrupt();
        poolTask2.interrupt();
        
        poolTask1.join(2000);
        poolTask2.join(2000);

        // ─────────────────────────────────────────────────────────────────
        // SUMMARY
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n" + "═".repeat(50));
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═".repeat(50));
        System.out.println("✓ lockInterruptibly() allows graceful cancellation");
        System.out.println("✓ Always catch InterruptedException properly");
        System.out.println("✓ Always use try-finally to ensure unlock");
        System.out.println("✓ Restore interrupt status after catching exception");
        System.out.println("✓ Keep critical sections small");
        System.out.println("✓ Perfect for thread pools and cancellable tasks");
        System.out.println("✓ Better than lock() for responsive applications");
        System.out.println("═".repeat(50));
    }
}
