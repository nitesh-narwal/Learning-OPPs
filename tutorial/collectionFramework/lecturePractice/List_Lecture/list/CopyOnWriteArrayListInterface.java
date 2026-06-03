package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.List_Lecture.list;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListInterface {
    static void main(String[] args) {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        /* "Copy On Write" means when ever a write operation
        * like adding or removing an element
        * Instead of modifying existing List
        * a new copy of the list is created, and modification is applied to that copy
        * This ensures that other threads reading the list while it's being modified are unaffected.
        *
        * Read Operations: Fast and direct, since they are happening on a stable list without interference from modification.
        * Write Operation: A new copy of the list is created for every modification.
        *                   The reference to the list is then updated so that subsequent reads use this new list.
        *
        *
        * Use this only when Read Intensive Operations are required.
        *  */

        // Let's check what happen when we do read and write together on the same list.
        List<String> shiooingList = new ArrayList<>();
        shiooingList.add("Bread");
        shiooingList.add("Milk");
        shiooingList.add("Eggs");
        shiooingList.add("Flour");
        shiooingList.add("Butter");

//        for(String s : shiooingList) { // Here we require a stable list, so we cannot modify the list while iterating over it.
//            System.out.println(s);
//            if(s.equals("Butter")) {
//                shiooingList.add("Sugar");  // This will throw ConcurrentModificationException
//            }
//        }
//        System.out.println("After modification: " + shiooingList);
         /* In the above code we are trying to modify the list while iterating over it, which will throw ConcurrentModificationException.
         * Because when we try to add "Sugar" to the list while iterating over it, it will cause a structural modification to the list,
         * which is not allowed while iterating over it.
         * because while iterating over the list required a stable list, but after modification the list is not stable anymore.
         *
         * Now let's see how CopyOnWriteArrayList handle this situation.
         * */

        CopyOnWriteArrayList<String> copyOnWriteArrayList1 = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList1.add("Bread");
        copyOnWriteArrayList1.add("Milk");
        copyOnWriteArrayList1.add("Eggs");
        copyOnWriteArrayList1.add("Flour");
        copyOnWriteArrayList1.add("Butter");
        for(String s : copyOnWriteArrayList1) {
            System.out.println(s);
            if(s.equals("Butter")) {
                copyOnWriteArrayList1.add("Sugar");
            }
        }
        System.out.println("After modification: " + copyOnWriteArrayList1);
         /* In the above code we are trying to modify the list while iterating over it, but it will not throw ConcurrentModificationException.
         * Because when we try to add "Sugar" to the list while iterating over it, it will create a new copy of the list and add "Sugar" to that copy,
         * and the reference to the list will be updated to point to this new copy. So, the original list that we are iterating over remains unchanged and stable,
         * allowing us to continue iterating without any issues.
         *
         * However, it's important to note that this approach can lead to performance issues if there are frequent modifications, as each modification creates a new copy of the list.
         * Therefore, CopyOnWriteArrayList is best suited for scenarios where read operations are much more frequent than write operations.
         * */


    }
}

class LearningThroughThreads{
    static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("Item1");
        list.add("Item2");
        list.add("Item3");
        list.add("Item4");
        list.add("Item5");
        list.add("Item6");
        list.add("Item7");
        list.add("Item8");
        list.add("Item9");
        list.add("Item10");

        Thread readingThread = new Thread(() -> {
            try{
                while(true){
                    // Iteration through the list
                    for(String read : list){
                        System.out.println("Reading: " + read);
                        Thread.sleep(100); // Simulate time taken to read an item
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("Reading thread encountered an error: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Reading thread was interrupted.");
            }
        });

        Thread writingThread = new Thread(() -> {
            try {
                Thread.sleep(500); // Simulate some delay before writing
                list.add("NewItem");
                System.out.println("Added NewItem to the list.");

                Thread.sleep(500);
                list.remove("Item1");
                System.out.println("Removed Item1 from the list.");
            } catch (InterruptedException e) {
                System.out.println("Writing thread was interrupted.");
            }
        });

        readingThread.start();
        writingThread.start();

         /* In this code, we have a reading thread that continuously iterates through the list and prints its contents,
         * simulating a read operation.
         * We also have a writing thread that adds a new item to the list after a short delay.
         * Since we are using an ArrayList, which is not thread-safe, this can lead to a ConcurrentModificationException when the reading thread tries to iterate over the list while the writing thread is modifying it.
         * This is because the reading thread expects a stable list, but the writing thread is changing the structure of the list by adding a new item.
         * To avoid this issue, we could use a thread-safe collection like CopyOnWriteArrayList or synchronize access to the list.
         * */
    }
}