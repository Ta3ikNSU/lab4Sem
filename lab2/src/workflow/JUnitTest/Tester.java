package workflow.JUnitTest;

import junit.framework.TestSuite;
import org.junit.Test;
import workflow.Workflow;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Tester extends TestSuite {
    @Test
    public void dump() throws Exception {
        Workflow worker = new Workflow();
        worker.work("src/workflow/JUnitTest/txtFile/testDump.txt");
        InputStreamReader reader1 = new InputStreamReader(new FileInputStream("src/workflow/JUnitTest/txtFile/testWord.txt"));
        InputStreamReader reader2 = new InputStreamReader(new FileInputStream("src/workflow/JUnitTest/txtFile/testDumpOutput.txt"));

        while (reader1.ready() && reader2.ready()) {
            assertEquals(reader2.read(), reader1.read());
        }

        assertFalse(reader1.ready() || reader2.ready());

        reader1.close();
        reader2.close();
    }

    @Test
    public void write() throws Exception {
        Workflow worker = new Workflow();
        worker.work("src/workflow/JUnitTest/txtFile/testWrite.txt");
    }

    @Test
    public void grep() throws Exception {
        Workflow worker = new Workflow();
        worker.work("src/workflow/JUnitTest/txtFile/testGrep.txt");

    }

    @Test
    public void read() throws Exception {
        Workflow worker = new Workflow();
        worker.work("src/workflow/JUnitTest/txtFile/testRead.txt");

    }

    @Test
    public void replace() throws Exception {
        Workflow worker = new Workflow();
        worker.work("src/workflow/JUnitTest/txtFile/testReplace.txt");

    }

    @Test
    public void sort() throws Exception {
        Workflow worker = new Workflow();
        worker.work("src/workflow/JUnitTest/txtFile/testSort.txt");
    }


}
