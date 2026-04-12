package me.niteshh.OPPs.Inheritance.innerClasses;

/**
 * STEP 3: LOCAL INNER CLASS
 * 
 * A local inner class is a class defined inside a METHOD of another class.
 * It has the most limited scope - it can only be used within that method.
 * 
 * KEY POINTS:
 * ✓ Defined inside a method of outer class
 * ✓ Exists only for the duration of that method
 * ✓ Cannot have public, private, or protected modifiers
 * ✓ Can access local variables (if they are final or effectively final)
 * ✓ Cannot be static
 * 
 * REAL-WORLD ANALOGY:
 * Think of a Temporary Workshop inside a Garage (method).
 * The workshop is created when you enter the garage,
 * and it disappears when you leave.
 */

public class Step3_LocalInnerClass {

    private String companyName = "AutoCorp";
    
    // ==================== OUTER CLASS METHOD CONTAINING LOCAL INNER CLASS ====================
    
    /**
     * STEP 3.1: Method with Local Inner Class
     * This method demonstrates how local inner classes work.
     */
    public void buildCar(String carModel) {
        
        // Local variable - used by local inner class
        final int year = 2026;  // Must be final or effectively final
        String location = "Factory A";  // Effectively final (not modified after declaration)
        
        // ===== LOCAL INNER CLASS DEFINITION =====
        class CarBuilder {
            
            private String model;
            private int enginePower;
            
            // Constructor of local inner class
            public CarBuilder(String model, int power) {
                this.model = model;
                this.enginePower = power;
            }
            
            // Method in local inner class
            public void buildProcess() {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("🏗️  CAR BUILDING PROCESS");
                System.out.println("=".repeat(50));
                
                // KEY POINT: Can access local variables of enclosing method
                System.out.println("Company: " + companyName);        // ✓ Outer class member
                System.out.println("Model: " + model);                 // ✓ Local variable
                System.out.println("Year: " + year);                   // ✓ Local variable (final)
                System.out.println("Location: " + location);           // ✓ Local variable (effectively final)
                System.out.println("Engine Power: " + enginePower + " HP");
                System.out.println("=".repeat(50));
            }
            
            public void startTesting() {
                System.out.println("\n✅ Testing car: " + model);
                System.out.println("   Year of manufacture: " + year);
                System.out.println("   Testing location: " + location);
            }
        }
        
        // ===== CREATE INSTANCE OF LOCAL INNER CLASS =====
        // Can only be done within this method
        CarBuilder builder = new CarBuilder(carModel, 350);
        builder.buildProcess();
        builder.startTesting();
    }
    
    // ==================== ANOTHER METHOD WITH LOCAL INNER CLASS ====================
    
    /**
     * STEP 3.2: Another example of local inner class
     * Demonstrates accessing final variables
     */
    public void createServiceCenter(String cityName) {
        
        // These variables must be effectively final to be used in local inner class
        final String serviceCharge = "₹5000";
        final int warrantyYears = 5;
        String technician = "Rajesh Kumar";  // Effectively final
        
        // ===== LOCAL INNER CLASS =====
        class ServiceCenter {
            
            private String centerName;
            private int employeeCount;
            
            public ServiceCenter(String name, int employees) {
                this.centerName = name;
                this.employeeCount = employees;
            }
            
            public void printDetails() {
                System.out.println("\n" + "-".repeat(40));
                System.out.println("📍 Service Center Details:");
                System.out.println("-".repeat(40));
                System.out.println("City: " + cityName);              // ✓ Parameter of method
                System.out.println("Center Name: " + centerName);
                System.out.println("Employees: " + employeeCount);
                System.out.println("Service Charge: " + serviceCharge); // ✓ Final variable
                System.out.println("Warranty: " + warrantyYears + " years");
                System.out.println("Lead Technician: " + technician);  // ✓ Effectively final
                System.out.println("-".repeat(40));
            }
        }
        
        // Create and use local inner class
        ServiceCenter center = new ServiceCenter("Service Center " + cityName, 15);
        center.printDetails();
    }
    
    // ==================== ADVANCED EXAMPLE ====================
    
    /**
     * STEP 3.3: Local inner class implementing an interface
     */
    public void demonstrateLocalInnerWithInterface() {
        
        final String projectName = "Smart Car System";
        
        // Interface that will be implemented by local inner class
        interface DeviceController {
            void initialize();
            void performAction();
            void shutdown();
        }
        
        // Local inner class implementing interface
        class TemperatureController implements DeviceController {
            
            @Override
            public void initialize() {
                System.out.println("\n🌡️  Initializing Temperature Controller for: " + projectName);
            }
            
            @Override
            public void performAction() {
                System.out.println("   Setting optimal temperature...");
            }
            
            @Override
            public void shutdown() {
                System.out.println("   Shutting down temperature controller");
            }
        }
        
        // Use the local inner class
        DeviceController controller = new TemperatureController();
        controller.initialize();
        controller.performAction();
        controller.shutdown();
    }
}

// ==================== SUMMARY OF LOCAL INNER CLASS ====================
/*
 * CHARACTERISTICS:
 * 1. Defined inside a method
 * 2. Only visible and usable within that method
 * 3. Scope is limited to the method where it's defined
 * 4. Cannot have access modifiers (public, private, protected)
 * 5. Can be abstract or final
 * 6. Cannot be static
 * 7. Cannot have static members (except static final constants)
 * 
 * LOCAL VARIABLES & SCOPE:
 * - Can access local variables of the method
 * - Those variables must be final or effectively final
 * - Why? Because local variables are on stack and disappear
 *   after method execution, but inner class instance might persist
 * - Compiler creates a copy of final variable in inner class
 * 
 * EFFECTIVELY FINAL:
 * Variable is "effectively final" if:
 * - It's not declared as final
 * - But it's never modified after initialization
 * Example:
 *   String name = "John";  // Effectively final (never changed)
 *   // OK: Can use in local inner class
 *   
 *   int count = 0;
 *   count = 5;  // Modified!
 *   // ERROR: Cannot use in local inner class
 * 
 * INSTANTIATION:
 * Only possible within the method:
 *   Outer outer = new Outer();
 *   outer.methodContainingLocalInner();
 *   // Inside method:
 *   LocalInnerClass obj = new LocalInnerClass();
 * 
 * MEMORY IMPACT:
 * - Lives on heap
 * - Creates implicit reference to outer class
 * - Garbage collected when method completes
 * 
 * USE CASES:
 * ✓ Implementing interfaces with specific behavior for a method
 * ✓ Creating temporary helper classes for specific logic
 * ✓ Encapsulating method-specific logic
 * ✓ Implementing callbacks or listeners
 * ✓ When you need different behavior for different method calls
 * 
 * COMPARISON WITH ANONYMOUS CLASSES:
 * Local Inner Class:
 * - Defined with class name
 * - Can have multiple constructors
 * - Can extend multiple levels
 * - More readable for complex logic
 * 
 * Anonymous Class:
 * - No name
 * - Single, implicit constructor
 * - Best for simple one-time implementations
 * - More concise for simple use cases
 */

