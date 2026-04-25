package me.niteshh.OPPs.tutorial.multithreading.inDepth;

/**
 * STEP 4: THREAD COMMUNICATION
 * 
 * PROBLEM: Threads often need to communicate and coordinate with each other.
 * - Producer needs to wait if buffer is full
 * - Consumer needs to wait if buffer is empty
 * - Threads need to notify others when state changes
 * 
 * SOLUTION: wait(), notify(), and notifyAll() methods
 * These allow threads to signal each other about state changes.
 */

import java.util.LinkedList;
import java.util.Queue;

// ==================== SIMPLE WAIT/NOTIFY EXAMPLE ====================
class SharedResource {
    private String data = null;
    private boolean dataAvailable = false;
    
    // Producer: Sets data and notifies consumer
    public synchronized void produce(String value) {
        this.data = value;
        this.dataAvailable = true;
        System.out.println("Produced: " + value);
        
        // Wake up all waiting threads (consumers)
        notifyAll();
    }
    
    // Consumer: Waits for data and retrieves it
    public synchronized String consume() throws InterruptedException {
        // Wait while no data is available
        while (!dataAvailable) {
            System.out.println("Consumer waiting for data...");
            wait();  // Release lock and wait for notification
        }
        
        String value = data;
        dataAvailable = false;
        System.out.println("Consumed: " + value);
        return value;
    }
}

// ==================== PRODUCER-CONSUMER PATTERN (Queue-based) ====================
/**
 * A common pattern for thread communication.
 * Producer adds items to queue, Consumer removes items.
 * Uses bounded buffer to control memory usage.
 */
class ProducerConsumerBuffer {
    private Queue<Integer> buffer = new LinkedList<>();
    private final int MAX_SIZE = 5;
    private final Object lock = new Object();
    
    public void produce(int value) throws InterruptedException {
        synchronized (lock) {
            // Wait if buffer is full
            while (buffer.size() >= MAX_SIZE) {
                System.out.println("[Producer] Buffer full, waiting...");
                lock.wait();  // Consumer will notify when buffer has space
            }
            
            buffer.add(value);
            System.out.println("[Producer] Produced: " + value + " (Size: " + buffer.size() + ")");
            
            // Notify consumer that data is available
            lock.notifyAll();
        }
    }
    
    public int consume() throws InterruptedException {
        synchronized (lock) {
            // Wait if buffer is empty
            while (buffer.isEmpty()) {
                System.out.println("[Consumer] Buffer empty, waiting...");
                lock.wait();  // Producer will notify when buffer has data
            }
            
            int value = buffer.poll();
            System.out.println("[Consumer] Consumed: " + value + " (Size: " + buffer.size() + ")");
            
            // Notify producer that buffer has space
            lock.notifyAll();
            return value;
        }
    }
}

// ==================== THREAD SIGNALING WITH COUNTERS ====================
class WorkerCoordinator {
    private int completedTasks = 0;
    private final int TOTAL_TASKS;
    
    public WorkerCoordinator(int totalTasks) {
        this.TOTAL_TASKS = totalTasks;
    }
    
    // Worker calls this to report completion
    public synchronized void taskCompleted(String workerName) {
        completedTasks++;
        System.out.println(workerName + " completed task. Progress: " + completedTasks + "/" + TOTAL_TASKS);
        
        // Notify main thread if all tasks are done
        if (completedTasks == TOTAL_TASKS) {
            notifyAll();
        }
    }
    
    // Main thread waits for all tasks to complete
    public synchronized void waitForCompletion() throws InterruptedException {
        System.out.println("Waiting for all " + TOTAL_TASKS + " tasks to complete...");
        
        while (completedTasks < TOTAL_TASKS) {
            wait();  // Wait until all tasks are done
        }
        
        System.out.println("All tasks completed!");
    }
}

// ==================== DIFFERENCE: wait() vs notify() vs notifyAll() ====================
/**
 * wait()       : Current thread RELEASES lock and waits
 * notify()     : Wakes ONE waiting thread (unpredictable which one)
 * notifyAll()  : Wakes ALL waiting threads (they compete for lock)
 * 
 * IMPORTANT: These only work inside synchronized blocks/methods!
 */

// ==================== THREADS FOR DEMO ====================
class ProducerThread implements Runnable {
    private ProducerConsumerBuffer buffer;
    private int itemsToProduced;
    
    public ProducerThread(ProducerConsumerBuffer buffer, int items) {
        this.buffer = buffer;
        this.itemsToProduced = items;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 1; i <= itemsToProduced; i++) {
                buffer.produce(i);
                Thread.sleep(500);  // Simulate production time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class ConsumerThread implements Runnable {
    private ProducerConsumerBuffer buffer;
    private int itemsToConsume;
    
    public ConsumerThread(ProducerConsumerBuffer buffer, int items) {
        this.buffer = buffer;
        this.itemsToConsume = items;
    }
    
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsToConsume; i++) {
                buffer.consume();
                Thread.sleep(800);  // Simulate consumption time
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class WorkerThread implements Runnable {
    private WorkerCoordinator coordinator;
    private String workerName;
    private int taskDuration;
    
    public WorkerThread(WorkerCoordinator coordinator, String name, int duration) {
        this.coordinator = coordinator;
        this.workerName = name;
        this.taskDuration = duration;
    }
    
    @Override
    public void run() {
        try {
            System.out.println(workerName + " started working...");
            Thread.sleep(taskDuration);
            coordinator.taskCompleted(workerName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Step4_ThreadCommunication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n========== THREAD COMMUNICATION TUTORIAL ==========\n");
        
        // ========== SIMPLE WAIT/NOTIFY ==========
        System.out.println("--- EXAMPLE 1: Simple Producer-Consumer ---");
        SharedResource resource = new SharedResource();
        
        Thread consumer = new Thread(() -> {
            try {
                resource.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        consumer.start();
        Thread.sleep(1000);  // Let consumer wait first
        
        resource.produce("Important Data");
        consumer.join();
        
        System.out.println();
        
        // ========== PRODUCER-CONSUMER WITH BUFFER ==========
        System.out.println("--- EXAMPLE 2: Producer-Consumer with Buffer ---");
        ProducerConsumerBuffer buffer = new ProducerConsumerBuffer();
        
        Thread producer = new Thread(new ProducerThread(buffer, 10));
        Thread consumer1 = new Thread(new ConsumerThread(buffer, 5));
        Thread consumer2 = new Thread(new ConsumerThread(buffer, 5));
        
        long startTime = System.currentTimeMillis();
        
        producer.start();
        consumer1.start();
        consumer2.start();
        
        producer.join();
        consumer1.join();
        consumer2.join();
        
        long endTime = System.currentTimeMillis();
        System.out.println("Buffer demo completed in " + (endTime - startTime) + "ms");
        
        System.out.println();
        
        // ========== WORKER COORDINATION ==========
        System.out.println("--- EXAMPLE 3: Worker Coordination ---");
        WorkerCoordinator coordinator = new WorkerCoordinator(3);
        
        Thread worker1 = new Thread(new WorkerThread(coordinator, "Worker-1", 1000));
        Thread worker2 = new Thread(new WorkerThread(coordinator, "Worker-2", 2000));
        Thread worker3 = new Thread(new WorkerThread(coordinator, "Worker-3", 1500));
        
        worker1.start();
        worker2.start();
        worker3.start();
        
        startTime = System.currentTimeMillis();
        coordinator.waitForCompletion();  // Main thread waits here
        endTime = System.currentTimeMillis();
        
        System.out.println("Main thread resumed after " + (endTime - startTime) + "ms");
        
        worker1.join();
        worker2.join();
        worker3.join();
        
        System.out.println();
        System.out.println("--- KEY CONCEPTS ---");
        System.out.println("1. wait() - Current thread releases lock and waits for notification");
        System.out.println("2. notify() - Wakes one waiting thread (unpredictable)");
        System.out.println("3. notifyAll() - Wakes all waiting threads (better for multiple consumers)");
        System.out.println("4. Always use while loop with wait() to check condition again");
        System.out.println("5. wait()/notify() only work in synchronized blocks");
        System.out.println("6. Producer-Consumer pattern is widely used in real applications");
    }
}

