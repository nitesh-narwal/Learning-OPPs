package me.niteshh.OPPs.tutorial.exceptionHandling.lecturePractice;

public class InsufficientFundsException extends Exception{

    private double amount;

    @Override
    public String toString() {
        return "Chutiye paisa kha hai tere pass MC... ";
    }

    public InsufficientFundsException(double amount) {
        /**this method is recommended to return a messagein constructor rather then using toString() method*/
        super("Bro you have insufficient funds to withdraw " + amount);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
