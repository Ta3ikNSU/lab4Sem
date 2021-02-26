package workflow.block;

import java.util.ArrayList;
import java.util.Vector;

public class Grep implements Block {
    @Override
    public ArrayList execute(ArrayList<String> text, Vector<String> args) throws Exception {
        if (args.size() != 1) throw new Exception("Args for grep != 1");
        String word = args.get(0);
        for(int i = 0; i < text.size(); i++){
            if(text.get(i).lastIndexOf(word) == -1){
                continue;
            } else {
                text.remove(i);
                i--;
            }
        }
        return text;
    }

    public BlockType getType() {
        return BlockType.OTHER;
    }
}
