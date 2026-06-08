package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

import static org.testcontainers.shaded.com.trilead.ssh2.log.Logger.logger;

public class Java8Demo {
    static void main(String[] args) {
        // Streams

        // Java 8 ---> minimal code, functional programming
        // Java 8 ---> lambda expressions, Streams, Date & Time API

        // lambda expressions
        // lambda expressions are anonymous functions( no name, no return type, no access modifier)
        // lambda expressions are used to implement functional interfaces( interfaces with only one abstract method)
        // lambda expressions are used to write concise code


        // Here we have created a task class for it
        Thread t = new Thread(new Task());
        t.start();

        // Doing the same thing using lambda expression
        Thread t2 = new Thread(() -> System.out.println("Task2 is running..."));
        t2.start();

        // We can create a functional interface and use it with lambda expressions
        Thread t3 = new Thread(new SumOperation(20, 40));
        t3.start();

        // Normal Object creation
        SumOp sumOp = new SumOp();
        int result = sumOp.execute(10, 20);
        System.out.println("Result from sumOp obj. creation : " + result);

        // Lambda expression using functional interface directly
        MathOperation sum = (a, b) -> a + b;
        MathOperation sub = (a, b) -> a - b;
        MathOperation mul = (a, b) -> a * b;
        int result2 = sum.execute(10, 20);
        System.out.println("Result from lambda expression : " + result2);
        int result3 = sub.execute(10, 20);
        System.out.println("Result from lambda expression : " + result3);
        int result4 = mul.execute(10, 20);
        System.out.println("Result from lambda expression : " + result4);
    }
}

// Learning about Predicate a functional interface
class PredicateDemo{
    static void main(String[] args) {
        // Predicate is a functional interface( Boolean valued function)
        Predicate<Integer> isEven = (n) -> n % 2 == 0;
        System.out.println("Is 10 even? : " + isEven.test(10));

        // we can even check the string
        Predicate<String> isWordStartWithA = s -> s.startsWith("A");
        System.out.println("Does the word start with A? : " + isWordStartWithA.test("Apple"));

        // We can also check that the string stats with and ends with a specidic character
        Predicate<String> isWordStartWithAAndEndWithZ = s -> s.startsWith("A") && s.endsWith("Z");
        System.out.println("Does the word start with A and ends with Z? : " + isWordStartWithAAndEndWithZ.test("Apple"));

        // We can combine predicates using and, or, negate methods
        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
        System.out.println("Is 10 even and positive? : " + isEvenAndPositive.test(10));
    }
}

class FunctionDemo{
    // Function ---> Works for you
    static void main(String[] args) {
        // Function is a functional interface( takes one argument and returns a result)
        // Function<T, R> where T is the type of the input and R is the type of the output
        Function<Integer, Integer> doubleIt = x -> x * 2;
        System.out.println("Double of 5 : " + doubleIt.apply(5));
        Function<Integer, Integer> tripleIt = x -> x * 3;
        System.out.println("Triple of 5 : " + tripleIt.apply(5));
        // We can also combine functions using compose method
        // Working: doubleIt.compose(tripleIt).apply(5)  --> what it's mean is that we first triple the number and then double it opposite to andThen()
        Function<Integer, Integer> tripleThenDouble = tripleIt.compose(doubleIt);
        System.out.println("Triple then double of 5 : " + tripleThenDouble.apply(5));
        // We can also combine functions using andThen method
        Function<Integer, Integer> doubleThenTriple = doubleIt.andThen(tripleIt);
        System.out.println("Double then triple of 5 : " + doubleThenTriple.apply(5));

        // Function.identity() ---> returns the same value that is passed to it as an argument
        // Which means that if we pass 5 to identity() it will return 5
        // we use this in case we want to keep the same value as it is
        Function<Integer, Integer> identity = Function.identity();
        System.out.println("Identity of 5 : " + identity.apply(5));

        Function<Integer, Integer> square = n -> n * n;
        System.out.println("\nSquare of 5 : " + square.apply(5));

        // We can also use Function to convert one type to another
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("Length of the string 'Hello' : " + stringLength.apply("Hello"));
    }
}

class SupplierDemo{
    static void main(String[] args) {
        // Supplier is a functional interface( takes no argument and returns a result)
        // Supplier<T> where T is the type of the output
        Supplier<String> giveHelloWorld = () -> "Hello, World!";
        System.out.println(giveHelloWorld.get());

        Supplier<Double> randomValue = () -> Math.random();
        System.out.println("Random value : " + randomValue.get());

        // We can also use Supplier to generate a sequence of values
        Supplier<Integer> randomInt = () -> (int) (Math.random() * 100);
        System.out.println("Random integer : " + randomInt.get());
    }
}


class ConsumerDemo{
    // Consumer is a functional interface( takes one argument and does not return anything)
    static void main(String[] args) {
        // Consumer<T> where T is the type of the input
        Consumer<String> print = s -> System.out.println(s);
        print.accept("Hello, World!");

        // We can also combine consumers using andThen method
        Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());
        Consumer<String> printLowerCase = s -> System.out.println(s.toLowerCase());
        Consumer<String> printBoth = printUpperCase.andThen(printLowerCase);
        //Their is no return type in consumer so they just sequentially print
        // and that's how things goes to print " accept(t); after.accept(t); " 1st and 2nd
        printBoth.accept("SuperMan");

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Consumer<List<Integer>> printList = x -> {
            for (Integer n : x) {
                System.out.print(n + " ");
            }
        };
        printList.accept(numbers);
    }
}

class CombinedExample{
    static void main(String[] args) {
        // Let's say we want to generate a random number and then check if it's even or not
        Supplier<Integer> randomInt = () -> (int) (Math.random() * 100);
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Consumer<Integer> printResult = n -> System.out.println("Generated number: " + n + " is even? : " + isEven.test(n));

        // Now we can combine these functional interfaces to achieve our goal
        int number = randomInt.get();
        printResult.accept(number);

        // Combined Example
        Predicate<Integer> predicate= n -> n % 2 == 0;
        Function<Integer, Integer> function = n -> n * n;
        Consumer<Integer> consumer = n -> System.out.println("The random Square no. we get is : " + n);
        Supplier<Integer> supplier = () -> (int) (Math.random()*100); // This will generate a random number between 10 and 100
        int num = supplier.get();  // This will get the random number generated by the supplier,
        // but if i use supplier.get() it will generate a new random number every time
        System.out.println("Generated random number : " + num);
        if(predicate.test(num)){
            consumer.accept(function.apply(num));
        } else {
            System.out.println("The generated number is not even, so we cannot calculate its square.");
        }
    }
}

class Taking2Components{
    static void main(String[] args) {
        // BiPredicate, BiFunction, BiConsumer, BiSupplier
        // BiPredicate<T, U> where T and U are the types of the input
        BiPredicate<Integer, Integer> isSumEven = (a, b) -> (a + b) % 2 == 0;
        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;
        BiConsumer<Integer, Integer> printSum = (a, b) -> System.out.println("Sum of " + a + " and " + b + " is : " + sum.apply(a, b));
        printSum.accept(10, 20);
        System.out.println("Is 30 even? : " + isSumEven.test(10, 20));

        if(isSumEven.test(10, 20)){
            printSum.accept(10, 20);
        } else {
            System.out.println("The sum is not even, so we cannot print it.");
        }

        // we also have BinaryOperator<T> which is a special case of BiFunction where the input and output types are the same
        // UnaryOperator<T> ---> Unary operator takes one argument and returns a result
        //  BinaryOperator<T> ---> Binary operator takes two arguments and returns a result
        // Now we don't need to write <Integer, Integer> in the BiFunction<Integer, Integer, Integer> again and again
        // we can just write <Integer> and it will automatically infer the type of the input and output
        // but only if the input and output types are the same
        // UnaryOperator<T> ---> Unary operator takes one argument and returns a result
        //  BinaryOperator<T> ---> Binary operator takes two arguments and returns a result

        //BiFunction<Integer, Integer, Integer> sum2 = (a, b) -> a + b;
        BinaryOperator<Integer> sum2 = (a, b) -> a + b;

        // Function<Integer, Integer> square = n -> n * n;
        UnaryOperator<Integer> square = n -> n * n;
        System.out.println("Sum of 10 and 20 using BinaryOperator : " + sum2.apply(10, 20));
        System.out.println("Square of 5 using UnaryOperator : " + square.apply(5));
    }
}

// Method Reference
class MethodReferenceDemo{
    static void main(String[] args) {

        // Method reference ---> use method without invoking & in place of lambda expression
        List<String> names = Arrays.asList("John", "Jane", "Bob", "Alice");
        names.forEach(name -> System.out.println(name)); // using lambda expression but method reference is better
        names.forEach(System.out::println); // using method reference

        // Constructor reference ---> use constructor without invoking & in place of lambda expression
        List<String> phones = Arrays.asList("iPhone", "Samsung", "OnePlus", "Google Pixel");
        phones.stream().map(x -> new MobilePhone(x)).collect(Collectors.toList()); // using lambda expression
        phones.stream().map(MobilePhone::new).collect(Collectors.toList()); // using constructor reference
        // Here we have used constructor reference to create a new MobilePhone object for each element in the list
        // and then we have used the collect() method to convert the list to a list of MobilePhone objects.
        // But if we want to print the names of the phones we can use the map() method to get the names of the phones and then print them
        System.out.println("Phones : " + phones);

    }
}

class MobilePhone{
    String names;

    public MobilePhone(String names) {
        this.names = names;
    }
}



class Task implements Runnable{
    @Override
    public void run() {
        System.out.println("Task1 is running...");
    }
}

// Functional interface this is a @FunctionalInterface having only one abstract method,
// for declaration we can use @FunctionalInterface annotation here
@FunctionalInterface
interface MathOperation {
    int execute(int a, int b);
}

// If we use this interface, to create a new class that uses this interface,
// we have to implement the execute method and write the logic for it.
// But with lambda expressions,
// we can directly write the logic without creating a new class.
class SumOperation implements MathOperation, Runnable {
    int a, b;
    public SumOperation(int a, int b){
        // This constructor can be used to initialize any variables if needed
        this.a = a;
        this.b = b;
    }

    @Override
    public int execute(int a, int b) {
        return a + b;
    }

    @Override
    public void run() {
        int result = execute(a, b);
        System.out.println("Sum from Thask 3 : " + result);
    }
}

class SumOp implements MathOperation {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}