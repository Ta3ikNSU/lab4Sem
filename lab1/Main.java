public class Main {
    public static void main(String[] args) {

        String inputName = "in.txt";
        String outputName = "out.csv";
        CSVParser obj1 = new CSVParser();
        try {
            obj1.readingAndCounting(inputName);
            obj1.sortDataAndWriteToCSV(outputName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
