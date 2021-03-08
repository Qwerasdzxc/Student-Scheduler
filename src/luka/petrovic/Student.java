package luka.petrovic;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Student extends Thread {

    private long defenseTime;
    private int grade;

    @Override
    public void run() {
        try {
            Random random = new Random();
            double value = random.nextDouble() * 0.5 + 0.5;
            defenseTime = (long) (value * 1000);

            Thread.sleep(defenseTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long getDefenseTime() {
        return defenseTime;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student " + super.getName();
    }
}
