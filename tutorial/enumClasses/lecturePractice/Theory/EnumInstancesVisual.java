package me.niteshh.OPPs.tutorial.enumClasses.lecturePractice.Theory;

/**
 * ============================================================================
 * VISUAL EXPLANATION: ENUM CLASS vs INSTANCES
 * ============================================================================
 * 
 * This file provides visual and conceptual explanations of enum instances.
 */

public class EnumInstancesVisual {

    /*
     * ============================================================================
     * CONCEPT 1: WHAT IS AN ENUM CLASS?
     * ============================================================================
     * 
     * Think of enum like a BLUEPRINT or TEMPLATE:
     * 
     *     enum Day {
     *         SUNDAY,    ← These are INSTANCES (objects)
     *         MONDAY,    ← Each one is created from the blueprint
     *         TUESDAY,   ← Predefined, fixed set
     *         ...
     *     }
     *     
     * The enum keyword is like saying:
     * "Here are FIXED, PREDEFINED instances I want to use"
     * 
     * 
     * ============================================================================
     * CONCEPT 2: ENUM INSTANCES vs REGULAR CLASS INSTANCES
     * ============================================================================
     * 
     * REGULAR CLASS:
     * ==============
     * 
     *     class Person {
     *         String name;
     *         int age;
     *     }
     * 
     *     Usage:
     *     Person p1 = new Person();  ← YOU create instances
     *     Person p2 = new Person();  ← YOU can create as many as needed
     *     Person p3 = new Person();
     * 
     *     Result: Three DIFFERENT Person instances
     * 
     * 
     * ENUM CLASS:
     * ===========
     * 
     *     enum Color {
     *         RED,       ← Instance already created by Java
     *         GREEN,     ← Instance already created by Java
     *         BLUE       ← Instance already created by Java
     *     }
     * 
     *     Usage:
     *     Color c1 = Color.RED;     ← Use EXISTING instance
     *     Color c2 = Color.RED;     ← Same instance
     *     Color c3 = Color.GREEN;   ← Different instance
     * 
     *     You CANNOT do:
     *     Color c4 = new Color();   ← ERROR! Cannot create new
     * 
     *     Result: Only RED, GREEN, BLUE instances exist
     */

    public static void main(String[] args) {
        System.out.println("============ ENUM INSTANCES VISUAL EXPLANATION ============\n");

        // ====================================================================
        // VISUAL 1: THE DAY ENUM
        // ====================================================================
        System.out.println("VISUAL 1: DAY ENUM AND ITS INSTANCES\n");

        System.out.println("  When you write:");
        System.out.println("  ┌────────────────────────┐");
        System.out.println("  │ enum Day {             │");
        System.out.println("  │     SUNDAY,            │");
        System.out.println("  │     MONDAY,            │");
        System.out.println("  │     TUESDAY,           │");
        System.out.println("  │     ...                │");
        System.out.println("  │ }                      │");
        System.out.println("  └────────────────────────┘");

        System.out.println("\n  Java automatically creates these instances:");
        System.out.println("  ┌──────────────────────────┐");
        System.out.println("  │ Day.SUNDAY   ← Instance  │");
        System.out.println("  │ Day.MONDAY   ← Instance  │");
        System.out.println("  │ Day.TUESDAY  ← Instance  │");
        System.out.println("  │ ...          ← Instance  │");
        System.out.println("  └──────────────────────────┘");

        // ====================================================================
        // VISUAL 2: MULTIPLE VARIABLES POINTING TO SAME INSTANCE
        // ====================================================================
        System.out.println("\n\nVISUAL 2: MULTIPLE VARIABLES, SAME INSTANCE\n");

        System.out.println("  When you do:");
        System.out.println("  Day day1 = Day.SUNDAY;");
        System.out.println("  Day day2 = Day.SUNDAY;");
        System.out.println("  Day day3 = Day.SUNDAY;");

        System.out.println("\n  Memory looks like:");
        System.out.println("  ┌───────────┐");
        System.out.println("  │ SUNDAY    │ ← ONE instance in memory");
        System.out.println("  │ instance  │");
        System.out.println("  └─────┬─────┘");
        System.out.println("        ↑");
        System.out.println("    ┌───┴────────┬────────┬────────┐");
        System.out.println("    ↑            ↑        ↑        ↑");
        System.out.println("  day1         day2      day3    Day.SUNDAY");
        System.out.println("\n  All four point to the SAME instance!");

        // ====================================================================
        // VISUAL 3: DIFFERENT INSTANCES
        // ====================================================================
        System.out.println("\n\nVISUAL 3: DIFFERENT ENUM INSTANCES\n");

        System.out.println("  When you do:");
        System.out.println("  Day sunday = Day.SUNDAY;");
        System.out.println("  Day monday = Day.MONDAY;");
        System.out.println("  Day tuesday = Day.TUESDAY;");

        System.out.println("\n  Memory looks like:");
        System.out.println("  ┌──────────────┐");
        System.out.println("  │ SUNDAY       │");
        System.out.println("  │ instance     │ ← sunday");
        System.out.println("  └──────────────┘");
        System.out.println("  ┌──────────────┐");
        System.out.println("  │ MONDAY       │");
        System.out.println("  │ instance     │ ← monday");
        System.out.println("  └──────────────┘");
        System.out.println("  ┌──────────────┐");
        System.out.println("  │ TUESDAY      │");
        System.out.println("  │ instance     │ ← tuesday");
        System.out.println("  └──────────────┘");
        System.out.println("\n  Three DIFFERENT instances");

        // ====================================================================
        // VISUAL 4: HOW ENUM INSTANCES ARE CREATED
        // ====================================================================
        System.out.println("\n\nVISUAL 4: HOW ENUM INSTANCES ARE CREATED\n");

        System.out.println("  Step 1: You define enum");
        System.out.println("  ─────────────────────");
        System.out.println("  enum Status { ACTIVE, INACTIVE }");

        System.out.println("\n  Step 2: Java automatically creates");
        System.out.println("  ───────────────────────────────────");
        System.out.println("  private static final Status ACTIVE = new Status();");
        System.out.println("  private static final Status INACTIVE = new Status();");

        System.out.println("\n  Step 3: You access the created instances");
        System.out.println("  ────────────────────────────────────────────");
        System.out.println("  Status s = Status.ACTIVE;  ← Use existing instance");

        // ====================================================================
        // VISUAL 5: SINGLETON NATURE
        // ====================================================================
        System.out.println("\n\nVISUAL 5: SINGLETON NATURE\n");

        System.out.println("  Enum instances are SINGLETONS:");
        System.out.println("  Only ONE instance of each exists");

        System.out.println("\n  Analogy with real world:");
        System.out.println("  ───────────────────────");
        System.out.println("  PLANET EARTH");
        System.out.println("  ├─ There is only ONE Earth");
        System.out.println("  ├─ Everyone refers to the SAME Earth");
        System.out.println("  ├─ John's Earth == Maria's Earth == Robot's Earth");
        System.out.println("  └─ True statement: all point to same object");

        System.out.println("\n  Similarly with enums:");
        System.out.println("  ──────────────────");
        System.out.println("  enum Status { ACTIVE }");
        System.out.println("  ├─ There is only ONE ACTIVE instance");
        System.out.println("  ├─ Everyone uses the SAME ACTIVE");
        System.out.println("  ├─ task1.status == Status.ACTIVE == task2.status");
        System.out.println("  └─ True statement: all point to same instance");

        // ====================================================================
        // VISUAL 6: ENUM vs REGULAR CLASS INSTANCES
        // ====================================================================
        System.out.println("\n\nVISUAL 6: COMPARISON\n");

        System.out.println("  REGULAR CLASS:");
        System.out.println("  ──────────────");
        System.out.println("  class Animal { }");
        System.out.println("  Animal a1 = new Animal();  ← Instance 1");
        System.out.println("  Animal a2 = new Animal();  ← Instance 2");
        System.out.println("  Animal a3 = new Animal();  ← Instance 3");
        System.out.println("  a1 == a2 → false (different instances)");
        System.out.println("  Can create infinite instances!");

        System.out.println("\n  ENUM CLASS:");
        System.out.println("  ───────────");
        System.out.println("  enum Animal { DOG, CAT, BIRD }");
        System.out.println("  Animal a1 = Animal.DOG;    ← Instance 1");
        System.out.println("  Animal a2 = Animal.DOG;    ← SAME Instance 1");
        System.out.println("  Animal a3 = Animal.CAT;    ← Instance 2");
        System.out.println("  a1 == a2 → true (same instance)");
        System.out.println("  Only predefined instances available!");

        // ====================================================================
        // VISUAL 7: ACCESSING ENUM INSTANCES
        // ====================================================================
        System.out.println("\n\nVISUAL 7: ACCESSING ENUM INSTANCES\n");

        System.out.println("  Method 1: Direct access");
        System.out.println("  ──────────────────────");
        System.out.println("  Color c = Color.RED;");
        System.out.println("           ↑     ↑");
        System.out.println("       Class  Instance");

        System.out.println("\n  Method 2: Get all instances");
        System.out.println("  ──────────────────────────");
        System.out.println("  Color[] all = Color.values();");
        System.out.println("  Returns: [RED, GREEN, BLUE, ...]");

        System.out.println("\n  Method 3: Convert string to instance");
        System.out.println("  ──────────────────────────────────");
        System.out.println("  Color c = Color.valueOf(\"RED\");");
        System.out.println("  String → Instance");

        // ====================================================================
        // VISUAL 8: ENUM INSTANCE WITH DATA
        // ====================================================================
        System.out.println("\n\nVISUAL 8: ENUM INSTANCES WITH DATA\n");

        System.out.println("  enum Level { HIGH(5), MEDIUM(3), LOW(1) }");

        System.out.println("\n  Each instance stores data:");
        System.out.println("  ┌─────────────────────┐");
        System.out.println("  │ Level.HIGH          │");
        System.out.println("  │ - name: \"HIGH\"      │");
        System.out.println("  │ - value: 5          │");
        System.out.println("  └─────────────────────┘");

        System.out.println("  ┌─────────────────────┐");
        System.out.println("  │ Level.MEDIUM        │");
        System.out.println("  │ - name: \"MEDIUM\"    │");
        System.out.println("  │ - value: 3          │");
        System.out.println("  └─────────────────────┘");

        System.out.println("  ┌─────────────────────┐");
        System.out.println("  │ Level.LOW           │");
        System.out.println("  │ - name: \"LOW\"       │");
        System.out.println("  │ - value: 1          │");
        System.out.println("  └─────────────────────┘");

        // ====================================================================
        // VISUAL 9: PRACTICAL FLOW
        // ====================================================================
        System.out.println("\n\nVISUAL 9: PRACTICAL USAGE FLOW\n");

        System.out.println("  Step 1: Define enum");
        System.out.println("  ─────────────────");
        System.out.println("  enum Status { PENDING, ACTIVE, DONE }");
        System.out.println("  ↓");
        System.out.println("  Java creates: Status.PENDING, Status.ACTIVE, Status.DONE");

        System.out.println("\n  Step 2: Store instance in variable");
        System.out.println("  ──────────────────────────────────");
        System.out.println("  Status current = Status.ACTIVE;");
        System.out.println("  ↓");
        System.out.println("  'current' now references Status.ACTIVE instance");

        System.out.println("\n  Step 3: Use the instance");
        System.out.println("  ─────────────────────");
        System.out.println("  if (current == Status.ACTIVE) { ... }");
        System.out.println("  switch (current) { case ACTIVE: ... }");
        System.out.println("  ↓");
        System.out.println("  Perform actions based on instance type");

        // ====================================================================
        // KEY INSIGHTS
        // ====================================================================
        System.out.println("\n\n============ KEY INSIGHTS ============\n");

        System.out.println("  KEY 1: Instance vs Class");
        System.out.println("  ────────────────────────");
        System.out.println("  • Enum = Class (blueprint/template)");
        System.out.println("  • SUNDAY = Instance (object)");

        System.out.println("\n  KEY 2: Singleton Pattern");
        System.out.println("  ────────────────────────");
        System.out.println("  • Only ONE instance of each enum constant");
        System.out.println("  • All references point to SAME object");
        System.out.println("  • Memory efficient");

        System.out.println("\n  KEY 3: Static Access");
        System.out.println("  ────────────────────");
        System.out.println("  • Access via class: Day.SUNDAY");
        System.out.println("  • NOT via object: day.SUNDAY (wrong!)");

        System.out.println("\n  KEY 4: Type Safety");
        System.out.println("  ──────────────────");
        System.out.println("  • Only valid instances allowed");
        System.out.println("  • Compiler prevents invalid values");
        System.out.println("  • Much safer than strings or numbers");

        System.out.println("\n  KEY 5: Comparison");
        System.out.println("  ────────────────");
        System.out.println("  • Use == for enum instances (not equals)");
        System.out.println("  • Works because of singleton nature");
        System.out.println("  • Faster than equals()");
    }
}

