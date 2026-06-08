package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class StreamTerminalOps {
    static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 1. collect(Collector<? super T, A, R>): Returns a R value by accumulating elements in a collector.
        // Collectors.toList() : Returns a new List containing the elements of the stream.
        numbers.stream().skip(2).collect(Collectors.toList());
        // Here we can also write
        numbers.stream().skip(2).toList(); // do the same thing

        // 2. forEach(Consumer<? super T>): Performs the given action for each element of the stream.
        System.out.println("Using forEach to print all numbers:");
        numbers.stream().forEach(x -> System.out.print(", " + x));
        System.out.println("\nUsing method reference to print all numbers:");
        numbers.stream().skip(2).forEach(System.out::println);

        // 3. reduce(T identity, BinaryOperator<T>): Returns a single value of type T by applying an accumulator function to the elements of the stream.
        // Optional<Integer> optionalInteger = numbers.stream().reduce((x, y) -> x + y);
        Optional<Integer> optionalInteger = numbers.stream().reduce(Integer::sum);
        System.out.println("Sum of all numbers: " + optionalInteger.get());

        // 4. count(): Returns the number of elements in the stream.
        System.out.println("Count of numbers: " + numbers.stream().count());

        // BELOW ALL THE OPERATIONS ARE Short Circuit Operations (returns as soon as it finds a match)
        // means they stop processing the stream as soon as they find a match, rather than processing the entire stream.

        // 5. anyMatch(Predicate<? super T>): Returns true if any element of the stream matches the given predicate.
        System.out.println("Is there an even number in the list? : " + numbers.stream().anyMatch(x -> x % 2 == 0));

        // 6. allMatch(Predicate<? super T>): Returns true if all elements of the stream match the given predicate.
        System.out.println("Is all numbers even? : " + numbers.stream().allMatch(x -> x % 2 == 0));

        // 7. noneMatch(Predicate<? super T>): Returns true if none of the elements of the stream match the given predicate.
        System.out.println("Is there a number greater than 10 in the list? : " + numbers.stream().noneMatch(x -> x > 10));
        // means none of the elements are greater than 10

        // 8. findFirst(): Returns an Optional describing the first element of the stream, or an empty Optional if the stream is empty.
        Optional<Integer> firstEven = numbers.stream().filter(x -> x % 2 == 0).findFirst();
        System.out.println("First even number: " + firstEven.get());

        Integer integer = numbers.stream().findFirst().get();// will return 1, the first element of the stream
        System.out.println("First element of the stream: " + integer);

        // 9. findAny(): Returns an Optional describing some element of the stream, or an empty Optional if the stream is empty.
        Optional<Integer> anyNumber = numbers.stream().findAny();
        System.out.println("Any number: " + anyNumber.get());

        // Example
        List<String> names = Arrays.asList("Anna", "Bob", "Charlie", "David", "Eve");
        List<String> stringList = names.stream().filter(x -> x.length() > 3).toList();
        System.out.println("Names with length greater than 3: " + stringList);

        // Squaring and sorting numbers
        List<Integer> squaredSortedNumbers = Arrays.asList(5,9,3,2,7,1,6);
        List<Integer> result = squaredSortedNumbers.stream()
                .map(x -> x * x) // Square each number
                .sorted() // Sort the squared numbers
                .toList(); // Collect the results into a List
        System.out.println("Squared and sorted numbers: " + result);

        List<Integer> summing = Arrays.asList(1, 2, 3, 4, 5);
      //  summing.stream().reduce(Integer::sum).ifPresent(sum -> System.out.println("Sum of the list: " + sum)); // Optional.ifPresent() is used to handle the Optional value.
        System.out.println("Sum of the numbers : " + summing.stream().reduce(Integer::sum).get()); // Another way to get the sum of the list is to use Optional.get() method, but it will throw NoSuchElementException if the stream is empty.

        // Example 3:
        String sentence = "The quick brown fox jumps over the lazy dog";
        // char[] charArray = sentence.toCharArray();  ---> this will return a char array
        // But we hvae a chars() method that returns us streams of Integer that basically represents the unicode code points of the characters in the string.
        // So we can use the chars() method to get the unicode code points of the characters in the string.
        long count = sentence.chars().filter(x -> x == 'e').count();// This will count the number of times the character 'e' appears in the sentence.
        System.out.println("Number of times 'e' appears in the sentence: " + count);

        // Statefull & Stateless Operators
        // Statefull operations means they have to keep track of othere information while processing the stream.
        // Stateless operations are operations that only work on the element they are working on.
    }
}
