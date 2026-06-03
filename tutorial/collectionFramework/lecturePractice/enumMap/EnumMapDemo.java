package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.enumMap;

import java.util.EnumMap;

public class EnumMapDemo {
    // EnumMap is a specialized implementation of the Map interface in Java that is designed to work with enum keys.
    // It is part of the java.util package and was introduced in Java 5.
    // EnumMap provides a more efficient and compact way to store mappings between enum keys and their corresponding values compared to other Map implementations like HashMap or TreeMap.

    // EnumMap internally uses an array to store the values associated with the enum keys, which allows for fast access and retrieval of values based on the ordinal values of the enum constants.

    // EnumMap does not allow null keys or null values, and it maintains the natural order of the enum constants.
    static void main(String[] args) {
        //Array same size as enum size.
        // [_,"GYM",_,_,_,_,_]  -> let's say we put GYM in TUESDAY position.
        // no hashing is required.
        // ordianl/index is used
        // Faster then HashMap
        // Memory efficient.
        // Example usage:
        EnumMap<Days, String> dayDescriptions = new EnumMap<>(Days.class);
        dayDescriptions.put(Days.TUESDAY, "GYM");
        dayDescriptions.put(Days.MONDAY, "Start of the work week");
        dayDescriptions.put(Days.WEDNESDAY, "Midweek");
        dayDescriptions.put(Days.THURSDAY, "Almost there");
        dayDescriptions.put(Days.FRIDAY, "End of the work week");
        dayDescriptions.put(Days.SATURDAY, "Weekend!");
        dayDescriptions.put(Days.SUNDAY, "Weekend!");

        System.out.println(dayDescriptions.get(Days.TUESDAY)); // Output: GYM -> we access data based on the ordinal value of the enum constant.

        System.out.println(dayDescriptions);
    }
}

enum Days{
    SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY
}