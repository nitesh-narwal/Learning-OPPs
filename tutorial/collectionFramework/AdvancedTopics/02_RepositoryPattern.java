package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ==========================================
 * REPOSITORY PATTERN - Data Access Layer
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Repository = Data access ka abstraction layer
 * Business logic ko database details se separate karta hai
 * 
 * Har enterprise application mein use hota hai!
 * Spring Data JPA bhi internally yahi pattern use karta hai
 * 
 * WHY REPOSITORY?
 * - Testable code (mock repositories easily)
 * - Database independence (MySQL to MongoDB switch easy)
 * - Centralized data access logic
 * - Clean separation of concerns
 * 
 * REAL USAGE:
 * - E-commerce: ProductRepository, OrderRepository
 * - Banking: AccountRepository, TransactionRepository
 * - Social Media: UserRepository, PostRepository
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class RepositoryPattern {
    
    public static void main(String[] args) {
        demonstrateBasicRepository();
        demonstrateSpecificationPattern();
        demonstrateCachedRepository();
        demonstrateCompositeRepository();
    }
    
    /**
     * ENTITY - Domain Model
     * Represents business object
     */
    static class User {
        private final String id;
        private String name;
        private String email;
        private int age;
        private boolean active;
        
        public User(String id, String name, String email, int age) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
            this.active = true;
        }
        
        // Getters
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public int getAge() { return age; }
        public boolean isActive() { return active; }
        
        // Setters
        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setAge(int age) { this.age = age; }
        public void setActive(boolean active) { this.active = active; }
        
        @Override
        public String toString() {
            return String.format("User{id='%s', name='%s', email='%s', age=%d, active=%b}",
                id, name, email, age, active);
        }
    }
    
    /**
     * REPOSITORY INTERFACE
     * Standard CRUD operations
     * 
     * Production mein ye generic interface hota hai:
     * interface Repository<T, ID>
     */
    interface UserRepository {
        User save(User user);
        Optional<User> findById(String id);
        List<User> findAll();
        List<User> findByName(String name);
        void delete(String id);
        boolean exists(String id);
        long count();
    }
    
    /**
     * IN-MEMORY REPOSITORY IMPLEMENTATION
     * ====================================
     * 
     * Production mein ye database se connect hota hai
     * But testing ke liye in-memory implementation perfect hai!
     * 
     * THREAD-SAFETY: Using ConcurrentHashMap for multi-threaded access
     */
    static class InMemoryUserRepository implements UserRepository {
        // ConcurrentHashMap = Thread-safe without external synchronization
        private final Map<String, User> storage = new ConcurrentHashMap<>();
        
        @Override
        public User save(User user) {
            if (user == null || user.getId() == null) {
                throw new IllegalArgumentException("User and ID cannot be null");
            }
            storage.put(user.getId(), user);
            return user;
        }
        
        @Override
        public Optional<User> findById(String id) {
            // Optional = Elegant null handling (Java 8+)
            return Optional.ofNullable(storage.get(id));
        }
        
        @Override
        public List<User> findAll() {
            // Return defensive copy to prevent external modification
            return new ArrayList<>(storage.values());
        }
        
        @Override
        public List<User> findByName(String name) {
            return storage.values().stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        }
        
        @Override
        public void delete(String id) {
            storage.remove(id);
        }
        
        @Override
        public boolean exists(String id) {
            return storage.containsKey(id);
        }
        
        @Override
        public long count() {
            return storage.size();
        }
    }
    
    static void demonstrateBasicRepository() {
        UserRepository repo = new InMemoryUserRepository();
        
        // Create users
        User alice = new User("1", "Alice", "alice@example.com", 25);
        User bob = new User("2", "Bob", "bob@example.com", 30);
        
        repo.save(alice);
        repo.save(bob);
        
        // Find by ID
        Optional<User> found = repo.findById("1");
        assert found.isPresent();
        assert found.get().getName().equals("Alice");
        
        // Find all
        List<User> all = repo.findAll();
        assert all.size() == 2;
        
        // Delete
        repo.delete("1");
        assert !repo.exists("1");
    }
    
    /**
     * SPECIFICATION PATTERN
     * =====================
     * 
     * Flexible querying without exploding repository methods
     * 
     * BAD APPROACH:
     * findByNameAndAge()
     * findByEmailAndActive()
     * findByAgeGreaterThan()
     * ... 100+ methods! 😱
     * 
     * GOOD APPROACH:
     * findAll(Specification<User> spec)
     * 
     * Used by Spring Data JPA!
     */
    interface Specification<T> {
        boolean isSatisfiedBy(T item);
        
        // Combine specifications with AND logic
        default Specification<T> and(Specification<T> other) {
            return item -> this.isSatisfiedBy(item) && other.isSatisfiedBy(item);
        }
        
        // Combine specifications with OR logic
        default Specification<T> or(Specification<T> other) {
            return item -> this.isSatisfiedBy(item) || other.isSatisfiedBy(item);
        }
        
        // Negate specification
        default Specification<T> not() {
            return item -> !this.isSatisfiedBy(item);
        }
    }
    
    /**
     * Pre-built specifications for common queries
     * Reusable across application!
     */
    static class UserSpecifications {
        static Specification<User> isActive() {
            return User::isActive;
        }
        
        static Specification<User> hasName(String name) {
            return user -> user.getName().equalsIgnoreCase(name);
        }
        
        static Specification<User> ageGreaterThan(int age) {
            return user -> user.getAge() > age;
        }
        
        static Specification<User> ageBetween(int minAge, int maxAge) {
            return user -> user.getAge() >= minAge && user.getAge() <= maxAge;
        }
        
        static Specification<User> emailContains(String domain) {
            return user -> user.getEmail().contains(domain);
        }
    }
    
    /**
     * Enhanced repository with specification support
     */
    static class SpecificationUserRepository extends InMemoryUserRepository {
        
        public List<User> findAll(Specification<User> spec) {
            return super.findAll().stream()
                .filter(spec::isSatisfiedBy)
                .collect(Collectors.toList());
        }
        
        public Optional<User> findOne(Specification<User> spec) {
            return super.findAll().stream()
                .filter(spec::isSatisfiedBy)
                .findFirst();
        }
        
        public long count(Specification<User> spec) {
            return super.findAll().stream()
                .filter(spec::isSatisfiedBy)
                .count();
        }
    }
    
    static void demonstrateSpecificationPattern() {
        SpecificationUserRepository repo = new SpecificationUserRepository();
        
        // Sample data
        repo.save(new User("1", "Alice", "alice@gmail.com", 25));
        repo.save(new User("2", "Bob", "bob@yahoo.com", 35));
        repo.save(new User("3", "Charlie", "charlie@gmail.com", 45));
        
        // Simple query: Find active users
        List<User> activeUsers = repo.findAll(UserSpecifications.isActive());
        assert activeUsers.size() == 3;
        
        // Complex query: Active users over 30 with Gmail
        Specification<User> complexSpec = UserSpecifications.isActive()
            .and(UserSpecifications.ageGreaterThan(30))
            .and(UserSpecifications.emailContains("gmail"));
        
        List<User> result = repo.findAll(complexSpec);
        assert result.size() == 1;
        assert result.get(0).getName().equals("Charlie");
        
        // Another complex query: Users between 20-40 OR with Yahoo email
        Specification<User> ageOrEmail = UserSpecifications.ageBetween(20, 40)
            .or(UserSpecifications.emailContains("yahoo"));
        
        result = repo.findAll(ageOrEmail);
        assert result.size() == 2; // Alice and Bob
    }
    
    /**
     * CACHED REPOSITORY
     * =================
     * 
     * Combines Repository + Cache for performance
     * 
     * PATTERN: Decorator Pattern
     * Wraps existing repository with caching layer
     */
    static class CachedUserRepository implements UserRepository {
        private final UserRepository delegate;
        private final Map<String, User> cache;
        
        public CachedUserRepository(UserRepository delegate) {
            this.delegate = delegate;
            this.cache = new ConcurrentHashMap<>();
        }
        
        @Override
        public User save(User user) {
            User saved = delegate.save(user);
            cache.put(saved.getId(), saved); // Update cache
            return saved;
        }
        
        @Override
        public Optional<User> findById(String id) {
            // Check cache first
            User cached = cache.get(id);
            if (cached != null) {
                return Optional.of(cached);
            }
            
            // Cache miss - load from repository
            Optional<User> user = delegate.findById(id);
            user.ifPresent(u -> cache.put(id, u));
            return user;
        }
        
        @Override
        public List<User> findAll() {
            return delegate.findAll();
        }
        
        @Override
        public List<User> findByName(String name) {
            return delegate.findByName(name);
        }
        
        @Override
        public void delete(String id) {
            delegate.delete(id);
            cache.remove(id); // Invalidate cache
        }
        
        @Override
        public boolean exists(String id) {
            return cache.containsKey(id) || delegate.exists(id);
        }
        
        @Override
        public long count() {
            return delegate.count();
        }
        
        // Cache management methods
        public void clearCache() {
            cache.clear();
        }
        
        public int getCacheSize() {
            return cache.size();
        }
    }
    
    static void demonstrateCachedRepository() {
        UserRepository base = new InMemoryUserRepository();
        CachedUserRepository cached = new CachedUserRepository(base);
        
        User user = new User("1", "Alice", "alice@example.com", 25);
        cached.save(user);
        
        // First access - cache miss
        long start = System.nanoTime();
        cached.findById("1");
        long firstAccess = System.nanoTime() - start;
        
        // Second access - cache hit (much faster!)
        start = System.nanoTime();
        cached.findById("1");
        long cachedAccess = System.nanoTime() - start;
        
        assert cachedAccess < firstAccess; // Cache is faster
        assert cached.getCacheSize() == 1;
    }
    
    /**
     * COMPOSITE REPOSITORY
     * ====================
     * 
     * Aggregates multiple repositories
     * Used for federated search across different data sources
     * 
     * REAL SCENARIO:
     * Search users across:
     * - Primary database
     * - Archive database
     * - External API
     */
    static class CompositeUserRepository implements UserRepository {
        private final List<UserRepository> repositories;
        
        public CompositeUserRepository(UserRepository... repos) {
            this.repositories = Arrays.asList(repos);
        }
        
        @Override
        public User save(User user) {
            // Save to primary (first) repository only
            return repositories.get(0).save(user);
        }
        
        @Override
        public Optional<User> findById(String id) {
            // Search across all repositories
            for (UserRepository repo : repositories) {
                Optional<User> found = repo.findById(id);
                if (found.isPresent()) {
                    return found;
                }
            }
            return Optional.empty();
        }
        
        @Override
        public List<User> findAll() {
            // Aggregate results from all repositories
            return repositories.stream()
                .flatMap(repo -> repo.findAll().stream())
                .distinct()
                .collect(Collectors.toList());
        }
        
        @Override
        public List<User> findByName(String name) {
            return repositories.stream()
                .flatMap(repo -> repo.findByName(name).stream())
                .distinct()
                .collect(Collectors.toList());
        }
        
        @Override
        public void delete(String id) {
            // Delete from all repositories
            repositories.forEach(repo -> repo.delete(id));
        }
        
        @Override
        public boolean exists(String id) {
            return repositories.stream()
                .anyMatch(repo -> repo.exists(id));
        }
        
        @Override
        public long count() {
            return repositories.stream()
                .mapToLong(UserRepository::count)
                .sum();
        }
    }
    
    static void demonstrateCompositeRepository() {
        UserRepository primary = new InMemoryUserRepository();
        UserRepository archive = new InMemoryUserRepository();
        
        primary.save(new User("1", "Alice", "alice@example.com", 25));
        archive.save(new User("2", "Bob", "bob@example.com", 30));
        
        // Composite searches both
        CompositeUserRepository composite = new CompositeUserRepository(primary, archive);
        
        assert composite.findById("1").isPresent(); // From primary
        assert composite.findById("2").isPresent(); // From archive
        assert composite.count() == 2; // Total from both
    }
}

/*
 * ==========================================
 * PRODUCTION PATTERNS
 * ==========================================
 * 
 * 1. REPOSITORY HIERARCHY:
 *    CrudRepository (basic CRUD)
 *      ↓
 *    PagingAndSortingRepository (pagination)
 *      ↓
 *    JpaRepository (JPA specific)
 * 
 * 2. NAMING CONVENTIONS:
 *    - findBy{Property}
 *    - findBy{Property}And{Property}
 *    - findBy{Property}Or{Property}
 *    - countBy{Property}
 *    - deleteBy{Property}
 * 
 * 3. TRANSACTION MANAGEMENT:
 *    @Transactional annotation in Spring
 *    Ensures data consistency across operations
 * 
 * 4. TESTING STRATEGY:
 *    - Unit tests: Mock repository
 *    - Integration tests: In-memory database (H2)
 *    - E2E tests: Real database
 * 
 * COMMON MISTAKES:
 * ================
 * ❌ Returning mutable collections (breaks encapsulation)
 * ❌ Not using Optional for nullable results
 * ❌ Too many custom finder methods (use Specification!)
 * ❌ Mixing business logic in repository
 * ❌ Not considering thread-safety
 * 
 * NEXT: 03_ObserverPattern.java
 * (Event-driven collection updates)
 */
