package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║                    HASHMAP DEPTH — MAIN DEMO                       ║
 * ║              Run this to see all concepts in action                ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * This file is your ENTRY POINT and QUICK REFERENCE.
 * It demonstrates the most important HashMap operations in one place.
 *
 * ─── LEARNING PATH ───────────────────────────────────────────────────
 *
 *  Step 1 → Step1_WhatIsHashMap.java
 *            What is HashMap, key-value concept, basic operations
 *
 *  Step 2 → Step2_InternalWorking.java
 *            Hashing, buckets, collisions, hashCode + equals contract
 *
 *  Step 3 → Step3_IterationAndViews.java
 *            entrySet, keySet, values, forEach, ConcurrentModificationException
 *
 *  Step 4 → Step4_UsefulMethods.java
 *            getOrDefault, putIfAbsent, computeIfAbsent, merge, compute, replace
 *
 *  Step 5 → Step5_Variants.java
 *            LinkedHashMap, TreeMap, EnumMap — when to use which
 *
 *  Step 6 → Step6_CommonMistakes.java
 *            Mutable keys, null traps, autoboxing, thread safety, order assumption
 *
 *  Step 7 → Step7_RealWorldPatterns.java
 *            Two-Sum, grouping, memoization, graph, inverted index, nested maps
 *
 *  Step 8 → Step8_AdvancedAndPerformance.java
 *            ConcurrentHashMap, capacity tuning, streams, sorting, merging maps
 *
 * ─── QUICK CHEAT SHEET ───────────────────────────────────────────────
 *
 *  CREATION:
 *    Map<K,V> map = new HashMap<>();                    // general use
 *    Map<K,V> map = new LinkedHashMap<>();              // insertion order
 *    Map<K,V> map = new TreeMap<>();                    // sorted keys
 *    Map<K,V> map = new EnumMap<>(MyEnum.class);        // enum keys
 *    Map<K,V> map = new ConcurrentHashMap<>();          // thread-safe
 *    Map<K,V> map = Map.of(k1,v1, k2,v2);              // immutable
 *
 *  BASIC OPS:
 *    map.put(key, value)                                // add/overwrite
 *    map.get(key)                                       // get (may return null)
 *    map.getOrDefault(key, defaultVal)                  // safe get
 *    map.remove(key)                                    // delete
 *    map.containsKey(key)                               // check existence
 *    map.size()                                         // count entries
 *    map.isEmpty()                                      // is empty?
 *    map.clear()                                        // remove all
 *
 *  SMART OPS:
 *    map.putIfAbsent(key, val)                          // add only if absent
 *    map.computeIfAbsent(key, k -> newVal)              // compute if absent
 *    map.merge(key, val, (old, newV) -> combined)       // combine values
 *    map.compute(key, (k, old) -> newVal)               // update by function
 *    map.replace(key, newVal)                           // update if exists
 *    map.replaceAll((k, v) -> newV)                     // bulk update
 *
 *  ITERATION:
 *    for (Map.Entry<K,V> e : map.entrySet()) { }        // key + value
 *    for (K key : map.keySet()) { }                     // keys only
 *    for (V val : map.values()) { }                     // values only
 *    map.forEach((k, v) -> { })                         // lambda style
 *
 *  SAFE REMOVAL DURING ITERATION:
 *    map.entrySet().removeIf(e -> condition)            // cleanest
 *    Iterator<...> it = map.entrySet().iterator();
 *    while (it.hasNext()) { if (cond) it.remove(); }   // explicit
 *
 * ─── THE GOLDEN RULES ────────────────────────────────────────────────
 *
 *  1. Declare as Map<K,V>, not HashMap<K,V>
 *  2. Use immutable keys (String, Integer, enum, record)
 *  3. Override BOTH hashCode() AND equals() for custom keys
 *  4. Always handle null from get() — use getOrDefault()
 *  5. Never rely on HashMap iteration order
 *  6. Use ConcurrentHashMap for multi-threaded code
 *  7. Never modify map while iterating with for-each
 *  8. Pre-size when you know the expected entry count
 */
public class MainDemo {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        HASHMAP COMPLETE DEMO             ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        // ─── 1. Basic Operations ─────────────────────────────────────────
        System.out.println("── 1. Basic Operations ──");
        Map<String, Integer> ages = new HashMap<>();
        ages.put("Alice", 25);
        ages.put("Bob",   30);
        ages.put("Charlie", 22);

        System.out.println("Alice's age: " + ages.get("Alice"));
        System.out.println("Unknown age: " + ages.getOrDefault("Zara", -1));
        System.out.println("Has Bob: "     + ages.containsKey("Bob"));
        System.out.println("Size: "        + ages.size());

        // ─── 2. Smart Methods ────────────────────────────────────────────
        System.out.println("\n── 2. Smart Methods ──");

        // Word frequency counter — the most common HashMap pattern
        String[] words = {"java", "is", "great", "java", "is", "fun", "java"};
        Map<String, Integer> freq = new HashMap<>();
        for (String w : words) {
            freq.merge(w, 1, Integer::sum);
        }
        System.out.println("Word frequency: " + freq);

        // Grouping — second most common pattern
        Map<Integer, List<String>> byLength = new HashMap<>();
        for (String w : words) {
            byLength.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
        }
        System.out.println("By length: " + byLength);

        // ─── 3. Iteration ────────────────────────────────────────────────
        System.out.println("\n── 3. Iteration ──");
        System.out.println("entrySet forEach:");
        ages.forEach((name, age) -> System.out.println("  " + name + " is " + age));

        // ─── 4. Variants ─────────────────────────────────────────────────
        System.out.println("\n── 4. Variants ──");

        // LinkedHashMap — insertion order
        Map<String, Integer> linked = new LinkedHashMap<>();
        linked.put("C", 3); linked.put("A", 1); linked.put("B", 2);
        System.out.println("LinkedHashMap: " + linked.keySet()); // [C, A, B]

        // TreeMap — sorted order
        Map<String, Integer> tree = new TreeMap<>();
        tree.put("C", 3); tree.put("A", 1); tree.put("B", 2);
        System.out.println("TreeMap:       " + tree.keySet()); // [A, B, C]

        // ─── 5. Immutable Map ────────────────────────────────────────────
        System.out.println("\n── 5. Immutable Map ──");
        Map<String, String> constants = Map.of(
            "PI",    "3.14159",
            "E",     "2.71828",
            "SQRT2", "1.41421"
        );
        System.out.println("PI = " + constants.get("PI"));

        // ─── 6. Two-Sum Demo ─────────────────────────────────────────────
        System.out.println("\n── 6. Two-Sum (O(n) with HashMap) ──");
        int[] nums = {3, 5, 2, 8, 1, 7};
        int target = 10;
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                System.out.println("Found: nums[" + seen.get(complement) + "]=" +
                    complement + " + nums[" + i + "]=" + nums[i] + " = " + target);
                break;
            }
            seen.put(nums[i], i);
        }

        System.out.println("\n✓ All demos complete. Study each Step file for deep dives.");
    }
}
