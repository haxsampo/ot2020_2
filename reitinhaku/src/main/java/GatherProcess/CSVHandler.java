package GatherProcess;

import Tietorakenteet.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jxmapviewer.viewer.GeoPosition;
/*
Käytetty CSV-tiedosto esittää tiedot kaarina joilla on kaksi koordinaattipistettä.
*/

public class CSVHandler {
    
    List<List<String>> csv;
    DecimalFormat df;
    ArrayList<GeoPosition> geopos;
    
    public CSVHandler() {
        this.df = new DecimalFormat("#.######");
        this.df.setRoundingMode(RoundingMode.CEILING);
        this.geopos = new ArrayList<GeoPosition>();
    }
    
    public void readCSV(String csvPath) throws Exception {
        List<List<String>> records = new ArrayList<>();
        int i = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while((line =br.readLine())!=null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
                i++;
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
    public ArrayList<Node> convertToNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for(int i=1; i <csv.size(); i++) {
            GeoPosition[] geopos = getNewGeoPos(i);
            GeoPosition uusGeo = geopos[0];
            Node node1 = new Node(uusGeo);    
            GeoPosition uusGeo2 = geopos[1];
            Node node2 = new Node(uusGeo2);
            node1.lisaaNaapuri(node2);
            node2.lisaaNaapuri(node1);
            nodes.add(node2);
            nodes.add(node1);
        }
        return nodes;
    }
    */
    
    /*
    * Konvertoidaan CSV:n rivit omiksi nodeiksiin. Kaksi peräkkäistä riviä
    * muodostaa kaaren.
    */
    public ArrayList<Node> csvToNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for(int i = 1; i<csv.size(); i+=2) {
            GeoPosition[] geopos = getNewGeoPos(i);
            Double dist = getDist(i);
            Node node1 = new Node(geopos[0]);
            Node node2 = new Node(geopos[1]);
            node1.lisaaNaapuri(node2,dist);
            node2.lisaaNaapuri(node1, dist);
            nodes.add(node1);
            nodes.add(node2);
        }
        return nodes;
    }
    
    /*
    * Hakee etäisyyden annetun indeksinoden ja sitä seuraavan noden välillä
    */
    public Double getDist(Integer i) {
        String str = this.csv.get(i+1).get(21);
        str = str.replaceAll("\"", str);
        Double doub =  Double.parseDouble(str);
        return doub;
    }
 
    /*
    * Hakee ja luo geopositiot/koordinaatit annetulle indeksille, ja sitä i+1 
    * elementille.
    * @param i: Käsiteltävä indeksi
    * @return Palauttaa Geoposition arrayn, ensimmäisenä i, toisena i+1 elementti
    */
    public GeoPosition[] getNewGeoPos(Integer i) {
        GeoPosition[] ret = new GeoPosition[2];
        String lat = this.csv.get(i).get(0);
        String lon = this.csv.get(i).get(1);
        
        String lat2 = this.csv.get(i+1).get(0);
        String lon2 = this.csv.get(i+1).get(1);
        /*
        if(lon.equals("F") || lon.equals("T")) {
           lat = checkForLength(this.csv.get(i).get(23));
           lon = checkForLength(this.csv.get(i).get(22));
           lat2 = checkForLength(this.csv.get(i).get(14));
           lon2 = checkForLength(this.csv.get(i).get(13));
        }*/
        GeoPosition uusGeo = new GeoPosition(Double.parseDouble(lat.replaceAll("\"", "")), Double.parseDouble(lon.replaceAll("\"", "")));
        GeoPosition uusGeo2 = new GeoPosition(Double.parseDouble(lat2.replaceAll("\"", "")), Double.parseDouble(lon2.replaceAll("\"", "")));
        ret[0] = uusGeo;
        ret[1] = uusGeo2;
        return ret;
    }
    
    public GeoPosition[] getNewGeoPos1(Integer i) {
        String lat = this.csv.get(i).get(11);
        String lon = this.csv.get(i).get(10);
        String lat2 = this.csv.get(i).get(13);
        String lon2 = this.csv.get(i).get(12);
        if(lon.equals("F") || lon.equals("T")) {
            lat = this.csv.get(i).get(12);
            lon = this.csv.get(i).get(11);
            lat2 = this.csv.get(i).get(14);
            lon2 = this.csv.get(i).get(13);
         }
        //System.out.println(lat+" "+lon);
        //System.out.println(lat2+" "+lon2);
        if(!lat.substring(0,2).equals("60")) {
            System.out.println("KAKKA");
        }
        if(!lon.substring(0,2).equals("24") && !lon.substring(0,2).equals("25")) {
            System.out.println(lon);
            System.out.println("PISSA");
        }
        if(!lat2.substring(0,2).equals("60")) {
            System.out.println("KAKKA");
        }
        GeoPosition uusGeo = new GeoPosition(Double.parseDouble(lat), Double.parseDouble(lon));
        GeoPosition uusGeo2 = new GeoPosition(Double.parseDouble(lat2), Double.parseDouble(lon2));
        GeoPosition[] ret = new GeoPosition[2];
        ret[0] = uusGeo;
        ret[1] = uusGeo2;
        return ret;
    }
    
    public String checkForLength(String kord) {
        if(kord.length() > 9) {
            return kord.substring(0,9);
        } else {
            return kord;
        }
    }
    
    public void readGeoPos() {
        for(int i=1; i <csv.size(); i++) {            
            GeoPosition[] line = getNewGeoPos(i);
            geopos.add(line[0]);
            geopos.add(line[1]);
        }
    }
    
    public ArrayList<GeoPosition> getGeoPos() {
        return this.geopos;
    }
}
