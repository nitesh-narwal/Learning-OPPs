package me.niteshh.OPPs.tutorial.langPackage.usefullFunctions;

/**
 * STEP 3: SYSTEM CLASS - SYSTEM UTILITIES
 * 
 * System class provides access to system resources and environment.
 * Used for console I/O, timing, environment variables, and more.
 * 
 * Key Concepts:
 * - System.out, System.in, System.err
 * - System timing with currentTimeMillis() and nanoTime()
 * - System properties and environment variables
 * - Garbage collection and memory info
 */

public class Step3_SystemClass_Essentials {

    public static void main(String[] args) {
        System.out.println("===== STEP 3: SYSTEM CLASS - SYSTEM UTILITIES =====\n");

        // ============= 1. STANDARD I/O STREAMS =============
        System.out.println("1️⃣  STANDARD I/O STREAMS:\n");

        System.out.println("  System.out: Standard output stream (console)");
        System.out.println("  System.err: Error output stream (console, usually red)");
        System.out.println("  System.in: Standard input stream (keyboard)");

        // Using System.err for errors
        System.err.println("  ⚠️  This is an error message (printed to System.err)");

        System.out.println("\n  Purpose: Interact with console I/O");

        // ============= 2. CURRENT TIME METHODS =============
        System.out.println("\n2️⃣  TIMING METHODS:\n");

        // currentTimeMillis() - Returns milliseconds since Jan 1, 1970
        long millis = System.currentTimeMillis();
        System.out.println("  System.currentTimeMillis(): " + millis);
        System.out.println("  Represents: Milliseconds since January 1, 1970 (Epoch)");

        // nanoTime() - Returns nanoseconds with arbitrary origin
        long nanos = System.nanoTime();
        System.out.println("\n  System.nanoTime(): " + nanos);
        System.out.println("  More precise: Nanosecond precision");

        // Practical use: Measuring execution time
        System.out.println("\n  ✓ PRACTICAL: Measuring code execution:");
        long startTime = System.currentTimeMillis();
        
        // Simulate some work
        long sum = 0;
        for (int i = 0; i < 1_000_000; i++) {
            sum += i;
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("  Execution time: " + (endTime - startTime) + " milliseconds");

        // ============= 3. SYSTEM PROPERTIES =============
        System.out.println("\n3️⃣  SYSTEM PROPERTIES:\n");

        // getProperty(key) - Get system property
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");
        String userHome = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");

        System.out.println("  os.name: " + osName);
        System.out.println("  java.version: " + javaVersion);
        System.out.println("  user.home: " + userHome);
        System.out.println("  file.separator: " + fileSeparator);

        System.out.println("\n  Purpose: Access system-specific information");

        // ============= 4. ENVIRONMENT VARIABLES =============
        System.out.println("\n4️⃣  ENVIRONMENT VARIABLES:\n");

        // getenv(name) - Get environment variable
        String pathEnv = System.getenv("PATH");
        String homeEnv = System.getenv("HOME");

        System.out.println("  PATH: " + (pathEnv != null ? pathEnv.substring(0, 50) + "..." : "Not set"));
        System.out.println("  HOME: " + homeEnv);
        System.out.println("  Purpose: Access OS environment variables");

        // ============= 5. MEMORY INFORMATION =============
        System.out.println("\n5️⃣  MEMORY INFORMATION:\n");

        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.println("  Total Memory: " + formatBytes(totalMemory));
        System.out.println("  Used Memory: " + formatBytes(usedMemory));
        System.out.println("  Free Memory: " + formatBytes(freeMemory));
        System.out.println("  Max Memory: " + formatBytes(maxMemory));
        System.out.println("  Purpose: Monitor JVM memory usage");

        // ============= 6. GARBAGE COLLECTION =============
        System.out.println("\n6️⃣  GARBAGE COLLECTION:\n");

        System.out.println("  System.gc() - Suggests garbage collection");
        System.out.println("  ⚠️  NOT guaranteed to run immediately");
        System.out.println("  ⚠️  Use only when necessary, not in performance-critical code");

        long memoryBefore = runtime.freeMemory();
        System.gc();  // Suggest garbage collection
        long memoryAfter = runtime.freeMemory();

        System.out.println("  Memory freed: " + formatBytes(memoryAfter - memoryBefore));

        // ============= 7. ARRAY COPY =============
        System.out.println("\n7️⃣  ARRAY COPY - System.arraycopy():\n");

        int[] source = {1, 2, 3, 4, 5};
        int[] destination = new int[5];

        System.out.println("  source: " + java.util.Arrays.toString(source));
        System.out.println("  destination (before): " + java.util.Arrays.toString(destination));

        // arraycopy(src, srcPos, dest, destPos, length)
        System.arraycopy(source, 0, destination, 0, 3);
        System.out.println("  After System.arraycopy(source, 0, dest, 0, 3):");
        System.out.println("  destination (after): " + java.util.Arrays.toString(destination));
        System.out.println("  Purpose: Fast array copying (native implementation)");

        // ============= 8. PROGRAM TERMINATION =============
        System.out.println("\n8️⃣  PROGRAM TERMINATION:\n");

        System.out.println("  System.exit(code) - Terminates JVM");
        System.out.println("  Exit code 0: Success");
        System.out.println("  Exit code 1: Error/Failure");
        System.out.println("  ⚠️  Be careful - terminates entire program!");

        // ============= 9. CONSOLE OUTPUT FORMATTING =============
        System.out.println("\n9️⃣  CONSOLE OUTPUT FORMATTING:\n");

        String name = "Alice";
        int score = 95;
        double percentage = 87.5;

        System.out.println("  Using String concatenation:");
        System.out.println("  Result: " + name + " scored " + score + " points");

        System.out.println("\n  Using String.format():");
        String formatted = String.format("%-10s scored %3d points (%.1f%%)", name, score, percentage);
        System.out.println("  Result: " + formatted);

        System.out.println("\n  Using System.out.printf():");
        System.out.printf("  Result: %-10s scored %3d points (%.1f%%)%n", name, score, percentage);

        // ============= 10. PRACTICAL EXAMPLE: Performance TESTING =============
        System.out.println("\n🔟  PRACTICAL EXAMPLE: Performance Testing:\n");

        performanceTest();

        // ============= SUMMARY =============
        System.out.println("\n===== SUMMARY =====");
        System.out.println("✓ System.out/err for console I/O");
        System.out.println("✓ currentTimeMillis() for time-based operations");
        System.out.println("✓ getProperty() for system information");
        System.out.println("✓ getenv() for environment variables");
        System.out.println("✓ Runtime for memory and garbage collection info");
        System.out.println("✓ arraycopy() for efficient array operations");
    }

    // ============= HELPER METHODS =============

    /**
     * Format bytes to readable format (KB, MB, GB)
     */
    static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }

    /**
     * Practical example: Performance comparison
     */
    static void performanceTest() {
        System.out.println("  Comparing loop performance:\n");

        // Method 1: For loop
        long startNano = System.nanoTime();
        for (int i = 0; i < 10_000_000; i++) {
            // Loop work
        }
        long forLoopTime = System.nanoTime() - startNano;

        // Method 2: While loop
        startNano = System.nanoTime();
        int i = 0;
        while (i < 10_000_000) {
            i++;
        }
        long whileLoopTime = System.nanoTime() - startNano;

        System.out.println("  For loop (10M iterations): " + (forLoopTime / 1_000_000) + " ms");
        System.out.println("  While loop (10M iterations): " + (whileLoopTime / 1_000_000) + " ms");
        System.out.println("  Difference: " + Math.abs(forLoopTime - whileLoopTime) / 1_000_000 + " ms");
    }
}

