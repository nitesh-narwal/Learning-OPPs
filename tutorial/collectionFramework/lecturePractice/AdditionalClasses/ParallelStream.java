package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ParallelStream {
    static void main(String[] args) {
        // A type of streams that enables parallel processing of elements.
        // Allowing multiple threads to process parts of the stream simultaneously.
        // This can improve performance for large data sets.
        // Work load is distributed across multiple threads.

        long startTime = System.currentTimeMillis();
        List<Integer> list = Stream.iterate(1, n -> n + 1).limit(20000).toList();
        List<Integer> list1 = list.stream().map(ParallelStream::factorial).toList();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to calculate factorials with sequential stream : " + (endTime - startTime) + " ms");

        startTime = System.currentTimeMillis();
         list1 = list.parallelStream().map(ParallelStream::factorial).toList();
        endTime = System.currentTimeMillis();
        System.out.println("Time taken to calculate factorials with parallel stream : " + (endTime - startTime) + " ms");

        // Parallel streaam are most effective for CPU-intensive tasks, such as complex calculations or processing large data sets.
        // They may add overhead for simple tasks or small data sets,
        // so it's important to consider the nature of the task and the size of the data when deciding to use parallel streams.

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        AtomicInteger sum = new AtomicInteger(0);
        //List<Integer> cumSum = numbers.parallelStream().map(n -> sum.addAndGet(n)).toList(); // Cumulative sum of numbers
        //List<Integer> cumSum = numbers.parallelStream().map(sum::getAndAdd).sequential().toList();  ---> Added sequential() to make it sequential stream to avoid the error
        List<Integer> cumSum = numbers.parallelStream().map(sum::getAndAdd).toList();
        System.out.println("Expected cumulative sum : [ 1, 3, 6, 10, 15, 21, 28, 36, 45, 55 ]");
        System.out.println("Actual cumulative sum using parallel stream : " + cumSum);
        // we got this error because of the non-deterministic nature of parallel streams.
        // But in cummulative sum the values are dependent to the next one which cause problems in parallel stream
        // because it processes elements in parallel and may not maintain the order of processing,
        // leading to incorrect results for cumulative sum.

         cumSum = numbers.stream().map(ParallelStream::cumulativeSum).toList(); // Cumulative sum of numbers using sequential stream
        System.out.println("Cumulative sum using sequential stream : " + cumSum); // we got the expected output because sequential stream processes elements in order, ensuring that the cumulative sum is calculated correctly



        // Parallel streams are a type of stream that can process elements in parallel,
        // utilizing multiple CPU cores to improve performance for large data sets.
        // They are created using the parallelStream() method on a collection or by calling the parallel() method on an existing stream.

         long count = java.util.stream.IntStream.range(1, 1000000) // Create a stream of integers from 1 to 999999
                .parallel() // Convert the stream to a parallel stream
                .filter(x -> x % 2 == 0) // Filter out odd numbers, keeping only even numbers
                .count(); // Count the number of even numbers in the stream
        System.out.println("\nCount of even numbers from 1 to 999999: " + count);
    }

    static int factorial(int n) {
        return n == 1 ? 1 : n * factorial(n - 1);
    }

    static int cumulativeSum(int n) {
        return n == 1 ? 1 : n + cumulativeSum(n - 1);
    }
}
