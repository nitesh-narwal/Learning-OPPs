package me.niteshh.OPPs.tutorial.exceptionHandling.inDepth;

/**
 * STEP 1: BASICS OF EXCEPTIONS
 * 
 * What is an Exception?
 * - An exception is an abnormal event or error that occurs during program execution
 * - It disrupts the normal flow of the program
 * - Java provides a mechanism to handle these exceptions gracefully
 * 
 * Why handle exceptions?
 * - Without exception handling, program crashes when an error occurs
 * - With exception handling, we can recover from errors and continue execution
 * - Provides better user experience and program stability
 * 
 * Exception Hierarchy in Java:
 * 
 *                          Throwable
 *                         /        \
 *                      Error      Exception
 *                      /            /        \
 *                   (JVM)      Checked    Unchecked
 *                            Exception    Exception
 *                                         (Runtime)
 * 
 * Throwable: Parent class of all errors and exceptions
 * Error: Serious problems that shouldn't be handled (e.g., OutOfMemoryError)
 * Exception: Problems that can be handled by the application
 * 
 * Two types of Exceptions:
 * 1. Checked Exceptions: Must be caught or declared (compile-time checking)
 *    - IOException, SQLException, FileNotFoundException
 * 
 * 2. Unchecked Exceptions: Don't need to be caught (runtime errors)
 *    - NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException
 */

public class Step1_BasicsOfExceptions {

    public static void main(String[] args) {
        System.out.println("=== EXCEPTION BASICS DEMONSTRATION ===\n");
        
        // Example 1: ArithmeticException (Unchecked Exception)
        System.out.println("Example 1: ArithmeticException");
        try {
            int result = 10 / 0; // This will throw ArithmeticException
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Exception Type: " + e.getClass().getName());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Example 2: ArrayIndexOutOfBoundsException (Unchecked Exception)
        System.out.println("Example 2: ArrayIndexOutOfBoundsException");
        try {
            int[] numbers = {1, 2, 3};
            System.out.println("Accessing index 5: " + numbers[5]); // Out of bounds
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Array accessed at invalid index");
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Example 3: NullPointerException (Unchecked Exception)
        System.out.println("Example 3: NullPointerException");
        try {
            String name = null;
            System.out.println("Length: " + name.length()); // null object reference
        } catch (NullPointerException e) {
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Tried to call method on null object");
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Example 4: NumberFormatException (Unchecked Exception)
        System.out.println("Example 4: NumberFormatException");
        try {
            String numberString = "abc123";
            int number = Integer.parseInt(numberString); // Cannot convert "abc123" to int
            System.out.println("Number: " + number);
        } catch (NumberFormatException e) {
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Invalid string format for number conversion");
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Example 5: Multiple exceptions in one block
        System.out.println("Example 5: Handling Multiple Exceptions");
        try {
            String[] fruits = {"Apple", "Banana", "Orange"};
            String fruit = fruits[2]; // Valid index
            int length = fruit.length();
            System.out.println("Fruit: " + fruit + ", Length: " + length);
            
            // This will cause an exception
            int value = Integer.parseInt(fruit); // NumberFormatException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array Index Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Number Format Error: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Example 6: Without exception handling (will crash if uncommented)
        /*
        System.out.println("Example 6: Without Exception Handling");
        int result = 100 / 0; // Program will crash here - ArithmeticException
        System.out.println("This line will never execute");
        */
        System.out.println("Example 6: Avoided intentional crash (see comments)");
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        System.out.println("✓ All examples executed successfully!");
        System.out.println("Program continues even after exceptions due to proper handling");
    }
}

/**
 * KEY CONCEPTS TO REMEMBER:
 * 
 * 1. Exception: An unwanted event that disrupts normal program flow
 * 
 * 2. try block: Contains code that might throw an exception
 * 
 * 3. catch block: Handles the exception if it occurs
 *    - Syntax: catch (ExceptionType variableName)
 *    - Can have multiple catch blocks
 * 
 * 4. Checked vs Unchecked:
 *    - Checked: Must be handled or declared in method signature
 *    - Unchecked: Optional to handle (but good practice to do so)
 * 
 * 5. Exception Methods:
 *    - getMessage(): Returns error message
 *    - printStackTrace(): Prints stack trace
 *    - getClass().getName(): Returns exception class name
 *    - toString(): Returns string representation
 * 
 * 6. Benefits of Exception Handling:
 *    ✓ Program doesn't crash unexpectedly
 *    ✓ Better error reporting and logging
 *    ✓ Graceful recovery from errors
 *    ✓ Improved user experience
 */

