package luka.petrovic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    final static int STUDENT_COUNT = 2;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        List<Student> studentsForProfessor = new ArrayList<>();

        ScheduledExecutorService executorServiceScheduled = Executors.newScheduledThreadPool(STUDENT_COUNT);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Professor(studentsForProfessor));

        // Prvo se pozove barrier 2nd argument pa onda thread-ovi
        for (int i = 0; i < STUDENT_COUNT; i ++) {
//            if (new Random().nextBoolean()) {
                Student student = new Student();
                studentsForProfessor.add(student);
                System.out.println("Assigned to Prof");
                executorServiceScheduled.schedule(student, 0, TimeUnit.MILLISECONDS);
//            }
//            else {
//                System.out.println("Assigned to TA");
//                executorServiceScheduled.schedule(new TeachingAssistant(new Student()), 0, TimeUnit.MILLISECONDS);
//            }
        }

//        Future<String> future = executorServiceScheduled.submit(new CallableExample());
//        System.out.println("Proveravamo da li je asinhroni zadatak zavrsen: " + future.isDone());
//        System.out.println("Rezultat izvrsavanja dobijamo pomocu get() metode: " + future.get());
        executorServiceScheduled.shutdown();
    }
}
