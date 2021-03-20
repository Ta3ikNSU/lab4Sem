package workflow.block;

import workflow.Exception.WrongNumberOfArguments;

import java.util.ArrayList;
import java.util.Vector;

public class Grep implements Block {
    @Override
    public void execute(ArrayList<String> text, Vector<String> args) throws Exception {
        if (args.size() != 1) throw new WrongNumberOfArguments("Args for grep != 1");
        String word = args.get(0);
        for(int i = 0; i < text.size(); i++){
            if(text.get(i).lastIndexOf(word) != -1){
                continue;
            } else {
                text.remove(i);
                i--;
            }
        }
    }

    public BlockType getType() {
        return BlockType.OTHER;
    }
}
