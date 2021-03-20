package workflow.Exception;

public class WrongWorkflowFormat extends Exception {

    public WrongWorkflowFormat() {

    }

    public WrongWorkflowFormat(String msg) {
        super(msg);
    }

    public WrongWorkflowFormat(String msg, Throwable ex) {
        super(msg, ex);
    }

    public WrongWorkflowFormat(Throwable ex) {
        super(ex);
    }
}
