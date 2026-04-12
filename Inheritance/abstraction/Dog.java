package me.niteshh.OPPs.Inheritance.abstraction;

public class Dog extends Animal{

    @Override
    public void sayHello() {
        System.out.println("Woof!");
        /** Here we are providing the implementation of the abstract method "sayHello()" in the Dog class ,
         *  because the Dog class is a subclass of the Animal class and the sayHello() method is an abstract method in the Animal class,
         *  so we need to provide the implementation of the sayHello() method in the Dog class, otherwise we will get an error.
         *  We can provide the implementation of the sayHello() method in the Dog class,
         *  And if we don't want to provide unnecessary implementation of the sayHello() method in the Animal class,
         *  no need to provide defination of the sayHello() method,
         *  we can just create the sayHello() method in the Animal class and make it abstract.
         *  And the child class will write their defination of the sayHello() method in the future
         */
    }
}
