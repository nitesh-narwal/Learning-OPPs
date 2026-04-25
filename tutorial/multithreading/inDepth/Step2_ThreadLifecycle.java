package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * STEP 2: THREAD LIFECYCLE
 * 
 * A thread goes through 5 states during its lifecycle:
 * 
 * 1. NEW: Thread object created but start() not called yet
 * 2. RUNNABLE: start() called, ready to run (JVM scheduler decides when to run)
 * 3. RUNNING: Thread is currently executing
 * 4. WAITING/BLOCKED: Thread is waiting (sleep, wait(), I/O, lock)
 * 5. TERMINATED: run() completed or stopped
 * 
 * Note: Java doesn't have a RUNNING state directly - it's part of RUNNABLE
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ThreadLifecycleMonitor {
    private String threadName;
    private boolean shouldRun = true;
    
    public ThreadLifecycleMonitor(String name) {
        this.threadName = name;
    }
    
    public void execute() {
        System.out.println(formatTime() + " - " + threadName + " started execution");
        
        // PHASE 1: Active running
        System.out.println(formatTime() + " - " + threadName + " is in RUNNING state");
        for (int i = 1; i <= 3; i++) {
            if (!shouldRun) break;
            System.out.println(formatTime() + " - " + threadName + " processing item: " + i);
            try {
                // PHASE 2: Transition to WAITING state
                System.out.println(formatTime() + " - " + threadName + " going to WAITING state (sleep)");
                Thread.sleep(1500);
                // Back to RUNNABLE after sleep
                System.out.println(formatTime() + " - " + threadName + " back to RUNNABLE (after sleep)");
            } catch (InterruptedException e) {
                System.out.println(formatTime() + " - " + threadName + " was INTERRUPTED!");
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // PHASE 3: Transition to TERMINATED
        System.out.println(formatTime() + " - " + threadName + " transitioning to TERMINATED state");
    }
    
    public void stop() {
        shouldRun = false;
    }
    
    private String formatTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        return "[" + formatter.format(LocalDateTime.now()) + "]";
    }
}

// ==================== THREAD PRIORITY ====================
class PriorityDemonstrator implements Runnable {
    private String name;
    private int priority;
    
    public PriorityDemonstrator(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    @Override
    public void run() {
        System.out.println(name + " (Priority: " + priority + ") is running");
        
        // Higher priority threads get more CPU time
        // But this is just a SUGGESTION to JVM, not guaranteed
        for (int i = 1; i <= 3; i++) {
            System.out.println(name + " (Priority: " + priority + ") - Work item: " + i);
        }
        
        System.out.println(name + " (Priority: " + priority + ") completed");
    }
}

// ==================== DAEMON THREADS ====================
/**
 * DAEMON THREADS:
 * - Background threads that don't prevent JVM from exiting
 * - JVM exits when all non-daemon threads finish
 * - Use cases: Garbage collection, background monitoring
 */
class DaemonThreadExample implements Runnable {
    private String name;
    
    public DaemonThreadExample(String name) {
        this.name = name;
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(name + " (Daemon) - Iteration: " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Step2_ThreadLifecycle {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n========== THREAD LIFECYCLE TUTORIAL ==========\n");
        
        // ========== LIFECYCLE DEMONSTRATION ==========
        System.out.println("--- THREAD LIFECYCLE STATES ---");
        
        ThreadLifecycleMonitor monitor = new ThreadLifecycleMonitor("LifecycleThread");
        Thread thread = new Thread(monitor::execute);
        
        // At this point: thread is in NEW state
        System.out.println("[BEFORE START] Thread state: " + thread.getState()); // NEW
        
        thread.start();
        // Now: thread is in RUNNABLE state
        System.out.println("[AFTER START] Thread state: " + thread.getState()); // RUNNABLE or RUNNING
        
        thread.join(); // Wait for completion
        // Now: thread is in TERMINATED state
        System.out.println("[AFTER JOIN] Thread state: " + thread.getState()); // TERMINATED
        
        System.out.println();
        
        // ========== THREAD PRIORITY ==========
        System.out.println("--- THREAD PRIORITY DEMONSTRATION ---");
        
        // Java threads have priority from 1 (MIN) to 10 (MAX), default is 5 (NORM)
        Thread highPriorityThread = new Thread(new PriorityDemonstrator("HighPriority", 10), "HighThread");
        Thread normalThread = new Thread(new PriorityDemonstrator("NormalPriority", 5), "NormThread");
        Thread lowPriorityThread = new Thread(new PriorityDemonstrator("LowPriority", 1), "LowThread");
        
        // Set thread priorities
        highPriorityThread.setPriority(Thread.MAX_PRIORITY);     // 10
        normalThread.setPriority(Thread.NORM_PRIORITY);         // 5
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);     // 1
        
        highPriorityThread.start();
        normalThread.start();
        lowPriorityThread.start();
        
        highPriorityThread.join();
        normalThread.join();
        lowPriorityThread.join();
        
        System.out.println();
        
        // ========== DAEMON THREADS ==========
        System.out.println("--- DAEMON THREAD DEMONSTRATION ---");
        System.out.println("Note: Daemon threads will be interrupted when main thread exits");
        
        Thread daemonThread = new Thread(new DaemonThreadExample("DaemonWorker"));
        daemonThread.setDaemon(true);  // Mark as daemon thread
        
        Thread normalWorker = new Thread(new DaemonThreadExample("NormalWorker"));
        // normalWorker.setDaemon(false); // Default is false (non-daemon)
        
        daemonThread.start();
        normalWorker.start();
        
        // Sleep to let them run a bit
        Thread.sleep(2000);
        
        // At this point, daemon might be interrupted but normal thread continues
        normalWorker.join();
        
        System.out.println();
        System.out.println("--- THREAD STATE TRANSITIONS ---");
        System.out.println("NEW -> RUNNABLE (after start())");
        System.out.println("RUNNABLE <-> RUNNING (JVM scheduler decides)");
        System.out.println("RUNNING -> WAITING (sleep, wait, I/O)");
        System.out.println("WAITING -> RUNNABLE (after condition met)");
        System.out.println("RUNNING -> TERMINATED (run() completes)");
        System.out.println("Any state -> TERMINATED (if interrupted)");
    }
}

