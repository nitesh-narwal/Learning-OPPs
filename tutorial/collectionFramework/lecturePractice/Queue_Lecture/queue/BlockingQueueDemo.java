package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.ThreadCommunication.ThreadComm;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueDemo {
    static void main(String[] args) {
        // thread safe queue.
        // wait for queue to become non-empty/ wait for space in queue.
        // Simplify concurrent problems like producer-consumer
        // Standard Queue ---> immediately runs
        // means when we run things like
           // queue is empty ---> remove element the command runs (no waiting happened here)
           // queue is full ---> add element the command runs (no waiting happened here)
        // Blocking Queue
            // put ---> Blocks if the queue is full until space is available.
            // take ---> Blocks if the queue is empty until an element becomes available.
            // offer ---> Wait for space to become available, up to a specified timeout, or until an element becomes available.
            // poll ---> Wait for an element to become available, up to a specified timeout, or until an element becomes available.

    /*
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        // A bounded blocking queue backed by a circular array.
        // low memory overhead
        // Uses a single lock for both enque and deque operations.
        // more threads ---> more problems in this ArrayBlockingQueue
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
*/

        BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
        // Optionally bounded, backed by LinkedList
        // Use 2 separate locks for enque and deque operations.
        // High concurrency between producer and consumer
        BlockingQueue<String> queue2 = new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        // Unbounded
        // Binary heap as an array and can grow dynamically.
        // Head is based on their natural ordering, or a provided Comparator like priority queue.
        // put() wouldn't block
        queue2.add("apple");
        queue2.add("banana");
        queue2.add("orange");
        queue2.add("grapes");
        queue2.add("mango");
        queue2.add("kiwi");
        queue2.add("pineapple");
        queue2.add("strawberry");
        queue2.add("watermelon");
        queue2.add("papaya");
        queue2.add("coconut");

        System.out.println(queue2);



    }
}

class Producer implements Runnable {

    private BlockingQueue<Integer> queue;
    private int value = 0;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                queue.put(value);
                // if i use offer then it will not wait for space to become available,
                // it will return false immediately if the queue is full.
                // 1000 is the timeout for waiting for space to become available.
                // But we can also add time in offer() method to wait for space to become available.
                System.out.println("Produced: " + value);
                value++;
                Thread.sleep(1000); // Simulate time taken to produce an item
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int item = queue.take();
                System.out.println("Consumed: " + item);
                Thread.sleep(2000); // Simulate time taken to consume an item
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}