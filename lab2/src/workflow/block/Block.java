package workflow.block;

import java.util.*;

public interface Block {
    void execute(ArrayList<String> text, Vector<String> args) throws Exception;
    BlockType getType();
}
