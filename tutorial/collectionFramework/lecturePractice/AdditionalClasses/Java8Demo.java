package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.AdditionalClasses;

import java.util.function.Predicate;

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
    static void main(String[] args) {

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