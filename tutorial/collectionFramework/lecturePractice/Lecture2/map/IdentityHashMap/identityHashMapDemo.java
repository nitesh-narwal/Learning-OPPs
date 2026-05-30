package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Lecture2.map.IdentityHashMap;

import java.util.HashMap;

import java.util.Map;

public class identityHashMapDemo {
    static void main(String[] args) {
        String key1 = new String("key");  // Both key1 and key2 refer to the different address in memory.
        String key2 = new String("key");

        Map<String, Integer> map = new HashMap<>();
        map.put(key1, 1); // key : 1
        map.put(key2, 2); // key : 2
        System.out.println(key1.equals(key2));
        System.out.println(map);

        // But in IdentityHashMap, it will consider key1 and key2 as different keys because they are different objects in memory.
         Map<String, Integer> identityMap = new java.util.IdentityHashMap<>();
         //
        identityMap.put(key1, 1); // key : 1
        identityMap.put(key2, 2); // key : 2
        System.out.println(key1.equals(key2)); //compares CONTENT (value equality)
        System.out.println(key1 == key2);      //compares REFERENCE (object equality)
        System.out.println(identityMap);
    }
}
