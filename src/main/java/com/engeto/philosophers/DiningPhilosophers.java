package com.engeto.philosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {
    // počet filozofů
    private static final int NUMBER_PHILOSOPHERS = 8;


    public static void main(String[] args) throws InterruptedException {
        // pole hůlek (zámků)
        Lock[] chopsticks = new ReentrantLock[NUMBER_PHILOSOPHERS];

        for (int i = 0; i < NUMBER_PHILOSOPHERS; i++) {
            chopsticks[i] = new ReentrantLock();
        }
        // pole filozofů
        Philosopher[] philosophers = new Philosopher[NUMBER_PHILOSOPHERS];

        //pole vláken
        Thread[] threads = new Thread[NUMBER_PHILOSOPHERS];

        //konstrukce filozofů (vláken)  a jejich spuštění
        for (int i = 0; i < NUMBER_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % NUMBER_PHILOSOPHERS]);
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }

        //napojení vláken
        for (int i = 0; i < NUMBER_PHILOSOPHERS; i++) {
            threads[i].join();
        }
    }
}
