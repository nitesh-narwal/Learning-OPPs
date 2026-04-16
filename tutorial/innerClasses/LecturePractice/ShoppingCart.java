package me.niteshh.OPPs.tutorial.innerClasses.LecturePractice;

public class ShoppingCart {
    private double totalAmount;

    public ShoppingCart(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /** Because Payment is an interface so it don't have an object for
     * to call for in test class, So what we do ?
     * We create a class that implements the payment interface*/
    public void processPayment(Payment paymentMethod){
        paymentMethod.pay(totalAmount);
    }
}
