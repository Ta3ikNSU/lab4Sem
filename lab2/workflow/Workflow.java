package workflow;

import workflow.block.Block;
import workflow.block.blockType;

import java.util.Vector;
import java.util.logging.Logger;

public class Workflow {

    //desc
    //csed
    public void work(String fileName) throws Exception {
        Vector<String> text = null;
        Parser parser = new Parser();
        try {
            parser.parse(fileName);
        } catch (Exception ex) {
            Logger.getLogger("WorkFlowLogger").warning(ex.getMessage());
        }
        var blocks = parser.getBlocks();
        var commands = parser.getCommands();
        for (int i = 0; i < commands.size(); i++) {
            String blockName = blocks.get(commands.get(i)).get(0);
            Vector<String> args = new Vector<>();
            for (int j = 1; j < blocks.get(commands.get(i)).size(); j++) {
                args.add(blocks.get(commands.get(i)).get(j));
            }
            Block block = BlockFactory.getInstance().createBlock(blockName);
            if(block == null) continue;
            if(i == 0 && block.getType() != blockType.IN_TYPE){
                throw new Exception("");
            }
            if(i == commands.size()-1 && block.getType() != blockType.OUT_TYPE){
                throw new Exception("");
            }
            block.execute(text, args);
            Logger.getLogger("WorkFlowLogger").info("Block " + blockName + " was created and executed");
        }
    }
}
