package me.niteshh.OPPs.tutorial.enumClasses.lecturePractice.Theory;

/**
 * ============================================================================
 * DETAILED EXPLANATION: UNDERSTANDING DAY.SUNDAY AND ENUM INSTANCES
 * ============================================================================
 * 
 * This file explains in detail what Day.SUNDAY is and how enum instances work.
 * 
 * KEY CONCEPT:
 * ============
 * In Java, when you create an enum, each constant (like SUNDAY, MONDAY, etc.)
 * is actually an INSTANCE of the enum class.
 * 
 * Think of it like this:
 * - If Enum is a BLUEPRINT (like a class definition)
 * - Then SUNDAY, MONDAY, etc. are OBJECTS (instances) created from that blueprint
 */

public class EnumInstancesExplained {

    /**
     * First, let's define a simple Day enum
     * This is the enum we're using in the Test.java file
     */
    enum Day {
        // These are NOT just simple values
        // Each one (SUNDAY, MONDAY, etc.) is actually an INSTANCE of the Day enum class
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

    // ============================================================================
    // EXPLANATION 1: WHAT IS Day.SUNDAY?
    // ============================================================================
    
    /*
     * SIMPLE ANSWER:
     * ==============
     * Day.SUNDAY is an INSTANCE (an object) of the Day enum class.
     * 
     * DETAILED EXPLANATION:
     * ====================
     * 
     * When you write: enum Day { SUNDAY, MONDAY, ... }
     * 
     * Java automatically creates SINGLETON INSTANCES like this:
     * (internally, Java does this automatically)
     * 
     *     private static final Day SUNDAY = new Day();
     *     private static final Day MONDAY = new Day();
     *     private static final Day TUESDAY = new Day();
     *     ... and so on
     * 
     * So Day.SUNDAY is:
     * 1. A STATIC member of the Day enum class
     * 2. An INSTANCE (object) of type Day
     * 3. A SINGLETON - there's only ONE SUNDAY instance
     * 4. A CONSTANT - it can never be changed
     * 5. IMMUTABLE - you cannot modify it
     * 
     * ANALOGY:
     * ========
     * Think of it like STATIC FINAL objects:
     * 
     * Instead of creating Day instances like:
     *     Day myDay = new Day();  // You cannot do this with enums!
     * 
     * You use the predefined instances:
     *     Day myDay = Day.SUNDAY;  // This is the correct way
     * 
     * COMPARISON WITH REGULAR CLASS:
     * ==============================
     * Regular class:
     *     class Color {
     *         // Can create multiple instances
     *         Color red = new Color();
     *         Color blue = new Color();
     *     }
     * 
     * Enum class:
     *     enum Color {
     *         // Fixed instances - cannot create more
     *         RED,      // This is an instance
     *         BLUE      // This is an instance
     *     }
     */

    public static void main(String[] args) {
        System.out.println("============ ENUM INSTANCES EXPLAINED ============\n");

        // ====================================================================
        // PART 1: Day.SUNDAY IS AN INSTANCE
        // ====================================================================
        System.out.println("PART 1: WHAT IS Day.SUNDAY?\n");

        /*
         * When you do this:
         *     Day myDay = Day.SUNDAY;
         * 
         * You are:
         * 1. Getting the SUNDAY instance from the Day enum
         * 2. Assigning it to a variable called myDay
         * 3. Now myDay holds a reference to the SUNDAY instance
         */
        Day myDay = Day.SUNDAY;

        System.out.println("  Day myDay = Day.SUNDAY;");
        System.out.println("  myDay now holds: " + myDay);
        System.out.println("  Type of myDay: " + myDay.getClass().getSimpleName());

        /*
         * Important facts:
         * 
         * 1. myDay is pointing to the SAME SUNDAY instance
         * 2. There is only ONE SUNDAY instance in the entire program
         * 3. Every time you access Day.SUNDAY, you get the same object
         */

        Day anotherDay = Day.SUNDAY;
        System.out.println("\n  Day anotherDay = Day.SUNDAY;");
        System.out.println("  myDay == anotherDay: " + (myDay == anotherDay));
        System.out.println("  They point to the SAME instance!");

        // ====================================================================
        // PART 2: UNDERSTANDING ENUM INSTANCES vs REGULAR CLASSES
        // ====================================================================
        System.out.println("\n\nPART 2: ENUM vs REGULAR CLASS\n");

        /*
         * REGULAR CLASS:
         * ==============
         * class Car {
         *     String color;
         *     String model;
         * }
         * 
         * You can create MULTIPLE instances:
         *     Car car1 = new Car();  // First instance
         *     Car car2 = new Car();  // Second instance
         *     Car car3 = new Car();  // Third instance
         * 
         * They are DIFFERENT objects in memory.
         * 
         * 
         * ENUM CLASS:
         * ===========
         * enum Day {
         *     SUNDAY,   // First (and only) SUNDAY instance
         *     MONDAY,   // First (and only) MONDAY instance
         *     ...
         * }
         * 
         * You CANNOT create new instances:
         *     Day day1 = new Day();  // COMPILATION ERROR!
         * 
         * You can only use the predefined instances:
         *     Day day1 = Day.SUNDAY;  // Correct
         *     Day day2 = Day.SUNDAY;  // Same instance
         *     Day day3 = Day.MONDAY;  // Different instance
         */

        System.out.println("  Regular Class Analogy:");
        System.out.println("  class Student { ... }");
        System.out.println("  Can create MULTIPLE different instances:");
        System.out.println("    Student s1 = new Student();  // First instance");
        System.out.println("    Student s2 = new Student();  // Different instance");
        System.out.println("    s1 == s2 → false (different objects)");

        System.out.println("\n  Enum Class:");
        System.out.println("  enum Day { SUNDAY, MONDAY, ... }");
        System.out.println("  Can only use FIXED instances:");
        System.out.println("    Day d1 = Day.SUNDAY;   // Use existing instance");
        System.out.println("    Day d2 = Day.SUNDAY;   // Same instance");
        System.out.println("    d1 == d2 → true (same object!)");

        // ====================================================================
        // PART 3: HOW ARE ENUM INSTANCES CREATED?
        // ====================================================================
        System.out.println("\n\nPART 3: HOW ARE ENUM INSTANCES CREATED?\n");

        /*
         * When Java sees:
         *     enum Day {
         *         SUNDAY,
         *         MONDAY,
         *         TUESDAY
         *     }
         * 
         * It automatically creates instances like:
         * 
         *     public static final Day SUNDAY = new Day();
         *     public static final Day MONDAY = new Day();
         *     public static final Day TUESDAY = new Day();
         * 
         * Key points:
         * 
         * 1. STATIC: These are class-level (shared by all)
         *    You access them as Day.SUNDAY (not through an instance)
         * 
         * 2. FINAL: These cannot be reassigned
         *    You cannot do: Day.SUNDAY = Day.MONDAY;  (ERROR!)
         * 
         * 3. new Day(): Each one is created with the enum constructor
         *    (The constructor is automatically called)
         * 
         * 4. SINGLETON: Only ONE instance of SUNDAY exists
         */

        System.out.println("  When you define:");
        System.out.println("    enum Day { SUNDAY, MONDAY, TUESDAY }");
        System.out.println("");
        System.out.println("  Java automatically creates:");
        System.out.println("    public static final Day SUNDAY = new Day();");
        System.out.println("    public static final Day MONDAY = new Day();");
        System.out.println("    public static final Day TUESDAY = new Day();");

        // ====================================================================
        // PART 4: ACCESSING ENUM INSTANCES
        // ====================================================================
        System.out.println("\n\nPART 4: HOW TO ACCESS ENUM INSTANCES\n");

        /*
         * STATIC ACCESS:
         * ==============
         * Since enum constants are STATIC members, you access them via the class:
         *     Day.SUNDAY    // Access SUNDAY instance
         *     Day.MONDAY    // Access MONDAY instance
         * 
         * NOT through an object:
         *     myDay.SUNDAY  // This doesn't work!
         */

        System.out.println("  Accessing enum instances:");
        System.out.println("    Day d1 = Day.SUNDAY;   // Correct (static access)");
        System.out.println("    Day d2 = Day.MONDAY;   // Correct (static access)");

        /*
         * Getting all instances:
         * ======================
         * Use values() method to get ALL enum instances:
         */

        System.out.println("\n  Getting all instances with values():");
        System.out.println("    Day[] allDays = Day.values();");

        Day[] allDays = Day.values();
        for (Day day : allDays) {
            System.out.println("      - " + day);
        }

        /*
         * Getting instance from string:
         * ==============================
         * Use valueOf() to convert string to enum instance:
         */

        System.out.println("\n  Converting string to instance with valueOf():");
        String dayName = "MONDAY";
        Day parsedDay = Day.valueOf(dayName);
        System.out.println("    Day.valueOf(\"" + dayName + "\") → " + parsedDay);

        // ====================================================================
        // PART 5: ENUM INSTANCES ARE SINGLETONS
        // ====================================================================
        System.out.println("\n\nPART 5: ENUM INSTANCES ARE SINGLETONS\n");

        /*
         * SINGLETON PATTERN:
         * ==================
         * A Singleton is a pattern where only ONE instance of a class exists.
         * 
         * Enums naturally implement the singleton pattern for each constant!
         * 
         * This means:
         * 1. Day.SUNDAY → always the SAME object
         * 2. Day.MONDAY → always the SAME object
         * 3. If you store Day.SUNDAY multiple times, they all point to same object
         */

        System.out.println("  Singleton demonstration:");

        Day sunday1 = Day.SUNDAY;
        Day sunday2 = Day.SUNDAY;
        Day sunday3 = Day.SUNDAY;

        System.out.println("    Day sunday1 = Day.SUNDAY;");
        System.out.println("    Day sunday2 = Day.SUNDAY;");
        System.out.println("    Day sunday3 = Day.SUNDAY;");

        System.out.println("\n    sunday1 == sunday2: " + (sunday1 == sunday2));
        System.out.println("    sunday2 == sunday3: " + (sunday2 == sunday3));
        System.out.println("    All three point to THE SAME instance!");

        // ====================================================================
        // PART 6: PROPERTIES OF ENUM INSTANCES
        // ====================================================================
        System.out.println("\n\nPART 6: PROPERTIES OF ENUM INSTANCES\n");

        /*
         * Each enum instance has built-in properties:
         */

        Day dayInstance = Day.FRIDAY;

        System.out.println("  Instance: " + dayInstance);
        System.out.println("    name(): " + dayInstance.name());
        System.out.println("      → Returns the name as string: \"FRIDAY\"");

        System.out.println("    ordinal(): " + dayInstance.ordinal());
        System.out.println("      → Returns position (0-based): SUNDAY=0, MONDAY=1, ..., FRIDAY=5");

        // ====================================================================
        // PART 7: USING ENUM INSTANCES
        // ====================================================================
        System.out.println("\n\nPART 7: PRACTICAL USAGE OF ENUM INSTANCES\n");

        /*
         * You use enum instances as VALUES to represent fixed choices:
         */

        System.out.println("  Example 1: Store a day");
        Day today = Day.FRIDAY;
        System.out.println("    Day today = Day.FRIDAY;");
        System.out.println("    today = " + today);

        System.out.println("\n  Example 2: Check which day");
        if (today == Day.FRIDAY) {
            System.out.println("    if (today == Day.FRIDAY) → true");
            System.out.println("    It's Friday!");
        }

        System.out.println("\n  Example 3: Use in switch");
        switch (today) {
            case SUNDAY:
                System.out.println("    It's Sunday");
                break;
            case FRIDAY:
                System.out.println("    It's Friday (matched!)");
                break;
            default:
                System.out.println("    Some other day");
        }

        // ====================================================================
        // PART 8: COMPARISON WITH CONSTANTS
        // ====================================================================
        System.out.println("\n\nPART 8: WHY ENUM INSTANCES ARE BETTER THAN CONSTANTS\n");

        /*
         * OLD WAY (using constants):
         * ==========================
         * public static final int SUNDAY = 0;
         * public static final int MONDAY = 1;
         * public static final int TUESDAY = 2;
         * 
         * Problems:
         * - Can pass invalid number: setDay(5)  // No error! But invalid!
         * - Not type-safe
         * - Confusing what the number means
         * 
         * 
         * NEW WAY (using enum instances):
         * ================================
         * enum Day {
         *     SUNDAY, MONDAY, TUESDAY
         * }
         * 
         * Benefits:
         * - Can only pass valid Day instance: setDay(Day.SUNDAY)
         * - Type-safe
         * - Self-documenting code
         * - Compiler prevents invalid values
         */

        System.out.println("  OLD way (unsafe):");
        System.out.println("    public static final int SUNDAY = 0;");
        System.out.println("    Can accidentally pass: setDay(999);  // No error!");

        System.out.println("\n  NEW way (using enum instances):");
        System.out.println("    enum Day { SUNDAY, MONDAY, ... }");
        System.out.println("    Can only pass: setDay(Day.SUNDAY);  // Type-safe!");

        // ====================================================================
        // SUMMARY
        // ====================================================================
        System.out.println("\n\n============ SUMMARY ============\n");

        System.out.println("  ✓ Day.SUNDAY is an INSTANCE (object) of Day enum");
        System.out.println("  ✓ Each enum constant is a SINGLETON instance");
        System.out.println("  ✓ All instances are STATIC members");
        System.out.println("  ✓ Instances are FINAL (cannot be changed)");
        System.out.println("  ✓ You cannot create new instances with new keyword");
        System.out.println("  ✓ Access via class: Day.SUNDAY (not through object)");
        System.out.println("  ✓ Get all instances with: Day.values()");
        System.out.println("  ✓ Convert string to instance: Day.valueOf(\"SUNDAY\")");
        System.out.println("  ✓ Much safer and cleaner than using constants");
        System.out.println("  ✓ Provides type safety and compile-time checking");
    }
}

