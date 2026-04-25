package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class LifeCycle {

    /**
     * 1. New: A thread is in this state when it has been created but not yet started.
     * 2. Runnable: After the Start method is called, the thread becomes runnable.
     *    it's ready to run and is waiting for CPU time
     * 3. Running: The thread id in this state when it is executing
     * 4. Blocked/Waiting: The thread in this state when it is waiting for a resource or
     *    for another thread to perform an action
     * 5. Terminated: The thread in this state when it has finished its execution
     * */

    static void main() {

        World world = new World();  // NEW thread
        world.start();  // RUNNABLE
    }
}
