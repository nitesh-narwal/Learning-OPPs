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

        Computer computer = new Computer("HP", "XP05", "XYZ");
        computer.getOs().displayInfo();

        Computer.OperatingSystem os = computer.new OperatingSystem("Windows");
        os.displayInfo();

        /** it helps us in Memory Management and
         * we don't have to create new instance everytime for usb class when we
         * create new instance for Computer class*/
        Computer.USB usb = new Computer.USB("Type-C");
        usb.displayInfo();

        /** Learning  about the anonymous Inner class but first
         * learning about why we need anonymous Inner class*/
        ShoppingCart shoppingCart = new ShoppingCart(1500);
        CreditCard creditCard = new CreditCard("9319-XXX-XXX");
        shoppingCart.processPayment(creditCard);  /** <-- Here we want a object of the payment interface but we don't have any class
                                                        * that implements the payment interface so what we do ? we create a class
                                                        * name "CreditCard" which implements the payment interface and then
                                                        * we create an object of the class and pass it to the processPayment method  */


        /**Here we want a object of the payment interface but we don't have any class
         *  that implements the payment interface so what we do ? we create an
         *  anonymous inner class that implements the payment interface and
         *  we override the pay method and we pass the object of the anonymous inner class
         *  to the processPayment method*/
         shoppingCart.processPayment(new Payment() {
             @Override
             public void pay(double amount) {
                 System.out.println("Paid " + amount + " using Credit Card" );
             }
             /** When do we need Anonymous Inner class
              *  when ever we need to implement an interface without creating it's seperate implementation class
              *  Or when we */
         });

        shoppingCart.processPayment(new Payment() {
            @Override
            public void pay(double amount) {
                System.out.println("Paid " + amount + " using Paypal" );
            }
        });

        /** learning about Local Inner Classes... */
        Hotel hotel = new Hotel("Taj Hotel" , 10, 5);
        hotel.reserveRoom("Shobit", 2);
        hotel.reserveRoom("Ankit", 4);
        hotel.displayHotelInfo();
    }
}
