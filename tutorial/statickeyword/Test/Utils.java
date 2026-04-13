package me.niteshh.OPPs.tutorial.statickeyword.Test;

public class Utils {
    /** Is class main hum un methods ko rkhe gai jinko hum class ki madat se call krayege...
     * Frequently used methods hum utility class main daal dete hai...*/

    public static int max(int a, int b){
        return a > b ? a : b;
    }

    public static int min(int a, int b){
        return a < b ? a : b;
    }

    public static String TrimAndUpperCase(String str){
        return str.trim().toUpperCase();
    }

    /** final keyword is used to make the variable constant, it cannot be changed after initialization
     * 1. we can initalize the variable in the start
     * 2. we firast create the variable and then we initalize it in a static block */

    /** 1. we can initalize the variable in the start
    private static final double PI = 3.1415 ;
     */

    /** 2. we firast create the variable and then we initalize it in a static block */
    private static final double PI ;

    static{
        PI = 3.1415;
    }

    /** we can get the value of the final variable or set getter method,
     * but b'coz of final we can't set the value of the final variable
     */

}
