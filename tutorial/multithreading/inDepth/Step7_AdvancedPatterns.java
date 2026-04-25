package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * STEP 7: ADVANCED THREADING PATTERNS
 * 
 * This step covers advanced concepts:
 * 1. CountDownLatch - Wait for multiple threads to complete
 * 2. CyclicBarrier - Synchronize multiple threads at a point
 * 3. Semaphore - Control access to limited resources
 * 4. ReadWriteLock - Multiple readers OR single writer
 * 5. Phaser - Advanced synchronization (Java 7+)
 */

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

// ==================== 1. COUNTDOWNLATCH ====================
/**
 * CountDownLatch:
 * - Allows one or more threads to wait until a set of operations
 * - Initialized with a count
 * - Each worker thread calls countDown() when done
 * - Main thread calls await() to wait for all workers
 * - One-time use (count cannot be reset)
 */
class CountDownLatchDemo {
    public static void demonstrateCountDownLatch() throws InterruptedException {
        System.out.println("--- COUNTDOWNLATCH DEMO ---");
        
        int workerCount = 3;
        CountDownLatch latch = new CountDownLatch(workerCount);
        
        // Create worker threads
        for (int i = 0; i < workerCount; i++) {
            int workerId = i + 1;
            new Thread(() -> {
                try {
                    System.out.println("Worker-" + workerId + " started");
                    Thread.sleep(1000 + (workerId * 500));  // Variable work time
                    System.out.println("Worker-" + workerId + " completed");
                    latch.countDown();  // Signal completion
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        System.out.println("Main thread waiting for all workers...");
        latch.await();  // Block until count reaches 0
        System.out.println("All workers completed! Main thread resuming.\n");
    }
}

// ==================== 2. CYCLICBARRIER ====================
/**
 * CyclicBarrier:
 * - Synchronizes multiple threads at a common point
 * - All threads wait at barrier until they all arrive
 * - Can be reset and reused (unlike CountDownLatch)
 * - Useful for iterations where threads must sync between phases
 */
class CyclicBarrierDemo {
    public static void demonstrateCyclicBarrier() throws InterruptedException {
        System.out.println("--- CYCLICBARRIER DEMO ---");
        
        int threadCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            // Action executed when all threads reach barrier
            System.out.println("*** All threads reached barrier! ***");
        });
        
        for (int i = 0; i < threadCount; i++) {
            int threadId = i + 1;
            new Thread(() -> {
                try {
                    for (int phase = 1; phase <= 2; phase++) {
                        System.out.println("Thread-" + threadId + " working on phase " + phase);
                        Thread.sleep(500 + (threadId * 300));
                        System.out.println("Thread-" + threadId + " reached barrier for phase " + phase);
                        barrier.await();  // Wait for all threads
                        System.out.println("Thread-" + threadId + " proceeding to phase " + (phase + 1));
                    }
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        Thread.sleep(5000);  // Let all phases complete
        System.out.println();
    }
}

// ==================== 3. SEMAPHORE ====================
/**
 * Semaphore:
 * - Controls access to a resource with a permit count
 * - acquire() reduces available permits (blocks if none available)
 * - release() increases available permits
 * - Useful for limiting concurrent access (connection pools, thread pools)
 * - Can be binary (like mutex) or counting
 */
class SemaphoreDemo {
    private static class PrinterPool {
        private Semaphore printerSemaphore = new Semaphore(2);  // 2 printers
        private int printerId = 0;
        
        public void usePrinter(String jobName) throws InterruptedException {
            printerSemaphore.acquire();  // Get a printer
            try {
                int assignedPrinter = ++printerId;
                System.out.println(jobName + " using Printer-" + assignedPrinter);
                Thread.sleep(1000);  // Simulate printing
                System.out.println(jobName + " finished on Printer-" + assignedPrinter);
            } finally {
                printerSemaphore.release();  // Release the printer
            }
        }
    }
    
    public static void demonstrateSemaphore() throws InterruptedException {
        System.out.println("--- SEMAPHORE DEMO (Resource Pool) ---");
        
        PrinterPool pool = new PrinterPool();
        
        // 5 jobs competing for 2 printers
        for (int i = 1; i <= 5; i++) {
            int jobId = i;
            new Thread(() -> {
                try {
                    pool.usePrinter("Job-" + jobId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        Thread.sleep(6000);  // Let all jobs complete
        System.out.println();
    }
}

// ==================== 4. READ-WRITE LOCK ====================
/**
 * ReadWriteLock:
 * - Multiple threads can read simultaneously
 * - Only ONE thread can write (exclusive access)
 * - No thread can read while writing
 * - Useful when reads >> writes (like caches)
 */
class ReadWriteLockDemo {
    private static class CachedData {
        private String data = "Initial Data";
        private ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public String readData() {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " reading: " + data);
                Thread.sleep(100);  // Simulate read operation
                return data;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } finally {
                lock.readLock().unlock();
            }
        }
        
        public void writeData(String newData) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " writing: " + newData);
                Thread.sleep(200);  // Simulate write operation (slower)
                this.data = newData;
                System.out.println(Thread.currentThread().getName() + " write completed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    public static void demonstrateReadWriteLock() throws InterruptedException {
        System.out.println("--- READ-WRITE LOCK DEMO ---");
        
        CachedData cache = new CachedData();
        
        // 3 reader threads
        for (int i = 1; i <= 3; i++) {
            int readerId = i;
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    cache.readData();
                }
            }, "Reader-" + i).start();
        }
        
        // 1 writer thread
        new Thread(() -> {
            try {
                Thread.sleep(150);
                cache.writeData("Updated Data");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Writer-1").start();
        
        Thread.sleep(2000);  // Let all operations complete
        System.out.println();
    }
}

// ==================== 5. PHASER ====================
/**
 * Phaser:
 * - Advanced synchronization for multi-phase computations
 * - Like CyclicBarrier but more flexible
 * - Threads can join/leave dynamically
 * - Supports multiple phases
 */
class PhaserDemo {
    public static void demonstratePhaser() throws InterruptedException {
        System.out.println("--- PHASER DEMO ---");
        
        Phaser phaser = new Phaser(1);  // Initial party count = 1 (main thread)
        
        // 3 tasks in 2 phases
        for (int i = 1; i <= 3; i++) {
            int taskId = i;
            phaser.register();  // Register this task
            
            new Thread(() -> {
                try {
                    // Phase 1
                    System.out.println("Task-" + taskId + " starting Phase 1");
                    Thread.sleep(1000);
                    System.out.println("Task-" + taskId + " Phase 1 done, waiting at barrier");
                    phaser.arriveAndAwaitAdvance();  // Wait for all at phase 1
                    
                    // Phase 2
                    System.out.println("Task-" + taskId + " starting Phase 2");
                    Thread.sleep(800);
                    System.out.println("Task-" + taskId + " Phase 2 done");
                    phaser.arriveAndDeregister();  // Signal completion and leave
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        
        // Main thread waits for all to complete all phases
        phaser.arriveAndAwaitAdvance();  // Wait for phase 1
        System.out.println("Main: All tasks completed Phase 1");
        
        phaser.arriveAndAwaitAdvance();  // Wait for phase 2
        System.out.println("Main: All tasks completed!");
        System.out.println();
    }
}

public class Step7_AdvancedPatterns {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n========== ADVANCED THREADING PATTERNS TUTORIAL ==========\n");
        
        // ========== 1. COUNTDOWNLATCH ==========
        CountDownLatchDemo.demonstrateCountDownLatch();
        
        // ========== 2. CYCLICBARRIER ==========
        CyclicBarrierDemo.demonstrateCyclicBarrier();
        
        // ========== 3. SEMAPHORE ==========
        SemaphoreDemo.demonstrateSemaphore();
        
        // ========== 4. READ-WRITE LOCK ==========
        ReadWriteLockDemo.demonstrateReadWriteLock();
        
        // ========== 5. PHASER ==========
        PhaserDemo.demonstratePhaser();
        
        // ========== SUMMARY ==========
        System.out.println("--- SUMMARY OF ADVANCED PATTERNS ---");
        System.out.println("CountDownLatch : Wait for N threads to complete (one-time)");
        System.out.println("CyclicBarrier  : Synchronize at checkpoints, reusable");
        System.out.println("Semaphore      : Control resource access, counting or binary");
        System.out.println("ReadWriteLock  : Multiple readers, single writer");
        System.out.println("Phaser         : Multi-phase synchronization, dynamic registration");
    }
}

