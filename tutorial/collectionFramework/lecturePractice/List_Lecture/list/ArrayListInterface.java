package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.List_Lecture.list;

import java.util.*;

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
        //ArrayList<String> list = new ArrayList<>(1000);  // we can also set the capacity of the arraylist
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

        /*
         * HOW TO CHECK CAPACITY OF ARRAYLIST (Including After Resizing)
         * 
         * PROBLEM: ArrayList does not have a built-in method like size() to get capacity.
         * WHY: The capacity is an internal implementation detail. Java hides it on purpose
         *      to prevent developers from relying on implementation details.
         *
         * SOLUTION: Use Reflection to access the private 'elementData' array
         *           which stores the actual elements. The length of this array is the capacity.
         *
         * REFLECTION: It's a Java mechanism to inspect and modify objects at runtime.
         *            We can access private fields, methods, etc. that are normally hidden.
         *
         */

        System.out.println("\n========== UNDERSTANDING ARRAYLIST CAPACITY ==========\n");

        // Create a fresh ArrayList to demonstrate capacity
        ArrayList<Integer> capacityDemo = new ArrayList<>();
        
        // Print initial state
        System.out.println("Step 1: Initial ArrayList created (empty)");
        System.out.println("Size: " + capacityDemo.size() + ", Capacity: " + getCapacity(capacityDemo));
        // Output: Size: 0, Capacity: 10 (default initial capacity)

        // Add elements one by one and observe capacity changes
        System.out.println("\nStep 2: Adding 10 elements (within initial capacity of 10)");
        for (int i = 1; i <= 10; i++) {
            capacityDemo.add(i);
            System.out.println("Added: " + i + " | Size: " + capacityDemo.size() + ", Capacity: " + getCapacity(capacityDemo));
        }
        // Capacity remains 10 as we haven't exceeded it yet

        // Now exceed the capacity - this triggers RESIZING
        System.out.println("\nStep 3: Adding 11th element (EXCEEDS initial capacity - RESIZE HAPPENS)");
        capacityDemo.add(11);
        System.out.println("Added: 11 | Size: " + capacityDemo.size() + ", Capacity: " + getCapacity(capacityDemo));
        // Capacity becomes 15 (ArrayList grows by approximately 50% or more)
        // OLD CAPACITY: 10
        // NEW CAPACITY: 15 (10 + 10/2 = 15)

        // Add more elements to trigger another resize
        System.out.println("\nStep 4: Adding more elements to trigger second resize");
        for (int i = 12; i <= 16; i++) {
            capacityDemo.add(i);
            System.out.println("Added: " + i + " | Size: " + capacityDemo.size() + ", Capacity: " + getCapacity(capacityDemo));
        }
        // After adding 16th element, capacity becomes 22 (15 + 15/2 = 22)

        System.out.println("\nStep 5: Add elements up to 23rd element");
        for (int i = 17; i <= 23; i++) {
            capacityDemo.add(i);
            System.out.println("Added: " + i + " | Size: " + capacityDemo.size() + ", Capacity: " + getCapacity(capacityDemo));
        }
        // After adding 23rd element, capacity becomes 33 (22 + 22/2 = 33)

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL STATE:");
        System.out.println("Size: " + capacityDemo.size());
        System.out.println("Capacity: " + getCapacity(capacityDemo));
        System.out.println("Unused Slots: " + (getCapacity(capacityDemo) - capacityDemo.size()));
        System.out.println("Memory Overhead: " + ((getCapacity(capacityDemo) - capacityDemo.size()) * 100 / getCapacity(capacityDemo)) + "%");
        System.out.println("=".repeat(60));

        /*
         * KEY INSIGHTS:
         * 
         * 1. SIZE vs CAPACITY:
         *    - Size = Number of actual elements in the list (3 elements = size 3)
         *    - Capacity = Maximum elements it can hold without resizing (10 slots allocated = capacity 10)
         * 
         * 2. RESIZING STRATEGY:
         *    - When you add beyond capacity, ArrayList creates a new larger array
         *    - Growth factor is typically 1.5x (50% increase) or sometimes 2x
         *    - Example: 10 -> 15 -> 22 -> 33 (using 1.5x formula)
         * 
         * 3. WHY USE CAPACITY CHECKING:
         *    - Performance Analysis: Understand if your list is wasting memory
         *    - Optimization: Pre-allocate capacity if you know size: new ArrayList<>(10000)
         *    - Debug: Verify resizing behavior in your application
         * 
         * 4. BEST PRACTICE:
         *    - If you know the size beforehand, create with that capacity:
         *      ArrayList<String> list = new ArrayList<>(5000); // Don't resize, just allocate
         *    - Avoids expensive copying operations during resizing
         *    - Improves performance for large collections
         */
    }

    /*
     * HELPER METHOD: Get the actual capacity of an ArrayList using Reflection
     * 
     * WHY WE NEED THIS:
     * - ArrayList doesn't have a capacity() method (unlike Vector which does)
     * - We use Java Reflection to access the private 'elementData' field
     * - The length of elementData is the actual capacity
     * 
     * HOW IT WORKS:
     * 1. Get the Class object of ArrayList
     * 2. Access the private field named "elementData" 
     * 3. Make it accessible (bypass private restriction)
     * 4. Get the field value from the given list object
     * 5. Cast to Object array and return its length
     */
    private static int getCapacity(ArrayList<?> list) {
        try {
            // Step 1: Get the Class type of ArrayList
            java.lang.reflect.Field field = ArrayList.class.getDeclaredField("elementData");
            
            // Step 2 & 3: Access private field by setting accessible to true
            field.setAccessible(true);
            
            // Step 4: Get the actual internal array from this list object
            Object[] elementData = (Object[]) field.get(list);
            
            // Step 5: Return the length of the internal array (this is the capacity)
            return elementData.length;
        } catch (Exception e) {
            // If reflection fails for any reason, return -1 to indicate error
            return -1;
        }
    }
}


class ArrayListInterface2{
    static void main() {
        List<String> list = new ArrayList<>();
        System.out.println(list.getClass().getName());

        List<String> list1 = Arrays.asList("Hello", "World", "Java", "Python", "Super");
        System.out.println(list1.getClass().getName());
       // list1.add("Sample"); // We can't add elements to a fixed-size list created by Arrays.asList()
                            // because it returns a fixed-size list backed by the original array.
                            // This means that while you can modify existing elements,
                            // you cannot add or remove elements from this list.
        list1.set(2, "Sample");
        System.out.println(list1);

        String[] arr = {"Mango", "Apple", "Banana", "Orange", "Pineapple", "Grapes"};
        List<String> list2 = Arrays.asList(arr); // Here we are creating a list from an array.
        System.out.println(">" + list2 + "<" + " & we are adding a new element to the list");
        System.out.println(list2.getClass().getName());

        //Now how to make this Arrays.asList() list modifiable?
        List<String> list21 = new ArrayList<>(list2);
        list21.add("Strawberry");
        list21.set(2, "Kiwi");
        System.out.println(list21);
        /* How this modification is done?
        * 1. We are creating a new ArrayList object from the original list2.
        * 2. We add a new element to the new list.
        * 3. We modify the element at index 2 in the new list.
        * 4. We replace the original list2 with the new list21.
        *     So in  List<String> list21 = new ArrayList<>("we can provide Capacity and Collection here... ");
        *
        *  */



        List<Integer> list3 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); // This creates an unmodifiable list from a variable-length arguments list.
        System.out.println(list3.getClass().getName());
//        list3.add(11);    // We can't add elements to an unmodifiable list created by List.of() because it returns an immutable list.
                            // This means that once the list is created, you cannot modify it in any way, including adding or removing elements.
 //       list3.set(2, 12);// We can't modify elements in an unmodifiable list because it returns an immutable list.'
        System.out.println(list3);


        List<Integer> list4 = new ArrayList<>();
        list4.add(1);
        list4.add(2);
        list4.add(3);
        list4.add(4);
        list4.add(5);

        List<Integer> list5 = Arrays.asList(6, 7, 8, 9, 10); // This list is unmutable.
        System.out.println("This is the list4: " + list4);
        System.out.println("This is the list5: " + list5);

        list4.addAll(list5); // We can add all elements of list5 to list4 because list4 is modifiable, but list5 is not modifiable.
        System.out.println("Here we are using the list4.addAll() method: " + list4);

        // Now we will talk about calling different remove methods( for index and value)
        System.out.println("\n========== DIFFERENT WAYS TO REMOVE ELEMENTS FROM AN ARRAYLIST ==========\n");
        List<Integer> list6 = new ArrayList<>();
        list6.add(1);
        list6.add(2);
        list6.add(3);
        list6.add(4);
        list6.add(5);
        list6.add(5);
        list6.add(6);
        list6.add(7);
        list6.add(8);
        list6.add(8);
        list6.add(9);
        list6.add(9);
        list6.add(10);
        list6.add(11);
        list6.add(11);
        System.out.println("Original list: " + list6);

        list6.remove(2); // This removes the element at index 2 (which is 3)
        System.out.println("After removing element at index 2: " + list6);

        // But if we want to remove the element with value 4, we can use the removeIf() method.
        list6.removeIf(e -> e == 4); // This removes all elements with value 4.
        System.out.println("After removing all elements with value 4: " + list6);

        // Also, instead of removeIf we can use this method:
        list6.remove(Integer.valueOf(5)); // This removes the first occurrence of the element with value 5.
        System.out.println("After removing the first occurrence of element with value 5: " + list6);

        // If we have duplicate elements in the list, and if we use remove method then it will remove only the first occurrence.
        list6.remove(Integer.valueOf(9)); // This removes the first occurrence of the element with value 9.
        System.out.println("After removing the first occurrence of element with value 9: " + list6);

        // So if we want to remove all duplicate elements, we can use the removeAll() method.
        list6.removeAll(Arrays.asList(5, 6, 7, 8, 9, 10, 11)); // This removes all elements with values 5, 6, 7, 8, 9, 10, and 11.
        System.out.println("After removing all duplicate elements with valuse 5, 6, 7, 8, 9, 10, 11: " + list6);
    }
}


class ListToArray {
    static void main() {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("Java");

        // Convert List to Array
        Object[] array1 = list.toArray();// This returns an Object[] array, which is not type-safe and requires casting.

        String[] array = list.toArray(new String[0]); /* This is a conventional way to convert a List to an array.
                                                       * We pass a new String array with size 0 to the toArray() method.
                                                       * The toArray() method will create a new array of the same type and size as
                                                       * the List and copy all elements into it.
                                                       *This returns a String[] array, which is type-safe.
                                                       * We can also specify the size of the array, but it's not necessary.*/

        System.out.println("Array: " + Arrays.toString(array));

        // Now if we want to sort an list we can use the sort() method.
        System.out.println("\n========== SORTING AN ARRAYLIST ==========\n");
        List<Integer> list1 = new ArrayList<>();
        list1.add(10);
        list1.add(5);
        list1.add(15);
        list1.add(20);
        list1.add(2);
        list1.add(1);
        System.out.println("Original List: " + list1);
    //    Collections.sort(list1); // Here we are using the Collections class to sort the list. This is a static method.
        list1.sort(null); // This sorts the list in ascending order. We can also specify a Comparator to customize the sorting.
        System.out.println("Sorted List: " + list1);


        /**
         * TIME COMPLEXITY:
         * - Adding an element: O(1) on average, but O(n) when resizing occurs.
         * - Removing an element: O(n) because it may require shifting elements.
         * - Accessing an element: O(1) because it's backed by an array.
         * - Sorting the list: O(n log n) due to the underlying sorting algorithm used by Collections.sort() or List.sort().
         * - Iterating through the list: O(n) because it requires traversing the entire list.
         * */

        // Now we are going to talk about comparator
        System.out.println("\n========== COMPARATOR ==========\n");
        List<Integer> list2 = new ArrayList<>();
        list2.add(10);
        list2.add(5);
        list2.add(15);
        list2.add(20);
        list2.add(2);
        list2.add(1);
        System.out.println("Original List: " + list2);


       // Comparator<Integer> comparator = (o1, o2) -> o2 - o1;
        // Throught comparator we can write our custom sorting algorithm.
        list2.sort(new MyComparator()); // As we can see that the Comparator is a functional interface
                        // that defines a single abstract method compare(T o1, T o2) which is used to compare two objects of type T.
                        // so we have to create an implementational class or we can use lambda expression to sort the list in descending order.
        System.out.println("Sorted List in Ascending Order: " + list2);


        ArrayList<String> list3 = new ArrayList<>();
        list3.add("Apple");
        list3.add("Banana");
        list3.add("Orange");
        list3.add("Mango");
        list3.add("Pineapple");
        list3.add("Grapes");
        list3.add("Strawberry");
        list3.add("Kiwi");
        list3.add("Pear");
        list3.add("Peach");

        //list3.sort(Comparator.comparing(String::length)); // This comparator compares the length of each string in the list.
        list3.sort(new StringLengthComparator()); // This comparator compares the length of each string in the list and sorts the list in descending order based on the length of the strings.
        System.out.println("Sorted List by length: " + list3);


        // Now using the Lambda Expression we can sort the list in descending order based on the length of the strings.
        List<String> list4 = new ArrayList<>();
        list4.add("Apple");
        list4.add("Banana");
        list4.add("Orange");
        list4.add("Mango");
        list4.add("Pineapple");
        list4.add("Grapes");
        list4.add("Strawberry");
        list4.add("Kiwi");
        list4.add("Pear");
        list4.add("Peach");
        list4.add("Pie");
        list4.sort((s1, s2) -> s2.length() - s1.length()); // This comparator compares the length of each string in the list and
                                                                        // sorts the list in descending order based on the length of the strings.
        System.out.println("Sorted List by length using Lambda Expression: " + list4);

    }

    // For learning comparator we can use this class:
    /*
    * This Comparator works in such a way that it compares elements
    *  1. If o1 is greater than o2, it returns a positive value.
    * 2.  If o1 is equal to o2, it returns 0.
    * 3.  If o1 is less than o2, it returns a negative value.
    * So, when we use this comparator to sort a list, it will sort the list in ascending order because it returns a positive value when o1 is greater than o2.
    * and this compare method use this positive and negative value to sort the list.
    * */
    static class  MyComparator implements Comparator<Integer>{

        @Override
        public int compare(Integer o1, Integer o2) {
//            return o1 - o2; // This comparator is used to sort the list in ascending order.
            return o2 - o1; // This comparator is used to sort the list in descending order.

        }
    }

    static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }
}



class Student{
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }


}


 class ArrayListComparatorPractice{
     static void main() {
         Scanner sc = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
//        System.out.println("Enter the number of students: ");
//        int n = sc.nextInt();
//        System.out.println("Enter the name and gpa of " + n + " students: ");
//        for (int i = 0; i < n; i++) {
//            System.out.println("Enter the name and gpa of student " + (i + 1) + ": ");
//            String name = sc.next();
//            double gpa = sc.nextDouble();
//            students.add(new Student(name, gpa));
//        }
         students.add(new Student("John", 3.5));
         students.add(new Student("Alice", 4.0));
         students.add(new Student("Bob", 3.0));
         students.add(new Student("Eve", 2.5));
         students.add(new Student("Charlie", 3.2));
         students.add(new Student("David", 3.8));
         students.add(new Student("Frank", 3.0));
         students.add(new Student("Grace", 3.5));
         students.add(new Student("Henry", 3.2));
         students.add(new Student("Ivy", 3.7));
         students.add(new Student("Backy", 3.3));
         students.add(new Student("Any", 3.9));
         students.add(new Student("Billy", 3.0));
         students.add(new Student("Bany", 3.0));

        System.out.println("Here is the list of students: ");
        for (Student student : students) {
            System.out.println(student.getName() + " " + student.getGpa());
        }
            // Now we will sort the list of students based on their gpa in descending order.
//            students.sort((s1, s2) ->
//                    Double.compare(s2.getGpa(), s1.getGpa())); // This comparator compares the gpa of each student in the list and
//                                                                // sorts the list in descending order based on the gpa of the students.

//         students.sort((s1, s2) -> {
//             if (s1.getGpa() > s2.getGpa()) {
//                 return -1; // s1 should come before s2
//             } else if (s1.getGpa() < s2.getGpa()) {
//                 return 1; // s1 should come after s2
//             } else {
//                 return 0; // they are equal
//             }
//         });

//         students.sort((o1, o2) -> {
//             if(o1.getGpa() -o2.getGpa() > 0){
//                 return 1;
//             } else if(o1.getGpa() -o2.getGpa() < 0){
//                 return -1;
//             }else{
//                 return  o1.getName().compareTo(o2.getName());
//                 // If the gpa is same then we will sort the students based on their names in ascending order.
//             }
//         });

         Comparator<Student> studentComparator = Comparator.comparing(Student::getGpa).reversed().thenComparing(Student::getName);
         students.sort(studentComparator);

         System.out.println("\nHere is the list of students sorted by gpa in ascending order: ");
         for (Student student : students) {
             System.out.println(student.getName() + " - " + student.getGpa());
         }

     }
 }