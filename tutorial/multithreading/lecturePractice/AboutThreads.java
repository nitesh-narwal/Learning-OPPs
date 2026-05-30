package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class AboutThreads extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running... " + Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000); // This will pause the execution of the current thread for 1 second
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i);
        }
    }

    static void main(String[] args) throws InterruptedException {
        AboutThreads about = new AboutThreads();
        about.start();

        /** Now we are stoping the main method untill the run method is completed */
        about.join();
        System.out.println("This will print after the run method is completed...");
    }
}
