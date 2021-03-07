package luka.petrovic;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Student extends Thread {

    @Override
    public void run() {
        try {
            System.out.println("[Student] Started presenting");

            Random random = new Random();
            double value = random.nextDouble() * 0.5 + 0.5;
            long milliseconds = (long) (value * 1000);

            Thread.sleep(milliseconds);

            System.out.println("[Student] Ended presenting after: " + (milliseconds) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
