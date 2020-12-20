package Algoritmit;

import Tietorakenteet.Haversine;
import Tietorakenteet.Node;
import Tietorakenteet.PrioJono;
import Tietorakenteet.PrioNode;
import java.util.ArrayList;
import java.util.HashMap;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author toni_
 */
public class Atahti {
   
    PrioJono pj;
    ArrayList<Node> nodet;
    Haversine haver;
    HashMap<Node,Node> reitti;
    ArrayList<GeoPosition> valmisReitti;
    Node kohde;
    Node alku;
    
    public Atahti(ArrayList<Node> nodet) {
        this.pj = new PrioJono();
        this.nodet = new ArrayList<Node>();
        this.haver = new Haversine();
        HashMap<Node,Node> reitti = new HashMap<Node,Node>();
    }
    
    public ArrayList<GeoPosition> etsiReitti(Node alku, Node kohde) {
        this.kohde = kohde;
        this.alku = alku;
        ArrayList<GeoPosition> pal = new ArrayList<GeoPosition>();
        PrioNode pnAlku = new PrioNode(alku, 0.0);
        pj.lisaaJonoon(pnAlku);
        HashMap<Node,Node> cameFrom = new HashMap<Node,Node>();
        HashMap<Node,Double> costSoFar = new HashMap<Node,Double>();
        cameFrom.put(alku,alku);
        costSoFar.put(alku,0.0);
        while(pj.getJono().size()>0){
            PrioNode current = pj.pop();
            GeoPosition curPos = current.getNod().getPos();
            if(curPos.getLatitude()==kohde.getPos().getLatitude() &&
                    curPos.getLongitude()==kohde.getPos().getLongitude()) {
                break;
            }
            for(int i = 0;i<current.getNod().getNaapurit().size();i++) {
                Double hinta = costSoFar.get(current.getNod()) +
                        current.getNod().getEtaisyydet().get(i);
                boolean nodeInCosts = costSoFar.containsKey(
                                    current.getNod().getNaapurit().get(i));
                if(!nodeInCosts || hinta <costSoFar.get( //käsiteltävä naapurinode ei ole vielä costSoFarissa
                                    current.getNod().getNaapurit().get(i))){
                    costSoFar.put(current.getNod().getNaapurit().get(i), hinta);
                    Double newPrio = hinta + haver.nodeDist(kohde,current.getNod().getNaapurit().get(i));
                    PrioNode next = new PrioNode(current.getNod().getNaapurit().get(i),newPrio);
                    pj.lisaaJonoon(next);
                    cameFrom.put(current.getNod().getNaapurit().get(i),current.getNod());
                }
            }
        }
        this.reitti = cameFrom;
        return pal;
    }
    
    public void parsiReitti(){
        ArrayList<GeoPosition> pal = new ArrayList<GeoPosition>();
        Node cur = this.kohde;
        while(true) {
            if(cur==alku) {
                break;
            }
            pal.add(cur.getPos());
            cur = reitti.get(cur);
        }
        this.valmisReitti = pal;
    }

    public ArrayList<GeoPosition> getValmisReitti() {
        return valmisReitti;
    }

    public void setValmisReitti(ArrayList<GeoPosition> valmisReitti) {
        this.valmisReitti = valmisReitti;
    }
    
}
