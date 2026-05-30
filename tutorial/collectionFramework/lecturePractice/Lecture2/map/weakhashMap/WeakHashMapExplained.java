package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Lecture2.map.weakhashMap;

import java.util.*;
import java.lang.ref.*;

/**
 * ============================================================
 *   WeakHashMap — COMPLETE GUIDE
 *   (Beginner se Advanced tak, thoda Hinglish mein bhi 😄)
 * ============================================================
 *
 *  PEHLE EK STORY:
 *  ─────────────────────────────────────────────────────────
 *  Socho tumhare paas ek HashMap hai jisme tum objects store karte ho.
 *  Jab tak object ka reference kisi variable mein hai, HashMap usse
 *  pakde rehta hai — chahe tumhe uski zaroorat ho ya na ho.
 *
 *  Ye ek possessive boyfriend/girlfriend jaisa hai:
 *  "Main tujhe tab tak nahi chhodunga jab tak tu khud nahi jaata!" 😤
 *
 *  WeakHashMap zyada samajhdar hai:
 *  "Agar koi aur tujhe nahi pakad raha, toh main bhi nahi pakdunga.
 *   GC bhai aa, isko le ja!" 🤝
 *
 * ============================================================
 *   PART 1: JAVA MEMORY & REFERENCES — THE FOUNDATION
 * ============================================================
 */
public class WeakHashMapExplained {

    // ─────────────────────────────────────────────────────────────
    // SECTION 1: JAVA REFERENCE TYPES (4 types — must know first)
    // ─────────────────────────────────────────────────────────────

    /*
     *  Java mein 4 tarah ke references hote hain:
     *
     *  1. STRONG REFERENCE (default — jo hum normally use karte hain)
     *     Object obj = new Object();
     *     → Jab tak 'obj' variable exist karta hai, GC KABHI nahi hatayega.
     *     → "Meri pakad bahut mazboot hai!" 💪
     *
     *  2. SOFT REFERENCE
     *     SoftReference<Object> soft = new SoftReference<>(new Object());
     *     → GC tab hatata hai jab memory BAHUT kam ho jaaye (OutOfMemory se pehle).
     *     → "Thoda pressure aaya toh chhor dunga." 😅
     *     → Use: image cache, large object cache
     *
     *  3. WEAK REFERENCE  ← WeakHashMap yahi use karta hai
     *     WeakReference<Object> weak = new WeakReference<>(new Object());
     *     → GC NEXT CYCLE mein hi hata deta hai agar koi strong ref nahi.
     *     → "Koi aur nahi pakad raha? Chalo bhai, bye!" 👋
     *     → Use: WeakHashMap, listener cleanup
     *
     *  4. PHANTOM REFERENCE
     *     PhantomReference<Object> phantom = new PhantomReference<>(obj, queue);
     *     → Object already GC ho chuka hota hai, sirf cleanup ke liye.
     *     → "Main toh ghost hoon, already gone!" 👻
     *     → Use: resource cleanup, off-heap memory management
     *
     *  STRENGTH ORDER:
     *  Strong > Soft > Weak > Phantom
     *  (GC pehle Phantom hatata hai, phir Weak, phir Soft, Strong kabhi nahi)
     */

    static void referenceTypesDemo() {
        // STRONG reference — normal Java
        String strong = new String("I am strong");
        // strong = null; // tabhi GC eligible hoga

        // SOFT reference — memory pressure pe GC karta hai
        SoftReference<String> soft = new SoftReference<>(new String("I am soft"));
        String softVal = soft.get(); // null ho sakta hai agar GC ne hata diya

        // WEAK reference — next GC cycle pe gone
        WeakReference<String> weak = new WeakReference<>(new String("I am weak"));
        String weakVal = weak.get(); // null ho sakta hai BAHUT jaldi

        // Phantom reference — cleanup ke liye, get() always returns null
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        PhantomReference<String> phantom = new PhantomReference<>(new String("ghost"), queue);
        // phantom.get() → always null
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 2: WHAT IS WeakHashMap?
    // ─────────────────────────────────────────────────────────────

    /*
     *  WeakHashMap = HashMap + Weak References on KEYS
     *
     *  Normal HashMap:
     *    map.put(key, value)
     *    → HashMap strongly holds the KEY
     *    → Even if YOU set key = null, HashMap still holds it
     *    → Entry NEVER gets removed unless you call map.remove(key)
     *    → MEMORY LEAK ka baap! 😱
     *
     *  WeakHashMap:
     *    map.put(key, value)
     *    → WeakHashMap weakly holds the KEY
     *    → Jab koi aur strong reference nahi raha key ka...
     *    → GC aata hai, key ko hata deta hai
     *    → WeakHashMap automatically entry remove kar deta hai!
     *    → Self-cleaning map! 🧹
     *
     *  IMPORTANT: Sirf KEY weakly held hoti hai, VALUE strongly held hoti hai.
     *  Agar value mein key ka reference hai, toh key kabhi GC nahi hogi!
     *  (Ye ek common mistake hai — _11_CommonMistakes jaisi cheez 😄)
     *
     *  PACKAGE: java.util.WeakHashMap
     *  IMPLEMENTS: Map<K, V>
     *  THREAD-SAFE: NO (use Collections.synchronizedMap() for thread safety)
     */

    static void basicWeakHashMapDemo() {

        WeakHashMap<Object, String> weakMap = new WeakHashMap<>();

        // Key banao — strong reference ke saath
        Object key1 = new Object();  // strong reference
        Object key2 = new Object();  // strong reference

        weakMap.put(key1, "Value for key1");
        weakMap.put(key2, "Value for key2");

        // Abhi map mein 2 entries hain
        // weakMap.size() → 2

        // key1 ka strong reference hata do
        key1 = null;  // ab sirf WeakHashMap ke paas weak reference hai

        // GC ko force karo (production mein mat karo, sirf demo ke liye)
        System.gc();

        // Thoda wait karo GC ke liye
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // Ab map mein sirf 1 entry hogi (key2 wali)
        // key1 wali entry automatically remove ho gayi!
        // weakMap.size() → 1 (approximately, GC timing pe depend karta hai)

        // key2 abhi bhi strong reference ke saath hai, toh wo safe hai
        // weakMap.get(key2) → "Value for key2"  ✅
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 3: HashMap vs WeakHashMap — Side by Side
    // ─────────────────────────────────────────────────────────────

    /*
     *  ┌─────────────────────┬──────────────────────┬──────────────────────┐
     *  │  Feature            │  HashMap             │  WeakHashMap         │
     *  ├─────────────────────┼──────────────────────┼──────────────────────┤
     *  │  Key reference      │  Strong              │  Weak                │
     *  │  Auto cleanup       │  No                  │  Yes (by GC)         │
     *  │  Memory leak risk   │  High                │  Low                 │
     *  │  Predictable size   │  Yes                 │  No (GC decides)     │
     *  │  Thread-safe        │  No                  │  No                  │
     *  │  Null key allowed   │  Yes (1 null key)    │  Yes (1 null key)    │
     *  │  Null value allowed │  Yes                 │  Yes                 │
     *  │  Performance        │  Slightly faster     │  Slightly slower     │
     *  │  Use case           │  General purpose     │  Cache/metadata      │
     *  └─────────────────────┴──────────────────────┴──────────────────────┘
     *
     *  HINGLISH SUMMARY:
     *  HashMap: "Main sab kuch pakad ke rakhta hoon, memory bhar jaaye toh bhar jaaye!"
     *  WeakHashMap: "Jab zaroorat nahi, main khud chhor deta hoon. Smart hoon main!" 😎
     */

    static void hashMapVsWeakHashMap() {

        // ── HashMap — memory leak scenario ──────────────────────
        Map<Object, String> hashMap = new HashMap<>();

        for (int i = 0; i < 1000; i++) {
            Object tempKey = new Object();  // temporary object
            hashMap.put(tempKey, "data-" + i);
            // tempKey local variable scope se bahar jaata hai
            // BUT HashMap strongly holds it → 1000 objects memory mein rahenge FOREVER
            // Ye ek classic memory leak hai!
        }
        // hashMap.size() → 1000 (sab entries hain, koi nahi gaya)

        // ── WeakHashMap — automatic cleanup ─────────────────────
        Map<Object, String> weakMap = new WeakHashMap<>();

        for (int i = 0; i < 1000; i++) {
            Object tempKey = new Object();  // temporary object
            weakMap.put(tempKey, "data-" + i);
            // tempKey loop ke baad scope se bahar jaata hai
            // WeakHashMap weakly holds it → GC aayega aur sab hata dega
        }
        System.gc();
        // weakMap.size() → ~0 (GC ne sab hata diya, memory free!)
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 4: HOW WeakHashMap WORKS INTERNALLY
    // ─────────────────────────────────────────────────────────────

    /*
     *  Andar se kya hota hai? (Internals)
     *
     *  1. WeakHashMap internally ek array of Entry[] rakhta hai (HashMap jaisa).
     *
     *  2. Har Entry mein key ek WeakReference ke andar wrapped hoti hai:
     *
     *     private static class Entry<K,V> extends WeakReference<Object>
     *         implements Map.Entry<K,V> {
     *         V value;
     *         int hash;
     *         Entry<K,V> next;
     *         // key is stored as WeakReference (this extends WeakReference)
     *     }
     *
     *  3. Ek ReferenceQueue bhi hoti hai:
     *     private final ReferenceQueue<Object> queue = new ReferenceQueue<>();
     *
     *  4. Jab GC kisi key ko collect karta hai:
     *     → GC us WeakReference ko ReferenceQueue mein daal deta hai
     *     → WeakHashMap ko pata chal jaata hai ki "ye key gone hai"
     *
     *  5. Jab bhi tum WeakHashMap pe koi operation karte ho
     *     (get, put, size, etc.), ye internally expungeStaleEntries() call karta hai:
     *     → Queue check karta hai
     *     → Jo bhi stale entries hain (key GC ho gayi), unhe remove karta hai
     *     → Isliye WeakHashMap "lazily" clean hota hai — operation pe, not immediately
     *
     *  FLOW:
     *
     *  key = null (strong ref gone)
     *       ↓
     *  GC runs → collects key object
     *       ↓
     *  WeakReference added to ReferenceQueue
     *       ↓
     *  Next WeakHashMap operation (get/put/size)
     *       ↓
     *  expungeStaleEntries() called
     *       ↓
     *  Entry removed from map
     *
     *  IMPORTANT: Entry is NOT removed IMMEDIATELY when key is GC'd.
     *  It's removed on the NEXT operation on the map.
     *  Isliye size() ka result GC timing pe depend karta hai.
     */

    static void internalWorkingDemo() {

        WeakHashMap<Object, String> map = new WeakHashMap<>();

        Object key = new Object();
        map.put(key, "hello");

        // Abhi: map mein 1 entry hai, key strongly referenced hai
        // map.size() → 1

        key = null;  // strong reference gone

        // ABHI BHI map.size() → 1 ho sakta hai!
        // Kyunki GC abhi nahi chala, aur expungeStaleEntries() abhi nahi chali

        System.gc();  // GC ko hint do

        // Ab map pe koi operation karo — ye expungeStaleEntries() trigger karega
        int size = map.size();  // internally stale entries clean karega
        // size → 0 (most likely, but not guaranteed — GC timing)

        // LESSON: WeakHashMap ka size() reliable nahi hai for exact counts.
        // Isko "approximately empty" ya "approximately N" samjho.
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 5: REAL-WORLD USE CASES
    // ─────────────────────────────────────────────────────────────

    /*
     *  WeakHashMap kahan use hota hai? (Industry mein)
     *
     *  RULE OF THUMB:
     *  "Jab cache/metadata ka lifetime us object ke saath tied ho
     *   jiske liye wo cache/metadata hai."
     *
     *  Matlab: Object jab tak zinda hai, data raho.
     *          Object gaya, data bhi jao. Automatically.
     */

    // ── USE CASE 1: Object Metadata / Attribute Cache ────────────
    /*
     *  Scenario: Tum kisi object ke baare mein extra info store karna chahte ho
     *  bina us object ko modify kiye (open/closed principle).
     *
     *  Example: Ek framework jo objects ke "last accessed time" track karta hai.
     *  Jab object GC ho jaaye, metadata bhi automatically chala jaaye.
     */

    // Metadata store — object ke saath tied, object gaya toh metadata bhi gaya
    private static final WeakHashMap<Object, Map<String, Object>> metadataStore
            = new WeakHashMap<>();

    static void attachMetadata(Object obj, String key, Object value) {
        // computeIfAbsent: agar obj ka entry nahi hai toh naya HashMap banao
        metadataStore.computeIfAbsent(obj, k -> new HashMap<>()).put(key, value);
    }

    static Object getMetadata(Object obj, String key) {
        Map<String, Object> meta = metadataStore.get(obj);
        return meta != null ? meta.get(key) : null;
    }

    static void metadataDemoUsage() {
        Object myObject = new Object();

        attachMetadata(myObject, "createdAt", System.currentTimeMillis());
        attachMetadata(myObject, "accessCount", 0);

        // Use the object...
        Object createdAt = getMetadata(myObject, "createdAt");

        // Jab myObject = null ho jaaye aur GC chale,
        // metadataStore se entry automatically remove ho jaayegi.
        // Koi manual cleanup nahi chahiye!
        myObject = null;
        // System.gc() → metadataStore automatically empty ho jaayega
    }


    // ── USE CASE 2: Event Listener / Observer Pattern ────────────
    /*
     *  CLASSIC MEMORY LEAK in Java:
     *  Tum ek listener register karte ho, aur unregister karna bhool jaate ho.
     *  Listener ke paas strong reference hai → object kabhi GC nahi hota.
     *  Ye "lapsed listener problem" hai — bahut common bug!
     *
     *  WeakHashMap se fix:
     *  Listeners ko weakly hold karo. Jab listener object GC ho jaaye,
     *  automatically unregistered ho jaata hai.
     */

    // Event system with automatic listener cleanup
    static class EventBus {

        // Key = listener object (weakly held)
        // Value = event type string
        private final WeakHashMap<Object, String> listeners = new WeakHashMap<>();

        void subscribe(Object listener, String eventType) {
            listeners.put(listener, eventType);
            // Listener weakly held — agar listener GC ho gaya, auto-unsubscribed!
        }

        void publish(String eventType) {
            // Iterate over listeners — stale ones already cleaned by WeakHashMap
            listeners.forEach((listener, type) -> {
                if (type.equals(eventType)) {
                    // notify listener...
                    // In real code: ((EventListener) listener).onEvent(eventType);
                }
            });
        }

        int listenerCount() {
            return listeners.size();  // approximate — GC timing pe depend
        }
    }

    static void listenerDemoUsage() {
        EventBus bus = new EventBus();

        // Listeners banao
        Object listener1 = new Object();  // imagine ye ek UI component hai
        Object listener2 = new Object();

        bus.subscribe(listener1, "USER_LOGIN");
        bus.subscribe(listener2, "USER_LOGIN");
        // bus.listenerCount() → 2

        // listener1 destroy ho gaya (UI component close hua)
        listener1 = null;
        System.gc();

        // bus.listenerCount() → ~1 (listener1 automatically unsubscribed!)
        // Koi manual removeListener() call nahi karna pada!
    }


    // ── USE CASE 3: In-Memory Cache (Object-Scoped) ──────────────
    /*
     *  Scenario: Tum kisi object ke liye computed results cache karna chahte ho.
     *  Jab object gone, cache bhi gone. Perfect!
     *
     *  Example: Database entity ke liye computed display strings cache karna.
     */

    // Simulated entity class
    static class User {
        final long id;
        final String firstName;
        final String lastName;

        User(long id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    // Cache: User object → formatted display name (expensive to compute)
    private static final WeakHashMap<User, String> displayNameCache = new WeakHashMap<>();

    static String getDisplayName(User user) {
        // computeIfAbsent: cache miss pe compute karo, cache hit pe return karo
        return displayNameCache.computeIfAbsent(user,
            u -> u.firstName.toUpperCase() + " " + u.lastName.toUpperCase()
                 + " (ID: " + u.id + ")");
        // Jab User object GC ho jaaye, cache entry bhi automatically remove!
    }

    // ── USE CASE 4: Class-Level Metadata (Framework/Library use) ─
    /*
     *  Frameworks (Spring, Hibernate) often need to store metadata
     *  about classes or objects without modifying them.
     *
     *  Example: Hibernate stores entity metadata keyed by entity objects.
     *  When entity is detached and GC'd, metadata auto-cleans.
     *
     *  Spring's AbstractBeanFactory uses WeakHashMap internally
     *  for merged bean definition caching.
     */

    // Framework-style: store processing metadata per object
    static class ObjectProcessor {

        // Stores processing state for each object being processed
        // When object is done and GC'd, state auto-removes
        private final WeakHashMap<Object, ProcessingState> processingStates
                = new WeakHashMap<>();

        void startProcessing(Object obj) {
            processingStates.put(obj, new ProcessingState(System.currentTimeMillis()));
        }

        boolean isProcessing(Object obj) {
            return processingStates.containsKey(obj);
        }

        void finishProcessing(Object obj) {
            processingStates.remove(obj);
            // Even if we forget to call this, GC will clean up eventually
        }

        static class ProcessingState {
            final long startTime;
            ProcessingState(long startTime) { this.startTime = startTime; }
        }
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 6: COMMON TRAPS & MISTAKES (Bahut important!)
    // ─────────────────────────────────────────────────────────────

    static void commonMistakesDemo() {

        // ── TRAP 1: String literals as keys — NEVER works as expected ──
        /*
         *  String literals are INTERNED by JVM — they live in String Pool
         *  and are NEVER garbage collected (they have permanent strong refs).
         *  WeakHashMap with String literal keys = basically a regular HashMap!
         *  Entries will NEVER be auto-removed.
         *
         *  Hinglish: String literal ek permanent resident hai JVM mein.
         *  GC usse kabhi nahi hatata. Toh WeakHashMap ka fayda hi kya? 😅
         */

        WeakHashMap<String, String> badMap = new WeakHashMap<>();

        // BAD — String literals are never GC'd
        badMap.put("user:1001", "Nitesh");  // "user:1001" is interned, lives forever
        badMap.put("config", "value");       // same problem

        // GOOD — use 'new String()' to create a non-interned string
        // (but honestly, if you need String keys, just use HashMap)
        String key = new String("user:1001");  // NOT interned, can be GC'd
        badMap.put(key, "Nitesh");
        key = null;  // now it CAN be GC'd
        // System.gc() → entry removed

        // ── TRAP 2: Value holds reference to Key — circular trap ──
        /*
         *  Agar value mein key ka strong reference hai, toh key kabhi GC nahi hogi!
         *  WeakHashMap ka poora point fail ho jaata hai.
         *
         *  Hinglish: Value ne key ko pakad liya — "Main tujhe jaane nahi dunga!" 😤
         *  GC bechara kuch nahi kar sakta.
         */

        WeakHashMap<Object, Object[]> trapMap = new WeakHashMap<>();

        Object myKey = new Object();
        // BAD: value array mein key ka reference store kar rahe hain
        trapMap.put(myKey, new Object[]{myKey, "some data"});
        //                               ↑ value holds strong ref to key!

        myKey = null;  // strong ref gone... but value still holds it!
        System.gc();
        // trapMap.size() → STILL 1! Key never GC'd because value holds it.

        // GOOD: value mein key ka reference mat rakho
        Object safeKey = new Object();
        trapMap.put(safeKey, new Object[]{"just data", 42});
        safeKey = null;
        System.gc();
        // trapMap.size() → 0 ✅

        // ── TRAP 3: Primitive types as keys ──────────────────────
        /*
         *  Primitives (int, long) can't be keys directly.
         *  Autoboxing creates Integer/Long objects.
         *  Small integers (-128 to 127) are CACHED by JVM (Integer.valueOf cache).
         *  These cached Integer objects are never GC'd!
         *
         *  Hinglish: Integer.valueOf(1) ek permanent object hai JVM mein.
         *  Toh WeakHashMap<Integer, ...> with small ints = memory leak!
         */

        WeakHashMap<Integer, String> intMap = new WeakHashMap<>();
        intMap.put(1, "one");    // Integer.valueOf(1) — cached, never GC'd!
        intMap.put(200, "two hundred");  // new Integer(200) — can be GC'd

        // ── TRAP 4: Iterating while GC can modify ────────────────
        /*
         *  WeakHashMap ka size() aur iteration unpredictable hai.
         *  GC kisi bhi time entry remove kar sakta hai.
         *  Iteration ke dauran size change ho sakta hai.
         *
         *  Ye ConcurrentModificationException nahi deta (unlike HashMap),
         *  but results inconsistent ho sakte hain.
         */

        WeakHashMap<Object, String> iterMap = new WeakHashMap<>();
        // Safe iteration: snapshot leke iterate karo
        Set<Map.Entry<Object, String>> snapshot = new HashSet<>(iterMap.entrySet());
        for (Map.Entry<Object, String> entry : snapshot) {
            // Safe — snapshot pe iterate kar rahe hain, original nahi
        }
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 7: THREAD SAFETY — WeakHashMap is NOT thread-safe
    // ─────────────────────────────────────────────────────────────

    /*
     *  WeakHashMap is NOT synchronized.
     *  Multiple threads simultaneously access karna = data corruption / exceptions.
     *
     *  OPTIONS for thread-safe weak maps:
     *
     *  1. Collections.synchronizedMap() — simple but coarse-grained lock
     *  2. ConcurrentHashMap with WeakReference values — manual approach
     *  3. Guava's CacheBuilder with weakKeys() — BEST for production
     */

    static void threadSafetyDemo() {

        // OPTION 1: synchronizedMap wrapper (simple, but every operation locks)
        Map<Object, String> syncWeakMap =
                Collections.synchronizedMap(new WeakHashMap<>());

        // Use it like a normal map — all operations are synchronized
        Object key = new Object();
        syncWeakMap.put(key, "value");
        syncWeakMap.get(key);

        // BUT: iteration still needs external synchronization!
        synchronized (syncWeakMap) {
            for (Map.Entry<Object, String> entry : syncWeakMap.entrySet()) {
                // safe iteration
            }
        }

        // OPTION 2: Guava Cache with weakKeys (RECOMMENDED for production)
        /*
         *  // pom.xml:
         *  // <dependency>
         *  //     <groupId>com.google.guava</groupId>
         *  //     <artifactId>guava</artifactId>
         *  //     <version>32.1.3-jre</version>
         *  // </dependency>
         *
         *  Cache<Object, String> guavaCache = CacheBuilder.newBuilder()
         *      .weakKeys()           // keys weakly referenced
         *      .weakValues()         // values bhi weakly referenced (optional)
         *      .maximumSize(1000)    // max entries
         *      .expireAfterAccess(10, TimeUnit.MINUTES)  // TTL
         *      .concurrencyLevel(4)  // thread-safe with 4 concurrent writers
         *      .build();
         *
         *  guavaCache.put(key, "value");
         *  String val = guavaCache.getIfPresent(key);
         *
         *  Guava Cache advantages over raw WeakHashMap:
         *    - Thread-safe by default
         *    - TTL support
         *    - Max size limit
         *    - Statistics (hit rate, miss rate)
         *    - Loading cache (auto-load on miss)
         */
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 8: INDUSTRY USAGE — Real frameworks mein kahan hai?
    // ─────────────────────────────────────────────────────────────

    /*
     *  WeakHashMap real production code mein kahan milta hai?
     *
     *  1. JAVA STANDARD LIBRARY:
     *     - java.util.logging.Logger: logger hierarchy caching
     *     - java.lang.ThreadLocal: thread-local variable cleanup
     *     - java.lang.reflect: reflection metadata caching
     *
     *  2. SPRING FRAMEWORK:
     *     - AbstractBeanFactory: merged bean definition cache
     *       (WeakHashMap<Class<?>, String[]> keyed by class objects)
     *     - ReflectionUtils: method/field caching
     *     - ConcurrentReferenceHashMap (Spring's own implementation)
     *       used in many places for weak-key caching
     *
     *  3. HIBERNATE / JPA:
     *     - Session-level entity cache (first-level cache)
     *     - Proxy object metadata storage
     *     - Type resolution caching
     *
     *  4. ANDROID:
     *     - Bitmap caching (before LruCache was introduced)
     *     - View tag storage
     *     - WeakHashMap used extensively to avoid Activity memory leaks
     *
     *  5. GUAVA (Google's library):
     *     - CacheBuilder.weakKeys() internally uses WeakHashMap concepts
     *     - ClassToInstanceMap with weak references
     *
     *  6. JUNIT / TESTING FRAMEWORKS:
     *     - Test context caching keyed by test class objects
     *
     *  REAL CODE EXAMPLE from Spring Framework (simplified):
     *
     *  // In AbstractBeanFactory.java (Spring source code):
     *  private final Map<Class<?>, String[]> allBeanNamesByType =
     *      new ConcurrentHashMap<>(64);
     *
     *  // In ReflectionUtils.java (Spring source code):
     *  private static final Map<Class<?>, Method[]> declaredMethodsCache =
     *      new ConcurrentReferenceHashMap<>(256);
     *  // ConcurrentReferenceHashMap is Spring's thread-safe WeakHashMap equivalent
     */

    // ─────────────────────────────────────────────────────────────
    // SECTION 9: PROFESSIONAL PATTERN — The "Canonicalizing Mapping"
    // ─────────────────────────────────────────────────────────────

    /*
     *  "Canonicalizing Mapping" — Effective Java (Joshua Bloch) mein mentioned.
     *
     *  Problem: Tum chahte ho ki same logical value ke liye same object return ho.
     *  (Like String interning, but for your custom objects)
     *
     *  WeakHashMap perfect hai iske liye:
     *  - Agar object already exist karta hai → same object return karo
     *  - Agar koi use nahi kar raha → GC hata dega, next time naya banao
     */

    static class CanonicalCache<T> {

        // WeakHashMap: key aur value dono same object hain
        // Jab koi strong reference nahi → automatically removed
        private final WeakHashMap<T, WeakReference<T>> cache = new WeakHashMap<>();

        @SuppressWarnings("unchecked")
        T canonicalize(T obj) {
            WeakReference<T> ref = cache.get(obj);
            T cached = (ref != null) ? ref.get() : null;

            if (cached != null) {
                return cached;  // existing canonical instance return karo
            }

            // Naya canonical instance store karo
            cache.put(obj, new WeakReference<>(obj));
            return obj;
        }
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 10: WHEN TO USE vs WHEN NOT TO USE
    // ─────────────────────────────────────────────────────────────

    /*
     *  ✅ USE WeakHashMap WHEN:
     *
     *  1. Cache lifetime = key object lifetime
     *     "Jab tak object zinda hai, cache raho. Object gaya, cache bhi jao."
     *
     *  2. You want automatic memory management
     *     "Manual cleanup ka jhanjhat nahi chahiye."
     *
     *  3. Storing metadata/attributes for objects you don't own
     *     "Object mujhara nahi hai, main usse modify nahi kar sakta,
     *      but extra info store karni hai."
     *
     *  4. Listener/observer registration where auto-cleanup is desired
     *     "Listener destroy hua? Automatically unregister ho jaaye."
     *
     *  5. Single-threaded or properly synchronized contexts
     *
     *
     *  ❌ DON'T USE WeakHashMap WHEN:
     *
     *  1. Keys are String literals, primitives, or enum constants
     *     "Ye kabhi GC nahi hote — WeakHashMap ka koi fayda nahi."
     *
     *  2. You need guaranteed data retention
     *     "GC kisi bhi time data hata sakta hai — predictability nahi."
     *
     *  3. You need thread safety without extra synchronization
     *     "WeakHashMap thread-safe nahi hai."
     *
     *  4. General-purpose caching with TTL or size limits
     *     "Guava Cache ya Caffeine use karo — zyada features hain."
     *
     *  5. Value holds reference to key
     *     "Circular reference — GC kabhi nahi hatayega."
     *
     *  6. You need reliable size() or iteration
     *     "GC timing pe depend karta hai — unreliable."
     *
     *
     *  DECISION TREE:
     *
     *  Kya cache lifetime = key object lifetime?
     *    YES → WeakHashMap consider karo
     *      Kya thread-safe chahiye?
     *        YES → Collections.synchronizedMap(new WeakHashMap<>())
     *               ya Guava CacheBuilder.weakKeys()
     *        NO  → WeakHashMap directly
     *    NO  → Regular HashMap, ConcurrentHashMap, ya Guava Cache
     */

    // ─────────────────────────────────────────────────────────────
    // SECTION 11: COMPLETE PRACTICAL EXAMPLE
    //             (Production-ready pattern)
    // ─────────────────────────────────────────────────────────────

    /*
     *  Real scenario: HTTP Request processing mein per-request context store karna.
     *  Request object ke saath context tied hai.
     *  Request done? Context automatically cleanup.
     */

    static class RequestContextHolder {

        // Thread-safe weak map: request object → context data
        private static final Map<Object, Map<String, Object>> contextMap =
                Collections.synchronizedMap(new WeakHashMap<>());

        // Request ke liye context set karo
        static void setContext(Object request, String key, Object value) {
            contextMap.computeIfAbsent(request, k -> new HashMap<>()).put(key, value);
        }

        // Context retrieve karo
        static Object getContext(Object request, String key) {
            Map<String, Object> ctx = contextMap.get(request);
            return ctx != null ? ctx.get(key) : null;
        }

        // Manual cleanup (optional — WeakHashMap will auto-clean anyway)
        static void clearContext(Object request) {
            contextMap.remove(request);
        }

        // Kitne active requests hain (approximate)
        static int activeRequestCount() {
            return contextMap.size();
        }
    }

    static void requestContextDemo() {
        // Simulate HTTP requests
        Object request1 = new Object();  // imagine HttpServletRequest
        Object request2 = new Object();

        RequestContextHolder.setContext(request1, "userId", 1001L);
        RequestContextHolder.setContext(request1, "startTime", System.currentTimeMillis());
        RequestContextHolder.setContext(request2, "userId", 1002L);

        // Process requests...
        Long userId = (Long) RequestContextHolder.getContext(request1, "userId");
        // userId → 1001

        // Request1 processing done — in real code, request object goes out of scope
        request1 = null;
        System.gc();

        // request1's context automatically cleaned up!
        // RequestContextHolder.activeRequestCount() → ~1 (only request2 remains)
        // No memory leak, no manual cleanup needed.
    }


    // ─────────────────────────────────────────────────────────────
    // SECTION 12: QUICK REFERENCE CHEATSHEET
    // ─────────────────────────────────────────────────────────────

    /*
     *  CREATION:
     *    new WeakHashMap<>()                    // default capacity 16
     *    new WeakHashMap<>(initialCapacity)     // custom capacity
     *    new WeakHashMap<>(map)                 // copy from existing map
     *
     *  BASIC OPERATIONS (same as HashMap):
     *    map.put(key, value)                    // add/update
     *    map.get(key)                           // retrieve (null if absent or GC'd)
     *    map.remove(key)                        // manual remove
     *    map.containsKey(key)                   // check existence
     *    map.size()                             // approximate count
     *    map.isEmpty()                          // approximate check
     *    map.clear()                            // remove all entries
     *    map.keySet()                           // set of live keys
     *    map.values()                           // collection of values
     *    map.entrySet()                         // set of live entries
     *    map.computeIfAbsent(key, fn)           // get or compute
     *    map.getOrDefault(key, defaultVal)      // get with fallback
     *
     *  THREAD-SAFE WRAPPER:
     *    Collections.synchronizedMap(new WeakHashMap<>())
     *
     *  PRODUCTION ALTERNATIVE (Guava):
     *    CacheBuilder.newBuilder().weakKeys().build()
     *
     *  KEY RULES TO REMEMBER:
     *    1. Keys are weakly referenced, values are strongly referenced
     *    2. String literals / small Integers as keys = entries never removed
     *    3. Value must NOT hold strong reference to key
     *    4. NOT thread-safe — synchronize externally
     *    5. size() is approximate — GC timing dependent
     *    6. Cleanup is LAZY — happens on next map operation
     *    7. Implements Map<K,V> — drop-in replacement for HashMap in right scenarios
     *
     *  HINGLISH FINAL SUMMARY:
     *  ─────────────────────────────────────────────────────────
     *  WeakHashMap ek "samajhdar" map hai jo kehta hai:
     *  "Bhai, agar koi aur teri parwah nahi kar raha (no strong ref),
     *   toh main bhi nahi karunga. GC bhai, le ja isko!"
     *
     *  Ye memory leaks se bachata hai un situations mein jahan
     *  cache/metadata ka lifetime object ke lifetime se tied hona chahiye.
     *
     *  Production mein directly use karne se pehle socho:
     *  - Thread safety chahiye? → synchronizedMap ya Guava
     *  - TTL chahiye? → Guava CacheBuilder
     *  - Simple single-threaded metadata? → WeakHashMap directly
     *
     *  "Sahi jagah use karo, galat jagah mat karo — yahi professional hai!" 🎯
     */

    // ─────────────────────────────────────────────────────────────
    // MAIN METHOD — Run all demos
    // ─────────────────────────────────────────────────────────────

    public static void main(String[] args) throws InterruptedException {

        // 1. Basic demo
        basicWeakHashMapDemo();

        // 2. HashMap vs WeakHashMap
        hashMapVsWeakHashMap();

        // 3. Internal working
        internalWorkingDemo();

        // 4. Metadata use case
        metadataDemoUsage();

        // 5. Listener use case
        listenerDemoUsage();

        // 6. User display name cache
        User user = new User(1L, "nitesh", "sharma");
        String displayName = getDisplayName(user);
        // displayName → "NITESH SHARMA (ID: 1)"
        // Call again — returns from cache, no recomputation
        String cachedName = getDisplayName(user);
        // cachedName == displayName (same result, from cache)

        // 7. Common mistakes demo
        commonMistakesDemo();

        // 8. Thread safety demo
        threadSafetyDemo();

        // 9. Request context demo
        requestContextDemo();

        /*
         *  OUTPUT EXPLANATION:
         *  Most demos don't print anything — they demonstrate memory behavior.
         *  To see WeakHashMap in action, add size() checks before and after
         *  setting keys to null and calling System.gc().
         *
         *  Remember: System.gc() is just a HINT to JVM.
         *  JVM may or may not run GC immediately.
         *  In production, never rely on System.gc().
         */
    }
}
