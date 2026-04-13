package me.niteshh.OPPs.tutorial.accessmodifier.zoo;

public class Dog extends Animal{

    public Dog(String name){
        super(name, "Woof!");
    }

    public void waggingTail(){
        System.out.println(getName() + " is Wagging it's tail");
    }

    private String getName(){
        return getClass().getSimpleName();  // getSimpleName() returns the name of the class without the package name
    }

    public void setDogSound(String sound){
        changeSound(sound);
    }

}
