package me.niteshh.OPPs.tutorial.interfaces.lecturePractive.animals;

public interface Animal {

    public abstract void eat();   /** Here the public and the abstraction are not highlighted because
                                        the methods are already public and abstract in the interface */

    void sleep();

    public static final int MAX_LIFE = 100; /** Here the public and the static are not highlighted because
                                        the variables are already public and static in the interface
                                        -->  and Interface have abstract methods and static constants*/

    // we can also write " int MAX_LIFE = 100; "


    public static void info(){
        System.out.println(" We are accessing a interface method");
    }

    /** when we write a abstract method we have to implement it in all the subclass or child class */
    public abstract void information();    // we can also write " void information(); "

    /** But we can also write a method without static, which we don't have to impllement in all the subclass or child class
     * but we can only access it through creating an object of the child class
     * For that we use default keyword
     *
     * Because this default method hit by our instance classes,
     * so we can also write "this.eat()" which are the methods
     * of a child class.
     * so it implements the those methods first*/
    public default void defaultMethod(){
        this.eat();
        System.out.println("This is a default method and can only be accessed through creating an object of the child class" + getClass().getSimpleName());
    }

}
