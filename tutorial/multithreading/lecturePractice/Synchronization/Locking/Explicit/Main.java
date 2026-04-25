package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.Locking.Explicit;

public class Main {
    /**
     *  Lock is an interface and we are going to make it's onject
     *  so it look like this "Lock lock = new "ImplementationClass"
     *  har object main phle se hi ek object hota hai synchronised uska istemaal
     *  krta hai.
     *  but here we are creating our own lock object and
     *  we are going to use that lock object to control
     *  the access to the critical section of code.
     * */
    static void main(String[] args) {
        BankAccount sbi = new BankAccount();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                sbi.withdraw(50);
            }
        };

        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");
        t1.start();
        t2.start();
    }
}