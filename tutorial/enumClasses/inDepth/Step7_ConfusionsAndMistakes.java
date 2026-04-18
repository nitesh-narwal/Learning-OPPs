package me.niteshh.OPPs.tutorial.enumClasses.inDepth;

import java.util.*;

/**
 * STEP 7: CONFUSIONS AND COMMON MISTAKES WITH ENUMS
 * 
 * This file documents common mistakes and confusing aspects when using enums.
 * Ranges from small issues to critical production bugs.
 */

public class Step7_ConfusionsAndMistakes {

    /**
     * Sample enum for demonstrations
     */
    enum Status {
        ACTIVE, INACTIVE, PENDING
    }

    enum Level {
        LOW(1),
        MEDIUM(2),
        HIGH(3);

        private int value;

        Level(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // ============= MAIN METHOD =============

    public static void main(String[] args) {
        System.out.println("===== CONFUSIONS AND COMMON MISTAKES =====\n");

        // ============= MISTAKE 1: Using new keyword with enums (COMPILE ERROR) =============
        System.out.println("❌ MISTAKE 1: Trying to create enum with new keyword\n");

        /*
         * The Problem:
         * Enums cannot be instantiated with new keyword
         * You can only use predefined constants
         */

        System.out.println("  WRONG: Status status = new Status();");
        System.out.println("  This causes: COMPILATION ERROR");
        System.out.println("  Enums cannot be instantiated with 'new'");

        System.out.println("\n  CORRECT: Status status = Status.ACTIVE;");
        System.out.println("  Use predefined enum constants");

        Status correctStatus = Status.ACTIVE;
        System.out.println("  Result: " + correctStatus);

        System.out.println("\n📌 KEY LESSON: Enums are singleton constants, no new keyword!");

        // ============= MISTAKE 2: Using equals() instead of == (PERFORMANCE) =============
        System.out.println("\n\n❌ MISTAKE 2: Using equals() instead of == for comparison\n");

        /*
         * The Problem:
         * While both work, == is preferred and faster
         * equals() involves method call overhead
         */

        Status s1 = Status.ACTIVE;
        Status s2 = Status.ACTIVE;

        System.out.println("  Using ==: " + (s1 == s2));
        System.out.println("  Using equals(): " + s1.equals(s2));
        System.out.println("  Both work, but == is faster for enums");

        System.out.println("\n  BETTER: Use == for enum comparison");
        System.out.println("  if (status == Status.ACTIVE) { }  // Fast!");

        System.out.println("\n  WRONG: Use equals()");
        System.out.println("  if (status.equals(Status.ACTIVE)) { }  // Slower");

        System.out.println("\n📌 KEY LESSON: Use == for enums, not equals()!");

        // ============= MISTAKE 3: Assuming enum order is guaranteed (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 3: Assuming enum order won't change\n");

        /*
         * The Problem:
         * If you add new enum values in the middle, order changes
         * Code relying on ordinal() values breaks
         */

        System.out.println("  Current Level ordinals:");
        for (Level level : Level.values()) {
            System.out.println("    " + level + " = ordinal " + level.ordinal());
        }

        System.out.println("\n  ⚠️  If we add CRITICAL between MEDIUM and HIGH:");
        System.out.println("    HIGH's ordinal would change from 2 to 3");
        System.out.println("    Stored database records break!");

        System.out.println("\n  BETTER: Store enum name or constant value");
        System.out.println("  NOT: Store ordinal() value");

        System.out.println("\n📌 KEY LESSON: Don't rely on ordinal() for data persistence!");

        // ============= MISTAKE 4: Case sensitivity in valueOf() (SMALL) =============
        System.out.println("\n\n❌ MISTAKE 4: Case sensitivity in valueOf()\n");

        /*
         * The Problem:
         * valueOf() is case-sensitive and throws exception
         */

        System.out.println("  Correct: Status.valueOf(\"ACTIVE\")");
        Status s = Status.valueOf("ACTIVE");
        System.out.println("  Result: " + s);

        System.out.println("\n  WRONG: Status.valueOf(\"active\")");
        try {
            Status wrong = Status.valueOf("active");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ Throws IllegalArgumentException: No enum constant");
        }

        System.out.println("\n  WRONG: Status.valueOf(\"Active\")");
        try {
            Status wrong = Status.valueOf("Active");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ Throws IllegalArgumentException: No enum constant");
        }

        System.out.println("\n📌 KEY LESSON: valueOf() is case-sensitive!");

        // ============= MISTAKE 5: Forgetting factory method for invalid input (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 5: Not handling invalid valueOf() input\n");

        /*
         * The Problem:
         * User input might be invalid, causing crash
         */

        String userInput = "UNKNOWN";
        System.out.println("  User input: \"" + userInput + "\"");

        System.out.println("\n  WRONG: Direct valueOf without handling");
        try {
            Status status = Status.valueOf(userInput);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ CRASH: IllegalArgumentException!");
        }

        System.out.println("\n  CORRECT: Use safe factory method");
        Status safeStatus = safeValueOf(userInput);
        System.out.println("  Result: " + safeStatus + " (with default)");

        System.out.println("\n📌 KEY LESSON: Always handle valueOf() exceptions!");

        // ============= MISTAKE 6: Modifying enum fields (IMPOSSIBLE) =============
        System.out.println("\n\n❌ MISTAKE 6: Thinking enums are mutable\n");

        /*
         * The Problem:
         * Enum fields should be private and final
         * Trying to modify breaks immutability
         */

        System.out.println("  Enum Level has value field");
        Level level = Level.HIGH;
        System.out.println("  Level.HIGH.getValue(): " + level.getValue());

        System.out.println("\n  ⚠️  Fields must be private final");
        System.out.println("  Cannot do: level.value = 10;  (compilation error)");
        System.out.println("  Cannot modify enum constants");

        System.out.println("\n📌 KEY LESSON: Enums are immutable by design!");

        // ============= MISTAKE 7: Null enum references (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 7: Null enum references (CRITICAL)\n");

        /*
         * The Problem:
         * Enum reference can be null from database
         * Causes NullPointerException when accessing
         */

        Status nullStatus = null;
        System.out.println("  Status nullStatus = null;  (from database)");

        System.out.println("\n  WRONG: Direct comparison");
        try {
            if (nullStatus == Status.ACTIVE) {
                System.out.println("  Active");
            }
        } catch (Exception e) {
            System.out.println("  No error here, but comparison looks wrong");
        }

        System.out.println("\n  WRONG: Calling method on null");
        try {
            String name = nullStatus.name();
        } catch (NullPointerException e) {
            System.out.println("  ✗ NullPointerException when calling name()!");
        }

        System.out.println("\n  CORRECT: Always null-check enum references");
        if (nullStatus != null && nullStatus == Status.ACTIVE) {
            System.out.println("  Active");
        }

        System.out.println("\n📌 KEY LESSON: Always null-check enum references!");

        // ============= MISTAKE 8: Using enums as keys without proper hashCode (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 8: Poor enum usage in HashMap\n");

        /*
         * The Problem:
         * While enums are safe in HashMap, using wrong map type wastes memory
         */

        System.out.println("  WRONG: Using HashMap with enum keys");
        Map<Level, String> wrongMap = new HashMap<>();
        wrongMap.put(Level.HIGH, "High priority");
        System.out.println("  HashMap uses more memory");

        System.out.println("\n  CORRECT: Using EnumMap with enum keys");
        Map<Level, String> rightMap = new EnumMap<>(Level.class);
        rightMap.put(Level.HIGH, "High priority");
        System.out.println("  EnumMap is more efficient");

        System.out.println("\n📌 KEY LESSON: Use EnumMap for enum keys!");

        // ============= MISTAKE 9: Extending enums (IMPOSSIBLE) =============
        System.out.println("\n\n❌ MISTAKE 9: Trying to extend enums\n");

        /*
         * The Problem:
         * Cannot extend or create subclasses of enums
         */

        System.out.println("  ⚠️  Cannot do: class MyStatus extends Status { }");
        System.out.println("  Compilation error: Cannot extend enum");
        System.out.println("  Enums are final by default");

        System.out.println("\n  WORKAROUND: Use composition if needed");
        System.out.println("  Or create interface that enum implements");

        System.out.println("\n📌 KEY LESSON: Enums cannot be extended!");

        // ============= MISTAKE 10: Comparing different enum types (COMPILE ERROR) =============
        System.out.println("\n\n❌ MISTAKE 10: Comparing enums of different types\n");

        /*
         * The Problem:
         * Cannot compare Status with Level enums
         */

        Status status = Status.ACTIVE;
        Level levels = Level.HIGH;

        System.out.println("  Status status = Status.ACTIVE;");
        System.out.println("  Level level = Level.HIGH;");

        System.out.println("\n  WRONG: status == level");
        System.out.println("  Compilation error: Incompatible types");

        System.out.println("\n  CORRECT: Compare same type");
        Status other = Status.PENDING;
        System.out.println("  status == other: " + (status == other));

        System.out.println("\n📌 KEY LESSON: Can only compare enums of same type!");

        // ============= MISTAKE 11: Assuming ordinal() for storage (CRITICAL) =============
        System.out.println("\n\n❌ MISTAKE 11: Storing enum ordinal() in database (CRITICAL)\n");

        /*
         * The Problem:
         * Ordinal changes if enum values are reordered
         * Database records become invalid
         */

        System.out.println("  Current Level ordinals in database:");
        System.out.println("    LOW = 0");
        System.out.println("    MEDIUM = 1");
        System.out.println("    HIGH = 2");

        System.out.println("\n  ⚠️  If someone adds CRITICAL at position 2:");
        System.out.println("    OLD database: 2 = HIGH");
        System.out.println("    NEW database: 2 = CRITICAL (WRONG!)");

        System.out.println("\n  CORRECT: Store enum name or dedicated code");
        System.out.println("  Storage: Level.name() or custom code property");

        System.out.println("\n📌 KEY LESSON: NEVER store ordinal() in database!");

        // ============= MISTAKE 12: Not providing valueOf for input (MEDIUM) =============
        System.out.println("\n\n❌ MISTAKE 12: No safe parsing method\n");

        /*
         * The Problem:
         * Raw valueOf() can crash application
         */

        System.out.println("  WRONG: Raw API design");
        System.out.println("  API returns: Status.valueOf(input)  // Can crash");

        System.out.println("\n  CORRECT: Provide safe factory method");
        System.out.println("  API returns: StatusParser.parse(input)  // Never crashes");

        System.out.println("\n📌 KEY LESSON: Always wrap valueOf in safe methods!");

        // ============= MISTAKE 13: Large switch statements (STYLE) =============
        System.out.println("\n\n❌ MISTAKE 13: Overly complex switch statements\n");

        /*
         * The Problem:
         * Switch statements with enums should be simple
         * Avoid complex logic inside switch
         */

        System.out.println("  WRONG: Complex logic in switch");
        System.out.println("  switch(status) {");
        System.out.println("      case ACTIVE: ");
        System.out.println("          // 50 lines of complex code");
        System.out.println("  }");

        System.out.println("\n  CORRECT: Extract to methods");
        System.out.println("  switch(status) {");
        System.out.println("      case ACTIVE: handleActive(); break;");
        System.out.println("  }");

        System.out.println("\n📌 KEY LESSON: Keep switch cases simple!");

        // ============= SUMMARY =============
        System.out.println("\n\n===== COMMON MISTAKES SUMMARY =====");
        System.out.println("1. ❌ Using new keyword with enums");
        System.out.println("2. ❌ Using equals() instead of ==");
        System.out.println("3. ❌ Relying on ordinal() order");
        System.out.println("4. ❌ Forgetting case sensitivity in valueOf()");
        System.out.println("5. ❌ Not handling valueOf() exceptions");
        System.out.println("6. ❌ Thinking enums are mutable");
        System.out.println("7. ❌ Not null-checking enum references (CRITICAL)");
        System.out.println("8. ❌ Using HashMap instead of EnumMap");
        System.out.println("9. ❌ Trying to extend enums");
        System.out.println("10. ❌ Comparing different enum types");
        System.out.println("11. ❌ Storing ordinal() in database (CRITICAL)");
        System.out.println("12. ❌ Not providing safe parsing methods");
        System.out.println("13. ❌ Complex logic in switch cases");
    }

    // ============= HELPER METHODS =============

    /**
     * Safe valueOf with default
     */
    static Status safeValueOf(String value) {
        try {
            return Status.valueOf(value);
        } catch (IllegalArgumentException e) {
            return Status.PENDING;  // Default
        }
    }
}

