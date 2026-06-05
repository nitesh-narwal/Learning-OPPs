package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Set_Lecture.sets;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class SetsOverview {

    // Set is a collection that does not allow duplicate elements.
    // faster operations
    // Map ---> HashMap, LinkedHashMap, TreeMap, EnumMap
    // Set ---> HashSet, LinkedHashSet, TreeSet, EnumSet
    static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set1.add(i);
        }
        Map<Integer, Integer> map = new HashMap<>();
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet(); // Now as we can see the entries in the map are unique so it is a part of set.
        map.entrySet().forEach(entry -> System.out.println(entry)); // we can also do this. 1st way is better.
        for (Map.Entry<Integer, Integer> entry : entries) {
            System.out.println(entry);
        }

        System.out.println(set1.contains(5));


        System.out.println(set1);

        // we can also do this and unlock more new features.
        NavigableSet<Integer> set = new TreeSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        System.out.println(set);
        System.out.println(set.subSet(2, 4));
        System.out.println(set.headSet(4));
        System.out.println(set.tailSet(2));
        System.out.println(set.first());
        System.out.println(set.last());
        System.out.println(set.lower(3));
        System.out.println(set.higher(3));
        System.out.println(set.floor(3));
        System.out.println(set.ceiling(3));
        System.out.println(set.pollFirst());
        System.out.println(set.pollLast());
        System.out.println(set);

        // making a set thread safe.
        Set<Integer> set2 = Collections.synchronizedSet(set); // This will make the set thread safe.
        // It is an external synchronization mechanism that ensures that only one thread can access the set at a time.
        // It wraps the set in a synchronized block, which prevents concurrent access by multiple threads.
        // It allows to all the methods of the set to be called by only one thread at a time.
        set2.add(6);

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                set2.add(i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                set2.add(i);
            }
        });

        thread.start();
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Size of set: " + set2.size() + ""); //
        // Without synchronizedSet, concurrent modifications
        //to HashSet can lead to unpredictable behavior,
        //including incorrect size, data corruption,
        //or exceptions.

        /** The Above Collections.synchronizedSet() method is not efficient as it locks the entire set for every operation,
         which can lead to performance issues in a multi-threaded environment.
         Instead of it we use ConcurrentSkipListSet which is a concurrent version of HashSet.*/

        Set<Integer> set3 = new ConcurrentSkipListSet<>(); // It has a built-in thread-safe implementation.

        // Now another not recommended way is...
               /* Set<Integer> syncSet = Collections.synchronizedSet(new TreeSet<>());

                // now here for a loop we have to use synchronized block again....
                synchronized (syncSet) {
                    for (int syncSet2 : syncSet) {
                        System.out.println(syncSet2);
                    }
                }
              */

        // Just like Map we also have unmodifiable set.

        Set<Integer> uniqueNumbers = Set.of(1, 2, 3, 4, 5, 66, 33, 21, 4456, 1234);
        // This is an unmodifiable set. It is immutable and does not allow null values. It is a factory method for creating unmodifiable sets.
        // Unlike Map where we can only put 10 entries in it, we can put unlimited entries in unmodifiable set.


        Set<Integer> unmodifiableSet = Collections.unmodifiableSet(set3);
    }
}
