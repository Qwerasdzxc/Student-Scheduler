package luka.petrovic;

import java.util.Random;

public class ProfessorWorker extends Thread {

    private Student student1;
    private Student student2;

    ProfessorWorker(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    @Override
    public void run() {
        try {
            long deltaTime = System.currentTimeMillis() - Main.startTime;

            student1.start();
            student2.start();

            Random random = new Random();

            int grade1 = random.nextInt(11);
            int grade2 = random.nextInt(11);

            student1.join();
            student1.setGrade(grade1);

            Main.studentCount.addAndGet(1);
            Main.gradeSum.addAndGet(grade1);

            student2.join();
            student2.setGrade(grade2);

            Main.studentCount.addAndGet(1);
            Main.gradeSum.addAndGet(grade2);

            System.out.println("Thread: " + student1.toString() +
                    " Arrival: " + deltaTime + "ms Prof: Professor " + getName() +
                    " TTC: " + student1.getDefenseTime() +
                    "ms : " + deltaTime + "ms Score: " + student1.getGrade());

            System.out.println("Thread: " + student2.toString() +
                    " Arrival: " + deltaTime + "ms Prof: Professor " + getName() +
                    " TTC: " + student2.getDefenseTime() +
                    "ms : " + deltaTime + "ms Score: " + student2.getGrade());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
