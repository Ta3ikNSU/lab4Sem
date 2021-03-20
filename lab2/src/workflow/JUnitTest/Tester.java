package workflow.JUnitTest;

import junit.framework.TestSuite;
import org.junit.Test;
import workflow.block.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class Tester extends TestSuite {
    @Test
    public void dump() throws Exception {
        Dump dumpTest = new Dump();
        ArrayList<String> text = new ArrayList<>();
        Vector<String> args = new Vector<>();
        text.add("Test1");
        text.add("Test1_");
        text.add("tEST_!");
        args.add("outDump.txt");
        dumpTest.execute(text, args);
        File inputFile = new File("outDump.txt");
        Scanner input = new Scanner(inputFile);
        ArrayList<String> testedOutput = new ArrayList<>();
        String inputLine;
        while (input.hasNextLine()) {
            inputLine = input.nextLine();
            testedOutput.add(inputLine);
        }
        input.close();
        inputFile.delete();
        assertEquals(text, testedOutput);
    }

    @Test
    public void write() throws Exception {
        Write writeTest = new Write();
        ArrayList<String> text = new ArrayList<>();
        Vector<String> args = new Vector<>();
        text.add("Test1");
        text.add("Test1_");
        text.add("tEST_!");
        args.add("outWrite.txt");
        writeTest.execute(text, args);
        File inputFile = new File("outWrite.txt");
        Scanner input = new Scanner(inputFile);
        ArrayList<String> testedOutput = new ArrayList<>();
        String inputLine;
        while (input.hasNextLine()) {
            inputLine = input.nextLine();
            testedOutput.add(inputLine);
        }
        input.close();
        inputFile.delete();
        assertEquals(text, testedOutput);
    }

    @Test
    public void grep() throws Exception {
        Grep grepTest = new Grep();
        ArrayList<String> text = new ArrayList<>();
        ArrayList<String> testedOutput = new ArrayList<>();
        Vector<String> args = new Vector<>();
        text.add("Test1");
        text.add("Test1_");
        text.add("tEST_!");
        args.add("est1");
        testedOutput.addAll(text);
        grepTest.execute(testedOutput, args);
        text.remove(2);
        assertEquals(text, testedOutput);
    }

    @Test
    public void read() throws Exception {
        Read readTest = new Read();
        ArrayList<String> text = new ArrayList<>();
        Vector<String> args = new Vector<>();
        text.add("Test1");
        text.add("Test1_");
        text.add("tEST_!");
        args.add("inputRead.txt");
        Write writer = new Write();
        writer.execute(text, args);
        File inputFile = new File("inputRead.txt");
        Scanner input = new Scanner(inputFile);
        ArrayList<String> testedOutput = new ArrayList<>();
        readTest.execute(testedOutput, args);
        input.close();
        inputFile.delete();
        assertEquals(text, testedOutput);

    }

    @Test
    public void replace() throws Exception {
        Replace replaceTest = new Replace();
        ArrayList<String> text = new ArrayList<>();
        Vector<String> args = new Vector<>();
        text.add("Test1");
        text.add("Test1_");
        text.add("tEST_!");
        ArrayList<String> testedOutput = new ArrayList<>(text);
        args.add("est");
        args.add("ste");
        replaceTest.execute(testedOutput, args);
        text.clear();
        text.add("Tste1");
        text.add("Tste1_");
        text.add("tEST_!");
        assertEquals(text, testedOutput);
    }

    @Test
    public void sort() throws Exception {
        Sort sortTest = new Sort();
        ArrayList<String> text = new ArrayList<>();
        Vector<String> args = new Vector<>();
        text.add("Test1");
        text.add("tEST_!");
        text.add("Test1_");
        ArrayList<String> testedOutput = new ArrayList<>(text);
        sortTest.execute(testedOutput, args);
        text.clear();
        text.add("Test1");
        text.add("Test1_");
        text.add("tEST_!");
        assertEquals(text, testedOutput);
    }

}
