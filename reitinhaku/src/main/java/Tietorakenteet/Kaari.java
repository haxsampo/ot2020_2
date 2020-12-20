package Tietorakenteet;

import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * Esittää noden yhteyttä yhteen naapuriinsa.
 * @param id - Kaaren oma SQL-kannan id
 * @param nodeId1 - Node jonka naapuriLista on kyseessä
 * @param nodeId2 - Node joka on nodeId1:sen naapurilistalla
 * @param pituus - Kaaren pituus
 */
public class Kaari {
    
    int id;
    int nodeId1;
    int nodeId2;
    Double pituus;
    
    
    public Kaari(Double pituus, int id, int nodeId1, int nodeId2) {
        this.pituus = pituus;
        this.id = id;
        this.nodeId1 = nodeId1;
        this.nodeId2 = nodeId2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPituus() {
        return pituus;
    }

    public void setPituus(Double pituus) {
        this.pituus = pituus;
    }

    public int getNodeId1() {
        return nodeId1;
    }

    public void setNodeId1(int nodeId1) {
        this.nodeId1 = nodeId1;
    }

    public int getNodeId2() {
        return nodeId2;
    }

    public void setNodeId2(int nodeId2) {
        this.nodeId2 = nodeId2;
    }
    
    
}
