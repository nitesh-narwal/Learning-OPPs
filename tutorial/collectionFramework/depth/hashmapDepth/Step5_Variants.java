package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║     STEP 5: HASHMAP VARIANTS — LinkedHashMap, TreeMap, EnumMap     ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. HashMap         — fast, no order guarantee
 *  2. LinkedHashMap   — insertion order preserved
 *  3. TreeMap         — sorted by key
 *  4. EnumMap         — optimized for enum keys
 *  5. When to use which
 *  6. The Map interface — why you should code to it
 */
public class Step5_Variants {

    // Enum for EnumMap demo
    enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

    public static void main(String[] args) {

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 1: HashMap — THE DEFAULT CHOICE
         * ─────────────────────────────────────────────────────────────────
         *
         * - Fastest for put/get/remove: O(1) average
         * - NO guaranteed order of keys
         * - Allows one null key, multiple null values
         * - NOT thread-safe
         *
         * Use when: you just need fast key-value storage and don't care about order.
         */

        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Banana", 3);
        hashMap.put("Apple",  1);
        hashMap.put("Cherry", 2);

        System.out.println("HashMap (no order guarantee):");
        hashMap.forEach((k, v) -> System.out.println("  " + k + " → " + v));
        // Output order is unpredictable — could be any order

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 2: LinkedHashMap — INSERTION ORDER PRESERVED
         * ─────────────────────────────────────────────────────────────────
         *
         * - Maintains a doubly-linked list through all entries
         * - Iterates in INSERTION ORDER (the order you put things in)
         * - Slightly slower than HashMap (extra pointer maintenance)
         * - Same O(1) for put/get/remove
         *
         * Use when: you need predictable iteration order.
         * Example: building a JSON object, maintaining history, LRU cache.
         */

        Map<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("Banana", 3);
        linkedMap.put("Apple",  1);
        linkedMap.put("Cherry", 2);

        System.out.println("\nLinkedHashMap (insertion order):");
        linkedMap.forEach((k, v) -> System.out.println("  " + k + " → " + v));
        // Always: Banana → Apple → Cherry (insertion order)

        /*
         * LinkedHashMap ACCESS ORDER mode — for LRU Cache
         *
         * LinkedHashMap(capacity, loadFactor, accessOrder)
         * When accessOrder = true:
         *   - Most recently ACCESSED entry moves to the end
         *   - Least recently used is at the beginning
         *   - Perfect for LRU (Least Recently Used) cache
         */

        // LRU Cache using LinkedHashMap
        int cacheSize = 3;
        Map<String, String> lruCache = new LinkedHashMap<>(cacheSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > cacheSize; // auto-remove oldest when full
            }
        };

        lruCache.put("page1", "Home");
        lruCache.put("page2", "About");
        lruCache.put("page3", "Contact");
        lruCache.get("page1"); // access page1 → moves to end (most recent)
        lruCache.put("page4", "Blog"); // cache full → removes page2 (least recent)

        System.out.println("\nLRU Cache after operations: " + lruCache.keySet());
        // [page3, page1, page4] — page2 was evicted (least recently used)

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 3: TreeMap — SORTED BY KEY
         * ─────────────────────────────────────────────────────────────────
         *
         * - Keys are always sorted in NATURAL ORDER (or custom Comparator)
         * - Backed by a Red-Black Tree
         * - put/get/remove: O(log n) — slower than HashMap
         * - Does NOT allow null keys (throws NullPointerException)
         * - Allows null values
         *
         * Use when: you need keys in sorted order.
         * Example: leaderboard, dictionary, range queries.
         */

        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Banana", 3);
        treeMap.put("Apple",  1);
        treeMap.put("Cherry", 2);
        treeMap.put("Avocado", 5);

        System.out.println("\nTreeMap (sorted alphabetically):");
        treeMap.forEach((k, v) -> System.out.println("  " + k + " → " + v));
        // Always: Apple → Avocado → Banana → Cherry

        // TreeMap has extra navigation methods (NavigableMap interface)
        TreeMap<String, Integer> navMap = new TreeMap<>(treeMap);

        System.out.println("\nTreeMap navigation:");
        System.out.println("  First key: " + navMap.firstKey());          // Apple
        System.out.println("  Last key:  " + navMap.lastKey());           // Cherry
        System.out.println("  Floor of 'B': " + navMap.floorKey("B"));   // Banana (≤ B)
        System.out.println("  Ceiling of 'B': " + navMap.ceilingKey("B")); // Banana (≥ B)
        System.out.println("  Lower than 'B': " + navMap.lowerKey("B")); // Avocado (< B)
        System.out.println("  Higher than 'B': " + navMap.higherKey("B")); // Banana (> B)

        // Range view — all entries from "Apple" to "Banana" (inclusive)
        System.out.println("  SubMap A to B: " + navMap.subMap("Apple", true, "Banana", true));

        /*
         * Custom sort order with Comparator
         * Sort by string length, then alphabetically
         */
        Map<String, Integer> customSorted = new TreeMap<>(
            Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        customSorted.put("Banana", 3);
        customSorted.put("Apple",  1);
        customSorted.put("Fig",    4);
        customSorted.put("Kiwi",   2);

        System.out.println("\nTreeMap (sorted by length then alpha): " + customSorted.keySet());
        // [Fig, Kiwi, Apple, Banana]

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 4: EnumMap — OPTIMIZED FOR ENUM KEYS
         * ─────────────────────────────────────────────────────────────────
         *
         * - Internally uses a simple array indexed by enum ordinal
         * - FASTEST map when keys are enums
         * - Iterates in enum declaration order
         * - Does NOT allow null keys
         *
         * Use when: your keys are enum values.
         * Example: day schedules, status mappings, config per category.
         */

        Map<Day, String> schedule = new EnumMap<>(Day.class);
        schedule.put(Day.MONDAY,    "Team standup");
        schedule.put(Day.WEDNESDAY, "Code review");
        schedule.put(Day.FRIDAY,    "Sprint demo");

        System.out.println("\nWeekly schedule (enum order):");
        schedule.forEach((day, task) -> System.out.println("  " + day + ": " + task));
        // Always in enum declaration order: MONDAY, WEDNESDAY, FRIDAY

        System.out.println("Monday task: " + schedule.getOrDefault(Day.MONDAY, "Free day"));
        System.out.println("Tuesday task: " + schedule.getOrDefault(Day.TUESDAY, "Free day"));

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 5: COMPARISON TABLE
         * ─────────────────────────────────────────────────────────────────
         *
         * Feature          | HashMap    | LinkedHashMap | TreeMap    | EnumMap
         * ─────────────────────────────────────────────────────────────────────
         * Order            | None       | Insertion     | Sorted     | Enum order
         * put/get/remove   | O(1) avg   | O(1) avg      | O(log n)   | O(1)
         * Null keys        | 1 allowed  | 1 allowed     | NOT allowed| NOT allowed
         * Null values      | Allowed    | Allowed       | Allowed    | Allowed
         * Thread-safe      | No         | No            | No         | No
         * Memory           | Medium     | More (links)  | More (tree)| Least
         * Best for         | General    | Order matters | Sorted keys| Enum keys
         *
         * ─────────────────────────────────────────────────────────────────
         * DECISION GUIDE
         * ─────────────────────────────────────────────────────────────────
         *
         * "I just need fast key-value storage"
         *   → HashMap
         *
         * "I need to iterate in the order I inserted"
         *   → LinkedHashMap
         *
         * "I need keys sorted alphabetically or numerically"
         *   → TreeMap
         *
         * "I need range queries (all keys between X and Y)"
         *   → TreeMap (subMap, headMap, tailMap)
         *
         * "My keys are enum values"
         *   → EnumMap
         *
         * "I need an LRU cache"
         *   → LinkedHashMap with accessOrder=true
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 6: CODE TO THE INTERFACE — PROFESSIONAL PRACTICE
         * ─────────────────────────────────────────────────────────────────
         *
         * Always declare variables as Map<K,V>, not HashMap<K,V>.
         * This lets you swap implementations without changing other code.
         */

        // ✗ RIGID — tied to HashMap:
        HashMap<String, Integer> rigidMap = new HashMap<>();
        // If you later need sorted order, you must change this line
        // AND every method signature that uses this variable

        // ✓ FLEXIBLE — coded to interface:
        Map<String, Integer> flexibleMap = new HashMap<>();
        // To switch to TreeMap: just change this one line
        // Map<String, Integer> flexibleMap = new TreeMap<>();
        // Everything else stays the same!

        // Exception: when you need TreeMap-specific methods (floorKey, etc.)
        // Then declare as TreeMap or NavigableMap
        NavigableMap<String, Integer> navigable = new TreeMap<>();
        navigable.put("A", 1);
        navigable.put("B", 2);
        String floor = navigable.floorKey("C"); // needs NavigableMap type
    }
}
