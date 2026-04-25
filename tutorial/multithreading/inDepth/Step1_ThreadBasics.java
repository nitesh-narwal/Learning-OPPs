package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * STEP 1: THREAD BASICS
 * 
 * A thread is a lightweight sub-process that allows concurrent execution of code within a program.
 * Think of it as a separate path of execution within the same program.
 * 
 * There are TWO ways to create threads in Java:
 * 1. Extend Thread class
 * 2. Implement Runnable interface
 * 
 * WHY TWO WAYS?
 * - Thread class: Direct extension but single inheritance limitation
 * - Runnable interface: More flexible, allows other inheritance, PREFERRED way
 */

// ==================== METHOD 1: EXTENDING THREAD CLASS ====================
class MyThread extends Thread {
    private String threadName;
    
    public MyThread(String name) {
        this.threadName = name;
        // Every thread has a unique thread ID and a name
        System.out.println("Thread created: " + threadName + " (ID: " + this.getId() + ")");
    }
    
    // run() method contains the code to execute
    @Override
    public void run() {
        System.out.println(threadName + " is running - CurrentThread: " + Thread.currentThread().getName());
        
        // Simulate some work
        for (int i = 1; i <= 3; i++) {
            System.out.println(threadName + " - Work iteration: " + i);
            try {
                // Thread sleeps for 1 second (pauses execution)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted!");
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }
        System.out.println(threadName + " finished!");
    }
}

// ==================== METHOD 2: IMPLEMENTING RUNNABLE INTERFACE ====================
class MyRunnable implements Runnable {
    private String runnableName;
    
    public MyRunnable(String name) {
        this.runnableName = name;
    }
    
    @Override
    public void run() {
        System.out.println(runnableName + " (Runnable) is running");
        
        for (int i = 1; i <= 3; i++) {
            System.out.println(runnableName + " - Task: " + i);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(runnableName + " (Runnable) finished!");
    }
}

// ==================== LAMBDA EXPRESSION (JAVA 8+) ====================
// Runnable is a functional interface, so we can use lambda
// This is the most modern and clean way

public class Step1_ThreadBasics {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n========== MULTITHREADING BASICS TUTORIAL ==========\n");
        
        // ========== METHOD 1: Thread Class ==========
        System.out.println("--- METHOD 1: Extending Thread Class ---");
        MyThread thread1 = new MyThread("Thread-1");
        MyThread thread2 = new MyThread("Thread-2");
        
        // Calling run() directly executes sequentially (NOT multithreading!)
        // thread1.run();  // DON'T DO THIS - Wrong approach
        
        // Calling start() creates a new thread and calls run() inside it
        thread1.start();  // RIGHT - Creates new thread
        thread2.start();  // Creates another new thread
        
        // Wait for threads to complete
        thread1.join();   // Main thread waits for thread1 to finish
        thread2.join();   // Main thread waits for thread2 to finish
        
        System.out.println();
        
        // ========== METHOD 2: Runnable Interface ==========
        System.out.println("--- METHOD 2: Implementing Runnable Interface ---");
        MyRunnable runnable1 = new MyRunnable("Worker-1");
        MyRunnable runnable2 = new MyRunnable("Worker-2");
        
        // Create Thread objects passing Runnable
        Thread thread3 = new Thread(runnable1);
        Thread thread4 = new Thread(runnable2);
        
        thread3.start();
        thread4.start();
        
        thread3.join();
        thread4.join();
        
        System.out.println();
        
        // ========== METHOD 3: Lambda Expression (Cleanest) ==========
        System.out.println("--- METHOD 3: Using Lambda Expression (PREFERRED) ---");
        Thread thread5 = new Thread(() -> {
            System.out.println("Lambda Thread is running");
            for (int i = 1; i <= 2; i++) {
                System.out.println("Lambda - Step: " + i);
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Lambda Thread finished!");
        });
        
        thread5.start();
        thread5.join();
        
        System.out.println("\n--- KEY LEARNING POINTS ---");
        System.out.println("1. start() creates a NEW thread, run() does NOT");
        System.out.println("2. join() makes main thread WAIT for other threads");
        System.out.println("3. Runnable is preferred over Thread class (flexibility)");
        System.out.println("4. Lambda is the cleanest syntax (Java 8+)");
        System.out.println("5. Each thread has its own stack, but shares heap memory");
    }
}

