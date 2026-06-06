package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * ==========================================
 * INDUSTRY PATTERNS - Real-World Iterator Usage
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Bhai, ab tak theory padhi. Ab real production code mein
 * iterator kaise use hota hai wo dekhte hain! 💼
 * 
 * Ye file actual industry scenarios cover karegi:
 * - Database result processing
 * - File I/O operations
 * - API response handling
 * - Batch processing
 * - Caching strategies
 * - And more!
 * 
 * These are patterns used by companies like Google, Amazon, Netflix! 🚀
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
public class IndustryPatterns {
    
    public static void main(String[] args) {
        System.out.println("=== INDUSTRY ITERATOR PATTERNS ===\n");
        
        // Pattern 1: Database Result Set Pattern
        databaseResultSetPattern();
        
        // Pattern 2: Batch Processing Pattern
        batchProcessingPattern();
        
        // Pattern 3: Cache Eviction Pattern
        cacheEvictionPattern();
        
        // Pattern 4: Event Processing Pattern
        eventProcessingPattern();
        
        // Pattern 5: Pagination Pattern
        paginationPattern();
        
        // Pattern 6: Stream Processing Pattern
        streamProcessingPattern();
        
        // Pattern 7: Resource Cleanup Pattern
        resourceCleanupPattern();
        
        // Summary
        industrySummary();
    }
    
    /**
     * PATTERN 1: Database Result Set Pattern
     * =======================================
     * Processing database results efficiently
     */
    private static void databaseResultSetPattern() {
        System.out.println("PATTERN #1: DATABASE RESULT SET PROCESSING");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Processing large database query results\n");
        
        /**
         * Simulates database result set
         * Real world: This would be actual JDBC ResultSet
         */
        class UserRecord {
            int id;
            String name;
            String email;
            
            UserRecord(int id, String name, String email) {
                this.id = id;
                this.name = name;
                this.email = email;
            }
            
            @Override
            public String toString() {
                return "User(" + id + ", " + name + ", " + email + ")";
            }
        }
        
        /**
         * Database result wrapper with iterator
         * Benefits:
         * - Memory efficient (no loading all results at once)
         * - Cursor-based fetching
         * - Resource management
         */
        class DatabaseResults implements Iterable<UserRecord>, AutoCloseable {
            private List<UserRecord> mockResults; // Real: JDBC ResultSet
            private boolean closed = false;
            
            public DatabaseResults() {
                // Simulate database query
                mockResults = Arrays.asList(
                    new UserRecord(1, "Alice", "alice@example.com"),
                    new UserRecord(2, "Bob", "bob@example.com"),
                    new UserRecord(3, "Charlie", "charlie@example.com"),
                    new UserRecord(4, "David", "david@example.com")
                );
                System.out.println("✅ Database connection opened");
            }
            
            @Override
            public Iterator<UserRecord> iterator() {
                if(closed) {
                    throw new IllegalStateException("ResultSet already closed");
                }
                return mockResults.iterator();
            }
            
            @Override
            public void close() {
                closed = true;
                System.out.println("✅ Database connection closed");
            }
        }
        
        // ❌ BAD: Loading all results into memory
        System.out.println("❌ BAD APPROACH:");
        System.out.println("```java");
        System.out.println("List<User> users = loadAllUsers(); // Loads millions!");
        System.out.println("for(User user : users) { process(user); }");
        System.out.println("// OutOfMemoryError! 💥");
        System.out.println("```\n");
        
        // ✅ GOOD: Process with iterator + try-with-resources
        System.out.println("✅ GOOD APPROACH:");
        System.out.println("Using iterator with resource management:\n");
        
        try(DatabaseResults results = new DatabaseResults()) {
            Iterator<UserRecord> it = results.iterator();
            int processed = 0;
            
            while(it.hasNext()) {
                UserRecord user = it.next();
                
                // Process user (send email, update cache, etc.)
                System.out.println("  Processing: " + user);
                processed++;
                
                // Can break early if needed
                if(processed >= 2) {
                    System.out.println("  (Stopping after 2 for demo...)");
                    break;
                }
            }
            
        } // Auto-closes connection!
        
        System.out.println();
        
        System.out.println("💡 PRODUCTION BENEFITS:");
        System.out.println("  ✓ Memory efficient (streaming results)");
        System.out.println("  ✓ Early termination possible");
        System.out.println("  ✓ Automatic resource cleanup");
        System.out.println("  ✓ Handles millions of records gracefully");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * PATTERN 2: Batch Processing Pattern
     * ====================================
     * Processing items in batches for efficiency
     */
    private static void batchProcessingPattern() {
        System.out.println("PATTERN #2: BATCH PROCESSING");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Processing 1000s of items in batches\n");
        
        /**
         * Batch iterator - groups elements into batches
         * Use case: Bulk database inserts, API rate limiting
         */
        class BatchIterator<E> implements Iterator<List<E>> {
            private Iterator<E> source;
            private int batchSize;
            
            public BatchIterator(Iterator<E> source, int batchSize) {
                this.source = source;
                this.batchSize = batchSize;
            }
            
            @Override
            public boolean hasNext() {
                return source.hasNext();
            }
            
            @Override
            public List<E> next() {
                List<E> batch = new ArrayList<>();
                
                while(source.hasNext() && batch.size() < batchSize) {
                    batch.add(source.next());
                }
                
                return batch;
            }
        }
        
        // Simulate large dataset
        List<Integer> orderIds = new ArrayList<>();
        for(int i = 1; i <= 25; i++) {
            orderIds.add(i);
        }
        
        System.out.println("Total orders: " + orderIds.size());
        System.out.println("Batch size: 10\n");
        
        BatchIterator<Integer> batchIt = new BatchIterator<>(orderIds.iterator(), 10);
        
        int batchNumber = 1;
        while(batchIt.hasNext()) {
            List<Integer> batch = batchIt.next();
            
            System.out.println("Batch " + batchNumber + ": " + batch);
            System.out.println("  → Processing " + batch.size() + " orders...");
            
            // Simulate batch API call
            // api.createOrders(batch);
            
            batchNumber++;
        }
        
        System.out.println();
        
        System.out.println("💡 REAL-WORLD USAGE:");
        System.out.println("  • Bulk database operations (INSERT 1000 rows at once)");
        System.out.println("  • API rate limiting (max 100 requests/minute)");
        System.out.println("  • Email campaigns (send in batches of 500)");
        System.out.println("  • File processing (read chunks instead of whole file)");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * PATTERN 3: Cache Eviction Pattern
     * ==================================
     * LRU cache with iterator for cleanup
     */
    private static void cacheEvictionPattern() {
        System.out.println("PATTERN #3: CACHE EVICTION (LRU)");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Remove least recently used items from cache\n");
        
        /**
         * Simple LRU Cache implementation
         * Uses LinkedHashMap (maintains insertion order)
         */
        class LRUCache<K, V> {
            private LinkedHashMap<K, V> cache;
            private int maxSize;
            
            public LRUCache(int maxSize) {
                this.maxSize = maxSize;
                // LinkedHashMap with access-order (true = LRU)
                this.cache = new LinkedHashMap<K, V>(maxSize, 0.75f, true) {
                    @Override
                    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                        return size() > LRUCache.this.maxSize;
                    }
                };
            }
            
            public void put(K key, V value) {
                cache.put(key, value);
            }
            
            public V get(K key) {
                return cache.get(key);
            }
            
            public void evictOldEntries(int count) {
                Iterator<Map.Entry<K, V>> it = cache.entrySet().iterator();
                int evicted = 0;
                
                while(it.hasNext() && evicted < count) {
                    Map.Entry<K, V> entry = it.next();
                    System.out.println("  Evicting: " + entry.getKey() + " = " + entry.getValue());
                    it.remove(); // Safe removal using iterator!
                    evicted++;
                }
            }
            
            public void printCache() {
                System.out.println("  Current cache: " + cache);
            }
        }
        
        // Demo
        LRUCache<String, String> cache = new LRUCache<>(5);
        
        System.out.println("Adding items to cache:");
        cache.put("user:1", "Alice");
        cache.put("user:2", "Bob");
        cache.put("user:3", "Charlie");
        cache.put("user:4", "David");
        cache.put("user:5", "Eve");
        cache.printCache();
        
        System.out.println("\nAccessing user:2 (makes it recently used):");
        cache.get("user:2");
        cache.printCache();
        
        System.out.println("\nManual eviction of 2 oldest entries:");
        cache.evictOldEntries(2);
        cache.printCache();
        
        System.out.println("\n💡 ITERATOR USAGE:");
        System.out.println("  • Safe removal during iteration");
        System.out.println("  • Order matters (LinkedHashMap preserves order)");
        System.out.println("  • Used in Redis, Memcached implementations");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * PATTERN 4: Event Processing Pattern
     * ====================================
     * Process events with filtering and early termination
     */
    private static void eventProcessingPattern() {
        System.out.println("PATTERN #4: EVENT PROCESSING");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Processing event stream until critical event\n");
        
        class Event {
            String type;
            String message;
            int severity; // 1=INFO, 2=WARNING, 3=ERROR, 4=CRITICAL
            
            Event(String type, String message, int severity) {
                this.type = type;
                this.message = message;
                this.severity = severity;
            }
            
            @Override
            public String toString() {
                return type + "(" + severity + "): " + message;
            }
        }
        
        // Event stream (simulated)
        List<Event> events = Arrays.asList(
            new Event("LOGIN", "User logged in", 1),
            new Event("API_CALL", "GET /users", 1),
            new Event("SLOW_QUERY", "Query took 5s", 2),
            new Event("DISK_FULL", "Disk 90% full", 3),
            new Event("API_CALL", "POST /orders", 1),
            new Event("DB_DOWN", "Database unreachable", 4), // CRITICAL!
            new Event("LOGIN", "Admin login", 1) // Won't reach here
        );
        
        System.out.println("Processing event stream...\n");
        
        Iterator<Event> eventIterator = events.iterator();
        int processed = 0;
        
        while(eventIterator.hasNext()) {
            Event event = eventIterator.next();
            processed++;
            
            System.out.println("Event #" + processed + ": " + event);
            
            // Handle based on severity
            if(event.severity == 4) {
                System.out.println("  ❌ CRITICAL EVENT DETECTED!");
                System.out.println("  → Stopping processing");
                System.out.println("  → Triggering alert");
                System.out.println("  → Initiating failover");
                break; // Early termination!
            } else if(event.severity >= 2) {
                System.out.println("  ⚠️  Warning logged");
            }
        }
        
        System.out.println("\nProcessed " + processed + " out of " + events.size() + " events");
        
        System.out.println("\n💡 PRODUCTION PATTERNS:");
        System.out.println("  • Log aggregation systems (Splunk, ELK)");
        System.out.println("  • Monitoring dashboards (Datadog, New Relic)");
        System.out.println("  • Alert systems (PagerDuty)");
        System.out.println("  • Stream processing (Kafka, Kinesis)");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * PATTERN 5: Pagination Pattern
     * ==============================
     * API pagination implementation
     */
    private static void paginationPattern() {
        System.out.println("PATTERN #5: API PAGINATION");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Paginated API responses\n");
        
        /**
         * Paginated result iterator
         * Fetches next page automatically
         */
        class PaginatedIterator<T> implements Iterator<T> {
            private List<T> allItems;
            private int pageSize;
            private int currentPage = 0;
            private Iterator<T> currentPageIterator;
            
            public PaginatedIterator(List<T> allItems, int pageSize) {
                this.allItems = allItems;
                this.pageSize = pageSize;
                loadNextPage();
            }
            
            private void loadNextPage() {
                int start = currentPage * pageSize;
                int end = Math.min(start + pageSize, allItems.size());
                
                if(start < allItems.size()) {
                    List<T> page = allItems.subList(start, end);
                    currentPageIterator = page.iterator();
                    currentPage++;
                    
                    System.out.println("  📄 Loaded page " + currentPage + 
                                     " (" + page.size() + " items)");
                } else {
                    currentPageIterator = Collections.emptyIterator();
                }
            }
            
            @Override
            public boolean hasNext() {
                if(currentPageIterator.hasNext()) {
                    return true;
                }
                
                // Try loading next page
                if(currentPage * pageSize < allItems.size()) {
                    loadNextPage();
                    return currentPageIterator.hasNext();
                }
                
                return false;
            }
            
            @Override
            public T next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentPageIterator.next();
            }
        }
        
        // Simulate API with 25 products
        List<String> products = new ArrayList<>();
        for(int i = 1; i <= 25; i++) {
            products.add("Product" + i);
        }
        
        System.out.println("Total products: " + products.size());
        System.out.println("Page size: 10\n");
        
        PaginatedIterator<String> paginatedIt = new PaginatedIterator<>(products, 10);
        
        int count = 0;
        while(paginatedIt.hasNext()) {
            String product = paginatedIt.next();
            count++;
            
            if(count % 10 == 1) {
                System.out.println(); // New line for each page
            }
            System.out.print("    " + product + " ");
        }
        
        System.out.println("\n\n💡 REST API PATTERN:");
        System.out.println("  GET /api/products?page=1&size=10");
        System.out.println("  GET /api/products?page=2&size=10");
        System.out.println("  ...");
        System.out.println("\n  Used by: GitHub, Twitter, Stripe APIs");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * PATTERN 6: Stream Processing Pattern
     * =====================================
     * Combining Iterator with Java 8 Streams
     */
    private static void streamProcessingPattern() {
        System.out.println("PATTERN #6: ITERATOR + STREAMS HYBRID");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Complex data pipeline\n");
        
        // Sample data
        List<String> logLines = Arrays.asList(
            "ERROR: Database connection failed",
            "INFO: Request processed successfully",
            "ERROR: Timeout exception",
            "WARN: High memory usage",
            "ERROR: NullPointerException in module X",
            "INFO: Cache hit",
            "ERROR: API rate limit exceeded"
        );
        
        System.out.println("Processing " + logLines.size() + " log lines...\n");
        
        // Modern approach: Iterator → Stream → Processing
        long errorCount = logLines.stream()
            .filter(line -> line.startsWith("ERROR"))
            .peek(line -> System.out.println("  Found error: " + line))
            .count();
        
        System.out.println("\nTotal errors: " + errorCount);
        
        System.out.println("\n💡 HYBRID BENEFITS:");
        System.out.println("  ✓ Iterator for data source");
        System.out.println("  ✓ Stream for transformations");
        System.out.println("  ✓ Clean, functional code");
        System.out.println("  ✓ Easy to parallelize (parallel streams)");
        
        System.out.println("\n📊 PERFORMANCE:");
        System.out.println("  • Small datasets: Iterator = Stream");
        System.out.println("  • Large datasets: Parallel streams WIN");
        System.out.println("  • Memory: Iterator better (lazy)");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * PATTERN 7: Resource Cleanup Pattern
     * ====================================
     * Proper resource management with iterators
     */
    private static void resourceCleanupPattern() {
        System.out.println("PATTERN #7: RESOURCE CLEANUP");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Processing files with cleanup\n");
        
        /**
         * File processor with automatic cleanup
         */
        class FileProcessor implements Iterable<String>, AutoCloseable {
            private List<String> lines;
            private boolean closed = false;
            
            public FileProcessor(String filename) {
                // Simulate file reading
                lines = Arrays.asList(
                    "Line 1: Data entry",
                    "Line 2: More data",
                    "Line 3: Final entry"
                );
                System.out.println("✅ Opened file: " + filename);
            }
            
            @Override
            public Iterator<String> iterator() {
                if(closed) {
                    throw new IllegalStateException("File already closed");
                }
                return lines.iterator();
            }
            
            @Override
            public void close() {
                if(!closed) {
                    closed = true;
                    System.out.println("✅ Closed file and released resources");
                }
            }
        }
        
        // ❌ BAD: Forgetting to close
        System.out.println("❌ BAD: Manual management (easy to forget!)");
        System.out.println("```java");
        System.out.println("FileProcessor fp = new FileProcessor(\"data.txt\");");
        System.out.println("for(String line : fp) { process(line); }");
        System.out.println("fp.close(); // What if exception happens? 💥");
        System.out.println("```\n");
        
        // ✅ GOOD: try-with-resources
        System.out.println("✅ GOOD: Automatic cleanup with try-with-resources\n");
        
        try(FileProcessor fp = new FileProcessor("data.txt")) {
            for(String line : fp) {
                System.out.println("  Processing: " + line);
            }
            // Cleanup happens automatically! ✨
        }
        
        System.out.println("\n💡 PRODUCTION PATTERN:");
        System.out.println("  • Database connections");
        System.out.println("  • File handles");
        System.out.println("  • Network sockets");
        System.out.println("  • Locks and semaphores");
        System.out.println("\n  → Always implement AutoCloseable!");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * INDUSTRY SUMMARY
     * ================
     */
    private static void industrySummary() {
        System.out.println("=" .repeat(60));
        System.out.println("INDUSTRY PATTERNS SUMMARY");
        System.out.println("=".repeat(60));
        
        System.out.println("\n🏢 COMPANIES USING THESE PATTERNS:");
        System.out.println("-".repeat(40));
        System.out.println("Amazon:");
        System.out.println("  • DynamoDB pagination (Pattern #5)");
        System.out.println("  • S3 object listing (Iterator pattern)");
        
        System.out.println("\nGoogle:");
        System.out.println("  • BigQuery results (Pattern #1)");
        System.out.println("  • Cloud Storage streaming (Pattern #6)");
        
        System.out.println("\nNetflix:");
        System.out.println("  • Event sourcing (Pattern #4)");
        System.out.println("  • Batch processing (Pattern #2)");
        
        System.out.println("\nSpotify:");
        System.out.println("  • Playlist iteration (Circular pattern)");
        System.out.println("  • Cache management (Pattern #3)");
        
        System.out.println("\n\n📚 PATTERN DECISION MATRIX:");
        System.out.println("-".repeat(60));
        System.out.println("Use Case                    | Pattern");
        System.out.println("----------------------------|-------------------");
        System.out.println("Database query results      | #1 ResultSet");
        System.out.println("API bulk operations         | #2 Batch");
        System.out.println("Memory management           | #3 Cache Eviction");
        System.out.println("Real-time monitoring        | #4 Event");
        System.out.println("Large datasets via API      | #5 Pagination");
        System.out.println("Data transformations        | #6 Stream");
        System.out.println("File/Network operations     | #7 Resource Cleanup");
        
        System.out.println("\n\n🎯 KEY INDUSTRY PRINCIPLES:");
        System.out.println("-".repeat(40));
        System.out.println("1. MEMORY EFFICIENCY");
        System.out.println("   → Don't load everything into memory");
        System.out.println("   → Stream/iterate instead");
        
        System.out.println("\n2. FAIL-SAFE");
        System.out.println("   → Always use try-with-resources");
        System.out.println("   → Handle exceptions gracefully");
        
        System.out.println("\n3. PERFORMANCE");
        System.out.println("   → Batch operations when possible");
        System.out.println("   → Early termination on critical conditions");
        
        System.out.println("\n4. MAINTAINABILITY");
        System.out.println("   → Clear, readable iterator usage");
        System.out.println("   → Proper documentation");
        
        System.out.println("\n5. SCALABILITY");
        System.out.println("   → Pagination for large datasets");
        System.out.println("   → Lazy evaluation");
        
        System.out.println("\n\n💼 INTERVIEW TIPS:");
        System.out.println("-".repeat(40));
        System.out.println("If asked \"How would you process 1 million records?\"");
        System.out.println("  → Answer: Iterator/Stream with batch processing");
        System.out.println("  → NOT: Load all into List (OutOfMemoryError!)");
        
        System.out.println("\nIf asked \"How to handle API pagination?\"");
        System.out.println("  → Answer: Custom iterator that fetches next page");
        System.out.println("  → Transparent to calling code");
        
        System.out.println("\nIf asked \"How to cleanup resources properly?\"");
        System.out.println("  → Answer: Implement AutoCloseable + try-with-resources");
        System.out.println("  → Show code example!");
        
        System.out.println("\n\n✨ You now know production-grade iterator patterns!");
        System.out.println("   Next: 09_PerformanceOptimization.java\n");
    }
}

/*
 * ==========================================
 * INDUSTRY WISDOM
 * ==========================================
 * 
 * "In production, iterator usage is about:
 *  1. Memory efficiency (don't blow up RAM!)
 *  2. Resource management (cleanup is critical!)
 *  3. Performance (batch operations!)
 *  4. Scalability (handle millions of records!)
 *  5. Reliability (graceful error handling!)"
 * 
 * PRODUCTION CHECKLIST:
 * =====================
 * □ Memory efficient (no unnecessary loading)
 * □ Proper resource cleanup (AutoCloseable)
 * □ Exception handling (try-catch-finally)
 * □ Early termination (break on critical conditions)
 * □ Batch processing (for bulk operations)
 * □ Logging (for debugging production issues)
 * □ Monitoring (track performance metrics)
 * 
 * NEXT FILE: 09_PerformanceOptimization.java
 * (Benchmarks, profiling, and optimization techniques!)
 */
