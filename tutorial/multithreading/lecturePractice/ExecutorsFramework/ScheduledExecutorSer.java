package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorSer {
    static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
         // if i want to execute a task after some delay use schedul
        scheduler.schedule(() ->
                System.out.println("Task execution is delayed by 5 seconds"),
                5, TimeUnit.SECONDS);

        // if i want to execute a task on a Regular Interval...
        // without proper handling our daily scheduler wouldn't run in every 5 minit delay
        // Here the shutdown method wouldn't know that our schedulerAtFixedRate() method
        // have an initial delay of 5 seconds, it just stop the function

        /** In this method their would be a chance of overlapping
         * Suppose the program takes around 10 seconds to complete but this
         * scheduleAtFixedRate() method would run in every 5 seconds
         * without even considering that the program before it is completed or not
         * that's why overlapping happen*/
        scheduler.scheduleAtFixedRate(() ->
                        System.out.println("Task executed in every 5 seconds delay"),
                        5,
                        5,
                        TimeUnit.SECONDS
                );

        /**This method wouldn't allow overlap
         * because the next program will execute after it's completion and
         * additional 5 second of delay */
        scheduler.scheduleWithFixedDelay(
                () ->
                        System.out.println("Task executed in every 5 seconds delay"),
                5,
                5,
                TimeUnit.SECONDS
        );

        // so for that we can put the shutdown method inside a schedul
        // to run the shutdown after a fixed time
        scheduler.schedule(() -> {
                System.out.println("Initiating Shutdoen...");
                    scheduler.shutdown();
                },
                20,
                TimeUnit.SECONDS);



    }
}
