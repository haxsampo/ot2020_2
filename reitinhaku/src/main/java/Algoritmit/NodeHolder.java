package Algoritmit;

import Tietorakenteet.Node;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.jxmapviewer.viewer.GeoPosition;
/*
Luokka sisältää nodet joita käytettään reitinhakualgoritmissa ja toimii jalostimena
csv:stä tuotetulle reittimateriaalille.
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
        boolean[] kasitellyt = new boolean[this.nodes.size()];
        for(int i = 0;i<this.nodes.size();i++) {
            //System.out.println("laskeNaapurit i: "+i);
            Node curr = this.nodes.get(i);            
            ArrayList<Integer> naapuriIndeksit = new ArrayList<Integer>();
            if(!(kasitellyt[i])) { //jos kasiteltava on kasitelty
                for(int j = 0;j<this.nodes.size();j++) {
                    if(!(i==j) && !(kasitellyt[j])) {
                        GeoPosition other = this.nodes.get(j).getPos();
                        GeoPosition cur = curr.getPos();
                        if(other.getLatitude() == cur.getLatitude() &&
                                other.getLongitude() == cur.getLongitude()) {
                            naapuriIndeksit.add(j);
                        }
                    }
                }
                naapuriIndeksit.add(i);
                lisaaIndeksilla(naapuriIndeksit, uusi);
                kasitellyt = lisaaKasitellyt(naapuriIndeksit, kasitellyt);
            }
        }
        this.nodes=uusi;
    }
    /*
    Muuttaa kasitellyt listalta naapurit listan sisältämät indeksit trueksi
    */
    public boolean[] lisaaKasitellyt(ArrayList<Integer> naapurit, boolean[] kasitellyt) {
        for(Integer indeksi : naapurit) {
            kasitellyt[indeksi] = true;
        }
        return kasitellyt;
    }
    
    /*
    * laskeNaapurit apufunktio - lisää indeksilistan nodejen naapuritlistan 
    * ensimmäisen noden naapuritlistalle. lisää lopuksi ensimmäisen noden uusi
    * listalle
    */
    public void lisaaIndeksilla(ArrayList<Integer> indeksiLista, ArrayList<Node> uusi) {
        Integer pienin = getSmallest(indeksiLista);
        Node cur = nodes.get(pienin);
        for(int i = 0; i < indeksiLista.size(); i++) {
            Node toinen = nodes.get(indeksiLista.get(i));
            yhdistaNaapurit(cur,toinen);
        }
        uusi.add(cur);
    }
    /*
    
    */
    public Integer getSmallest(ArrayList<Integer> list) {
        Integer ret = 99999;
        int index = 0;
        for(int i = 0; i<list.size();i++) {
            if(list.get(i)<ret) {
                ret = list.get(i);
                index=i;
            }
        }
        list.remove(index);
        return ret;
    }
    /*
    Lisää ekan noden naapureihin tokan noden naapurit
    */
    public void yhdistaNaapurit(Node eka, Node toka) {
        for(int i = 0; i < toka.getNaapurit().size(); i++) {
            Double etais = toka.getEtaisyydet().get(i);
            Node nap = toka.getNaapurit().get(i);
            eka.lisaaNaapuri(nap,etais);
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
    
    /*
    Indeksoi nodes-listan nodet SQL-kantaa varten. Indeksoi myös naapuritlistat
    erikseen.
    */
    public void indeksoiNodet() {
        for(int i = 1;i<this.nodes.size()+1;i++) {
            this.nodes.get(i-1).setId(i);          
        }
        for(int i = 0; i<this.nodes.size();i++) {
            ArrayList<Node> naapurit = this.nodes.get(i).getNaapurit();
            int loytyneetParit = 0;
            for(int j = 0;j<this.nodes.size();j++) {
                if(!(i==j)) {
                    naapuriEquals(naapurit,this.nodes.get(j), loytyneetParit);
                } 
            }
        }
    }
    
    /*
    indeksoiNodet-funktion apufunktio - Vertaa annettua nodea nod naapurit listan 
    nodeihin. Jos samanlainen löytyy, niin antaa naapuritlistan nodelle nod:en 
    id nron. toFind toimii terminaattorina kun tarvittava määrä naapureita on
    löytynyt.
    */
    private void naapuriEquals(ArrayList<Node> naapurit, Node nod, int parit) {
        for(int i = 0; i<naapurit.size();i++) {
            if(nod.getPos().getLatitude() == naapurit.get(i).getPos().getLatitude() &&
                    nod.getPos().getLongitude() == naapurit.get(i).getPos().getLongitude()) {
                naapurit.get(i).setId(nod.getId());
                parit++;
            }
        } 
    }
}


