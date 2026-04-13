package me.niteshh.OPPs.tutorial.Polymorphism;

// Compile time polymorphism ---> also called method overloading
public class Calculator {

    public int add(int a, int b){
        return a + b;
    }

    public int add(int a, int b, int c){
        return a + b + c;
    }

    public int add(int a, int b, int c, int d){
        return a + b + c + d;
    }

    public double add(double a, double b){
        return a + b;
    }
}
