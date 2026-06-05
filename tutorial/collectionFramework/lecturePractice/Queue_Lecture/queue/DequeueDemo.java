package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class DequeueDemo {
    static void main(String[] args) {
        // double ended queue
        // Allow insertion and removal of elements at both ends.
        // Versatile the regular queue and stack because they support all the operations of both.

        /*
        * INSERTION METHODS:
        *
        * 1. addFirst(E e): Inserts the specified element at the front of the deque.
        * 2. addLast(E e): Inserts the specified element at the end of the deque.
        * 3. offerFirst(E e): Inserts the specified element at the front of the deque. Returns true if the element was added successfully, false otherwise.
        * 4. offerLast(E e): Inserts the specified element at the end of the deque. Returns true if the element was added successfully, false otherwise.
        *
        *
        * REMOVAL METHODS:
        *
        * 1. removeFirst(): Removes and returns the element at the front of the deque.
        * 2. removeLast(): Removes and returns the element at the end of the deque.
        * 3. pollFirst(): Removes and returns the element at the front of the deque, or null if the deque is empty.
        * 4. pollLast(): Removes and returns the element at the end of the deque, or null if the deque is empty.
        *
        *
        * EXAMINATION METHODS:
        *
        * 1. getFirst(): Retrieves, but does not remove, the element at the front of the deque.
        * 2. getLast(): Retrieves, but does not remove, the element at the end of the deque.
        * 3. peekFirst(): Retrieves, but does not remove, the element at the front of the deque, or null if the deque is empty.
        * 4. peekLast(): Retrieves, but does not remove, the element at the end of the deque, or null if the deque is empty.
        *
        *
        * STACK METHODS:
        *
        * 1. push(E e): Inserts the specified element at the top of this deque(equivalent to addFirst(e))
        * 2. pop(): Removes and returns the element at the top of this deque(equivalent to removeFirst())
        * */

        Deque<Integer> deque = new ArrayDeque<>(); // faster iteration because internally it uses array, low memory usage because we are not involving pointers, no null allowed
        // Circular, head and tail
        // No need to shift elements, just shift head and tail pointers.
        // If the deque is full, then the head and tail pointers will point to the same element. Then we increase the size of the deque.
        //
        deque.addFirst(10);
        deque.addLast(20);
        deque.offerFirst(5);
        deque.offerLast(25);
        System.out.println(deque); // [5, 10, 20, 25]
        System.out.println("First element: " + deque.getFirst() + ", Last element: " + deque.getLast()); // First element: 5, Last element: 25
        System.out.println("First element removed: " + deque.removeFirst()); // 5
        System.out.println("Last element poll: " + deque.pollLast()); // 25

        System.out.println(deque); // [10, 20]
        System.out.println("\nNormal iteration:");
        // normal iteration
        for(int dq : deque){
            System.out.println(dq);
        }

        // Below all the iteration methods are SELF LEARNINGS not from the leacture.
        System.out.println("\nUsing forEach:");
        // using forEach
        deque.forEach(System.out::println);

        System.out.println("\nUsing iterator:");
        // using iterator
        var iterator = deque.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println("\nUsing Lambda expression:");
        deque.forEach(e -> System.out.println(e));

        System.out.println("\nUsing method reference:");
        deque.forEach(System.out::println);

        System.out.println("\nUsing parallel stream:");
        deque.parallelStream().forEach(System.out::println);

        System.out.println("\nUsing sequential stream:");
        deque.stream().sequential().forEach(System.out::println);

        System.out.println("\nUsing stream:");
        deque.stream().forEach(System.out::println);

        Deque<Integer> deque1 = new LinkedList<>(); // Better if uses for Inserting and removing the elements from somewhere middle of the deque.
        deque1.addFirst(10);
        deque1.addLast(20);
        deque1.offerFirst(5);
        deque1.offerLast(25);
        System.out.println(deque1); // [5, 10, 20, 25]


    }
}
