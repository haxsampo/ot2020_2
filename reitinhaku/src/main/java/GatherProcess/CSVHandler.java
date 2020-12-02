package GatherProcess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHandler {
    
    List<List<String>> csv;
    
    public CSVHandler() {
    }
    
    public void readCSV(String csvPath) throws Exception {
        List<List<String>> records = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while((line =br.readLine())!=null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        }
        this.csv = records;
    }

    public List<List<String>> getCsv() {
        return csv;
    }

    public void setCsv(List<List<String>> csv) {
        this.csv = csv;
    }
}
