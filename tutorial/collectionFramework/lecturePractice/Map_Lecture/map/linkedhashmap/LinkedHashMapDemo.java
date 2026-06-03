package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Map_Lecture.map.linkedhashmap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {
    static void main(String[] args) {

        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>(); // LinkedHashMap is a type of HashMap that maintains the insertion order of elements.

        HashMap<String, Integer> hashMap = new HashMap<>(); // In HashMap, the order of elements is not guaranteed or maintained.
        hashMap.put("Orange", 10);
        hashMap.put("Apple", 20);
        hashMap.put("Banana", 30);
        hashMap.put("Mango", 40);
        hashMap.put("Pineapple", 50);

        for(Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("<----------- LinkedHashMap( Order is maintained ) -------------->");

        // LinkedHashMap uses double linked list to maintain the insertion order of elements.
        linkedHashMap.put("Orange", 10);
        linkedHashMap.put("Apple", 20);
        linkedHashMap.put("Banana", 30);
        linkedHashMap.put("Mango", 40);
        linkedHashMap.put("Pineapple", 50);
        for(Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


        LinkedHashMap<String, Integer> linkedHashMap1 = new LinkedHashMap<>(16, 0.75f, true);
        // accessOrder = true, it maintains the order of elements
        // based on the access order (most recently accessed elements are moved to the end of the list).
        // Which help us to get the last or least accessed element.
        // for eg: if we have 1000000000 elements in the map, and we want to get the last accessed element,
        // then we can use LinkedHashMap with accessOrder = true.

        linkedHashMap1.put("Cartoon", 10);
        linkedHashMap1.put("Anime", 20);
        linkedHashMap1.put("Movie", 30);
        linkedHashMap1.put("TV Show", 40);
        linkedHashMap1.put("Documentary", 50);
        linkedHashMap1.put("Music", 60);
        linkedHashMap1.put("Book", 70);
        System.out.println("<----------- LinkedHashMap with accessOrder = true"
                                    + " ( Order is maintained based on access order ) -------------->");
        linkedHashMap1.get("Cartoon");
        linkedHashMap1.get("Anime");
        linkedHashMap1.get("Music");
        linkedHashMap1.get("Book");
        linkedHashMap1.get("Movie");
        linkedHashMap1.get("Cartoon");
        linkedHashMap1.get("Anime");
        linkedHashMap1.get("Documentary");
        linkedHashMap1.get("TV Show");

        System.out.println("After accessing some elements, the order of elements in LinkedHashMap with"
                            + " accessOrder = true is changed based on the access order:");
        for(Map.Entry<String, Integer> entry : linkedHashMap1.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        /* This type of thing also use in cache eviction strategy,
        *   because we can't store the entire data into the cache.
        *   we just want to store the recently accessed data into it.
        */

        //CONVERTING HASHMAP INTO LINKEDHASHMAP
        HashMap<String, Integer> hashMap1 = new HashMap<>();
        LinkedHashMap<String, Integer> linkedHashMap2 = new LinkedHashMap<>(hashMap1);

        hashMap1.put("Subham", 90);
        hashMap1.put("Ronak", 78);
        hashMap1.put("Sunil", 92);
        hashMap1.put("Rahul", 95);

        System.out.println("Now we use a method name hashMap1.getorDefault() to get the value of a key or default value"
                            + "if the key is not present in the map it will return the default value:");
        System.out.println(hashMap1.getOrDefault("Subham", 0)); // it will return 90 because the key "Subham" is present in the map and its value is 90.
        System.out.println(hashMap1.getOrDefault("Nitesh", 0)); // it will return 0 because the key "Nitesh" is not present in the map and the default value is 0.

        System.out.println("Now we use a method name hashMap1.putifAbsent()"
                            +"to put the key-value pair into the map if the key is not present in the map:"
                            + hashMap1.putIfAbsent("Subham", 90)); // it will return null because the key "Subham" is already present in the map
        System.out.println(hashMap1.putIfAbsent("Subham", 100)); // it will return 90 because the key "Subham" is already present in the map and
                                                                // it will not put the key-value pair into the map.
        System.out.println(hashMap1.putIfAbsent("Nitesh", 80)); // it will return null because the key "Nitesh" is not present in the map and
                                                                // it will put the key-value pair into the map.
        for(Map.Entry<String, Integer> entry : hashMap1.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue()); // it will print the key-value pairs in the order in which they were inserted.
        }
    }

}
