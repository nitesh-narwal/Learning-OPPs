package me.niteshh.OPPs.tutorial.innerClasses.LecturePractice;

public class Computer {
    /** Here we are learning about Static Inner Class*/

    private String brand;
    private String model;

    private OperatingSystem os;

    public OperatingSystem getOs() {
        return os;
    }

    static class USB{
        private String type;

        public USB(String type) {
            this.type = type;
        }

        public void displayInfo(){
            System.out.println("USB type: " + type);
        }
    }

    public Computer(String brand, String model, String os ) {
        this.brand = brand;
        this.model = model;
        this.os =  new OperatingSystem(os);

    }

    class OperatingSystem{
        private String osName;

        public OperatingSystem(String osName) {
            this.osName = osName;
        }

        public void displayInfo(){
            System.out.println("Computer model: " + model + ", os: " + osName);
        }
    }

}
