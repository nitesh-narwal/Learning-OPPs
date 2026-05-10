package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class NewFeatures {

    static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(9);

        Future<Integer> result = executorService.submit(() -> 1 + 2);
        Integer i = null;
        try {
            i = result.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }catch (CancellationException e){
            System.out.println("Cancellation");
        }

        System.out.println("result: " + i);
       // executorService.shutdown();
       // System.out.println(executorService.isShutdown());
        // when i directly trying to check, is all the process are terminated it shows in false,
        // which means some processes are running in the background.
        // to check clearly we have to give it sometime
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("intrupted...");
        }catch (CancellationException e){
            System.out.println("cancelled");
        }
        // System.out.println(executorService.isTerminated());





        Callable<Integer> callable1 = () -> {
            Thread.sleep(1000);
            System.out.println("Task 1");
            return  1;
        };
        Callable<Integer> callable2 = () -> {
            Thread.sleep(1000);
            System.out.println("task 2");
            return 2;
        };
        Callable<Integer> callable3 = () -> {
            Thread.sleep(1000);
            System.out.println("Task 3");
            return 3;
        };

        List<Callable<Integer>> list = Arrays.asList(callable1, callable2, callable3);
        // invokeAll() takes the collection of tasks & execute All
        // It can take lists.
        /** we can also provide timer in this invokeAll(Collection task, Timeout, TimeUnits) method
         *  after that specific time it stops the execution*/
        List<Future<Integer>> futures = null;
        try {
            futures = executorService.invokeAll(list, 5000 , TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("Intrupted...");
        }catch (CancellationException e){
            System.out.println("task 1 cancelled");
        }
        // we can also print futures
        for(Future<Integer> f: futures){
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }catch (CancellationException e){
                System.out.println("task 2 cancelled");
            }

        }
//        executorService.shutdown();
//        System.out.println("Hello");

        /** It directly returns the result
         * and show us the result of that task
         * which completes first and the other tasks results are
         * canceled*/
        try {
            Integer i1 = executorService.invokeAny(list);
            System.out.println("invokeAny result: " + i);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


        Future<Integer> future3 = executorService.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 42;
        });

        try {
            // we have seen get() method with time....
            Integer j =  future3.get(1, TimeUnit.SECONDS);
            System.out.println(future3.isDone());
            System.out.println(j);

        } catch (InterruptedException  | TimeoutException  | ExecutionException e ) {
            System.out.println("task 3 cancelled");
        }
    }
}