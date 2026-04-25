package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * STEP 3: THREAD SYNCHRONIZATION
 * 
 * PROBLEM: When multiple threads access shared resources simultaneously,
 * race conditions occur - unpredictable results due to timing differences.
 * 
 * SOLUTION: Synchronization mechanisms to control access to shared resources.
 * 
 * KEY CONCEPTS:
 * 1. Race Condition: When multiple threads read/write shared data without coordination
 * 2. Critical Section: Code block that accesses shared resource
 * 3. Mutex/Lock: Ensures only one thread enters critical section at a time
 * 4. Mutual Exclusion: Only one thread can access resource at a time
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// ==================== PROBLEM: RACE CONDITION ====================
class UnsafeCounter {
    private int count = 0;  // Shared resource - DANGER!
    
    // This method has a RACE CONDITION
    public void increment() {
        // This looks like one operation, but it's actually 3 operations:
        // 1. Read count value
        // 2. Add 1
        // 3. Write back to count
        // If 2+ threads do this simultaneously, some increments are lost!
        count++;
    }
    
    public int getCount() {
        return count;
    }
}

// ==================== SOLUTION 1: SYNCHRONIZED METHOD ====================
class SynchronizedMethodCounter {
    private int count = 0;
    
    // synchronized keyword ensures only ONE thread can execute this at a time
    // Lock is on the object (this)
    public synchronized void increment() {
        count++;
    }
    
    // Can also synchronize getter
    public synchronized int getCount() {
        return count;
    }
}

// ==================== SOLUTION 2: SYNCHRONIZED BLOCK ====================
class SynchronizedBlockCounter {
    private int count = 0;
    private final Object lock = new Object();  // Lock object
    
    public void increment() {
        // Synchronize only the critical section
        // More granular control than synchronized method
        // Lock is on the specified object (lock in this case)
        synchronized (lock) {
            count++;
        }
    }
    
    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
    
    // Non-critical work can happen without synchronization (better performance)
    public void someOtherMethod() {
        System.out.println("This doesn't need synchronization");
        // ... do some work
        synchronized (lock) {
            // Only critical part is synchronized
            count++;
        }
    }
}

// ==================== SOLUTION 3: REENTRANT LOCK (java.util.concurrent) ====================
/**
 * ReentrantLock is more flexible than synchronized:
 * - Can try to acquire lock with timeout
 * - Can check if locked
 * - Can be held by same thread multiple times (reentrant)
 * - More explicit and readable
 */
class ReentrantLockCounter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();
    
    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            // IMPORTANT: Always unlock in finally to ensure it's released
            // even if exception occurs
            lock.unlock();
        }
    }
    
    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
    
    // Attempt to acquire lock with timeout
    public boolean tryIncrement() {
        try {
            // Try to get lock within 2 seconds
            if (lock.tryLock(2, java.util.concurrent.TimeUnit.SECONDS)) {
                try {
                    count++;
                    return true;
                } finally {
                    lock.unlock();
                }
            }
            return false;  // Couldn't acquire lock
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}

// ==================== BANK ACCOUNT EXAMPLE (Real-world scenario) ====================
class BankAccount {
    private double balance = 1000;
    private final Object lock = new Object();
    
    public void deposit(double amount) {
        synchronized (lock) {
            double temp = balance;  // Read
            temp += amount;         // Modify
            try {
                Thread.sleep(10);   // Simulate processing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            balance = temp;         // Write
        }
    }
    
    public void withdraw(double amount) {
        synchronized (lock) {
            if (balance >= amount) {
                double temp = balance;
                temp -= amount;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                balance = temp;
            }
        }
    }
    
    public synchronized double getBalance() {
        return balance;
    }
}

// ==================== DEMONSTRATOR ====================
class CounterTester implements Runnable {
    private Object counter;
    private int iterations;
    private String name;
    
    public CounterTester(Object counter, int iterations, String name) {
        this.counter = counter;
        this.iterations = iterations;
        this.name = name;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            if (counter instanceof SynchronizedMethodCounter) {
                ((SynchronizedMethodCounter) counter).increment();
            } else if (counter instanceof SynchronizedBlockCounter) {
                ((SynchronizedBlockCounter) counter).increment();
            } else if (counter instanceof ReentrantLockCounter) {
                ((ReentrantLockCounter) counter).increment();
            } else if (counter instanceof UnsafeCounter) {
                ((UnsafeCounter) counter).increment();
            }
        }
    }
}

public class Step3_ThreadSynchronization {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n========== THREAD SYNCHRONIZATION TUTORIAL ==========\n");
        
        final int NUM_THREADS = 5;
        final int ITERATIONS = 1000;
        
        // ========== PROBLEM: RACE CONDITION ==========
        System.out.println("--- PROBLEM: RACE CONDITION (Unsafe) ---");
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        Thread[] unsafeThreads = new Thread[NUM_THREADS];
        
        for (int i = 0; i < NUM_THREADS; i++) {
            unsafeThreads[i] = new Thread(new CounterTester(unsafeCounter, ITERATIONS, "Unsafe-" + i));
            unsafeThreads[i].start();
        }
        
        for (Thread t : unsafeThreads) {
            t.join();
        }
        
        System.out.println("Expected: " + (NUM_THREADS * ITERATIONS));
        System.out.println("Actual: " + unsafeCounter.getCount());
        System.out.println("Result: INCORRECT - Race condition occurred!\n");
        
        // ========== SOLUTION 1: SYNCHRONIZED METHOD ==========
        System.out.println("--- SOLUTION 1: Synchronized Method ---");
        SynchronizedMethodCounter syncMethodCounter = new SynchronizedMethodCounter();
        Thread[] syncMethodThreads = new Thread[NUM_THREADS];
        
        for (int i = 0; i < NUM_THREADS; i++) {
            syncMethodThreads[i] = new Thread(new CounterTester(syncMethodCounter, ITERATIONS, "SyncMethod-" + i));
            syncMethodThreads[i].start();
        }
        
        for (Thread t : syncMethodThreads) {
            t.join();
        }
        
        System.out.println("Expected: " + (NUM_THREADS * ITERATIONS));
        System.out.println("Actual: " + syncMethodCounter.getCount());
        System.out.println("Result: CORRECT - Synchronized method works!\n");
        
        // ========== SOLUTION 2: SYNCHRONIZED BLOCK ==========
        System.out.println("--- SOLUTION 2: Synchronized Block ---");
        SynchronizedBlockCounter syncBlockCounter = new SynchronizedBlockCounter();
        Thread[] syncBlockThreads = new Thread[NUM_THREADS];
        
        for (int i = 0; i < NUM_THREADS; i++) {
            syncBlockThreads[i] = new Thread(new CounterTester(syncBlockCounter, ITERATIONS, "SyncBlock-" + i));
            syncBlockThreads[i].start();
        }
        
        for (Thread t : syncBlockThreads) {
            t.join();
        }
        
        System.out.println("Expected: " + (NUM_THREADS * ITERATIONS));
        System.out.println("Actual: " + syncBlockCounter.getCount());
        System.out.println("Result: CORRECT - Synchronized block works!\n");
        
        // ========== SOLUTION 3: REENTRANT LOCK ==========
        System.out.println("--- SOLUTION 3: ReentrantLock ---");
        ReentrantLockCounter lockCounter = new ReentrantLockCounter();
        Thread[] lockThreads = new Thread[NUM_THREADS];
        
        for (int i = 0; i < NUM_THREADS; i++) {
            lockThreads[i] = new Thread(new CounterTester(lockCounter, ITERATIONS, "Lock-" + i));
            lockThreads[i].start();
        }
        
        for (Thread t : lockThreads) {
            t.join();
        }
        
        System.out.println("Expected: " + (NUM_THREADS * ITERATIONS));
        System.out.println("Actual: " + lockCounter.getCount());
        System.out.println("Result: CORRECT - ReentrantLock works!\n");
        
        // ========== BANK ACCOUNT EXAMPLE ==========
        System.out.println("--- REAL-WORLD: Bank Account Transfers ---");
        BankAccount account = new BankAccount();
        
        Thread depositor = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
            }
        });
        
        Thread withdrawer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(50);
            }
        });
        
        System.out.println("Initial Balance: $" + account.getBalance());
        depositor.start();
        withdrawer.start();
        
        depositor.join();
        withdrawer.join();
        
        System.out.println("Final Balance: $" + account.getBalance());
        System.out.println("Expected: $1250 (1000 + 250 deposit - 250 withdrawal)\n");
        
        // ========== COMPARISON ==========
        System.out.println("--- SYNCHRONIZATION COMPARISON ---");
        System.out.println("synchronized method   : Simple, locks entire method");
        System.out.println("synchronized block    : Granular, locks only critical section");
        System.out.println("ReentrantLock         : Flexible, supports timeout/tryLock");
        System.out.println("Best practice         : Use synchronized for simple cases, Lock for complex");
    }
}

