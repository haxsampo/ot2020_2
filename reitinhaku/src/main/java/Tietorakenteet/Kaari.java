package Tietorakenteet;

import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * Esittää Open Street Mapista saatavan reittidatan yhtä vektoria
 */
public class Kaari {
    
    GeoPosition eka;
    GeoPosition toka;
    double dist;
    
    
    public Kaari(GeoPosition eka, GeoPosition toka) {
        this.eka = eka;
        this.toka = toka;
    }
}
