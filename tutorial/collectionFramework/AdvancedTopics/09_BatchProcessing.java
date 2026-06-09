package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * ==========================================
 * BATCH PROCESSING - Bulk Operations Optimization
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Batch = Ek saath bohot saare operations kar lo!
 * One by one slow hai, bulk mein fast! 🚀
 * 
 * WHY BATCH PROCESSING?
 * - 10-100x faster than individual operations
 * - Reduces database/API calls
 * - Better resource utilization
 * - Network overhead minimized
 * 
 * REAL EXAMPLES:
 * - Database bulk inserts (1000 rows at once)
 * - Email campaigns (send in batches)
 * - Image processing (batch resize)
 * - Log aggregation
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class BatchProcessing {
    
    public static void main(String[] args) throws Exception {
        demonstrateBatchInsert();
        demonstrateBatchUpdate();
        demonstratePartitionedProcessing();
        demonstrateParallelBatching();
        demonstrateAdaptiveBatching();
    }
    
    /**
     * BATCH INSERT - Database Example
     * ================================
     * 
     * Individual inserts: 1000 calls = slow 🐢
     * Batch insert: 1 call with 1000 items = fast 🚀
     */
    static class Database {
        private final List<String> storage = new ArrayList<>();
        
        // BAD: Individual insert
        public void insert(String record) {
            // Simulate network call
            simulateNetworkDelay(10);
            storage.add(record);
        }
        
        // GOOD: Batch insert
        public void batchInsert(List<String> records) {
            // Single network call
            simulateNetworkDelay(10);
            storage.addAll(records);
        }
        
        public int count() {
            return storage.size();
        }
        
        private void simulateNetworkDelay(long ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {}
        }
    }
    
    static void demonstrateBatchInsert() {
        Database db = new Database();
        List<String> records = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            records.add("Record" + i);
        }
        
        // BAD APPROACH: One by one
        long start = System.currentTimeMillis();
        for (String record : records.subList(0, 10)) {
            db.insert(record);
        }
        long individualTime = System.currentTimeMillis() - start;
        
        // GOOD APPROACH: Batch
        start = System.currentTimeMillis();
        db.batchInsert(records.subList(10, 100));
        long batchTime = System.currentTimeMillis() - start;
        
        // Batch is 10x+ faster!
        assert batchTime < individualTime;
    }
    
    /**
     * BATCH UPDATE - Smart Batching
     * ==============================
     */
    static class BatchProcessor<T> {
        private final List<T> batch = new ArrayList<>();
        private final int batchSize;
        private final java.util.function.Consumer<List<T>> processor;
        
        public BatchProcessor(int batchSize, java.util.function.Consumer<List<T>> processor) {
            this.batchSize = batchSize;
            this.processor = processor;
        }
        
        public void add(T item) {
            batch.add(item);
            
            if (batch.size() >= batchSize) {
                flush();
            }
        }
        
        public void flush() {
            if (!batch.isEmpty()) {
                processor.accept(new ArrayList<>(batch));
                batch.clear();
            }
        }
        
        public int pendingCount() {
            return batch.size();
        }
    }
    
    static void demonstrateBatchUpdate() {
        List<String> processedBatches = new ArrayList<>();
        
        BatchProcessor<String> processor = new BatchProcessor<>(10, batch -> {
            // Process batch (e.g., send to API)
            processedBatches.add("Batch of " + batch.size() + " items");
        });
        
        // Add items
        for (int i = 0; i < 25; i++) {
            processor.add("Item" + i);
        }
        
        // 2 batches of 10 processed automatically
        assert processedBatches.size() == 2;
        
        // Flush remaining 5 items
        processor.flush();
        assert processedBatches.size() == 3;
    }
    
    /**
     * PARTITIONED PROCESSING
     * =======================
     * 
     * Split large dataset into manageable chunks
     */
    static class PartitionedProcessor<T> {
        
        public static <T> List<List<T>> partition(List<T> list, int size) {
            List<List<T>> partitions = new ArrayList<>();
            
            for (int i = 0; i < list.size(); i += size) {
                int end = Math.min(i + size, list.size());
                partitions.add(new ArrayList<>(list.subList(i, end)));
            }
            
            return partitions;
        }
        
        public static <T, R> List<R> processInPartitions(
                List<T> items,
                int partitionSize,
                java.util.function.Function<List<T>, List<R>> processor) {
            
            List<R> results = new ArrayList<>();
            List<List<T>> partitions = partition(items, partitionSize);
            
            for (List<T> partition : partitions) {
                List<R> partitionResults = processor.apply(partition);
                results.addAll(partitionResults);
            }
            
            return results;
        }
    }
    
    static void demonstratePartitionedProcessing() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            numbers.add(i);
        }
        
        // Process in batches of 25
        List<Integer> results = PartitionedProcessor.processInPartitions(
            numbers,
            25,
            partition -> {
                // Process each partition (e.g., API call)
                return partition.stream()
                    .map(n -> n * 2)
                    .collect(Collectors.toList());
            }
        );
        
        assert results.size() == 100;
        assert results.get(0) == 2;
    }
    
    /**
     * PARALLEL BATCH PROCESSING
     * ==========================
     * 
     * Process multiple batches concurrently
     */
    static class ParallelBatchProcessor<T, R> {
        private final ExecutorService executor;
        private final int batchSize;
        
        public ParallelBatchProcessor(int threads, int batchSize) {
            this.executor = Executors.newFixedThreadPool(threads);
            this.batchSize = batchSize;
        }
        
        public List<R> process(
                List<T> items,
                java.util.function.Function<T, R> processor) throws Exception {
            
            List<List<T>> batches = PartitionedProcessor.partition(items, batchSize);
            List<Future<List<R>>> futures = new ArrayList<>();
            
            // Submit all batches for parallel processing
            for (List<T> batch : batches) {
                Future<List<R>> future = executor.submit(() -> {
                    return batch.stream()
                        .map(processor)
                        .collect(Collectors.toList());
                });
                futures.add(future);
            }
            
            // Collect results
            List<R> allResults = new ArrayList<>();
            for (Future<List<R>> future : futures) {
                allResults.addAll(future.get());
            }
            
            return allResults;
        }
        
        public void shutdown() {
            executor.shutdown();
        }
    }
    
    static void demonstrateParallelBatching() throws Exception {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            numbers.add(i);
        }
        
        ParallelBatchProcessor<Integer, Integer> processor = 
            new ParallelBatchProcessor<>(4, 100);
        
        List<Integer> results = processor.process(
            numbers,
            n -> n * n  // Square each number
        );
        
        processor.shutdown();
        
        assert results.size() == 1000;
        assert results.get(0) == 1;
        assert results.get(9) == 100;
    }
    
    /**
     * ADAPTIVE BATCHING
     * =================
     * 
     * Automatically adjusts batch size based on performance
     */
    static class AdaptiveBatchProcessor<T> {
        private int currentBatchSize;
        private final int minBatchSize;
        private final int maxBatchSize;
        private final java.util.function.Consumer<List<T>> processor;
        private final List<T> buffer = new ArrayList<>();
        
        private long lastProcessTime = 0;
        private static final long TARGET_PROCESS_TIME = 100; // ms
        
        public AdaptiveBatchProcessor(
                int minSize, int maxSize,
                java.util.function.Consumer<List<T>> processor) {
            this.minBatchSize = minSize;
            this.maxBatchSize = maxSize;
            this.currentBatchSize = minSize;
            this.processor = processor;
        }
        
        public void add(T item) {
            buffer.add(item);
            
            if (buffer.size() >= currentBatchSize) {
                processBatch();
            }
        }
        
        private void processBatch() {
            if (buffer.isEmpty()) return;
            
            long start = System.currentTimeMillis();
            processor.accept(new ArrayList<>(buffer));
            long processTime = System.currentTimeMillis() - start;
            
            adjustBatchSize(processTime);
            buffer.clear();
        }
        
        private void adjustBatchSize(long processTime) {
            if (processTime < TARGET_PROCESS_TIME / 2) {
                // Too fast, increase batch size
                currentBatchSize = Math.min(
                    currentBatchSize * 2,
                    maxBatchSize
                );
            } else if (processTime > TARGET_PROCESS_TIME * 2) {
                // Too slow, decrease batch size
                currentBatchSize = Math.max(
                    currentBatchSize / 2,
                    minBatchSize
                );
            }
        }
        
        public void flush() {
            processBatch();
        }
        
        public int getCurrentBatchSize() {
            return currentBatchSize;
        }
    }
    
    static void demonstrateAdaptiveBatching() {
        AdaptiveBatchProcessor<String> processor = new AdaptiveBatchProcessor<>(
            10, 1000,
            batch -> {
                // Simulate varying processing time
                try {
                    Thread.sleep(batch.size() / 10);
                } catch (InterruptedException e) {}
            }
        );
        
        // Add items - batch size adapts automatically
        for (int i = 0; i < 500; i++) {
            processor.add("Item" + i);
        }
        
        processor.flush();
        
        // Batch size adapted based on performance
        int finalBatchSize = processor.getCurrentBatchSize();
        assert finalBatchSize >= 10 && finalBatchSize <= 1000;
    }
}

/*
 * ==========================================
 * BATCH PROCESSING - Best Practices
 * ==========================================
 * 
 * OPTIMAL BATCH SIZES:
 * ====================
 * Database inserts: 100-1000 rows
 * API calls: 10-100 items
 * File processing: 1000-10000 records
 * Email sending: 50-500 emails
 * 
 * BATCH SIZE FACTORS:
 * ===================
 * ✓ Network latency
 * ✓ Memory constraints
 * ✓ Processing time per item
 * ✓ Transaction limits
 * ✓ Timeout constraints
 * 
 * ERROR HANDLING:
 * ===============
 * 1. All-or-nothing: Rollback entire batch on error
 * 2. Partial success: Continue with remaining items
 * 3. Retry failed items: Exponential backoff
 * 4. Dead letter queue: Store failed items
 * 
 * MONITORING:
 * ===========
 * - Track batch sizes
 * - Monitor processing time
 * - Alert on failures
 * - Log batch statistics
 * 
 * COMMON MISTAKES:
 * ================
 * ❌ Batch too large (memory issues)
 * ❌ Batch too small (overhead dominates)
 * ❌ No error handling (one failure stops all)
 * ❌ Not flushing remaining items
 * ❌ Ignoring order requirements
 * 
 * REAL-WORLD PATTERNS:
 * ====================
 * 1. Spring Batch (job processing)
 * 2. Kafka batching (message aggregation)
 * 3. ETL pipelines (extract-transform-load)
 * 4. Log aggregation (Fluentd, Logstash)
 * 5. Analytics processing (Spark, Flink)
 * 
 * NEXT: 10_MemoryManagement.java
 * (Preventing memory leaks and optimization)
 */
