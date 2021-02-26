package workflow.block;

import workflow.Parser;

import java.util.Vector;

public class Grep implements Block {
    @Override
    public void execute(Vector<String> text, Vector<String> args) throws Exception {
        if (args.size() != 1) throw new Exception("Args for dump != 1");
        String word = args.get(0);
        for(int i = 0; i < text.size(); i++){
            if(text.get(i).lastIndexOf(word) == -1){
                continue;
            } else {
                text.remove(i);
                i--;
            }
        }
    }

    public blockType getType() {
        return blockType.OTHER;
    }
}
