package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class IteratorDemo {
    static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        // suppose we are iterating through iterate a list of numbers. and we are doing it through a for loop.
        for(int i : list) {
            System.out.println(list.get(i));
        } // And how for Each loop works internally ?
        // But Java compiler convert it into different code. And that's how it looks
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        // and that's how it iterates through the list or a collection
        // because it implements the Iterator interface so we use methods
        // like hasNext(), next(), remove() etc.
        // hasnext() method checks if there are more elements to iterate through. or is there any element
        // next() method returns the next element in the list.


        // NOW TAKE AN EXAMPLE OF ARRAYLIST.
        // ❌ PROBLEM: Arrays.asList() returns IMMUTABLE list (fixed-size)
        // ✅ SOLUTION: Wrap it with new ArrayList<>() to make it mutable
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // now if we want to iterate through the list we can use for each loop.
        // while iteratung through the list. If we do some modification to the list then it will through an exception.
        // Like if we try to remove even numbers or element from the list.
        System.out.println("Original list: " + numbers);
        for(int i : numbers) {
            try {
                if (i % 2 == 0) {
                    numbers.remove((Integer) i);
                }
            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getClass().getSimpleName());
                System.out.println("Message: " + e.getMessage());
            }
        }
        System.out.println(numbers);  // And modification didn't happen.

        // We can do modification if we choose CopyOnWriteArrayList.
        List<Integer> numbers1 = new CopyOnWriteArrayList<>();// This is a thread safe list.
        /* CopyOnWriteArrayList creates a NEW copy of the array
         * whenever modification happens.
         *
         * Iterator keeps reading the OLD snapshot.
         *
         * Therefore:
         * No ConcurrentModificationException.
         */
        numbers1.add(1);
        numbers1.add(2);
        numbers1.add(3);
        numbers1.add(4);
        numbers1.add(5);
        numbers1.add(6);
        numbers1.add(7);
        numbers1.add(8);
        System.out.println("The unmodified list is: " + numbers1);
        for(int i : numbers1) {
            /*
             * Here i is an int.
             * If we write:
             * numbers3.remove(i);
             * Java treats i as INDEX.
             * To remove VALUE,
             * convert it into Integer object.
             */
                if (i % 2 == 0) {
                    int j = numbers1.indexOf(i);
                    numbers1.remove(j);
                }
        }

        System.out.println("The modified list is : " + numbers1);

        // But we don't need to do all this iterator can do modification while iterating.
        // This is the BEST approach for regular ArrayList
        List<Integer> numbers2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Iterator<Integer> itr = numbers2.iterator();
        while (itr.hasNext()) {
            Integer number = itr.next();
            if (number % 2 == 0) {
                itr.remove();
            }
        }
        System.out.println("The modified list is via iterator : " + numbers2);


        // NOW WE ARE GOING TO TALK ABOUT ListIterator.
        ListIterator<Integer> itr2 = numbers2.listIterator();
        while (itr2.hasNext()) {
           itr2.set(itr2.next() * 10); // This will multiply each element by 10. here we are using set() method to modify the element while iterating through the list.
            }
        System.out.println("The modified list is via ListIterator : " + numbers2);

    }
}
