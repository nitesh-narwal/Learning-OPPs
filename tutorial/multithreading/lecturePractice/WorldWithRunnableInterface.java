package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class WorldWithRunnableInterface implements Runnable{
    @Override
    public void run() {
        for(;;){
            System.out.println(Thread.currentThread().getName());
        }
    }
}
