package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.Locking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnfairLockExample {

    /**
     * why do we need fairness? -> we required fairness because without it,
     *                              if their are many threads working together
     *                              their would be a high chance of thread starvation,
     *                              which means that some threads may never get a chance to execute
     *                              because other threads are always acquiring the lock before them.
     * */

    private final Lock unfairlock = new ReentrantLock(true);

    public void performTask() {
        System.out.println(Thread.currentThread().getName() + " is trying to acquire the lock...");
        unfairlock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " has acquired the lock.");
            // Simulate some work with the lock held
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted while holding the lock.");
        } finally {
            unfairlock.unlock();
            System.out.println(Thread.currentThread().getName() + " has released the lock.");
        }
    }

    static void main(String[] args) {
        UnfairLockExample example = new UnfairLockExample();

        Runnable task = example::performTask;

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        Thread t3 = new Thread(task, "Thread 3");

        try{
            t1.start();
            t1.sleep(100); // Slight delay to increase the chance of contention
            t2.start();
            t2.sleep(100); // Slight delay to increase the chance of contention
            t3.start();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread was interrupted.");
        }


    }

}
