package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Lecture2.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
            /*
               LRU Cache (Least Recently Used Cache) is a data structure that stores a fixed number of items
               and evicts the least recently used item when the cache reaches its capacity.
               It is commonly implemented using a combination of a HashMap and a Doubly Linked List.

               The HashMap provides O(1) time complexity for accessing items,
               while the Doubly Linked List allows for efficient insertion and deletion of nodes to maintain the order of usage.

               Here's a simple implementation of an LRU Cache in Java:
           */

    // Now what we want in this class, we want it store only 5 items.
    // and if new item is added then it should remove the least recently used item.

    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // This method is called after inserting a new entry into the map.
        // It checks if the size of the map exceeds the specified capacity.
        // If so, it removes the least recently used entry.
        return size() > capacity;
    }

    static void main(String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(5);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        cache.put("D", 4);
        cache.put("E", 5);
        cache.put("F", 6);
        cache.put("G", 7);
        cache.put("H", 8);
        cache.put("I", 9);
        cache.put("J", 10);

        System.out.println("Cache: " + cache);
    }
}
