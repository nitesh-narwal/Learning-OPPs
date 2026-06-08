package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsDemo {
    static void main(String[] args) {
        // Feature Introduced in Java 8
        // Processing collection of data in a functional & declarative manner
        // Simplify data processing
        // Improve readability and maintainability
        // Enable Easy Parallelism

        // What is Streams?
        // A sequence of elements supporting functional and declarative programming

        // How to use Streams?
        // Source, intermediate operationd, terminal operations

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // Traditional way to count the even numbers
        int evenCount = 0;
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                evenCount++;
            }
        }
        System.out.println("Even numbers: " + evenCount);

        // counting using streams ( we convert collections to streams, so that we can perform operations on them and get desired result)
        long count = numbers.stream() // Source
                .filter(n -> n % 2 == 0) // Intermediate operation
                .count();// Terminal operation
        System.out.println("Counting even numbers through Streams : " + count);

        // Creating Streams
        // 1. Collection to Stream
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Jill", "Jeremy");
        Stream<String> stream = names.stream();

        // 2. Array to Stream
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        IntStream stream1 = Arrays.stream(array);

        // 3. Using Stream.of()
        Stream<String> stream2 = Stream.of("John", "Jane", "Jack", "Jill", "Jeremy");

        // 4. Using Stream.iterate()
        Stream<Integer> stream3 = Stream.iterate(1, n -> n + 1); // Infinite stream of natural numbers
        System.out.println("\nFirst 10  natural numbers using Stream.iterate:");
        stream3.limit(10).forEach(System.out::println); // Limiting to first 10 numbers and printing them

        // 5. Using Stream.generate
        Stream<Double> generate = Stream.generate(() -> Math.random() * 100);// Infinite stream of random numbers between 0 and 100
        System.out.println("\nFirst 100 random numbers using Stream.generate:");
        generate.limit(100).forEach(System.out::println); // Limiting to first 10 numbers and printing them

    }
}
