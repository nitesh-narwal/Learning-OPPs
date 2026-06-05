package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class QueueDemo {
    // A data structure that follows FIFO(First In First Out) principle.
    // Elements are inserted at the end and removed at the beginning.
    static void main() {
        // Let checking that linklist act as a Stack work on LIFO principle.
        System.out.println("<-------Stack---------->");
        LinkedList<Integer> stack = new LinkedList<>();
        stack.addFirst(10);
        stack.addFirst(20);
        stack.addFirst(30);
        System.out.println("This is the stack: Last In First Out => means 30 came Last so it will be removed first");
        System.out.println(stack); // [30, 20, 10]
        // Removing the first element.
        stack.removeFirst();
        System.out.println(stack); // [20, 10]

        // Let checking that linklist act as a Queue work on FIFO principle.
        System.out.println("\n<-------Queue---------->");
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(10); // puting the element is called as enqueue.
        queue.addLast(20);
        queue.addLast(30);
        System.out.println("This is the queue: First In First Out => means 10 came first so it will be removed first");
        System.out.println(queue); // [10, 20, 30]
        // Removing the first element.
        queue.removeFirst(); // Removing the first element is called as dequeue.
        System.out.println(queue); // [20, 30]
        queue.getFirst(); // This is called as peek() in queue. It will return the first element without removing it.

        // LinkedList implements Queue interface.
        System.out.println("\n<-------Queue using Queue Interface---------->");
        Queue<Integer> queue1 = new LinkedList<>();
        queue1.add(10); // puting the element is called as enqueue.
        queue1.add(20);
        queue1.add(30);
        System.out.println(queue1); // [10, 20, 30]
        // Removing the first element.
        queue1.remove(); // Removing the first element is called as dequeue.
        System.out.println(queue1); // [20, 30]
        queue1.element(); // This is called as peek() in queue. It will return the first element without removing it.

    }
}

class QueueDemo2 {
    static void main() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        System.out.println("Size of the queue: " + queue.size());
        System.out.println("Is the queue empty? " + queue.isEmpty());

        System.out.println("Removing element from the queue: " + queue.remove()); // throws NoSuchElementException if the queue is empty.
        System.out.println("Poll element form the queue: " + queue.poll()); // Better, returns null if the queue is empty.

        try {
            System.out.println("Element from the queue: " + queue.element()); // throws NoSuchElementException if the queue is empty.
        }catch (Exception e) {
            System.out.println("Queue is empty, So element is throwing exception: " + e);
        }

        System.out.println("Peek element from the queue: " + queue.peek()); // Better, returns null if the queue is empty.

        System.out.println("\n<-------Queue using ArrayBlockingQueue--------->");
        Queue<Integer> queue1 = new ArrayBlockingQueue<>(2); // This is a bounded queue, it has a fixed capacity.
        System.out.println(queue1.add(1)); // This will block if the queue is full.
        System.out.println(queue1.offer(2)); // This will return false if the queue is full.

        try {
            queue1.add(3); // Now this will throw an exception as the queue is full.
        }catch (Exception e) {
            System.out.println("Queue is full, So add is throwing exception: " + e);
        }

        System.out.println(queue1.offer(4)); // This will return false as the queue is full.
    }
}