package Tietorakenteet;

/**
 * PrioJonon elementti 
 * @author toni_
 */
public class PrioNode {
    
    Node nod;
    Double prio;
    
    public PrioNode(Node nod, Double prio) {
        this.nod = nod;
        this.prio=prio;
    }

    public Node getNod() {
        return nod;
    }

    public void setNod(Node nod) {
        this.nod = nod;
    }

    public Double getPrio() {
        return prio;
    }

    public void setPrio(Double prio) {
        this.prio = prio;
    }
}
