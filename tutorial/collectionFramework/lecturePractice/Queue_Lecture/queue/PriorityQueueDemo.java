package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueDemo {
    static void main(String[] args) {
        // part of the queue interface.
        // Order elements based on their natural ordering(for Primitives Lowest first)
        // custom comparator for customize ordering.
        // does not allow null elements.

        Queue<Integer> pq = new PriorityQueue<>();
        // We can also put ordering in it
        //Queue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder()); // for reverse order, highest first.
        // Or our custom comparator.
        // Queue<Integer> pq = new PriorityQueue<>((x, y) -> y - x ); // for reverse order, highest first.
        pq.add(10);
        pq.add(15);
        pq.add(3);
        pq.add(20);
        pq.add(5);
        pq.add(11);
        pq.add(2);
        pq.add(41);
        System.out.println(pq); // not sorted
        while (!pq.isEmpty()) {
            System.out.println(pq.poll()); // removes the head of the queue (the smallest element) and returns it.
        }
    }
}

class SuggestedByAi{
    static void main() {
        // A priority queue is a special type of queue in
        // which each element is associated with a priority and is served according to its priority.
        // If two elements have the same priority, they are served according to their order in the queue.
        // In Java, the PriorityQueue class is used to implement a priority queue.
        // It is based on a binary heap data structure and provides O(log n) time complexity for insertion and removal operations.

        // Creating a priority queue of integers.
        java.util.PriorityQueue<Integer> pq = new java.util.PriorityQueue<>();
        pq.add(30);
        pq.add(10);
        pq.add(20);
        System.out.println("Priority Queue: " + pq); // The order of elements may not be the same as insertion due to the nature of the priority queue.

        // Removing elements from the priority queue.
        System.out.println("Removing element: " + pq.remove()); // Removes the head of the queue (the smallest element).
        System.out.println("Priority Queue after removal: " + pq);

        // Peeking at the head of the queue.
        System.out.println("Head of the queue: " + pq.peek()); // Returns the head of the queue without removing it.
    }
}