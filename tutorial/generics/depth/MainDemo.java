package me.niteshh.OPPs.tutorial.generics.depth;

import java.util.*;
import java.util.function.*;

/*
 * ============================================================================
 * MAIN DEMO - PRACTICAL EXAMPLES OF ALL GENERIC CONCEPTS
 * ============================================================================
 * 
 * Ye main class mein sab concepts ko practically demonstrate kiya gaya hai.
 * Comments mein output bhi likha hai taaki samajh aaye ki kya expected hona chahiye.
 * 
 * Yeh file padho last, jab sab steps clear ho gaye ho.
 * ============================================================================
 */

public class MainDemo {
    
    // ========================================================================
    // DEMO 1: BASIC GENERICS
    // ========================================================================
    
    public static void demo1_BasicGenerics() {
        // System.out.println("\n=== DEMO 1: BASIC GENERICS ===");
        
        // String container
        // Step1_BasicsOfGenerics<String> stringBox = 
        //     new Step1_BasicsOfGenerics<>("Hello World");
        // String result = stringBox.getValue();  // No casting needed!
        // System.out.println("String: " + result);
        // Output: String: Hello World
        
        // Integer container
        // Step1_BasicsOfGenerics<Integer> intBox = 
        //     new Step1_BasicsOfGenerics<>(42);
        // Integer number = intBox.getValue();  // No casting needed!
        // System.out.println("Integer: " + number);
        // Output: Integer: 42
        
        // Double container
        // Step1_BasicsOfGenerics<Double> doubleBox = 
        //     new Step1_BasicsOfGenerics<>(3.14159);
        // Double pi = doubleBox.getValue();
        // System.out.println("Double: " + pi);
        // Output: Double: 3.14159
    }
    
    // ========================================================================
    // DEMO 2: MULTIPLE TYPE PARAMETERS
    // ========================================================================
    
    public static void demo2_MultipleTypeParameters() {
        // System.out.println("\n=== DEMO 2: MULTIPLE TYPE PARAMETERS ===");
        
        // Using generic pair
        // Step2_GenericContainer.Step2_GenericPair<String, Integer> pair1 =
        //     new Step2_GenericContainer.Step2_GenericPair<>("Name", 25);
        // System.out.println("First: " + pair1.getFirst());  // Name
        // System.out.println("Second: " + pair1.getSecond()); // 25
        // Output: 
        // First: Name
        // Second: 25
        
        // Another pair with different types
        // Step2_GenericContainer.Step2_GenericPair<Double, Boolean> pair2 =
        //     new Step2_GenericContainer.Step2_GenericPair<>(3.14, true);
        // System.out.println("First: " + pair2.getFirst());  // 3.14
        // System.out.println("Second: " + pair2.getSecond()); // true
        // Output:
        // First: 3.14
        // Second: true
    }
    
    // ========================================================================
    // DEMO 3: BOUNDED TYPE PARAMETERS
    // ========================================================================
    
    public static void demo3_BoundedTypeParameters() {
        // System.out.println("\n=== DEMO 3: BOUNDED TYPE PARAMETERS ===");
        
        // ✅ Allowed - Number types
        // Step3_BoundedTypeParameters obj = new Step3_BoundedTypeParameters();
        // var numberContainer1 = obj.new NumberContainer<Integer>(42);
        // System.out.println("Integer: " + numberContainer1.getDoubleValue());
        // Output: Integer: 42.0
        
        // var numberContainer2 = obj.new NumberContainer<Double>(3.14);
        // System.out.println("Double: " + numberContainer2.getDoubleValue());
        // Output: Double: 3.14
        
        // ❌ Not allowed - String nahi Number extend karta
        // var numberContainer3 = obj.new NumberContainer<String>("Hello");
        // ^ Compile error!
    }
    
    // ========================================================================
    // DEMO 4: WILDCARDS
    // ========================================================================
    
    public static void demo4_Wildcards() {
        // System.out.println("\n=== DEMO 4: WILDCARDS ===");
        
        // UNBOUNDED WILDCARD (?)
        // Step4_Wildcards obj = new Step4_Wildcards();
        // 
        // List<String> strings = new ArrayList<>();
        // strings.add("Hello");
        // strings.add("World");
        // obj.new UnboundedWildcardExample().printListSize(strings);
        // Output: List size: 2
        
        // List<Integer> integers = new ArrayList<>();
        // integers.add(10);
        // integers.add(20);
        // obj.new UnboundedWildcardExample().printListSize(integers);
        // Output: List size: 2
        
        // UPPER BOUNDED WILDCARD (? extends Number)
        // List<Integer> intList = new ArrayList<>();
        // intList.add(10);
        // intList.add(20);
        // double sum = obj.new UpperBoundedWildcardExample().sumNumbers(intList);
        // System.out.println("Sum of integers: " + sum);
        // Output: Sum of integers: 30.0
        
        // List<Double> doubleList = new ArrayList<>();
        // doubleList.add(2.5);
        // doubleList.add(3.5);
        // double sum2 = obj.new UpperBoundedWildcardExample().sumNumbers(doubleList);
        // System.out.println("Sum of doubles: " + sum2);
        // Output: Sum of doubles: 6.0
    }
    
    // ========================================================================
    // DEMO 5: GENERIC METHODS
    // ========================================================================
    
    public static void demo5_GenericMethods() {
        // System.out.println("\n=== DEMO 5: GENERIC METHODS ===");
        
        // Printing different types using generic method
        // Step5_GenericMethods.printElement("Hello");
        // Output: Element: Hello
        
        // Step5_GenericMethods.printElement(42);
        // Output: Element: 42
        
        // Step5_GenericMethods.printElement(3.14);
        // Output: Element: 3.14
        
        // Array operations
        // String[] stringArray = {"Hello", "World", "Java"};
        // String first = Step5_GenericMethods.getFirstElement(stringArray);
        // System.out.println("First string: " + first);
        // Output: First string: Hello
        
        // Integer[] intArray = {10, 20, 30};
        // Integer firstInt = Step5_GenericMethods.getFirstElement(intArray);
        // System.out.println("First integer: " + firstInt);
        // Output: First integer: 10
        
        // Finding max with bounded generics
        // Integer max1 = Step5_GenericMethods.findMax(10, 20, 30);
        // System.out.println("Max of 10, 20, 30: " + max1);
        // Output: Max of 10, 20, 30: 30
        
        // String max2 = Step5_GenericMethods.findMax("apple", "banana", "cherry");
        // System.out.println("Max of strings: " + max2);
        // Output: Max of strings: cherry
        
        // Sum of number list
        // List<Integer> numbers = Arrays.asList(10, 20, 30, 40);
        // double sum = Step5_GenericMethods.sumList(numbers);
        // System.out.println("Sum: " + sum);
        // Output: Sum: 100.0
    }
    
    // ========================================================================
    // DEMO 6: TIPS AND TRICKS (From Step6)
    // ========================================================================
    
    public static void demo6_TipsAndTricks() {
        // System.out.println("\n=== DEMO 6: TIPS AND TRICKS ===");
        
        // TIP 1: Safe Box without casting
        // Step6_TipsAndTricks.SafeBox<String> safeBox = 
        //     new Step6_TipsAndTricks.SafeBox<>("Important Data");
        // String value = safeBox.get();  // No casting!
        // System.out.println("Safe value: " + value);
        // Output: Safe value: Important Data
        
        // TIP 3: Bounded repository
        // // Assuming Entity class exists
        // Step6_TipsAndTricks.BaseRepository<Entity> repo = 
        //     new Step6_TipsAndTricks.BaseRepository<>();
        
        // TIP 6: Diamond operator
        // List<String> list = new ArrayList<>();  // Diamond operator
        // Map<String, Integer> map = new HashMap<>();
        // Set<Double> set = new HashSet<>();
        
        // TIP 7: Generic pair utility
        // Step6_TipsAndTricks.GenericPair<String, String> namePair =
        //     new Step6_TipsAndTricks.GenericPair<>("First Name", "John");
        // System.out.println("Key: " + namePair.getKey());
        // System.out.println("Value: " + namePair.getValue());
        // Output: 
        // Key: First Name
        // Value: John
        
        // TIP 7: Generic cache
        // Step6_TipsAndTricks.GenericCache<String, String> cache =
        //     new Step6_TipsAndTricks.GenericCache<>();
        // cache.put("user1", "John");
        // cache.put("user2", "Jane");
        // System.out.println("User1: " + cache.get("user1"));
        // Output: User1: John
    }
    
    // ========================================================================
    // DEMO 7: REAL-WORLD SCENARIOS
    // ========================================================================
    
    public static void demo7_RealWorldScenarios() {
        // System.out.println("\n=== DEMO 7: REAL-WORLD SCENARIOS ===");
        
        // SCENARIO 1: API Response wrapper
        // Step6_TipsAndTricks.ApiResponse<String> response1 =
        //     new Step6_TipsAndTricks.ApiResponse<>(true, "Data loaded", "Success");
        // System.out.println("Response: " + response1.getData());
        // Output: Response: Data loaded
        
        // SCENARIO 2: Multiple data type response
        // List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5);
        // Step6_TipsAndTricks.ApiResponse<List<Integer>> response2 =
        //     new Step6_TipsAndTricks.ApiResponse<>(true, dataList, "Multiple items");
        // System.out.println("Items: " + response2.getData());
        // Output: Items: [1, 2, 3, 4, 5]
    }
    
    // ========================================================================
    // DEMO 8: COLLECTIONS WITH GENERICS
    // ========================================================================
    
    public static void demo8_CollectionsWithGenerics() {
        // System.out.println("\n=== DEMO 8: COLLECTIONS WITH GENERICS ===");
        
        // Type-safe collections
        // List<String> names = new ArrayList<>();
        // names.add("Alice");
        // names.add("Bob");
        // names.add("Charlie");
        // 
        // for (String name : names) {
        //     System.out.println("Name: " + name);
        // }
        // Output:
        // Name: Alice
        // Name: Bob
        // Name: Charlie
        
        // Map with generics
        // Map<String, Integer> ages = new HashMap<>();
        // ages.put("Alice", 25);
        // ages.put("Bob", 30);
        // ages.put("Charlie", 28);
        // 
        // for (Map.Entry<String, Integer> entry : ages.entrySet()) {
        //     System.out.println(entry.getKey() + ": " + entry.getValue());
        // }
        // Output:
        // Alice: 25
        // Bob: 30
        // Charlie: 28
    }
    
    // ========================================================================
    // DEMO 9: COMMON MISTAKES TO AVOID
    // ========================================================================
    
    public static void demo9_MistakesToAvoid() {
        // System.out.println("\n=== DEMO 9: MISTAKES TO AVOID ===");
        
        // ✅ CORRECT: Using typed collections
        // List<String> goodList = new ArrayList<>();
        // goodList.add("Hello");
        // String value = goodList.get(0);  // No casting!
        // System.out.println("Good: " + value);
        // Output: Good: Hello
        
        // ❌ MISTAKE: Using raw types (generates warnings)
        // List badList = new ArrayList();  // Raw type warning!
        // badList.add("Hello");
        // String badValue = (String) badList.get(0);  // Casting required!
        // System.out.println("Bad: " + badValue);
        // Output: Bad: Hello (but with warnings)
    }
    
    // ========================================================================
    // MAIN METHOD - RUN ALL DEMOS
    // ========================================================================
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          JAVA GENERICS - COMPREHENSIVE GUIDE               ║");
        System.out.println("║                  All Concepts Demonstrated                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // UNCOMMENT THE DEMOS TO RUN THEM
        
        // demo1_BasicGenerics();
        // Output: Shows how basic generics work with type safety
        
        // demo2_MultipleTypeParameters();
        // Output: Shows generic classes with multiple type parameters
        
        // demo3_BoundedTypeParameters();
        // Output: Shows how bounded types restrict which classes can be used
        
        // demo4_Wildcards();
        // Output: Shows unbounded and bounded wildcards in action
        
        // demo5_GenericMethods();
        // Output: Shows how generic methods work independently
        
        // demo6_TipsAndTricks();
        // Output: Shows practical tips for effective generics
        
        // demo7_RealWorldScenarios();
        // Output: Shows real-world usage patterns
        
        // demo8_CollectionsWithGenerics();
        // Output: Shows type-safe collections
        
        // demo9_MistakesToAvoid();
        // Output: Shows correct vs incorrect usage
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    STUDY GUIDE SUMMARY                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                            ║");
        System.out.println("║  1. BEGINNER: Read Step1_BasicsOfGenerics.java             ║");
        System.out.println("║     - Understand what generics are                         ║");
        System.out.println("║     - Learn type parameters <T>                            ║");
        System.out.println("║     - Compare with vs without generics                     ║");
        System.out.println("║                                                            ║");
        System.out.println("║  2. INTERMEDIATE: Read Step2_GenericContainer.java         ║");
        System.out.println("║     - Multiple type parameters                             ║");
        System.out.println("║     - Generic containers                                   ║");
        System.out.println("║                                                            ║");
        System.out.println("║  3. INTERMEDIATE: Read Step3_BoundedTypeParameters.java    ║");
        System.out.println("║     - Upper bounded wildcards                              ║");
        System.out.println("║     - Multiple bounds                                      ║");
        System.out.println("║                                                            ║");
        System.out.println("║  4. ADVANCED: Read Step4_Wildcards.java                    ║");
        System.out.println("║     - Unbounded wildcards <?>                              ║");
        System.out.println("║     - Lower bounded wildcards                              ║");
        System.out.println("║     - PECS principle                                       ║");
        System.out.println("║                                                            ║");
        System.out.println("║  5. ADVANCED: Read Step5_GenericMethods.java               ║");
        System.out.println("║     - Generic methods                                      ║");
        System.out.println("║     - Multiple type parameters in methods                  ║");
        System.out.println("║     - Type inference                                       ║");
        System.out.println("║                                                            ║");
        System.out.println("║  6. PRACTICAL: Read Step6_TipsAndTricks.java               ║");
        System.out.println("║     - 12 practical tips for effective usage                ║");
        System.out.println("║     - Best practices                                       ║");
        System.out.println("║                                                            ║");
        System.out.println("║  7. CRITICAL: Read Step7_CommonMistakes.java               ║");
        System.out.println("║     - 12 common mistakes                                   ║");
        System.out.println("║     - How to avoid them                                    ║");
        System.out.println("║                                                            ║");
        System.out.println("║  8. FINAL: Come back to MainDemo.java (this file)          ║");
        System.out.println("║     - See all concepts in action                           ║");
        System.out.println("║     - Practical examples                                   ║");
        System.out.println("║                                                            ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║                  REVISION CHECKLIST                        ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  ☐ Type parameters and placeholders                        ║");
        System.out.println("║  ☐ Type erasure concept                                    ║");
        System.out.println("║  ☐ Bounded vs unbounded types                              ║");
        System.out.println("║  ☐ Wildcards (all three types)                             ║");
        System.out.println("║  ☐ Generic methods                                         ║");
        System.out.println("║  ☐ PECS principle                                          ║");
        System.out.println("║  ☐ Collections with generics                               ║");
        System.out.println("║  ☐ Common mistakes and fixes                               ║");
        System.out.println("║  ☐ Real-world patterns                                     ║");
        System.out.println("║  ☐ Best practices                                          ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n💡 TIP: Uncomment demos one by one to see output in action!");
        System.out.println("📚 Read the extensive comments in each file for deep understanding.");
        System.out.println("🎯 Master these concepts - Generics are CRITICAL for enterprise Java!");
    }
}

/*
 * ============================================================================
 * HOW TO USE THIS GUIDE
 * ============================================================================
 * 
 * SEQUENCE TO FOLLOW:
 * 1. Start with Step1_BasicsOfGenerics.java (Read all comments carefully)
 * 2. Then Step2_GenericContainer.java (Practice with examples)
 * 3. Then Step3_BoundedTypeParameters.java (Understand bounds)
 * 4. Then Step4_Wildcards.java (Advanced concepts)
 * 5. Then Step5_GenericMethods.java (Method-level generics)
 * 6. Then Step6_TipsAndTricks.java (Practical wisdom)
 * 7. Then Step7_CommonMistakes.java (Critical mistakes!)
 * 8. Finally MainDemo.java (This file - see everything together)
 * 
 * TIME ESTIMATE:
 * - Beginner: 2-3 hours for complete understanding
 * - Intermediate: 1-2 hours (if refreshing knowledge)
 * - Advanced developer: 30-45 minutes (for reference)
 * 
 * PRACTICE:
 * Uncomment demos one by one and run them. Modify examples to test
 * your understanding. Try to break things intentionally to understand
 * what works and what doesn't.
 * 
 * ADVANCED TOPICS NOT COVERED HERE:
 * - Generic interface implementation
 * - Generic abstract classes
 * - Recursive type bounds in depth
 * - Covariance and Contravariance (advanced)
 * - Generic array creation with reflection
 * 
 * These topics are less commonly used but can be learned from official
 * Oracle documentation when needed.
 * ============================================================================
 */

