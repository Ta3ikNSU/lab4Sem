package workflow.block;

import workflow.Exception.WrongNumberOfArguments;

import java.util.ArrayList;
import java.util.Vector;

public class Replace implements Block {
    @Override
    public void execute(ArrayList<String> text, Vector<String> args) throws Exception {
        if (args.size() != 2) throw new WrongNumberOfArguments("Args for replace != 2");
        String word = args.get(0);
        String newWord = args.get(1);
        for(int i = 0; i < text.size(); i++){
            String line = text.get(i);
            line = line.replaceAll(word, newWord);
            if(!line.equals(text.get(i))){
                text.add(i + 1, line);
                text.remove(i);
            }
        }
    }

    public BlockType getType() {
        return BlockType.OTHER;
    }
}
