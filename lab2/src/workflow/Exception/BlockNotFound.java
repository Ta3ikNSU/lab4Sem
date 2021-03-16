package workflow.Exception;

public class BlockNotFound extends Exception{
    public BlockNotFound() {
        super();
    }

    public BlockNotFound(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
