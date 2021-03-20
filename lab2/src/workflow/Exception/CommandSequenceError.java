package workflow.Exception;

public class CommandSequenceError extends Exception{
    public CommandSequenceError() {

    }

    public CommandSequenceError(String msg) {
        super(msg);
    }

    public CommandSequenceError(String msg, Throwable ex) {
        super(msg, ex);
    }

    public CommandSequenceError(Throwable ex) {
        super(ex);
}
}
