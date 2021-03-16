package workflow.Exception;

public class WrongNumberOfArguments extends Exception{
    public WrongNumberOfArguments() {
        super();
    }

    public WrongNumberOfArguments(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
