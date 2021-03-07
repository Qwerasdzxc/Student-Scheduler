package luka.petrovic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    final static int STUDENT_COUNT = 4;

    public static Boolean taFree = true;
    public static Boolean profFree = true;

    public static void main(String[] args) {
        Queue<Student> studentsForProfessor = new LinkedList<>();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                Main.profFree = false;
                Professor professor = new Professor(studentsForProfessor.poll(), studentsForProfessor.poll());
                professor.start();
                try {
                    professor.join();
                    Main.profFree = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ScheduledExecutorService executorServiceScheduled = Executors.newScheduledThreadPool(STUDENT_COUNT);

        for (int i = 0; i < STUDENT_COUNT;) {
            if (new Random().nextBoolean()) {
                if (studentsForProfessor.size() < 2) {
                    i ++;
                    System.out.println("Assigned to Prof");
                    studentsForProfessor.add(new Student());
                    executorServiceScheduled.schedule(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cyclicBarrier.await();
                            } catch (InterruptedException | BrokenBarrierException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 0, TimeUnit.MILLISECONDS);
                }
            } else {
                if (taFree) {
                    i ++;
                    System.out.println("Assigned to TA");
                    TeachingAssistant teachingAssistant = new TeachingAssistant(new Student());
                    Main.taFree = false;
                    executorServiceScheduled.schedule(teachingAssistant, 0, TimeUnit.MILLISECONDS);
                }
            }
        }

        executorServiceScheduled.shutdown();
    }
}
