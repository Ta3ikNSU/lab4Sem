import java.io.*;
import java.util.*;
public class CSVParser{

    private int count;
    private HashMap<String, Integer> wordMap;

    public CSVParser(){
        wordMap = new HashMap<>();
        count = 0;
    }

    public void parser(String line) {
        StringBuilder curWord = new StringBuilder();
        for(int i = 0; i < line.length();i++){
            if(Character.isLetter(line.charAt(i))){
                curWord.append(line.charAt(i));
            } else {
                int newValue;
                if(wordMap.get(curWord.toString()) == null){
                    newValue = 1;
                } else {
                    newValue = wordMap.get(curWord.toString()) + 1;
                }
                wordMap.put(curWord.toString(), newValue);
                count++;
            }
        }
        if(Character.isLetter(line.charAt(line.length()-1))){int newValue;
            if(wordMap.get(curWord.toString()) == null){
                newValue = 1;
            } else {
                newValue = wordMap.get(curWord.toString()) + 1;
            }
            wordMap.put(curWord.toString(), newValue);
            count++;
        }
    }

    public void readingAndCounting(String in) {
        Scanner input = null;
        File inFile = new File(in);
        try {
            input = new Scanner(inFile);
            while (input.hasNext()) {
                String curword = input.nextLine();
                parser(curword);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sortDataAndWriteToCSV(String out) throws IOException {
        FileWriter fileWriter = new FileWriter(out);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        ArrayList<Pair<String, Integer>> sortedList = new ArrayList<>();
        for(Map.Entry <String, Integer> entry : wordMap.entrySet()){
            Pair<String, Integer> pair = new Pair<>(entry.getKey(), entry.getValue());
            sortedList.add(pair);

        }
        sortedList.sort((arg1, arg2) -> -(arg1.getSecond()).compareTo(arg2.getSecond()));
        for(Pair <String, Integer> cur : sortedList){
            float fract = ((float) cur.getSecond()) / count;
            printWriter.println(cur.toString() + ";" + fract);
        }
        printWriter.close();
    }
}
