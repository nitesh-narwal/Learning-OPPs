package me.niteshh.OPPs.tutorial.generics.lecturePractice.basic;

public class Box<T> {

    // we are going to make Box class as generic Type...
    /** Generic Type allow allow you to define a class, interface or method with placeholders (type Parameters)
     * for the data types they will work with...
     *
     * Syntax of generic type:
     * class Box<T> {...}
     * interface Box<T> {...}
     * method<T> void method(T t) {...}
     * T is a type parameter
     * */

//    private Object box;
    private T box;

    public T getBox() {
        return box;
    }

    public void setBox(T box) {
        this.box = box;
    }
}
