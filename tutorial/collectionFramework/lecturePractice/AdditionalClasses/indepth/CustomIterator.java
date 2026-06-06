package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;

/**
 * ==========================================
 * CUSTOM ITERATOR - Build Your Own!
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Bhai, ab tak hum built-in iterators use kar rahe the.
 * Lekin agar tumhe APNA custom data structure banana hai,
 * to uske liye APNA iterator bhi banana padega! 🛠️
 * 
 * WHY CREATE CUSTOM ITERATOR?
 * ============================
 * 1. Custom data structures (LinkedList, Tree, Graph)
 * 2. Special traversal logic (skip nulls, filter conditions)
 * 3. Lazy evaluation (generate elements on-the-fly)
 * 4. Custom business logic during iteration
 * 
 * ITERATOR INTERFACE:
 * ===================
 * public interface Iterator<E> {
 *     boolean hasNext();
 *     E next();
 *     default void remove() { throw new UnsupportedOperationException(); }
 *     default void forEachRemaining(Consumer<? super E> action) { ... }
 * }
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
public class CustomIterator {
    
    public static void main(String[] args) {
        System.out.println("=== CUSTOM ITERATOR IMPLEMENTATION ===\n");
        
        // Example 1: Simple custom collection
        simpleCustomCollection();
        
        // Example 2: Filtering iterator
        filteringIterator();
        
        // Example 3: Range iterator
        rangeIterator();
        
        // Example 4: Circular iterator
        circularIterator();
        
        // Example 5: Tree iterator
        treeIterator();
        
        // Industry best practices
        bestPractices();
    }
    
    /**
     * EXAMPLE 1: Simple Custom Collection
     * ====================================
     * Basic custom collection with iterator support
     */
    private static void simpleCustomCollection() {
        System.out.println("1. SIMPLE CUSTOM COLLECTION:");
        System.out.println("-".repeat(60));
        
        /**
         * Custom Array-based collection
         * Implements Iterable to support enhanced for-loop
         */
        class SimpleCollection<E> implements Iterable<E> {
            private Object[] elements;
            private int size = 0;
            
            public SimpleCollection(int capacity) {
                elements = new Object[capacity];
            }
            
            public void add(E element) {
                if(size < elements.length) {
                    elements[size++] = element;
                }
            }
            
            public int size() {
                return size;
            }
            
            @Override
            public Iterator<E> iterator() {
                return new SimpleIterator();
            }
            
            /**
             * Private inner class - Custom Iterator implementation
             */
            private class SimpleIterator implements Iterator<E> {
                private int currentIndex = 0;
                
                @Override
                public boolean hasNext() {
                    return currentIndex < size;
                }
                
                @Override
                @SuppressWarnings("unchecked")
                public E next() {
                    if(!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return (E) elements[currentIndex++];
                }
                
                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Remove not supported");
                }
            }
        }
        
        // Usage
        SimpleCollection<String> collection = new SimpleCollection<>(5);
        collection.add("Java");
        collection.add("Python");
        collection.add("C++");
        
        System.out.println("Using custom iterator:");
        
        // Method 1: Manual iteration
        Iterator<String> it = collection.iterator();
        while(it.hasNext()) {
            System.out.println("  → " + it.next());
        }
        
        System.out.println();
        
        // Method 2: Enhanced for-loop (works because we implemented Iterable!)
        System.out.println("Using enhanced for-loop:");
        for(String lang : collection) {
            System.out.println("  → " + lang);
        }
        
        /*
         * KEY CONCEPTS:
         * =============
         * 1. Implement Iterable<E> interface in your collection class
         * 2. Override iterator() method to return your custom Iterator
         * 3. Custom Iterator implements Iterator<E> interface
         * 4. Implement hasNext(), next(), and optionally remove()
         * 5. Inner class has access to outer class's fields!
         */
        
        System.out.println("\n");
    }
    
    /**
     * EXAMPLE 2: Filtering Iterator
     * ==============================
     * Iterator that skips elements based on condition
     */
    private static void filteringIterator() {
        System.out.println("2. FILTERING ITERATOR:");
        System.out.println("-".repeat(60));
        
        /**
         * Iterator that filters elements based on a predicate
         * Real-world use: Skip null values, filter by condition, etc.
         */
        class FilteringIterator<E> implements Iterator<E> {
            private Iterator<E> sourceIterator;
            private java.util.function.Predicate<E> predicate;
            private E nextElement;
            private boolean hasNextElement;
            
            public FilteringIterator(Iterator<E> source, java.util.function.Predicate<E> predicate) {
                this.sourceIterator = source;
                this.predicate = predicate;
                advance(); // Find first valid element
            }
            
            /**
             * Advance to next element that matches predicate
             */
            private void advance() {
                hasNextElement = false;
                
                while(sourceIterator.hasNext()) {
                    E element = sourceIterator.next();
                    
                    if(predicate.test(element)) {
                        nextElement = element;
                        hasNextElement = true;
                        break;
                    }
                }
            }
            
            @Override
            public boolean hasNext() {
                return hasNextElement;
            }
            
            @Override
            public E next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                
                E result = nextElement;
                advance(); // Prepare next element
                return result;
            }
        }
        
        // Usage: Filter out even numbers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        System.out.println("Original: " + numbers);
        System.out.println("\nFiltered (odd numbers only):");
        
        Iterator<Integer> oddIterator = new FilteringIterator<>(
            numbers.iterator(),
            num -> num % 2 != 0 // Keep only odd
        );
        
        while(oddIterator.hasNext()) {
            System.out.print(oddIterator.next() + " ");
        }
        System.out.println();
        
        // Another example: Filter out nulls
        List<String> withNulls = Arrays.asList("Java", null, "Python", null, "C++");
        
        System.out.println("\nOriginal with nulls: " + withNulls);
        System.out.println("Filtered (non-null only):");
        
        Iterator<String> nonNullIterator = new FilteringIterator<>(
            withNulls.iterator(),
            obj -> obj != null // Keep only non-null
        );
        
        while(nonNullIterator.hasNext()) {
            System.out.print(nonNullIterator.next() + " ");
        }
        System.out.println();
        
        /*
         * FILTERING ITERATOR PATTERN:
         * ===========================
         * - Wraps existing iterator
         * - Applies filter logic during iteration
         * - Lazy evaluation (checks only when needed)
         * - Memory efficient (no intermediate collection)
         * 
         * INDUSTRY USE CASES:
         * ===================
         * - Log file processing (skip empty lines)
         * - Data validation (skip invalid records)
         * - Security filtering (hide sensitive data)
         * - Performance optimization (skip unnecessary processing)
         */
        
        System.out.println("\n");
    }
    
    /**
     * EXAMPLE 3: Range Iterator
     * ==========================
     * Generate numbers on-the-fly without storing them
     */
    private static void rangeIterator() {
        System.out.println("3. RANGE ITERATOR (Lazy Generation):");
        System.out.println("-".repeat(60));
        
        /**
         * Generates numbers in a range without storing them
         * Similar to Python's range() or Java's IntStream
         */
        class RangeIterator implements Iterator<Integer> {
            private int current;
            private int end;
            private int step;
            
            public RangeIterator(int start, int end, int step) {
                this.current = start;
                this.end = end;
                this.step = step;
            }
            
            public RangeIterator(int start, int end) {
                this(start, end, 1);
            }
            
            @Override
            public boolean hasNext() {
                return step > 0 ? current < end : current > end;
            }
            
            @Override
            public Integer next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                
                int result = current;
                current += step;
                return result;
            }
        }
        
        // Example 1: Simple range
        System.out.println("Range(0, 10):");
        Iterator<Integer> range1 = new RangeIterator(0, 10);
        while(range1.hasNext()) {
            System.out.print(range1.next() + " ");
        }
        System.out.println();
        
        // Example 2: Range with step
        System.out.println("\nRange(0, 20, 3):");
        Iterator<Integer> range2 = new RangeIterator(0, 20, 3);
        while(range2.hasNext()) {
            System.out.print(range2.next() + " ");
        }
        System.out.println();
        
        // Example 3: Countdown
        System.out.println("\nCountdown Range(10, 0, -1):");
        Iterator<Integer> countdown = new RangeIterator(10, 0, -1);
        while(countdown.hasNext()) {
            System.out.print(countdown.next() + " ");
        }
        System.out.println();
        
        /*
         * LAZY EVALUATION BENEFITS:
         * =========================
         * 1. Memory efficient - No array storage needed!
         * 2. Can represent infinite sequences
         * 3. Fast - generates only what's needed
         * 4. Composable - can chain with other iterators
         * 
         * REAL-WORLD EXAMPLE:
         * ===================
         * Instead of:
         *   List<Integer> ids = new ArrayList<>();
         *   for(int i=0; i<1000000; i++) ids.add(i);
         * 
         * Use:
         *   Iterator<Integer> ids = new RangeIterator(0, 1000000);
         * 
         * Saves memory! No million integers stored! 🚀
         */
        
        System.out.println("\n");
    }
    
    /**
     * EXAMPLE 4: Circular Iterator
     * =============================
     * Never-ending iterator that loops back to start
     */
    private static void circularIterator() {
        System.out.println("4. CIRCULAR ITERATOR (Infinite Loop):");
        System.out.println("-".repeat(60));
        
        /**
         * Iterates circularly - goes back to start after end
         * Use case: Round-robin scheduling, playlist repeat, etc.
         */
        class CircularIterator<E> implements Iterator<E> {
            private List<E> list;
            private int currentIndex = 0;
            
            public CircularIterator(List<E> list) {
                if(list == null || list.isEmpty()) {
                    throw new IllegalArgumentException("List must not be empty");
                }
                this.list = list;
            }
            
            @Override
            public boolean hasNext() {
                return true; // Always has next (infinite!)
            }
            
            @Override
            public E next() {
                E element = list.get(currentIndex);
                currentIndex = (currentIndex + 1) % list.size(); // Wrap around!
                return element;
            }
        }
        
        // Example: Round-robin server selection
        List<String> servers = Arrays.asList("Server1", "Server2", "Server3");
        
        System.out.println("Servers: " + servers);
        System.out.println("\nRound-robin request distribution (12 requests):");
        
        Iterator<String> serverIterator = new CircularIterator<>(servers);
        
        for(int i = 1; i <= 12; i++) {
            String server = serverIterator.next();
            System.out.println("  Request " + i + " → " + server);
        }
        
        /*
         * CIRCULAR ITERATOR USE CASES:
         * =============================
         * 1. Load balancing (round-robin server selection)
         * 2. Music playlist (repeat mode)
         * 3. Carousel/slideshow (loop through images)
         * 4. Game turns (player rotation)
         * 5. Shift scheduling (rotating shifts)
         * 
         * ⚠️ WARNING:
         * hasNext() always returns true!
         * Must manually break the loop or it runs forever! ∞
         */
        
        System.out.println("\n");
    }
    
    /**
     * EXAMPLE 5: Tree Iterator (Advanced)
     * ====================================
     * Traverse tree structure using iterator pattern
     */
    private static void treeIterator() {
        System.out.println("5. TREE ITERATOR (In-order Traversal):");
        System.out.println("-".repeat(60));
        
        /**
         * Simple Binary Tree Node
         */
        class TreeNode {
            int value;
            TreeNode left, right;
            
            TreeNode(int value) {
                this.value = value;
            }
        }
        
        /**
         * Binary Tree with custom iterator for in-order traversal
         */
        class BinaryTree implements Iterable<Integer> {
            TreeNode root;
            
            public void insert(int value) {
                root = insertRec(root, value);
            }
            
            private TreeNode insertRec(TreeNode node, int value) {
                if(node == null) {
                    return new TreeNode(value);
                }
                
                if(value < node.value) {
                    node.left = insertRec(node.left, value);
                } else {
                    node.right = insertRec(node.right, value);
                }
                
                return node;
            }
            
            @Override
            public Iterator<Integer> iterator() {
                return new InOrderIterator(root);
            }
            
            /**
             * In-order traversal iterator (Left-Root-Right)
             */
            private class InOrderIterator implements Iterator<Integer> {
                private Stack<TreeNode> stack = new Stack<>();
                
                public InOrderIterator(TreeNode root) {
                    pushLeftNodes(root);
                }
                
                private void pushLeftNodes(TreeNode node) {
                    while(node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }
                
                @Override
                public boolean hasNext() {
                    return !stack.isEmpty();
                }
                
                @Override
                public Integer next() {
                    if(!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    
                    TreeNode node = stack.pop();
                    int result = node.value;
                    
                    // Process right subtree
                    if(node.right != null) {
                        pushLeftNodes(node.right);
                    }
                    
                    return result;
                }
            }
        }
        
        // Build tree
        BinaryTree tree = new BinaryTree();
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        
        System.out.println("Inserting values: " + Arrays.toString(values));
        for(int val : values) {
            tree.insert(val);
        }
        
        System.out.println("\nTree structure:");
        System.out.println("        50");
        System.out.println("       /  \\");
        System.out.println("      30   70");
        System.out.println("     / \\   / \\");
        System.out.println("    20 40 60 80");
        
        System.out.println("\nIn-order traversal (should be sorted):");
        for(Integer val : tree) {
            System.out.print(val + " ");
        }
        System.out.println();
        
        /*
         * TREE ITERATOR COMPLEXITY:
         * ==========================
         * - Space: O(h) where h is tree height (for stack)
         * - Time: O(1) amortized per next() call
         * 
         * TRAVERSAL TYPES:
         * ================
         * - In-order: Left → Root → Right (sorted for BST)
         * - Pre-order: Root → Left → Right
         * - Post-order: Left → Right → Root
         * - Level-order: Level by level (BFS)
         * 
         * Each can be implemented as custom iterator!
         * 
         * INDUSTRY APPLICATIONS:
         * ======================
         * - File system traversal
         * - HTML DOM traversal
         * - Expression tree evaluation
         * - Database index traversal
         */
        
        System.out.println("\n");
    }
    
    /**
     * BEST PRACTICES FOR CUSTOM ITERATORS
     * ====================================
     */
    private static void bestPractices() {
        System.out.println("6. CUSTOM ITERATOR BEST PRACTICES:");
        System.out.println("-".repeat(60));
        
        System.out.println("✅ DO:");
        System.out.println("  1. Implement Iterable<E> in your collection class");
        System.out.println("  2. Make iterator class private inner class");
        System.out.println("  3. Throw NoSuchElementException in next() when exhausted");
        System.out.println("  4. hasNext() should not modify state");
        System.out.println("  5. Document whether remove() is supported");
        System.out.println("  6. Consider fail-fast behavior for concurrent modification");
        System.out.println("  7. Keep iterator lightweight (avoid heavy computation)");
        System.out.println("  8. Make iterator generic <E> for type safety");
        
        System.out.println("\n❌ DON'T:");
        System.out.println("  1. Don't store all elements before iteration (defeats lazy evaluation)");
        System.out.println("  2. Don't make hasNext() expensive (called frequently!)");
        System.out.println("  3. Don't forget to check hasNext() in next()");
        System.out.println("  4. Don't modify collection state in hasNext()");
        System.out.println("  5. Don't make iterator publicly accessible if not needed");
        System.out.println("  6. Don't leak iterator implementation details");
        
        System.out.println("\n📋 IMPLEMENTATION CHECKLIST:");
        System.out.println("  □ hasNext() returns boolean correctly");
        System.out.println("  □ next() throws NoSuchElementException when appropriate");
        System.out.println("  □ remove() either works or throws UnsupportedOperationException");
        System.out.println("  □ Iterator doesn't expose internal structure");
        System.out.println("  □ Thread-safety considered (if needed)");
        System.out.println("  □ Memory efficiency maintained");
        System.out.println("  □ Documentation clear about behavior");
        
        System.out.println("\n🎯 WHEN TO CREATE CUSTOM ITERATOR:");
        System.out.println("  ✓ Custom data structure (Tree, Graph, etc.)");
        System.out.println("  ✓ Special traversal order needed");
        System.out.println("  ✓ Lazy generation/filtering required");
        System.out.println("  ✓ Integration with enhanced for-loop wanted");
        System.out.println("  ✓ Standard iterators don't fit your use case");
        
        System.out.println("\n🚀 PERFORMANCE TIPS:");
        System.out.println("  • Cache next element if computation is expensive");
        System.out.println("  • Use primitive types to avoid boxing overhead");
        System.out.println("  • Consider batch processing for I/O operations");
        System.out.println("  • Profile before optimizing (premature optimization is evil!)");
        
        System.out.println("\n");
    }
}

/*
 * ==========================================
 * CUSTOM ITERATOR TEMPLATE
 * ==========================================
 * 
 * Use this as a starting point for your own iterator:
 * 
 * class MyCollection<E> implements Iterable<E> {
 *     // Your data structure
 *     
 *     @Override
 *     public Iterator<E> iterator() {
 *         return new MyIterator();
 *     }
 *     
 *     private class MyIterator implements Iterator<E> {
 *         // State variables
 *         private int currentPosition = 0;
 *         
 *         @Override
 *         public boolean hasNext() {
 *             // Check if more elements exist
 *             return currentPosition < size();
 *         }
 *         
 *         @Override
 *         public E next() {
 *             if(!hasNext()) {
 *                 throw new NoSuchElementException();
 *             }
 *             // Return element and advance
 *             return getElementAt(currentPosition++);
 *         }
 *         
 *         @Override
 *         public void remove() {
 *             throw new UnsupportedOperationException();
 *         }
 *     }
 * }
 * 
 * 
 * KEY LEARNINGS:
 * ==============
 * 1. Iterator pattern separates collection from traversal logic
 * 2. Implementing Iterable<E> enables enhanced for-loop
 * 3. Custom iterators enable lazy evaluation and filtering
 * 4. Inner class has access to outer class's private members
 * 5. Always throw NoSuchElementException when exhausted
 * 6. Consider fail-fast vs fail-safe based on use case
 * 
 * NEXT FILE: 08_IndustryPatterns.java
 * (Real-world iterator usage patterns in production code!)
 */
