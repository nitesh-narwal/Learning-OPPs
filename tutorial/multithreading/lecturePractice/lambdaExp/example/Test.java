package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.lambdaExp.example;

public class Test {
    static void main(String[] args) {
        // Here i can implement the interface, extends the thread or instead of that
        // i can use lambda expression to implement the Runnable interface

//        EngineeringStrudent engineeringStrudent = new EngineeringStrudent();
//        System.out.println(engineeringStrudent.getBio("Nitesh"));

        // here i have to create a an interface, then a engineeringStudent class then using it in the main method.


//        Student student = new Student() {
//            @Override
//            public String getBio(String name) {
//                return "i'm an Enginnering Student, My name is: " + name;
//            }
//        };
//
//        System.out.println(student.getBio("Nitesh"));

        // using lambda expression to implement the Student interface
        Student lawStudent = (name) -> "i'm an Law Student, My name is: " + name;

        //if their only one parameter we can remove the bracket and if their is only one line of execution,
        // we can remove the brackets and the return statement as well.

        System.out.println(lawStudent.getBio("Nitesh"));


    }
}
