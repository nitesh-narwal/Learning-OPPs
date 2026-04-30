package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.Locking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                 🎓 ALL ABOUT LOCK FAIRNESS IN JAVA 🎓                      ║
 * ║              Complete Guide: Beginner → Intermediate → Advanced            ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * 📚 CORE CONCEPT - What is Lock Fairness?
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * FAIRNESS means: Threads acquire locks in the order they requested them (FIFO).
 *
 * Think of it like a bank queue:
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │                                                                     │
 * │ WITHOUT FAIRNESS (Unfair Lock):                                     │
 * │ ────────────────────────────────                                    │
 * │ Queue: [Thread1, Thread2, Thread3, Thread4]  ← Waiting threads      │
 * │ Lock holder: Thread3 is using the resource                          │
 * │ When Thread3 releases:                                              │
 * │   ❌ Thread4 might grab it (random/arbitrary)                        │
 * │   ❌ Thread2 might grab it (unpredictable)                           │
 * │   ❌ Thread1 is stuck waiting (STARVATION!)                          │
 * │ Result: UNFAIR but FAST                                             │
 * │                                                                     │
 * │ WITH FAIRNESS (Fair Lock):                                          │
 * │ ──────────────────────────                                          │
 * │ Queue: [Thread1, Thread2, Thread3, Thread4]  ← Ordered queue        │
 * │ Lock holder: Thread3 is using the resource                          │
 * │ When Thread3 releases:                                              │
 * │   ✅ Thread1 MUST get it (FIFO = First In First Out)                │
 * │   ✅ Then Thread2, then next waiting thread                         │
 * │   ✅ Everyone eventually gets their turn                            │
 * │ Result: FAIR but SLOWER                                            │
 * │                                                                    │
 * └────────────────────────────────────────────────────────────────────┘
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * 🔧 HOW TO USE FAIRNESS IN CODE
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Creating locks:
 *   Unfair:  Lock lock = new ReentrantLock();         // Default (false)
 *   Fair:    Lock lock = new ReentrantLock(true);     // Enable fairness
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * 📊 fQUICK COMPARISON TABLE
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Feature              │ Unfair Lock      │ Fair Lock
 * ─────────────────────┼──────────────────┼──────────────────────────
 * Thread Order         │ Random (No FIFO) │ FIFO (Ordered)
 * Performance          │ ⚡ FASTER         │ 🐢 SLOWER
 * Throughput           │ ⬆️ HIGHER        │ ⬇️ LOWER
 * Latency              │ ⚡ LOW (some)     │ 🕐 HIGH (fair wait)
 * Starvation Risk      │ ⚠️ YES (possible)│ ✅ NO (prevented)
 * Context Switches     │ ⬇️ FEWER         │ ⬆️ MORE
 * CPU Cache            │ ✅ Better reuse   │ ❌ Worse (switching)
 * Default              │ ✅ Used always    │ ⚠️ Only when needed
 * Real-time Safe       │ ❌ Unpredictable  │ ✅ Predictable
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * 💡 KEY INSIGHT - Why is Fair Lock Slower?
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Unfair Lock Speed (Lock Acquisition):
 *   1. Thread releases lock
 *   2. ANY waiting thread grabs it (immediate, no queue check)
 *   3. Result: Very fast
 *
 * Fair Lock Speed (Lock Acquisition):
 *   1. Thread releases lock
 *   2. Lock checks queue → finds first waiting thread
 *   3. Gives lock only to that specific thread
 *   4. Other threads MUST wait for their turn
 *   5. Result: Slower (queue management overhead)
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * ⚠️ CONFUSING PART #1: What is "Starvation"?
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Starvation = A thread keeps getting denied the resource (forever or long time)
 *
 * Example:
 *   Thread A: Wants lock repeatedly and always gets it
 *   Thread B: Wants lock but NEVER gets it (other threads keep cutting ahead)
 *   Thread C: Same as B
 *
 * Result: Threads B and C are STARVED (no chance to run)
 *
 * Causes:
 *   • Unfair locks (aggressive threads dominate)
 *   • Different thread priorities
 *   • Thread always ready vs sometimes blocked
 *   • Locks not released properly
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * ⚠️ CONFUSING PART #2: When Does Fairness Actually Help?
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Fairness helps when:
 *   ✅ MANY threads competing for same lock (high contention)
 *   ✅ Some threads are "aggressive" (always trying to lock)
 *   ✅ Some threads might get starved
 *   ✅ Fair turns matter (e.g., payments, transactions)
 *
 * Fairness doesn't help when:
 *   ❌ Few threads (low contention)
 *   ❌ Threads naturally take turns (rare lock attempts)
 *   ❌ Performance is critical and fairness isn't required
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * ⚠️ CONFUSING PART #3: Fair Lock Increases Average Latency
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * This seems backwards, right? How can "fair" hurt latency?
 *
 * Unfair lock:
 *   Thread arrives and tries to grab lock immediately
 *   If it's free → INSTANT (no wait)
 *   Average latency: LOW (but some threads wait forever)
 *
 * Fair lock:
 *   Thread arrives and must wait for its turn in queue
 *   Must wait for EACH thread ahead of it
 *   Average latency: HIGHER (but ALL threads eventually get turn)
 *
 * Trade-off:
 *   Unfair: Some threads have LOW latency, others have INFINITE latency
 *   Fair: All threads have MEDIUM latency (balanced)
 *
 */

public class AllAboutFairness {

    // ════════════════════════════════════════════════════════════════════════════
    // 🟢 BEGINNER LEVEL - SIMPLE CONCEPTS & BASIC EXAMPLES
    // ════════════════════════════════════════════════════════════════════════════

    /**
     * BEGINNER EXAMPLE 1: Understanding Fairness with Simple Counter
     *
     * This shows the BASIC DIFFERENCE between fair and unfair locks.
     * We'll use many threads incrementing a counter and observe the order.
     *
     * Expected Result:
     *   • Unfair: Random thread order (unpredictable)
     *   • Fair: More orderly (though not guaranteed perfect FIFO)
     */
    static class BeginnerSimpleCounter {
        private Lock unfairLock = new ReentrantLock(false);  // false = unfair
        private Lock fairLock = new ReentrantLock(true);     // true = fair
        private int counter = 0;

        // Using unfair lock
        public void incrementUnfair() {
            unfairLock.lock();
            try {
                counter++;
                System.out.println(Thread.currentThread().getName() + " → " + counter);
            } finally {
                unfairLock.unlock();
            }
        }

        // Using fair lock
        public void incrementFair() {
            fairLock.lock();
            try {
                counter++;
                System.out.println(Thread.currentThread().getName() + " → " + counter);
            } finally {
                fairLock.unlock();
            }
        }

        public void runBeginnerDemo() {
            System.out.println("\n╔─── BEGINNER EXAMPLE 1: Simple Counter ───╗");

            System.out.println("\n🔴 Testing UNFAIR Lock:");
            System.out.println("Expected: Threads acquire lock in random order\n");
            counter = 0;

            Thread[] unfairThreads = new Thread[5];
            for (int i = 0; i < 5; i++) {
                int id = i;
                unfairThreads[i] = new Thread(() -> {
                    for (int j = 0; j < 3; j++) {
                        incrementUnfair();
                    }
                }, "U-Thread" + id);
            }
            for (Thread t : unfairThreads) t.start();
            for (Thread t : unfairThreads) {
                try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            System.out.println("\n🟢 Testing FAIR Lock:");
            System.out.println("Expected: More orderly thread sequence\n");
            counter = 0;

            Thread[] fairThreads = new Thread[5];
            for (int i = 0; i < 5; i++) {
                int id = i;
                fairThreads[i] = new Thread(() -> {
                    for (int j = 0; j < 3; j++) {
                        incrementFair();
                    }
                }, "F-Thread" + id);
            }
            for (Thread t : fairThreads) t.start();
            for (Thread t : fairThreads) {
                try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            System.out.println("\n💡 Observation: Notice the thread order difference!");
            System.out.println("╚──────────────────────────────────────────╝\n");
        }
    }

    /**
     * BEGINNER EXAMPLE 2: Fairness vs Performance
     *
     * Simple speed comparison between fair and unfair locks.
     * This is easy to understand: fair locks are slower.
     */
    static class BeginnerSpeedComparison {
        public void compareSpeed() {
            System.out.println("\n╔─── BEGINNER EXAMPLE 2: Speed Comparison ───╗");

            Lock unfairLock = new ReentrantLock(false);
            Lock fairLock = new ReentrantLock(true);

            int iterations = 10000;

            // Test unfair lock
            System.out.println("\n⏱️  Testing UNFAIR lock with " + iterations + " iterations...");
            long startUnfair = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                unfairLock.lock();
                unfairLock.unlock();
            }
            long durationUnfair = System.nanoTime() - startUnfair;

            // Test fair lock
            System.out.println("⏱️  Testing FAIR lock with " + iterations + " iterations...");
            long startFair = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                fairLock.lock();
                fairLock.unlock();
            }
            long durationFair = System.nanoTime() - startFair;

            System.out.println("\n📊 Results:");
            System.out.println("Unfair time: " + (durationUnfair / 1_000_000) + "ms");
            System.out.println("Fair time:   " + (durationFair / 1_000_000) + "ms");
            System.out.println("Fair is " + String.format("%.2f", (double)durationFair/durationUnfair) + "x slower");

            System.out.println("\n💡 Key Point: Fair lock has overhead for queue management");
            System.out.println("╚──────────────────────────────────────────────╝\n");
        }
    }

    // ════════════════════════════════════════════════════════════════════════════
    // 🟡 INTERMEDIATE LEVEL - DEEPER UNDERSTANDING & MONITORING
    // ════════════════════════════════════════════════════════════════════════════

    /**
     * INTERMEDIATE EXAMPLE 1: Monitoring Access Distribution
     *
     * Shows HOW FAIR or UNFAIR the lock actually is.
     * We count how many times each thread gets the lock.
     *
     * Unfair Expected: Uneven distribution (some threads get more)
     * Fair Expected: Even distribution (all threads get similar turns)
     */
    static class IntermediateMonitoring {
        private Lock lock;
        private String lockType;
        private Map<String, Integer> threadAccessCount = new HashMap<>();
        private int totalAccess = 0;

        public IntermediateMonitoring(boolean fair, String type) {
            this.lock = new ReentrantLock(fair);
            this.lockType = type;
        }

        public void accessResource() {
            lock.lock();
            try {
                totalAccess++;
                String threadName = Thread.currentThread().getName();
                threadAccessCount.put(threadName, threadAccessCount.getOrDefault(threadName, 0) + 1);
            } finally {
                lock.unlock();
            }
        }

        public void printStatistics() {
            System.out.println("\n📊 Statistics for " + lockType + ":");
            System.out.println("Total accesses: " + totalAccess);

            int maxAccess = threadAccessCount.values().stream().max(Integer::compare).orElse(0);
            int minAccess = threadAccessCount.values().stream().min(Integer::compare).orElse(0);

            System.out.println("Max access: " + maxAccess + ", Min access: " + minAccess);
            System.out.println("Fairness ratio (Min/Max): " + 
                String.format("%.1f%%", (minAccess * 100.0) / maxAccess));

            System.out.println("Thread breakdown:");
            threadAccessCount.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));
        }

        public void runMonitoringDemo() {
            System.out.println("\n╔─── INTERMEDIATE EXAMPLE 1: Access Monitoring ───╗");

            // Test with unfair lock
            System.out.println("\n🔴 UNFAIR Lock (10 threads, 100 accesses each):");
            IntermediateMonitoring unfairDemo = new IntermediateMonitoring(false, "UNFAIR");

            Thread[] unfairThreads = new Thread[10];
            for (int i = 0; i < 10; i++) {
                int id = i;
                unfairThreads[i] = new Thread(() -> {
                    for (int j = 0; j < 100; j++) {
                        unfairDemo.accessResource();
                        try { Thread.sleep(1); } catch (InterruptedException e) {}
                    }
                }, "Unfair-" + id);
            }
            for (Thread t : unfairThreads) t.start();
            for (Thread t : unfairThreads) {
                try { t.join(); } catch (InterruptedException e) {}
            }
            unfairDemo.printStatistics();

            // Test with fair lock
            System.out.println("\n🟢 FAIR Lock (10 threads, 100 accesses each):");
            IntermediateMonitoring fairDemo = new IntermediateMonitoring(true, "FAIR");

            Thread[] fairThreads = new Thread[10];
            for (int i = 0; i < 10; i++) {
                int id = i;
                fairThreads[i] = new Thread(() -> {
                    for (int j = 0; j < 100; j++) {
                        fairDemo.accessResource();
                        try { Thread.sleep(1); } catch (InterruptedException e) {}
                    }
                }, "Fair-" + id);
            }
            for (Thread t : fairThreads) t.start();
            for (Thread t : fairThreads) {
                try { t.join(); } catch (InterruptedException e) {}
            }
            fairDemo.printStatistics();

            System.out.println("\n💡 Analysis:");
            System.out.println("Unfair: Low fairness ratio (min/max close to 0) = Some starved");
            System.out.println("Fair:   High fairness ratio (min/max close to 100%) = All treated equally");
            System.out.println("╚─────────────────────────────────────────────────────╝\n");
        }
    }

    /**
     * INTERMEDIATE EXAMPLE 2: Demonstrating Actual Starvation
     *
     * This shows REAL STARVATION with aggressive threads.
     * One thread constantly grabs lock while another waits.
     *
     * CONFUSING PART EXPLAINED:
     * You might think "Why would anyone design unfair locks?"
     * Answer: They're 2-3x faster when contention isn't causing starvation!
     */
    static class IntermediateStarvation {
        private Lock lock;
        private String lockType;
        private AtomicLong normalThreadCount = new AtomicLong(0);
        private AtomicLong aggressiveThreadCount = new AtomicLong(0);

        public IntermediateStarvation(boolean fair, String type) {
            this.lock = new ReentrantLock(fair);
            this.lockType = type;
        }

        public void normalThreadWork() {
            lock.lock();
            try { normalThreadCount.incrementAndGet(); } 
            finally { lock.unlock(); }
        }

        public void aggressiveThreadWork() {
            lock.lock();
            try { aggressiveThreadCount.incrementAndGet(); } 
            finally { lock.unlock(); }
        }

        public void runStarvationDemo() {
            System.out.println("\n╔─── INTERMEDIATE EXAMPLE 2: Starvation Demo ───╗");
            System.out.println("\nScenario: 1 Aggressive Thread vs 1 Normal Thread");
            System.out.println("Aggressive: Continuously tries to grab lock");
            System.out.println("Normal: Takes turns peacefully\n");

            // Test unfair
            System.out.println("🔴 UNFAIR Lock (3 seconds):");
            IntermediateStarvation unfairDemo = new IntermediateStarvation(false, "UNFAIR");

            final long endTimeUnfair = System.currentTimeMillis() + 3000;

            Thread normalThread = new Thread(() -> {
                while (System.currentTimeMillis() < endTimeUnfair) {
                    unfairDemo.normalThreadWork();
                }
            }, "Normal");

            Thread aggressiveThread = new Thread(() -> {
                while (System.currentTimeMillis() < endTimeUnfair) {
                    unfairDemo.aggressiveThreadWork();
                }
            }, "Aggressive");

            normalThread.start();
            aggressiveThread.start();
            try {
                normalThread.join();
                aggressiveThread.join();
            } catch (InterruptedException e) {}

            long totalUnfair = unfairDemo.normalThreadCount.get() + unfairDemo.aggressiveThreadCount.get();
            System.out.println("Normal thread: " + unfairDemo.normalThreadCount.get() + 
                " (" + String.format("%.1f%%", (unfairDemo.normalThreadCount.get() * 100.0) / totalUnfair) + ")");
            System.out.println("Aggressive thread: " + unfairDemo.aggressiveThreadCount.get() + 
                " (" + String.format("%.1f%%", (unfairDemo.aggressiveThreadCount.get() * 100.0) / totalUnfair) + ")");
            System.out.println("⚠️  STARVATION: Normal thread starved by aggressive one!");

            // Test fair
            System.out.println("\n🟢 FAIR Lock (3 seconds):");
            IntermediateStarvation fairDemo = new IntermediateStarvation(true, "FAIR");

            final long endTimeFair = System.currentTimeMillis() + 3000;

            normalThread = new Thread(() -> {
                while (System.currentTimeMillis() < endTimeFair) {
                    fairDemo.normalThreadWork();
                }
            }, "Normal");

            aggressiveThread = new Thread(() -> {
                while (System.currentTimeMillis() < endTimeFair) {
                    fairDemo.aggressiveThreadWork();
                }
            }, "Aggressive");

            normalThread.start();
            aggressiveThread.start();
            try {
                normalThread.join();
                aggressiveThread.join();
            } catch (InterruptedException e) {}

            long totalFair = fairDemo.normalThreadCount.get() + fairDemo.aggressiveThreadCount.get();
            System.out.println("Normal thread: " + fairDemo.normalThreadCount.get() + 
                " (" + String.format("%.1f%%", (fairDemo.normalThreadCount.get() * 100.0) / totalFair) + ")");
            System.out.println("Aggressive thread: " + fairDemo.aggressiveThreadCount.get() + 
                " (" + String.format("%.1f%%", (fairDemo.aggressiveThreadCount.get() * 100.0) / totalFair) + ")");
            System.out.println("✅ FAIR: Both threads got ~50% (no starvation)!");

            System.out.println("\n💡 Key Lesson: Fair locks prevent starvation by enforcing order");
            System.out.println("╚──────────────────────────────────────────────────╝\n");
        }
    }

    // ════════════════════════════════════════════════════════════════════════════
    // 🔴 ADVANCED LEVEL - REAL-WORLD SCENARIOS & BEST PRACTICES
    // ════════════════════════════════════════════════════════════════════════════

    /**
     * ADVANCED EXAMPLE 1: Banking System (Real-World)
     *
     * Why fairness matters in real systems:
     *   • Financial transactions must be fair
     *   • Audit trails need consistent ordering
     *   • Customers expect fair treatment
     *   • System integrity is more important than speed
     */
    static class AdvancedBankAccount {
        private double balance;
        private Lock lock;
        private String bankType;
        private List<String> transactions = new ArrayList<>();

        public AdvancedBankAccount(double initial, boolean fair, String type) {
            this.balance = initial;
            this.lock = new ReentrantLock(fair);
            this.bankType = type;
        }

        public void withdraw(double amount) {
            lock.lock();
            try {
                if (balance >= amount) {
                    balance -= amount;
                    transactions.add(Thread.currentThread().getName() + " withdrew " + amount);
                    System.out.println(bankType + " - " + Thread.currentThread().getName() + 
                        " withdrew $" + amount);
                } else {
                    System.out.println(bankType + " - " + Thread.currentThread().getName() + 
                        " INSUFFICIENT FUNDS");
                }
            } finally {
                lock.unlock();
            }
        }

        public void printAuditTrail() {
            lock.lock();
            try {
                System.out.println("\n📋 Audit Trail (" + bankType + ") - Final Balance: $" + balance);
                for (int i = 0; i < transactions.size(); i++) {
                    System.out.println((i + 1) + ". " + transactions.get(i));
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class AdvancedBankingDemo {
        public void runBankingDemo() {
            System.out.println("\n╔─── ADVANCED EXAMPLE 1: Banking System ───╗");

            AdvancedBankAccount unfairBank = new AdvancedBankAccount(1000, false, "UNFAIR Bank");
            AdvancedBankAccount fairBank = new AdvancedBankAccount(1000, true, "FAIR Bank");

            System.out.println("\n🔴 UNFAIR Bank (5 customers, 3 withdrawals each):");
            Thread[] unfairCustomers = new Thread[5];
            for (int i = 0; i < 5; i++) {
                int id = i;
                unfairCustomers[i] = new Thread(() -> {
                    for (int j = 0; j < 3; j++) {
                        unfairBank.withdraw(50);
                    }
                }, "Customer" + id);
            }
            for (Thread t : unfairCustomers) t.start();
            for (Thread t : unfairCustomers) {
                try { t.join(); } catch (InterruptedException e) {}
            }
            unfairBank.printAuditTrail();

            System.out.println("\n🟢 FAIR Bank (5 customers, 3 withdrawals each):");
            Thread[] fairCustomers = new Thread[5];
            for (int i = 0; i < 5; i++) {
                int id = i;
                fairCustomers[i] = new Thread(() -> {
                    for (int j = 0; j < 3; j++) {
                        fairBank.withdraw(50);
                    }
                }, "Customer" + id);
            }
            for (Thread t : fairCustomers) t.start();
            for (Thread t : fairCustomers) {
                try { t.join(); } catch (InterruptedException e) {}
            }
            fairBank.printAuditTrail();

            System.out.println("\n💡 Real-World Insight:");
            System.out.println("✅ Fair lock ensures all customers treated equally");
            System.out.println("✅ Audit trail is consistent and predictable");
            System.out.println("✅ Worth the performance cost for financial integrity");
            System.out.println("╚────────────────────────────────────────────╝\n");
        }
    }

    /**
     * ADVANCED EXAMPLE 2: Decision Matrix
     *
     * Clear guidance on WHEN to use fair vs unfair locks.
     * This is the most practical information for developers.
     */
    static class AdvancedDecisionMatrix {
        public void showDecisionMatrix() {
            System.out.println("\n╔─── ADVANCED EXAMPLE 2: Decision Matrix ───╗");

            System.out.println("\n🔴 USE UNFAIR LOCK (new ReentrantLock()) when:");
            System.out.println("   ✓ Maximum performance/throughput is critical");
            System.out.println("   ✓ Web servers and REST APIs");
            System.out.println("   ✓ Database connection pools");
            System.out.println("   ✓ Few competing threads (low contention)");
            System.out.println("   ✓ Starvation won't harm business");
            System.out.println("   ✓ Cache-friendly (same thread re-acquires)");
            System.out.println("   Example: public static int counter = 0;");

            System.out.println("\n🟢 USE FAIR LOCK (new ReentrantLock(true)) when:");
            System.out.println("   ✓ Fairness is business requirement");
            System.out.println("   ✓ Many threads competing (high contention)");
            System.out.println("   ✓ SLA (Service Level Agreements) with fairness");
            System.out.println("   ✓ Financial transactions");
            System.out.println("   ✓ Real-time systems with deadline guarantees");
            System.out.println("   ✓ Safety-critical operations");
            System.out.println("   ✓ Gaming/lottery systems (fairness important)");
            System.out.println("   Example: bank.withdraw(100);");

            System.out.println("\n⚠️  CONFUSING - Why do defaults use UNFAIR?");
            System.out.println("   Because: 99% of cases don't need fairness");
            System.out.println("           Speed matters more than perfect fairness");
            System.out.println("           Starvation rarely happens in practice");

            System.out.println("\n╚───────────────────────────────────────────╝\n");
        }
    }

    /**
     * ADVANCED EXAMPLE 3: Tricks and Optimization Techniques
     *
     * Advanced methods to handle fairness challenges in production.
     */
    static class AdvancedTricks {
        public void showTricks() {
            System.out.println("\n╔─── ADVANCED EXAMPLE 3: Tricks & Optimization ───╗");

            System.out.println("\n🎯 TRICK 1: Hybrid Approach - tryLock + Fair Lock");
            System.out.println("─────────────────────────────────────────────────");
            System.out.println("Problem: Fair locks are slow");
            System.out.println("Solution: Use tryLock() for fast paths");
            System.out.println("");
            System.out.println("if (fairLock.tryLock()) {");
            System.out.println("    try { /* do quick work */ }");
            System.out.println("    finally { fairLock.unlock(); }");
            System.out.println("} else {");
            System.out.println("    // Fall back or skip (don't block)");
            System.out.println("}");
            System.out.println("");
            System.out.println("Benefit: Get fairness guarantee with partial speed");

            System.out.println("\n🎯 TRICK 2: ReentrantReadWriteLock");
            System.out.println("─────────────────────────────────────────────────");
            System.out.println("Problem: Fair lock overhead for read-heavy workload");
            System.out.println("Solution: Multiple readers, single writer");
            System.out.println("");
            System.out.println("ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);");
            System.out.println("rwLock.readLock().lock();   // Many can read");
            System.out.println("rwLock.writeLock().lock();  // Only 1 can write");
            System.out.println("");
            System.out.println("Benefit: Parallel reads, fair write access");

            System.out.println("\n🎯 TRICK 3: Partition Data (Sharding)");
            System.out.println("─────────────────────────────────────────────────");
            System.out.println("Problem: Single fair lock is bottleneck");
            System.out.println("Solution: Multiple locks on partitioned data");
            System.out.println("");
            System.out.println("Lock[] locks = new Lock[16];  // 16 partitions");
            System.out.println("int partition = hash(key) % 16;");
            System.out.println("locks[partition].lock();");
            System.out.println("");
            System.out.println("Benefit: Reduces contention (ConcurrentHashMap uses this)");

            System.out.println("\n🎯 TRICK 4: Lock-Free with ConcurrentHashMap");
            System.out.println("─────────────────────────────────────────────────");
            System.out.println("Problem: Even fair locks have overhead");
            System.out.println("Solution: Use lock-free data structures");
            System.out.println("");
            System.out.println("ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();");
            System.out.println("map.put(key, value);  // Thread-safe, no locks");
            System.out.println("");
            System.out.println("Benefit: Best performance, no starvation possible");

            System.out.println("\n╚─────────────────────────────────────────────────╝\n");
        }
    }

    /**
     * ADVANCED EXAMPLE 4: Common Mistakes
     *
     * Mistakes developers make with fairness.
     */
    static class AdvancedMistakes {
        public void showMistakes() {
            System.out.println("\n╔─── ADVANCED EXAMPLE 4: Common Mistakes ───╗");

            System.out.println("\n❌ MISTAKE 1: Using Fair Lock Everywhere");
            System.out.println("─────────────────────────────────────────────");
            System.out.println("Wrong: new ReentrantLock(true) in all classes");
            System.out.println("Why wrong: Unnecessary 2-3x slowdown");
            System.out.println("✅ Correct: Measure first, then optimize");

            System.out.println("\n❌ MISTAKE 2: Holding Lock During I/O");
            System.out.println("─────────────────────────────────────────────");
            System.out.println("Wrong:");
            System.out.println("  lock.lock()");
            System.out.println("  data = network.fetch();  // 1000ms blocking");
            System.out.println("  lock.unlock()");
            System.out.println("");
            System.out.println("Why wrong: Blocks other threads for 1000ms");
            System.out.println("✅ Correct: Fetch first, lock later");
            System.out.println("  data = network.fetch();");
            System.out.println("  lock.lock()");
            System.out.println("  process(data);");
            System.out.println("  lock.unlock()");

            System.out.println("\n❌ MISTAKE 3: Fairness doesn't prevent Deadlock");
            System.out.println("─────────────────────────────────────────────");
            System.out.println("Thread1: lock(A) → wait for lock(B)");
            System.out.println("Thread2: lock(B) → wait for lock(A)");
            System.out.println("Result: DEADLOCK (fairness can't help)");
            System.out.println("✅ Solution: Always acquire locks in same order");

            System.out.println("\n❌ MISTAKE 4: Confusing Fair with Reentrancy");
            System.out.println("─────────────────────────────────────────────");
            System.out.println("Reentrancy: Can same thread acquire lock twice?");
            System.out.println("  new ReentrantLock() → Yes");
            System.out.println("Fairness: In what order do threads acquire lock?");
            System.out.println("  new ReentrantLock(true) → FIFO order");

            System.out.println("\n╚──────────────────────────────────────────────╝\n");
        }
    }

    // ════════════════════════════════════════════════════════════════════════════
    // SUMMARY & QUICK REFERENCE
    // ════════════════════════════════════════════════════════════════════════════

    /**
     * Final Summary - Important Points to Remember
     */
    static class Summary {
        public void printSummary() {
            System.out.println("\n" + "═".repeat(70));
            System.out.println("                    📝 FAIRNESS SUMMARY");
            System.out.println("═".repeat(70) + "\n");

            System.out.println("🎯 QUICK REFERENCE:");
            System.out.println("   Unfair:  new ReentrantLock()      → ⚡ Fast, unfair");
            System.out.println("   Fair:    new ReentrantLock(true)  → 🐢 Slow, fair\n");

            System.out.println("📊 DECISION TREE:");
            System.out.println("   Do you need fairness?");
            System.out.println("   ├─ YES → Use Fair Lock (new ReentrantLock(true))");
            System.out.println("   └─ NO  → Use Unfair Lock (new ReentrantLock())\n");

            System.out.println("⚡ PERFORMANCE RULE:");
            System.out.println("   Fair lock is 2-3x slower in high contention");
            System.out.println("   Fair lock has negligible overhead in low contention\n");

            System.out.println("✅ BEST PRACTICE:");
            System.out.println("   1. Start with UNFAIR (99% of cases)");
            System.out.println("   2. Switch to FAIR only if:");
            System.out.println("      • Starvation observed");
            System.out.println("      • Business requirement");
            System.out.println("      • Testing proves real problem\n");

            System.out.println("═".repeat(70) + "\n");
        }
    }

    // ════════════════════════════════════════════════════════════════════════════
    // MAIN - Running All Examples
    // ════════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("         COMPLETE LOCK FAIRNESS GUIDE (All Examples)");
        System.out.println("═".repeat(70));

        // Beginner
        System.out.println("\n━━━━━━━━━━━━━━━━━━ 🟢 BEGINNER LEVEL 🟢 ━━━━━━━━━━━━━━━━━━\n");
        new BeginnerSimpleCounter().runBeginnerDemo();
        new BeginnerSpeedComparison().compareSpeed();

        // Intermediate
        System.out.println("━━━━━━━━━━━━━━━━ 🟡 INTERMEDIATE LEVEL 🟡 ━━━━━━━━━━━━━━━\n");
        new IntermediateMonitoring(false, "DEMO").runMonitoringDemo();
        new IntermediateStarvation(false, "DEMO").runStarvationDemo();

        // Advanced
        System.out.println("━━━━━━━━━━━━━━━━ 🔴 ADVANCED LEVEL 🔴 ━━━━━━━━━━━━━━━━\n");
        new AdvancedBankingDemo().runBankingDemo();
        new AdvancedDecisionMatrix().showDecisionMatrix();
        new AdvancedTricks().showTricks();
        new AdvancedMistakes().showMistakes();

        // Summary
        new Summary().printSummary();
    }
}
