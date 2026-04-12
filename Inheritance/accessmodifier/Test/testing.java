package me.niteshh.OPPs.Inheritance.accessmodifier.Test;

import me.niteshh.OPPs.Inheritance.accessmodifier.school.Student;

public class testing {
    static void main(String[] args) {
//        Student student = new Student();
//        System.out.println(student.sayHello());
        /**
         * because the Student class have private constructor
         * we can't create object of Student class
         * so we can't call the method sayHello()
         * but we can access methods with static keyword
         * and call them at class level*/

        Student.sayBye();   /** --->  this will call the method sayBye() at class level
                                   without creating object of Student class */ 

        School.getInstance();  /** ---> this will create only one object of School class and return it
                                    b'coz only one time this instance variable remain null and after,
                                    And after that we put the "new School()" in instance variable */

    }
}
