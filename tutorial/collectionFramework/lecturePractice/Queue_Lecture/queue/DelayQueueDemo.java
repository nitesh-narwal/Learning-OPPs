package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.Queue_Lecture.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo {
    static void main(String[] args) throws InterruptedException {
        // Thread safe unbounded blocking queue
        // Elements can only be taken form this queue when their delay has expired.
        // Useful for scheduling tasks to be executed after a certain delay.
        // internally uses PriorityQueue.

        BlockingQueue<DelayedTask> dq = new DelayQueue<>();
        dq.put(new DelayedTask("Task 1", 5, TimeUnit.SECONDS));
        dq.put(new DelayedTask("Task 2", 3, TimeUnit.SECONDS));
        dq.put(new DelayedTask("Task 3", 10, TimeUnit.SECONDS));

        while (!dq.isEmpty()) {
            DelayedTask task = dq.take(); // Blocks until the head of the queue is ready to be taken
            System.out.println("Executing: " + task.getTaskName() + " at " + System.currentTimeMillis() );
        }
    }
}

class DelayedTask implements Delayed {
    private final String taskName;
    private final long startTime;

    public DelayedTask(String taskName, long delay, TimeUnit unit) {
        this.taskName = taskName;
        this.startTime = System.currentTimeMillis() + unit.toMillis(delay);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long remaining = startTime - System.currentTimeMillis();
        return unit.convert(remaining, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if( this.startTime < ((DelayedTask) o).startTime){
            return -1;
        }
        if( this.startTime > ((DelayedTask) o).startTime){
            return 1;
        }
        return 0;
    }

    public String getTaskName() {
        return taskName;
    }
}
