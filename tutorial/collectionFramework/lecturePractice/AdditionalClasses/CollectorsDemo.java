package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorsDemo {
    static void main(String[] args) {
        // Collectors is a utility class
        // that provides set of methods to create common collectors

        // Example 1: Collecting a list of names that start with 'A'
        List<String> names = List.of("Anna", "Bob", "Charlie", "David", "Eve");
        List<String> list = names.stream()
                .filter(x -> x.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println(list);

        // Example 2: Collecting a set of unique numbers
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 2, 3);
        var set = numbers.stream()
                .collect(Collectors.toSet());
        System.out.println(set);
        
        //  Collecting to a specific Collection
        ArrayDeque<String> namesDeque = names.stream().collect(Collectors.toCollection(() -> new ArrayDeque<>()));
        System.out.println(namesDeque);
        // We can also use method reference instead of lambda expression
        ArrayDeque<String> namesDeque1 = names.stream().collect(Collectors.toCollection(ArrayDeque::new));
        System.out.println(namesDeque1);
        // So the above code is equivalent to: ArrayDeque<String> namesDeque = new ArrayDeque<>(names);
        // means it convert the list to a deque.


        // Example : Collecting a map of numbers to their squares
        List<Integer> numbers2 = List.of(1, 2, 3, 4, 5);
        var map = numbers2.stream()
                .collect(Collectors.toMap(x -> x, x -> x * x));
        System.out.println(map);


        // Example 4: Joining strings
        // Concatenating stream elements into a single string
        List<String> words = List.of("Hello", "World", "Java", "Streams");
        String concaternate = words.stream().map(String::toUpperCase).collect(Collectors.joining(", "));
        System.out.println(concaternate);

        String result = words.stream()
                .collect(Collectors.joining(", "));
        System.out.println(result);


        // Example 5: Summerizing Data
        // Generates Statistics summery like average, min, max, count, sum, etc.
        List<Integer> numbers1 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("The numbers are: " + numbers1);
        IntSummaryStatistics summaryStatistics = numbers1.stream().collect(Collectors.summarizingInt(x -> x));
        System.out.println("Average: " + summaryStatistics.getAverage());
        System.out.println("Count: " + summaryStatistics.getCount());
        System.out.println("Max: " + summaryStatistics.getMax());
        System.out.println("Min: " + summaryStatistics.getMin());
        System.out.println("Sum: " + summaryStatistics.getSum());

        var stats = numbers1.stream()
                .collect(Collectors.summarizingInt(x -> x));
        System.out.println(stats);

        System.out.println("\nUsing collect method Individually:");
        // Calculating average of a list of numbers
        double average = numbers1.stream().collect(Collectors.averagingInt(x -> x));
        System.out.println("Average: " + average);

        // Counting the number of elements in a stream
        long count = numbers1.stream().collect(Collectors.counting());
        System.out.println("Count: " + count);

        // Example 6: Grouping Data
        // Grouping elements into a map based on a key function
        List<String> words1 = List.of("apple", "banana", "orange", "grape", "apple", "pear", "grape");
        // what we did here is we grouped the words based on their length in map
        Map<Integer, List<String>> collect = words1.stream().collect(Collectors.groupingBy(x -> x.length()));
        System.out.println("The grouped words are: " + collect);

        // what we are doing here is we grouped the words based on their length
        // and then joined them into a string using comma and space.
        Map<Integer, String> lengthGroupedWords = words1.stream().collect(Collectors.groupingBy(String::length, Collectors.joining(", ")));
        System.out.println("\nThe grouped and joined words are: " + lengthGroupedWords);
        // what we are doing here is we grouped the words based on their length
        // and then counted the number of words in each group.
        Map<Integer, Long> wordLengthFrequency = words1.stream().collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println("\nThe frequency of words based on their length is: " + wordLengthFrequency);
        // what we are doing here is we grouped the words based on their length
        // and then counted the number of words in each group and then stored them in a TreeMap.
        TreeMap<Integer, Long> integerLongTreeMap = words1.stream().collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));
        System.out.println("\nThe frequency of words based on their length in a TreeMap is: " + integerLongTreeMap);


        System.out.println("\nUsing collect method Individually:");
        //Example:
        var map1 = words1.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(map1);

         var map2 = words1.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println(map2);

        System.out.println("\n<----Example 7: Partitioning Data---->");
        // Example 7: Partitioning Data
        // Partitioning a stream into two groups (true and false) based on a predicate
        List<String> words2 = List.of("apple", "banana", "orange", "grape", "apple", "pear", "grape");
        Map<Boolean, List<String>> partitioned = words2.stream().collect(Collectors.partitioningBy(x -> x.length() > 5));
        System.out.println("The partitioned words are: " + partitioned);

        System.out.println("\nUsing collect method Individually:");
        // Example 10: Mapping & Collecting
        // Mapping a stream to a different type and collecting the results into a collection
        List<String> capitalizedWords = words2.stream().collect(Collectors.mapping(String::toUpperCase, Collectors.toList()));
        System.out.println("The capitalized words are: " + capitalizedWords);
    }
}

class ExamplesOfTheory{
    static void main(String[] args) {
        // Example 1: Collecting names by length
        List<String> names = List.of("John", "Jane", "Bob", "Alice", "Dave", "Eve");
        Map<Integer, List<String>> integerListMap = names.stream().collect(Collectors.groupingBy(String::length));
        System.out.println("Names of length 5: " + integerListMap.get(5));

        // Example 2:
        String sentence = "This is a sample sentence for CollectorsDemo class." +
                " It contains multiple words. The sentence is very long." +
                " It is very important to understand the concept of Collectors.";
        Map<Object, Long> wordToOccurrencesMap = Arrays.stream(sentence.split(" ")).collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        System.out.println("The occurrences of the word 'sentence' are: " + wordToOccurrencesMap.get("sentence"));
        System.out.println("The Grouping are : " + wordToOccurrencesMap);

        // Example 3:
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<Boolean, List<Integer>> evenOddPartition = numbers.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0));
        System.out.println("Numbers partition : " + evenOddPartition);
        System.out.println("Even Numbers : " + evenOddPartition.get(true));
        System.out.println("Odd Numbers : " + evenOddPartition.get(false));

        // Example 4:
        Map<String, Integer> items = new HashMap<>();
        items.put("apple", 10);
        items.put("banana", 5);
        items.put("orange", 15);
        items.put("grape", 20);
        items.put("pear", 12);
        items.put("strawberry", 8);
        System.out.println(items.values().stream().reduce(Integer::sum));
        System.out.println("The sum of values : " + items.values().stream().collect(Collectors.summingInt(x -> x)));

        // Example 5:
        // ToMap() :  Collects a Map<K, V> from the elements of the stream.
        // The key and value are extracted from the stream using the provided keyMapper and valueMapper functions.
        // The resulting Map is created using the provided MapSupplier.
        // If the stream is empty, the MapSupplier is used to create an empty Map.
        // If the stream contains duplicate keys, the last value is used.

        // Basically what it does is it takes the elements of the stream and converts them into a Map<K, V> using the provided keyMapper and valueMapper functions.
        List<String> words = Arrays.asList("apple", "banana", "orange", "grape", "pear", "strawberry");
        Map<String, Integer> fruit = words.stream().collect(Collectors.toMap(x -> x.toUpperCase(), x -> x.length()));
        System.out.println("The fruit map is: " + fruit);

        // Example 6: Counting the frequency of each word in a sentence
        // To count the frequency of each word in a sentence, we can use the following code:
        // Basically what it does is it takes the elements of the stream and counts the number of occurrences of each word.
        // The resulting Map is created using the provided MapSupplier.
        List<String> fruits = Arrays.asList("apple", "banana", "orange", "grape", "pear", "strawberry", "apple", "orange");
        fruits.stream().collect(Collectors.toMap(k -> k, v -> 1, (x,y) -> x + y));
        // We used the toMap() method to count the frequency of each word in the list of fruits.
        // The key is the word itself and the value is the count of occurrences.
        // The third parameter is a merge function that is used to handle duplicate keys.
        // In this case, we simply add the counts together.
        System.out.println("The frequency of each fruit is: " + fruits.stream().collect(Collectors.toMap(k -> k, v -> 1, (x,y) -> x + y)));



    }
}