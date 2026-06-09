package me.niteshh.OPPs.tutorial.collectionFramework.AdvancedTopics;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * ==========================================
 * OBSERVER PATTERN - Event-Driven Collections
 * ==========================================
 * 
 * HINGLISH CONTEXT:
 * Observer = Listener pattern, jab collection change ho to sabko notify karo
 * 
 * Real-life example:
 * YouTube subscription - Jab creator video upload kare, sabhi subscribers ko notify
 * 
 * PRODUCTION USAGE:
 * - GUI frameworks (Swing, JavaFX)
 * - Event buses (Guava EventBus)
 * - Reactive programming (RxJava)
 * - Spring Application Events
 * 
 * WHY OBSERVER?
 * - Loose coupling between components
 * - One-to-many dependency
 * - Dynamic subscription/unsubscription
 * - Event-driven architecture foundation
 * 
 * @author Nitesh Kumar
 * @level Advanced
 */
class ObserverPattern {
    
    public static void main(String[] args) {
        demonstrateBasicObserver();
        demonstrateTypedEvents();
        demonstrateEventBus();
        demonstrateReactiveCollection();
    }
    
    /**
     * BASIC OBSERVER IMPLEMENTATION
     * ==============================
     * 
     * Classic GoF pattern implementation
     */
    
    // Observer interface
    interface CollectionObserver<T> {
        void onAdd(T item);
        void onRemove(T item);
        void onClear();
    }
    
    // Observable collection
    static class ObservableList<T> {
        private final List<T> items = new ArrayList<>();
        private final List<CollectionObserver<T>> observers = new CopyOnWriteArrayList<>();
        
        public void addObserver(CollectionObserver<T> observer) {
            observers.add(observer);
        }
        
        public void removeObserver(CollectionObserver<T> observer) {
            observers.remove(observer);
        }
        
        public void add(T item) {
            items.add(item);
            // Notify all observers
            observers.forEach(obs -> obs.onAdd(item));
        }
        
        public void remove(T item) {
            if (items.remove(item)) {
                observers.forEach(obs -> obs.onRemove(item));
            }
        }
        
        public void clear() {
            items.clear();
            observers.forEach(CollectionObserver::onClear);
        }
        
        public List<T> getItems() {
            return new ArrayList<>(items); // Defensive copy
        }
    }
    
    static void demonstrateBasicObserver() {
        ObservableList<String> playlist = new ObservableList<>();
        
        // Logger observer - logs all changes
        CollectionObserver<String> logger = new CollectionObserver<String>() {
            @Override
            public void onAdd(String item) {
                // Minimal logging, real observer would use proper logger
            }
            
            @Override
            public void onRemove(String item) {
                // Logging removed
            }
            
            @Override
            public void onClear() {
                // Logging cleared
            }
        };
        
        // Analytics observer - tracks statistics
        class AnalyticsObserver implements CollectionObserver<String> {
            private int addCount = 0;
            private int removeCount = 0;
            
            @Override
            public void onAdd(String item) {
                addCount++;
            }
            
            @Override
            public void onRemove(String item) {
                removeCount++;
            }
            
            @Override
            public void onClear() {
                // Reset counters
                addCount = 0;
                removeCount = 0;
            }
            
            public int getAddCount() { return addCount; }
            public int getRemoveCount() { return removeCount; }
        }
        
        AnalyticsObserver analytics = new AnalyticsObserver();
        
        playlist.addObserver(logger);
        playlist.addObserver(analytics);
        
        // Perform operations
        playlist.add("Song1");
        playlist.add("Song2");
        playlist.remove("Song1");
        
        assert analytics.getAddCount() == 2;
        assert analytics.getRemoveCount() == 1;
    }
    
    /**
     * TYPED EVENT SYSTEM
     * ==================
     * 
     * More flexible than basic observer
     * Supports different event types with data
     */
    
    // Event types
    interface CollectionEvent {
        long getTimestamp();
    }
    
    static class ItemAddedEvent implements CollectionEvent {
        private final Object item;
        private final int index;
        private final long timestamp;
        
        public ItemAddedEvent(Object item, int index) {
            this.item = item;
            this.index = index;
            this.timestamp = System.currentTimeMillis();
        }
        
        public Object getItem() { return item; }
        public int getIndex() { return index; }
        
        @Override
        public long getTimestamp() { return timestamp; }
    }
    
    static class ItemRemovedEvent implements CollectionEvent {
        private final Object item;
        private final int index;
        private final long timestamp;
        
        public ItemRemovedEvent(Object item, int index) {
            this.item = item;
            this.index = index;
            this.timestamp = System.currentTimeMillis();
        }
        
        public Object getItem() { return item; }
        public int getIndex() { return index; }
        
        @Override
        public long getTimestamp() { return timestamp; }
    }
    
    // Event listener
    interface EventListener {
        void onEvent(CollectionEvent event);
    }
    
    // Event-driven collection
    static class EventDrivenList<T> {
        private final List<T> items = new ArrayList<>();
        private final Map<Class<?>, List<EventListener>> listeners = new HashMap<>();
        
        public void addEventListener(Class<? extends CollectionEvent> eventType, 
                                    EventListener listener) {
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
        }
        
        private void fireEvent(CollectionEvent event) {
            List<EventListener> eventListeners = listeners.get(event.getClass());
            if (eventListeners != null) {
                eventListeners.forEach(listener -> listener.onEvent(event));
            }
        }
        
        public void add(T item) {
            int index = items.size();
            items.add(item);
            fireEvent(new ItemAddedEvent(item, index));
        }
        
        public void remove(int index) {
            if (index >= 0 && index < items.size()) {
                T item = items.remove(index);
                fireEvent(new ItemRemovedEvent(item, index));
            }
        }
    }
    
    static void demonstrateTypedEvents() {
        EventDrivenList<String> cart = new EventDrivenList<>();
        
        // Listener for add events
        cart.addEventListener(ItemAddedEvent.class, event -> {
            // Track cart additions for analytics
            ItemAddedEvent addEvent = (ItemAddedEvent) event;
            // Process: addEvent.getItem(), addEvent.getIndex()
        });
        
        // Listener for remove events
        cart.addEventListener(ItemRemovedEvent.class, event -> {
            // Handle item removal
            ItemRemovedEvent removeEvent = (ItemRemovedEvent) event;
            // Process removal
        });
        
        cart.add("Product1");
        cart.add("Product2");
        cart.remove(0);
    }
    
    /**
     * EVENT BUS PATTERN
     * =================
     * 
     * Centralized event distribution system
     * Like Google Guava EventBus
     * 
     * BENEFITS:
     * - Decouples publishers and subscribers
     * - No direct dependencies
     * - Easy to add new event types
     * - Perfect for microservices communication
     */
    static class EventBus {
        private final Map<Class<?>, List<Consumer<?>>> subscribers = new ConcurrentHashMap<>();
        
        public <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
            subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                      .add(handler);
        }
        
        @SuppressWarnings("unchecked")
        public <T> void publish(T event) {
            Class<?> eventType = event.getClass();
            List<Consumer<?>> handlers = subscribers.get(eventType);
            
            if (handlers != null) {
                handlers.forEach(handler -> 
                    ((Consumer<T>) handler).accept(event));
            }
        }
        
        public <T> void unsubscribe(Class<T> eventType, Consumer<T> handler) {
            List<Consumer<?>> handlers = subscribers.get(eventType);
            if (handlers != null) {
                handlers.remove(handler);
            }
        }
    }
    
    // Domain events
    static class UserRegisteredEvent {
        final String userId;
        final String email;
        
        UserRegisteredEvent(String userId, String email) {
            this.userId = userId;
            this.email = email;
        }
    }
    
    static class OrderPlacedEvent {
        final String orderId;
        final String userId;
        final double amount;
        
        OrderPlacedEvent(String orderId, String userId, double amount) {
            this.orderId = orderId;
            this.userId = userId;
            this.amount = amount;
        }
    }
    
    static void demonstrateEventBus() {
        EventBus eventBus = new EventBus();
        
        // Email service subscribes to user registration
        eventBus.subscribe(UserRegisteredEvent.class, event -> {
            // Send welcome email (in real app)
            // emailService.sendWelcomeEmail(event.email);
        });
        
        // Analytics service subscribes to user registration
        eventBus.subscribe(UserRegisteredEvent.class, event -> {
            // Track new user signup
            // analytics.track("user_signup", event.userId);
        });
        
        // Notification service subscribes to orders
        eventBus.subscribe(OrderPlacedEvent.class, event -> {
            // Send order confirmation
            // notificationService.notifyUser(event.userId, "Order placed!");
        });
        
        // Inventory service subscribes to orders
        eventBus.subscribe(OrderPlacedEvent.class, event -> {
            // Reduce inventory
            // inventoryService.decreaseStock(event.orderId);
        });
        
        // Publish events
        eventBus.publish(new UserRegisteredEvent("user123", "user@example.com"));
        eventBus.publish(new OrderPlacedEvent("order456", "user123", 99.99));
        
        // Multiple subscribers get notified automatically!
    }
    
    /**
     * REACTIVE COLLECTION
     * ===================
     * 
     * Combines Observer + Functional programming
     * Inspired by RxJava, Project Reactor
     * 
     * OPERATORS:
     * - map: Transform items
     * - filter: Select items
     * - forEach: Process items
     */
    static class ReactiveList<T> {
        private final List<T> items = new ArrayList<>();
        private final List<Consumer<T>> addListeners = new CopyOnWriteArrayList<>();
        private final List<Consumer<T>> removeListeners = new CopyOnWriteArrayList<>();
        
        // Subscribe to additions
        public ReactiveList<T> onAdd(Consumer<T> listener) {
            addListeners.add(listener);
            return this;
        }
        
        // Subscribe to removals
        public ReactiveList<T> onRemove(Consumer<T> listener) {
            removeListeners.add(listener);
            return this;
        }
        
        // Fluent API for filtering additions
        public ReactiveList<T> filterAdd(java.util.function.Predicate<T> predicate, 
                                         Consumer<T> action) {
            return onAdd(item -> {
                if (predicate.test(item)) {
                    action.accept(item);
                }
            });
        }
        
        public void add(T item) {
            items.add(item);
            addListeners.forEach(listener -> listener.accept(item));
        }
        
        public void remove(T item) {
            if (items.remove(item)) {
                removeListeners.forEach(listener -> listener.accept(item));
            }
        }
        
        public List<T> getItems() {
            return new ArrayList<>(items);
        }
    }
    
    static void demonstrateReactiveCollection() {
        ReactiveList<Integer> scores = new ReactiveList<>();
        
        // React to high scores only
        scores.filterAdd(
            score -> score > 80,  // Predicate
            score -> {            // Action
                // Send achievement notification
                // notificationService.sendAchievement(score);
            }
        );
        
        // React to all additions
        scores.onAdd(score -> {
            // Update leaderboard
            // leaderboard.update(score);
        });
        
        // React to removals
        scores.onRemove(score -> {
            // Log removal for audit
            // auditLog.log("Score removed: " + score);
        });
        
        // Add scores - observers react automatically
        scores.add(50);  // No high score notification
        scores.add(95);  // High score notification sent!
        scores.add(70);  // No high score notification
        
        assert scores.getItems().size() == 3;
    }
}

/*
 * ==========================================
 * PRODUCTION PATTERNS
 * ==========================================
 * 
 * 1. OBSERVER VARIATIONS:
 *    - Push: Observer gets all data
 *    - Pull: Observer queries for data
 *    - Hybrid: Notification + query on demand
 * 
 * 2. THREAD SAFETY:
 *    - Use CopyOnWriteArrayList for observers
 *    - Consider synchronization for critical sections
 *    - Be careful with observer callbacks (can deadlock!)
 * 
 * 3. MEMORY LEAKS:
 *    - Always unsubscribe when done
 *    - Use WeakReference for observers
 *    - Implement proper cleanup in dispose()
 * 
 * 4. EVENT ORDERING:
 *    - Guarantee: FIFO order within same thread
 *    - No guarantee: Across threads
 *    - Solution: Event queue with priorities
 * 
 * COMMON MISTAKES:
 * ================
 * ❌ Forgetting to unsubscribe (memory leak!)
 * ❌ Long-running observer callbacks (blocks others)
 * ❌ Modifying collection in observer (ConcurrentModification!)
 * ❌ Not handling observer exceptions (breaks chain)
 * ❌ Circular dependencies (A observes B, B observes A)
 * 
 * BEST PRACTICES:
 * ===============
 * ✅ Keep observer callbacks fast
 * ✅ Use async processing for heavy work
 * ✅ Handle exceptions in observers
 * ✅ Provide unsubscribe mechanism
 * ✅ Document observer contract clearly
 * 
 * REAL-WORLD EXAMPLES:
 * ====================
 * 1. Spring ApplicationEventPublisher
 * 2. Android LiveData
 * 3. JavaFX Property Bindings
 * 4. RxJava Observables
 * 5. Java Swing Listeners
 * 
 * INTERVIEW QUESTIONS:
 * ====================
 * Q: Observer vs Pub-Sub difference?
 * A: Observer = tight coupling, Pub-Sub = event bus decouples
 * 
 * Q: How to prevent memory leaks in Observer?
 * A: Always unsubscribe, use WeakReference, or auto-cleanup
 * 
 * Q: Observer pattern drawbacks?
 * A: Can cause cascading updates, hard to debug event flow
 * 
 * NEXT: 04_StrategyPattern.java
 * (Pluggable sorting/filtering algorithms)
 */
