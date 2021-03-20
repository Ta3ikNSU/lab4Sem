package workflow.Exception;

public class CommandOrderError extends Exception{
    public CommandOrderError() {

    }

    public CommandOrderError(String msg) {
        super(msg);
    }

    public CommandOrderError(String msg, Throwable ex) {
        super(msg, ex);
    }

    public CommandOrderError(Throwable ex) {
        super(ex);
    }
}
