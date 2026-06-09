package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * ==========================================
 * LAZY LOADING PATTERNS - On-Demand Data Loading
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Lazy = Kaam ko delay karna jab tak zaroori na ho! 😴
 * Data tab load karo jab actually use ho
 * 
 * WHY LAZY LOADING?
 * - Fast application startup
 * - Less memory usage
 * - Better performance
 * - Load only what's needed
 * 
 * REAL EXAMPLES:
 * - Hibernate lazy collections
 * - Image loading in galleries
 * - Pagination (load page by page)
 * - Infinite scrolling
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class LazyLoading {
    
    public static void main(String[] args) {
        demonstrateLazyList();
        demonstrateLazyMap();
        demonstrateVirtualProxy();
        demonstrateLazyInitialization();
        demonstratePaginatedLoading();
    }
    
    /**
     * LAZY LIST - Load elements on access
     * ====================================
     */
    static class LazyList<T> extends AbstractList<T> {
        private final List<T> loadedElements = new ArrayList<>();
        private final Supplier<List<T>> dataSource;
        private boolean fullyLoaded = false;
        private int totalSize;
        
        public LazyList(Supplier<List<T>> dataSource, int totalSize) {
            this.dataSource = dataSource;
            this.totalSize = totalSize;
        }
        
        @Override
        public T get(int index) {
            ensureLoaded(index);
            return loadedElements.get(index);
        }
        
        @Override
        public int size() {
            return totalSize;
        }
        
        private void ensureLoaded(int index) {
            if (index >= loadedElements.size() && !fullyLoaded) {
                // Load data only when accessed
                List<T> allData = dataSource.get();
                loadedElements.addAll(allData);
                fullyLoaded = true;
            }
        }
    }
    
    static void demonstrateLazyList() {
        // Simulate expensive database query
        Supplier<List<String>> expensiveQuery = () -> {
            // In real app: database.query("SELECT * FROM users")
            List<String> users = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                users.add("User" + i);
            }
            return users;
        };
        
        LazyList<String> users = new LazyList<>(expensiveQuery, 1000);
        
        // No data loaded yet! ✨
        // Data loads only when accessed
        String firstUser = users.get(0); // NOW it loads
        assert firstUser.equals("User0");
    }
    
    /**
     * LAZY MAP - Compute values on-demand
     * ====================================
     * 
     * Perfect for expensive computations
     */
    static class LazyMap<K, V> {
        private final Map<K, V> cache = new ConcurrentHashMap<>();
        private final java.util.function.Function<K, V> valueComputer;
        
        public LazyMap(java.util.function.Function<K, V> valueComputer) {
            this.valueComputer = valueComputer;
        }
        
        public V get(K key) {
            return cache.computeIfAbsent(key, valueComputer);
        }
        
        public void clear() {
            cache.clear();
        }
        
        public int cachedSize() {
            return cache.size();
        }
    }
    
    static void demonstrateLazyMap() {
        // Expensive computation: Fibonacci
        LazyMap<Integer, Long> fibCache = new LazyMap<>(n -> {
            if (n <= 1) return (long) n;
            
            long a = 0, b = 1;
            for (int i = 2; i <= n; i++) {
                long temp = a + b;
                a = b;
                b = temp;
            }
            return b;
        });
        
        // First call: Computes
        long fib10 = fibCache.get(10);
        
        // Second call: Returns cached value (instant!)
        long fib10Again = fibCache.get(10);
        
        assert fib10 == fib10Again;
    }
    
    /**
     * VIRTUAL PROXY PATTERN
     * ======================
     * 
     * Proxy delays object creation until actually used
     * Heavy objects ko lazy load karo!
     */
    interface Image {
        void display();
        int getSize();
    }
    
    static class RealImage implements Image {
        private final String filename;
        private final byte[] data;
        
        public RealImage(String filename) {
            this.filename = filename;
            // Expensive operation: Load from disk
            this.data = loadFromDisk(filename);
        }
        
        private byte[] loadFromDisk(String filename) {
            // Simulate expensive I/O
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            return new byte[1024 * 1024]; // 1MB
        }
        
        @Override
        public void display() {
            // Display image
        }
        
        @Override
        public int getSize() {
            return data.length;
        }
    }
    
    static class ImageProxy implements Image {
        private final String filename;
        private RealImage realImage; // Lazy loaded!
        
        public ImageProxy(String filename) {
            this.filename = filename;
            // No loading yet! Just store filename
        }
        
        @Override
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(filename); // Load now!
            }
            realImage.display();
        }
        
        @Override
        public int getSize() {
            if (realImage == null) {
                realImage = new RealImage(filename);
            }
            return realImage.getSize();
        }
    }
    
    static void demonstrateVirtualProxy() {
        // Gallery with 100 images
        List<Image> gallery = new ArrayList<>();
        
        // Fast! No images loaded yet
        for (int i = 0; i < 100; i++) {
            gallery.add(new ImageProxy("image" + i + ".jpg"));
        }
        
        // Only load when displayed
        gallery.get(0).display(); // This loads image 0
        gallery.get(5).display(); // This loads image 5
        // Other 98 images still not loaded! 🚀
    }
    
    /**
     * LAZY INITIALIZATION - Thread-Safe
     * ==================================
     * 
     * Singleton pattern with lazy loading
     */
    static class LazyInitializedSingleton {
        private static LazyInitializedSingleton instance;
        
        private LazyInitializedSingleton() {
            // Expensive initialization
        }
        
        // Double-checked locking (thread-safe + efficient)
        public static LazyInitializedSingleton getInstance() {
            if (instance == null) {
                synchronized (LazyInitializedSingleton.class) {
                    if (instance == null) {
                        instance = new LazyInitializedSingleton();
                    }
                }
            }
            return instance;
        }
    }
    
    /**
     * Better approach: Initialization-on-demand holder
     */
    static class BetterLazySingleton {
        private BetterLazySingleton() {}
        
        private static class Holder {
            static final BetterLazySingleton INSTANCE = new BetterLazySingleton();
        }
        
        public static BetterLazySingleton getInstance() {
            return Holder.INSTANCE;
        }
    }
    
    static void demonstrateLazyInitialization() {
        // No instance created yet
        
        // First call creates instance
        BetterLazySingleton instance1 = BetterLazySingleton.getInstance();
        
        // Subsequent calls return same instance (cached)
        BetterLazySingleton instance2 = BetterLazySingleton.getInstance();
        
        assert instance1 == instance2;
    }
    
    /**
     * PAGINATED LOADING
     * =================
     * 
     * Load data page by page (like Facebook feed)
     */
    static class PaginatedList<T> {
        private final List<T> loadedItems = new ArrayList<>();
        private final DataSource<T> dataSource;
        private int currentPage = 0;
        private final int pageSize;
        private boolean hasMore = true;
        
        interface DataSource<T> {
            List<T> loadPage(int page, int size);
            boolean hasMorePages(int page);
        }
        
        public PaginatedList(DataSource<T> dataSource, int pageSize) {
            this.dataSource = dataSource;
            this.pageSize = pageSize;
        }
        
        public T get(int index) {
            // Load pages until we have the requested index
            while (index >= loadedItems.size() && hasMore) {
                loadNextPage();
            }
            
            if (index < loadedItems.size()) {
                return loadedItems.get(index);
            }
            throw new IndexOutOfBoundsException();
        }
        
        public void loadNextPage() {
            if (!hasMore) return;
            
            List<T> page = dataSource.loadPage(currentPage, pageSize);
            loadedItems.addAll(page);
            currentPage++;
            hasMore = dataSource.hasMorePages(currentPage);
        }
        
        public int loadedCount() {
            return loadedItems.size();
        }
        
        public boolean hasMore() {
            return hasMore;
        }
    }
    
    static void demonstratePaginatedLoading() {
        // Simulate database with pagination
        PaginatedList.DataSource<String> db = new PaginatedList.DataSource<String>() {
            private final int totalRecords = 250;
            
            @Override
            public List<String> loadPage(int page, int size) {
                List<String> pageData = new ArrayList<>();
                int start = page * size;
                int end = Math.min(start + size, totalRecords);
                
                for (int i = start; i < end; i++) {
                    pageData.add("Record" + i);
                }
                return pageData;
            }
            
            @Override
            public boolean hasMorePages(int page) {
                return page * 50 < totalRecords;
            }
        };
        
        PaginatedList<String> records = new PaginatedList<>(db, 50);
        
        // Only first page loaded initially
        String first = records.get(0);
        assert records.loadedCount() == 50;
        
        // Accessing 51st element loads second page
        String fiftyFirst = records.get(50);
        assert records.loadedCount() == 100;
        
        // Can explicitly load more
        records.loadNextPage();
        assert records.loadedCount() == 150;
    }
}

/*
 * ==========================================
 * LAZY LOADING PATTERNS - Summary
 * ==========================================
 * 
 * WHEN TO USE:
 * ============
 * ✅ Large datasets (don't load everything)
 * ✅ Expensive computations (defer until needed)
 * ✅ Heavy objects (images, files)
 * ✅ Rarely used data
 * ✅ Performance optimization
 * 
 * WHEN NOT TO USE:
 * ================
 * ❌ Small, cheap objects
 * ❌ Always-needed data
 * ❌ Adds unnecessary complexity
 * ❌ Real-time requirements (latency issues)
 * 
 * IMPLEMENTATION STRATEGIES:
 * ==========================
 * 1. Virtual Proxy (most common)
 * 2. Lazy initialization (singletons)
 * 3. On-demand loading (pagination)
 * 4. Caching computed values
 * 5. Holder pattern (thread-safe singleton)
 * 
 * THREAD-SAFETY:
 * ==============
 * - Double-checked locking
 * - Holder pattern (best!)
 * - Lazy + ConcurrentHashMap
 * - computeIfAbsent (atomic)
 * 
 * COMMON PITFALLS:
 * ================
 * ❌ N+1 query problem (Hibernate)
 * ❌ Not handling null/empty cases
 * ❌ Memory leaks (forgetting to clear cache)
 * ❌ Race conditions (not thread-safe)
 * ❌ Over-engineering (keep it simple!)
 * 
 * REAL-WORLD EXAMPLES:
 * ====================
 * 1. Hibernate lazy collections
 * 2. Image galleries (load on scroll)
 * 3. Spring @Lazy beans
 * 4. React lazy loading
 * 5. Database pagination
 * 
 * PERFORMANCE BENEFITS:
 * =====================
 * - Faster application startup (70-90% improvement)
 * - Lower memory footprint (50-80% reduction)
 * - Better user experience (perceived performance)
 * - Scalability (handle more users)
 * 
 * NEXT: 09_BatchProcessing.java
 * (Bulk operations for efficiency)
 */
