package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.read_write_locking;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.HashMap;
import java.util.Map;

/*
 * ============================================================================
 * READ-WRITE LOCKING - COMPREHENSIVE GUIDE
 * ============================================================================
 * 
 * WHAT IS READ-WRITE LOCKING?
 * ---------------------------
 * Read-Write Locking is a synchronization mechanism that allows multiple 
 * threads to read the same resource SIMULTANEOUSLY, but only ONE thread can
 * WRITE at a time. No thread can read while a write is in progress, and 
 * no thread can write while reads are in progress.
 * 
 * Think of it like a library:
 * - Multiple people (threads) can READ books simultaneously
 * - But only ONE person can REWRITE/UPDATE a book at a time
 * - While someone is rewriting, no one else can read that book
 * 
 * ============================================================================
 * WHY DO WE NEED READ-WRITE LOCKS?
 * ================================
 * 
 * Problem: When using synchronized/ReentrantLock (mutual exclusion locks):
 * - Only ONE thread can access the resource at a time
 * - Even if multiple threads just want to READ (no modification)
 * - This causes unnecessary waiting and poor performance
 * 
 * Solution: Read-Write Lock distinguishes between:
 * 1. READ operations (non-destructive, commutative)
 * 2. WRITE operations (destructive, exclusive)
 * 
 * ============================================================================
 * KEY BENEFITS
 * ============
 * 1. BETTER CONCURRENCY: Multiple readers can run in parallel
 * 2. HIGH THROUGHPUT: For read-heavy applications
 * 3. PERFORMANCE: Eliminates unnecessary locking for read operations
 * 4. REAL-WORLD APPLICABLE: Cache systems, databases, file systems use this
 * 
 * ============================================================================
 * KEY DRAWBACKS
 * ==============
 * 1. COMPLEXITY: Harder to implement and debug than simple locks
 * 2. WRITE STARVATION: Writers might wait forever if readers keep coming
 * 3. OVERHEAD: More complex than simple locks, extra CPU usage
 * 4. NOT ALWAYS BETTER: For write-heavy workloads, it's WORSE than simple locks
 * 5. FAIRNESS ISSUES: Some implementations favor readers over writers
 * 
 * ============================================================================
 * WHEN TO USE READ-WRITE LOCKS?
 * =============================
 * USE when:
 * - Read operations >> Write operations (e.g., 80% read, 20% write)
 * - Reads are quick and frequent
 * - Data needs to be extremely up-to-date for consistency
 * 
 * DON'T USE when:
 * - Reads and writes are balanced (use simple ReentrantLock)
 * - Writes are more frequent than reads
 * - You need high fairness (some threads starve)
 * - Reads are very long (writers can't proceed)
 * 
 * ============================================================================
 * REAL-WORLD USE CASES
 * ====================
 * 1. CACHING SYSTEMS
 *    - Users frequently read cache (multiple threads)
 *    - Invalidation/update is rare (single thread)
 *    - Example: Session cache, configuration cache
 * 
 * 2. DATABASE SYSTEMS
 *    - Many SELECT queries (reads) happening parallelly
 *    - Few UPDATE/DELETE operations (writes)
 *    - Example: Web server serving 10,000 read-only requests/sec
 * 
 * 3. CONFIGURATION MANAGEMENT
 *    - Threads constantly read application config
 *    - Config is updated rarely during runtime
 *    - Example: Spring Configuration, Settings manager
 * 
 * 4. FILE SYSTEMS
 *    - Multiple processes read files simultaneously
 *    - Rarely modified files
 *    - Example: OS file caching, Java class loading
 * 
 * 5. REFERENCE DATA
 *    - Product catalogs in e-commerce
 *    - Master data in enterprise systems
 *    - Employee directory in company systems
 * 
 * ============================================================================
 */

public class ReadAndWrite {
    
    /*
     * ========================================================================
     * SECTION 1: CONCEPT - THE BASIC IDEA (BEGINNER LEVEL)
     * ========================================================================
     * 
     * Imagine a shared resource (email inbox, bank account, game leaderboard)
     * 
     * SCENARIO WITHOUT READ-WRITE LOCK (just ReentrantLock):
     * -------------------------------------------------------
     * User1 wants to READ email
     * User2 wants to READ email
     * User3 wants to WRITE/UPDATE email
     * 
     * What happens with simple lock:
     * Time 0: User1 gets lock (User2 and User3 wait)
     * Time 1: User2 gets lock (User3 waits)
     * Time 2: User3 gets lock
     * 
     * Problem: User2 had to wait for User1 even though both are just READING!
     * 
     * SCENARIO WITH READ-WRITE LOCK:
     * --------------------------------
     * Time 0: User1 gets READ lock
     * Time 1: User2 gets READ lock (immediately, no waiting!)
     * Time 2: User3 WAITS for READ lock (no concurrent writing allowed)
     * Time 3: User1 releases READ lock
     * Time 4: User2 releases READ lock
     * Time 5: User3 gets WRITE lock
     * 
     * Benefit: User2 didn't have to wait for User1!
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 2: BASIC IMPLEMENTATION PATTERN
     * ========================================================================
     * 
     * HOW TO USE ReadWriteLock IN JAVA:
     * 
     * Step 1: Create a ReadWriteLock instance
     *         ReadWriteLock lock = new ReentrantReadWriteLock();
     * 
     * Step 2: For READ operations:
     *         lock.readLock().lock();
     *         try {
     *             // Read the resource
     *         } finally {
     *             lock.readLock().unlock();
     *         }
     * 
     * Step 3: For WRITE operations:
     *         lock.writeLock().lock();
     *         try {
     *             // Write/Modify the resource
     *         } finally {
     *             lock.writeLock().unlock();
     *         }
     * 
     * ========================================================================
     */
    
    // Example 1: Simple User Profile Cache (Read-Heavy)
    public static class UserProfileCache {
        private final Map<Integer, String> userProfiles = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        
        // WRITE OPERATION: Update user profile
        // Only ONE thread can do this at a time
        public void updateProfile(int userId, String profileData) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " is WRITING");
                // Simulate some database call
                Thread.sleep(100);
                userProfiles.put(userId, profileData);
                System.out.println(Thread.currentThread().getName() + " finished WRITING");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        // READ OPERATION: Get user profile
        // Multiple threads can do this simultaneously
        public String getProfile(int userId) {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " is READING");
                // Simulate some processing
                Thread.sleep(50);
                System.out.println(Thread.currentThread().getName() + " finished READING");
                return userProfiles.getOrDefault(userId, "Not Found");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } finally {
                lock.readLock().unlock();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 3: COMPARISON WITH OTHER LOCKING MECHANISMS
     * ========================================================================
     * 
     * 1. NO LOCK (Unsynchronized):
     *    - Fastest but DATA CORRUPTION / INCONSISTENCY
     *    - Use: Only for immutable data or single-threaded apps
     * 
     * 2. SIMPLE LOCK (synchronized / ReentrantLock):
     *    - Mutual exclusion: Only ONE thread at a time
     *    - Safe but poor concurrency for read-heavy workloads
     *    - Performance: Worst for read-heavy scenarios
     * 
     * 3. READ-WRITE LOCK (ReentrantReadWriteLock):
     *    - Multiple readers OR one writer
     *    - Best for read-heavy scenarios
     *    - Performance: Better than simple lock for reads
     *    - Trade-off: More complex implementation
     * 
     * 4. OPTIMISTIC LOCKING (Version number / timestamp):
     *    - No lock, just check version before write
     *    - Best for rare conflicts
     *    - Performance: Best for read-heavy, rare conflicts
     *    - Risk: Failed writes need retry logic
     * 
     * ========================================================================
     */
    
    // Example 2: Bank Account with Read-Write Lock
    // Use case: Many withdrawals are read-only checks, few are actual transfers
    public static class BankAccount {
        private double balance;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }
        
        // READ operation: Check balance (no modification)
        // MANY threads can do this simultaneously
        public double checkBalance() {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + 
                    " checking balance: $" + balance);
                // Simulate reading from database
                Thread.sleep(50);
                return balance;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            } finally {
                lock.readLock().unlock();
            }
        }
        
        // WRITE operation: Transfer money (modification)
        // Only ONE thread can do this at a time
        public synchronized void transfer(double amount) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + 
                    " transferring: $" + amount);
                // Simulate database transaction
                Thread.sleep(100);
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + 
                    " transfer complete. New balance: $" + balance);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 4: ADVANCED CONCEPT - FAIRNESS & WRITER STARVATION
     * ========================================================================
     * 
     * PROBLEM: WRITER STARVATION
     * ===========================
     * If readers keep coming and going, a waiting writer might NEVER get the lock!
     * 
     * Timeline example:
     * Time 0: Reader1 enters (has readLock)
     * Time 1: Reader2 wants to enter (Reader1 is still there)
     * ------- Writer1 wants to WRITE (must wait)
     * Time 2: Reader2 enters (Reader1 still there)
     * Time 3: Reader3 wants to enter
     * Time 4: Reader3 enters (Reader1, 2 still there)
     * ... readers keep coming ...
     * Writer1 NEVER gets the lock!
     * 
     * SOLUTION: ReentrantReadWriteLock with fairness
     * ================================================
     * ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);
     *                                                              ^^^^
     *                                                            fairness=true
     * 
     * When fairness = true:
     * - Lock is given in FIFO (first-come, first-served) order
     * - Writers won't starve
     * - Performance is slightly worse (fairness has overhead)
     * 
     * ========================================================================
     */
    
    // Example 3: Demonstrating Fairness
    public static class FairReadWriteLock {
        private int sharedValue = 0;
        // fairness = true ensures writers don't starve
        private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
        
        public void read() {
            lock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + 
                    " reading value: " + sharedValue);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.readLock().unlock();
            }
        }
        
        public void write(int newValue) {
            lock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + 
                    " writing new value: " + newValue);
                Thread.sleep(100);
                sharedValue = newValue;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 5: TRICKS AND TIPS FOR EFFICIENT USE
     * ========================================================================
     * 
     * TRICK 1: LOCK DOWNGRADING (Advanced)
     * =====================================
     * You CAN'T directly downgrade write lock to read lock in Java!
     * ReadWriteLock doesn't support this.
     * 
     * WRONG:
     *   lock.writeLock().lock();
     *   lock.readLock().lock();  // DEADLOCK! Can't acquire read while holding write
     * 
     * WORKAROUND:
     *   1. Release write lock
     *   2. Then acquire read lock
     *   BUT: Someone else might modify in between!
     * 
     * TRICK 2: KEEPING LOCK SCOPE SMALL
     * ==================================
     * GOOD:
     *   lock.readLock().lock();
     *   try {
     *       String value = data.get(key);  // Only locked part
     *   } finally {
     *       lock.readLock().unlock();
     *   }
     *   // Do processing WITHOUT lock (outside try block)
     *   processValue(value);
     * 
     * BAD:
     *   lock.readLock().lock();
     *   try {
     *       String value = data.get(key);
     *       processValue(value);           // Keeps lock while processing!
     *       sendToNetwork(value);          // Lock held for entire operation!
     *   } finally {
     *       lock.readLock().unlock();
     *   }
     * 
     * TRICK 3: REENTRANT BEHAVIOR
     * ============================
     * GOOD NEWS: ReentrantReadWriteLock allows the SAME thread to acquire
     * the lock multiple times!
     * 
     * Same thread can:
     * - Acquire read lock multiple times (read lock is reentrant)
     * - Acquire write lock multiple times (write lock is reentrant)
     * - But still can't upgrade read -> write or downgrade write -> read
     * 
     * TRICK 4: CATCHING INTERRUPTIBLE LOCKS
     * ======================================
     * Use lockInterruptibly() for cancellation support:
     * 
     * lock.readLock().lockInterruptibly();
     * 
     * This allows other threads to interrupt the waiting thread!
     * 
     * ========================================================================
     */
    
    // Example 4: Efficient Lock Scope
    public static class EfficientLockUsage {
        private final Map<String, String> userCache = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        
        // GOOD: Lock scope is minimal
        public String getUserData(String userId) {
            String data;
            lock.readLock().lock();
            try {
                data = userCache.get(userId);  // Only this is locked
            } finally {
                lock.readLock().unlock();
            }
            
            // Expensive operation OUTSIDE lock
            if (data != null) {
                data = parseUserData(data);  // Can run in parallel!
            }
            return data;
        }
        
        // Helper method (doesn't need lock)
        private String parseUserData(String data) {
            try {
                Thread.sleep(100);  // Simulate expensive parsing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Parsed: " + data;
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 6: COMMON CONFUSIONS & MISTAKES
     * ========================================================================
     * 
     * CONFUSION 1: "When should I use READ lock vs WRITE lock?"
     * ==========================================================
     * Decision Tree:
     *   - Does my operation MODIFY the data? 
     *     YES -> Use WRITE lock (mutex)
     *     NO  -> Use READ lock (shared)
     * 
     * Examples:
     *   - Reading from cache -> READ lock
     *   - Writing to cache -> WRITE lock
     *   - Checking balance -> READ lock
     *   - Transferring money -> WRITE lock
     *   - Counting users -> READ lock
     *   - Adding user -> WRITE lock
     * 
     * CONFUSION 2: "Can I use ReadWriteLock for write-heavy workloads?"
     * ==================================================================
     * NO! ReadWriteLock actually makes things WORSE for write-heavy workloads!
     * 
     * Reason: ReadWriteLock has MORE overhead than a simple lock
     * 
     * Example:
     * If your workload is 80% writes, 20% reads:
     * - Simple ReentrantLock: Still better
     * - ReadWriteLock: Overkill and slower!
     * 
     * Use ReadWriteLock ONLY when reads >> writes (e.g., 80% read, 20% write)
     * 
     * CONFUSION 3: "Why did the writer get blocked even though no readers are reading?"
     * ==================================================================================
     * Because OTHER readers are STILL reading!
     * 
     * Timeline:
     * Time 0: Reader1 acquires read lock
     * Time 1: Writer requests write lock (BLOCKED)
     * Time 2: Reader2 acquires read lock
     * ...
     * The writer WAITS until ALL readers release their locks!
     * 
     * CONFUSION 4: "Is ReadWriteLock thread-safe?"
     * ==============================================
     * The LOCK is thread-safe, but the DATA it protects isn't automatically safe!
     * 
     * WRONG:
     *   ReadWriteLock lock = new ReentrantReadWriteLock();
     *   Map<String, String> data = new HashMap<>();  // NOT thread-safe!
     *   
     *   // Even with lock, if someone accesses 'data' without lock, it's unsafe!
     * 
     * CONFUSION 5: "Can I upgrade read lock to write lock?"
     * ========================================================
     * NO! This will cause DEADLOCK!
     * 
     * DEADLOCK EXAMPLE:
     *   lock.readLock().lock();
     *   ... some code ...
     *   lock.writeLock().lock();  // DEADLOCK! You already hold read lock!
     * 
     * CONFUSION 6: "What happens if I forget to unlock?"
     * ===================================================
     * The lock will BLOCK other threads FOREVER!
     * 
     * WRONG:
     *   lock.readLock().lock();
     *   doSomething();
     *   // Forgot to unlock!
     * 
     * RIGHT:
     *   lock.readLock().lock();
     *   try {
     *       doSomething();
     *   } finally {
     *       lock.readLock().unlock();  // ALWAYS unlock!
     *   }
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 7: PERFORMANCE ANALYSIS & DECISION MATRIX
     * ========================================================================
     * 
     * When to use WHAT lock:
     * 
     * +-------------------+------------------+-------------------+------------------+
     * | Workload Type     | Simple Lock      | ReadWriteLock     | Atomic/Optimistic|
     * +-------------------+------------------+-------------------+------------------+
     * | Read Only (>95%)  | OKAY             | BEST              | GOOD             |
     * | Read Heavy (75%)  | OKAY             | BEST              | OKAY             |
     * | Balanced (50%)    | GOOD             | OKAY              | OKAY             |
     * | Write Heavy (75%) | BEST             | POOR              | POOR             |
     * | Write Only (>95%) | BEST             | POOR              | N/A              |
     * +-------------------+------------------+-------------------+------------------+
     * 
     * PERFORMANCE NUMBERS (Approximate):
     * 
     * Scenario: 100 threads, 1,000,000 operations
     * 
     * 95% Read, 5% Write:
     * - synchronized: 2000ms (one thread at a time)
     * - ReentrantLock: 1900ms (one thread at a time)
     * - ReadWriteLock: 200ms (multiple readers!)
     * 
     * 50% Read, 50% Write:
     * - synchronized: 1500ms
     * - ReentrantLock: 1450ms
     * - ReadWriteLock: 1800ms (extra overhead not worth it!)
     * 
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 8: REAL-WORLD EXAMPLES
     * ========================================================================
     */
    
    // Example 5: Cache System (Practical)
    public static class CacheSystem<K, V> {
        private final Map<K, V> cache = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        
        // GET: Multiple threads can do this simultaneously
        public V get(K key) {
            lock.readLock().lock();
            try {
                return cache.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }
        
        // PUT: Only one thread can do this at a time
        public void put(K key, V value) {
            lock.writeLock().lock();
            try {
                cache.put(key, value);
                System.out.println("Cache updated: " + key + " = " + value);
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        // INVALIDATE: Clear entire cache (write operation)
        public void invalidate() {
            lock.writeLock().lock();
            try {
                cache.clear();
                System.out.println("Cache cleared!");
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    // Example 6: Database Connection Pool (Read-Heavy)
    public static class ConnectionPool {
        private int availableConnections;
        private int totalConnections;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public ConnectionPool(int size) {
            this.totalConnections = size;
            this.availableConnections = size;
        }
        
        // GET STATUS: Many threads want to check available connections
        // This is a READ operation (no modification)
        public int getAvailableConnections() {
            lock.readLock().lock();
            try {
                return availableConnections;
            } finally {
                lock.readLock().unlock();
            }
        }
        
        // ACQUIRE: Only when connections are actually taken
        // This is a WRITE operation (modification)
        public void acquireConnection() {
            lock.writeLock().lock();
            try {
                if (availableConnections > 0) {
                    availableConnections--;
                } else {
                    throw new RuntimeException("No available connections!");
                }
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        // RELEASE: Returning connection
        // This is a WRITE operation (modification)
        public void releaseConnection() {
            lock.writeLock().lock();
            try {
                if (availableConnections < totalConnections) {
                    availableConnections++;
                }
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 9: WHEN NOT TO USE READ-WRITE LOCK
     * ========================================================================
     * 
     * DON'T USE when:
     * 
     * 1. DATA IS IMMUTABLE
     *    - No writes at all, just reads
     *    - Use: No lock needed at all!
     *    
     * 2. WRITE-HEAVY WORKLOAD
     *    - Writes > reads
     *    - Use: Simple ReentrantLock or synchronized
     *    
     * 3. SIMPLE OPERATIONS
     *    - Single variable updates (int, boolean)
     *    - Use: AtomicInteger, AtomicBoolean
     *    
     * 4. VERY HIGH CONTENTION
     *    - Lock is held very frequently and for long
     *    - Use: Consider lock-free data structures
     *    
     * 5. EXTREME LOW LATENCY REQUIRED
     *    - Any lock overhead is unacceptable
     *    - Use: Lock-free algorithms (ConcurrentHashMap, CopyOnWriteArrayList)
     *    
     * ========================================================================
     */
    
    
    /*
     * ========================================================================
     * SECTION 10: BEST PRACTICES CHECKLIST
     * ========================================================================
     * 
     * [✓] Always unlock in finally block or use try-with-resources
     * [✓] Keep lock scope as small as possible
     * [✓] Never nest locks (can cause deadlock)
     * [✓] Release write lock before acquiring another lock
     * [✓] Use fairness only if writer starvation is a real issue
     * [✓] Profile before using ReadWriteLock (ensure it helps)
     * [✓] Document which operations need read vs write
     * [✓] Never hold locks during I/O operations or expensive computations
     * [✓] Don't try to upgrade read lock to write lock
     * [✓] Use lockInterruptibly() if threads need to be cancellable
     * 
     * ========================================================================
     */
    
    // Example 7: Complete Real-World Pattern
    public static class UserRegistry {
        private final Map<Integer, String> users = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock(true);  // fair
        
        // READ: Get user by ID (multiple threads can do this)
        public String getUser(int userId) {
            lock.readLock().lock();
            try {
                return users.get(userId);
            } finally {
                lock.readLock().unlock();
            }
        }
        
        // READ: Check if user exists (multiple threads can do this)
        public boolean userExists(int userId) {
            lock.readLock().lock();
            try {
                return users.containsKey(userId);
            } finally {
                lock.readLock().unlock();
            }
        }
        
        // WRITE: Register new user
        public void registerUser(int userId, String name) {
            lock.writeLock().lock();
            try {
                if (users.containsKey(userId)) {
                    throw new RuntimeException("User already exists!");
                }
                users.put(userId, name);
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        // WRITE: Update user info
        public void updateUser(int userId, String newName) {
            lock.writeLock().lock();
            try {
                if (!users.containsKey(userId)) {
                    throw new RuntimeException("User not found!");
                }
                users.put(userId, newName);
            } finally {
                lock.writeLock().unlock();
            }
        }
        
        // WRITE: Delete user
        public void deleteUser(int userId) {
            lock.writeLock().lock();
            try {
                users.remove(userId);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
    
    
    /*
     * ========================================================================
     * SECTION 11: TESTING YOUR UNDERSTANDING
     * ========================================================================
     * 
     * Q1: If 5 threads are reading and 1 thread wants to write, what happens?
     * A1: The writer WAITS until all 5 readers finish, then gets exclusive access.
     *     No new readers can enter while writer is waiting (with fairness).
     * 
     * Q2: Is it safe to use ArrayList with ReadWriteLock?
     * A2: YES, as long as ALL access to ArrayList goes through the lock!
     *     If any code accesses ASCII directly without lock, it's still unsafe.
     * 
     * Q3: Why not just use synchronized?
     * A3: Because synchronized is mutual exclusion (only 1 thread).
     *     ReadWriteLock allows multiple readers (better concurrency).
     * 
     * Q4: What if reads are very expensive (take 10 seconds each)?
     * A4: Avoid ReadWriteLock! Writes will starve. Use simpler mechanisms.
     * 
     * Q5: Can the same thread acquire read lock twice?
     * A5: YES! ReadWriteLock is reentrant. Same thread can acquire
     *     the same lock multiple times. But still can't upgrade to write.
     * 
     * ========================================================================
     */
}
