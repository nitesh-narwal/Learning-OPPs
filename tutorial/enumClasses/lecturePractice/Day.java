package me.niteshh.OPPs.tutorial.enumClasses.lecturePractice;

public enum Day {

   /** Now keep in mind the very first thing we put in enum body are
    * enum constants,
    *
    *  private String lower;
    *
    *  not the above line, but the below the constants
    * */

   /** when we write Monday("MONDAY")
    * it shows us error that we are providing 1 argument but we are expecting 0
    * beecause the default constructor of this class that will run automatically
    * but we are giving a parameter to SUNDAY but their is no constructor for that
    * so we have to create a constructor for that
    *
    *
    * we can also add more parameters in them like time and
    * then we have to change the constructor accordingly
    * have to add field and the getter method
    * */
    MONDAY("MONDAY", "Evening"),
    TUESDAY("TUESDAY", "Afternoon"),
    WEDNESDAY("WEDNESDAY", "Morning"),
    THURSDAY("THURSDAY", "Evening"),
    FRIDAY("FRIDAY", "Midnight"),
    SATURDAY("SATURDAY", "Morning"),
    SUNDAY("SUNDAY", "Afternoon");


    /** we can also add parameters to the constructor like the time in it */
    private Day(String lower, String time) {
        System.out.println("Constructor called for : " + this.name());
        this.lower = lower;
        this.time = time;
    }

    private String lower; // we have created a field
    private String time;

    public String getLower() {
        return lower;
    }

    public String getTime() {
        return time;
    }

    /** we can a method in it because eventually this enum class will be converted into a class*/
    public void displayDay(){
        System.out.println("Today is " + this.name());
    }
}
