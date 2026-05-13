package me.niteshh.OPPs.tutorial.generics.lecturePractice;

public class GenericMethods {


    public static void main(String[] args) {
        print(10);
        print("Hello");
        print(new Integer[]{1,2,3,4,5});
        print(new String[]{"Hello", "World", "Java", "Python", "Kangaru"});
    }

     static <T> void print(T item){
        System.out.println( item);
    }

     static <T> void print(T[] list){
        for(T item : list){
            System.out.print(", " +item);
        }
    }
}

