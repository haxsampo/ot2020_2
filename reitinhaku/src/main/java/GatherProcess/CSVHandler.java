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
    ArrayList<GeoPosition> geopos;
    
    public CSVHandler() {
        this.geopos = new ArrayList<GeoPosition>();
    }
    
    public void readCSV(String csvPath) throws Exception {
        List<List<String>> records = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            String kakke="";
            int k = 0;
            int amount = 0;
            while((line =br.readLine())!=null) {
                String[] values = line.split(";");
                if(!(values[0].equals("x"))){
                   records.add(Arrays.asList(values));
                   amount++;
                }
                k++;
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
    * Konvertoidaan CSV:n rivit omiksi nodeiksiin. Kaksi peräkkäistä riviä
    * muodostaa kaaren.
    */
    public ArrayList<Node> csvToNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        System.out.println(this.csv.size());
        int amount = 0;
        for(int i = 1; i<csv.size()-1; i+=2) {
            if(i >= csv.size()) {
                break;
            }
            Integer index = Integer.parseInt(csv.get(i).get(16).replaceAll("^\"|\"$", ""));
            Integer indexMinus = Integer.parseInt(csv.get(i+1).get(16).replaceAll("^\"|\"$", ""));
            System.out.println(csv.get(i).get(16));
            System.out.println(csv.get(i+1).get(16));
            
            if((index==0 && indexMinus==-1)) {
                GeoPosition[] geopos = getNewGeoPos(i);
                Double dist = getDist(i);
                Node node1 = new Node(geopos[0]);
                Node node2 = new Node(geopos[1]);
                node1.lisaaNaapuri(node2,dist);
                node2.lisaaNaapuri(node1, dist);
                nodes.add(node1);
                nodes.add(node2);
                amount++;
            }
            if(index==-1) {
                String lat = this.csv.get(i).get(0);
                String lon = this.csv.get(i).get(1);
                GeoPosition uusGeo = new GeoPosition(Double.parseDouble(lat.replaceAll("\"", "")), 
                                            Double.parseDouble(lon.replaceAll("\"", "")));
                Node node1 = new Node(uusGeo);
                Double dist = getDist(i-1);
                nodes.get(nodes.size()-1).lisaaNaapuri(node1,dist);
                node1.lisaaNaapuri(nodes.get(nodes.size()-1), dist);
                nodes.add(node1);
                i--;
                amount++;
            }
            if(index==0 && indexMinus==0) {
                System.out.println("AMMUU");
                GeoPosition[] geopos = geoKolme(i);
                Double dist = getDist(i+1);
                Node node1 = new Node(geopos[0]);
                Node node2 = new Node(geopos[1]);
                Node node3 = new Node(geopos[2]);
                node3.lisaaNaapuri(node1, dist);
                node3.lisaaNaapuri(node2,dist);
                node2.lisaaNaapuri(node3, dist);
                node1.lisaaNaapuri(node3, dist);
                nodes.add(node1);
                nodes.add(node2);
                nodes.add(node3);
                i++;
                amount++;
            }
            /*
            if(this.csv.get(i).get(16).equals("\"-1\"") && this.csv.get(i+1).get(16).equals("\"-1\"")) {
                System.out.println("DINGDINGDING");
                System.out.println(this.csv.get(i));
                System.out.println(this.csv.get(i+1));
                //i++;
            }*/
            
            System.out.println("AMOUNT: "+amount+" i: "+i);
        }
        return nodes;
    }
    
    /*
    * Hakee etäisyyden annetun indeksinoden ja sitä seuraavan noden välillä
    */
    public Double getDist(Integer i) {
        String str = this.csv.get(i+1).get(20);
        Double doub = null;
        str = str.replaceAll("^\"|\"$", "");
        if(str.equals("1\"1\"1") || str.equals("\"\"1\"1\"1\"\"") || str.equals("\"1\"1\"1\"")) {
            doub = 1.0;
        } else {
            try{
                doub =  Double.parseDouble(str);
            }catch(Exception e) {
                doub = 1.0;
            }
            
        }
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
        GeoPosition uusGeo = new GeoPosition(Double.parseDouble(lat.replaceAll("\"", "")), 
                                            Double.parseDouble(lon.replaceAll("\"", "")));
        GeoPosition uusGeo2 = new GeoPosition(Double.parseDouble(lat2.replaceAll("\"", "")), 
                                            Double.parseDouble(lon2.replaceAll("\"", "")));
        ret[0] = uusGeo;
        ret[1] = uusGeo2;
        return ret;
    }
    
    public GeoPosition[] geoKolme(Integer i) {
        GeoPosition[] ret = new GeoPosition[3];
        String lat = this.csv.get(i).get(0);
        String lon = this.csv.get(i).get(1);
        String lat2 = this.csv.get(i+1).get(0);
        String lon2 = this.csv.get(i+2).get(1);
        String lat3 = this.csv.get(i+2).get(0);
        String lon3 = this.csv.get(i+2).get(1);
        GeoPosition uusGeo = new GeoPosition(Double.parseDouble(lat.replaceAll("\"", "")), 
                                            Double.parseDouble(lon.replaceAll("\"", "")));
        GeoPosition uusGeo2 = new GeoPosition(Double.parseDouble(lat2.replaceAll("\"", "")), 
                                            Double.parseDouble(lon2.replaceAll("\"", "")));
        GeoPosition uusGeo3 = new GeoPosition(Double.parseDouble(lat3.replaceAll("\"", "")), 
                                            Double.parseDouble(lon3.replaceAll("\"", "")));
        ret[0] = uusGeo;
        ret[1] = uusGeo2;
        ret[2] = uusGeo3;
        return ret;
    }
    
    public ArrayList<GeoPosition> getGeoPos() {
        return this.geopos;
    }
}
