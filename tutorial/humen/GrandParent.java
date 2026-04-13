package me.niteshh.OPPs.tutorial.humen;

public class GrandParent {
    private String name;
    private int age;

    public GrandParent(int age, String name){
        this.age = age;
        this.name = name;
        System.out.println("GrandParent Constructor called");
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
}
