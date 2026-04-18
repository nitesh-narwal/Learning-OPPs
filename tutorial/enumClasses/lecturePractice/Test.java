package me.niteshh.OPPs.tutorial.enumClasses.lecturePractice;

import java.awt.*;

import static me.niteshh.OPPs.tutorial.enumClasses.lecturePractice.Day.SUNDAY;


public class Test {

    /** Enumeration( enum ) is a special data type that: allows you to define a set of named constants.
     * and Enumeration means listing things
     *
     *
     */

    static void main() {
        /** Think we need a String again and again... so printing it continuously by
         * System.out.println() is not a good idea. and a very haptic task,
         * And may be we would do mistake in the future,
         *
         * instead we can create a interface where we write and then use it again
         * */

        /*
        System.out.println("SUNDAY");
        System.out.println("MONDAY");
        System.out.println("TUESDAY");
        System.out.println("WEDNESDAY");
        System.out.println("THURSDAY");
        System.out.println("FRIDAY");
        System.out.println("SATURDAY");

         */

        // Instrad we can use this

        System.out.println(DayClass.Monday);
        System.out.println(DayClass.Tuesday);
        System.out.println(DayClass.Wednesday);
        System.out.println(DayClass.Thursday);
        System.out.println(DayClass.Friday);
        System.out.println(DayClass.Saturday);
        System.out.println(DayClass.Sunday);
        System.out.println(DayClass.Monday);
        System.out.println(DayClass.Sunday);
        System.out.println("");

        /** Now even better we can use the enum class*/

        System.out.println(SUNDAY);
        System.out.println(Day.MONDAY);
        System.out.println(Day.TUESDAY);
        System.out.println(Day.WEDNESDAY);
        System.out.println(Day.THURSDAY);
        System.out.println(Day.FRIDAY);
        System.out.println(Day.SATURDAY);

        Day day = SUNDAY; // Day is an enum class
        System.out.println(day);


        /** Wanted to print enum form string
         * then use Day.valueOf(dayString)*/

        Day day1 = Day.valueOf("SUNDAY");
        System.out.println(day1);

        System.out.println("<----->");

        /** It checks the string and if it matches it returns the enum value*/
        Day day2 = Day.valueOf("MONDAY");
        System.out.println(day2);

        System.out.println("<--Now we can use the values() method--->");

        Day[] values = Day.values();
        for (Day d : values) {
            System.out.println(d);
        }

        Day monday = Day.MONDAY;
        monday.displayDay();

        /** But, this will not work when we call very first time or call the constructor
         * in the very beginning of the program
         * so we see the result in the beginning , so their is no use of this constructor
         * for that puepose... */
        Day lowerConstructorTester = Day.MONDAY;

        System.out.println("This is for testing the field: " + lowerConstructorTester.getLower());

        System.out.println("This is for testing the time field: " + lowerConstructorTester.getTime());

        /** we can also add switch case statements to check the enum value*/
        Day day3 = Day.MONDAY;
        switch (day3) {
            case SUNDAY:
                System.out.println("Sunday");
                break;
            case MONDAY:
                System.out.println("Monday");
                break;
            case TUESDAY:
                System.out.println("Tuesday");
                break;
        }

        /** we can also add new switch case statements in java 14 and above*/
        newSwitchCase newCase = newSwitchCase.MONDAY;
        String result = switch (newCase) {
            case MONDAY -> "cool it's monday";
            case TUESDAY -> "cool it's tuesday";
            case WEDNESDAY -> "cool it's wednesday";
            case THURSDAY -> "cool it's thursday";
            default -> "weekend";
        };
        System.out.println(result);


        /** now we have create a new enum class called Months so we can use it in the main method*/
        Months months = Months.MAY;
        System.out.println("My birthday comes in " + months);
        System.out.println(months.name());
        System.out.println(months.ordinal());

    }

    /** Like methods we can also create enums in this class
     *  and we don't need to make them static because they are inherently static
     *  and they are attached to the class not to the instance(object) of the class*/

    public enum Months {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }


}
