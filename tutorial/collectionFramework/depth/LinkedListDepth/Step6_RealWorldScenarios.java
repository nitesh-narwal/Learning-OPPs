package me.niteshh.OPPs.tutorial.collectionFramework.depth.LinkedListDepth;

import java.util.*;

public class Step6_RealWorldScenarios {

    private static String V ;

    static void main(String[] args) {
        /*
         * ╔═══════════════════════════════════════════════════════════════╗
         * ║     STEP 6: REAL-WORLD PROFESSIONAL USE CASES & PATTERNS     ║
         * ╚═══════════════════════════════════════════════════════════════╝
         * 
         * In this step, we see how LinkedList is used in professional code:
         * 1. LRU Cache implementation
         * 2. Task Queue processor
         * 3. Event processing pipeline
         * 4. Undo-Redo functionality
         * 5. Music playlist manager
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SCENARIO 1: LRU CACHE - PROFESSIONAL IMPLEMENTATION
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SCENARIO 1: LRU CACHE - LEAST RECENTLY USED                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Simplified LRU Cache implementation
        class LRUCache<K, V> {
            private final int capacity;
            // LinkedList maintains order: least recent at front, most recent at back
            private final Deque<K> accessOrder;
            private final Map<K, V> cache;
            
            LRUCache(int capacity) {
                this.capacity = capacity;
                this.accessOrder = new LinkedList<>();
                this.cache = new HashMap<>();
            }
            
            public void put(K key, V value) {
                // If key already exists, remove it (will add again at end)
                if (cache.containsKey(key)) {
                    accessOrder.remove(key);  // Remove from middle: O(n)
                    // Note: For production, use LinkedHashMap instead!
                }
                // If cache is full, remove least recently used
                if (cache.size() >= capacity && !cache.containsKey(key)) {
                    K lruKey = accessOrder.removeFirst();  // O(1)
                    cache.remove(lruKey);
                }
                // Add the new/updated key
                cache.put(key, value);
                accessOrder.addLast(key);  // O(1) - most recent
            }
            
            public V get(K key) {
                if (!cache.containsKey(key)) return null;
                
                // Move accessed key to end (most recently used)
                accessOrder.remove(key);  // O(n) - remove from middle
                accessOrder.addLast(key);  // O(1) - add to end
                
                return cache.get(key);
            }
            
            public void printCache() {
                System.out.println("Cache: " + cache);
                System.out.println("Order (LRU → MRU): " + accessOrder);
            }
        }

        // Test LRU Cache
        LRUCache<String, String> lruCache = new LRUCache<>(3);
        
        lruCache.put("user123", "John");      // [John]
        lruCache.put("user456", "Jane");      // [John, Jane]
        lruCache.put("user789", "Bob");       // [John, Jane, Bob]
        System.out.println("After 3 puts:");
        lruCache.printCache();
        System.out.println();
        
        lruCache.put("user999", "Alice");     // [Jane, Bob, Alice] - John removed (LRU)
        System.out.println("After 4th put (John removed as LRU):");
        lruCache.printCache();
        System.out.println();
        
        V = lruCache.get("user456");           // Access Jane, move to end
        System.out.println("After get('user456'):");
        lruCache.printCache();
        System.out.println();
        
        /*
         * Why LinkedList for LRU Cache?
         * • Fast access/removal from both ends: O(1)
         * • addLast(): Mark as most recently used
         * • removeFirst(): Remove least recently used
         * 
         * BUT for production, use:
         * • LinkedHashMap: Optimized for this pattern
         * • Or use LinkedList with HashMap together
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SCENARIO 2: TASK QUEUE PROCESSOR - PROFESSIONAL PATTERN
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SCENARIO 2: TASK QUEUE - FIFO PROCESSING                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Task class
        class Task {
            String id;
            String description;
            long createdAt;
            
            Task(String id, String description) {
                this.id = id;
                this.description = description;
                this.createdAt = System.currentTimeMillis();
            }
            
            @Override
            public String toString() {
                return id + ": " + description;
            }
        }

        // Task Queue - Professional Implementation
        class TaskQueue {
            private final Queue<Task> queue;
            private final int maxSize;
            private int processedCount = 0;
            
            TaskQueue(int maxSize) {
                this.queue = new LinkedList<>();  // Using LinkedList for Queue
                this.maxSize = maxSize;
            }
            
            public void addTask(Task task) {
                if (queue.size() >= maxSize) {
                    System.out.println("⚠️ Queue full! Cannot add: " + task);
                    return;
                }
                queue.offer(task);  // O(1) - add to end
                System.out.println("✓ Added: " + task);
            }
            
            public void processNext() {
                if (queue.isEmpty()) {
                    System.out.println("No tasks to process");
                    return;
                }
                
                Task task = queue.poll();  // O(1) - remove from front (FIFO)
                System.out.println("⚙️ Processing: " + task);
                processedCount++;
                
                // Simulate processing
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                
                System.out.println("✓ Completed: " + task);
            }
            
            public void processAll() {
                while (!queue.isEmpty()) {
                    processNext();
                    System.out.println();
                }
            }
            
            public void status() {
                System.out.println("Queue Status:");
                System.out.println("  Pending: " + queue.size());
                System.out.println("  Processed: " + processedCount);
                System.out.println("  Total: " + (queue.size() + processedCount));
            }
        }

        // Demo Task Queue
        TaskQueue taskQueue = new TaskQueue(5);
        taskQueue.addTask(new Task("T1", "Download file"));
        taskQueue.addTask(new Task("T2", "Resize image"));
        taskQueue.addTask(new Task("T3", "Send email"));
        System.out.println();
        
        taskQueue.processAll();
        taskQueue.status();
        System.out.println();

        /*
         * Why LinkedList for Task Queue?
         * • add() at end: O(1)
         * • remove() from front: O(1)
         * • FIFO behavior: First submitted, first processed
         * • Professional job queues (RabbitMQ, Kafka use similar patterns)
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SCENARIO 3: UNDO-REDO FUNCTIONALITY - STACK PATTERN
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SCENARIO 3: UNDO-REDO (Editor Text History)                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Document Editor with Undo-Redo
        class TextEditor {
            private StringBuilder document;
            private Stack<String> undoStack;
            private Stack<String> redoStack;
            
            TextEditor() {
                this.document = new StringBuilder();
                this.undoStack = new Stack<>();  // Using LinkedList for Stack
                this.redoStack = new Stack<>();
            }
            
            public void type(String text) {
                saveState();  // Save current state for undo
                document.append(text);
                redoStack.clear();  // Clear redo stack on new action
                System.out.println("Typed: '" + text + "'");
                System.out.println("Content: '" + document.toString() + "'");
            }
            
            private void saveState() {
                undoStack.push(document.toString());  // O(1) - save current state
            }
            
            public void undo() {
                if (undoStack.isEmpty()) {
                    System.out.println("Nothing to undo");
                    return;
                }
                
                redoStack.push(document.toString());  // Save for redo
                document = new StringBuilder(undoStack.pop());  // O(1)
                System.out.println("Undo - Content: '" + document.toString() + "'");
            }
            
            public void redo() {
                if (redoStack.isEmpty()) {
                    System.out.println("Nothing to redo");
                    return;
                }
                
                undoStack.push(document.toString());  // Save for undo
                document = new StringBuilder(redoStack.pop());  // O(1)
                System.out.println("Redo - Content: '" + document.toString() + "'");
            }
            
            public String getContent() {
                return document.toString();
            }
        }

        // Demo Text Editor
        TextEditor editor = new TextEditor();
        editor.type("Hello");
        editor.type(" World");
        editor.type("!");
        System.out.println();
        
        editor.undo();
        editor.undo();
        System.out.println();
        
        editor.redo();
        System.out.println();

        /*
         * Why LinkedList for Undo-Redo?
         * • push(): Add to stack O(1)
         * • pop(): Remove from stack O(1)
         * • LIFO: Last action is first undone
         * • Professional editors use this pattern (VS Code, Sublime, etc.)
         */

        /*
         * ═════════════════════════════════════════════════════════════════
         * SCENARIO 4: MUSIC PLAYLIST - DEQUE PATTERN
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SCENARIO 4: MUSIC PLAYLIST (Shuffle, Queue, History)       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // Song class
        class Song {
            String title;
            String artist;
            int duration;
            
            Song(String title, String artist, int duration) {
                this.title = title;
                this.artist = artist;
                this.duration = duration;
            }
            
            @Override
            public String toString() {
                return title + " - " + artist + " (" + duration + "s)";
            }
        }

        // Music Player with Queue and History
        class MusicPlayer {
            private Deque<Song> playbackQueue;    // Songs to play
            private Deque<Song> history;          // Previously played
            private Song currentSong;
            
            MusicPlayer() {
                this.playbackQueue = new LinkedList<>();
                this.history = new LinkedList<>();
                this.currentSong = null;
            }
            
            public void queue(Song song) {
                playbackQueue.addLast(song);  // O(1)
                System.out.println("Queued: " + song);
            }
            
            public void play() {
                if (playbackQueue.isEmpty()) {
                    System.out.println("Queue is empty");
                    return;
                }
                
                if (currentSong != null) {
                    history.addLast(currentSong);  // O(1) - save to history
                }
                
                currentSong = playbackQueue.removeFirst();  // O(1) - play next
                System.out.println("♫ Now playing: " + currentSong);
            }
            
            public void previous() {
                if (history.isEmpty()) {
                    System.out.println("No previous song");
                    return;
                }
                
                if (currentSong != null) {
                    playbackQueue.addFirst(currentSong);  // O(1) - put back in queue
                }
                
                currentSong = history.removeLast();  // O(1) - go back
                System.out.println("⏮ Previous: " + currentSong);
            }
            
            public void showQueue() {
                System.out.println("Queue: " + playbackQueue);
                System.out.println("Playing: " + currentSong);
                System.out.println("History: " + history);
            }
        }

        // Demo Music Player
        MusicPlayer player = new MusicPlayer();
        player.queue(new Song("Song A", "Artist 1", 180));
        player.queue(new Song("Song B", "Artist 2", 200));
        player.queue(new Song("Song C", "Artist 3", 220));
        System.out.println();
        
        player.play();
        player.play();
        player.play();
        System.out.println();
        
        player.showQueue();
        System.out.println();
        
        player.previous();
        System.out.println();

        /*
         * ═════════════════════════════════════════════════════════════════
         * SUMMARY: REAL-WORLD PATTERNS WITH LINKEDLIST
         * ═════════════════════════════════════════════════════════════════
         */
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SUMMARY: LINKEDLIST IN PRODUCTION                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        System.out.println("1. LRU CACHE:");
        System.out.println("   • Maintains access order");
        System.out.println("   • Add to end (most recent)");
        System.out.println("   • Remove from front (least recent)");
        System.out.println("   • Production: Use LinkedHashMap!");
        System.out.println();

        System.out.println("2. TASK QUEUE:");
        System.out.println("   • FIFO processing");
        System.out.println("   • add() to end: O(1)");
        System.out.println("   • remove() from front: O(1)");
        System.out.println("   • Real: Message queues, job processors");
        System.out.println();

        System.out.println("3. UNDO-REDO:");
        System.out.println("   • LIFO stacks");
        System.out.println("   • push() to stack: O(1)");
        System.out.println("   • pop() from stack: O(1)");
        System.out.println("   • Used in: Text editors, graphics apps");
        System.out.println();

        System.out.println("4. MUSIC PLAYER:");
        System.out.println("   • Deque with history");
        System.out.println("   • Add/remove from both ends: O(1)");
        System.out.println("   • Forward and backward navigation");
        System.out.println("   • Used in: Music apps, media players");
        System.out.println();

        System.out.println("KEY TAKEAWAYS:");
        System.out.println("✓ Always use the right data structure for the pattern");
        System.out.println("✓ LinkedList is perfect for Queue/Stack/Deque");
        System.out.println("✓ But use specialized classes when available (LinkedHashMap, ArrayDeque)");
        System.out.println("✓ For production, consider memory and performance trade-offs");
    }
}

