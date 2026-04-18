package me.niteshh.OPPs.tutorial.enumClasses.inDepth;

/**
 * STEP 2: ENUM METHODS AND FUNCTIONALITY
 * 
 * Enums are not just simple constants. They are actually classes!
 * Enums can have:
 * - Instance variables (fields)
 * - Constructor methods
 * - Instance methods
 * - Abstract methods
 * 
 * This step explores these advanced features.
 */

public class Step2_EnumMethods {

    /**
     * Enum with constructor and fields
     * Each enum constant gets assigned initial values
     */
    enum Season {
        // Syntax: CONSTANT_NAME(constructorArguments)
        SPRING(15, "March-May"),
        SUMMER(25, "June-August"),
        AUTUMN(20, "September-November"),
        WINTER(5, "December-February");

        // ============= INSTANCE VARIABLES =============
        // These variables belong to each enum constant
        private int averageTemperature;  // Each season has a temperature
        private String months;           // Each season spans certain months

        // ============= CONSTRUCTOR =============
        /**
         * Constructor for Season enum
         * Called automatically when enum constants are initialized
         * 
         * Important: Enum constructor must be private or package-private
         * (cannot be public)
         */
        Season(int temperature, String months) {
            this.averageTemperature = temperature;
            this.months = months;
        }

        // ============= GETTER METHODS =============
        /**
         * Get the average temperature for this season
         */
        public int getAverageTemperature() {
            return averageTemperature;
        }

        /**
         * Get the months for this season
         */
        public String getMonths() {
            return months;
        }

        /**
         * Custom method to provide description
         */
        public String getDescription() {
            return this.name() + ": " + months + " (Avg: " + averageTemperature + "°C)";
        }
    }

    /**
     * Priority enum showing different methods
     */
    enum Priority {
        LOW(1),
        MEDIUM(2),
        HIGH(3),
        CRITICAL(4);

        private int level;

        Priority(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        // ============= CUSTOM METHOD =============
        /**
         * Check if this priority is urgent
         */
        public boolean isUrgent() {
            return this.level >= 3;  // HIGH and CRITICAL are urgent
        }

        /**
         * Get action description based on priority
         */
        public String getAction() {
            switch (this) {
                case LOW:
                    return "Can be scheduled later";
                case MEDIUM:
                    return "Should be done this week";
                case HIGH:
                    return "Should be done this day";
                case CRITICAL:
                    return "Immediate action required";
                default:
                    return "Unknown priority";
            }
        }
    }

    /**
     * Payment method enum with type information
     */
    enum PaymentMethod {
        CREDIT_CARD("Credit Card", "Fast"),
        DEBIT_CARD("Debit Card", "Fast"),
        NET_BANKING("Net Banking", "Very Fast"),
        CASH_ON_DELIVERY("Cash On Delivery", "At Delivery"),
        WALLET("Digital Wallet", "Instant");

        private String displayName;
        private String processingTime;

        PaymentMethod(String displayName, String processingTime) {
            this.displayName = displayName;
            this.processingTime = processingTime;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getProcessingTime() {
            return processingTime;
        }
    }

    // ============= MAIN METHOD =============

    public static void main(String[] args) {
        System.out.println("===== STEP 2: ENUM METHODS AND FUNCTIONALITY =====\n");

        // ============= 1. ENUM WITH CONSTRUCTOR AND FIELDS =============
        System.out.println("1️⃣  ENUM WITH CONSTRUCTOR AND FIELDS:\n");

        // Each Season constant has its own temperature and months
        Season spring = Season.SPRING;
        System.out.println("  Season: " + spring.name());
        System.out.println("  Months: " + spring.getMonths());
        System.out.println("  Temperature: " + spring.getAverageTemperature() + "°C");

        System.out.println("\n  Using custom method:");
        System.out.println("  " + spring.getDescription());

        // ============= 2. ITERATING ENUMS WITH THEIR DATA =============
        System.out.println("\n2️⃣  ITERATING ENUMS WITH THEIR DATA:\n");

        System.out.println("  All seasons with information:");
        for (Season season : Season.values()) {
            // Access fields through methods
            System.out.println("    " + season.getDescription());
        }

        // ============= 3. ENUM METHODS IN PRACTICE =============
        System.out.println("\n3️⃣  ENUM METHODS - PRIORITY EXAMPLE:\n");

        Priority taskPriority = Priority.HIGH;
        System.out.println("  Task priority: " + taskPriority);
        System.out.println("  Priority level: " + taskPriority.getLevel());
        System.out.println("  Is urgent? " + taskPriority.isUrgent());
        System.out.println("  Action: " + taskPriority.getAction());

        // ============= 4. FINDING URGENT PRIORITIES =============
        System.out.println("\n4️⃣  FINDING URGENT PRIORITIES:\n");

        System.out.println("  Urgent priorities:");
        for (Priority p : Priority.values()) {
            if (p.isUrgent()) {
                System.out.println("    - " + p + " (Level " + p.getLevel() + ")");
            }
        }

        // ============= 5. PAYMENT METHOD EXAMPLE =============
        System.out.println("\n5️⃣  PAYMENT METHOD ENUM:\n");

        System.out.println("  Available payment methods:");
        for (PaymentMethod method : PaymentMethod.values()) {
            // Access constructor-initialized fields
            System.out.println("    " + method.getDisplayName() + 
                             " - Processing time: " + method.getProcessingTime());
        }

        // ============= 6. COMPARING ENUM VALUES =============
        System.out.println("\n6️⃣  COMPARING ENUM VALUES:\n");

        Priority p1 = Priority.HIGH;
        Priority p2 = Priority.HIGH;
        Priority p3 = Priority.MEDIUM;

        System.out.println("  p1 (HIGH) == p2 (HIGH): " + (p1 == p2));
        System.out.println("  p1 (HIGH).compareTo(p3 (MEDIUM)): " + p1.compareTo(p3));
        // Positive value means p1 comes after p3 in definition order
        System.out.println("  (Positive = p1 is after p3 in definition order)");

        // ============= 7. ENUM.COMPARETO() METHOD =============
        System.out.println("\n7️⃣  COMPARING ENUM POSITIONS:\n");

        Season summer = Season.SUMMER;
        Season winter = Season.WINTER;

        System.out.println("  SUMMER.compareTo(WINTER): " + summer.compareTo(winter));
        System.out.println("  (Negative: SUMMER comes before WINTER)");

        System.out.println("\n  WINTER.compareTo(SPRING): " + winter.compareTo(Season.SPRING));
        System.out.println("  (Positive: WINTER comes after SPRING)");

        // ============= 8. FINDING MAX PRIORITY =============
        System.out.println("\n8️⃣  FINDING MAXIMUM PRIORITY:\n");

        Priority[] taskPriorities = {Priority.LOW, Priority.CRITICAL, Priority.MEDIUM, Priority.HIGH};
        Priority maxPriority = findMaxPriority(taskPriorities);
        System.out.println("  Maximum priority among tasks: " + maxPriority);
        System.out.println("  Action required: " + maxPriority.getAction());

        // ============= 9. ENUM ORDINAL USAGE =============
        System.out.println("\n9️⃣  USING ORDINAL VALUES:\n");

        System.out.println("  Seasons in order of appearance:");
        for (Season s : Season.values()) {
            // ordinal() gives 0-based position
            System.out.println("    Position " + s.ordinal() + ": " + s);
        }

        // ============= 10. PRACTICAL: ENUM-BASED LOOKUP =============
        System.out.println("\n🔟  PRACTICAL: TEMPERATURE RANGE LOOKUP:\n");

        int targetTemp = 22;
        Season bestSeason = findSeasonByTemperature(targetTemp);
        System.out.println("  Searching for season around " + targetTemp + "°C");
        System.out.println("  Best season: " + bestSeason);
        System.out.println("  Details: " + bestSeason.getDescription());

        // ============= SUMMARY OF ENUM CAPABILITIES =============
        System.out.println("\n===== ENUM CAPABILITIES =====");
        System.out.println("✓ Can have instance variables (fields)");
        System.out.println("✓ Can have constructors (must be private)");
        System.out.println("✓ Can have instance methods");
        System.out.println("✓ Can have abstract methods");
        System.out.println("✓ Compare using compareTo()");
        System.out.println("✓ Store and retrieve data per constant");
        System.out.println("✓ Use in switch statements safely");
    }

    // ============= HELPER METHODS =============

    /**
     * Find the maximum priority from an array
     */
    static Priority findMaxPriority(Priority[] priorities) {
        // Start with first priority
        Priority max = priorities[0];

        // Compare each priority
        for (Priority p : priorities) {
            // compareTo returns positive if p is greater (comes after)
            if (p.compareTo(max) > 0) {
                max = p;
            }
        }
        return max;
    }

    /**
     * Find season closest to target temperature
     */
    static Season findSeasonByTemperature(int targetTemp) {
        Season closest = Season.SPRING;
        int minDifference = Math.abs(Season.SPRING.getAverageTemperature() - targetTemp);

        // Check each season
        for (Season season : Season.values()) {
            int difference = Math.abs(season.getAverageTemperature() - targetTemp);
            if (difference < minDifference) {
                minDifference = difference;
                closest = season;
            }
        }
        return closest;
    }
}

