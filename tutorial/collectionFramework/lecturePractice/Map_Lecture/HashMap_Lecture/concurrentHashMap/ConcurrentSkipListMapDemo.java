package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Map_Lecture.HashMap_Lecture.concurrentHashMap;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapDemo {
    static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("D", 4);
        map.put("E", 5);

        System.out.println(map);
        System.out.println(map.get("A"));
        System.out.println(map.get("F"));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        System.out.println(map.containsKey("A"));
        System.out.println(map.containsValue(1));
        System.out.println(map.remove("A"));
        System.out.println(map);
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        System.out.println(map.remove("F"));
        System.out.println(map);

    }
}
