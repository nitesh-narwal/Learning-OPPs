package me.niteshh.OPPs.Inheritance.vehical;

/** we can also prevent our class from extending another class by using final keyword,
 * Or extend by another class by using final keyword*/
public class Car extends Vehical{
    @Override
    public void drive() {

    }

    @Override
    public void accelerate() {

    }

    @Override
    public void decelerate() {

    }

    @Override
    public void brake() {

    }

    /** we can prevent from overriding a method by simpliy using final keyword in the method*/
    public final void airBags(){
        System.out.println("we provide 4 AirBags in a Car");
    }

    /** Putting final keyword in constructor is not allowed
     * because their is no meaning */


}
