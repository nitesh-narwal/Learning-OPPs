package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;


import java.util.concurrent.*;

public class ExampleOfScheduler {
    static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<String> future = executor.submit(new DependentServices());
        Future<String> future1 = executor.submit(new DependentServices());
        Future<String> future2 = executor.submit(new DependentServices());

//        Thread.sleep(5000);
        /** We have to use Variable.get() method because without it main method execute
         * without checking that all the dependentServices are started or completed the task
         * or not.*/
//        future.get();
//        future1.get();
//        future2.get();

        /** But the problem is we can't use .get() method everytime to make the main function
         * method wait, in case of too many independent services...
         * we can also use .invokeAll() funtion but why we have to do this much work.
         *
         * We can simply use CountDownLatch() function.
         * */

        System.out.println("All dependent services executed. Now executing main task: " + Thread.currentThread().getName());
        executor.shutdown();


        // Continue with CountDownLatchExample.java file

    }

    public static class DependentServices implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("Executing dependent service: " + Thread.currentThread().getName());
            Thread.sleep(2000); // Simulate time-consuming task
            return null;
        }
    }

}
