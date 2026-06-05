package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Map_Lecture.HashMap_Lecture.navigableMap;

import java.util.NavigableMap;
import java.util.TreeMap;

public class NavigableMapDemo {
    // NavigableMap is a sub-interface of Map that provides additional methods for navigating the map.
    // TreeMap is an implementation of NavigableMap that uses a red-black tree for efficient navigation.
    // If we go inside the TreeMap that it implements NavigableMap, and NavigableMap extends SortedMap,
    // we can see that TreeMap is a SortedMap.

    static void main(String[] args) {
        /**
         * NavigableMap<K,V> : A map that allows efficient navigation of its entries.
         * NavigableMap extends SortedMap, which means it provides methods for navigating the map based on its keys.
         * such as finding the closest matching key or retrieving the map in reverse order.
         */

        // Here in NavigableMap we can find the closest matching key or retrieve the map in reverse order.
        // Instead of SortedMap where we were finding the range of keys.
        NavigableMap<Integer, String> navigableMap = new TreeMap<>(); // TreeMap is an implementation of NavigableMap.
        navigableMap.put(3, "Apple");
        navigableMap.put(1, "Banana");
        navigableMap.put(2, "Orange");
        navigableMap.put(4, "Mango");
        navigableMap.put(9, "Pineapple");
        navigableMap.put(5, "Grapes");
        navigableMap.put(7, "Watermelon");
        navigableMap.put(8, "Strawberry");

        System.out.println(navigableMap);
        // it returns the key-value mapping associated with the least key in this map, or null if the map is empty.
        System.out.println("First entry: " + navigableMap.firstEntry());

        // it returns the key-value mapping associated with the greatest key in this map, or null if the map is empty.
        System.out.println("Last entry: " + navigableMap.lastEntry());

        // it returns the least entry greater than or equal to the given key, or null if there is no such key.
        System.out.println("Ceiling entry for key 6: " + navigableMap.ceilingEntry(6));

        // it returns the greatest entry less than or equal to the given key, or null if there is no such key.
        System.out.println("Floor entry for key 6: " + navigableMap.floorEntry(6));

        // it returns the least entry strictly greater than the given key, or null if there is no such key.
        System.out.println("Higher entry for key 6: " + navigableMap.higherEntry(6));

        // it returns the greatest entry strictly less than the given key, or null if there is no such key.
        System.out.println("Lower entry for key 6: " + navigableMap.lowerEntry(6));

        // it returns a reverse order view of the mappings contained in this map.
        System.out.println("Descending map: " + navigableMap.descendingMap());

        // it returns a view of the portion of this map whose keys range from fromKey, inclusive, to toKey, exclusive.
        navigableMap.subMap(4, 9).forEach((key, value) -> System.out.println("Key: " + key + " Value: " + value));

        System.out.println("Navigable map: " + navigableMap);



    }
}
