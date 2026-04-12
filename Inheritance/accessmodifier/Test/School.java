package me.niteshh.OPPs.Inheritance.accessmodifier.Test;

public class School {   /** There are two types of access modifier for class
                             * 1. public
                             * 2. default(package) means class can only be accessed inside the package
                             *  class School{...}  that's how we create a default class 
                             * */

    /**Agr main cahta hu ki puri School class main bas ek hi object bane*/ 

    private static School instance;  /**b'coz of static keyword we can access this instance from this School class only
                                            */
    private School(){
        /** Now because of private constructor we can't create object of this class
         */
    }

    /** Now to make sure that only one object of this class is created
     *  For that we use this
     *  and Object toh create nhi hoga to isko static banana pdega */

    /** This is called "SINGLETON PATTERN" */
    public static School getInstance(){
        if(instance == null){
            instance = new School();
        }
        return instance ;
    }
}
