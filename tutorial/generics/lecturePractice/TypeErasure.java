package me.niteshh.OPPs.tutorial.generics.lecturePractice;

public class TypeErasure {

    /** After compiling the compiler removes the generic types.
     * And it will be replaced by the Object type, after the type is checked... */

    static void main(String[] args) {
//        NumberBox numberBox = new NumberBox();
//        numberBox.setNumber(10); // Autoboxing to Integer
//        Number num = numberBox.getNumber(); // Returns a Number
//        System.out.println(num);


//        NumberBox<Integer> numberBox = new NumberBox<>();
//        numberBox.setNumber(10); // Autoboxing to Integer
//        Number num = numberBox.getNumber(); // Returns a Number
//        System.out.println(num);

        NumberBox numberBox = new NumberBox();
        numberBox.setNumber(10);
        Integer num = (Integer) numberBox.getNumber();
        System.out.println(num);
    }


//    class NumberBox<T extends Number>{
//
//        private T number;
//
//        public void setNumber(T number){
//            this.number = number;
//        }
//
//        public T getNumber(){
//            return number;
//        }
//    }


    /** Now how compiler will do the thing...
     * the T will be replaced by the Number type not with the Object type
     * because it was not the T only*/
    static class NumberBox{

        private Number number;

        public void setNumber(Number number){
            this.number = number;
        }

        public Number getNumber(){
            return number;
        }
    }
}
