package me.niteshh.OPPs.tutorial.interfaces.inDepth;

import java.util.*;

/**
 * ============================================================================
 * COMPREHENSIVE MAIN CLASS - INTERFACES IN ACTION
 * ============================================================================
 * 
 * This main class demonstrates ALL interface concepts we've learned
 * Run this to understand interfaces practically
 * 
 * Topics Covered:
 * 1. Basic Interface Implementation
 * 2. Multiple Interface Implementation
 * 3. Interface tutorial
 * 4. Real-world Database Storage Example
 * 5. Polymorphism with Interfaces
 * 6. Common Java Interfaces
 * 7. Interface vs Abstract Class
 * 8. Best Practices
 * 
 * ============================================================================
 */
public class InterfaceMainClass {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       COMPREHENSIVE INTERFACES LEARNING & DEMONSTRATION        ║");
        System.out.println("║         Java | Spring Boot | Cloud Developer - Nitesh         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        // DEMO 1: Basic Interface Implementation
        demo1_BasicInterfaceImplementation();
        
        // DEMO 2: Multiple Interface Implementation
        demo2_MultipleInterfaceImplementation();
        
        // DEMO 3: Interface tutorial
        demo3_InterfaceInheritance();
        
        // DEMO 4: Real-world Storage System
        demo4_RealWorldStorageExample();
        
        // DEMO 5: Polymorphism Power
        demo5_PolymorphismPower();
        
        // DEMO 6: Common Interfaces
        demo6_CommonInterfaces();
        
        // DEMO 7: Design Patterns with Interfaces
        demo7_DesignPatterns();
        
        // DEMO 8: Best Practices
        demo8_BestPractices();
    }
    
    /**
     * ========================================================================
     * DEMO 1: Basic Interface Implementation
     * ========================================================================
     */
    private static void demo1_BasicInterfaceImplementation() {
        System.out.println("\n┌─ DEMO 1: BASIC INTERFACE IMPLEMENTATION ─────────────────────┐");
        
        // Create objects from 02_InterfaceImplementation.java
        Document doc = new Document("Java Guide", "Complete Java Learning Material");
        Report report = new Report("Monthly Report", 50000.00);
        
        System.out.println("│ Printable Interface - Different Implementations");
        doc.print();
        report.print();
        
        System.out.println("│ Saveable Interface - Different Implementations");
        doc.save();
        report.save();
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * DEMO 2: Multiple Interface Implementation
     * ========================================================================
     */
    private static void demo2_MultipleInterfaceImplementation() {
        System.out.println("\n┌─ DEMO 2: MULTIPLE INTERFACE IMPLEMENTATION ─────────────────┐");
        
        // Create resizable circle that implements multiple interfaces
        ResizableCircle circle = new ResizableCircle(5.0);
        
        System.out.println("│ Drawing (from Drawable interface)");
        circle.draw();
        
        System.out.println("│ Filling (from Drawable interface)");
        circle.fill();
        
        System.out.println("│ Resizing (from Resizable interface)");
        circle.resize(2.0);
        
        System.out.println("│ Area calculation using Shape.PI constant");
        System.out.println("│ Area: " + circle.calculateArea() + " square units\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * DEMO 3: Interface tutorial
     * ========================================================================
     */
    private static void demo3_InterfaceInheritance() {
        System.out.println("\n┌─ DEMO 3: INTERFACE INHERITANCE ──────────────────────────────┐");
        
        // Circle implements Drawable which extends Shape
        Circle c = new Circle(7.0);
        
        System.out.println("│ Circle implements Drawable (which extends Shape)");
        System.out.println("│ Must implement: draw(), fill() from Drawable");
        System.out.println("│ Inherits: describe() from Shape\n");
        
        c.draw();
        c.fill();
        c.describe();
        System.out.println("│ Area: " + c.calculateArea() + " square units\n");
        
        // Static method from interface
        System.out.println("│ Static method from Shape interface:");
        Shape.printShapeInfo();
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * DEMO 4: Real-world Storage System Example
     * ========================================================================
     */
    private static void demo4_RealWorldStorageExample() {
        System.out.println("\n┌─ DEMO 4: REAL-WORLD STORAGE SYSTEM ──────────────────────────┐");
        System.out.println("│ Scenario: Save data to different storage backends\n");
        
        // Different storage implementations
        DataStorage dbStorage = new DatabaseStorage();
        DataStorage fileStorage = new FileStorage();
        DataStorage cloudStorage = new CloudStorage();
        
        String userId = "user123";
        String userData = "Nitesh Kumar - Java Developer";
        
        System.out.println("│ Saving to Database:");
        dbStorage.save(userId, userData);
        
        System.out.println("│ Saving to File System:");
        fileStorage.save(userId, userData);
        
        System.out.println("│ Saving to Cloud:");
        cloudStorage.save(userId, userData);
        
        System.out.println("│ Retrieving from Database:");
        String retrieved = dbStorage.retrieve(userId);
        System.out.println("│ Data: " + retrieved + "\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * DEMO 5: Polymorphism Power
     * ========================================================================
     */
    private static void demo5_PolymorphismPower() {
        System.out.println("\n┌─ DEMO 5: POLYMORPHISM POWER ─────────────────────────────────┐");
        System.out.println("│ Same interface, different implementations, single code\n");
        
        // List of different storage types
        List<DataStorage> storages = new ArrayList<>();
        storages.add(new DatabaseStorage());
        storages.add(new FileStorage());
        storages.add(new CloudStorage());
        
        String key = "employee_1";
        String value = "John Doe - Senior Developer";
        
        System.out.println("│ Save to ALL storage types with SINGLE loop:\n");
        
        // Single loop, multiple implementations
        for (DataStorage storage : storages) {
            storage.save(key, value);
        }
        
        System.out.println("│ Retrieve from ALL storage types with SINGLE loop:\n");
        
        for (DataStorage storage : storages) {
            storage.retrieve(key);
        }
        
        System.out.println("│ ✓ This is the POWER of interfaces and polymorphism!");
        System.out.println("│ ✓ Add new storage type without changing this code!\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * DEMO 6: Common Java Interfaces
     * ========================================================================
     */
    private static void demo6_CommonInterfaces() {
        System.out.println("\n┌─ DEMO 6: COMMON JAVA INTERFACES ─────────────────────────────┐");
        
        // 6.1: Comparable Interface (for sorting)
        System.out.println("│ 6.1 - Comparable Interface (Sorting):\n");
        
        List<ComparableEmployee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Alice", 50000));
        employees.add(new Employee(2, "Bob", 45000));
        employees.add(new Employee(3, "Charlie", 60000));
        
        System.out.println("│ Before sorting:");
        for (ComparableEmployee emp : employees) {
            System.out.println("│ " + emp);
        }
        
        Collections.sort(employees); // Uses Comparable
        
        System.out.println("│ After sorting by salary:");
        for (ComparableEmployee emp : employees) {
            System.out.println("│ " + emp);
        }
        
        // 6.2: Iterable Interface (for-each loop)
        System.out.println("\n│ 6.2 - Iterable Interface (For-Each Loop):\n");
        
        CustomList<String> fruits = new CustomList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        
        System.out.println("│ Iterating using for-each loop (requires Iterable):");
        for (String fruit : fruits) {
            System.out.println("│ - " + fruit);
        }
        
        // 6.3: Runnable Interface (Threading)
        System.out.println("\n│ 6.3 - Runnable Interface (Multi-threading):\n");
        
        Task task = new Task("Download File");
        System.out.println("│ Task object:");
        task.run();
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * DEMO 7: Design Patterns with Interfaces
     * ========================================================================
     */
    private static void demo7_DesignPatterns() {
        System.out.println("\n┌─ DEMO 7: DESIGN PATTERNS WITH INTERFACES ────────────────────┐");
        
        // Dependency Injection Pattern
        System.out.println("│ Design Pattern: Dependency Injection\n");
        
        System.out.println("│ Creating service with Console Logger:");
        GOOD_Service service1 = new GOOD_Service(new ConsoleLoggerBP());
        service1.doSomething();
        
        System.out.println("│ Creating service with File Logger:");
        GOOD_Service service2 = new GOOD_Service(new FileLoggerBP());
        service2.doSomething();
        
        System.out.println("│ ✓ Same Service class, different Logger implementations!");
        System.out.println("│ ✓ Loose coupling achieved!\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * DEMO 8: Best Practices
     * ========================================================================
     */
    private static void demo8_BestPractices() {
        System.out.println("\n┌─ DEMO 8: BEST PRACTICES ──────────────────────────────────────┐");
        
        // Functional Interface with Lambda
        System.out.println("│ Best Practice 1: Functional Interface with Lambda\n");
        
        Operation add = (a, b) -> a + b;
        Operation multiply = (a, b) -> a * b;
        
        System.out.println("│ Lambda Expression: (a, b) -> a + b");
        System.out.println("│ Result: " + add.execute(10, 5) + "\n");
        
        System.out.println("│ Lambda Expression: (a, b) -> a * b");
        System.out.println("│ Result: " + multiply.execute(10, 5) + "\n");
        
        // Interface Segregation
        System.out.println("│ Best Practice 2: Interface Segregation Principle\n");
        
        Document doc = new Document("Report", "Monthly Sales");
        
        System.out.println("│ Document implements Saveable interface:");
        doc.save();
        
        System.out.println("│ Document implements Printable interface:");
        doc.print();
        
        System.out.println("│ ✓ Not forced to implement unneeded methods!\n");
        
        System.out.println("└─────────────────────────────────────────────────────────────────┘");
    }
    
    /**
     * ========================================================================
     * SUMMARY & KEY TAKEAWAYS
     * ========================================================================
     */
    static {
        // Print summary at the end
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║                    KEY TAKEAWAYS                               ║");
            System.out.println("╠════════════════════════════════════════════════════════════════╣");
            System.out.println("║ ✓ Interfaces define CONTRACTS, not implementation            ║");
            System.out.println("║ ✓ Classes implement multiple interfaces (multiple inheritance)║");
            System.out.println("║ ✓ Interfaces enable POLYMORPHISM                             ║");
            System.out.println("║ ✓ Program to INTERFACE, not implementation                   ║");
            System.out.println("║ ✓ Loose coupling = Better code maintenance                   ║");
            System.out.println("║ ✓ Use IS-A for Abstract Class, CAN-DO for Interface          ║");
            System.out.println("║ ✓ Keep interfaces FOCUSED (Interface Segregation)            ║");
            System.out.println("║ ✓ Use Dependency Injection for loose coupling                ║");
            System.out.println("║ ✓ Functional interfaces enable Lambda expressions            ║");
            System.out.println("║ ✓ Interfaces are foundation of SOLID principles              ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        }));
    }
}

/**
 * ============================================================================
 * QUICK REFERENCE: HOW TO RUN THIS PROJECT
 * ============================================================================
 * 
 * Step 1: Understand conceptually (read files in order)
 *    1. Read 01_BasicInterfaceExplanation.java - Understand WHY
 *    2. Read 02_InterfaceImplementation.java - Simple examples
 *    3. Read 03_InterfaceInheritance.java - Advanced concepts
 *    4. Read 04_RealWorldExample.java - Practical usage
 *    5. Read 05_CommonInterfaces.java - Standard interfaces
 *    6. Read 06_InterfaceVsAbstractClass.java - Decision making
 *    7. Read 07_BestPractices.java - Professional coding
 * 
 * Step 2: Run the code
 *    Execute: java InterfaceMainClass
 * 
 * Step 3: Modify and Experiment
 *    - Create new storage type (e.g., MemoryStorage)
 *    - Add new operations to existing interfaces
 *    - Implement multiple interfaces in new classes
 *    - Try different design patterns
 * 
 * ============================================================================
 * REAL-WORLD USAGE IN INDUSTRY
 * ============================================================================
 * 
 * In Spring Boot applications:
 * - Repository interfaces define data access contracts
 * - Service interfaces define business logic contracts
 * - Controller interfaces define API contracts
 * - Interfaces make dependency injection possible
 * 
 * In Microservices:
 * - Event interfaces define message contracts
 * - API interfaces define service contracts
 * - Strategy interfaces enable loose coupling
 * 
 * In Cloud Applications (AWS, Azure, GCP):
 * - Storage interfaces for different backends
 * - Payment interfaces for different processors
 * - Notification interfaces for different channels
 * 
 * ============================================================================
 */

