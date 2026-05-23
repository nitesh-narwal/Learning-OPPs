package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║     STEP 3: ITERATING OVER A HASHMAP — ALL THE WAYS                ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. entrySet() — iterate keys AND values together (most common)
 *  2. keySet()   — iterate only keys
 *  3. values()   — iterate only values
 *  4. forEach()  — modern lambda style
 *  5. Which approach to use when
 *  6. ConcurrentModificationException — the trap everyone falls into
 */
public class Step3_IterationAndViews {

    public static void main(String[] args) {

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice",  95);
        scores.put("Bob",    82);
        scores.put("Charlie",78);
        scores.put("Diana",  91);
        scores.put("Eve",    88);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 1: entrySet() — THE MOST COMMON AND EFFICIENT WAY
         * ─────────────────────────────────────────────────────────────────
         *
         * entrySet() returns a Set of Map.Entry objects.
         * Each Map.Entry holds ONE key-value pair.
         *
         * Use this when you need BOTH the key and the value.
         * This is the PREFERRED way in professional code.
         */

        System.out.println("=== entrySet() iteration ===");
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            String name  = entry.getKey();
            int    score = entry.getValue();
            System.out.println(name + " → " + score);
        }

        /*
         * WHY entrySet() is better than keySet() when you need both:
         *
         * ✗ INEFFICIENT (two lookups per iteration):
         *   for (String key : scores.keySet()) {
         *       int val = scores.get(key);  // extra O(1) lookup each time
         *   }
         *
         * ✓ EFFICIENT (one lookup per iteration):
         *   for (Map.Entry<String, Integer> entry : scores.entrySet()) {
         *       // entry already has both key and value — no extra lookup
         *   }
         *
         * For small maps the difference is tiny.
         * For large maps in tight loops, entrySet() is noticeably faster.
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 2: keySet() — WHEN YOU ONLY NEED KEYS
         * ─────────────────────────────────────────────────────────────────
         *
         * keySet() returns a Set<K> of all keys.
         * Use this when you only care about the keys, not the values.
         */

        System.out.println("\n=== keySet() iteration ===");
        Set<String> names = scores.keySet();
        for (String name : names) {
            System.out.println("Student: " + name);
        }

        // Common use case: check if a key exists before processing
        if (scores.keySet().contains("Alice")) {
            System.out.println("Alice is in the map");
        }
        // But containsKey() is cleaner for this:
        if (scores.containsKey("Alice")) {
            System.out.println("Alice found via containsKey");
        }

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 3: values() — WHEN YOU ONLY NEED VALUES
         * ─────────────────────────────────────────────────────────────────
         *
         * values() returns a Collection<V> of all values.
         * Note: it's a Collection, NOT a Set — values can be duplicates.
         */

        System.out.println("\n=== values() iteration ===");
        Collection<Integer> allScores = scores.values();
        int total = 0;
        for (int score : allScores) {
            total += score;
        }
        double average = (double) total / allScores.size();
        System.out.println("Average score: " + average);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 4: forEach() — MODERN LAMBDA STYLE (Java 8+)
         * ─────────────────────────────────────────────────────────────────
         *
         * map.forEach((key, value) -> { ... })
         *
         * This is the cleanest, most readable way in modern Java.
         * Preferred in professional code today.
         */

        System.out.println("\n=== forEach() lambda ===");
        scores.forEach((name, score) -> {
            String grade = score >= 90 ? "A" : score >= 80 ? "B" : "C";
            System.out.println(name + ": " + score + " (" + grade + ")");
        });

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 5: THE VIEWS ARE LIVE — CHANGES REFLECT IMMEDIATELY
         * ─────────────────────────────────────────────────────────────────
         *
         * keySet(), values(), and entrySet() return VIEWS of the map.
         * They are NOT copies. Changes to the map are reflected in the view,
         * and changes to the view affect the map.
         *
         * This is powerful but also dangerous if you're not careful.
         */

        Set<String> liveKeys = scores.keySet();
        System.out.println("\nBefore remove, liveKeys size: " + liveKeys.size()); // 5

        scores.put("Frank", 70); // add to map
        System.out.println("After adding Frank, liveKeys size: " + liveKeys.size()); // 6 — live!

        liveKeys.remove("Frank"); // remove from the VIEW
        System.out.println("After removing Frank via view, map size: " + scores.size()); // 5 — map updated!

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 6: ConcurrentModificationException — THE BIG TRAP
         * ─────────────────────────────────────────────────────────────────
         *
         * You CANNOT add or remove entries from a HashMap WHILE iterating
         * over it with a for-each loop. Java will throw:
         *   ConcurrentModificationException
         *
         * ⚠️ THIS WILL CRASH:
         *
         *   for (Map.Entry<String, Integer> entry : scores.entrySet()) {
         *       if (entry.getValue() < 80) {
         *           scores.remove(entry.getKey()); // ← CRASH!
         *       }
         *   }
         *
         * WHY? HashMap has a "modCount" counter.
         * Every structural change (put/remove) increments modCount.
         * The iterator checks modCount on each step.
         * If it changed → ConcurrentModificationException.
         */

        // ✓ SAFE WAY 1: Use Iterator's own remove() method
        System.out.println("\n=== Safe removal during iteration (Iterator) ===");
        java.util.Iterator<Map.Entry<String, Integer>> iterator = scores.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() < 80) {
                iterator.remove(); // ← SAFE: uses iterator's remove, not map's
                System.out.println("Removed: " + entry.getKey());
            }
        }

        // ✓ SAFE WAY 2: Collect keys to remove, then remove after loop
        Map<String, Integer> scores2 = new HashMap<>();
        scores2.put("Alice", 95);
        scores2.put("Bob", 60);
        scores2.put("Charlie", 55);

        java.util.List<String> toRemove = new java.util.ArrayList<>();
        for (Map.Entry<String, Integer> entry : scores2.entrySet()) {
            if (entry.getValue() < 70) {
                toRemove.add(entry.getKey()); // collect first
            }
        }
        toRemove.forEach(scores2::remove); // remove after loop — safe!

        // ✓ SAFE WAY 3: Java 8+ removeIf on entrySet
        scores2.entrySet().removeIf(entry -> entry.getValue() < 70);
        // Clean one-liner — internally uses iterator.remove()

        System.out.println("Remaining after cleanup: " + scores2);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 7: WHICH ITERATION METHOD TO USE — DECISION GUIDE
         * ─────────────────────────────────────────────────────────────────
         *
         * Need both key and value?
         *   → entrySet() for-each  OR  forEach() lambda
         *
         * Need only keys?
         *   → keySet() for-each
         *
         * Need only values?
         *   → values() for-each
         *
         * Need to remove during iteration?
         *   → Iterator with iterator.remove()
         *   → OR collect keys and remove after
         *   → OR entrySet().removeIf(...)
         *
         * Writing modern Java 8+ code?
         *   → forEach() lambda is cleanest
         *
         * Writing code that needs to be readable by beginners?
         *   → entrySet() for-each is most explicit
         */
    }
}
