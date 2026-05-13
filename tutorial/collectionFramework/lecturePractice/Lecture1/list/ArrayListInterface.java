package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Lecture1.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayListInterface {
    /*
    * An ArrayList is a resizable array implementation of the List interface in Java.
    *  It provides dynamic arrays that can grow as needed,
    *  allowing for efficient storage and retrieval of elements.
    *  The ArrayList class is part of the java.util package and offers various methods to manipulate the list,
    *  such as adding, removing, and accessing elements.
    * ArrayList is a good choice for storing a large number of elements,
    * where the order of insertion or removal is important.
    *
    * */

    static void main(String[] args) {
        // Create an ArrayList of Strings
        //ArrayList<String> list = new ArrayList<>();  // we can also set the capacity of the arraylist
        List<String> list1 = new ArrayList<>(); // Right now the size of the list is 0 but the capacity is 10,
                                                // which means it can hold up to 10 elements before needing to resize.

        /** When you create an ArrayList, it has an initial capacity(default 10) and a load factor(default 0.75).
         *  The capacity refers to the size of the internal array that can hold elements before needing to resize.*/

        // Add elements to the ArrayList
        list1.add("Hello");
        list1.add("World");
        list1.add("Java");
/*
        // Access elements in the ArrayList
        System.out.println("First element: " + list.get(0)); // Output: Hello

        // Remove an element from the ArrayList
        list.remove(1); // Removes "World"

        // Print the size of the ArrayList
        System.out.println("Size of the list: " + list.size()); // Output: 2

        // Iterate through the ArrayList
        for (String element : list) {
            System.out.println(element);
        }

        List<String> names = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        boolean continueProgram = false;
        while (!continueProgram) {
            System.out.println("Enter 'y' to continue or 'n' to exit:");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("y")) {
                String name = sc.nextLine();
                System.out.print("Enter your word: " );
                names.add(name);
            } else if (input.equalsIgnoreCase("r") && names.size() > 0) {
                // removing the element of the specidic index
                names.remove(names.size() - 1);
                System.out.println("The last element is removed");
            } else {
                System.out.println("Exiting the program...");
                break;
            }
        }

 */
//        List<Integer> names = new ArrayList<>();
//        Scanner sc = new Scanner(System.in);
//        int i = 0;
//        boolean continueProgram = true;
//
////        if(names.size() == 0) {
////            while (continueProgram) {
////                System.out.print("Enter your word: ");
////                names.add(sc.nextInt());
////                i++;
////                if (i == 5) {
////                    continueProgram = false;
////                }
////            }
////        }
//            System.out.println("Your list is not empty, here are the elements: ");
//            for (int x : names) {
//                System.out.print(x + ", ");
//            }


        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);

        list.remove(2); // Removes the element at index 2 (which is 3)
        list.add(2, 10); // Adds the element 10 at index 2
        list.set(3, 20); // Updates the element at index 3 to 20
        System.out.println(list);

    }

}
