package luka.petrovic;

import java.util.Random;

public class TeachingAssistantWorker extends Thread {

    private Student student;

    TeachingAssistantWorker(Student student) {
        this.student = student;
    }

    @Override
    public void run() {
        try {
            long deltaTime = System.currentTimeMillis() - Main.startTime;

            student.start();

            int grade = new Random().nextInt(11);
            student.join();
            student.setGrade(grade);

            Main.studentCount.addAndGet(1);
            Main.gradeSum.addAndGet(grade);

            System.out.println("Thread: " + student.toString() +
                    " Arrival: " + deltaTime + "ms Prof: Teaching Assistant " + getName() +
                    " TTC: " + student.getDefenseTime() +
                    "ms : " + deltaTime + "ms Score: " + student.getGrade());

            Main.taFree = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
