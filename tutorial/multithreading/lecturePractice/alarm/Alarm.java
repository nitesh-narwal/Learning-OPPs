package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.alarm;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Alarm {

    private LocalDateTime time;

    private String reminder;

    public Alarm(LocalDateTime localDateTime, String s) {
        this.time = localDateTime;
        this.reminder = s;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }


}
