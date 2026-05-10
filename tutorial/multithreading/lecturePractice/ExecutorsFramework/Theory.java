package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.sql.SQLOutput;

public class Theory {
    /**
     *  -> Executors Framework:
     *  The executors framework was introduced in java 5 as part of
     *  the java.util.concurrent package to simplify the development
     *  of concurrent applications by abstracting many of the complexities
     *  involved in creating and managing threads.
     *
     * -> Problems cames before the Executors framework:
     * 1. Manual Thread Management
     * 2. Resource Management
     * 3. Scalability
     * 4. Thread Reuse
     * 5. Error Handling
     *
     * -> Their would be 3 more core of Execution Framework:
     * 1. Executor
     * 2. ExecutorService
     * 3. ScheduledExecutorService
     * */

    static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        /** Here multiple threads are doing work together so we are getting
         *  result quickly because of multi threading...
         *  But still here we are doing things manually
         *  to tackle that problem
         *  we use ExecutorService
         *  */
        // Creating an array of threads
        Thread[] threads = new Thread[14];

        for(int i= 1; i < 15 ; i++){
            int finalI = i;
                threads[i -1 ] = new Thread(
                    () -> {
                        long result = factorial(finalI);
                        System.out.println(result);
                    });
            threads[i -1].start();
        }
        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                thread.currentThread().interrupt();
            }
        }

        /** this is giving me the thread trigger time not the total execution time
         * -> Now to tackle this problem we have to create an array of threads and
         *    then we have to wait to execute all of them.
         * */
        System.out.println("Total time for execution: " + (System.currentTimeMillis() - startTime));
    }

    public static long factorial(int num){
        long result = 1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        for (int i = 1; i <= num; i++){
             result *= i;
        }
        return result;
    }
}
