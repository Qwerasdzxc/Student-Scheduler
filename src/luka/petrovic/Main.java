package luka.petrovic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static int STUDENT_COUNT = 10;

    public static Boolean taFree = true;
    public static Boolean profFree = true;

    public static long startTime;

    public static AtomicInteger gradeSum = new AtomicInteger(0);
    public static AtomicInteger studentCount = new AtomicInteger(0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter student count: ");
        STUDENT_COUNT = scanner.nextInt();

        ScheduledExecutorService executorServiceScheduled = Executors.newScheduledThreadPool(STUDENT_COUNT);

        Queue<Student> studentsForProfessor = new LinkedList<>();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                Main.profFree = false;
                ProfessorWorker professorWorker = new ProfessorWorker(studentsForProfessor.poll(), studentsForProfessor.poll());
                professorWorker.start();
                try {
                    professorWorker.join();
                    Main.profFree = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        startTime = System.currentTimeMillis();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Student count: " + studentCount.get() + " Total grade average: " + (double) gradeSum.get() / studentCount.get());
                System.exit(0);
            }
        }, 5000);

        for (int i = 0; i < STUDENT_COUNT;) {
            if (new Random().nextBoolean()) {
                if (studentsForProfessor.size() < 2) {
                    i ++;
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
                    }, ((int) (new Random().nextDouble())) * 1000, TimeUnit.MILLISECONDS);
                }
            } else {
                if (taFree) {
                    i ++;
                    TeachingAssistantWorker teachingAssistantWorker = new TeachingAssistantWorker(new Student());
                    Main.taFree = false;
                    executorServiceScheduled.schedule(teachingAssistantWorker, ((int) (new Random().nextDouble())) * 1000, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}
