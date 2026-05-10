package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.alarm;

/**
 * ============================================================================
 * DETAILED EXPLANATION: ALARMMCLOCK.JAVA - SEMAPHORE BASED PRODUCER-CONSUMER
 * ============================================================================
 * 
 * This file explains EVERYTHING happening in AlarmClock.java
 * - What each line does
 * - Why it works (or doesn't work)
 * - Problems and pitfalls
 * - How to fix them (best practices)
 * 
 * ============================================================================
 * SECTION 1: UNDERSTANDING THE CURRENT CODE STRUCTURE
 * ============================================================================
 * 
 * CURRENT AlarmClock.java BREAKDOWN:
 * 
 *     public class AlarmClock {
 *         
 *         private final List<Alarm> alarms = new ArrayList<>();
 *         private Semaphore vacantSeats = new Semaphore(5);
 *         private Semaphore filledSeats = new Semaphore(0);
 *         
 *         public void pushAlarm(Alarm alarm) { ... }  // PRODUCER
 *         public void startAlarm() { ... }             // CONSUMER
 *     }
 * 
 * THREE SHARED RESOURCES:
 * ========================
 * 
 * 1. alarms (ArrayList)
 *    - Stores Alarm objects
 *    - Shared between producers and consumer
 *    - Can hold MAX 5 items
 * 
 * 2. vacantSeats (Semaphore)
 *    - Initial value: 5 (5 empty slots available)
 *    - Represents free space in alarm queue
 *    - Decrements when producer adds alarm
 *    - Increments when consumer removes alarm
 *    
 *    Example states:
 *    ├─ vacantSeats = 5 → Queue is empty (all 5 slots free)
 *    ├─ vacantSeats = 3 → 2 alarms in queue (3 slots free)
 *    ├─ vacantSeats = 0 → Queue is FULL (no slots free)
 *    └─ vacantSeats < 0 → Producers are BLOCKED waiting for consumer
 * 
 * 3. filledSeats (Semaphore)
 *    - Initial value: 0 (no alarms to process yet)
 *    - Represents occupied slots in alarm queue
 *    - Increments when producer adds alarm
 *    - Decrements when consumer removes alarm
 *    
 *    Example states:
 *    ├─ filledSeats = 0 → Queue is empty (nothing to process)
 *    ├─ filledSeats = 2 → 2 alarms waiting to ring
 *    ├─ filledSeats = 5 → Queue is FULL
 *    └─ filledSeats < 0 → Consumer is BLOCKED waiting for producer
 * 
 * ============================================================================
 * SECTION 2: PARKING LOT ANALOGY (BEST WAY TO UNDERSTAND)
 * ============================================================================
 * 
 * Imagine a parking lot with 5 spaces:
 * 
 *     PARKING LOT REPRESENTATION:
 *     ┌─────────────────────────────┐
 *     │ [ ][ ][ ][ ][ ]             │  ← 5 empty spaces
 *     └─────────────────────────────┘
 *     
 *     SEMAPHORE REPRESENTATION:
 *     vacantSeats = 5  (empty spaces available)
 *     filledSeats = 0  (cars parked)
 * 
 * CAR ARRIVES (Producer adds alarm):
 *     
 *     Step 1: Driver checks "Are there vacant spaces?"
 *     └─ vacantSeats.acquire()  ← "Reserve a space"
 *        vacantSeats decrements: 5 → 4
 *     
 *     Step 2: Driver parks car
 *     └─ alarms.add(alarm)
 *     
 *     Step 3: Driver notifies lot manager
 *     └─ filledSeats.release()  ← "A car is now parked"
 *        filledSeats increments: 0 → 1
 * 
 * LOT MANAGER CHECKS (Consumer processes alarm):
 *     
 *     Step 1: Manager checks "Are there parked cars?"
 *     └─ filledSeats.acquire()  ← "Remove a car"
 *        filledSeats decrements: 1 → 0
 *     
 *     Step 2: Manager drives car away
 *     └─ alarms.remove()
 *     
 *     Step 3: Manager notifies drivers about free space
 *     └─ vacantSeats.release()  ← "A space is now free"
 *        vacantSeats increments: 4 → 5
 * 
 * BLOCKED SCENARIO - Parking lot FULL:
 *     
 *     All 5 spaces occupied:
 *     ├─ vacantSeats = 0 (no free spaces)
 *     └─ filledSeats = 5 (5 cars parked)
 *     
 *     New car arrives:
 *     └─ Driver calls: vacantSeats.acquire()
 *        vacantSeats is 0, so driver BLOCKS here
 *        Waits automatically until manager removes a car
 *        (NO busy waiting, NO CPU waste)
 * 
 * BLOCKED SCENARIO - Parking lot EMPTY:
 *     
 *     No alarms:
 *     ├─ vacantSeats = 5 (all spaces free)
 *     └─ filledSeats = 0 (no cars parked)
 *     
 *     Manager checks:
 *     └─ Manager calls: filledSeats.acquire()
 *        filledSeats is 0, so manager BLOCKS here
 *        Waits automatically until driver adds a car
 *        (NO busy waiting, NO CPU waste)
 * 
 * ============================================================================
 * SECTION 3: LINE-BY-LINE CODE ANALYSIS - pushAlarm() METHOD
 * ============================================================================
 * 
 * CURRENT CODE:
 * ==============
 * 
 *     public void pushAlarm(Alarm alarm){
 *         synchronized (this){
 *             try {
 *                 vacantSeats.acquire();  // Line A
 *                 if (alarm.getTime().isAfter(LocalDateTime.now())) {  // Line B
 *                     alarms.add(alarm);  // Line C
 *                     System.out.println("Alarm set: " + alarm.getReminder());
 *                     filledSeats.release();  // Line D
 *                 }
 *             } catch (Exception e) {  // Line E
 *                 // Empty catch - PROBLEM!
 *             }
 *         }
 *     }
 * 
 * LINE-BY-LINE EXPLANATION:
 * ==========================
 * 
 * Line A: vacantSeats.acquire()
 * ──────────────────────────────
 * PURPOSE: Check if there's space in the queue
 * 
 * WHAT HAPPENS:
 * ├─ If vacantSeats > 0:
 * │  ├─ Decrements by 1 (reserves a space)
 * │  └─ Returns immediately (thread continues)
 * │
 * └─ If vacantSeats = 0:
 *    ├─ Queue is FULL
 *    ├─ Thread BLOCKS automatically
 *    ├─ Releases lock (other producers can run)
 *    └─ Waits indefinitely for consumer to call vacantSeats.release()
 * 
 * EXAMPLE:
 *     vacantSeats = 5 → acquire() → vacantSeats becomes 4 ✓ Proceeds
 *     vacantSeats = 1 → acquire() → vacantSeats becomes 0 ✓ Proceeds
 *     vacantSeats = 0 → acquire() → BLOCKS ⏸️ Waits for release()
 * 
 * Line B: if (alarm.getTime().isAfter(LocalDateTime.now()))
 * ────────────────────────────────────────────────────────────
 * PURPOSE: Validate alarm is scheduled for FUTURE, not past
 * 
 * WHAT HAPPENS:
 * ├─ TRUE: Alarm time is in future → Add to queue ✓
 * └─ FALSE: Alarm time is in past → Skip adding, don't trigger alarm ✗
 * 
 * IMPORTANT ISSUE HERE!
 * ═════════════════════
 * If this condition fails:
 * ├─ vacantSeats was already decremented (Line A)
 * ├─ But alarm is NOT added
 * ├─ And filledSeats.release() is NOT called
 * │
 * RESULT: One vacantSeat is permanently lost!
 * └─ Consumer will be woken with no alarm to process (DEADLOCK RISK!)
 * 
 * Line C: alarms.add(alarm)
 * ─────────────────────────
 * PURPOSE: Add alarm to the shared list
 * 
 * THREAD SAFETY NOTE:
 * ├─ ArrayList is NOT thread-safe
 * ├─ But we're protected by vacantSeats semaphore
 * ├─ Only one producer can reach here at a time (others blocked)
 * └─ So concurrent modification is impossible
 * 
 * Line D: filledSeats.release()
 * ──────────────────────────────
 * PURPOSE: Signal consumer that an alarm is available
 * 
 * WHAT HAPPENS:
 * ├─ Increments filledSeats by 1
 * ├─ If consumer is blocked in filledSeats.acquire():
 * │  ├─ Consumer wakes up
 * │  └─ Acquires the released permit
 * └─ If consumer is not waiting:
 *    └─ Permit remains available for next acquire()
 * 
 * Line E: catch (Exception e) { }
 * ────────────────────────────────
 * PURPOSE: Handle exceptions
 * 
 * PROBLEMS WITH CURRENT CODE:
 * ├─ Empty catch block - completely ignores exception!
 * ├─ No logging - no way to debug problems
 * ├─ No recovery - application silently fails
 * ├─ Violates exception handling best practices
 * └─ InterruptedException is lost (thread.interrupt() status discarded)
 * 
 * ============================================================================
 * SECTION 4: UNNECESSARY synchronized BLOCK - A MISTAKE!
 * ============================================================================
 * 
 * CURRENT CODE:
 * ==============
 *     public void pushAlarm(Alarm alarm){
 *         synchronized (this){  ← THIS IS NOT NEEDED!
 *             try {
 *                 vacantSeats.acquire();
 *                 // ...
 *             }
 *         }
 *     }
 * 
 * WHY IS synchronized(this) HERE?
 * ════════════════════════════════
 * Probably leftover from earlier wait/notify implementation.
 * 
 * IS IT NEEDED?
 * ═════════════
 * NO! Here's why:
 * 
 * 1. Semaphore.acquire() is ALREADY ATOMIC
 *    └─ Semaphore has internal locks for thread safety
 *    └─ Multiple threads can call acquire() safely
 * 
 * 2. Semaphore.release() is ALREADY ATOMIC
 *    └─ Internal synchronization handles it
 *    └─ No external lock needed
 * 
 * 3. ArrayList.add() is NOT thread-safe BUT:
 *    └─ Semaphore ensures only one producer runs at a time
 *    └─ vacantSeats acquisition serializes producers
 *    └─ No concurrent modification possible
 * 
 * WHAT'S THE PROBLEM WITH synchronized(this)?
 * ════════════════════════════════════════════
 * 
 * ✗ PERFORMANCE DEGRADATION:
 *   ├─ Extra lock contention
 *   ├─ Each producer must acquire both:
 *   │  ├─ synchronized(this) lock
 *   │  └─ Semaphore permit
 *   └─ Slows down the system
 * 
 * ✗ UNNECESSARY COMPLEXITY:
 *   ├─ Makes code harder to understand
 *   ├─ Suggests thread safety is unclear
 *   └─ Confuses developers reading code
 * 
 * ✗ POTENTIAL DEADLOCK RISK (if misused):
 *   ├─ If code changes, locks could interfere
 *   └─ Mixing synchronized + Semaphore is confusing
 * 
 * ✗ REDUCES PARALLELISM:
 *   ├─ Only one producer can run inside synchronized block
 *   ├─ Defeats semaphore's purpose of allowing N concurrent threads
 *   └─ Turns it into single-writer system
 * 
 * BEST PRACTICE:
 * ══════════════
 * REMOVE synchronized(this)!
 * 
 *     public void pushAlarm(Alarm alarm) {
 *         try {
 *             vacantSeats.acquire();  // No synchronized needed!
 *             alarms.add(alarm);
 *             filledSeats.release();
 *         }
 *     }
 * 
 * ============================================================================
 * SECTION 5: LINE-BY-LINE CODE ANALYSIS - startAlarm() METHOD
 * ============================================================================
 * 
 * CURRENT CODE:
 * ==============
 * 
 *     public void startAlarm(){
 *         synchronized (this){
 *             try {
 *                 filledSeats.acquire();  // Line F
 *                 Alarm alarm = alarms.remove(alarms.size() - 1);  // Line G
 *                 System.out.println("Alarm ringing: " + alarm.getReminder());
 *                 vacantSeats.release();  // Line H
 *             } catch (Exception e) {  // Line I
 *                 // Empty catch - PROBLEM!
 *             }
 *         }
 *     }
 * 
 * Line F: filledSeats.acquire()
 * ──────────────────────────────
 * PURPOSE: Check if there's an alarm to process
 * 
 * WHAT HAPPENS:
 * ├─ If filledSeats > 0:
 * │  ├─ Decrements by 1 (claims an alarm)
 * │  └─ Returns immediately (thread continues)
 * │
 * └─ If filledSeats = 0:
 *    ├─ Queue is EMPTY
 *    ├─ Thread BLOCKS automatically
 *    ├─ Releases lock (producers can run)
 *    └─ Waits indefinitely for producer to call filledSeats.release()
 * 
 * EXAMPLE:
 *     filledSeats = 3 → acquire() → filledSeats becomes 2 ✓ Proceeds
 *     filledSeats = 1 → acquire() → filledSeats becomes 0 ✓ Proceeds
 *     filledSeats = 0 → acquire() → BLOCKS ⏸️ Waits for release()
 * 
 * Line G: Alarm alarm = alarms.remove(alarms.size() - 1)
 * ───────────────────────────────────────────────────────
 * PURPOSE: Remove and get the alarm to trigger
 * 
 * HOW IT WORKS:
 * ├─ alarms.size() - 1 = index of LAST element
 * ├─ Example: If size=3, indices are [0,1,2], so last is at index 2
 * └─ remove(2) = removes and returns last element (LIFO - Last In First Out)
 * 
 * WHY REMOVE FROM END?
 * ├─ Most recent alarm rings first
 * ├─ Stack-like behavior (LIFO)
 * └─ Earlier alarms wait longer
 * 
 * THREAD SAFETY:
 * ├─ ArrayList.remove() is not thread-safe
 * ├─ But filledSeats.acquire() ensures only consumer reaches here
 * ├─ Multiple producers blocked by their own acquire()
 * └─ No concurrent modification possible
 * 
 * Line H: vacantSeats.release()
 * ──────────────────────────────
 * PURPOSE: Signal that a space is freed in the queue
 * 
 * WHAT HAPPENS:
 * ├─ Increments vacantSeats by 1
 * ├─ If producer is blocked in vacantSeats.acquire():
 * │  ├─ Producer wakes up
 * │  └─ Acquires the released permit
 * └─ If no producer waiting:
 *    └─ Permit remains available for next acquire()
 * 
 * Line I: catch (Exception e) { }
 * ────────────────────────────────
 * PURPOSE: Handle exceptions
 * 
 * SAME PROBLEMS AS pushAlarm():
 * ├─ Empty catch block - ignores exceptions
 * ├─ No logging or error handling
 * ├─ InterruptedException status is lost
 * └─ Secret failures (application seems fine but isn't)
 * 
 * ============================================================================
 * SECTION 6: IDENTIFIED PROBLEMS IN CURRENT CODE
 * ============================================================================
 * 
 * PROBLEM 1: DEADLOCK RISK IF VALIDATION FAILS
 * ═════════════════════════════════════════════
 * 
 * SCENARIO:
 * ─────────
 *     Producer calls: pushAlarm() with PAST time
 *     
 *     Step 1: vacantSeats.acquire() ✓ decrements: 5 → 4
 *     Step 2: Check: alarm.getTime().isAfter(LocalDateTime.now()) = FALSE
 *     Step 3: Condition fails, skip alarms.add()
 *     Step 4: Skip filledSeats.release() ✗ NEVER CALLED!
 * 
 * RESULT:
 * ──────
 *     ├─ One vacantSeat was consumed but no alarm added
 *     ├─ Queue appears full to other producers (vacantSeats = 4 instead of 5)
 *     ├─ Consumer gets confused (woken but no alarm to process)
 *     └─ System is in INCONSISTENT state!
 * 
 * FIX:
 * ────
 *     Validate BEFORE acquiring semaphore:
 *     
 *     if (!alarm.getTime().isAfter(LocalDateTime.now())) {
 *         throw new IllegalArgumentException("Alarm must be in future");
 *     }
 *     
 *     vacantSeats.acquire();  // Now safe to acquire
 *     alarms.add(alarm);
 *     filledSeats.release();
 * 
 * PROBLEM 2: HANGS FOREVER WITH NO TIMEOUT
 * ═════════════════════════════════════════
 * 
 * SCENARIO 1: Queue full
 * ──────────────────────
 *     vacantSeats = 0 (queue full)
 *     Producer calls: vacantSeats.acquire()
 *     
 *     RESULT:
 *     ├─ Producer BLOCKS and waits forever
 *     ├─ If consumer never runs (deadlock elsewhere):
 *     │  └─ Producer never wakes up
 *     └─ Producer thread is stuck indefinitely
 * 
 * SCENARIO 2: Queue empty
 * ───────────────────────
 *     filledSeats = 0 (queue empty)
 *     Consumer calls: filledSeats.acquire()
 *     
 *     RESULT:
 *     ├─ Consumer BLOCKS and waits forever
 *     ├─ If producer never runs (system shutdown?):
 *     │  └─ Consumer never wakes up
 *     └─ Application might be stuck
 * 
 * FIX:
 * ────
 *     Use tryAcquire(timeout) instead of acquire():
 *     
 *     boolean acquired = vacantSeats.tryAcquire(5, TimeUnit.SECONDS);
 *     if (!acquired) {
 *         throw new TimeoutException("Could not set alarm");
 *     }
 * 
 * PROBLEM 3: SILENT FAILURES (EMPTY CATCH BLOCKS)
 * ════════════════════════════════════════════════
 * 
 * EXAMPLE:
 * ────────
 *     try {
 *         vacantSeats.acquire();
 *     } catch (Exception e) {
 *         // What exception? Why did it happen? Where is it?
 *         // NOBODY KNOWS! (application just continues)
 *     }
 * 
 * CONSEQUENCES:
 * ─────────────
 *     ├─ Exception is swallowed (hidden)
 *     ├─ No logging = no way to debug
 *     ├─ Thread continues with missed alarm
 *     ├─ Consumer unaware alarm wasn't added
 *     └─ System appears to work but is broken
 * 
 * FIX:
 * ────
 *     catch (InterruptedException e) {
 *         Thread.currentThread().interrupt();  // Restore status
 *         LOGGER.severe("Failed to set alarm: " + e.getMessage());
 *         throw new RuntimeException("Alarm operation interrupted", e);
 *     }
 * 
 * PROBLEM 4: LOST INTERRUPT STATUS
 * ═════════════════════════════════
 * 
 * WHAT IS INTERRUPT?
 * ──────────────────
 *     Mechanism for gracefully stopping a thread:
 *     └─ Thread.currentThread().interrupt() sets interrupt flag
 *     └─ Blocked operations wake up with InterruptedException
 * 
 * HOW CURRENT CODE BREAKS IT:
 * ───────────────────────────
 *     try {
 *         vacantSeats.acquire();  // Throws InterruptedException
 *     } catch (Exception e) {
 *         // Exception is caught and ignored
 *         // Interrupt status is LOST!
 *     }
 *     // Thread continues as if nothing happened
 *     // Interrupt was ignored = thread never stops!
 * 
 * CONSEQUENCE:
 * ───────────
 *     Program tries to shutdown:
 *     ├─ Calls Thread.interrupt() on producer thread
 *     ├─ Producer's acquire() throws InterruptedException
 *     ├─ Current code catches and ignores it
 *     └─ Producer doesn't stop! → Shutdown hangs!
 * 
 * FIX:
 * ────
 *     catch (InterruptedException e) {
 *         Thread.currentThread().interrupt();  // RESTORE interrupt status!
 *         throw e;  // Propagate the exception up
 *     }
 * 
 * PROBLEM 5: NO NULL/VALIDATION CHECKS
 * ═════════════════════════════════════
 * 
 * CURRENT CODE:
 * ─────────────
 *     public void pushAlarm(Alarm alarm) {
 *         // No checks! What if alarm is null?
 *         if (alarm.getTime().isAfter(...)) {  // NullPointerException!
 *             alarms.add(alarm);
 *         }
 *     }
 * 
 * WHAT HAPPENS:
 * ─────────────
 *     pushAlarm(null);
 *     ├─ Calls: null.getTime() → NullPointerException
 *     ├─ Exception caught by empty catch block
 *     ├─ Silently ignored
 *     └─ Developer has NO IDEA what failed
 * 
 * FIX:
 * ────
 *     if (alarm == null) {
 *         throw new IllegalArgumentException("Alarm cannot be null");
 *     }
 * 
 * PROBLEM 6: System.out.println() IN PRODUCTION CODE
 * ════════════════════════════════════════════════════
 * 
 * CURRENT CODE:
 * ─────────────
 *     System.out.println("Alarm set: " + alarm.getReminder());
 *     System.out.println("Alarm ringing: " + alarm.getReminder());
 * 
 * PROBLEMS:
 * ─────────
 *     ├─ Not configurable (always prints)
 *     ├─ Can't set log level (debug/info/error)
 *     ├─ Difficult to redirect or suppress
 *     ├─ Not thread-safe for concurrent output
 *     ├─ Performance impact (unbuffered I/O)
 *     └─ Poor for production/logging
 * 
 * FIX:
 * ────
 *     Use proper Logger:
 *     
 *     private static final Logger LOGGER = 
 *         Logger.getLogger(AlarmClock.class.getName());
 *     
 *     LOGGER.info("Alarm set: " + alarm.getReminder());
 *     LOGGER.warning("Alarm ringing: " + alarm.getReminder());
 * 
 * ============================================================================
 * SECTION 7: SUMMARY OF IMPROVEMENTS NEEDED
 * ============================================================================
 * 
 * ✗ REMOVE: synchronized(this) block
 * ✓ ADD: Timeout handling with tryAcquire()
 * ✓ ADD: Input validation BEFORE acquire
 * ✓ ADD: Proper exception handling
 * ✓ ADD: Logging instead of System.out
 * ✓ ADD: Proper interrupt status restoration
 * ✓ ADD: null checks and validations
 * ✓ MAKE: Configuration values constants
 * ✓ ADD: Javadoc for thread safety
 * ✓ ADD: Helper methods for monitoring
 * 
 * ============================================================================
 */

public class DetailSemaphore {
    // This file is purely documentation
    // All explanations are in comments above
}
