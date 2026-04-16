package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * STEP 4: CREATING CUSTOM EXCEPTIONS
 * 
 * Why Create Custom Exceptions?
 * - Express domain-specific errors
 * - Provide meaningful error messages
 * - Allow specific exception handling
 * - Better code organization and readability
 * 
 * Types of Custom Exceptions:
 * 1. Checked Custom Exception: extends Exception
 *    - Caller must handle it
 *    - For recoverable conditions
 * 
 * 2. Unchecked Custom Exception: extends RuntimeException
 *    - Optional to handle
 *    - For programming errors
 * 
 * Creating Custom Exception:
 * 1. Extend Exception or RuntimeException
 * 2. Provide constructors
 * 3. Optionally override methods
 * 4. Use meaningful names and documentation
 */

public class Step4_CustomExceptions {

    public static void main(String[] args) {
        System.out.println("=== CUSTOM EXCEPTIONS ===\n");
        
        // Example 1: Using custom checked exception
        System.out.println("Example 1: Custom Checked Exception");
        try {
            BankAccount account = new BankAccount("ACC001", 5000);
            System.out.println("Account created: " + account.getAccountNumber());
            
            account.withdraw(3000);
            System.out.println("Withdrawn: Rs. 3000");
            System.out.println("Balance: Rs. " + account.getBalance());
            
            account.withdraw(3000); // Will throw InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            System.out.println("Details: " + e.getDetails());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 2: Using custom checked exception for invalid input
        System.out.println("Example 2: Custom Exception for Validation");
        try {
            Student student = new Student(-1, "John"); // Invalid age
        } catch (InvalidStudentDataException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 3: Custom unchecked exception (extends RuntimeException)
        System.out.println("Example 3: Custom Unchecked Exception");
        try {
            validateEmail("invalid-email"); // Will throw InvalidEmailException
        } catch (InvalidEmailException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 4: Exception with custom fields
        System.out.println("Example 4: Exception with Custom Fields");
        try {
            int userID = 999;
            User user = findUserById(userID); // Will throw UserNotFoundException
        } catch (UserNotFoundException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Search ID: " + e.getSearchedId());
            System.out.println("Message: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 5: Chaining exceptions with custom exception
        System.out.println("Example 5: Exception Chaining");
        try {
            readAndParseFile("nonexistent.txt");
        } catch (DataProcessingException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            System.out.println("Root cause: " + e.getCause().getClass().getSimpleName());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 6: Multiple custom exceptions
        System.out.println("Example 6: Multiple Custom Exceptions");
        try {
            processOrder("", 0, -100); // Will throw different exceptions
        } catch (InvalidOrderException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        } catch (NegativePriceException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
            System.out.println("Price: " + e.getPrice());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // Example 7: Custom exception with recovery suggestion
        System.out.println("Example 7: Exception with Recovery Suggestion");
        try {
            configureDatabase("invalid_connection_string");
        } catch (DatabaseConfigException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName());
            System.out.println("Error: " + e.getMessage());
            System.out.println("Recovery: " + e.getRecoverySuggestion());
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        System.out.println("✓ All custom exception examples completed!");
    }
    
    // Helper methods
    
    static void validateEmail(String email) throws InvalidEmailException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidEmailException("Email format is invalid: " + email);
        }
    }
    
    static User findUserById(int id) throws UserNotFoundException {
        // Simulate database lookup
        if (id != 1) {
            throw new UserNotFoundException("User with ID " + id + " not found", id);
        }
        return new User(1, "John Doe");
    }
    
    static void readAndParseFile(String fileName) throws DataProcessingException {
        try {
            // Simulating file read that throws IOException
            throw new java.io.IOException("File not found: " + fileName);
        } catch (java.io.IOException e) {
            // Wrapping checked exception in custom exception
            throw new DataProcessingException("Failed to process file", e);
        }
    }
    
    static void processOrder(String itemId, int quantity, double price) 
            throws InvalidOrderException, NegativePriceException {
        if (itemId == null || itemId.isEmpty()) {
            throw new InvalidOrderException("Item ID cannot be empty");
        }
        if (quantity <= 0) {
            throw new InvalidOrderException("Quantity must be positive");
        }
        if (price < 0) {
            throw new NegativePriceException("Price cannot be negative", price);
        }
    }
    
    static void configureDatabase(String connectionString) throws DatabaseConfigException {
        if (!connectionString.contains("://")) {
            throw new DatabaseConfigException(
                "Invalid database connection string",
                "Use format: dbtype://host:port/database"
            );
        }
    }
}

// ============ CUSTOM EXCEPTION DEFINITIONS ============

/**
 * CUSTOM CHECKED EXCEPTION: InsufficientFundsException
 * Extends Exception - must be caught or declared
 * Used for banking operations
 */
class InsufficientFundsException extends Exception {
    private double requiredAmount;
    private double availableAmount;
    
    public InsufficientFundsException(String message, double required, double available) {
        super(message);
        this.requiredAmount = required;
        this.availableAmount = available;
    }
    
    public double getRequiredAmount() {
        return requiredAmount;
    }
    
    public double getAvailableAmount() {
        return availableAmount;
    }
    
    public String getDetails() {
        return "Required: " + requiredAmount + ", Available: " + availableAmount;
    }
}

/**
 * CUSTOM CHECKED EXCEPTION: InvalidStudentDataException
 * Used for student validation
 */
class InvalidStudentDataException extends Exception {
    private String fieldName;
    private Object invalidValue;
    
    public InvalidStudentDataException(String fieldName, Object invalidValue) {
        super("Invalid " + fieldName + ": " + invalidValue);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public Object getInvalidValue() {
        return invalidValue;
    }
}

/**
 * CUSTOM UNCHECKED EXCEPTION: InvalidEmailException
 * Extends RuntimeException - optional to catch
 * Used for email validation errors
 */
class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
    
    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * CUSTOM CHECKED EXCEPTION: UserNotFoundException
 * With custom fields for better error information
 */
class UserNotFoundException extends Exception {
    private int searchedId;
    
    public UserNotFoundException(String message, int id) {
        super(message);
        this.searchedId = id;
    }
    
    public int getSearchedId() {
        return searchedId;
    }
}

/**
 * CUSTOM CHECKED EXCEPTION: DataProcessingException
 * Used when processing data from external sources
 */
class DataProcessingException extends Exception {
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * CUSTOM CHECKED EXCEPTION: InvalidOrderException
 * Used for order validation
 */
class InvalidOrderException extends Exception {
    public InvalidOrderException(String message) {
        super(message);
    }
}

/**
 * CUSTOM CHECKED EXCEPTION: NegativePriceException
 * Used when price is negative
 */
class NegativePriceException extends Exception {
    private double price;
    
    public NegativePriceException(String message, double price) {
        super(message);
        this.price = price;
    }
    
    public double getPrice() {
        return price;
    }
}

/**
 * CUSTOM CHECKED EXCEPTION: DatabaseConfigException
 * With recovery suggestion
 */
class DatabaseConfigException extends Exception {
    private String recoverySuggestion;
    
    public DatabaseConfigException(String message, String suggestion) {
        super(message);
        this.recoverySuggestion = suggestion;
    }
    
    public String getRecoverySuggestion() {
        return recoverySuggestion;
    }
}

// ============ HELPER CLASSES ============

/**
 * Simple BankAccount class for demonstration
 */
class BankAccount {
    private String accountNumber;
    private double balance;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient funds for withdrawal",
                amount,
                balance
            );
        }
        balance -= amount;
    }
}

/**
 * Simple Student class for demonstration
 */
class Student {
    private int age;
    private String name;
    
    public Student(int age, String name) throws InvalidStudentDataException {
        if (age < 0 || age > 100) {
            throw new InvalidStudentDataException("age", age);
        }
        if (name == null || name.isEmpty()) {
            throw new InvalidStudentDataException("name", name);
        }
        this.age = age;
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getName() {
        return name;
    }
}

/**
 * Simple User class for demonstration
 */
class User {
    private int id;
    private String name;
    
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}

/**
 * KEY CONCEPTS FOR CUSTOM EXCEPTIONS:
 * 
 * 1. Creating Custom Exceptions:
 *    ✓ Extend Exception (checked) or RuntimeException (unchecked)
 *    ✓ Provide meaningful constructors
 *    ✓ Include context information
 *    ✓ Use clear, descriptive names
 * 
 * 2. Checked vs Unchecked:
 *    - Checked: for recoverable conditions (extends Exception)
 *    - Unchecked: for programming errors (extends RuntimeException)
 * 
 * 3. Best Practices:
 *    ✓ Include error context (what, where, why)
 *    ✓ Provide getter methods for exception details
 *    ✓ Use exception chaining for root cause
 *    ✓ Document when exception is thrown
 *    ✓ Avoid catching generic Exception
 * 
 * 4. Exception Chaining:
 *    - Preserves original exception information
 *    - Use getCause() to access root cause
 *    - Helps with debugging
 * 
 * 5. Custom Fields:
 *    ✓ Store relevant error context
 *    ✓ Provide getters to access details
 *    ✓ Makes exception handling more informative
 */

