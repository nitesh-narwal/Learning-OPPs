package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Lecture1.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class VectorInterface {
    /*
    * A Vector in java is a part of the java.util package and is one of the legecy classes in Java that implements the List interface.
    * It was introduced in Java 1.0 before Collections-Framework was introduced and is synchronized, making it thread-safe.
    * Now it is a part of the Collections-Framework.
    * However, due to it's synchronization overhead, it's generally recommended to use other modern alternatives like ArrayList in single-threaded scenarios.
    * Despite this Vector is still useful in some situations particularly in multi-threaded environments where thread safety is a concern.
    *
    *
    * KEY FEATURES OF VECTOR:
    * 1. Dynamic Array: Like ArrayList, Vector is a dynamic array that can grow automatically when more elements
    *                   are added then it current capacity or shrink in size as needed.
    * 2. Synchronized: All the methods in Vector are synchronized to ensure thread safety. Which means multiple threads
    *                   can work on vector without the risk of corrupting the data. However, this can introduce performance overhead.
    *                   in Single-threaded environment.
    * 3. Legacy Class: Vector was part of Java Original release and is considered legacy class. It's generally recommended to use
    *                   on Single-threaded environment due to performance considerations.
    * 4. Resizing Mechanism: Vector uses a resizing mechanism to dynamically adjust its capacity when needed.
    * 5. Random Access: Vector supports random access to elements, making it suitable for applications that require fast retrieval and modification.
    *
    *
    * METHODS IN VECTOR:
    * 1. add(E e) : Adds the specified element to the end of this vector.
    * 2. add(int index, E element) : Inserts the specified element at the specified position in this vector.
    * 3. get(int index) : Returns the element at the specified position in this vector.
    * 4. set(int index, E element) : Replaces the element at the specified position in this vector with the specified element.
    * 5. remove(int index) : Removes the element at the specified position in this vector.
    * 6. remove(Object o) : Removes the first occurrence of the specified element from this vector, if it is present.
    * 7. removeAll(Collection<?> c) : Removes from this vector all of its elements that are contained in the specified collection.
    * 8. retainAll(Collection<?> c) : Removes from this vector all of its elements that are not contained in the specified collection.
    * 9. clear() : Removes all of the elements from this vector.
    * 10. size() : Returns the number of elements in this vector.
    * 11. isEmpty() : Returns true if this vector contains no elements.
    * 12. contains(Object o) : Returns true if this vector contains the specified element.
    * 13. indexOf(Object o) : Returns the index of the first occurrence of the specified element in this vector, or -1 if this vector does not contain the element.
    * 14. lastIndexOf(Object o) : Returns the index of the last occurrence of the specified element in this vector, or -1 if this vector does not contain the element.
    * 15. iterator() : Returns an iterator over the elements in this vector.
    * 16. toArray() : Returns an array containing all of the elements in this vector.
    * 17. toArray(T[] a) : Returns an array containing all of the elements in this vector; the runtime type of the returned array is that of the specified array.
    * 18. clone() : Returns a shallow copy of this vector.
    * */

    static void main(String[] args) {

        /**
         * Vector() : Create a vector with default initial capacity of 10.
         * Vector(int initialCapacity) : Create a vector with the specified initial capacity.
         * Vector(int initialCapacity, int capacityIncrement) : Create a vector with the specified initial capacity and capacity increment(
         *                                                      how much a vector should grow when it's full).
         * Vector(Collection<? extends E> c) : Create a vector that contains the elements of the specified collection.
         * */

        // The capacity of vector increases automatically with a rate of 100% when the current capacity is exceeded.
        // For example, if the initial capacity is 10 and you add an 11th element, the capacity will increase to 20.
        Vector<Integer> vector = new Vector<>();
        // In vector we have a method to check the capacity of the vector.
        System.out.println("The capacity of the vector is: " + vector.capacity());

        // now if the vector exceeds it's capacity increase with rate of 2X.
        for (int i = 0; i < 11; i++) {  // 11th element will increase the capacity to 20, in ArrayList the rate was 1.5X
            vector.add(i);
        }
        System.out.println("vector elements are: " + vector);
        System.out.println("The capacity of the vector after adding 11 elements is: " + vector.capacity());
        System.out.println("The elements in the vector are: " + vector);

        System.out.println("<-------------------------------------------------------------->");
        // In vextor we can decide the initial capacity and the capacity increment.
        Vector<Integer> vector2 = new Vector<>(5, 3); // 5th element will increase the capacity to 8
        vector2.add(10);
        vector2.add(11);
        vector2.add(12);
        vector2.add(13);
        vector2.add(14);
        System.out.println("The elements in the vector2 are: " + vector2);
        System.out.println("The capacity of the vector2 is: " + vector2.capacity());
        vector2.add(15);
        System.out.println("The capacity of the vector2 after adding 6th element is: " + vector2.capacity());

        System.out.println("<-------------------------------------------------------------->");

        // We can also convert Collection( ArrayList, LinkedList...) to Vector.
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        Vector<Integer> vector3 = new Vector<>(linkedList);
        System.out.println("The elements in the vector3 are: " + vector3);

        System.out.println("<-------------------------------------------------------------->");

        Vector<Integer> vector4 = new Vector<>(Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        System.out.println("The elements in the vector4 are: " + vector4);

    }

}

class SynchronizedAndPerformance {
    /*
    * Since Vector methods are synchronized, it ensures that only one thread can access the vector at a time.
    * This make it thread-safe but can introduce performance overhead.
    * In single-threaded environments because Synchronization can add locking and unlocking Cost.
    *
    * Since Vector methods are synchronized, they can be slower
    * than non-synchronized alternatives like ArrayList in single-threaded scenarios.
    *  The synchronization overhead can lead to performance degradation,
    *  especially when there are frequent modifications to the vector.
    * In multi-threaded environments where multiple threads are accessing and modifying the same vector,
    *  the synchronization can help prevent data corruption and ensure thread safety.
    *  However, if the vector is accessed by multiple threads but only read operations are performed,
    *  the synchronization can still introduce unnecessary overhead, and in such cases,
    *  using a non-synchronized alternative like ArrayList with external synchronization
    * (e.g., using Collections.synchronizedList) may be more efficient.
    * */

    static void main(String[] args) {

        // why we use vector in multi-threaded environment?
        // 1. because it is thread-safe.
        // 2. because it is faster than ArrayList.
        // 3. because it is more efficient than ArrayList.

        //ArrayList<Integer> list = new ArrayList<>();
        // now if i choose vextor over arraylist
        Vector<Integer> list = new Vector<>();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println(list.size());
    }
}
