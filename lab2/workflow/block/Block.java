package workflow.block;

import java.util.ArrayList;
import java.util.Vector;

public interface Block {
    ArrayList execute(ArrayList<String> text, Vector<String> args) throws Exception;
    BlockType getType();
}
