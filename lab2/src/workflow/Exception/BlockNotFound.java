package workflow.Exception;

public class BlockNotFound extends Exception{
    public BlockNotFound() {

    }

    public BlockNotFound(String msg) {
        super(msg);
    }

    public BlockNotFound(String msg, Throwable ex) {
        super(msg, ex);
    }

    public BlockNotFound(Throwable ex) {
        super(ex);
    }


}
