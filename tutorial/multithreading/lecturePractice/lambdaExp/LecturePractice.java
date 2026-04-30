package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.lambdaExp;

public class LecturePractice {
    static void main(String[] args) {
        /**
         * Lambda Expression is an anonymous function that can be used to implement a functional interface.
         * we don't require public, void and function name instead we can write
         *  () -> " Piece of Code";
         *
         * Also those interfaces in which only one abstract method is present are called functional interfaces
         * and we can use lambda expression to implement those interfaces.
         * */

        // Here i can implement the interface, extends the thread or instead of that
        // i can use lambda expression to implement the Runnable interface
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Hello from the thread!");
//            }
//        };

//        Runnable runnable = () -> {
//            System.out.println("Hello from the thread!");
//        };
//        Thread t1 = new Thread(runnable);
//        t1.start();
        // we can also write

        Thread task1 = new Thread(() -> System.out.println("Hello from the thread!"));
        task1.start();

    }

}
