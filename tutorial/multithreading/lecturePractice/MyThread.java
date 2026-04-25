package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class MyThread extends Thread{
    @Override
    public void run() {   // their i haven't throws the exception in the run method so it will give an error
        System.out.println(" t thread is running..." + Thread.currentThread().getState());
        //now if i try to stop this thread for 2 second and run the mnain thread again
        try {
            Thread.sleep(5000); // This will pause the execution of the current thread for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static void main() throws InterruptedException {
        MyThread t = new MyThread();
        System.out.println(t.getState()); // Here i got NEW because the thread is created but not yet started
        t.start();
        System.out.println(t.getState()); // Here i got RUNNABLE in java their is no running stat
                                            // It's only runnable, which means it's waiting to be run' or running

        /**Now trying to run the above thread... by using sleep method*/
        Thread.sleep(100);
        System.out.println("main method wait for t thread... "+t.getState()); // Here i got TIMED_WATING because the thread has finished its execution

        /** join method is used to wait for the thread to finish its execution
         * means that the caller of this t is main method, so main method
         * will wait for the t thread to finish its execution*/
        t.join();
        System.out.println("Main waited 5 sec. for  t to finish...");
        System.out.println(" t thread is finished..." + t.getState()); // Here i got TERMINATED because the thread has finished its execution
    }
}
