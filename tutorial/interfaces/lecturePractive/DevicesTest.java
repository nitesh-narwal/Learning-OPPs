package me.niteshh.OPPs.tutorial.interfaces.lecturePractive;

import me.niteshh.OPPs.tutorial.interfaces.lecturePractive.devices.Camera;
import me.niteshh.OPPs.tutorial.interfaces.lecturePractive.devices.SmartPhone;

public class DevicesTest {

    public static void main(String[] args) {
        SmartPhone smartPhone = new SmartPhone();
        smartPhone.call("1234567890");
        smartPhone.sendSMS("1234567890", "Hello");
        smartPhone.takePicture();
        smartPhone.PlayMusic();
        smartPhone.StopMusic();
        String[] Sleep = new String[0];
        Camera.main( Sleep );
    }
}
