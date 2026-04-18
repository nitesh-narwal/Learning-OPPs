package me.niteshh.OPPs.tutorial.enumClasses.inDepth;

import java.util.*;

/**
 * STEP 5: ENUM BEST PRACTICES AND ADVANCED TECHNIQUES
 * 
 * This step covers:
 * - When to use enums vs other approaches
 * - Performance considerations
 * - Thread safety with enums
 * - Custom enum maps and sets
 * - Design patterns with enums
 */

public class Step5_EnumBestPractices {

    /**
     * User preference settings enum
     * Shows best practices for configuration enums
     */
    enum PreferenceLevel {
        // Preferences with priorities
        LOW(1),
        MEDIUM(2),
        HIGH(3),
        CRITICAL(4);

        private int priority;

        PreferenceLevel(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        /**
         * Static method for getting enum from priority value
         * Best practice: Provide factory methods
         */
        public static PreferenceLevel fromPriority(int priority) {
            for (PreferenceLevel level : PreferenceLevel.values()) {
                if (level.priority == priority) {
                    return level;
                }
            }
            return MEDIUM;  // Default
        }
    }

    /**
     * Request method enum with validation
     * Shows best practice: enum validation
     */
    enum RequestMethod {
        GET("Retrieve"),
        POST("Create"),
        PUT("Update"),
        DELETE("Remove"),
        PATCH("Partial Update");

        private String description;

        RequestMethod(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        /**
         * Validate if method is safe (read-only)
         */
        public boolean isSafe() {
            return this == GET;
        }

        /**
         * Validate if method is idempotent
         */
        public boolean isIdempotent() {
            return this == GET || this == PUT || this == DELETE;
        }
    }

    /**
     * Config enum for application settings
     * Best practice: Use enums for configuration
     */
    enum Environment {
        DEVELOPMENT("dev", "localhost", 8080),
        STAGING("stage", "staging.example.com", 80),
        PRODUCTION("prod", "example.com", 80);

        private String code;
        private String host;
        private int port;

        Environment(String code, String host, int port) {
            this.code = code;
            this.host = host;
            this.port = port;
        }

        public String getCode() {
            return code;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getUrl() {
            return "http://" + host + ":" + port;
        }

        /**
         * Factory method: Best practice for enum instantiation
         */
        public static Environment fromCode(String code) {
            for (Environment env : Environment.values()) {
                if (env.code.equalsIgnoreCase(code)) {
                    return env;
                }
            }
            return DEVELOPMENT;  // Default
        }

        /**
         * Check if environment is production
         * Useful for conditional logic
         */
        public boolean isProduction() {
            return this == PRODUCTION;
        }
    }

    // ============= MAIN METHOD =============

    public static void main(String[] args) {
        System.out.println("===== STEP 5: ENUM BEST PRACTICES =====\n");

        // ============= 1. FACTORY METHODS =============
        System.out.println("1️⃣  BEST PRACTICE: FACTORY METHODS:\n");

        /*
         * Best Practice: Provide factory/lookup methods for enums
         * This makes it easier to convert from other types (string, int) to enum
         */

        // Using factory method to get enum from priority
        PreferenceLevel pref = PreferenceLevel.fromPriority(3);
        System.out.println("  PreferenceLevel.fromPriority(3): " + pref);

        Environment env = Environment.fromCode("prod");
        System.out.println("  Environment.fromCode(\"prod\"): " + env);

        // ============= 2. ENUM IMMUTABILITY =============
        System.out.println("\n2️⃣  BEST PRACTICE: ENUM IMMUTABILITY:\n");

        /*
         * Enums are inherently immutable and thread-safe
         * This is one of their biggest advantages over regular classes
         * 
         * You cannot:
         * - Create new instances (no new keyword allowed)
         * - Modify enum constants
         * - Extend enums
         */

        System.out.println("  Enums are immutable and thread-safe by default");
        System.out.println("  No synchronization needed");
        System.out.println("  Safe to share across threads");

        // ============= 3. VALIDATION USING ENUMS =============
        System.out.println("\n3️⃣  BEST PRACTICE: VALIDATION WITH ENUMS:\n");

        /*
         * Use enums to validate input and represent valid states
         * This replaces magic strings or numbers
         */

        RequestMethod method = RequestMethod.POST;
        System.out.println("  Request method: " + method);
        System.out.println("  Description: " + method.getDescription());
        System.out.println("  Is safe? " + method.isSafe());
        System.out.println("  Is idempotent? " + method.isIdempotent());

        System.out.println("\n  All request methods:");
        for (RequestMethod m : RequestMethod.values()) {
            System.out.println("    " + m + ": " + (m.isSafe() ? "SAFE" : "NOT SAFE") + 
                             ", Idempotent: " + m.isIdempotent());
        }

        // ============= 4. CONFIGURATION ENUMS =============
        System.out.println("\n4️⃣  BEST PRACTICE: CONFIGURATION ENUMS:\n");

        /*
         * Use enums for application configuration
         * Provides compile-time safety instead of magic strings
         */

        Environment currentEnv = Environment.PRODUCTION;
        System.out.println("  Current environment: " + currentEnv);
        System.out.println("  URL: " + currentEnv.getUrl());
        System.out.println("  Is Production? " + currentEnv.isProduction());

        // Configuration-based behavior
        if (currentEnv.isProduction()) {
            System.out.println("  ⚠️  Running in PRODUCTION - extra caution needed");
        } else {
            System.out.println("  ✓ Running in non-production environment");
        }

        // ============= 5. ENUM MAPS =============
        System.out.println("\n5️⃣  BEST PRACTICE: ENUMMAP FOR PERFORMANCE:\n");

        /*
         * Best Practice: Use EnumMap instead of HashMap for enum keys
         * EnumMap is more efficient (uses array internally)
         * Better performance and memory usage
         */

        // Regular HashMap
        Map<Environment, String> descriptions = new HashMap<>();
        descriptions.put(Environment.DEVELOPMENT, "Local development");
        descriptions.put(Environment.STAGING, "Testing environment");
        descriptions.put(Environment.PRODUCTION, "Live environment");

        System.out.println("  HashMap with enum keys:");
        for (Environment e : Environment.values()) {
            System.out.println("    " + e + ": " + descriptions.get(e));
        }

        // EnumMap (Better performance!)
        EnumMap<Environment, String> enumDescriptions = new EnumMap<>(Environment.class);
        enumDescriptions.putAll(descriptions);

        System.out.println("\n  EnumMap (more efficient):");
        for (Environment e : Environment.values()) {
            System.out.println("    " + e + ": " + enumDescriptions.get(e));
        }

        // ============= 6. ENUM SETS =============
        System.out.println("\n6️⃣  BEST PRACTICE: ENUMSET FOR COLLECTIONS:\n");

        /*
         * Best Practice: Use EnumSet instead of HashSet for enums
         * EnumSet uses bit vectors, more efficient
         */

        // Create a set of safe request methods
        EnumSet<RequestMethod> safeMethods = EnumSet.noneOf(RequestMethod.class);

        for (RequestMethod m : RequestMethod.values()) {
            if (m.isSafe()) {
                safeMethods.add(m);
            }
        }

        System.out.println("  Safe HTTP methods: " + safeMethods);

        // Create all idempotent methods
        EnumSet<RequestMethod> idempotent = EnumSet.noneOf(RequestMethod.class);
        for (RequestMethod m : RequestMethod.values()) {
            if (m.isIdempotent()) {
                idempotent.add(m);
            }
        }

        System.out.println("  Idempotent methods: " + idempotent);

        // ============= 7. TYPE SAFETY =============
        System.out.println("\n7️⃣  BEST PRACTICE: TYPE SAFETY:\n");

        /*
         * Enums provide compile-time type safety
         * Impossible to pass invalid values
         */

        // This is type-safe
        Environment env1 = Environment.DEVELOPMENT;
        System.out.println("  env1 = Environment.DEVELOPMENT: Type-safe");

        // This would NOT compile:
        // Environment env2 = Environment.INVALID;  // Compilation error!

        // ============= 8. SINGLETON PATTERN =============
        System.out.println("\n8️⃣  BEST PRACTICE: ENUM SINGLETON:\n");

        /*
         * Enums are the best way to implement singleton pattern
         * Automatically thread-safe, handles serialization, prevents reflection
         */

        System.out.println("  Each enum constant is a singleton");
        System.out.println("  Environment.PRODUCTION is always the same instance");
        System.out.println("  Multiple threads can safely share enum constants");

        // ============= 9. WHEN TO USE ENUMS =============
        System.out.println("\n9️⃣  WHEN TO USE ENUMS:\n");

        /*
         * Use enums when:
         * 1. You have a fixed set of related constants
         * 2. Values don't change at runtime
         * 3. Need type safety
         * 4. Need switch statement support
         * 5. Values represent a type or state
         */

        System.out.println("  ✓ Fixed set of related constants");
        System.out.println("  ✓ Type-safe representation");
        System.out.println("  ✓ Switch statement optimized");
        System.out.println("  ✓ Immutable and thread-safe");
        System.out.println("  ✓ Can contain data and methods");

        // ============= 10. WHEN NOT TO USE ENUMS =============
        System.out.println("\n🔟  WHEN NOT TO USE ENUMS:\n");

        /*
         * Don't use enums when:
         * 1. Values change at runtime
         * 2. Need dynamic values from database
         * 3. Too many constants (readability issue)
         * 4. Values are user-defined
         */

        System.out.println("  ✗ Values loaded from database");
        System.out.println("  ✗ Values created at runtime");
        System.out.println("  ✗ Very large number of constants");
        System.out.println("  ✗ User-defined/dynamic values");

        // ============= SUMMARY =============
        System.out.println("\n===== BEST PRACTICES SUMMARY =====");
        System.out.println("✓ Use factory methods for enum lookup");
        System.out.println("✓ Enums are inherently thread-safe");
        System.out.println("✓ Use for configuration and state representation");
        System.out.println("✓ Use EnumMap/EnumSet for better performance");
        System.out.println("✓ Prefer enums over if-else chains");
        System.out.println("✓ Use for type-safe validation");
        System.out.println("✓ Replaces singleton pattern effectively");
    }
}

