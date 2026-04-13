package me.niteshh.OPPs.tutorial.abstraction;

public abstract class Animal {

    private String name;
    private int age;
    private boolean hasSuperPower;

    protected Animal(){
        this.hasSuperPower = false;
    }


    protected abstract void sayHello();
    /** if we want to make a method abstract, we need to write "abstract" before the method
     and this abstruct class the method we make abstract,
     we don't need to write the body of the method,
     we just need to write the method signature and end it with a semicolon (;)
     and that's it, the method is now abstract'

     if i use this...
     abstruct void sayHello(); --> this will only accessable in this package only
     &
     public abstract void sayHello(); --> this will be accessable in this folder
     &
     protected abstract void sayHello(); --> this will be accessable in this folder and we can override it in the subclass
                                            and we use protected so that only those class can access it
                                            untill which "extends" Animal class
    */



    public void eat(){
        System.out.println("Eating...");
    }
    // these methods are called concrete methods, because they have a body and they can be called directly,
    // but the abstract methods cannot be called directly, they need to be implemented in the subclass

    public void sleep(){
        System.out.println("zzz...");


    }
}
