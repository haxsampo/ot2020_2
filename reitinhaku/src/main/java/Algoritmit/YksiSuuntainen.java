package Algoritmit;

import Tietorakenteet.Node;
import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * Testausalgoritmi joka yrittää päästä aina etelään tai pohjoiseen argumentin
 * perusteella
 * @author toni_
 */
public class YksiSuuntainen {
    
    public YksiSuuntainen() {
        
    }
    
    public ArrayList<GeoPosition> laskeReitti(String suunta, Node nod) {
        ArrayList<GeoPosition> ret = new ArrayList<GeoPosition>();
        ret.add(nod.getPos());
        ArrayList<Node> kaydyt = new ArrayList<Node>();
        while(true) {
            Node uusi = valitseNaapuri(nod, suunta);
            if(uusi == nod) { //ei enää uusia nodeja halutussa suunnassa
                //System.out.println("lat:"+nod.getPos().getLatitude() + " lon: "+nod.getPos().getLongitude());
                GeoPosition nap = nod.getNaapurit().get(0).getPos();
                //System.out.println("node nap length"+nod.getNaapurit().size());
                //System.out.println("napLat: "+ nap.getLatitude() + " napLon: "+nap.getLongitude());
                break;
            } else {
                nod = uusi;
                ret.add(nod.getPos());
            }
        }
        return ret;
    }
    
    public Node valitseNaapuri(Node nod, String suunta) {
        Node ret = nod;
        for(Node el : nod.getNaapurit()) {
            if(suunta.equals("p")) {
                if(el.getPos().getLatitude() > ret.getPos().getLatitude()) {
                    ret = el;
                }
            } else {
                if(ret.getPos().getLatitude()> el.getPos().getLatitude()) {
                    ret = el;
                }
            }
            
        }
        return ret;
    }
}
