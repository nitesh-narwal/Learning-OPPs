package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.*;

public class Step7_TipsAndTricks {
    
    static void main(String[] args) {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║       STEP 7: TIPS AND TRICKS - PROFESSIONAL PRACTICES       ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * 15+ Professional tips to use LinkedList effectively
         * and avoid common pitfalls
         */

        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║           15+ LINKEDLIST TIPS & TRICKS FOR PROFESSIONALS      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // TIP 1: Choose LinkedList interface abstraction
        System.out.println("TIP 1: Use Deque interface instead of LinkedList directly");
        System.out.println("────────────────────────────────────────────────────────\n");
        System.out.println("✓ GOOD:");
        System.out.println("Deque<String> deque = new LinkedList<>();");
        System.out.println("deque.addFirst(\"A\");");
        System.out.println("deque.removeFirst();");
        System.out.println();
        System.out.println("✗ AVOID:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(0, \"A\");  // This is O(n), not obvious!");
        System.out.println();
        System.out.println("Why? Deque makes intent clear (both-ends operations).\n");

        // TIP 2: Use Queue for FIFO operations
        System.out.println("TIP 2: Use Queue interface for FIFO patterns");
        System.out.println("────────────────────────────────────────────\n");
        System.out.println("✓ GOOD:");
        System.out.println("Queue<Task> queue = new LinkedList<>();");
        System.out.println("queue.offer(task);      // Add to end");
        System.out.println("Task t = queue.poll();  // Remove from front");
        System.out.println();
        System.out.println("✗ AVOID:");
        System.out.println("LinkedList<Task> list = new LinkedList<>();");
        System.out.println("list.add(task);         // Less explicit");
        System.out.println("Task t = list.remove(); // remove() means FIRST element");
        System.out.println();
        System.out.println("Why? Queue methods (offer/poll) are more explicit about FIFO.\n");

        // TIP 3: Use Stack for LIFO operations
        System.out.println("TIP 3: Use Stack class for LIFO patterns (Deque is modern alternative)");
        System.out.println("──────────────────────────────────────────────────────────────\n");
        System.out.println("✓ GOOD (Modern):");
        System.out.println("Deque<String> stack = new LinkedList<>();");
        System.out.println("stack.push(\"A\");    // Add to front");
        System.out.println("String s = stack.pop();  // Remove from front");
        System.out.println();
        System.out.println("✓ ALSO GOOD (Legacy):");
        System.out.println("Stack<String> stack = new Stack<>();");
        System.out.println("stack.push(\"A\");");
        System.out.println("String s = stack.pop();");
        System.out.println();
        System.out.println("Why? Stack class is well-known for LIFO pattern.\n");

        // TIP 4: Iterator is your friend
        System.out.println("TIP 4: Use Iterator for traversal, not index loop");
        System.out.println("───────────────────────────────────────────────\n");
        System.out.println("✓ GOOD - O(n):");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("for (String s : list) {  // Iterator internally");
        System.out.println("    System.out.println(s);");
        System.out.println("}");
        System.out.println();
        System.out.println("✗ TERRIBLE - O(n²):");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("for (int i = 0; i < list.size(); i++) {");
        System.out.println("    String s = list.get(i);  // get(i) is O(n)!");
        System.out.println("}");
        System.out.println();
        System.out.println("Difference: Iterator = 1000 operations, Index loop = 1,000,000 operations!\n");

        // TIP 5: ListIterator for bidirectional traversal
        System.out.println("TIP 5: Use ListIterator for backward traversal");
        System.out.println("───────────────────────────────────────────────\n");
        System.out.println("✓ GOOD - O(n) both directions:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("ListIterator<String> iter = list.listIterator(list.size());");
        System.out.println("while (iter.hasPrevious()) {");
        System.out.println("    String s = iter.previous();  // O(1) per step");
        System.out.println("}");
        System.out.println();
        System.out.println("✗ HARD WAY - O(n²):");
        System.out.println("for (int i = list.size()-1; i >= 0; i--) {");
        System.out.println("    String s = list.get(i);  // O(n) backward access!");
        System.out.println("}");
        System.out.println();
        System.out.println("Why? get(i) from end is O(n) because traversal from head!\n");

        // TIP 6: Use addFirst/addLast instead of add(0,E) / add(size,E)
        System.out.println("TIP 6: Use addFirst/addLast for end operations, not add(index, E)");
        System.out.println("────────────────────────────────────────────────────────────────\n");
        System.out.println("✓ GOOD - O(1):");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.addFirst(\"Top\");  // O(1)");
        System.out.println("list.addLast(\"End\");   // O(1)");
        System.out.println();
        System.out.println("✗ WORSE - O(n):");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(0, \"Top\");        // O(1) but traverses");
        System.out.println("list.add(list.size(), \"End\");  // O(n)");
        System.out.println();
        System.out.println("Why? addFirst/addLast are optimized (constant time).\n");

        // TIP 7: Use removeFirst/removeLast instead of remove(0) / remove(size-1)
        System.out.println("TIP 7: Use removeFirst/removeLast, not remove(index)");
        System.out.println("─────────────────────────────────────────────────────\n");
        System.out.println("✓ GOOD - O(1):");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.removeFirst();  // O(1)");
        System.out.println("list.removeLast();   // O(1) because doubly-linked");
        System.out.println();
        System.out.println("✗ WORSE - O(n):");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.remove(0);           // O(1) - removes first");
        System.out.println("list.remove(list.size()-1);  // O(n) - must traverse");
        System.out.println();
        System.out.println("Why? Named methods are clearer and optimized.\n");

        // TIP 8: ArrayDeque is better than LinkedList for Deque operations
        System.out.println("TIP 8: Use ArrayDeque instead of LinkedList for Deque");
        System.out.println("───────────────────────────────────────────────────────\n");
        System.out.println("✓ BEST:");
        System.out.println("Deque<String> deque = new ArrayDeque<>();  // Better!");
        System.out.println("deque.addFirst(\"A\")  // O(1) amortized");
        System.out.println("deque.removeFirst();  // O(1) amortized");
        System.out.println();
        System.out.println("✓ ALSO GOOD:");
        System.out.println("Deque<String> deque = new LinkedList<>();");
        System.out.println("deque.addFirst(\"A\")  // O(1)");
        System.out.println("deque.removeFirst();  // O(1)");
        System.out.println();
        System.out.println("Why? ArrayDeque is faster (no pointer overhead, better cache).\n");

        // TIP 9: Use LinkedHashMap for LRU Cache, not LinkedList
        System.out.println("TIP 9: Use LinkedHashMap for LRU Cache, not LinkedList+HashMap");
        System.out.println("────────────────────────────────────────────────────────────\n");
        System.out.println("✓ BEST:");
        System.out.println("LinkedHashMap<String, String> lru = new LinkedHashMap<>(16, 0.75f, true);");
        System.out.println("// Built for LRU, optimized implementation");
        System.out.println();
        System.out.println("~ OKAY:");
        System.out.println("LinkedList<String> order = new LinkedList<>();");
        System.out.println("HashMap<String, String> cache = new HashMap<>();");
        System.out.println("// Manual LRU implementation, inefficient");
        System.out.println();
        System.out.println("Why? LinkedHashMap is purpose-built and tested.\n");

        // TIP 10: Consider memory usage
        System.out.println("TIP 10: Be aware of LinkedList memory overhead");
        System.out.println("──────────────────────────────────────────────\n");
        System.out.println("LinkedList memory per element: ~48 bytes (on 64-bit JVM)");
        System.out.println("  • prev pointer: 8 bytes");
        System.out.println("  • data: 16-32 bytes (depends on element type)");
        System.out.println("  • next pointer: 8 bytes");
        System.out.println("  • object overhead: 16 bytes");
        System.out.println();
        System.out.println("ArrayList memory per element: ~16 bytes + array overhead");
        System.out.println();
        System.out.println("Example: 100,000 elements");
        System.out.println("  • LinkedList: ~4.8 MB");
        System.out.println("  • ArrayList: ~1.6 MB (3x less!)");
        System.out.println();
        System.out.println("Tip: Use ArrayList unless you really need LinkedList!\n");

        // TIP 11: Know the optimization: traverse from nearer end
        System.out.println("TIP 11: LinkedList traverses from nearer end for get/add/remove");
        System.out.println("───────────────────────────────────────────────────────────\n");
        System.out.println("Internal logic in LinkedList:");
        System.out.println("  if (index < size >> 1)");
        System.out.println("      traverse from head (forward)");
        System.out.println("  else");
        System.out.println("      traverse from tail (backward)");
        System.out.println();
        System.out.println("Example: List of 1000 elements");
        System.out.println("  • get(10): traverse 10 steps from head");
        System.out.println("  • get(990): traverse only 10 steps from tail!");
        System.out.println("  • get(500): traverse 500 steps from either end (worst case)");
        System.out.println();
        System.out.println("This cuts worst-case complexity in half!\n");

        // TIP 12: Avoid contains() - it's O(n)
        System.out.println("TIP 12: Avoid contains() for frequent lookups");
        System.out.println("───────────────────────────────────────────────\n");
        System.out.println("✓ IF you need many lookups:");
        System.out.println("Set<String> seen = new HashSet<>();");
        System.out.println("seen.add(item);");
        System.out.println("if (seen.contains(item)) { }  // O(1)");
        System.out.println();
        System.out.println("✗ DON'T DO:");
        System.out.println("LinkedList<String> list = new LinkedList<>();");
        System.out.println("list.add(item);");
        System.out.println("if (list.contains(item)) { }  // O(n)!");
        System.out.println();
        System.out.println("Why? contains() must traverse entire list!\n");

        // TIP 13: Use null checks for safety
        System.out.println("TIP 13: Always check for empty/null when using poll()/pop()");
        System.out.println("─────────────────────────────────────────────────────────────\n");
        System.out.println("✓ SAFE:");
        System.out.println("Queue<String> queue = new LinkedList<>();");
        System.out.println("String item = queue.poll();  // Returns null if empty");
        System.out.println("if (item != null) { use(item); }");
        System.out.println();
        System.out.println("✗ CRASH:");
        System.out.println("String item = queue.remove();  // Throws exception if empty!");
        System.out.println("use(item);  // NullPointerException");
        System.out.println();
        System.out.println("Tip: poll/peek return null, remove/get throw exception!\n");

        // TIP 14: Concurrent modification careful
        System.out.println("TIP 14: Use Iterator.remove() when modifying while iterating");
        System.out.println("───────────────────────────────────────────────────────────\n");
        System.out.println("✓ CORRECT:");
        System.out.println("Iterator<String> iter = list.iterator();");
        System.out.println("while (iter.hasNext()) {");
        System.out.println("    String item = iter.next();");
        System.out.println("    if (condition) iter.remove();  // Safe!");
        System.out.println("}");
        System.out.println();
        System.out.println("✗ CRASH:");
        System.out.println("for (String item : list) {");
        System.out.println("    if (condition) list.remove(item);  // ConcurrentModificationException!");
        System.out.println("}");
        System.out.println();
        System.out.println("Why? Uses iterator internally, direct removal breaks iteration!\n");

        // TIP 15: Use List.removeAll() for bulk operations
        System.out.println("TIP 15: Use removeAll() for bulk removal");
        System.out.println("───────────────────────────────────────────\n");
        System.out.println("✓ BETTER:");
        System.out.println("LinkedList<Integer> list = new LinkedList<>();");
        System.out.println("LinkedList<Integer> toRemove = new LinkedList<>();");
        System.out.println("list.removeAll(toRemove);  // Optimized");
        System.out.println();
        System.out.println("✗ SLOWER:");
        System.out.println("for (Integer num : toRemove) {");
        System.out.println("    list.remove(num);  // O(n) each time, total O(n²)");
        System.out.println("}");
        System.out.println();
        System.out.println("Why? removeAll() has special optimizations.\n");

        // TIP 16: Profile before choosing
        System.out.println("TIP 16: Profile your application before choosing data structure");
        System.out.println("──────────────────────────────────────────────────────────────\n");
        System.out.println("LinkedList is fast for:");
        System.out.println("  • Frequent add/remove at beginning or end");
        System.out.println("  • Queue/Stack/Deque patterns");
        System.out.println("  • Bidirectional iteration");
        System.out.println();
        System.out.println("ArrayList is fast for:");
        System.out.println("  • Random access (get by index)");
        System.out.println("  • Forward iteration only");
        System.out.println("  • Sorting");
        System.out.println();
        System.out.println("Default: Use ArrayList unless you have specific reason for LinkedList.\n");

        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     BONUS: COMMON LINKEDLIST PATTERNS                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("PATTERN 1: Recently Used Items (Push to front, limit size)");
        System.out.println("  Deque<String> recent = new LinkedList<>();");
        System.out.println("  recent.addFirst(newItem);");
        System.out.println("  if (recent.size() > MAX) recent.removeLast();\n");

        System.out.println("PATTERN 2: Circular Buffer (Use Deque in with limit)");
        System.out.println("  Deque<Event> buffer = new LinkedList<>();");
        System.out.println("  buffer.addLast(event);");
        System.out.println("  if (buffer.size() > CAPACITY) buffer.removeFirst();\n");

        System.out.println("PATTERN 3: Pipeline Processing (Queue through multiple stages)");
        System.out.println("  Queue<Input> inputs = new LinkedList<>();");
        System.out.println("  while (!inputs.isEmpty()) {");
        System.out.println("      process(inputs.poll());");
        System.out.println("  }\n");
    }
}

