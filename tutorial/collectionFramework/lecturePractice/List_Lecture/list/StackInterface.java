package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.List_Lecture.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class StackInterface {
    static void main(String[] args) {
        //Stack follow LIFO(Last In First Out)
         /*
         * Stack() : Create a stack with default initial capacity of 10.
         * Stack(int initialCapacity) : Create a stack with the specified initial capacity.
         * Stack(Collection<? extends E> c) : Create a stack that contains the elements of the specified collection.
         * */

         /*
         * METHODS IN STACK:
         * 1. push(E item) : Pushes an item onto the top of this stack.
         * 2. pop() : Removes the object at the top of this stack and returns that object as the value of this function.
         * 3. peek() : Looks at the object at the top of this stack without removing it from the stack.
         * 4. empty() : Tests if this stack is empty.
         * 5. search(Object o) : Returns the 1-based position where an object is on this stack. If the object o occurs as an item in this stack,
         *                        the distance from the top of the stack is returned; the most recently added item is considered to be at distance 1.
         *                        The equals method is used to compare o to the items in this stack.
         * 6. clone() : Returns a shallow copy of this stack.
         * */

        Stack<Integer> stack = new Stack<>();
        //Stack is a class that extends Vector class, so it inherits all the methods of Vector class and also has its own methods.
        // And all the methods it has are synchronized.
        for (int i = 0; i < 15; i++) {
            stack.push(i);
        }
        System.out.println("The elements in the stack are: " + stack);
        stack.pop();
        System.out.println("The elements in the stack after popping an element are: " + stack);
        System.out.println("The top element in the stack is: " + stack.peek()); // peek() method show the top element without removing it.
        System.out.println(stack.empty()); // empty() method checks if the stack is empty or not.
        System.out.println(stack.hashCode()); // hashCode() method returns the hash code value for this stack.
        System.out.println(stack.isEmpty()); // isEmpty() method checks if the stack is empty or not.
        System.out.println(stack.size()); // size() method returns the number of elements in this stack.
        System.out.println(stack.search(10)); // search() method returns the 1-based position where an object is on this stack.
                                                // If the object o occurs as an item in this stack,
                                                // the distance from the top of the stack is returned;
                                                // the most recently added item is considered to be at distance 1.
        //System.out.println(stack.toString());
        System.out.println(stack);

        System.out.println("<-------------------------------------------------------------->");
        // We can use LinkedList as a Stack.
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addLast(1); // addLast() method adds an element at the end of the linked list.
        linkedList.addLast(2);
        linkedList.addLast(3);
        linkedList.addLast(4);
        linkedList.addLast(5);
        System.out.println("The elements in the linkedList are: " + linkedList);
        System.out.println(linkedList.getLast()); // getLast() method returns the last element in the linked list.
        //System.out.println(linkedList);
        linkedList.removeLast(); // removeLast() method removes the last element in the linked list.
        System.out.println(linkedList.size());

        System.out.println("<-------------------------------------------------------------->");
        // We can also use ArrayList as a Stack.
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        System.out.println("The elements in the arrayList are: " + arrayList);
        System.out.println(arrayList.get(arrayList.size() - 1)); // get() method or peek() for stack
        System.out.println(arrayList.remove(arrayList.size() - 1)); // remove() method or pop() for stack
        System.out.println(arrayList);
    }
}
