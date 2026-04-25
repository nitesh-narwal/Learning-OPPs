package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class Interrupted extends Thread{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Thread is running..." + Thread.currentThread().getState());
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted while sleeping." + e);
        }
    }

    static void main() {
        Interrupted i = new Interrupted();
        i.start();
        i.interrupt();
    }
}
