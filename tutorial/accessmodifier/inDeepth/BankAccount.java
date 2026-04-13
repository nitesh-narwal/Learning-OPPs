package me.niteshh.OPPs.tutorial.accessmodifier.inDeepth;

/**
 * STEP 5: ADVANCED - REAL-WORLD SCENARIO (BANK SYSTEM)
 * 
 * This demonstrates how to use all access modifiers together
 * in a real-world production-like scenario.
 * 
 * Key Concepts:
 * - Combining access modifiers for security
 * - Encapsulation in practice
 * - Immutability using access modifiers
 * - Template method pattern with protected
 */

// ============ BASE CLASS ============
public abstract class BankAccount {
    
    // PRIVATE - Internal state, never exposed
    private long accountNumber;
    private double balance;
    private int failedAttempts = 0;
    private static final int MAX_FAILED_ATTEMPTS = 3;
    
    // PROTECTED - For subclasses to use
    protected String accountHolder;
    protected String accountType;
    
    // PUBLIC - External API
    public static final double MINIMUM_BALANCE = 500.0;
    
    /**
     * PROTECTED CONSTRUCTOR - Only subclasses can instantiate
     */
    protected BankAccount(long accountNumber, String accountHolder, String accountType, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.balance = validateBalance(initialBalance) ? initialBalance : MINIMUM_BALANCE;
    }
    
    /**
     * PUBLIC - External API to get balance
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * PUBLIC - External API to deposit
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount");
            return false;
        }
        
        balance += amount;
        recordTransaction("Deposit", amount, balance);
        return true;
    }
    
    /**
     * PUBLIC - External API to withdraw
     */
    public boolean withdraw(double amount) {
        if (!validateWithdrawal(amount)) {
            failedAttempts++;
            if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                lockAccount();
            }
            return false;
        }
        
        balance -= amount;
        failedAttempts = 0; // Reset on successful transaction
        recordTransaction("Withdrawal", amount, balance);
        return true;
    }
    
    /**
     * PROTECTED - For subclasses to override (template method pattern)
     */
    protected abstract double calculateInterest();
    
    /**
     * PUBLIC - Uses protected abstract method
     */
    public void applyInterest() {
        double interest = calculateInterest();
        balance += interest;
        recordTransaction("Interest", interest, balance);
        System.out.println("Interest of " + interest + " applied");
    }
    
    /**
     * PRIVATE - Internal validation logic
     */
    private boolean validateBalance(double amount) {
        return amount >= MINIMUM_BALANCE;
    }
    
    /**
     * PRIVATE - Internal validation logic
     */
    private boolean validateWithdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive");
            return false;
        }
        if (balance - amount < MINIMUM_BALANCE) {
            System.out.println("Insufficient balance. Minimum balance required: " + MINIMUM_BALANCE);
            return false;
        }
        return true;
    }
    
    /**
     * PRIVATE - Internal helper method
     */
    private void recordTransaction(String type, double amount, double newBalance) {
        System.out.println("[TRANSACTION] " + type + " | Amount: " + amount + " | New Balance: " + newBalance);
    }
    
    /**
     * PRIVATE - Internal helper method
     */
    private void lockAccount() {
        System.out.println("Account locked due to multiple failed attempts!");
    }
    
    /**
     * PUBLIC - Account info
     */
    public void displayAccountInfo() {
        System.out.println("=== Account Info ===");
        System.out.println("Holder: " + accountHolder);
        System.out.println("Type: " + accountType);
        System.out.println("Number: " + accountNumber);
        System.out.println("Balance: " + balance);
    }
}

// ============ SUBCLASS 1: SAVINGS ACCOUNT ============
class SavingsAccount extends BankAccount {
    
    // PRIVATE - Specific to this account type
    private static final double INTEREST_RATE = 0.04; // 4% annual
    
    public SavingsAccount(long accountNumber, String accountHolder, double initialBalance) {
        super(accountNumber, accountHolder, "Savings", initialBalance);
    }
    
    /**
     * PROTECTED - Overrides abstract method from parent
     */
    @Override
    protected double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }
}

// ============ SUBCLASS 2: CHECKING ACCOUNT ============
class CheckingAccount extends BankAccount {
    
    // PRIVATE - Specific to this account type
    private static final double INTEREST_RATE = 0.01; // 1% annual
    private int checkCount = 0;
    
    public CheckingAccount(long accountNumber, String accountHolder, double initialBalance) {
        super(accountNumber, accountHolder, "Checking", initialBalance);
    }
    
    /**
     * PROTECTED - Overrides abstract method from parent
     */
    @Override
    protected double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }
    
    /**
     * PUBLIC - Additional feature for checking account
     */
    public void writeCheck(String checkNumber, double amount) {
        if (withdraw(amount)) {
            checkCount++;
            System.out.println("Check #" + checkNumber + " written for " + amount);
        }
    }
    
    /**
     * PUBLIC - Uses private variable
     */
    public int getTotalChecksWritten() {
        return checkCount;
    }
}

// ============ PACKAGE-PRIVATE UTILITY CLASS ============
class AccountValidator {
    
    /**
     * DEFAULT - Package-private method
     * Can validate accounts within this package
     */
    static boolean validateAccountHolder(String name) {
        return name != null && name.length() >= 3;
    }
    
    /**
     * DEFAULT - Package-private method
     */
    static boolean validateAccountNumber(long accountNum) {
        return accountNum > 100000 && accountNum < 9999999;
    }
    
    /**
     * DEFAULT - Package-private method
     */
    static boolean isValidTransaction(double amount) {
        return amount > 0 && amount < 1000000;
    }
}

/**
 * VISIBILITY SUMMARY:
 * 
 * ┌─ PUBLIC
 * │  └─ Can be called from anywhere
 * │  └─ Example: getBalance(), deposit(), withdraw()
 * │
 * ├─ PROTECTED
 * │  └─ Can be overridden in subclasses
 * │  └─ Example: calculateInterest() (abstract)
 * │
 * ├─ DEFAULT (PACKAGE-PRIVATE)
 * │  └─ Utility classes for internal use
 * │  └─ Example: AccountValidator methods
 * │
 * └─ PRIVATE
 *    └─ Internal implementation details
 *    └─ Example: validateBalance(), recordTransaction()
 * 
 * PRINCIPLE: Expose only what's necessary!
 */

