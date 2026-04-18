package me.niteshh.OPPs.tutorial.wrapperClass.inDepth;

import java.util.Scanner;

/**
 * WRAPPER CLASS LEARNING JOURNEY - MAIN ENTRY POINT
 * 
 * This is your complete guide to understanding Java Wrapper Classes
 * from beginner to advanced level.
 * 
 * STRUCTURE:
 * ==========
 * Step 1: BasicsOfWrapperClasses        - What are wrapper classes, auto-boxing, unboxing
 * Step 2: PracticalApplications         - Where and how to use wrapper classes
 * Step 3: BoxingAndUnboxing            - Deep dive into boxing/unboxing mechanism
 * Step 4: AdvancedConcepts             - Integer caching and cache boundaries
 * Step 5: UtilityMethods               - Methods available in wrapper classes
 * Step 6: CommonPitfalls               - Mistakes to avoid and how to prevent them
 * Step 7: TipsAndTricks                - Best practices and optimization techniques
 * 
 * HOW TO USE THIS GUIDE:
 * =====================
 * 1. Run this main class to see the menu
 * 2. Select a step (1-7) to learn about that topic
 * 3. Each step has multiple demonstrations with explanations
 * 4. Read comments carefully to understand concepts
 * 5. Observe the code output to see how things work
 * 6. Try modifying code examples to experiment
 * 
 * RECOMMENDED LEARNING PATH:
 * ==========================
 * Beginner:
 *   - Start with Step 1 (Basics)
 *   - Then Step 2 (Practical Applications)
 *   
 * Intermediate:
 *   - Study Step 3 (Boxing/Unboxing)
 *   - Review Step 6 (Common Pitfalls)
 *   
 * Advanced:
 *   - Explore Step 4 (Advanced Concepts)
 *   - Learn Step 5 (Utility Methods)
 *   - Master Step 7 (Tips & Tricks)
 * 
 * KEY CONCEPTS TO REMEMBER:
 * =========================
 * 1. Wrapper classes convert primitives to objects
 * 2. Auto-boxing (primitive -> wrapper) happens automatically (Java 5+)
 * 3. Unboxing (wrapper -> primitive) also automatic (Java 5+)
 * 4. ALWAYS use equals() to compare wrapper values, NOT ==
 * 5. Null checking is needed before unboxing to avoid NullPointerException
 * 6. Use primitives for performance, wrappers for collections
 * 7. Integer cache (-128 to 127) affects == comparisons
 * 8. valueOf() is better than new Constructor for memory efficiency
 */

public class WrapperClassMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMainMenu();
            System.out.print("\nEnter your choice (1-8): ");
            
            String input = scanner.nextLine().trim();
            
            switch (input) {
                case "1":
                    runStep1();
                    break;
                case "2":
                    runStep2();
                    break;
                case "3":
                    runStep3();
                    break;
                case "4":
                    runStep4();
                    break;
                case "5":
                    runStep5();
                    break;
                case "6":
                    runStep6();
                    break;
                case "7":
                    runStep7();
                    break;
                case "8":
                    running = false;
                    System.out.println("\n✓ Thank you for learning! Keep practicing!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please enter 1-8.");
            }

            if (running && !input.equals("8")) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // ============= DISPLAY METHODS =============

    /**
     * Display the main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  WRAPPER CLASS LEARNING GUIDE - MAIN MENU");
        System.out.println("=".repeat(60));
        System.out.println("\n  BEGINNER LEVEL:");
        System.out.println("    1. Step 1: Basics of Wrapper Classes");
        System.out.println("       → What are wrapper classes");
        System.out.println("       → Auto-boxing and unboxing");
        System.out.println("       → Creating wrapper objects");

        System.out.println("\n    2. Step 2: Practical Applications");
        System.out.println("       → Using wrapper classes with collections");
        System.out.println("       → Real-world use cases");
        System.out.println("       → Null handling");

        System.out.println("\n  INTERMEDIATE LEVEL:");
        System.out.println("    3. Step 3: Boxing and Unboxing Deep Dive");
        System.out.println("       → How boxing/unboxing works internally");
        System.out.println("       → Performance implications");
        System.out.println("       → Null handling in unboxing");

        System.out.println("\n    4. Step 4: Advanced Concepts");
        System.out.println("       → Integer caching (-128 to 127)");
        System.out.println("       → Cache boundaries and behavior");
        System.out.println("       → Why == is dangerous");

        System.out.println("\n  ADVANCED LEVEL:");
        System.out.println("    5. Step 5: Utility Methods");
        System.out.println("       → All utility methods explained");
        System.out.println("       → Type conversions");
        System.out.println("       → String parsing techniques");

        System.out.println("\n    6. Step 6: Common Pitfalls");
        System.out.println("       → Mistakes to avoid");
        System.out.println("       → How to prevent errors");
        System.out.println("       → Debugging tips");

        System.out.println("\n    7. Step 7: Tips & Tricks");
        System.out.println("       → Best practices and optimization");
        System.out.println("       → Advanced techniques");
        System.out.println("       → Real-world patterns");

        System.out.println("\n  8. Exit");
        System.out.println("=" + "=".repeat(59));
    }

    /**
     * Run Step 1: Basics
     */
    private static void runStep1() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Running: Step 1 - Basics of Wrapper Classes");
        System.out.println("=".repeat(60) + "\n");
        Step1_BasicsOfWrapperClasses.main(new String[]{});
    }

    /**
     * Run Step 2: Practical Applications
     */
    private static void runStep2() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Running: Step 2 - Practical Applications");
        System.out.println("=".repeat(60) + "\n");
        Step2_PracticalApplications.main(new String[]{});
    }

    /**
     * Run Step 3: Boxing and Unboxing
     */
    private static void runStep3() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Running: Step 3 - Boxing and Unboxing Deep Dive");
        System.out.println("=".repeat(60) + "\n");
        Step3_BoxingAndUnboxing.main(new String[]{});
    }

    /**
     * Run Step 4: Advanced Concepts
     */
    private static void runStep4() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Running: Step 4 - Advanced Concepts");
        System.out.println("=".repeat(60) + "\n");
        Step4_AdvancedConcepts.main(new String[]{});
    }

    /**
     * Run Step 5: Utility Methods
     */
    private static void runStep5() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Running: Step 5 - Utility Methods");
        System.out.println("=".repeat(60) + "\n");
        Step5_UtilityMethods.main(new String[]{});
    }

    /**
     * Run Step 6: Common Pitfalls
     */
    private static void runStep6() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Running: Step 6 - Common Pitfalls");
        System.out.println("=".repeat(60) + "\n");
        Step6_CommonPitfalls.main(new String[]{});
    }

    /**
     * Run Step 7: Tips and Tricks
     */
    private static void runStep7() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Running: Step 7 - Tips and Tricks");
        System.out.println("=".repeat(60) + "\n");
        Step7_TipsAndTricks.main(new String[]{});
    }

    // ============= QUICK REFERENCE SECTION =============

    /**
     * Print quick reference
     */
    public static void printQuickReference() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("QUICK REFERENCE: WRAPPER CLASSES");
        System.out.println("=".repeat(60));

        System.out.println("\n📦 PRIMITIVE -> WRAPPER MAPPING:");
        System.out.println("  boolean  -> Boolean");
        System.out.println("  byte     -> Byte");
        System.out.println("  char     -> Character");
        System.out.println("  short    -> Short");
        System.out.println("  int      -> Integer");
        System.out.println("  long     -> Long");
        System.out.println("  float    -> Float");
        System.out.println("  double   -> Double");

        System.out.println("\n🔄 AUTO-BOXING & UNBOXING:");
        System.out.println("  Auto-boxing:   Integer num = 100;  // primitive -> wrapper");
        System.out.println("  Unboxing:      int val = num;      // wrapper -> primitive");

        System.out.println("\n📊 COMPARISON RULES:");
        System.out.println("  ❌ DON'T: if (a == b)           // Compares references");
        System.out.println("  ✓ DO:     if (a.equals(b))      // Compares values");

        System.out.println("\n💾 CACHING:");
        System.out.println("  Integer Cache: -128 to 127");
        System.out.println("  Boolean Cache: true, false");
        System.out.println("  Float/Double:  NO CACHE");

        System.out.println("\n🛡️ NULL SAFETY:");
        System.out.println("  Integer val = null;");
        System.out.println("  int primitive = val;          // NullPointerException!");
        System.out.println("  int safe = val != null ? val : 0;  // Safe");

        System.out.println("\n" + "=".repeat(60));
    }
}

