package me.niteshh.OPPs.Inheritance.statickeyword.Test;

public class testing {

    /**
     * Static keyword primarly used in java for memory management
     * we can use static keyword in class, variable, method etc
     */

    /** Here the main class is static because the JVM calls it directly without creating object of this Test class */
    public static void main(String[] args) {
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        Student student5 = new Student();

        System.out.println("Total Student: " + Student.totalStudent);  // we can access static variable using class name

        System.out.println("Total Student in this class : " + student1.totalStudent );  // we can also access static variable using object reference
                                                                                        // but it's not recommended

        System.out.println("Total Student in this class students : " + Student.getTotalStudent() );  // we can also access static variable using static method

        System.out.println("Sum of 2 numbers: " + sum(10, 20));


        System.out.println(Utils.max(10, 20));
        System.out.println(Utils.min(10, 20));
        System.out.println(Utils.TrimAndUpperCase("   hello world   "));
    }

    /** Static methods can not use non-static data members and non-static methods
     *  Means if i create a method
     *  public int sum(int a, int b){
     *      return a + b;
     *  } --->  it shows us Error.
     *  then we can't call this method without creating object of this class
     *  and we can access it if we create object of this class
     *
     *   testing test = new testing();
     *   test.sum(10, 20);
     */

    public static int sum(int a, int b){
        return a + b;

    }

    /**  this and super cannot be used in static context, they refer the objects but the static is attached to the class not the object,
     *  so we can't use this and super in static method or static block
     * 1. this --> it refers to the current object
     * 2. super --> it refers to the parent class
     *
     *  but the static attached to the class */


}