package workflow.block;

import java.util.*;

public interface Block {
    ArrayList execute(ArrayList<String> text, Vector<String> args) throws Exception;
    BlockType getType();
}
