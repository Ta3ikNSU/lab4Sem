package workflow.Exception;

public class CommandOrderError extends Exception{
    public CommandOrderError() {
        super();
    }

    public CommandOrderError(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
