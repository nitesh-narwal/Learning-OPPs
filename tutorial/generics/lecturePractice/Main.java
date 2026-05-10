package me.niteshh.OPPs.tutorial.generics.lecturePractice;

import me.niteshh.OPPs.tutorial.generics.lecturePractice.basic.Box;
import me.niteshh.OPPs.tutorial.generics.lecturePractice.basic.Pair;
import me.niteshh.OPPs.tutorial.generics.lecturePractice.learningWithInterfaces.GenericContainer;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        ArrayList list = new ArrayList();
        /** ArrayList is not type safe, it has type casting error
         * 1. It can store any type of object
         * 2. Manual Casting is required*/

        list.add("Hello");
        list.add(123);
        // here the list can store any type of object
        // but if we want to store only string then we have to use generics

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Provide a string:");
//        ArrayList<String> list1 = new ArrayList<>();
//        for (int j = 0; j < 10; j++) {
//            String str = sc.nextLine(); // here we can store only string
//            list1.add(str);
//        }

       // Problems we face without generics
        // 1. Type safety: we can store any type of object in the list, which can lead to type casting errors at runtime.
        // 2. Code readability: it is not clear what type of objects are stored in the list, which can make the code harder to understand and maintain.
        // 3. Error handling: it is difficult to handle errors that may occur during the runtime of the program.


        Box<String> box = new Box<>();
        box.setBox("Hello");  // now after adding generic type we are getting compile time error so it becomes type safe
        String i = box.getBox(); // this will give a type casting error at runtime because we are trying to cast an Object to an int
        System.out.println(i);


        Pair<Integer, String> pair = new Pair<>(1, "Hello");
        boolean input = true;
        Scanner sc = new Scanner(System.in);
//        while(input? true : false){
//            System.out.print("Enter key and value: ");
//            pair.setKey(sc.nextInt());
//            System.out.print("Enter value: ");
//            pair.setValue(sc.next());
//            System.out.println(pair.getKey() + " - " + pair.getValue());
//
//            System.out.println("Do you want to continue? (true/false)");
//            input = sc.nextBoolean();
//        }


        GenericContainer<String> genericContainer = new GenericContainer<>();
        genericContainer.addItem("Generic Item");
        String item = genericContainer.getItem();
        System.out.println(item);
    }
}
