package me.niteshh.OPPs.tutorial.exceptionHandling.lecturePractice;


import me.niteshh.OPPs.tutorial.statickeyword.Test.Student;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Test {
    public static void main() throws FileNotFoundException {
        int[] numeratior = {10, 200, 30, 400};
        int[] denominator = {1, 2, 0, 4};

     //   for(int i =0; i < numeratior.length; i++){
          for(int i =0; i < 10; i++){  // this throws us ArrayIndexOutOfBoundException, b'coz we are calling array,
              // more then the array length and to tackle this we use try and catch block here
              try {
                  System.out.println("Result: " + divide(numeratior[i], denominator[i]));
              } catch (ArrayIndexOutOfBoundsException e) {
                  System.out.println("Array index out of bounds: " + e.getMessage());
              } catch (Exception e) {
                  System.out.println("An error occurred: " + e.getMessage());
              }
        }

          /** Learning about Stack Trace: so stack trace is a detailed result or blueprints of methords class
           *  For understanding how and where this error and exception occurs */
          // level1();   so this lead us to the index out of bound exception
          // and we can see the stack trace for that and we can understand where this error occurs
        try{
            level1();
        } catch (Exception e) {
            e.setStackTrace(new StackTraceElement[0]); // Clear stack trace for cleaner output
            System.out.println("An error occurred: " + e.getMessage());
        }

        /** Now Learning about UNCHECKED Exception
         * These are the exception which are never thrown during compile time or by compiler
         * */
//        Student student = null;
//        student.setId(12345);   // this throw us NullPointerException
        // because we are trying to access the method of null object and this is unchecked exception

        /** Learning about CHECKED Exception...
         * That pop up during compile time we can handele it via try and catch block*/
        try {
            FileReader fileReader = new FileReader("non_existent_file.txt"); // this throws us FileNotFoundException
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            throw new RuntimeException(e);
        }
        /** Either we can do this or we can add throws exception in the caller method
         *  So that it would be the responsiblity of a caller method to remind,
         *  that we have to handle this error */
        fileErrorLevel2();


    }

    static int divide(int n , int d){
        try {
            /** Here the element 3 in numerator is not divisible by zero
             *  for that we use exception handling
             *  this type of exception comes under ArthematicException
             *  According to hierarchy
             *  We can use Exception instead for generalization but we have to define
             *  It in the end of the catch block otherwise we got an error */
            int result = n / d;
            return result;
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero: " + e.getMessage());
            return -1; // Return a default value or handle as needed
        } catch ( Exception e){
            System.out.println("Cannot divide by zero: " + e.getMessage());
            return -1; // Return a default value or handle as needed
        }
    }

    static void level3(){
        int[] arr = {9,2,5,3};
        arr[7] = 10;
    }

    static void level2(){
        level3();
    }

    static void level1(){
        level2();
    }

    public static void fileErrorLevel2() throws FileNotFoundException {
        fileErrorLevel3();
    }

    public static void fileErrorLevel3() throws FileNotFoundException {
        FileReader fileReader = new FileReader("Random_chutiya.txt");
    }
}
