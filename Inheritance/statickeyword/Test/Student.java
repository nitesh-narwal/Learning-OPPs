package me.niteshh.OPPs.Inheritance.statickeyword.Test;

public class Student {

    // before any instance is created, the static variable is initialized and it is shared among all the instances of the class
    public static int totalStudent = 0;

    /** this is a static block, this block execute when we call this class
     * 1. this block execute only once
     * We can use this block to initialize the static variable
     * */
    static{
        // this type of static block is called "STATIC INITIALIZATION BLOCK" use for one time setup task
        System.out.println("Static block is called :) ");
    }

    public Student(){
        totalStudent++;
    }

    private int id;
    private String name;
    private int age;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static int getTotalStudent() {
        return totalStudent;
    }
}
