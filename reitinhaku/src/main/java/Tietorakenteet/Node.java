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
    
    ArrayList<Kaari> kaaret;
    GeoPosition pos;
    
    public Node(GeoPosition pos) {
        this.pos = pos;
        this.kaaret = new ArrayList<Kaari>();
    }
    
    public void lisaaKaari(Kaari kaari) {
        //Tsekkaa ettei oo samaa?
        this.kaaret.add(kaari);
    }
}
