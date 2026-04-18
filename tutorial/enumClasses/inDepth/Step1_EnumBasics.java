package me.niteshh.OPPs.tutorial.enumClasses.inDepth;

/**
 * STEP 1: ENUMERATION BASICS
 * 
 * What is an Enum?
 * ===============
 * An Enum (Enumeration) is a special data type that allows you to define
 * a set of named constants. It's used when you want a variable to hold
 * only one value from a predefined set of constants.
 * 
 * Why Use Enums?
 * ==============
 * 1. Type-safe: Compiler checks values at compile time
 * 2. Cleaner code: Instead of magic numbers/strings (1, 2, 3 or "ACTIVE", "INACTIVE")
 * 3. Better readability: DayOfWeek.MONDAY vs 1
 * 4. Easier maintenance: All related constants in one place
 * 5. Can have methods and fields: More powerful than simple constants
 */

public class Step1_EnumBasics {

    // ============= BASIC ENUM DEFINITION =============
    /**
     * Simple enum defining directions
     * Each value is a constant direction
     */
    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    /**
     * Enum for colors
     * Each value represents a color constant
     */
    enum Color {
        RED,
        GREEN,
        BLUE,
        YELLOW,
        ORANGE
    }

    /**
     * Enum for days of the week
     * Standard enum with common use case
     */
    enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    /**
     * Enum for user roles in a system
     * Shows practical use case in real applications
     */
    enum UserRole {
        ADMIN,
        USER,
        MODERATOR,
        GUEST
    }

    // ============= USING ENUMS IN MAIN METHOD =============

    public static void main(String[] args) {
        System.out.println("===== STEP 1: ENUMERATION BASICS =====\n");

        // ============= 1. DECLARING ENUM VARIABLES =============
        System.out.println("1️⃣  DECLARING ENUM VARIABLES:\n");

        // Declare a variable of type Direction and assign a value
        Direction currentDirection = Direction.NORTH;
        System.out.println("  currentDirection = Direction.NORTH: " + currentDirection);

        // Declare another direction
        Direction targetDirection = Direction.EAST;
        System.out.println("  targetDirection = Direction.EAST: " + targetDirection);

        // ============= 2. ITERATING THROUGH ENUM VALUES =============
        System.out.println("\n2️⃣  ITERATING THROUGH ENUM VALUES:\n");

        System.out.println("  All directions in the Direction enum:");
        // values() returns array of all enum constants
        for (Direction dir : Direction.values()) {
            System.out.println("    - " + dir);
        }

        System.out.println("\n  All colors in the Color enum:");
        for (Color color : Color.values()) {
            System.out.println("    - " + color);
        }

        // ============= 3. COMPARING ENUM VALUES =============
        System.out.println("\n3️⃣  COMPARING ENUM VALUES:\n");

        Direction dir1 = Direction.NORTH;
        Direction dir2 = Direction.NORTH;
        Direction dir3 = Direction.SOUTH;

        // Use equals() or == (both work for enums)
        if (dir1 == dir2) {
            System.out.println("  dir1 and dir2 are the same direction");
        }

        if (dir1 != dir3) {
            System.out.println("  dir1 and dir3 are different directions");
        }

        // ============= 4. ENUM CONSTANT NAMES AND VALUES =============
        System.out.println("\n4️⃣  ENUM METHODS:\n");

        UserRole role = UserRole.ADMIN;

        // name() returns the enum constant as a string
        String roleName = role.name();
        System.out.println("  role.name(): " + roleName);

        // ordinal() returns the position (0-based index)
        int roleOrdinal = role.ordinal();
        System.out.println("  role.ordinal(): " + roleOrdinal);
        System.out.println("  (ADMIN is at position 0)");

        // ============= 5. CREATING ENUM FROM STRING =============
        System.out.println("\n5️⃣  CREATING ENUM FROM STRING:\n");

        // valueOf(String) returns enum constant matching the string
        String dayString = "MONDAY";
        DayOfWeek day = DayOfWeek.valueOf(dayString);
        System.out.println("  DayOfWeek.valueOf(\"MONDAY\"): " + day);

        // This also works:
        DayOfWeek friday = DayOfWeek.valueOf("FRIDAY");
        System.out.println("  DayOfWeek.valueOf(\"FRIDAY\"): " + friday);

        // ============= 6. USING ENUMS IN IF-ELSE =============
        System.out.println("\n6️⃣  USING ENUMS IN IF-ELSE:\n");

        Color selectedColor = Color.RED;

        // Check the enum value
        if (selectedColor == Color.RED) {
            System.out.println("  Selected color is RED");
        } else if (selectedColor == Color.GREEN) {
            System.out.println("  Selected color is GREEN");
        } else if (selectedColor == Color.BLUE) {
            System.out.println("  Selected color is BLUE");
        }

        // ============= 7. USING ENUMS IN SWITCH STATEMENT =============
        System.out.println("\n7️⃣  USING ENUMS IN SWITCH:\n");

        UserRole userRole = UserRole.MODERATOR;

        // Switch statement is cleaner than if-else for multiple values
        switch (userRole) {
            case ADMIN:
                System.out.println("  User has full access - Admin privileges");
                break;
            case USER:
                System.out.println("  User has standard access");
                break;
            case MODERATOR:
                System.out.println("  User can moderate content");
                break;
            case GUEST:
                System.out.println("  Guest user with limited access");
                break;
        }

        // ============= 8. PRINTING ALL ENUM CONSTANTS WITH THEIR ORDINAL =============
        System.out.println("\n8️⃣  ENUM INFORMATION:\n");

        System.out.println("  Days of week with their positions:");
        for (DayOfWeek d : DayOfWeek.values()) {
            // Combine name() and ordinal() for detailed info
            System.out.println("    " + d.ordinal() + ": " + d.name());
        }

        // ============= 9. PRACTICAL EXAMPLE: STATUS ENUM =============
        System.out.println("\n9️⃣  PRACTICAL EXAMPLE: Task Status:\n");

        /**
         * Status enum showing practical use in real application
         * Used to represent different states of a task
         */
        enum TaskStatus {
            PENDING,
            IN_PROGRESS,
            COMPLETED,
            CANCELLED
        }

        TaskStatus taskStatus = TaskStatus.IN_PROGRESS;
        System.out.println("  Current task status: " + taskStatus);

        // Display status message based on task status
        String statusMessage;
        if (taskStatus == TaskStatus.PENDING) {
            statusMessage = "Task is waiting to be started";
        } else if (taskStatus == TaskStatus.IN_PROGRESS) {
            statusMessage = "Task is currently being worked on";
        } else if (taskStatus == TaskStatus.COMPLETED) {
            statusMessage = "Task has been completed";
        } else {
            statusMessage = "Task has been cancelled";
        }
        System.out.println("  Message: " + statusMessage);

        // ============= 10. ADVANTAGES OF ENUMS =============
        System.out.println("\n🔟  ADVANTAGES OF ENUMS:\n");

        /*
         * Advantages demonstrated:
         * 
         * 1. Type Safety:
         *    - Only valid values allowed
         *    - Compiler catches invalid values
         * 
         * 2. Cleaner Code:
         *    - Instead of: if (status == 1) or if (status.equals("active"))
         *    - We use: if (status == TaskStatus.IN_PROGRESS)
         * 
         * 3. Readability:
         *    - DayOfWeek.MONDAY is clearer than 1
         *    - UserRole.ADMIN is clearer than "admin"
         * 
         * 4. Maintainability:
         *    - All related constants in one enum
         *    - Easy to add new constants
         * 
         * 5. Exhaustiveness:
         *    - Switch statement warns if you forget a case
         * 
         * 6. Built-in Methods:
         *    - values() to get all constants
         *    - valueOf() to get enum from string
         *    - name() and ordinal() for information
         */

        System.out.println("  ✓ Type-safe: Only valid enum values allowed");
        System.out.println("  ✓ Readable: Self-documenting code");
        System.out.println("  ✓ Maintainable: All constants in one place");
        System.out.println("  ✓ Powerful: Can have methods and fields");
        System.out.println("  ✓ Flexible: Iterable and comparable");
    }
}

