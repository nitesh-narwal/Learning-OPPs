package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

/**
 * CPU:
 * The CPU, Often refers as the brain of the computer.
 * Is responsible for executing instructions from programs.
 * It performs calculations, memory management, and  other tasks
 * operations specified by the instructions
 *
 * Cores:
 * A core is an individual processing unit within a CPU.
 * It is responsible for executing instructions from programs.
 * Modern CPUs have multiple cores, each with their own set of instructions.
 * Allowing them to perform multiple tasks simultaneously.
 * eg...
 * A 4-core processor has 4 cores, which means it can execute 4 tasks simultaneously.
 * For instance, 1 core could handle your web-browser, another music player
 * another a download manager, and the last one is your database manager.
 *
 *Program:
 * A set of instructions written in a specific programming language
 * That tells the CPU how to perform a specific task.
 *
 * Process:
 * A program is an instance of a program that is being executed.
 * When a program runs, the operating system creates a process to
 * manage it's execution and memory.
 *
 * Thread:
 * A thread is a small unit of execution within a process.
 * A process can have multiple threads, which share the same resources
 * but can run independently.
 *  eg...
 * A web browser process may have multiple threads, for different tabs,
 * with each thread running as a separate thread
 * */


/**
 * Multitasking:
 * multitasking allows an OS to run multiple processes simultaneously.
 * On Single core CPUs, this is done through time sharing, rapidly switching between tasks.
 * On Multi-core CPUs, true parallel execution occurs, with tasks distributed across cores.
 * The OS scheduler balances the load, ensuring efficient and responsive system performance.
 * */

/**
 * Multithreading refers to the ability to execute multiple threads with in a single process
 * concurrently, allowing for parallelism and improved performance.
 *
 * A web Browser can use multithreading by having seperate threads for rendering the page,
 * running JavaScript, and managing the user inputs.
 * this makes the browser responsive and efficient.
 *
 * Multithreading enhances the efficiency of multitasking by breaking down the individual tasks
 * into smaller sub-tasks or threads. These threads can be processed simultaneously,
 * making better use of the CPU's capabilities.
 * */

/**
 * In a Single-Core System:
 * - Both threads and processes are managed by the OS scheduler through time sliciing and
 *   context switching to create the illusion of simultaneous execution.
 * */

/**
 * In a Multi-Core System:
 * - Threads and processes can run on true parallel on different cores, with OS scheduler
 *   distributing tasks across cores for improved performance and responsiveness.
 * */

/** Time Slicing:
 * -Time Slicing divides the CPU's time into small intervals called time slices or quanta.
 * -The OS scheduler allocated these time slices to different processes and threads, ensuring
 *  each get's a fair share of the CPU's time.
 * -This prevents any single process or thread from monopolizing the CPU,
 * allowing for efficient multitasking and responsiveness.
 * */

/**
 * Context Switching:
 * -Context Switching is the process of saving the state of a currently running process
 *  or thread and loading the state of the next one to be executed.
 * -When a process or threads time slice expires, the OS schedular performs a context switch to move the CPUs
 *  focus to another process or thread.
 * -This allows multiple processes or threads to share the CPU, giving the appearance of simultaneous execution,
 *  even on single-core CPU or improving parallelism on multi-core CPUs.
 *
 * */

/** When java starts, one thread begins imediately, which is called the main thread.
 * This thread is responsible for executing the main method of the program.
 * */
public class Test {
    static void main() {
        System.out.println("Hello World...");
        System.out.println(Thread.currentThread().getName()); //gives the name of the thread

        /** To create a new thread in java, you can either extends the Thread class
         * or implement the Runnable interface.
         * */

        /** It's a way where we just extends the thread class and create a object of it and run the thread*/
        World world = new World();
        world.start();

        /** Here we implement the runnable interface in which  we create a object of the class
         * The run method is overridden to define the code that consitutes the new thead
         * then i have to  create an object of the thread class and then i have to pass the object of the class
         * which implements the runnable interface and then i have to start the thread
         * */
        WorldWithRunnableInterface wWRI = new WorldWithRunnableInterface();
        Thread thread = new Thread(wWRI);
        thread.start();

        /** In both cases, the run method contains the code that will be executed in the new thread.
         * */
        for (;;) {
                System.out.println(Thread.currentThread().getName());
        }


    }
}
























