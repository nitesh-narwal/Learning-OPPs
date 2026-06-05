package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Map_Lecture.HashMap_Lecture;

import java.util.Hashtable;

// We don't use it it's a legecy class and it's synchronized so it's not recommended to use it in modern Java programming.
// Instead, we can use HashMap or ConcurrentHashMap for better performance and thread safety.
public class HashTableDemo {
    static void main(String[] args) {
        Hashtable<Integer, String> hashtable = new Hashtable<>();
        /*
        * 1. It is a synchronized class.
        * 2. It is thread-safe.
        * 3. No null keys or values.
        * 4. Legecy class, ConcurrentHashMap is better.
        * 5. Slower than HashMap.
        * 6. Only LinkedList is used in case of collision.
        * 7. All the methods in Hashtable are synchronized.*/
        hashtable.put(1, "Subham");
        hashtable.put(2, "Rohit");
        hashtable.put(3, "Rahul");
        hashtable.put(4, "Rahul");
        hashtable.put(5, "Komal");
        System.out.println(hashtable);
        System.out.println("The size of the hashtable is: " + hashtable.size());
        System.out.println("The value of key 4 is: " + hashtable.get(4));
        System.out.println("hashCode: " + hashtable.hashCode());
        System.out.println("The value of key 6 is: " + hashtable.get(6));
        System.out.println("The value of key 4 is: " + hashtable.containsKey(4));
        System.out.println("The value of key 4 is: " + hashtable.containsValue("Rahul"));
        System.out.println("The value of key 4 is: " + hashtable.isEmpty());
        System.out.println("The value of key 4 is: " + hashtable.remove(4));
        System.out.println(hashtable);

        // can't put null key or value.
        try {
            System.out.println("Trying to put null key or value: " + hashtable.put(null, "Rohit")); // null key is not allowed.
            System.out.println("Trying to put null key or value: " + hashtable.put(1, null)); // null value is not allowed.
        }catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println(hashtable);

    }
}

class WorkingWithThread{
    static void main(String[] args) {
      //  HashMap<Integer, String> hashMap = new HashMap<>();
        Hashtable<Integer, String> hashMap = new Hashtable<>();  // now we get the correct size.

        Thread t1 = new Thread(() ->
        {
            for (int i = 0; i < 1000; i++) {
                hashMap.put(i, "Thread1");
            }
        });

        Thread t2 = new Thread(() ->
        {
            for (int i = 1000; i < 2000; i++) {
                hashMap.put(i, "Thread2");
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("HashMap size: " + hashMap.size());
    }
}