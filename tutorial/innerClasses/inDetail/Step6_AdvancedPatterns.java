package me.niteshh.OPPs.tutorial.innerClasses.inDetail;

/**
 * STEP 6: ADVANCED PATTERNS WITH INNER CLASSES
 * 
 * This file demonstrates advanced and practical design patterns
 * that use inner classes in production-level code.
 * 
 * PATTERNS COVERED:
 * ✓ Builder Pattern
 * ✓ Factory Pattern
 * ✓ Wrapper/Adapter Pattern
 * ✓ Strategy Pattern
 * ✓ Nested Interfaces
 */

public class Step6_AdvancedPatterns {

    // ==================== PATTERN 1: BUILDER PATTERN ====================
    
    /**
     * STEP 6.1: Builder Pattern using Static Inner Class
     * 
     * The builder pattern is perfect use case for static inner classes.
     * It allows flexible object construction with many optional parameters.
     */
    public static class Car {
        
        // Required parameters
        private final String brand;
        private final String model;
        
        // Optional parameters
        private final String color;
        private final int year;
        private final String fuelType;
        private final int engineHP;
        private final boolean hasABS;
        private final boolean hasNavigation;
        
        // Private constructor - only builder can use it
        private Car(CarBuilder builder) {
            this.brand = builder.brand;
            this.model = builder.model;
            this.color = builder.color;
            this.year = builder.year;
            this.fuelType = builder.fuelType;
            this.engineHP = builder.engineHP;
            this.hasABS = builder.hasABS;
            this.hasNavigation = builder.hasNavigation;
        }
        
        // ===== STATIC INNER CLASS: BUILDER =====
        /**
         * Static inner class for building Car objects
         */
        public static class CarBuilder {
            
            // Required parameters
            private final String brand;
            private final String model;
            
            // Optional parameters with defaults
            private String color = "Black";
            private int year = 2026;
            private String fuelType = "Petrol";
            private int engineHP = 200;
            private boolean hasABS = true;
            private boolean hasNavigation = false;
            
            // Constructor for required fields
            public CarBuilder(String brand, String model) {
                this.brand = brand;
                this.model = model;
            }
            
            // Fluent setter methods for optional fields
            public CarBuilder color(String color) {
                this.color = color;
                return this;
            }
            
            public CarBuilder year(int year) {
                this.year = year;
                return this;
            }
            
            public CarBuilder fuelType(String fuelType) {
                this.fuelType = fuelType;
                return this;
            }
            
            public CarBuilder engineHP(int hp) {
                this.engineHP = hp;
                return this;
            }
            
            public CarBuilder hasABS(boolean hasABS) {
                this.hasABS = hasABS;
                return this;
            }
            
            public CarBuilder hasNavigation(boolean hasNav) {
                this.hasNavigation = hasNav;
                return this;
            }
            
            // Build method - final step
            public Car build() {
                return new Car(this);
            }
        }
        
        // Getters
        public String getBrand() { return brand; }
        public String getModel() { return model; }
        public String getColor() { return color; }
        public int getYear() { return year; }
        public String getFuelType() { return fuelType; }
        public int getEngineHP() { return engineHP; }
        public boolean hasABS() { return hasABS; }
        public boolean hasNavigation() { return hasNavigation; }
        
        @Override
        public String toString() {
            return String.format("""
                    Car {
                      Brand: %s
                      Model: %s
                      Year: %d
                      Color: %s
                      Fuel: %s
                      HP: %d
                      ABS: %s
                      Navigation: %s
                    }""",
                    brand, model, year, color, fuelType, engineHP, hasABS, hasNavigation);
        }
    }
    
    // ==================== PATTERN 2: FACTORY PATTERN ====================
    
    /**
     * STEP 6.2: Factory Pattern using Static Inner Classes
     * 
     * Factory pattern creates objects without exposing creation logic.
     */
    public static class DatabaseConnection {
        
        private String connectionString;
        private String type;
        
        private DatabaseConnection(String connectionString, String type) {
            this.connectionString = connectionString;
            this.type = type;
        }
        
        // ===== STATIC INNER CLASS: CONNECTION FACTORY =====
        public static class ConnectionFactory {
            
            // Create MySQL connection
            public static DatabaseConnection createMySQLConnection(String host, String database) {
                String connString = "jdbc:mysql://" + host + "/" + database;
                return new DatabaseConnection(connString, "MySQL");
            }
            
            // Create PostgreSQL connection
            public static DatabaseConnection createPostgreSQLConnection(String host, String database) {
                String connString = "jdbc:postgresql://" + host + "/" + database;
                return new DatabaseConnection(connString, "PostgreSQL");
            }
            
            // Create MongoDB connection
            public static DatabaseConnection createMongoDBConnection(String host, int port) {
                String connString = "mongodb://" + host + ":" + port;
                return new DatabaseConnection(connString, "MongoDB");
            }
        }
        
        public void connect() {
            System.out.println("🔗 Connecting to " + type);
            System.out.println("   Connection String: " + connectionString);
        }
    }
    
    // ==================== PATTERN 3: STRATEGY PATTERN ====================
    
    /**
     * STEP 6.3: Strategy Pattern using Inner Classes
     * 
     * Strategy pattern encapsulates algorithms in separate classes.
     */
    interface PaymentStrategy {
        void pay(double amount);
        String getPaymentMethod();
    }
    
    public class PaymentProcessor {
        
        private PaymentStrategy strategy;
        
        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.strategy = strategy;
        }
        
        public void processPayment(double amount) {
            if (strategy == null) {
                System.out.println("❌ No payment strategy selected!");
                return;
            }
            strategy.pay(amount);
        }
        
        // ===== INNER CLASS: CREDIT CARD PAYMENT =====
        public class CreditCardPayment implements PaymentStrategy {
            
            private String cardNumber;
            private String cardHolder;
            
            public CreditCardPayment(String cardNumber, String cardHolder) {
                this.cardNumber = cardNumber;
                this.cardHolder = cardHolder;
            }
            
            @Override
            public void pay(double amount) {
                System.out.println("💳 Credit Card Payment");
                System.out.println("   Cardholder: " + cardHolder);
                System.out.println("   Card: ****-****-****-" + cardNumber.substring(12));
                System.out.println("   Amount: ₹" + amount);
                System.out.println("   ✅ Payment Processed!");
            }
            
            @Override
            public String getPaymentMethod() {
                return "Credit Card";
            }
        }
        
        // ===== INNER CLASS: DIGITAL WALLET PAYMENT =====
        public class DigitalWalletPayment implements PaymentStrategy {
            
            private String walletID;
            private double balance;
            
            public DigitalWalletPayment(String walletID, double balance) {
                this.walletID = walletID;
                this.balance = balance;
            }
            
            @Override
            public void pay(double amount) {
                if (balance >= amount) {
                    balance -= amount;
                    System.out.println("📱 Digital Wallet Payment");
                    System.out.println("   Wallet ID: " + walletID);
                    System.out.println("   Amount: ₹" + amount);
                    System.out.println("   Remaining Balance: ₹" + balance);
                    System.out.println("   ✅ Payment Processed!");
                } else {
                    System.out.println("❌ Insufficient balance!");
                }
            }
            
            @Override
            public String getPaymentMethod() {
                return "Digital Wallet";
            }
        }
    }
    
    // ==================== PATTERN 4: WRAPPER/DECORATOR PATTERN ====================
    
    /**
     * STEP 6.4: Wrapper Pattern for Security
     * 
     * Wraps sensitive data with access control.
     */
    public static class SecureData {
        
        private String data;
        private String accessLevel;
        
        private SecureData(String data, String accessLevel) {
            this.data = data;
            this.accessLevel = accessLevel;
        }
        
        // ===== STATIC INNER CLASS: WRAPPER WITH ACCESS CONTROL =====
        public static class SecureWrapper {
            
            public static SecureData createAdminData(String data) {
                return new SecureData(data, "ADMIN");
            }
            
            public static SecureData createUserData(String data) {
                return new SecureData(data, "USER");
            }
        }
        
        public String getData(String requestorLevel) {
            if (requestorLevel.equals(accessLevel) || requestorLevel.equals("ADMIN")) {
                return data;
            }
            return "❌ Access Denied!";
        }
    }
    
    // ==================== PATTERN 5: NESTED INTERFACES ====================
    
    /**
     * STEP 6.5: Nested Interfaces for logical grouping
     */
    public static class VehicleSystem {
        
        // Nested interface for security operations
        public interface SecurityOps {
            void lock();
            void unlock();
            void enableAlarm();
        }
        
        // Nested interface for performance operations
        public interface PerformanceOps {
            void accelerate();
            void brake();
            void setGear(String gear);
        }
        
        // Implementation of nested interfaces
        public static class SmartCar implements SecurityOps, PerformanceOps {
            
            private String carName;
            
            public SmartCar(String carName) {
                this.carName = carName;
            }
            
            @Override
            public void lock() { System.out.println("🔒 " + carName + " locked"); }
            
            @Override
            public void unlock() { System.out.println("🔓 " + carName + " unlocked"); }
            
            @Override
            public void enableAlarm() { System.out.println("🚨 Alarm enabled"); }
            
            @Override
            public void accelerate() { System.out.println("⚡ Accelerating"); }
            
            @Override
            public void brake() { System.out.println("🛑 Braking"); }
            
            @Override
            public void setGear(String gear) { System.out.println("⚙️  Gear: " + gear); }
        }
    }
}

// ==================== SUMMARY OF ADVANCED PATTERNS ====================
/*
 * PATTERN USAGE SUMMARY:
 * 
 * 1. BUILDER PATTERN:
 *    Use: When creating complex objects with many optional parameters
 *    Benefits: Fluent API, easy to read, mandatory parameters enforced
 *    Implementation: Static inner class with fluent setters
 * 
 * 2. FACTORY PATTERN:
 *    Use: When object creation logic needs to be encapsulated
 *    Benefits: Hide creation complexity, centralized object creation
 *    Implementation: Static inner class with factory methods
 * 
 * 3. STRATEGY PATTERN:
 *    Use: When you have multiple algorithms for same task
 *    Benefits: Runtime algorithm selection, easy to add new strategies
 *    Implementation: Member inner classes implementing strategy interface
 * 
 * 4. WRAPPER/DECORATOR PATTERN:
 *    Use: When you need to add behavior to existing objects
 *    Benefits: Separation of concerns, security/access control
 *    Implementation: Static inner class wrapping sensitive data
 * 
 * 5. NESTED INTERFACES:
 *    Use: For logical grouping of related operations
 *    Benefits: Better organization, namespace clarity
 *    Implementation: Multiple nested interfaces with implementations
 * 
 * BEST PRACTICES:
 * ✓ Use static inner classes for utility/factory classes
 * ✓ Use member inner classes only when need outer class state
 * ✓ Use local inner classes for method-specific logic
 * ✓ Use anonymous classes for one-time implementations
 * ✓ Consider lambdas for functional interfaces (Java 8+)
 * ✓ Always name inner classes clearly for maintainability
 * ✓ Keep inner classes small and focused
 */

