package me.niteshh.OPPs.tutorial.innerClasses.LecturePractice;

public class EnginWithoutInnerClass {
    /**
     * What happen if i don't create inner class Engin then i have to do some
     * extra work
     * Because i'm not able to access the private variables or isEnginOn,
     * likre Inner class which i can use directly
     * So i have to define them throught different way
     */

    private Car car;

    /**
     * 1st -->  i have to create this variable to access the car object
     */

    public EnginWithoutInnerClass(Car car) {
        this.car = car; /** 2nd -->  i have to initialize this variable in the constructor */
    }

    public void start() {
        if (!car.isEnginOn()) { /** 3rd -->  i have to access the isEnginOn variable through car object */
            car.setEnginOn(true); /** 4th -->  i have to change the isEnginOn variable through car object */
            System.out.println(car.getModel() + " Engin started"); /** 5th -->  i have to access the model variable through car object */
        } else {
            System.out.println(car.getModel() + " Engin is already on");
        }
    }

    public void stop() {
        if (car.isEnginOn()) {
            car.setEnginOn(false);
            System.out.println(car.getModel() + " Engin stopped");
        } else {
            System.out.println(car.getModel() + " Engin is already off");
        }
    }
}