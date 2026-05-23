package me.niteshh.OPPs.tutorial.collectionFramework.depth.hashmapDepth;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║     STEP 7: REAL-WORLD PATTERNS — HOW PROFESSIONALS USE HASHMAPS   ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 *
 * WHAT YOU WILL LEARN HERE:
 *  1. Two-Sum problem (classic interview pattern)
 *  2. Grouping / bucketing data
 *  3. Caching / memoization
 *  4. Frequency analysis
 *  5. Graph adjacency list
 *  6. Index building (inverted index)
 *  7. Deduplication with metadata
 *  8. Nested maps (Map of Maps)
 */
public class Step7_RealWorldPatterns {

    public static void main(String[] args) {

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 1: TWO-SUM — THE CLASSIC INTERVIEW PROBLEM
         * ─────────────────────────────────────────────────────────────────
         *
         * Problem: Given an array of integers and a target sum,
         * find two numbers that add up to the target.
         *
         * Brute force: O(n²) — check every pair
         * HashMap approach: O(n) — one pass
         *
         * KEY INSIGHT: For each number x, we need (target - x).
         * Store each number in a map as we go.
         * For each new number, check if its complement is already in the map.
         */

        int[] nums = {2, 7, 11, 15};
        int target = 9;

        Map<Integer, Integer> seen = new HashMap<>(); // value → index
        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (seen.containsKey(complement)) {
                result[0] = seen.get(complement); // index of complement
                result[1] = i;                    // current index
                break;
            }

            seen.put(nums[i], i); // store current number and its index
        }

        System.out.println("Two-Sum result: [" + result[0] + ", " + result[1] + "]");
        // [0, 1] — nums[0]=2 and nums[1]=7 add up to 9

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 2: GROUPING DATA (GROUP BY)
         * ─────────────────────────────────────────────────────────────────
         *
         * Group a list of objects by some property.
         * This is the HashMap equivalent of SQL's GROUP BY.
         */

        List<String> emails = Arrays.asList(
            "alice@gmail.com", "bob@yahoo.com", "charlie@gmail.com",
            "diana@outlook.com", "eve@yahoo.com", "frank@gmail.com"
        );

        // Group emails by domain
        Map<String, List<String>> byDomain = new HashMap<>();
        for (String email : emails) {
            String domain = email.split("@")[1];
            byDomain.computeIfAbsent(domain, k -> new ArrayList<>()).add(email);
        }

        System.out.println("\nEmails by domain:");
        byDomain.forEach((domain, list) ->
            System.out.println("  " + domain + ": " + list));

        // Java 8 Streams version (even cleaner):
        Map<String, List<String>> byDomainStream = emails.stream()
            .collect(Collectors.groupingBy(email -> email.split("@")[1]));

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 3: MEMOIZATION — CACHE EXPENSIVE COMPUTATIONS
         * ─────────────────────────────────────────────────────────────────
         *
         * Store results of expensive function calls.
         * If called again with same input, return cached result.
         * Turns O(2^n) recursive Fibonacci into O(n).
         */

        System.out.println("\nFibonacci with memoization:");
        Map<Integer, Long> memo = new HashMap<>();
        System.out.println("fib(10) = " + fib(10, memo));
        System.out.println("fib(50) = " + fib(50, memo));
        // Without memo, fib(50) would take billions of operations
        // With memo, it's just 50 lookups

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 4: FREQUENCY MAP — COUNT OCCURRENCES
         * ─────────────────────────────────────────────────────────────────
         *
         * Count how many times each item appears.
         * Used in: analytics, text processing, voting systems.
         */

        String sentence = "the quick brown fox jumps over the lazy dog the fox";
        String[] wordsArr = sentence.split(" ");

        Map<String, Integer> freq = new HashMap<>();
        for (String word : wordsArr) {
            freq.merge(word, 1, Integer::sum);
        }

        // Find top 3 most frequent words
        System.out.println("\nTop 3 words:");
        freq.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(3)
            .forEach(e -> System.out.println("  '" + e.getKey() + "' → " + e.getValue() + " times"));

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 5: GRAPH AS ADJACENCY LIST
         * ─────────────────────────────────────────────────────────────────
         *
         * Represent a graph where each node maps to its neighbors.
         * Used in: social networks, routing, dependency resolution.
         */

        Map<String, List<String>> graph = new HashMap<>();

        // Build graph: A connects to B and C, B connects to D, etc.
        addEdge(graph, "A", "B");
        addEdge(graph, "A", "C");
        addEdge(graph, "B", "D");
        addEdge(graph, "C", "D");
        addEdge(graph, "D", "E");

        System.out.println("\nGraph adjacency list:");
        graph.forEach((node, neighbors) ->
            System.out.println("  " + node + " → " + neighbors));

        // BFS traversal using the graph
        System.out.println("BFS from A: " + bfs(graph, "A"));

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 6: INVERTED INDEX — SEARCH ENGINE CORE
         * ─────────────────────────────────────────────────────────────────
         *
         * Map each word to the list of documents containing it.
         * This is how search engines find documents for a query.
         */

        Map<String, String> documents = new LinkedHashMap<>();
        documents.put("doc1", "java hashmap tutorial");
        documents.put("doc2", "java collections framework");
        documents.put("doc3", "hashmap vs treemap comparison");

        // Build inverted index: word → list of doc IDs
        Map<String, List<String>> invertedIndex = new HashMap<>();
        for (Map.Entry<String, String> doc : documents.entrySet()) {
            String docId = doc.getKey();
            String[] docWords = doc.getValue().split(" ");
            for (String word : docWords) {
                invertedIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(docId);
            }
        }

        System.out.println("\nInverted index:");
        invertedIndex.forEach((word, docs) ->
            System.out.println("  '" + word + "' → " + docs));

        // Search: find docs containing "java" AND "hashmap"
        List<String> javaDocs    = invertedIndex.getOrDefault("java", Collections.emptyList());
        List<String> hashmapDocs = invertedIndex.getOrDefault("hashmap", Collections.emptyList());

        List<String> intersection = new ArrayList<>(javaDocs);
        intersection.retainAll(hashmapDocs); // keep only common docs
        System.out.println("Docs with 'java' AND 'hashmap': " + intersection);

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 7: NESTED MAP — MAP OF MAPS
         * ─────────────────────────────────────────────────────────────────
         *
         * When you need a 2D lookup: map[row][col] or map[user][product].
         * Used in: spreadsheets, game boards, user preferences.
         */

        // Student → Subject → Score
        Map<String, Map<String, Integer>> studentScores = new HashMap<>();

        addScore(studentScores, "Alice", "Math",    95);
        addScore(studentScores, "Alice", "Science",  88);
        addScore(studentScores, "Bob",   "Math",    72);
        addScore(studentScores, "Bob",   "Science",  80);

        System.out.println("\nStudent scores:");
        studentScores.forEach((student, subjects) -> {
            System.out.println("  " + student + ":");
            subjects.forEach((subject, score) ->
                System.out.println("    " + subject + ": " + score));
        });

        // Lookup: Alice's Math score
        int aliceMath = studentScores
            .getOrDefault("Alice", Collections.emptyMap())
            .getOrDefault("Math", 0);
        System.out.println("Alice's Math score: " + aliceMath);

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 8: DEDUPLICATION WITH METADATA
         * ─────────────────────────────────────────────────────────────────
         *
         * Remove duplicates while keeping track of first/last occurrence.
         * Used in: log deduplication, event deduplication.
         */

        String[] events = {"login", "click", "login", "purchase", "click", "logout"};

        // Keep only first occurrence of each event, with its index
        Map<String, Integer> firstOccurrence = new LinkedHashMap<>();
        for (int i = 0; i < events.length; i++) {
            firstOccurrence.putIfAbsent(events[i], i); // only adds if not present
        }

        System.out.println("\nFirst occurrence of each event:");
        firstOccurrence.forEach((event, idx) ->
            System.out.println("  " + event + " at index " + idx));

        /*
         * ─────────────────────────────────────────────────────────────────
         * PATTERN 9: IMMUTABLE MAP — SAFE CONSTANTS
         * ─────────────────────────────────────────────────────────────────
         *
         * Use Map.of() or Map.copyOf() for maps that should never change.
         * Common for: HTTP status codes, error messages, config constants.
         */

        // Java 9+ Map.of() — up to 10 entries
        Map<Integer, String> httpStatus = Map.of(
            200, "OK",
            404, "Not Found",
            500, "Internal Server Error",
            401, "Unauthorized"
        );

        System.out.println("\nHTTP 404: " + httpStatus.get(404));
        // httpStatus.put(201, "Created"); // throws UnsupportedOperationException!

        // For more than 10 entries, use Map.ofEntries():
        Map<String, String> moreConfig = Map.ofEntries(
            Map.entry("host", "localhost"),
            Map.entry("port", "8080"),
            Map.entry("db",   "mydb")
            // ... can add more entries
        );

        System.out.println("Host: " + moreConfig.get("host"));
    }

    // ─── Helper: Fibonacci with memoization ─────────────────────────────────
    static long fib(int n, Map<Integer, Long> memo) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n); // cache hit!

        long result = fib(n - 1, memo) + fib(n - 2, memo);
        memo.put(n, result); // cache the result
        return result;
    }

    // ─── Helper: Add undirected edge to graph ────────────────────────────────
    static void addEdge(Map<String, List<String>> graph, String from, String to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        graph.computeIfAbsent(to,   k -> new ArrayList<>()).add(from);
    }

    // ─── Helper: BFS traversal ───────────────────────────────────────────────
    static List<String> bfs(Map<String, List<String>> graph, String start) {
        List<String> visited = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        seen.add(start);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            visited.add(node);

            for (String neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                if (!seen.contains(neighbor)) {
                    seen.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }

    // ─── Helper: Add score to nested map ────────────────────────────────────
    static void addScore(Map<String, Map<String, Integer>> map,
                         String student, String subject, int score) {
        map.computeIfAbsent(student, k -> new HashMap<>()).put(subject, score);
    }
}
