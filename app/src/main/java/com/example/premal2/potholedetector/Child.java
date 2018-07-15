package com.example.premal2.potholedetector;

/**
 * Created by premal2 on 7/15/2018.
 */

public class Child extends Thread {
    public static int val = 0;

    @Override
    public void run() {
        val = 1;
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        val = 0;
    }
}
