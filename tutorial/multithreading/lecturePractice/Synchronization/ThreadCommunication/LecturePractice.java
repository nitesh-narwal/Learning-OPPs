package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.ThreadCommunication;

public class LecturePractice {
    /**
     * In a multithreading environment, threads often needs to
     * communicate and coordinate with each other to accomplish
     * a task
     * Without proper communication mechanism, threads are end up
     * in insufficient busy-wating state, leading to wastage of
     * CPU resources and potential deadlocks.
     *
     * For communication we use these methods:
     * 1. wait: this method says to the current thread to Release the lock and
     *          wait until the another method run notify or notifyAll is called on the same object.
     * 2. notify: this method says to the current thread to wake up one of the threads
     *            that are waiting on the same object.
     * 3. notifyAll: this method wakes up all the threads
     * These methods can only be called from within a synchronized block or method,
     * and they are used to coordinate the execution of threads.
     * */


}
