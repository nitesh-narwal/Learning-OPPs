package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.ImmutableMap;

import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMapDemo {
    // ImmutableMap is a type of Map that cannot be modified after it has been created.
    // It is a part of the Guava library, which is a set of core libraries for Java developed by Google.

    // ImmutableMap provides several benefits, including thread safety, improved performance,
    // and reduced memory usage compared to mutable maps. Since the contents of an ImmutableMap cannot be changed,
    // it can be safely shared across multiple threads without the need for synchronization.

    // To create an ImmutableMap, you can use the ImmutableMap.
    // Builder class or the of() method provided by the ImmutableMap class.
    // Once an ImmutableMap is created, any attempt to modify it will result in an UnsupportedOperationException.

    static void main(String[] args) {
        // Now think we can modify the map like...
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        // Now let's try to make it unmodifiable...'
        Map<String, Integer> unmodifiableMap = Collections.unmodifiableMap(map);
        // This will return an unmodifiable view of the specified map, but it does not create a new map.
        // So if we modify the original map, the changes will be reflected in the unmodifiable view as well.

        // now if i try to modify this unmodifiable map... we get an exception.
        try{
        unmodifiableMap.put("D", 4);
        // This will throw UnsupportedOperationException
        // because the unmodifiableMap is just a view of the original map and does not create a new map.
        }catch (UnsupportedOperationException e){
            System.out.println("Cannot modify an unmodifiableMap");
        }

        // So for that we introduced Map.of() method in Java 9.
        // Limitation: it can only create 10 entries.
        Map<String, Integer> map3 = Map.of("Subham", 91, "Akshit", 82, "Komal", 93);
        System.out.println(map3);
        try {
            map3.put("Subham", 90);
            // This will throw UnsupportedOperationException because
            // the map created by Map.of() is immutable and cannot be modified after it has been created.
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify a Map created by Map.of()");
        }

        // Now to remove the limitation of 10 entries we use Map.ofEntries() method.
        Map<String, Integer> ofEntries = Map.ofEntries(
                Map.entry("Subham", 91), Map.entry("Akshit", 82), Map.entry("Komal", 93),
                Map.entry("Rahul", 94), Map.entry("Rohit", 95), Map.entry("Satyarth", 96),
                Map.entry("Shivam", 97), Map.entry("Satyam", 98));
        System.out.println(ofEntries);
        System.out.println(ofEntries.get("Subham"));
        try {
            ofEntries.put("Subham", 90);
            ofEntries.put("Saksham", 65);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify a Map created by Map.ofEntries()");
        }


        // Example usage:
//        ImmutableMap<String, Integer> immutableMap = new ImmutableMap.Builder<String, Integer>()
//                .put("A", 1)
//                .put("B", 2)
//                .put("C", 3)
//                .build();
//
//        System.out.println(immutableMap);
//        System.out.println(immutableMap.get("A"));
//        System.out.println(immutableMap.get("D"));
//        System.out.println(immutableMap.size());
//        System.out.println(immutableMap.isEmpty());
//        System.out.println(immutableMap.containsKey("A"));
//        System.out.println(immutableMap.containsValue(1));
//        try {
//            immutableMap.put("D", 4); // This will throw UnsupportedOperationException
//        } catch (UnsupportedOperationException e) {
//            System.out.println("Cannot modify an ImmutableMap");
//        }
    }
}
