package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorSer {

    static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Executors is a class in which their are many utilities methoda...
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for(int i = 1; i< 10; i++){
            int finalI = i;
//            threads[i - 1] = new Thread(
//                    () -> {
//                        long result = factorial(finalI);
//                        System.out.println(finalI + ": " + result);
//                    });
//            threads[i - 1].start();

            executor.submit( // through submit we use can use fixed no. threads from the pool
                    // and we don't have to create resources manually.
                    () -> {
                        long result = factorial(finalI);
                        System.out.println(finalI + ": " + result);
                    }
            );
        }
        executor.shutdown(); // it shutdown the program otherwise the program still runs
        /** Now the above shutdown();  don't wait for the other threads to be executed first
         * for that we have to use await method that helps to make main thread to wait for a specific period
         * of time */

        try {
//            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            //  Now creating for unlimited wait...
            while(!executor.awaitTermination(1, TimeUnit.SECONDS)){
                System.out.println("Waiting....");
            }
        } catch (InterruptedException e) {
            System.out.println("Waiting....");
        }
        System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
    }

    public static long factorial(int num){
        long result = 1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for(int i =1; i < num; i++){
            result *= i;
        }
        return  result;
    }
}
