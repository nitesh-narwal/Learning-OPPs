package me.niteshh.OPPs.tutorial.generics.lecturePractice;

public class GenericEnum {

    enum Operations{
        ADD, SUB, MUL, DIV;

        public <T extends Number> double apply(T a, T b){
            switch (this){
                case ADD: return a.doubleValue() + b.doubleValue();
                case SUB: return a.doubleValue() - b.doubleValue();
                case MUL: return a.doubleValue() * b.doubleValue();
                case DIV: return a.doubleValue() / b.doubleValue();
                default: return 0;
            }
        }
    }

    public static void main(String[] args) {
        Double res1 = Operations.ADD.apply(10, 20);
        Double res2 = Operations.SUB.apply(10.5, 5.5);
        Double res3 = Operations.MUL.apply(3, 4);
        Double res4 = Operations.DIV.apply(10, 2);
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
        System.out.println(res4);

    }
}
