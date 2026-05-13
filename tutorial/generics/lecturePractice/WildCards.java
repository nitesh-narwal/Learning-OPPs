package me.niteshh.OPPs.tutorial.generics.lecturePractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Wild cards in generics means -> ?
public class WildCards  {
    /** In Java Generic, wild cards (?) are a special tyoe of type arguments.
     * That can be used in methods arguments or class definition to represent an
     * unkown type. They allow for more flexible and dynamic code by letting
     * the type be specified later or be more loosely defined.*/

    static void main(String[] args) {
        ArrayList<?> list = new ArrayList<>();
        // list.add(2); // ---> when using wild card we cannot add anything to the list 
        //                    because we don't know the type of the list, it can be any type.


        System.out.println(sum(Arrays.asList(1, 2, 3, 4, 5))); // Output: 15.0
        System.out.println(sumWithLowerBound(Arrays.asList(1, 2, 3, 4, 5))); // Output: 15.0


        /** In this case i can't add anything to the list because the list is of type ArrayList<?>
         * and it can take any type of object. but it's not the similarclase with lower bound. */
//        List<? extends Number> numberList = new ArrayList<>();
//        numberList.add(123);

        /** Here we can add it tell us that it can take things upper form Integer class which means we specify things
         * */
        List<? super Integer> intList = new ArrayList<>();
        intList.add(123);

    }

    // we can use wild card when we are not returning anything and reading stuff.

        static void printList(List<?> list){
            for(Object item : list){
                System.out.println(item);
            }
        }

        static void addToList(List<? super Integer> list){
            list.add(10);
            list.add(20);
        }

        static void readFromList(List<? extends Number> list){
            for(Number item : list){
                System.out.println(item);
            }
        }

        public <T> void getFirstElement(List<? extends T> list){
            if(!list.isEmpty()){
                T firstElement = list.get(0);
                System.out.println("First element: " + firstElement);
            }
        }

        public <T> void copy(List<T> source, List<T> destination){
            for(T item : source){
                destination.add(item);
            }
        }

        public <T> void copyList(List<T> source, List<? super T> destination){
            for(T item : source){
                destination.add(item);
            }
        }

        //Upper Bound: we can use the classes under the Number class
        public static double sum(List<? extends Number> numbers){
            double sum = 0;
            for(Number num : numbers){
                sum += num.doubleValue();
            }
            return sum;
        }

        //Lower Bound: we can use the classes Upper from Integer class
        public static double sumWithLowerBound(List<? super Integer> numbers){
            double sum = 0;
            for(Object num : numbers){
                if(num instanceof Integer){
                    sum += ((Integer) num).doubleValue();
                }
            }
            return sum;
        }

}
