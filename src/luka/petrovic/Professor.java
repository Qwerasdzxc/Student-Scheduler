package luka.petrovic;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Professor extends Thread {

    private List<Student> students;

    Professor(List<Student> students) {
        this.students = students;
    }

    @Override
    public void run() {
        try {
            System.out.println("[Professor] Started grading");

            for (Student student : students) {
                student.join();
            }

            students.clear();

            System.out.println("[Professor] Ended grading");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
