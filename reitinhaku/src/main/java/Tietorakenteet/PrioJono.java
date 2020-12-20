package Tietorakenteet;

import java.util.ArrayList;

/**
 * Luokka järjestää ArrayListallista nodeja siten, että lyhyin matka
 * @author toni_
 */
public class PrioJono {
    
    ArrayList<PrioNode> jono;
    
    public PrioJono(){
        this.jono = new ArrayList<PrioNode>();
    }
    
    public void lisaaJonoon(PrioNode uusi) {
        if(jono.size() == 0) {
            jono.add(uusi);
        } else {
            for(int i = 0; i<this.jono.size();i++) {
                if(jono.get(i).getPrio() > uusi.getPrio()) {
                    jono.add(i,uusi);
                    break;
                }
            }
        }
        
    }
    
    public PrioNode pop() {
        if(jono.size()==0) {
            return null;
        }
        PrioNode pal = this.jono.get(0);
        System.out.println(pal.getPrio());
        this.jono.remove(0);
        System.out.println(pal.getPrio());
        return pal;
    }

    public ArrayList<PrioNode> getJono() {
        return jono;
    }

    public void setJono(ArrayList<PrioNode> jono) {
        this.jono = jono;
    }
}
