package com.engeto.philosophers;

import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Philosopher implements Runnable {

    private final int NUMBER_ROUNDS = 10_000;
    private final Random numGenerator = new Random(); //náhodné číslo pro případné čekání

    private final int id; // číslo filozofa
    private final Lock leftChopstick;
    private final Lock rightChopstick;
    private int counter; //počet najezení se
    private int conflictCounter; // počet konfliktů


    /**
     * Konstruktor filozofa / vlákna
     */
    public Philosopher(int id, Lock leftChopstick, Lock rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.counter = 0;
        this.conflictCounter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public int getNUMBER_ROUNDS() {
        return NUMBER_ROUNDS;
    }

    /**
     * spuštění vlákna
     */
    public void run() {
        try {
            while (this.counter < NUMBER_ROUNDS) {
                if (pickUpChopsticks()) {
                    eat();
                    putDownChopsticks();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Ph. " + id + " was interrupted.\n");
        }
        System.out.println("Ph. " + id + " has eaten a total of " + counter + " times.");
        System.out.println("Počet konfliktů: " + conflictCounter);
    }


    private boolean pickUpChopsticks() throws InterruptedException {
        if (leftChopstick.tryLock()) {
            if (rightChopstick.tryLock()) {
                return true;
            } else {
                leftChopstick.unlock();
                System.out.println("Ph. " + id + " is letting go left chopstick.");
                conflictCounter++;
                Thread.sleep(numGenerator.nextInt(100));
                return false;
            }
        } else {
            conflictCounter++;
            Thread.sleep(numGenerator.nextInt(100));
            return false;
        }
    }

    private void eat(){
        counter++;
        System.out.println(id + " eats for the " + counter + ". time"); //Filozof číslo : jedl tolikrát
    }

    private void putDownChopsticks() {
        leftChopstick.unlock();
        rightChopstick.unlock();

    }


}
