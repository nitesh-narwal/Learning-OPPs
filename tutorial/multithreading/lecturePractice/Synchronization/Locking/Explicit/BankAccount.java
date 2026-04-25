package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.Locking.Explicit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private int balance = 100;

    private final Lock lock = new ReentrantLock();

    public void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw: " + amount);

        /** here we got 2 types of "tryLock" method 1st without time and 2nd with time
         * 1st Case: which means if t2 thread is acquired the critical section
         * then t1 thread wouldn't wait for it to get free instead it returns false
         * telling the user that the method is busy so try later*/
//        if(lock.tryLock()){
//            // CRITICAL SECTION....
//        }

        /** 2nd Case: which means if t2 thread is acquired the critical section
         * then t1 thread will wait for the specified time for it to get free and
         * if it gets free within that time then it returns true otherwise false
         * */

        try {
            // lock.lock(); ----> it's same as synchronized, in it the 2nd thread will wait until the first thread releases
            //  the lock but in tryLock() method we can specify the time to wait for the lock .
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                try {
                    // CRITICAL SECTION....
                    if (balance >= amount) {
                        System.out.println(Thread.currentThread().getName() + " is withdrawing: " + amount);
                        Thread.sleep(5000); // Simulate time taken for withdrawal
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() + " completed the withdrawal. Remaining balance: " + balance);
                    } else {
                        System.out.println(Thread.currentThread().getName() + " insufficient funds for withdrawal. Current balance: " + balance);
                    }
                } finally {
                    lock.unlock();
                }
            }else{
                System.out.println(Thread.currentThread().getName() + " could not acquire the lock. Please try again later...");
            }
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getName() + " was interrupted while waiting for the lock.");
        }
    }
}
