package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class PrimitiveStream {
    static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // When we want to work with primitive data types,
        // we can use specialized streams like IntStream, LongStream, and DoubleStream.
        // These streams are specialized versions of the Stream class for primitive data types.
        // They provide methods to perform primitive operations on the elements of the stream.
        IntStream streamOfNumbers = Arrays.stream(numbers);

        // Difference betrwwn range and rangeClosed are that rangeClosed includes the end value in the range. 1,2,3,4,5,6,7,8,9,10.
        // 10 is included in the range.
        System.out.println(IntStream.range(1, 10).boxed().collect(toList()));
        System.out.println(IntStream.rangeClosed(1, 10).boxed().collect(toList()));

        IntStream.of(1, 2, 3, 4, 5);

        DoubleStream doubles = new Random().doubles(5);
        // System.out.println("Sum :  " + doubles.sum());
        // System.out.println("Average : " + doubles.average());
        /*System.out.println("Max : " + doubles.max().getAsDouble());
        System.out.println("Min : " + doubles.min().getAsDouble());
        System.out.println("Count : " + doubles.count());
        System.out.println("Sum of squares : " + doubles.map(x -> x * x).sum());
        System.out.println("Standard deviation : " + doubles.map(x -> x * x).average().getAsDouble());
        doubles.summaryStatistics(); // SummaryStatistics class provides methods to calculate summary statistics of a stream of double values.
        */
        System.out.println(doubles.boxed().toList());

        //doubles.mapToInt(x -> (int) x); // maptoint is used to convert a stream of double values to a stream of integers.

        IntStream intStream = new Random().ints(5);
        System.out.println(intStream.boxed().toList());

    }
}
