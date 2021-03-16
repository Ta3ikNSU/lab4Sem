import workflow.*;

public class Main {
    public static void main(String[] args) {
        Workflow worker = new Workflow();
        try {
            worker.work("C:\\Users\\Ta3ik\\IdeaProjects\\lab2(21.02)\\work.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
