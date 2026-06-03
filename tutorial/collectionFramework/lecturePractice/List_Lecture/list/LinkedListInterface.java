package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.List_Lecture.list;

import java.util.Arrays;
import java.util.LinkedList;

public class LinkedListInterface {
    /*
     * The LinkedList Class is a part of the Collections Framework and implements the List interface.
     * unlike an ArrayList, which uses a dynamic array to store its elements, a linkedlist stores elements as nodes.
     * This provide different performance characteristics and usage scenarios compared to an ArrayList.
     * */

    static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        list.add(60);
        System.out.println("The list is: " + list);
        System.out.println("The size of the list is: " + list.size());
        System.out.println("The element at index 2 is: " + list.get(2)); // O(n) time complexity for get method in LinkedList
        list.addFirst(70); // O(1) time complexity for addFirst method in LinkedList
        list.addLast(80); // O(1) time complexity for addLast method in LinkedList
        System.out.println("The first element is: " + list.getFirst());
        System.out.println("The last element is: " + list.getLast());
        System.out.println("The list is empty: " + list.isEmpty());
        System.out.println("The list contains 30: " + list.contains(30));
        System.out.println("The index of 30 is: " + list.indexOf(30));
        System.out.println("The list after removing 30: " + list.remove((Integer) 30));
        System.out.println("The list after removing the first element: " + list.removeFirst());
        System.out.println("The list after removing the last element: " + list.removeLast());
        System.out.println(list);
        list.add(1, 100);
        list.add(2, 200);
        list.add(3, 300);
        list.add(6, 400);
        System.out.println("List after adding: " + list);
        list.removeIf(x -> x%3 == 0);
        System.out.println(list);


        // Creating a linked list on Run
        LinkedList<String> list1 = new LinkedList<>( Arrays.asList( "Java", "Python", "C++", "JavaScript", "Ruby", "Swift", "Kotlin", "Go", "Rust", "TypeScript" ));
        System.out.println(list1);
        LinkedList<String> languagesToRemove = new LinkedList<>(Arrays.asList( "Java", "Python", "C++" ));
        list1.removeAll(languagesToRemove);
        System.out.println("After removing: "+ list1);


    }
}

class CustomeLinkedList {
    static void main(String[] args) {
//        // creating a linked list
//        Node head = new Node(10);
//        head.next = new Node(20);
//        head.next.next = new Node(30);
//        head.next.next.next = new Node(40);
//        head.next.next.next.next = new Node(50);
//
//        // traversing the linked list
//        Node current = head;
//        while (current != null) {
//            System.out.println(current.data);
//            current = current.next;
//        }

        Node node1 = new Node();
        Node node2 = new Node();
        node1.data = 10;
        node2.data = 20;
        node1.next = node2;
        node2.next = null;
        System.out.println(node1.data);
        System.out.println(node1.next.data);
        System.out.println(node1.next.next);
      //  System.out.println(node1.next.next.data); // This will throw a null pointer exception.



    }

}

class Node {
    public int data;
    public Node next;

}
