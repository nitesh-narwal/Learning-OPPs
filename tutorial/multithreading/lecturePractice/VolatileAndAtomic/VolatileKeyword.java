package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.VolatileAndAtomic;

public class VolatileKeyword {
    static void main(String[] args) {

        SharedData sharedData = new SharedData();
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sharedData.setFlagTrue();
            System.out.println("Flag set to true by: " + Thread.currentThread().getName());
        });

        Thread reader = new Thread(() -> {
            sharedData.printIfFlagTrue();
        });
        writer.start();
        reader.start();

    }
}

class SharedData{
    // this flag is shared between the reader and writer threads
    // private boolean flag = false;

    // add volatile keyword to make the flag visible to all threads and
    // so that the reader thread can read the updated value of flag
    // and it wouldn't bring the data form cache instead it will read the value from main memory
    private volatile boolean flag = false;

    public void setFlagTrue(){
        flag = true;
    }

    /**If the flag is true then why it's not printing?
     * 1. Because of the visibility issue,
     *    the reader thread might not see the updated value of flag set by the writer thread.
     * 2. Why? because every thread has its own local copy of the variable in their cache,
     *    so the reader thread might not see the updated value of flag.
     * 3. To solve this we use volatile keyword,
     *    which ensures that all threads see the most up-to-date value of the variable.
     * */
    public void printIfFlagTrue(){
        while (!flag){
            // Busy waiting, waiting for flag to become true
        }
        System.out.println("We are printing this only if flag is true: " + flag + " " + Thread.currentThread().getName());
    }
}


