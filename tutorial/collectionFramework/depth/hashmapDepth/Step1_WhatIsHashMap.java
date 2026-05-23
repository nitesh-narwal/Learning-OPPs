package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.HashMap;
import java.util.Map;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║         STEP 1: WHAT IS A HASHMAP? — THE FOUNDATION                ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. What is a HashMap and why it exists
 *  2. Key-Value concept
 *  3. How to create a HashMap
 *  4. Basic operations: put, get, remove, containsKey
 *  5. The "phone book" mental model
 *  6. HashMap vs Array — why HashMap wins in many cases
 */
public class Step1_WhatIsHashMap {

    public static void main(String[] args) {

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 1: THE PROBLEM HASHMAP SOLVES
         * ─────────────────────────────────────────────────────────────────
         *
         * Imagine you have 1 million students and you want to find
         * a student by their roll number.
         *
         * With an Array:
         *   - You search one by one → O(n) → slow for large data
         *   - Even with binary search → O(log n) → still not instant
         *
         * With a HashMap:
         *   - You look up directly by key → O(1) → INSTANT (on average)
         *
         * Think of it like a PHONE BOOK:
         *   - Name  = KEY   (what you search by)
         *   - Phone = VALUE (what you want to find)
         *
         *   "Nitesh" → "9876543210"
         *   "Rahul"  → "9123456789"
         *
         * You don't flip every page. You jump directly to "N" and find Nitesh.
         * HashMap does the same thing internally using a technique called HASHING.
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 2: CREATING A HASHMAP
         * ─────────────────────────────────────────────────────────────────
         *
         * Syntax:
         *   HashMap<KeyType, ValueType> mapName = new HashMap<>();
         *
         * Both KeyType and ValueType MUST be Objects (not primitives).
         *   ✗  HashMap<int, String>     → WRONG (int is primitive)
         *   ✓  HashMap<Integer, String> → CORRECT (Integer is wrapper class)
         */

        // A phone book: name → phone number
        HashMap<String, String> phoneBook = new HashMap<>();

        // A student grade book: studentId → grade
        HashMap<Integer, String> gradeBook = new HashMap<>();

        // A word counter: word → count
        HashMap<String, Integer> wordCount = new HashMap<>();

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 3: PUT — ADDING KEY-VALUE PAIRS
         * ─────────────────────────────────────────────────────────────────
         *
         * map.put(key, value)
         *
         * - If key does NOT exist → adds new entry
         * - If key ALREADY exists → REPLACES the old value (no duplicates!)
         *
         * This is a very common confusion for beginners:
         *   HashMap does NOT allow duplicate KEYS.
         *   But it DOES allow duplicate VALUES.
         */

        phoneBook.put("Nitesh", "9876543210");
        phoneBook.put("Rahul",  "9123456789");
        phoneBook.put("Priya",  "9988776655");
        phoneBook.put("Amit",   "9876543210"); // same VALUE as Nitesh — allowed!

        // What happens when you put a duplicate KEY?
        phoneBook.put("Nitesh", "1111111111"); // replaces "9876543210" with "1111111111"

        /*
         * phoneBook now looks like:
         *   "Nitesh" → "1111111111"   ← updated, old value gone
         *   "Rahul"  → "9123456789"
         *   "Priya"  → "9988776655"
         *   "Amit"   → "9876543210"
         *
         * KEY RULE: One key can only hold ONE value at a time.
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 4: GET — RETRIEVING A VALUE
         * ─────────────────────────────────────────────────────────────────
         *
         * map.get(key) → returns the value, or NULL if key doesn't exist
         *
         * ⚠️  COMMON MISTAKE: Not checking for null before using the result.
         *     If key doesn't exist, get() returns null.
         *     Calling a method on null → NullPointerException!
         */

        String niteshPhone = phoneBook.get("Nitesh");
        // niteshPhone = "1111111111"

        String unknownPhone = phoneBook.get("Zara"); // "Zara" doesn't exist
        // unknownPhone = null  ← NOT an exception, just null

        // ⚠️ DANGER ZONE — this would crash if unknownPhone is null:
        // int len = unknownPhone.length(); // NullPointerException!

        // ✓ SAFE WAY — always check before using:
        if (unknownPhone != null) {
            System.out.println("Found: " + unknownPhone);
        } else {
            System.out.println("Zara not found in phone book");
        }

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 5: containsKey — CHECK BEFORE YOU GET
         * ─────────────────────────────────────────────────────────────────
         *
         * map.containsKey(key) → returns true/false
         *
         * Use this when you want to know IF a key exists
         * before doing something with its value.
         */

        if (phoneBook.containsKey("Rahul")) {
            System.out.println("Rahul's number: " + phoneBook.get("Rahul"));
        }

        // containsValue() also exists but it's O(n) — scans all values
        boolean hasNumber = phoneBook.containsValue("9123456789"); // O(n) — slow!
        // Use containsKey() whenever possible — it's O(1)

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 6: REMOVE — DELETING AN ENTRY
         * ─────────────────────────────────────────────────────────────────
         *
         * map.remove(key)          → removes the entry, returns the old value
         * map.remove(key, value)   → removes ONLY if key maps to that exact value
         */

        String removed = phoneBook.remove("Amit");
        // removed = "9876543210" (the value that was there)
        // "Amit" is now gone from the map

        // Conditional remove — useful for thread-safe-like logic
        boolean wasRemoved = phoneBook.remove("Priya", "WRONG_NUMBER");
        // wasRemoved = false — because Priya's number is NOT "WRONG_NUMBER"
        // Priya is still in the map!

        boolean wasRemovedCorrectly = phoneBook.remove("Priya", "9988776655");
        // wasRemovedCorrectly = true — correct value matched, entry removed

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 7: SIZE AND EMPTY CHECK
         * ─────────────────────────────────────────────────────────────────
         */

        int size = phoneBook.size();       // number of key-value pairs
        boolean empty = phoneBook.isEmpty(); // true if size == 0

        phoneBook.clear(); // removes ALL entries — map is now empty
        // size() = 0, isEmpty() = true

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 8: HASHMAP vs ARRAY — WHEN TO USE WHAT
         * ─────────────────────────────────────────────────────────────────
         *
         * USE ARRAY when:
         *   - You access by numeric index (0, 1, 2...)
         *   - Data is ordered and dense
         *   - Example: scores[0], scores[1]...
         *
         * USE HASHMAP when:
         *   - You access by a meaningful key (name, id, word...)
         *   - You need fast lookup, insert, delete
         *   - You want to count, group, or map things
         *   - Example: wordCount.get("hello"), studentGrade.get(101)
         *
         * REAL WORLD ANALOGY:
         *   Array  = numbered lockers (locker 1, 2, 3...)
         *   HashMap = named lockers  (locker "Nitesh", "Rahul"...)
         *
         * ─────────────────────────────────────────────────────────────────
         * PERFORMANCE SUMMARY
         * ─────────────────────────────────────────────────────────────────
         *
         * Operation         | HashMap  | Array (unsorted)
         * ──────────────────────────────────────────────
         * put / insert      | O(1) avg | O(1) at end
         * get / lookup      | O(1) avg | O(n) search
         * remove            | O(1) avg | O(n) shift
         * containsKey       | O(1) avg | O(n) search
         *
         * "avg" = average case. Worst case is O(n) — we'll cover why in Step 3.
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 9: QUICK TIPS FOR BEGINNERS
         * ─────────────────────────────────────────────────────────────────
         *
         * TIP 1: Always use the Map interface type on the left side:
         *   Map<String, String> map = new HashMap<>();   ← PROFESSIONAL
         *   HashMap<String, String> map = new HashMap<>(); ← works but less flexible
         *
         *   Why? Because later you can swap HashMap for TreeMap or LinkedHashMap
         *   without changing the rest of your code.
         *
         * TIP 2: Keys must be immutable (don't change after putting in map).
         *   String and Integer are safe keys.
         *   Mutable objects as keys = bugs that are very hard to find.
         *
         * TIP 3: HashMap is NOT thread-safe.
         *   Multiple threads writing at the same time = data corruption.
         *   Use ConcurrentHashMap for multi-threaded code.
         *
         * TIP 4: HashMap does NOT guarantee order.
         *   If you need insertion order → use LinkedHashMap
         *   If you need sorted order   → use TreeMap
         */

        // Professional style: declare as Map interface
        Map<String, String> contacts = new HashMap<>();
        contacts.put("Boss", "9000000001");
        contacts.put("Mom",  "9000000002");

        System.out.println("Contacts: " + contacts);
        // Output order is NOT guaranteed — HashMap doesn't preserve insertion order
    }
}
