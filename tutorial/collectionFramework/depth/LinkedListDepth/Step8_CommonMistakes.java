package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.*;

public class Step8_CommonMistakes {
    
    static void main(String[] args) {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║      STEP 8: COMMON MISTAKES & HOW TO AVOID THEM              ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * 15+ Critical mistakes developers make with LinkedList
         * and how to avoid them
         */

        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  15+ LINKEDLIST MISTAKES & HOW TO AVOID THEM                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // MISTAKE 1: Using LinkedList when ArrayList is better
        System.out.println("❌ MISTAKE 1: Using LinkedList by default (wrong choice!)");
        System.out.println("────────────────────────────────────────────────────────\n");
        System.out.println("WHAT'S WRONG:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("for (int i = 0; i < 1000; i++) {");
        System.out.println("    list.add(data[i]);");
        System.out.println("    String s = list.get(i);  // O(n) each time!");
        System.out.println("}");
        System.out.println("// Total: 1000 add() calls O(1) + 1000 get() calls O(n) = O(n²)");
        System.out.println();
        System.out.println("✓ CORRECT:");
        System.out.println("ArrayList<String> list = new ArrayList<>();  // Default choice");
        System.out.println("for (int i = 0; i < 1000; i++) {");
        System.out.println("    list.add(data[i]);  // O(1)");
        System.out.println("    String s = list.get(i);  // O(1)");
        System.out.println("}");
        System.out.println("// Total: O(n)");
        System.out.println();
        System.out.println("LESSON: ArrayList is default choice 99% of time.\n");

        // MISTAKE 2: Loop with index to iterate LinkedList
        System.out.println("❌ MISTAKE 2: Loop with index (classic O(n²) error!)");
        System.out.println("────────────────────────────────────────────────────\n");
        System.out.println("WHAT'S WRONG:");
        System.out.println("LinkedList<Item> list = new LinkedList<>();");
        System.out.println("// ... add items ...");
        System.out.println("for (int i = 0; i < list.size(); i++) {");
        System.out.println("    Item item = list.get(i);  // ← get(i) is O(n)!");
        System.out.println("    process(item);");
        System.out.println("}");
        System.out.println("// With 1000 items: 1000*1000 = 1,000,000 traversals!");
        System.out.println();
        System.out.println("✓ CORRECT:");
        System.out.println("LinkedList<Item> list = new LinkedList<>();");
        System.out.println("for (Item item : list) {  // Uses iterator");
        System.out.println("    process(item);  // O(1) per item");
        System.out.println("}");
        System.out.println("// Total: O(n)");
        System.out.println();
        System.out.println("LESSON: Always iterate, never index loop LinkedList.\n");

        // MISTAKE 3: Forgetting LinkedList is doubly-linked
        System.out.println("❌ MISTAKE 3: Thinking remove(0) is faster than remove(size-1)");
        System.out.println("──────────────────────────────────────────────────────────\n");
        System.out.println("WHAT'S WRONG:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("// ... add 1 million items ...");
        System.out.println("String last = list.remove(list.size()-1);  // Thinking: O(n)!");
        System.out.println();
        System.out.println("WHY IT'S ACTUALLY O(1):");
        System.out.println("Java's LinkedList is DOUBLY-LINKED:");
        System.out.println("• Each node has prev pointer");
        System.out.println("• LinkedList has tail pointer");
        System.out.println("• remove(size-1) just: tail = tail.prev (O(1))");
        System.out.println();
        System.out.println("LESSON: Java's LinkedList has both ends optimized.\n");

        // MISTAKE 4: Using remove(Object) by mistake
        System.out.println("❌ MISTAKE 4: Calling list.remove(item) instead of list.remove(index)");
        System.out.println("───────────────────────────────────────────────────────────────\n");
        System.out.println("WHAT'S CONFUSING:");
        System.out.println("LinkedList<Integer> list = new LinkedList<>();");
        System.out.println("list.add(100);");
        System.out.println("list.add(200);");
        System.out.println("list.add(300);");
        System.out.println();
        System.out.println("list.remove(2);  // ← Removes at index 2? Or removes value 2?");
        System.out.println();
        System.out.println("ANSWER: Integer is autoboxed, so...");
        System.out.println("new Integer(2) != existing elements");
        System.out.println("It removes FIRST element equal to 2 (if exists)");
        System.out.println();
        System.out.println("✓ TO BE CLEAR:");
        System.out.println("list.remove((Object)2);      // Remove value 2");
        System.out.println("list.remove(Integer.valueOf(2));  // Remove value 2");
        System.out.println();
        System.out.println("LESSON: Be explicit with remove() to avoid confusion.\n");

        // MISTAKE 5: ConcurrentModificationException
        System.out.println("❌ MISTAKE 5: Modifying list while iterating (ConcurrentModification)");
        System.out.println("───────────────────────────────────────────────────────────────\n");
        System.out.println("WHAT'S WRONG:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(\"A\"); list.add(\"B\"); list.add(\"C\");");
        System.out.println();
        System.out.println("for (String s : list) {");
        System.out.println("    if (s.equals(\"B\")) {");
        System.out.println("        list.remove(s);  // ← ConcurrentModificationException!");
        System.out.println("    }");
        System.out.println("}");
        System.out.println();
        System.out.println("✓ CORRECT:");
        System.out.println("Iterator<String> iter = list.iterator();");
        System.out.println("while (iter.hasNext()) {");
        System.out.println("    String s = iter.next();");
        System.out.println("    if (s.equals(\"B\")) {");
        System.out.println("        iter.remove();  // Safe! Uses iterator");
        System.out.println("    }");
        System.out.println("}");
        System.out.println();
        System.out.println("LESSON: Always use iterator.remove() when modifying during iteration.\n");

        // MISTAKE 6: Forgetting remove() returns the removed element
        System.out.println("❌ MISTAKE 6: Not capturing remove() return value");
        System.out.println("──────────────────────────────────────────────── \n");
        System.out.println("WHAT'S WRONG:");
        System.out.println("LinkedList<Task> queue = new LinkedList<>();");
        System.out.println("queue.add(task1);");
        System.out.println("queue.remove();  // ← Removed but not captured!");
        System.out.println("task1.execute();  // task1 is still in memory but lost from queue");
        System.out.println();
        System.out.println("✓ CORRECT:");
        System.out.println("LinkedList<Task> queue = new LinkedList<>();");
        System.out.println("queue.add(task1);");
        System.out.println("Task removed = queue.remove();  // Capture the removed element");
        System.out.println("removed.execute();");
        System.out.println();
        System.out.println("✓ OR USE POLL:");
        System.out.println("Task removed = queue.poll();  // More explicit for Queue");
        System.out.println("if (removed != null) removed.execute();");
        System.out.println();
        System.out.println("LESSON: Capture removed elements, or use poll/pop.\n");

        // MISTAKE 7: Not handling empty queue/stack
        System.out.println("❌ MISTAKE 7: Not checking if queue/stack is empty");
        System.out.println("─────────────────────────────────────────────────\n");
        System.out.println("WHAT'S WRONG:");
        System.out.println("Queue<Task> queue = new LinkedList<>();");
        System.out.println("Task next = queue.remove();  // ← Throws exception if empty!");
        System.out.println();
        System.out.println("✓ CORRECT:");
        System.out.println("Queue<Task> queue = new LinkedList<>();");
        System.out.println("if (!queue.isEmpty()) {");
        System.out.println("    Task next = queue.remove();");
        System.out.println("    process(next);");
        System.out.println("}");
        System.out.println();
        System.out.println("✓ OR USE POLL:");
        System.out.println("Task next = queue.poll();  // Returns null if empty");
        System.out.println("if (next != null) {");
        System.out.println("    process(next);");
        System.out.println("}");
        System.out.println();
        System.out.println("LESSON: Always check or use safe methods like poll().\n");

        // MISTAKE 8: Using LinkedList as cache
        System.out.println("❌ MISTAKE 8: Building LRU Cache with LinkedList manually");
        System.out.println("───────────────────────────────────────────────────────\n");
        System.out.println("WHAT'S INEFFICIENT:");
        System.out.println("private LinkedList<K> order;  // Track order");
        System.out.println("private Map<K, V> cache;");
        System.out.println();
        System.out.println("public V get(K key) {");
        System.out.println("    order.remove(key);  // ← O(n) to find and remove!");
        System.out.println("    order.addLast(key);  // O(1)");
        System.out.println("    return cache.get(key);  // O(1)");
        System.out.println("}");
        System.out.println();
        System.out.println("✓ USE LINKEDHASHMAP INSTEAD:");
        System.out.println("Map<String, String> cache = new LinkedHashMap<>(16, 0.75f, true) {");
        System.out.println("    protected boolean removeEldestEntry(Map.Entry eldest) {");
        System.out.println("        return size() > CAPACITY;");
        System.out.println("    }");
        System.out.println("};");
        System.out.println();
        System.out.println("LESSON: Use LinkedHashMap for LRU, not manual LinkedList.\n");

        // MISTAKE 9: Comparing LinkedList and ArrayList incorrectly
        System.out.println("❌ MISTAKE 9: Wrong mental model - thinking LinkedList is always faster");
        System.out.println("──────────────────────────────────────────────────────────────────\n");
        System.out.println("WRONG: Just because LinkedList == O(1) remove from end");
        System.out.println();
        System.out.println("But:");
        System.out.println("• LinkedList uses 3x more memory");
        System.out.println("• LinkedList has worse cache locality (slower iteration)");
        System.out.println("• LinkedList is only faster for specific patterns (Queue/Stack)");
        System.out.println("• ArrayList is faster for most real-world use cases");
        System.out.println();
        System.out.println("✓ CORRECT MENTAL MODEL:");
        System.out.println("LinkedList is specialized tool for Queue/Stack/Deque patterns");
        System.out.println("ArrayList is general-purpose, faster for most cases");
        System.out.println();
        System.out.println("LESSON: Choose by usage pattern, not theoretical complexity.\n");

        // MISTAKE 10: Not understanding add(0, E) internals
        System.out.println("❌ MISTAKE 10: Thinking add(0, E) is O(1) because it's LinkedList");
        System.out.println("───────────────────────────────────────────────────────────────\n");
        System.out.println("MISCONCEPTION:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(0, \"new\");  // Seems O(1) but it's:  ");
        System.out.println("                      // - Traverse to index 0: O(1) (head)");
        System.out.println("                      // - Insert: O(1)");
        System.out.println("                      // Total: O(1)");
        System.out.println();
        System.out.println("But for middle positions:");
        System.out.println("list.add(500, \"new\");  // Must traverse 500 nodes = O(n)");
        System.out.println();
        System.out.println("LESSON: add(0, E) is O(1) only for FIRST position, use addFirst().\n");

        // MISTAKE 11: Memory waste with small lists
        System.out.println("❌ MISTAKE 11: Using LinkedList for small collections");
        System.out.println("──────────────────────────────────────────────────────\n");
        System.out.println("WASTEFUL:");
        System.out.println("LinkedList<Integer> list = new LinkedList<>();");
        System.out.println("for (int i = 0; i < 5; i++) list.add(i);");
        System.out.println("// Memory: ~240 bytes for 5 integers!");
        System.out.println();
        System.out.println("✓ EFFICIENT:");
        System.out.println("ArrayList<Integer> list = new ArrayList<>();");
        System.out.println("for (int i = 0; i < 5; i++) list.add(i);");
        System.out.println("// Memory: ~40 bytes for 5 integers");
        System.out.println();
        System.out.println("LESSON: LinkedList overhead only worth it for large collections.\n");

        // MISTAKE 12: Not using Deque for double-ended operations
        System.out.println("❌ MISTAKE 12: Using LinkedList directly instead of Deque interface");
        System.out.println("───────────────────────────────────────────────────────────────\n");
        System.out.println("NOT CLEAR:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(0, \"start\");  // What does this do? Unclear!");
        System.out.println("list.add(\"end\");");
        System.out.println();
        System.out.println("✓ CLEAR INTENT:");
        System.out.println("Deque<String> deque = new LinkedList<>();");
        System.out.println("deque.addFirst(\"start\");  // Crystal clear!");
        System.out.println("deque.addLast(\"end\");");
        System.out.println();
        System.out.println("LESSON: Use Deque interface for clarity, not LinkedList.\n");

        // MISTAKE 13: Not profiling before choosing
        System.out.println("❌ MISTAKE 13: Choosing data structure without profiling");
        System.out.println("─────────────────────────────────────────────────────\n");
        System.out.println("PREMATURE OPTIMIZATION:");
        System.out.println("LinkedList<Item> list = new LinkedList<>();  // Assumed faster");
        System.out.println("// But application bottleneck is get(i) access!");
        System.out.println();
        System.out.println("✓ CORRECT APPROACH:");
        System.out.println("1. Use ArrayList (default)");
        System.out.println("2. Profile the application");
        System.out.println("3. If bottleneck is add/remove at ends, switch to LinkedList");
        System.out.println();
        System.out.println("LESSON: Profile first, optimize second.\n");

        // MISTAKE 14: Forgetting about GC pressure
        System.out.println("❌ MISTAKE 14: Ignoring garbage collection overhead");
        System.out.println("────────────────────────────────────────────────────\n");
        System.out.println("CREATES MANY OBJECTS:");
        System.out.println("LinkedList<Integer> list = new LinkedList<>();");
        System.out.println("for (int i = 0; i < 1_000_000; i++) {");
        System.out.println("    list.add(i);  // Creates 1 million Node objects!");
        System.out.println("}");
        System.out.println("// GC pressure: Collecting 1M objects is expensive");
        System.out.println();
        System.out.println("✓ LESS PRESSURE:");
        System.out.println("ArrayList<Integer> list = new ArrayList<>(1_000_000);");
        System.out.println("for (int i = 0; i < 1_000_000; i++) {");
        System.out.println("    list.add(i);  // Only array resizing, few allocations");
        System.out.println("}");
        System.out.println();
        System.out.println("LESSON: LinkedList creates more objects, more GC pressure.\n");

        // MISTAKE 15: Using remove(i) in loop incorrectly
        System.out.println("❌ MISTAKE 15: Loop index corruption when using remove(i)");
        System.out.println("────────────────────────────────────────────────────────\n");
        System.out.println("BUG:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(\"A\"); list.add(\"B\"); list.add(\"C\"); list.add(\"D\");");
        System.out.println();
        System.out.println("for (int i = 0; i < list.size(); i++) {");
        System.out.println("    if (list.get(i).equals(\"B\")) {");
        System.out.println("        list.remove(i);  // ← Skips next element!");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("// Removes B, but now C is at index i, loop skips it!");
        System.out.println();
        System.out.println("✓ CORRECT:");
        System.out.println("Iterator<String> iter = list.iterator();");
        System.out.println("while (iter.hasNext()) {");
        System.out.println("    if (iter.next().equals(\"B\")) {");
        System.out.println("        iter.remove();  // Safe, iterator handles index");
        System.out.println("    }");
        System.out.println("}");
        System.out.println();
        System.out.println("LESSON: Use iterator for safe removal during iteration.\n");

        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SUMMARY: MISTAKES CHECKLIST                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("✓ ALWAYS CHECK:");
        System.out.println("  1. Am I really need LinkedList, or should I use ArrayList?");
        System.out.println("  2. Am I looping with index? (Replace with iterator!)");
        System.out.println("  3. Am I modifying while iterating? (Use iterator.remove()!)");
        System.out.println("  4. Is the list empty before accessing? (Use poll() not remove()!)");
        System.out.println("  5. Am I using named methods? (addFirst, removeFirst, not add(0, E)!)");
        System.out.println("  6. Am I using Deque/Queue interface for clarity?");
        System.out.println("  7. Have I profiled before choosing LinkedList?");
        System.out.println("  8. Am I wasting memory on small collections?");
        System.out.println("  9. Am I building LRU Cache? (Use LinkedHashMap!)");
        System.out.println(" 10. Am I concerned about GC pressure? (ArrayList is better!)");
    }
}

