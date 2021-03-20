package workflow;

import workflow.Exception.BlockNotFound;
import workflow.Exception.CommandSequenceError;
import workflow.Exception.WrongWorkflowFormat;

import javax.naming.InsufficientResourcesException;
import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class Parser {

    private final Map<String, Vector<String>> _blocks;
    private final Vector<String> _commands;

    public Parser() {
        _blocks = new HashMap<>();
        _commands = new Vector<>();
    }

    public void parse(String fileName) throws Exception {
        boolean check_csed = true;
        Scanner input = new Scanner(new File(fileName));
        String line = input.nextLine();
        if (!line.equals("desc")) {
            throw new WrongWorkflowFormat("Skipped desc");
        }
        while (input.hasNextLine()) {
            Vector<String> tokens = new Vector<>();
            line = input.nextLine();
            String[] arg = line.split(" ");
            if (line.equals("")) continue;
            Collections.addAll(tokens, arg);
            if (tokens.size() == 1 && tokens.get(0).equals("csed")) {
                check_csed = false;
                break;
            }
            if (_blocks.get(tokens.get(0)) != null) {
                throw new InsufficientResourcesException("no block description found");
            }
            String key = tokens.get(0);
            tokens.remove(0);
            _blocks.put(key, tokens);
        }

        if (check_csed) throw new WrongWorkflowFormat("csed was not found");
        line = input.nextLine();
        if (line.equals("")) throw new CommandSequenceError("command sequence not found");
        String[] arg = line.split(" ");
        int wordCounter = 0;
        for (var i : arg) {
            if (wordCounter % 2 != 0 && !i.equals("->")) throw new CommandSequenceError("wrong sequence format");
            if (wordCounter % 2 == 0 && _blocks.get(i) == null) throw new BlockNotFound("block was not recognized");
            if (wordCounter % 2 == 0 && !i.equals("->")) _commands.add(i);
            wordCounter++;
        }
    }

    public Map<String, Vector<String>> getBlocks() {
        return _blocks;
    }

    public Vector<String> getCommands() {
        return _commands;
    }
}
