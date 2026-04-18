package me.niteshh.OPPs.tutorial.wrapperClass.lecturePractice;

public class Test {
    static void main() {

        /** Java is not purely an Object oriented Programming language because
         * it have primitive data types and when we try to make it's object it's not
         * possible because we can only create objects of classes... */

        /** Java would do auto-Boxing means it automatically converts the Primitive data type into
         * Integer if auto-boxing is not present we have to do this thing instead
         * --->      Integer b = Integer.valueOf(1);   */
        int a = 10;
        Integer b = a; // Auto-boxing: primitive to wrapper
        System.out.println("Wrapper class value: " + b);

        /** Also do un-Boxing means it automatically converts the Integer into
         * Primitive data type if auto-boxing is not present we have to do this thing instead
         * --->      int c = b.intValue(b);   */
        Integer c = 20;
        int d = c; // Auto-unboxing: wrapper to primitive
        System.out.println("Primitive value: " + d);

        // Using wrapper class methods
        String str = "123";
        int num = Integer.parseInt(str); // Convert String to int
        System.out.println("Parsed number: " + num);


        Student x = new Student();
        x.id = 1;
        fun(x);
        System.out.println("Student ID: " + x.id);


    }

    private static void fun(Student a){
        Student student = new Student();
        student.id = 2;
        a = student;
    }
}


class Student{
    int id;
}