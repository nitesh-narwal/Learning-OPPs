package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ==========================================
 * STRATEGY PATTERN - Pluggable Algorithms
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Strategy = Algorithm ko runtime pe change kar sakte ho
 * Like payment: Credit Card, UPI, Cash - same checkout, different implementation
 * 
 * REAL ANALOGY:
 * Google Maps routes:
 * - Shortest distance
 * - Fastest time
 * - Avoid tolls
 * Same destination, different strategies!
 * 
 * INDUSTRY USAGE:
 * - E-commerce: Different pricing strategies (discount, bulk, seasonal)
 * - Sorting: Different sort algorithms (quicksort, mergesort, heapsort)
 * - Validation: Different validation rules per field
 * - Payment: Credit card, PayPal, cryptocurrency
 * - Search: Different search algorithms (linear, binary, fuzzy)
 * 
 * WHY STRATEGY?
 * - Open/Closed Principle (open for extension, closed for modification)
 * - Easy to add new algorithms without changing existing code
 * - Client can choose algorithm at runtime
 * - Eliminates conditional statements
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class StrategyPattern {
    
    public static void main(String[] args) {
        demonstrateSortingStrategies();
        demonstrateFilteringStrategies();
        demonstratePricingStrategies();
        demonstrateSearchStrategies();
        demonstrateValidationStrategies();
    }
    
    /**
     * SORTING STRATEGIES
     * ==================
     * Different ways to sort collections
     */
    
    // Strategy interface
    interface SortStrategy<T> {
        void sort(List<T> list);
    }
    
    static class Product {
        String name;
        double price;
        int rating;
        
        Product(String name, double price, int rating) {
            this.name = name;
            this.price = price;
            this.rating = rating;
        }
        
        @Override
        public String toString() {
            return String.format("%s ($%.2f, ⭐%d)", name, price, rating);
        }
    }
    
    // Concrete strategies
    static class SortByPrice<T extends Product> implements SortStrategy<T> {
        @Override
        public void sort(List<T> list) {
            list.sort(Comparator.comparingDouble(p -> p.price));
        }
    }
    
    static class SortByRating<T extends Product> implements SortStrategy<T> {
        @Override
        public void sort(List<T> list) {
            list.sort((p1, p2) -> Integer.compare(p2.rating, p1.rating)); // Descending
        }
    }
    
    static class SortByName<T extends Product> implements SortStrategy<T> {
        @Override
        public void sort(List<T> list) {
            list.sort(Comparator.comparing(p -> p.name));
        }
    }
    
    /**
     * Context class - Uses strategy
     */
    static class ProductSorter {
        private SortStrategy<Product> strategy;
        
        public void setStrategy(SortStrategy<Product> strategy) {
            this.strategy = strategy;
        }
        
        public List<Product> sort(List<Product> products) {
            List<Product> copy = new ArrayList<>(products);
            if (strategy != null) {
                strategy.sort(copy);
            }
            return copy;
        }
    }
    
    static void demonstrateSortingStrategies() {
        List<Product> products = Arrays.asList(
            new Product("Laptop", 1200.0, 4),
            new Product("Mouse", 25.0, 5),
            new Product("Keyboard", 75.0, 3)
        );
        
        ProductSorter sorter = new ProductSorter();
        
        // Strategy 1: Sort by price
        sorter.setStrategy(new SortByPrice<>());
        List<Product> byPrice = sorter.sort(products);
        assert byPrice.get(0).name.equals("Mouse"); // Cheapest
        
        // Strategy 2: Sort by rating
        sorter.setStrategy(new SortByRating<>());
        List<Product> byRating = sorter.sort(products);
        assert byRating.get(0).rating == 5; // Highest rated
        
        // Strategy 3: Sort by name
        sorter.setStrategy(new SortByName<>());
        List<Product> byName = sorter.sort(products);
        assert byName.get(0).name.equals("Keyboard"); // Alphabetically first
    }
    
    /**
     * FILTERING STRATEGIES
     * ====================
     * Different criteria for filtering data
     * 
     * Modern approach using Predicate (Java 8+)
     * More flexible than traditional Strategy!
     */
    static class FilterStrategy<T> {
        private final Predicate<T> predicate;
        
        FilterStrategy(Predicate<T> predicate) {
            this.predicate = predicate;
        }
        
        public List<T> filter(List<T> items) {
            return items.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        }
        
        // Combine strategies with AND
        public FilterStrategy<T> and(FilterStrategy<T> other) {
            return new FilterStrategy<>(predicate.and(other.predicate));
        }
        
        // Combine strategies with OR
        public FilterStrategy<T> or(FilterStrategy<T> other) {
            return new FilterStrategy<>(predicate.or(other.predicate));
        }
    }
    
    /**
     * Pre-built filter strategies
     * Reusable across application!
     */
    static class ProductFilters {
        static FilterStrategy<Product> priceUnder(double maxPrice) {
            return new FilterStrategy<>(p -> p.price < maxPrice);
        }
        
        static FilterStrategy<Product> ratingAtLeast(int minRating) {
            return new FilterStrategy<>(p -> p.rating >= minRating);
        }
        
        static FilterStrategy<Product> nameContains(String keyword) {
            return new FilterStrategy<>(p -> 
                p.name.toLowerCase().contains(keyword.toLowerCase())
            );
        }
        
        static FilterStrategy<Product> inPriceRange(double min, double max) {
            return new FilterStrategy<>(p -> p.price >= min && p.price <= max);
        }
    }
    
    static void demonstrateFilteringStrategies() {
        List<Product> products = Arrays.asList(
            new Product("Gaming Laptop", 1500.0, 5),
            new Product("Office Laptop", 800.0, 4),
            new Product("Wireless Mouse", 30.0, 5),
            new Product("Mechanical Keyboard", 120.0, 4)
        );
        
        // Single filter: Products under $100
        FilterStrategy<Product> cheapFilter = ProductFilters.priceUnder(100.0);
        List<Product> cheap = cheapFilter.filter(products);
        assert cheap.size() == 2; // Mouse and Keyboard
        
        // Combined filters: Cheap AND highly rated
        FilterStrategy<Product> bestValue = ProductFilters.priceUnder(100.0)
            .and(ProductFilters.ratingAtLeast(5));
        List<Product> bestValueProducts = bestValue.filter(products);
        assert bestValueProducts.size() == 1; // Only Mouse
        
        // Complex filter: Laptops OR keyboards in price range
        FilterStrategy<Product> complex = ProductFilters.nameContains("laptop")
            .or(ProductFilters.nameContains("keyboard"))
            .and(ProductFilters.inPriceRange(100.0, 1000.0));
        List<Product> result = complex.filter(products);
        assert result.size() == 2; // Office Laptop and Keyboard
    }
    
    /**
     * PRICING STRATEGIES
     * ==================
     * Real-world e-commerce scenario
     */
    interface PricingStrategy {
        double calculatePrice(double basePrice, int quantity);
    }
    
    // No discount
    static class RegularPricing implements PricingStrategy {
        @Override
        public double calculatePrice(double basePrice, int quantity) {
            return basePrice * quantity;
        }
    }
    
    // Bulk discount: 10% off for 10+ items
    static class BulkPricing implements PricingStrategy {
        @Override
        public double calculatePrice(double basePrice, int quantity) {
            if (quantity >= 10) {
                return basePrice * quantity * 0.9; // 10% discount
            }
            return basePrice * quantity;
        }
    }
    
    // Seasonal discount: Flat 20% off
    static class SeasonalPricing implements PricingStrategy {
        @Override
        public double calculatePrice(double basePrice, int quantity) {
            return basePrice * quantity * 0.8; // 20% off
        }
    }
    
    // Premium customer: Always 15% off
    static class PremiumPricing implements PricingStrategy {
        @Override
        public double calculatePrice(double basePrice, int quantity) {
            return basePrice * quantity * 0.85; // 15% off
        }
    }
    
    static class ShoppingCart {
        private PricingStrategy pricingStrategy;
        private final List<CartItem> items = new ArrayList<>();
        
        static class CartItem {
            String product;
            double basePrice;
            int quantity;
            
            CartItem(String product, double basePrice, int quantity) {
                this.product = product;
                this.basePrice = basePrice;
                this.quantity = quantity;
            }
        }
        
        public void setPricingStrategy(PricingStrategy strategy) {
            this.pricingStrategy = strategy;
        }
        
        public void addItem(String product, double price, int quantity) {
            items.add(new CartItem(product, price, quantity));
        }
        
        public double calculateTotal() {
            if (pricingStrategy == null) {
                pricingStrategy = new RegularPricing();
            }
            
            return items.stream()
                .mapToDouble(item -> pricingStrategy.calculatePrice(item.basePrice, item.quantity))
                .sum();
        }
    }
    
    static void demonstratePricingStrategies() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Widget", 10.0, 5);
        cart.addItem("Gadget", 20.0, 3);
        
        // Regular pricing: 5*10 + 3*20 = 110
        cart.setPricingStrategy(new RegularPricing());
        assert Math.abs(cart.calculateTotal() - 110.0) < 0.01;
        
        // Seasonal discount: 110 * 0.8 = 88
        cart.setPricingStrategy(new SeasonalPricing());
        assert Math.abs(cart.calculateTotal() - 88.0) < 0.01;
        
        // Premium customer: 110 * 0.85 = 93.5
        cart.setPricingStrategy(new PremiumPricing());
        assert Math.abs(cart.calculateTotal() - 93.5) < 0.01;
    }
    
    /**
     * SEARCH STRATEGIES
     * =================
     * Different search algorithms for different scenarios
     */
    interface SearchStrategy<T> {
        int search(List<T> list, T target);
    }
    
    // Linear search - works on unsorted lists
    static class LinearSearch<T> implements SearchStrategy<T> {
        @Override
        public int search(List<T> list, T target) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(target)) {
                    return i;
                }
            }
            return -1;
        }
    }
    
    // Binary search - requires sorted list, faster!
    static class BinarySearch<T extends Comparable<T>> implements SearchStrategy<T> {
        @Override
        public int search(List<T> list, T target) {
            int left = 0, right = list.size() - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int comparison = list.get(mid).compareTo(target);
                
                if (comparison == 0) return mid;
                if (comparison < 0) left = mid + 1;
                else right = mid - 1;
            }
            return -1;
        }
    }
    
    // Hash-based search - O(1) average case!
    static class HashSearch<T> implements SearchStrategy<T> {
        private final Set<T> hashSet;
        
        HashSearch(List<T> list) {
            this.hashSet = new HashSet<>(list);
        }
        
        @Override
        public int search(List<T> list, T target) {
            return hashSet.contains(target) ? list.indexOf(target) : -1;
        }
    }
    
    static void demonstrateSearchStrategies() {
        List<Integer> unsorted = Arrays.asList(5, 2, 8, 1, 9);
        List<Integer> sorted = Arrays.asList(1, 2, 5, 8, 9);
        
        // Linear search - works on both
        SearchStrategy<Integer> linear = new LinearSearch<>();
        assert linear.search(unsorted, 8) == 2;
        
        // Binary search - only on sorted
        SearchStrategy<Integer> binary = new BinarySearch<>();
        assert binary.search(sorted, 8) == 3;
        
        // Hash search - fastest for multiple searches
        SearchStrategy<Integer> hash = new HashSearch<>(sorted);
        assert hash.search(sorted, 8) == 3;
    }
    
    /**
     * VALIDATION STRATEGIES
     * =====================
     * Different validation rules
     * 
     * Enterprise applications need flexible validation!
     */
    interface ValidationStrategy<T> {
        boolean isValid(T value);
        String getErrorMessage();
    }
    
    static class EmailValidator implements ValidationStrategy<String> {
        @Override
        public boolean isValid(String email) {
            return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
        }
        
        @Override
        public String getErrorMessage() {
            return "Invalid email format";
        }
    }
    
    static class PhoneValidator implements ValidationStrategy<String> {
        @Override
        public boolean isValid(String phone) {
            return phone != null && phone.matches("^[0-9]{10}$");
        }
        
        @Override
        public String getErrorMessage() {
            return "Phone must be 10 digits";
        }
    }
    
    static class AgeValidator implements ValidationStrategy<Integer> {
        private final int minAge;
        private final int maxAge;
        
        AgeValidator(int minAge, int maxAge) {
            this.minAge = minAge;
            this.maxAge = maxAge;
        }
        
        @Override
        public boolean isValid(Integer age) {
            return age != null && age >= minAge && age <= maxAge;
        }
        
        @Override
        public String getErrorMessage() {
            return String.format("Age must be between %d and %d", minAge, maxAge);
        }
    }
    
    /**
     * Validator that can combine multiple strategies
     */
    static class CompositeValidator<T> {
        private final List<ValidationStrategy<T>> strategies = new ArrayList<>();
        
        public void addStrategy(ValidationStrategy<T> strategy) {
            strategies.add(strategy);
        }
        
        public ValidationResult validate(T value) {
            List<String> errors = new ArrayList<>();
            
            for (ValidationStrategy<T> strategy : strategies) {
                if (!strategy.isValid(value)) {
                    errors.add(strategy.getErrorMessage());
                }
            }
            
            return new ValidationResult(errors.isEmpty(), errors);
        }
        
        static class ValidationResult {
            final boolean valid;
            final List<String> errors;
            
            ValidationResult(boolean valid, List<String> errors) {
                this.valid = valid;
                this.errors = errors;
            }
        }
    }
    
    static void demonstrateValidationStrategies() {
        // Email validation
        ValidationStrategy<String> emailValidator = new EmailValidator();
        assert emailValidator.isValid("test@example.com");
        assert !emailValidator.isValid("invalid-email");
        
        // Phone validation
        ValidationStrategy<String> phoneValidator = new PhoneValidator();
        assert phoneValidator.isValid("9876543210");
        assert !phoneValidator.isValid("123");
        
        // Age validation
        ValidationStrategy<Integer> ageValidator = new AgeValidator(18, 65);
        assert ageValidator.isValid(25);
        assert !ageValidator.isValid(15);
        assert !ageValidator.isValid(70);
    }
}

/*
 * ==========================================
 * STRATEGY VS OTHER PATTERNS
 * ==========================================
 * 
 * STRATEGY vs STATE:
 * - Strategy: Client chooses algorithm
 * - State: Object changes behavior based on internal state
 * 
 * STRATEGY vs Template Method:
 * - Strategy: Composition (has-a)
 * - Template Method: Inheritance (is-a)
 * 
 * WHEN TO USE STRATEGY?
 * =====================
 * ✅ Multiple algorithms for same task
 * ✅ Need to switch algorithms at runtime
 * ✅ Want to avoid conditionals (if-else chains)
 * ✅ Algorithms should be independent
 * 
 * PRODUCTION TIPS:
 * ================
 * 1. FACTORY PATTERN:
 *    Combine with Factory to create strategies
 *    strategy = StrategyFactory.create("bulk_pricing");
 * 
 * 2. CONFIGURATION:
 *    Load strategies from config files
 *    pricing_strategy=SEASONAL in application.properties
 * 
 * 3. CACHING:
 *    Cache strategy instances if stateless
 *    Don't recreate every time!
 * 
 * 4. NAMING:
 *    Use descriptive names
 *    BulkDiscountPricingStrategy vs Strategy1
 * 
 * 5. DEFAULT STRATEGY:
 *    Always have a sensible default
 *    Fail gracefully if no strategy set
 * 
 * COMMON MISTAKES:
 * ================
 * ❌ Creating too many strategies (keep it reasonable)
 * ❌ Strategies with state (should be stateless usually)
 * ❌ Not considering performance of strategy switching
 * ❌ Tight coupling between context and strategies
 * ❌ Forgetting to set strategy (null checks!)
 * 
 * INDUSTRY EXAMPLES:
 * ==================
 * Collections.sort() - Different Comparators
 * Spring Security - Different authentication strategies
 * Payment gateways - Different payment processors
 * Machine Learning - Different algorithms for same problem
 * 
 * NEXT: 05_ProducerConsumer.java
 * (Queue-based concurrent processing)
 */
