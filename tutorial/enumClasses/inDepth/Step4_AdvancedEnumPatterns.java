package me.niteshh.OPPs.tutorial.enumClasses.inDepth;

/**
 * STEP 4: ADVANCED ENUM PATTERNS
 * 
 * This step covers advanced enum patterns and techniques:
 * - Abstract methods in enums
 * - Enum singleton pattern
 * - Type-safe heterogeneous collections
 * - Advanced comparisons
 */

public class Step4_AdvancedEnumPatterns {

    /**
     * Enum with abstract methods
     * Each enum constant provides its own implementation
     */
    enum Calculator {
        // Each operation enum value defines its own calculation
        PLUS {
            /**
             * Concrete implementation of PLUS operation
             */
            @Override
            public int apply(int a, int b) {
                return a + b;
            }

            @Override
            public String getSymbol() {
                return "+";
            }
        },

        MINUS {
            /**
             * Concrete implementation of MINUS operation
             */
            @Override
            public int apply(int a, int b) {
                return a - b;
            }

            @Override
            public String getSymbol() {
                return "-";
            }
        },

        MULTIPLY {
            /**
             * Concrete implementation of MULTIPLY operation
             */
            @Override
            public int apply(int a, int b) {
                return a * b;
            }

            @Override
            public String getSymbol() {
                return "*";
            }
        },

        DIVIDE {
            /**
             * Concrete implementation of DIVIDE operation
             * Shows how to handle edge cases
             */
            @Override
            public int apply(int a, int b) {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            }

            @Override
            public String getSymbol() {
                return "/";
            }
        };

        // ============= ABSTRACT METHODS =============
        /**
         * Abstract method that each constant must implement
         */
        public abstract int apply(int a, int b);

        /**
         * Abstract method for symbol representation
         */
        public abstract String getSymbol();

        /**
         * Concrete method available to all constants
         */
        public String getDescription() {
            return this.name() + " (" + getSymbol() + ")";
        }
    }

    /**
     * Enum representing currency with exchange rates
     * Shows how to maintain state and perform calculations
     */
    enum Currency {
        // Each currency stores its code and exchange rate to USD
        USD("United States Dollar", 1.0),
        EUR("Euro", 0.92),
        GBP("British Pound", 0.79),
        INR("Indian Rupee", 83.0),
        JPY("Japanese Yen", 149.0);

        private String fullName;
        private double exchangeRateToUSD;

        Currency(String fullName, double exchangeRateToUSD) {
            this.fullName = fullName;
            this.exchangeRateToUSD = exchangeRateToUSD;
        }

        public String getFullName() {
            return fullName;
        }

        public double getExchangeRateToUSD() {
            return exchangeRateToUSD;
        }

        /**
         * Convert amount from this currency to target currency
         */
        public double convertTo(double amount, Currency target) {
            // First convert to USD, then to target currency
            double amountInUSD = amount / this.exchangeRateToUSD;
            return amountInUSD * target.exchangeRateToUSD;
        }

        /**
         * Get exchange rate between two currencies
         */
        public double getExchangeRate(Currency target) {
            return this.exchangeRateToUSD / target.exchangeRateToUSD;
        }
    }

    /**
     * Operation enum with different computation strategies
     */
    enum Operation {
        // Different operation types with custom implementations
        ABSOLUTE {
            /**
             * Compute absolute value
             */
            @Override
            public double compute(double a, double b) {
                return Math.abs(a);
            }

            @Override
            public String describe() {
                return "Absolute value of first operand";
            }
        },

        MAX {
            /**
             * Return maximum of two values
             */
            @Override
            public double compute(double a, double b) {
                return Math.max(a, b);
            }

            @Override
            public String describe() {
                return "Maximum of two values";
            }
        },

        MIN {
            /**
             * Return minimum of two values
             */
            @Override
            public double compute(double a, double b) {
                return Math.min(a, b);
            }

            @Override
            public String describe() {
                return "Minimum of two values";
            }
        },

        AVERAGE {
            /**
             * Compute average of two values
             */
            @Override
            public double compute(double a, double b) {
                return (a + b) / 2.0;
            }

            @Override
            public String describe() {
                return "Average of two values";
            }
        },

        POWER {
            /**
             * Raise first to power of second
             */
            @Override
            public double compute(double a, double b) {
                return Math.pow(a, b);
            }

            @Override
            public String describe() {
                return "First raised to power of second";
            }
        };

        /**
         * Abstract method for computation
         */
        public abstract double compute(double a, double b);

        /**
         * Abstract method for description
         */
        public abstract String describe();
    }

    // ============= MAIN METHOD =============

    public static void main(String[] args) {
        System.out.println("===== STEP 4: ADVANCED ENUM PATTERNS =====\n");

        // ============= 1. ABSTRACT METHOD PATTERN =============
        System.out.println("1️⃣  ABSTRACT METHOD PATTERN - CALCULATOR:\n");

        // Each enum constant has its own implementation
        int x = 10;
        int y = 3;

        // Test each calculator operation
        for (Calculator calc : Calculator.values()) {
            try {
                int result = calc.apply(x, y);
                System.out.println("  " + x + " " + calc.getSymbol() + " " + y + " = " + result);
            } catch (ArithmeticException e) {
                System.out.println("  " + x + " " + calc.getSymbol() + " " + y + " = Error: " + e.getMessage());
            }
        }

        // ============= 2. CURRENCY CONVERSION =============
        System.out.println("\n2️⃣  CURRENCY CONVERSION:\n");

        // Convert 100 USD to other currencies
        double amountUSD = 100.0;
        System.out.println("  Converting " + amountUSD + " USD to other currencies:");

        for (Currency currency : Currency.values()) {
            if (currency != Currency.USD) {
                double converted = Currency.USD.convertTo(amountUSD, currency);
                System.out.println("    100 USD = " + String.format("%.2f", converted) + " " + currency);
            }
        }

        // ============= 3. EXCHANGE RATE LOOKUP =============
        System.out.println("\n3️⃣  EXCHANGE RATES:\n");

        System.out.println("  1 EUR equals:");
        double eurToUsd = Currency.EUR.convertTo(1.0, Currency.USD);
        System.out.println("    " + String.format("%.4f", eurToUsd) + " USD");

        double eurToInr = Currency.EUR.convertTo(1.0, Currency.INR);
        System.out.println("    " + String.format("%.4f", eurToInr) + " INR");

        // ============= 4. ADVANCED OPERATIONS =============
        System.out.println("\n4️⃣  ADVANCED OPERATIONS:\n");

        double a = 15.0;
        double b = 3.0;

        System.out.println("  Operations on " + a + " and " + b + ":");
        for (Operation op : Operation.values()) {
            double result = op.compute(a, b);
            System.out.println("    " + op + ": " + String.format("%.2f", result) + 
                             " (" + op.describe() + ")");
        }

        // ============= 5. POLYMORPHIC BEHAVIOR =============
        System.out.println("\n5️⃣  POLYMORPHIC BEHAVIOR:\n");

        /*
         * Demonstrate how different enum constants can have
         * different behavior while maintaining type safety
         */

        Calculator[] calculations = {Calculator.PLUS, Calculator.MINUS, Calculator.MULTIPLY};
        int operand1 = 20;
        int operand2 = 4;

        System.out.println("  Performing calculations:");
        for (Calculator calc : calculations) {
            int result = calc.apply(operand1, operand2);
            System.out.println("    " + operand1 + " " + calc.getSymbol() + " " + operand2 + " = " + result);
        }

        // ============= 6. COMPARING CURRENCIES =============
        System.out.println("\n6️⃣  COMPARING CURRENCY VALUES:\n");

        double amount1USD = 50.0;
        double amount100INR = 100.0;

        // Convert both to USD for comparison
        double inrInUSD = Currency.INR.convertTo(amount100INR, Currency.USD);

        System.out.println("  Comparing amounts:");
        System.out.println("    50 USD = " + String.format("%.2f", amount1USD) + " USD");
        System.out.println("    100 INR = " + String.format("%.2f", inrInUSD) + " USD");
        System.out.println("    50 USD is " + (amount1USD > inrInUSD ? "more" : "less") + " than 100 INR");

        // ============= 7. TYPE-SAFE HETEROGENEOUS COLLECTIONS =============
        System.out.println("\n7️⃣  TYPE-SAFE COLLECTIONS:\n");

        /*
         * Using enum as type-safe heterogeneous container
         * This is a pattern where we can store objects of different types
         * but maintain type safety through enums
         */

        java.util.Map<Operation, Double> results = new java.util.HashMap<>();

        // Store results of different operations
        results.put(Operation.ABSOLUTE, Operation.ABSOLUTE.compute(-5.0, 0));
        results.put(Operation.MAX, Operation.MAX.compute(10.0, 20.0));
        results.put(Operation.AVERAGE, Operation.AVERAGE.compute(10.0, 20.0));

        System.out.println("  Stored operation results:");
        for (java.util.Map.Entry<Operation, Double> entry : results.entrySet()) {
            System.out.println("    " + entry.getKey() + ": " + entry.getValue());
        }

        // ============= 8. PRACTICAL CALCULATOR USAGE =============
        System.out.println("\n8️⃣  CALCULATOR CHAIN:\n");

        /*
         * Show how abstract method pattern enables
         * different calculations using the same interface
         */

        int num = 50;
        System.out.println("  Performing sequential calculations on " + num + ":");

        for (Calculator op : Calculator.values()) {
            if (op != Calculator.DIVIDE) {
                try {
                    int result = op.apply(num, 5);
                    System.out.println("    " + num + " " + op.getSymbol() + " 5 = " + result);
                } catch (ArithmeticException e) {
                    System.out.println("    Error in " + op + ": " + e.getMessage());
                }
            }
        }

        // ============= SUMMARY =============
        System.out.println("\n===== ADVANCED ENUM PATTERNS SUMMARY =====");
        System.out.println("✓ Abstract methods for polymorphic behavior");
        System.out.println("✓ Strategy pattern with enums");
        System.out.println("✓ Type-safe calculations");
        System.out.println("✓ Business logic encapsulation");
        System.out.println("✓ Heterogeneous type-safe collections");
        System.out.println("✓ Powerful alternative to if-else chains");
    }
}

