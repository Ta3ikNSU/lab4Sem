package workflow;

import workflow.Exception.CommandOrderError;
import workflow.block.Block;
import workflow.block.BlockType;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

public class Workflow {

    //desc
    //csed
    public void work(String fileName) throws Exception {
        ArrayList<String> text = new ArrayList<>();
        Parser parser = new Parser();
        try {
            parser.parse(fileName);
        } catch (Exception ex) {
            Logger.getLogger("WorkFlowLogger").severe(ex.getMessage());
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

            if (block == null) continue;

            if (i == 0 && block.getType() != BlockType.IN_TYPE) {
                throw new CommandOrderError("Reading cannot be performed outside of the start of work");
            }

            if (i == commands.size() - 1 && block.getType() != BlockType.OUT_TYPE) {
                throw new CommandOrderError("Writing cannot be performed not at the end of work");
            }

            try {
                text = block.execute(text, args);
                Logger.getLogger("WorkFlowLogger").info("Block " + blockName + " was created and executed");
            } catch (Exception e) {
                Logger.getLogger("WorkFlowLogger").severe("An error occurred during block " + blockName + " execution\n" + e.getMessage());
            }
        }
    }

}
