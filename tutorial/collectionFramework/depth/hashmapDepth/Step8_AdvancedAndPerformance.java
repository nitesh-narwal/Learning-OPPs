package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║     STEP 8: ADVANCED TOPICS AND PERFORMANCE TUNING                 ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. ConcurrentHashMap — thread-safe HashMap
 *  2. Capacity and load factor tuning
 *  3. HashMap with Java Streams
 *  4. Sorting a HashMap by value
 *  5. Merging two maps
 *  6. Inverting a map (swap keys and values)
 *  7. HashMap with record keys (Java 16+)
 *  8. Performance benchmarking mindset
 */
public class Step8_AdvancedAndPerformance {

    public static void main(String[] args) {

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 1: ConcurrentHashMap — THREAD-SAFE OPERATIONS
         * ─────────────────────────────────────────────────────────────────
         *
         * ConcurrentHashMap is the thread-safe version of HashMap.
         * It uses SEGMENT LOCKING (or CAS in Java 8+):
         *   - Divides the map into segments
         *   - Multiple threads can write to DIFFERENT segments simultaneously
         *   - Only locks the specific segment being modified
         *   - Much faster than synchronizedMap() which locks the whole map
         *
         * KEY DIFFERENCES from HashMap:
         *   - Thread-safe: yes
         *   - Null keys: NOT allowed (throws NullPointerException)
         *   - Null values: NOT allowed
         *   - Iteration: weakly consistent (may not reflect latest changes)
         */

        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("counter", 0);

        // atomic increment — safe in multi-threaded code
        concurrentMap.compute("counter", (key, val) -> val == null ? 1 : val + 1);
        concurrentMap.compute("counter", (key, val) -> val == null ? 1 : val + 1);

        System.out.println("Counter: " + concurrentMap.get("counter")); // 2

        // ConcurrentHashMap also has atomic bulk operations:
        concurrentMap.put("a", 1);
        concurrentMap.put("b", 2);
        concurrentMap.put("c", 3);

        // forEach with parallelism threshold
        // If map has > 2 entries, process in parallel
        concurrentMap.forEach(2, (key, val) ->
            System.out.println("  Processing: " + key + "=" + val));

        // search — returns first non-null result
        String found = concurrentMap.search(1, (key, val) -> val > 1 ? key : null);
        System.out.println("First key with value > 1: " + found);

        // reduce — aggregate all values
        int sum = concurrentMap.reduceValues(1, Integer::sum);
        System.out.println("Sum of all values: " + sum);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 2: CAPACITY AND LOAD FACTOR TUNING
         * ─────────────────────────────────────────────────────────────────
         *
         * Default HashMap: capacity=16, loadFactor=0.75
         *
         * WHEN TO TUNE:
         *
         * Scenario A: You know you'll store exactly N entries
         *   → Set initial capacity to avoid rehashing
         *   → Formula: capacity = (int)(N / 0.75) + 1
         *
         * Scenario B: Memory is tight, few lookups
         *   → Increase load factor (e.g., 0.9) → fewer buckets, more collisions
         *   → Saves memory, slightly slower lookups
         *
         * Scenario C: Speed is critical, memory is plentiful
         *   → Decrease load factor (e.g., 0.5) → more buckets, fewer collisions
         *   → Faster lookups, uses more memory
         */

        // Pre-sized for 1000 entries — no rehashing will occur
        int expectedSize = 1000;
        Map<String, String> preSized = new HashMap<>((int)(expectedSize / 0.75) + 1);

        // High load factor — memory efficient, slightly slower
        Map<String, String> memoryEfficient = new HashMap<>(16, 0.9f);

        // Low load factor — faster lookups, uses more memory
        Map<String, String> speedOptimized = new HashMap<>(256, 0.5f);

        /*
         * REHASHING COST:
         * Each rehash copies ALL entries to a new array.
         * For 1 million entries, that's 1 million operations just for rehashing.
         * Pre-sizing eliminates this cost entirely.
         *
         * In production systems handling large datasets, pre-sizing can
         * reduce startup time significantly.
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 3: HASHMAP WITH JAVA STREAMS
         * ─────────────────────────────────────────────────────────────────
         *
         * Streams work beautifully with maps via entrySet().
         * This is the modern, functional way to process maps.
         */

        Map<String, Integer> population = new HashMap<>();
        population.put("India",  1400);
        population.put("China",  1400);
        population.put("USA",     330);
        population.put("Brazil",  215);
        population.put("Germany",  84);

        // Filter: countries with population > 300 million
        Map<String, Integer> large = population.entrySet().stream()
            .filter(e -> e.getValue() > 300)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println("\nLarge countries: " + large);

        // Transform values: convert to billions
        Map<String, Double> inBillions = population.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue() / 1000.0
            ));
        System.out.println("Population in billions: " + inBillions);

        // Collect to sorted map
        Map<String, Integer> sortedByPop = population.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,          // merge function (for duplicate keys)
                LinkedHashMap::new        // use LinkedHashMap to preserve order
            ));
        System.out.println("Sorted by population: " + sortedByPop.keySet());

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 4: SORTING A HASHMAP BY VALUE
         * ─────────────────────────────────────────────────────────────────
         *
         * HashMap itself can't be sorted — it has no order.
         * To "sort by value", you collect into a LinkedHashMap (preserves order).
         */

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice",   95);
        scores.put("Bob",     72);
        scores.put("Charlie", 88);
        scores.put("Diana",   95);
        scores.put("Eve",     60);

        // Sort by value descending, then by key ascending for ties
        Map<String, Integer> sortedScores = scores.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new
            ));

        System.out.println("\nLeaderboard:");
        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedScores.entrySet()) {
            System.out.println("  #" + rank++ + " " + entry.getKey() + ": " + entry.getValue());
        }

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 5: MERGING TWO MAPS
         * ─────────────────────────────────────────────────────────────────
         *
         * Combine two maps, handling key conflicts.
         */

        Map<String, Integer> map1 = new HashMap<>(Map.of("a", 1, "b", 2, "c", 3));
        Map<String, Integer> map2 = new HashMap<>(Map.of("b", 20, "c", 30, "d", 40));

        // Merge: map2 values win on conflict
        Map<String, Integer> merged = new HashMap<>(map1);
        map2.forEach((key, val) -> merged.merge(key, val, (old, newVal) -> newVal));
        System.out.println("\nMerged (map2 wins): " + merged);

        // Merge: sum values on conflict
        Map<String, Integer> sumMerged = new HashMap<>(map1);
        map2.forEach((key, val) -> sumMerged.merge(key, val, Integer::sum));
        System.out.println("Merged (sum): " + sumMerged);

        // Java 8 putAll (map2 overwrites map1 on conflict):
        Map<String, Integer> simpleMerge = new HashMap<>(map1);
        simpleMerge.putAll(map2); // map2 values overwrite map1 values
        System.out.println("putAll merge: " + simpleMerge);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 6: INVERTING A MAP (SWAP KEYS AND VALUES)
         * ─────────────────────────────────────────────────────────────────
         *
         * Swap keys and values.
         * Only works cleanly if values are unique (bijective mapping).
         * If values are not unique, you need to handle collisions.
         */

        Map<String, Integer> nameToId = Map.of("Alice", 1, "Bob", 2, "Charlie", 3);

        // Simple invert (assumes unique values)
        Map<Integer, String> idToName = nameToId.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        System.out.println("\nInverted map: " + idToName);

        // Invert with duplicate values → group keys by value
        Map<String, String> colorMap = Map.of(
            "apple", "red", "tomato", "red", "banana", "yellow", "lemon", "yellow"
        );

        Map<String, List<String>> invertedWithDups = colorMap.entrySet().stream()
            .collect(Collectors.groupingBy(
                Map.Entry::getValue,
                Collectors.mapping(Map.Entry::getKey, Collectors.toList())
            ));
        System.out.println("Inverted with duplicates: " + invertedWithDups);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 7: RECORD AS MAP KEY (Java 16+)
         * ─────────────────────────────────────────────────────────────────
         *
         * Java records automatically generate:
         *   - equals() based on all fields
         *   - hashCode() based on all fields
         *   - toString()
         *
         * This makes records PERFECT as HashMap keys.
         * No need to manually override hashCode/equals!
         */

        // Record: immutable, auto-generates hashCode and equals
        record Point(int x, int y) {}

        Map<Point, String> grid = new HashMap<>();
        grid.put(new Point(0, 0), "origin");
        grid.put(new Point(1, 0), "right");
        grid.put(new Point(0, 1), "up");

        // Two Point(0,0) objects are equal — record handles this correctly
        System.out.println("\nGrid at (0,0): " + grid.get(new Point(0, 0))); // "origin"
        System.out.println("Grid at (1,0): " + grid.get(new Point(1, 0))); // "right"

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 8: PERFORMANCE TIPS SUMMARY
         * ─────────────────────────────────────────────────────────────────
         *
         * 1. PRE-SIZE when you know the expected number of entries:
         *    new HashMap<>((int)(n / 0.75) + 1)
         *    → Eliminates rehashing overhead
         *
         * 2. USE String/Integer/Long/enum as keys:
         *    → Their hashCode() is well-distributed and fast
         *    → Minimizes collisions
         *
         * 3. AVOID complex objects as keys:
         *    → Expensive hashCode() computation on every put/get
         *    → Cache the hashCode if you must use complex keys
         *
         * 4. USE EnumMap for enum keys:
         *    → Fastest possible map — O(1) with no hashing at all
         *
         * 5. USE ConcurrentHashMap for multi-threaded code:
         *    → Never use HashMap + synchronized block — use CHM instead
         *
         * 6. AVOID HashMap for sorted data:
         *    → Use TreeMap — it's designed for sorted access
         *    → Sorting a HashMap on every read is wasteful
         *
         * 7. USE getOrDefault() and computeIfAbsent():
         *    → Cleaner code, fewer null checks, fewer bugs
         *
         * 8. PROFILE before optimizing:
         *    → HashMap is already very fast for most use cases
         *    → Only tune capacity/loadFactor if profiling shows it's a bottleneck
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 9: WHEN NOT TO USE HASHMAP
         * ─────────────────────────────────────────────────────────────────
         *
         * ✗ When you need sorted keys → use TreeMap
         * ✗ When you need insertion order → use LinkedHashMap
         * ✗ When keys are enums → use EnumMap
         * ✗ When you need thread safety → use ConcurrentHashMap
         * ✗ When you need a simple list → use ArrayList (no key needed)
         * ✗ When you need unique values (not keys) → use HashSet
         * ✗ When data is tiny (< 5 entries) → a simple array or list may be faster
         *   (HashMap has overhead; for tiny data, linear search can beat it)
         */
    }
}
