package workflow.block;

import java.util.Vector;

public interface Block {
    void execute(Vector<String> text, Vector<String> args) throws Exception;

    blockType getType();
}
