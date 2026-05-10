package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.concurrent.*;

public class CompletableFutureExample {
    static void main(String[] args) {

        // CompletableFuture we use when we need asynchronous programming and we want to chain multiple tasks together.
        // It allows us to write non-blocking code and handle results or exceptions in a more flexible way.
        // It's a demon thread by default so main thread will not wait for the task to complete
        // and it will execute separately and execute the task.
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->
        {
            // here i can do some long running task but they have to return a somethingh.
            try {
                Thread.sleep(5000); // Simulate a long-running task
                System.out.println("Task completed!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
        });
        /** CompletableFuture seperate the task between threads
         * if we don't make main thread to wait the main
         * thread will execute separately and execute the task.
         * */
        String state1 = null;
        String state2 = null;
        try {
            state1 = completableFuture.get();// This will block the main thread until the CompletableFuture is complete
            state2 = completableFuture.getNow("Not completed yet"); // This will return "Not completed yet" if the CompletableFuture is not complete,
            // otherwise it will return the result of the CompletableFuture

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(state1);
        System.out.println(state2);
        System.out.println("Main thread is done!");
    }
}

class CompletableFutureTask{
    static void main(String[] args) {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() ->
        {
            // here i can do some long running task but they have to return a somethingh.
            try {
                Thread.sleep(5000); // Simulate a long-running task
                System.out.println("Task1 completed!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() ->
        {
            try {
                Thread.sleep(3000); // Simulate a long-running task
                System.out.println("Task2 completed!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
        });

        /** it don't take the result of f1 and f2 but it will wait for both of them to complete.*/
        CompletableFuture<Void> j = CompletableFuture.allOf(f1, f2);
        j.join(); // This will block the main thread until both f1 and f2 are complete
        // j.get(); // This will block the main thread until both f1 and f2 are complete, but here have to use try catch block
        // for exception handling but join will not throw any exception it will just return null
        // if there is any exception in f1 or f2 and it will not block the main thread.
        System.out.println("Main thread is done!");
    }
}

class CompletableFutureTask2{
    static void main(String[] args) {

        Executor executors = Executors.newFixedThreadPool(3); // we can use these threads for the below code

        // we can also apply  .get in the start but it will block the main thread
        // until the CompletableFuture is complete and it will not execute the task in separate thread.
        CompletableFuture<String> completableFuture = null; // This will block the main thread until the CompletableFuture is complete
        completableFuture = CompletableFuture.supplyAsync(() ->
        {
            // here i can do some long running task but they have to return a somethingh.
            try {
                Thread.sleep(3000); // Simulate a long-running task
                System.out.println("Task completed!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
            // }).get();   // now we are using .thenApply()
        }).thenApply(x -> x + x); // .thenApply() will not block the main thread until the CompletableFuture is complete

        // and it will not execute the task in separate thread.
         // it will execute the task in main thread and it will block the main thread until the task is complete.
         // so it's not a good idea to use get() in the start of the CompletableFuture.
         // because it will block the main thread and it will not execute the task in separate thread.
         // so it's better to use get() after the CompletableFuture is complete or use join() instead of get().

         System.out.println(completableFuture);
         System.out.println("Main thread is done!");

        System.out.println("");
        System.out.println("<----------LEARNING ABOUT ORTIMEOUT----------->");
        System.out.println("");

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() ->
        {
            // here i can do some long running task but they have to return a somethingh.
            try {
                Thread.sleep(5000); // Simulate a long-running task
                System.out.println("Task completed for orTimeout!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ok";
            // Now we are using the the thread pool threads to execute the task.
        }, executors ).orTimeout(3, TimeUnit.SECONDS).exceptionally(s -> "Timeout Occurred: "); // This will throw a TimeoutException if the CompletableFuture is not complete within 3 seconds

         try {
             System.out.println(f1.get()); // This will block the main thread until the CompletableFuture is complete
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         } catch (ExecutionException e) {
             throw new RuntimeException(e);
         }

        System.out.println("Main thread after timeout is done!");
    }
}
