package me.niteshh.OPPs.tutorial.Test;

import me.niteshh.OPPs.tutorial.Animal;
import me.niteshh.OPPs.tutorial.Dog;
import me.niteshh.OPPs.tutorial.Polymorphism.Calculator;
import me.niteshh.OPPs.tutorial.humen.Child;
import me.niteshh.OPPs.tutorial.multipleInheritance.SmartPhone;

public class testing {

    static void main() {

        Child child = new Child(9, "Rahul");
        child.getAge();
        child.getName();
//        Dog dog = new Dog();
//        dog.setAge(2);
//        dog.setName("Chiku");
//        dog.setBreed("Golden Retriever");
//        dog.eat();
//        dog.sayHello();
//
//        System.out.println(dog.getName());
//        System.out.println(dog.getAge());
//        System.out.println(dog.getBreed());
//
//        Child child = new Child();
//        child.setName("rahul");
//        child.setAge(9);
//
//        Parent parent = new Parent();
//        parent.setName("Ramesh");
//        parent.setAge(35);

        SmartPhone smartPhone = new SmartPhone();
        smartPhone.clickPhotos();

        Calculator calculator = new Calculator();
        System.out.println(calculator.add(10, 20));
        System.out.println(calculator.add(20.9876, 9.34567));
        System.out.println(calculator.add(10, 20, 30));
        System.out.println(calculator.add(10, 20, 30, 40));


        // it's time to move to Runetime polymorphism

        Animal animal = new Dog();   // called upcasting--> because we are calleng the sub class of the higher class
        animal.eat();
        animal.sayHello();
        // cannot call the method of the Dog class because we have created the object of the Dog class
        // but we have assigned it to the Animal class reference variable

        // animal.sayBye();  --> this will give an error because the reference variable is of the Animal class and the sayBye() method is not present in the Animal class,
        // it is only present in the Dog class

        Dog dog = new Dog();
        dog.sayBye();

        Animal animal1 = new Cat();
        animal1.eat();
        animal1.sayHello();


    }
}
