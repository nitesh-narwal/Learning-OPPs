package me.niteshh.OPPs.Inheritance.innerClasses;


/**
 * ════════════════════════════════════════════════════════════════════════════
 * INNER CLASSES MASTER CLASS - COMPLETE DEMONSTRATION
 * ════════════════════════════════════════════════════════════════════════════
 * 
 * This is the main class that demonstrates all types of inner classes
 * Run this file to see all concepts in action!
 * 
 * STRUCTURE:
 * - Menu-driven interface for exploring each concept
 * - Live examples of all inner class types
 * - Side-by-side comparisons
 * - Complete output for learning
 */

public class InnerClassesMain {

    // ==================== SEPARATOR FOR CLEAN OUTPUT ====================
    
    private static void printSection(String title) {
        System.out.println("\n" + "█".repeat(80));
        System.out.println("█ " + title);
        System.out.println("█".repeat(80) + "\n");
    }
    
    private static void printSubSection(String title) {
        System.out.println("\n" + "▓".repeat(80));
        System.out.println("▓ " + title);
        System.out.println("▓".repeat(80) + "\n");
    }
    
    // ==================== MAIN METHOD ====================
    
    public static void main(String[] args) {
        
        System.out.println("""
            ╔════════════════════════════════════════════════════════════════════════════╗
            ║                                                                            ║
            ║           🚀 INNER CLASSES IN JAVA - COMPLETE LEARNING GUIDE 🚀            ║
            ║                                                                            ║
            ║              Master all types of inner classes with examples               ║
            ║                                                                            ║
            ╚════════════════════════════════════════════════════════════════════════════╝
            """);
        
        // Run all demonstrations
        demonstrateMemberInnerClass();
        demonstrateStaticInnerClass();
        demonstrateLocalInnerClass();
        demonstrateAnonymousInnerClass();
        compareAnonymousVsLambda();
        demonstrateAdvancedPatterns();
        
        // Final summary
        printFinalSummary();
    }
    
    // ==================== DEMONSTRATION 1: MEMBER INNER CLASS ====================
    
    private static void demonstrateMemberInnerClass() {
        
        printSection("01: MEMBER INNER CLASS");
        
        System.out.println("""
            📌 WHAT IS A MEMBER INNER CLASS?
            ├─ A class defined inside another class (without static)
            ├─ Requires an instance of outer class to create instance
            ├─ Can access ALL members of outer class (private, public, etc.)
            └─ Each inner class instance holds reference to outer instance
            
            🎯 REAL-WORLD ANALOGY:
            └─ Car (outer) → Engine (inner class) 
               You cannot have an Engine without a Car!
            """);
        
        // Create outer class instance
        Step1_MemberInnerClass car = new Step1_MemberInnerClass();
        
        // Display car information
        car.displayCarInfo();
        
        // Create member inner class instance
        System.out.println("\n📍 Creating Engine (member inner class):");
        Step1_MemberInnerClass.Engine engine = car.new Engine();
        engine.startEngine();
        
        // Create another member inner class
        System.out.println("\n📍 Creating Radio System (another member inner class):");
        Step1_MemberInnerClass.RadioSystem radio = car.new RadioSystem();
        radio.turnOn();
        radio.setVolume(75);
        radio.showStatus();
        radio.turnOff();
        
        System.out.println("\n✅ KEY TAKEAWAY:");
        System.out.println("   ├─ Syntax: OuterClass outerObj = new OuterClass();");
        System.out.println("   ├─ Syntax: OuterClass.InnerClass inner = outerObj.new InnerClass();");
        System.out.println("   └─ Use when: Inner class needs outer class state/data");
    }
    
    // ==================== DEMONSTRATION 2: STATIC INNER CLASS ====================
    
    private static void demonstrateStaticInnerClass() {
        
        printSection("02: STATIC INNER CLASS");
        
        System.out.println("""
            📌 WHAT IS A STATIC INNER CLASS?
            ├─ A class defined inside another class (with static)
            ├─ Does NOT require outer class instance
            ├─ Can ONLY access static members of outer class
            └─ No implicit reference to outer instance (more memory efficient)
            
            🎯 REAL-WORLD ANALOGY:
            └─ Car Company → Car Blueprint
               You don't need a Car Company to see the Blueprint!
            """);
        
        // No need to create outer class instance!
        // Create static inner class directly
        
        System.out.println("📍 Creating Car Specification (static inner class):");
        System.out.println("   Note: No need for outer class instance!\n");
        
        Step2_StaticInnerClass.CarSpecification spec1 = 
            new Step2_StaticInnerClass.CarSpecification("BMW M5", "Petrol", 4400);
        spec1.displaySpecification();
        
        Step2_StaticInnerClass.CarSpecification spec2 = 
            new Step2_StaticInnerClass.CarSpecification("Tesla Model S", "Electric", 0);
        spec2.displaySpecification();
        
        System.out.println("\n📍 Creating Engine Specification (static inner class):");
        Step2_StaticInnerClass.EngineSpecification engineSpec = 
            new Step2_StaticInnerClass.EngineSpecification("V8 Twin Turbo", 6500, 750);
        engineSpec.displayEngineSpec();
        
        // Accessing static method of static inner class
        System.out.println("\n📍 Accessing static members:");
        Step2_StaticInnerClass.CarSpecification.displayManufacturingStandard();
        
        System.out.println("\n✅ KEY TAKEAWAY:");
        System.out.println("   ├─ Syntax: OuterClass.StaticInnerClass obj = new OuterClass.StaticInnerClass();");
        System.out.println("   ├─ NO outer class instance needed!");
        System.out.println("   └─ Use when: Inner class doesn't need outer class state");
    }
    
    // ==================== DEMONSTRATION 3: LOCAL INNER CLASS ====================
    
    private static void demonstrateLocalInnerClass() {
        
        printSection("03: LOCAL INNER CLASS");
        
        System.out.println("""
            📌 WHAT IS A LOCAL INNER CLASS?
            ├─ A class defined INSIDE A METHOD
            ├─ Only visible within that method
            ├─ Can access final or effectively final local variables
            └─ Dies when method execution ends
            
            🎯 REAL-WORLD ANALOGY:
            └─ Temporary Workshop inside Garage (method)
               Created when entering garage, disappears when leaving!
            """);
        
        Step3_LocalInnerClass factory = new Step3_LocalInnerClass();
        
        System.out.println("📍 Calling method containing local inner class:");
        factory.buildCar("BMW X5");
        
        System.out.println("\n📍 Another example - Service Center:");
        factory.createServiceCenter("Mumbai");
        
        System.out.println("\n📍 Advanced example - Local inner class with interface:");
        factory.demonstrateLocalInnerWithInterface();
        
        System.out.println("\n✅ KEY TAKEAWAY:");
        System.out.println("   ├─ Local variables used must be final or effectively final");
        System.out.println("   ├─ Scope limited to enclosing method");
        System.out.println("   └─ Use when: Behavior specific to one method only");
    }
    
    // ==================== DEMONSTRATION 4: ANONYMOUS INNER CLASS ====================
    
    private static void demonstrateAnonymousInnerClass() {
        
        printSection("04: ANONYMOUS INNER CLASS");
        
        System.out.println("""
            📌 WHAT IS AN ANONYMOUS INNER CLASS?
            ├─ A class WITHOUT A NAME
            ├─ Created and instantiated in one expression
            ├─ Usually implements interface or extends class
            └─ Used for one-time, simple implementations
            
            🎯 REAL-WORLD ANALOGY:
            └─ Hiring a temporary contractor (no permanent record/name)
               for a one-time project!
            """);
        
        Step4_AnonymousInnerClass anonymousDemo = new Step4_AnonymousInnerClass();
        
        System.out.println("📍 EXAMPLE 1 - Basic Anonymous Class:");
        anonymousDemo.demonstrateBasicAnonymousClass();
        
        System.out.println("\n📍 EXAMPLE 2 - Anonymous Class with State:");
        anonymousDemo.demonstrateAnonymousWithState();
        
        System.out.println("\n📍 EXAMPLE 3 - Anonymous Class with Initializer Block:");
        anonymousDemo.demonstrateAnonymousWithInitializer();
        
        System.out.println("\n📍 EXAMPLE 4 - Anonymous Class as Method Parameter:");
        anonymousDemo.demonstrateAnonymousAsParameter();
        
        System.out.println("\n📍 EXAMPLE 5 - Anonymous Class Extending Abstract Class:");
        anonymousDemo.demonstrateAnonymousExtendingClass();
        
        System.out.println("\n✅ KEY TAKEAWAY:");
        System.out.println("   ├─ Perfect for event listeners and callbacks");
        System.out.println("   ├─ Reduces code compared to separate class definition");
        System.out.println("   └─ Use when: Simple, one-time implementation needed");
    }
    
    // ==================== DEMONSTRATION 5: ANONYMOUS VS LAMBDA ====================
    
    private static void compareAnonymousVsLambda() {
        
        printSection("05: ANONYMOUS CLASS VS LAMBDA EXPRESSIONS");
        
        System.out.println("""
            📌 BOTH ARE USED FOR FUNCTIONAL INTERFACES
            ├─ Functional Interface = Interface with exactly 1 abstract method
            ├─ Anonymous Class: Traditional way (Java 5+)
            ├─ Lambda: Modern way (Java 8+)
            └─ Lambda is cleaner and more concise
            """);
        
        Step5_AnonymousVsLambda comparisonDemo = new Step5_AnonymousVsLambda();
        
        comparisonDemo.compareSimpleOperation();
        comparisonDemo.compareMultipleStatements();
        comparisonDemo.practicalExamples();
        comparisonDemo.builtInInterfacesComparison();
        comparisonDemo.decisionGuide();
    }
    
    // ==================== DEMONSTRATION 6: ADVANCED PATTERNS ====================
    
    private static void demonstrateAdvancedPatterns() {
        
        printSection("06: ADVANCED PATTERNS WITH INNER CLASSES");
        
        System.out.println("""
            📌 REAL-WORLD DESIGN PATTERNS
            ├─ Builder Pattern: Complex object construction
            ├─ Factory Pattern: Encapsulated object creation
            ├─ Strategy Pattern: Algorithm selection at runtime
            ├─ Wrapper Pattern: Security and access control
            └─ Nested Interfaces: Logical grouping
            """);
        
        // ===== PATTERN 1: BUILDER =====
        printSubSection("PATTERN 1: BUILDER - Complex Object Construction");
        
        System.out.println("Creating cars using Builder Pattern:\n");
        
        Step6_AdvancedPatterns.Car car1 = new Step6_AdvancedPatterns.Car.CarBuilder("BMW", "M5")
                .color("Red")
                .year(2026)
                .fuelType("Petrol")
                .engineHP(500)
                .hasABS(true)
                .hasNavigation(true)
                .build();
        
        System.out.println(car1);
        
        Step6_AdvancedPatterns.Car car2 = new Step6_AdvancedPatterns.Car.CarBuilder("Tesla", "Model S")
                .color("White")
                .fuelType("Electric")
                .engineHP(600)
                .build();
        
        System.out.println("\n" + car2);
        
        // ===== PATTERN 2: FACTORY =====
        printSubSection("PATTERN 2: FACTORY - Encapsulated Object Creation");
        
        System.out.println("Creating database connections using Factory Pattern:\n");
        
        Step6_AdvancedPatterns.DatabaseConnection mysqlConn = 
            Step6_AdvancedPatterns.DatabaseConnection.ConnectionFactory
                .createMySQLConnection("localhost", "smartcar_db");
        mysqlConn.connect();
        
        Step6_AdvancedPatterns.DatabaseConnection pgConn = 
            Step6_AdvancedPatterns.DatabaseConnection.ConnectionFactory
                .createPostgreSQLConnection("cloud.server.com", "analytics_db");
        pgConn.connect();
        
        Step6_AdvancedPatterns.DatabaseConnection mongoConn = 
            Step6_AdvancedPatterns.DatabaseConnection.ConnectionFactory
                .createMongoDBConnection("mongo.cloud.com", 27017);
        mongoConn.connect();
        
        // ===== PATTERN 3: STRATEGY =====
        printSubSection("PATTERN 3: STRATEGY - Algorithm Selection at Runtime");
        
        System.out.println("Processing payments with different strategies:\n");
        
        Step6_AdvancedPatterns advancedDemo = new Step6_AdvancedPatterns();
        Step6_AdvancedPatterns.PaymentProcessor processor = advancedDemo.new PaymentProcessor();
        
        // Strategy 1: Credit Card
        processor.setPaymentStrategy(processor.new CreditCardPayment("1234567812345678", "Nitesh Kumar"));
        processor.processPayment(50000);
        
        System.out.println();
        
        // Strategy 2: Digital Wallet
        processor.setPaymentStrategy(processor.new DigitalWalletPayment("WALLET_001", 100000));
        processor.processPayment(30000);
        processor.processPayment(80000);  // Will fail - insufficient balance
        
        // ===== PATTERN 4: WRAPPER =====
        printSubSection("PATTERN 4: WRAPPER - Security and Access Control");
        
        System.out.println("Creating secure data with access control:\n");
        
        Step6_AdvancedPatterns.SecureData adminData = 
            Step6_AdvancedPatterns.SecureData.SecureWrapper.createAdminData("Confidential Database Credentials");
        Step6_AdvancedPatterns.SecureData userData = 
            Step6_AdvancedPatterns.SecureData.SecureWrapper.createUserData("User Profile Information");
        
        System.out.println("Admin accessing admin data: " + adminData.getData("ADMIN"));
        System.out.println("Admin accessing user data: " + adminData.getData("ADMIN"));
        System.out.println("User accessing user data: " + userData.getData("USER"));
        System.out.println("User accessing admin data: " + adminData.getData("USER"));
        
        // ===== PATTERN 5: NESTED INTERFACES =====
        printSubSection("PATTERN 5: NESTED INTERFACES - Logical Grouping");
        
        System.out.println("Smart car with nested interface implementation:\n");
        
        Step6_AdvancedPatterns.VehicleSystem.SmartCar smartCar = 
            new Step6_AdvancedPatterns.VehicleSystem.SmartCar("Tesla Model X");
        
        System.out.println("Security Operations:");
        smartCar.lock();
        smartCar.enableAlarm();
        smartCar.unlock();
        
        System.out.println("\nPerformance Operations:");
        smartCar.setGear("Drive");
        smartCar.accelerate();
        smartCar.brake();
    }
    
    // ==================== FINAL SUMMARY ====================
    
    private static void printFinalSummary() {
        
        printSection("📚 COMPLETE INNER CLASSES REFERENCE GUIDE");
        
        System.out.println("""
            ┌─────────────────────────────────────────────────────────────────────────┐
            │ TYPES OF INNER CLASSES - QUICK REFERENCE                               │
            ├─────────────────────────────────────────────────────────────────────────┤
            │                                                                          │
            │ 1️⃣  MEMBER INNER CLASS (Non-static)                                    │
            │     • Requires outer class instance                                     │
            │     • Syntax: Outer.Inner inner = outer.new Inner()                   │
            │     • Access: ALL members of outer class                                │
            │     • Use: When inner needs outer state                                 │
            │                                                                          │
            │ 2️⃣  STATIC INNER CLASS                                                │
            │     • No outer instance needed                                          │
            │     • Syntax: Outer.Inner inner = new Outer.Inner()                   │
            │     • Access: Only STATIC members of outer class                        │
            │     • Use: Utility/Factory classes, DTOs, Builders                      │
            │                                                                          │
            │ 3️⃣  LOCAL INNER CLASS (Method-scoped)                                 │
            │     • Defined inside a method                                           │
            │     • Only visible within that method                                   │
            │     • Access: final/effectively final variables                         │
            │     • Use: Method-specific, temporary logic                             │
            │                                                                          │
            │ 4️⃣  ANONYMOUS INNER CLASS                                             │
            │     • No class name - defined inline                                    │
            │     • Single instantiation                                              │
            │     • Syntax: new Interface() { implementation }                       │
            │     • Use: One-time interface implementations, listeners                │
            │                                                                          │
            │ 5️⃣  LAMBDA EXPRESSIONS (Java 8+)                                      │
            │     • For functional interfaces (1 abstract method)                    │
            │     • Syntax: (params) -> { body }                                     │
            │     • More concise than anonymous classes                               │
            │     • Use: When you prefer modern syntax                                │
            │                                                                          │
            └─────────────────────────────────────────────────────────────────────────┘
            
            ┌─────────────────────────────────────────────────────────────────────────┐
            │ DESIGN PATTERNS USING INNER CLASSES                                     │
            ├─────────────────────────────────────────────────────────────────────────┤
            │ • Builder Pattern         → Use Static Inner Class                      │
            │ • Factory Pattern         → Use Static Inner Class                      │
            │ • Strategy Pattern        → Use Member/Anonymous Inner Class           │
            │ • Decorator/Wrapper       → Use Static Inner Class                      │
            │ • Nested Interfaces       → Use Static Inner Interface                  │
            └─────────────────────────────────────────────────────────────────────────┘
            
            ┌─────────────────────────────────────────────────────────────────────────┐
            │ MEMORY & PERFORMANCE IMPLICATIONS                                       │
            ├─────────────────────────────────────────────────────────────────────────┤
            │ • Member Inner:     Reference overhead (can cause memory leaks)         │
            │ • Static Inner:     No reference overhead (more efficient)              │
            │ • Local Inner:      Scope-limited (cleaned up after method)             │
            │ • Anonymous:        Extra .class files created (OuterClass$1.class)    │
            │ • Lambda:           Uses invokedynamic (more efficient)                 │
            └─────────────────────────────────────────────────────────────────────────┘
            
            ┌─────────────────────────────────────────────────────────────────────────┐
            │ BEST PRACTICES                                                          │
            ├─────────────────────────────────────────────────────────────────────────┤
            │ ✓ Prefer static inner classes (less memory overhead)                    │
            │ ✓ Use member inner only when you need outer state                      │
            │ ✓ Use lambdas instead of anonymous classes (Java 8+)                   │
            │ ✓ Keep inner classes small and focused                                 │
            │ ✓ Clear naming convention (OuterClass$InnerClass)                      │
            │ ✓ Avoid deep nesting (more than 2 levels)                              │
            │ ✓ Use for logical grouping, not just hiding code                       │
            └─────────────────────────────────────────────────────────────────────────┘
            """);
        
        System.out.println("\n" + "═".repeat(80));
        System.out.println("🎉 CONGRATULATIONS! You've learned all about Inner Classes in Java!");
        System.out.println("═".repeat(80) + "\n");
        
        System.out.println("📖 FILES TO REVIEW:");
        System.out.println("   01. Step1_MemberInnerClass.java      - Member inner classes");
        System.out.println("   02. Step2_StaticInnerClass.java      - Static inner classes");
        System.out.println("   03. Step3_LocalInnerClass.java       - Local inner classes");
        System.out.println("   04. Step4_AnonymousInnerClass.java   - Anonymous classes");
        System.out.println("   05. Step5_AnonymousVsLambda.java     - Lambda comparisons");
        System.out.println("   06. Step6_AdvancedPatterns.java      - Design patterns");
        System.out.println("   07. InnerClassesMain.java            - This file!\n");
        
        System.out.println("💡 NEXT STEPS:");
        System.out.println("   • Review each file in numerical order");
        System.out.println("   • Run this main class to see all examples");
        System.out.println("   • Try modifying the code to experiment");
        System.out.println("   • Use these patterns in your own projects\n");
        
        System.out.println("❓ QUICK DECISION TREE:");
        System.out.println("   Need access to outer class instance?");
        System.out.println("   ├─ YES → Use Member Inner Class");
        System.out.println("   └─ NO  → Use Static Inner Class");
        System.out.println();
        System.out.println("   Need complex object creation?");
        System.out.println("   └─ YES → Use Builder Pattern (Static Inner Class)");
        System.out.println();
        System.out.println("   One-time interface implementation?");
        System.out.println("   ├─ Simple logic → Use Lambda");
        System.out.println("   └─ Complex logic → Use Anonymous Class\n");
    }
}

// ════════════════════════════════════════════════════════════════════════════════
// QUICK COMPILATION & EXECUTION GUIDE
// ════════════════════════════════════════════════════════════════════════════════
/*
 * COMPILATION:
 * $ javac *.java
 * 
 * EXECUTION:
 * $ java InnerClassesMain
 * 
 * OUTPUT:
 * You'll see a comprehensive demonstration of all inner class types!
 * 
 * UNDERSTANDING THE OUTPUT:
 * - Each section clearly shows the concept being demonstrated
 * - Real-world examples are provided for each pattern
 * - Side-by-side comparisons help understanding
 * - Summary sections reinforce learning
 * 
 * PRACTICE EXERCISES:
 * 1. Create a custom member inner class for a custom outer class
 * 2. Implement builder pattern for your own complex object
 * 3. Write strategy pattern examples for different algorithms
 * 4. Convert anonymous classes to lambdas
 * 5. Identify where each inner class type fits in your code
 */

