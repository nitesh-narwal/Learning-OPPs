package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.alarm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

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
   // private final int MAX_ALARM = 5;

    private Semaphore vacantSeats = new Semaphore(5);
    private Semaphore filledSeats = new Semaphore(0);

    public void pushAlarm(Alarm alarm){
        synchronized (this){
//            while(alarms.size() == MAX_ALARM){
//                try{
//                    wait();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    return;
//                }
//            }
        try {
                vacantSeats.acquire();  // vacantSeat - 1
                if (alarm.getTime().isAfter(LocalDateTime.now())) {
                    alarms.add(alarm);
                    System.out.println("Alarm set: " + alarm.getReminder() + " at " + alarm.getTime());
                    filledSeats.release();  // filledSeat + 1
                }
        } catch (Exception e) {

            }
        }
    }

    public void startAlarm(){
        synchronized (this){
//            while(alarms.isEmpty()){
//                try{
//                    wait();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                    return;
//                }
//            }

            try {
                filledSeats.acquire(); // filledSeat - 1 < 0 wait Forever, proceed
                Alarm alarm = alarms.remove(alarms.size() - 1);
                System.out.println("Alarm ringing: " + alarm.getReminder());
                vacantSeats.release(); // vacantSeat + 1
            } catch (Exception e) {

            }
        }
    }
}
