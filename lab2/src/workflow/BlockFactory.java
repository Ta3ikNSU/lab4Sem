package workflow;

import workflow.block.Block;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class BlockFactory {
    private static BlockFactory factory = null;
    private final Properties properties = new Properties();

    private BlockFactory() throws IOException {
        Logger.getLogger("WorkFlowLogger").info("Block factory creation completed successfully");
        var input = BlockFactory.class.getResourceAsStream("blocks.properties");

        if (input != null) {
            properties.load(input);
            input.close();
        }
    }

    public static BlockFactory getInstance() throws IOException {
        if (factory == null) {
            synchronized (BlockFactory.class) {
                if (factory == null)
                    factory = new BlockFactory();
            }
        }
        return factory;
    }


    public Block createBlock(String name){
        Block block;
        try{
            var classOfBlock = Class.forName(properties.getProperty(name));
            var objectInstance = classOfBlock.getDeclaredConstructor().newInstance();
            block = (Block) objectInstance;
        } catch (Exception ex) {
            Logger.getLogger("WorkFlowLogger").warning("block " + name + " was not found");
            return null;
        }
        return block;

    }
}
