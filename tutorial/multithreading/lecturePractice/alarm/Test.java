package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.alarm;

import java.time.LocalDateTime;

public class Test {
    static void main(String[] args) {
        AlarmClock alarmClock = new AlarmClock();
        for(int i = 0; i <10; i++){
            final int index = i;
            new Thread(() -> {
                Alarm alarm = new Alarm(LocalDateTime.now().plusSeconds(5), "Alarm " + index);
                alarmClock.pushAlarm(alarm);
            }).start();
        }
        try{
            while(true) {
                alarmClock.startAlarm();
                Thread.sleep(1000); // Check for alarms every second
            }
        } catch (Exception e) {
            System.out.println("Alarm clock interrupted: " + e.getMessage());
        }
    }
}
