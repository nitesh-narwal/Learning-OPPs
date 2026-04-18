package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

import java.util.*;

/**
 * MAIN CLASS - USEFUL FUNCTIONS IN JAVA.LANG PACKAGE
 * 
 * Complete learning path from beginner to advanced level.
 * 
 * 📚 LEARNING STRUCTURE:
 * 
 * STEP 1: String Class Basics
 *         - String creation
 *         - Basic methods (length, charAt, substring)
 *         - Case conversion
 *         - Searching and comparison
 * 
 * STEP 2: Object Class Deep Dive
 *         - toString(), equals(), hashCode()
 *         - getClass() for runtime inspection
 *         - clone() for object copying
 *         - HashMap and HashSet usage
 * 
 * STEP 3: System Class Essentials
 *         - I/O streams (System.out, System.err, System.in)
 *         - Timing methods (currentTimeMillis, nanoTime)
 *         - System properties and environment variables
 *         - Memory and garbage collection
 * 
 * STEP 4: Math and Number Classes
 *         - Math constants (PI, E)
 *         - Arithmetic operations (abs, max, min)
 *         - Power and square root
 *         - Rounding methods (round, floor, ceil)
 *         - Trigonometric and logarithmic functions
 *         - Random number generation
 * 
 * STEP 5: Class and Reflection - Advanced
 *         - Getting Class objects
 *         - Class metadata inspection
 *         - Method and field reflection
 *         - Creating instances at runtime
 *         - Dynamic method invocation
 * 
 * STEP 6: Advanced String Operations
 *         - StringBuilder for efficient building
 *         - String formatting (format, printf)
 *         - Regular expressions and pattern matching
 *         - Text processing
 * 
 * STEP 7: Tips and Tricks
 *         - Performance optimization tips
 *         - Best practices for production code
 *         - Memory efficiency guidelines
 *         - Common gotchas and solutions
 * 
 * STEP 8: Confusions and Common Mistakes
 *         - String comparison with ==
 *         - Integer caching confusion
 *         - Unboxing null values
 *         - Performance pitfalls
 *         - Critical production bugs
 * 
 * 🎯 LEARNING PROGRESSION:
 * Beginner → Intermediate → Advanced
 * 
 * Each step builds upon previous knowledge and includes:
 * - Detailed explanations
 * - Runnable code examples
 * - Real-world use cases
 * - Best practices
 */

public class MainClassUsefullFunctions {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║    USEFUL FUNCTIONS IN JAVA.LANG PACKAGE - COMPLETE GUIDE      ║");
        System.out.println("║              From Beginner to Advanced Level                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        displayMenu();
    }

    /**
     * Display interactive menu
     */
    static void displayMenu() {
        System.out.println("📚 LEARNING MODULES:\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 1. String Class - Basics (Beginner Level)                       │");
        System.out.println("│    • String creation and basic operations                       │");
        System.out.println("│    • Search, comparison, and manipulation                       │");
        System.out.println("│    • Whitespace and replacement operations                      │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step1_StringClass_Basics                             │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 2. Object Class - Deep Dive (Intermediate Level)                │");
        System.out.println("│    • toString() for string representation                       │");
        System.out.println("│    • equals() and hashCode() contract                           │");
        System.out.println("│    • getClass() for runtime type information                    │");
        System.out.println("│    • HashMap and HashSet integration                            │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step2_ObjectClass_DeepDive                           │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 3. System Class - Essentials (Intermediate Level)               │");
        System.out.println("│    • I/O Streams (System.out, System.err, System.in)            │");
        System.out.println("│    • Timing methods (currentTimeMillis, nanoTime)               │");
        System.out.println("│    • System properties and environment variables                │");
        System.out.println("│    • Memory information and garbage collection                  │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step3_SystemClass_Essentials                         │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 4. Math and Number Classes (Intermediate Level)                 │");
        System.out.println("│    • Math constants (PI, E)                                     │");
        System.out.println("│    • Arithmetic operations (abs, max, min, sqrt, pow)           │");
        System.out.println("│    • Rounding methods (round, floor, ceil)                      │");
        System.out.println("│    • Trigonometric and logarithmic functions                    │");
        System.out.println("│    • Random number generation                                   │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step4_MathAndNumberClasses                           │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 5. Class and Reflection (Advanced Level)                        │");
        System.out.println("│    • Getting Class objects (Class.forName, .class, getClass)    │");
        System.out.println("│    • Class metadata inspection                                  │");
        System.out.println("│    • Inspecting methods, fields, and constructors               │");
        System.out.println("│    • Creating instances at runtime                              │");
        System.out.println("│    • Dynamic method invocation                                  │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step5_ClassAndReflection                             │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 6. Advanced String Operations (Advanced Level)                  │");
        System.out.println("│    • StringBuilder for efficient string building                │");
        System.out.println("│    • String formatting (format, printf)                         │");
        System.out.println("│    • Regular expressions and pattern matching                   │");
        System.out.println("│    • Text processing and validation                             │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step6_AdvancedStringOperations                       │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 7. Tips and Tricks - Best Practices (All Levels)                │");
        System.out.println("│    • String pool optimization                                   │");
        System.out.println("│    • Performance optimization tips                              │");
        System.out.println("│    • Memory efficiency guidelines                               │");
        System.out.println("│    • Common gotchas and solutions                               │");
        System.out.println("│    • Production-ready best practices                            │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step7_TipsAndTricks                                  │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 8. Confusions & Mistakes - Critical Issues (All Levels)         │");
        System.out.println("│    • String comparison with ==                                  │");
        System.out.println("│    • Integer caching confusion                                  │");
        System.out.println("│    • Unboxing null values (CRITICAL)                            │");
        System.out.println("│    • String concatenation in loops (CRITICAL)                   │");
        System.out.println("│    • Database NULL handling (CRITICAL)                          │");
        System.out.println("│    • Production bugs and how to avoid them                      │");
        System.out.println("│                                                                 │");
        System.out.println("│    👉 Run: Step8_ConfusionsAndMistakes                          │");
        System.out.println("└─────────────────────────────────────────────────────────────────┘\n");

        displayKeyHighlights();
        displayQuickReference();
        displayLearningPath();
    }

    /**
     * Display key highlights
     */
    static void displayKeyHighlights() {
        System.out.println("\n⭐ KEY HIGHLIGHTS:\n");

        System.out.println("🔤 STRING OPERATIONS:");
        System.out.println("   • Use equals() for comparison, never ==");
        System.out.println("   • Use StringBuilder in loops");
        System.out.println("   • String pool optimization");

        System.out.println("\n🔢 OBJECT OPERATIONS:");
        System.out.println("   • Override equals() and hashCode() together");
        System.out.println("   • toString() for debugging");
        System.out.println("   • getClass() for runtime type info");

        System.out.println("\n⚙️  SYSTEM UTILITIES:");
        System.out.println("   • currentTimeMillis() for timing");
        System.out.println("   • getProperty() for system info");
        System.out.println("   • arraycopy() for efficient copying");

        System.out.println("\n📐 MATH OPERATIONS:");
        System.out.println("   • Math constants (PI, E)");
        System.out.println("   • Trigonometric functions");
        System.out.println("   • Random number generation");

        System.out.println("\n🔍 REFLECTION:");
        System.out.println("   • Dynamic class inspection");
        System.out.println("   • Runtime instance creation");
        System.out.println("   • Method invocation dynamically");

        System.out.println("\n⚠️  CRITICAL ISSUES:");
        System.out.println("   • Unboxing null → NullPointerException");
        System.out.println("   • String +=  in loops → Performance disaster");
        System.out.println("   • parseInt without try-catch → Application crash");
    }

    /**
     * Display quick reference
     */
    static void displayQuickReference() {
        System.out.println("\n📋 QUICK REFERENCE - WHEN TO USE WHAT:\n");

        System.out.println("┌─────────────────────┬──────────────────────────────────────┐");
        System.out.println("│ Task                │ Use This                             │");
        System.out.println("├─────────────────────┼──────────────────────────────────────┤");
        System.out.println("│ String comparison   │ equals() or equalsIgnoreCase()       │");
        System.out.println("│ Build long strings  │ StringBuilder                        │");
        System.out.println("│ Get string length   │ length() method                      │");
        System.out.println("│ Extract substring   │ substring() method                   │");
        System.out.println("│ Split text          │ split() with regex                   │");
        System.out.println("│ Object equality     │ override equals() and hashCode()     │");
        System.out.println("│ Get runtime type    │ getClass() or instanceof             │");
        System.out.println("│ Math operations     │ Math class static methods            │");
        System.out.println("│ Random numbers      │ Math.random() or Random class        │");
        System.out.println("│ System info         │ System.getProperty()                 │");
        System.out.println("│ Timing operations   │ System.currentTimeMillis()           │");
        System.out.println("│ Dynamic inspection  │ Reflection API (getDeclaredFields)   │");
        System.out.println("│ Format strings      │ String.format() or printf()          │");
        System.out.println("│ Pattern matching    │ Pattern.compile() and matches()      │");
        System.out.println("└─────────────────────┴──────────────────────────────────────┘");
    }

    /**
     * Display recommended learning path
     */
    static void displayLearningPath() {
        System.out.println("\n🎓 RECOMMENDED LEARNING PATH:\n");

        System.out.println("BEGINNER (Start here):");
        System.out.println("  1️⃣  Step1_StringClass_Basics");
        System.out.println("       └─ Understand String creation, basic methods");
        System.out.println("");

        System.out.println("INTERMEDIATE (After basics):");
        System.out.println("  2️⃣  Step2_ObjectClass_DeepDive");
        System.out.println("       └─ Learn equals(), hashCode(), toString()");
        System.out.println("  3️⃣  Step3_SystemClass_Essentials");
        System.out.println("       └─ System utilities and timing");
        System.out.println("  4️⃣  Step4_MathAndNumberClasses");
        System.out.println("       └─ Math operations and type conversion");

        System.out.println("\nADVANCED (Master level):");
        System.out.println("  5️⃣  Step5_ClassAndReflection");
        System.out.println("       └─ Advanced metaprogramming");
        System.out.println("  6️⃣  Step6_AdvancedStringOperations");
        System.out.println("       └─ StringBuilder, formatting, regex");

        System.out.println("\nALL LEVELS (Critical knowledge):");
        System.out.println("  7️⃣  Step7_TipsAndTricks");
        System.out.println("       └─ Best practices and optimization");
        System.out.println("  8️⃣  Step8_ConfusionsAndMistakes");
        System.out.println("       └─ Avoid critical bugs");

        System.out.println("\n💡 Pro Tip: Review Step7 and Step8 frequently!");
        System.out.println("           They contain production-critical knowledge.");
    }

    /**
     * Static block to provide usage instructions
     */
    static {
        System.out.println("═══════════════════════════════════════════════════════════════════");
        System.out.println("HOW TO USE THIS MODULE:");
        System.out.println("═══════════════════════════════════════════════════════════════════");
        System.out.println("");
        System.out.println("Run each step individually:");
        System.out.println("");
        System.out.println("  java Step1_StringClass_Basics");
        System.out.println("  java Step2_ObjectClass_DeepDive");
        System.out.println("  java Step3_SystemClass_Essentials");
        System.out.println("  java Step4_MathAndNumberClasses");
        System.out.println("  java Step5_ClassAndReflection");
        System.out.println("  java Step6_AdvancedStringOperations");
        System.out.println("  java Step7_TipsAndTricks");
        System.out.println("  java Step8_ConfusionsAndMistakes");
        System.out.println("");
        System.out.println("Or run this Main class to see the guide:");
        System.out.println("  java MainClassUsefullFunctions");
        System.out.println("");
        System.out.println("═══════════════════════════════════════════════════════════════════\n");
    }
}

