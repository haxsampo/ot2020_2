package Tietorakenteet;

import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * Node-luokka kuvaa tieaineiston digitointivaiheessa piirrettyjen viivojen
 * yhtä päätä. Kaksi nodea muodostavat viivan.
 * @author toni_
 */
public class Node {
    
    ArrayList<Node> naapurit;
    ArrayList<Double> etaisyydet;
    GeoPosition pos;
    Integer id;
    
    /*
     * Konstruktori - luo uuden noden annetulla GeoPositiolla. Geoposition
     * sisältää latitudin ja longitudin.
     */
    public Node(GeoPosition pos) {
        this.pos = pos;
        this.naapurit = new ArrayList<Node>();
        this.etaisyydet = new ArrayList<Double>();
    }
    
    public Node(GeoPosition pos, Integer id) {
        this.pos = pos;
        this.naapurit = new ArrayList<Node>();
        this.etaisyydet = new ArrayList<Double>();
        this.id = id;
    }

    public GeoPosition getPos() {
        return pos;
    }

    public ArrayList<Node> getNaapurit() {
        return naapurit;
    }

    public void setNaapurit(ArrayList<Node> naapurit) {
        this.naapurit = naapurit;
    }

    public void setPos(GeoPosition pos) {
        this.pos = pos;
    }
    
    public void lisaaNaapuri(Node node, Double etaisyys) {
        this.etaisyydet.add(etaisyys);
        this.naapurit.add(node);
    }

    public ArrayList<Double> getEtaisyydet() {
        return etaisyydet;
    }

    public void setEtaisyydet(ArrayList<Double> etaisyydet) {
        this.etaisyydet = etaisyydet;
    }
    
    @Override
    public String toString() {
        String pal = this.pos.getLatitude()+ " - "+pos.getLongitude()+", naapurien määrä: "+this.naapurit.size();
        return pal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
