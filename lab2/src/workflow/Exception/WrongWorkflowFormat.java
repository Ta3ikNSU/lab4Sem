package workflow.Exception;

public class WrongWorkflowFormat extends Exception {
    public WrongWorkflowFormat() {
        super();
    }

    public WrongWorkflowFormat(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
