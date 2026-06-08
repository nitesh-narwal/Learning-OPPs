package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LazyEvaluationDemo {
    static void main(String[] args) {
        // Lazy evaluation is a programming technique that delays the evaluation of an expression until its value is needed.
        // In Java, lazy evaluation is often used in the context of streams and functional programming.

        // Example of lazy evaluation with streams
        List<String> names = Arrays.asList("Anna", "Bob", "Charlie", "David", "Eve");
        // Wouldn't invoke the filtering operation because it is not invoked until the terminal operation is invoked
        Stream<String> stringStream = names.stream()
                .filter(name -> {
                    System.out.println("Filtering: " + name);
                    return name.length() > 3;
                });
        // Now see in the terminal, you will see that the above code would not print anything until we invoke a terminal operation on the stream, such as collect() or count().
        // So the "Before terminal operation" would be printed first, and then when the terminal operation is invoked, the filtering would happen.
        System.out.println("Before terminal operation:");

        List<String> result = stringStream.collect(Collectors.toList());
        System.out.println("After terminal operation: " + result);
    }
}
