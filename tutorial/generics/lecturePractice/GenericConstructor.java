package me.niteshh.OPPs.tutorial.generics.lecturePractice;

//public class GenericConstructor<T extends Number> {
/** Without making the class generic, we can make out constuctor generic
 * 1. we create a generic in angular brackets after access modifier and before constructor name
 * */
public class GenericConstructor {
   // private T value;

    public <T> GenericConstructor(T value) {

    }

//    public T getValue() {
//        return value;
//    }
//
//    public void setValue(T value) {
//        this.value = value;
//    }

    static void main(String[] args) {
        // we can create object of generic constructor without providing type argument
        GenericConstructor gc1 = new GenericConstructor(10); // T is inferred as Integer
        GenericConstructor gc2 = new GenericConstructor("Hello"); // T is inferred as String

        // we can also specify type argument explicitly
        GenericConstructor gc3 = new <Double>GenericConstructor(3.14); // T is Double

    }

}
