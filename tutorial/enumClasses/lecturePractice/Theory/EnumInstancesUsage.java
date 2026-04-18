package me.niteshh.OPPs.tutorial.enumClasses.lecturePractice.Theory;

/**
 * ============================================================================
 * PRACTICAL EXAMPLES: HOW ENUM INSTANCES ARE USED IN REAL CODE
 * ============================================================================
 * 
 * This file shows practical examples of using enum instances in various scenarios.
 */

public class EnumInstancesUsage {

    /**
     * Example 1: Simple Enum with Instances
     * 
     * This enum represents different Status states
     * Each constant is an instance of the Status enum
     */
    enum Status {
        // Each one below is an INSTANCE of Status
        // Think of it like creating object: new Status();
        PENDING,      // Instance 1
        ACTIVE,       // Instance 2
        COMPLETED,    // Instance 3
        CANCELLED     // Instance 4
    }

    /**
     * Example 2: Enum with Constructor
     * 
     * This enum shows how instances are created with constructor parameters
     */
    enum Priority {
        // When you write Priority.HIGH, Java actually calls the constructor
        // HIGH(3) calls the constructor with value 3
        LOW(1),           // Creates instance with priority 1
        MEDIUM(2),        // Creates instance with priority 2
        HIGH(3),          // Creates instance with priority 3
        CRITICAL(4);      // Creates instance with priority 4

        /*
         * Constructor for Priority
         * This is called automatically when enum instances are created
         * 
         * When Java creates Priority.HIGH, it does:
         *     HIGH = new Priority(3);  // Constructor called
         */
        private int level;

        Priority(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    /**
     * Example 3: Enum with Multiple Fields
     * 
     * Each instance can hold multiple pieces of data
     */
    enum Color {
        // Each instance stores color name and hex code
        RED("Red", "#FF0000"),
        GREEN("Green", "#00FF00"),
        BLUE("Blue", "#0000FF"),
        YELLOW("Yellow", "#FFFF00");

        /*
         * Fields for each instance
         * When you access Color.RED, you're getting an instance that has:
         *   - name: "Red"
         *   - hexCode: "#FF0000"
         */
        private String name;
        private String hexCode;

        Color(String name, String hexCode) {
            this.name = name;
            this.hexCode = hexCode;
        }

        public String getName() {
            return name;
        }

        public String getHexCode() {
            return hexCode;
        }
    }

    // ============================================================================
    // MAIN METHOD - PRACTICAL EXAMPLES
    // ============================================================================

    public static void main(String[] args) {
        System.out.println("============ ENUM INSTANCES USAGE ============\n");

        // ====================================================================
        // EXAMPLE 1: STORING ENUM INSTANCES IN VARIABLES
        // ====================================================================
        System.out.println("EXAMPLE 1: STORING ENUM INSTANCES\n");

        /*
         * When you do this:
         *     Status status = Status.ACTIVE;
         * 
         * You are:
         * 1. Creating a variable 'status' of type Status
         * 2. Assigning the ACTIVE instance to it
         * 3. Now 'status' points to the ACTIVE instance
         */

        Status status = Status.ACTIVE;
        System.out.println("  Status status = Status.ACTIVE;");
        System.out.println("  status = " + status);

        /*
         * The instance is reusable
         * You can assign the same instance to different variables
         */

        Status anotherStatus = Status.ACTIVE;
        System.out.println("\n  Status anotherStatus = Status.ACTIVE;");
        System.out.println("  anotherStatus = " + anotherStatus);

        /*
         * Both variables point to the SAME instance
         */

        System.out.println("\n  status == anotherStatus: " + (status == anotherStatus));
        System.out.println("  They are the SAME instance!");

        // ====================================================================
        // EXAMPLE 2: COMPARING ENUM INSTANCES
        // ====================================================================
        System.out.println("\n\nEXAMPLE 2: COMPARING ENUM INSTANCES\n");

        /*
         * Since each instance is a singleton, you can use == for comparison
         * This is type-safe and efficient
         */

        Status task1 = Status.ACTIVE;
        Status task2 = Status.COMPLETED;
        Status task3 = Status.ACTIVE;

        System.out.println("  Status task1 = Status.ACTIVE;");
        System.out.println("  Status task2 = Status.COMPLETED;");
        System.out.println("  Status task3 = Status.ACTIVE;");

        System.out.println("\n  Comparisons:");
        System.out.println("    task1 == task2: " + (task1 == task2) + " (false - different instances)");
        System.out.println("    task1 == task3: " + (task1 == task3) + " (true - same instance)");
        System.out.println("    task1 == Status.ACTIVE: " + (task1 == Status.ACTIVE));

        // ====================================================================
        // EXAMPLE 3: USING ENUM INSTANCES IN IF-ELSE
        // ====================================================================
        System.out.println("\n\nEXAMPLE 3: USING IN IF-ELSE\n");

        /*
         * You can check which instance is being used
         * This is type-safe - only valid instances can be used
         */

        Status currentStatus = Status.PENDING;
        System.out.println("  Status currentStatus = Status.PENDING;");

        if (currentStatus == Status.PENDING) {
            System.out.println("  if (currentStatus == Status.PENDING)");
            System.out.println("    → Condition matched!");
        } else if (currentStatus == Status.ACTIVE) {
            System.out.println("    Task is active");
        } else if (currentStatus == Status.COMPLETED) {
            System.out.println("    Task is completed");
        }

        // ====================================================================
        // EXAMPLE 4: USING ENUM INSTANCES IN SWITCH
        // ====================================================================
        System.out.println("\n\nEXAMPLE 4: USING IN SWITCH STATEMENT\n");

        /*
         * Switch statements work great with enum instances
         * You only need to specify instance names, not the type
         */

        Status orderStatus = Status.ACTIVE;
        System.out.println("  Status orderStatus = Status.ACTIVE;");
        System.out.println("  switch(orderStatus):");

        switch (orderStatus) {
            case PENDING:
                System.out.println("    case PENDING: Waiting to start");
                break;
            case ACTIVE:
                System.out.println("    case ACTIVE: Currently processing (matched!)");
                break;
            case COMPLETED:
                System.out.println("    case COMPLETED: Done");
                break;
            case CANCELLED:
                System.out.println("    case CANCELLED: Task cancelled");
                break;
        }

        // ====================================================================
        // EXAMPLE 5: PASSING ENUM INSTANCES TO METHODS
        // ====================================================================
        System.out.println("\n\nEXAMPLE 5: PASSING INSTANCES TO METHODS\n");

        /*
         * You can pass enum instances as parameters
         * This is type-safe - only valid instances accepted
         */

        System.out.println("  Method: processTask(Status status)");
        System.out.println("  Calling: processTask(Status.ACTIVE)");
        processTask(Status.ACTIVE);

        System.out.println("\n  Calling: processTask(Status.CANCELLED)");
        processTask(Status.CANCELLED);

        // ====================================================================
        // EXAMPLE 6: RETURNING ENUM INSTANCES FROM METHODS
        // ====================================================================
        System.out.println("\n\nEXAMPLE 6: RETURNING INSTANCES FROM METHODS\n");

        /*
         * Methods can return enum instances
         */

        Status returnedStatus = getDefaultStatus();
        System.out.println("  Method: getDefaultStatus()");
        System.out.println("  Returned instance: " + returnedStatus);

        // ====================================================================
        // EXAMPLE 7: ITERATING THROUGH ALL INSTANCES
        // ====================================================================
        System.out.println("\n\nEXAMPLE 7: ITERATING ALL INSTANCES\n");

        /*
         * values() returns array of all instances
         * You can iterate through them
         */

        System.out.println("  Status.values() returns all instances:");
        for (Status s : Status.values()) {
            System.out.println("    - " + s);
        }

        // ====================================================================
        // EXAMPLE 8: ENUM INSTANCES WITH DATA
        // ====================================================================
        System.out.println("\n\nEXAMPLE 8: INSTANCES WITH DATA\n");

        /*
         * Each instance can store and provide data
         */

        Priority taskPriority = Priority.CRITICAL;
        System.out.println("  Priority taskPriority = Priority.CRITICAL;");
        System.out.println("  Priority name: " + taskPriority.name());
        System.out.println("  Priority level: " + taskPriority.getLevel());

        /*
         * Different instances have different data
         */

        System.out.println("\n  All Priority instances with their levels:");
        for (Priority p : Priority.values()) {
            System.out.println("    " + p + " → Level: " + p.getLevel());
        }

        // ====================================================================
        // EXAMPLE 9: COLOR INSTANCES WITH MULTIPLE FIELDS
        // ====================================================================
        System.out.println("\n\nEXAMPLE 9: COMPLEX INSTANCES\n");

        /*
         * Instances can have multiple fields with different data
         */

        Color selectedColor = Color.RED;
        System.out.println("  Color selectedColor = Color.RED;");
        System.out.println("    Name: " + selectedColor.getName());
        System.out.println("    Hex Code: " + selectedColor.getHexCode());

        /*
         * Each instance has its own data
         */

        System.out.println("\n  All Color instances:");
        for (Color c : Color.values()) {
            System.out.println("    " + c + " → " + c.getName() + " (" + c.getHexCode() + ")");
        }

        // ====================================================================
        // EXAMPLE 10: STORING INSTANCES IN COLLECTIONS
        // ====================================================================
        System.out.println("\n\nEXAMPLE 10: STORING INSTANCES\n");

        /*
         * Enum instances can be stored in lists, sets, maps, etc.
         * This is type-safe
         */

        java.util.List<Status> statuses = new java.util.ArrayList<>();
        statuses.add(Status.ACTIVE);
        statuses.add(Status.PENDING);
        statuses.add(Status.COMPLETED);
        statuses.add(Status.ACTIVE);  // Can add same instance multiple times

        System.out.println("  List of Status instances:");
        for (Status s : statuses) {
            System.out.println("    - " + s);
        }

        System.out.println("\n  List size: " + statuses.size());
        System.out.println("  (Note: Status.ACTIVE added twice, list stores both)");

        // ====================================================================
        // EXAMPLE 11: ENUM INSTANCES AND MEMORY
        // ====================================================================
        System.out.println("\n\nEXAMPLE 11: SINGLETON NATURE\n");

        /*
         * All references to Status.ACTIVE point to the SAME object in memory
         * This saves memory and makes comparison efficient
         */

        Status ref1 = Status.ACTIVE;
        Status ref2 = Status.ACTIVE;
        Status ref3 = Status.ACTIVE;

        System.out.println("  Status ref1 = Status.ACTIVE;");
        System.out.println("  Status ref2 = Status.ACTIVE;");
        System.out.println("  Status ref3 = Status.ACTIVE;");

        System.out.println("\n  All three reference the SAME instance:");
        System.out.println("    ref1 == ref2 == ref3: " + (ref1 == ref2 && ref2 == ref3));
        System.out.println("    Memory efficient - only ONE ACTIVE instance exists");

        // ====================================================================
        // SUMMARY TABLE
        // ====================================================================
        System.out.println("\n\n============ SUMMARY TABLE ============\n");

        System.out.println("  Key Points About Enum Instances:");
        System.out.println("  ──────────────────────────────────");
        System.out.println("  1. Instance creation: Status.PENDING is an instance");
        System.out.println("  2. How created: Automatically by Java when enum defined");
        System.out.println("  3. Access method: Via class name (Status.ACTIVE)");
        System.out.println("  4. Storage: Static members of the enum class");
        System.out.println("  5. Singleton: Only one instance of each");
        System.out.println("  6. Comparison: Use == (type-safe and efficient)");
        System.out.println("  7. Immutable: Cannot be changed");
        System.out.println("  8. Data: Can store additional fields");
        System.out.println("  9. Methods: Can have instance methods");
        System.out.println("  10. Iteration: Access all via values()");
    }

    // ============================================================================
    // HELPER METHODS
    // ============================================================================

    /**
     * This method demonstrates passing enum instances as parameters
     * The parameter type is Status, so only Status instances accepted
     */
    static void processTask(Status status) {
        /*
         * The method receives an instance
         * status could be any Status instance: PENDING, ACTIVE, etc.
         */

        System.out.println("    Processing task with status: " + status);

        if (status == Status.ACTIVE) {
            System.out.println("    Action: Continue processing");
        } else if (status == Status.COMPLETED) {
            System.out.println("    Action: Mark as done");
        } else if (status == Status.CANCELLED) {
            System.out.println("    Action: Rollback changes");
        }
    }

    /**
     * This method demonstrates returning enum instances
     */
    static Status getDefaultStatus() {
        /*
         * The method returns a Status instance
         * It could return any Status instance based on logic
         */
        return Status.PENDING;  // Return a Status instance
    }
}

