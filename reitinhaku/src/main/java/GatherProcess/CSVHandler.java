package GatherProcess;

import Tietorakenteet.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jxmapviewer.viewer.GeoPosition;
/*
Käytetty CSV-tiedosto esittää tiedot kaarina joilla on kaksi koordinaattipistettä.
*/

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
    
    /*
    Konvertoidaan CSV:n rivit omiksi nodeiksiin jotka ovat toistensa naapureita.
    */
    public ArrayList<Node> convertToNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for(int i=0; i <csv.size(); i++) {
            double lat = Double.parseDouble(this.csv.get(i).get(11));
            double lon = Double.parseDouble(this.csv.get(i).get(10));
            GeoPosition uusGeo = new GeoPosition(lon, lat);
            Node node1 = new Node(uusGeo);
            
            double lat2 = Double.parseDouble(this.csv.get(i).get(13));
            double lon2 = Double.parseDouble(this.csv.get(i).get(12));
            GeoPosition uusGeo2 = new GeoPosition(lon2, lat2);
            Node node2 = new Node(uusGeo2);
            node1.lisaaNaapuri(node2);
            node2.lisaaNaapuri(node1);
            nodes.add(node2);
            nodes.add(node1);
        }
        return nodes;
    }
}
