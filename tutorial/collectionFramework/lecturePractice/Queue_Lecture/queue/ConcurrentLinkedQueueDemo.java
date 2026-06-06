package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueDemo {
    // An implementation of the Queue interface that supports lock-free, thread-safe operations and concurrent access.
    //
    static void main(String[] args) {
    }
}

class TaskSubmissionSystem {
    static void main(String[] args) {
        ConcurrentLinkedQueue<String> taskQueue = new ConcurrentLinkedQueue<>();

        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    taskQueue.add("Task " + System.currentTimeMillis() + " - " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//        Thread producer2 = new Thread(() -> {
//            while (true) {
//                try {
//                    taskQueue.add("Task " + System.currentTimeMillis() +" - "+ Thread.currentThread().getName());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        Thread consumer = new Thread(() -> {
            while (true) {
                String task = taskQueue.poll();
                if (task != null) {
                    System.out.println("Processing: " + task);
                }
            }
        });

//        Thread consumer2 = new Thread(() -> {
//            while (true) {
//                String task = taskQueue.poll();
//                if (task != null) {
//                    System.out.println("Processing: " + task);
//                }
//            }
//        });

        producer.start();
        consumer.start();
      //  producer2.start();
     //   consumer2.start();

    }
}
