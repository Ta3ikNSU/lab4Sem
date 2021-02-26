package workflow.block;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class Dump implements Block {
    @Override
    public void execute(Vector<String> text, Vector<String> args) throws Exception {
        if (args.size() != 1) throw new Exception("Args for dump != 1");
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
    }

    public blockType getType() {
        return blockType.OUT_TYPE;
    }
}
