package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {
    static void main(String[] args) {
        // A blocking queue in which each insert operation must wait for a corresponding remove operation by another thread, and vice versa.
        // No internal capacity, not even a capacity of one.
        // Max capacity of at most one.
        // Each put must wait for a take, and each take must wait for a put.
        // Useful when you want to hand off tasks from one thread to another without buffering.

        BlockingQueue<String> sq = new SynchronousQueue<>();

        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer is waiting to put an element...");
                sq.put("Hello from Producer!");
                System.out.println("Producer has put an element.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Consumer is waiting to take an element...");
                String message = sq.take();
                System.out.println("Consumer has taken an element: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
