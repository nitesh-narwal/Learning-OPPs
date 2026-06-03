package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice;






import me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.List_Lecture.Student;

import java.util.ArrayList;
import java.util.List;

public class ComparableDemo {
    static void main(String[] args) {
    /* Comparable is an interface in Java that allows objects to be compared to each other for sorting purposes.
         * It defines a single method, compareTo(), which compares the current object with another object of the same type.
         * The compareTo() method returns a negative integer, zero, or a positive integer as this object is less than,
            equal to, or greater than the specified object.
    */
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20));
        students.add(new Student("Bob", 22));
        students.add(new Student("Charlie", 18));
        students.add(new Student("David", 25));
        students.add(new Student("Eve", 23));
        students.add(new Student("Frank", 21));

        List<Integer> number = new ArrayList<>();
        number.add(80);
        number.add(20);
        number.add(40);
        number.add(10);
        number.add(90);
        number.add(60);
        // Now it don't show use any exception. It will sort the list.
        number.sort(null); // Sorts the list using the natural ordering defined by the compareTo() method in the Integer class
        System.out.println(number);


        // Now to remove the exception we have to implement the comparable interface in the student class and we have to override the compareTo() method in the student class.
        students.sort(null); // Sorts the list using the natural ordering defined by the compareTo() method in the Student class
         // but we haven't given the natural ordering instead we put null
        /**
          * We use comparator when we want to sort the list based on some criteria or to write our own custom sorting logic.
          *
          * But when we have to sort the list using the natural ordering we uses comparable interface and we have to implement the compareTo() method in the class whose object we want to sort.
          * And how we can do that by implements comparable interface on the class.
          * */
        System.out.println(students);

        /*
        *  We use comparator when we want to sort the list based on some criteria or to write our own custom sorting logic.
        *  and on multiple criteria we use comparator.
        * */




        // Example of using Comparable interface
//        Student student1 = new Student("Alice", 20);
//        Student student2 = new Student("Bob", 22);
//        Student student3 = new Student("Charlie", 18);
//
//        System.out.println(student1.compareTo(student2)); // Output: -1 (because Alice is younger than Bob)
//        System.out.println(student1.compareTo(student3)); // Output: 1 (because Alice is older than Charlie)
//        System.out.println(student2.compareTo(student3)); // Output: 1 (because Bob is older than Charlie)
    }
}
