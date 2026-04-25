package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

/** Jab bhi mujhe kisi thread se koi kaam krana hai toh
 *  Sabse phle mai ek class banauga or use extends kra lunga thread se
 *  or thread ke ander ek method hoga usko override kra lunga
 *  @author nitesh
 *
 *
 *  -The run method is overridden to define the code that consitutes the new thead
 *  Start method is called to initiate the new thread
 *  */

public class World extends Thread{
    @Override
    public void run() {
        for(;;){
            System.out.println(Thread.currentThread().getName());
        }
    }
}
