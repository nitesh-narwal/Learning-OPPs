package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class SetDeamon extends Thread{

    @Override
    public void run() {
        while(true){
            System.out.println( Thread.currentThread().getName() + " is running... ");
        }
    }

    static void main(String[] args) {
        SetDeamon t1 = new SetDeamon();  //here creating a thread t1 is called a userThread(we use it do run thread)
        t1.setDaemon(true); // but there i setting t1 as a daemon thread
        t1.start(); // that means when main method exits t1 will also exit even if it is running a infinite loop

        /** Can comment the below user thread to see the working of daemon thread*/
        SetDeamon t2 = new SetDeamon();  // here creating a thread t2 is called a userThread(we use it do run thread)
        t2.start(); // this thread wouldn't exit even if main method exits'

        System.out.println("Main thread is exiting...");
    }

    /** Now demon threads are those which run in background
     * 1. Daemon threads are automatically terminated when the main thread exits
     * 2. JVM do not wait for daemon threads to finish, which means
     *    main threads wait for user threads to finish
     *    but it don't wait for daemon threads to get finish...
     *    That means if user thered work is finished it will not wait for daemon threads to finish
     */
}
