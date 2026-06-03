package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Map_Lecture.map.weakhashMap;

import java.util.WeakHashMap;

public class WeakHashMapDemo {
    /*
    * WeakHashMap is a type of HashMap that holds weak references to its keys and values.
    * When a key or value is no longer in use, the garbage collector can reclaim the memory associated with it.
    * This allows the HashMap to store more entries without running out of memory.
    * It is useful for caching data that is not expected to be accessed for long periods of time.
    * WeakHashMap is not thread-safe.
    * WeakHashMap is not synchronized.
    * WeakHashMap does not support null keys or values.
    */
     static void main(String[] args) {
            WeakHashMap<Image, String> weakHashMap = new WeakHashMap<>();

            Image image1 = new Image("Image1");
            Image image2 = new Image("Image2");
            Image image3 = new Image("Image3");

            weakHashMap.put(image1, "This is image 1");
            weakHashMap.put(image2, "This is image 2");
            weakHashMap.put(image3, "This is image 3");

            System.out.println("Before GC: " + weakHashMap);

            // Remove strong references to the keys
            image1 = null;
            image2 = null;

            // Suggest the JVM to run the garbage collector
            System.gc();

            // Wait for a moment to allow GC to complete
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("After GC: " + weakHashMap);

    }
}

class Image{
    private String name;

    public Image(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                '}';
    }
}
