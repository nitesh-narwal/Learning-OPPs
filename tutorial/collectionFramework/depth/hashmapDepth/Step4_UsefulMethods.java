package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.HashMap;
import java.util.Map;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║     STEP 4: POWERFUL HASHMAP METHODS YOU MUST KNOW                 ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. getOrDefault()    — safe get with a fallback
 *  2. putIfAbsent()     — put only if key doesn't exist
 *  3. computeIfAbsent() — compute and put if absent (great for grouping)
 *  4. merge()           — combine old and new values
 *  5. compute()         — update a value based on current value
 *  6. replace()         — update existing entries safely
 *  7. Real patterns: word count, grouping, frequency maps
 */
public class Step4_UsefulMethods {

    public static void main(String[] args) {

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 1: getOrDefault() — SAFE GET WITH FALLBACK
         * ─────────────────────────────────────────────────────────────────
         *
         * map.getOrDefault(key, defaultValue)
         *
         * Returns the value if key exists, otherwise returns defaultValue.
         * Avoids null checks. Very common in real code.
         */

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Apple",  50);
        inventory.put("Banana", 30);

        // ✗ OLD WAY — verbose and error-prone:
        Integer mangoCount = inventory.get("Mango");
        int mangoQty = (mangoCount != null) ? mangoCount : 0;

        // ✓ NEW WAY — clean and safe:
        int mangoQtyClean = inventory.getOrDefault("Mango", 0);
        int appleQty      = inventory.getOrDefault("Apple", 0); // returns 50

        System.out.println("Mango qty: " + mangoQtyClean); // 0
        System.out.println("Apple qty: " + appleQty);      // 50

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 2: putIfAbsent() — ADD ONLY IF KEY DOESN'T EXIST
         * ─────────────────────────────────────────────────────────────────
         *
         * map.putIfAbsent(key, value)
         *
         * - If key is NOT in map → puts the entry, returns null
         * - If key IS in map     → does nothing, returns existing value
         *
         * Use case: setting default values, initializing entries.
         */

        Map<String, String> config = new HashMap<>();
        config.put("theme", "dark");

        config.putIfAbsent("theme",    "light"); // key exists → ignored
        config.putIfAbsent("language", "en");    // key absent → added

        System.out.println("\nConfig: " + config);
        // {theme=dark, language=en}
        // "theme" was NOT overwritten — putIfAbsent protected it

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 3: computeIfAbsent() — THE GROUPING POWERHOUSE
         * ─────────────────────────────────────────────────────────────────
         *
         * map.computeIfAbsent(key, key -> newValue)
         *
         * - If key is NOT in map → computes the value using the lambda,
         *   puts it, and returns it
         * - If key IS in map     → returns existing value, does nothing
         *
         * This is EXTREMELY useful for grouping data.
         * Pattern: Map<String, List<Something>>
         */

        // REAL WORLD: Group students by their grade
        String[] students = {"Alice:A", "Bob:B", "Charlie:A", "Diana:B", "Eve:A"};

        Map<String, java.util.List<String>> byGrade = new HashMap<>();

        for (String entry : students) {
            String[] parts = entry.split(":");
            String name  = parts[0];
            String grade = parts[1];

            // ✗ OLD WAY — verbose:
            // if (!byGrade.containsKey(grade)) {
            //     byGrade.put(grade, new ArrayList<>());
            // }
            // byGrade.get(grade).add(name);

            // ✓ NEW WAY — clean with computeIfAbsent:
            byGrade.computeIfAbsent(grade, k -> new java.util.ArrayList<>()).add(name);
            //                              ↑ only creates new list if grade not in map
        }

        System.out.println("\nStudents by grade: " + byGrade);
        // {A=[Alice, Charlie, Eve], B=[Bob, Diana]}

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 4: merge() — COMBINE OLD AND NEW VALUES
         * ─────────────────────────────────────────────────────────────────
         *
         * map.merge(key, value, (oldVal, newVal) -> combinedVal)
         *
         * - If key is NOT in map → puts value directly
         * - If key IS in map     → applies the merge function to combine
         *                          old value and new value
         *
         * PERFECT for counting, summing, concatenating.
         */

        // REAL WORLD: Count word frequency
        String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};

        Map<String, Integer> wordFreq = new HashMap<>();

        for (String word : words) {
            // ✗ OLD WAY:
            // wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);

            // ✓ CLEAN WAY with merge:
            wordFreq.merge(word, 1, Integer::sum);
            //              ↑    ↑   ↑
            //              key  if absent: put 1
            //                   if present: add 1 to existing count
        }

        System.out.println("\nWord frequency: " + wordFreq);
        // {apple=3, banana=2, cherry=1}

        // Another merge example: concatenate strings
        Map<String, String> messages = new HashMap<>();
        messages.merge("log", "Start",  (old, newVal) -> old + ", " + newVal);
        messages.merge("log", "Middle", (old, newVal) -> old + ", " + newVal);
        messages.merge("log", "End",    (old, newVal) -> old + ", " + newVal);

        System.out.println("Log: " + messages.get("log"));
        // Log: Start, Middle, End

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 5: compute() — UPDATE BASED ON CURRENT VALUE
         * ─────────────────────────────────────────────────────────────────
         *
         * map.compute(key, (key, oldVal) -> newVal)
         *
         * - oldVal is null if key doesn't exist
         * - If lambda returns null → key is REMOVED from map
         * - Otherwise → key is updated with returned value
         *
         * More flexible than merge() — you get access to the key too.
         */

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 80);
        scores.put("Bob",   70);

        // Add 10 bonus points to everyone
        scores.compute("Alice", (key, val) -> val == null ? 10 : val + 10);
        scores.compute("Bob",   (key, val) -> val == null ? 10 : val + 10);
        scores.compute("Charlie", (key, val) -> val == null ? 10 : val + 10); // new entry

        System.out.println("\nScores after bonus: " + scores);
        // {Alice=90, Bob=80, Charlie=10}

        // Remove entry if value becomes 0 or negative
        scores.compute("Bob", (key, val) -> (val != null && val > 0) ? val - 100 : null);
        // Bob's score would be -20, but we return null → Bob is REMOVED
        System.out.println("After removing Bob: " + scores);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 6: replace() — UPDATE EXISTING ENTRIES SAFELY
         * ─────────────────────────────────────────────────────────────────
         *
         * map.replace(key, newValue)
         *   → replaces value only if key EXISTS (unlike put which always inserts)
         *   → returns old value, or null if key didn't exist
         *
         * map.replace(key, oldValue, newValue)
         *   → replaces ONLY if current value matches oldValue (atomic-like)
         *   → returns true if replaced, false otherwise
         */

        Map<String, String> status = new HashMap<>();
        status.put("task1", "pending");
        status.put("task2", "pending");

        // Replace unconditionally (only if key exists)
        String old = status.replace("task1", "completed");
        System.out.println("\nOld status: " + old);         // pending
        System.out.println("New status: " + status.get("task1")); // completed

        // Replace only if current value matches — safe update
        boolean updated = status.replace("task2", "pending", "in-progress");
        System.out.println("task2 updated: " + updated);   // true

        boolean failed = status.replace("task2", "pending", "done");
        System.out.println("task2 failed update: " + failed); // false — it's "in-progress" now

        // replaceAll — update ALL values with a function
        Map<String, Integer> prices = new HashMap<>();
        prices.put("Apple",  100);
        prices.put("Banana", 50);
        prices.put("Cherry", 200);

        prices.replaceAll((item, price) -> (int)(price * 1.1)); // 10% price hike
        System.out.println("\nPrices after hike: " + prices);

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 7: REAL PATTERN — FREQUENCY / COUNTING MAP
         * ─────────────────────────────────────────────────────────────────
         *
         * This is one of the most common HashMap patterns in coding interviews
         * and real applications.
         */

        // Count character frequency in a string
        String text = "programming";
        Map<Character, Integer> charFreq = new HashMap<>();

        for (char c : text.toCharArray()) {
            charFreq.merge(c, 1, Integer::sum);
        }

        System.out.println("\nChar frequency in '" + text + "': " + charFreq);

        // Find the most frequent character
        char mostFrequent = ' ';
        int maxCount = 0;
        for (Map.Entry<Character, Integer> entry : charFreq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        System.out.println("Most frequent: '" + mostFrequent + "' (" + maxCount + " times)");

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 8: METHOD QUICK REFERENCE
         * ─────────────────────────────────────────────────────────────────
         *
         * Method                          | Use When
         * ────────────────────────────────────────────────────────────────
         * get(key)                        | Simple lookup, handle null yourself
         * getOrDefault(key, def)          | Lookup with safe fallback
         * put(key, val)                   | Always insert/overwrite
         * putIfAbsent(key, val)           | Insert only if not present
         * computeIfAbsent(key, fn)        | Insert computed value if absent
         * merge(key, val, fn)             | Count, sum, or combine values
         * compute(key, fn)                | Update based on current value
         * replace(key, val)               | Update only if key exists
         * replace(key, old, new)          | Conditional update (safe swap)
         * replaceAll((k,v) -> newV)       | Bulk update all values
         * remove(key)                     | Delete entry
         * remove(key, val)                | Conditional delete
         */
    }
}
