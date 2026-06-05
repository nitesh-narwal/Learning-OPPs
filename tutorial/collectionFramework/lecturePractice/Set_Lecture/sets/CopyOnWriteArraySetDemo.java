package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Set_Lecture.sets;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetDemo {

    // CopyOnWriteArraySet is a thread safe variant of HashSet. It is implemented using a CopyOnWriteArrayList.
    // It is a concurrent collection that allows multiple threads to read
    // and write to the set concurrently without the need for external synchronization.
    // It is a good choice for sets that are mostly read and infrequently modified,
    // as it provides better performance for read operations at the cost of higher overhead for write operations.
    static void main(String[] args) {
        // Thread safe.
        // Copy-On_Write Mechanism
        // No Duplicate elements.
        // Iterators do not Reflect Modifications.

        CopyOnWriteArraySet<Integer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        ConcurrentSkipListSet<Integer> concurrentSkipListSet = new ConcurrentSkipListSet<>();

        for (int i = 0; i < 10; i++) {
            copyOnWriteArraySet.add(i);
            concurrentSkipListSet.add(i);
        }

        System.out.println("CopyOnWriteArraySet: " + copyOnWriteArraySet);
        System.out.println("ConcurrentSkipListSet: " + concurrentSkipListSet);

        for(int copy : copyOnWriteArraySet) {
            System.out.println("CopyOnWriteArraySet element: " + copy);

            // Attempting to modify the iterator will not reflect the changes in the set.
            copyOnWriteArraySet.add(12); // This will not be reflected in the iterator, as it is a copy on write mechanism.
        }

        System.out.println("CopyOnWriteArraySet after modification: " + copyOnWriteArraySet);

        System.out.println("ConcurrentSkipListSet: " + concurrentSkipListSet);

        System.out.println("\n Iterating and modifying ConcurrentSkipListSet: ");
        // It is weakly consistent.
        for(int concurrent : concurrentSkipListSet){
            System.out.println("ConcurrentSkipListSet element: " + concurrent);
            // Attempting to modify the iterator will reflect the changes in the set.
            concurrentSkipListSet.add(12);
            // Here we can see that the ConcurrentSkipListSet is modified while iterating
            // but it's not guaranteed that it will always reflect the changes.

            // for example: if it try to add 13 after 12, it may or may not be reflected in the iterator,
            // as it is a concurrent collection and it may be modified by other threads while iterating.

            if (concurrent == 12) {
                concurrentSkipListSet.add(13);
            }
        }
    }
}
