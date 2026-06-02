package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Lecture3.sortedMap;

import java.util.SortedMap;
import java.util.TreeMap;

public class SortedMapDemo {
    static void main(String[] args) {
        /*
        * Sortedmap is an interdace that extends Map and gurantees that the entries are sorted based on their keys,
        * either in their natural ordering or by a specified comparator.
        * It provides additional methods to navigate and manipulate the sorted entries,
        * such as firstKey(), lastKey(), headMap(), tailMap(), and subMap().
        * TreeMap is a common implementation of SortedMap that uses a red-black tree to maintain the sorted order of keys.
        */
        SortedMap<Integer, String> sortedMap = new TreeMap<>(); // and it's implementation class is TreeMap.
        sortedMap.put(3, "Apple");
        sortedMap.put(1, "Banana");
        sortedMap.put(2, "Orange");
        sortedMap.put(4, "Mango");
        sortedMap.put(9, "Pineapple");
        sortedMap.put(5, "Grapes");
        sortedMap.put(7, "Strawberry");

        System.out.println(sortedMap); // TreeMap is a sorted map.

        // Sorted map extends Map interface, so we can use all the methods of Map interface.
        // So why we are using SortedMap?
        // Because it provides additional methods to navigate and manipulate the sorted entries.

        System.out.println("First key: " + sortedMap.firstKey()); // it returns the first key in the sorted map.
        System.out.println("Last key: " + sortedMap.lastKey()); // it returns the last key in the sorted map.
        System.out.println("Head map: " + sortedMap.headMap(4)); // it returns a view of the portion of this map whose keys are strictly less than toKey.
        System.out.println("Tail map: " + sortedMap.tailMap(4)); // it returns a view of the portion of this map whose keys are greater than or equal to fromKey.
        System.out.println("Sub map: " + sortedMap.subMap(4, 9)); // it returns a view of the portion of this map whose keys range from fromKey, inclusive, to toKey, exclusive.
        //System.out.println("Sub map: " + sortedMap.subMap(4, true, 9, true)); // it returns a view of the portion of this map whose keys range from fromKey to toKey, inclusive.


        // We can do sorting through comparator.
        sortedMap = new TreeMap<>((o1, o2) -> o2 - o1 ); // it will sort the map in descending order. "(o1, o2) -> o2.compareTo(o1)" is the comparator.
        // TreeMap uses a red-black tree to maintain the sorted order of keys. So the time complexity of insertion and retrieval is O(log n).
        sortedMap.put(3, "Apple");
        sortedMap.put(1, "Banana");
        sortedMap.put(2, "Orange");
        sortedMap.put(4, "Mango");
        sortedMap.put(9, "Pineapple");
        sortedMap.put(5, "Grapes");
        sortedMap.put(7, "Strawberry");
        System.out.println(sortedMap);

        sortedMap.get(1); // O(log n)
        sortedMap.containsKey(1); // O(log n)
        sortedMap.containsValue("Apple"); // O(n) because it has to iterate through all the values to find the value.
        sortedMap.size(); // O(1)

        // Now we are going to talk about NavigableMap.
    }
}
