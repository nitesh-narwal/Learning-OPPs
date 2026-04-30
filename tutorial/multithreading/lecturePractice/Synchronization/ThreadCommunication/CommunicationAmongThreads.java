package me.niteshh.OPPs.tutorial.multithreading.lecturePractice.Synchronization.ThreadCommunication;


class SharedResources{

    private int data;
    private boolean hasData;

    public synchronized void producer(int value){
        while(hasData){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Producer thread interrupted" + e.getMessage());
            }
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + data);
        notify();
    }

    public synchronized int consumer(){
        while(!hasData){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Consumer thread interrupted" + e.getMessage());
            }
        }

        hasData = false;
        System.out.println("Consumed: " + data);
        notify();
        return data;

    }
}

class Producer implements Runnable{

    private SharedResources sharedResources;

    public Producer(SharedResources sharedResources){
        this.sharedResources = sharedResources;
    }
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            sharedResources.producer(i);
        }
    }
}

class Consumer implements Runnable{
    private SharedResources sharedResources;

    public Consumer(SharedResources sharedResources){
        this.sharedResources = sharedResources;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            sharedResources.consumer();
        }
    }
}

public class CommunicationAmongThreads {
    static void main(String[] args) {

        SharedResources sharedResources = new SharedResources();

        Thread producerThread = new Thread(new Producer(sharedResources));
        Thread consumerThread = new Thread(new Consumer(sharedResources));

        producerThread.start();
        consumerThread.start();
    }
}
