package me.niteshh.OPPs.tutorial.innerClasses.LecturePractice;

public class CreditCard implements Payment{

    private String creditCardNumber;

    public CreditCard(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Processing credit card payment of $" + amount + " using card number: " + creditCardNumber);
    }
}
