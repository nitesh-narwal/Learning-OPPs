package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.lambdaExp;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/*
 * ============================================================================
 * LAMBDA EXPRESSIONS - COMPREHENSIVE GUIDE
 * ============================================================================
 * 
 * WHAT IS A LAMBDA EXPRESSION?
 * ============================
 * A lambda expression is a SHORT, ANONYMOUS function (method without name)
 * that can be used to implement a functional interface (interface with 
 * exactly ONE abstract method).
 * 
 * In simple terms: Lambda = Anonymous function that does one thing
 * 
 * SYNTAX:
 * =======
 * (parameters) -> { body }
 * 
 * Examples:
 * (x) -> x * 2              // Takes x, returns x * 2
 * (a, b) -> a + b           // Takes a and b, returns their sum
 * () -> System.out.println("Hello")  // Takes nothing, prints message
 * (name) -> System.out.println("Hi " + name)  // Takes name, prints greeting
 * 
 * LAMBDA = Short way to write anonymous inner classes!
 * 
 * ============================================================================
 * BEFORE LAMBDA (Java 7 and earlier)
 * ===================================
 * 
 * Creating a thread required:
 * 
 * new Thread(new Runnable() {
 *     public void run() {
 *         System.out.println("Hello from thread");
 *     }
 * }).start();
 * 
 * AFTER LAMBDA (Java 8+)
 * ======================
 * 
 * Much shorter:
 * 
 * new Thread(() -> System.out.println("Hello from thread")).start();
 * 
 * Same result, but much cleaner!
 * 
 * ============================================================================
 * KEY BENEFITS OF LAMBDA EXPRESSIONS
 * ===================================
 * 
 * 1. CODE CONCISENESS
 *    - Reduces boilerplate code dramatically
 *    - Makes code more readable
 *    - Less typing, fewer lines
 * 
 * 2. READABILITY
 *    - Intent is immediately clear
 *    - No noise from anonymous class syntax
 *    - Easier to understand at a glance
 * 
 * 3. FUNCTIONAL PROGRAMMING
 *    - Enables functional programming style
 *    - Functions as first-class objects
 *    - Can pass functions around like values
 * 
 * 4. STREAM API INTEGRATION
 *    - Required for Java Streams (Java 8+)
 *    - Makes data processing intuitive
 *    - Parallel processing becomes simple
 * 
 * 5. BETTER MULTITHREADING
 *    - Cleaner thread creation syntax
 *    - Makes concurrent code easier to write
 *    - Callbacks become simpler
 * 
 * 6. JAVA COLLECTIONS
 *    - forEach(), filter(), map() become usable
 *    - Collections processing becomes elegant
 *    - Sorting and filtering made intuitive
 * 
 * ============================================================================
 * KEY DRAWBACKS OF LAMBDA EXPRESSIONS
 * ====================================
 * 
 * 1. READABILITY (when overused)
 *    - Complex lambdas are harder to read than methods
 *    - Stack traces are harder to debug
 *    - Line numbers less meaningful
 * 
 * 2. NO STATE MODIFICATION
 *    - Variables captured must be effectively final
 *    - Can't modify captured variables
 *    - Limited flexibility compared to methods
 * 
 * 3. DEBUGGING DIFFICULTY
 *    - Stack trace shows lambda instead of method name
 *    - Breakpoints less intuitive
 *    - Stack trace becomes confusing
 * 
 * 4. LEARNING CURVE
 *    - New syntax to learn
 *    - Requires understanding functional interfaces
 *    - Different programming paradigm
 * 
 * 5. PERFORMANCE (sometimes)
 *    - Creates objects for each lambda
 *    - Memory overhead in some cases
 *    - Not always better than regular methods
 * 
 * ============================================================================
 */

public class LambdaExpression {
    
    /*
     * ========================================================================
     * SECTION 1: FUNCTIONAL INTERFACES - FOUNDATION OF LAMBDAS
     * ========================================================================
     * 
     * WHAT IS A FUNCTIONAL INTERFACE?
     * ================================
     * An interface with EXACTLY ONE abstract method.
     * Lambda expressions can ONLY be used with functional interfaces.
     * 
     * EXAMPLES OF FUNCTIONAL INTERFACES:
     * ===================================
     * 
     * 1. Runnable
     *    public interface Runnable {
     *        void run();  // ONE abstract method
     *    }
     * 
     * 2. Comparator<T>
     *    public interface Comparator<T> {
     *        int compare(T o1, T o2);  // ONE abstract method
     *    }
     * 
     * 3. Callable<V>
     *    public interface Callable<V> {
     *        V call() throws Exception;  // ONE abstract method
     *    }
     * 
     * 4. Predicate<T> (from java.util.function)
     *    public interface Predicate<T> {
     *        boolean test(T t);  // ONE abstract method
     *    }
     * 
     * KEY: If interface has more than 1 abstract method, you CAN'T use lambda!
     * 
     * HOW LAMBDA MAPS TO FUNCTIONAL INTERFACE:
     * =========================================
     * 
     * Functional Interface:
     *   public interface Adder {
     *       int add(int a, int b);  // Method signature
     *   }
     * 
     * Using Lambda:
     *   Adder adder = (a, b) -> a + b;  // Lambda implements add()
     *   int result = adder.add(3, 5);   // result = 8
     * 
     * The lambda automatically implements the one abstract method!
     * 
     * COMMON BUILT-IN FUNCTIONAL INTERFACES:
     * ======================================
     * 
     * In java.util.function package:
     * - Predicate<T>: test(T) -> boolean
     * - Consumer<T>: accept(T) -> void
     * - Function<T, R>: apply(T) -> R
     * - Supplier<T>: get() -> T
     * - BinaryOperator<T>: apply(T, T) -> T
     * 
     * ========================================================================
     */
    
    // Example 1: Define your own functional interface
    @FunctionalInterface
    public interface Calculator {
        int calculate(int a, int b);  // Exactly one abstract method
    }
    
    // Example 2: Using lambda with custom functional interface
    public static void lambdaBasics() {
        System.out.println("=== SECTION 1: Lambda Basics ===\n");
        
        // Lambda implementing Calculator interface
        Calculator addition = (a, b) -> a + b;
        Calculator subtraction = (a, b) -> a - b;
        Calculator multiplication = (a, b) -> a * b;
        Calculator division = (a, b) -> a / b;
        
        System.out.println("5 + 3 = " + addition.calculate(5, 3));
        System.out.println("5 - 3 = " + subtraction.calculate(5, 3));
        System.out.println("5 * 3 = " + multiplication.calculate(5, 3));
        System.out.println("6 / 3 = " + division.calculate(6, 3));
        
        // Each lambda is a different implementation of same interface!
    }
    
    
    /*
     * ========================================================================
     * SECTION 2: LAMBDA SYNTAX VARIATIONS (Beginner to Intermediate)
     * ========================================================================
     * 
     * SYNTAX RULE 1: Parameter Types Can Be Omitted
     * ==============================================
     * (int a, int b) -> a + b    is same as    (a, b) -> a + b
     * 
     * Compiler can infer types from context!
     * 
     * 
     * SYNTAX RULE 2: Single Parameter Parentheses Can Be Omitted
     * ============================================================
     * (name) -> System.out.println(name)    is same as    name -> System.out.println(name)
     * 
     * But:
     * () -> System.out.println("Hi")        has no parameters, keep ()
     * (a, b) -> a + b                       multiple params, keep ()
     * 
     * 
     * SYNTAX RULE 3: Single Statement Braces Can Be Omitted
     * ========================================================
     * (a, b) -> { return a + b; }    is same as    (a, b) -> a + b
     * 
     * When body has single statement, braces and return are optional!
     * 
     * 
     * SYNTAX RULE 4: Return Type Is Inferred
     * =======================================
     * Compiler automatically determines return type from context.
     * You NEVER write return type explicitly.
     * 
     * 
     * EXAMPLES:
     * ==========
     * 
     * No parameters, no return:
     *   () -> System.out.println("Hello")
     * 
     * One parameter, no return:
     *   name -> System.out.println(name)
     *   OR
     *   (name) -> System.out.println(name)
     * 
     * One parameter, return value:
     *   x -> x * 2
     *   OR
     *   (x) -> x * 2
     * 
     * Multiple parameters, return value:
     *   (a, b) -> a + b
     * 
     * Complex body:
     *   (n) -> {
     *       int result = n * 2;
     *       return result;
     *   }
     * 
     * ========================================================================
     */
    
    // Example 3: Lambda syntax variations
    public static void lambdaSyntaxVariations() {
        System.out.println("\n=== SECTION 2: Lambda Syntax Variations ===\n");
        
        // Variation 1: No parameters, no return
        Runnable greet = () -> System.out.println("Hello World");
        greet.run();
        
        // Variation 2: One parameter, no type specified (inferred)
        Consumer<String> printName = name -> System.out.println("Name: " + name);
        printName.accept("John");
        
        // Variation 3: One parameter, with explicit type
        Consumer<String> printWithType = (String name) -> System.out.println("Name: " + name);
        printWithType.accept("Jane");
        
        // Variation 4: Multiple parameters, single return
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("3 + 7 = " + add.apply(3, 7));
        
        // Variation 5: Complex body with multiple statements
        BiFunction<Integer, Integer, String> complexCalc = (a, b) -> {
            int sum = a + b;
            int product = a * b;
            return "Sum: " + sum + ", Product: " + product;
        };
        System.out.println(complexCalc.apply(4, 5));
    }
    
    
    /*
     * ========================================================================
     * SECTION 3: BUILT-IN FUNCTIONAL INTERFACES (Intermediate)
     * ========================================================================
     * 
     * Java provides common functional interfaces in java.util.function:
     * 
     * 1. Predicate<T>
     *    - Takes: T
     *    - Returns: boolean
     *    - Usage: Testing/filtering conditions
     *    - Example: test(object) -> boolean
     * 
     * 2. Consumer<T>
     *    - Takes: T
     *    - Returns: void (nothing)
     *    - Usage: Performing action on object
     *    - Example: accept(object) -> does something
     * 
     * 3. Function<T, R>
     *    - Takes: T (input)
     *    - Returns: R (output of different type)
     *    - Usage: Transform/convert object
     *    - Example: apply(input) -> output
     * 
     * 4. Supplier<T>
     *    - Takes: nothing
     *    - Returns: T
     *    - Usage: Providing/generating values
     *    - Example: get() -> value
     * 
     * 5. BiFunction<T, U, R>
     *    - Takes: T and U (two inputs)
     *    - Returns: R
     *    - Usage: Combining/operating on two values
     *    - Example: apply(input1, input2) -> output
     * 
     * WHY USE THESE?
     * ==============
     * - Already defined, no need to create interfaces
     * - Standardized naming across Java ecosystem
     * - Work seamlessly with Streams and Collections
     * - Immediately recognizable to other developers
     * 
     * ========================================================================
     */
    
    // Example 4: Using built-in functional interfaces
    public static void builtInFunctionalInterfaces() {
        System.out.println("\n=== SECTION 3: Built-In Functional Interfaces ===\n");
        
        // Predicate: Test condition (returns boolean)
        Predicate<Integer> isPositive = n -> n > 0;
        System.out.println("Is 5 positive? " + isPositive.test(5));
        System.out.println("Is -3 positive? " + isPositive.test(-3));
        
        // Consumer: Do something with object (returns void)
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());
        printUpperCase.accept("hello");
        
        // Function: Transform object (returns different type)
        Function<String, Integer> stringLength = str -> str.length();
        System.out.println("Length of 'Java': " + stringLength.apply("Java"));
        
        // Supplier: Generate/provide value (takes nothing)
        Supplier<Integer> randomNumber = () -> (int)(Math.random() * 100);
        System.out.println("Random number: " + randomNumber.get());
        
        // BiFunction: Combine two values
        BiFunction<Integer, Integer, String> combineNumbers = (a, b) -> 
            "Sum: " + (a + b) + ", Product: " + (a * b);
        System.out.println(combineNumbers.apply(3, 4));
    }
    
    
    /*
     * ========================================================================
     * SECTION 4: CAPTURING VARIABLES (Intermediate)
     * ========================================================================
     * 
     * WHAT IS VARIABLE CAPTURE?
     * ==========================
     * Lambda can use variables from the surrounding scope.
     * 
     * IMPORTANT RULE: Variables must be EFFECTIVELY FINAL
     * =====================================================
     * This means:
     * 1. Variable declared as final, OR
     * 2. Variable IS final (never modified after initialization)
     * 
     * WRONG (will not compile):
     * 
     * int x = 5;
     * x = 10;  // x is modified
     * Predicate<Integer> isGreaterThanX = n -> n > x;  // ERROR!
     * 
     * RIGHT:
     * 
     * int x = 5;  // Never modified
     * Predicate<Integer> isGreaterThanX = n -> n > x;  // OK!
     * 
     * WHY THIS RULE?
     * ==============
     * Lambda is converted to a separate method behind the scenes.
     * That method gets a COPY of x, not the original.
     * If x could be modified, which value should lambda see?
     * To avoid confusion, x must be final (never changes).
     * 
     * CAPTURED VARIABLES ARE IMMUTABLE IN LAMBDA:
     * =============================================
     * You can READ captured variables in lambda.
     * You CANNOT MODIFY them in lambda.
     * 
     * ========================================================================
     */
    
    // Example 5: Variable capturing
    public static void variableCapturing() {
        System.out.println("\n=== SECTION 4: Variable Capturing ===\n");
        
        final int threshold = 50;  // Effectively final (declared final)
        
        Predicate<Integer> isAboveThreshold = num -> num > threshold;
        System.out.println("Is 60 above threshold? " + isAboveThreshold.test(60));
        System.out.println("Is 30 above threshold? " + isAboveThreshold.test(30));
        
        // Example with strings
        String prefix = "Mr. ";  // Effectively final (never modified)
        Function<String, String> addPrefix = name -> prefix + name;
        System.out.println(addPrefix.apply("Smith"));
        System.out.println(addPrefix.apply("Johnson"));
        
        // This would NOT compile:
        // int counter = 0;
        // Consumer<String> increment = s -> counter++;  // ERROR! counter is modified
        
        // But this WORKS:
        int[] counter = {0};  // Array is final, but contents can change
        Consumer<String> printWithCounter = s -> System.out.println(counter[0]++ + ": " + s);
        printWithCounter.accept("First");
        printWithCounter.accept("Second");
    }
    
    
    /*
     * ========================================================================
     * SECTION 5: LAMBDAS WITH COLLECTIONS (Practical)
     * ========================================================================
     * 
     * LAMBDAS MAKE COLLECTIONS PROCESSING ELEGANT
     * ============================================
     * 
     * BEFORE LAMBDAS (Java 7):
     * 
     * for (String name : names) {
     *     System.out.println(name);
     * }
     * 
     * AFTER LAMBDAS (Java 8+):
     * 
     * names.forEach(name -> System.out.println(name));
     * 
     * The second is clearer and more concise!
     * 
     * COMMON COLLECTION OPERATIONS WITH LAMBDAS:
     * ===========================================
     * 
     * 1. forEach() - Do something for each element
     * 2. sort()    - Sort with custom comparator
     * 3. removeIf() - Remove elements matching condition
     * 4. replaceAll() - Replace all elements
     * 
     * ========================================================================
     */
    
    // Example 6: Lambdas with Collections
    public static void lambdasWithCollections() {
        System.out.println("\n=== SECTION 5: Lambdas with Collections ===\n");
        
        List<String> fruits = new ArrayList<>(Arrays.asList(
            "Apple", "Banana", "Cherry", "Date", "Elderberry"
        ));
        
        // forEach: Print each fruit
        System.out.println("All fruits:");
        fruits.forEach(fruit -> System.out.println("  - " + fruit));
        
        // sort: Sort using comparator lambda
        System.out.println("\nSorted by length:");
        fruits.sort((f1, f2) -> f1.length() - f2.length());
        fruits.forEach(fruit -> System.out.println("  - " + fruit));
        
        // removeIf: Remove fruits with length > 5
        System.out.println("\nRemoving long names:");
        fruits.removeIf(fruit -> fruit.length() > 5);
        fruits.forEach(fruit -> System.out.println("  - " + fruit));
        
        // replaceAll: Convert to uppercase
        System.out.println("\nAll uppercase:");
        fruits.replaceAll(fruit -> fruit.toUpperCase());
        fruits.forEach(fruit -> System.out.println("  - " + fruit));
    }
    
    
    /*
     * ========================================================================
     * SECTION 6: LAMBDAS WITH STREAMS (Advanced)
     * ========================================================================
     * 
     * WHAT ARE STREAMS?
     * =================
     * A Stream represents a sequence of elements that can be processed functionally.
     * Streams are LAZILY EVALUATED (only computed when needed).
     * Streams are not data structures; they process data!
     * 
     * WHY STREAMS + LAMBDAS?
     * ======================
     * Streams REQUIRE lambdas for filtering, mapping, reducing.
     * Together they enable:
     * - Functional data processing
     * - Declarative "what" instead of imperative "how"
     * - Parallelization with parallelStream()
     * 
     * COMMON STREAM OPERATIONS:
     * ==========================
     * 
     * 1. filter(predicate) - Keep only matching elements
     *    Example: numbers.stream().filter(n -> n > 5)
     * 
     * 2. map(function) - Transform each element
     *    Example: names.stream().map(n -> n.length())
     * 
     * 3. forEach(consumer) - Do action for each element
     *    Example: numbers.stream().forEach(n -> System.out.println(n))
     * 
     * 4. reduce(combiner) - Combine all elements to single value
     *    Example: numbers.stream().reduce((a, b) -> a + b)
     * 
     * 5. collect(collector) - Gather results into collection
     *    Example: numbers.stream().collect(Collectors.toList())
     * 
     * STREAM PIPELINE:
     * ================
     * source -> filter -> map -> forEach
     * 
     * Data flows through operations like pipeline!
     * Each operation is a filter/transformer.
     * 
     * ========================================================================
     */
    
    // Example 7: Lambdas with Streams
    public static void lambdasWithStreams() {
        System.out.println("\n=== SECTION 6: Lambdas with Streams ===\n");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // filter: Keep only even numbers
        System.out.println("Even numbers:");
        numbers.stream()
            .filter(n -> n % 2 == 0)
            .forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        // map: Double each number
        System.out.println("\nDoubled numbers:");
        numbers.stream()
            .map(n -> n * 2)
            .forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        // filter + map combined
        System.out.println("\nEven numbers doubled:");
        numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * 2)
            .forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        // reduce: Sum all numbers
        int sum = numbers.stream()
            .reduce(0, (accumulator, current) -> accumulator + current);
        System.out.println("\nSum of all numbers: " + sum);
        
        // collect: Gather results into new list
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Collected even numbers: " + evenNumbers);
    }
    
    
    /*
     * ========================================================================
     * SECTION 7: LAMBDAS IN MULTITHREADING (IMPORTANT!)
     * ========================================================================
     * 
     * HOW LAMBDAS REVOLUTIONIZED MULTITHREADING IN JAVA
     * ===================================================
     * 
     * BEFORE LAMBDAS (Java 7):
     * 
     * Thread t = new Thread(new Runnable() {
     *     public void run() {
     *         System.out.println("Thread running");
     *     }
     * });
     * t.start();
     * 
     * AFTER LAMBDAS (Java 8+):
     * 
     * Thread t = new Thread(() -> System.out.println("Thread running"));
     * t.start();
     * 
     * MASSIVE REDUCTION in boilerplate code!
     * 
     * WHY LAMBDAS ARE PERFECT FOR MULTITHREADING:
     * ============================================
     * 
     * 1. CLEANER SYNTAX
     *    - Anonymous inner classes are verbose
     *    - Lambda syntax is concise and readable
     *    - Intent emerges immediately
     * 
     * 2. FUNCTIONAL APPROACH
     *    - Threading is about executing code
     *    - Lambdas are code as data
     *    - Natural fit!
     * 
     * 3. WITH EXECUTORS
     *    - ExecutorService.execute() takes Runnable
     *    - Can pass lambda directly
     *    - Much cleaner than anonymous classes
     * 
     * 4. WITH CALLBACKS
     *    - Callbacks are naturally lambda-friendly
     *    - Asynchronous operations become readable
     *    - Exception handling integrates well
     * 
     * 5. VARIABLE CAPTURING
     *    - Can capture data from surrounding context
     *    - Thread gets copy of captured variables
     *    - Easy to pass data to threads
     * 
     * ========================================================================
     */
    
    // Example 8: Lambdas in Multithreading
    public static void lambdasInMultithreading() {
        System.out.println("\n=== SECTION 7: Lambdas in Multithreading ===\n");
        
        // Simple thread with lambda
        System.out.println("Creating threads with lambdas:");
        for (int i = 1; i <= 3; i++) {
            final int threadId = i;
            Thread t = new Thread(() -> {
                System.out.println("Thread " + threadId + " running");
            });
            t.start();
        }
        
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        // Using ExecutorService with lambda
        System.out.println("\nUsing ExecutorService:");
        java.util.concurrent.ExecutorService executor =
            java.util.concurrent.Executors.newFixedThreadPool(3);
        
        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " executed");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Callable with lambda (returns value)
        System.out.println("\nUsing Callable with lambda:");
        java.util.concurrent.ExecutorService executor2 =
            java.util.concurrent.Executors.newFixedThreadPool(2);
        
        java.util.concurrent.Future<Integer> future = executor2.submit(() -> {
            System.out.println("Computing result...");
            Thread.sleep(100);
            return 42;
        });
        
        try {
            int result = future.get();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        executor2.shutdown();
    }
    
    
    /*
     * ========================================================================
     * SECTION 8: LAMBDA BEST PRACTICES (Advanced)
     * ========================================================================
     * 
     * AVOID OVERLY COMPLEX LAMBDAS
     * =============================
     * 
     * WRONG (too complex for lambda):
     * list.forEach(item -> {
     *     int x = calculate(item);
     *     String formatted = String.format(x);
     *     List<String> results = process(formatted);
     *     results.forEach(r -> System.out.println(r));
     * });
     * 
     * RIGHT (extract to method):
     * list.forEach(item -> processItem(item));
     * 
     * private static void processItem(Item item) {
     *     int x = calculate(item);
     *     String formatted = String.format(x);
     *     List<String> results = process(formatted);
     *     results.forEach(r -> System.out.println(r));
     * }
     * 
     * 
     * PREFER STREAMS TO LOOPS
     * ========================
     * 
     * OLD WAY (loops):
     * List<Integer> evens = new ArrayList<>();
     * for (Integer n : numbers) {
     *     if (n % 2 == 0) {
     *         evens.add(n);
     *     }
     * }
     * 
     * NEW WAY (streams):
     * List<Integer> evens = numbers.stream()
     *     .filter(n -> n % 2 == 0)
     *     .collect(Collectors.toList());
     * 
     * 
     * USE METHOD REFERENCES WHEN POSSIBLE
     * =====================================
     * 
     * Instead of:
     * list.forEach(item -> System.out.println(item));
     * 
     * Use:
     * list.forEach(System.out::println);
     * 
     * More concise and equally readable!
     * 
     * 
     * DOCUMENT COMPLEX LAMBDAS
     * =========================
     * 
     * If lambda is complex, add a comment:
     * list.forEach(item -> {
     *     // Filter out inactive items and log them
     *     if (!item.isActive()) {
     *         logger.debug("Skipping inactive item: {}", item.getId());
     *     }
     * });
     * 
     * ========================================================================
     */
    
    // Example 9: Best practices demonstration
    public static void lambdaBestPractices() {
        System.out.println("\n=== SECTION 8: Lambda Best Practices ===\n");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        
        // GOOD: Simple, clear lambda
        names.forEach(name -> System.out.println(name));
        
        // BETTER: Use method reference
        System.out.println("\nUsing method reference:");
        names.forEach(System.out::println);
        
        // GOOD: Chain operations
        System.out.println("\nFiltered and transformed:");
        names.stream()
            .filter(name -> name.length() > 3)
            .map(String::toUpperCase)
            .forEach(System.out::println);
    }
    
    
    /*
     * ========================================================================
     * SECTION 9: COMMON MISTAKES & CONFUSION
     * ========================================================================
     * 
     * MISTAKE 1: Forgetting the -> operator
     * ======================================
     * WRONG: (a, b) { return a + b; }
     * RIGHT: (a, b) -> a + b
     * 
     * 
     * MISTAKE 2: Using return and semicolon with single expression
     * =============================================================
     * WRONG: (a, b) -> { return a + b; }  // Unnecessary braces
     * RIGHT: (a, b) -> a + b              // Just the expression
     * 
     * But if multiple statements:
     * (a, b) -> { return a + b; }  // Braces and return needed
     * 
     * 
     * MISTAKE 3: Modifying captured variables
     * =========================================
     * WRONG:
     * int x = 5;
     * Predicate<Integer> test = n -> {
     *     x++;  // ERROR! Can't modify captured variable
     *     return n > x;
     * };
     * 
     * 
     * MISTAKE 4: Using lambda with wrong functional interface
     * ==========================================================
     * WRONG:
     * public void doSomething(Supplier<String> supplier) {}
     * doSomething((x, y) -> x + y);  // ERROR! Wrong signature
     * 
     * RIGHT:
     * doSomething(() -> "result");
     * 
     * 
     * CONFUSION 1: Lambda parentheses for single parameter
     * ======================================================
     * These are equivalent:
     * x -> x * 2
     * (x) -> x * 2
     * 
     * Both valid! Use whichever you prefer for readability.
     * 
     * 
     * CONFUSION 2: Method reference vs Lambda
     * ========================================
     * These do the same thing:
     * list.forEach(x -> System.out.println(x));
     * list.forEach(System.out::println);
     * 
     * Method reference is shorthand when lambda just calls single method!
     * 
     * 
     * CONFUSION 3: Are lambdas slower?
     * =================================
     * Modern JVMs optimize lambdas very well.
     * Performance is almost identical to anonymous classes.
     * Sometimes lambdas are FASTER due to inlining.
     * So: Don't worry about lambda performance!
     * 
     * 
     * CONFUSION 4: Can lambda access this?
     * =====================================
     * YES! Lambda can access 'this' from enclosing scope.
     * 'this' refers to the enclosing class, not the lambda!
     * 
     * public class MyClass {
     *     private int value = 10;
     *     
     *     public void test() {
     *         Function<Integer, Integer> func = x -> {
     *             return x + this.value;  // Can access this.value!
     *         };
     *     }
     * }
     * 
     * ========================================================================
     */
    
    // Example 10: Common mistakes
    public static void commonMistakes() {
        System.out.println("\n=== SECTION 9: Common Mistakes & Confusion ===\n");
        
        // MISTAKE: Unnecessary braces with single expression
        // WRONG: (a, b) -> { return a + b; }
        // RIGHT:
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("3 + 5 = " + add.apply(3, 5));
        
        // MISTAKE: Using lambda with wrong interface
        // This would be WRONG:
        // Consumer<Integer> wrong = (a, b) -> a + b;  // ERROR!
        
        // RIGHT:
        Consumer<Integer> right = a -> System.out.println("Value: " + a);
        right.accept(42);
        
        // CONFUSION: Single parameter parentheses
        // Both work:
        Function<Integer, Integer> double1 = x -> x * 2;
        Function<Integer, Integer> double2 = (x) -> x * 2;
        System.out.println("Double: " + double1.apply(5) + " or " + double2.apply(5));
    }
    
    
    /*
     * ========================================================================
     * SECTION 10: REAL-WORLD EXAMPLES
     * ========================================================================
     */
    
    // Real-world Example 1: Filtering and sorting users
    public static class User {
        public String name;
        public int age;
        public String city;
        
        public User(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }
        
        @Override
        public String toString() {
            return name + " (" + age + ") from " + city;
        }
    }
    
    public static void realWorldFiltering() {
        System.out.println("\n=== SECTION 10: Real-World Examples ===\n");
        
        List<User> users = Arrays.asList(
            new User("Alice", 25, "New York"),
            new User("Bob", 17, "London"),
            new User("Charlie", 30, "Paris"),
            new User("Diana", 22, "Berlin")
        );
        
        // Filter adults (age >= 18) and sort by name
        System.out.println("Adults sorted by name:");
        users.stream()
            .filter(u -> u.age >= 18)
            .sorted((u1, u2) -> u1.name.compareTo(u2.name))
            .forEach(u -> System.out.println("  - " + u));
        
        // Count users from each city
        System.out.println("\nUsers per city:");
        users.stream()
            .collect(
                java.util.stream.Collectors.groupingBy(
                    u -> u.city,
                    java.util.stream.Collectors.counting()
                )
            )
            .forEach((city, count) -> System.out.println("  " + city + ": " + count));
    }
    
    
    // Real-world Example 2: Async operations with lambdas
    public static void realWorldAsync() throws Exception {
        System.out.println("\nAsynchronous operations with ExecutorService:");
        
        java.util.concurrent.ExecutorService executor =
            java.util.concurrent.Executors.newFixedThreadPool(2);
        
        // Submit tasks that perform different operations
        java.util.concurrent.Future<String> task1 = executor.submit(() -> {
            Thread.sleep(500);
            return "Task 1 completed";
        });
        
        java.util.concurrent.Future<String> task2 = executor.submit(() -> {
            Thread.sleep(300);
            return "Task 2 completed";
        });
        
        System.out.println("  " + task1.get());
        System.out.println("  " + task2.get());
        
        executor.shutdown();
    }
    
    
    // Real-world Example 3: Event handling/callbacks
    public static void realWorldCallbacks() {
        System.out.println("\nEvent callbacks with lambdas:");
        
        @FunctionalInterface
        interface EventListener {
            void onEvent(String message);
        }
        
        class EventManager {
            private EventListener listener;
            
            public void setListener(EventListener listener) {
                this.listener = listener;
            }
            
            public void triggerEvent(String message) {
                if (listener != null) {
                    listener.onEvent(message);
                }
            }
        }
        
        EventManager manager = new EventManager();
        
        // Set callback using lambda!
        manager.setListener(msg -> System.out.println("  Event received: " + msg));
        
        manager.triggerEvent("Button clicked");
        manager.triggerEvent("User logged in");
    }
    
    
    /*
     * ========================================================================
     * SECTION 11: WHEN TO USE LAMBDAS vs METHODS
     * ========================================================================
     * 
     * USE LAMBDAS WHEN:
     * =================
     * 1. Code is very short (1-3 lines)
     * 2. Used in only one place
     * 3. Logic is simple and straightforward
     * 4. Working with functional interfaces
     * 5. Part of stream chains
     * 
     * USE REGULAR METHODS WHEN:
     * ==========================
     * 1. Code is complex (many lines)
     * 2. Used in multiple places
     * 3. Needs its own documentation
     * 4. Has specific name that aids understanding
     * 5. Easy to test separately
     * 6. Needs exception handling
     * 
     * EXAMPLE:
     * 
     * USE LAMBDA:
     * list.forEach(item -> System.out.println(item));
     * 
     * USE METHOD:
     * list.forEach(item -> processComplexLogic(item));
     * 
     * private void processComplexLogic(Item item) {
     *     // Complex logic here...
     * }
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 12: PERFORMANCE CONSIDERATIONS
     * ========================================================================
     * 
     * ARE LAMBDAS SLOWER?
     * ===================
     * Answer: NO! Modern JVMs treat lambdas very efficiently.
     * JVM optimizer:
     * 1. Inlines lambdas at call sites
     * 2. Eliminates allocations when possible
     * 3. Optimizes captured variables
     * 
     * PERFORMANCE COMPARISON:
     * Anonymous class: 100%
     * Lambda (naive):  100%
     * Lambda (optimized by JVM): 95-105% (varies)
     * 
     * Result: No meaningful difference!
     * 
     * WHEN LAMBDAS MIGHT BE SLOWER:
     * =============================
     * 1. Capturing multiple variables from outer scope
     * 2. Creating lambda inside loop (allocation overhead)
     * 3. Very tight loops where every instruction matters
     * 
     * But even then, difference is usually negligible.
     * 
     * OPTIMIZATION TIP:
     * =================
     * Create lambda outside loop:
     * 
     * SLOW:
     * for (int i = 0; i < 1000000; i++) {
     *     stream.filter(x -> x > threshold);  // Lambda created every iteration
     * }
     * 
     * FAST:
     * Predicate<Integer> isBig = x -> x > threshold;  // Created once
     * for (int i = 0; i < 1000000; i++) {
     *     stream.filter(isBig);
     * }
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 13: METHOD REFERENCES (Advanced)
     * ========================================================================
     * 
     * WHAT ARE METHOD REFERENCES?
     * ============================
     * Shorthand syntax for lambdas that call single method.
     * 
     * Instead of:
     * list.forEach(x -> System.out.println(x));
     * 
     * Write:
     * list.forEach(System.out::println);
     * 
     * FOUR TYPES OF METHOD REFERENCES:
     * =================================
     * 
     * 1. Static Method Reference
     *    Class::staticMethod
     *    Example: Integer::parseInt
     * 
     * 2. Instance Method Reference (specific object)
     *    object::method
     *    Example: str::toUpperCase
     * 
     * 3. Instance Method Reference (any object)
     *    Class::method
     *    Example: String::length
     * 
     * 4. Constructor Reference
     *    Class::new
     *    Example: ArrayList::new
     * 
     * EXAMPLES:
     * ==========
     * 
     * Static:
     * list.stream()
     *     .map(Integer::valueOf)
     *     .forEach(System.out::println);
     * 
     * Instance (specific):
     * String prefix = "Mr. ";
     * Function<String, String> addPrefix = prefix::concat;
     * 
     * Instance (any):
     * list.stream()
     *     .map(String::toUpperCase)
     *     .forEach(System.out::println);
     * 
     * Constructor:
     * Stream.generate(ArrayList::new)
     *     .limit(5)
     *     .forEach(list -> System.out.println(list));
     * 
     * ========================================================================
     */
    
    // Example 11: Method references
    public static void methodReferences() {
        System.out.println("\n=== SECTION 13: Method References ===\n");
        
        List<String> words = Arrays.asList("hello", "world", "java");
        
        // Lambda version
        System.out.println("Using lambda:");
        words.stream()
            .map(w -> w.toUpperCase())
            .forEach(w -> System.out.println(w));
        
        // Method reference version (cleaner!)
        System.out.println("\nUsing method reference:");
        words.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);
        
        // Constructor reference
        System.out.println("\nUsing constructor reference:");
        List<String> uppercase = words.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println(uppercase);
    }
    
    
    /*
     * ========================================================================
     * SECTION 14: MULTITHREADING DEEP DIVE
     * ========================================================================
     * 
     * HOW LAMBDAS IMPROVE MULTITHREADING:
     * ====================================
     * 
     * 1. CLEANER THREAD CREATION
     * 
     *    BEFORE:
     *    Thread t = new Thread(new Runnable() {
     *        public void run() {
     *            System.out.println("Running");
     *        }
     *    });
     * 
     *    AFTER:
     *    Thread t = new Thread(() -> System.out.println("Running"));
     * 
     * 
     * 2. BETTER WITH EXECUTORS
     * 
     *    ExecutorService executor = Executors.newFixedThreadPool(5);
     *    for (int i = 0; i < 10; i++) {
     *        final int taskId = i;
     *        executor.execute(() -> {
     *            System.out.println("Task " + taskId);
     *        });
     *    }
     * 
     * 
     * 3. CALLBACKS AND FUTURES
     * 
     *    Future<Integer> future = executor.submit(() -> {
     *        return complexCalculation();
     *    });
     * 
     *    int result = future.get();  // When ready
     * 
     * 
     * 4. SCHEDULING WITH DELAY
     * 
     *    ScheduledExecutorService scheduler =
     *        Executors.newScheduledThreadPool(1);
     *    
     *    scheduler.schedule(() -> {
     *        System.out.println("Delayed task");
     *    }, 5, TimeUnit.SECONDS);
     * 
     * 
     * 5. THREAD-SAFE CALLBACKS
     * 
     *    private Consumer<Result> onComplete = result -> {
     *        synchronized (this) {
     *            processResult(result);
     *        }
     *    };
     * 
     * 
     * 6. PARALLEL STREAMS
     * 
     *    numbers.parallelStream()
     *        .filter(n -> n % 2 == 0)
     *        .forEach(n -> process(n));
     * 
     *    JVM automatically creates thread pool!
     *    No manual threading needed!
     * 
     * 
     * LAMBDA + MULTITHREADING PATTERNS:
     * ==================================
     * 
     * Pattern 1: Simple Background Task
     * 
     *    new Thread(() -> {
     *        System.out.println("Running in background");
     *    }).start();
     * 
     * 
     * Pattern 2: Scheduled Task
     * 
     *    Timer timer = new Timer();
     *    timer.schedule(new TimerTask() {
     *        // Can now be replaced with lambda for Runnable
     *    }, 1000);
     * 
     * 
     * Pattern 3: Thread Pool with Work Queue
     * 
     *    ExecutorService executor = Executors.newFixedThreadPool(10);
     *    
     *    for (WorkItem item : workQueue) {
     *        executor.execute(() -> {
     *            item.process();
     *        });
     *    }
     * 
     * 
     * Pattern 4: Parallel Processing
     * 
     *    largeList.parallelStream()
     *        .map(item -> item.expensiveOperation())
     *        .collect(Collectors.toList());
     * 
     * 
     * Pattern 5: Async Callback
     * 
     *    asyncOperation(() -> {
     *        // Lambda executed when operation completes
     *        System.out.println("Operation done!");
     *    });
     * 
     * ========================================================================
     */
    
    // Example 12: Advanced multithreading with lambdas
    public static void advancedMultithreading() throws Exception {
        System.out.println("\n=== SECTION 14: Multithreading Deep Dive ===\n");
        
        // Pattern: Parallel Stream Processing
        System.out.println("Parallel stream processing:");
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        long startTime = System.currentTimeMillis();
        data.parallelStream()
            .map(n -> {
                // Simulate expensive operation
                try { Thread.sleep(10); } catch (InterruptedException e) {}
                return n * n;
            })
            .forEach(result -> System.out.print(result + " "));
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("\n(Took " + duration + " ms with parallel processing)");
        
        // Pattern: Thread Pool Queue
        System.out.println("\nThread pool processing:");
        java.util.concurrent.ExecutorService executor =
            java.util.concurrent.Executors.newFixedThreadPool(3);
        
        List<String> tasks = Arrays.asList("Task1", "Task2", "Task3", "Task4", "Task5");
        
        tasks.forEach(task -> {
            executor.execute(() -> {
                System.out.println("  " + Thread.currentThread().getName() + " executing " + task);
                try { Thread.sleep(200); } catch (InterruptedException e) {}
            });
        });
        
        executor.shutdown();
        executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
    }
    
    
    /*
     * ========================================================================
     * SECTION 15: BEST PRACTICES CHECKLIST
     * ========================================================================
     * 
     * [✓] Use lambda for simple, single-purpose code
     * [✓] Keep lambdas short (1-3 lines ideal)
     * [✓] Extract complex logic to methods
     * [✓] Use streams instead of loops when possible
     * [✓] Remember captured variables must be effectively final
     * [✓] Prefer method references for single-method calls
     * [✓] Use appropriate functional interface (Predicate, Consumer, etc.)
     * [✓] Don't modify captured variables in lambda
     * [✓] Document complex lambda behavior in comments
     * [✓] Consider performance for tight loops (but usually not an issue)
     * [✓] Use parallelStream() for heavy computations
     * [✓] Handle exceptions properly in lambdas
     * [✓] Test lambda expressions just like regular methods
     * [✓] Don't nest lambdas too deeply
     * [✓] Use meaningful names for lambda parameters
     * 
     * ========================================================================
     */
    
    
    public static void main(String[] args) throws Exception {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("LAMBDA EXPRESSIONS - COMPREHENSIVE GUIDE");
        System.out.println("=".repeat(70));
        
        lambdaBasics();
        lambdaSyntaxVariations();
        builtInFunctionalInterfaces();
        variableCapturing();
        lambdasWithCollections();
        lambdasWithStreams();
        lambdasInMultithreading();
        lambdaBestPractices();
        commonMistakes();
        realWorldFiltering();
        realWorldAsync();
        realWorldCallbacks();
        methodReferences();
        advancedMultithreading();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Guide completed! Lambda expressions are now clear!");
        System.out.println("=".repeat(70));
    }
}
