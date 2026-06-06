package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeDemo {
    static void main(String[] args) {
            /*
            ConcurrentLinkedDeque is a thread-safe, non-blocking, unbounded deque (double-ended queue) implementation in Java.
            It is part of the java.util.concurrent package and is designed for concurrent access by multiple threads.

            Key features of ConcurrentLinkedDeque:
            1. Thread-Safe: It allows multiple threads to access and modify the deque concurrently without the need for external synchronization.
            2. Non-Blocking: It uses a lock-free algorithm, which means that threads can operate on the deque without being blocked by other threads.
            3. Unbounded: The deque can grow dynamically as needed, and there is no fixed capacity limit.
            4. Double-Ended: It supports operations at both ends of the deque, allowing you to add or remove elements from either the front or the back.

            Common operations include:
            - addFirst(E e): Adds an element to the front of the deque.
            - addLast(E e): Adds an element to the back of the deque.
            - pollFirst(): Retrieves and removes the first element of the deque, or returns null if the deque is empty.
            - pollLast(): Retrieves and removes the last element of the deque, or returns null if the deque is empty.
            - peekFirst(): Retrieves, but does not remove, the first element of the deque, or returns null if the deque is empty.
            - peekLast(): Retrieves, but does not remove, the last element of the deque, or returns null if the deque is empty.

            ConcurrentLinkedDeque is particularly useful in scenarios where multiple threads need to access a shared queue without blocking each other, such as in producer-consumer patterns or task scheduling.

            * And It uses CAS (Compare and Swap) to implement the thread-safe operations.
            */
        ConcurrentLinkedDeque<String> dqueue = new ConcurrentLinkedDeque<>();
        dqueue.add("Element1");
        dqueue.addFirst("Element0");
        dqueue.addLast("Element2");
        System.out.println(dqueue);

        String first = dqueue.removeFirst();
        String last = dqueue.removeLast();
        System.out.println("Removed First: " + first);
        System.out.println("Removed Last: " + last);
        System.out.println(dqueue);
    }
}
