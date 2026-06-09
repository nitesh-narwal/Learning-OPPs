package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.concurrent.*;

/**
 * ==========================================
 * CACHING PATTERNS - Industry Standard Implementations
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Cache = Temporary storage jo frequently accessed data ko RAM mein rakhta hai
 * Database hit karne se better hai cache check karna - 100x faster!
 * 
 * Har badi company (Google, Facebook, Amazon) caching use karti hai.
 * Production mein 70% requests cache se serve hoti hain!
 * 
 * PATTERNS COVERED:
 * 1. LRU (Least Recently Used) - Most popular! 🔥
 * 2. LFU (Least Frequently Used)
 * 3. TTL (Time To Live) - Auto-expiring cache
 * 4. Write-Through vs Write-Back
 * 
 * REAL USAGE:
 * - Redis, Memcached use LRU
 * - Browser cache uses LRU
 * - Database query cache
 * - Session management
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class CachingPatterns {
    
    public static void main(String[] args) {
        demonstrateLRUCache();
        demonstrateLFUCache();
        demonstrateTTLCache();
        demonstrateWriteStrategies();
    }
    
    /**
     * LRU CACHE - PRODUCTION READY IMPLEMENTATION
     * ============================================
     * 
     * KEY CONCEPT:
     * Least Recently Used item ko remove karo jab cache full ho
     * 
     * WHY LRU?
     * - Simple to implement
     * - Good hit rate
     * - Predictable behavior
     * - Industry standard
     * 
     * COMPLEXITY:
     * - get(): O(1)
     * - put(): O(1)
     * - Space: O(capacity)
     */
    static class LRUCache<K, V> {
        private final int capacity;
        private final Map<K, V> cache;
        
        /**
         * LinkedIn
HashMap ka magic: accessOrder=true maintains LRU order!
         * Automatically moves accessed elements to end
         */
        public LRUCache(int capacity) {
            this.capacity = capacity;
            // true = access-order (LRU), false = insertion-order
            this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    // Automatically remove oldest when size exceeds capacity
                    return size() > LRUCache.this.capacity;
                }
            };
        }
        
        public V get(K key) {
            return cache.get(key); // Automatically marks as recently used
        }
        
        public void put(K key, V value) {
            cache.put(key, value); // Automatically handles eviction
        }
        
        public int size() {
            return cache.size();
        }
        
        // For debugging - see current cache state
        public Map<K, V> snapshot() {
            return new LinkedHashMap<>(cache);
        }
    }
    
    static void demonstrateLRUCache() {
        /*
         * SCENARIO: User session cache
         * Store last 3 active user sessions
         */
        LRUCache<String, String> sessionCache = new LRUCache<>(3);
        
        // Add sessions
        sessionCache.put("user1", "Alice");
        sessionCache.put("user2", "Bob");
        sessionCache.put("user3", "Charlie");
        // Cache: user1, user2, user3
        
        // Access user1 - moves to end (most recent)
        sessionCache.get("user1");
        // Cache: user2, user3, user1
        
        // Add user4 - user2 gets evicted (least recent)
        sessionCache.put("user4", "David");
        // Cache: user3, user1, user4
        
        assert sessionCache.get("user2") == null; // Evicted!
        assert sessionCache.get("user1").equals("Alice"); // Still there
    }
    
    /**
     * LFU CACHE - FREQUENCY BASED EVICTION
     * =====================================
     * 
     * KEY CONCEPT:
     * Remove item jisko sabse kam baar access kiya gaya
     * 
     * WHEN TO USE:
     * - Data access patterns are predictable
     * - Popular items should stay longer
     * - Example: YouTube trending videos cache
     */
    static class LFUCache<K, V> {
        private final int capacity;
        private final Map<K, V> values;
        private final Map<K, Integer> frequencies;
        private final Map<Integer, LinkedHashSet<K>> frequencyBuckets;
        private int minFrequency;
        
        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.values = new HashMap<>();
            this.frequencies = new HashMap<>();
            this.frequencyBuckets = new HashMap<>();
            this.minFrequency = 0;
        }
        
        public V get(K key) {
            if (!values.containsKey(key)) return null;
            
            // Increment frequency
            int freq = frequencies.get(key);
            frequencies.put(key, freq + 1);
            
            // Move to higher frequency bucket
            frequencyBuckets.get(freq).remove(key);
            if (freq == minFrequency && frequencyBuckets.get(freq).isEmpty()) {
                minFrequency++;
            }
            
            frequencyBuckets.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
            
            return values.get(key);
        }
        
        public void put(K key, V value) {
            if (capacity <= 0) return;
            
            if (values.containsKey(key)) {
                values.put(key, value);
                get(key); // Update frequency
                return;
            }
            
            // Evict if full
            if (values.size() >= capacity) {
                K evictKey = frequencyBuckets.get(minFrequency).iterator().next();
                frequencyBuckets.get(minFrequency).remove(evictKey);
                values.remove(evictKey);
                frequencies.remove(evictKey);
            }
            
            // Add new entry
            values.put(key, value);
            frequencies.put(key, 1);
            frequencyBuckets.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFrequency = 1;
        }
    }
    
    static void demonstrateLFUCache() {
        /*
         * SCENARIO: Popular product cache
         * Keep frequently viewed products in memory
         */
        LFUCache<String, String> productCache = new LFUCache<>(2);
        
        productCache.put("p1", "iPhone");
        productCache.put("p2", "Samsung");
        
        // iPhone accessed 3 times - becomes popular
        productCache.get("p1");
        productCache.get("p1");
        productCache.get("p1");
        
        // Samsung accessed once
        productCache.get("p2");
        
        // Add new product - Samsung evicted (lower frequency)
        productCache.put("p3", "OnePlus");
        
        assert productCache.get("p2") == null; // Evicted
        assert productCache.get("p1") != null; // Popular, stays
    }
    
    /**
     * TTL CACHE - AUTO-EXPIRING CACHE
     * ================================
     * 
     * KEY CONCEPT:
     * Data expires after certain time
     * Like OTP - valid for 5 minutes only!
     * 
     * REAL USAGE:
     * - JWT tokens
     * - OTP verification
     * - Temporary download links
     * - Session tokens
     */
    static class TTLCache<K, V> {
        private final Map<K, CacheEntry<V>> cache;
        private final long ttlMillis;
        
        static class CacheEntry<V> {
            final V value;
            final long expiryTime;
            
            CacheEntry(V value, long ttlMillis) {
                this.value = value;
                this.expiryTime = System.currentTimeMillis() + ttlMillis;
            }
            
            boolean isExpired() {
                return System.currentTimeMillis() > expiryTime;
            }
        }
        
        public TTLCache(long ttlMillis) {
            this.cache = new ConcurrentHashMap<>();
            this.ttlMillis = ttlMillis;
            startCleanupThread();
        }
        
        public void put(K key, V value) {
            cache.put(key, new CacheEntry<>(value, ttlMillis));
        }
        
        public V get(K key) {
            CacheEntry<V> entry = cache.get(key);
            if (entry == null || entry.isExpired()) {
                cache.remove(key);
                return null;
            }
            return entry.value;
        }
        
        /**
         * Background thread to clean expired entries
         * Production mein ye zaroori hai to prevent memory leak!
         */
        private void startCleanupThread() {
            Thread cleanupThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(ttlMillis / 2);
                        cache.entrySet().removeIf(e -> e.getValue().isExpired());
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            cleanupThread.setDaemon(true); // Won't prevent JVM shutdown
            cleanupThread.start();
        }
    }
    
    static void demonstrateTTLCache() {
        /*
         * SCENARIO: OTP verification
         * OTP valid for 2 seconds (demo purposes)
         */
        TTLCache<String, String> otpCache = new TTLCache<>(2000);
        
        String phone = "+91-9876543210";
        String otp = "123456";
        
        otpCache.put(phone, otp);
        
        // Immediate verification - works
        assert otpCache.get(phone).equals(otp);
        
        // After 3 seconds - expired
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        
        assert otpCache.get(phone) == null; // Expired!
    }
    
    /**
     * WRITE STRATEGIES
     * ================
     * 
     * Write-Through: Write to cache AND database simultaneously
     * Pros: Data consistency
     * Cons: Slower writes
     * Use: Banking, critical data
     * 
     * Write-Back: Write to cache first, DB later (async)
     * Pros: Faster writes
     * Cons: Data loss risk if crash
     * Use: Social media posts, logs
     */
    static class WriteStrategies {
        
        // Simulated database
        static class Database {
            Map<String, String> storage = new HashMap<>();
            
            void save(String key, String value) {
                // Simulate slow DB operation
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                storage.put(key, value);
            }
            
            String load(String key) {
                return storage.get(key);
            }
        }
        
        /**
         * WRITE-THROUGH CACHE
         * Every write goes to cache + DB immediately
         */
        static class WriteThroughCache {
            private Map<String, String> cache = new HashMap<>();
            private Database db = new Database();
            
            void put(String key, String value) {
                // Write to both atomically
                cache.put(key, value);
                db.save(key, value); // Blocks until DB write completes
            }
            
            String get(String key) {
                // Try cache first
                String value = cache.get(key);
                if (value == null) {
                    // Cache miss - load from DB
                    value = db.load(key);
                    if (value != null) {
                        cache.put(key, value);
                    }
                }
                return value;
            }
        }
        
        /**
         * WRITE-BACK CACHE
         * Write to cache immediately, DB write happens async
         */
        static class WriteBackCache {
            private Map<String, String> cache = new HashMap<>();
            private Set<String> dirtyKeys = new HashSet<>();
            private Database db = new Database();
            private ExecutorService asyncWriter = Executors.newSingleThreadExecutor();
            
            void put(String key, String value) {
                cache.put(key, value);
                dirtyKeys.add(key);
                
                // Async DB write
                asyncWriter.submit(() -> {
                    db.save(key, value);
                    dirtyKeys.remove(key);
                });
            }
            
            String get(String key) {
                return cache.getOrDefault(key, db.load(key));
            }
            
            // Call this before shutdown to flush pending writes
            void flush() {
                asyncWriter.shutdown();
                try {
                    asyncWriter.awaitTermination(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {}
            }
        }
    }
    
    static void demonstrateWriteStrategies() {
        // Write-Through: Slower but safer
        WriteStrategies.WriteThroughCache safeCache = 
            new WriteStrategies.WriteThroughCache();
        
        long start = System.currentTimeMillis();
        safeCache.put("account", "1000");
        long writeThroughTime = System.currentTimeMillis() - start;
        
        // Write-Back: Faster but risky
        WriteStrategies.WriteBackCache fastCache = 
            new WriteStrategies.WriteBackCache();
        
        start = System.currentTimeMillis();
        fastCache.put("post", "Hello World");
        long writeBackTime = System.currentTimeMillis() - start;
        
        // Write-back is typically 10-100x faster!
        assert writeBackTime < writeThroughTime;
        
        fastCache.flush(); // Important: flush before exit
    }
}

/*
 * ==========================================
 * PRODUCTION TIPS
 * ==========================================
 * 
 * 1. CHOOSE RIGHT CACHE:
 *    - LRU: General purpose (90% cases)
 *    - LFU: Predictable access patterns
 *    - TTL: Temporary data (tokens, OTP)
 * 
 * 2. CACHE SIZE:
 *    - Too small: High miss rate
 *    - Too large: Memory waste
 *    - Rule of thumb: 80% of working set
 * 
 * 3. EVICTION POLICY:
 *    - LRU: Good for recent data
 *    - LFU: Good for popular data
 *    - TTL: Good for expiring data
 * 
 * 4. WRITE STRATEGY:
 *    - Critical data: Write-Through
 *    - High throughput: Write-Back
 *    - Hybrid: Write-Through for critical, Write-Back for others
 * 
 * 5. MONITORING:
 *    - Track hit rate (>80% is good)
 *    - Monitor memory usage
 *    - Log evictions
 *    - Alert on anomalies
 * 
 * COMMON MISTAKES:
 * ================
 * ❌ No size limit - Memory leak!
 * ❌ Forgetting to cleanup TTL entries
 * ❌ Using Write-Back without flush
 * ❌ Not monitoring hit rate
 * ❌ Caching mutable objects (reference issues!)
 * 
 * INTERVIEW QUESTIONS:
 * ====================
 * Q: Design a cache for Instagram feed
 * A: LRU with TTL (recent + auto-expire old posts)
 * 
 * Q: Design a cache for trending topics
 * A: LFU (popular topics stay longer)
 * 
 * Q: How to handle cache stampede?
 * A: Use locks or request coalescing
 * 
 * NEXT: 02_RepositoryPattern.java
 * (Data access layer with collections)
 */
