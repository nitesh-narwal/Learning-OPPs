package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Lecture2.map.IdentityHashMap;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * ============================================================
 *           IDENTITYHASHMAP - THE IDENTITY CRISIS MAP 😂
 * ============================================================
 *
 * Bhai, sun. Java mein ek aisa Map hai jo thoda "suspicious" hai.
 * Wo har cheez ko uske CONTENT se nahi, uske ADDRESS se pehchanta hai.
 *
 * Normal HashMap bolega: "Arre yaar, dono ka naam 'Rahul' hai, same hi hain!"
 * IdentityHashMap bolega: "Nahi bhai, ek Rahul Sector-5 mein rehta hai,
 *                          doosra Sector-9 mein. ALAG hain dono!"
 *
 * ============================================================
 *  CORE CONCEPT: == vs .equals()
 * ============================================================
 *
 *  HashMap     → uses key.equals()  → compares CONTENT (value equality)
 *  IdentityHashMap → uses key == key → compares REFERENCE (memory address)
 *
 *  Simple rule:
 *    HashMap     = "Kya tum same dikhte ho?" (content check)
 *    IdentityHashMap = "Kya tum same INSAAN ho?" (reference/identity check)
 *
 * ============================================================
 *  INTERNAL WORKING
 * ============================================================
 *
 *  - Uses a simple linear-probe hash table (NOT chaining like HashMap)
 *  - Hash is computed using System.identityHashCode(key)
 *    → This is based on the object's memory address (default Object.hashCode())
 *  - Even if you override hashCode() and equals() in your class,
 *    IdentityHashMap IGNORES them completely. Savage hai yeh. 😤
 *  - Default initial capacity: 32 (HashMap ka 16 hota hai)
 *  - Load factor: fixed at 2/3 (HashMap ka 0.75 hota hai)
 *
 * ============================================================
 *  WHERE TO USE (Industry Use Cases)
 * ============================================================
 *
 *  1. SERIALIZATION / DEEP COPY
 *     → Track which objects have already been visited/serialized
 *     → Agar same object do jagah reference ho, toh duplicate copy mat banao
 *     → Java's own ObjectOutputStream uses this internally!
 *
 *  2. OBJECT GRAPH TRAVERSAL (Cycle Detection)
 *     → Jab tum ek graph/tree traverse karo aur cycles detect karni ho
 *     → "Is this exact object already visited?" — IdentityHashMap perfect hai
 *
 *  3. PROXY / AOP FRAMEWORKS
 *     → Spring, Hibernate jaise frameworks proxy objects banate hain
 *     → Unhe track karna hota hai by reference, not by value
 *
 *  4. MEMORY-SENSITIVE CACHES
 *     → Jab cache key ek specific object INSTANCE ho, not its value
 *     → E.g., Class objects as keys (Class objects are singletons per classloader)
 *
 *  5. COMPILER / INTERPRETER INTERNALS
 *     → Symbol tables mein jahan same name ke alag variables alag hote hain
 *
 * ============================================================
 *  WHEN NOT TO USE
 * ============================================================
 *
 *  ❌ Jab tumhe String keys use karni ho normally
 *     (String pool ki wajah se kabhi kabhi same reference milega, kabhi nahi)
 *  ❌ Jab content-based equality chahiye
 *  ❌ General purpose key-value storage ke liye
 *
 * ============================================================
 *  NOT THREAD SAFE ⚠️
 * ============================================================
 *  IdentityHashMap thread-safe nahi hai.
 *  Multi-threaded use ke liye:
 *  Collections.synchronizedMap(new IdentityHashMap<>()) use karo
 *
 */
public class IdentityHashMapExplained {

    public static void main(String[] args) {

        // ============================================================
        // DEMO 1: The Classic "Same Content, Different Object" Problem
        // ============================================================

        System.out.println("===== DEMO 1: HashMap vs IdentityHashMap =====\n");

        // new String() forces a NEW object in heap (not from String pool)
        // Dono ka content same hai: "Rahul"
        // Lekin memory mein alag-alag objects hain
        String key1 = new String("Rahul");
        String key2 = new String("Rahul");

        // Proof karte hain ki yeh alag objects hain
        System.out.println("key1 == key2 (same reference?): " + (key1 == key2));         // false
        System.out.println("key1.equals(key2) (same content?): " + key1.equals(key2));   // true

        // --- HashMap: content-based (uses .equals()) ---
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(key1, "Engineer");
        hashMap.put(key2, "Doctor"); // key2.equals(key1) → true → OVERWRITES key1's value

        System.out.println("\nHashMap size (should be 1): " + hashMap.size());
        System.out.println("HashMap value: " + hashMap.get(key1)); // "Doctor" — overwritten!

        // --- IdentityHashMap: reference-based (uses ==) ---
        Map<String, String> identityMap = new IdentityHashMap<>();
        identityMap.put(key1, "Engineer");
        identityMap.put(key2, "Doctor"); // key1 != key2 (different objects) → SEPARATE entries

        System.out.println("\nIdentityHashMap size (should be 2): " + identityMap.size());
        System.out.println("IdentityHashMap value for key1: " + identityMap.get(key1)); // "Engineer"
        System.out.println("IdentityHashMap value for key2: " + identityMap.get(key2)); // "Doctor"

        // ============================================================
        // DEMO 2: String Pool Trap 🪤
        // ============================================================

        System.out.println("\n===== DEMO 2: String Pool Trap =====\n");

        // String literals come from the String pool → SAME reference
        String poolKey1 = "Nitesh";
        String poolKey2 = "Nitesh"; // Same object from pool!

        System.out.println("poolKey1 == poolKey2: " + (poolKey1 == poolKey2)); // true (same pool object)

        Map<String, String> identityMap2 = new IdentityHashMap<>();
        identityMap2.put(poolKey1, "Backend Dev");
        identityMap2.put(poolKey2, "Full Stack Dev"); // Same reference → OVERWRITES

        // Yahan size 1 hoga kyunki dono same object hain (String pool)
        System.out.println("Size with pool strings (should be 1): " + identityMap2.size());
        // Lesson: IdentityHashMap ke saath new String() use karo, literals nahi

        // ============================================================
        // DEMO 3: Real Industry Use Case — Object Graph / Cycle Detection
        // ============================================================

        System.out.println("\n===== DEMO 3: Cycle Detection in Object Graph =====\n");

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");

        // Create a cycle: A → B → C → A
        nodeA.next = nodeB;
        nodeB.next = nodeC;
        nodeC.next = nodeA; // cycle!

        System.out.println("Traversing graph with cycle detection:");
        traverseWithCycleDetection(nodeA);

        // ============================================================
        // DEMO 4: Real Industry Use Case — Tracking Object Instances
        //         (like a lightweight serialization visited-set)
        // ============================================================

        System.out.println("\n===== DEMO 4: Serialization-style Object Tracking =====\n");

        // Imagine these are complex domain objects
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = obj1; // same reference as obj1!

        // IdentityHashMap as a "visited" tracker
        // Value = the serialized ID we assigned
        IdentityHashMap<Object, Integer> visited = new IdentityHashMap<>();
        int idCounter = 1;

        // Process obj1
        if (!visited.containsKey(obj1)) {
            visited.put(obj1, idCounter++);
            System.out.println("Serializing obj1 with ID: " + visited.get(obj1));
        }

        // Process obj2
        if (!visited.containsKey(obj2)) {
            visited.put(obj2, idCounter++);
            System.out.println("Serializing obj2 with ID: " + visited.get(obj2));
        }

        // Process obj3 — same reference as obj1, should NOT serialize again
        if (!visited.containsKey(obj3)) {
            visited.put(obj3, idCounter++);
            System.out.println("Serializing obj3 with ID: " + visited.get(obj3));
        } else {
            // IdentityHashMap correctly identifies obj3 == obj1
            System.out.println("obj3 already serialized! (it's the same object as obj1, ID: " + visited.get(obj3) + ")");
        }

        // ============================================================
        // DEMO 5: Professional Pattern — Using Class Objects as Keys
        // ============================================================

        System.out.println("\n===== DEMO 5: Class Objects as Keys (Professional Pattern) =====\n");

        // Class objects are singletons per ClassLoader
        // IdentityHashMap is perfect here — no need for .equals() overhead
        // This pattern is used in DI frameworks like Spring internally

        IdentityHashMap<Class<?>, String> typeRegistry = new IdentityHashMap<>();
        typeRegistry.put(String.class, "STRING_HANDLER");
        typeRegistry.put(Integer.class, "INTEGER_HANDLER");
        typeRegistry.put(Double.class, "DOUBLE_HANDLER");

        // Lookup by class type
        Object someValue = "Hello World";
        String handler = typeRegistry.get(someValue.getClass());
        System.out.println("Handler for String type: " + handler); // STRING_HANDLER

        someValue = 42;
        handler = typeRegistry.get(someValue.getClass());
        System.out.println("Handler for Integer type: " + handler); // INTEGER_HANDLER

        // ============================================================
        // DEMO 6: Performance Note
        // ============================================================

        System.out.println("\n===== DEMO 6: Quick Performance Comparison =====\n");

        int iterations = 1_000_000;

        // HashMap benchmark
        Map<String, Integer> hm = new HashMap<>();
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            hm.put("key" + i, i);
        }
        long hmTime = System.nanoTime() - start;

        // IdentityHashMap benchmark
        // Note: using new String() to ensure unique references
        Map<String, Integer> ihm = new IdentityHashMap<>();
        String[] keys = new String[iterations];
        for (int i = 0; i < iterations; i++) keys[i] = new String("key" + i);

        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            ihm.put(keys[i], i);
        }
        long ihmTime = System.nanoTime() - start;

        System.out.println("HashMap insert time (ms):         " + hmTime / 1_000_000);
        System.out.println("IdentityHashMap insert time (ms): " + ihmTime / 1_000_000);
        System.out.println("(IdentityHashMap can be faster because it skips .equals() and .hashCode() calls)");

        System.out.println("\n===== ALL DEMOS COMPLETE =====");
    }

    /**
     * Traverses a linked-list style graph and detects cycles using IdentityHashMap.
     *
     * Why IdentityHashMap here?
     * → We want to track EXACT object instances we've visited.
     * → Two nodes could theoretically have the same "name" but be different objects.
     * → We care about object identity, not content equality.
     *
     * Industry use: Deep clone, garbage collector simulation, JSON serializers (Jackson, Gson)
     */
    private static void traverseWithCycleDetection(Node start) {
        // Using IdentityHashMap as a visited set (value = true, just a marker)
        IdentityHashMap<Node, Boolean> visited = new IdentityHashMap<>();

        Node current = start;
        while (current != null) {
            if (visited.containsKey(current)) {
                // Same object reference encountered again → cycle detected!
                System.out.println("Cycle detected at node: " + current.name + " → stopping traversal");
                break;
            }
            visited.put(current, Boolean.TRUE);
            System.out.println("Visiting node: " + current.name);
            current = current.next;
        }
    }

    /**
     * Simple Node class to simulate an object graph / linked list with potential cycles.
     */
    static class Node {
        String name;
        Node next;

        Node(String name) {
            this.name = name;
        }
    }
}

/*
 * ============================================================
 *  QUICK REFERENCE CHEAT SHEET
 * ============================================================
 *
 *  Feature              | HashMap          | IdentityHashMap
 *  ---------------------|------------------|------------------
 *  Key comparison       | .equals()        | ==
 *  Hash computation     | key.hashCode()   | System.identityHashCode(key)
 *  Respects overrides?  | Yes              | No (ignores them)
 *  Null keys allowed?   | Yes (one)        | Yes (one)
 *  Thread safe?         | No               | No
 *  Default capacity     | 16               | 32
 *  Load factor          | 0.75             | 2/3
 *  Collision strategy   | Chaining/Tree    | Linear probing
 *  Use case             | General purpose  | Reference tracking, serialization, proxies
 *
 * ============================================================
 *  PROFESSIONAL TIPS (Industry Best Practices)
 * ============================================================
 *
 *  ✅ Use IdentityHashMap when you need to track object INSTANCES, not values
 *  ✅ Prefer it in serialization/deserialization pipelines to avoid infinite loops
 *  ✅ Use it in framework internals where proxy objects must be tracked by reference
 *  ✅ Great for implementing deep-copy utilities
 *  ✅ Use Collections.synchronizedMap() wrapper for thread safety
 *
 *  ⚠️  Never use String literals as keys if you expect separate entries
 *      (String pool will give you the same reference → single entry)
 *  ⚠️  Don't use it as a drop-in replacement for HashMap — semantics are different
 *  ⚠️  Document clearly WHY you're using IdentityHashMap — it surprises teammates
 *
 * ============================================================
 *  HINGLISH SUMMARY (For the dil se samajhne wale 😄)
 * ============================================================
 *
 *  HashMap ek aisa dost hai jo bolega:
 *  "Yaar, dono ka naam Rahul hai, toh same hi hain na?"
 *
 *  IdentityHashMap ek aisa suspicious dost hai jo bolega:
 *  "Bhai, naam same ho sakta hai, lekin Aadhar card check karo!
 *   Ek Rahul Pune mein rehta hai, doosra Delhi mein.
 *   Mujhe address chahiye, naam nahi!"
 *
 *  Jab use karo:
 *  → Jab tumhe pata ho ki same content wale alag objects alag treat hone chahiye
 *  → Jab tum object graph traverse kar rahe ho (cycle detection)
 *  → Jab tum serialization likh rahe ho
 *  → Jab framework/library internals likh rahe ho
 *
 *  Jab mat use karo:
 *  → Normal key-value storage ke liye
 *  → Jab String literals keys hain
 *  → Jab tumhe content equality chahiye
 *
 * ============================================================
 */
