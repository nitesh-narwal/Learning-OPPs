package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.concurrent.*;

public class FutureGeneric {
    static void main(String[] args) throws ExecutionException, InterruptedException {
        // Entry point of program
        // throws:
        // - ExecutionException → if task fails
        // - InterruptedException → if thread is interrupted
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Creating a thread pool with ONLY ONE thread
        // Means:
        // - Tasks will run one-by-one (not parallel)
        // - But still runs in background thread

        // Think:
        // You hired 1 worker → he does tasks one at a time




//        Runnable runnable = () -> "Hello"; this method shows us error here
//                                           because it returns void but we are expecting String
//                                           for that we use Callable....
// ❌ ERROR because:
// Runnable DOES NOT return anything (void)

// Runnable = () -> { do work }
// NO return allowed

        Callable<String> callable =  () -> "Hello";
        /** Here we use Future because it helps us to extract the value which we can use anywhere... */
        Future<?> future = executor.submit(callable);

        // Here the submit(Runnable Task, T result);
        // As runnable don't return anythingh but by using it
        // we can return a message or result
        Future<String> future2 = executor.submit(() -> System.out.println("Hello... "), "Success");

//      Future<?> future = executor.submit(System.out.println("Hello... "));
        // This submit method automatically calls the Runnable Interface not the caller
        // because we are not returning the things.
        // but their is no meaning of the "future.get()" method  because it's not returning anything.
        // Runnable never return things, for that we can use "submit & Future<>" to return stuff
        System.out.println(future.get());
        executor.shutdown();
    }
}
