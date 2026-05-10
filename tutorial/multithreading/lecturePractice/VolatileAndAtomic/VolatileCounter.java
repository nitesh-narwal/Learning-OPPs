package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.VolatileAndAtomic;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileCounter {
    static void main() {

        //Now the code is thread safe
        // we achived atomicity by using AtomicInteger class
        // Atomicity means that the operation is performed atomically,

        Counter counter = new Counter();
        Thread incrementer = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        Thread increment2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        incrementer.start();
        increment2.start();

        try {
            incrementer.join();
            increment2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + counter.getCount());
    }

    public static class Counter {
        // voilatile keyword is used to make sure that the value of the variable is always updated in the main memory
        // but their is no use of volatile keyword in this case because the increment and decrement operations are not atomic,
        // they are not indivisible
     //   private volatile int count = 0;

        private AtomicInteger count = new AtomicInteger(0);

//        public void increment() {
//            count++;
//        }

        // we can use synchronized keyword to make sure that only one thread can access the method
//        public synchronized void increment() {
//            count++;
//        }

        //or we can use AtomicInteger class
        public void increment() {
            count.incrementAndGet();
        }

        public void decrement() {
            count.decrementAndGet();
        }

        public int getCount() {
            return count.get();
        }
    }
}
