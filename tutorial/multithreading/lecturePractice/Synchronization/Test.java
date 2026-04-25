package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization;

public class Test {

    static void main(String[] args) {
        Counter counter = new Counter();
        MyThread t1 = new MyThread(counter);
        MyThread t2 = new MyThread(counter);

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();

        } catch (InterruptedException e) {
            System.out.println("Got this error" + e);
        }

        System.out.println("Final count: " + counter.getCount());
    }
    /** Here we wouldn't get the exact 20000 beacuse of the reason,
     *  when our 2 threads run simultaneously, then let's say
     *  if count = 101  at some point then both can read that same
     *  count and increase it together which count as one
     *  So their would be some cases though...
     *
     *  So if we want only one method can access the increment method
     *  At a time then we just have to add synchronization in it
     *  and other methods would wait if one is using it... */


    /** And we call it as "LOCKING"...
     *  and their are 2 types of locks
     *  1. Intrinsic -> These are built into every object in java. You don't see them, but they are their.
     *                  when you use synchronized keyword, you are using these automatic lock.
     *  2. Explicit ->  These are more advanced locks you can control yourself using lock class from using
     *                  java.util.concurrent.locks.
     *                  You explicitly say when to lock and unlock, give you more control over how and when
     *                  people can write in the notebook.
     *  */

}
