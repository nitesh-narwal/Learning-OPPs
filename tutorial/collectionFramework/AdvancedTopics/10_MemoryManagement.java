package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.lang.ref.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ==========================================
 * MEMORY MANAGEMENT - Leak Prevention & Optimization
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Memory leak = Memory reserve kar liya but kabhi free nahi kiya! 😱
 * Java mein GC hai but developers still create leaks!
 * 
 * COMMON LEAK SOURCES:
 * - Static collections (never cleaned)
 * - Listeners not removed
 * - Thread-local variables
 * - Unclosed resources
 * - Cache without eviction
 * 
 * REAL IMPACT:
 * - OutOfMemoryError crashes
 * - Slow application
 * - Frequent GC pauses
 * - Production incidents
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class MemoryManagement {
    
    public static void main(String[] args) {
        demonstrateMemoryLeak();
        demonstrateWeakReferences();
        demonstrateSoftReferences();
        demonstrateProperCacheEviction();
        demonstrateResourceManagement();
    }
    
    /**
     * MEMORY LEAK EXAMPLE
     * ===================
     * 
     * Classic mistake: Static collection grows forever
     */
    static class LeakyCache {
        // BAD: Static collection never cleared
        private static final Map<String, byte[]> cache = new HashMap<>();
        
        public static void cache(String key, byte[] data) {
            cache.put(key, data); // Memory leak!
            // Data never removed, keeps growing!
        }
        
        public static byte[] get(String key) {
            return cache.get(key);
        }
        
        // Missing: Method to clear old entries!
    }
    
    static void demonstrateMemoryLeak() {
        // This will eventually cause OutOfMemoryError
        // Don't run in production! ☠️
        /*
        for (int i = 0; i < 10000; i++) {
            byte[] data = new byte[1024 * 1024]; // 1MB
            LeakyCache.cache("key" + i, data);
            // Memory keeps growing...
        }
        */
        
        // Proper approach shown later
    }
    
    /**
     * WEAK REFERENCES - Auto-Cleanup
     * ===============================
     * 
     * WeakReference allows GC to collect objects
     * when memory is needed
     * 
     * PERFECT FOR:
     * - Caches
     * - Metadata storage
     * - Listener registrations
     */
    static class WeakCache<K, V> {
        private final Map<K, WeakReference<V>> cache = new ConcurrentHashMap<>();
        
        public void put(K key, V value) {
            cache.put(key, new WeakReference<>(value));
        }
        
        public V get(K key) {
            WeakReference<V> ref = cache.get(key);
            if (ref == null) return null;
            
            V value = ref.get();
            if (value == null) {
                // Object was garbage collected
                cache.remove(key);
            }
            return value;
        }
        
        public void cleanup() {
            // Remove collected entries
            cache.entrySet().removeIf(entry -> entry.getValue().get() == null);
        }
        
        public int size() {
            cleanup();
            return cache.size();
        }
    }
    
    static void demonstrateWeakReferences() {
        WeakCache<String, String> cache = new WeakCache<>();
        
        String strongRef = new String("Important");
        cache.put("key1", strongRef);
        
        String weakOnly = new String("Temporary");
        cache.put("key2", weakOnly);
        weakOnly = null; // Remove strong reference
        
        // Suggest GC (not guaranteed to run immediately)
        System.gc();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {}
        
        // key1 still accessible (strong reference exists)
        assert cache.get("key1") != null;
        
        // key2 might be collected (only weak reference)
        // In real scenario, would be null after GC
    }
    
    /**
     * SOFT REFERENCES - Memory-Sensitive Cache
     * =========================================
     * 
     * SoftReference: GC collects only when memory low
     * Better than WeakReference for caches
     * 
     * LIFECYCLE:
     * Strong → Soft → Weak → Phantom
     * (Harder to collect → Easier to collect)
     */
    static class SoftCache<K, V> {
        private final Map<K, SoftReference<V>> cache = new ConcurrentHashMap<>();
        private final ReferenceQueue<V> refQueue = new ReferenceQueue<>();
        
        public void put(K key, V value) {
            cleanupCollected();
            cache.put(key, new SoftReference<>(value, refQueue));
        }
        
        public V get(K key) {
            cleanupCollected();
            SoftReference<V> ref = cache.get(key);
            return ref != null ? ref.get() : null;
        }
        
        private void cleanupCollected() {
            Reference<? extends V> ref;
            while ((ref = refQueue.poll()) != null) {
                // Remove collected entry
                cache.values().remove(ref);
            }
        }
    }
    
    static void demonstrateSoftReferences() {
        SoftCache<String, byte[]> imageCache = new SoftCache<>();
        
        // Cache images (memory-intensive)
        for (int i = 0; i < 10; i++) {
            byte[] imageData = new byte[1024 * 100]; // 100KB
            imageCache.put("image" + i, imageData);
        }
        
        // Images stay in cache while memory available
        // Automatically cleared when memory low!
    }
    
    /**
     * PROPER CACHE WITH EVICTION
     * ===========================
     * 
     * Production-ready cache with size limits
     */
    static class BoundedCache<K, V> {
        private final Map<K, CacheEntry<V>> cache = new LinkedHashMap<>(16, 0.75f, true);
        private final int maxSize;
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
        
        public BoundedCache(int maxSize, long ttlMillis) {
            this.maxSize = maxSize;
            this.ttlMillis = ttlMillis;
        }
        
        public synchronized void put(K key, V value) {
            // Remove expired entries
            cleanupExpired();
            
            // Evict oldest if full
            if (cache.size() >= maxSize) {
                Iterator<K> it = cache.keySet().iterator();
                if (it.hasNext()) {
                    it.next();
                    it.remove();
                }
            }
            
            cache.put(key, new CacheEntry<>(value, ttlMillis));
        }
        
        public synchronized V get(K key) {
            CacheEntry<V> entry = cache.get(key);
            if (entry == null || entry.isExpired()) {
                cache.remove(key);
                return null;
            }
            return entry.value;
        }
        
        private void cleanupExpired() {
            cache.entrySet().removeIf(e -> e.getValue().isExpired());
        }
        
        public synchronized void clear() {
            cache.clear();
        }
    }
    
    static void demonstrateProperCacheEviction() {
        BoundedCache<String, String> cache = new BoundedCache<>(3, 5000);
        
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        
        // Adding 4th item evicts oldest
        cache.put("key4", "value4");
        
        // key1 should be evicted
        assert cache.get("key1") == null;
        assert cache.get("key4") != null;
    }
    
    /**
     * RESOURCE MANAGEMENT
     * ===================
     * 
     * Properly closing resources prevents leaks
     */
    static class ResourceManager implements AutoCloseable {
        private final List<AutoCloseable> resources = new ArrayList<>();
        
        public <T extends AutoCloseable> T register(T resource) {
            resources.add(resource);
            return resource;
        }
        
        @Override
        public void close() throws Exception {
            Exception firstException = null;
            
            // Close in reverse order
            for (int i = resources.size() - 1; i >= 0; i--) {
                try {
                    resources.get(i).close();
                } catch (Exception e) {
                    if (firstException == null) {
                        firstException = e;
                    }
                }
            }
            
            resources.clear();
            
            if (firstException != null) {
                throw firstException;
            }
        }
    }
    
    static void demonstrateResourceManagement() {
        try (ResourceManager rm = new ResourceManager()) {
            // Register resources
            // All will be auto-closed!
        } catch (Exception e) {
            // Handle cleanup errors
        }
    }
}

/*
 * ==========================================
 * MEMORY MANAGEMENT - Complete Guide
 * ==========================================
 * 
 * REFERENCE TYPES:
 * ================
 * 1. Strong: Normal references (never collected)
 * 2. Soft: Collected when memory low (caches)
 * 3. Weak: Collected next GC (metadata)
 * 4. Phantom: Post-collection cleanup (rare)
 * 
 * COMMON LEAK PATTERNS:
 * =====================
 * ❌ Static collections never cleared
 * ❌ Listeners not unregistered
 * ❌ ThreadLocal not removed
 * ❌ Unclosed streams/connections
 * ❌ Inner class holding outer reference
 * 
 * LEAK PREVENTION:
 * ================
 * ✅ Limit collection sizes
 * ✅ Use WeakReference for caches
 * ✅ Implement cleanup methods
 * ✅ Use try-with-resources
 * ✅ Null out references when done
 * ✅ Monitor memory usage
 * 
 * MONITORING TOOLS:
 * =================
 * - VisualVM (heap dumps)
 * - JProfiler (memory profiler)
 * - YourKit (leak detection)
 * - Eclipse MAT (heap analyzer)
 * - JConsole (JMX monitoring)
 * 
 * GC TUNING:
 * ==========
 * -Xms: Initial heap size
 * -Xmx: Maximum heap size
 * -XX:+UseG1GC: G1 collector (recommended)
 * -XX:MaxGCPauseMillis: Target pause time
 * 
 * BEST PRACTICES:
 * ===============
 * 1. Always close resources
 * 2. Remove listeners when done
 * 3. Clear ThreadLocal in finally
 * 4. Use bounded collections
 * 5. Implement cache eviction
 * 6. Profile before production
 * 7. Monitor heap usage
 * 8. Test under load
 * 
 * INTERVIEW QUESTIONS:
 * ====================
 * Q: Difference between Soft and Weak references?
 * A: Soft survives GC until memory low, Weak collected next GC
 * 
 * Q: Common causes of memory leaks in Java?
 * A: Static collections, listeners, ThreadLocal, unclosed resources
 * 
 * Q: How to detect memory leaks?
 * A: Heap dump analysis, profiler tools, memory monitoring
 * 
 * FILES COMPLETED: 10/20 (50%)
 * NEXT: Enterprise patterns starting with 11_EventSourcing.java
 */
