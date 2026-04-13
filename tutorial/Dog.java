package me.niteshh.OPPs.tutorial;

public class Dog extends Animal{

    // Here we are "Overriding" the method of the Animal class"
    // Writing "@Override" annotation is considered as a good practice because if the method is not overridden, then it will give an error
    @Override
    public void sayHello(){
        System.out.println("Woof!");
    }

    public void sayBye(){
        System.out.println("Woof Woof... ");
    }
}
