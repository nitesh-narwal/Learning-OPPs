package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.HashMap_Lecture.concurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    static void main(String[] args) {
            // ConcurrentHashMap is a thread-safe variant of HashMap
            // that allows concurrent read and write operations without the need for external synchronization.
            // It achieves this by using a combination of locking and non-blocking algorithms to manage concurrent access to the map.
            // ConcurrentHashMap is designed to provide high concurrency and scalability,
            // making it suitable for use in multi-threaded environments
            // where multiple threads may be accessing and modifying the map simultaneously.
            // It is part of the java.util.concurrent package and was introduced in Java 5.

            /*
            * Java 7 ---> segment based locking --> 16 segments by default ---> smaller hashmaps ---> faster.
            * Only the segments being written to or read form is locked.
            * read: do not required locking unless their is a write operation happening on the same segment.
            * write: required locking.
             *
             * Java 8 --> no segments
             *        --> CAS(Compare-And-Swap) approach --> no locking required except resizing or collision.
             * read: do not required locking unless their is a write operation happening on the same bucket.
             * write: required locking.
             * Suppose the size of the bucket is full, then new bucket will be created(this comes under resizing) and think
             * if 2 threads try to access that bucket simultaneously, then one of the thread will get the lock and will update the value.
             * so here the locking gets important.
             * for eg:-
             *   Thread A last saw: x = 45
             *   Thread A works : x to 50
             *   If x is still 45, then update x to 50 else then don't change and retry.
             *
             * If the CAS operations continue to fail, then the thread will wait for a short time and then retry.
             *
            *
            * MAP --> SORTED --> THREAD SAFE --> concurrentSkipListMap
            */
             /** But first let's talk about SkipList:
                 *  It is a probabilistic data structure that allows for fast search, insertion, and deletion operations.
                 *  It is similar to the sorted linked list but with the multiple layers that "skip" over portion of th list to provide faster access to the elements.
                * */
             ConcurrentHashMap<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
             concurrentHashMap.put(1, "Subham");
             concurrentHashMap.put(2, "Rohit");
             concurrentHashMap.put(3, "Rahul");
             concurrentHashMap.put(4, "Rahul");
             concurrentHashMap.put(5, "Komal");
             System.out.println(concurrentHashMap);
             System.out.println("The size of the concurrentHashMap is: " + concurrentHashMap.size());
             System.out.println("The value of key 4 is: " + concurrentHashMap.get(4));
             System.out.println("hashCode: " + concurrentHashMap.hashCode());
             System.out.println("The value of key 6 is: " + concurrentHashMap.get(6));
             System.out.println("The value of key 4 is: " + concurrentHashMap.containsKey(4));
             System.out.println("The value of key 4 is: " + concurrentHashMap.containsValue("Rahul"));
             System.out.println("The value of key 4 is: " + concurrentHashMap.isEmpty());
             System.out.println("The value of key 4 is: " + concurrentHashMap.remove(4));
             System.out.println(concurrentHashMap);

              // ConcurrentHashMap allows null values but does not allow null keys.
              try {
                  System.out.println("Trying to put null key or value: " + concurrentHashMap.put(null, "Rohit")); // null key is not allowed.
              }catch (Exception e) {
                  System.out.println("Exception: " + e.getMessage());
              }
              try {
                  System.out.println("Trying to put null key or value: " + concurrentHashMap.put(1, null)); // null value is allowed.
              }catch (Exception e) {
                  System.out.println("Exception: " + e.getMessage());
              }
              System.out.println(concurrentHashMap);
    }
}
