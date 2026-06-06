package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * ==========================================
 * PERFORMANCE OPTIMIZATION - Benchmarks & Tips
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Bhai, performance matter karta hai production mein! 🚀
 * Is file mein hum dekhenge:
 * - Different iteration methods ki performance comparison
 * - Optimization techniques
 * - Real benchmarks with numbers
 * - When to use what for best performance
 * 
 * "Premature optimization is the root of all evil" - but
 * knowing performance characteristics is NOT premature! 😎
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
public class PerformanceOptimization {
    
    private static final int SMALL_SIZE = 1_000;
    private static final int MEDIUM_SIZE = 100_000;
    private static final int LARGE_SIZE = 1_000_000;
    
    public static void main(String[] args) {
        System.out.println("=== ITERATOR PERFORMANCE OPTIMIZATION ===\n");
        
        // Benchmark 1: ArrayList traversal methods
        benchmarkArrayListTraversal();
        
        // Benchmark 2: LinkedList traversal methods
        benchmarkLinkedListTraversal();
        
        // Benchmark 3: Iterator vs Stream
        benchmarkIteratorVsStream();
        
        // Benchmark 4: Collection removal strategies
        benchmarkRemovalStrategies();
        
        // Benchmark 5: Custom iterator overhead
        benchmarkCustomIteratorOverhead();
        
        // Optimization tips
        optimizationTips();
        
        // Performance summary
        performanceSummary();
    }
    
    /**
     * BENCHMARK 1: ArrayList Traversal Methods
     * =========================================
     */
    private static void benchmarkArrayListTraversal() {
        System.out.println("BENCHMARK #1: ArrayList Traversal Methods");
        System.out.println("=".repeat(60));
        
        List<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i < MEDIUM_SIZE; i++) {
            arrayList.add(i);
        }
        
        System.out.println("Size: " + arrayList.size() + " elements\n");
        
        // Method 1: Traditional for loop with get(i)
        long start = System.nanoTime();
        long sum1 = 0;
        for(int i = 0; i < arrayList.size(); i++) {
            sum1 += arrayList.get(i);
        }
        long time1 = System.nanoTime() - start;
        
        // Method 2: Enhanced for loop
        start = System.nanoTime();
        long sum2 = 0;
        for(Integer num : arrayList) {
            sum2 += num;
        }
        long time2 = System.nanoTime() - start;
        
        // Method 3: Iterator
        start = System.nanoTime();
        long sum3 = 0;
        Iterator<Integer> it = arrayList.iterator();
        while(it.hasNext()) {
            sum3 += it.next();
        }
        long time3 = System.nanoTime() - start;
        
        // Method 4: forEach with lambda
        start = System.nanoTime();
        long[] sum4 = {0}; // Array to use in lambda
        arrayList.forEach(num -> sum4[0] += num);
        long time4 = System.nanoTime() - start;
        
        // Method 5: Stream
        start = System.nanoTime();
        long sum5 = arrayList.stream().mapToLong(Integer::longValue).sum();
        long time5 = System.nanoTime() - start;
        
        // Results
        System.out.println("Method                    | Time (ms) | Relative");
        System.out.println("--------------------------|-----------|----------");
        System.out.printf("Traditional for loop      | %6.2f    | 1.00x\n", time1/1_000_000.0);
        System.out.printf("Enhanced for loop         | %6.2f    | %.2fx\n", time2/1_000_000.0, (double)time2/time1);
        System.out.printf("Iterator                  | %6.2f    | %.2fx\n", time3/1_000_000.0, (double)time3/time1);
        System.out.printf("forEach lambda            | %6.2f    | %.2fx\n", time4/1_000_000.0, (double)time4/time1);
        System.out.printf("Stream                    | %6.2f    | %.2fx\n", time5/1_000_000.0, (double)time5/time1);
        
        System.out.println("\n📊 ANALYSIS:");
        System.out.println("  • Traditional for loop is FASTEST (direct array access)");
        System.out.println("  • Enhanced for loop ≈ Iterator (almost same)");
        System.out.println("  • Streams have overhead but more readable");
        System.out.println("  • For ArrayList, all methods are reasonable");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * BENCHMARK 2: LinkedList Traversal Methods
     * ==========================================
     */
    private static void benchmarkLinkedListTraversal() {
        System.out.println("BENCHMARK #2: LinkedList Traversal Methods");
        System.out.println("=".repeat(60));
        
        List<Integer> linkedList = new LinkedList<>();
        for(int i = 0; i < SMALL_SIZE; i++) { // Note: Smaller size!
            linkedList.add(i);
        }
        
        System.out.println("Size: " + linkedList.size() + " elements");
        System.out.println("⚠️  Using smaller size for LinkedList (index access is O(n))\n");
        
        // Method 1: Traditional for loop with get(i) - SLOW!
        long start = System.nanoTime();
        long sum1 = 0;
        for(int i = 0; i < linkedList.size(); i++) {
            sum1 += linkedList.get(i); // O(n) each time!
        }
        long time1 = System.nanoTime() - start;
        
        // Method 2: Enhanced for loop - FAST!
        start = System.nanoTime();
        long sum2 = 0;
        for(Integer num : linkedList) {
            sum2 += num;
        }
        long time2 = System.nanoTime() - start;
        
        // Method 3: Iterator - FAST!
        start = System.nanoTime();
        long sum3 = 0;
        Iterator<Integer> it = linkedList.iterator();
        while(it.hasNext()) {
            sum3 += it.next();
        }
        long time3 = System.nanoTime() - start;
        
        // Results
        System.out.println("Method                    | Time (ms) | Relative");
        System.out.println("--------------------------|-----------|----------");
        System.out.printf("Traditional for loop ❌   | %6.2f    | %.0fx  SLOW!\n", 
                         time1/1_000_000.0, (double)time1/time2);
        System.out.printf("Enhanced for loop ✅      | %6.2f    | 1.00x\n", time2/1_000_000.0);
        System.out.printf("Iterator ✅               | %6.2f    | %.2fx\n", 
                         time3/1_000_000.0, (double)time3/time2);
        
        System.out.println("\n📊 CRITICAL LEARNING:");
        System.out.println("  • Traditional for loop with LinkedList is DISASTER! 🔥");
        System.out.println("  • get(i) is O(n) → Total complexity becomes O(n²)!");
        System.out.println("  • Iterator/Enhanced-for use O(1) next() → Total O(n)");
        System.out.println("  • NEVER use index access with LinkedList!");
        
        System.out.println("\n💡 PRODUCTION RULE:");
        System.out.println("  If collection type is unknown (polymorphism),");
        System.out.println("  ALWAYS prefer Iterator or Enhanced-for loop!");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * BENCHMARK 3: Iterator vs Stream
     * ================================
     */
    private static void benchmarkIteratorVsStream() {
        System.out.println("BENCHMARK #3: Iterator vs Stream");
        System.out.println("=".repeat(60));
        
        List<Integer> data = new ArrayList<>();
        for(int i = 0; i < MEDIUM_SIZE; i++) {
            data.add(i);
        }
        
        System.out.println("Task: Filter even numbers and sum");
        System.out.println("Size: " + data.size() + " elements\n");
        
        // Method 1: Iterator
        long start = System.nanoTime();
        long sum1 = 0;
        Iterator<Integer> it = data.iterator();
        while(it.hasNext()) {
            int num = it.next();
            if(num % 2 == 0) {
                sum1 += num;
            }
        }
        long time1 = System.nanoTime() - start;
        
        // Method 2: Stream
        start = System.nanoTime();
        long sum2 = data.stream()
                        .filter(n -> n % 2 == 0)
                        .mapToLong(Integer::longValue)
                        .sum();
        long time2 = System.nanoTime() - start;
        
        // Method 3: Parallel Stream
        start = System.nanoTime();
        long sum3 = data.parallelStream()
                        .filter(n -> n % 2 == 0)
                        .mapToLong(Integer::longValue)
                        .sum();
        long time3 = System.nanoTime() - start;
        
        // Results
        System.out.println("Method                    | Time (ms) | Relative");
        System.out.println("--------------------------|-----------|----------");
        System.out.printf("Iterator                  | %6.2f    | 1.00x\n", time1/1_000_000.0);
        System.out.printf("Stream                    | %6.2f    | %.2fx\n", 
                         time2/1_000_000.0, (double)time2/time1);
        System.out.printf("Parallel Stream           | %6.2f    | %.2fx\n", 
                         time3/1_000_000.0, (double)time3/time1);
        
        System.out.println("\n📊 OBSERVATIONS:");
        System.out.println("  • Iterator is fastest for simple operations");
        System.out.println("  • Stream has overhead but more readable");
        System.out.println("  • Parallel stream helps ONLY for large data + CPU-heavy tasks");
        System.out.println("  • Small datasets: Iterator wins");
        System.out.println("  • Large datasets + complex ops: Parallel stream wins");
        
        System.out.println("\n🎯 DECISION GUIDE:");
        System.out.println("  Use Iterator when:");
        System.out.println("    - Performance is critical");
        System.out.println("    - Simple iteration logic");
        System.out.println("    - Need to break early");
        
        System.out.println("\n  Use Stream when:");
        System.out.println("    - Readability > Performance");
        System.out.println("    - Complex transformations");
        System.out.println("    - Want to parallelize easily");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * BENCHMARK 4: Removal Strategies
     * ================================
     */
    private static void benchmarkRemovalStrategies() {
        System.out.println("BENCHMARK #4: Element Removal Strategies");
        System.out.println("=".repeat(60));
        
        int SIZE = 10_000;
        System.out.println("Task: Remove all even numbers");
        System.out.println("Initial size: " + SIZE + " elements\n");
        
        // Strategy 1: iterator.remove()
        List<Integer> list1 = new ArrayList<>();
        for(int i = 0; i < SIZE; i++) list1.add(i);
        
        long start = System.nanoTime();
        Iterator<Integer> it = list1.iterator();
        while(it.hasNext()) {
            if(it.next() % 2 == 0) {
                it.remove();
            }
        }
        long time1 = System.nanoTime() - start;
        
        // Strategy 2: removeIf()
        List<Integer> list2 = new ArrayList<>();
        for(int i = 0; i < SIZE; i++) list2.add(i);
        
        start = System.nanoTime();
        list2.removeIf(n -> n % 2 == 0);
        long time2 = System.nanoTime() - start;
        
        // Strategy 3: Stream filter (creates new list)
        List<Integer> list3 = new ArrayList<>();
        for(int i = 0; i < SIZE; i++) list3.add(i);
        
        start = System.nanoTime();
        list3 = list3.stream()
                     .filter(n -> n % 2 != 0)
                     .collect(Collectors.toList());
        long time3 = System.nanoTime() - start;
        
        // Strategy 4: Two-pass (collect then remove)
        List<Integer> list4 = new ArrayList<>();
        for(int i = 0; i < SIZE; i++) list4.add(i);
        
        start = System.nanoTime();
        List<Integer> toRemove = new ArrayList<>();
        for(Integer n : list4) {
            if(n % 2 == 0) toRemove.add(n);
        }
        list4.removeAll(toRemove);
        long time4 = System.nanoTime() - start;
        
        // Results
        System.out.println("Strategy                  | Time (ms) | Relative");
        System.out.println("--------------------------|-----------|----------");
        System.out.printf("iterator.remove()         | %6.2f    | %.2fx\n", 
                         time1/1_000_000.0, (double)time1/time2);
        System.out.printf("removeIf() ✅             | %6.2f    | 1.00x  FASTEST\n", 
                         time2/1_000_000.0);
        System.out.printf("Stream filter             | %6.2f    | %.2fx\n", 
                         time3/1_000_000.0, (double)time3/time2);
        System.out.printf("Two-pass removeAll        | %6.2f    | %.2fx\n", 
                         time4/1_000_000.0, (double)time4/time2);
        
        System.out.println("\n📊 WINNER: removeIf() (Java 8+)");
        System.out.println("  • Most optimized internally");
        System.out.println("  • Single pass");
        System.out.println("  • Clean syntax");
        
        System.out.println("\n💡 RECOMMENDATION:");
        System.out.println("  1st choice: removeIf() (if Java 8+)");
        System.out.println("  2nd choice: iterator.remove()");
        System.out.println("  Avoid: Two-pass approach (memory + time overhead)");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * BENCHMARK 5: Custom Iterator Overhead
     * ======================================
     */
    private static void benchmarkCustomIteratorOverhead() {
        System.out.println("BENCHMARK #5: Custom Iterator Overhead");
        System.out.println("=".repeat(60));
        
        List<Integer> data = new ArrayList<>();
        for(int i = 0; i < MEDIUM_SIZE; i++) {
            data.add(i);
        }
        
        System.out.println("Task: Iterate through all elements");
        System.out.println("Size: " + data.size() + " elements\n");
        
        // Built-in iterator
        long start = System.nanoTime();
        Iterator<Integer> builtIn = data.iterator();
        while(builtIn.hasNext()) {
            builtIn.next();
        }
        long time1 = System.nanoTime() - start;
        
        // Wrapper iterator (adds one level of indirection)
        class WrapperIterator<T> implements Iterator<T> {
            private Iterator<T> delegate;
            
            WrapperIterator(Iterator<T> delegate) {
                this.delegate = delegate;
            }
            
            @Override
            public boolean hasNext() {
                return delegate.hasNext();
            }
            
            @Override
            public T next() {
                return delegate.next();
            }
        }
        
        start = System.nanoTime();
        Iterator<Integer> wrapped = new WrapperIterator<>(data.iterator());
        while(wrapped.hasNext()) {
            wrapped.next();
        }
        long time2 = System.nanoTime() - start;
        
        // Results
        System.out.println("Type                      | Time (ms) | Overhead");
        System.out.println("--------------------------|-----------|----------");
        System.out.printf("Built-in iterator         | %6.2f    | baseline\n", time1/1_000_000.0);
        System.out.printf("Wrapper iterator          | %6.2f    | +%.1f%%\n", 
                         time2/1_000_000.0, ((double)time2/time1 - 1) * 100);
        
        System.out.println("\n📊 OVERHEAD ANALYSIS:");
        System.out.println("  • Custom iterators add ~5-15% overhead");
        System.out.println("  • Usually acceptable for readability/functionality");
        System.out.println("  • Profile before optimizing!");
        
        System.out.println("\n💡 WHEN OVERHEAD MATTERS:");
        System.out.println("  • Tight inner loops (millions of iterations)");
        System.out.println("  • Real-time systems (latency sensitive)");
        System.out.println("  • High-frequency trading");
        System.out.println("  • Game engines (per-frame operations)");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * OPTIMIZATION TIPS
     * =================
     */
    private static void optimizationTips() {
        System.out.println("OPTIMIZATION TIPS & TRICKS");
        System.out.println("=".repeat(60));
        
        System.out.println("\n1. CHOOSE RIGHT COLLECTION:");
        System.out.println("   ArrayList → Fast index access, slower remove");
        System.out.println("   LinkedList → Fast add/remove, slower index access");
        System.out.println("   HashSet → Fast contains check");
        System.out.println("   TreeSet → Sorted, slower than HashSet");
        
        System.out.println("\n2. ITERATOR SELECTION:");
        System.out.println("   ✅ Use Iterator/Enhanced-for for LinkedList");
        System.out.println("   ✅ Traditional for loop OK for ArrayList");
        System.out.println("   ✅ removeIf() for bulk removal (Java 8+)");
        System.out.println("   ❌ NEVER index-based loop for LinkedList!");
        
        System.out.println("\n3. AVOID COMMON ANTI-PATTERNS:");
        System.out.println("   ❌ list.size() in loop condition (cache it!)");
        System.out.println("      BAD:  for(int i=0; i<list.size(); i++)");
        System.out.println("      GOOD: int size = list.size();");
        System.out.println("            for(int i=0; i<size; i++)");
        
        System.out.println("\n   ❌ Creating iterator in loop");
        System.out.println("      BAD:  while(collection.iterator().hasNext())");
        System.out.println("      GOOD: Iterator it = collection.iterator();");
        System.out.println("            while(it.hasNext())");
        
        System.out.println("\n4. MEMORY OPTIMIZATION:");
        System.out.println("   • Use iterator instead of copying to new list");
        System.out.println("   • Lazy evaluation with custom iterators");
        System.out.println("   • Stream for one-time processing");
        System.out.println("   • Reuse iterators when possible");
        
        System.out.println("\n5. PARALLEL PROCESSING:");
        System.out.println("   • Use parallel streams for CPU-intensive tasks");
        System.out.println("   • Data size > 10,000 to benefit from parallelism");
        System.out.println("   • Be careful with thread-safety!");
        
        System.out.println("\n6. PROFILING BEFORE OPTIMIZING:");
        System.out.println("   Tools: JProfiler, YourKit, VisualVM");
        System.out.println("   Measure: Don't guess!");
        System.out.println("   Focus: Optimize hotspots only");
        
        System.out.println("\n" + "-".repeat(60) + "\n");
    }
    
    /**
     * PERFORMANCE SUMMARY
     * ===================
     */
    private static void performanceSummary() {
        System.out.println("=" .repeat(60));
        System.out.println("PERFORMANCE SUMMARY");
        System.out.println("=".repeat(60));
        
        System.out.println("\n🏆 PERFORMANCE RANKINGS:");
        System.out.println("-".repeat(40));
        
        System.out.println("\nArrayList Traversal:");
        System.out.println("  1. Traditional for loop (fastest)");
        System.out.println("  2. Enhanced for loop / Iterator");
        System.out.println("  3. forEach lambda");
        System.out.println("  4. Stream");
        
        System.out.println("\nLinkedList Traversal:");
        System.out.println("  1. Iterator / Enhanced for (fastest)");
        System.out.println("  2. forEach lambda");
        System.out.println("  3. Stream");
        System.out.println("  4. Traditional for loop (SLOWEST - O(n²)!)");
        
        System.out.println("\nElement Removal:");
        System.out.println("  1. removeIf() (fastest - Java 8+)");
        System.out.println("  2. iterator.remove()");
        System.out.println("  3. Stream filter");
        System.out.println("  4. Two-pass removeAll");
        
        System.out.println("\n\n📊 COMPLEXITY CHEAT SHEET:");
        System.out.println("-".repeat(60));
        System.out.println("Operation           | ArrayList | LinkedList | HashSet");
        System.out.println("--------------------|-----------|------------|--------");
        System.out.println("get(i)              | O(1)      | O(n)       | N/A");
        System.out.println("Iterator.next()     | O(1)      | O(1)       | O(1)");
        System.out.println("add(e)              | O(1)*     | O(1)       | O(1)");
        System.out.println("remove(i)           | O(n)      | O(1)**     | O(1)");
        System.out.println("iterator.remove()   | O(n)      | O(1)       | O(1)");
        System.out.println("contains(e)         | O(n)      | O(n)       | O(1)");
        System.out.println("\n* Amortized  ** If you have reference to node");
        
        System.out.println("\n\n🎯 GOLDEN RULES:");
        System.out.println("-".repeat(40));
        System.out.println("1. ArrayList + index access = Fast ✅");
        System.out.println("2. LinkedList + index access = Slow ❌");
        System.out.println("3. LinkedList + Iterator = Fast ✅");
        System.out.println("4. Unknown collection type → Use Iterator ✅");
        System.out.println("5. Bulk removal → Use removeIf() ✅");
        System.out.println("6. Simple iteration → Enhanced for loop ✅");
        System.out.println("7. Complex transformations → Stream ✅");
        System.out.println("8. Performance critical → Profile first! 📊");
        
        System.out.println("\n\n💡 FINAL ADVICE:");
        System.out.println("-".repeat(40));
        System.out.println("\"Premature optimization is the root of all evil\"");
        System.out.println("                                    - Donald Knuth");
        System.out.println();
        System.out.println("BUT: Knowing these fundamentals is NOT premature!");
        System.out.println("Write clean code first, optimize bottlenecks later.");
        System.out.println();
        System.out.println("📈 Steps:");
        System.out.println("  1. Write correct code");
        System.out.println("  2. Profile to find bottlenecks");
        System.out.println("  3. Optimize ONLY bottlenecks");
        System.out.println("  4. Measure improvements");
        System.out.println("  5. Repeat if necessary");
        
        System.out.println("\n\n✨ Next: 10_ModernAlternatives.java");
        System.out.println("   (Java 8+ features: Streams, Optional, etc.)\n");
    }
}

/*
 * ==========================================
 * KEY PERFORMANCE LEARNINGS
 * ==========================================
 * 
 * 1. COLLECTION MATTERS:
 *    ArrayList vs LinkedList makes HUGE difference!
 *    O(1) vs O(n) can be 100x-1000x slower!
 * 
 * 2. ITERATION METHOD MATTERS:
 *    But usually not as much as collection choice
 *    ~10-30% difference between methods
 * 
 * 3. ALWAYS PROFILE:
 *    Your intuition might be wrong!
 *    Measure before optimizing!
 * 
 * 4. READABILITY vs PERFORMANCE:
 *    Usually readability wins
 *    Optimize only proven bottlenecks
 * 
 * 5. MODERN JAVA:
 *    Java 8+ features (removeIf, Streams) are
 *    BOTH readable AND performant!
 * 
 * NEXT FILE: 10_ModernAlternatives.java
 * (Explore Java 8+ alternatives to traditional iterators!)
 */
