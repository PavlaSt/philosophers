package com.engeto.philosophers;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PhilosopherTest {

    Lock chopstick1 = new ReentrantLock();
    Lock chopstick2 = new ReentrantLock();

    Philosopher philosopher1 = new Philosopher(1, chopstick1, chopstick2);
    Philosopher philosopher2 = new Philosopher(2, chopstick2, chopstick1);
    Thread t1 = new Thread(philosopher1);
    Thread t2 = new Thread(philosopher2);




    @Test
    void run() throws InterruptedException {
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertThat(philosopher1.getCounter()).isEqualTo(philosopher1.getNUMBER_ROUNDS());
        assertThat(philosopher2.getCounter()).isEqualTo(philosopher2.getNUMBER_ROUNDS());


    }
}