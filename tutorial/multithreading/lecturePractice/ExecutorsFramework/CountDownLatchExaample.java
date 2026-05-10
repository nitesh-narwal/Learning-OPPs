package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.concurrent.*;

public class CountDownLatchExaample {
    static void main(String[] args) throws InterruptedException {

        int numberOfServices = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfServices);

        //When we want to make 1 or more services wait, then we use this CountDownLatch method...
        // Why we use CountDownLatch method?
        // Because it allows one or more threads to wait until a set of operations being performed in other threads completes.
        CountDownLatch latch = new CountDownLatch(numberOfServices);
        executorService.submit(new DependentServices(latch));
        executorService.submit(new DependentServices(latch));
        executorService.submit(new DependentServices(latch));
        latch.await();

        System.out.println("All dependent services executed. Now executing main task: " + Thread.currentThread().getName());

        System.out.println("<---------------------NEW DEPENDENT SERVICES--------------------->");

        // if we want to manually count down the latch,
        // then we can use latch.countDown() method in the call() method of DependentServices class,
        // and we have to make sure that we are using it in finally block because if any exception occurs
        // then also we have to count down the latch otherwise main thread will wait indefinitely.

        for(int i = 0; i < numberOfServices; i++){
            new Thread(new NewDependentServices(latch)).start();
        }

        latch. await(5, TimeUnit.SECONDS);
        System.out.println("All New dependent services executed. Now executing main task: " + Thread.currentThread().getName());
        executorService.shutdown();  // this is not needed for the newDependentServices,
        // because we are manually creating threads for them,
        // but for the dependentServices we are using executorService,
        // so we have to shutdown it.


        /** CountDownLatch is not reusable to learn about it learn in CyclicBarrierExample.java
         * CountDownLatch is not reusable means once it get's to zero then we can't use it again...
         * */
    }

    public static class DependentServices implements Callable<String> {

        private final CountDownLatch latch;

        public DependentServices(CountDownLatch latch){
            this.latch = latch;
        }

        @Override
        public String call() throws Exception {
            try {
                System.out.println("Executing independent service: " + Thread.currentThread().getName());
                Thread.sleep(2000); // Simulate time-consuming task
            }finally {
                latch.countDown(); // Wait for the latch to count down to zero before proceeding
            }
            return "ok";
        }
    }

    public static class NewDependentServices implements Runnable{

        private final CountDownLatch latch;

        public NewDependentServices(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(6000);// Simulate time-consuming task
                System.out.println("Executing independent service: " + Thread.currentThread().getName());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                latch.countDown(); // Wait for the latch to count down to zero before proceeding
            }
        }
    }
}
