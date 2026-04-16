package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * MAIN CLASS: ExceptionHandlingComprehensiveGuide
 * 
 * This is the main entry point for the complete exception handling learning journey.
 * Run this class to execute all examples in a structured manner from beginner to advanced.
 * 
 * Learning Path:
 * Step 1: Basics of Exceptions
 * Step 2: Try-Catch-Finally Mechanism
 * Step 3: Checked vs Unchecked Exceptions
 * Step 4: Creating Custom Exceptions
 * Step 5: Throws Keyword and Exception Propagation
 * Step 6: Advanced Exception Handling
 * Step 7: Tips and Tricks
 * 
 * Each step builds upon the previous one, progressing from beginner to advanced level.
 * All methods are fully implemented with detailed comments and real-world examples.
 */

import java.util.*;

public class ExceptionHandlingMain {

    public static void main(String[] args) {
        // Display welcome banner
        displayWelcomeBanner();
        
        // Create interactive menu
        boolean continueProgram = true;
        Scanner scanner = new Scanner(System.in);
        
        while (continueProgram) {
            displayMenu();
            System.out.print("\n→ Enter your choice (0-8): ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                continueProgram = handleMenuChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input. Please enter a number between 0 and 8.");
            } catch (Exception e) {
                System.out.println("✗ An error occurred: " + e.getMessage());
            }
        }
        
        scanner.close();
        displayGoodbyeMessage();
    }
    
    /**
     * Display welcome banner with learning objectives
     */
    static void displayWelcomeBanner() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("    COMPREHENSIVE JAVA EXCEPTION HANDLING GUIDE");
        System.out.println("    From Beginner to Advanced Level");
        System.out.println("=".repeat(70));
        System.out.println("\n📚 LEARNING OBJECTIVES:");
        System.out.println("  ✓ Understand exception basics and hierarchy");
        System.out.println("  ✓ Master try-catch-finally mechanism");
        System.out.println("  ✓ Distinguish between checked and unchecked exceptions");
        System.out.println("  ✓ Create custom exceptions for your domain");
        System.out.println("  ✓ Use throws keyword and exception propagation");
        System.out.println("  ✓ Learn advanced exception handling techniques");
        System.out.println("  ✓ Apply practical tips and best practices");
        System.out.println("\n" + "=".repeat(70) + "\n");
    }
    
    /**
     * Display menu options
     */
    static void displayMenu() {
        System.out.println("\n" + "-".repeat(70));
        System.out.println("SELECT A LEARNING STEP:");
        System.out.println("-".repeat(70));
        System.out.println("1. Step 1  → Basics of Exceptions");
        System.out.println("            (Exception hierarchy, types, simple examples)");
        System.out.println("\n2. Step 2  → Try-Catch-Finally Mechanism");
        System.out.println("            (Try block, catch block, finally block, execution flow)");
        System.out.println("\n3. Step 3  → Checked vs Unchecked Exceptions");
        System.out.println("            (Compile-time vs Runtime, handling requirements)");
        System.out.println("\n4. Step 4  → Creating Custom Exceptions");
        System.out.println("            (Custom checked/unchecked, exception fields)");
        System.out.println("\n5. Step 5  → Throws Keyword & Exception Propagation");
        System.out.println("            (Throws declaration, call stack propagation)");
        System.out.println("\n6. Step 6  → Advanced Exception Handling");
        System.out.println("            (Try-with-resources, exception suppression, stack traces)");
        System.out.println("\n7. Step 7  → Tips and Tricks");
        System.out.println("            (Best practices, pitfalls, performance, testing)");
        System.out.println("\n8. Display → Complete Learning Guide (Text Format)");
        System.out.println("            (Read all concepts and explanations)");
        System.out.println("\n0. Exit    → Quit the program");
        System.out.println("-".repeat(70));
    }
    
    /**
     * Handle menu choice and execute corresponding example
     * 
     * @param choice User's menu choice
     * @return true to continue program, false to exit
     */
    static boolean handleMenuChoice(int choice) {
        System.out.println();
        
        switch (choice) {
            case 1:
                runStep1();
                return true;
                
            case 2:
                runStep2();
                return true;
                
            case 3:
                runStep3();
                return true;
                
            case 4:
                runStep4();
                return true;
                
            case 5:
                runStep5();
                return true;
                
            case 6:
                runStep6();
                return true;
                
            case 7:
                runStep7();
                return true;
                
            case 8:
                displayCompleteLearningGuide();
                return true;
                
            case 0:
                return false;
                
            default:
                System.out.println("✗ Invalid choice. Please select 0-8.");
                return true;
        }
    }
    
    /**
     * Run Step 1: Basics of Exceptions
     */
    static void runStep1() {
        System.out.println("🚀 Launching: STEP 1 - BASICS OF EXCEPTIONS");
        System.out.println("Demonstrating exception basics and hierarchy...\n");
        try {
            Class.forName("me.niteshh.OPPs.tutorial.exceptionHandling.inDepth.Step1_BasicsOfExceptions")
                .getMethod("main", String[].class)
                .invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.out.println("Error running Step 1: " + e.getMessage());
        }
    }
    
    /**
     * Run Step 2: Try-Catch-Finally
     */
    static void runStep2() {
        System.out.println("🚀 Launching: STEP 2 - TRY-CATCH-FINALLY MECHANISM");
        System.out.println("Demonstrating exception handling structure...\n");
        try {
            Class.forName("me.niteshh.OPPs.tutorial.exceptionHandling.inDepth.Step2_TryCatchFinally")
                .getMethod("main", String[].class)
                .invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.out.println("Error running Step 2: " + e.getMessage());
        }
    }
    
    /**
     * Run Step 3: Checked vs Unchecked
     */
    static void runStep3() {
        System.out.println("🚀 Launching: STEP 3 - CHECKED VS UNCHECKED EXCEPTIONS");
        System.out.println("Demonstrating different exception types...\n");
        try {
            Class.forName("me.niteshh.OPPs.tutorial.exceptionHandling.inDepth.Step3_CheckedVsUnchecked")
                .getMethod("main", String[].class)
                .invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.out.println("Error running Step 3: " + e.getMessage());
        }
    }
    
    /**
     * Run Step 4: Custom Exceptions
     */
    static void runStep4() {
        System.out.println("🚀 Launching: STEP 4 - CREATING CUSTOM EXCEPTIONS");
        System.out.println("Demonstrating custom exception creation...\n");
        try {
            Class.forName("me.niteshh.OPPs.tutorial.exceptionHandling.inDepth.Step4_CustomExceptions")
                .getMethod("main", String[].class)
                .invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.out.println("Error running Step 4: " + e.getMessage());
        }
    }
    
    /**
     * Run Step 5: Throws Keyword
     */
    static void runStep5() {
        System.out.println("🚀 Launching: STEP 5 - THROWS KEYWORD & PROPAGATION");
        System.out.println("Demonstrating exception propagation...\n");
        try {
            Class.forName("me.niteshh.OPPs.tutorial.exceptionHandling.inDepth.Step5_ThrowsKeyword")
                .getMethod("main", String[].class)
                .invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.out.println("Error running Step 5: " + e.getMessage());
        }
    }
    
    /**
     * Run Step 6: Advanced Handling
     */
    static void runStep6() {
        System.out.println("🚀 Launching: STEP 6 - ADVANCED EXCEPTION HANDLING");
        System.out.println("Demonstrating advanced techniques...\n");
        try {
            Class.forName("me.niteshh.OPPs.tutorial.exceptionHandling.inDepth.Step6_AdvancedExceptionHandling")
                .getMethod("main", String[].class)
                .invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.out.println("Error running Step 6: " + e.getMessage());
        }
    }
    
    /**
     * Run Step 7: Tips and Tricks
     */
    static void runStep7() {
        System.out.println("🚀 Launching: STEP 7 - TIPS AND TRICKS");
        System.out.println("Demonstrating best practices and common pitfalls...\n");
        try {
            Class.forName("me.niteshh.OPPs.tutorial.exceptionHandling.inDepth.Step7_TipsAndTricks")
                .getMethod("main", String[].class)
                .invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            System.out.println("Error running Step 7: " + e.getMessage());
        }
    }
    
    /**
     * Display complete learning guide in text format
     */
    static void displayCompleteLearningGuide() {
        System.out.println("📖 COMPLETE EXCEPTION HANDLING LEARNING GUIDE");
        System.out.println("=".repeat(70) + "\n");
        
        System.out.println("CHAPTER 1: EXCEPTION BASICS");
        System.out.println("-".repeat(70));
        System.out.println("• Exception: An abnormal event disrupting normal program flow");
        System.out.println("• Hierarchy: Throwable → Error/Exception → Checked/Unchecked");
        System.out.println("• Checked: Compile-time, must handle (IOException, SQLException)");
        System.out.println("• Unchecked: Runtime, optional to handle (NullPointerException)");
        System.out.println("• Benefits: Graceful recovery, better error reporting, stability\n");
        
        System.out.println("CHAPTER 2: TRY-CATCH-FINALLY");
        System.out.println("-".repeat(70));
        System.out.println("• try: Contains risky code");
        System.out.println("• catch: Handles exceptions (can have multiple)");
        System.out.println("• finally: Always executes (cleanup code)");
        System.out.println("• Order matters: Specific exceptions before general");
        System.out.println("• Multi-catch: catch (Ex1 | Ex2 e) - Java 7+\n");
        
        System.out.println("CHAPTER 3: CHECKED VS UNCHECKED");
        System.out.println("-".repeat(70));
        System.out.println("• Checked exceptions:");
        System.out.println("  - Extends Exception");
        System.out.println("  - Must catch or declare with throws");
        System.out.println("  - For recoverable conditions (file I/O, network)");
        System.out.println("• Unchecked exceptions:");
        System.out.println("  - Extends RuntimeException");
        System.out.println("  - Optional to handle");
        System.out.println("  - For programming errors (null pointer, index out of bounds)\n");
        
        System.out.println("CHAPTER 4: CUSTOM EXCEPTIONS");
        System.out.println("-".repeat(70));
        System.out.println("• Extend Exception (checked) or RuntimeException (unchecked)");
        System.out.println("• Provide constructors with context information");
        System.out.println("• Include custom fields for error details");
        System.out.println("• Use meaningful names reflecting the error");
        System.out.println("• Implement getters for exception details\n");
        
        System.out.println("CHAPTER 5: THROWS & PROPAGATION");
        System.out.println("-".repeat(70));
        System.out.println("• throws: Declares potential exceptions from method");
        System.out.println("• Propagation: Exception moves up call stack");
        System.out.println("• Multiple: throws IOException, SQLException");
        System.out.println("• Re-throw: catch, process, then rethrow exception");
        System.out.println("• Partial handling: Some caught, some propagated\n");
        
        System.out.println("CHAPTER 6: ADVANCED TECHNIQUES");
        System.out.println("-".repeat(70));
        System.out.println("• Try-with-resources: Auto close AutoCloseable resources");
        System.out.println("• Exception suppression: getSuppressed() for secondary exceptions");
        System.out.println("• Stack trace: getStackTrace() for call sequence");
        System.out.println("• Cause chain: getCause() for root exception");
        System.out.println("• Handling strategies: Fail fast vs Fail safe\n");
        
        System.out.println("CHAPTER 7: BEST PRACTICES");
        System.out.println("-".repeat(70));
        System.out.println("✓ DO:");
        System.out.println("  - Catch specific exceptions");
        System.out.println("  - Always handle or log exceptions");
        System.out.println("  - Provide meaningful error messages");
        System.out.println("  - Use exception chaining for root cause");
        System.out.println("  - Clean up resources with finally/try-with-resources");
        System.out.println("\n✗ DON'T:");
        System.out.println("  - Catch generic Exception");
        System.out.println("  - Use empty catch blocks");
        System.out.println("  - Ignore exception information");
        System.out.println("  - Use exceptions for flow control");
        System.out.println("  - Forget to close resources\n");
        
        System.out.println("QUICK REFERENCE: COMMON EXCEPTIONS");
        System.out.println("-".repeat(70));
        System.out.println("Checked:");
        System.out.println("  • IOException - File/stream operations fail");
        System.out.println("  • SQLException - Database operations fail");
        System.out.println("  • FileNotFoundException - File not found");
        System.out.println("  • InterruptedException - Thread interrupted");
        System.out.println("\nUnchecked:");
        System.out.println("  • NullPointerException - Method on null object");
        System.out.println("  • ArrayIndexOutOfBoundsException - Invalid array index");
        System.out.println("  • NumberFormatException - Invalid number format");
        System.out.println("  • ClassCastException - Invalid type cast");
        System.out.println("  • IllegalArgumentException - Invalid argument value\n");
        
        System.out.println("=".repeat(70));
        System.out.println("✓ Review each step for detailed examples and code!");
        System.out.println("=".repeat(70) + "\n");
    }
    
    /**
     * Display goodbye message with summary
     */
    static void displayGoodbyeMessage() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("    THANK YOU FOR LEARNING EXCEPTION HANDLING!");
        System.out.println("=".repeat(70));
        System.out.println("\n📚 WHAT YOU'VE LEARNED:");
        System.out.println("  ✓ Exception hierarchy and types");
        System.out.println("  ✓ Try-catch-finally mechanism");
        System.out.println("  ✓ Checked vs Unchecked exceptions");
        System.out.println("  ✓ Creating custom exceptions");
        System.out.println("  ✓ Exception propagation and throws");
        System.out.println("  ✓ Advanced handling techniques");
        System.out.println("  ✓ Best practices and tips");
        System.out.println("\n🎯 NEXT STEPS:");
        System.out.println("  1. Review each step multiple times");
        System.out.println("  2. Practice with your own code examples");
        System.out.println("  3. Implement proper error handling in your projects");
        System.out.println("  4. Study exception handling in popular frameworks");
        System.out.println("  5. Write unit tests for exception scenarios");
        System.out.println("\n💡 REMEMBER:");
        System.out.println("  • Exceptions are for exceptional conditions");
        System.out.println("  • Always provide meaningful error information");
        System.out.println("  • Clean up resources in finally block");
        System.out.println("  • Catch specific exceptions, not generic ones");
        System.out.println("  • Use exception chaining to preserve debugging info");
        System.out.println("\nGood luck with your Java programming journey! 🚀");
        System.out.println("=".repeat(70) + "\n");
    }
}

/**
 * KEY TAKEAWAYS FOR QUICK REFERENCE:
 * 
 * BASIC SYNTAX:
 * try {
 *     // Code that might throw exception
 * } catch (SpecificException e) {
 *     // Handle exception
 * } finally {
 *     // Always executes - cleanup code
 * }
 * 
 * EXCEPTION TYPES:
 * • Checked (must handle): IOException, SQLException, FileNotFoundException
 * • Unchecked (optional): NullPointerException, ArrayIndexOutOfBoundsException
 * 
 * THROWS KEYWORD:
 * public void method() throws IOException, SQLException {
 *     // Method can throw these checked exceptions
 * }
 * 
 * CUSTOM EXCEPTION:
 * class MyException extends Exception {
 *     public MyException(String message) {
 *         super(message);
 *     }
 * }
 * 
 * EXCEPTION CHAIN:
 * catch (IOException e) {
 *     throw new RuntimeException("Context about error", e);
 * }
 * 
 * TRY-WITH-RESOURCES (Java 7+):
 * try (AutoCloseable resource = new Resource()) {
 *     // Resource automatically closed
 * } catch (Exception e) {
 *     // Handle exception
 * }
 * 
 * BEST PRACTICE PATTERN:
 * try {
 *     // Risky code
 * } catch (SpecificCheckedException e) {
 *     // Log and handle checked exception
 *     logger.error("Specific error: " + e.getMessage(), e);
 *     // Take recovery action
 * } catch (SpecificRuntimeException e) {
 *     // Log runtime error
 *     logger.error("Programming error: " + e.getMessage(), e);
 *     // Maybe rethrow if critical
 * } finally {
 *     // Cleanup resources
 *     closeResources();
 * }
 */

