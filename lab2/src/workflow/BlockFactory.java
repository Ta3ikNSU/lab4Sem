package workflow;

import workflow.block.Block;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;


public class BlockFactory {
    private static Logger log = Logger.getLogger(BlockFactory.class.getName());

    private static volatile BlockFactory factory = null;
    private final Properties properties = new Properties();

    private BlockFactory() throws IOException {
        log.info("Block factory creation completed successfully");
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
            log.log(java.util.logging.Level.WARNING, "block " + name + " was not found", ex);
            return null;
        }
        return block;
    }
}
