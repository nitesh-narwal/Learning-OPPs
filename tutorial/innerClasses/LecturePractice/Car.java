package me.niteshh.OPPs.tutorial.innerClasses.LecturePractice;


public class Car {

    /** Their are 4 types of Inner Classed
     * 1. Member Class
     * */

    private String model;
    private boolean isEnginOn;

    public Car(String model){
        this.model = model;
        this.isEnginOn = false;
    }
    /** here when i want to call this engin class with the help of Car object...
     * And this inner class is associated to the instance of the outher class
     */
     class Engin{

        void start(){
            if(!isEnginOn){
                isEnginOn = true;
                System.out.println(model + "Engin started");
            } else {
                System.out.println(model + "Engin is already on");
            }
        }

        void stop(){
            if(isEnginOn){
                isEnginOn = false;
                System.out.println(model + "Engin stopped");
            }else {
                System.out.println(model + "Engin is already off");
            }
        }
    }

    /** Done for the EnginWithoutInnerClass*/
    public String getModel() {
        return model;
    }

    public void setEnginOn(boolean enginOn) {
        isEnginOn = enginOn;
    }

    public boolean isEnginOn() {
        return isEnginOn;
    }

}
