/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tietorakenteet;

import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author toni_
 */
public class Node {
    
    ArrayList<Node> naapurit;
    GeoPosition pos;
    
    public Node(GeoPosition pos) {
        this.pos = pos;
        this.naapurit = new ArrayList<Node>();
    }
    
    public void lisaaNaapuri(Node node) {
        naapurit.add(node);
    }
}
