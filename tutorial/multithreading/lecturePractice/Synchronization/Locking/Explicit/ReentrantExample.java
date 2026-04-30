package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.Locking.Explicit;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
╔════════════════════════════════════════════════════════════════════════════════╗
║                 COMPREHENSIVE GUIDE TO REENTRANT LOCK                          ║
╠════════════════════════════════════════════════════════════════════════════════╣
║                                                                                ║
║ 1. WHAT IS REENTRANT LOCK?                                                     ║
║    ├─ A ReentrantLock is a mutual exclusion lock (mutex) that can be           ║
║    │  acquired multiple times by the same thread                               ║
║    ├─ It's an explicit alternative to synchronized keyword                     ║
║    └─ Provides more control over lock behavior and waiting threads             ║
║                                                                                ║
║ 2. KEY FEATURES:                                                               ║
║    ├─ Reentrancy: Same thread can lock it multiple times                       ║
║    ├─ Fair Locking: Option to use fair/unfair acquisition policy               ║
║    ├─ Interruptible: Can interrupt threads waiting for lock                    ║
║    ├─ Try-Lock: Non-blocking attempt to acquire lock                           ║
║    └─ Condition Variables: Support for wait/notify patterns                    ║
║                                                                                ║
║ 3. BENEFITS OVER SYNCHRONIZED:                                                 ║
║    ├─ More flexible (can tryLock, lock with timeout)                           ║
║    ├─ Fair lock option (prevents thread starvation)                            ║
║    ├─ Better for complex concurrent scenarios                                  ║
║    ├─ Can use Condition objects for advanced synchronization                   ║
║    └─ Better debugging and monitoring                                          ║
║                                                                                ║
║ 4. REENTRANCY CONCEPT:                                                         ║
║    └─ A thread can acquire the same lock multiple times                        ║
║       Example: Thread holds lock in method A, calls method B                   ║
║       (which also needs the same lock). Instead of deadlocking,                ║
║       the lock allows re-entry and maintains a count.                          ║
║                                                                                ║
║ 5. IMPORTANT RULES:                                                            ║
║    ├─ Always call unlock() same number of times as lock()                      ║
║    ├─ Use try-finally to ensure unlock is called                               ║
║    └─ Each lock acquisition increments an internal counter                     ║
║                                                                                ║
╚════════════════════════════════════════════════════════════════════════════════╝
*/

public class ReentrantExample {

    /**
    ─────────────────────────────────────────────────────────────────
    BASIC EXAMPLE: Demonstrating Reentrancy
    ─────────────────────────────────────────────────────────────────
    This example shows:
    - How same thread can acquire lock multiple times
    - Each lock() call increments internal counter
    - Each unlock() call decrements the counter
    - Lock is only released when counter reaches 0
    
    If we used synchronized keyword instead, this would cause DEADLOCK!
    ─────────────────────────────────────────────────────────────────
    */
    private final Lock lock = new ReentrantLock();
    // DeadLock prevention...

    public void OuterMethod(){
        // FIRST LOCK ACQUISITION (counter = 1)
        lock.lock();
        try{
            System.out.println("Outer method is executing...");
            System.out.println("Thread: " + Thread.currentThread().getName());
            // Calling method that also needs the same lock
            InnerMethod();
        }finally {
            // FIRST UNLOCK (counter = 1, decrements to 0)
            lock.unlock();
        }
    }

    public void InnerMethod(){
        // SECOND LOCK ACQUISITION (counter = 2)
        // This works because it's the SAME THREAD that already holds the lock
        lock.lock();
        try{
            System.out.println("Inner method is executing...");
            System.out.println("Thread: " + Thread.currentThread().getName());
        }finally{
            // SECOND UNLOCK (counter = 2, decrements to 1)
            lock.unlock();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // INTERMEDIATE LEVEL: Using tryLock() - Non-blocking approach
    // ═══════════════════════════════════════════════════════════════════════
    
    /**
    tryLock() advantages:
    - Returns true if lock was acquired, false otherwise
    - Doesn't wait/block if lock is not available
    - Useful to avoid deadlock in complex scenarios
    - Can use with timeout: tryLock(long time, TimeUnit unit)
    */
    public void demonstrateTryLock() {
        System.out.println("\n--- Demonstrating tryLock() ---");
        
        // Attempt to acquire lock without waiting
        if (lock.tryLock()) {
            try {
                System.out.println("Lock acquired successfully!");
                System.out.println("Performing critical section...");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not acquire lock, another thread is using it");
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // INTERMEDIATE LEVEL: Checking lock state
    // ═══════════════════════════════════════════════════════════════════════
    
    /**
    getHoldCount(): Returns how many times current thread holds the lock
    isHeldByCurrentThread(): Checks if current thread holds this lock
    getQueueLength(): Number of threads waiting for this lock
    */
    public void demonstrateLockStatistics() {
        System.out.println("\n--- Demonstrating Lock Statistics ---");
        ReentrantLock statLock = new ReentrantLock();
        
        statLock.lock();
        try {
            System.out.println("Hold count: " + statLock.getHoldCount()); // Output: 1
            System.out.println("Is held by current thread: " + statLock.isHeldByCurrentThread()); // true
            
            // Acquire lock again (reentrancy)
            statLock.lock();
            try {
                System.out.println("Hold count after 2nd lock: " + statLock.getHoldCount()); // 2
            } finally {
                statLock.unlock();
            }
        } finally {
            statLock.unlock();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // ADVANCED LEVEL: Fair vs Unfair Locks
    // ═══════════════════════════════════════════════════════════════════════
    
    /**
    Fair Lock:
    - Constructor: new ReentrantLock(true)
    - Threads acquire lock in the order they requested it (FIFO)
    - Prevents thread starvation but slightly slower
    
    Unfair Lock (default):
    - Constructor: new ReentrantLock(false) or new ReentrantLock()
    - Threads can acquire lock in any order
    - Better performance but possible thread starvation
    */
    private static class FairLockExample {
        private final Lock fairLock = new ReentrantLock(true);
        private int counter = 0;
        
        public void incrementFairly() {
            fairLock.lock();
            try {
                counter++;
                System.out.println("Thread " + Thread.currentThread().getName() + 
                                 " incremented counter to: " + counter);
            } finally {
                fairLock.unlock();
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // ADVANCED LEVEL: Efficient Sequential Operations
    // ═══════════════════════════════════════════════════════════════════════
    
    /**
    BEST PRACTICES for efficient ReentrantLock usage:
    
    1. Keep critical section SMALL and SHORT
       - Release lock as soon as possible
       - Don't do heavy computations while holding lock
    
    2. Use try-finally ALWAYS
       - Ensures lock is released even if exception occurs
       - Never forget to unlock!
    
    3. Prefer tryLock() when avoiding deadlock is critical
       - Can specify timeout
       - Example: if (lock.tryLock(1, TimeUnit.SECONDS))
    
    4. Use Condition variables for producer-consumer patterns
       - lock.newCondition() creates condition object
       - condition.await() - thread waits
       - condition.signalAll() - wake waiting threads
    
    5. Monitor lock contention
       - Use getQueueLength() to check waiting threads
       - High contention = might need different approach
    */
    
    private static class SequentialOperationExample {
        private final ReentrantLock lock = new ReentrantLock();
        private int balance = 1000;
        
        // EFFICIENT: Minimal critical section
        public int withdrawCash(int amount) {
            lock.lock();
            try {
                // Only the absolute necessary operations in critical section
                if (balance >= amount) {
                    balance -= amount;
                    return amount;
                }
                return 0;
            } finally {
                lock.unlock();
            }
        }
        
        // INEFFICIENT: Large critical section (DON'T DO THIS!)
        public void inefficientOperation() {
            lock.lock();
            try {
                // Bad: Heavy computation while holding lock
                System.out.println("Starting heavy computation...");
                Thread.sleep(5000); // Holding lock for 5 seconds!
                balance += 100;
                System.out.println("Heavy computation done");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
        
        // EFFICIENT: Do heavy work outside lock, light work inside
        public void efficientOperation() {
            // Do heavy computation OUTSIDE the lock
            System.out.println("Starting heavy computation...");
            int result = expensiveCalculation();
            
            // Only do quick update inside lock
            lock.lock();
            try {
                balance += result;
                System.out.println("Balance updated to: " + balance);
            } finally {
                lock.unlock();
            }
        }
        
        private int expensiveCalculation() {
            // Simulate heavy computation
            int total = 0;
            for (int i = 0; i < 1000000; i++) {
                total += i;
            }
            return total;
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // ADVANCED LEVEL: Multi-threaded Demo with proper synchronization
    // ═══════════════════════════════════════════════════════════════════════
    
    private static class BankAccount {
        private final ReentrantLock lock = new ReentrantLock();
        private double balance;
        
        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }
        
        // SAFE: Multiple threads can call this safely
        public void deposit(double amount) {
            lock.lock();
            try {
                double previousBalance = balance;
                balance += amount;
                System.out.printf("Thread %s: Deposited $%.2f. Balance: $%.2f -> $%.2f%n",
                    Thread.currentThread().getName(), amount, previousBalance, balance);
            } finally {
                lock.unlock();
            }
        }
        
        public void withdraw(double amount) {
            lock.lock();
            try {
                if (balance >= amount) {
                    double previousBalance = balance;
                    balance -= amount;
                    System.out.printf("Thread %s: Withdrew $%.2f. Balance: $%.2f -> $%.2f%n",
                        Thread.currentThread().getName(), amount, previousBalance, balance);
                } else {
                    System.out.printf("Thread %s: Insufficient funds. Current balance: $%.2f%n",
                        Thread.currentThread().getName(), balance);
                }
            } finally {
                lock.unlock();
            }
        }
        
        public double getBalance() {
            lock.lock();
            try {
                return balance;
            } finally {
                lock.unlock();
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // DEMONSTRATION: Comparing Reentrant vs Non-Reentrant behavior
    // ═══════════════════════════════════════════════════════════════════════
    
    /**
    If we tried to use a regular lock (non-reentrant):
    
    Lock regularLock = new Object(); // Not reentrant
    regularLock.lock();           // First acquisition
    regularLock.lock();           // Tries to acquire again
    
    Result: DEADLOCK! The same thread waits for itself forever.
    
    With ReentrantLock:
    lock.lock();                  // Counter = 1
    lock.lock();                  // Counter = 2 (same thread allowed)
    lock.unlock();                // Counter = 1
    lock.unlock();                // Counter = 0 (lock released)
    */

    static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   REENTRANT LOCK COMPREHENSIVE DEMONSTRATION       ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        // ─────────────────────────────────────────────────────────────────
        // 1. BASIC REENTRANCY EXAMPLE (Original Code)
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n1. BASIC REENTRANCY EXAMPLE:");
        System.out.println("─".repeat(50));
        ReentrantExample example = new ReentrantExample();
        example.OuterMethod();
        
        // ─────────────────────────────────────────────────────────────────
        // 2. TRY LOCK EXAMPLE
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n2. TRY LOCK EXAMPLE:");
        System.out.println("─".repeat(50));
        example.demonstrateTryLock();
        
        // ─────────────────────────────────────────────────────────────────
        // 3. LOCK STATISTICS
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n3. LOCK STATISTICS:");
        System.out.println("─".repeat(50));
        example.demonstrateLockStatistics();
        
        // ─────────────────────────────────────────────────────────────────
        // 4. EFFICIENT SEQUENTIAL OPERATIONS
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n4. SEQUENTIAL OPERATIONS WITH BEST PRACTICES:");
        System.out.println("─".repeat(50));
        SequentialOperationExample seqExample = new SequentialOperationExample();
        System.out.println("Withdrawing $200: " + seqExample.withdrawCash(200));
        System.out.println("Current balance: " + seqExample.balance);
        System.out.println("Withdrawing $500: " + seqExample.withdrawCash(500));
        System.out.println("Efficient operation:");
        seqExample.efficientOperation();
        
        // ─────────────────────────────────────────────────────────────────
        // 5. MULTI-THREADED BANK ACCOUNT EXAMPLE
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n5. MULTI-THREADED BANK ACCOUNT:");
        System.out.println("─".repeat(50));
        BankAccount account = new BankAccount(1000.0);
        
        // Create multiple threads that access the same account
        Thread depositor1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(100);
                try { Thread.sleep(50); } catch (InterruptedException e) {}
            }
        }, "Depositor-1");
        
        Thread depositor2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(150);
                try { Thread.sleep(50); } catch (InterruptedException e) {}
            }
        }, "Depositor-2");
        
        Thread withdrawer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.withdraw(200);
                try { Thread.sleep(50); } catch (InterruptedException e) {}
            }
        }, "Withdrawer");
        
        depositor1.start();
        depositor2.start();
        withdrawer.start();
        
        try {
            depositor1.join();
            depositor2.join();
            withdrawer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nFinal Balance: $" + account.getBalance());
        
        // ─────────────────────────────────────────────────────────────────
        // SUMMARY
        // ─────────────────────────────────────────────────────────────────
        System.out.println("\n" + "═".repeat(50));
        System.out.println("KEY TAKEAWAYS:");
        System.out.println("═".repeat(50));
        System.out.println("✓ ReentrantLock allows same thread to acquire lock multiple times");
        System.out.println("✓ Always use try-finally to ensure lock is released");
        System.out.println("✓ Keep critical sections small and efficient");
        System.out.println("✓ Use tryLock() to avoid potential deadlocks");
        System.out.println("✓ Consider fair locks when preventing starvation is important");
        System.out.println("✓ Monitor lock contention for performance optimization");
        System.out.println("═".repeat(50));
    }
}
