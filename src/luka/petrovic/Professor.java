package luka.petrovic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Professor extends Thread {

    private Student student1;
    private Student student2;

//    private final List<Student> students;

    Professor(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

//    Professor(List<Student> studentsForProfessor) {
//        this.studentsForProfessor = new ArrayList<>(studentsForProfessor);
//    }

    @Override
    public void run() {
        try {
            System.out.println("[Professor] Started grading");

            student1.start();
            student2.start();

            student1.join();
            student2.join();

            Thread.sleep(500);
            System.out.println("[Professor] Ended grading");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
