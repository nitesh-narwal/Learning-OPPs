package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class StreamsIntermediateOps {
    static void main(String[] args) {
        // Intermediate Operations transform a stream into another stream
        // They are lazy, meaning they are not executed until a terminal operation is invoked on the stream

        // 1. filter(Predicate<T> predicate): Returns a stream consisting of the elements of this stream that match the given predicate.
        List<String> names = Arrays.asList("John", "Jane", "Bob", "Alice", "Jack", "Jill");
        long count = names.stream()  // It wouldn't do the filtering until the terminal operation is invoked
                .filter(x -> x.startsWith("A") && x.endsWith("e")) // Intermediate operation: filter names starting with "A" and ending with "e"
                .count();// Terminal operation: count the number of names that match the filter criteria
        System.out.println("Count of names starting with 'A' and ending with 'e': " + count);

        // 2. map(Function<T, R> mapper): Returns a stream consisting of the results of applying the given function to the elements of this stream.
        List<String> list = names.stream().map(x -> x.toUpperCase()).toList();// Intermediate operation: convert each name to uppercase and collect the results into a list
        System.out.println("Names in uppercase: " + list);   // One way to convert a stream to a list is to use the toList() method.

        List<String> list2 = names.stream().map(String::toUpperCase).toList(); // Another way to convert a stream to a list is to use method reference.
        System.out.println("Names in uppercase using Method Ref. : " + list2);

        // 3. sorted(Comparator<T> comparator): Returns a stream consisting of the elements of this stream sorted according to the provided Comparator.
        //                                      or in the natural order of the elements.
        Stream<String> sorted = names.stream().sorted();// Intermediate operation: sort the names in natural order and print them
        System.out.println("Names in sorted order: " + sorted.toList( ));

        // Sorted stream using comparator
        Stream<String> sortedUsingComparator = names.stream().sorted((x, y) -> x.length() - y.length());// Intermediate operation: sort the names based on their length
        System.out.println("Names sorted based on their length: " + sortedUsingComparator.toList());

        // 4. distinct(): Returns a stream consisting of the distinct elements (according to Object.equals(Object)) of this stream.
        Stream<String> distinct = names.stream().filter(x -> x.startsWith("J")).distinct();// Intermediate operation: remove duplicate names
        System.out.println("Distinct count : " + distinct.count()); // Terminal operation: count the number of distinct names that start with "J"

        // 5. limit(long maxSize): Returns a stream consisting of the first maxSize elements of this stream.
        long counted = Stream.iterate(1, n -> n + 1) // Infinite stream of natural numbers
                .limit(10).count();// Intermediate operation: limit the stream to the first 10 numbers
        System.out.println("Count : " + counted);

        // 6. skip(long n): Returns a stream consisting of the elements of this stream skipping the first n elements.
        Stream<String> skip = names.stream().skip(2);// Intermediate operation: skip the first 2 names
        System.out.println("Names after skipping the first 2: " + skip.toList());

        // OR
        System.out.println(Stream.iterate(1, n -> n + 1).skip(10).limit(100).count()); // Skip the first 10 natural numbers and print the next 90 numbers

        // 7. peek(Consumer<T> action): Performs the given action for each element of this stream as long as the stream has not been terminated.
        Stream.iterate(1, n -> n + 1).skip(10).limit(100).peek(System.out::println).count(); // Intermediate operation: print each number to the console and count the numbers
        Stream<String> peek = names.stream().peek(System.out::println);// Intermediate operation: print each name to the console
        System.out.println("count the no. of names : " + peek.count()); // Terminal operation: count the number of names printed to the console

        // 8. toArray(IntFunction<A[]> generator): Returns an array containing the elements of this stream.
        Object[] array1 = Stream.of(1, 2, 3, 4, 5).toArray();// Intermediate operation: convert the stream to an array of Objects
        System.out.println("Array of Objects: " + Arrays.toString(array1));

        String[] array = names.stream().toArray(String[]::new); // Intermediate operation: convert the stream to an array
        System.out.println("Names as an array: " + Arrays.toString(array));

        // 9. min/max(Comparator<T> comparator): Returns an Optional<T> describing the minimum element of this stream,
        //                                     or an empty Optional if this stream is empty.
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Min value: " + stream.min(Comparator.naturalOrder()).get()); // Intermediate operation: find the minimum value in the stream and print it
        System.out.println("Max value: " + stream1.max(Comparator.naturalOrder()).get()); // Intermediate operation: find the maximum value in the stream and print it

        // 10. flatMap(Function<T, Stream<R>> mapper): Returns a stream consisting of the results of replacing
        //                                           each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.
            /** Handle stream of collections, lists, or arrays where each element is itself is a collectiom
             *  Flatten nested structures (e.g., lists within lists) so that they can be processed as a single sequence of collections.
             *  Transform and flatten elements at the same time.
             *  Example: Stream<List<String>> stream = Stream.of(Arrays.asList("a", "b"), Arrays.asList("c", "d"));*/
            List<List<String>> listOfLists = Arrays.asList(
                    Arrays.asList("apple", "banana"),
                    Arrays.asList("carrot", "donut"),
                    Arrays.asList("egg", "flour"),
                    Arrays.asList("grape", "honey")
            );
        System.out.println(listOfLists.get(1).get(1));
        // Intermediate operation: convert each element of the list to uppercase and concatenate them
        Stream<String> stringStream = listOfLists.stream().flatMap(x -> x.stream().map(String::toUpperCase));
        System.out.println("Flattened stream: " + stringStream.toList());

        //Example 2:
        List<String> sentence = Arrays.asList(
                "Hello World",
                "The Java Stream API is powerful",
                "FlatMap is useful for handling nested structures",
                "It can be used to flatten a list of lists into a single stream"
        );

        System.out.println("Converting a sentence into a single List using flatMap : "
                + sentence.stream()
                .flatMap(x -> Arrays.stream(x.split(" ")))
                .map(String::toUpperCase).toList()); // Intermediate operation: split each sentence into words and flatten them into a single stream

        // Intermediate operation: convert each name to uppercase and concatenate them
        Stream<String> stream2 = names.stream().flatMap(x -> Stream.of(x, x.toUpperCase()));
        System.out.println("Names in uppercase and concatenated: " + stream2.toList());

        // Example :
        // Stream can't be reused after terminal operation is invoked.
        // So, if we want to use the same stream for another operation, we need to create a new stream.
        // otherwise, we will get an IllegalStateException: stream has already been operated upon or closed.
        // For example, if we want to print the names in uppercase and then sort them, we can use the following code:
        Stream<String> stream3 = names.stream().map(String::toUpperCase).sorted();
        System.out.println("Names in uppercase and sorted: " + stream3.toList());

        // 11. forEachOrdered(Consumer<? super T> action): Performs the given action for each element of this stream,
        //                                             in the order they are returned by the stream.
        //                                             The action is performed after the stream is terminated,
        //                                             if it is a terminal operation.
        // Use when we are using parallelstream and want to print the elements in the order they are processed.

        List<Integer> numberOrdered = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("\nUsing forEach to print the numbers in parallerl stream in the order they are processed: ");
        numberOrdered.parallelStream().forEach(System.out::println);

        System.out.println("\nUsing forEachOrdered to print the numbers in parallerl stream in the order they are processed: ");
        numberOrdered.parallelStream().forEachOrdered(System.out::println);

            Stream<String> stream4 = names.parallelStream().map(String::toUpperCase).sorted();
            stream4.forEachOrdered(System.out::println);

    }
}

/*
    Intermediate Operations
     1. filter(Predicate<T> predicate): Returns a stream consisting of the elements of this stream that match the given predicate.

     2. map(Function<T, R> mapper): Returns a stream consisting of the results of applying the given function to the elements of this stream.

     3. flatMap(Function<T, Stream<R>> mapper): Returns a stream consisting of the results of replacing
        each element of this stream with the contents of a mapped stream produced by applying the provided mapping function to each element.

     4. distinct(): Returns a stream consisting of the distinct elements (according to Object.equals(Object)) of this stream.

     5. sorted(): Returns a stream consisting of the elements of this stream sorted according to natural order.

     6. sorted(Comparator<T> comparator): Returns a stream consisting of the elements of this stream sorted according to the provided Comparator.

     7. limit(long maxSize): Returns a stream consisting of the first maxSize elements of this stream.

     8. skip(long n): Returns a stream consisting of the elements of this stream skipping the first n elements.

     9. peek(Consumer<T> action): Performs the given action for each element of this stream as long as the stream has not been terminated.

     10. forEach(Consumer<T> action): Performs the given action for each element of this stream.

     11. reduce(T identity, BinaryOperator<T> accumulator): Returns an OptionalInt describing the result of applying
        an accumulation function to the elements of this stream,
        or an empty OptionalInt if this stream is empty.
*/