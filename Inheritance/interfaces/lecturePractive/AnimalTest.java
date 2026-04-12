package me.niteshh.OPPs.Inheritance.interfaces.lecturePractive;


import me.niteshh.OPPs.Inheritance.interfaces.lecturePractive.animals.Animal;
import me.niteshh.OPPs.Inheritance.interfaces.lecturePractive.animals.Cat;
import me.niteshh.OPPs.Inheritance.interfaces.lecturePractive.animals.Dog;

public class AnimalTest {

    static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();
        dog.eat();
        dog.sleep();

        /** we can access the static variable from the child class and form the parent class also*/
        System.out.println(Animal.MAX_LIFE);
        System.out.println(Dog.MAX_LIFE);
        System.out.println(Cat.MAX_LIFE);

        /** Static methods can be accessed from the interface class only not from the child class*/
        // Dog.info(); --> this will give an error
        Animal.info();

        /** here we are trying to access the default method in the interface
         *
         * and here i'm also calling this.eat() method from the child classes */
        // Animal.defaultMethod(); --> this will give an error or this method will not apper in this interface
        dog.defaultMethod();
        cat.defaultMethod();

    }
}
