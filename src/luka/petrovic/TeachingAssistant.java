package luka.petrovic;

public class TeachingAssistant extends Thread {

    private Student student;

    TeachingAssistant(Student student) {
        this.student = student;
    }

    @Override
    public void run() {
        try {
            System.out.println("[TeachingAssistant] Started grading");

            student.start();
            student.join();

            System.out.println("[TeachingAssistant] Ended grading");
            Main.taFree = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
