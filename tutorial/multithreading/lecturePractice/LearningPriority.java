package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class LearningPriority extends Thread{

    /** How to name the thread*/
    public LearningPriority(String name){
        super(name);
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
           for(int j = 0; j < 10; j++){
               System.out.println(
                       Thread.currentThread().getName()
                               + " is running with priority-  "
                               + Thread.currentThread().getPriority() +
                               " and id- " + Thread.currentThread().getId()
               + " and is- " + (Thread.currentThread().isAlive() ? "alive" : "dead")
               );
               try {
                   Thread.sleep(100); // This will pause the execution of the current thread for 100 milliseconds
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }

           }
        }
    }

    static void main(String[] args){
         /** Thread priority is a mechanism that allows you to specify the relative importance of threads in a multithreaded application.
          *  It helps the thread scheduler determine which thread should be given more CPU time when multiple threads are competing for resources.
          *  In Java, thread priorities are represented by integer values ranging from 1 (MIN_PRIORITY) to 10 (MAX_PRIORITY),
          *  with a default priority of 5 (NORM_PRIORITY). */
        LearningPriority low = new LearningPriority("Low Priority");
        LearningPriority medium = new LearningPriority("Medium Priority");
        LearningPriority high = new LearningPriority("High Priority");
        low.setPriority(Thread.MIN_PRIORITY);
        medium.setPriority(Thread.NORM_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);
        low.start();
        medium.start();
        high.start();
    }
}
