package me.niteshh.OPPs.tutorial.innerClasses.LecturePractice;

public class Test {
    static void main() {

        /** This is an example of the inner class */
        Car car = new Car("Tata Nexon");
        Car.Engin engin = car.new Engin(); // Create an instance of the inner class using the outer class instance
        engin.start();
        engin.stop();

        /** This is the example of EnginWithoutInnerClass
         *  Means creating extra class rather then creating the Inner one
         *  Cons(Challenges):
         *  1. Have to create new engin object for every new Car
         *  */

        Car car1 = new Car("lamborGinni");
        EnginWithoutInnerClass engin1 = new EnginWithoutInnerClass(car1);
        engin1.start();
        engin1.stop();

        Car car2 = new Car("BMW");
        EnginWithoutInnerClass engin2 = new EnginWithoutInnerClass(car2);
        engin2.start();
        engin2.stop();
    }
}
