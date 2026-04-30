package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.read_write_locking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteCounter {

    private int count = 0;

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    /** We have created an instance for readwrite lock
     * In it we got to methods readlock and for write lock
     * */
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();


    public void increment(){
        writeLock.lock();
        try{
            count++;
        }
        finally {
            writeLock.unlock();
        }
    }

    public int getReadLock() {
        /** The special thing in ReentrantReadWriteLock is that
         * Multiple threads which are reading can access this lock
         * simultaneously
         * But in case of Reentrant lock only one thread can access the methods
         * one by one if one acquired the lock then the other thread will wait until the first thread releases the lock
         *
         * but in case of ReentrantReadWriteLock if one thread is reading then the other thread can also read at the same time
         * but if one thread is writing then the other thread will wait until the first thread releases the lock
         * */
        readLock.lock();
        try{
            return count;
        }
        finally {
            readLock.unlock();
        }
    }

    static void main(String[] args) {

        ReadWriteCounter counter = new ReadWriteCounter();

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " read count: " + counter.getReadLock());
                    try {
                        Thread.sleep(1000); // Simulate time taken for reading
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    counter.increment();
                    System.out.println(Thread.currentThread().getName() + " incremented count.");
                    try {
                        Thread.sleep(1000); // Simulate time taken for writing
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        Thread writeThread = new Thread(writeTask, "Writer Thread");
        Thread readThread1 = new Thread(readTask, "Reader Thread 1");
        Thread readThread2 = new Thread(readTask, "Reader Thread 2");

        writeThread.start();
        readThread1.start();
        readThread2.start();

        try {
            writeThread.join();
            readThread1.join();
            readThread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Final count: " + counter.getReadLock());
    }
}




