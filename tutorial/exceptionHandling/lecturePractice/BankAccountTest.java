package me.niteshh.OPPs.tutorial.exceptionHandling.lecturePractice;

public class BankAccountTest {
    static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        try {
            account.withdraw(1500); // This will throw an exception
        } catch (InsufficientFundsException  e) {
            System.out.println(e);
          //  e.getAmount(); // You can access the amount that caused the exception
            System.out.println("Error: " + e.getMessage());
        }
    }
}
