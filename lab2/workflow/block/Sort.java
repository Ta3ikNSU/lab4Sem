package workflow.block;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class Sort implements Block {
    @Override
    public ArrayList execute(ArrayList<String> text, Vector<String> args) throws Exception {
        if (args.size() != 0) throw new Exception("Args for sort != 0");
        text.sort((arg1, arg2) -> arg1.compareToIgnoreCase(arg2));
        return text;
    }

    public BlockType getType() {
        return BlockType.OTHER;
    }
}
