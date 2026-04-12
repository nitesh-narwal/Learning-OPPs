package me.niteshh.OPPs.Inheritance.innerClasses;

/**
 * STEP 4: ANONYMOUS INNER CLASS
 * 
 * An anonymous inner class is a class without a name, defined and instantiated in a single expression.
 * It's typically used to implement an interface or extend a class with a specific behavior.
 * 
 * KEY POINTS:
 * ✓ No class name - hence "anonymous"
 * ✓ Created and instantiated in one step
 * ✓ Usually implements an interface or extends a class
 * ✓ Can only be used once
 * ✓ Useful for short, simple implementations
 * ✓ Can access final or effectively final variables
 * 
 * REAL-WORLD ANALOGY:
 * Like hiring a temporary contractor for a one-time project
 * instead of hiring a permanent employee.
 * You don't even need to give them a formal contract (name).
 */

public class Step4_AnonymousInnerClass {

    private String appName = "Smart Car System";
    
    // ==================== INTERFACES FOR DEMONSTRATION ====================
    
    /**
     * Simple interface for vehicle actions
     */
    interface VehicleAction {
        void performAction();
    }
    
    /**
     * Interface with multiple methods
     */
    interface Engine {
        void start();
        void stop();
        void accelerate();
    }
    
    /**
     * Listener interface (common pattern)
     */
    interface EventListener {
        void onEvent(String message);
    }
    
    // ==================== METHOD 1: BASIC ANONYMOUS CLASS ====================
    
    /**
     * STEP 4.1: Basic anonymous inner class implementing an interface
     */
    public void demonstrateBasicAnonymousClass() {
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANONYMOUS INNER CLASS - BASIC EXAMPLE");
        System.out.println("=".repeat(50));
        
        // Anonymous class implementing VehicleAction interface
        // Syntax: new InterfaceName() { implementation }
        VehicleAction action = new VehicleAction() {
            
            // No class name!
            // Must implement all abstract methods of interface
            @Override
            public void performAction() {
                System.out.println("🚗 Anonymous class performing: Car locked!");
            }
        };
        
        // Call the method
        action.performAction();
    }
    
    // ==================== METHOD 2: ANONYMOUS CLASS WITH STATE ====================
    
    /**
     * STEP 4.2: Anonymous inner class with variables
     */
    public void demonstrateAnonymousWithState() {
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANONYMOUS CLASS - WITH STATE");
        System.out.println("=".repeat(50));
        
        // Access to effectively final variable
        final int engineHP = 350;
        
        // Anonymous class with variables and multiple methods
        Engine engine = new Engine() {
            
            // Can have variables
            private boolean isRunning = false;
            private int currentRPM = 0;
            
            @Override
            public void start() {
                isRunning = true;
                currentRPM = 1000;
                System.out.println("🔧 Engine started! RPM: " + currentRPM);
                System.out.println("   Max HP available: " + engineHP);
            }
            
            @Override
            public void stop() {
                isRunning = false;
                currentRPM = 0;
                System.out.println("🛑 Engine stopped!");
            }
            
            @Override
            public void accelerate() {
                if (isRunning) {
                    currentRPM += 500;
                    System.out.println("⚡ Accelerating... RPM: " + currentRPM);
                    
                    // Access appName from outer class
                    System.out.println("   [" + appName + "]");
                } else {
                    System.out.println("❌ Engine is not running!");
                }
            }
        };
        
        // Use the anonymous class
        engine.start();
        engine.accelerate();
        engine.accelerate();
        engine.stop();
    }
    
    // ==================== METHOD 3: ANONYMOUS CLASS WITH CONSTRUCTOR-LIKE BEHAVIOR ====================
    
    /**
     * STEP 4.3: Anonymous class with instance initializer block
     * (Works like a constructor)
     */
    public void demonstrateAnonymousWithInitializer() {
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANONYMOUS CLASS - WITH INITIALIZER BLOCK");
        System.out.println("=".repeat(50));
        
        final String carModel = "BMW M5";
        final int productionYear = 2026;
        
        VehicleAction action = new VehicleAction() {
            
            // Instance variables
            private String model;
            private int year;
            
            // Initializer block (runs when object is created)
            // Acts like constructor
            {
                this.model = carModel;
                this.year = productionYear;
                System.out.println("✅ Initializer block executed");
                System.out.println("   Model: " + model);
                System.out.println("   Year: " + year);
            }
            
            @Override
            public void performAction() {
                System.out.println("📍 Performing action on: " + model + " (" + year + ")");
            }
        };
        
        action.performAction();
    }
    
    // ==================== METHOD 4: PASSING ANONYMOUS CLASS AS PARAMETER ====================
    
    /**
     * Method that accepts EventListener parameter
     */
    private void setEventListener(EventListener listener) {
        System.out.println("\nEvent listener registered!");
        listener.onEvent("System initialized");
        listener.onEvent("All systems operational");
    }
    
    /**
     * STEP 4.4: Passing anonymous class as method parameter
     */
    public void demonstrateAnonymousAsParameter() {
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANONYMOUS CLASS - AS METHOD PARAMETER");
        System.out.println("=".repeat(50));
        
        // Pass anonymous class as parameter
        setEventListener(new EventListener() {
            
            @Override
            public void onEvent(String message) {
                System.out.println("📢 Event: " + message);
            }
        });
    }
    
    // ==================== METHOD 5: ANONYMOUS CLASS EXTENDING A CLASS ====================
    
    /**
     * Regular class to be extended
     */
    abstract class ConfigurationManager {
        abstract void loadConfig();
        abstract String getConfigName();
    }
    
    /**
     * STEP 4.5: Anonymous class extending an abstract class
     */
    public void demonstrateAnonymousExtendingClass() {
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANONYMOUS CLASS - EXTENDING A CLASS");
        System.out.println("=".repeat(50));
        
        // Anonymous class extending abstract class
        // Syntax: new AbstractClassName() { implementation }
        ConfigurationManager configManager = new ConfigurationManager() {
            
            private String configName = "ProductionConfig";
            
            @Override
            void loadConfig() {
                System.out.println("⚙️  Loading configuration: " + configName);
                System.out.println("   Database: PostgreSQL");
                System.out.println("   Cache: Redis");
                System.out.println("   Cloud: AWS");
            }
            
            @Override
            String getConfigName() {
                return configName;
            }
        };
        
        configManager.loadConfig();
        System.out.println("   Config Name: " + configManager.getConfigName());
    }
}

// ==================== SUMMARY OF ANONYMOUS INNER CLASS ====================
/*
 * CHARACTERISTICS:
 * 1. Has no class name
 * 2. Created and instantiated in a single expression
 * 3. Must implement all abstract methods of interface/abstract class
 * 4. Scope is the variable it's assigned to
 * 5. Can have instance variables, methods, and initializer blocks
 * 6. Cannot have static members (except static final)
 * 7. Cannot have constructors, but can use initializer blocks
 * 
 * SYNTAX PATTERNS:
 * 
 * 1. Implementing Interface:
 *    InterfaceName obj = new InterfaceName() {
 *        @Override
 *        public void method() { ... }
 *    };
 * 
 * 2. Extending Class:
 *    ClassName obj = new ClassName() {
 *        @Override
 *        public void method() { ... }
 *    };
 * 
 * 3. With Initialization:
 *    InterfaceName obj = new InterfaceName() {
 *        private int value;
 *        {
 *            value = 10;  // Initializer block
 *        }
 *        @Override
 *        public void method() { value++; }
 *    };
 * 
 * 4. As Method Parameter:
 *    someMethod(new InterfaceName() {
 *        @Override
 *        public void method() { ... }
 *    });
 * 
 * VARIABLE ACCESS:
 * - Can access final or effectively final variables
 * - Can access outer class instance members
 * - Cannot access non-final local variables
 * 
 * USE CASES:
 * ✓ Event listeners/callbacks in GUI programming
 * ✓ Thread creation: new Thread(() -> { ... }).start()
 * ✓ One-time implementation of interface
 * ✓ Comparators: Collections.sort(list, new Comparator() { ... })
 * ✓ Exception handling with specific behavior
 * ✓ Adapter pattern implementation
 * 
 * COMPARISON WITH LAMBDA:
 * Anonymous Class (Java 7 and before):
 *   Collections.sort(list, new Comparator<String>() {
 *       @Override
 *       public int compare(String a, String b) {
 *           return a.compareTo(b);
 *       }
 *   });
 * 
 * Lambda (Java 8+):
 *   Collections.sort(list, (a, b) -> a.compareTo(b));
 * 
 * Lambdas are preferred for simple functional interfaces
 * Anonymous classes are better for complex logic or multiple methods
 * 
 * MEMORY CONSIDERATIONS:
 * - Each anonymous class instance increases memory
 * - Compiler creates a .class file for each: OuterClass$1.class, OuterClass$2.class
 * - Use lambdas for memory efficiency (Java 8+)
 */

