package Algoritmit;

import Tietorakenteet.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jxmapviewer.viewer.GeoPosition;
/*
Luokka sisältää nodet joita käytettään reitinhakualgoritmissa
*/
public class NodeHolder {
    
    ArrayList<Node> nodes;
    
    public NodeHolder() {
        this.nodes = new ArrayList<Node>();
    }
    
    public void add(Node node) {
        nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    
    /*
        Sisään lista jossa on laskettu naapureiksi jo samalla rivillä CSV:ssä 
        olleet. Funktio laskee listasta samaa geopositiota esittävät nodet ja
        muokkaa ne yksittäisiksi nodeiksi joilla on oikeat naapurit
    */
    public void laskeNaapurit() {
        ArrayList<Node> uusi = new ArrayList<Node>();
        ArrayList<Integer> kasitellyt = new ArrayList<Integer>();
        for(int i = 0;i<this.nodes.size();i++) {
            Node curr = this.nodes.get(i);
            ArrayList<Integer> naapuriIndeksit = new ArrayList<Integer>();
            if(!(kasitellyt.contains(i))) {
                for(int j = 0;j<this.nodes.size();j++) {
                    if(!(i==j) && !(kasitellyt.contains(j))) {
                        if(curr.getPos() == this.nodes.get(j).getPos()) {
                            naapuriIndeksit.add(j);
                        }
                    }  
                }
            }//if naapuriindeksi.size() == 0
            naapuriIndeksit.add(i);
            lisaaIndeksilla(naapuriIndeksit, uusi);
            kasitellyt.addAll(naapuriIndeksit);
        }
        this.nodes=uusi;
    }
    
    /*
    laskeNaapurit apufunktio - lisää indeksilistan nodejen naapurit listan 
    ensimmäisen noden naapurit listalle
    */
    public void lisaaIndeksilla(ArrayList<Integer> indeksiLista, ArrayList<Node> uusi) {
        Node cur = nodes.get(indeksiLista.get(0));
        for(int i = 1; i < indeksiLista.size(); i++) {
            Node toinen = nodes.get(indeksiLista.get(i));
            yhdistaNaapurit(cur,toinen);
        }
        uusi.add(cur);
    }
    /*
    Lisää ekan noden naapureihin tokan noden naapurit
    */
    public void yhdistaNaapurit(Node eka, Node toka) {
        for(Node naapuri : toka.getNaapurit()) {
            eka.getNaapurit().add(naapuri);
        }
    }
    /*
    Palauttaa listassa n. argumentin verran geopositiota joista yksikään ei ole
    sama
    -> toteuta
    */
    public ArrayList<GeoPosition> haeSatunnaiset(Integer maara) {
        ArrayList<GeoPosition> pal = new ArrayList<GeoPosition>();
        if(maara == 0 || maara > 1000) {
            maara = 1;
        }
        Random rand = new Random();
        Node nod = nodes.get(rand.nextInt(nodes.size()-1));
        pal.add(nod.getPos());
        Integer r = rand.nextInt(nod.getNaapurit().size()-1);
        if(r<0){
            r = 0;
        }
        Node nod2 = nod.getNaapurit().get(r);
        pal.add(nod2.getPos());
        for(int i = 0; i>maara;i++) {
            nod = nod2;
            Integer ran = rand.nextInt(nod2.getNaapurit().size()-1);
            if(r<=0){ //Umpikuja
                break;
            }
            nod2 = nodeRec(nod,nod2,ran);
            

        }      
        return pal;
    }
    
    public Node nodeRec(Node nod, Node nod2, Integer ran) {
        nod2 = nod2.getNaapurit().get(ran);
        if(nod2.getPos() == nod.getPos()) {
            nod2 = nodeRec(nod2, nod, ran);
        }
        return nod;
    }
}


