package me.niteshh.OPPs.tutorial.enumClasses.inDepth;

import java.util.*;

/**
 * STEP 6: TIPS AND TRICKS - ENUM MASTERY
 * 
 * This file contains practical tips, tricks, and advanced techniques
 * for mastering enum usage in real-world applications.
 */

public class Step6_TipsAndTricks {

    /**
     * Enum with useful utility methods
     */
    enum StatusCode {
        SUCCESS(200),
        CREATED(201),
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        NOT_FOUND(404),
        SERVER_ERROR(500);

        private int code;

        StatusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * Enum for color with RGB values
     */
    enum Color {
        RED(255, 0, 0),
        GREEN(0, 255, 0),
        BLUE(0, 0, 255),
        WHITE(255, 255, 255),
        BLACK(0, 0, 0);

        private int r, g, b;

        Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public String toHex() {
            return String.format("#%02x%02x%02x", r, g, b);
        }

        public int getRGB() {
            return (r << 16) | (g << 8) | b;
        }
    }

    // ============= MAIN METHOD =============

    public static void main(String[] args) {
        System.out.println("===== STEP 6: TIPS AND TRICKS =====\n");

        // ============= TIP 1: REVERSE LOOKUP =============
        System.out.println("💡 TIP 1: REVERSE LOOKUP FROM VALUE:\n");

        /*
         * Tip: Create utility method to find enum by code/value
         * This is commonly needed when converting from database or API
         */

        int code = 404;
        StatusCode status = findStatusByCode(code);
        System.out.println("  Status code " + code + " -> " + status);

        // ============= TIP 2: SAFE STRING CONVERSION =============
        System.out.println("\n💡 TIP 2: SAFE STRING TO ENUM CONVERSION:\n");

        /*
         * Tip: Handle valueOf() gracefully with exception handling
         * Use factory methods instead of raw valueOf()
         */

        String colorName = "RED";
        try {
            Color color = Color.valueOf(colorName);
            System.out.println("  Color \"" + colorName + "\" -> " + color);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ Invalid color: " + colorName);
        }

        // Better approach with factory method:
        Color safeColor = parseColor("INVALID");
        System.out.println("  Safe parse result: " + (safeColor != null ? safeColor : "null (with default)"));

        // ============= TIP 3: ENUM AS STATE MACHINE =============
        System.out.println("\n💡 TIP 3: ENUM AS STATE MACHINE:\n");

        /*
         * Tip: Use enums to represent state transitions
         * Much cleaner than manual state checking
         */

        enum OrderState {
            PENDING {
                @Override
                OrderState nextState() {
                    return PROCESSING;
                }
            },
            PROCESSING {
                @Override
                OrderState nextState() {
                    return SHIPPED;
                }
            },
            SHIPPED {
                @Override
                OrderState nextState() {
                    return DELIVERED;
                }
            },
            DELIVERED {
                @Override
                OrderState nextState() {
                    return this;  // Final state
                }
            };

            abstract OrderState nextState();
        }

        // State machine in action
        OrderState currentState = OrderState.PENDING;
        System.out.println("  Current state: " + currentState);

        currentState = currentState.nextState();
        System.out.println("  After transition: " + currentState);

        // ============= TIP 4: FILTERING COLLECTIONS =============
        System.out.println("\n💡 TIP 4: FILTERING ENUM COLLECTIONS:\n");

        /*
         * Tip: Use streams to filter and process enums efficiently
         */

        System.out.println("  Colors with RGB sum > 400:");
        for (Color c : Color.values()) {
            int sum = c.r + c.g + c.b;
            if (sum > 400) {
                System.out.println("    " + c + " (sum: " + sum + ")");
            }
        }

        // ============= TIP 5: CACHING ENUM MAPS =============
        System.out.println("\n💡 TIP 5: PRE-COMPUTE ENUM DATA:\n");

        /*
         * Tip: Pre-compute data once and cache in EnumMap
         * Better than computing same values multiple times
         */

        Map<Color, String> colorHexCache = new EnumMap<>(Color.class);
        for (Color c : Color.values()) {
            colorHexCache.put(c, c.toHex());
        }

        System.out.println("  Cached hex values:");
        for (Color c : Color.values()) {
            System.out.println("    " + c + " = " + colorHexCache.get(c));
        }

        // ============= TIP 6: ENUM COMPARISONS =============
        System.out.println("\n💡 TIP 6: EFFICIENT ENUM COMPARISONS:\n");

        /*
         * Tip: Use == instead of equals() for enums (faster)
         * Since enum constants are singletons
         */

        Color color1 = Color.RED;
        Color color2 = Color.RED;
        Color color3 = Color.BLUE;

        System.out.println("  color1 == color2: " + (color1 == color2) + " (fast!)");
        System.out.println("  color1.equals(color2): " + color1.equals(color2));
        System.out.println("  ✓ Use == for enums (better performance)");

        // ============= TIP 7: ENUM WITH OPTIONAL DATA =============
        System.out.println("\n💡 TIP 7: HANDLING OPTIONAL ENUM DATA:\n");

        /*
         * Tip: Use Optional or null checks for optional enum data
         */

        enum Priority {
            HIGH(5, "urgent"),
            MEDIUM(3, null),  // No description
            LOW(1, "can wait");

            int level;
            String description;

            Priority(int level, String description) {
                this.level = level;
                this.description = description;
            }

            public String getDescription() {
                return description != null ? description : "No description";
            }
        }

        System.out.println("  Priority descriptions:");
        for (Priority p : Priority.values()) {
            System.out.println("    " + p + ": " + p.getDescription());
        }

        // ============= TIP 8: BATCH OPERATIONS =============
        System.out.println("\n💡 TIP 8: BATCH OPERATIONS WITH ENUMS:\n");

        /*
         * Tip: Process multiple enums at once using EnumSet
         * Efficient bit-based storage
         */

        EnumSet<StatusCode> successCodes = EnumSet.of(
            StatusCode.SUCCESS,
            StatusCode.CREATED
        );

        System.out.println("  Success codes: " + successCodes);

        EnumSet<StatusCode> errorCodes = EnumSet.complementOf(successCodes);
        System.out.println("  Error codes: " + errorCodes);

        // ============= TIP 9: ENUM NAMING CONVENTIONS =============
        System.out.println("\n💡 TIP 9: NAMING CONVENTIONS:\n");

        /*
         * Tip: Follow conventions for better code clarity
         * 
         * Good:
         * - Use UPPER_CASE for constants: RED, BLUE, HIGH, LOW
         * - Use meaningful names: PROCESSING, SHIPPED, not P, S
         * - Use singular names: State (not States)
         * 
         * Bad:
         * - Lowercase: red, blue (inconsistent)
         * - Abbreviations: R, B (unclear)
         * - Plural: Colors (should be Color)
         */

        System.out.println("  ✓ UPPER_CASE for enum constants");
        System.out.println("  ✓ Meaningful descriptive names");
        System.out.println("  ✓ Singular names for enum type");

        // ============= TIP 10: ENUM PERFORMANCE =============
        System.out.println("\n💡 TIP 10: ENUM PERFORMANCE:\n");

        /*
         * Tip: Enums are highly optimized
         * - Singleton per constant
         * - Zero allocation overhead
         * - Fast switch statement compilation
         */

        long startTime = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            Color c = Color.values()[i % Color.values().length];
        }
        long enumTime = System.nanoTime() - startTime;

        System.out.println("  1M enum accesses: " + (enumTime / 1_000_000) + "ms");
        System.out.println("  ✓ Enums are highly optimized");

        // ============= TIP 11: TYPE CONVERSIONS =============
        System.out.println("\n💡 TIP 11: SAFE TYPE CONVERSIONS:\n");

        /*
         * Tip: Provide conversion methods between related enums
         */

        enum Size {
            SMALL, MEDIUM, LARGE
        }

        enum Priority_2 {
            LOW, MEDIUM, HIGH
        }

        // Manual conversion (not ideal, but shows pattern)
        Size size = Size.MEDIUM;
        System.out.println("  Size: " + size);
        System.out.println("  ✓ Can create mapping methods between enums");

        // ============= TIP 12: SERIALIZATION SAFETY =============
        System.out.println("\n💡 TIP 12: SERIALIZATION SAFETY:\n");

        /*
         * Tip: Enums handle serialization automatically
         * Safe to use in serialized objects
         */

        System.out.println("  ✓ Enums are serialization-safe");
        System.out.println("  ✓ Singleton pattern preserved after deserialization");
        System.out.println("  ✓ Can safely use in hashCode/equals");

        // ============= SUMMARY =============
        System.out.println("\n===== TIPS AND TRICKS SUMMARY =====");
        System.out.println("✓ Create factory methods for reverse lookup");
        System.out.println("✓ Use enums for state machines");
        System.out.println("✓ Pre-compute and cache enum data");
        System.out.println("✓ Use == for enum comparisons (faster)");
        System.out.println("✓ Use EnumMap and EnumSet for efficiency");
        System.out.println("✓ Follow UPPER_CASE naming convention");
        System.out.println("✓ Enums are naturally serialization-safe");
        System.out.println("✓ Perfect for strategy pattern");
    }

    // ============= HELPER METHODS =============

    /**
     * Find status by code safely
     */
    static StatusCode findStatusByCode(int code) {
        for (StatusCode status : StatusCode.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return StatusCode.SERVER_ERROR;  // Default
    }

    /**
     * Safe color parsing
     */
    static Color parseColor(String name) {
        try {
            return Color.valueOf(name);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ Invalid color name, using default");
            return Color.BLACK;  // Default
        }
    }
}

