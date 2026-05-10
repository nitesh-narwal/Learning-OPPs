package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.ExecutorsFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThread {
    static void main(String[] args) {

        /** In this Thread Pool we don't specify how many threads are required
         * it's creates threads as required or the  no. of tasks came
         * and it adjusts the thread pool dynamically
         *
         * use when we get variable tasks came
         * and the load should short lived*/
        ExecutorService executorService = Executors.newCachedThreadPool();
    }

}
