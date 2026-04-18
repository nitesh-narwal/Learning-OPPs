package me.niteshh.OPPs.tutorial.enumClasses.lecturePractice.Theory;

/**
 * ============================================================================
 * QUICK REFERENCE: DAY.SUNDAY AND ENUM INSTANCES SUMMARY
 * ============================================================================
 * 
 * This file is a quick reference guide for understanding enum instances.
 */

public class EnumInstancesQuickReference {

    /*
     * ============================================================================
     * QUESTION 1: WHAT IS Day.SUNDAY?
     * ============================================================================
     * 
     * SIMPLE ANSWER:
     * Day.SUNDAY is an INSTANCE (an object) of the Day enum class.
     * 
     * DETAILED ANSWER:
     * ────────────────
     * When you define:
     *     enum Day { SUNDAY, MONDAY, TUESDAY, ... }
     * 
     * Java automatically creates objects like:
     *     public static final Day SUNDAY = new Day();  ← Instance
     *     public static final Day MONDAY = new Day();  ← Instance
     *     public static final Day TUESDAY = new Day(); ← Instance
     * 
     * So Day.SUNDAY is:
     * • An INSTANCE of Day enum
     * • A STATIC member (accessed via class: Day.SUNDAY)
     * • A SINGLETON (only one SUNDAY instance exists)
     * • IMMUTABLE (cannot be changed)
     * • FINAL (cannot be reassigned)
     * 
     * 
     * ============================================================================
     * QUESTION 2: ARE THINGS IN ENUM CLASS INSTANCES?
     * ============================================================================
     * 
     * YES, THEY ARE INSTANCES!
     * 
     * YES BUT NOT LIKE REGULAR CLASS INSTANCES:
     * 
     * REGULAR CLASS:
     * ──────────────
     * class Car { ... }
     * Car car1 = new Car();  ← You create instance
     * Car car2 = new Car();  ← Different instance
     * Can create unlimited instances
     * 
     * ENUM CLASS:
     * ───────────
     * enum Color { RED, GREEN, BLUE }
     * Color c1 = Color.RED;   ← Use existing instance
     * Color c2 = Color.RED;   ← Same instance
     * Color c3 = Color.GREEN; ← Different existing instance
     * Can only use predefined instances
     * 
     * The enum constants ARE INSTANCES - but special ones!
     * They are:
     * • Pre-created by Java
     * • Fixed in number
     * • Not creatable with new keyword
     * • Singletons (one instance each)
     * 
     * 
     * ============================================================================
     * QUESTION 3: HOW ARE THEY USED?
     * ============================================================================
     * 
     * USE 1: Store in variables
     * ─────────────────────────
     * Day myDay = Day.SUNDAY;      ← Store instance
     * Status task = Status.ACTIVE; ← Store instance
     * 
     * 
     * USE 2: Pass to methods
     * ──────────────────────
     * void processDay(Day day) { ... }
     * processDay(Day.MONDAY);  ← Pass instance
     * 
     * 
     * USE 3: Return from methods
     * ───────────────────────────
     * Day getToday() {
     *     return Day.SUNDAY;  ← Return instance
     * }
     * 
     * 
     * USE 4: Compare instances
     * ─────────────────────────
     * if (myDay == Day.SUNDAY) { ... }      ← Compare instances
     * if (status == Status.ACTIVE) { ... }  ← Compare instances
     * 
     * 
     * USE 5: Use in switch
     * ────────────────────
     * switch (myDay) {
     *     case SUNDAY: System.out.println("Rest day"); break;
     *     case MONDAY: System.out.println("Work"); break;
     * }
     * 
     * 
     * USE 6: Iterate through instances
     * ─────────────────────────────────
     * for (Day d : Day.values()) {
     *     System.out.println(d);  ← Each d is an instance
     * }
     * 
     * 
     * ============================================================================
     * SIMPLE EXPLANATIONS
     * ============================================================================
     * 
     * Day.SUNDAY Analogy:
     * ──────────────────
     * Imagine SUNDAY is like a Singleton President:
     * • Only ONE President of USA exists at a time
     * • Everyone refers to the SAME President
     * • President is accessed by title: President.Obama (not created new)
     * • If you ask for President, you get the same one everyone knows
     * 
     * Similarly:
     * • Only ONE Day.SUNDAY instance exists
     * • Everyone uses the SAME SUNDAY instance
     * • Accessed directly: Day.SUNDAY (not new Day())
     * • Compare using == (it's always the same object)
     * 
     * 
     * Instance Creation:
     * ──────────────────
     * Regular objects need new:
     * • new Student()  → Creates new Student instance
     * • new Car()      → Creates new Car instance
     * 
     * Enum instances are pre-created:
     * • Day.SUNDAY     → Already created by Java
     * • Status.ACTIVE  → Already created by Java
     * • No new needed!
     * 
     * 
     * Memory Storage:
     * ───────────────
     * When you do:
     * Day d1 = Day.SUNDAY;
     * Day d2 = Day.SUNDAY;
     * Day d3 = Day.SUNDAY;
     * 
     * All three variables point to ONE object in memory:
     * 
     * Memory:
     * ┌──────────────┐
     * │ SUNDAY       │ ← ONE instance
     * │ instance     │
     * └──────┬───────┘
     *        ↑
     *    ┌───┴────────┬───────┬───────┐
     *    d1           d2      d3    Day.SUNDAY
     * 
     * All four reference the SAME SUNDAY instance
     * 
     * 
     * ============================================================================
     * WHY ENUM INSTANCES INSTEAD OF REGULAR VALUES?
     * ============================================================================
     * 
     * OLD WAY (Bad):
     * ──────────────
     * public static final int SUNDAY = 0;
     * public static final int MONDAY = 1;
     * public static final int TUESDAY = 2;
     * 
     * Problems:
     * • Can pass invalid: setDay(999);  (No error!)
     * • Not clear what 0 means
     * • Type-unsafe
     * • Easy to make mistakes
     * 
     * 
     * NEW WAY (Good):
     * ───────────────
     * enum Day { SUNDAY, MONDAY, TUESDAY }
     * 
     * Benefits:
     * • Can only pass valid Day instance: setDay(Day.SUNDAY)
     * • Self-documenting
     * • Type-safe (compiler checks)
     * • Harder to make mistakes
     * • Can store data in instances
     * 
     * 
     * ============================================================================
     * PRACTICAL COMPARISON TABLE
     * ============================================================================
     */

    public static void main(String[] args) {
        System.out.println("============ QUICK REFERENCE ============\n");

        System.out.println("Q: What is Day.SUNDAY?");
        System.out.println("A: An INSTANCE (object) of Day enum class\n");

        System.out.println("Q: Are enum constants instances?");
        System.out.println("A: YES! Special pre-created, singleton instances\n");

        System.out.println("Q: How many instances of SUNDAY exist?");
        System.out.println("A: Only ONE - it's a singleton\n");

        System.out.println("Q: Can I create new instances?");
        System.out.println("A: NO! enum has fixed instances only\n");

        System.out.println("Q: How do I access SUNDAY instance?");
        System.out.println("A: Day.SUNDAY (static access, no new keyword)\n");

        System.out.println("Q: Are Day.SUNDAY and another Day.SUNDAY same?");
        System.out.println("A: YES! Both reference the same singleton instance\n");

        System.out.println("Q: Can I compare with ==?");
        System.out.println("A: YES! Always safe with enum instances\n");

        System.out.println("Q: What methods work with instances?");
        System.out.println("A:");
        System.out.println("   • name() - returns \"SUNDAY\"");
        System.out.println("   • ordinal() - returns position (0, 1, 2...)");
        System.out.println("   • values() - returns all instances");
        System.out.println("   • valueOf(String) - converts string to instance\n");

        System.out.println("Q: Instance storage - is it memory efficient?");
        System.out.println("A: YES! Only one copy per instance, shared by all\n");

        System.out.println("Q: Can instances contain data?");
        System.out.println("A: YES! Each can have fields and methods\n");

        System.out.println("Q: Example enum with data?");
        System.out.println("A:");
        System.out.println("   enum Season {");
        System.out.println("       SUMMER(25),   ← Instance with data");
        System.out.println("       WINTER(5);    ← Instance with data");
        System.out.println("       private int temp;");
        System.out.println("       Season(int t) { this.temp = t; }");
        System.out.println("   }\n");

        System.out.println("Q: Why use enums instead of String constants?");
        System.out.println("A:");
        System.out.println("   OLD: public static final String RED = \"red\";");
        System.out.println("        Can pass: setColor(\"bluee\");  (Typo!)");
        System.out.println("");
        System.out.println("   NEW: enum Color { RED, GREEN, BLUE }");
        System.out.println("        Can only pass: setColor(Color.RED);  (Type-safe!)\n");

        System.out.println("Q: Instance vs singleton - what's difference?");
        System.out.println("A:");
        System.out.println("   Instance = object in memory");
        System.out.println("   Singleton = ONE instance shared everywhere");
        System.out.println("   Enum constants are singleton instances\n");

        // ====================================================================
        // PRACTICAL DEMONSTRATION
        // ====================================================================
        System.out.println("\n============ PRACTICAL DEMONSTRATION ============\n");

        enum Day {
            SUNDAY, MONDAY, TUESDAY
        }

        System.out.println("Creating instances:");
        Day d1 = Day.SUNDAY;
        Day d2 = Day.SUNDAY;
        Day d3 = Day.MONDAY;

        System.out.println("  Day d1 = Day.SUNDAY;");
        System.out.println("  Day d2 = Day.SUNDAY;");
        System.out.println("  Day d3 = Day.MONDAY;");

        System.out.println("\nComparison:");
        System.out.println("  d1 == d2: " + (d1 == d2) + " (same instance)");
        System.out.println("  d1 == d3: " + (d1 == d3) + " (different instance)");

        System.out.println("\nInstance information:");
        System.out.println("  d1.name(): " + d1.name());
        System.out.println("  d1.ordinal(): " + d1.ordinal());

        System.out.println("\nAll instances:");
        for (Day d : Day.values()) {
            System.out.println("  " + d);
        }

        // ====================================================================
        // KEY TAKEAWAY
        // ====================================================================
        System.out.println("\n============ KEY TAKEAWAY ============\n");

        System.out.println("Day.SUNDAY is:");
        System.out.println("✓ An INSTANCE of Day enum");
        System.out.println("✓ Automatically created by Java");
        System.out.println("✓ A SINGLETON (only one exists)");
        System.out.println("✓ STATIC member (accessed via Day.SUNDAY)");
        System.out.println("✓ IMMUTABLE (cannot be changed)");
        System.out.println("✓ Type-safe and efficient");
        System.out.println("✓ Better than strings or numbers");
    }
}

