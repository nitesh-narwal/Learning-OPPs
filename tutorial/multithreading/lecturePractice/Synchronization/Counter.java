package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization;

public class Counter {

    private int count =0;

    /** we call this condition as Rase condition in which we aren not using synchronized
     * when multiple threads working on a same condition and due to time
     * we got different results */
//    public void increment(){
//        count++;
//    }

    /** So if we want only one method can access the increment method
     *  At a time then we just have to add "synchronized" in it */
//    public synchronized void increment(){
    /** And we call this term as "mutual exclusion"... */
//        count++;   // this called a critical part(just a nomily)
//    }      // This is when we want to make the entire method is accessed one at a time.


    /** But if i want to make only a specific code block synchronized or
     * can access by one method at a time... then we need to use*/
    public void increment(){
        synchronized (this){  // Here "this" means the current object or instance  of Counter class, who is accessing it
            count++;
        }
        /** And this is called a synchronized block... */
    }

    public int getCount(){
        return count;
    }
}
