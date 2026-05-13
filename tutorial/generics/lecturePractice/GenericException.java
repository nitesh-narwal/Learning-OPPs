package me.niteshh.OPPs.tutorial.generics.lecturePractice;

/**
 * GENERICS WITH EXCEPTIONS - DEEP DIVE EXPLANATION
 * ================================================
 *
 * WHAT IS AN EXCEPTION?
 * An exception is an abnormal event that occurs during program execution, disrupting the normal flow.
 * Examples: NullPointerException, ArithmeticException, IOException, etc.
 *
 * KEY CONCEPTS:
 * 1. Exceptions are classes that inherit from Throwable
 * 2. We can catch and handle them using try-catch blocks
 * 3. We can create custom exceptions by extending Exception class
 *
 * THE PROBLEM: CAN WE CREATE GENERIC EXCEPTIONS?
 * Initially, you might think: "Why not make exception classes generic like other classes?"
 * Answer: JAVA DOESN'T ALLOW GENERIC EXCEPTIONS due to Type Erasure and runtime constraints
 *
 * WHY CAN'T WE HAVE GENERIC EXCEPTIONS? THE ROOT CAUSE:
 * =====================================================
 * 1. TYPE ERASURE: When code is compiled, generic type information is removed
 *    - At compile time: Box<String> exists
 *    - At runtime: Only Box exists (T is replaced with Object)
 *
 * 2. BUT EXCEPTIONS ARE RUNTIME ENTITIES: Exception handling happens at runtime
 *    - When JVM runs "catch (MyException<String> e)", it can't work because <String> is erased
 *    - JVM would see "catch (MyException e)" for ALL MyException instances
 *    - It can't distinguish between MyException<String> and MyException<Integer>
 *
 * 3. JAVA LANGUAGE RESTRICTION: Explicitly forbidden in Java Language Specification
 *    - You CANNOT write: public class MyException<T> extends Exception { }
 *    - Compiler will throw an error
 *
 * PRACTICAL EXAMPLE OF WHY IT FAILS:
 * ==================================
 * If this was allowed (but it's NOT):
 *
 *   public class BadException<T> extends Exception { }
 *   
 *   try {
 *       throw new BadException<String>();
 *   } catch (BadException<Integer> e) {  // Would catch String too! WRONG!
 *       // This block would unexpectedly catch both String and Integer
 *   }
 *
 * This is dangerous! The generics promise type safety is BROKEN at runtime.
 *
 * SOLUTION: Use Generic CONSTRUCTORS or methods instead (shown below)
 */

public class GenericException {

    /**
     * INCORRECT APPROACH: WHY THIS DOESN'T WORK
     * ==========================================
     * 
     * The following code looks logical but WILL NOT COMPILE:
     * 
     *     public class GenericException<T> extends Exception { ... }
     *
     * WHY IT FAILS:
     * 1. Java Language Specification forbids generic exception classes
     * 2. Compiler error: "Generic class may not extend java.lang.Throwable"
     * 3. Even if it compiled, catching would be ambiguous (Type Erasure problem)
     * 
     * COMMENTED CODE BELOW (This is INVALID JAVA):
     */

//    /* ❌ INVALID - DO NOT USE THIS PATTERN ❌
//    
//    public class MyException<T> extends Exception {
//        // This class declaration itself is ILLEGAL in Java
//        // Compiler will reject it at compile time
//
//        private T data;
//
//        public MyException(T data) {
//            this.data = data;
//        }
//
//        public T getData() {
//            return data;
//        }
//
//        public void setData(T data) {
//            this.data = data;
//        }
//
//        @Override
//        public String toString() {
//            return "MyException{" + "data=" + data + '}';
//        }
//    }
//    */

    /**
     * THE CORRECT SOLUTION: GENERIC CONSTRUCTOR APPROACH
     * ====================================================
     * 
     * Instead of making the class generic, we make the CONSTRUCTOR generic.
     * This workaround leverages generic methods which ARE allowed on exception classes.
     * 
     * WHY THIS WORKS:
     * 1. The exception class itself is NOT generic (passes Java restrictions)
     * 2. The constructor accepts ANY type T (flexibility like generics)
     * 3. We extract useful information from the generic parameter at construction time
     * 4. No catching ambiguity at runtime (we catch non-generic MyException)
     * 
     * FLOW:
     * 1. User passes any object to constructor: new MyException<String>(value)
     * 2. Generic constructor receives it as type T
     * 3. Constructor extracts: value, class name, toString representation
     * 4. This info is stored in the exception message (String is stored at compile time)
     * 5. At runtime, we just have a regular MyException with the message stored
     * 6. No type information needed to catch it
     */

    static class MyException extends Exception {

        /**
         * GENERIC CONSTRUCTOR (Generic method inside exception class)
         * 
         * public <T> MyException(T value)
         * 
         * EXPLANATION:
         * - "<T>" means this constructor accepts a generic type T
         * - T can be String, Integer, List, or ANY type
         * - The generic parameter is resolved at COMPILE TIME
         * - By the time exception is thrown, T is completely gone
         * 
         * PARAMETER: value -> the actual data that caused the exception
         * 
         * WHAT WE DO:
         * 1. Extract the value's toString() representation
         * 2. Extract the actual class name using getClass().getSimpleName()
         * 3. Create a meaningful error message with both
         * 4. Pass this String message to Exception parent class
         * 5. Exception stores this String (String survives type erasure!)
         */

        public <T> MyException(T value) {
            // super() calls the parent Exception class constructor
            // We pass a String message that contains information about the generic parameter
            // This message IS preserved at runtime, even though T is erased
            
            super("Exception related to value: " + 
                  value.toString() + 
                  " of type: " + 
                  value.getClass().getSimpleName());
            
            // WHAT HAPPENED HERE:
            // - Input: T value (e.g., String "Hello")
            // - At compile time: <T> resolved to String type
            // - Extracted: "Hello".toString() = "Hello"
            // - Extracted: "Hello".getClass().getSimpleName() = "String"
            // - Created message: "Exception related to value: Hello of type: String"
            // - This message (plain String) is passed to parent Exception
            // - At runtime: Only the message survives, T is completely erased
        }
    }

    /**
     * BEST PRACTICES FOR WORKING WITH EXCEPTIONS AND GENERICS
     * ==========================================================
     * 
     * 1. ✅ DO: Use generic constructors/methods in exception classes
     * 2. ✅ DO: Extract and preserve essential information as String message
     * 3. ✅ DO: Store the data in non-generic fields for runtime use
     * 4. ❌ DON'T: Try to make exception class itself generic
     * 5. ❌ DON'T: Rely on generic type info at exception catching time
     * 6. ✅ DO: Use meaningful error messages that include type information
     * 7. ✅ DO: Consider using wrapper types in exception for storing generic data
     * 
     * COMMON MISTAKES TO AVOID:
     * A) Trying: catch (MyException<String> e) -> COMPILE ERROR
     * B) Trying: class MyException<T> extends Exception -> COMPILE ERROR
     * C) Storing only data without type info -> Confusing error messages
     * D) Assuming type info is available at runtime -> It's NOT (type erasure)
     */

    static void main(String[] args) {
        
        // ============ DEMONSTRATION 1: Integer Exception ============
        try {
            // SCENARIO: Method fails while processing an Integer
            // We throw MyException with an Integer value (123)
            
            int userAge = 123;
            throw new MyException(userAge);
            
            // WHAT HAPPENS:
            // 1. Compiler sees: new MyException<Integer>(123)
            // 2. <T> is resolved to Integer at compile time
            // 3. Constructor runs: userAge.toString() = "123"
            // 4. Constructor runs: userAge.getClass().getSimpleName() = "Integer"
            // 5. Message created: "Exception related to value: 123 of type: Integer"
            // 6. Exception thrown with this message
            
        } catch (MyException e) {
            // CATCHING: We catch non-generic MyException (type is erased)
            // The message contains all the info we need about what went wrong
            System.out.println("DEMO 1 - Integer Exception:");
            System.out.println(e.getMessage());
            // OUTPUT: Exception related to value: 123 of type: Integer
        }

        System.out.println(); // Blank line for readability

        // ============ DEMONSTRATION 2: String Exception ============
        try {
            // SCENARIO: Method fails while processing a String
            String data = "InvalidUser";
            throw new MyException(data);
            
            // WHAT HAPPENS:
            // 1. Compiler sees: new MyException<String>("InvalidUser")
            // 2. <T> is resolved to String at compile time
            // 3. Constructor runs: "InvalidUser".toString() = "InvalidUser"
            // 4. Constructor runs: "InvalidUser".getClass().getSimpleName() = "String"
            // 5. Message created: "Exception related to value: InvalidUser of type: String"
            // 6. Exception thrown with this message
            
        } catch (MyException e) {
            // Same catching mechanism, but different type info in message
            System.out.println("DEMO 2 - String Exception:");
            System.out.println(e.getMessage());
            // OUTPUT: Exception related to value: InvalidUser of type: String
        }

        System.out.println(); // Blank line for readability

        // ============ DEMONSTRATION 3: Custom Object Exception ============
        try {
            // SCENARIO: Method fails while processing a custom object
            Person person = new Person("Alice", 25);
            throw new MyException(person);
            
            // WHAT HAPPENS:
            // 1. Compiler sees: new MyException<Person>(person)
            // 2. <T> is resolved to Person at compile time
            // 3. Constructor runs: person.toString() (calls Person's toString method)
            // 4. Constructor runs: person.getClass().getSimpleName() = "Person"
            // 5. Message includes custom toString from Person class
            
        } catch (MyException e) {
            System.out.println("DEMO 3 - Custom Object Exception:");
            System.out.println(e.getMessage());
            // OUTPUT: Exception related to value: [custom Person toString] of type: Person
        }

        System.out.println(); // Blank line for readability

        // ============ KEY INSIGHT: TYPE ERASURE IN ACTION ============
        // All three exceptions above were caught by same: catch (MyException e)
        // This works because <T> is erased at runtime
        // All are just "MyException" at runtime - no generic type info
        // But we preserved the important info in the message!
    }

    /**
     * HELPER CLASS: Simple Person class for demonstration
     */
    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
        }
    }

}
