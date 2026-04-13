package me.niteshh.OPPs.tutorial.interfaces.lecturePractive.animals;

public class Cat implements Animal{

    @Override
    public void eat() {
        System.out.println("Cat is eating...");
    }

    @Override
    public void sleep() {
        System.out.println("Cat is sleeping...");
    }

    @Override
    public void information() {
        System.out.println("This is a cat class which implements the animal interface");
    }
}
