package me.niteshh.OPPs.tutorial.exceptionHandling.lecturePractice;

public class BankAccount {

    private double balance;

    public BankAccount(double amount) {
        this.balance = amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if( amount > balance){
            throw new InsufficientFundsException(amount);
        } else {
            balance -= amount;
            System.out.println("Withdrawal successful. Remaining balance: " + balance);
        }
    }
}
