package me.niteshh.OPPs.Inheritance.interfaces.lecturePractive.animals;

import java.util.logging.Logger;

/** To use the interface in a class, we need to implement the interface or the class */
public class Dog implements Animal{

    Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void eat() {
        System.out.println("Dog is eating meat...");
    }

    @Override
    public void sleep() {
        logger.info("Dog sleeping...");
    }

    @Override
    public void information() {
        logger.info("This is a dog class which implements the animal interface");
    }


}
