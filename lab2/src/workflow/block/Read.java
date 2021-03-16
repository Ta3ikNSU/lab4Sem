package workflow.block;

import workflow.Exception.WrongNumberOfArguments;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Read implements Block {
    @Override
    public ArrayList execute(ArrayList<String> text, Vector<String> args) throws Exception {
        if (args.size() != 1) throw new WrongNumberOfArguments("Args for read != 1");
        String fileName = args.get(0);
        String line;
        Scanner input = new Scanner(new File(fileName));
        text.clear();
        while(input.hasNextLine()){
            line = input.nextLine();
            text.add(line);
        }
        return text;
    }

    public BlockType getType() {
        return BlockType.IN_TYPE;
    }
}
