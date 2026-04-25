package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

/** Created to find out the difference between the Thread class and the Runnable interface
 *  Suppose we have a class that's extends the another class
 *  or i say i have a class A that extends the MyThread class
 *  then in that case i can't extend it again with Thread class
 *  then the only way to do that is to implement the Runnable interface
 * */
public class A extends MyThread implements Runnable {

    @Override
    public void run() {
        super.run();
    }
}
