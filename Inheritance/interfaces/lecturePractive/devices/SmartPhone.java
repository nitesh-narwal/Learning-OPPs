package me.niteshh.OPPs.Inheritance.interfaces.lecturePractive.devices;



public class SmartPhone implements Phone, MusicPlayer, Camera {


    @Override
    public void takePicture() {
        System.out.println("Taking a picture");
    }

    @Override
    public void PlayMusic() {
        System.out.println("Playing music...");
    }

    @Override
    public void StopMusic() {
        System.out.println("Stopping music...");
    }

    @Override
    public void call(String number) {
        System.out.println("Calling " + number);
    }

    @Override
    public void sendSMS(String number, String message) {
        System.out.println("Sending SMS to " + number + ": " + message);
    }
}
