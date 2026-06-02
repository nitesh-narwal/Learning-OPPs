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

        System.out.println(  "Identity hashcode of key1 and key2: They are not same because they are different objects in memory.");
        System.out.println(System.identityHashCode(key1));
        System.out.println(System.identityHashCode(key2));

        System.out.println("hashcode of key1 and key2: They are same because they have same content because they checks the content of the object.");
        System.out.println(key1.hashCode());
        System.out.println(key2.hashCode());

        System.out.println(key1.equals(key2));
        System.out.println(map);

        // But in IdentityHashMap, it will consider key1 and key2 as different keys because they are different objects in memory.
         Map<String, Integer> identityMap = new java.util.IdentityHashMap<>();
         // In IdentityHashMap, if in my class hashcode is present or not but in identity hashmap object hashcode is taken into consideration.
        // The thingh which is comes into consideration are Identityhashcode and == operator.
        identityMap.put(key1, 1); // key : 1
        identityMap.put(key2, 2); // key : 2
        System.out.println(key1.equals(key2)); //compares CONTENT (value equality)
        System.out.println(key1 == key2);      //compares REFERENCE (object equality)
        System.out.println(identityMap);
    }
}
