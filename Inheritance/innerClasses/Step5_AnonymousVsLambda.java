package me.niteshh.OPPs.Inheritance.innerClasses;

/**
 * STEP 5: ANONYMOUS CLASS VS LAMBDA EXPRESSIONS
 * 
 * Both are used to implement functional interfaces with minimal code.
 * But they have important differences in readability, performance, and use cases.
 * 
 * FUNCTIONAL INTERFACE:
 * An interface with exactly ONE abstract method.
 * Can be marked with @FunctionalInterface annotation.
 * 
 * KEY DIFFERENCES:
 * ✓ Lambdas are more concise and readable for simple implementations
 * ✓ Anonymous classes are better for complex logic with multiple methods
 * ✓ Performance: Lambdas are generally more efficient
 * ✓ Syntax: Lambda is cleaner and requires less boilerplate
 */

public class Step5_AnonymousVsLambda {

    // ==================== FUNCTIONAL INTERFACES ====================
    
    /**
     * Marked as FunctionalInterface
     * Must have exactly ONE abstract method
     */
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }
    
    @FunctionalInterface
    interface Greeting {
        void greet(String name);
    }
    
    @FunctionalInterface
    interface TextProcessor {
        String process(String input);
    }
    
    // ==================== COMPARISON 1: SIMPLE CALCULATION ====================
    
    /**
     * STEP 5.1: Anonymous Class vs Lambda - Simple Operation
     */
    public void compareSimpleOperation() {
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPARISON 1: SIMPLE OPERATION (Addition)");
        System.out.println("=".repeat(60));
        
        // ❌ ANONYMOUS CLASS - More verbose
        System.out.println("\n🔴 ANONYMOUS CLASS APPROACH:");
        System.out.println("─".repeat(60));
        
        Calculator addAnon = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                return a + b;
            }
        };
        
        System.out.println("Code: new Calculator() { @Override public int calculate(...) }");
        System.out.println("Result: 10 + 5 = " + addAnon.calculate(10, 5));
        System.out.println("Lines of Code: 4");
        
        // ✅ LAMBDA - Concise and clean
        System.out.println("\n🟢 LAMBDA EXPRESSION APPROACH:");
        System.out.println("─".repeat(60));
        
        Calculator addLambda = (a, b) -> a + b;
        
        System.out.println("Code: (a, b) -> a + b");
        System.out.println("Result: 10 + 5 = " + addLambda.calculate(10, 5));
        System.out.println("Lines of Code: 1");
        System.out.println("\n✨ Lambda is 4x shorter!");
    }
    
    // ==================== COMPARISON 2: MULTIPLE STATEMENTS ====================
    
    /**
     * STEP 5.2: When multiple statements are needed
     */
    public void compareMultipleStatements() {
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPARISON 2: MULTIPLE STATEMENTS (Division with validation)");
        System.out.println("=".repeat(60));
        
        // ANONYMOUS CLASS - Good for complex logic
        System.out.println("\n🔴 ANONYMOUS CLASS - BETTER HERE:");
        System.out.println("─".repeat(60));
        
        Calculator divideAnon = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                if (b == 0) {
                    System.out.println("   ⚠️  Warning: Division by zero!");
                    return 0;
                }
                System.out.println("   ✓ Division valid");
                return a / b;
            }
        };
        
        System.out.println("Using anonymous class:");
        int result = divideAnon.calculate(10, 0);
        System.out.println("Result: " + result);
        
        // LAMBDA - Awkward for multiple statements
        System.out.println("\n🟡 LAMBDA - AWKWARD HERE:");
        System.out.println("─".repeat(60));
        
        // Lambda with multiple statements requires braces and explicit return
        Calculator divideLambda = (a, b) -> {
            if (b == 0) {
                System.out.println("   ⚠️  Warning: Division by zero!");
                return 0;
            }
            System.out.println("   ✓ Division valid");
            return a / b;
        };
        
        System.out.println("Using lambda (with curly braces):");
        int resultLambda = divideLambda.calculate(10, 2);
        System.out.println("Result: " + resultLambda);
        
        System.out.println("\n📌 Note: Anonymous class is more readable here!");
    }
    
    // ==================== COMPARISON 3: DIFFERENT FUNCTIONAL INTERFACES ====================
    
    /**
     * STEP 5.3: Practical examples with different interfaces
     */
    public void practicalExamples() {
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPARISON 3: PRACTICAL EXAMPLES");
        System.out.println("=".repeat(60));
        
        // Example 1: String Greeting
        System.out.println("\n📌 EXAMPLE 1: GREETING");
        System.out.println("─".repeat(60));
        
        // Anonymous class
        Greeting greetAnon = new Greeting() {
            @Override
            public void greet(String name) {
                System.out.println("Hello, " + name + "! Welcome to SmartCar System");
            }
        };
        
        // Lambda
        Greeting greetLambda = name -> System.out.println("Hello, " + name + "! Welcome to SmartCar System");
        
        System.out.println("Anonymous: ");
        greetAnon.greet("Nitesh");
        System.out.println("\nLambda: ");
        greetLambda.greet("Nitesh");
        System.out.println("\n✅ Both work, but lambda is cleaner!");
        
        // Example 2: String Processing
        System.out.println("\n📌 EXAMPLE 2: STRING PROCESSING");
        System.out.println("─".repeat(60));
        
        // Anonymous class
        TextProcessor toUpperAnon = new TextProcessor() {
            @Override
            public String process(String input) {
                return input.toUpperCase();
            }
        };
        
        // Lambda
        TextProcessor toUpperLambda = str -> str.toUpperCase();
        
        String text = "smart car system";
        System.out.println("Input: " + text);
        System.out.println("Anonymous result: " + toUpperAnon.process(text));
        System.out.println("Lambda result: " + toUpperLambda.process(text));
    }
    
    // ==================== COMPARISON 4: WITH BUILTIN INTERFACES ====================
    
    /**
     * STEP 5.4: Using with Java's built-in functional interfaces
     */
    public void builtInInterfacesComparison() {
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPARISON 4: JAVA BUILT-IN FUNCTIONAL INTERFACES");
        System.out.println("=".repeat(60));
        
        // Runnable - Common for threading
        System.out.println("\n📌 RUNNABLE (Threading)");
        System.out.println("─".repeat(60));
        
        // Anonymous class
        System.out.println("Anonymous class:");
        Runnable runAnon = new Runnable() {
            @Override
            public void run() {
                System.out.println("   Running in thread (anonymous)");
            }
        };
        
        // Lambda
        System.out.println("\nLambda:");
        Runnable runLambda = () -> System.out.println("   Running in thread (lambda)");
        
        runAnon.run();
        runLambda.run();
        
        // Comparator - Common for sorting
        System.out.println("\n📌 COMPARATOR (Sorting)");
        System.out.println("─".repeat(60));
        
        java.util.List<String> cars = new java.util.ArrayList<>();
        cars.addAll(java.util.Arrays.asList("BMW", "Audi", "Mercedes"));
        
        // Anonymous comparator
        System.out.println("Anonymous comparator:");
        java.util.Collections.sort(cars, new java.util.Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });
        System.out.println("Sorted: " + cars);
        
        // Lambda comparator
        System.out.println("\nLambda comparator:");
        cars = new java.util.ArrayList<>(java.util.Arrays.asList("BMW", "Audi", "Mercedes"));
        java.util.Collections.sort(cars, (a, b) -> a.compareTo(b));
        System.out.println("Sorted: " + cars);
    }
    
    // ==================== DECISION GUIDE ====================
    
    /**
     * STEP 5.5: When to use what
     */
    public void decisionGuide() {
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DECISION GUIDE: WHEN TO USE WHAT?");
        System.out.println("=".repeat(60));
        
        System.out.println("""
            ┌─────────────────────────────────────────────────────┐
            │ USE ANONYMOUS CLASS WHEN:                           │
            ├─────────────────────────────────────────────────────┤
            │ ✓ Implementing interface with 2+ methods            │
            │ ✓ Need complex logic with multiple statements       │
            │ ✓ Need to maintain state with multiple fields       │
            │ ✓ Java version < 8 (no lambda support)              │
            │ ✓ Non-functional interface implementation           │
            │ ✓ Extending abstract class with multiple methods    │
            │ ✓ Need constructor-like initialization              │
            └─────────────────────────────────────────────────────┘
            
            ┌─────────────────────────────────────────────────────┐
            │ USE LAMBDA WHEN:                                    │
            ├─────────────────────────────────────────────────────┤
            │ ✓ Single abstract method (functional interface)     │
            │ ✓ Simple, one-liner implementation                  │
            │ ✓ Java 8 or later                                   │
            │ ✓ Common operations: sorting, filtering, mapping   │
            │ ✓ Callback/listener with simple behavior            │
            │ ✓ Want cleaner, more readable code                  │
            │ ✓ No state needed                                   │
            └─────────────────────────────────────────────────────┘
            
            ┌─────────────────────────────────────────────────────┐
            │ PERFORMANCE NOTES:                                  │
            ├─────────────────────────────────────────────────────┤
            │ • Lambdas: Use invokedynamic instruction (faster)   │
            │ • Anonymous: Regular virtual method calls           │
            │ • Lambdas generate less bytecode                    │
            │ • Performance difference is negligible in most apps │
            └─────────────────────────────────────────────────────┘
            """);
    }
}

// ==================== SUMMARY: ANONYMOUS CLASS VS LAMBDA ====================
/*
 * QUICK COMPARISON TABLE:
 * ┌──────────────────┬──────────────────┬──────────────────┐
 * │ Aspect           │ Anonymous Class  │ Lambda           │
 * ├──────────────────┼──────────────────┼──────────────────┤
 * │ Code Conciseness │ Verbose          │ Very Concise     │
 * │ Methods Support  │ Multiple         │ Only 1 (in FI)   │
 * │ State            │ Can maintain     │ Cannot maintain  │
 * │ Java Version     │ All versions     │ Java 8+          │
 * │ Readability      │ Good for complex │ Best for simple  │
 * │ Performance      │ Standard         │ Slightly faster  │
 * │ Compilation      │ .class generated │ invokedynamic    │
 * │ Debugging        │ Easier           │ Harder (stack)   │
 * └──────────────────┴──────────────────┴──────────────────┘
 * 
 * LAMBDA SYNTAX RULES:
 * 1. Single parameter, no type:      x -> x * 2
 * 2. Single parameter with type:     (int x) -> x * 2
 * 3. Multiple parameters:             (x, y) -> x + y
 * 4. Multiple params with types:      (int x, int y) -> x + y
 * 5. Single statement:                (x, y) -> x + y
 * 6. Multiple statements:             (x, y) -> { statements; return value; }
 * 7. No parameters:                   () -> System.out.println("Hi")
 * 
 * EVOLUTION IN JAVA:
 * Java 5:  Anonymous classes became standard
 * Java 8:  Lambda expressions introduced (game changer!)
 * Java 10: var keyword for type inference
 * Java 14: Records introduced (immutable data holders)
 * 
 * BEST PRACTICES:
 * • Prefer lambdas for simple, single-method implementations
 * • Use anonymous classes for complex logic
 * • Always use @FunctionalInterface annotation for clarity
 * • Avoid nested lambdas - they reduce readability
 * • Consider method references for even cleaner code
 */

