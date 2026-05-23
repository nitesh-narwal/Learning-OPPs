package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.HashMap;
import java.util.Objects;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║     STEP 2: HOW HASHMAP WORKS INTERNALLY — HASHING EXPLAINED       ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. What is a hash function?
 *  2. What is a bucket array?
 *  3. How put() and get() work step by step
 *  4. What is a collision?
 *  5. How Java resolves collisions (chaining + tree)
 *  6. hashCode() and equals() — the two most important methods
 *  7. Why bad hashCode() breaks your HashMap
 */
public class Step2_InternalWorking {

    public static void main(String[] args) {

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 1: THE BUCKET ARRAY — HASHMAP'S INTERNAL STRUCTURE
         * ─────────────────────────────────────────────────────────────────
         *
         * Internally, HashMap stores data in an ARRAY of "buckets".
         * Default size = 16 buckets (indices 0 to 15).
         *
         * Each bucket can hold a LINKED LIST (or tree) of entries.
         *
         * Visual:
         *
         *  Index | Bucket
         *  ──────┼──────────────────────────────────────
         *    0   | null
         *    1   | [key="Rahul", val="9123"] → null
         *    2   | null
         *    3   | [key="Nitesh", val="9876"] → null
         *    4   | null
         *    5   | [key="Priya", val="9988"] → [key="Amit", val="9000"] → null
         *    ...
         *   15   | null
         *
         * Index 5 has TWO entries — that's a COLLISION (explained below).
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 2: HOW put(key, value) WORKS — STEP BY STEP
         * ─────────────────────────────────────────────────────────────────
         *
         * When you call: map.put("Nitesh", "9876543210")
         *
         * Step 1: Java calls "Nitesh".hashCode()
         *         → Returns some large integer, e.g., 2043456789
         *
         * Step 2: Java applies a "spread" function to reduce collisions
         *         → hash = spread(2043456789) → e.g., 1234567
         *
         * Step 3: Java calculates the bucket index:
         *         → index = hash & (capacity - 1)
         *         → index = 1234567 & 15 = 7   (for default capacity 16)
         *         (This is like: index = hash % capacity, but faster with &)
         *
         * Step 4: Java goes to bucket[7] and stores the entry there.
         *
         * When you call: map.get("Nitesh")
         *
         * Step 1: Java calls "Nitesh".hashCode() → same hash as before
         * Step 2: Calculates same index → 7
         * Step 3: Goes to bucket[7]
         * Step 4: Compares keys using .equals() to find the exact entry
         * Step 5: Returns the value
         *
         * This is why get() is O(1) — it jumps directly to the right bucket!
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 3: WHAT IS A COLLISION?
         * ─────────────────────────────────────────────────────────────────
         *
         * A collision happens when TWO different keys hash to the SAME bucket index.
         *
         * Example:
         *   "Priya".hashCode() → index 5
         *   "Amit".hashCode()  → index 5  ← COLLISION!
         *
         * Both want to go into bucket[5].
         *
         * Java's solution: CHAINING
         *   → Store both entries in bucket[5] as a linked list:
         *   bucket[5] → [Priya, 9988] → [Amit, 9000] → null
         *
         * When you call get("Amit"):
         *   → Go to bucket[5]
         *   → Walk the chain: check "Priya" (not equal), check "Amit" (equal!) → return value
         *
         * If a bucket has too many entries (≥ 8 by default), Java converts
         * the linked list to a RED-BLACK TREE for O(log n) lookup instead of O(n).
         *
         * This is why worst case is O(n) — if ALL keys collide into one bucket,
         * you're searching a list of n items.
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 4: hashCode() AND equals() — THE GOLDEN RULE
         * ─────────────────────────────────────────────────────────────────
         *
         * THE GOLDEN RULE (memorize this):
         *   If two objects are EQUAL (equals() returns true),
         *   they MUST have the SAME hashCode().
         *
         * If you break this rule, your HashMap will silently LOSE data.
         *
         * Java's String, Integer, etc. already implement this correctly.
         * The problem arises when YOU create a custom class as a key.
         */

        // ─── EXAMPLE: Custom class WITHOUT proper hashCode/equals ───────

        HashMap<BadKey, String> badMap = new HashMap<>();
        BadKey k1 = new BadKey("Nitesh");
        BadKey k2 = new BadKey("Nitesh"); // same content as k1

        badMap.put(k1, "Developer");

        // You'd expect this to return "Developer", right?
        String result = badMap.get(k2);
        // result = null  ← WRONG! HashMap can't find it!

        /*
         * WHY? Because BadKey doesn't override hashCode().
         * k1 and k2 have different memory addresses → different hashCodes
         * → HashMap looks in different buckets → can't find k2's entry!
         *
         * Even though k1.equals(k2) might return true (if you override equals),
         * the hashCode mismatch means HashMap looks in the WRONG bucket.
         */

        // ─── EXAMPLE: Custom class WITH proper hashCode/equals ──────────

        HashMap<GoodKey, String> goodMap = new HashMap<>();
        GoodKey g1 = new GoodKey("Nitesh");
        GoodKey g2 = new GoodKey("Nitesh");

        goodMap.put(g1, "Developer");

        String correctResult = goodMap.get(g2);
        // correctResult = "Developer"  ← CORRECT!

        System.out.println("Bad map result:  " + result);        // null
        System.out.println("Good map result: " + correctResult); // Developer

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 5: LOAD FACTOR AND REHASHING
         * ─────────────────────────────────────────────────────────────────
         *
         * Load Factor = (number of entries) / (number of buckets)
         * Default load factor = 0.75
         *
         * When load factor exceeds 0.75, HashMap REHASHES:
         *   → Creates a new array with DOUBLE the capacity
         *   → Moves all existing entries to new positions
         *   → This is O(n) but happens rarely
         *
         * Example with default capacity 16:
         *   - After 12 entries (16 * 0.75 = 12), rehash triggers
         *   - New capacity = 32
         *   - After 24 entries (32 * 0.75 = 24), rehash again
         *   - New capacity = 64
         *   - And so on...
         *
         * WHY 0.75?
         *   - Too low (e.g., 0.25) → rehash too often → wastes time
         *   - Too high (e.g., 1.0) → too many collisions → slow lookups
         *   - 0.75 is the sweet spot between time and space
         *
         * PRO TIP: If you know you'll store ~1000 entries, initialize with:
         *   new HashMap<>(2048)  or  new HashMap<>(1000 / 0.75 + 1)
         *   This avoids rehashing entirely → better performance.
         */

        // Pre-sized HashMap — avoids rehashing for known sizes
        int expectedEntries = 1000;
        HashMap<String, Integer> optimizedMap = new HashMap<>((int)(expectedEntries / 0.75) + 1);
        // Now it won't rehash until you exceed 1000 entries

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 6: VISUAL SUMMARY OF INTERNAL STRUCTURE
         * ─────────────────────────────────────────────────────────────────
         *
         *  HashMap<String, String>
         *
         *  put("A", "1") → hashCode("A") → index 3
         *  put("B", "2") → hashCode("B") → index 7
         *  put("C", "3") → hashCode("C") → index 3  ← COLLISION with "A"
         *
         *  Bucket Array (capacity = 16):
         *  ┌───┬──────────────────────────────────────────┐
         *  │ 0 │ null                                     │
         *  │ 1 │ null                                     │
         *  │ 2 │ null                                     │
         *  │ 3 │ Node["A","1"] → Node["C","3"] → null    │ ← chain
         *  │ 4 │ null                                     │
         *  │ 5 │ null                                     │
         *  │ 6 │ null                                     │
         *  │ 7 │ Node["B","2"] → null                    │
         *  │...│ null                                     │
         *  │15 │ null                                     │
         *  └───┴──────────────────────────────────────────┘
         *
         *  Each Node contains: key, value, hash, next (pointer to next node)
         */

        /*
         * ─────────────────────────────────────────────────────────────────
         * SECTION 7: KEY TAKEAWAYS
         * ─────────────────────────────────────────────────────────────────
         *
         * 1. HashMap uses an array of buckets internally.
         * 2. hashCode() determines WHICH bucket to use.
         * 3. equals() determines WHICH entry within the bucket.
         * 4. Collisions are handled by chaining (linked list → tree).
         * 5. Load factor 0.75 triggers rehashing when exceeded.
         * 6. ALWAYS override both hashCode() AND equals() for custom keys.
         * 7. String and Integer are safe keys — they already do this correctly.
         */
    }
}

// ─── BAD KEY: No hashCode override ──────────────────────────────────────────
class BadKey {
    String name;

    BadKey(String name) {
        this.name = name;
    }

    // equals() is overridden — but hashCode() is NOT
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BadKey)) return false;
        BadKey other = (BadKey) o;
        return Objects.equals(this.name, other.name);
    }

    // ⚠️ hashCode() NOT overridden → uses Object's default (memory address)
    // Two BadKey("Nitesh") objects will have DIFFERENT hashCodes
    // → HashMap puts them in DIFFERENT buckets
    // → get() can never find what put() stored
}

// ─── GOOD KEY: Both hashCode and equals overridden ──────────────────────────
class GoodKey {
    String name;

    GoodKey(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodKey)) return false;
        GoodKey other = (GoodKey) o;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        // Objects.hash() is the standard, clean way to generate hashCode
        // It combines the hashCodes of all fields you care about
        return Objects.hash(name);
    }

    /*
     * RULE: If two GoodKey objects have the same name:
     *   equals()   → true
     *   hashCode() → same value
     * → HashMap finds them in the SAME bucket → works correctly!
     */
}
