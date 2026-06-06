package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses.indepth;
import java.util.*;

/**
 * ==========================================
 * COMMON ITERATOR MISTAKES 
 * ==========================================
 * 
 * HINGLISH EXPLANATION:
 * Bhai, ye file sabse important hai! 🎯
 * Yahan pe wo SAARI mistakes hain jo 99% developers karte hain!
 * 
 * Agar ye file achhe se padh liye, to tumhe production mein
 * bohot saare headaches se bach jaoge! 💊
 * 
 * Har mistake ke saath:
 * - Why it's wrong? (Kyu galat hai?)
 * - What happens? (Kya hoga?)
 * - How to fix? (Kaise theek karein?)
 * - Real-world example
 * 
 * @author Nitesh Kumar
 * @level Intermediate
 */
public class CommonMistakes {
    
    public static void main(String[] args) {
        System.out.println("=== COMMON ITERATOR MISTAKES ===\n");
        System.out.println("⚠️  Learning from mistakes = Fastest way to mastery!\n");
        
        // Mistake 1: Calling next() without hasNext()
        mistake1_NextWithoutHasNext();
        
        // Mistake 2: Using collection.remove() instead of iterator.remove()
        mistake2_CollectionRemove();
        
        // Mistake 3: Calling remove() twice
        mistake3_DoubleRemove();
        
        // Mistake 4: Calling remove() before next()
        mistake4_RemoveBeforeNext();
        
        // Mistake 5: Reusing exhausted iterator
        mistake5_ReusingIterator();
        
        // Mistake 6: Modifying with index during enhanced for
        mistake6_IndexInEnhancedFor();
        
        // Mistake 7: Expecting fail-safe behavior from fail-fast
        mistake7_WrongCollectionType();
        
        // Mistake 8: Not handling NoSuchElementException
        mistake8_NoExceptionHandling();
        
        // Mistake 9: Confusing Iterator with ListIterator
        mistake9_WrongIteratorType();
        
        // Mistake 10: Breaking without fully iterating
        mistake10_PartialIteration();
        
        // Summary and best practices
        summaryAndBestPractices();
    }
    
    /**
     * MISTAKE 1: Calling next() without hasNext()
     * ============================================
     * Sabse common mistake! 😅
     */
    private static void mistake1_NextWithoutHasNext() {
        System.out.println("MISTAKE #1: Calling next() without hasNext()");
        System.out.println("=".repeat(60));
        
        List<String> items = Arrays.asList("A", "B", "C");
        Iterator<String> it = items.iterator();
        
        System.out.println("❌ WRONG WAY:");
        System.out.println("```java");
        System.out.println("while(true) {");
        System.out.println("    String item = it.next(); // No hasNext check!");
        System.out.println("    if(item.equals(\"C\")) break;");
        System.out.println("}");
        System.out.println("```");
        
        try {
            it.next();
            it.next();
            it.next();
            it.next(); // BOOM! 💥
        } catch(NoSuchElementException e) {
            System.out.println("💥 Result: NoSuchElementException\n");
        }
        
        System.out.println("✅ CORRECT WAY:");
        System.out.println("```java");
        System.out.println("while(it.hasNext()) {");
        System.out.println("    String item = it.next(); // Safe!");
        System.out.println("}");
        System.out.println("```");
        
        it = items.iterator(); // Fresh iterator
        while(it.hasNext()) {
            System.out.println("  Reading: " + it.next());
        }
        System.out.println("✅ Result: All elements read safely!\n");
        
        System.out.println("💡 WHY THIS HAPPENS:");
        System.out.println("  Beginners assume 'next()' will return null when no more elements.");
        System.out.println("  But Java throws exception instead!\n");
        
        System.out.println("🎯 REAL-WORLD SCENARIO:");
        System.out.println("  Processing user uploads in a loop without proper checks.");
        System.out.println("  Production crash at midnight! 🌙💥\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 2: Using collection.remove() instead of iterator.remove()
     * ==================================================================
     * This is THE most frequent mistake! 🔥
     */
    private static void mistake2_CollectionRemove() {
        System.out.println("MISTAKE #2: Using collection.remove() during iteration");
        System.out.println("=".repeat(60));
        
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        
        System.out.println("❌ WRONG WAY:");
        System.out.println("```java");
        System.out.println("for(Integer num : numbers) {");
        System.out.println("    if(num % 2 == 0) {");
        System.out.println("        numbers.remove(num); // ConcurrentModificationException!");
        System.out.println("    }");
        System.out.println("}");
        System.out.println("```");
        
        try {
            for(Integer num : numbers) {
                if(num % 2 == 0) {
                    numbers.remove(num); // BOOM! 💥
                }
            }
        } catch(ConcurrentModificationException e) {
            System.out.println("💥 Result: ConcurrentModificationException\n");
        }
        
        System.out.println("✅ CORRECT WAY #1: iterator.remove()");
        numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("Original: " + numbers);
        
        Iterator<Integer> it = numbers.iterator();
        while(it.hasNext()) {
            if(it.next() % 2 == 0) {
                it.remove(); // Safe!
            }
        }
        System.out.println("After: " + numbers + "\n");
        
        System.out.println("✅ CORRECT WAY #2: removeIf() - Java 8+");
        numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("Original: " + numbers);
        numbers.removeIf(num -> num % 2 == 0); // One-liner!
        System.out.println("After: " + numbers + "\n");
        
        System.out.println("💡 WHY THIS HAPPENS:");
        System.out.println("  Enhanced for-loop internally uses Iterator.");
        System.out.println("  When you call collection.remove(), modCount changes,");
        System.out.println("  but iterator doesn't know! So exception!\n");
        
        System.out.println("🎯 REAL-WORLD SCENARIO:");
        System.out.println("  Removing inactive users from cache during iteration.");
        System.out.println("  Application crashes during peak hours! 📉\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 3: Calling remove() twice without next()
     * =================================================
     */
    private static void mistake3_DoubleRemove() {
        System.out.println("MISTAKE #3: Calling remove() twice without next()");
        System.out.println("=".repeat(60));
        
        List<String> fruits = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        Iterator<String> it = fruits.iterator();
        
        System.out.println("❌ WRONG WAY:");
        System.out.println("```java");
        System.out.println("it.next();");
        System.out.println("it.remove(); // OK");
        System.out.println("it.remove(); // IllegalStateException!");
        System.out.println("```");
        
        try {
            it.next();
            it.remove(); // First remove - OK
            System.out.println("First remove: Success");
            it.remove(); // Second remove - ERROR!
        } catch(IllegalStateException e) {
            System.out.println("💥 Second remove: IllegalStateException\n");
        }
        
        System.out.println("✅ CORRECT WAY:");
        System.out.println("```java");
        System.out.println("it.next();");
        System.out.println("it.remove(); // OK");
        System.out.println("it.next();   // Must call next() again");
        System.out.println("it.remove(); // Now OK");
        System.out.println("```");
        
        fruits = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        it = fruits.iterator();
        
        it.next();
        it.remove();
        System.out.println("After 1st remove: " + fruits);
        
        it.next(); // Read next element!
        it.remove();
        System.out.println("After 2nd remove: " + fruits + "\n");
        
        System.out.println("💡 WHY THIS HAPPENS:");
        System.out.println("  Iterator tracks which element was last returned.");
        System.out.println("  Without next(), there's no 'current' element to remove!\n");
        
        System.out.println("🔑 REMEMBER: One next() = One remove() allowed\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 4: Calling remove() before next()
     * ==========================================
     */
    private static void mistake4_RemoveBeforeNext() {
        System.out.println("MISTAKE #4: Calling remove() before next()");
        System.out.println("=".repeat(60));
        
        List<Integer> nums = new ArrayList<>(Arrays.asList(10, 20, 30));
        Iterator<Integer> it = nums.iterator();
        
        System.out.println("❌ WRONG WAY:");
        System.out.println("```java");
        System.out.println("Iterator<Integer> it = list.iterator();");
        System.out.println("it.remove(); // No next() called yet!");
        System.out.println("```");
        
        try {
            it.remove(); // BOOM! 💥
        } catch(IllegalStateException e) {
            System.out.println("💥 Result: IllegalStateException");
            System.out.println("   Message: Must call next() before remove()\n");
        }
        
        System.out.println("✅ CORRECT WAY:");
        it = nums.iterator();
        System.out.println("Original: " + nums);
        
        it.next(); // Read first element
        it.remove(); // Now remove it
        
        System.out.println("After remove: " + nums + "\n");
        
        System.out.println("💡 RULE: Always next() → then remove()\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 5: Reusing exhausted iterator
     * ======================================
     */
    private static void mistake5_ReusingIterator() {
        System.out.println("MISTAKE #5: Reusing exhausted iterator");
        System.out.println("=".repeat(60));
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        Iterator<String> it = names.iterator();
        
        System.out.println("First iteration:");
        while(it.hasNext()) {
            System.out.println("  " + it.next());
        }
        
        System.out.println("\n❌ WRONG: Trying to reuse same iterator");
        System.out.println("Second iteration attempt:");
        int count = 0;
        while(it.hasNext()) {
            System.out.println("  " + it.next());
            count++;
        }
        System.out.println("  Elements printed: " + count + " (Expected 3!)");
        System.out.println("💥 Iterator is exhausted! Nothing prints!\n");
        
        System.out.println("✅ CORRECT WAY: Get fresh iterator");
        it = names.iterator(); // Get NEW iterator!
        count = 0;
        while(it.hasNext()) {
            System.out.println("  " + it.next());
            count++;
        }
        System.out.println("  Elements printed: " + count + " ✅\n");
        
        System.out.println("💡 KEY POINT:");
        System.out.println("  Iterator is ONE-TIME USE (like a ticket!)");
        System.out.println("  Need to iterate again? Get a new iterator!\n");
        
        System.out.println("🎯 REAL-WORLD SCENARIO:");
        System.out.println("  Processing same list in multiple methods.");
        System.out.println("  Second method gets nothing if same iterator passed! 🤦\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 6: Trying to access index in enhanced for loop
     * =======================================================
     */
    private static void mistake6_IndexInEnhancedFor() {
        System.out.println("MISTAKE #6: Expecting index in enhanced for loop");
        System.out.println("=".repeat(60));
        
        List<String> items = Arrays.asList("Java", "Python", "C++");
        
        System.out.println("❌ WRONG EXPECTATION:");
        System.out.println("```java");
        System.out.println("for(String item : items) {");
        System.out.println("    // How to get index here? 🤔");
        System.out.println("    // Can't! Enhanced for doesn't provide index!");
        System.out.println("}");
        System.out.println("```\n");
        
        System.out.println("✅ SOLUTION #1: Traditional for loop");
        for(int i = 0; i < items.size(); i++) {
            System.out.println("  [" + i + "] " + items.get(i));
        }
        
        System.out.println("\n✅ SOLUTION #2: Manual counter");
        int index = 0;
        for(String item : items) {
            System.out.println("  [" + index++ + "] " + item);
        }
        
        System.out.println("\n✅ SOLUTION #3: Iterator with tracking");
        Iterator<String> it = items.iterator();
        index = 0;
        while(it.hasNext()) {
            System.out.println("  [" + index++ + "] " + it.next());
        }
        
        System.out.println("\n✅ SOLUTION #4: ListIterator (for Lists)");
        ListIterator<String> lit = items.listIterator();
        while(lit.hasNext()) {
            int idx = lit.nextIndex();
            String item = lit.next();
            System.out.println("  [" + idx + "] " + item);
        }
        
        System.out.println("\n💡 CHOOSE WISELY:");
        System.out.println("  Need index? Use traditional for or ListIterator");
        System.out.println("  Don't need index? Enhanced for is cleanest!\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 7: Expecting fail-safe behavior from fail-fast collections
     * ===================================================================
     */
    private static void mistake7_WrongCollectionType() {
        System.out.println("MISTAKE #7: Wrong collection type for use case");
        System.out.println("=".repeat(60));
        
        System.out.println("❌ WRONG: ArrayList for concurrent access");
        System.out.println("```java");
        System.out.println("List<String> list = new ArrayList<>();");
        System.out.println("// Multiple threads modifying...");
        System.out.println("// ConcurrentModificationException! 💥");
        System.out.println("```\n");
        
        System.out.println("✅ CORRECT: CopyOnWriteArrayList for concurrent access");
        System.out.println("```java");
        System.out.println("List<String> list = new CopyOnWriteArrayList<>();");
        System.out.println("// Multiple threads can safely modify!");
        System.out.println("```\n");
        
        System.out.println("COLLECTION SELECTION GUIDE:");
        System.out.println("-".repeat(40));
        System.out.println("Single-threaded + Need speed:");
        System.out.println("  → ArrayList ✅");
        
        System.out.println("\nMulti-threaded + Read-heavy:");
        System.out.println("  → CopyOnWriteArrayList ✅");
        
        System.out.println("\nMulti-threaded + Write-heavy:");
        System.out.println("  → Collections.synchronizedList() ✅");
        
        System.out.println("\nFrequent modifications during iteration:");
        System.out.println("  → Use streams or collect-then-modify pattern ✅\n");
        
        System.out.println("💡 REMEMBER:");
        System.out.println("  Right tool for right job = Fewer bugs! 🔧\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 8: Not handling exceptions properly
     * ============================================
     */
    private static void mistake8_NoExceptionHandling() {
        System.out.println("MISTAKE #8: No exception handling in production code");
        System.out.println("=".repeat(60));
        
        List<String> data = Arrays.asList("A", "B", "C");
        
        System.out.println("❌ WRONG: No exception handling");
        System.out.println("```java");
        System.out.println("Iterator<String> it = data.iterator();");
        System.out.println("while(condition) {");
        System.out.println("    String item = it.next(); // What if no more elements?");
        System.out.println("}");
        System.out.println("```\n");
        
        System.out.println("✅ CORRECT: Proper exception handling");
        System.out.println("```java");
        System.out.println("try {");
        System.out.println("    Iterator<String> it = data.iterator();");
        System.out.println("    while(it.hasNext()) { // Proper check!");
        System.out.println("        processItem(it.next());");
        System.out.println("    }");
        System.out.println("} catch(NoSuchElementException e) {");
        System.out.println("    logger.error(\"Iterator error\", e);");
        System.out.println("} catch(ConcurrentModificationException e) {");
        System.out.println("    logger.error(\"Concurrent modification\", e);");
        System.out.println("}");
        System.out.println("```\n");
        
        System.out.println("PRODUCTION-READY PATTERN:");
        System.out.println("-".repeat(40));
        
        Iterator<String> it = data.iterator();
        try {
            while(it.hasNext()) {
                String item = it.next();
                System.out.println("  Processing: " + item);
                // Your business logic here
            }
        } catch(NoSuchElementException e) {
            System.err.println("  ❌ Unexpected end of iteration");
            // Log and handle
        } catch(ConcurrentModificationException e) {
            System.err.println("  ❌ Collection modified during iteration");
            // Log and retry or fail gracefully
        } catch(Exception e) {
            System.err.println("  ❌ Unexpected error: " + e.getMessage());
            // Generic fallback
        }
        
        System.out.println("\n💡 PRODUCTION TIP:");
        System.out.println("  Always expect the unexpected! 🛡️");
        System.out.println("  Proper error handling = Happy users!\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 9: Using Iterator when ListIterator is needed
     * ======================================================
     */
    private static void mistake9_WrongIteratorType() {
        System.out.println("MISTAKE #9: Using Iterator when ListIterator is needed");
        System.out.println("=".repeat(60));
        
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        
        System.out.println("❌ WRONG: Trying to modify with regular Iterator");
        System.out.println("```java");
        System.out.println("Iterator<Integer> it = list.iterator();");
        System.out.println("// Can't call it.set() or it.add() - not available!");
        System.out.println("// Can only remove!");
        System.out.println("```\n");
        
        System.out.println("✅ CORRECT: Use ListIterator for modification");
        ListIterator<Integer> lit = numbers.listIterator();
        
        System.out.println("Original: " + numbers);
        
        while(lit.hasNext()) {
            int num = lit.next();
            if(num % 2 == 0) {
                lit.set(num * 10); // Update!
            }
        }
        
        System.out.println("After update: " + numbers + "\n");
        
        System.out.println("ITERATOR vs LISTITERATOR:");
        System.out.println("-".repeat(40));
        System.out.println("Need to remove elements?");
        System.out.println("  → Iterator ✅ or ListIterator ✅");
        
        System.out.println("\nNeed to update elements?");
        System.out.println("  → ListIterator ✅ (has set method)");
        
        System.out.println("\nNeed to add elements during iteration?");
        System.out.println("  → ListIterator ✅ (has add method)");
        
        System.out.println("\nNeed bidirectional traversal?");
        System.out.println("  → ListIterator ✅ (has previous)");
        
        System.out.println("\nWorking with Set?");
        System.out.println("  → Iterator ✅ (ListIterator only for Lists)\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * MISTAKE 10: Breaking without cleanup
     * =====================================
     */
    private static void mistake10_PartialIteration() {
        System.out.println("MISTAKE #10: Not considering partial iteration implications");
        System.out.println("=".repeat(60));
        
        System.out.println("SCENARIO: Finding first match and breaking");
        List<String> files = Arrays.asList("doc.txt", "image.png", "video.mp4", "data.csv");
        
        System.out.println("Files: " + files);
        System.out.println("\nSearching for .png file...\n");
        
        Iterator<String> it = files.iterator();
        String found = null;
        
        while(it.hasNext()) {
            String file = it.next();
            System.out.println("  Checking: " + file);
            
            if(file.endsWith(".png")) {
                found = file;
                System.out.println("  ✅ Found: " + found);
                break; // Early exit
            }
        }
        
        System.out.println("\n💡 THINGS TO CONSIDER:");
        System.out.println("  1. Iterator still holds reference to collection");
        System.out.println("  2. Remaining elements weren't processed");
        System.out.println("  3. Is this intentional or a bug?");
        
        System.out.println("\n✅ BETTER APPROACH: Document intent clearly");
        System.out.println("```java");
        System.out.println("// Find first PNG file and stop");
        System.out.println("String findFirstPng(List<String> files) {");
        System.out.println("    for(String file : files) {");
        System.out.println("        if(file.endsWith(\".png\")) {");
        System.out.println("            return file; // Clear intent!");
        System.out.println("        }");
        System.out.println("    }");
        System.out.println("    return null;");
        System.out.println("}");
        System.out.println("```\n");
        
        System.out.println("OR use Stream with findFirst():");
        System.out.println("```java");
        System.out.println("Optional<String> png = files.stream()");
        System.out.println("    .filter(f -> f.endsWith(\".png\"))");
        System.out.println("    .findFirst();");
        System.out.println("```\n");
        
        System.out.println("-".repeat(60) + "\n");
    }
    
    /**
     * SUMMARY AND BEST PRACTICES
     * ===========================
     */
    private static void summaryAndBestPractices() {
        System.out.println("=" .repeat(60));
        System.out.println("SUMMARY: TOP 10 ITERATOR MISTAKES");
        System.out.println("=".repeat(60));
        
        System.out.println("\n1. ❌ next() without hasNext()");
        System.out.println("   ✅ Always check hasNext() before next()");
        
        System.out.println("\n2. ❌ collection.remove() during iteration");
        System.out.println("   ✅ Use iterator.remove() or removeIf()");
        
        System.out.println("\n3. ❌ Double remove() without next()");
        System.out.println("   ✅ One next() = One remove() max");
        
        System.out.println("\n4. ❌ remove() before next()");
        System.out.println("   ✅ Always next() first, then remove()");
        
        System.out.println("\n5. ❌ Reusing exhausted iterator");
        System.out.println("   ✅ Get fresh iterator for each iteration");
        
        System.out.println("\n6. ❌ Expecting index in enhanced for");
        System.out.println("   ✅ Use traditional for or ListIterator");
        
        System.out.println("\n7. ❌ Wrong collection type for use case");
        System.out.println("   ✅ Choose fail-safe for multi-threading");
        
        System.out.println("\n8. ❌ No exception handling");
        System.out.println("   ✅ Handle NoSuchElement and ConcurrentModification");
        
        System.out.println("\n9. ❌ Using Iterator when ListIterator needed");
        System.out.println("   ✅ ListIterator for bidirectional/modification");
        
        System.out.println("\n10. ❌ Unclear partial iteration intent");
        System.out.println("    ✅ Document early breaks clearly");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("BEST PRACTICES CHECKLIST");
        System.out.println("=".repeat(60));
        
        System.out.println("\n✅ Always use hasNext() before next()");
        System.out.println("✅ Use iterator.remove() for safe removal");
        System.out.println("✅ Choose right iterator type (Iterator vs ListIterator)");
        System.out.println("✅ Choose right collection type (fail-fast vs fail-safe)");
        System.out.println("✅ Handle exceptions in production code");
        System.out.println("✅ Get fresh iterator for each full iteration");
        System.out.println("✅ Use modern alternatives (removeIf, Streams) when possible");
        System.out.println("✅ Document complex iteration logic");
        System.out.println("✅ Consider performance (ArrayList vs LinkedList)");
        System.out.println("✅ Test concurrent scenarios if multi-threaded");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎓 MASTERY ACHIEVED!");
        System.out.println("=".repeat(60));
        System.out.println("\nYou now know:");
        System.out.println("  ✨ All common mistakes");
        System.out.println("  ✨ Why they happen");
        System.out.println("  ✨ How to fix them");
        System.out.println("  ✨ How to avoid them in future");
        
        System.out.println("\n💪 You're now in top 10% of Java developers");
        System.out.println("   who understand iterators properly!");
        
        System.out.println("\n🚀 Next step: Practice in real projects!");
        System.out.println("   And move to next file: 07_CustomIterator.java\n");
    }
}

/*
 * ==========================================
 * FINAL WISDOM
 * ==========================================
 * 
 * "Good judgment comes from experience.
 *  Experience comes from bad judgment."
 *  
 * Ye mistakes samajh liye to tumhe wo bad judgment
 * nahi karni padegi jo humne ki thi! 😄
 * 
 * KEY LEARNINGS:
 * ==============
 * 1. Iterator mistakes are common but preventable
 * 2. Most bugs come from misunderstanding fail-fast behavior
 * 3. Modern Java provides better alternatives (removeIf, Streams)
 * 4. Production code needs proper exception handling
 * 5. Choose right tool for right job
 * 
 * DEBUGGING TIPS:
 * ===============
 * If you get:
 * - NoSuchElementException → Check hasNext() usage
 * - ConcurrentModificationException → Check modification during iteration
 * - IllegalStateException → Check remove() usage pattern
 * 
 * NEXT FILE: 07_CustomIterator.java
 * (Apna khud ka Iterator banana seekho!)
 */
