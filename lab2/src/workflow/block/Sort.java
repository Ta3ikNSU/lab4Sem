package workflow.block;

import workflow.Exception.WrongNumberOfArguments;

import java.util.ArrayList;
import java.util.Vector;

public class Sort implements Block {
    @Override
    public void execute(ArrayList<String> text, Vector<String> args) throws Exception {
        if (args.size() != 0) throw new WrongNumberOfArguments("Args for sort != 0");
        text.sort((arg1, arg2) -> arg1.compareToIgnoreCase(arg2));
    }

    public BlockType getType() {
        return BlockType.OTHER;
    }
}
