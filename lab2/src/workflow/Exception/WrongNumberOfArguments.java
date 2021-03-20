package workflow.Exception;

public class WrongNumberOfArguments extends Exception{
    public WrongNumberOfArguments() {

    }

    public WrongNumberOfArguments(String msg) {
        super(msg);
    }

    public WrongNumberOfArguments(String msg, Throwable ex) {
        super(msg, ex);
    }

    public WrongNumberOfArguments(Throwable ex) {
        super(ex);
    }
}
