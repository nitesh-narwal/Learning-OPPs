package me.niteshh.OPPs.tutorial.humen;

// Child class extends Parent class and parent class extends grandparent class (called multi level inheritance)
public class Child extends Parent{

    public Child(int age, String name){
        /*super keyword is used to call the constructor of the parent class
          once called, then we can use the methods of the parent class
          And we have to call the super() at the beginning of the constructor so that the child class can access the parent class
         */
        super(age, name);
        System.out.println("Child Constructor called");
    }

}

