package me.niteshh.OPPs.Inheritance.accessmodifier.zooTesting;

import me.niteshh.OPPs.Inheritance.accessmodifier.zoo.Dog;

public class Testing {
    static void main(String[] args) {

        Dog dog = new Dog("Roxy");
        dog.makeSound();
        dog.waggingTail();

        // dog.changeSound();
        /** here we can't call the changeSound() method,
                                because it is protected
                                and we are in the different package
         and we can't access this chaneSound() method without extendind the Animal class */

        dog.setDogSound("woof-woof");
        dog.makeSound();
    }
}
