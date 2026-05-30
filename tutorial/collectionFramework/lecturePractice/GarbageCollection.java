package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice;

import java.lang.ref.WeakReference;

public class GarbageCollection {
    // Garbage Collection is the process of automatically freeing up memory by removing objects that are no longer in use.
    // In Java, the garbage collector runs in the background and identifies
    // objects that are no longer reachable (i.e., they cannot be accessed through any reference).
    // Once an object is identified as unreachable,
    // it becomes eligible for garbage collection, and the memory it occupies can be reclaimed.
    static void main(String[] args) {

        // Example:
        String str1 = new String("Hello");
        String str2 = new String("World");

        // At this point, both str1 and str2 are reachable and occupy memory.

        str1 = null; // Now str1 is no longer referencing the "Hello" string object.
        // The "Hello" string object is now eligible for garbage collection.

        System.gc(); // Suggests the JVM to perform garbage collection (not guaranteed to work).

        // Note: Garbage collection in Java is non-deterministic, meaning you cannot predict when it will occur.

        phone p1 = new phone("Samsung", "Galaxy S20");  // Here p1 is called as "Strong Reference".
        // Now what's going on here is that we have created an object of phone class in heap memory
        // and assigned it to reference variable p1.
        // The object is not yet eligible for garbage collection because it is still in use.
        System.out.println(p1);
        p1 = null;
        // now here what we did  we assigned the reference variable p1 to null. Who is pointing to the phone object in heap memory.
        // Now the phone object is no longer referenced by any variable, making it eligible for garbage collection.
      //  System.gc();

        System.out.println("Learning about weak reference...");

        // Now we talk about weak reference.
        // Weak reference is a reference type that does not prevent the garbage collector from reclaiming the referenced object.
        // Weak references are useful when you want to associate an object with a task that needs to be completed later.
        // For example, you might have a background task that needs to be completed after a certain amount of time.
        // In this case, you can use a weak reference to associate the background task with the object that needs to be completed.
        // When the background task is completed, you can remove the weak reference to free up the object.

        WeakReference<phone> weakPhone = new WeakReference<>(new phone("Apple", "iPhone 12"));
        // Here we have created a weak reference to a phone object. The phone object is eligible for garbage collection because it is not strongly referenced by any variable.
        System.out.println(weakPhone.get()); // This will print the phone object if it is still available, or null if it has been garbage collected.
        try {
            Thread.sleep(10000);
        } catch (Exception ignored) {
        }
        System.out.println(weakPhone.get()); // After sleeping for 10 seconds, the garbage collector may have reclaimed the phone object, so this may print null.

    }
}

class phone{
    private String brand;
    private String model;

    public phone(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    @Override
    public String toString() {
        return "phone{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
