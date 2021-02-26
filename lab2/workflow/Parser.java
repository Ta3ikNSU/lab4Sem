package workflow;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Parser {
    private Map<String, Vector<String>> _blocks;
    private Vector<String> _commands;

    public Parser() {
    }

    ;

    public void parse(String fileName) throws Exception {
        boolean check_csed = true;
        Scanner input = new Scanner(new File(fileName));
        String line = input.nextLine();
        if (!line.equals("desc")) {
            throw new Exception("Skipped desc");
        }
        while (input.hasNextLine()) {
            Vector<String> tokens = new Vector<>();
            line = input.nextLine();
            String[] arg = line.split(" ");
            if (line.equals("")) continue;
            for (var i : arg) {
                tokens.add(i);
            }
            if (tokens.size() < 3) {
                if (tokens.size() == 1 && tokens.get(0).equals("csed")) {
                    check_csed = false;
                    break;
                } else
                    throw new Exception("");
            }

            if (_blocks.get(tokens.get(0)) == null) {
                throw new Exception("not enough data to work");
            }
            String key = tokens.get(0);
            tokens.remove(0);
            tokens.remove(0);
            _blocks.put(key, tokens);
        }

        if (check_csed) throw new Exception("csed was not found");
        line = input.nextLine();
        if (line.equals("")) throw new Exception("command sequence not found");
        String[] arg = line.split(" ");
        int wordCounter = 0;
        for (var i : arg) {
            if (wordCounter % 2 != 0 && !i.equals("->")) throw new Exception("wrong sequence format");
            if (wordCounter % 2 == 0 && _blocks.get(i) == null) throw new Exception("block was not found");
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
