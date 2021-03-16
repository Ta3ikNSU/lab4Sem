package workflow.Exception;

public class CommandSequenceError extends Exception{
    public CommandSequenceError() {
        super();
    }

    public CommandSequenceError(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
