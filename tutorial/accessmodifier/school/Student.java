package me.niteshh.OPPs.tutorial.accessmodifier.school;

public class Student {

    private String name;
    private int age;

    private Student(){
        /**
         * private constructor--> can only be accessed inside the class
         *                        And if i don't that other people create object of this class
         *                        And that's why we need to make it private
         *                        But,
         *                              Throught Static keyword we can create object of this class without using the constructor
         *                              instead we can access the method at class level*/
    }

    public String sayHello(){
        return "Hello";
    }

    public static void sayBye(){
        System.out.println("Bye...");
    }
}
