package me.niteshh.OPPs.tutorial.multithreading.lecturePractice;

public class YeildMethod extends Thread{

    public YeildMethod(String name){
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++){
            System.out.println( Thread.currentThread().getName() + " is running... " + i);
            Thread.yield();  // giving hint to the scheduler - ki baaki threads ko bhi chances do...
        }
    }

    static void main(String[] args) {
        YeildMethod y1 = new YeildMethod("t1");
        YeildMethod y2 = new YeildMethod("t2");
        YeildMethod y3 = new YeildMethod("t3");
        y1.start();
        y2.start();
        y3.start();
    }
}
