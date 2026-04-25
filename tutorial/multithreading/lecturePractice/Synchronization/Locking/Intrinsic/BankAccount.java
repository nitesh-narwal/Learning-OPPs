package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.Locking.Intrinsic;

public class BankAccount {

    private int balance = 100;

    /** as we can see because of synchronized the 2nd thread will wait and it would be long
     * if the process going with first thread takes longer time.
     * which means if 1 thread is accessing this method then 2nd thread would w8 untill the
     * process is complete by one thread */
    public synchronized void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw: " + amount);
        
        if(balance >= amount){
            System.out.println(Thread.currentThread().getName() + " proceeding with withdrawls");
            try {
                Thread.sleep(3000); // let this time is going to make changes in the database
            } catch (InterruptedException e) {

            }
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " Completed withdrawn. Remaining balance : " + balance);
        }else{
            System.out.println(Thread.currentThread().getName() + " Insufficient Balance for this amount: " + amount);
        }
    }
}
