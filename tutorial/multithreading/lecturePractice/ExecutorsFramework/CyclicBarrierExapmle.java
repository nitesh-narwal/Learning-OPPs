package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.concurrent.*;

public class CyclicBarrierExapmle {
    static void main() {
        /** CyclicBarrier is a synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point.
         *
         * When we use it?
         * We use it -> when we have to conform that all the threads
         *              reach to a point without any of them proceeding until all of them have reached that point.
         *              Execute first...*/
        int numberOfServices = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfServices);
        CyclicBarrier barrier = new CyclicBarrier(numberOfServices);
        executorService.submit(new DependentServices(barrier));
        executorService.submit(new DependentServices(barrier));
        executorService.submit(new DependentServices(barrier));

        System.out.println(Thread.currentThread().getName() + " :Service Completed... ");
        executorService.shutdown();
    }

    public static class  DependentServices implements Callable<String> {

        private CyclicBarrier barrier;

        public DependentServices(CyclicBarrier barrier){
            this.barrier = barrier;
        }

        @Override
        public String call() throws Exception{

                System.out.println(Thread.currentThread().getName() + ": Service Started");
                Thread.sleep(2000); // Simulate time-consuming task
                System.out.println(Thread.currentThread().getName() + ": Service Completed, waiting at barrier...");
                barrier.await(); // Wait for all threads to reach this point
                System.out.println(Thread.currentThread().getName() + ": Passed the barrier, proceeding with main task...");

                return "ok";
        }
    }
}

class CyclicBarrierExapmle2{
    static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        int numberOfServices = 4;
        ExecutorService taskExecutor = Executors.newFixedThreadPool(numberOfServices);
        CyclicBarrier barrier = new CyclicBarrier(numberOfServices, () -> {
            System.out.println("All services have reached the barrier. Executing the barrier action...");
        });

//        taskExecutor.submit(new NewDependantServices("Web Server", 2400, barrier));
//        taskExecutor.submit(new NewDependantServices("Database", 5000, barrier));
//        taskExecutor.submit(new NewDependantServices("Cache", 3000, barrier));
//        taskExecutor.submit(new NewDependantServices("Message Service", 4000, barrier));
//        barrier.await();
//        taskExecutor.shutdown();

        Thread webServer = new Thread( new NewDependantServices("Web Server", 2400, barrier));
        Thread database = new Thread( new NewDependantServices("Database", 5000, barrier));
        Thread cached = new Thread( new NewDependantServices("Cache", 3200,barrier ));
        Thread messageService = new Thread(new NewDependantServices("Messaging Service ", 4200, barrier));

        webServer.start();
        database.start();
        cached.start();
        messageService.start();

    }

    public static class NewDependantServices implements Runnable{

        private String serviceName;
        private int intitializationTime;
        private CyclicBarrier barrier;

        public NewDependantServices(String serviceName, int intitializationTime, CyclicBarrier barrier){
            this.serviceName = serviceName;
            this.intitializationTime = intitializationTime;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(serviceName + " is initializing...");
                Thread.sleep(intitializationTime); // Simulate time-consuming initialization
                System.out.println(serviceName + " has initialized, waiting at barrier...");
                barrier.await(); // Wait for all services to reach this point
                System.out.println(serviceName + " passed the barrier, proceeding with main task...");
            }catch (InterruptedException | BrokenBarrierException e){
                e.printStackTrace();
            }
        }
    }
}


class MatrixMultiplication {
    // ============================================================================
    // COMPLETE CYCLICBARRIER EXPLANATION USING MATRIX MULTIPLICATION
    // ============================================================================
    // CyclicBarrier enables parallel matrix multiplication with synchronization
    // at checkpoints to ensure all threads complete their work before proceeding
    // ============================================================================

    /*
    ========== SECTION 1: WHAT IS CYCLICBARRIER? ==========

    SIMPLE DEFINITION:
    CyclicBarrier is a synchronization primitive that allows a SET OF THREADS to
    wait for each other to reach a COMMON BARRIER POINT before proceeding.

    Think of it like a RELAY RACE:
    ├─ 4 runners positioned at different starting points
    ├─ Each runner must wait until all 4 are ready
    ├─ Referee says "On your marks!" (all waiting at barrier)
    ├─ Referee signals "GO!" (all threads released simultaneously)
    └─ All runners start the relay race together

    KEY DIFFERENCE FROM COUNTDOWNLATCH:
    CountDownLatch:
    ├─ One-time use (counter doesn't reset)
    ├─ Different roles (waiters vs signalers)
    └─ Not reusable

    CyclicBarrier:
    ├─ Reusable (counter resets automatically)
    ├─ All threads play SAME role (all must reach barrier)
    └─ Perfect for repeated synchronization

    ========== SECTION 2: WHY MATRIX MULTIPLICATION? ==========

    MATRIX MULTIPLICATION PARALLELIZATION STRATEGY:
    
    If we multiply Matrix A (3×3) with Matrix B (3×3):
    
    A = [1 2 3]    B = [1 0 0]
        [4 5 6]        [0 1 0]
        [7 8 9]        [0 0 1]

    Result C = A × B (each element calculated independently)

    PARALLELIZATION OPPORTUNITY:
    ├─ Thread 1: Calculate Row 0 of Result (c[0][0], c[0][1], c[0][2])
    ├─ Thread 2: Calculate Row 1 of Result (c[1][0], c[1][1], c[1][2])
    ├─ Thread 3: Calculate Row 2 of Result (c[2][0], c[2][1], c[2][2])
    └─ All threads work in parallel!

    SYNCHRONIZATION POINTS:
    1. Phase 1: All threads calculate their assigned rows
       └─ CyclicBarrier checkpoint: Wait until all rows calculated
    2. Phase 2: Verify results (optional second phase)
       └─ CyclicBarrier checkpoint: Reusable barrier (can use again!)
    3. Phase 3: Print results
       └─ All threads proceed together

    BENEFIT: If one thread finishes early, it waits. No race conditions!

    ========== SECTION 3: INTERNAL WORKING ==========

    STEP-BY-STEP BARRIER BEHAVIOR:

    Step 1: CREATE BARRIER
    CyclicBarrier barrier = new CyclicBarrier(3);  // 3 threads must participate
    Internal state:
    ├─ parties = 3 (number of threads)
    ├─ count = 3 (remaining threads to reach barrier)
    └─ generation = 0 (cycle counter)

    Step 2: ALL THREADS CALL await()
    Thread-1: barrier.await();  // BLOCKED, count: 3 → 2
    Thread-2: barrier.await();  // BLOCKED, count: 2 → 1
    Thread-3: barrier.await();  // count: 1 → 0, ALL RELEASED!
    
    When count reaches 0:
    ├─ All waiting threads released simultaneously
    ├─ generation incremented (0 → 1)
    ├─ count reset to 3 (for next cycle)
    └─ Next await() will block again (reusable!)

    Step 3: NEXT BARRIER CALL (Reusable!)
    Thread-1: barrier.await();  // New cycle! Can block again
    Thread-2: barrier.await();  // Different generation
    Thread-3: barrier.await();  // Will eventually release again

    KEY INSIGHT: CyclicBarrier is REUSABLE!
    └─ Perfect for repeated synchronization points in long-running tasks

    ========== SECTION 4: MATRIX MULTIPLICATION EXAMPLE ==========
    */

    static void main(String[] args) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
          System.out.println("║   CYCLICBARRIER WITH MATRIX MULTIPLICATION (Beginner Friendly)    ║");
          System.out.println("╚═══════════════════════════════════════════════════════════════════╝");

        // EXAMPLE 1: Basic Matrix Multiplication with CyclicBarrier
        example1_BasicMatrixMultiplication();

        // EXAMPLE 2: Matrix Multiplication with verification phase
        example2_MultiPhaseMatrixMultiplication();

        // EXAMPLE 3: Understanding barrier mechanics
        example3_BarrierMechanics();

        // EXAMPLE 4: Common mistakes and how to avoid
        example4_CommonMistakes();

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              CyclicBarrier Understanding Complete!                ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
    }

    // ============================================================================
    // EXAMPLE 1: BASIC MATRIX MULTIPLICATION WITH CYCLICBARRIER
    // ============================================================================
    /*
    SCENARIO: Single synchronization point
    ├─ 3 threads each calculate one row of result matrix
    ├─ All must complete their row computation
    ├─ THEN all proceed to print results
    └─ No thread progresses until all rows are done
    */

    static void example1_BasicMatrixMultiplication() {
        System.out.println("\n=== EXAMPLE 1: BASIC MATRIX MULTIPLICATION ===\n");

        // Matrix data
        int[][] matrixA = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrixB = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        int[][] resultMatrix = new int[3][3];  // Store results here

        // Create barrier for 3 threads (one thread per row)
        int numberOfThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads);

        System.out.println("Step 1: Created CyclicBarrier with " + numberOfThreads + " threads");
        System.out.println("Each thread will:");
        System.out.println("  ├─ Calculate one row of result matrix");
        System.out.println("  ├─ Wait at barrier.await()");
        System.out.println("  └─ Proceed only when all threads reach barrier\n");

        // Create threads for each row
        Thread[] threads = new Thread[numberOfThreads];
        
        for (int row = 0; row < numberOfThreads; row++) {
            final int currentRow = row;  // Final for lambda
            
            threads[row] = new Thread(() -> {
                try {
                    // PHASE 1: CALCULATE ASSIGNED ROW
                    System.out.println("  [Thread-" + currentRow + "] Starting calculation of row " + currentRow);
                    
                    // Calculate each element in this row
                    for (int col = 0; col < 3; col++) {
                        resultMatrix[currentRow][col] = 0;
                        
                        // Matrix multiplication: result[i][j] = A[i] dot B[j]
                        for (int k = 0; k < 3; k++) {
                            resultMatrix[currentRow][col] += matrixA[currentRow][k] * matrixB[k][col];
                        }
                    }
                    
                    System.out.println("  [Thread-" + currentRow + "] Completed row calculation: " + 
                                     java.util.Arrays.toString(resultMatrix[currentRow]));
                    
                    // SYNCHRONIZATION POINT: Wait for all threads
                    System.out.println("  [Thread-" + currentRow + "] Reaching barrier...");
                    barrier.await();  // BLOCKS until all 3 threads reach here
                    System.out.println("  [Thread-" + currentRow + "] Passed barrier! All rows complete!");
                    
                    // PHASE 2: PROCEED (all threads synchronized here)
                    Thread.sleep(100 * currentRow);  // Small delay for demonstration
                    System.out.println("  [Thread-" + currentRow + "] Proceeding with next phase");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threads[row].start();
        }

        // Wait for all threads to complete
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print final result
        System.out.println("\nFinal Result Matrix:");
        for (int i = 0; i < 3; i++) {
            System.out.println("  Row " + i + ": " + java.util.Arrays.toString(resultMatrix[i]));
        }
    }

    // ============================================================================
    // EXAMPLE 2: MULTI-PHASE WITH REUSABLE BARRIER
    // ============================================================================
    /*
    SCENARIO: Multiple synchronization points (demonstrating reusability)
    ├─ PHASE 1: Calculate matrix result
    ├─ BARRIER 1: Wait for all calculations
    ├─ PHASE 2: Verify results (optional check)
    ├─ BARRIER 2: Wait for verification (reuses same barrier!)
    ├─ PHASE 3: Print results
    └─ All threads stay coordinated throughout
    */

    static void example2_MultiPhaseMatrixMultiplication() {
        System.out.println("\n=== EXAMPLE 2: MULTI-PHASE WITH REUSABLE BARRIER ===\n");

        // Create barrier with BARRIERACTION (runs when all threads reach)
        int numberOfThreads = 2;
        
        // Optional: Runnable that executes when all threads reach barrier
        Runnable barrierAction = () -> {
            System.out.println("  *** BARRIER ACTION: All threads synchronized! ***");
        };

        CyclicBarrier barrier = new CyclicBarrier(numberOfThreads, barrierAction);

        System.out.println("Created CyclicBarrier with " + numberOfThreads + " threads");
        System.out.println("Barrier has action: runs when all threads reach barrier\n");

        int[][] matrixA = {
                {1, 2},
                {3, 4}
        };

        int[][] matrixB = {
                {5, 6},
                {7, 8}
        };

        int[][] resultMatrix = new int[2][2];
        boolean[] verificationPassed = {false, false};  // Track verification per thread

        // Create threads
        Thread[] threads = new Thread[numberOfThreads];
        
        for (int row = 0; row < numberOfThreads; row++) {
            final int currentRow = row;
            
            threads[row] = new Thread(() -> {
                try {
                    // ===== PHASE 1: CALCULATE ROW =====
                    System.out.println("[Thread-" + currentRow + "] PHASE 1: Calculating row " + currentRow);
                    
                    for (int col = 0; col < 2; col++) {
                        resultMatrix[currentRow][col] = 0;
                        for (int k = 0; k < 2; k++) {
                            resultMatrix[currentRow][col] += 
                                matrixA[currentRow][k] * matrixB[k][col];
                        }
                    }
                    
                    System.out.println("[Thread-" + currentRow + "] Calculation complete: " + 
                                     java.util.Arrays.toString(resultMatrix[currentRow]));
                    
                    // ===== BARRIER 1: SYNCHRONIZE =====
                    System.out.println("[Thread-" + currentRow + "] Waiting at BARRIER 1...");
                    barrier.await();  // First barrier
                    System.out.println("[Thread-" + currentRow + "] Released from BARRIER 1!");
                    
                    // ===== PHASE 2: VERIFY RESULT (shows reusability) =====
                    System.out.println("[Thread-" + currentRow + "] PHASE 2: Verifying results...");
                    
                    // Verify: all matrix values are > 0
                    boolean allPositive = true;
                    for (int val : resultMatrix[currentRow]) {
                        if (val <= 0) allPositive = false;
                    }
                    verificationPassed[currentRow] = allPositive;
                    
                    System.out.println("[Thread-" + currentRow + "] Verification: " + 
                                     (allPositive ? "PASSED" : "FAILED"));
                    
                    // ===== BARRIER 2: SYNCHRONIZE AGAIN (REUSABLE!) =====
                    System.out.println("[Thread-" + currentRow + "] Waiting at BARRIER 2...");
                    barrier.await();  // REUSE same barrier for second phase!
                    System.out.println("[Thread-" + currentRow + "] Released from BARRIER 2!");
                    
                    // ===== PHASE 3: FINAL PROCESSING =====
                    System.out.println("[Thread-" + currentRow + "] PHASE 3: Final processing complete");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threads[row].start();
        }

        // Wait for completion
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n✓ All phases complete!");
        System.out.println("Result matrix:");
        for (int[] row : resultMatrix) {
            System.out.println("  " + java.util.Arrays.toString(row));
        }
    }

    // ============================================================================
    // EXAMPLE 3: UNDERSTANDING BARRIER MECHANICS
    // ============================================================================
    /*
    SCENARIO: Demonstrate exactly what happens at barrier.await()
    */

    static void example3_BarrierMechanics() {
        System.out.println("\n=== EXAMPLE 3: BARRIER MECHANICS IN DETAIL ===\n");

        System.out.println("STEP-BY-STEP WHAT HAPPENS:\n");

        int numThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numThreads);

        System.out.println("Initial state: CyclicBarrier(" + numThreads + ")");
        System.out.println("├─ parties = 3 (expected threads)");
        System.out.println("├─ count = 3 (threads still needed)");
        System.out.println("└─ generation = 0 (cycle number)\n");

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int threadNum = i;
            
            threads[i] = new Thread(() -> {
                try {
                    System.out.println("Time T" + threadNum + ": Thread-" + threadNum + " calling barrier.await()");
                    
                    long startTime = System.currentTimeMillis();
                    barrier.await();  // BLOCK here
                    long waitTime = System.currentTimeMillis() - startTime;
                    
                    System.out.println("Time T" + threadNum + ": Thread-" + threadNum + 
                                     " RELEASED! (waited ~" + waitTime + "ms)");
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threads[i].start();
            
            // Small delay between thread starts for visibility
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }

        // Wait for all threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nAfter barrier reset:");
        System.out.println("├─ count = 3 (reset for reuse)");
        System.out.println("├─ generation = 1 (new cycle)");
        System.out.println("└─ Ready for next barrier.await() calls!");
    }

    // ============================================================================
    // EXAMPLE 4: COMMON MISTAKES
    // ============================================================================
    /*
    SCENARIO: Show common pitfalls and how to avoid them
    */

    static void example4_CommonMistakes() {
        System.out.println("\n=== EXAMPLE 4: COMMON MISTAKES ===\n");

        // MISTAKE 1: Wrong barrier count
        System.out.println("MISTAKE 1: Initializing barrier with wrong count");
        System.out.println("├─ CyclicBarrier barrier = new CyclicBarrier(5);  // Expects 5 threads");
        System.out.println("├─ But only 3 threads created!");
        System.out.println("├─ Result: 2 threads wait forever (DEADLOCK!)");
        System.out.println("└─ Fix: Count must match number of threads\n");

        // MISTAKE 2: Not catching exceptions
        System.out.println("MISTAKE 2: Not handling BrokenBarrierException");
        System.out.println("├─ barrier.await() throws BrokenBarrierException");
        System.out.println("├─ If exception not caught: thread crashes");
        System.out.println("├─ Other threads still stuck in barrier.await()!");
        System.out.println("└─ Fix: Always use try-catch pattern\n");

        // MISTAKE 3: Lost reference
        System.out.println("MISTAKE 3: Creating barrier inside method scope");
        System.out.println("├─ Barrier created in method A");
        System.out.println("├─ Different threads try to use different barriers");
        System.out.println("├─ Result: Synchronization doesn't work!");
        System.out.println("└─ Fix: Share same barrier reference across threads\n");

        // DEMONSTRATION: Correct pattern
        System.out.println("CORRECT PATTERN:");
        System.out.println("try {");
        System.out.println("    // Do work");
        System.out.println("    barrier.await();  // Synchronized here");
        System.out.println("    // Continue");
        System.out.println("} catch (InterruptedException | BrokenBarrierException e) {");
        System.out.println("    // Handle gracefully");
        System.out.println("}\n");

        // Practical example
        System.out.println("PRACTICAL CORRECTION DEMO:\n");
        
        int numThreads = 3;
        CyclicBarrier barrier = new CyclicBarrier(numThreads);  // ✓ Correct count
        
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                try {
                    System.out.println("  Thread-" + id + " doing work...");
                    Thread.sleep(500 + id * 100);
                    
                    System.out.println("  Thread-" + id + " waiting at barrier");
                    barrier.await();  // ✓ Correct exception handling
                    
                    System.out.println("  Thread-" + id + " completed!");
                } catch (InterruptedException | BrokenBarrierException e) {
                    // ✓ Proper exception handling
                    System.out.println("  Thread-" + id + " error: " + e.getClass().getSimpleName());
                    Thread.currentThread().interrupt();
                }
            });
            threads[i].start();
        }

        // Wait for all
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("✓ All threads completed successfully!");
    }

    /*
    ========== SECTION 5: KEY DIFFERENCES SUMMARY ==========

    CYCLICBARRIER vs COUNTDOWNLATCH:

    Feature              | CyclicBarrier      | CountDownLatch
    =====================+=====================+====================
    Reusable             | YES (automatic)    | NO (one-time)
    Reset                | Auto after barrier | Cannot reset
    All threads wait     | YES                | NO (mixed roles)
    Participation        | All equal          | Asymmetric
    Phases               | Multiple           | Single
    Use case             | Repeated sync      | One-time event
    Matrix multiply      | BETTER             | WORSE
    Server startup       | WORSE              | BETTER

    ========== SECTION 6: REAL-WORLD APPLICATIONS ==========

    1. IMAGE PROCESSING (parallel pixel processing)
       └─ Each thread processes one row of image
       └─ Barrier ensures all rows processed before next stage

    2. GAME DEVELOPMENT (multiplayer synchronization)
       └─ All players must reach same waypoint
       └─ Barrier prevents some players from progressing early

    3. LOAD TESTING (synchronized request waves)
       └─ All virtual users wait until ready
       └─ Then all send requests simultaneously

    4. DATA AGGREGATION (parallel data collection)
       └─ Each collector gathers data for one partition
       └─ Barrier ensures all data collected before aggregation

    5. SCIENTIFIC SIMULATION (parallel computation)
       └─ Each node calculates its region
       └─ Barrier before next time step synchronizes computation
    */
}

