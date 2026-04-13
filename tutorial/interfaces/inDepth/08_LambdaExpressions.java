package me.niteshh.OPPs.tutorial.interfaces.inDepth;

import java.util.*;

/**
 * ============================================================================
 * STEP 8: LAMBDA EXPRESSIONS & FUNCTIONAL INTERFACES (Java 8+)
 * ============================================================================
 * 
 * Lambda expressions revolutionized Java code style
 * They make functional programming possible in Java
 * 
 * IMPORTANT: This requires Java 8 or higher
 * ============================================================================
 */

/**
 * ============================================================================
 * WHAT IS A FUNCTIONAL INTERFACE?
 * ============================================================================
 * 
 * Definition: An interface with exactly ONE abstract method
 * 
 * Characteristics:
 * 1. Must have exactly 1 abstract method (no more, no less)
 * 2. Can have multiple default methods
 * 3. Can have multiple static methods
 * 4. Can have Object methods (toString, equals, hashCode)
 * 5. Use @FunctionalInterface annotation (optional but recommended)
 * 
 * Examples from Java Standard Library:
 * - Runnable: void run()
 * - Callable: T call()
 * - Comparator: int compare(T o1, T o2)
 * - Consumer: void accept(T t)
 * - Supplier: T get()
 * - Function: R apply(T t)
 * - Predicate: boolean test(T t)
 */

/**
 * ============================================================================
 * LAMBDA EXPRESSION SYNTAX
 * ============================================================================
 * 
 * Traditional Anonymous Class:
 * ============================
 * Operation add = new Operation() {
 *     @Override
 *     public int calculate(int a, int b) {
 *         return a + b;
 *     }
 * };
 * 
 * Lambda Expression (Java 8+):
 * ============================
 * Operation add = (a, b) -> a + b;
 * 
 * Syntax Breakdown:
 * (a, b)     = Parameters (can omit types, compiler infers)
 * ->         = Lambda arrow (means "goes to")
 * a + b      = Body (implementation)
 * 
 * Different Lambda Syntax Variations:
 * 1. No parameters:        () -> System.out.println("Hello");
 * 2. One parameter:        x -> x * 2
 * 3. Multiple parameters:  (x, y) -> x + y
 * 4. Explicit types:       (int x, int y) -> x + y
 * 5. Multiple statements:  (x, y) -> { int sum = x + y; return sum; }
 * 
 * ============================================================================
 */

/**
 * EXAMPLE 1: Simple Functional Interface & Lambda
 */
@FunctionalInterface
interface StringTransformer {
    String transform(String input);
}

/**
 * EXAMPLE 2: Mathematical Functional Interface
 */
@FunctionalInterface
interface MathOperation {
    int calculate(int a, int b);
}

/**
 * EXAMPLE 3: Predicate - Tests condition
 */
@FunctionalInterface
interface NumberValidator {
    boolean isValid(int number);
}

/**
 * ============================================================================
 * DEMONSTRATION CLASS
 * ============================================================================
 */
class LambdaExpressionDemo {
    
    /**
     * Method to demonstrate different lambda syntax variations
     */
    public static void syntaxVariations() {
        System.out.println("\n┌─ LAMBDA SYNTAX VARIATIONS ────────────────────────────────────┐");
        
        // 1. Simple Math Operation
        System.out.println("│ 1. Simple Math Operation:\n");
        MathOperation add = (a, b) -> a + b;
        System.out.println("│ (a, b) -> a + b");
        System.out.println("│ Result: " + add.calculate(5, 3) + " (5 + 3)\n");
        
        // 2. String Transformation
        System.out.println("│ 2. String Transformation:\n");
        StringTransformer toUpperCase = str -> str.toUpperCase();
        System.out.println("│ str -> str.toUpperCase()");
        System.out.println("│ Result: " + toUpperCase.transform("hello") + "\n");
        
        // 3. With Multiple Statements
        System.out.println("│ 3. Lambda with Multiple Statements:\n");
        MathOperation multiply = (a, b) -> {
            System.out.println("│ Calculating: " + a + " * " + b);
            int result = a * b;
            System.out.println("│ Done!");
            return result;
        };
        System.out.println("│ Result: " + multiply.calculate(4, 5) + "\n");
        
        // 4. Complex String Operations
        System.out.println("│ 4. Complex String Transformation:\n");
        StringTransformer formatter = text -> {
            String result = "[" + text.toUpperCase() + "]";
            return result;
        };
        System.out.println("│ Result: " + formatter.transform("formatted") + "\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * Method to demonstrate lambda with business logic
     */
    public static void businessLogicExamples() {
        System.out.println("\n┌─ LAMBDA IN BUSINESS LOGIC ────────────────────────────────────┐");
        
        // Validator Examples
        System.out.println("│ Number Validators using Lambda:\n");
        
        NumberValidator isPositive = n -> n > 0;
        NumberValidator isEven = n -> n % 2 == 0;
        NumberValidator isPrime = n -> n > 1 && 
                                       java.util.stream.IntStream.range(2, n)
                                       .noneMatch(i -> n % i == 0);
        
        int testNumber = 17;
        
        System.out.println("│ Testing number: " + testNumber);
        System.out.println("│ Is Positive: " + isPositive.isValid(testNumber));
        System.out.println("│ Is Even: " + isEven.isValid(testNumber));
        System.out.println("│ Is Prime: " + isPrime.isValid(testNumber) + "\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * Method to demonstrate lambda with collections
     */
    public static void lambdaWithCollections() {
        System.out.println("\n┌─ LAMBDA WITH COLLECTIONS ────────────────────────────────────┐");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        System.out.println("│ Original List: " + numbers + "\n");
        
        // Filter even numbers
        System.out.println("│ 1. Filter even numbers using Predicate lambda:");
        List<Integer> evenNumbers = new ArrayList<>();
        numbers.forEach(n -> {
            if (n % 2 == 0) {
                evenNumbers.add(n);
            }
        });
        System.out.println("│ Even Numbers: " + evenNumbers + "\n");
        
        // Transform numbers
        System.out.println("│ 2. Transform (square) numbers using Function lambda:");
        List<Integer> squared = new ArrayList<>();
        numbers.forEach(n -> squared.add(n * n));
        System.out.println("│ Squared Numbers: " + squared + "\n");
        
        // Sort in reverse order
        System.out.println("│ 3. Sort in reverse using Comparator lambda:");
        List<Integer> sorted = new ArrayList<>(numbers);
        sorted.sort((a, b) -> b - a); // Reverse order
        System.out.println("│ Sorted (Descending): " + sorted + "\n");
        
        // Find sum using reduce
        System.out.println("│ 4. Calculate sum using reduce:");
        int sum = 0;
        for (Integer n : numbers) {
            sum += n;
        }
        System.out.println("│ Sum: " + sum + "\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * Method demonstrating Java's built-in functional interfaces
     */
    public static void javaBuiltInFunctionalInterfaces() {
        System.out.println("\n┌─ JAVA BUILT-IN FUNCTIONAL INTERFACES ─────────────────────────┐");
        
        // 1. Consumer - takes input, returns nothing
        System.out.println("│ 1. Consumer (takes T, returns nothing):\n");
        java.util.function.Consumer<String> printer = msg -> System.out.println("│ " + msg);
        printer.accept("Hello from Consumer!");
        printer.accept("Lambdas make Java cleaner!\n");
        
        // 2. Supplier - takes nothing, returns output
        System.out.println("│ 2. Supplier (takes nothing, returns T):\n");
        java.util.function.Supplier<String> messageSupplier = 
            () -> "Message from Supplier";
        System.out.println("│ " + messageSupplier.get() + "\n");
        
        // 3. Function - takes input, returns transformed output
        System.out.println("│ 3. Function (takes T, returns R):\n");
        java.util.function.Function<Integer, String> numberToWord = 
            n -> n == 1 ? "One" : n == 2 ? "Two" : n == 3 ? "Three" : "Other";
        System.out.println("│ Number 2 as word: " + numberToWord.apply(2) + "\n");
        
        // 4. Predicate - tests condition
        System.out.println("│ 4. Predicate (takes T, returns boolean):\n");
        java.util.function.Predicate<String> isLongString = str -> str.length() > 5;
        System.out.println("│ Is 'hello' long (>5 chars)? " + isLongString.test("hello"));
        System.out.println("│ Is 'helloworld' long (>5 chars)? " + isLongString.test("helloworld") + "\n");
        
        // 5. BiFunction - two inputs, one output
        System.out.println("│ 5. BiFunction (takes T and U, returns R):\n");
        java.util.function.BiFunction<Integer, Integer, String> addAndFormat = 
            (a, b) -> "Sum: " + (a + b);
        System.out.println("│ " + addAndFormat.apply(10, 20) + "\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * Method demonstrating functional programming with streams (Java 8+)
     */
    public static void streamsWithLambda() {
        System.out.println("\n┌─ STREAMS & LAMBDA EXPRESSIONS ────────────────────────────────┐");
        
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry", "Date", "Elderberry");
        
        System.out.println("│ Original List: " + fruits + "\n");
        
        // Map: Transform each element
        System.out.println("│ 1. Map (Transform):");
        System.out.println("│ Uppercase: ");
        fruits.stream()
            .map(String::toUpperCase)  // or: f -> f.toUpperCase()
            .forEach(f -> System.out.print("│ " + f + " "));
        System.out.println("\n");
        
        // Filter: Select matching elements
        System.out.println("│ 2. Filter (Select):");
        System.out.println("│ Fruits with 5+ characters: ");
        fruits.stream()
            .filter(f -> f.length() >= 5)
            .forEach(f -> System.out.print("│ " + f + " "));
        System.out.println("\n\n");
        
        // Count: Count matching elements
        System.out.println("│ 3. Count (Aggregate):");
        long count = fruits.stream()
            .filter(f -> f.length() >= 5)
            .count();
        System.out.println("│ Count of fruits with 5+ characters: " + count + "\n");
        
        // AnyMatch, AllMatch, NoneMatch: Predicate testing
        System.out.println("│ 4. Predicate Tests:");
        boolean hasApple = fruits.stream().anyMatch(f -> f.equals("Apple"));
        System.out.println("│ Has Apple? " + hasApple);
        
        boolean allLongerThan2 = fruits.stream().allMatch(f -> f.length() > 2);
        System.out.println("│ All longer than 2 chars? " + allLongerThan2 + "\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * Real-world example: Event handling
     */
    public static void eventHandlingExample() {
        System.out.println("\n┌─ REAL-WORLD: EVENT HANDLING ─────────────────────────────────┐");
        
        // Define callback interface
        @FunctionalInterface
        interface EventListener {
            void onEvent(String event);
        }
        
        // Simulate an event emitter
        class EventEmitter {
            private List<EventListener> listeners = new ArrayList<>();
            
            void subscribe(EventListener listener) {
                listeners.add(listener);
            }
            
            void emit(String event) {
                listeners.forEach(listener -> listener.onEvent(event));
            }
        }
        
        EventEmitter emitter = new EventEmitter();
        
        System.out.println("│ Subscribing to events using Lambda:\n");
        
        // Different listeners using lambdas
        emitter.subscribe(event -> System.out.println("│ [Logger] Event: " + event));
        emitter.subscribe(event -> System.out.println("│ [Handler] Processing: " + event));
        emitter.subscribe(event -> System.out.println("│ [Alert] Notifying: " + event));
        
        System.out.println("│ Emitting event...\n");
        emitter.emit("User Login");
        
        System.out.println("\n└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * Main method to run all demonstrations
     */
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     LAMBDA EXPRESSIONS & FUNCTIONAL INTERFACES (Java 8+)      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        syntaxVariations();
        businessLogicExamples();
        lambdaWithCollections();
        javaBuiltInFunctionalInterfaces();
        streamsWithLambda();
        eventHandlingExample();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    LAMBDA KEY POINTS                          ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║ ✓ Lambdas are anonymous functions                             ║");
        System.out.println("║ ✓ Work only with @FunctionalInterface (1 abstract method)     ║");
        System.out.println("║ ✓ Make code cleaner and more readable                        ║");
        System.out.println("║ ✓ Enable functional programming in Java                      ║");
        System.out.println("║ ✓ Perfect for callbacks and event handling                   ║");
        System.out.println("║ ✓ Works seamlessly with Streams API                          ║");
        System.out.println("║ ✓ Enables parallel processing                                ║");
        System.out.println("║ ✓ Replaces verbose anonymous classes                         ║");
        System.out.println("║ ✓ Industry standard in modern Java development               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
    }
}

/**
 * ============================================================================
 * COMPARISON: BEFORE vs AFTER LAMBDAS
 * ============================================================================
 * 
 * BEFORE Java 8 (Anonymous Class - VERBOSE):
 * ===========================================
 * List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
 * 
 * // Sort in reverse - SO VERBOSE!
 * Collections.sort(numbers, new Comparator<Integer>() {
 *     @Override
 *     public int compare(Integer a, Integer b) {
 *         return b - a;
 *     }
 * });
 * 
 * AFTER Java 8 (Lambda - CLEAN):
 * ===============================
 * List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
 * 
 * // Sort in reverse - SO CLEAN!
 * numbers.sort((a, b) -> b - a);
 * 
 * ============================================================================
 * WHEN TO USE LAMBDAS
 * ============================================================================
 * 
 * USE LAMBDAS:
 * ✓ When implementing functional interface (1 abstract method)
 * ✓ For callbacks and event handlers
 * ✓ With Collection operations (filter, map, reduce)
 * ✓ With Streams API
 * ✓ For simple, short implementations
 * ✓ Dependency injection with functional interfaces
 * 
 * DON'T USE LAMBDAS:
 * ✗ When implementation is complex (many lines)
 * ✗ When interface has multiple abstract methods
 * ✗ When you need access to local variables (beyond final/effectively final)
 * ✗ When code clarity suffers (use named methods instead)
 * 
 * ============================================================================
 */

