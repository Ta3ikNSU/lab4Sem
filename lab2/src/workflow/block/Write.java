package workflow.block;

import workflow.Exception.WrongNumberOfArguments;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

public class Write implements Block {
    @Override
    public void execute(ArrayList<String> text, Vector<String> args) throws Exception {
        if (args.size() != 1) throw new WrongNumberOfArguments("Args for write != 1");
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(args.get(0));
            printWriter = new PrintWriter(fileWriter);
        } catch (IOException e) {
            throw e;
        }
        for(var i : text){
            printWriter.println(i);
        }
        printWriter.close();
    }

    public BlockType getType() {
        return BlockType.OUT_TYPE;
    }
}
