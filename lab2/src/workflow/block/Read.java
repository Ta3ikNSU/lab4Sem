package workflow.block;

import java.util.Vector;

public class Read implements Block {
    @Override
    public void execute(Vector<String> text, Vector<String> args) {

    }

    public blockType getType() {
        return blockType.IN_TYPE;
    }
}
