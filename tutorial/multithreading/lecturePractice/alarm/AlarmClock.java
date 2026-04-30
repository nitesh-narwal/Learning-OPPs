package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.alarm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlarmClock {

    /**
     * Requirements: multiple clients should be able to set alarm for a particular time
     * I have a list of alarm which re already set nd i will trigger the alarm when the time is up
     * [1,2,3,4,5] event Loop:
     *
     *
     * Shared resource -> List of alarms
     * capacity is fixed to 5alarms
     * clients are multiple threads
     * */

    private final List<Alarm> alarms = new ArrayList<>();
    private final int MAX_ALARM = 5;

    public void pushAlarm(Alarm alarm){
        synchronized (this){
            while(alarms.size() == MAX_ALARM){
                try{
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if(alarm.getTime().isAfter(LocalDateTime.now())){
                alarms.add(alarm);
                notifyAll();
            }
        }
    }

    public void startAlarm(){
        synchronized (this){
            while(alarms.isEmpty()){
                try{
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            try {
                Thread.sleep(1000); // Simulate the time taken to check the alarms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            Alarm alarm = alarms.remove(alarms.size()-1);
            System.out.println("Alarm ringing: " + alarm.getReminder());
            notifyAll();
        }
    }
}
