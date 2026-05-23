package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║     STEP 6: COMMON MISTAKES AND CONFUSIONS — AVOID THESE TRAPS     ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. Mutable keys — the silent data loss bug
 *  2. Null key and null value confusion
 *  3. Integer key autoboxing trap
 *  4. Modifying map while iterating
 *  5. Using == instead of equals() for keys
 *  6. Forgetting that get() returns null (NullPointerException)
 *  7. Assuming HashMap has order
 *  8. Using HashMap in multi-threaded code
 */
public class Step6_CommonMistakes {

    public static void main(String[] args) {

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 1: MUTABLE OBJECT AS KEY — SILENT DATA LOSS
         * ─────────────────────────────────────────────────────────────────
         *
         * If you use a mutable object as a key and then CHANGE it after
         * putting it in the map, the hashCode changes.
         * The entry is now in the WRONG bucket.
         * You can never find it again. The data is "lost" — no exception!
         *
         * This is one of the hardest bugs to find.
         */

        Map<StringBuilder, String> badIdea = new HashMap<>();
        StringBuilder key = new StringBuilder("hello");
        badIdea.put(key, "world");

        System.out.println("Before mutation: " + badIdea.get(key)); // "world"

        key.append("!!!"); // MUTATE the key after putting it in the map

        System.out.println("After mutation:  " + badIdea.get(key)); // null — LOST!
        System.out.println("Map still has entry: " + badIdea.size()); // 1 — it's there but unreachable!

        /*
         * WHY? Before mutation: hashCode("hello") → bucket 5
         *      After mutation:  hashCode("hello!!!") → bucket 11
         *      get() looks in bucket 11 → not there → returns null
         *      The entry is stuck in bucket 5 forever.
         *
         * RULE: NEVER use mutable objects as HashMap keys.
         *       Safe keys: String, Integer, Long, UUID, enums, records
         *       Unsafe keys: StringBuilder, ArrayList, custom mutable classes
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 2: NULL KEY AND NULL VALUE CONFUSION
         * ─────────────────────────────────────────────────────────────────
         *
         * HashMap allows:
         *   - ONE null key
         *   - MULTIPLE null values
         *
         * This creates a confusing situation:
         *   map.get(key) returns null for TWO different reasons:
         *   1. The key doesn't exist
         *   2. The key exists but its value IS null
         *
         * You can't tell which case it is just from get()!
         */

        Map<String, String> map = new HashMap<>();
        map.put("name", null);   // key exists, value is null
        // "age" key doesn't exist at all

        String nameVal = map.get("name"); // null — key exists, value is null
        String ageVal  = map.get("age");  // null — key doesn't exist

        // Both return null! How do you tell them apart?
        System.out.println("\nnameVal == null: " + (nameVal == null)); // true
        System.out.println("ageVal  == null: " + (ageVal  == null));  // true

        // ✓ USE containsKey() to distinguish:
        System.out.println("map has 'name': " + map.containsKey("name")); // true
        System.out.println("map has 'age':  " + map.containsKey("age"));  // false

        /*
         * PRO TIP: Avoid storing null values in maps.
         * It creates ambiguity. Use Optional<V> or a sentinel value instead.
         * Or use a different design where absence means "not set".
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 3: INTEGER KEY AUTOBOXING TRAP
         * ─────────────────────────────────────────────────────────────────
         *
         * Java caches Integer objects for values -128 to 127.
         * Outside this range, new Integer objects are created each time.
         * This causes == to behave unexpectedly.
         *
         * HashMap uses equals() for key comparison, so this is usually fine.
         * But if you accidentally use == to compare Integer keys, you'll get bugs.
         */

        Map<Integer, String> intMap = new HashMap<>();
        intMap.put(1, "one");
        intMap.put(200, "two hundred");

        Integer a = 1;
        Integer b = 1;
        System.out.println("\na == b (value 1):   " + (a == b));   // true (cached)

        Integer x = 200;
        Integer y = 200;
        System.out.println("x == y (value 200): " + (x == y));   // false (not cached!)
        System.out.println("x.equals(y):        " + x.equals(y)); // true

        // HashMap uses equals() internally, so get() works correctly:
        System.out.println("intMap.get(200): " + intMap.get(200)); // "two hundred" — correct

        // But if YOU write code like this, you'll get a bug:
        // if (x == y) { ... }  ← WRONG for Integer outside -128..127
        // if (x.equals(y)) { ... }  ← CORRECT

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 4: remove() WITH WRONG TYPE
         * ─────────────────────────────────────────────────────────────────
         *
         * map.remove(key) takes an Object, not K.
         * If you pass the wrong type, it silently does nothing.
         */

        Map<Integer, String> numMap = new HashMap<>();
        numMap.put(1, "one");
        numMap.put(2, "two");

        // ⚠️ This compiles but does NOTHING — passing String "1" not Integer 1
        numMap.remove("1"); // wrong type — no error, no removal!
        System.out.println("\nAfter remove(\"1\"): " + numMap.size()); // still 2

        numMap.remove(1);   // correct — Integer 1
        System.out.println("After remove(1):   " + numMap.size()); // 1

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 5: ASSUMING HASHMAP PRESERVES ORDER
         * ─────────────────────────────────────────────────────────────────
         *
         * HashMap does NOT guarantee any order.
         * The order can change between:
         *   - Different JVM versions
         *   - Different runs of the same program
         *   - After rehashing (when capacity doubles)
         *
         * Never write code that depends on HashMap iteration order.
         */

        Map<String, Integer> unordered = new HashMap<>();
        unordered.put("C", 3);
        unordered.put("A", 1);
        unordered.put("B", 2);

        System.out.println("\nHashMap order (unpredictable): " + unordered.keySet());
        // Could be [A, B, C] or [C, A, B] or anything — don't rely on this!

        // ✓ If you need order, use LinkedHashMap or TreeMap:
        Map<String, Integer> ordered = new LinkedHashMap<>();
        ordered.put("C", 3);
        ordered.put("A", 1);
        ordered.put("B", 2);
        System.out.println("LinkedHashMap order (insertion): " + ordered.keySet());
        // Always: [C, A, B]

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 6: USING HASHMAP IN MULTI-THREADED CODE
         * ─────────────────────────────────────────────────────────────────
         *
         * HashMap is NOT thread-safe.
         * Two threads writing simultaneously can cause:
         *   - Lost updates
         *   - Infinite loops (in old Java versions during rehash)
         *   - Data corruption
         *
         * ✓ For multi-threaded code, use:
         *   ConcurrentHashMap — thread-safe, high performance
         *   Collections.synchronizedMap() — thread-safe but slower
         */

        // Thread-safe option:
        Map<String, Integer> threadSafeMap = new java.util.concurrent.ConcurrentHashMap<>();
        threadSafeMap.put("key", 1);
        // Safe to use from multiple threads simultaneously

        // Synchronized wrapper (less preferred — locks entire map):
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        // Must synchronize manually when iterating:
        synchronized (syncMap) {
            for (Map.Entry<String, Integer> entry : syncMap.entrySet()) {
                // safe iteration
            }
        }

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 7: FORGETTING get() CAN RETURN NULL → NullPointerException
         * ─────────────────────────────────────────────────────────────────
         *
         * This is the #1 most common HashMap bug in production code.
         */

        Map<String, List<String>> groups = new HashMap<>();
        groups.put("fruits", new ArrayList<>(Arrays.asList("apple", "banana")));

        // ⚠️ DANGEROUS — "veggies" key doesn't exist:
        // groups.get("veggies").add("carrot"); // NullPointerException!

        // ✓ SAFE WAY 1: Check first
        List<String> veggies = groups.get("veggies");
        if (veggies != null) {
            veggies.add("carrot");
        }

        // ✓ SAFE WAY 2: computeIfAbsent (best for this pattern)
        groups.computeIfAbsent("veggies", k -> new ArrayList<>()).add("carrot");
        System.out.println("\nGroups: " + groups);

        // ✓ SAFE WAY 3: getOrDefault
        List<String> grains = groups.getOrDefault("grains", new ArrayList<>());
        // Note: this does NOT add "grains" to the map — just returns a default

        /*
         * ─────────────────────────────────────────────────────────────────
         * MISTAKE 8: ITERATING AND MODIFYING SIMULTANEOUSLY
         * ─────────────────────────────────────────────────────────────────
         * Already covered in Step 3, but worth repeating — it's that common.
         *
         * ⚠️ CRASH:
         *   for (String key : map.keySet()) {
         *       map.remove(key); // ConcurrentModificationException!
         *   }
         *
         * ✓ SAFE:
         *   map.keySet().removeIf(key -> someCondition(key));
         *   OR
         *   Iterator<String> it = map.keySet().iterator();
         *   while (it.hasNext()) { if (condition) it.remove(); }
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SUMMARY: THE GOLDEN RULES
         * ─────────────────────────────────────────────────────────────────
         *
         * 1. Use IMMUTABLE keys (String, Integer, enums, records)
         * 2. Always check for null after get() — or use getOrDefault()
         * 3. Use containsKey() to distinguish "absent" from "null value"
         * 4. Never rely on HashMap iteration order
         * 5. Use ConcurrentHashMap in multi-threaded code
         * 6. Never modify map while iterating with for-each
         * 7. Override BOTH hashCode() AND equals() for custom key classes
         * 8. Use == only for primitives; use .equals() for objects
         */
    }
}
